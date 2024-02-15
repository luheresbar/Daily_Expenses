package com.luheresbar.daily.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "incomes")
@Getter
@Setter
public class IncomeEntity {

    @Id
    @Column(name = "income_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incomeId;

    @Column(nullable = false)
    private Double income;

    @Column(length = 50)
    private String description;

    @Column(name = "income_date")
    private LocalDateTime incomeDate;

    @Column(name = "user_id", nullable = false, length = 25)
    private Integer userId;

    @Column(name = "account_name", nullable = false, length = 30)
    private String accountName;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "account_name", referencedColumnName = "account_name", insertable = false, updatable = false)
    })
    @JsonIgnore
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns ({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, insertable = false),
            @JoinColumn(name = "category_name", referencedColumnName = "category_name",insertable = false, updatable = false)
    })
    @JsonIgnore
    private IncomeCategoryEntity categoryIncome;

}
