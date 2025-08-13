package com.z.common.utils;

import java.time.LocalDateTime;

public class LocalDateTimeComparisonUtil {

    /**
     * 比较两个 LocalDateTime 对象，判断第一个是否早于第二个
     * @param dateTime1 第一个 LocalDateTime 对象
     * @param dateTime2 第二个 LocalDateTime 对象
     * @return 如果 dateTime1 早于 dateTime2 返回 true，否则返回 false
     */
    public static boolean isBefore(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isBefore(dateTime2);
    }

    /**
     * 比较两个 LocalDateTime 对象，判断第一个是否晚于第二个
     * @param dateTime1 第一个 LocalDateTime 对象
     * @param dateTime2 第二个 LocalDateTime 对象
     * @return 如果 dateTime1 晚于 dateTime2 返回 true，否则返回 false
     */
    public static boolean isAfter(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isAfter(dateTime2);
    }

    /**
     * 比较两个 LocalDateTime 对象，判断它们是否相等
     * @param dateTime1 第一个 LocalDateTime 对象
     * @param dateTime2 第二个 LocalDateTime 对象
     * @return 如果 dateTime1 等于 dateTime2 返回 true，否则返回 false
     */
    public static boolean isEqual(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isEqual(dateTime2);
    }
}    