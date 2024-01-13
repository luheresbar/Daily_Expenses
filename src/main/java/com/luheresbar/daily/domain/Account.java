package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private String accountName;
    private Integer userId;
    private Double availableMoney;
    private Boolean available;

}
