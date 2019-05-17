package com.yrm.permission.dto;

import com.yrm.permission.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysUserResponseDto
 * @createTime 2019年05月08日 17:40:00
 */
public class SysUserResponseDTO {
    private List<SysUser> selectedUserList;
    private List<SysUser> unselectedUserList;

    public SysUserResponseDTO() {
        super();
    }

    public SysUserResponseDTO(List<SysUser> selectedUserList, List<SysUser> unselectedUserList) {
        this.selectedUserList = selectedUserList;
        this.unselectedUserList = unselectedUserList;
    }

    public List<SysUser> getSelectedUserList() {
        return selectedUserList;
    }

    public void setSelectedUserList(List<SysUser> selectedUserList) {
        this.selectedUserList = selectedUserList;
    }

    public List<SysUser> getUnselectedUserList() {
        return unselectedUserList;
    }

    public void setUnselectedUserList(List<SysUser> unselectedUserList) {
        this.unselectedUserList = unselectedUserList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
