package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.dto.UpdateAccountIdDto;
import com.luheresbar.daily.domain.repository.IAccountRepository;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public boolean exists(AccountPK accountPK) {
        return this.accountRepository.exists(accountPK);
    }

    public Optional<Account> getById(String accountName, String userId) {
        return this.accountRepository.getById(accountName, userId);
    }
    public Account save(Account account) {
        return this.accountRepository.save(account);
    }

    public boolean delete(AccountPK accountPK) {
        return this.accountRepository.delete(accountPK);
    }

//    public Optional<Account> updateAccountName(UpdateAccountIdDto updateAccountIdDto) {
//        return this.accountRepository.updateAccountName(updateAccountIdDto);
//    }
}
