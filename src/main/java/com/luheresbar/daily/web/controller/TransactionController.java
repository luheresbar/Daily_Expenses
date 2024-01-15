package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Transaction;
import com.luheresbar.daily.domain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private Integer currentUser;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getUserTransactions() {
        return ResponseEntity.ok(this.transactionService.getUserTransactions(this.currentUser));
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> add(@RequestBody Transaction transaction) {
        transaction.setUserId(this.currentUser);
        if(!transaction.getSourceAccountName().equals(transaction.getDestinationAccountName())) {

            if(transaction.getTransactionDate() == null) {
                transaction.setTransactionDate(String.valueOf(LocalDateTime.now()));
            }
            extractedTypeTransaction(transaction);

            return ResponseEntity.ok(this.transactionService.save(transaction));
        }
        return ResponseEntity.badRequest().build();
    }

    private static void extractedTypeTransaction(Transaction transaction) {
        if(transaction.getDestinationAccountName().equals("Cash")) {
            transaction.setType("Withdrawal");
        }
        if(transaction.getSourceAccountName().equals("Cash")) {
            transaction.setType("Deposit");
        }
        if(!transaction.getDestinationAccountName().equals("Cash") && !transaction.getSourceAccountName().equals("Cash")) {
            transaction.setType("Transfer");
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Transaction> update(@RequestBody Transaction transaction) {
        transaction.setUserId(this.currentUser);
        Optional<Transaction> transactionDb = this.transactionService.getById(transaction.getTransactionId());

        if(transactionDb.get().getUserId().equals(this.currentUser)) {
            if(transaction.getTransactionValue() == null) {
                transaction.setTransactionValue(transactionDb.get().getTransactionValue());
            }
            if(transaction.getTransactionDate() == null) {
                transaction.setTransactionDate(transactionDb.get().getTransactionDate());
            }
            if(transaction.getSourceAccountName() == null) {
                transaction.setSourceAccountName(transactionDb.get().getSourceAccountName());
            }
            if(transaction.getDestinationAccountName() == null) {
                transaction.setDestinationAccountName(transactionDb.get().getDestinationAccountName());
            }
            extractedTypeTransaction(transaction);
            return ResponseEntity.ok(this.transactionService.save(transaction));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<Void> delete(@PathVariable int transactionId) {
        if(this.transactionService.delete(transactionId, this.currentUser)) {
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
