package com.sb.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.model.Body;
import com.sb.model.IndexData;
import com.sb.service.KisService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Controller
public class KisController {

    @Autowired
    private KisService kisService;

    public KisController(KisService kisService) {
        this.kisService = kisService;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("jobDate", kisService.getJobDateTime());

        return "index";

    }

    @GetMapping("/indices")
    public String majorIndices(Model model) {

        String val = "1000000000000"; // 大きい値を検索の基準値として入れて大きい順にリスト
        String valx = "1"; // 時価総額
        String vol = null;
        String rate = null;
        String amount = null;
        String price = null;
        String val2 = null;

        List<String> indexNames = Arrays.asList(
                "NIKKEI 225", "KOSPI", "S&P 500", "Nasdaq", "Dow Jones", "Euro Stoxx 50",
                "CSI 300");

        List<Tuple2<String, String>> iscdsAndOtherVariable1 = Arrays.asList(
                Tuples.of("JP#NI225", "N"), // Japan Nikkei 225
                Tuples.of("KOSPI", "N"), // KOSPI200
                // Tuples.of("1001", "U"), // KOSDAQ
                Tuples.of("SPX", "N"), // S&P500
                Tuples.of("COMP", "N"), // NASDAQ Composite
                Tuples.of(".DJI", "N"), // Dow Jones
                Tuples.of("SX5E", "N"), // EURO STOXX 50
                Tuples.of("CH#000300", "N") // CSI300
        );

        Flux<IndexData> indicesFlux = Flux.fromIterable(iscdsAndOtherVariable1)
                .concatMap(tuple -> kisService.getMajorIndex(tuple.getT1(), tuple.getT2()))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<IndexData> indicesList = indicesFlux.collectList().block();

        List<String> iscdsAndOtherVariable3 = Arrays.asList(val);

        Flux<IndexData> marketCapFlux = Flux.fromIterable(iscdsAndOtherVariable3)
                .concatMap(tuple -> kisService.getCurrentRanking(valx, vol, rate, price,
                        amount, val, val2))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<IndexData> marketCapList = marketCapFlux.collectList().block();

        for (int i = 0; i < indicesList.size(); i++) {
            indicesList.get(i).setName(indexNames.get(i));
        }

        model.addAttribute("currentIndices", indicesList);
        model.addAttribute("marketCapRank", marketCapList);
        model.addAttribute("jobDate", kisService.getJobDateTime());

        return "indices";
    }

    @GetMapping("/equities-tse/{id}")
    public Mono<String> getCurrentPrice(@PathVariable("id") String id, Model model) {
        // 非同期API呼び出しの結果をMono（0-1つの値を処理。output1のみ使うので）でラッピング
        Mono<Body> searchInfoMono = kisService.getSearchInfo(id); // 詳細情報
        Mono<Body> currentPriceTseMono = kisService.getCurrentPriceTse(id); // 現在株価
        Mono<Body> currentPriceDetailMono = kisService.getCurrentPriceDetail(id); // 現在株価詳細

        // 日単位
        // Flux()
        List<String> iscdsAndOtherVariable2 = Arrays.asList(id);
        Flux<IndexData> dailyPriceFlux = Flux.fromIterable(iscdsAndOtherVariable2)
                .concatMap(tuple -> kisService.getDailyPrice(id))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                        // jsonデータをIndexData型に変換
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<IndexData> dailyPriceList = dailyPriceFlux.collectList().block();
        // dailyPriceFluxで得られた結果をリスト変換。blockでブロッキング

        // 分単位
        List<String> iscdsAndOtherVariable3 = Arrays.asList(id);
        Flux<IndexData> minutePriceFlux = Flux.fromIterable(iscdsAndOtherVariable3)
                .concatMap(tuple -> kisService.getMinutePrice(id))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<IndexData> minutePriceList = minutePriceFlux.collectList().block();

        // Mono.zipメソッドを使って複数のMonoを結合
        return Mono.zip(searchInfoMono, currentPriceTseMono, currentPriceDetailMono)
                .doOnNext(tuple -> {
                    // 複数のそれぞれ異なるレスポンスが含まれているBodyオブジェクトをタプルを用いてモデルに追加。
                    model.addAttribute("info", tuple.getT1().getOutput());
                    model.addAttribute("equity", tuple.getT2().getOutput());
                    model.addAttribute("detail", tuple.getT3().getOutput());
                    model.addAttribute("jobDate", kisService.getJobDateTime());
                    model.addAttribute("daily", dailyPriceList);
                    model.addAttribute("minute", minutePriceList);
                })
                .thenReturn("equities-tse");
    }
}

// @GetMapping("/info/{id}")
// public Mono<String> getStockInfo(@PathVariable("id") String id, Model model)
// {
// return kisService.getSearchInfo(id)
// .doOnNext(body -> {
// model.addAttribute("info", body.getOutput());
// model.addAttribute("jobDate", kisService.getJobDateTime());
// })
// .thenReturn("info");
// }

// @GetMapping("/equities-nas/{id}")
// public Mono<String> getCurrentPriceNas(@PathVariable("id") String id, Model
// model) {

// Mono<Body> searchInfoNasMono = kisService.getSearchInfoNas(id);
// Mono<Body> currentPriceNasMono = kisService.getCurrentPriceNas(id);
// Mono<Body> currentPriceDetailNasMono =
// kisService.getCurrentPriceDetailNas(id);

// List<String> iscdsAndOtherVariable2 = Arrays.asList(id);
// Flux<IndexData> dailyPriceNasFlux = Flux.fromIterable(iscdsAndOtherVariable2)
// .concatMap(tuple -> kisService.getDailyPriceNas(id))
// .map(jsonData -> {
// ObjectMapper objectMapper = new ObjectMapper();
// try {
// return objectMapper.readValue(jsonData, IndexData.class);
// } catch (JsonProcessingException e) {
// throw new RuntimeException(e);
// }
// });

// List<IndexData> dailyPriceList = dailyPriceNasFlux.collectList().block();

// List<String> iscdsAndOtherVariable3 = Arrays.asList(id);
// Flux<IndexData> minutePriceNasFlux =
// Flux.fromIterable(iscdsAndOtherVariable3)
// .concatMap(tuple -> kisService.getMinutePriceNas(id))
// .map(jsonData -> {
// ObjectMapper objectMapper = new ObjectMapper();
// try {
// return objectMapper.readValue(jsonData, IndexData.class);
// } catch (JsonProcessingException e) {
// throw new RuntimeException(e);
// }
// });

// List<IndexData> minutePriceList = minutePriceNasFlux.collectList().block();

// return Mono.zip(searchInfoNasMono, currentPriceNasMono,
// currentPriceDetailNasMono)
// .doOnNext(tuple -> {

// model.addAttribute("info", tuple.getT1().getOutput());
// model.addAttribute("equity", tuple.getT2().getOutput());
// model.addAttribute("detail", tuple.getT3().getOutput());
// model.addAttribute("jobDate", kisService.getJobDateTime());
// model.addAttribute("daily", dailyPriceList);
// model.addAttribute("minute", minutePriceList);
// })
// .thenReturn("equities-nas");
// }

// @GetMapping("/equities/{id}")
// public Mono<String> getCurrentPrice(@PathVariable("id") String id, Model
// model) {
// return kisService.getCurrentPrice(id)
// .doOnNext(body -> {
// model.addAttribute("equity", body.getOutput());
// model.addAttribute("jobDate", kisService.getJobDateTime());
// })
// .thenReturn("equities")
// .onErrorResume(error -> {
// System.out.println("*** error: " + error);
// return Mono.just("error");
// });
// }