package com.yrm.so2o.dao;

import com.yrm.so2o.entity.UserShopMap;

public interface UserShopMapMapper {
    int deleteByPrimaryKey(Integer userShopId);

    int insert(UserShopMap record);

    int insertSelective(UserShopMap record);

    UserShopMap selectByPrimaryKey(Integer userShopId);

    int updateByPrimaryKeySelective(UserShopMap record);

    int updateByPrimaryKey(UserShopMap record);
}