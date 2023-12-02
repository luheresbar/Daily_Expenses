package com.luheresbar.daily.domain.dto;

import lombok.Data;

@Data
public class UpdateUserIdDto {

    private String currentUserId;
    private String newUserId;

}
