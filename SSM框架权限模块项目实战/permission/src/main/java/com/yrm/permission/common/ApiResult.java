package com.yrm.permission.common;

import com.yrm.permission.enmus.ErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ApiResult
 * @createTime 2019年03月21日 16:27:00
 */
public class ApiResult extends BaseApiResult<Object, ApiResult> {
    private static final Logger logger = LoggerFactory.getLogger(ApiResult.class);

    public ApiResult() {
    }

    public ApiResult(String code, String msg, Object data) {
        setCode(code);
        setData(data);
        setMsg(msg);
    }

    public void setErrorCodeEnum(ErrorCodeEnum errorCodeEnum) {
        setCode(errorCodeEnum.getCode());
        setMsg(errorCodeEnum.getErrorMessage());
    }

    /**
     * 请求成功返回结果集
     */
    public static ApiResult okResult(Object data) {
        return okResult(ApiResult.class, data);
    }

    /**
     * 请求失败返回结果集
     */
    public static ApiResult failResult(String code, String msg, Object data) {
        return failResult(ApiResult.class, data, msg, code);
    }

    /**
     * 请求失败返回结果集
     */
    public static ApiResult failResult(String code, String msg) {
        return failResult(ApiResult.class, null, msg, code);
    }

    /**
     * 请求失败返回结果集
     */
    public static ApiResult failResult(String msg) {
        return failResult(ApiResult.class, msg);
    }


}
