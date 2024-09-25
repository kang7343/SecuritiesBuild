package com.kis.model;

import lombok.Data;

@Data
public class IndexData {
    private String rt_cd;
    private String msg_cd;
    private String msg1;
    private Object output1;
    private Object[] output2;

    private String name; // 指数名呼び出し

}
