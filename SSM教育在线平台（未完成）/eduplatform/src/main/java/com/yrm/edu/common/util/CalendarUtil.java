package com.yrm.edu.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author 杨汝明
 * @version 1.0.0
 * @className CalenderUtil
 * @createTime 2019年05月29日 18:19:00
 * 日期工具类
 */
public class CalendarUtil {

    private static final Logger logger = LoggerFactory.getLogger(CalendarUtil.class);

    private static final String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String FIRST_DAY_OF_MONTH = "fist_day_of_month";
    private static final String END_DAY_OF_MONTH = "end_day_of_month";


    /**
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @Description: 获得当前年的前五年数据
     */

    public static List<String> getPreFiveYears() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<String> preFiveYears = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            preFiveYears.add((year - i) + "");
        }
        return preFiveYears;
    }


    /**
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @Description: 获得一年的月份
     */

    public static List<String> getFullYearMonths() {
        List<String> fullYearMonths = new ArrayList<String>();
        for (int i = 1; i < 13; i++) {
            fullYearMonths.add(i + "");
        }
        return fullYearMonths;
    }

    /**
     * @param: []
     * @return: java.util.Date
     * @Description: 获得N天后的日期
     */

    public static Date getNextNDay(Date startDate, Integer n) {
        if (startDate != null || n != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, n);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * @param: [startDate, n]
     * @return: java.util.Date
     * @Description: 获得N天后的日期0点0分0秒
     */

    public static Date getNextNDayBegin(Date startDate, Integer n) {
        if (startDate != null || n != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, n);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * @param: [startDate, n]
     * @return: java.util.Date
     * @Description: 获得N天后的日期23点59分59秒
     */

    public static Date getNextNDayEnd(Date startDate, Integer n) {
        if (startDate != null || n != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, n);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * @param: [endDate, n]
     * @return: java.util.Date
     * @Description: 获得N天前的日期
     */

    public static Date getBeforeNDay(Date endDate, Integer n) {
        if (endDate != null || n != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.DATE, -n);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * @param: [startDate, n]
     * @return: java.util.Date
     * @Description: 获得N天前的日期0点0分0秒
     */

    public static Date getBeforeNDayBegin(Date startDate, Integer n) {
        if (startDate != null || n != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, -n);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * @param: [startDate, n]
     * @return: java.util.Date
     * @Description: 获得N天前的日期23点59分59秒
     */

    public static Date getBeforeNDayEnd(Date startDate, Integer n) {
        if (startDate != null || n != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, -n);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @Description: 获得当前年
     */

    public static String getYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @Description: 获得当前月
     */

    public static String getMonth() {
        /**月份是0到11*/
        int month = Calendar.getInstance().get(Calendar.MONTH);
        return String.valueOf(month + 1);
    }

    /**
     * @param: [format]
     * @return: java.lang.String
     * @Description: 获得当前天
     */

    public static String getToday(String format) {
        if (StringUtils.isBlank(format)) {
            format = DEFAULT_FORMAT_PATTERN;
        }
        return DateUtil.Date2Str(Calendar.getInstance().getTime(), format);
    }


    /**
     * @param: [year, month]
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @Description: 获取月初和月末日 放到map中
     */

    public static Map<String, String> getFirstAndEndTime(String yearStr, String monthStr) {
        Map<String, String> dateStrMap = new HashMap<String, String>(2);
        if (StringUtils.isNotBlank(yearStr) && StringUtils.isNotBlank(monthStr)) {
            //获得月初时间
            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr) - 1;
            Calendar calendarBegin = Calendar.getInstance();
            Calendar calendarEnd = Calendar.getInstance();
            calendarBegin.set(year, month, 1, 0, 0, 0);
            String firtDay = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN).format(calendarBegin.getTime());
            calendarEnd.set(Calendar.YEAR, year);
            calendarEnd.set(Calendar.MONTH, month);
            int maxDate = calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendarEnd.set(year, month, maxDate, 23, 59, 59);
            String endDay = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN).format(calendarEnd.getTime());
            dateStrMap.put(FIRST_DAY_OF_MONTH, firtDay);
            dateStrMap.put(END_DAY_OF_MONTH, endDay);
            return dateStrMap;
        }
        return null;
    }

    /**
     * @param: []
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @Description: 获得前一个月
     */

    public static Map<String, String> getPreMonth() {
        Map<String, String> map = new HashMap<>(2);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        map.put("year", year + "");
        map.put("month", month + "");
        return map;
    }


    /**
     * @param: [startDate, endMonth] 参数格式 一定要是 yyyy-MM-dd 这种类型的
     * @return: java.util.List<java.lang.String>
     * @Description: 获取开始月份和结束月份中间的所有月份，包括开始月份和结束月份
     */

    public static List<String> getMiddleMonths(String startDate, String endDate) {
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            List<String> months = new ArrayList<String>();
            String[] startDateArray = startDate.split("-");
            String[] endDateArray = endDate.split("-");
            if (startDateArray.length <= 1 && endDateArray.length <= 1) {
                return null;
            }
            Integer startYear = Integer.parseInt(startDateArray[0]);
            Integer startMonth = Integer.parseInt(startDateArray[1]);
            Integer endYear = Integer.parseInt(endDateArray[0]);
            Integer endMonth = Integer.parseInt(endDateArray[1]);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(startYear, startMonth - 1, 1);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endYear, endMonth - 1, 1);
            while (startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()) {
                months.add(new SimpleDateFormat("yyyy-MM").format(startCalendar.getTime()));
                startCalendar.add(Calendar.MONTH, 1);
            }
            return months;

        }
        return null;

    }

    /**
     * @param: [startDate, endMonth] 参数格式 一定要是 yyyy-MM-dd 这种类型的
     * @return: java.util.List<java.lang.String>
     * @Description: 获取开始月份和结束月份中间的所有天数，包括开始开始天数和结束天数
     */

    public static List<String> getMiddleDays(String startDate, String endDate) {
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            List<String> days = new ArrayList<String>();
            String[] startDateArray = startDate.split("-");
            String[] endDateArray = endDate.split("-");
            if (startDateArray.length <= 1 && endDateArray.length <= 1) {
                return null;
            }
            Integer startYear = Integer.parseInt(startDateArray[0]);
            Integer startMonth = Integer.parseInt(startDateArray[1]);
            Integer startDay = Integer.parseInt(startDateArray[2]);
            Integer endYear = Integer.parseInt(endDateArray[0]);
            Integer endMonth = Integer.parseInt(endDateArray[1]);
            Integer endDay = Integer.parseInt(endDateArray[2]);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(startYear, startMonth - 1, startDay);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endYear, endMonth - 1, endDay);
            while (startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()) {
                days.add(new SimpleDateFormat("yyyy-MM-dd").format(startCalendar.getTime()));
                startCalendar.add(Calendar.DATE, 1);
            }
            return days;

        }
        return null;
    }

    /**
     * @param: [d1, d2]
     * @return: boolean
     * @Description: 比较2个日期 天是否相等
     */

    public static boolean dateCompare(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d1);
        int day1 = calendar.get(Calendar.DATE);
        calendar.setTime(d2);
        int day2 = calendar.get(Calendar.DATE);
        return day1 == day2;
    }

    /**
     * @param: [date, startDate, endDate]
     * @return: boolean
     * @Description: 判断日期在起始或者终止之间 不包含
     */

    public static boolean isDateBetweenNotContain(Date date, Date startDate, Date endDate) {
        if (date == null || startDate == null || endDate == null) {
            return false;
        }
        return startDate.getTime() < date.getTime() && endDate.getTime() > date.getTime();
    }

    /**
     * @param: [date, startDate, endDate]
     * @return: boolean
     * @Description: 判断日期在起始或者终止之间 包含
     */

    public static boolean isDateBetweenContain(Date date, Date startDate, Date endDate) {
        if (date == null || startDate == null || endDate == null) {
            return false;
        }
        return startDate.getTime() <= date.getTime() && endDate.getTime() >= date.getTime();
    }

    /**
     *  @param: []
     *  @return: long
     *  @Description:  获取当前毫秒数 类似于时间戳
     */

    public static long getTimeInMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }



    public static void main(String[] args) {
        System.out.println(Calendar.getInstance().getTimeInMillis());
    }

}
