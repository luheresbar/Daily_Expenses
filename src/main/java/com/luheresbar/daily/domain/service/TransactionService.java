package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.Transaction;
import com.luheresbar.daily.domain.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final ITransactionRepository transactionRepository;

    @Autowired
    public TransactionService(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAll() {
        return transactionRepository.getAll();
    }

    public Optional<Transaction> getById(int transactionId) {
        return this.transactionRepository.getById(transactionId);
    }

    public Transaction save(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    public boolean delete(int transactionId, String userId) {
        return this.transactionRepository.delete(transactionId, userId);
    }

    public List<Transaction> getUserTransactions(String userId) {
        return this.transactionRepository.getUserTransactions(userId);
    }
}
