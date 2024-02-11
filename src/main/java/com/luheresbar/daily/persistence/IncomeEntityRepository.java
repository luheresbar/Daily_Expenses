package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.repository.IIncomeRepository;
import com.luheresbar.daily.persistence.crud.IIncomeCrudRepository;
import com.luheresbar.daily.persistence.entity.IncomeEntity;
import com.luheresbar.daily.persistence.mapper.IIncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IncomeEntityRepository implements IIncomeRepository {

    private final IIncomeCrudRepository incomeCrudRepository;
    private final JdbcTemplate jdbcTemplate;
    private final IIncomeMapper incomeMapper;

    @Autowired
    public IncomeEntityRepository(IIncomeCrudRepository incomeCrudRepository, JdbcTemplate jdbcTemplate, IIncomeMapper incomeMapper) {
        this.incomeCrudRepository = incomeCrudRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.incomeMapper = incomeMapper;
    }


    @Override
    public List<Income> getUserIncomes(Integer userId) {

        // Metodo usando JdbcTemplate
        String sql = "SELECT * FROM incomes WHERE user_id = ? ORDER BY income_date DESC";
        List<IncomeEntity> incomeEntities = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(IncomeEntity.class)); // TODO reeemplazar JdbcTemplate, ya que esta dreprecate
        return this.incomeMapper.toIncomes(incomeEntities);

    }

    @Override
    public List<Income> getAccountIncomes(String accountName, Integer userId) {
        return this.incomeMapper.toIncomes(this.incomeCrudRepository.getAccountIncomes(accountName, userId));
    }

    @Override
    public Income save(Income income) {
        IncomeEntity incomeEntity = incomeMapper.toIncomeEntity(income);
        return incomeMapper.toIncome(incomeCrudRepository.save(incomeEntity));
    }

    @Override
    public Optional<Income> getById(int incomeId) {
        Optional<IncomeEntity> incomeEntity = this.incomeCrudRepository.findById(incomeId);
        return incomeEntity.map(income -> this.incomeMapper.toIncome(income));
    }


    @Override
    public boolean delete(int incomeId, Integer userId) {
        IncomeEntity incomeEntity = this.incomeCrudRepository.findById(incomeId).orElse(null);
        if(incomeEntity != null && incomeEntity.getUserId().equals(userId)) {
            this.incomeCrudRepository.delete(incomeEntity);
            return true;
        }
        return false;
    }
}
