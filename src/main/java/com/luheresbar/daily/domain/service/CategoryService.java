package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Category;
import com.luheresbar.daily.domain.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getByUser(String userId) {
        return this.categoryRepository.getByUser(userId);
    }
}
