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

    public List<Expense> getUserExpenses(String userId) {
        return expenseRepository.getUserExpenses(userId);
    }

//    public Optional<Expense> getById(int expenseId) {
//        return this.expenseRepository.getById(expenseId);
//    }
//
//    public Expense save(Expense expense) {
//        return expenseRepository.save(expense);
//    }
//    public boolean delete(int expenseId, String userId) {
//        return this.expenseRepository.delete(expenseId, userId);
//    }
//
//    public List<Expense> getAccountExpenses(String accountName, String userId) {
//        return this.expenseRepository.getAccountExpenses(accountName, userId);
    }

}
