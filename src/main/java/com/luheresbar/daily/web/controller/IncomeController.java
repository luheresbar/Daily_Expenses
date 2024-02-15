package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import com.luheresbar.daily.domain.dto.TransactionDto;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.IncomeService;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private Integer currentUser;

    @Autowired
    public IncomeController(IncomeService incomeService, AccountService accountService, TransactionService transactionsService) {
        this.incomeService = incomeService;
        this.accountService = accountService;
        this.transactionService = transactionsService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @GetMapping
    public ResponseEntity<TransactionDto> getUserIncomes(@RequestParam(required = false) String account_name) {
        if (account_name != null) {
            AccountPK accountPK = new AccountPK(account_name, this.currentUser);
            if (this.accountService.exists(accountPK)) {
                List<Income> incomes = this.incomeService.getAccountIncomes(account_name, this.currentUser);
                List<TransactionDetail> transactionDetails = this.transactionService.incomeToTransactionDetail(incomes);
                List<TransactionDetail> transactionDetailsSort = this.transactionService.sortTransactionsByDateDescending(transactionDetails);
                Double totalIncome = this.incomeService.getTotalIncome(incomes);
                return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, totalIncome));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Income> incomes = this.incomeService.getUserIncomes(this.currentUser);
            List<TransactionDetail> transactionDetails = this.transactionService.incomeToTransactionDetail(incomes);
            List<TransactionDetail> transactionDetailsSort = this.transactionService.sortTransactionsByDateDescending(transactionDetails);
            Double totalIncome = this.incomeService.getTotalIncome(incomes);
            return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, totalIncome));
        }
    }

    @GetMapping("/{account}")
    public ResponseEntity<List<Income>> getAccountIncomes(@PathVariable String account) {
        AccountPK accountPK = new AccountPK(account, this.currentUser);
        if (this.accountService.exists(accountPK)) {
            return ResponseEntity.ok(this.incomeService.getAccountIncomes(account, this.currentUser));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<TransactionDetail> add(@RequestBody TransactionDetail transactionDetailIncome) {
        Income income = this.transactionService.transactionDetailToIncome(transactionDetailIncome);
        income.setUserId(this.currentUser);

        if (income.getIncomeDate() == null) {
            income.setIncomeDate(String.valueOf(LocalDateTime.now()));
        }

        Income savedIncome = this.incomeService.save(income);
        List<Income> incomeList = Collections.singletonList(savedIncome);
        List<TransactionDetail> transactionDetails = this.transactionService.incomeToTransactionDetail(incomeList);
        return new ResponseEntity<>(transactionDetails.get(0), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TransactionDetail> update(@RequestBody TransactionDetail transactionDetailIncome) {
        Income income = this.transactionService.transactionDetailToIncome(transactionDetailIncome);

        income.setUserId(this.currentUser);
        Optional<Income> incomeDb = this.incomeService.getById(income.getIncomeId());
        if (incomeDb.get().getUserId().equals(this.currentUser)) {

            if (income.getIncome() == null) {
                income.setIncome(incomeDb.get().getIncome());
            }
            if (income.getDescription() == null) {
                income.setDescription(incomeDb.get().getDescription());
            }
            if (income.getIncomeDate() == null) {
                income.setIncomeDate(incomeDb.get().getIncomeDate());
            }
            if (income.getAccountName() == null) {
                income.setAccountName(incomeDb.get().getAccountName());
            }
            if (income.getCategoryName() == null) {
                income.setCategoryName(incomeDb.get().getCategoryName());
            }
            Income savedIncome = this.incomeService.save(income);
            List<Income> incomeList = Collections.singletonList(savedIncome);
            List<TransactionDetail> transactionDetails = this.transactionService.incomeToTransactionDetail(incomeList);
            return ResponseEntity.ok(transactionDetails.get(0));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{incomeId}")
    public ResponseEntity<Void> delete(@PathVariable int incomeId) {
        if (this.incomeService.delete(incomeId, this.currentUser)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
