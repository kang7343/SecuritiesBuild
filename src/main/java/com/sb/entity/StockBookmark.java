package com.sb.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockBookmark {

    private Integer userId;
    private Integer stockId;
    private LocalDateTime createdDate;

}
