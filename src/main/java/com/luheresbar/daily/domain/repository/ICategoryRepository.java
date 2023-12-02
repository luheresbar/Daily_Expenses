package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Category;

import java.util.List;

public interface ICategoryRepository {

    List<Category> getByUser(String userId);
}
