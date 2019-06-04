package com.yrm.edu.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 加密工具类
 * @author 杨汝明
 * @version 1.0.0
 * @className EncrypUtil
 * @createTime 2019年05月30日 18:03:00
 */
public class EncrypUtil {
    private static final Logger logger = LoggerFactory.getLogger(EncrypUtil.class);

    private static final String DEFAULT_URL_ENCODING = "UTF-8";


    /**
     * MD5加密 返回是32位小写
     */
    public static String MD5Encode(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        String md5Code;
        try {
            md5Code = DigestUtils.md5Hex(source.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("MD5 encode error .Error===>[{}]", e.getMessage());
            return null;
        }
        return md5Code;
    }

    /**
     * @param: [source]
     * @return: java.lang.String
     * @Description: URL encode加密  存在空格encode后存在+号
     */

    public static String urlEncode(String source) {
        String encode;
        try {
            encode = URLEncoder.encode(source, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL encode error. Error===>[{}]", e.getMessage());
            return null;
        }
        /**存在一个空格转换变成+号的问题，使用%20都是通用的*/
        return encode.replace("+", "%20");
    }


    /**
     * @param: [source]
     * @return: java.lang.String
     * @Description: URL encode解密
     */

    public static String urlDecode(String source) {
        String decode;
        try {
            decode = URLDecoder.decode(source, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL encode error. Error===>[{}]", e.getMessage());
            return null;
        }
        return decode;
    }

    /**
     * Base64编码.
     */
    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符�?,/=转为其他字符, 见RFC3548).
     */
    public static String base64UrlSafeEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }
}
