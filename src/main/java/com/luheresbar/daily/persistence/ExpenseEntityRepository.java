package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.repository.IExpenseRepository;
import com.luheresbar.daily.persistence.crud.IExpenseCrudRepository;
import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import com.luheresbar.daily.persistence.mapper.IExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpenseEntityRepository implements IExpenseRepository {

    private final IExpenseCrudRepository expenseCrudRepository;
    private final IExpenseMapper expenseMapper;

    @Autowired
    public ExpenseEntityRepository(IExpenseCrudRepository expenseCrudRepository, IExpenseMapper expenseMapper) {
        this.expenseCrudRepository = expenseCrudRepository;
        this.expenseMapper = expenseMapper;
    }

    @Override
    public List<Expense> getAll() {
        List<ExpenseEntity> expenses = expenseCrudRepository.findAll();
        return expenseMapper.toExpenses(expenses);
    }

    @Override
    public Expense save(Expense expense) {
        ExpenseEntity expenseEntity = expenseMapper.toExpenseEntity(expense);
        return expenseMapper.toExpense(expenseCrudRepository.save(expenseEntity));
    }
}
