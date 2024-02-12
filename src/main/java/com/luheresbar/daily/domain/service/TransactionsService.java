package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsService {

    public List<TransactionDetail> sortTransactionsByDateDescending(List<TransactionDetail> transactionDetails) {
        if (transactionDetails.size() <= 1) {
            return transactionDetails;
        }

        int middle = transactionDetails.size() / 2;
        List<TransactionDetail> leftList = transactionDetails.subList(0, middle);
        List<TransactionDetail> rightList = transactionDetails.subList(middle, transactionDetails.size());

        leftList = sortTransactionsByDateDescending(leftList);
        rightList = sortTransactionsByDateDescending(rightList);

        List<TransactionDetail> mergedList = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            TransactionDetail leftTransaction = leftList.get(leftIndex);
            TransactionDetail rightTransaction = rightList.get(rightIndex);

            // Comparar las fechas en orden descendente
            if (leftTransaction.getDate().compareTo(rightTransaction.getDate()) >= 0) {
                mergedList.add(leftTransaction);
                leftIndex++;
            } else {
                mergedList.add(rightTransaction);
                rightIndex++;
            }
        }

        // Agregar las transacciones restantes de leftList
        while (leftIndex < leftList.size()) {
            mergedList.add(leftList.get(leftIndex));
            leftIndex++;
        }

        // Agregar las transacciones restantes de rightList
        while (rightIndex < rightList.size()) {
            mergedList.add(rightList.get(rightIndex));
            rightIndex++;
        }

        return mergedList;
    }

    public List<TransactionDetail> expenseToTransactionDetail(List<Expense> expenses) {

        List<TransactionDetail> transactionDetails = new ArrayList<>();

        // Convertir Expense a TransactionDetail
        for (Expense expense : expenses) {
            TransactionDetail transaction = new TransactionDetail();
            transaction.setType("expense");
            transaction.setDescription(expense.getDescription());
            transaction.setDate(expense.getExpenseDate());
            transaction.setAmount(expense.getExpense());
            transaction.setSourceAccountName(expense.getAccountName());
            transactionDetails.add(transaction);
        }
        return transactionDetails;
    }

}
