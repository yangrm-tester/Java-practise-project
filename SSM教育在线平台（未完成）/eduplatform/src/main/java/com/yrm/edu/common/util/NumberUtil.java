package com.yrm.edu.common.util;

import java.util.Date;
import java.util.Random;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className NumberUtil
 * @createTime 2019年05月29日 16:42:00
 */
public class NumberUtil {

    /**
     * @param: [number]
     * @return: long
     * @Description: 随机生成数字
     */

    public static long generateRandomNuber(int number) {
        double rate1 = Math.pow(10, number - 1);
        double rate2 = rate1 * 9;
        long rate3 = (long) rate1 * 10;
        Random random = new Random();
        double tmp = random.nextDouble() * rate2 + rate1;
        return Math.round(tmp) % rate3;
    }

    /**
     * 序列号
     */
    private static int seq = 0;
    /**
     * 序列号上限
     */
    private static final int LIMIT = 100000;

    private static Date date = new Date();


    /**
     * @param: []
     * @return: long
     * @Description: 根据时间生成唯一编码(考虑并发)
     */

    public static synchronized long timeUniqueNumber() {
        if (seq > LIMIT) {
            seq = 0;
        }
        date.setTime(System.currentTimeMillis());
        /**String.formate 格式 可以在java官网能查到*/
        String dateStr = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
        return Long.parseLong(dateStr);
    }
}
