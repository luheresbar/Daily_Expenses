package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Category;
import com.luheresbar.daily.persistence.entity.CategoryPK;

import java.util.List;

public interface ICategoryRepository {

    List<Category> getByUser(String userId);

    boolean exists(CategoryPK categoryPK);

    Category save(Category category);

    void delete(CategoryPK categoryPK);
}
