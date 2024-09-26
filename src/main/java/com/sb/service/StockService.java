package com.sb.service;

import java.util.List;

import com.sb.entity.Stock;
import com.sb.entity.StockSearchCriteria;

public interface StockService {

    List<Stock> selectAll();

    // List<Stock> selectAll(@Param("limit") Integer limit, @Param("offset") Integer
    // offset);

    String selectCodeById(Integer stockId);

    List<Stock> selectCode();

    List<Stock> selectByName(String name);

    Integer countAll();

    boolean isMarketOpen();

    // boolean isNasdaqMarketOpen();

    List<Stock> selectByMarketCode(String marketCode);

    List<Stock> selectBySubSectorCode(String subSectorCode);

    List<Stock> selectByMarketCodeAndSubSectorCode(String marketCode, String subSectorCode);

    Integer countByMarket(String marketCode);

    Integer countBySubSectorCode(String subSectorCode);

    Integer countByMarketCodeAndSubSectorCode(String marketCode, String subSectorCode);

    List<Stock> selectByCode(String code);

    List<Stock> searchStocks(StockSearchCriteria criteria);

}
