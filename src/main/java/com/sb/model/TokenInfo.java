package com.sb.model;

import lombok.Data;

@Data
public class TokenInfo {
    private String access_token;
    private String token_type;
    private long expires_in;

}
