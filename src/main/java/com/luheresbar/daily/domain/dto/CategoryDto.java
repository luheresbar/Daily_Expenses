package com.luheresbar.daily.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private String categoryType;
    private String categoryName;
    private Integer userId;
}
