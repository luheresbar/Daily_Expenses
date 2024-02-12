package com.luheresbar.daily.persistence.crud;


import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.CategoryPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICategoryCrudRepository extends CrudRepository<ExpenseCategoryEntity, CategoryPK> {

    List<ExpenseCategoryEntity> findAllByUserIdOrderByCategoryName(Integer userId);

}
