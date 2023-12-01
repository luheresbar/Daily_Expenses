package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.persistence.entity.AccountPK;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    List<Account> getAccountsByUser(String userId);

    Double availableMoney(String userId);

    boolean exists(Account account);

    Optional<Account> getById(AccountPK accountPK);

    Account save(Account account);
}
