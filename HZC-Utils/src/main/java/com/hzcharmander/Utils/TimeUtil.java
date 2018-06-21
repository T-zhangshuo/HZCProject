package com.hzcharmander.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 日期格式化
     *
     * @return
     */

    public static String StrformatTime(String timeStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(timeStr);
            return DateformatTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String LongFormatTime(Long time) {
        //转换为日期
        Date date = new Date();
        date.setTime(time);
        return DateformatTime(date);
    }


    public static String DateformatTime(Date date) {
        //转换为日期
        long time = date.getTime();
        if (isThisYear(date)) {//今年
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            if (isToday(date)) { //今天
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内
                    if (minute <= 1) {//一分钟之内，显示刚刚
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                return getString(date, simpleDateFormat);
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        }
    }

    private static String getString(Date date, SimpleDateFormat simpleDateFormat) {
        if (isYestYesterday(date)) {//昨天，显示昨天
            return "昨天 " + simpleDateFormat.format(date);
        } else if (isThisWeek(date)) {//本周,显示周几
            String weekday = null;
            if (date.getDay() == 1) {
                weekday = "周一";
            }
            if (date.getDay() == 2) {
                weekday = "周二";
            }
            if (date.getDay() == 3) {
                weekday = "周三";
            }
            if (date.getDay() == 4) {
                weekday = "周四";
            }
            if (date.getDay() == 5) {
                weekday = "周五";
            }
            if (date.getDay() == 6) {
                weekday = "周六";
            }
            if (date.getDay() == 0) {
                weekday = "周日";
            }
            return weekday + " " + simpleDateFormat.format(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            return sdf.format(date);
        }
    }
    private static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    private static boolean isToday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() == now.getDate());
    }

    private static boolean isYestYesterday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() + 1 == now.getDate());
    }

    private static boolean isThisWeek(Date date) {
        Date now = new Date();
        if ((date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth())) {
            if (now.getDay() - date.getDay() < now.getDay() && now.getDate() - date.getDate() > 0 && now.getDate() - date.getDate() < 7) {
                return true;
            }
        }
        return false;
    }

    private static boolean isThisYear(Date date) {
        Date now = new Date();
        return date.getYear() == now.getYear();
    }
}
