package com.yrm.so2o.dao;

import com.yrm.so2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className AreaDaoTest
 * @createTime 2019年05月20日 17:11:00
 */
public class AreaDaoTest extends BaseDaoTest {

    @Autowired
    private AreaMapper areaMapper;

    @Test
    public void testSelectByPrimaryKey(){
        Area area = areaMapper.selectByPrimaryKey(1);
        assertEquals("beijing",area.getAreaName());
    }
}
