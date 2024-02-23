package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.repository.IExpenseCategoryRepository;
import com.luheresbar.daily.persistence.crud.IExpenseCategoryCrudRepository;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import com.luheresbar.daily.persistence.mapper.IExpenseCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseCategoryRepository implements IExpenseCategoryRepository {

    private final IExpenseCategoryCrudRepository expenseCategoryCrudRepository;
    private final IExpenseCategoryMapper categoryMapper;

    public ExpenseCategoryRepository(IExpenseCategoryCrudRepository categoryCrudRepository, IExpenseCategoryMapper categoryMapper) {
        this.expenseCategoryCrudRepository = categoryCrudRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<ExpenseCategory> getByUser(Integer userId) {
        List<ExpenseCategoryEntity> expenseCategoryEntity = this.expenseCategoryCrudRepository.findAllByUserIdOrderByCategoryName(userId);
        return categoryMapper.toExpenseCategories(expenseCategoryEntity);
    }

    @Override
    public Optional<ExpenseCategory> getById(String categoryName, Integer userId) {
        ExpenseCategoryPK categoryPK = new ExpenseCategoryPK(categoryName, userId);
        Optional<ExpenseCategoryEntity> expenseCategory = this.expenseCategoryCrudRepository.findById(categoryPK);
        return expenseCategory.map(acc -> this.categoryMapper.toExpenseCategory(acc));
    }

    @Override
    public List<ExpenseCategory> getEnabledCategoriesByUser(Integer userId) {
        List<ExpenseCategoryEntity> accounts = this.expenseCategoryCrudRepository.findAllAvailableByUserIdOrderByCategoryName(userId);
        return categoryMapper.toExpenseCategories(accounts);
    }

    @Override
    public List<ExpenseCategory> getDisabledCategoriesByUser(Integer userId) {
        List<ExpenseCategoryEntity> accounts = this.expenseCategoryCrudRepository.findAllNoAvailableByUserIdOrderByCategoryName(userId);
        return categoryMapper.toExpenseCategories(accounts);
    }

    @Override
    public boolean exists(ExpenseCategoryPK expenseCategoryPK) {
        return this.expenseCategoryCrudRepository.existsById(expenseCategoryPK);
    }

    @Override
    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        ExpenseCategoryEntity expenseCategoryEntity = this.categoryMapper.toExpenseCategoryEntity(expenseCategory);
        return categoryMapper.toExpenseCategory(this.expenseCategoryCrudRepository.save(expenseCategoryEntity));
    }

    @Override
    public void delete(ExpenseCategoryPK expenseCategoryPK) {
        this.expenseCategoryCrudRepository.deleteById(expenseCategoryPK);
    }

    @Override
    public void updateNameCategory(String categoryName, String newCategoryName, Integer userId) {
        this.expenseCategoryCrudRepository.updateExpenseCategory(categoryName, newCategoryName, userId);
    }


}




