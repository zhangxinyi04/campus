package com.z.system.util;

public class NumberToChineseUtil {
    public static String convert(int number) {
        String[] chineseNumbers = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        if (number >= 1 && number <= 10) {
            return chineseNumbers[number - 1];
        }
        return String.valueOf(number);
    }

}