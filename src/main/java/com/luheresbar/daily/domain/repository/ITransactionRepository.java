package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Transaction;

import java.util.List;

public interface ITransactionRepository {

    List<Transaction> getAll();

    Transaction save(Transaction transaction);

    boolean delete(int transactionId, String userId);

    List<Transaction> getUserTransactions(String userId);

}
