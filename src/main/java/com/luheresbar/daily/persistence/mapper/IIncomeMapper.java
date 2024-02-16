package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.persistence.entity.IncomeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IAccountMapper.class})
public interface IIncomeMapper {

    Income toIncome(IncomeEntity incomeEntity);

    List<Income> toIncomes(List<IncomeEntity> incomeEntities);

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "categoryIncome", ignore = true)
    IncomeEntity toIncomeEntity(Income income);

}
