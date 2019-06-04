package com.yrm.edu.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className PropertiesUtil
 * @createTime 2019年05月29日 17:32:00
 */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Map<String, Properties> proMap = new HashMap<>();

    /**
     * 设置默认的文件
     */
    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";

    /**
     * @param: [file, key]
     * @return: java.lang.Object
     * @Description: 通过file key 获取property的value值
     */

    public static Object getProperty(String file, String key) {
        if (StringUtils.isBlank(file) || StringUtils.isBlank(key)) {
            return null;
        }
        Properties properties = getProperties(file);
        if (properties == null) {
            return null;
        }
        return properties.get(key) != null ? properties.get(key) : null;
    }


    /**
     * @param: [file]
     * @return: java.util.Properties
     * @Description: 通过file去获取properties 没有则重新load
     */

    public static Properties getProperties(String file) {
        if (file == null) {
            return null;
        }
        Properties propertiesInMap = proMap.get(file);
        if (propertiesInMap != null) {
            return propertiesInMap;
        } else {
            Properties properties = new Properties();
            try {
                properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(file));
            } catch (IOException e) {
                logger.error("properties load file error.Error===>[{}]", e.getMessage());
                return null;
            }
            proMap.put(file, properties);
            return properties;
        }
    }

    /**
     * @param: []
     * @return: java.util.Properties
     * @Description: 更新properties 方法不太理解
     */

    public static void updateProperties(Properties properties, String filePath) {
        if (properties == null || filePath == null) {
            return;
        }
        BufferedInputStream bis = null;
        try {
            URI fileUri = PropertiesUtil.class.getClassLoader().getResource(filePath).toURI();
            bis = new BufferedInputStream(new FileInputStream(new File(fileUri)));
            Properties tmpProperties = new Properties();
            tmpProperties.load(bis);
            FileOutputStream fos = new FileOutputStream(new File(fileUri));
            for (Object key : properties.keySet()) {
                tmpProperties.setProperty(String.valueOf(key), String.valueOf(properties.get(key)));
            }
            tmpProperties.store(fos, null);
            bis.close();
            fos.close();
        } catch (Exception e) {
            logger.error("update properties error .Error===>[{}]", e.getMessage());
        }

    }

    /**
     * @param: []
     * @return: java.util.Properties
     * @Description: 获取默认文件的properties
     */

    public static Properties getDefaultProperties() {
        return getProperties(DEFAULT_PROPERTIES_FILE);
    }

    /**
     * @param: [key]
     * @return: java.lang.Object
     * @Description: 通过key获取默认文件property中的value值
     */

    public static Object getProperty(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Properties defaultProperties = getDefaultProperties();
        if (defaultProperties == null) {
            return null;
        }
        return defaultProperties.get(key);
    }

}
