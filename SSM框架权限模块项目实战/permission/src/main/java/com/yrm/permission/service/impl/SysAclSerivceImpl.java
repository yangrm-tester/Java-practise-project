package com.yrm.permission.service.impl;

import com.yrm.permission.Constant.Constant;
import com.yrm.permission.common.RequestHolder;
import com.yrm.permission.dao.*;
import com.yrm.permission.dto.SysAclRequestDTO;
import com.yrm.permission.dto.UserListAndRoleListDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.entity.SysAcl;
import com.yrm.permission.entity.SysRole;
import com.yrm.permission.entity.SysUser;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysAclService;
import com.yrm.permission.service.SysLogService;
import com.yrm.permission.util.IpUtil;
import com.yrm.permission.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclSerivceImpl
 * @createTime 2019年04月24日 11:23:00
 */
@Service
public class SysAclSerivceImpl implements SysAclService {
    private static final Logger logger = LoggerFactory.getLogger(SysAclSerivceImpl.class);

    private static final String CODE_PATTERN = "yyyyMMddHHmmss";

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void saveSysAcl(SysAclRequestDTO sysAclRequestDTO) throws Exception {
        logger.info("Method [saveSysAcl] call start. Input params===>[sysAclRequestDTO={}]", JsonUtil.obj2String(sysAclRequestDTO));
        if (SysAclRequestDTO.checkParam(sysAclRequestDTO)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //校验数据重复性 id name 和acl_module_id
        SysAcl repeatSysAcl = sysAclMapper.getRepeatSysAcl(sysAclRequestDTO);
        if (repeatSysAcl != null) {
            throw new PermissionException(ErrorCodeEnum.SAME_ACL_IN_SAME_ACLMODULE_ERROR);
        }
        //封装数据SysAcl数据
        SysAcl sysAcl = SysAcl.packageSysAcl(sysAclRequestDTO);
        //封装剩余数据
        sysAcl.setOperator(RequestHolder.getSysUser().getUsername());
        sysAcl.setOperateIp(IpUtil.getIpAttr(RequestHolder.getHttpServletRequest()));
        sysAcl.setOperateTime(new Date());
        sysAcl.setCode(generateCode());
        int insertSysAclCount = sysAclMapper.insertSelective(sysAcl);
        sysLogService.saveAclLog(null, sysAcl);
        logger.info("Method [saveSysAcl] call end .Input params===>[sysAcl={},insertSysAclCount={}]", JsonUtil.obj2String(sysAcl), insertSysAclCount);
        if (insertSysAclCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.ACL_DB_INSERT_ERROR);
        }
    }

    @Override
    public void updateSysAcl(SysAclRequestDTO sysAclRequestDTO) throws Exception {
        logger.info("Method [updateSysAcl] call start . Input params===>[sysAclRequestDTO={}]", JsonUtil.obj2String(sysAclRequestDTO));
        if (SysAclRequestDTO.checkParam(sysAclRequestDTO)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //校验更新数据是否存在
        SysAcl existSysAcl = sysAclMapper.selectByPrimaryKey(sysAclRequestDTO.getId());
        if (existSysAcl == null) {
            throw new PermissionException(ErrorCodeEnum.NEED_UPDATE_ACL_NOT_EXIST);
        }
        //封装数据
        SysAcl updateSysAcl = SysAcl.packageSysAcl(sysAclRequestDTO);
        updateSysAcl.setOperator(RequestHolder.getSysUser().getUsername());
        updateSysAcl.setOperateIp(Constant.OPERATOR_IP);
        updateSysAcl.setOperateTime(new Date());
        int updateSysAclCount = sysAclMapper.updateByPrimaryKeySelective(updateSysAcl);
        sysLogService.saveAclLog(existSysAcl, updateSysAcl);
        logger.info("Method [updateSysAcl] call end .Input params===>[updateSysAcl={},updateSysAclCount={}]", JsonUtil.obj2String(updateSysAcl), updateSysAclCount);
        if (updateSysAclCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.ACL_DB_UPDATE_ERROR);
        }
    }

    @Override
    public void deleteSysAcl(Integer aclId) throws Exception {
        logger.info("Method [deleteSysAcl] call start .Input params===>[aclId={}]", aclId);
        if (aclId != null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //校验数据数据存在性
        SysAcl existSysAcl = sysAclMapper.selectByPrimaryKey(aclId);
        if (existSysAcl == null) {
            throw new PermissionException(ErrorCodeEnum.NEED_DELET_ACL_NOT_EXIST);
        }
        int deleteSysAclCount = sysAclMapper.deleteByPrimaryKey(aclId);
        sysLogService.saveAclLog(existSysAcl, null);
        logger.info("Method [deleteSysAcl] call end .Results===>[aclId={},deleteSysAclCount={}]", aclId, deleteSysAclCount);
        if (deleteSysAclCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.ACL_DB_DELETE_ERROR);
        }
    }

    @Override
    public UserListAndRoleListDTO getUserListAndRoleList(Integer aclId) throws Exception {
        logger.info("Method [getUserListAndRoleList] call start.Input params===>[aclId={}]", aclId);
        if (aclId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        UserListAndRoleListDTO userListAndRoleListDTO = new UserListAndRoleListDTO();
        //获取roleList
        List<SysRole> sysRoleList = getRoleListByAclId(aclId);
        //获取userList
        List<SysUser> sysUserList = getUserListByAclId(aclId);
        userListAndRoleListDTO.setRoleList(sysRoleList);
        userListAndRoleListDTO.setSysUserList(sysUserList);
        return userListAndRoleListDTO;
    }

    private List<SysUser> getUserListByAclId(Integer aclId) {
        //aclId 拿到 roleId
        List<Integer> roleIdList = sysRoleAclMapper.getRoleListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Collections.EMPTY_LIST;
        }
        //roleId 拿到userId
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Collections.EMPTY_LIST;
        }
        //userId 拿到SysUser
        List<SysUser> userListByUserIds = sysUserMapper.getUserListByUserIds(userIdList);
        return CollectionUtils.isNotEmpty(userListByUserIds) ? userListByUserIds : Collections.EMPTY_LIST;
    }

    private List<SysRole> getRoleListByAclId(Integer aclId) {
        //aclId 拿到 roleId
        List<Integer> roleIdList = sysRoleAclMapper.getRoleListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Collections.EMPTY_LIST;
        }
        //roleId 拿到SysRole
        List<SysRole> sysRoleListByIdList = sysRoleMapper.getSysRoleListByIdList(roleIdList);
        if (CollectionUtils.isEmpty(sysRoleListByIdList)) {
            return Collections.EMPTY_LIST;
        }
        return sysRoleListByIdList;
    }

    private static String generateCode() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CODE_PATTERN);
        String codeStr = simpleDateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
        return codeStr;
    }

}
