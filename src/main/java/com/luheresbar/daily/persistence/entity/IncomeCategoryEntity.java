package com.luheresbar.daily.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "income_categories")
//@IdClass(InCategoryPK.class)
@Setter
@Getter
public class IncomeCategoryEntity {

    @Id
    @Column(name = "category_name", nullable = false, length = 25)
    private String categoryName;

    @Id
    @Column(name = "user_id", nullable = false, length = 25)
    private Integer userId;

}
