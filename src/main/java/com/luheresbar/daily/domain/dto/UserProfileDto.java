package com.luheresbar.daily.domain.dto;

import com.luheresbar.daily.domain.UserRole;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    private Integer userId;
    private String username;
    private String email;
    private String registerDate;
    private List<UserRole> roles;

}

