package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserRole {

    private String role;
    private Integer userId;
    private String grantedDate;

}
