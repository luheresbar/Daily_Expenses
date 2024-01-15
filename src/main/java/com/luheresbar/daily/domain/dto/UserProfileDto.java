package com.luheresbar.daily.domain.dto;

public record UserProfileDto(
        Integer userId,
        String username,
        String email,
        String registerDate
) {
}
