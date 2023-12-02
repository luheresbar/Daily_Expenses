package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.Income;

import java.util.List;
import java.util.Optional;

public interface IIncomeRepository {

    List<Expense> getUserExpenses(String userId);

//    Expense save(Expense expense);
//
//    boolean delete(int expenseId, String userId);
//
//    Optional<Expense> getById(int expenseId);
//
//    List<Expense> getAccountExpenses(String accountName, String userId);

}
