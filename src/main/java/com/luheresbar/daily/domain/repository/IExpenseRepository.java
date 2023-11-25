package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Expense;

import java.util.List;

public interface IExpenseRepository {

    List<Expense> getAll();

    Expense save(Expense expense);

}
