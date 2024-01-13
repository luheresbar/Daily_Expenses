package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRole {

    private String role;
    private Integer userId;
    private String grantedDate;

}
