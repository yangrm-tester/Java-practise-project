package com.yrm.edu.dao;

import com.yrm.edu.entity.ConstsDictionary;

public interface ConstsDictionaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConstsDictionary record);

    int insertSelective(ConstsDictionary record);

    ConstsDictionary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConstsDictionary record);

    int updateByPrimaryKey(ConstsDictionary record);
}