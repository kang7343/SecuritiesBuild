package com.sb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sb.entity.Market;

@Mapper
public interface MarketRepository {

    public List<Market> selectAll();

    public List<Market> selectName();

}
