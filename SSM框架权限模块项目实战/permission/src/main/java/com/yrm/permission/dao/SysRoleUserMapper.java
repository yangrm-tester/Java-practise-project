package com.yrm.permission.dao;

import com.yrm.permission.entity.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysRoleUserMapper
 * @createTime 2019年03月21日 16:24:00
 */
public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    /**
     *  通过用户id获取角色Id
     *  @Param: [userId]
     *  @return: java.util.List<java.lang.Integer>
     */

    List<Integer> getRoleIdListByUserId(@Param("userId") Integer userId);

    /** 通过角色id查询用户id列表
     *  @Param: [roleId]
     *  @return: java.util.List<java.lang.Integer>
     */

    List<Integer> getUserIdListByRoleId(@Param("roleId") Integer roleId);


    /** 通过角色ID 获取用户ID
     *  @Param: [roleIdList]
     *  @return: java.util.List<java.lang.Integer>
     */

    List<Integer> getUserListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    /**
     *  角色ID 删除角色用户之间关系
     *  @Param: [roleId]
     *  @return: void
     */

    void deleteByRoleId(@Param("roleId") Integer roleId);

    /**
     *  批量插入角色和role之间关系
     *  @Param: [sysRoleUserList]
     *  @return: int
     */
    int batchInsert(@Param("sysRoleUserList") List<SysRoleUser> sysRoleUserList);


    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
}