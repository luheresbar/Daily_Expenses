package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.dto.UpdateAccountDto;
import com.luheresbar.daily.domain.repository.IAccountRepository;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getEnabledAccountsByUser(Integer userId) {
        return this.accountRepository.getEnabledAccountsByUser(userId);
    }
    public List<Account> getDisabledAccountsByUser(Integer userId) {
        return this.accountRepository.getDisabledAccountsByUser(userId);
    }

    public Double availableMoney(Integer userId) {
        return this.accountRepository.availableMoney(userId);
    }

    public boolean exists(AccountPK accountPK) {
        return this.accountRepository.exists(accountPK);
    }

    public Optional<Account> getById(String accountName, Integer userId) {
        return this.accountRepository.getById(accountName, userId);
    }
    public Account save(Account account) {
        return this.accountRepository.save(account);
    }

    public void delete(AccountPK accountPK) {
        this.accountRepository.delete(accountPK);
    }

    public void updateNameAccount(String accountName, String newAccountName, Integer currentUser) {
        this.accountRepository.updateNameAccount(accountName, newAccountName, currentUser);
    }
}
