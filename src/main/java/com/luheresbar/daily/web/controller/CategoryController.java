package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Category;
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
@RequestMapping("/api/user/categories")
public class CategoryController {

    private final CategoryService categoryService;
    String currentUser;

    @ModelAttribute
    private void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        this.currentUser = (String) authentication.getPrincipal();
    }

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return new ResponseEntity<>(categoryService.getByUser(currentUser), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        category.setUserId(this.currentUser);
        CategoryPK categoryPK = new CategoryPK(category.getCategoryName(), category.getUserId());

        if(!this.categoryService.exists(categoryPK)) {
            return ResponseEntity.ok(this.categoryService.save(category));
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
