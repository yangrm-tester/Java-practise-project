package com.yrm.so2o.util;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className PathUtil
 * @createTime 2019年05月21日 17:54:00
 */
public class PathUtil {

    private static final String WIN_SYSTEM_NAME = "win";
    /**
     * 系统分隔符
     */
    private static final String separator = System.getProperty("file.separator");
    private static final String SEPARATOR_SYMBOL = "/";

    public static String getImageBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os != null) {
            if (os.toLowerCase().startsWith(WIN_SYSTEM_NAME)) {
                basePath = "D:/projectdev/image/";
            } else {
                basePath = "/home/dev/images/";
            }
            basePath = basePath.replace(SEPARATOR_SYMBOL, separator);
        }
        return basePath;
    }

    public static String getShopImagePath(Integer shopId) {
        String shopPath = "/upload/item/" + shopId + "/";
        return shopPath.replace(SEPARATOR_SYMBOL, separator);
    }
}
