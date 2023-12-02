package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.dto.UpdateAccountIdDto;
import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAccountCrudRepository extends CrudRepository<AccountEntity, AccountPK> {
    List<AccountEntity> findAllByUserIdOrderByAccountName(String userId);

    @Query(value = "SELECT SUM(available_money) FROM accounts WHERE user_id = :userId", nativeQuery = true)
    Double availableMoney(@Param("userId") String userId);

//    @Query(value =  "UPDATE accounts " +
//                    "SET account_name = :#{#newName.newAccountName} " +
//                    "WHERE user_id = :#{#newName.userId} AND account_name = :#{#newName.currentAccountName}", nativeQuery = true)
//    @Modifying
//    void updateAccountName(@Param("newName") UpdateAccountIdDto updateAccountIdDto);
}
