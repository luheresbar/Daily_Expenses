package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.service.ExpenseService;
import com.luheresbar.daily.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/expenses")
public class ExpenseController {

    private final JwtUtil jwtUtil;
    private final ExpenseService expenseService;
    private String currentUser;

    @Autowired
    public ExpenseController(JwtUtil jwtUtil, ExpenseService expenseService) {
        this.jwtUtil = jwtUtil;
        this.expenseService = expenseService;
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

    @PostMapping("/save")
    public ResponseEntity<Expense> save(@RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.save(expense), HttpStatus.CREATED);
    }

}
