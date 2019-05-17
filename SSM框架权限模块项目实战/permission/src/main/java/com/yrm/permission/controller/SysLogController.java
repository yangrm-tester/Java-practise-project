package com.yrm.permission.controller;

import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.common.ApiResult;
import com.yrm.permission.dto.SysLogRequestDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.entity.SysLogWithBLOBs;
import com.yrm.permission.service.SysLogService;
import com.yrm.permission.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysLogController
 * @createTime 2019年05月15日 16:28:00
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    private static final Logger logger = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    private SysLogService sysLogService;


    @RequestMapping("/log.page")
    public ModelAndView showLogPage() {
        return new ModelAndView("log");
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public ApiResult getPageData(SysLogRequestDTO sysLogRequestDTO, PageQuery pageQuery) {
        logger.info("Method [getPageData] call start.Input params===>[sysLogRequestDTO={},pageQuery={}]", JsonUtil.obj2String(sysLogRequestDTO), JsonUtil.obj2String(pageQuery));
        ApiResult apiResult = new ApiResult();
        PageResult<SysLogWithBLOBs> pageResult;
        if (sysLogRequestDTO == null && pageQuery == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            pageResult = sysLogService.getPageData(sysLogRequestDTO, pageQuery);
        } catch (Exception e) {
            logger.error("Method [getPageData] call error.Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.GET_SYSLOG_PAGE_DATA_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        apiResult.setData(pageResult);
        logger.info("Method [getPageData] call end.Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }
}
