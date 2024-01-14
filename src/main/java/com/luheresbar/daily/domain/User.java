package com.luheresbar.daily.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {

    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String registerDate;

}
