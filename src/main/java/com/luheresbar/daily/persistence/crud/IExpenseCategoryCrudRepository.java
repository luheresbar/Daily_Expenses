package com.luheresbar.daily.persistence.crud;


import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IExpenseCategoryCrudRepository extends CrudRepository<ExpenseCategoryEntity, ExpenseCategoryPK> {


    List<ExpenseCategoryEntity> findAllByUserIdOrderByCategoryName(Integer userId);

}
