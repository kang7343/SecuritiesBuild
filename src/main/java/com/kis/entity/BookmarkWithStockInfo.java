package com.kis.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookmarkWithStockInfo {
    private Integer bookmarkId;
    private Integer userId;
    private LocalDateTime createdDate;
    private Integer stockId;
    private String stockCode;
    private String stockName;
    private String stockEngName;
    private String stockMarket;
    private String stockSector;
    private String stockSubSector;

}
