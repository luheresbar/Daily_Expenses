package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.ExpenseCategory;
import com.luheresbar.daily.domain.dto.CategoryDto;
import com.luheresbar.daily.domain.dto.UpdateCategoryDto;
import com.luheresbar.daily.domain.service.ExpenseCategoryService;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense-categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;
    private Integer currentUser;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
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
        List<ExpenseCategory> expenseCategories = this.expenseCategoryService.getByUser(this.currentUser);
        List<CategoryDto> categoryDtos = this.expenseCategoryService.expenseCategoriesToDto(expenseCategories);
        System.out.println("Holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + this.currentUser + expenseCategories); //TODO(Eliminar)
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> add(@RequestBody ExpenseCategory expenseCategory) {
        expenseCategory.setUserId(this.currentUser);
        expenseCategory.setAvailable(true);
        ExpenseCategoryPK expenseCategoryPK = new ExpenseCategoryPK(expenseCategory.getCategoryName(), expenseCategory.getUserId());

        if (!this.expenseCategoryService.exists(expenseCategoryPK)) {
            ExpenseCategory newExpenseCategory = this.expenseCategoryService.save(expenseCategory);
            List<ExpenseCategory> categoryList = Collections.singletonList(newExpenseCategory);
            List<CategoryDto> categoryDto = this.expenseCategoryService.expenseCategoriesToDto(categoryList);
            return ResponseEntity.ok(categoryDto.get(0));
        }
        return ResponseEntity.badRequest().build();
    }


    @PutMapping("/update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto) {
        updateCategoryDto.setUserId(currentUser);
        ExpenseCategoryPK expenseCategoryPK = new ExpenseCategoryPK(updateCategoryDto.getCategoryName(), updateCategoryDto.getUserId());

        if (!this.expenseCategoryService.exists(expenseCategoryPK)) {
            System.out.println("No encontradoooooooooooooooo" + expenseCategoryPK); //TODO(Eliminar)
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(updateCategoryDto.getCategoryName(), updateCategoryDto.getNewCategoryName())) {
            this.expenseCategoryService.updateNameCategory(updateCategoryDto.getCategoryName(), updateCategoryDto.getNewCategoryName(), this.currentUser);
        }
        Optional<ExpenseCategory> categoryInDb = this.expenseCategoryService.getById(updateCategoryDto.getCategoryName(), this.currentUser);
        ExpenseCategory category = new ExpenseCategory();
        category.setUserId(categoryInDb.get().getUserId());
        category.setCategoryName(categoryInDb.get().getCategoryName());
        List<ExpenseCategory> categoryList = Collections.singletonList(category);
        List<CategoryDto> categoryDto = this.expenseCategoryService.expenseCategoriesToDto(categoryList);
        return ResponseEntity.ok(categoryDto.get(0));
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
