package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Category;
import com.luheresbar.daily.domain.repository.ICategoryRepository;
import com.luheresbar.daily.persistence.crud.ICategoryCrudRepository;
import com.luheresbar.daily.persistence.entity.CategoryEntity;
import com.luheresbar.daily.persistence.mapper.ICategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository implements ICategoryRepository {

    private final ICategoryCrudRepository categoryCrudRepository;
    private final ICategoryMapper categoryMapper;

    @Autowired
    public CategoryRepository(ICategoryCrudRepository categoryCrudRepository, ICategoryMapper categoryMapper) {
        this.categoryCrudRepository = categoryCrudRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getAll() {
        List<CategoryEntity> categories = categoryCrudRepository.findAll();
        return categoryMapper.toCategories(categories);
    }
}




