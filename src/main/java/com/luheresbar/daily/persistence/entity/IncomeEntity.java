package com.luheresbar.daily.persistence.entity;

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
    private String userId;

    @Column(name = "account_name", nullable = false, length = 30)
    private String accountName;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "account_name", referencedColumnName = "account_name", insertable = false, updatable = false)
    })
    private AccountEntity account;



}
