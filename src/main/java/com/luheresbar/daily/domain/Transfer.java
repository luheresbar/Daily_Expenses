package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Transfer {

    private int transferId;
    private Double transferValue;
    private String transferDate;
    private Integer userId;
    private String sourceAccountName;
    private String destinationAccountName;
    private String type;
    private String description;

}
