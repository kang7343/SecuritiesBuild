package com.kis.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KisConfig {
    public static final String REST_BASE_URL = "https://openapi.koreainvestment.com:9443";
    public static final String REST_VTS_URL = "https://openapivts.koreainvestment.com:29443";
    public static final String WS_BASE_URL = "ws://ops.koreainvestment.com:21000";
    public static final String APPKEY = "PSmLrCLSUooFmt3u9Tm5RhlqrJjbn9d9fYZn";
    public static final String APPSECRET = "SBrtjwowkxM8ZRkMMdYVbVw3b+z6yPOm2zy9jUNWep8wzA2NbOPBJhqZw30ULuvcSgTOlvd8RQyE5PYQwtUohyGPrYePZusj3aiayHnwtl3TkxDI5i/zVSuLBaxf5tW7GQyYh8HyyKrX/apPeLcy4fL4ONNKnkQafrCmX2eezDF+aX/mbo4=";
    public static final String CANO = "64508561";
    public static final String ACNT_PRDT_CD = "01";
    public static final String CUSTTYPE = "P";

    public static final String FHKUP03500100_PATH = "/uapi/domestic-stock/v1/quotations/inquire-daily-indexchartprice";
    // public static final String FHKST03500100_PATH =
    // "/uapi/overseas-price/v1/quotations/inquire-daily-chartprice";
    public static final String FHKST03030100_PATH = "/uapi/overseas-price/v1/quotations/inquire-daily-chartprice";
    public static final String TTTC8434R_PATH = "/uapi/domestic-stock/v1/trading/inquire-balance";

}
