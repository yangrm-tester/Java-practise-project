package com.yrm.permission.common;

import com.yrm.permission.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className HttpIntercepter
 * @createTime 2019年03月22日 16:03:00
 */

public class HttpIntercepter implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpIntercepter.class);

    private static final String RQUEST_START_TIME="startTime";



    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestUrl = httpServletRequest.getRequestURI();
        Map mapResult = httpServletRequest.getParameterMap();
        logger.info("request start,requestUrl===>{},requestParms===>{}",requestUrl, JsonUtil.obj2String(mapResult));
        /**
         * 统计请求开始时间
         * */
        Long requestStartTime = System.currentTimeMillis();
        httpServletRequest.setAttribute(RQUEST_START_TIME,requestStartTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String requestUrl = httpServletRequest.getRequestURI();
        Map mapResult = httpServletRequest.getParameterMap();
        Long requestStartTime = (Long) httpServletRequest.getAttribute(RQUEST_START_TIME);
        Long requestEndTime = System.currentTimeMillis();
        Long cost = requestEndTime-requestStartTime;
        logger.info("request finish,requestUrl===>{},requestParms===>{},costTime===>{}",requestUrl, JsonUtil.obj2String(mapResult),cost);
        removeThreadLocalInfo();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        String requestUrl = httpServletRequest.getRequestURI();
        Map mapResult = httpServletRequest.getParameterMap();
        logger.info("request finish,requestUrl===>{},requestParms===>{}",requestUrl, JsonUtil.obj2String(mapResult));
        //移除防止threadlocal内存泄漏
        removeThreadLocalInfo();
    }

    public void removeThreadLocalInfo() {
        RequestHolder.remove();
    }
}
