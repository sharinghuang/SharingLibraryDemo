package com.sharing.library.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <p>日期格式化类</p>
 *
 * @author hxm
 * @version 1.0 (2015-10-19)
 */
public class TimeUtils {
    /** NORMAL 日期+时间格式 */
    public static final String TYPE_NORMAL = "yyyy-MM-dd HH:mm:ss";
    /** 时间戳变换为秒时需要除以1000 */
    public static final int CONSTANCE_1000 = 1000;
    /** 时间格式 */
    public static final String YY_MM_DD = "yy-MM-dd";
    /** 时间格式 */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /** 时间格式 */
    public static final String YYYY_MM_DD_H = "yyyy年MM月dd日";
    /** 时间格式 */
    public static final String HH_MM = "HH:mm";
    /** 时间格式 */
    public static final String HH_MM_SS = "HH:mm:ss";
    /** 时间格式 */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /** 时间格式 */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /** 时间格式 */
    public static final String YYYY_MM_DD_HH_MM_SS_W = "yyyy-MM-dd-HH-mm-ss";
    /** 时间格式 */
    public static final String YY_MM_DD_HH_MM_SS = "yy-MM-dd HH:mm:ss";
    /** 时间格式 */
    public static final String YY_MM_DD_HH_MM = "yy-MM-dd HH:mm";
    /** 一秒 */
    public static final long ONE_SECOND = 1000;
    /** 一分钟 */
    public static final long ONE_MINUTE = 60 * ONE_SECOND;
    /** 一小时 */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    /** 一天 */
    public static final long ONE_DAY = 12 * ONE_HOUR;

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return 当前时间戳
     */
    public static String getTimeStamp() {
        long time = System.currentTimeMillis();
        return String.valueOf(time / CONSTANCE_1000);
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     *
     * @return 转换后的时间戳 单位秒
     */
    public static long date2TimeStampLong(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
            return sdf.parse(dateStr).getTime() / CONSTANCE_1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒
     * @param format  日期格式
     *
     * @return 转换后的日期
     */
    public static String timeStamp2Date(long seconds, String format) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
            result = sdf.format(new Date(seconds * CONSTANCE_1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dt 需要转换的Date
     *
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    /**
     * Description：格式化时间戳为指定日期格式
     *
     * @param time  时间戳，long 类型
     * @param style 格式
     *
     * @return { String }
     *
     * @throws
     */
    public static String parseTimeStrToDate(Long time, String style) {
        SimpleDateFormat format = new SimpleDateFormat(style, Locale.CHINESE);
        String d = format.format(time);
        return d;
    }


    /**
     * 得到几天后的时间
     *
     * @param d   时间
     * @param day 多少天后
     *
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }


    /**
     * 判断是否是同一天
     *
     * @param history
     * @param curr
     *
     * @return
     */
    public static boolean isSameDayOfMillis(long history, long curr) {
        try {
            int historyTemp = Integer.parseInt(parseTimeStrToDate(history, "dd"));
            int currTemp = Integer.parseInt(parseTimeStrToDate(curr, "dd"));
            if (historyTemp == currTemp) {
                //同一天
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
