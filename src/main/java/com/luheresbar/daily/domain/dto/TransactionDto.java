package com.luheresbar.daily.domain.dto;

import java.util.List;

public record TransactionDto(
        List<TransactionDetail> transactionDetails,
        Double totalExpense,
        Double totalIncome
) {
}
