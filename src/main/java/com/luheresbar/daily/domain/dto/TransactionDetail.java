package com.luheresbar.daily.domain.dto;

import lombok.Data;

@Data
public class TransactionDetail {
    private String type;
    private String description;
    private String date;
    private Double amount;
    private String sourceAccountName;
    private String destinationAccountName;
    private String category;
}
