package com.yrm.permission.dto;

import com.yrm.permission.entity.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysDeptLevelDTO
 * @createTime 2019年03月26日 19:40:00
 */
@Getter
@Setter
@ToString
public class SysDeptLevelDTO extends SysDept {

    private List<SysDeptLevelDTO> sysDeptLevelDTOList;

    public static SysDeptLevelDTO adapt(SysDept sysDept){
        SysDeptLevelDTO sysDeptLevelDTO = new SysDeptLevelDTO();
        BeanUtils.copyProperties(sysDept,sysDeptLevelDTO);
        return sysDeptLevelDTO;
    }

}

