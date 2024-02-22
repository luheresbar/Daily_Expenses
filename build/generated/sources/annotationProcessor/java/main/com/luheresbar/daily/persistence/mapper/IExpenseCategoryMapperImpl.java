package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-21T22:26:28-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class IExpenseCategoryMapperImpl implements IExpenseCategoryMapper {

    @Override
    public ExpenseCategory toExpenseCategory(ExpenseCategoryEntity expenseCategoryEntity) {
        if ( expenseCategoryEntity == null ) {
            return null;
        }

        ExpenseCategory expenseCategory = new ExpenseCategory();

        expenseCategory.setCategoryName( expenseCategoryEntity.getCategoryName() );
        expenseCategory.setUserId( expenseCategoryEntity.getUserId() );

        return expenseCategory;
    }

    @Override
    public List<ExpenseCategory> toExpenseCategories(List<ExpenseCategoryEntity> expenseCategoryEntities) {
        if ( expenseCategoryEntities == null ) {
            return null;
        }

        List<ExpenseCategory> list = new ArrayList<ExpenseCategory>( expenseCategoryEntities.size() );
        for ( ExpenseCategoryEntity expenseCategoryEntity : expenseCategoryEntities ) {
            list.add( toExpenseCategory( expenseCategoryEntity ) );
        }

        return list;
    }

    @Override
    public ExpenseCategoryEntity toExpenseCategoryEntity(ExpenseCategory expenseCategory) {
        if ( expenseCategory == null ) {
            return null;
        }

        ExpenseCategoryEntity expenseCategoryEntity = new ExpenseCategoryEntity();

        expenseCategoryEntity.setCategoryName( expenseCategory.getCategoryName() );
        expenseCategoryEntity.setUserId( expenseCategory.getUserId() );

        return expenseCategoryEntity;
    }
}
