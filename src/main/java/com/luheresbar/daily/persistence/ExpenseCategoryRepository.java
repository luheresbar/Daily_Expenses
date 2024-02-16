package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.repository.IExpenseCategoryRepository;
import com.luheresbar.daily.persistence.crud.IExpenseCategoryCrudRepository;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import com.luheresbar.daily.persistence.mapper.IExpenseCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        List<ExpenseCategoryEntity> expenseCategoryEntity =  this.expenseCategoryCrudRepository.findAllByUserIdOrderByCategoryName(userId);
        return categoryMapper.toExpenseCategories(expenseCategoryEntity);
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
}




