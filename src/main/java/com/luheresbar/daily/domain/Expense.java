package com.luheresbar.daily.domain;

import com.luheresbar.daily.persistence.entity.AccountEntity;
import com.luheresbar.daily.persistence.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

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
