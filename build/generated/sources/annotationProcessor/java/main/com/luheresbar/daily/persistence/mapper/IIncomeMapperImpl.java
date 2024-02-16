package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.persistence.entity.IncomeEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-15T22:09:03-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class IIncomeMapperImpl implements IIncomeMapper {

    @Override
    public Income toIncome(IncomeEntity incomeEntity) {
        if ( incomeEntity == null ) {
            return null;
        }

        Income income = new Income();

        income.setIncomeId( incomeEntity.getIncomeId() );
        income.setIncome( incomeEntity.getIncome() );
        income.setDescription( incomeEntity.getDescription() );
        if ( incomeEntity.getIncomeDate() != null ) {
            income.setIncomeDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( incomeEntity.getIncomeDate() ) );
        }
        income.setUserId( incomeEntity.getUserId() );
        income.setAccountName( incomeEntity.getAccountName() );
        income.setCategoryName( incomeEntity.getCategoryName() );

        return income;
    }

    @Override
    public List<Income> toIncomes(List<IncomeEntity> incomeEntities) {
        if ( incomeEntities == null ) {
            return null;
        }

        List<Income> list = new ArrayList<Income>( incomeEntities.size() );
        for ( IncomeEntity incomeEntity : incomeEntities ) {
            list.add( toIncome( incomeEntity ) );
        }

        return list;
    }

    @Override
    public IncomeEntity toIncomeEntity(Income income) {
        if ( income == null ) {
            return null;
        }

        IncomeEntity incomeEntity = new IncomeEntity();

        incomeEntity.setIncomeId( income.getIncomeId() );
        incomeEntity.setIncome( income.getIncome() );
        incomeEntity.setDescription( income.getDescription() );
        if ( income.getIncomeDate() != null ) {
            incomeEntity.setIncomeDate( LocalDateTime.parse( income.getIncomeDate() ) );
        }
        incomeEntity.setUserId( income.getUserId() );
        incomeEntity.setAccountName( income.getAccountName() );
        incomeEntity.setCategoryName( income.getCategoryName() );

        return incomeEntity;
    }
}
