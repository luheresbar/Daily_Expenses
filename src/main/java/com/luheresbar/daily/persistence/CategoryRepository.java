package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.repository.ICategoryRepository;
import com.luheresbar.daily.persistence.crud.ICategoryCrudRepository;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.CategoryPK;
import com.luheresbar.daily.persistence.mapper.ICategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository implements ICategoryRepository {

    private final ICategoryCrudRepository categoryCrudRepository;
    private final ICategoryMapper categoryMapper;

    @Autowired
    public CategoryRepository(ICategoryCrudRepository categoryCrudRepository, ICategoryMapper categoryMapper) {
        this.categoryCrudRepository = categoryCrudRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<ExpenseCategory> getByUser(Integer userId) {
        List<ExpenseCategoryEntity> expenseCategoryEntity =  this.categoryCrudRepository.findAllByUserIdOrderByCategoryName(userId);
        return categoryMapper.toCategories(expenseCategoryEntity);
    }

    @Override
    public boolean exists(CategoryPK categoryPK) {
        return this.categoryCrudRepository.existsById(categoryPK);
    }

    @Override
    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        ExpenseCategoryEntity expenseCategoryEntity = this.categoryMapper.toCategoryEntity(expenseCategory);
        return categoryMapper.toCategory(this.categoryCrudRepository.save(expenseCategoryEntity));
    }

    @Override
    public void delete(CategoryPK categoryPK) {
        this.categoryCrudRepository.deleteById(categoryPK);
    }
}




