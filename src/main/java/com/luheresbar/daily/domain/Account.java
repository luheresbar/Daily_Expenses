package com.luheresbar.daily.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class Account {
    private String accountName;
    private Integer userId;
    private Double availableMoney;
    private Boolean available;


}
