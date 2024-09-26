package com.sb.entity;

import lombok.Data;

@Data
public class Stock {

    private Integer id;
    private String code;
    private String name;
    private String eng_name;
    private String sector_code;
    private String sector_name;
    private String sub_sector_code;
    private String sub_sector_name;
    private String category;
    private String market_code;
    private String market_name;

}
