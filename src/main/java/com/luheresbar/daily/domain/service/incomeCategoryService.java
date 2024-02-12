package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.domain.repository.IIncomeCategoryRepository;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class incomeCategoryService {


    private final IIncomeCategoryRepository incomeCategoryRepository;

    public incomeCategoryService(IIncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    public List<IncomeCategory> getByUser(Integer userId) {
        return this.incomeCategoryRepository.getByUser(userId);
    }

    public boolean exists(IncomeCategoryPK expenseCategoryPK) {
        return this.incomeCategoryRepository.exists(expenseCategoryPK);
    }

    public IncomeCategory save(IncomeCategory expenseCategory) {
        return this.incomeCategoryRepository.save(expenseCategory);
    }

    public void delete(IncomeCategoryPK expenseCategoryPK) {
        this.incomeCategoryRepository.delete(expenseCategoryPK);
    }

}
