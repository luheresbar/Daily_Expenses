package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.domain.repository.IIncomeCategoryRepository;
import com.luheresbar.daily.persistence.crud.IIncomeCategoryCrudRepository;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import com.luheresbar.daily.persistence.entity.IncomeCategoryEntity;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import com.luheresbar.daily.persistence.mapper.IIncomeCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IncomeCategoryRepository implements IIncomeCategoryRepository {

    private final IIncomeCategoryCrudRepository incomeCategoryCrudRepository;
    private final IIncomeCategoryMapper incomeCategoryMapper;

    public IncomeCategoryRepository(IIncomeCategoryCrudRepository incomeCategoryCrudRepository, IIncomeCategoryMapper incomeCategoryMapper) {
        this.incomeCategoryCrudRepository = incomeCategoryCrudRepository;
        this.incomeCategoryMapper = incomeCategoryMapper;
    }

    @Override
    public List<IncomeCategory> getByUser(Integer userId) {
        List<IncomeCategoryEntity> expenseCategoryEntity =  this.incomeCategoryCrudRepository.findAllByUserIdOrderByCategoryName(userId);
        return incomeCategoryMapper.toIncomeCategories(expenseCategoryEntity);
    }

    @Override
    public List<IncomeCategory> getEnabledCategoriesByUser(Integer userId) {
        List<IncomeCategoryEntity> accounts = this.incomeCategoryCrudRepository.findAllAvailableByUserIdOrderByCategoryName(userId);
        return incomeCategoryMapper.toIncomeCategories(accounts);
    }

    @Override
    public List<IncomeCategory> getDisabledCategoriesByUser(Integer userId) {
        List<IncomeCategoryEntity> accounts = this.incomeCategoryCrudRepository.findAllNoAvailableByUserIdOrderByCategoryName(userId);
        return incomeCategoryMapper.toIncomeCategories(accounts);
    }

    @Override
    public Optional<IncomeCategory> getById(String categoryName, Integer userId) {
        IncomeCategoryPK categoryPK = new IncomeCategoryPK(categoryName, userId);
        Optional<IncomeCategoryEntity> incomeCategory = this.incomeCategoryCrudRepository.findById(categoryPK);
        return incomeCategory.map(acc -> this.incomeCategoryMapper.toIncomeCategory(acc));
    }

    @Override
    public boolean exists(IncomeCategoryPK expenseCategoryPK) {
        return this.incomeCategoryCrudRepository.existsById(expenseCategoryPK);
    }

    @Override
    public IncomeCategory save(IncomeCategory expenseCategory) {
        IncomeCategoryEntity expenseCategoryEntity = this.incomeCategoryMapper.toIncomeCategoryEntity(expenseCategory);
        return incomeCategoryMapper.toIncomeCategory(this.incomeCategoryCrudRepository.save(expenseCategoryEntity));
    }

    @Override
    public void delete(IncomeCategoryPK expenseCategoryPK) {
        this.incomeCategoryCrudRepository.deleteById(expenseCategoryPK);
    }

    @Override
    public void updateNameCategory(String categoryName, String newCategoryName, Integer userId) {
        this.incomeCategoryCrudRepository.updateIncomeCategory(categoryName, newCategoryName, userId);
    }

}
