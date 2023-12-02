package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.service.IncomeService;
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
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private String currentUser;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        this.currentUser = (String) authentication.getPrincipal();
    }


    @GetMapping
    public ResponseEntity<List<Income>> getUserIncome() {
        return  ResponseEntity.ok(incomeService.getUserIncome(currentUser));
    }
//    @GetMapping("/{account}")
//    public ResponseEntity<List<Income>> getAccountIncomes(@PathVariable String account) {
//        AccountPK accountPK = new AccountPK(account, this.currentUser);
//        if(this.accountService.exists(accountPK)) {
//            return ResponseEntity.ok(this.incomeService.getAccountIncomes(account, this.currentUser));
//        }
//        return ResponseEntity.notFound().build();
//
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<Income> add(@RequestBody Income expense) {
//        expense.setUserId(this.currentUser);
//        if(expense.getCategoryName() == null) {
//            expense.setCategoryName("Others");
//        }
//        if(expense.getIncomeDate() == null) {
//            expense.setIncomeDate(String.valueOf(LocalDateTime.now()));
//        }
//        return new ResponseEntity<>(incomeService.save(expense), HttpStatus.CREATED);
//    }
//
//    @PatchMapping("/update")
//    public ResponseEntity<Income> update(@RequestBody Income expense) {
//        Optional<Income> expenseDb = this.incomeService.getById(expense.getIncomeId());
//        expense.setUserId(this.currentUser);
//
//        if(expense.getIncome() == null) {
//            expense.setIncome(expenseDb.get().getIncome());
//        }
//        if(expense.getDescription() == null) {
//            expense.setDescription(expenseDb.get().getDescription());
//        }
//        if(expense.getIncomeDate() == null) {
//            expense.setIncomeDate(expenseDb.get().getIncomeDate());
//        }
//        if(expense.getAccountName() == null) {
//            expense.setAccountName(expenseDb.get().getAccountName());
//        }
//        if(expense.getCategoryName() == null) {
//            expense.setCategoryName(expenseDb.get().getCategoryName());
//        }
//
//        return ResponseEntity.ok(this.incomeService.save(expense));
//
//    }
//
//    @DeleteMapping("/delete/{expenseId}")
//    public ResponseEntity<Void> delete(@PathVariable int expenseId) {
//        if(this.incomeService.delete(expenseId, this.currentUser)) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }


}
