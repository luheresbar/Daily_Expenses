package com.luheresbar.daily.domain.dto;

import lombok.Data;

@Data
public class UpdateUserIdDto {

    private Integer currentUserId;
    private Integer newUserId;

}
