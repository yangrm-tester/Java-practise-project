package com.yrm.permission.controller;

import com.yrm.permission.common.ApiResult;
import com.yrm.permission.exception.PermissionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className TestController
 * @createTime 2019年03月20日 16:24:00
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello.json")
    @ResponseBody
    public ApiResult hello(){
       // throw new RuntimeException("error");
        return ApiResult.okResult("request success1111");
    }
    @RequestMapping("/error.json")
    @ResponseBody
    public ApiResult error(){
         throw new RuntimeException("error");
        //return ApiResult.okResult("request success");
    }
    @RequestMapping("/error1.json")
    @ResponseBody
    public ApiResult error1(){
        throw new PermissionException("error");
        //return ApiResult.okResult("request success");
    }
}
