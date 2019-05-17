package com.yrm.permission.dto;

import com.yrm.permission.entity.SysRole;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className AclListAndRoleListDTO
 * @createTime 2019年05月09日 17:12:00
 */
public class AclListAndRoleListDTO {
    List<SysRole> sysRoleList;
    List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList;

    public AclListAndRoleListDTO() {
    }

    public AclListAndRoleListDTO(List<SysRole> sysRoleList, List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList) {
        this.sysRoleList = sysRoleList;
        this.sysAclModuleLevelDTOList = sysAclModuleLevelDTOList;
    }

    public List<SysRole> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<SysRole> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }

    public List<SysAclModuleLevelDTO> getSysAclModuleLevelDTOList() {
        return sysAclModuleLevelDTOList;
    }

    public void setSysAclModuleLevelDTOList(List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList) {
        this.sysAclModuleLevelDTOList = sysAclModuleLevelDTOList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
