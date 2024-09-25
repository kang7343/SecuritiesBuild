package com.kis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kis.entity.Market;

@Mapper
public interface MarketRepository {

    public List<Market> selectAll();

    public List<Market> selectName();

}
