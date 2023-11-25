package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.persistence.entity.IncomeEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IAccountMapper.class})
public interface IIncomeMapper {

    Income toIncome(IncomeEntity incomeEntity);

    List<Income> toIncomes(List<IncomeEntity> incomeEntities);

    IncomeEntity toIncomeEntity(Income income);

}
