package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.repository.IIncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private final IIncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IIncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<Income> getAll() {
        return incomeRepository.getAll();
    }

}
