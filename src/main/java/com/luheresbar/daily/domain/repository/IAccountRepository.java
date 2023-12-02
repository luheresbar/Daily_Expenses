package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.dto.UpdateAccountIdDto;
import com.luheresbar.daily.persistence.entity.AccountPK;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    List<Account> getAccountsByUser(String userId);

    Double availableMoney(String userId);

    boolean exists(AccountPK accountPK);

    Optional<Account> getById(String accountName, String userId);

    Account save(Account account);

    boolean delete(AccountPK accountPK);

//    Optional<Account> updateAccountName(UpdateAccountIdDto updateAccountIdDto);
}
