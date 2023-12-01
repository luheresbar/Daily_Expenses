package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private String currentUser;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        this.currentUser = (String) authentication.getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<Account>> viewAccountsUser() {
        return ResponseEntity.ok(this.accountService.getAccountsByUser(this.currentUser));
    }

    @GetMapping("/money")
    public ResponseEntity<Double> availableMoney() {
        return ResponseEntity.ok(this.accountService.availableMoney(currentUser));
    }

    @PostMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        account.setUserId(currentUser);
        if(!this.accountService.exists(account)) {
            return ResponseEntity.badRequest().build();
        }
        AccountPK accountPK = new AccountPK(account.getAccountName(), account.getUserId());
        Optional<Account> accountInDb = this.accountService.getById(accountPK);

        if (account.getAccountName() == null) {
            account.setUserId(accountInDb.get().getAccountName());
        }

        if (account.getAvailableMoney() == null) {
            account.setAvailableMoney(accountInDb.get().getAvailableMoney());
        }
        if (account.getAvailable() == null) {
            account.setAvailable(accountInDb.get().getAvailable());
        }

        return ResponseEntity.ok(this.accountService.save(account));
    }

}
