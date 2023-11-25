package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/account/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {
        return new ResponseEntity<>(expenseService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Expense> save(@RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.save(expense), HttpStatus.CREATED);
    }

}
