package com.yrm.permission.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className IPUtil
 * @createTime 2019年04月24日 16:30:00
 */
public class IpUtil {

    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

    private static final String UNKNOWN_IP = "unknown";

    /**
     * 这是一个 Squid 开发的字段，只有在通过了HTTP代理或者负载均衡服务器时才会添加该项。
     */
    private static final String X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * 这个一般是经过apache http服务器的请求才会有，用apache http做代理时一般会加上Proxy-Client-IP请求头，
     * 而WL-Proxy-Client-IP是他的weblogic插件加上的头
     */
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * 有些代理服务器会加上此请求头
     */
    private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    /**
     * local ip
     */
    private static final String LOCAL_IP_IPV4 = "127.0.0.1";
    private static final String LOCAL_IP_IPV6 = "0:0:0:0:0:0:0:1";

    /**
     * ip长度
     */
    private static final int IP_LENGTH = 15;

    /**
     * ip分隔符
     */
    private static final String SEPARATE_SYMBOL = ",";

    /**
     * X-Real-IP  nginx代理一般会加上此请求头。
     */

    public static String getIpAttr(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        logger.info("Method [getIpAttr] call start .Input params===>[ip={}]", ip);
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HTTP_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HTTP_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals(LOCAL_IP_IPV4) || ip.equals(LOCAL_IP_IPV6)) {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    logger.error("Method [getIpAttr] call error .Error===>[{}]", e.getMessage());
                    return null;
                }
                ip = inetAddress.getHostAddress();
            }
        }
        logger.info("client access ip===>[{}]", ip);
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (StringUtils.isNotBlank(ip) && ip.length() > IP_LENGTH) {
            if (ip.indexOf(SEPARATE_SYMBOL) > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }

        }
        return ip;
    }

}
