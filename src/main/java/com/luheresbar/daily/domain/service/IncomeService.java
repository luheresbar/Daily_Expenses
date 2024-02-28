package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.repository.IIncomeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final IIncomeRepository incomeRepository;

    public IncomeService(IIncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<Income> getUserIncomes(Integer userId) {
        return incomeRepository.getUserIncomes(userId);
    }

    public Optional<Income> getById(int incomeId) {
        return this.incomeRepository.getById(incomeId);
    }
    public List<Income> getAccountIncomes(String accountName, Integer userId) {
        return this.incomeRepository.getAccountIncomes(accountName, userId);
    }

    public List<Income> findByDateBetween (LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return this.incomeRepository.findByDateBetween(startDate, endDate, userId);
    }

    public Income save(Income income) {
        return incomeRepository.save(income);
    }
    public boolean delete(int incomeId, Integer userId) {
        return this.incomeRepository.delete(incomeId, userId);
    }


    public Double getTotalIncome(List<Income> incomes) {
        Double totalIncome = 0.0;
        for (int i = 0; i < incomes.size(); i++) {
            totalIncome += incomes.get(i).getIncome();
        }
        return totalIncome;
    }

    public Double getMonthlyIncomeTotal(LocalDateTime startDate, LocalDateTime endDate, Integer userId) {
        return this.incomeRepository.getMonthlyIncomeTotal(startDate, endDate, userId);
    }

}
