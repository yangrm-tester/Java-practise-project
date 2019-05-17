package com.yrm.permission.bean;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className PageResult
 * @createTime 2019年04月08日 14:40:00
 */
public class PageResult<T> {
    private Integer total;
    private List<T> data;
    private Integer totalPageNo;

    public PageResult(Integer total, List<T> data, Integer totalPageNo) {
        this.total = total;
        this.data = data;
        this.totalPageNo=totalPageNo;
    }

    public PageResult() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getTotalPageNo() {
        return totalPageNo;
    }

    public void setTotalPageNo(Integer totalPageNo) {
        this.totalPageNo = totalPageNo;
    }
}
