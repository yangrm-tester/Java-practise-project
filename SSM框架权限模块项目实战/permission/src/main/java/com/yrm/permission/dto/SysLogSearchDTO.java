package com.yrm.permission.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysLogSearchDTO
 * @createTime 2019年05月17日 14:38:00
 */
public class SysLogSearchDTO {
    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 操作前的值
     */
    private String beforeSeg;

    /**
     * 操作后的值
     */
    private String afterSeg;
    /**
     * 操作者
     */
    private String operator;

    /**
     * 操作起始日期 格式：yyyy-MM-dd HH:mm:ss
     */
    private Date fromTime;

    /**
     * 操作结束日期 格式：yyyy-MM-dd HH:mm:ss
     */
    private Date toTime;

    public SysLogSearchDTO() {
    }

    public SysLogSearchDTO(Integer logType, String beforeSeg, String afterSeg, String operator, Date fromTime, Date toTime) {
        this.logType = logType;
        this.beforeSeg = beforeSeg;
        this.afterSeg = afterSeg;
        this.operator = operator;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getBeforeSeg() {
        return beforeSeg;
    }

    public void setBeforeSeg(String beforeSeg) {
        this.beforeSeg = beforeSeg;
    }

    public String getAfterSeg() {
        return afterSeg;
    }

    public void setAfterSeg(String afterSeg) {
        this.afterSeg = afterSeg;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static SysLogSearchDTO packageSysLogSearchDTO(SysLogRequestDTO sysLogRequestDTO) {
        if (sysLogRequestDTO == null) {
            return null;
        }
        SysLogSearchDTO sysLogSearchDTO = new SysLogSearchDTO();

        sysLogSearchDTO.setAfterSeg(StringUtils.isNotBlank(sysLogRequestDTO.getAfterSeg()) ? ("%" + sysLogRequestDTO.getAfterSeg() + "%") : sysLogRequestDTO.getAfterSeg());
        sysLogSearchDTO.setBeforeSeg(StringUtils.isNotBlank(sysLogRequestDTO.getBeforeSeg()) ? ("%" + sysLogRequestDTO.getBeforeSeg() + "%") : sysLogRequestDTO.getBeforeSeg());
        sysLogSearchDTO.setOperator(StringUtils.isNotBlank(sysLogRequestDTO.getOperator()) ? ("%" + sysLogRequestDTO.getOperator() + "%") : sysLogRequestDTO.getOperator());
        sysLogSearchDTO.setLogType(sysLogRequestDTO.getLogType());
        return sysLogSearchDTO;
    }
}
