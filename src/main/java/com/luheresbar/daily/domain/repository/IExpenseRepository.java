package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Expense;

import java.util.List;

public interface IExpenseRepository {

    List<Expense> getUserExpenses(String userId);

    Expense save(Expense expense);

}
