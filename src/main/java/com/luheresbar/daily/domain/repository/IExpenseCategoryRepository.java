package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;

import java.util.List;
import java.util.Optional;

public interface IExpenseCategoryRepository {

    List<ExpenseCategory> getByUser(Integer userId);

    boolean exists(ExpenseCategoryPK expenseCategoryPK);

    ExpenseCategory save(ExpenseCategory expenseCategory);

    void delete(ExpenseCategoryPK expenseCategoryPK);

    void updateNameCategory(String categoryName, String newCategoryName, Integer userId);

    Optional<ExpenseCategory> getById(String categoryName, Integer userId);

    List<ExpenseCategory> getEnabledCategoriesByUser(Integer userId);

    List<ExpenseCategory> getDisabledCategoriesByUser(Integer userId);
}
