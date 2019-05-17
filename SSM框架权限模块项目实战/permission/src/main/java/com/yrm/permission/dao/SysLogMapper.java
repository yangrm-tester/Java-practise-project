package com.yrm.permission.dao;

import com.yrm.permission.dto.SysLogSearchDTO;
import com.yrm.permission.entity.SysLog;
import com.yrm.permission.entity.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysLogMapper
 * @createTime 2019年03月21日 16:24:00
 */

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    int countBySysLogRequestDTO(@Param("sysLogSearchDTO") SysLogSearchDTO sysLogSearchDTO);

    List<SysLogWithBLOBs> getCurrentPageData(@Param("sysLogSearchDTO") SysLogSearchDTO sysLogSearchDTO, @Param("startIndex") int startIndex, @Param("pageSize") Integer pageSize);
}