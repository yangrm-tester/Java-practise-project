package com.yrm.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.Authenticator;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className AuthController
 * @createTime 2019年06月04日 15:08:00
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping("/login")
    public ModelAndView  login(){
        return new ModelAndView("auth/login");
    }
}
