package com.luheresbar.daily.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateCategoryDto {
    private String categoryName;
    private String newCategoryName;
    private Integer userId;
}
