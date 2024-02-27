package com.ej.hgj.base;

import lombok.Data;

@Data
public class BaseRespVo {

    /**
     * 系统响应码
     */
    private String respCode;
    /**
     * 业务返回码
     */
    private String errCode;
    /**
     * 业务返回描述
     */
    private String errDesc;

    private Object data;

}
