package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.Transfer;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import com.luheresbar.daily.domain.service.ExpenseService;
import com.luheresbar.daily.domain.service.IncomeService;
import com.luheresbar.daily.domain.service.TransactionService;
import com.luheresbar.daily.domain.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    final private ExpenseService expenseService;
    final private IncomeService incomeService;
    final private TransferService transferService;
    final private TransactionService transactionsService;

    private Integer currentUser;

    public TransactionController(ExpenseService expenseService, IncomeService incomeService, TransferService transferService, TransactionService transactionsService) {
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
    public ResponseEntity<List<TransactionDetail>> getUserTransactions(@RequestParam(required = false) String current_date, @RequestParam(required = false) String next_date) {
//        System.out.println("aquiiiiiiiiiiiiiiiiiiii" + current_date); //TODO (Delete)
//        System.out.println("aquiiiiiiiiiiiiiiiiiiii" + next_date);
        List<Expense> expenses = new ArrayList<>();
        List<Income> incomes = new ArrayList<>();
        List<Transfer> transfers = new ArrayList<>();

        if (current_date != null && next_date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime startDate = LocalDateTime.parse(current_date, formatter);
            LocalDateTime endDate = LocalDateTime.parse(next_date, formatter);

            expenses = this.expenseService.findByDateBetween( startDate, endDate, this.currentUser);
            incomes = this.incomeService.findByDateBetween( startDate, endDate, this.currentUser);
            transfers = this.transferService.findByDateBetween( startDate, endDate, this.currentUser);
        } else {
            expenses = this.expenseService.getUserExpenses(this.currentUser);
            incomes = this.incomeService.getUserIncomes(this.currentUser);
            transfers = this.transferService.getUserTransfers(this.currentUser);
        }
        List<TransactionDetail> transactionDetails = new ArrayList<>();

        List<TransactionDetail> expensesTransaction = this.transactionsService.expenseToTransactionDetail(expenses);
        List<TransactionDetail> incomesTransaction = this.transactionsService.incomeToTransactionDetail(incomes);
        List<TransactionDetail> transfersTransaction = this.transactionsService.transferToTransactionDetail(transfers);

        transactionDetails.addAll(expensesTransaction);
        transactionDetails.addAll(incomesTransaction);
        transactionDetails.addAll(transfersTransaction);


        List<TransactionDetail> transactionDetailsSorted = this.transactionsService.sortTransactionsByDateDescending(transactionDetails);

        return new ResponseEntity<>(transactionDetailsSorted, HttpStatus.OK);
    }

}


