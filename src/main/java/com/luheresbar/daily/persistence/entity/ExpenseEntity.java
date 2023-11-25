package com.luheresbar.daily.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Getter
@Setter
public class ExpenseEntity {

    @Id
    @Column(name = "expense_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expenseId;

    @Column(nullable = false)
    private Double expense;

    @Column(length = 50)
    private String description;

    @Column(name = "expense_date")
    private LocalDateTime expenseDate;

    @Column(name = "user_id", nullable = false, length = 25)
    private String userId;

    @Column(name = "account_name", nullable = false, length = 30)
    private String accountName;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne
    @JoinColumns ({
            @JoinColumn(name = "category_id", referencedColumnName = "category_id",insertable = false, updatable = false),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    })
    private CategoryEntity category;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "account_name", referencedColumnName = "account_name", insertable = false, updatable = false)
    })
    private AccountEntity account;


}