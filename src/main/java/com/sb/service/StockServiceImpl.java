package com.sb.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.entity.Stock;
import com.sb.entity.StockSearchCriteria;
import com.sb.repository.StockRepository;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    private static final ZoneId TOKYO_ZONE = ZoneId.of("Asia/Tokyo");
    // private static final ZoneId NY_ZONE = ZoneId.of("America/New_York");

    private static final LocalTime TOKYO_MARKET_OPEN_START = LocalTime.of(9, 0);
    private static final LocalTime TOKYO_MARKET_OPEN_END = LocalTime.of(11, 30);
    // private static final LocalTime TOKYO_LUNCH_START = LocalTime.of(11, 30);
    // private static final LocalTime TOKYO_LUNCH_END = LocalTime.of(12, 30);
    private static final LocalTime TOKYO_MARKET_REOPEN_START = LocalTime.of(12, 30);
    private static final LocalTime TOKYO_MARKET_REOPEN_END = LocalTime.of(15, 0);

    // private static final LocalTime NASDAQ_MARKET_OPEN = LocalTime.of(9, 30);
    // private static final LocalTime NASDAQ_MARKET_CLOSE = LocalTime.of(16, 0);

    @Override
    public List<Stock> selectAll() {
        return stockRepository.selectAll();
    }
    // @Override
    // public List<Stock> selectAll(Integer page, Integer size) {
    // int offset = page * size;
    // return stockRepository.selectAll(size, offset);
    // }

    @Override
    public List<Stock> searchStocks(StockSearchCriteria criteria) {
        return stockRepository.searchStocks(criteria);
    }

    @Override
    public String selectCodeById(Integer id) {
        return stockRepository.selectCodeById(id);
    }

    @Override
    public List<Stock> selectCode() {
        return stockRepository.selectCode();
    }

    @Override
    public List<Stock> selectByCode(String code) {
        return stockRepository.selectByCode(code);
    }

    @Override
    public List<Stock> selectByName(String name) {
        return stockRepository.selectByName(name);
    }

    @Override
    public Integer countAll() {
        return stockRepository.countAll();
    }

    @Override
    public List<Stock> selectByMarketCode(String marketCode) {
        return stockRepository.selectByMarketCode(marketCode);
    }

    @Override
    public List<Stock> selectBySubSectorCode(String subSectorCode) {
        return stockRepository.selectBySubSectorCode(subSectorCode);
    }

    @Override
    public List<Stock> selectByMarketCodeAndSubSectorCode(String marketCode, String subSectorCode) {
        return stockRepository.selectByMarketCodeAndSubSectorCode(marketCode, subSectorCode);
    }

    @Override
    public Integer countByMarket(String marketCode) {
        return stockRepository.countByMarket(marketCode);
    }

    @Override
    public Integer countBySubSectorCode(String subSectorCode) {
        return stockRepository.countBySubSectorCode(subSectorCode);
    }

    @Override
    public Integer countByMarketCodeAndSubSectorCode(String marketCode, String subSectorCode) {
        return stockRepository.countByMarketCodeAndSubSectorCode(marketCode, subSectorCode);
    }

    private static final Set<LocalDate> PUBLIC_HOLIDAYS = new HashSet<>(Arrays.asList(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 8),
            LocalDate.of(2024, 2, 11),
            LocalDate.of(2024, 3, 21),
            LocalDate.of(2024, 4, 29),
            LocalDate.of(2024, 5, 3),
            LocalDate.of(2024, 5, 4),
            LocalDate.of(2024, 5, 5),
            LocalDate.of(2024, 5, 6),
            LocalDate.of(2024, 7, 15),
            LocalDate.of(2024, 8, 10),
            LocalDate.of(2024, 9, 16),
            LocalDate.of(2024, 9, 23),
            LocalDate.of(2024, 10, 14),
            LocalDate.of(2024, 11, 3),
            LocalDate.of(2024, 11, 4),
            LocalDate.of(2024, 11, 23),
            LocalDate.of(2024, 12, 23)));

    public boolean isMarketOpen() {
        ZonedDateTime now = ZonedDateTime.now(TOKYO_ZONE);
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        boolean isWeekend = now.getDayOfWeek().getValue() >= 6; // 6은 토요일, 7은 일요일

        boolean isHoliday = PUBLIC_HOLIDAYS.contains(today);

        if (isWeekend || isHoliday) {
            return false;
        }

        boolean isBeforeLunch = currentTime.isAfter(TOKYO_MARKET_OPEN_START)
                && currentTime.isBefore(TOKYO_MARKET_OPEN_END);
        boolean isAfterLunch = currentTime.isAfter(TOKYO_MARKET_REOPEN_START)
                && currentTime.isBefore(TOKYO_MARKET_REOPEN_END);

        return isBeforeLunch || isAfterLunch;
    }

    // @Override
    // public boolean isNasdaqMarketOpen() {
    // ZonedDateTime now = ZonedDateTime.now(NY_ZONE);
    // LocalTime currentTime = now.toLocalTime();

    // return !currentTime.isBefore(NASDAQ_MARKET_OPEN) &&
    // !currentTime.isAfter(NASDAQ_MARKET_CLOSE);
    // }

}
