package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

    private int transactionId;
    private double transactionValue;
    private String transactionDate;
    private String userId;
    private String sourceAccountName;
    private String destinationAccountName;
    private Account sourceAccount;
    private Account destinationAccount;



}
