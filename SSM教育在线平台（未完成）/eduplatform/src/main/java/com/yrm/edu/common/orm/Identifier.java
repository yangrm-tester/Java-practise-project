package com.yrm.edu.common.orm;

import java.io.Serializable;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className Identifier
 * @createTime 2019年05月29日 09:57:00
 *
 * 泛型 向上通配符  适合取 不适合存
 */

public interface Identifier<T extends Serializable> {
    /**
     *  获取T
     *  @param: []
     *  @return: T
     *  @Description:
     */

    T getId();
}
