package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Transaction;
import com.luheresbar.daily.domain.repository.ITransactionRepository;
import com.luheresbar.daily.persistence.crud.ITransactionCrudRepository;
import com.luheresbar.daily.persistence.entity.TransactionEntity;
import com.luheresbar.daily.persistence.mapper.ITransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionEntityRepository implements ITransactionRepository {

    private final ITransactionCrudRepository transactionCrudRepository;
    private final ITransactionMapper transactionMapper;

    @Autowired
    public TransactionEntityRepository(ITransactionCrudRepository transactionCrudRepository, ITransactionMapper transactionMapper) {
        this.transactionCrudRepository = transactionCrudRepository;
        this.transactionMapper = transactionMapper;
    }


    @Override
    public List<Transaction> getAll() {
        List<TransactionEntity> transactions = transactionCrudRepository.findAll();
        return transactionMapper.toTransactions(transactions);
    }
}
