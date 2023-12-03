package com.luheresbar.daily.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @Column(name = "transaction_value",nullable = false)
    private Double transactionValue;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "user_id", nullable = false, length = 25)
    private String userId;

    @Column(name = "source_account_name", nullable = false, length = 30)
    private String sourceAccountName;

    @Column(name = "destination_account_name", nullable = false, length = 30)
    private String destinationAccountName;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "source_account_name", referencedColumnName = "account_name", insertable = false, updatable = false)
    })
    private AccountEntity sourceAccount;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "destination_account_name", referencedColumnName = "account_name", insertable = false, updatable = false)
    })
    private AccountEntity destinationAccount;


}
