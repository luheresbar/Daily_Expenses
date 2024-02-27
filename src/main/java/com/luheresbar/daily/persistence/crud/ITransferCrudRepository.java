package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.IncomeEntity;
import com.luheresbar.daily.persistence.entity.TransferEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransferCrudRepository extends ListCrudRepository<TransferEntity, Integer> {
    List<TransferEntity> findAllByUserIdOrderByTransferDate(Integer userId);

    @Query(value = "SELECT t FROM TransferEntity t " +
            "WHERE t.userId = :userId " +
            "AND t.transferDate BETWEEN :startDate AND :endDate")
    List<TransferEntity> findByDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") Integer userId);

}
