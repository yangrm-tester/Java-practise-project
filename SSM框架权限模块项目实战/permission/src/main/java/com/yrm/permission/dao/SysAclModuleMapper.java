package com.yrm.permission.dao;

import com.yrm.permission.entity.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclModuleMapper
 * @createTime 2019年03月21日 16:24:00
 */

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);


    int countByNameAndParentId(@Param("name") String name, @Param("parentId") Integer parentId);

    String selectAclModuleLevelById(@Param("parentId") Integer parentId);

    List<SysAclModule> selectNeedUpdateAclModule(@Param("levelParam") String levelParam);

    int updateLevelById(@Param("sysAclModuleChild") SysAclModule sysAclModuleChild);

    List<SysAclModule> getAllSysModuleList();

    int countByParentId(@Param("aclModuleId") Integer aclModuleId);
}