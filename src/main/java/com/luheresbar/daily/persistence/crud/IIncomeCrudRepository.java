package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.IncomeEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface IIncomeCrudRepository extends ListCrudRepository<IncomeEntity, Integer> {


}
