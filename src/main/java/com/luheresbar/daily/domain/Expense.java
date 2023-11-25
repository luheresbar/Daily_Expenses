package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Expense {

    private int expenseId;
    private double expense;
    private String description;
    private String expenseDate;
    private String userId;
    private String accountName;
    private int categoryId;
    private Category category;
    private Account account;



}
