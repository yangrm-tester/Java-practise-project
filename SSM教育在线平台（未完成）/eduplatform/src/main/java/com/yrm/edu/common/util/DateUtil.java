package com.yrm.edu.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author 杨汝明
 * @version 1.0.0
 * @className DateUtil
 * @createTime 2019年05月29日 11:56:00
 */
public class DateUtil {

    private static final String COMPAT_PATTERN = "yyyyMMdd";

    private static final String COMPAT_FULL_PATTERN = "yyyyMMddHHmmss";

    private static final String COMMON_PATTERN = "yyyy-MM-dd";

    private static final String COMMON_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String SLASH_PATTERN = "yyyy/MM/dd";

    private static final String CHINESE_PATTERN = "yyyy年MM月dd日";

    private static final String CHINESE_FULL_PATTERN = "yyyy年MM月dd日 HH时mm分ss秒";



    /**
     * @param: [date]
     * @return: java.lang.String
     * @Description: 日期转为字符串
     */
    public  static String Date2Str(Date date) {
        if (date != null) {
            return new SimpleDateFormat().format(date);
        }
        return null;
    }

    /**
     * 字符串转日期
     */
    public  static Date Str2Date(String dateStr) throws ParseException {
        if (StringUtils.isNotBlank(dateStr)) {
            return new SimpleDateFormat().parse(dateStr);
        }
        return null;
    }

    /**
     * 日期按照格式转字符串
     */
    public static String Date2Str(Date date, String format) {
        if (date == null || StringUtils.isBlank(format)) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 字符串按照格式转日期
     */
    public  static Date Str2Date(String dateStr, String format) throws ParseException {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)) {
            return null;
        }
        return new SimpleDateFormat(format).parse(dateStr);
    }

    /**
     * @param: [date]
     * @return: int
     * @Description: 根据日期获取星期的天数 ，周一是1 礼拜天是7
     */

    public static int getWeekDay(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayNumber == Calendar.SUNDAY) {
            dayNumber = 7;
        } else {
            dayNumber = dayNumber - 1;
        }
        return dayNumber;
    }
}
