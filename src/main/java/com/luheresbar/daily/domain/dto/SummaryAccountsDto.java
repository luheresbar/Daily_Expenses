package com.luheresbar.daily.domain.dto;

import com.luheresbar.daily.domain.Account;

import java.util.List;

public record SummaryAccountsDto(
        List<Account> enabledAccounts,
        List<Account> disabledAccounts,
        Double totalAvailableMoney
) {
}
