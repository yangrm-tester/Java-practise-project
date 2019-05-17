package com.yrm.permission.controller;

import com.yrm.permission.Constant.Constant;
import com.yrm.permission.entity.SysUser;
import com.yrm.permission.service.SysUserService;
import com.yrm.permission.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于 用户页面登录注销操作的，不是后台管理的功能
 * 15029664551 123456
 *
 * @author 杨汝明
 * @version 1.0.0
 * @className UserController
 * @createTime 2019年04月07日 15:38:00
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private static final String LOGIN_MSG_PARMS_EMPTY = "用户名或者密码不可以为空";

    private static final String LOGIN_MSG_USER_NOT_EXISTS = "登录用户名不存在";

    private static final String LOGIN_MSG_PARMS_ERROR = "用户名或者密码错误";

    private static final String LOGIN_MSG_ERROR = "登录异常";

    private static final String LOGIN_MSG_USER_STATUSERROR = "登录用户状态不正确，请联系管理员";

    private static final String PATH = "signin.jsp";

    private static final String REDIRECT_URL = "/admin/index.page";

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     *
     * @Param: [request, response, username, password, ret]
     * @return: void
     * @Description:
     */

    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("ret") String ret) {
        logger.info("Method [login] call start. Input parmas===[username={},password={}]", username, password);
        //用户页面展示的错误信息
        String errorMsg = "";
        SysUser sysUser = new SysUser();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            errorMsg = LOGIN_MSG_PARMS_EMPTY;
        }
        //根据用户名去查找用户是否存在
        try {
            sysUser = sysUserService.findUserByKeyword(username);
        } catch (Exception e) {
            logger.error("Method [login] call error.Error message===>[{}]", e.getMessage());
            errorMsg = LOGIN_MSG_ERROR;
        }
        if (sysUser == null) {
            errorMsg = LOGIN_MSG_USER_NOT_EXISTS;
        } else if (!sysUser.getPassword().equals(Md5Util.encrypt(password))) {
            errorMsg = LOGIN_MSG_PARMS_ERROR;
        } else if (!sysUser.getStatus().equals(Constant.USER_STATUS_NORMAL)) {
            errorMsg = LOGIN_MSG_USER_STATUSERROR;
        } else {
            //login sucess
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)) {
                try {
                    response.sendRedirect(ret);
                } catch (Exception e) {
                    logger.error("login fail.Error message===>{}", e.getMessage());
                }
            } else {
                try {
                    response.sendRedirect(REDIRECT_URL);
                } catch (Exception e) {
                    logger.error("login fail.Error message===>{}", e.getMessage());
                }
            }
        }
        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        try {
            request.getRequestDispatcher(PATH).forward(request, response);
        } catch (Exception e) {
            logger.error("login fail.Error message===>{}", e.getMessage());
        }
    }


    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        try {
            response.sendRedirect(REDIRECT_URL);
        } catch (Exception e) {
            logger.error("logout fail.Error message===>{}", e.getMessage());
        }
    }


}
