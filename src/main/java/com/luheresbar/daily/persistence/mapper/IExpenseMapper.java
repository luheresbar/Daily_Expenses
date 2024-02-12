package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IExpenseCategoryMapper.class, IAccountMapper.class})
public interface IExpenseMapper {

    Expense toExpense(ExpenseEntity expenseEntity);

    List<Expense> toExpenses(List<ExpenseEntity> expenseEntities);

    @Mappings({
            @Mapping(target = "account", ignore = true),
            @Mapping(target = "categoryExpense", ignore = true),
            @Mapping(target = "expenseDate", source = "expenseDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss") // Example date format, adjust as needed
    })
    ExpenseEntity toExpenseEntity(Expense expense);

}
