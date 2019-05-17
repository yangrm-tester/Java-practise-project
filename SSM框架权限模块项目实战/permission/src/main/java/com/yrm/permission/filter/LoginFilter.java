package com.yrm.permission.filter;

import com.yrm.permission.common.RequestHolder;
import com.yrm.permission.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className LoginFilter
 * @createTime 2019年04月09日 15:37:00
 */
public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private static final String REDIRECT_PATH = "/signin.jsp";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //从session中拿用户
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        if (sysUser == null) {
            //未登录则跳转到登录页面 ,这里重定向 是在当前目录的位置重定向，所以重定向的路径最好加上/ 表示绝对路径
            response.sendRedirect(REDIRECT_PATH);
            return;
        }
        //登录则放行 顺便把用户信息和request放到threadLocal中
        RequestHolder.add(sysUser);
        RequestHolder.add(request);
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
