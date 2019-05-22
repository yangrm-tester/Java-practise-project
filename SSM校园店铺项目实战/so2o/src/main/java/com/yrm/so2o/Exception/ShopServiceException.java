package com.yrm.so2o.Exception;

import com.yrm.so2o.enums.ErrorCodeEnum;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ShopServiceException
 * @createTime 2019年05月22日 14:39:00
 */
public class ShopServiceException extends RuntimeException {
    private int code;
    private String msg;

    public ShopServiceException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ShopServiceException(ErrorCodeEnum errorCodeEnum) {
        super();
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
