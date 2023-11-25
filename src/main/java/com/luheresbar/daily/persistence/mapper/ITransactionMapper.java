package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Transaction;
import com.luheresbar.daily.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IAccountMapper.class})
public interface ITransactionMapper {

    Transaction toTransaction(TransactionEntity transactionEntity);

    List<Transaction> toTransactions(List<TransactionEntity> transactionEntities);

    TransactionEntity toTransactionEntity(Transaction transaction);

}
