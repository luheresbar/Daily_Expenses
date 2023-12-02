package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.ExpenseEntity;
import com.luheresbar.daily.persistence.entity.IncomeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IIncomeCrudRepository extends ListCrudRepository<IncomeEntity, Integer> {

//    @Query(value = "SELECT e " +
//            "FROM ExpenseEntity e " +
//            "WHERE userId = :userId AND accountName = :accountName " +
//            "ORDER BY expenseDate DESC")
//    List<ExpenseEntity> getAccountExpenses(@Param("accountName") String accountName, @Param("userId") String userId);

}
