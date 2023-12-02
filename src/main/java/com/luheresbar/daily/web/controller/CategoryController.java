package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Category;
import com.luheresbar.daily.domain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
