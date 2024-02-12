package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.domain.repository.IIncomeCategoryRepository;
import com.luheresbar.daily.persistence.crud.IIncomeCategoryCrudRepository;
import com.luheresbar.daily.persistence.entity.IncomeCategoryEntity;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import com.luheresbar.daily.persistence.mapper.IIncomeCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
