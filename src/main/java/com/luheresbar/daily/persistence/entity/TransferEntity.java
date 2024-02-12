package com.luheresbar.daily.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Getter
@Setter
public class TransferEntity {

    @Id
    @Column(name = "transfer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferId;

    @Column(name = "transfer_value",nullable = false)
    private Double transferValue;

    @Column(name = "transfer_date")
    private LocalDateTime transferDate;

    @Column(name = "user_id", nullable = false, length = 25)
    private Integer userId;

    @Column(name = "source_account_name", nullable = false, length = 30)
    private String sourceAccountName;

    @Column(name = "destination_account_name", nullable = false, length = 30)
    private String destinationAccountName;

    @Column(name = "type")
    private String type;

    @Column(length = 50)
    private String description;

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
