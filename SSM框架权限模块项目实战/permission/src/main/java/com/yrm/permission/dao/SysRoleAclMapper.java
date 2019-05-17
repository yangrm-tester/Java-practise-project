package com.yrm.permission.dao;

import com.yrm.permission.entity.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysRoleAclMapper
 * @createTime 2019年03月21日 16:24:00
 */

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);


    /**
     *  通过用户角色Id来获取权限点Id
     *  @Param: [userRoleIdList]
     *  @return: java.util.List<java.lang.Integer>
     *  @Description:
     */

    List<Integer> getAclListByRoleIdList(@Param("userRoleIdList") List<Integer> userRoleIdList);


    int deleteByRoleId(@Param("roleId") Integer roleId);


    /**
     *  批量插入权限点和role之间关系
     *  @Param: [sysRoleAclList]
     *  @return: int
     */

    int batchInsert(@Param("sysRoleAclList") List<SysRoleAcl> sysRoleAclList);



    List<Integer> getRoleListByAclId(@Param("aclId") Integer aclId);
}