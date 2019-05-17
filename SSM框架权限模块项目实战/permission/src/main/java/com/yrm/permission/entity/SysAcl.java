package com.yrm.permission.entity;

import com.yrm.permission.dto.SysAclRequestDTO;

import java.util.Date;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAcl
 * @createTime 2019年03月21日 16:24:00
 */

public class SysAcl {
    private Integer id;

    private String code;

    private String name;

    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAclModuleId() {
        return aclModuleId;
    }

    public void setAclModuleId(Integer aclModuleId) {
        this.aclModuleId = aclModuleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp == null ? null : operateIp.trim();
    }

    public static SysAcl packageSysAcl(SysAclRequestDTO sysAclRequestDTO) {
        if (sysAclRequestDTO == null) {
            return null;
        }
        SysAcl sysAcl = new SysAcl();
        sysAcl.setId(sysAclRequestDTO.getId());
        sysAcl.setName(sysAclRequestDTO.getName());
        sysAcl.setCode(sysAclRequestDTO.getCode());
        sysAcl.setAclModuleId(sysAclRequestDTO.getAclModuleId());
        sysAcl.setStatus(sysAclRequestDTO.getStatus());
        sysAcl.setSeq(sysAclRequestDTO.getSeq());
        sysAcl.setUrl(sysAclRequestDTO.getUrl());
        sysAcl.setType(sysAclRequestDTO.getType());
        sysAcl.setRemark(sysAclRequestDTO.getRemark());
        return sysAcl;
    }
}