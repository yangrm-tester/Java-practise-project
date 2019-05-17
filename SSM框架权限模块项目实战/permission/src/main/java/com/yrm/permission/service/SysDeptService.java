package com.yrm.permission.service;

import com.yrm.permission.common.ApiResult;
import com.yrm.permission.dto.SysDeptRequestDTO;
import com.yrm.permission.dto.SysDeptTreeResponseDto;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysDeptService
 * @createTime 2019年03月22日 19:25:00
 */
public interface SysDeptService {

    /**
     *  部门保存
     *  @param sysDeptRequestDTO
     *  @return: SysDept
     *  @Description:
     *  @exception  [Exception]
     */

    ApiResult saveSysDept(SysDeptRequestDTO sysDeptRequestDTO) throws Exception;


    /**
     *  根据部门level获取列表tree
     *  @Param: []
     *  @return: java.util.List<com.yrm.permission.dto.SysDeptTreeResponseDto>
     *  @exception  Exception
     */

    List<SysDeptTreeResponseDto> getTreeList() throws Exception;

    /**
     *  部门信息修改
     *  @Param: [sysDeptRequestDTO]
     *  @return: void
     *  @exception Exception
     */

    void updateSysDept(SysDeptRequestDTO sysDeptRequestDTO) throws Exception;

    /**
     *  部门删除
     *  @Param: [deptId]
     *  @return: void
     *  @exception Exception
     */
    void deleteSysDept(Integer deptId) throws Exception;
}

