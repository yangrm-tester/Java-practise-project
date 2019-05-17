package com.yrm.permission.dto;

import com.yrm.permission.entity.SysAclModule;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclModuleLevelDTO
 * @createTime 2019年04月12日 15:20:00
 */
public class SysAclModuleLevelDTO extends SysAclModule {
    private List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList;

    private List<SysAclDTO> sysAclDTOList;


    public List<SysAclModuleLevelDTO> getSysAclModuleLevelDTOList() {
        return sysAclModuleLevelDTOList;
    }

    public void setSysAclModuleLevelDTOList(List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList) {
        this.sysAclModuleLevelDTOList = sysAclModuleLevelDTOList;
    }

    @Override
    public String toString() {
        return "SysAclModuleLevelDTO{" +
                "sysAclModuleLevelDTOList=" + sysAclModuleLevelDTOList +
                '}';
    }

    public static SysAclModuleLevelDTO adapt(SysAclModule sysAclModule) {
        if (sysAclModule == null) {
            return null;
        }
        SysAclModuleLevelDTO sysAclModuleLevelDTO = new SysAclModuleLevelDTO();
        BeanUtils.copyProperties(sysAclModule, sysAclModuleLevelDTO);
        return sysAclModuleLevelDTO;
    }

    public List<SysAclDTO> getSysAclDTOList() {
        return sysAclDTOList;
    }

    public void setSysAclDTOList(List<SysAclDTO> sysAclDTOList) {
        this.sysAclDTOList = sysAclDTOList;
    }
}
