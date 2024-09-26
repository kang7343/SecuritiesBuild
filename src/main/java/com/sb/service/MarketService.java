package com.sb.service;

import java.util.List;

import com.sb.entity.Market;

public interface MarketService {

    List<Market> selectAll();

    List<Market> selectName();
}
