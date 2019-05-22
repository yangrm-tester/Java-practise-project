package com.yrm.so2o.dao;

import com.yrm.so2o.entity.HeadLine;

public interface HeadLineMapper {
    int deleteByPrimaryKey(Integer lineId);

    int insert(HeadLine record);

    int insertSelective(HeadLine record);

    HeadLine selectByPrimaryKey(Integer lineId);

    int updateByPrimaryKeySelective(HeadLine record);

    int updateByPrimaryKey(HeadLine record);
}