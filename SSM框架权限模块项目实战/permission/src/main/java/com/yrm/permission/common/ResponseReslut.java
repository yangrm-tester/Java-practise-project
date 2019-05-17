package com.yrm.permission.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ResponseReslut
 * @createTime 2019年03月21日 18:10:00
 */
public class ResponseReslut<T> {
    public static final Logger logger = LoggerFactory.getLogger(ResponseReslut.class);

    public static final int OK_CODE = 0;
    public static final String OK_MSG = "success";

    private int code;
    private String msg;
    private T data;

    public ResponseReslut() {
    }

    public ResponseReslut(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseReslut okResult(T data){
        ResponseReslut responseReslut = new ResponseReslut(OK_CODE,OK_MSG,data);
        return responseReslut;
    }




    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseReslut{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
