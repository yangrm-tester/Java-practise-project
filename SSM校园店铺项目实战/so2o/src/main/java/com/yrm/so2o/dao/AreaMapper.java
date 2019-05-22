package com.yrm.so2o.dao;

import com.yrm.so2o.entity.Area;

/**
 * @author 杨汝明
 * */
public interface AreaMapper {

    int deleteByPrimaryKey(Integer areaId);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer areaId);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

}