package com.kis.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kis.entity.Market;
import com.kis.entity.Sector;
import com.kis.entity.Stock;
import com.kis.entity.StockBookmark;
import com.kis.entity.StockSearchCriteria;
import com.kis.service.MarketService;
import com.kis.service.SectorService;
import com.kis.service.StockBookmarkService;
import com.kis.service.StockService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ScreenerController {

    @Autowired
    private StockService stockService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private StockBookmarkService bookmarkService;

    @GetMapping("/screener")
    public String stockScreener(HttpSession session,
            @RequestParam(name = "marketCode", required = false) String marketCode,
            @RequestParam(name = "subSectorCode", required = false) String subSectorCode,
            @RequestParam(name = "name", required = false) String name,
            Model model) {

        if (marketCode == null) {
            marketCode = (String) session.getAttribute("marketCode");
        }
        if (subSectorCode == null) {
            subSectorCode = (String) session.getAttribute("subSectorCode");
        }
        if (name == null) {
            name = (String) session.getAttribute("name");
        }

        StockSearchCriteria criteria = new StockSearchCriteria();
        criteria.setMarketCode(marketCode);
        criteria.setSubSectorCode(subSectorCode);
        criteria.setName(name);

        List<Market> marketList = marketService.selectAll();
        model.addAttribute("markets", marketList);

        List<Sector> subSectorList = sectorService.selectAll();
        model.addAttribute("subSectors", subSectorList);

        List<Stock> stockList = stockService.searchStocks(criteria);
        Integer stockCount = stockList.size();

        model.addAttribute("counts", stockCount);
        model.addAttribute("stocks", stockList);
        model.addAttribute("selectedMarket", marketCode);
        model.addAttribute("selectedSubSector", subSectorCode);
        model.addAttribute("searchName", name);

        session.setAttribute("marketCode", marketCode);
        session.setAttribute("subSectorCode", subSectorCode);
        session.setAttribute("name", name);

        return "screener";
    }

    @PostMapping("/screener")
    public String filterStocks(HttpSession session,
            @RequestParam(name = "marketCode", required = false) String marketCode,
            @RequestParam(name = "subSectorCode", required = false) String subSectorCode,
            @RequestParam(name = "name", required = false) String name,
            Model model) {

        return stockScreener(session, marketCode, subSectorCode, name, model);
    }

    @GetMapping("/screener/clear")
    public String clearSession(HttpSession session) {
        // 세션에서 필터 관련 값 제거
        session.removeAttribute("marketCode");
        session.removeAttribute("subSectorCode");
        session.removeAttribute("name");

        // 초기 상태로 리다이렉트
        return "redirect:/screener";
    }

    @GetMapping("add")
    public String addBookmark(HttpSession session,
            @RequestParam(name = "stockId") Integer stockId,
            @RequestParam(name = "userId") Integer userId,
            Model model) {

        Stock stock = new Stock();
        stock.setId(stockId);

        StockBookmark bookmark = new StockBookmark();
        bookmark.setUserId(userId);
        bookmark.setStockId(stockId);
        bookmark.setCreatedDate(LocalDateTime.now());

        bookmarkService.addBookmark(bookmark);

        // 리다이렉트 없이 바로 스크리너를 다시 호출
        return stockScreener(session,
                (String) session.getAttribute("marketCode"),
                (String) session.getAttribute("subSectorCode"),
                (String) session.getAttribute("name"),
                model);
    }
}

// @GetMapping("/screener")
// public String stockScreener(
// @RequestParam(name = "marketCode", required = false) String marketCode,
// @RequestParam(name = "subSectorCode", required = false) String subSectorCode,
// @RequestParam(name = "name", required = false) String name,
// Model model) {

// List<Market> marketList = marketService.selectAll();
// model.addAttribute("markets", marketList);

// List<Sector> subSectorList = sectorService.selectAll();
// model.addAttribute("subSectors", subSectorList);

// Integer stockCount = getStockCount(marketCode, subSectorCode, name);

// List<Stock> stockList = getStockList(marketCode, subSectorCode, name);

// List<Stock> nameList = stockService.selectByName(name);

// model.addAttribute("counts", stockCount);
// model.addAttribute("stocks", stockList);
// model.addAttribute("names", nameList);
// model.addAttribute("selectedMarket", marketCode);
// model.addAttribute("selectedSubSector", subSectorCode);

// return "screener";
// }

// private Integer getStockCount(String marketCode, String subSectorCode) {
// if (marketCode != null && !marketCode.isEmpty() && subSectorCode != null &&
// !subSectorCode.isEmpty()) {
// return stockService.countByMarketCodeAndSubSectorCode(marketCode,
// subSectorCode);
// } else if (marketCode != null && !marketCode.isEmpty()) {
// return stockService.countByMarket(marketCode);
// } else if (subSectorCode != null && !subSectorCode.isEmpty()) {
// return stockService.countBySubSectorCode(subSectorCode);
// } else {
// return stockService.countAll();
// }
// }

// private List<Stock> getStockList(String marketCode, String subSectorCode,
// String name) {
// if (marketCode != null && !marketCode.isEmpty() && subSectorCode != null &&
// !subSectorCode.isEmpty()) {
// return stockService.selectByMarketCodeAndSubSectorCode(marketCode,
// subSectorCode);
// } else if (marketCode != null && !marketCode.isEmpty()) {
// return stockService.selectByMarketCode(marketCode);
// } else if (subSectorCode != null && !subSectorCode.isEmpty()) {
// return stockService.selectBySubSectorCode(subSectorCode);
// } else {
// return stockService.selectAll();
// }
// }
