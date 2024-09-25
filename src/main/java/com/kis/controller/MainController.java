package com.kis.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kis.model.IndexData;
import com.kis.service.KisService;
import com.kis.service.StockService;

import reactor.core.publisher.Flux;

@Controller
public class MainController {

    @Autowired
    private KisService kisService;

    @Autowired
    private StockService stockService;

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper를 빈으로 관리

    public MainController(KisService kisService, StockService stockService) {
        this.kisService = kisService;
        this.stockService = stockService;
    }

    @GetMapping("/main")
    public String main(Model model) {
        return processRequest(model, "1000000000000", null, null, null, null, null, null, "main");
    }

    @GetMapping("/main-amount")
    public String mainAmount(Model model) {
        return processRequest(model, "1000000000000", "1", null, null, null, null, null, "main-amount");
    }

    @GetMapping("/main-price")
    public String mainPrice(Model model) {
        return processRequest(model, "1000000000000", null, "1", null, null, null, null, "main-price");
    }

    @GetMapping("/main-advanced")
    public String mainAdvanced(Model model) {
        return processRequest(model, "100", null, null, "1", null, null, null, "main-adv");
    }

    // @GetMapping("/main-declined")
    // public String mainDeclined(Model model) {
    // return processRequest(model, null, null, null, null, "1", null, "-100000",
    // "main-dec");
    // }

    // 重複するメソッドをまとめて効率化
    private String processRequest(Model model, String val, String amount, String price, String rate, String vol,
            String val2, String valx, String viewName) {
        List<String> iscdsAndOtherVariable = Arrays.asList(val);

        Flux<IndexData> volumeFlux = Flux.fromIterable(iscdsAndOtherVariable)
                .concatMap(tuple -> kisService.getCurrentRanking(valx, vol, rate, price, amount, val, val2))
                .map(jsonData -> {
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<IndexData> volumeList = volumeFlux.collectList().block();
        boolean marketOpen = stockService.isMarketOpen();
        // boolean nasdaqMarketOpen = stockService.isNasdaqMarketOpen();

        model.addAttribute("marketOpen", marketOpen);
        // model.addAttribute("nasdaqMarketOpen", nasdaqMarketOpen);
        model.addAttribute("jobDate", kisService.getJobDateTime());
        model.addAttribute("volumeRank", volumeList);

        return viewName;
    }
}
