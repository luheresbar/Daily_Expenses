package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IExpenseCrudRepository extends ListCrudRepository<ExpenseEntity, Integer> {

    @Query(value = "SELECT * FROM expenses WHERE user_id = :id", nativeQuery = true)
    List<ExpenseEntity> findUserExpenses(@Param("id") String userId);

}
