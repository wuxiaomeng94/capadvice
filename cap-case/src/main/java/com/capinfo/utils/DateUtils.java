package com.capinfo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    public static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    /**
     * 获取对应天数之前的时间
     * @param date
     * @param days
     * @return
     */
    public static Date getBeforeDate(Date date, int days) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        Date d = c.getTime();
        return d;
    }

    /**
     * 获取对应天数之后对应几天的时间
     * @param date
     * @param days
     * @return
     */
    public static Date getAfterDate(Date date, int days) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, +days);
        Date d = c.getTime();
        return d;
    }


    /**
     * 得到对应小时之前的时间
     * @param hourse
     * @return
     */
    public static Date getBeforeHourse(Date date, int hourse) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, -hourse);
        Date d = c.getTime();
        return d;
    }


    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }





}
