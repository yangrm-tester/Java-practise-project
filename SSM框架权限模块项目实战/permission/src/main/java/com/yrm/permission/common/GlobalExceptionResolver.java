package com.yrm.permission.common;

import com.yrm.permission.exception.PermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className GlobalExceptionResolve
 * @createTime 2019年03月22日 09:36:00
 * @desc springmvc  处理全局异常
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
    /**api请求结尾后缀*/
    private static final String API_REQUEST_URL_SUFFIX = ".json";
    /**页面请求结尾后缀*/
    private static final String PAGE_REQUEST_URL_SUFFIX = ".page";
    /**处理json视图名字，与spring-servlet配置文件中一致*/
    public static final String JSON_VIEW="jsonView";


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //这里定义接口请求地址 url是以.json 结尾 页面发起的请求是以.page结尾
        ModelAndView modelAndView;
        String defaultMessage = "System error";
        String requestUrl = httpServletRequest.getRequestURL().toString();
        if (requestUrl.endsWith(API_REQUEST_URL_SUFFIX)){
            if (e instanceof PermissionException){
                PermissionException pe = (PermissionException)e;
                ApiResult apiResult = ApiResult.failResult(pe.getErrorMessage());
                modelAndView =  new ModelAndView(JSON_VIEW,apiResult.toMap());
                return modelAndView;
            }else {
                logger.error("unknown request url,url===>{},e===>{}",requestUrl,e);
                ApiResult apiResult = ApiResult.failResult(defaultMessage);
                modelAndView =  new ModelAndView(JSON_VIEW,apiResult.toMap());
                return modelAndView;
            }
        }else if (requestUrl.endsWith(PAGE_REQUEST_URL_SUFFIX)){
            logger.error("unknown request url,url===>{},e===>{}",requestUrl,e);
            ApiResult apiResult = ApiResult.failResult(defaultMessage);
            modelAndView =  new ModelAndView(JSON_VIEW,apiResult.toMap());
            return modelAndView;
        }else{
            logger.error("unknown request url,url===>{},e===>{}",requestUrl,e);
            ApiResult apiResult = ApiResult.failResult(defaultMessage);
            modelAndView =  new ModelAndView(JSON_VIEW,apiResult.toMap());
            return modelAndView;
        }
    }

    public static void main(String[] args) {
        String url = "http://localhost:9191/sys/dept/save.json";
        System.out.println(url.endsWith(".json"));
    }
}
