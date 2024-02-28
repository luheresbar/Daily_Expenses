package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IExpenseCrudRepository extends ListCrudRepository<ExpenseEntity, Integer> {

    List<ExpenseEntity> findAllByUserIdOrderByExpenseDate(Integer userId);

    @Query(value = "SELECT e " +
            "FROM ExpenseEntity e " +
            "WHERE userId = :userId AND accountName = :accountName " +
            "ORDER BY expenseDate DESC")
    List<ExpenseEntity> getAccountExpenses(@Param("accountName") String accountName, @Param("userId") Integer userId);

    @Query(value = "SELECT e FROM ExpenseEntity e " +
            "WHERE e.userId = :userId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate")
    List<ExpenseEntity> findByDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") Integer userId);

    @Query(value = "SELECT SUM(e.expense) " +
            "FROM ExpenseEntity e " +
            "WHERE e.userId = :userId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate")
    Double getMonthlyExpenseTotal(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") Integer userId);
}
