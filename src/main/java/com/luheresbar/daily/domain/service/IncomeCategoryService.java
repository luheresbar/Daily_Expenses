package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.domain.dto.CategoryDto;
import com.luheresbar.daily.domain.repository.IIncomeCategoryRepository;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeCategoryService {


    private final IIncomeCategoryRepository incomeCategoryRepository;

    public IncomeCategoryService(IIncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    public List<IncomeCategory> getByUser(Integer userId) {
        return this.incomeCategoryRepository.getByUser(userId);
    }

    public Optional<IncomeCategory> getById(String categoryName, Integer userId) {
        return this.incomeCategoryRepository.getById(categoryName, userId);
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

    public List<CategoryDto> incomeCategoriesToDto(List<IncomeCategory> expenseCategories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();

        // Convertir Transfer a TransactionDetail
        for (IncomeCategory category : expenseCategories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryType("income");
            categoryDto.setUserId(category.getUserId());
            categoryDto.setAvailable(category.getAvailable());
            categoryDto.setCategoryName(category.getCategoryName());

            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    public void updateNameCategory(String categoryName, String newCategoryName, Integer userId) {
        this.incomeCategoryRepository.updateNameCategory(categoryName, newCategoryName, userId);
    }

}
