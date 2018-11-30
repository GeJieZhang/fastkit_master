package com.example.fastkit.utils.time_deal;

import java.util.Arrays;
import java.util.Date;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        /*获取当前日期*/
        Date date = new Date();
        String dateStr = TimeUtils.getNowString(TimeUtils.FORMAT_2);
        System.out.println("当前时间:" + dateStr);
        /*今天是本年的第几个星期*/
        int dayOfWeek = TimeUtils.dayOfWeek();
        System.out.println("当前日期对应的星期数" + dayOfWeek + "周");
        /*获取明天日期*/
        Date tomorrowDate = TimeUtils.getOffsetDay(date, 1);
        String tomorrow = TimeUtils.getDateStr(tomorrowDate, TimeUtils.FORMAT_2);
        System.out.println("明天" + tomorrow);
        /*获取昨天日期*/
        Date yesterdayDate = TimeUtils.getOffsetDay(date, -1);
        String yesterday = TimeUtils.getDateStr(yesterdayDate, TimeUtils.FORMAT_2);
        System.out.println("昨天" + yesterday);
        /*获取35分钟后的时间*/
        Date after35MinutesDate = TimeUtils.getOffsetMinute(date, 35);
        String after35Minutes = TimeUtils.getDateStr(after35MinutesDate, TimeUtils.FORMAT_2);
        System.out.println("35分钟后" + after35Minutes);
        /*获取35分钟前的时间*/
        Date before35MinutesDate = TimeUtils.getOffsetMinute(date, -35);
        String before35Minutes = TimeUtils.getDateStr(before35MinutesDate, TimeUtils.FORMAT_2);
        System.out.println("35分钟前" + before35Minutes);
        /*获取一小时后的时间*/
        Date afterOneHourDate = TimeUtils.getOffsetHour(date, 1);
        String afterOneHour = TimeUtils.getDateStr(afterOneHourDate, TimeUtils.FORMAT_2);
        System.out.println("一小时后" + afterOneHour);

        String[] strings = TimeUtils.fecthAllTimeZoneIds();
        System.out.println("时区" + Arrays.toString(strings));

        for (String zoneId : strings) {
            String utcTime = TimeUtils.convertTimeZone(TimeUtils.FORMAT_2, dateStr, TimeUtils.FORMAT_2, zoneId);
            System.out.println(zoneId + "\n   " + utcTime);
        }

    }
}