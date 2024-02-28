package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.IncomeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IIncomeCrudRepository extends ListCrudRepository<IncomeEntity, Integer> {

    List<IncomeEntity> findAllByUserIdOrderByIncomeDate(Integer userId);

    @Query(value = "SELECT i " +
            "FROM IncomeEntity i " +
            "WHERE userId = :userId AND accountName = :accountName " +
            "ORDER BY incomeDate DESC")
    List<IncomeEntity> getAccountIncomes(@Param("accountName") String accountName, @Param("userId") Integer userId);

    @Query(value = "SELECT i FROM IncomeEntity i " +
            "WHERE i.userId = :userId " +
            "AND i.incomeDate BETWEEN :startDate AND :endDate")
    List<IncomeEntity> findByDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") Integer userId);

    @Query(value = "SELECT SUM(i.income) " +
            "FROM IncomeEntity i " +
            "WHERE i.userId = :userId " +
            "AND i.incomeDate BETWEEN :startDate AND :endDate")
    Double getMonthlyIncomeTotal(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") Integer userId);

}
