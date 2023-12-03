package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Transaction;
import com.luheresbar.daily.domain.repository.ITransactionRepository;
import com.luheresbar.daily.persistence.crud.ITransactionCrudRepository;
import com.luheresbar.daily.persistence.entity.IncomeEntity;
import com.luheresbar.daily.persistence.entity.TransactionEntity;
import com.luheresbar.daily.persistence.mapper.ITransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionEntityRepository implements ITransactionRepository {

    private final ITransactionCrudRepository transactionCrudRepository;
    private final ITransactionMapper transactionMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionEntityRepository(ITransactionCrudRepository transactionCrudRepository, ITransactionMapper transactionMapper, JdbcTemplate jdbcTemplate) {
        this.transactionCrudRepository = transactionCrudRepository;
        this.transactionMapper = transactionMapper;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transaction> getAll() {
        List<TransactionEntity> transactions = transactionCrudRepository.findAll();
        return transactionMapper.toTransactions(transactions);
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = this.transactionMapper.toTransactionEntity(transaction);
        return this.transactionMapper.toTransaction(this.transactionCrudRepository.save(transactionEntity));
    }

    @Override
    public boolean delete(int transactionId, String userId) {
        TransactionEntity transactionEntity = this.transactionCrudRepository.findById(transactionId).orElse(null);
        if(transactionEntity != null && transactionEntity.getUserId().equals(userId)) {
            this.transactionCrudRepository.delete(transactionEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<Transaction> getUserTransactions(String userId) {
        // Metodo usando JdbcTemplate
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
        List<TransactionEntity> transactionEntities = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(TransactionEntity.class));
        return this.transactionMapper.toTransactions(transactionEntities);
    }
}
