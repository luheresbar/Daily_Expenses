package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.dto.UpdateAccountDto;
import com.luheresbar.daily.persistence.entity.AccountPK;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    List<Account> getEnabledAccountsByUser(Integer userId);
    
    List<Account> getDisabledAccountsByUser(Integer userId);

    Double availableMoney(Integer userId);

    boolean exists(AccountPK accountPK);

    Optional<Account> getById(String accountName, Integer userId);

    Account save(Account account);

    void delete(AccountPK accountPK);

    void updateNameAccount(String accountName, String newAccountName, Integer currentUser);

}
