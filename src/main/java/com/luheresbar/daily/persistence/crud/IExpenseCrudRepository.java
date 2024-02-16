package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IExpenseCrudRepository extends ListCrudRepository<ExpenseEntity, Integer> {

    List<ExpenseEntity> findAllByUserIdOrderByExpenseDate(Integer userId);

    @Query(value = "SELECT e " +
            "FROM ExpenseEntity e " +
            "WHERE userId = :userId AND accountName = :accountName " +
            "ORDER BY expenseDate DESC")
    List<ExpenseEntity> getAccountExpenses(@Param("accountName") String accountName, @Param("userId") Integer userId);
}
