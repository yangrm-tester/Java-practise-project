package com.yrm.permission.dto;

import com.yrm.permission.entity.SysAclModule;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclModuleRequestDTO
 * @createTime 2019年04月09日 17:02:00
 */

public class SysAclModuleRequestDTO {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer seq;
    private Integer status;
    private String remark;

    public SysAclModuleRequestDTO() {
    }

    public SysAclModuleRequestDTO(Integer id, String name, Integer parentId, Integer seq, Integer status, String remark) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.seq = seq;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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
        return "SysAclModuleRequestDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", seq=" + seq +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }

    public static SysAclModule adapt(SysAclModuleRequestDTO sysAclModuleRequestDTO) {
        if (sysAclModuleRequestDTO == null) {
            return null;
        }
        SysAclModule sysAclModule = new SysAclModule();
        sysAclModule.setId(sysAclModuleRequestDTO.getId());
        sysAclModule.setName(sysAclModuleRequestDTO.getName());
        sysAclModule.setParentId(sysAclModuleRequestDTO.getParentId());
        sysAclModule.setStatus(sysAclModuleRequestDTO.getStatus());
        sysAclModule.setRemark(sysAclModuleRequestDTO.getRemark());
        sysAclModule.setSeq(sysAclModuleRequestDTO.getSeq());
        return sysAclModule;
    }

}
