package com.luheresbar.daily.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
 public class TokenDto {
     private String access_token;
     private String refresh_token;
}


