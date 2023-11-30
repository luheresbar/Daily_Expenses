package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {

    private String userId;
    private String username;
    private String password;
    private String email;
    private String registerDate;

}
