package com.sb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.entity.Market;
import com.sb.repository.MarketRepository;

@Service
@Transactional
public class MarketServiceImpl implements MarketService {

    @Autowired
    MarketRepository marketRepository;

    @Override
    public List<Market> selectAll() {
        return marketRepository.selectAll();
    }

    @Override
    public List<Market> selectName() {
        return marketRepository.selectName();
    }
}
