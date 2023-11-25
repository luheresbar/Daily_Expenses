package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Income {

    private int incomeId;
    private double income;
    private String description;
    private String incomeDate;
    private String userId;
    private String accountName;
    private Account account;


}