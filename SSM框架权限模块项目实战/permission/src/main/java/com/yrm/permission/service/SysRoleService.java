package com.yrm.permission.service;

import com.yrm.permission.dto.SysAclModuleLevelDTO;
import com.yrm.permission.dto.SysRoleRequestDTO;
import com.yrm.permission.dto.SysUserResponseDTO;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysRoleService
 * @createTime 2019年05月03日 16:46:00
 */

public interface SysRoleService {
    /**
     *  新增角色用户
     *  @Param: [sysRoleRequestDTO]
     *  @return: void
     *  @throws Exception
     */

    void save(SysRoleRequestDTO sysRoleRequestDTO,HttpServletRequest request) throws Exception;

    /**
     *  修改角色用户
     *  @Param: [sysRoleRequestDTO]
     *  @return: void
     *  @throws Exception
     */

    void update(SysRoleRequestDTO sysRoleRequestDTO, HttpServletRequest request) throws Exception;

    /**
     *  获取角色用户列表
     *  @Param: []
     *  @return: java.util.List<javax.management.relation.Role>
     *  @throws Exception
     */
    List<Role> getRoleList() throws Exception;

    /**
     *  删除角色用户
     *  @Param: [id]
     *  @return: void
     *  @throws Exception
     */

    void delete(Integer id) throws Exception;



    /**
     *  获取角色权限树
     *  @Param: [roleId]
     *  @return: java.util.List<com.yrm.permission.dto.SysAclModuleLevelDTO>
     *  @throws Exception
     */

    List<SysAclModuleLevelDTO> getRoleAclTree(Integer roleId) throws Exception;


    /** 权限角色关系保存
     *  @Param: [roleId, aclIds]
     *  @return: void
     *  @throws Exception
     */

    void saveRoleAcl(Integer roleId, String aclIds) throws Exception;

    /**
     *  获取权限用户列表
     *  @Param: [roleId]
     *  @return: com.yrm.permission.dto.SysUserResponseDTO
     *  @throws Exception
     */

    SysUserResponseDTO getUserList(Integer roleId) throws Exception;

    /** 角色用户关系保存
     *  @Param: [roleId, aclIds]
     *  @return: void
     *  @throws Exception
     */
    void saveRoleUser(Integer roleId, String userIds) throws Exception;


    /**
     *  判断当前用户是否有此url的权限点
     *  @Param: [servletPath]
     *  @return: boolean
     */

    boolean hasUrlAcl(String servletPath) throws Exception;
}
