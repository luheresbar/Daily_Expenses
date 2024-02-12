package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.repository.IExpenseCategoryRepository;
import com.luheresbar.daily.persistence.crud.IExpenseCategoryCrudRepository;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import com.luheresbar.daily.persistence.mapper.ICategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpenseCategoryRepository implements IExpenseCategoryRepository {

    private final IExpenseCategoryCrudRepository categoryCrudRepository;
    private final ICategoryMapper categoryMapper;

    @Autowired
    public ExpenseCategoryRepository(IExpenseCategoryCrudRepository categoryCrudRepository, ICategoryMapper categoryMapper) {
        this.categoryCrudRepository = categoryCrudRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<ExpenseCategory> getByUser(Integer userId) {
        List<ExpenseCategoryEntity> expenseCategoryEntity =  this.categoryCrudRepository.findAllByUserIdOrderByCategoryName(userId);
        return categoryMapper.toCategories(expenseCategoryEntity);
    }

    @Override
    public boolean exists(ExpenseCategoryPK expenseCategoryPK) {
        return this.categoryCrudRepository.existsById(expenseCategoryPK);
    }

    @Override
    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        ExpenseCategoryEntity expenseCategoryEntity = this.categoryMapper.toCategoryEntity(expenseCategory);
        return categoryMapper.toCategory(this.categoryCrudRepository.save(expenseCategoryEntity));
    }

    @Override
    public void delete(ExpenseCategoryPK expenseCategoryPK) {
        this.categoryCrudRepository.deleteById(expenseCategoryPK);
    }
}




