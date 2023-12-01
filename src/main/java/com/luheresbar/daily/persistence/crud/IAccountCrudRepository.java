package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAccountCrudRepository extends CrudRepository<AccountEntity, AccountPK> {
    List<AccountEntity> findAllByUserIdOrderByAccountName(String userId);

    @Query(value = "SELECT SUM(available_money) FROM accounts WHERE user_id = :userId", nativeQuery = true)
    Double availableMoney(@Param("userId") String userId);
}
