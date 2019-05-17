package com.yrm.permission.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className AdminController
 * @createTime 2019年04月07日 15:59:00
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/index.page")
    public ModelAndView index(){
        return new ModelAndView("admin");
    }

}
