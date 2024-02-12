package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.persistence.entity.CategoryPK;

import java.util.List;

public interface ICategoryRepository {

    List<ExpenseCategory> getByUser(Integer userId);

    boolean exists(CategoryPK categoryPK);

    ExpenseCategory save(ExpenseCategory expenseCategory);

    void delete(CategoryPK categoryPK);
}
