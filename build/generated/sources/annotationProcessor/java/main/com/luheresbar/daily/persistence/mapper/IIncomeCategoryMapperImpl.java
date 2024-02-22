package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.persistence.entity.IncomeCategoryEntity;
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
public class IIncomeCategoryMapperImpl implements IIncomeCategoryMapper {

    @Override
    public IncomeCategory toIncomeCategory(IncomeCategoryEntity incomeCategoryEntity) {
        if ( incomeCategoryEntity == null ) {
            return null;
        }

        IncomeCategory incomeCategory = new IncomeCategory();

        incomeCategory.setCategoryName( incomeCategoryEntity.getCategoryName() );
        incomeCategory.setUserId( incomeCategoryEntity.getUserId() );

        return incomeCategory;
    }

    @Override
    public List<IncomeCategory> toIncomeCategories(List<IncomeCategoryEntity> incomeCategoryEntities) {
        if ( incomeCategoryEntities == null ) {
            return null;
        }

        List<IncomeCategory> list = new ArrayList<IncomeCategory>( incomeCategoryEntities.size() );
        for ( IncomeCategoryEntity incomeCategoryEntity : incomeCategoryEntities ) {
            list.add( toIncomeCategory( incomeCategoryEntity ) );
        }

        return list;
    }

    @Override
    public IncomeCategoryEntity toIncomeCategoryEntity(IncomeCategory incomeCategory) {
        if ( incomeCategory == null ) {
            return null;
        }

        IncomeCategoryEntity incomeCategoryEntity = new IncomeCategoryEntity();

        incomeCategoryEntity.setCategoryName( incomeCategory.getCategoryName() );
        incomeCategoryEntity.setUserId( incomeCategory.getUserId() );

        return incomeCategoryEntity;
    }
}
