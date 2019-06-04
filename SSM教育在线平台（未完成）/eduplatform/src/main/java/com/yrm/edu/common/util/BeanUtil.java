package com.yrm.edu.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className BeanUtil
 * @createTime 2019年05月29日 11:07:00
 */
public class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private static final String SERIAL_VERSION_UID = "serialVersionUID";


    /**
     * @param: [clazz]
     * @return: java.util.Map<java.lang.String,com.yrm.edu.common.util.BeanField>
     * @Description: 获取类的所有属性
     */

    public static Map<String, BeanField> getAllFields(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        Map<String, BeanField> beanFieldMap = new LinkedHashMap<String, BeanField>();
        try {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null) {
                for (Field field : fields) {
                    /**把序列化ID给去掉*/
                    if (!SERIAL_VERSION_UID.equals(field.getName())) {
                        String name = field.getName();
                        String colName = BeanUtil.fieldToColumn(name);
                        BeanField beanField = new BeanField();
                        beanField.setField(field);
                        beanField.setColumnName(colName);
                        beanFieldMap.put(colName, beanField);
                    }
                }
                /**递归获取父类的filed*/
                Class<?> superclass = clazz.getSuperclass();
                Map<String, BeanField> superFieldMap = getAllFields(superclass);
                if (superclass != null) {
                    beanFieldMap.putAll(superFieldMap);
                }
                if (beanFieldMap.size() == 0) {
                    return null;
                }
                return beanFieldMap;
            }

        } catch (Exception e) {
            logger.error("get clazz field error.Errors===>[{}]",e.getMessage());
            return null;
        }
        return null;
    }


    /**
     * 转换带下划线的字符串，后面第一个字母为 第一个字母大小写根据标识符来判定
     */
    public static String columnToField(String column, boolean flag) {
        if (StringUtils.isBlank(column)) {
            return null;
        }
        String[] columnArry = column.split("_");
        if (null != columnArry && columnArry.length > 1) {
            StringBuilder sb = new StringBuilder();
            sb.append(columnArry[0]);
            /**拼接字符串*/
            for (int i = 1; i < columnArry.length; i++) {
                sb.append(columnArry[i].substring(0, 1).toUpperCase()).append(columnArry[i].substring(1));
            }
            return sb.toString();
        } else {
            if (flag) {
                return column.substring(0, 1).toUpperCase() + column.substring(1);
            } else {
                return column;
            }
        }
    }

    /**
     * 转换将第一个字母大写变成小写，并在前面加下划线
     */
    public static String fieldToColumn(String field) {
        if (StringUtils.isBlank(field)) {
            return null;
        }
        /**转换为字符数组 来遍历判断大写字母的ASCII码  A是65 Z是94*/
        char[] fieldChars = field.toCharArray();
        String retStr = "";
        for (int i = 0; i < fieldChars.length; i++) {
            if (fieldChars[i] > 64 && fieldChars[i] < 91) {
                /**说明当前字符是大写字母*/
                retStr += ("_" + fieldChars[i]).toLowerCase();
            } else {
                retStr += fieldChars[i];
            }
        }
        return retStr;
    }

    /**
     * 首字母变大写
     */
    public static String upperCaseFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    /**
     * 首字母变成小写
     */

    public static String lowerCaseFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }


    public static void main(String[] args) {
        System.out.println(lowerCaseFirst("UserName"));
        System.out.println(2 % 4);
    }

}
