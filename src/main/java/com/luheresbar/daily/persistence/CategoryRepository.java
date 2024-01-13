package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.Category;
import com.luheresbar.daily.domain.repository.ICategoryRepository;
import com.luheresbar.daily.persistence.crud.ICategoryCrudRepository;
import com.luheresbar.daily.persistence.entity.CategoryEntity;
import com.luheresbar.daily.persistence.entity.CategoryPK;
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
    public List<Category> getByUser(Integer userId) {
        List<CategoryEntity> categoryEntity =  this.categoryCrudRepository.findAllByUserIdOrderByCategoryName(userId);
        return categoryMapper.toCategories(categoryEntity);
    }

    @Override
    public boolean exists(CategoryPK categoryPK) {
        return this.categoryCrudRepository.existsById(categoryPK);
    }

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = this.categoryMapper.toCategoryEntity(category);
        return categoryMapper.toCategory(this.categoryCrudRepository.save(categoryEntity));
    }

    @Override
    public void delete(CategoryPK categoryPK) {
        this.categoryCrudRepository.deleteById(categoryPK);
    }
}




