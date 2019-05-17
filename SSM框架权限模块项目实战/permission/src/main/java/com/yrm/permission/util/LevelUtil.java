package com.yrm.permission.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className LevelUtil
 * @createTime 2019年03月25日 10:41:00
 * level拼接规则：父级的level+"."+父级的id
 */
public class LevelUtil {
    public static final String LEVEL_ROOT = "0";
    public static final String LEVEL_SEPARATOR = ".";

    public static String generateLevel(Integer parentId, String parentLevel) {
        if (StringUtils.isBlank(parentLevel)){
            return LEVEL_ROOT;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(parentLevel).append(LEVEL_SEPARATOR).append(parentId);
        return sb.toString();
    }

}
