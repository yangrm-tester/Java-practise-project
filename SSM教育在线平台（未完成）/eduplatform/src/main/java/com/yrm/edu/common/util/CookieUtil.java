package com.yrm.edu.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 *
 * @author 杨汝明
 * @version 1.0.0
 * @className CookieUtil
 * @createTime 2019年05月30日 20:07:00
 */
public class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static final String WEIXIN_OPENID = "_weixin_openid_";

    public static final String DEFAULT_PATH = "/";

    /**
     * @param: [request, cookieName]
     * @return: java.lang.String
     * @Description: 通过cookie名称获得cookie值
     */

    public static String getCookie(HttpServletRequest request, String cookieName) {
        if (request == null || StringUtils.isBlank(cookieName)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null && cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equalsIgnoreCase(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * @param: [cookieName, request]
     * @return: void
     * @Description: 通过cooikeName 删除cookie
     */

    public static void deleteCookie(String cookieName, HttpServletResponse response) {
        if (StringUtils.isBlank(cookieName) || response == null) {
            return;
        }
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * @param: [response, cookieName, cookieValue, domain, path, maxAge]
     * @return: javax.servlet.http.Cookie
     * @Description: 新增cookie
     */

    public static Cookie addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain, String path, Integer maxAge) {
        if (response == null || StringUtils.isBlank(cookieName) || StringUtils.isBlank(cookieValue) || StringUtils.isBlank(domain) || maxAge == null) {
            return null;
        }
        if (StringUtils.isBlank(path)) {
            path = DEFAULT_PATH;
        }
        if (maxAge < 0) {
            logger.info("add cookie error.maxAge=[{}], can not < 0", maxAge);
            return null;
        }
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
        return cookie;
    }
}
