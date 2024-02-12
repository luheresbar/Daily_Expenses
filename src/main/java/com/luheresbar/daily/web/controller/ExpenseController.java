package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.dto.TransactionDto;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.ExpenseService;
import com.luheresbar.daily.domain.service.TransactionService;
import com.luheresbar.daily.persistence.entity.AccountPK;
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
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final AccountService accountService;
    private final TransactionService transactionsService;
    private Integer currentUser;

    @Autowired
    public ExpenseController(ExpenseService expenseService, AccountService accountService, TransactionService transactionsService) {
        this.expenseService = expenseService;
        this.accountService = accountService;
        this.transactionsService = transactionsService;
    }

    // Filtro para extraer el usuario del token y almacenarlo en la variable de instancia
    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @GetMapping
    public ResponseEntity<TransactionDto> getUserExpenses(@RequestParam(required = false) String account_name) {
        if (account_name != null) {
            AccountPK accountPK = new AccountPK(account_name, this.currentUser);
            if (this.accountService.exists(accountPK)) {
                List<Expense> expenses = this.expenseService.getAccountExpenses(account_name, this.currentUser);
                List<TransactionDetail> transactionDetails = this.transactionsService.expenseToTransactionDetail(expenses);
                List<TransactionDetail> transactionDetailsSort = this.transactionsService.sortTransactionsByDateDescending(transactionDetails);
                Double totalExpense = this.expenseService.getTotalExpense(expenses);
                return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, totalExpense));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Expense> expenses = this.expenseService.getUserExpenses(this.currentUser);
            List<TransactionDetail> transactionDetails = this.transactionsService.expenseToTransactionDetail(expenses);
            List<TransactionDetail> transactionDetailsSort = this.transactionsService.sortTransactionsByDateDescending(transactionDetails);
            Double totalExpense = this.expenseService.getTotalExpense(expenses);
            return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, totalExpense));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Expense> add(@RequestBody Expense expense) {
        expense.setUserId(this.currentUser);
        if (expense.getCategoryName() == null) {
            expense.setCategoryName("Others");
        }
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(String.valueOf(LocalDateTime.now()));
        }
        System.out.println("Cracion de expense " + expense.toString());
        return new ResponseEntity<>(expenseService.save(expense), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Expense> update(@RequestBody Expense expense) {
        expense.setUserId(this.currentUser);
        Optional<Expense> expenseDb = this.expenseService.getById(expense.getExpenseId());

        if (expenseDb.get().getUserId().equals(this.currentUser)) {

            if (expense.getExpense() == null) {
                expense.setExpense(expenseDb.get().getExpense());
            }
            if (expense.getDescription() == null) {
                expense.setDescription(expenseDb.get().getDescription());
            }
            if (expense.getExpenseDate() == null) {
                expense.setExpenseDate(expenseDb.get().getExpenseDate());
            }
            if (expense.getAccountName() == null) {
                expense.setAccountName(expenseDb.get().getAccountName());
            }
            if (expense.getCategoryName() == null) {
                expense.setCategoryName(expenseDb.get().getCategoryName());
            }

            return ResponseEntity.ok(this.expenseService.save(expense));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable int expenseId) {
        if (this.expenseService.delete(expenseId, this.currentUser)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
