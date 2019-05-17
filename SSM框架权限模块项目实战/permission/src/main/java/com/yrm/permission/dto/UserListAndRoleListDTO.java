package com.yrm.permission.dto;

import com.yrm.permission.entity.SysRole;
import com.yrm.permission.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className UserListAndRoleListDTO
 * @createTime 2019年05月14日 15:50:00
 */
public class UserListAndRoleListDTO {
    List<SysRole> roleList;
    List<SysUser> sysUserList;

    public UserListAndRoleListDTO() {
    }

    public UserListAndRoleListDTO(List<SysRole> roleList, List<SysUser> sysUserList) {
        this.roleList = roleList;
        this.sysUserList = sysUserList;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public List<SysUser> getSysUserList() {
        return sysUserList;
    }

    public void setSysUserList(List<SysUser> sysUserList) {
        this.sysUserList = sysUserList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
