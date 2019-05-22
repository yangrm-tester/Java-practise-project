package com.yrm.so2o.service;

import com.yrm.so2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className AreaServiceTest
 * @createTime 2019年05月20日 17:34:00
 */
public class AreaServiceTest  extends BaseServiceTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void testAreaService(){
        Area area = areaService.getAreaById(1);
        assertEquals("beijing",area.getAreaName());
    }

}
