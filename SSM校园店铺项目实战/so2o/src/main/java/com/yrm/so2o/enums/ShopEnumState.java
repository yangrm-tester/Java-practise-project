package com.yrm.so2o.enums;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ShopEnumState
 * @createTime 2019年05月22日 11:28:00
 */
public enum ShopEnumState {
    /**
     * 店铺的几种状态
     */
    CHECK(0, "审核中"),
    OFFLINE(-1, "非法商铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "操作失败"),
    NULL_SHOP_ID(-1002, "ShopId为空"),
    NULL_SHOP_INFO(-1003, "传入了空的信息");


    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态标识符
     */
    private String stateInfo;

    private ShopEnumState(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public Integer getState() {
        return state;
    }


    public String getStateInfo() {
        return stateInfo;
    }

    /**通过state状态值拿到枚举值对象*/
    public static ShopEnumState stateOf(int index) {
        for (ShopEnumState shopEnumState : values()) {
            if (shopEnumState.getState() == index) {
                return shopEnumState;
            }
        }
        return null;
    }
}
