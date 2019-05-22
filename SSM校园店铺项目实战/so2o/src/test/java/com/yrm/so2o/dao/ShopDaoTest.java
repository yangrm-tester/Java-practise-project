package com.yrm.so2o.dao;

import com.yrm.so2o.entity.PersonInfo;
import com.yrm.so2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ShopDaoTest
 * @createTime 2019年05月21日 17:10:00
 */
public class ShopDaoTest extends BaseDaoTest {
    @Autowired
    private ShopMapper shopMapper;

    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        shop.setOwnerId(1);
        shop.setAreaId(1);
        shop.setShopName("test");
        shop.setEnableStatus(0);
        int i = shopMapper.insert(shop);
        assertEquals(1,i);

    }
    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1);
        shop.setOwnerId(1);
        shop.setAreaId(1);
        shop.setShopName("test1111");
        shop.setEnableStatus(0);
        int i = shopMapper.updateByPrimaryKey(shop);
        assertEquals(1,i);

    }
}
