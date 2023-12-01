package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
