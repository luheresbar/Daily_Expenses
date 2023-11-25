package com.luheresbar.daily.persistence.crud;


import com.luheresbar.daily.persistence.entity.CategoryEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ICategoryCrudRepository extends ListCrudRepository<CategoryEntity, Integer> {
}
