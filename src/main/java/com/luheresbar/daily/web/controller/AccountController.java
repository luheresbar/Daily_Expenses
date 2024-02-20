package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.dto.SummaryAccountsDto;
import com.luheresbar.daily.domain.dto.UpdateAccountDto;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private Integer currentUser;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @GetMapping
    public ResponseEntity<SummaryAccountsDto> viewAccountsUser() {
        List<Account> enabledAccounts = this.accountService.getEnabledAccountsByUser(this.currentUser);
        List<Account> disabledAccounts = this.accountService.getDisabledAccountsByUser(this.currentUser);
        Double availableMoney = this.accountService.availableMoney(this.currentUser);
        return ResponseEntity.ok(new SummaryAccountsDto(enabledAccounts, disabledAccounts, availableMoney));
    }

    @GetMapping("/available-money")
    public ResponseEntity<Double> availableMoney() {
        return ResponseEntity.ok(this.accountService.availableMoney(this.currentUser));
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody UpdateAccountDto updateAccount) {
        updateAccount.setUserId(currentUser);

        if (!this.accountService.exists(new AccountPK(updateAccount.getAccountName(), updateAccount.getUserId()))) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(updateAccount.getAccountName(), updateAccount.getNewAccountName())) {
            this.accountService.updateNameAccount(updateAccount.getAccountName(), updateAccount.getNewAccountName(), this.currentUser);
        }
        Optional<Account> accountInDb = this.accountService.getById(updateAccount.getAccountName(), this.currentUser);
        Account account = new Account();
        account.setAccountName(updateAccount.getNewAccountName());
        account.setUserId(updateAccount.getUserId());
        account.setAvailableMoney(updateAccount.getAvailableMoney());
        account.setAvailable(updateAccount.getAvailable());

        if (updateAccount.getAvailableMoney() == null && accountInDb.get().getAvailable()) {
            account.setAvailableMoney(accountInDb.get().getAvailableMoney());
        }
        if (updateAccount.getAvailable() == null) {
            account.setAvailable(accountInDb.get().getAvailable());
        }

        if (accountInDb.equals(account)) {
            return ResponseEntity.ok(account);
        }

        return ResponseEntity.ok(this.accountService.save(account));
    }

    @PostMapping("/create")
    public ResponseEntity<Account> add(@RequestBody Account account) {
        account.setUserId(this.currentUser);
        if (account.getAvailableMoney() == null) {
            account.setAvailableMoney(0.0);
        }
        account.setAvailable(true);
        AccountPK accountPK = new AccountPK(account.getAccountName(), account.getUserId());
        if (!this.accountService.exists(accountPK)) {
            return ResponseEntity.ok(this.accountService.save(account));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@RequestBody AccountPK accountPK) {
        accountPK.setUserId(this.currentUser);
        if (this.accountService.exists(accountPK)) {
            this.accountService.delete(accountPK);
            return ResponseEntity.ok().build();
        }
        ;
        return ResponseEntity.notFound().build();
    }


}
