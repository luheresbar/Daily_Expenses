package com.luheresbar.daily.persistence.crud;


import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.entity.CategoryEntity;
import com.luheresbar.daily.persistence.entity.CategoryPK;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ICategoryCrudRepository extends ListCrudRepository<CategoryEntity, CategoryPK> {

    List<CategoryEntity> findAllByUserIdOrderByCategoryName(String userId);

}
