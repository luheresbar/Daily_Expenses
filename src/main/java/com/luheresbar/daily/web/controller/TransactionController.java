package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.Transfer;
import com.luheresbar.daily.domain.dto.ExpenseDto;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import com.luheresbar.daily.domain.service.ExpenseService;
import com.luheresbar.daily.domain.service.IncomeService;
import com.luheresbar.daily.domain.service.TransactionsService;
import com.luheresbar.daily.domain.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    final private ExpenseService expenseService;
    final private IncomeService incomeService;
    final private TransferService transferService;
    final private TransactionsService transactionsService;

    private Integer currentUser;

    public TransactionController(ExpenseService expenseService, IncomeService incomeService, TransferService transferService, TransactionsService transactionsService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.transferService = transferService;
        this.transactionsService = transactionsService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDetail>> getUserTransactions() {
        List<Expense> expenses = this.expenseService.getUserExpenses(this.currentUser);
        List<Income> incomes = this.incomeService.getUserIncomes(this.currentUser);
        List<Transfer> transfers = this.transferService.getUserTransfers(this.currentUser);

        List<TransactionDetail> transactionDetails = new ArrayList<>();

        // Convertir gastos a TransactionDetail
        for (Expense expense : expenses) {
            TransactionDetail transaction = new TransactionDetail();
            transaction.setType("expense");
            transaction.setDescription(expense.getDescription());
            transaction.setDate(expense.getExpenseDate());
            transaction.setAmount(expense.getExpense());
            transaction.setSourceAccountName(expense.getAccountName());
            transactionDetails.add(transaction);
        }

        // Convertir ingresos a TransactionDetail
        for (Income income : incomes) {
            TransactionDetail transaction = new TransactionDetail();
            transaction.setType("income");
            transaction.setDescription(income.getDescription());
            transaction.setDate(income.getIncomeDate());
            transaction.setAmount(income.getIncome());
            transaction.setSourceAccountName(income.getAccountName());
            transactionDetails.add(transaction);
        }

        // Convertir transferencias a TransactionDetail
        for (Transfer transfer : transfers) {
            TransactionDetail transaction = new TransactionDetail();
            transaction.setType("transfer");
            transaction.setDescription("Transferencia");
            transaction.setDate(transfer.getTransferDate());
            transaction.setAmount(transfer.getTransferValue());
            transaction.setSourceAccountName(transfer.getSourceAccountName());
            transaction.setDestinationAccountName(transfer.getDestinationAccountName());
            transactionDetails.add(transaction);
        }

        List<TransactionDetail> transactionDetailsSort = this.transactionsService.sortTransactionsByDateDescending(transactionDetails);

        return new ResponseEntity<>(transactionDetailsSort, HttpStatus.OK);
    }

}


