package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class User {

    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String registerDate;
    private List<UserRole> roles;
}

