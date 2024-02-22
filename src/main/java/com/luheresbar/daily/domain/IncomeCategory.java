package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeCategory {

    private String categoryName;
    private Integer userId;
    private Boolean available;

}
