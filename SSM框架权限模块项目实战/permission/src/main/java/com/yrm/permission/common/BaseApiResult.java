package com.yrm.permission.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className BaseApiResult
 * @createTime 2019年03月21日 17:18:00
 */
public abstract class BaseApiResult<T, S extends BaseApiResult<T, S>> {

    protected final static String OK_CODE = "0";
    protected final static String OK_MSG="success";

    private static final Logger logger = LoggerFactory.getLogger(BaseApiResult.class);
    protected String code;
    protected String msg;
    protected T data;

    public BaseApiResult() {

    }

    /**
     *  实例化BaseApiResult的子类
     *  @param subClass BaseApiResult子类
     *  @Param: [subClass]
     *  @return: C
     *  @Description:
     *
     */
    public static <T,C extends BaseApiResult<T,C>> C create(Class<C> subClass) {
        C c = null;
        try {
            c = subClass.newInstance();
        } catch (Exception e) {
            logger.info("error when initiate subclass of BaseApiResult,e===>{}", e);
        }
        return c;
    }
    /**
     *  请求成功返回的结果
     *  @Param: [subClass, data]
     *  @return: C
     *  @Description:
     *
     */

    public static <T,C extends BaseApiResult<T,C>> C okResult(Class<C> subClass,T data){
        C c = create(subClass);
        c.setCode(OK_CODE);
        c.setData(data);
        c.setMsg(OK_MSG);
        return c;
    }

    public static <T,C extends BaseApiResult<T,C>> C failResult(Class<C> subClass,T data,String msg,String code){
        C c = create(subClass);
        c.setMsg(msg);
        c.setData(data);
        c.setCode(code);
        return c;
    }

    public static <T,C extends BaseApiResult<T,C>> C failResult(Class<C> subClass,String msg){
        C c = create(subClass);
        c.setMsg(msg);
        return c;
    }

    public  Map<String,Object> toMap(){
        Map<String,Object> resultMap = new HashMap<String,Object>(3);
        resultMap.put("code",code);
        resultMap.put("data",data);
        resultMap.put("msg",msg);
        return resultMap;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
}
