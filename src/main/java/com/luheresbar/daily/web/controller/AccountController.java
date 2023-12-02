package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.dto.UpdateAccountIdDto;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.persistence.entity.AccountPK;
import lombok.experimental.PackagePrivate;
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

    @PatchMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        account.setUserId(currentUser);
        if(!this.accountService.exists(new AccountPK(account.getAccountName(), account.getUserId()))) {
            return ResponseEntity.notFound().build();
        }
        Optional<Account> accountInDb = this.accountService.getById(account.getAccountName(), currentUser);

        if (account.getAvailableMoney() == null && accountInDb.get().getAvailable()) {
            account.setAvailableMoney(accountInDb.get().getAvailableMoney());
        }
        if (account.getAvailable() == null) {
            account.setAvailable(accountInDb.get().getAvailable());
        }

        return ResponseEntity.ok(this.accountService.save(account));
    }

    @PostMapping("/add")
    public ResponseEntity<Account> add(@RequestBody Account account) {
        account.setUserId(this.currentUser);
        if(account.getAvailableMoney() == null) {
        account.setAvailableMoney(0.0);
        }
        if(account.getAvailable() == null) {
        account.setAvailable(true);
        }
        AccountPK accountPK = new AccountPK(account.getAccountName(), account.getUserId());
        if(!this.accountService.exists(accountPK)) {
            return ResponseEntity.ok(this.accountService.save(account));
        }
        return ResponseEntity.badRequest().build();
    }

    // Actualizar el AccountName de una cuenta.
//    @PatchMapping("/update/accountname")
//    public ResponseEntity<Account> updateAccountName(@RequestBody UpdateAccountIdDto updateAccountIdDto) {
//        updateAccountIdDto.setUserId(this.currentUser);
//        AccountPK accountPK = new AccountPK(updateAccountIdDto.getCurrentAccountName(), updateAccountIdDto.getUserId());
//        if (this.accountService.exists(accountPK)) {
//            this.accountService.updateAccountName(updateAccountIdDto);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@RequestBody AccountPK accountPK) {
        accountPK.setUserId(currentUser);
        if(this.accountService.delete(accountPK)) {
            return ResponseEntity.ok().build();
        };
        return ResponseEntity.notFound().build();
    }


}
