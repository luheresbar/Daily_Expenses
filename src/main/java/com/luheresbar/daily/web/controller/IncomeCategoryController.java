package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.domain.dto.CategoryDto;
import com.luheresbar.daily.domain.service.IncomeCategoryService;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<IncomeCategory> expenseCategories = this.incomeCategoryService.getByUser(this.currentUser);
        List<CategoryDto> categoryDtos = this.incomeCategoryService.expenseCategoriesToDto(expenseCategories);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> add(@RequestBody IncomeCategory expenseCategory) {
        expenseCategory.setUserId(this.currentUser);
        IncomeCategoryPK expenseCategoryPK = new IncomeCategoryPK(expenseCategory.getCategoryName(), expenseCategory.getUserId());

        if (!this.incomeCategoryService.exists(expenseCategoryPK)) {
            IncomeCategory newIncomeCategory = this.incomeCategoryService.save(expenseCategory);
            List<IncomeCategory> categoryList = Collections.singletonList(newIncomeCategory);
            List<CategoryDto> categoryDto = this.incomeCategoryService.expenseCategoriesToDto(categoryList);
            return ResponseEntity.ok(categoryDto.get(0));
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
