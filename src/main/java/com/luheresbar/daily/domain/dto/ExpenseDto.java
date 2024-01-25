package com.luheresbar.daily.domain.dto;

import com.luheresbar.daily.domain.Expense;

import java.util.List;

public record ExpenseDto(
        List<Expense> expenses,
        Double totalExpense
) {
}
