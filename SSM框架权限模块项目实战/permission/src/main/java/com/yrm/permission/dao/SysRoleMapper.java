package com.yrm.permission.dao;

import com.yrm.permission.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import javax.management.relation.Role;
import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysRoleMapper
 * @createTime 2019年03月21日 16:24:00
 */
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    SysRole getSysRoleByNameAndId(@Param("name") String name,@Param("id") Integer id);

    List<Role> getSysRoleList();

    List<SysRole> getSysRoleListByIdList(@Param("roleIdListByUserId") List<Integer> roleIdListByUserId);
}