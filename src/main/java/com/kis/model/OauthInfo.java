package com.kis.model;

import lombok.Data;

@Data
public class OauthInfo {
    private String grant_type;
    private String appkey;
    private String appsecret;
    private String custtype;

}
