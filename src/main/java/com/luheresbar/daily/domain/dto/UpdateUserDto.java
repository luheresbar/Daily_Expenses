package com.luheresbar.daily.domain.dto;

public record UpdateUserDto(
        Integer userId,
        String username,
        String password,
        String email
) {
}
