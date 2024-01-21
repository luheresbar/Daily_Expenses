package com.luheresbar.daily.domain.dto;

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
    private String registerDat;
    private List<AccountDto> accounts;
    private List<CategoryDto> categories;

}

