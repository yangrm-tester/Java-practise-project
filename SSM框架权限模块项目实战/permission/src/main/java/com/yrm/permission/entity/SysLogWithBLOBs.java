package com.yrm.permission.entity;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysLogWithBLOBs
 * @createTime 2019年03月21日 16:24:00
 */

public class SysLogWithBLOBs extends SysLog {
    private String oldValue;

    private String newValue;

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }
}