package com.yrm.so2o.dto;

import com.yrm.so2o.entity.Shop;
import com.yrm.so2o.enums.ShopEnumState;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ShopResponseDTO
 * @createTime 2019年05月22日 11:21:00
 */
public class ShopResponseDTO {
    /**
     * 结果状态
     */
    private Integer state;
    /**
     * 店铺数量
     */
    private Integer shopCount;
    /**
     * 店铺状态标识
     */
    private String stateInfo;
    /**
     * 操作的店铺
     */
    private Shop shop;
    /**
     * 供查询用的店铺列表
     */
    private List<Shop> shopList;

    public ShopResponseDTO() {
    }

    /**
     * 操作失败的的时候构造函数
     */
    public ShopResponseDTO(ShopEnumState shopEnumState) {
        this.state = shopEnumState.getState();
        this.stateInfo = shopEnumState.getStateInfo();
    }

    /**
     * 操作成功的构造函数
     */
    public ShopResponseDTO(ShopEnumState shopEnumState, Shop shop) {
        this.state = shopEnumState.getState();
        this.stateInfo = shopEnumState.getStateInfo();
        this.shop = shop;
    }

    /**
     * 查询成功的构造函数
     */
    public ShopResponseDTO(ShopEnumState shopEnumState, List<Shop> shopList) {
        this.state = shopEnumState.getState();
        this.stateInfo = shopEnumState.getStateInfo();
        this.shopList = shopList;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
