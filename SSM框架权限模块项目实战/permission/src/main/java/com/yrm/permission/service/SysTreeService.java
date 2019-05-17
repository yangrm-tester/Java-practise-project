package com.yrm.permission.service;

import com.yrm.permission.dto.SysDeptLevelDTO;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysTreeService
 * @createTime 2019年03月26日 19:26:00
 */
public interface SysTreeService {

    /**
     *  获取部门列表tree
     *  @param
     *  @return: java.util.List<com.yrm.permission.dto.SysDeptLevelDTO>
     *  @exception Exception
     */

    List<SysDeptLevelDTO> getTreeList() throws Exception;
}
