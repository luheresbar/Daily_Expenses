package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.persistence.entity.AccountPK;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    List<Account> getAccountsByUser(String userId);

    Double availableMoney(String userId);

    boolean exists(AccountPK accountPK);

    Optional<Account> getById(String accountName, String userId);

    Account save(Account account);

    void delete(AccountPK accountPK);

//    Optional<Account> updateAccountName(UpdateAccountIdDto updateAccountIdDto);
}
