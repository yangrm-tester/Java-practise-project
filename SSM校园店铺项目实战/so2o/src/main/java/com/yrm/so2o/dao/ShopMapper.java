package com.yrm.so2o.dao;

import com.yrm.so2o.entity.Shop;

/**
 * @author 杨汝明
* */
public interface ShopMapper {

    /**
     *  通过id删除
     *  @param  shopId 店铺id
     *  @return: int
     *  @Description:
     */
    int deleteByPrimaryKey(Integer shopId);

    /**
     *  通过shop实体类保存
     *  @param record
     *  @return: int
     *  @Description:
     */

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer shopId);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
}