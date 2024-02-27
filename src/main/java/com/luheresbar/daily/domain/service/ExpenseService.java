package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.repository.IExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<Expense> getAccountExpenses(String accountName, Integer userId) {
        return this.expenseRepository.getAccountExpenses(accountName, userId);
    }

    public List<Expense> findByDateBetween (LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return this.expenseRepository.findByDateBetween(startDate, endDate, userId);
    }

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    public boolean delete(int expenseId, Integer userId) {
        return this.expenseRepository.delete(expenseId, userId);
    }

    public Double getTotalExpense(List<Expense> expenses) {
        Double totalExpense = 0.0;
        for (int i = 0; i < expenses.size(); i++) {
            totalExpense += expenses.get(i).getExpense();
        }
        return totalExpense;
    }

}
