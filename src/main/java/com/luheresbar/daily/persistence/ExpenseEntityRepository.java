package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.repository.IExpenseRepository;
import com.luheresbar.daily.persistence.crud.IExpenseCrudRepository;
import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import com.luheresbar.daily.persistence.mapper.IExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseEntityRepository implements IExpenseRepository {

    private final IExpenseCrudRepository expenseCrudRepository;
    private final IExpenseMapper expenseMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseEntityRepository(IExpenseCrudRepository expenseCrudRepository, IExpenseMapper expenseMapper, JdbcTemplate jdbcTemplate) {
        this.expenseCrudRepository = expenseCrudRepository;
        this.expenseMapper = expenseMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Expense> getUserExpenses(String userId) {

        // Metodo usando JdbcTemplate
        String sql = "SELECT * FROM expenses WHERE user_id = ? ORDER BY expense_date DESC";
        List<ExpenseEntity> expenseEntities = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(ExpenseEntity.class));
        return this.expenseMapper.toExpenses(expenseEntities);

    }

    @Override
    public List<Expense> getAccountExpenses(String accountName, String userId) {
        return this.expenseMapper.toExpenses(this.expenseCrudRepository.getAccountExpenses(accountName, userId));
    }

    @Override
    public Expense save(Expense expense) {
        ExpenseEntity expenseEntity = expenseMapper.toExpenseEntity(expense);
        return expenseMapper.toExpense(expenseCrudRepository.save(expenseEntity));
    }

    @Override
    public Optional<Expense> getById(int expenseId) {
        Optional<ExpenseEntity> expenseEntity = this.expenseCrudRepository.findById(expenseId);
        return expenseEntity.map(expense -> this.expenseMapper.toExpense(expense));
    }


    @Override
    public boolean delete(int expenseId, String userId) {
        ExpenseEntity expenseEntity = this.expenseCrudRepository.findById(expenseId).orElse(null);
        if(expenseEntity != null && expenseEntity.getUserId().equals(userId)) {
            this.expenseCrudRepository.delete(expenseEntity);
            return true;
        }
        return false;
    }


}
