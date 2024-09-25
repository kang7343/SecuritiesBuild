package com.kis.entity;

import lombok.Data;

@Data
public class StockSearchCriteria {
    private String marketCode;
    private String subSectorCode;
    private String name;

}
