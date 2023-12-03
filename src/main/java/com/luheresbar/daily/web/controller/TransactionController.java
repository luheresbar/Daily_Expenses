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

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private String currentUser;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        this.currentUser = (String) authentication.getPrincipal();
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
            if(transaction.getDestinationAccountName().equals("Cash")) {
                transaction.setType("Withdrawal");
            }
            if(transaction.getSourceAccountName().equals("Cash")) {
                transaction.setType("Deposit");
            }
            if(!transaction.getDestinationAccountName().equals("Cash") && !transaction.getSourceAccountName().equals("Cash")) {
                transaction.setType("Transfer");
            }
            return ResponseEntity.ok(this.transactionService.save(transaction));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<Void> delete(@PathVariable int transactionId) {
        if(this.transactionService.delete(transactionId, this.currentUser)) {
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
