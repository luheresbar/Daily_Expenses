package com.luheresbar.daily.domain.dto;

import java.util.List;

public record SummaryCategoryDto(
        List<CategoryDto> enabledCategories,

        List<CategoryDto> disabledCategories
) {
}
