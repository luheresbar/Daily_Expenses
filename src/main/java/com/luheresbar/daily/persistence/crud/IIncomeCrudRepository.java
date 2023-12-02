package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import com.luheresbar.daily.persistence.entity.IncomeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IIncomeCrudRepository extends ListCrudRepository<IncomeEntity, Integer> {

    @Query(value = "SELECT i " +
            "FROM IncomeEntity i " +
            "WHERE userId = :userId AND accountName = :accountName " +
            "ORDER BY incomeDate DESC")
    List<IncomeEntity> getAccountIncomes(@Param("accountName") String accountName, @Param("userId") String userId);

}
