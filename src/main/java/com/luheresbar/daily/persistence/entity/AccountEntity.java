package com.luheresbar.daily.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Entity
@Table(name = "accounts")
@IdClass(AccountPK.class)
@Getter
@Setter
public class AccountEntity {

    @Id
    @Column(name = "account_name", nullable = false, length = 30)
    private String accountName;

    @Id
    @Column(name = "user_id", nullable = false, length = 25)
    private String userId;

    @Column(name = "available_money", columnDefinition = "default 0")
    private Double availableMoney;

    @Column(columnDefinition = "boolean default true")
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "account")
    private List<ExpenseEntity> expenses;

    @OneToMany(mappedBy = "account")
    private List<IncomeEntity> incomes;

    @OneToMany(mappedBy = "sourceAccount")
    private List<TransactionEntity> sourceAccounts;

    @OneToMany(mappedBy = "destinationAccount")
    private List<TransactionEntity> destinationAccounts;

}