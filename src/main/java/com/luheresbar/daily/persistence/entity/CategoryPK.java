package com.luheresbar.daily.persistence.entity;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPK {

    private String categoryName;
    private Integer userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryPK that)) return false;
        return Objects.equals(categoryName, that.categoryName) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, userId);
    }
}
