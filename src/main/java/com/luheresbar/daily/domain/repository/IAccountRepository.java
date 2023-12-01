package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Account;

import java.util.List;

public interface IAccountRepository {

    List<Account> getAccountsByUser(String userId);

    Double availableMoney(String userId);
}
