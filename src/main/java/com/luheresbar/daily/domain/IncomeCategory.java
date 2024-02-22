package com.luheresbar.daily.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class IncomeCategory {

    private String categoryName;
    private Integer userId;
    private Boolean available;

}
