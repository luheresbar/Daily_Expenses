package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;

import java.util.List;

public interface IExpenseCategoryRepository {

    List<ExpenseCategory> getByUser(Integer userId);

    boolean exists(ExpenseCategoryPK expenseCategoryPK);

    ExpenseCategory save(ExpenseCategory expenseCategory);

    void delete(ExpenseCategoryPK expenseCategoryPK);
}
