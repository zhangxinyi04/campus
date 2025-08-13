package com.z.common.utils;

import java.util.HashMap;
import java.util.Map;

public class SemesterUtils {
    private static final int FIRST_SEMESTER_START_MONTH = 9;
    private static final int FIRST_SEMESTER_END_MONTH = 1;
    private static final int SECOND_SEMESTER_START_MONTH = 2;
    private static final int SECOND_SEMESTER_END_MONTH = 8;

    /**
     * 判断指定年份和月份属于学校的第一学期还是第二学期
     *
     * @param year  年份
     * @param month 月份（1 - 12）
     * @return 如果是第一学期返回 "第一学期"，如果是第二学期返回 "第二学期"
     */
    public static int getSemester(int year, int month) {
        if (month >= FIRST_SEMESTER_START_MONTH) {
            return 1;
        } else if (month <= FIRST_SEMESTER_END_MONTH) {
            return 1;
        } else if (month >= SECOND_SEMESTER_START_MONTH && month <= SECOND_SEMESTER_END_MONTH) {
            return 2;
        }
        throw new IllegalArgumentException("输入的月份不合法，月份范围应该是 1 - 12。");
    }

    /**
     * 获取指定年份和月份所在学年及学期信息
     *
     * @param year  年份
     * @param month 月份（1 - 12）
     * @return 包含学年和学期信息的 Map，键为 "schoolYear" 和 "semester"
     */
    public static String getSchoolYearAndSemester(int year, int month) {
        int semester = getSemester(year, month);
        String schoolYear;
        if (month >= FIRST_SEMESTER_START_MONTH) {
            schoolYear = year + "-" + (year + 1);
        } else {
            schoolYear = (year - 1) + "-" + year;
        }
        return schoolYear + "-" + semester;
    }

    public static void main(String[] args) {
        int year = 2025;
        int month = 9;
        String info = getSchoolYearAndSemester(year, month);
        System.out.println(info);
    }
}