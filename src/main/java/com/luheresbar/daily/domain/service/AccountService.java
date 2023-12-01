package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;

    @Autowired
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccountsByUser(String userId) {
        return this.accountRepository.getAccountsByUser(userId);
    }

    public Double availableMoney(String userId) {
        return this.accountRepository.availableMoney(userId);
    }
}
