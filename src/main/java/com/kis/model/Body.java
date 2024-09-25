package com.kis.model;

import lombok.Data;

@Data
public class Body {
    private String rt_cd; // 成功か失敗
    private String msg_cd; // レスポンスコード
    private String msg1; // レスポンスメッセージ
    private Object output; // レスポンスの詳細

}
