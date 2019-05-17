package com.yrm.permission.service;

import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.dto.AclListAndRoleListDTO;
import com.yrm.permission.dto.SysUserRequestDTO;
import com.yrm.permission.entity.SysUser;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysUserService
 * @createTime 2019年04月03日 14:42:00
 */

public interface SysUserService {

    /**
     * 用户信息保存
     *
     * @throws Exception
     * @Param: [sysUserRequestDTO]
     * @return: void
     */

    void saveSysUser(SysUserRequestDTO sysUserRequestDTO) throws Exception;

    /**
     * 后台功能修改用户信息，例如手机号，邮箱或者用户状态等等
     *
     * @throws Exception
     * @Param: [sysUserRequestDTO]
     * @return: void
     */

    void updateSysUser(SysUserRequestDTO sysUserRequestDTO) throws Exception;

    /**
     * 通过关键字去查用户信息，如用户名或者邮箱
     *
     * @throws Exception
     * @Param: [username]
     * @return: com.yrm.permission.entity.SysUser
     */


    SysUser findUserByKeyword(String username) throws Exception;

    /**
     * 获取分页数据
     *
     * @throws Exception
     * @Param: [deptId, pageQuery]
     * @return: com.yrm.permission.bean.PageResult
     */


    PageResult getPageData(Integer deptId, PageQuery pageQuery) throws Exception;

    /**
     * 获取权限和角色信息列表
     *
     * @throws Exception
     * @Param: [userId]
     * @return: com.yrm.permission.dto.AclListAndRoleListDTO
     */

    AclListAndRoleListDTO getAclListAndRoleList(Integer userId) throws Exception;
}
