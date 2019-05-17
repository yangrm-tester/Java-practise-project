package com.yrm.permission.dto;

import com.yrm.permission.entity.SysRole;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysRoleRequestDTO
 * @createTime 2019年05月03日 16:50:00
 */
public class SysRoleRequestDTO {
    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    public SysRoleRequestDTO() {
    }

    public SysRoleRequestDTO(Integer id, String name, Integer type, Integer status, String remark) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static boolean checkParam(SysRoleRequestDTO sysRoleRequestDTO) {
        if (sysRoleRequestDTO == null || sysRoleRequestDTO.getName() == null || sysRoleRequestDTO.getStatus() == null) {
            return false;
        }
        return true;
    }

    public static SysRole packageSysRole(SysRoleRequestDTO sysRoleRequestDTO) {
        if (sysRoleRequestDTO == null) {
            return null;
        }
        SysRole sysRole = new SysRole();
        sysRole.setId(sysRoleRequestDTO.getId());
        sysRole.setName(sysRoleRequestDTO.getName());
        sysRole.setType(sysRoleRequestDTO.getType());
        sysRole.setStatus(sysRoleRequestDTO.getStatus());
        sysRole.setRemark(sysRoleRequestDTO.getRemark());
        return sysRole;
    }
}
