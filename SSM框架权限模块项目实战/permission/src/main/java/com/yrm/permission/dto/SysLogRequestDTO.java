package com.yrm.permission.dto;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysLogRequestDTO
 * @createTime 2019年05月16日 17:58:00
 */
public class SysLogRequestDTO {

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
    private String fromTime;

    /**
     * 操作结束日期 格式：yyyy-MM-dd HH:mm:ss
     */
    private String toTime;

    public SysLogRequestDTO() {
        super();
    }

    public SysLogRequestDTO(Integer logType, String beforeSeg, String afterSeg, String operator, String fromTime, String toTime) {
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

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
