package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.repository.IIncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final IIncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IIncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<Income> getUserIncomes(String userId) {
        return incomeRepository.getUserIncomes(userId);
    }

    public Optional<Income> getById(int incomeId) {
        return this.incomeRepository.getById(incomeId);
    }

    public Income save(Income income) {
        return incomeRepository.save(income);
    }
    public boolean delete(int incomeId, String userId) {
        return this.incomeRepository.delete(incomeId, userId);
    }

    public List<Income> getAccountIncomes(String accountName, String userId) {
        return this.incomeRepository.getAccountIncomes(accountName, userId);
    }

}
