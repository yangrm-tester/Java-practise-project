package com.yrm.permission.service;

import com.yrm.permission.dto.SysAclRequestDTO;
import com.yrm.permission.dto.UserListAndRoleListDTO;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclService
 * @createTime 2019年04月24日 11:23:00
 */
public interface SysAclService {

    /**
     * 权限点保存功能
     *
     * @throws Exception
     * @Param: [sysAclRequestDTO]
     * @return: void
     */
    void saveSysAcl(SysAclRequestDTO sysAclRequestDTO) throws Exception;


    /**
     * 权限点更新功能
     *
     * @throws Exception
     * @Param: [sysAclRequestDTO]
     * @return: void
     */
    void updateSysAcl(SysAclRequestDTO sysAclRequestDTO) throws Exception;


    /**
     * 权限模块删除
     *
     * @throws Exception
     * @Param: [aclId]
     * @return: void
     */
    void deleteSysAcl(Integer aclId) throws Exception;

    /**
     * 获取用户和角色集合
     *
     * @Param: [aclId]
     * @return: com.yrm.permission.dto.UserListAndRoleListDTO
     */

    UserListAndRoleListDTO getUserListAndRoleList(Integer aclId) throws Exception;
}
