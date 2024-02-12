package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.IncomeCategoryEntity;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IIncomeCategoryCrudRepository extends CrudRepository<IncomeCategoryEntity, IncomeCategoryPK> {
    List<IncomeCategoryEntity> findAllByUserIdOrderByCategoryName(Integer userId);

}
