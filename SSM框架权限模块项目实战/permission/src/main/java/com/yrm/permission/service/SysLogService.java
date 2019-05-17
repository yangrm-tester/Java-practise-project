package com.yrm.permission.service;

import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.dto.SysLogRequestDTO;
import com.yrm.permission.entity.*;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysLogService
 * @createTime 2019年05月15日 17:13:00
 */
public interface SysLogService {

    /**
     * 保存部门操作日志
     *
     * @throws Exception
     * @Param: []
     * @return: void
     */

    void saveDeptLog(SysDept before, SysDept after) throws Exception;

    /**
     * 保存用户操作日志
     *
     * @throws Exception
     * @Param: [before, after]
     * @return: void
     */

    void saveUserLog(SysUser before, SysUser after) throws Exception;


    /**
     * 保存权限模块操作日志
     *
     * @throws Exception
     * @Param: [before, after]
     * @return: void
     */

    void saveAclModuleLog(SysAclModule before, SysAclModule after) throws Exception;

    /**
     * 保存权限点操作日志
     *
     * @throws Exception
     * @Param: [before, after]
     * @return: void
     */

    void saveAclLog(SysAcl before, SysAcl after) throws Exception;

    /**
     *  保存角色操作日志
     *  @Param: [before, after]
     *  @return: void
     *  @throws Exception
     */


    void saveRoleLog(SysRole before, SysRole after) throws Exception;

    /**
     *  获取日志列表分页数据
     *  @Param: [sysLogRequestDTO, pageQuery]
     *  @return: com.yrm.permission.bean.PageResult<com.yrm.permission.entity.SysLogWithBLOBs>
     *  @throws Exception
     */

    PageResult<SysLogWithBLOBs> getPageData(SysLogRequestDTO sysLogRequestDTO, PageQuery pageQuery) throws Exception;
}
