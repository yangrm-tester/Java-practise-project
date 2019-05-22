package com.yrm.so2o.service.impl;

import com.yrm.so2o.Exception.ShopServiceException;
import com.yrm.so2o.dao.ShopMapper;
import com.yrm.so2o.dto.ShopResponseDTO;
import com.yrm.so2o.entity.Shop;
import com.yrm.so2o.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ShopServiceImpl
 * @createTime 2019年05月22日 14:02:00
 */
@Service
public class ShopServiceImpl implements ShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopMapper shopMapper;


    @Override
    public ShopResponseDTO addShop(Shop shop, File ShopImg) throws ShopServiceException {
        logger.info("Method []");
        return null;
    }
}
