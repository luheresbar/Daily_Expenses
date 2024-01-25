package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transfer {

    private int transferId;
    private Double transferValue;
    private String transferDate;
    private Integer userId;
    private String sourceAccountName;
    private String destinationAccountName;
    private String type;

}
