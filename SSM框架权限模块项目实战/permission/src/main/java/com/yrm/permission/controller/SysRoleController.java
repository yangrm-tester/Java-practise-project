package com.yrm.permission.controller;

import com.yrm.permission.common.ApiResult;
import com.yrm.permission.dto.SysAclModuleLevelDTO;
import com.yrm.permission.dto.SysRoleRequestDTO;
import com.yrm.permission.dto.SysUserResponseDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.service.SysRoleService;
import com.yrm.permission.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysRoleController
 * @createTime 2019年05月03日 16:45:00
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    private static final String PAGE_VIEW = "role";

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/role.page")
    public ModelAndView role() {
        return new ModelAndView(PAGE_VIEW);
    }


    @RequestMapping("/save.json")
    @ResponseBody
    public ApiResult saveSysRole(SysRoleRequestDTO sysRoleRequestDTO, HttpServletRequest request) {
        logger.info("Method [saveSysRole] call start. Input params===>{sysRoleRequestDTO=[]}", JsonUtil.obj2String(sysRoleRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (!SysRoleRequestDTO.checkParam(sysRoleRequestDTO)) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysRoleService.save(sysRoleRequestDTO, request);
        } catch (Exception e) {
            logger.info("Method [saveSysRole] call error. Error===>{[]}", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ROLE_SAVE_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        return apiResult;
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public ApiResult updateSysRole(SysRoleRequestDTO sysRoleRequestDTO, HttpServletRequest request) {
        logger.info("Method [updateSysRole] call start.Input params===>[sysRoleRequestDTO={}]", JsonUtil.obj2String(sysRoleRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (!SysRoleRequestDTO.checkParam(sysRoleRequestDTO)) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysRoleService.update(sysRoleRequestDTO, request);
        } catch (Exception e) {
            logger.error("Method [updateSysRole] call error .Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ROLE_UPDATE_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        logger.info("Method [updateSysRole] call end.Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }


    @RequestMapping("/list.json")
    @ResponseBody
    public ApiResult list() {
        logger.info("Method [list] call start. Input params===>[{}]");
        ApiResult apiResult = new ApiResult();
        List<Role> roleList = new ArrayList<Role>();
        try {
            roleList = sysRoleService.getRoleList();
        } catch (Exception e) {
            logger.error("Method [list] call error. Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ROLE_GET_LIST_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        apiResult.setData(roleList);
        logger.info("Method [list] call end .Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ApiResult delete(@RequestParam("id") Integer id) {
        logger.info("Method [delete] call start.Input params===>[id={}]", id);
        ApiResult apiResult = new ApiResult();
        if (id == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysRoleService.delete(id);
        } catch (Exception e) {
            logger.error("Method [delete] call error.Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ROLE_DELETE_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        logger.info("Method [delete] call end .Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }

    @RequestMapping("/roleAclTree.json")
    @ResponseBody
    public ApiResult roleAclToTree(@RequestParam("roleId") Integer roleId) {
        logger.info("Method [roleAclToTree] call start .Input params===>[roleId={}]", roleId);
        ApiResult apiResult = new ApiResult();
        List<SysAclModuleLevelDTO> roleAclTree = null;
        if (roleId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            roleAclTree = sysRoleService.getRoleAclTree(roleId);
        } catch (Exception e) {
            logger.error("Method [roleAclToTree] call error.Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.GET_ACL_TREE_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        apiResult.setData(roleAclTree);
        logger.info("Method [roleAclToTree] call end .Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }

    @RequestMapping("/saveRoleAcl.json")
    @ResponseBody
    public ApiResult saveRoleAcl(@RequestParam("roleId") Integer roleId, @RequestParam("aclIds") String aclIds) {
        logger.info("Method [saveRoleAcl] call start.Input params===>[roleId={},aclIds={}]", roleId, aclIds);
        ApiResult apiResult = new ApiResult();
        if (roleId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysRoleService.saveRoleAcl(roleId, aclIds);
        } catch (Exception e) {
            logger.error("Method [saveRoleAcl] call error.Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.SAVE_ROLE_ACL_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        logger.info("Method [saveRoleAcl] call end .Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }

    @RequestMapping("/getUserList.json")
    @ResponseBody
    public ApiResult getUserList(@RequestParam("roleId") Integer roleId) {
        logger.info("Method [getUserList] call start.Input params===>[roleId={}]", roleId);
        ApiResult apiResult = new ApiResult();
        SysUserResponseDTO sysUserResponseDTO = null;
        if (roleId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysUserResponseDTO = sysRoleService.getUserList(roleId);
        } catch (Exception e) {
            logger.error("Method [getUserList] call error.Errors===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.GET_ROLE_USER_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        apiResult.setData(sysUserResponseDTO);
        return apiResult;
    }

    @RequestMapping("/saveRoleUser.json")
    @ResponseBody
    public ApiResult saveRoleUser(@RequestParam("roleId") Integer roleId, @RequestParam("userIds") String userIds) {
        logger.info("Method [saveRoleAcl] call start.Input params===>[roleId={},userIds={}]", roleId, userIds);
        ApiResult apiResult = new ApiResult();
        if (roleId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysRoleService.saveRoleUser(roleId, userIds);
        } catch (Exception e) {
            logger.error("Method [saveRoleAcl] call error.Error===>[{}]", e.getMessage());
            apiResult.setErrorCodeEnum(ErrorCodeEnum.SAVE_ROLE_USER_ERROR);
            return apiResult;
        }
        apiResult.setErrorCodeEnum(ErrorCodeEnum.SUCCESS);
        return apiResult;
    }
}
