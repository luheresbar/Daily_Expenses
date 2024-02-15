package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.Transfer;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

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
            transaction.setId(expense.getExpenseId());
            transaction.setDescription(expense.getDescription());
            transaction.setDate(expense.getExpenseDate());
            transaction.setAmount(expense.getExpense());
            transaction.setSourceAccountName(expense.getAccountName());
            transaction.setCategory(expense.getCategoryName());
            transactionDetails.add(transaction);
        }
        return transactionDetails;
    }

    public List<TransactionDetail> incomeToTransactionDetail(List<Income> incomes) {

        List<TransactionDetail> transactionDetails = new ArrayList<>();

        // Convertir Income a TransactionDetail
        for (Income income : incomes) {
            TransactionDetail transaction = new TransactionDetail();
            transaction.setType("income");
            transaction.setId(income.getIncomeId());
            transaction.setDescription(income.getDescription());
            transaction.setDate(income.getIncomeDate());
            transaction.setAmount(income.getIncome());
            transaction.setSourceAccountName(income.getAccountName());
            transaction.setCategory(income.getCategoryName());
            transactionDetails.add(transaction);
        }
        return transactionDetails;
    }

    public List<TransactionDetail> transferToTransactionDetail(List<Transfer> transfers) {

        List<TransactionDetail> transactionDetails = new ArrayList<>();

        // Convertir Transfer a TransactionDetail
        for (Transfer transfer : transfers) {
            TransactionDetail transaction = new TransactionDetail();
            transaction.setType("transfer");
            transaction.setId(transfer.getTransferId());
            transaction.setDescription(transfer.getDescription());
            transaction.setDate(transfer.getTransferDate());
            transaction.setAmount(transfer.getTransferValue());
            transaction.setSourceAccountName(transfer.getSourceAccountName());
            transaction.setDestinationAccountName(transfer.getDestinationAccountName());
            transaction.setCategory(transfer.getType());
            transactionDetails.add(transaction);
        }
        return transactionDetails;
    }

    public Expense transactionDetailToExpense(TransactionDetail transactionDetail) {
        Expense expense = new Expense();
        expense.setExpenseId(transactionDetail.getId());
        expense.setExpense(transactionDetail.getAmount());
        expense.setAccountName(transactionDetail.getSourceAccountName());
        expense.setCategoryName(transactionDetail.getCategory());
        expense.setDescription(transactionDetail.getDescription());
        expense.setExpenseDate(transactionDetail.getDate());
        return expense;
    }

    public Income transactionDetailToIncome(TransactionDetail transactionDetail) {
        Income income = new Income();
        income.setIncomeId(transactionDetail.getId());
        income.setIncome(transactionDetail.getAmount());
        income.setAccountName(transactionDetail.getSourceAccountName());
        income.setCategoryName(transactionDetail.getCategory());
        income.setDescription(transactionDetail.getDescription());
        income.setIncomeDate(transactionDetail.getDate());
        return income;
    }

    public Transfer transactionDetailToTransfer(TransactionDetail transactionDetail) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(transactionDetail.getId());
        transfer.setTransferValue(transactionDetail.getAmount());
        transfer.setSourceAccountName(transactionDetail.getSourceAccountName());
        transfer.setDestinationAccountName(transactionDetail.getDestinationAccountName());
        transfer.setType(transactionDetail.getCategory());
        transfer.setDescription(transactionDetail.getDescription());
        transfer.setTransferDate(transactionDetail.getDate());
        return transfer;
    }

}
