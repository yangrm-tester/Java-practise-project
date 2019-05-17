package com.yrm.permission.dao;

import com.yrm.permission.dto.SysAclRequestDTO;
import com.yrm.permission.entity.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclMapper
 * @createTime 2019年03月21日 16:24:00
 */
public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    /**
     * 通过id name 和aclModuleId 去查询数据
     *
     * @Param: [sysAclRequestDTO]
     * @return: com.yrm.permission.entity.SysAcl
     */

    SysAcl getRepeatSysAcl(@Param("sysAclRequestDTO") SysAclRequestDTO sysAclRequestDTO);

    Integer getTotalCount(@Param("aclModuleId") Integer aclModuleId);

    List<SysAcl> getPageData(@Param("aclModuleId") Integer aclModuleId, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 获取所有权限点集合
     *
     * @Param: []
     * @return: java.util.List<com.yrm.permission.entity.SysAcl>
     * @Description:
     */

    List<SysAcl> getAllAclList();


    /**
     *  通过权限点ID获取权限集合
     *  @Param: [userAclIdList]
     *  @return: java.util.List<com.yrm.permission.entity.SysAcl>
     *  @Description:
     */

    List<SysAcl> getAclListByAclIds(@Param("userAclIdList") List<Integer> userAclIdList);

    int countByAclModuleId(@Param("aclModuleId") Integer aclModuleId);

    List<SysAcl> getSysAclByUrl(@Param("servletPath") String servletPath);
}