package com.kis.service;

import java.util.List;

import com.kis.entity.Market;

public interface MarketService {

    List<Market> selectAll();

    List<Market> selectName();
}
