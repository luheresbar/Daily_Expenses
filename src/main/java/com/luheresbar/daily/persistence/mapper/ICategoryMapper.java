package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IUserMapper.class})
public interface ICategoryMapper {

    ExpenseCategory toCategory(ExpenseCategoryEntity expenseCategoryEntity);

    List<ExpenseCategory> toCategories(List<ExpenseCategoryEntity> categoryEntities);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    ExpenseCategoryEntity toCategoryEntity(ExpenseCategory expenseCategory);

}
