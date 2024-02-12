package com.luheresbar.daily.persistence.entity;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategoryPK {

    private String categoryName;
    private Integer userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseCategoryPK that)) return false;
        return Objects.equals(getCategoryName(), that.getCategoryName()) && Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryName(), getUserId());
    }
}
