package com.yrm.so2o.dao;

import com.yrm.so2o.entity.WechatAuth;

public interface WechatAuthMapper {
    int deleteByPrimaryKey(Integer wechatAuthId);

    int insert(WechatAuth record);

    int insertSelective(WechatAuth record);

    WechatAuth selectByPrimaryKey(Integer wechatAuthId);

    int updateByPrimaryKeySelective(WechatAuth record);

    int updateByPrimaryKey(WechatAuth record);
}