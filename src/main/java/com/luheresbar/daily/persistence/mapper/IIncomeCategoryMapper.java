package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.persistence.entity.IncomeCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IUserMapper.class})
public interface IIncomeCategoryMapper {

    IncomeCategory toIncomeCategory(IncomeCategoryEntity incomeCategoryEntity);

    List<IncomeCategory> toIncomeCategories(List<IncomeCategoryEntity> incomeCategoryEntities);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "incomes", ignore = true)
    IncomeCategoryEntity toIncomeCategoryEntity(IncomeCategory incomeCategory);
}
