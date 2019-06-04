package com.yrm.edu.common.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className BeanField
 * @createTime 2019年05月29日 11:05:00
 */
public class BeanField {
    private String columnName;

    private Field field;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String cloumnName) {
        this.columnName = cloumnName;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
