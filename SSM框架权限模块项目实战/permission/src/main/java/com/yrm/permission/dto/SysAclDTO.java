package com.yrm.permission.dto;

import com.yrm.permission.entity.SysAcl;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclDTO
 * @createTime 2019年05月07日 09:58:00
 */
public class SysAclDTO extends SysAcl {
    /**
     * ACL选中状态
     */
    private Boolean isChecked;


    /**
     * 是否有权限操作
     */
    private Boolean hasAcl;


    public static SysAclDTO adaptSysAclDTO(SysAcl sysAcl) {
        if (sysAcl == null) {
            return null;
        }
        SysAclDTO sysAclDTO = new SysAclDTO();
        BeanUtils.copyProperties(sysAcl, sysAclDTO);
        return sysAclDTO;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Boolean getHasAcl() {
        return hasAcl;
    }

    public void setHasAcl(Boolean hasAcl) {
        this.hasAcl = hasAcl;
    }
}
