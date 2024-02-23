package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.IncomeCategoryEntity;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IIncomeCategoryCrudRepository extends CrudRepository<IncomeCategoryEntity, IncomeCategoryPK> {
    List<IncomeCategoryEntity> findAllByUserIdOrderByCategoryName(Integer userId);

    @Query("SELECT c FROM IncomeCategoryEntity c WHERE c.available = true AND c.userId = :userId ORDER BY c.categoryName")
    List<IncomeCategoryEntity> findAllAvailableByUserIdOrderByCategoryName(@Param("userId") Integer userId);

    // Query JPQL
    @Query("SELECT c FROM IncomeCategoryEntity c WHERE c.available = false AND c.userId = :userId ORDER BY c.categoryName")
    List<IncomeCategoryEntity> findAllNoAvailableByUserIdOrderByCategoryName(@Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE IncomeCategoryEntity c " +
            "SET c.categoryName = :newCategoryName " +
            "WHERE c.categoryName = :categoryName AND c.userId = :userId ")
    void updateIncomeCategory(@Param("categoryName") String categoryName, @Param("newCategoryName") String newCategoryName, @Param("userId") Integer userId);

}
