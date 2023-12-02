package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.dto.UpdateAccountIdDto;
import com.luheresbar.daily.domain.repository.IAccountRepository;
import com.luheresbar.daily.persistence.crud.IAccountCrudRepository;
import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.entity.AccountPK;
import com.luheresbar.daily.persistence.mapper.IAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public boolean exists(AccountPK accountPK) {
        return this.accountCrudRepository.existsById(accountPK);
    }

    @Override
    public Optional<Account> getById(String accountName, String userId) {
        AccountPK accountPK = new AccountPK(accountName, userId );
        Optional<AccountEntity> accountEntity = this.accountCrudRepository.findById(accountPK);
        return accountEntity.map(acc -> this.accountMapper.toAccount(acc));
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = this.accountMapper.toAccountEntity(account);
        return this.accountMapper.toAccount(this.accountCrudRepository.save(accountEntity));
    }

    @Override
    public void delete(AccountPK accountPK) {
        this.accountCrudRepository.deleteById(accountPK);
    }

//    @Override
//    @Transactional
//    public Optional<Account> updateAccountName(UpdateAccountIdDto updateAccountIdDto) {
//        this.accountCrudRepository.updateAccountName(updateAccountIdDto);
//        Optional<Account> account = this.getById(updateAccountIdDto.getNewAccountName(), updateAccountIdDto.getUserId());
//        return account;
//    }


}
