package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface IExpenseCrudRepository extends ListCrudRepository<ExpenseEntity, Integer> {

}
