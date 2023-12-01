package com.luheresbar.daily.persistence.mapper;


import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IUserMapper.class})
public interface IAccountMapper {

    Account toAccount(AccountEntity accountEntity);

    List<Account> toAccounts(List<AccountEntity> accountEntities);

    @Mappings({
            @Mapping(target = "incomes", ignore = true),
            @Mapping(target = "expenses", ignore = true),
            @Mapping(target = "destinationAccounts", ignore = true),
            @Mapping(target = "sourceAccounts", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    AccountEntity toAccountEntity(Account account);

}
