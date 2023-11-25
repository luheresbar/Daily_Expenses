package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.repository.IIncomeRepository;
import com.luheresbar.daily.persistence.crud.IIncomeCrudRepository;
import com.luheresbar.daily.persistence.entity.IncomeEntity;
import com.luheresbar.daily.persistence.mapper.IIncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncomeEntityRepository implements IIncomeRepository {

    private final IIncomeCrudRepository incomeCrudRepository;
    private final IIncomeMapper incomeMapper;

    @Autowired
    public IncomeEntityRepository(IIncomeCrudRepository incomeCrudRepository, IIncomeMapper incomeMapper) {
        this.incomeCrudRepository = incomeCrudRepository;
        this.incomeMapper = incomeMapper;
    }


    @Override
    public List<Income> getAll() {
        List<IncomeEntity> incomes = incomeCrudRepository.findAll();
        return incomeMapper.toIncomes(incomes);
    }
}
