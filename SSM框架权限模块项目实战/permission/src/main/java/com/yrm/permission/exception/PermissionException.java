package com.yrm.permission.exception;

import com.yrm.permission.enmus.ErrorCodeEnum;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className PermissionException
 * @createTime 2019年03月22日 09:23:00
 */
public class PermissionException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public PermissionException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public PermissionException(ErrorCodeEnum errorCodeEnum) {
        super();
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getErrorMessage();
    }

    public PermissionException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }


    public String getErrorcode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
