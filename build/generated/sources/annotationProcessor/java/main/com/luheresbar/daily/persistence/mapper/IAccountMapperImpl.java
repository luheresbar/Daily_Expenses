package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.persistence.entity.AccountEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-20T11:49:02-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class IAccountMapperImpl implements IAccountMapper {

    @Override
    public Account toAccount(AccountEntity accountEntity) {
        if ( accountEntity == null ) {
            return null;
        }

        Account account = new Account();

        account.setAccountName( accountEntity.getAccountName() );
        account.setUserId( accountEntity.getUserId() );
        account.setAvailableMoney( accountEntity.getAvailableMoney() );
        account.setAvailable( accountEntity.getAvailable() );

        return account;
    }

    @Override
    public List<Account> toAccounts(List<AccountEntity> accountEntities) {
        if ( accountEntities == null ) {
            return null;
        }

        List<Account> list = new ArrayList<Account>( accountEntities.size() );
        for ( AccountEntity accountEntity : accountEntities ) {
            list.add( toAccount( accountEntity ) );
        }

        return list;
    }

    @Override
    public AccountEntity toAccountEntity(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setAccountName( account.getAccountName() );
        accountEntity.setUserId( account.getUserId() );
        accountEntity.setAvailableMoney( account.getAvailableMoney() );
        accountEntity.setAvailable( account.getAvailable() );

        return accountEntity;
    }
}
