package com.yrm.permission.bean;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className PageQuery
 * @createTime 2019年04月08日 14:40:00
 */
public class PageQuery {

    private Integer pageSize;

    private Integer pageNo;

    public PageQuery() {
    }

    public PageQuery(Integer pageSize, Integer pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
