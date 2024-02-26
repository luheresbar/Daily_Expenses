package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Expense;

import java.util.List;
import java.util.Optional;

public interface IExpenseRepository {

    List<Expense> getUserExpenses(Integer userId);

    Expense save(Expense expense);

    boolean delete(int expenseId, Integer userId);

    Optional<Expense> getById(int expenseId);

    List<Expense> getAccountExpenses(String accountName, Integer userId);


}
