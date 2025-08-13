package com.z.system.util;

import com.alibaba.fastjson2.JSONObject;
import com.z.system.domain.SysEventCycle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class PunchDateCalculator {

    public enum Frequency {
        DAILY,
        WEEKLY,
        MONTHLY
    }

    public static List<SysEventCycle> calculatePunchDates(LocalDateTime startDateTime, LocalDateTime endDateTime, Frequency frequency) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        switch (frequency) {
            case DAILY:
                return calculateDaily(startDateTime, endDateTime);
            case WEEKLY:
                return calculateWeekly(startDateTime, endDateTime);
            case MONTHLY:
                return calculateMonthly(startDateTime, endDateTime);
            default:
                throw new IllegalArgumentException("不支持的频率类型");
        }
    }

    private static List<SysEventCycle> calculateDaily(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<SysEventCycle> periods = new ArrayList<>();
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();

        LocalDate currentDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        int i = 1;
        while (!currentDate.isAfter(endDate)) {

            LocalDateTime periodStart = LocalDateTime.of(currentDate, startTime);
            LocalDateTime periodEnd = LocalDateTime.of(currentDate, endTime);
//            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String start = periodStart.format(df);
//            String end = periodEnd.format(df);

            if (!periodEnd.isBefore(periodStart)) {
                SysEventCycle eventCycle = new SysEventCycle();
                eventCycle.setStartTime(periodStart);
                eventCycle.setEndTime(periodEnd);
                eventCycle.setEventCycle(i);
                periods.add(eventCycle);
            }
            currentDate = currentDate.plusDays(1);
            i += 1;
        }

        return periods;
    }

    private static List<SysEventCycle> calculateWeekly(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<SysEventCycle> periods = new ArrayList<>();
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        LocalDate firstMonday = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastSunday = endDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        int i = 1;
        for (LocalDate weekStart = firstMonday; !weekStart.isAfter(lastSunday); weekStart = weekStart.plusWeeks(1)) {
            LocalDate weekEnd = weekStart.plusDays(6);

            LocalDateTime periodStart = weekStart.atStartOfDay();
            LocalDateTime periodEnd = LocalDateTime.of(weekEnd, LocalTime.MAX);

            LocalDateTime adjustedStart = periodStart.isBefore(startDateTime) ? startDateTime : periodStart;
            LocalDateTime adjustedEnd = periodEnd.isAfter(endDateTime) ? endDateTime : periodEnd;
//            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String start = adjustedStart.format(df);
//            String end = adjustedEnd.format(df);
            if (!adjustedStart.isAfter(adjustedEnd)) {
                SysEventCycle eventCycle = new SysEventCycle();
                eventCycle.setStartTime(adjustedStart);
                eventCycle.setEndTime(adjustedEnd);
                eventCycle.setEventCycle(i);
                periods.add(eventCycle);
            }
            i += 1;
        }

        return periods;
    }

    private static List<SysEventCycle> calculateMonthly(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<SysEventCycle> periods = new ArrayList<>();
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        LocalDate currentMonthStart = startDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastMonthEnd = endDate.with(TemporalAdjusters.lastDayOfMonth());
        int i = 1;
        while (!currentMonthStart.isAfter(lastMonthEnd)) {
            LocalDate monthEnd = currentMonthStart.with(TemporalAdjusters.lastDayOfMonth());

            LocalDateTime periodStart = currentMonthStart.atStartOfDay();
            LocalDateTime periodEnd = LocalDateTime.of(monthEnd, LocalTime.MAX);

            LocalDateTime adjustedStart = periodStart.isBefore(startDateTime) ? startDateTime : periodStart;
            LocalDateTime adjustedEnd = periodEnd.isAfter(endDateTime) ? endDateTime : periodEnd;

//            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String start = adjustedStart.format(df);
//            String end = adjustedEnd.format(df);
            if (!adjustedStart.isAfter(adjustedEnd)) {
                SysEventCycle eventCycle = new SysEventCycle();
                eventCycle.setStartTime(adjustedStart);
                eventCycle.setEndTime(adjustedEnd);
                eventCycle.setEventCycle(i);
                periods.add(eventCycle);
            }

            currentMonthStart = currentMonthStart.plusMonths(1);
            i += 1;
        }

        return periods;
    }

    // 示例用法
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.of(2025, 4, 3, 00, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 4, 5, 23, 59, 59);

        System.out.println("每日打卡:");
        calculatePunchDates(start, end, Frequency.DAILY)
                .forEach(p -> System.out.println(JSONObject.toJSONString(p)));

        System.out.println("\n每周打卡:");
        calculatePunchDates(start, end, Frequency.WEEKLY)
                .forEach(p -> System.out.println(JSONObject.toJSONString(p)));

        System.out.println("\n每月打卡:");
        calculatePunchDates(start, end, Frequency.MONTHLY)
                .forEach(p -> System.out.println(JSONObject.toJSONString(p)));
    }
}