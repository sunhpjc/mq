package com.sunhp.rocketmq.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author sunhp
 * @Description
 * @Date 2021/4/21 11:40
 **/
public class TimeUtils {
    /**
     * 获取间隔n小时整点时间
     * n等于0即当前整点，n等于正数，即之后n小时整点，n等于负数，即之前n小时整点
     * @param date
     * @param n
     * @return
     */
    public static String getLastHourTime(Date date, int n){
        Calendar ca = setUtils(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY)+n);
        date = ca.getTime();
        return sdf.format(date);
    }

    /**
     * 获取间隔n小时整点时间，返回时间格式
     * @param date
     * @param n
     * @return
     */
    public static Date getLastHourTimeDate(Date date, int n){
        Calendar ca = setUtils(date);
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY)+n);
        date = ca.getTime();
        return date;
    }

    /**
     * 获取间隔n天整点时间，返回时间格式
     * @param date
     * @param n
     * @return
     */
    public static Date getLastDayTimeDate(Date date, int n){
        Calendar ca = setUtils(date);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+n);
        date = ca.getTime();
        return date;
    }

    /**
     * 内部工具类
     * @param date
     * @return
     */
    private static Calendar setUtils(Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca;
    }

    public static void main(String[] args) {
        Date date = new Date();
        Date te = getLastDayTimeDate(date, -30);
        System.out.println("=====te原始数据30天前：" + te);

        String hourTime = getLastHourTime(te, 10);
        System.out.println("=====te30天前，延后10小时：" + hourTime);
    }
}
