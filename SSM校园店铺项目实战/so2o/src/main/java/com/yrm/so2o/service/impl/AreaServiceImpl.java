package com.yrm.so2o.service.impl;

import com.yrm.so2o.dao.AreaMapper;
import com.yrm.so2o.entity.Area;
import com.yrm.so2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className AreaServiceImpl
 * @createTime 2019年05月20日 15:14:00
 */
@Service
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public Area getAreaById(Integer id) {
        return areaMapper.selectByPrimaryKey(id);
    }

}
