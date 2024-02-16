package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-15T22:09:02-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class IExpenseMapperImpl implements IExpenseMapper {

    @Override
    public Expense toExpense(ExpenseEntity expenseEntity) {
        if ( expenseEntity == null ) {
            return null;
        }

        Expense expense = new Expense();

        if ( expenseEntity.getExpenseId() != null ) {
            expense.setExpenseId( expenseEntity.getExpenseId() );
        }
        expense.setExpense( expenseEntity.getExpense() );
        expense.setDescription( expenseEntity.getDescription() );
        if ( expenseEntity.getExpenseDate() != null ) {
            expense.setExpenseDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( expenseEntity.getExpenseDate() ) );
        }
        expense.setUserId( expenseEntity.getUserId() );
        expense.setAccountName( expenseEntity.getAccountName() );
        expense.setCategoryName( expenseEntity.getCategoryName() );

        return expense;
    }

    @Override
    public List<Expense> toExpenses(List<ExpenseEntity> expenseEntities) {
        if ( expenseEntities == null ) {
            return null;
        }

        List<Expense> list = new ArrayList<Expense>( expenseEntities.size() );
        for ( ExpenseEntity expenseEntity : expenseEntities ) {
            list.add( toExpense( expenseEntity ) );
        }

        return list;
    }

    @Override
    public ExpenseEntity toExpenseEntity(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseEntity expenseEntity = new ExpenseEntity();

        if ( expense.getExpenseDate() != null ) {
            expenseEntity.setExpenseDate( LocalDateTime.parse( expense.getExpenseDate() ) );
        }
        expenseEntity.setExpenseId( expense.getExpenseId() );
        expenseEntity.setExpense( expense.getExpense() );
        expenseEntity.setDescription( expense.getDescription() );
        expenseEntity.setUserId( expense.getUserId() );
        expenseEntity.setAccountName( expense.getAccountName() );
        expenseEntity.setCategoryName( expense.getCategoryName() );

        return expenseEntity;
    }
}
