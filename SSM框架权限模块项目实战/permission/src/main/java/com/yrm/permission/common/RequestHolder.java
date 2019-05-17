package com.yrm.permission.common;

import com.yrm.permission.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className RequestHolder
 * @createTime 2019年04月09日 15:49:00
 */
public class RequestHolder {
    private static final ThreadLocal<SysUser> sysUserThreadLocal = new ThreadLocal<SysUser>();
    private static final ThreadLocal<HttpServletRequest> requstThreadLocal = new ThreadLocal<HttpServletRequest>();

    public static void add(SysUser sysUser) {
        sysUserThreadLocal.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requstThreadLocal.set(request);
    }

    public static SysUser getSysUser() {
        return sysUserThreadLocal.get();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return requstThreadLocal.get();
    }

    public static void remove() {
        requstThreadLocal.remove();
        requstThreadLocal.remove();
    }
}
