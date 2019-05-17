package com.yrm.permission.dto;

import com.yrm.permission.entity.SysUser;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysUserRequestDTO
 * @createTime 2019年04月03日 14:19:00
 */
public class SysUserRequestDTO {
    private Integer id;

    private String username;

    private String telephone;

    private String mail;

    private Integer deptId;

    private Integer status;

    private String remark;

    public SysUserRequestDTO() {
    }

    public SysUserRequestDTO(Integer id, String username, String telephone, String mail, Integer deptId, Integer status, String remark) {
        this.id = id;
        this.username = username;
        this.telephone = telephone;
        this.mail = mail;
        this.deptId = deptId;
        this.status = status;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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

    public static boolean checkParam(SysUserRequestDTO sysUserRequestDTO) {
        if (sysUserRequestDTO == null) {
            return false;
        }
        String username = sysUserRequestDTO.getUsername();
        String telephone = sysUserRequestDTO.getTelephone();
        String mail = sysUserRequestDTO.getMail();
        Integer deptId = sysUserRequestDTO.getDeptId();
        Integer status = sysUserRequestDTO.getStatus();
        String remark = sysUserRequestDTO.getRemark();
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(telephone) && StringUtils.isNotBlank(mail) && StringUtils.isNotBlank(remark) && deptId != null && status != null) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SysUserRequestDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mail='" + mail + '\'' +
                ", deptId=" + deptId +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
