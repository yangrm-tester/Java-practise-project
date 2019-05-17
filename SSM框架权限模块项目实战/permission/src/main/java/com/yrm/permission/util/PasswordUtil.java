package com.yrm.permission.util;

import java.util.Random;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className PasswordUtil
 * @createTime 2019年04月07日 10:39:00
 */
public class PasswordUtil {

    /**
     * 定义密码的可用字符和数字
     */
    private static final String[] LETERS = {
            "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    };

    private static final String[] NUMBERS = {
            "0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9"
    };

    private static final String[] CHARACTERS = {
            "!", "@", "#", "$", "%", "^", "&", "*",
            "(", ")"
    };

    private static final Integer DEFAULT_LENGTH = 8;

    public static synchronized String generatePassword() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        int passwordLength = random.nextInt(3) + DEFAULT_LENGTH;
        for (int i = 1; i <= passwordLength; i++) {
            if (i % 3 == 0) {
                sb.append(LETERS[random.nextInt(LETERS.length)]);
            } else if (i % 3 == 1) {
                sb.append(NUMBERS[random.nextInt(NUMBERS.length)]);
            } else if (i % 3 == 2) {
                sb.append(CHARACTERS[random.nextInt(CHARACTERS.length)]);
            } else {
                sb.append(LETERS[random.nextInt(LETERS.length)]);
            }
        }
        return sb.toString();
    }
}
