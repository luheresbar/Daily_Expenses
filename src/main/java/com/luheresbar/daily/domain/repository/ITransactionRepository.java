package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Transaction;

import java.util.List;

public interface ITransactionRepository {

    List<Transaction> getAll();

}
