package com.luheresbar.daily.domain.dto;

import lombok.Data;

@Data
public class UpdateAccountIdDto {

    private String userId;
    private String currentAccountName;
    private String newAccountName;

}
