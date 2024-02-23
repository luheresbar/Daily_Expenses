package com.luheresbar.daily.persistence.crud;


import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IExpenseCategoryCrudRepository extends CrudRepository<ExpenseCategoryEntity, ExpenseCategoryPK> {


    List<ExpenseCategoryEntity> findAllByUserIdOrderByCategoryName(Integer userId);

    // Query JPQL
    @Query("SELECT c FROM ExpenseCategoryEntity c WHERE c.available = true AND c.userId = :userId ORDER BY c.categoryName")
    List<ExpenseCategoryEntity> findAllAvailableByUserIdOrderByCategoryName(@Param("userId") Integer userId);

    // Query JPQL
    @Query("SELECT c FROM ExpenseCategoryEntity c WHERE c.available = false AND c.userId = :userId ORDER BY c.categoryName")
    List<ExpenseCategoryEntity> findAllNoAvailableByUserIdOrderByCategoryName(@Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE ExpenseCategoryEntity c " +
            "SET c.categoryName = :newCategoryName " +
            "WHERE c.categoryName = :categoryName AND c.userId = :userId ")
    void updateExpenseCategory(@Param("categoryName") String categoryName, @Param("newCategoryName") String newCategoryName, @Param("userId") Integer userId);

}
