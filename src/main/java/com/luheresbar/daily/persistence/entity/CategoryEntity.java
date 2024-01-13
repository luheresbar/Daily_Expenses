package com.luheresbar.daily.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Entity
@Table(name = "categories")
@IdClass(CategoryPK.class)
@Setter
@Getter
public class CategoryEntity {

    @Id
    @Column(name = "category_name", nullable = false, length = 25)
    private String categoryName;

    @Id
    @Column(name = "user_id", nullable = false, length = 25)
    private Integer userId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ExpenseEntity> expenses;


}
