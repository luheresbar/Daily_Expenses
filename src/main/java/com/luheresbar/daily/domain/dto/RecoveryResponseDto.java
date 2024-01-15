package com.luheresbar.daily.domain.dto;

//public record RecoveryResponse( String link, String token ) {
//}

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
 public class RecoveryResponseDto {
     private String link;
     private String token;
}
