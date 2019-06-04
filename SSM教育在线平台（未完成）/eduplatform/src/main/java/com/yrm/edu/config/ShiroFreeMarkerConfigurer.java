package com.yrm.edu.config;

import cn.hutool.extra.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ShiroFreeMarkerConfigurer
 * @createTime 2019年06月04日 15:17:00
 */
public class ShiroFreeMarkerConfigurer  extends FreeMarkerConfigurer{
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();

    }
}
