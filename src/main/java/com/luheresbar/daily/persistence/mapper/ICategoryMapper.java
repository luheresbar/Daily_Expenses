package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Category;
import com.luheresbar.daily.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IUserMapper.class})
public interface ICategoryMapper {

    Category toCategory(CategoryEntity categoryEntity);

    List<Category> toCategories(List<CategoryEntity> categoryEntities);

    @Mapping(target = "expenses", ignore = true)
    CategoryEntity toCategoryEntity(Category category);

}
