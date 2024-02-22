package com.luheresbar.daily.persistence.crud;


import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IExpenseCategoryCrudRepository extends CrudRepository<ExpenseCategoryEntity, ExpenseCategoryPK> {


    List<ExpenseCategoryEntity> findAllByUserIdOrderByCategoryName(Integer userId);

//    @Transactional
    @Modifying
    @Query("UPDATE ExpenseCategoryEntity c " +
            "SET c.categoryName = :newCategoryName " +
            "WHERE c.categoryName = :categoryName AND c.userId = :userId ")
    void updateExpenseCategory(@Param("categoryName") String categoryName, @Param("newCategoryName") String newCategoryName, @Param("userId") Integer userId);

}
