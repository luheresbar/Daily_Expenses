package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Transaction;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {

    List<Transaction> getAll();

    Transaction save(Transaction transaction);

    boolean delete(int transactionId, Integer userId);

    List<Transaction> getUserTransactions(Integer userId);

    Optional<Transaction> getById(int transactionId);

}
