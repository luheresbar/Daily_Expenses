package com.luheresbar.daily.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private int categoryId;
    private String categoryName;
    private String userId;
    private User user;


}
