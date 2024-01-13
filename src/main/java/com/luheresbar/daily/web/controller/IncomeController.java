package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.service.AccountService;
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
    private final AccountService accountService;
    private Integer currentUser;

    @Autowired
    public IncomeController(IncomeService incomeService, AccountService accountService) {
        this.incomeService = incomeService;
        this.accountService = accountService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        this.currentUser = (Integer) authentication.getPrincipal();
    }


    @GetMapping
    public ResponseEntity<List<Income>> getUserIncomes() {
        return  ResponseEntity.ok(incomeService.getUserIncomes(currentUser));
    }
    @GetMapping("/{account}")
    public ResponseEntity<List<Income>> getAccountIncomes(@PathVariable String account) {
        AccountPK accountPK = new AccountPK(account, this.currentUser);
        if(this.accountService.exists(accountPK)) {
            return ResponseEntity.ok(this.incomeService.getAccountIncomes(account, this.currentUser));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Income> add(@RequestBody Income income) {
        income.setUserId(this.currentUser);

        if(income.getIncomeDate() == null) {
            income.setIncomeDate(String.valueOf(LocalDateTime.now()));
        }
        return new ResponseEntity<>(incomeService.save(income), HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<Income> update(@RequestBody Income income) {
        income.setUserId(this.currentUser);
        Optional<Income> incomeDb = this.incomeService.getById(income.getIncomeId());
        if(incomeDb.get().getUserId().equals(this.currentUser)) {

            if(income.getIncome() == null) {
                income.setIncome(incomeDb.get().getIncome());
            }
            if(income.getDescription() == null) {
                income.setDescription(incomeDb.get().getDescription());
            }
            if(income.getIncomeDate() == null) {
                income.setIncomeDate(incomeDb.get().getIncomeDate());
            }
            if(income.getAccountName() == null) {
                income.setAccountName(incomeDb.get().getAccountName());
            }
            return ResponseEntity.ok(this.incomeService.save(income));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{incomeId}")
    public ResponseEntity<Void> delete(@PathVariable int incomeId) {
        if(this.incomeService.delete(incomeId, this.currentUser)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
