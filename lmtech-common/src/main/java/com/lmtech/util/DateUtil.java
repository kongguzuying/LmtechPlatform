package com.lmtech.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date parse(String dateStr) {
        return parse(dateStr, "yyyy-MM-dd", null);
    }

    public static Date parse(String dateStr, String pattern) {
        return parse(dateStr, pattern, null);
    }

    public static Date parse(String dateStr, String pattern, Date defaultValue) {
        if (dateStr == null || dateStr.equals("")) return defaultValue;

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            LoggerManager.error(e);
            return defaultValue;
        }
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        } else {
            return null;
        }
    }

    public static Date addDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);

        return c.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);

        return c.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hours);

        return c.getTime();
    }

    public static Date addSecond(Date date, int seconds) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, seconds);

        return c.getTime();
    }

    /**
     * 获取日期相减的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static double getDaySub(Date beginDate, Date endDate) {
        if (beginDate.compareTo(endDate) >= 0) {
            throw new IllegalArgumentException("结束日期必须大于开始日期");
        }
        double days = (double) (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        return (double) Math.round(days * 100) / 100;
    }
    
    /**
     *将字符串格式yyyyMMddhhmmss的字符串转为日期，格式"yyyy-MM-dd hh:mm:ss"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static String strToDateFormat(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        formatter.setLenient(false);
        Date newDate= formatter.parse(date);
        formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return formatter.format(newDate);
    }
    
    public static void main(String argg[]) {
        Date date = new Date();

        Date newDate = addHours(date, 5);

        System.out.println(DateUtil.format(newDate, "yyyy-MM-dd HH:mm:ss"));

        System.out.println(getDaySub(date, newDate));

        Date monthDate = parse("2018-12-12");

        Date newDate1 = addMonth(monthDate, 12);

        System.out.println("month day:" + getDaySub(monthDate, newDate1));
    }
}
