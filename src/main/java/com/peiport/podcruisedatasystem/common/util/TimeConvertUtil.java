package com.peiport.podcruisedatasystem.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(TimeConvertUtil.class);
    private TimeConvertUtil(){}

    /**
     * 日期转换
     * @param d
     * @return
     */
    public static String dateToString(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(d);
    }
    /**
     * 时间转换成时分秒
     * @param object  一个double类型数字
     * @return int数组,下标0,1 ,2分别表示时分秒
     */
    public static String convertHMS(double object){
        int time =(int) Math.round(object);
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }


    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 字符串转年月日时分秒
     * @param character
     * @return
     */
    public static Date StringToDate(String character) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
           // Date date = sdf.parse(character);
            return sdf.parse(character);

    }

    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String dateFormat(Date date)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         return sdf.format(date);
    }

    public static String dateFormat2(Date date)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        return sdf.format(date);
    }
}
