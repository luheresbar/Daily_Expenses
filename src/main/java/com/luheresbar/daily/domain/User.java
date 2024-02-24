package com.luheresbar.daily.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String registerDate;
    private List<UserRole> roles;
}

