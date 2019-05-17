package com.yrm.permission.controller;

import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.common.ApiResult;
import com.yrm.permission.dto.SysAclModuleLevelDTO;
import com.yrm.permission.dto.SysAclModuleRequestDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.entity.SysAcl;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysAclModuleService;
import com.yrm.permission.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclModuleController
 * @createTime 2019年04月09日 17:01:00
 */

@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    private static final Logger logger = LoggerFactory.getLogger(SysAclModuleController.class);

    @Autowired
    private SysAclModuleService sysAclModuleService;

    @RequestMapping("/acl.page")
    public ModelAndView acl() {
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public ApiResult save(SysAclModuleRequestDTO sysAclModuleRequestDTO) {
        logger.info("Method [save] call start. Input params===>[sysAclModuleRequestDTO={}]", JsonUtil.obj2String(sysAclModuleRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (sysAclModuleRequestDTO == null || sysAclModuleRequestDTO.getName() == null || sysAclModuleRequestDTO.getSeq() == null || sysAclModuleRequestDTO.getRemark() == null) {
            apiResult.setCode(ErrorCodeEnum.ILLGEAL_PARMS.getCode());
            apiResult.setMsg(ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
            return apiResult;
        }
        try {
            sysAclModuleService.saveSysAclModule(sysAclModuleRequestDTO);
        } catch (Exception e) {
            logger.error("Method [save] call error. Error message===>[{}]", e.getMessage());
            if (e instanceof PermissionException) {
                PermissionException pe = (PermissionException) e;
                apiResult.setCode(pe.getErrorcode());
                apiResult.setMsg(pe.getErrorMessage());
                return apiResult;
            }
            apiResult.setCode(ErrorCodeEnum.ACL_MODULE_SAVE_ERROR.getCode());
            apiResult.setMsg(ErrorCodeEnum.ACL_MODULE_SAVE_ERROR.getErrorMessage());
            return apiResult;
        }
        apiResult.setCode(ErrorCodeEnum.SUCCESS.getCode());
        apiResult.setMsg(ErrorCodeEnum.SUCCESS.getErrorMessage());
        logger.info("Method [save] call end. Results===>[{}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }


    @RequestMapping("/update.json")
    @ResponseBody
    public ApiResult updateSysAclModule(SysAclModuleRequestDTO sysAclModuleRequestDTO) {
        logger.info("Method [updateSysAclModule] call start. Input params===[sysAclModuleRequestDTO={}]", JsonUtil.obj2String(sysAclModuleRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (sysAclModuleRequestDTO == null || sysAclModuleRequestDTO.getId() == null || sysAclModuleRequestDTO.getName() == null || sysAclModuleRequestDTO.getParentId() == null || sysAclModuleRequestDTO.getRemark() == null || sysAclModuleRequestDTO.getSeq() == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysAclModuleService.updateSysAclModule(sysAclModuleRequestDTO);
        } catch (Exception e) {
            logger.error("Method [updateSysAclModule] call error.Error message===>[{}]", e.getMessage());
            if (e instanceof PermissionException) {
                PermissionException pe = (PermissionException) e;
                apiResult.setCode(pe.getErrorcode());
                apiResult.setMsg(pe.getErrorMessage());
                return apiResult;
            }
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ACL_MODULE_UPDATE_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        logger.info("Method [save] call end. Results===>[{}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }

    @RequestMapping("tree.json")
    @ResponseBody
    public ApiResult sysAclModuleTree() {
        logger.info("Method [sysAclModuleTree] call start.Input params===>[{}]");
        ApiResult apiResult = new ApiResult();
        List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList;
        try {
            sysAclModuleLevelDTOList = sysAclModuleService.sysAclModuleTree();
        } catch (Exception e) {
            logger.error("Method [sysAclModuleTree] call error.Error message===>[{}]", e.getMessage());
            if (e instanceof PermissionException) {
                PermissionException pe = (PermissionException) e;
                apiResult.setCode(pe.getErrorcode());
                apiResult.setMsg(pe.getErrorMessage());
                return apiResult;
            }
            apiResult.setErrorCodeEnum(ErrorCodeEnum.GET_ACL_MODULE_TREE_LIST_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        apiResult.setData(sysAclModuleLevelDTOList);
        return apiResult;
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public ApiResult pageQuery(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        logger.info("Method [pageQuery] call start .Input params===>[aclModuleId={},pageQuery={}]", aclModuleId, JsonUtil.obj2String(pageQuery));
        ApiResult apiResult = new ApiResult();
        PageResult<SysAcl> pageResult;
        if (aclModuleId == null || pageQuery == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            pageResult = sysAclModuleService.getPageData(aclModuleId, pageQuery);
        } catch (Exception e) {
            logger.error("Method [pageQuery] call error. Errors===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ACL_PAGE_DATA_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        apiResult.setData(pageResult);
        logger.info("Method [pageQuery] call end .Results===>[pageResult={}]", JsonUtil.obj2String(pageResult));
        return apiResult;
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ApiResult deleteAclModule(@RequestParam("aclModuleId") Integer aclModuleId) {
        logger.info("Method [deleteAclModule] call start .Input params===>[aclModuleId={}]", aclModuleId);
        ApiResult apiResult = new ApiResult();
        if (aclModuleId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysAclModuleService.deleteAclModule(aclModuleId);
        } catch (Exception e) {
            logger.error("Method [deleteAclModule] call error .Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ACL_MODULE_DELETE_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        logger.info("Method [deleteAclModule] call end .Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }
}

