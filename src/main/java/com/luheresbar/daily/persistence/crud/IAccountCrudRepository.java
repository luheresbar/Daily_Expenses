package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.data.repository.ListCrudRepository;

public interface IAccountCrudRepository extends ListCrudRepository<AccountEntity, AccountPK> {
}
