package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.repository.IAccountRepository;
import com.luheresbar.daily.persistence.crud.IAccountCrudRepository;
import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.entity.AccountPK;
import com.luheresbar.daily.persistence.mapper.IAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public boolean exists(Account account) {
        AccountEntity accountEntity = this.accountMapper.toAccountEntity(account);
        AccountPK accountPK = new AccountPK(accountEntity.getAccountName(), accountEntity.getUserId());
        return this.accountCrudRepository.existsById(accountPK);
    }

    @Override
    public Optional<Account> getById(AccountPK accountPK) {
        Optional<AccountEntity> accountEntity1 = this.accountCrudRepository.findById(accountPK);
        return accountEntity1.map(acc -> this.accountMapper.toAccount(acc));
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = this.accountMapper.toAccountEntity(account);
        return this.accountMapper.toAccount(this.accountCrudRepository.save(accountEntity));
    }
}
