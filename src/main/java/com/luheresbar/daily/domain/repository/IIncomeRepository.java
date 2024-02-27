package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Income;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IIncomeRepository {

    List<Income> getUserIncomes(Integer userId);

    Income save(Income income);

    boolean delete(int incomeId, Integer userId);

    Optional<Income> getById(int incomeId);

    List<Income> getAccountIncomes(String accountName, Integer userId);

    List<Income> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
