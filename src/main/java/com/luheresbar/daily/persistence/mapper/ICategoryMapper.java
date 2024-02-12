package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Category;
import com.luheresbar.daily.persistence.entity.ExpenseCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IUserMapper.class})
public interface ICategoryMapper {

    Category toCategory(ExpenseCategoryEntity expenseCategoryEntity);

    List<Category> toCategories(List<ExpenseCategoryEntity> categoryEntities);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    ExpenseCategoryEntity toCategoryEntity(Category category);

}
