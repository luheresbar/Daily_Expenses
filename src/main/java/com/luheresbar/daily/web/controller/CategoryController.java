package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.service.CategoryService;
import com.luheresbar.daily.persistence.entity.CategoryPK;
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

    private final CategoryService categoryService;
    private Integer currentUser;

    @ModelAttribute
    private void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategory>> getAll() {
        return new ResponseEntity<>(categoryService.getByUser(this.currentUser), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseCategory> add(@RequestBody ExpenseCategory expenseCategory) {
        expenseCategory.setUserId(this.currentUser);
        CategoryPK categoryPK = new CategoryPK(expenseCategory.getCategoryName(), expenseCategory.getUserId());

        if (!this.categoryService.exists(categoryPK)) {
            return ResponseEntity.ok(this.categoryService.save(expenseCategory));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody CategoryPK categoryPK) {
        categoryPK.setUserId(this.currentUser);
        if (this.categoryService.exists(categoryPK)) {
            if (!categoryPK.getCategoryName().equals("Others")) {
                this.categoryService.delete(categoryPK);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

}
