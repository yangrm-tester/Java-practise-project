package com.yrm.permission.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className MD5util
 * @createTime 2019年04月07日 11:35:00
 */
public class Md5Util {
    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public static final String encrypt(String str) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] strBytes = str.getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            md5.update(strBytes);
            //获得密文
            byte[] digestBytes = md5.digest(strBytes);
            //将密文转化为16进制的形式的字符串
            int digestBytesLength = digestBytes.length;
            char[] md5Str = new char[digestBytesLength * 2];
            int k = 0;
            for (int i = 0; i < digestBytesLength; i++) {
                byte byte0 = digestBytes[i];
                md5Str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                md5Str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(md5Str);
        } catch (Exception e) {
            logger.error("generate md5 error. Error message===>[{}]", e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
    }
}
