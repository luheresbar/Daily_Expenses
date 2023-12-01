package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.repository.IAccountRepository;
import com.luheresbar.daily.persistence.crud.IAccountCrudRepository;
import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.mapper.IAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class AccountEntityRepository implements IAccountRepository {

    private final IAccountCrudRepository accountCrudRepository;
    private final IAccountMapper accountMapper;

    @Autowired
    public AccountEntityRepository(IAccountCrudRepository accountCrudRepository, IAccountMapper accountMapper) {
        this.accountCrudRepository = accountCrudRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<Account> getAccountsByUser(String userId) {
        List<AccountEntity> accounts = (List<AccountEntity>) this.accountCrudRepository.findAllByUserIdOrderByAccountName(userId);
        return accountMapper.toAccounts(accounts);
    }

    @Override
    public Double availableMoney(String userId) {
        return this.accountCrudRepository.availableMoney(userId);
    }
}
