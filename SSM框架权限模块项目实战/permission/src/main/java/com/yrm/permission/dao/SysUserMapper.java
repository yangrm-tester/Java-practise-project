package com.yrm.permission.dao;

import com.yrm.permission.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysUserMapper
 * @createTime 2019年03月21日 16:24:00
 */
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int findByPhone(@Param("telephone") String telephone, @Param("userId") Integer userId);

    int findByMail(@Param("mail") String mail, @Param("userId") Integer userId);

    SysUser findByKeyword(@Param("keyword") String keyword);

    int getTotalCountByDeptId(@Param("deptId") Integer deptId);

    List<SysUser> getCurrentPageData(@Param("deptId")Integer deptId,@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);

    /**
     *  获取所有用户信息
     *  @Param: []
     *  @return: java.util.List<com.yrm.permission.entity.SysUser>
     */

    List<SysUser> getAllUserList();

    /**
     *  通过用户id获取用户信息
     *  @Param: [userIdListByRoleId]
     *  @return: java.util.List<com.yrm.permission.entity.SysUser>
     */

    List<SysUser> getUserListByUserIds(@Param("userIdListByRoleId") List<Integer> userIdListByRoleId);
}