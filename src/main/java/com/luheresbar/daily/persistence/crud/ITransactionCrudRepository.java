package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.TransactionEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ITransactionCrudRepository extends ListCrudRepository<TransactionEntity, Integer> {

}
