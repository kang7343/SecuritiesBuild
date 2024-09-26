package com.sb.security;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.sb.model.OauthInfo;
import com.sb.model.TokenInfo;

import reactor.core.publisher.Mono;

@Component
public class AccessTokenManager {
    private final WebClient webClient;
    public static String ACCESS_TOKEN;
    public static long last_auth_time = 0;

    public AccessTokenManager(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(KisConfig.REST_BASE_URL).build();

    }

    public String getAccessToken() {
        if (ACCESS_TOKEN == null) {
            ACCESS_TOKEN = generateAccessToken();
            System.out.println("generate ACCESS_TOKEN: " + ACCESS_TOKEN);
        }
        return ACCESS_TOKEN;
    }

    public String generateAccessToken() {
        String url = KisConfig.REST_BASE_URL + "/oauth2/tokenP";
        OauthInfo bodyOauthInfo = new OauthInfo();
        bodyOauthInfo.setGrant_type("client_credentials");
        bodyOauthInfo.setAppkey(KisConfig.APPKEY);
        bodyOauthInfo.setAppsecret(KisConfig.APPSECRET);

        Mono<TokenInfo> mono = webClient.post()
                .uri(url)
                .header("content-type", "application/json")
                .bodyValue(bodyOauthInfo)
                .retrieve()
                .bodyToMono(TokenInfo.class);

        TokenInfo tokenInfo = mono.block();
        if (tokenInfo == null) {
            throw new RuntimeException("トークンを呼び出せません");
        }

        ACCESS_TOKEN = tokenInfo.getAccess_token();
        System.out.println("＊＊＊＊＊＊＊トークン取得＊＊＊＊＊＊＊");
        return ACCESS_TOKEN;
    }

}
