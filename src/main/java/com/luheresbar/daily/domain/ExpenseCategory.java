package com.luheresbar.daily.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class ExpenseCategory {

    private String categoryName;
    private Integer userId;
    private Boolean available;


}
