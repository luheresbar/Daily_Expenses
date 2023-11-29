package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ICategoryMapper.class, IAccountMapper.class})
public interface IExpenseMapper {

    Expense toExpense(ExpenseEntity expenseEntity);

    List<Expense> toExpenses(List<ExpenseEntity> expenseEntities);

    @Mappings({
        @Mapping(target = "account", ignore = true),
        @Mapping(target = "category", ignore = true)
    })
    ExpenseEntity toExpenseEntity(Expense expense);

}
