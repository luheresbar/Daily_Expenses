package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Expense {

    private int expenseId;
    private Double expense;
    private String description;
    private String expenseDate;
    private Integer userId;
    private String accountName;
    private String categoryName;

}
