package com.yrm.so2o.enums;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ErrorCodeEnum
 * @createTime 2019年05月22日 14:42:00
 */
public enum ErrorCodeEnum {
    /**错误状态码*/
    SUCCESS(0, "success"),
    ILLEGAL_PARAM(1, "param is illegal");
    private int code;
    private String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
