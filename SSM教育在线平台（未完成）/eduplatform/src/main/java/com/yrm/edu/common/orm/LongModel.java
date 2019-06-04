package com.yrm.edu.common.orm;

import java.io.Serializable;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className LongModel
 * @createTime 2019年05月29日 10:05:00
 */
public class LongModel implements Identifier<Long>,Serializable {

    private static final long serialVersionUID=7978917143723588623L;

    private long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
