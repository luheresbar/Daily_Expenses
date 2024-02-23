package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;

import java.util.List;
import java.util.Optional;

public interface IIncomeCategoryRepository {

    List<IncomeCategory> getByUser(Integer userId);

    Optional<IncomeCategory> getById(String categoryName, Integer userId);

    boolean exists(IncomeCategoryPK incomeCategoryPK);

    IncomeCategory save(IncomeCategory incomeCategory);

    void delete(IncomeCategoryPK incomeCategoryPK);

    void updateNameCategory(String categoryName, String newCategoryName, Integer userId);

    List<IncomeCategory> getEnabledCategoriesByUser(Integer userId);

    List<IncomeCategory> getDisabledCategoriesByUser(Integer userId);
}
