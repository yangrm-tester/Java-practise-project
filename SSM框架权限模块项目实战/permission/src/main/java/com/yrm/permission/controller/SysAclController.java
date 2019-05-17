package com.yrm.permission.controller;

import com.yrm.permission.common.ApiResult;
import com.yrm.permission.dto.SysAclRequestDTO;
import com.yrm.permission.dto.UserListAndRoleListDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysAclService;
import com.yrm.permission.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclController
 * @createTime 2019年04月24日 11:22:00
 */
@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

    private static final Logger logger = LoggerFactory.getLogger(SysAclController.class);

    @Autowired
    private SysAclService sysAclService;

    /**
     * 权限保存
     *
     * @Param: [sysAclRequestDTO]
     * @return: com.yrm.permission.common.ApiResult
     */

    @RequestMapping("/save.json")
    @ResponseBody
    public ApiResult saveSysAcl(SysAclRequestDTO sysAclRequestDTO) {
        logger.info("Method [saveSysAcl] call start. Input params====>[sysAclRequestDTO={}]", JsonUtil.obj2String(sysAclRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (SysAclRequestDTO.checkParam(sysAclRequestDTO)) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysAclService.saveSysAcl(sysAclRequestDTO);
        } catch (PermissionException pe) {
            logger.error("Method [saveSysAcl] call error. error===>[{}] ", JsonUtil.obj2String(pe));
            apiResult.setMsg(pe.getErrorMessage());
            apiResult.setCode(pe.getErrorcode());
            return apiResult;
        } catch (Exception e) {
            logger.error("Method [saveSysAcl] call error. error===>[{}] ", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ACL_SAVE_ERROR);
            return apiResult;
        }
        logger.info("Method [saveSysAcl] call end .Results===>[]");
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        return apiResult;
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public ApiResult updateSysAcl(SysAclRequestDTO sysAclRequestDTO) {
        logger.info("Method [updateSysAcl] call start.Input params===>[sysAclRequestDTO={}]", JsonUtil.obj2String(sysAclRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (SysAclRequestDTO.checkParam(sysAclRequestDTO)) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysAclService.updateSysAcl(sysAclRequestDTO);
        } catch (PermissionException pe) {
            logger.error("Method [updateSysAcl] call error.Error===>[{}]", JsonUtil.obj2String(pe));
            apiResult.setCode(pe.getErrorcode());
            apiResult.setMsg(pe.getErrorMessage());
            return apiResult;
        } catch (Exception e) {
            logger.error("Method [updateSysAcl] call error.Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ACL_UPDATE_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        return apiResult;
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ApiResult deleteSysAcl(Integer aclId) {
        logger.info("Method [deleteSysAcl] call start .Input params===>[aclId={}]", aclId);
        ApiResult apiResult = new ApiResult();
        if (aclId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysAclService.deleteSysAcl(aclId);
        } catch (Exception e) {
            logger.error("Method [deleteSysAcl] call error .Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ACL_DELETE_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        return apiResult;
    }

    @RequestMapping("/aclRole.json")
    @ResponseBody
    public ApiResult getAclRole(@RequestParam("aclId") Integer aclId) {
        logger.info("Method [getAclRole] call start .Input params===>[aclId={}]", aclId);
        ApiResult apiResult = new ApiResult();
        UserListAndRoleListDTO userListAndRoleListDTO;
        if (aclId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            userListAndRoleListDTO = sysAclService.getUserListAndRoleList(aclId);
        } catch (Exception e) {
            logger.error("Method [deleteSysAcl] call error .Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.GET_USERS_ROLES_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        //设置data
        apiResult.setData(userListAndRoleListDTO);
        return apiResult;
    }
}
