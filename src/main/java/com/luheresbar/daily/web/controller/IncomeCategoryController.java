package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.domain.service.IncomeCategoryService;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income-categories")
public class IncomeCategoryController {


    private final IncomeCategoryService incomeCategoryService;
    private Integer currentUser;

    public IncomeCategoryController(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }

    @ModelAttribute
    private void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @GetMapping
    public ResponseEntity<List<IncomeCategory>> getAll() {
        return new ResponseEntity<>(incomeCategoryService.getByUser(this.currentUser), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<IncomeCategory> add(@RequestBody IncomeCategory expenseCategory) {
        expenseCategory.setUserId(this.currentUser);
        IncomeCategoryPK expenseCategoryPK = new IncomeCategoryPK(expenseCategory.getCategoryName(), expenseCategory.getUserId());

        if (!this.incomeCategoryService.exists(expenseCategoryPK)) {
            return ResponseEntity.ok(this.incomeCategoryService.save(expenseCategory));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody IncomeCategoryPK categoryPK) {
        categoryPK.setUserId(this.currentUser);
        if (this.incomeCategoryService.exists(categoryPK)) {
            if (!categoryPK.getCategoryName().equals("Others")) {
                this.incomeCategoryService.delete(categoryPK);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }


}
