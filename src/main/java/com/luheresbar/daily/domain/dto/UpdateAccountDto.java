package com.luheresbar.daily.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountDto {
    private String accountName;
    private String newAccountName;
    private Integer userId;
    private Double availableMoney;
    private Boolean available;

}
