package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.service.ExpenseService;
import com.luheresbar.daily.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/expenses")
public class ExpenseController {

    private final JwtUtil jwtUtil;
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(JwtUtil jwtUtil, ExpenseService expenseService) {
        this.jwtUtil = jwtUtil;
        this.expenseService = expenseService;
    }


    @GetMapping
    public ResponseEntity<List<Expense>> gerUserExpenses(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        System.out.println("JWT: " + jwt);
        String userId = jwtUtil.getUsername(jwt.split(" ")[1].trim());
        return new ResponseEntity<>(expenseService.getUserExpenses(userId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Expense> save(@RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.save(expense), HttpStatus.CREATED);
    }

}
