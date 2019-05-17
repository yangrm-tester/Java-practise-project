package com.yrm.permission.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclRequestDTO
 * @createTime 2019年04月24日 11:24:00
 */
public class SysAclRequestDTO {
    private Integer id;
    private String code;
    private String name;
    private Integer aclModuleId;
    private String url;
    private Integer type;
    private Integer status;
    private Integer seq;
    private String remark;


    public SysAclRequestDTO() {
    }

    public SysAclRequestDTO(Integer id, String code, String name, Integer aclModuleId, String url, Integer type, Integer status, Integer seq, String renmark) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.aclModuleId = aclModuleId;
        this.url = url;
        this.type = type;
        this.status = status;
        this.seq = seq;
        this.remark = remark;
    }

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
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.url = url;
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
        this.remark = remark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static boolean checkParam(SysAclRequestDTO sysAclRequestDTO) {
        return sysAclRequestDTO == null || StringUtils.isBlank(sysAclRequestDTO.getName()) || null == sysAclRequestDTO.getAclModuleId() || null == sysAclRequestDTO.getSeq() || null == sysAclRequestDTO.getStatus() || null == sysAclRequestDTO.getType();
    }
}
