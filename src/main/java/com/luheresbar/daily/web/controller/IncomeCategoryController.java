package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.domain.dto.CategoryDto;
import com.luheresbar.daily.domain.dto.UpdateCategoryDto;
import com.luheresbar.daily.domain.service.IncomeCategoryService;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryPK;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        List<IncomeCategory> incomeCategories = this.incomeCategoryService.getByUser(this.currentUser);

        List<CategoryDto> categoryDtos = this.incomeCategoryService.incomeCategoriesToDto(incomeCategories);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> add(@RequestBody IncomeCategory incomeCategory) {
        incomeCategory.setUserId(this.currentUser);
        IncomeCategoryPK incomeCategoryPK = new IncomeCategoryPK(incomeCategory.getCategoryName(), incomeCategory.getUserId());

        if (!this.incomeCategoryService.exists(incomeCategoryPK)) {
            IncomeCategory newIncomeCategory = this.incomeCategoryService.save(incomeCategory);
            List<IncomeCategory> categoryList = Collections.singletonList(newIncomeCategory);
            List<CategoryDto> categoryDto = this.incomeCategoryService.incomeCategoriesToDto(categoryList);
            return ResponseEntity.ok(categoryDto.get(0));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto) {
        updateCategoryDto.setUserId(currentUser);
        IncomeCategoryPK incomeCategoryPK = new IncomeCategoryPK(updateCategoryDto.getCategoryName(), updateCategoryDto.getUserId());

        if (!this.incomeCategoryService.exists(incomeCategoryPK)) {
            return ResponseEntity.notFound().build();
        }

        if (!Objects.equals(updateCategoryDto.getCategoryName(), updateCategoryDto.getNewCategoryName())) {
            this.incomeCategoryService.updateNameCategory(updateCategoryDto.getCategoryName(), updateCategoryDto.getNewCategoryName(), this.currentUser);
        }

        Optional<IncomeCategory> optionalCategoryInDb = this.incomeCategoryService.getById(updateCategoryDto.getNewCategoryName(), this.currentUser);

        // Verificar si el Optional contiene un valor antes de extraerlo y asignar valores predeterminados
        IncomeCategory category = optionalCategoryInDb.orElse(new IncomeCategory());
        category.setUserId(updateCategoryDto.getUserId());
        category.setCategoryName(updateCategoryDto.getNewCategoryName());
        category.setAvailable(updateCategoryDto.getAvailable() != null ? updateCategoryDto.getAvailable() : category.getAvailable());

        if (optionalCategoryInDb.isPresent()) {
            IncomeCategory categoryInDb = optionalCategoryInDb.get();

            if (categoryInDb.equals(category)) {
                List<IncomeCategory> categoryList = Collections.singletonList(category);
                List<CategoryDto> categoryDto = this.incomeCategoryService.incomeCategoriesToDto(categoryList);
                return ResponseEntity.ok(categoryDto.get(0));
            }
        }

        // Guardar la categoría actualizada o nueva
        IncomeCategory updatedCategory = this.incomeCategoryService.save(category);
        List<IncomeCategory> categoryList = Collections.singletonList(updatedCategory);
        List<CategoryDto> categoryDto = this.incomeCategoryService.incomeCategoriesToDto(categoryList);
        return ResponseEntity.ok(categoryDto.get(0));
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
