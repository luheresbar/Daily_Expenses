package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.Income;

import java.util.List;
import java.util.Optional;

public interface IIncomeRepository {

    List<Income> getUserIncomes(String userId);

    Income save(Income income);

    boolean delete(int incomeId, String userId);

    Optional<Income> getById(int incomeId);

    List<Income> getAccountIncomes(String accountName, String userId);

}
