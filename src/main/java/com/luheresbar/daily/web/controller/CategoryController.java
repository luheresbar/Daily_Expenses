package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.service.ExpenseCategoryService;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ExpenseCategoryService expenseCategoryService;
    private Integer currentUser;

    @ModelAttribute
    private void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @Autowired
    public CategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategory>> getAll() {
        return new ResponseEntity<>(expenseCategoryService.getByUser(this.currentUser), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseCategory> add(@RequestBody ExpenseCategory expenseCategory) {
        expenseCategory.setUserId(this.currentUser);
        ExpenseCategoryPK expenseCategoryPK = new ExpenseCategoryPK(expenseCategory.getCategoryName(), expenseCategory.getUserId());

        if (!this.expenseCategoryService.exists(expenseCategoryPK)) {
            return ResponseEntity.ok(this.expenseCategoryService.save(expenseCategory));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody ExpenseCategoryPK categoryPK) {
        categoryPK.setUserId(this.currentUser);
        if (this.expenseCategoryService.exists(categoryPK)) {
            if (!categoryPK.getCategoryName().equals("Others")) {
                this.expenseCategoryService.delete(categoryPK);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

}
