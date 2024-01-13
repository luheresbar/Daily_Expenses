package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.ExpenseService;
import com.luheresbar.daily.domain.service.UserService;
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
    private final UserService userService;
    Integer userToken;

    @Autowired
    public ExpenseController(JwtUtil jwtUtil, ExpenseService expenseService, AccountService accountService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.expenseService = expenseService;
        this.accountService = accountService;
        this.userService = userService;
    }

    // Filtro para extraer el usuario del token y almacenarlo en la variable de instancia
    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String emailToken = (String) authentication.getPrincipal();
        Optional<User> userDb = this.userService.findUserByEmail(emailToken);
        this.userToken = userDb.get().getUserId();
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getUserExpenses() {
        return  ResponseEntity.ok(expenseService.getUserExpenses(userToken));
    }
    @GetMapping("/{account}")
    public ResponseEntity<List<Expense>> getAccountExpenses(@PathVariable String account) {
        AccountPK accountPK = new AccountPK(account, this.userToken);
        if(this.accountService.exists(accountPK)) {
            return ResponseEntity.ok(this.expenseService.getAccountExpenses(account, this.userToken));
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/add")
    public ResponseEntity<Expense> add(@RequestBody Expense expense) {
        expense.setUserId(this.userToken);
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
        expense.setUserId(this.userToken);
        Optional<Expense> expenseDb = this.expenseService.getById(expense.getExpenseId());

        if(expenseDb.get().getUserId().equals(this.userToken)) {

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
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable int expenseId) {
        if(this.expenseService.delete(expenseId, this.userToken)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
