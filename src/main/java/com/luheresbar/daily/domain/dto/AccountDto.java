package com.luheresbar.daily.domain.dto;

import com.luheresbar.daily.domain.Account;

import java.util.List;

public record AccountDto(
        List<Account> accounts,
        Double totalAvailableMoney
) {
}
