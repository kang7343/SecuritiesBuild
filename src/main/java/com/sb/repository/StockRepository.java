package com.sb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sb.entity.Stock;
import com.sb.entity.StockSearchCriteria;

@Mapper
public interface StockRepository {

        public List<Stock> selectAll();

        // List<Stock> selectAll(@Param("limit") Integer limit, @Param("offset") Integer
        // offset);

        public String selectCodeById(Integer stockId);

        public List<Stock> selectCode();

        public List<Stock> selectByName(String name);

        public Integer countAll();

        public boolean isMarketOpen();

        public List<Stock> selectByCode(String code);

        // boolean isNasdaqMarketOpen();

        public List<Stock> selectByMarketCode(String marketCode);

        public List<Stock> selectBySubSectorCode(String subSectorCode);

        public List<Stock> selectByMarketCodeAndSubSectorCode(@Param("marketCode") String marketCode,
                        @Param("subSectorCode") String subSectorCode);

        public Integer countByMarket(String marketCode);

        public Integer countBySubSectorCode(String subSectorCode);

        public Integer countByMarketCodeAndSubSectorCode(@Param("marketCode") String marketCode,
                        @Param("subSectorCode") String subSectorCode);

        // public List<Stock> searchStocks(@Param("marketCode") String marketCode,
        // @Param("subSectorCode") String subSectorCode,
        // @Param("name") String name);

        public List<Stock> searchStocks(StockSearchCriteria criteria);

}