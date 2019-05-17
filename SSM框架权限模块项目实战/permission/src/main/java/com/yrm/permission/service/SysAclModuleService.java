package com.yrm.permission.service;

import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.dto.SysAclModuleLevelDTO;
import com.yrm.permission.dto.SysAclModuleRequestDTO;
import com.yrm.permission.entity.SysAcl;

import java.security.acl.Acl;
import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclModuleService
 * @createTime 2019年04月09日 20:33:00
 */
public interface SysAclModuleService {


    /**
     * 权限模块保存
     *
     * @throws Exception
     * @Param: [sysAclModuleRequestDTO]
     * @return: void
     */

    void saveSysAclModule(SysAclModuleRequestDTO sysAclModuleRequestDTO) throws Exception;

    /**
     * 权限模块更新
     *
     * @throws Exception
     * @Param: [sysAclModuleRequestDTO]
     * @return: void
     */

    void updateSysAclModule(SysAclModuleRequestDTO sysAclModuleRequestDTO) throws Exception;


    /**
     * 获取权限模块的tree
     * @throws Exception
     * @Param: []
     * @return: java.util.List<com.yrm.permission.dto.SysAclModuleLevelDTO>
     */

    List<SysAclModuleLevelDTO> sysAclModuleTree() throws Exception;

    /**
     *  获取分页数据
     *  @Param: [aclModuleId, pageQuery]
     *  @return: com.yrm.permission.bean.PageResult<java.security.acl.Acl>
     *  @throws Exception
     */
    PageResult<SysAcl> getPageData(Integer aclModuleId, PageQuery pageQuery) throws Exception;

    /**
     *  权限模块删除
     *  @Param: [aclModuleId]
     *  @return: void
     *  @throws Exception
     */

    void deleteAclModule(Integer aclModuleId) throws Exception;
}
