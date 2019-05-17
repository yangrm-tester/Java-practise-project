package com.yrm.permission.controller;

import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.common.ApiResult;
import com.yrm.permission.dto.AclListAndRoleListDTO;
import com.yrm.permission.dto.SysUserRequestDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysUserService;
import com.yrm.permission.util.JsonUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysUserController
 * @createTime 2019年04月03日 14:39:00
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/noAuth.page")
    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }


    @RequestMapping("/save.json")
    @ResponseBody
    public ApiResult save(SysUserRequestDTO sysUserRequestDTO) {
        logger.info("Method [save] call start. Input params====>[sysUserRequestDTO={}]", JsonUtil.obj2String(sysUserRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (!SysUserRequestDTO.checkParam(sysUserRequestDTO)) {
            apiResult.setCode(ErrorCodeEnum.ILLGEAL_PARMS.getCode());
            apiResult.setMsg(ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
            return apiResult;
        }
        try {
            sysUserService.saveSysUser(sysUserRequestDTO);
        } catch (Exception e) {
            if (e instanceof PermissionException) {
                PermissionException pe = (PermissionException) e;
                logger.error("Method [save] call error .Error message===>{}", e.getMessage());
                apiResult.setCode(pe.getErrorcode());
                apiResult.setMsg(pe.getErrorMessage());
                return apiResult;
            }
            logger.error("Method [save] call error .Error message===>{}", e.getMessage());
            apiResult.setCode(ErrorCodeEnum.USER_SAVE_ERROR.getCode());
            apiResult.setMsg(ErrorCodeEnum.USER_SAVE_ERROR.getErrorMessage());
            return apiResult;
        }
        apiResult.setCode(ErrorCodeEnum.SUCCESS.getCode());
        apiResult.setMsg(ErrorCodeEnum.SUCCESS.getErrorMessage());
        logger.info("Method [save] call end. Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public ApiResult updateSysUser(SysUserRequestDTO sysUserRequestDTO) {
        logger.info("Method [update] call start. Input params===>[sysUserRequestDTO={}]", JsonUtil.obj2String(sysUserRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (!SysUserRequestDTO.checkParam(sysUserRequestDTO)) {
            apiResult.setCode(ErrorCodeEnum.ILLGEAL_PARMS.getCode());
            apiResult.setMsg(ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
            return apiResult;
        }
        try {
            sysUserService.updateSysUser(sysUserRequestDTO);
        } catch (Exception e) {
            if (e instanceof PermissionException) {
                logger.error("Method [update] call error .Error message===>{}", e.getMessage());
                PermissionException pe = (PermissionException) e;
                apiResult.setCode(pe.getErrorcode());
                apiResult.setMsg(pe.getErrorMessage());
                return apiResult;
            }
            logger.error("Method [update] call error .Error message===>{}", e.getMessage());
            apiResult.setCode(ErrorCodeEnum.USER_UPDATE_ERROR.getCode());
            apiResult.setMsg(ErrorCodeEnum.USER_UPDATE_ERROR.getErrorMessage());
            return apiResult;
        }
        apiResult.setCode(ErrorCodeEnum.SUCCESS.getCode());
        apiResult.setMsg(ErrorCodeEnum.SUCCESS.getErrorMessage());
        return apiResult;
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public ApiResult page(@RequestParam("deptId") Integer deptId, PageQuery pageQuery) {
        logger.info("Method [page] call start. Input parmas===>[deptId={},pageQuery={}]", deptId, JsonUtil.obj2String(pageQuery));
        ApiResult apiResult = new ApiResult();
        PageResult pageResult;
        if (deptId == null || pageQuery == null) {
            apiResult.setCode(ErrorCodeEnum.ILLGEAL_PARMS.getCode());
            apiResult.setMsg(ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
            return apiResult;
        }
        try {
            pageResult = sysUserService.getPageData(deptId, pageQuery);
        } catch (Exception e) {
            if (e instanceof PermissionException) {
                logger.error("Method [page] call error .Error message===>{}", e.getMessage());
                PermissionException pe = (PermissionException) e;
                apiResult.setCode(pe.getErrorcode());
                apiResult.setCode(pe.getErrorMessage());
                return apiResult;
            }
            logger.error("Method [page] call error .Error message===>{}", e.getMessage());
            apiResult.setCode(ErrorCodeEnum.PAGE_DATA_ERROR.getCode());
            apiResult.setCode(ErrorCodeEnum.PAGE_DATA_ERROR.getErrorMessage());
            return apiResult;
        }
        apiResult.setCode(ErrorCodeEnum.SUCCESS.getCode());
        apiResult.setMsg(ErrorCodeEnum.SUCCESS.getErrorMessage());
        apiResult.setData(pageResult);
        logger.info("Method [page] call end .Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }

    @RequestMapping("/userAcl")
    @ResponseBody
    public ApiResult getAclListAndRoleList(@Param("userId") Integer userId) {
        logger.info("Method [getAclListAndRoleList] call start.Input params===>[userId={}]", userId);
        ApiResult apiResult = new ApiResult();
        AclListAndRoleListDTO aclListAndRoleListDTO = new AclListAndRoleListDTO();
        if (userId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            aclListAndRoleListDTO = sysUserService.getAclListAndRoleList(userId);
        } catch (Exception e) {
            logger.error("Method [getAclListAndRoleList] call error .Errors===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.GET_ACLS_ROLES_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        apiResult.setData(aclListAndRoleListDTO);
        logger.info("Method [getAclListAndRoleList] call end.Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }
}
