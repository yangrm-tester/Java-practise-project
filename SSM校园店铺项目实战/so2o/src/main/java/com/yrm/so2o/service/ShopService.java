package com.yrm.so2o.service;

import com.yrm.so2o.Exception.ShopServiceException;
import com.yrm.so2o.dto.ShopResponseDTO;
import com.yrm.so2o.entity.Shop;

import java.io.File;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ShopService
 * @createTime 2019年05月22日 14:02:00
 */
public interface ShopService {

    ShopResponseDTO addShop(Shop shop, File ShopImg)  throws ShopServiceException;

}
