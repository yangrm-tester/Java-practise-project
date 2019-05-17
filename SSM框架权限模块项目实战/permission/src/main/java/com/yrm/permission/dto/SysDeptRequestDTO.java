package com.yrm.permission.dto;


import lombok.Builder;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysDeptDTO
 * @createTime 2019年03月22日 18:25:00
 */
@Builder
public class SysDeptRequestDTO {
    /**
     * 部门id
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级部门id
     */
    private Integer parentId;

    /**
     * 部门描述
     */
    private String remark;

    /**
     * 排序顺序
     */
    private Integer seq;


    public SysDeptRequestDTO() {

    }

    public SysDeptRequestDTO(Integer id, String name, Integer parentId, String remark, Integer seq) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.remark = remark;
        this.seq = seq;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
