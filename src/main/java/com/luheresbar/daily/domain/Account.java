package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private String accountName;
    private String userId;
    private double availableMoney;
    private boolean available;
    private User user;

}
