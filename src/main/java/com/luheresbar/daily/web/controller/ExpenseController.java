package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.ExpenseService;
import com.luheresbar.daily.persistence.entity.AccountPK;
import com.luheresbar.daily.web.config.JwtUtil;
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

    private final JwtUtil jwtUtil;
    private final ExpenseService expenseService;
    private final AccountService accountService;
    private String currentUser;

    @Autowired
    public ExpenseController(JwtUtil jwtUtil, ExpenseService expenseService, AccountService accountService) {
        this.jwtUtil = jwtUtil;
        this.expenseService = expenseService;
        this.accountService = accountService;
    }

    // Filtro para extraer el usuario del token y almacenarlo en la variable de instancia
    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        this.currentUser = (String) authentication.getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getUserExpenses() {
        return  ResponseEntity.ok(expenseService.getUserExpenses(currentUser));
    }
    @GetMapping("/{account}")
    public ResponseEntity<List<Expense>> getAccountExpenses(@PathVariable String account) {
        AccountPK accountPK = new AccountPK(account, this.currentUser);
        if(this.accountService.exists(accountPK)) {
            return ResponseEntity.ok(this.expenseService.getAccountExpenses(account, this.currentUser));
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/add")
    public ResponseEntity<Expense> add(@RequestBody Expense expense) {
        expense.setUserId(this.currentUser);
        if(expense.getCategoryName() == null) {
            expense.setCategoryName("Others");
        }
        if(expense.getExpenseDate() == null) {
            expense.setExpenseDate(String.valueOf(LocalDateTime.now()));
        }
        return new ResponseEntity<>(expenseService.save(expense), HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<Expense> update(@RequestBody Expense expense) {
        Optional<Expense> expenseDb = this.expenseService.getById(expense.getExpenseId());
        expense.setUserId(this.currentUser);

        if(expense.getExpense() == null) {
            expense.setExpense(expenseDb.get().getExpense());
        }
        if(expense.getDescription() == null) {
            expense.setDescription(expenseDb.get().getDescription());
        }
        if(expense.getExpenseDate() == null) {
            expense.setExpenseDate(expenseDb.get().getExpenseDate());
        }
        if(expense.getAccountName() == null) {
            expense.setAccountName(expenseDb.get().getAccountName());
        }
        if(expense.getCategoryName() == null) {
            expense.setCategoryName(expenseDb.get().getCategoryName());
        }

        return ResponseEntity.ok(this.expenseService.save(expense));

    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable int expenseId) {
        if(this.expenseService.delete(expenseId, this.currentUser)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
