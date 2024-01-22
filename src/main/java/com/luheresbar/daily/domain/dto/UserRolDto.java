package com.luheresbar.daily.domain.dto;

import java.time.LocalDateTime;

public record UserRolDto(
        String role,
        String grantedDate
) {
}
