package com.sb.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sb.model.Body;
import com.sb.security.AccessTokenManager;
import com.sb.security.KisConfig;

import reactor.core.publisher.Mono;

@Service
public class KisService {

        @Autowired
        private AccessTokenManager accessTokenManager;

        private final WebClient WebClient;
        private String path;
        private String tr_id;

        public KisService(WebClient.Builder webclientBuiler) {
                this.WebClient = webclientBuiler.baseUrl(KisConfig.REST_BASE_URL).build();

        }

        public String getStringToday() {
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                return localDate.format(formatter);
        }

        public String getJobDateTime() {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return now.format(formatter);
        }

        public Mono<String> getMajorIndex(String iscd, String fid_cond_mrkt_div_code) {

                if (fid_cond_mrkt_div_code.equals("U")) {
                        path = KisConfig.FHKUP03500100_PATH;
                        tr_id = "FHKUP03500100"; // 国内指数
                } else {
                        path = KisConfig.FHKST03030100_PATH;
                        tr_id = "FHKST03030100"; // 海外指数
                }

                return WebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path(path)
                                                .queryParam("fid_cond_mrkt_div_code", fid_cond_mrkt_div_code)
                                                .queryParam("fid_input_iscd", iscd)
                                                .queryParam("fid_input_date_1", getStringToday())
                                                .queryParam("fid_input_date_2", getStringToday())
                                                .queryParam("fid_period_div_code", "D")
                                                .build())
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", tr_id)
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(String.class)
                                .doOnError(e -> {
                                        System.err.println("Error in getMajorIndex: " + e.getMessage());
                                });
        }

        public Mono<String> getDailyPrice(String id) {

                return WebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/uapi/overseas-price/v1/quotations/dailyprice")
                                                .queryParam("AUTH", "")
                                                .queryParam("EXCD", "TSE")
                                                .queryParam("SYMB", id)
                                                .queryParam("GUBN", 0)
                                                .queryParam("BYMD", getStringToday())
                                                .queryParam("MODP", 1)
                                                .build())
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS76240000")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(String.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<String> getCurrentInfo(String code) { // Screener用

                return WebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/uapi/overseas-price/v1/quotations/price")
                                                .queryParam("AUTH", "")
                                                .queryParam("EXCD", "TSE")
                                                .queryParam("SYMB", code)
                                                .build())
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS00000300")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(String.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<String> getDailyPriceNas(String id) {

                return WebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/uapi/overseas-price/v1/quotations/dailyprice")
                                                .queryParam("AUTH", "")
                                                .queryParam("EXCD", "NAS")
                                                .queryParam("SYMB", id)
                                                .queryParam("GUBN", 0)
                                                .queryParam("BYMD", getStringToday())
                                                .queryParam("MODP", 1)
                                                .build())
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS76240000")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(String.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<Body> getCurrentPrice(String id) {
                String url = KisConfig.REST_BASE_URL
                                + "/uapi/domestic-stock/v1/quotations/inquire-price?fid_cond_mrkt_div_code=J&fid_input_iscd="
                                + id;

                return WebClient.get()
                                .uri(url)
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "FHKST01010100")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(Body.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<Body> getSearchInfo(String id) {
                String url = KisConfig.REST_BASE_URL
                                + "/uapi/domestic-stock/v1/quotations/search-info?prdt_type_cd=515&pdno=" + id;

                return WebClient.get()
                                .uri(url)
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "CTPF1604R")
                                .header("custtype", "P")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(Body.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<Body> getSearchInfoNas(String id) {
                String url = KisConfig.REST_BASE_URL
                                + "/uapi/domestic-stock/v1/quotations/search-info?prdt_type_cd=512&pdno=" + id;

                return WebClient.get()
                                .uri(url)
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "CTPF1604R")
                                .header("custtype", "P")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(Body.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<Body> getCurrentPriceNas(String id) {
                String url = KisConfig.REST_BASE_URL
                                + "/uapi/overseas-price/v1/quotations/price?auth=&excd=NAS&symb=" + id;

                return WebClient.get()
                                .uri(url)
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS00000300")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(Body.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<Body> getCurrentPriceTse(String id) {
                String url = KisConfig.REST_BASE_URL
                                + "/uapi/overseas-price/v1/quotations/price?auth=&excd=TSE&symb=" + id;

                return WebClient.get()
                                .uri(url)
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS00000300")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(Body.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<Body> getCurrentPriceDetail(String id) {
                String url = KisConfig.REST_BASE_URL
                                + "/uapi/overseas-price/v1/quotations/price-detail?auth=&excd=TSE&symb=" + id;

                return WebClient.get()
                                .uri(url)
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS76200200")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(Body.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<Body> getCurrentPriceDetailNas(String id) {
                String url = KisConfig.REST_BASE_URL
                                + "/uapi/overseas-price/v1/quotations/price-detail?auth=&excd=NAS&symb=" + id;

                return WebClient.get()
                                .uri(url)
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS76200200")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(Body.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<String> getMinutePrice(String id) {

                return WebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/uapi/overseas-price/v1/quotations/inquire-time-itemchartprice")
                                                .queryParam("AUTH", "")
                                                .queryParam("EXCD", "TSE")
                                                .queryParam("SYMB", id)
                                                .queryParam("NMIN", 1)
                                                .queryParam("PINC", 1)
                                                .queryParam("NEXT", "")
                                                .queryParam("NREC", 120) // 取得データ数
                                                .queryParam("FILL", "")
                                                .queryParam("KEYB", "")
                                                .build())
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS76950200")
                                .header("custtype", "P")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(String.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<String> getMinutePriceNas(String id) {

                return WebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/uapi/overseas-price/v1/quotations/inquire-time-itemchartprice")
                                                .queryParam("AUTH", "")
                                                .queryParam("EXCD", "NAS")
                                                .queryParam("SYMB", id)
                                                .queryParam("NMIN", 1)
                                                .queryParam("PINC", 0)
                                                .queryParam("NEXT", "")
                                                .queryParam("NREC", 120)
                                                .queryParam("FILL", "")
                                                .queryParam("KEYB", "")
                                                .build())
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS76950200")
                                .header("custtype", "P")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(String.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

        public Mono<String> getCurrentRanking(String valx, String vol, String rate, String price, String amount,
                        String val, String val2) {
                // コントローラで使いたいデータのパラムを渡す
                return WebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/uapi/overseas-price/v1/quotations/inquire-search")
                                                .queryParam("AUTH", "")
                                                .queryParam("EXCD", "TSE")
                                                .queryParam("CO_YN_VALX", valx) // 時価総額
                                                .queryParam("CO_EN_VALX", val)
                                                .queryParam("CO_YN_PRICECUR", price) // 株価
                                                .queryParam("CO_EN_PRICECUR", val)
                                                .queryParam("CO_YN_VOLUME", vol) // 売買高
                                                .queryParam("CO_EN_VOLUME", val)
                                                .queryParam("CO_YN_AMT", amount)
                                                .queryParam("CO_EN_AMT", val)
                                                .queryParam("CO_YN_RATE", rate) // 値上がり率
                                                .queryParam("CO_EN_RATE", val)
                                                .queryParam("CO_ST_RATE", val2)
                                                .build())
                                .header("content-type", "application/json; charset=utf-8")
                                .header("authorization", "Bearer " + accessTokenManager.getAccessToken())
                                .header("appkey", KisConfig.APPKEY)
                                .header("appsecret", KisConfig.APPSECRET)
                                .header("tr_id", "HHDFS76410000")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError()
                                                || status.is5xxServerError(),
                                                clientResponse -> clientResponse.bodyToMono(String.class)
                                                                .map(body -> new RuntimeException(body)))
                                .bodyToMono(String.class)
                                .doOnError(error -> System.out.println("*** error: " + error));
        }

}
