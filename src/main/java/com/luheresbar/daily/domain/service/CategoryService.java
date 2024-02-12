package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.repository.ICategoryRepository;
import com.luheresbar.daily.persistence.entity.CategoryPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ExpenseCategory> getByUser(Integer userId) {
        return this.categoryRepository.getByUser(userId);
    }

    public boolean exists(CategoryPK categoryPK) {
        return this.categoryRepository.exists(categoryPK);
    }

    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        return this.categoryRepository.save(expenseCategory);
    }

    public void delete(CategoryPK categoryPK) {
        this.categoryRepository.delete(categoryPK);
    }
}
