package com.yrm.permission.dto;

import com.yrm.permission.entity.SysDept;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysDeptTreeRequestDto
 * @createTime 2019年03月26日 14:28:00
 */
public class SysDeptTreeResponseDto {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private String remark;

    private List<SysDeptTreeResponseDto> sysDeptTreeResponseDtoList;

    public SysDeptTreeResponseDto() {

    }

    public SysDeptTreeResponseDto(Integer id, String name, Integer parentId, String level, Integer seq, String remark, List<SysDeptTreeResponseDto> sysDeptTreeResponseDtoList) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.seq = seq;
        this.remark = remark;
        this.sysDeptTreeResponseDtoList = sysDeptTreeResponseDtoList;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public List<SysDeptTreeResponseDto> getSysDeptTreeResponseDtoList() {
        return sysDeptTreeResponseDtoList;
    }

    public void setSysDeptTreeResponseDtoList(List<SysDeptTreeResponseDto> sysDeptTreeResponseDtoList) {
        this.sysDeptTreeResponseDtoList = sysDeptTreeResponseDtoList;
    }


    public static SysDeptTreeResponseDto adapt(SysDept sysDept) {
        if (null == sysDept) {
            return null;
        }
        SysDeptTreeResponseDto sysDeptTreeResponseDto = new SysDeptTreeResponseDto();
        sysDeptTreeResponseDto.setId(sysDept.getId());
        sysDeptTreeResponseDto.setName(sysDept.getName());
        sysDeptTreeResponseDto.setParentId(sysDept.getParentId());
        sysDeptTreeResponseDto.setLevel(sysDept.getLevel());
        sysDeptTreeResponseDto.setSeq(sysDept.getSeq());
        sysDeptTreeResponseDto.setRemark(sysDept.getRemark());
        return sysDeptTreeResponseDto;
    }
}
