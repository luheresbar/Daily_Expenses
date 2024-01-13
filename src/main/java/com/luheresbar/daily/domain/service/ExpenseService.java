package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.repository.IExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final IExpenseRepository expenseRepository;

    public ExpenseService(IExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getUserExpenses(Integer userId) {
        return expenseRepository.getUserExpenses(userId);
    }

    public Optional<Expense> getById(int expenseId) {
        return this.expenseRepository.getById(expenseId);
    }

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }
    public boolean delete(int expenseId, Integer userId) {
        return this.expenseRepository.delete(expenseId, userId);
    }

    public List<Expense> getAccountExpenses(String accountName, Integer userId) {
        return this.expenseRepository.getAccountExpenses(accountName, userId);
    }
}
