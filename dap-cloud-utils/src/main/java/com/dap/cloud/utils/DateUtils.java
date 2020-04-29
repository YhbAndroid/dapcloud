package com.dap.cloud.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {"yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
            "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length() > 0) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
                * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
                + sss;
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        return date;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    public static String getOldSeven() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        // System.out.println(formatDate(parseDate("2010/3/6")));
        // System.out.println(getDate("yyyy年MM月dd日 E"));
        // long time = new Date().getTime()-parseDate("2012-11-19").getTime();
        // System.out.println(time/(24*60*60*1000));
    }
    /**
     * 功能：判断两个日期段内对应一周的周几，并返回具体的日期
     * @param date1 开始时间
     * @param date2 结束时间
     * @return
     * @throws Exception
     */
    public static HashMap<String,String> getEveryWeek(Date date1, Date date2) throws Exception{
        HashMap<String, String> map = new HashMap<String, String>();
        Calendar start = Calendar.getInstance();
        start.setTime(date1);
        while(date2.after(start.getTime())){
            String date = formatDate(start.getTime(), null);
            int week=start.get(Calendar.DAY_OF_WEEK)-1;
            if(week == 0){//0代表周日，6代表周六  ,5代表周五
                map.put(date, "0");
            }else if(week == 1){
                map.put(date, "1");
            }else if(week == 2){
                map.put(date, "2");
            }else if(week == 3){
                map.put(date, "3");
            }else if(week == 4){
                map.put(date, "4");
            }else if(week == 5){
                map.put(date, "5");
            }else if(week == 6){
                map.put(date, "6");
            }
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        return map;
    }

    /**
     * 功能：判断某一个日期是否在两个日期范围内
     * @return
     * @throws Exception
     */
    public static boolean isBetween(String date,String startStr,String endStr) throws Exception{
        Date startDate = parseDate(startStr);
        Date endDate = parseDate(endStr);
        Date now = parseDate(date, "yyyy-MM-dd");
        //判断当前时间是否在时间段内
        if((startDate.getTime() <= now.getTime()) && (now.getTime() <= endDate.getTime())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取两个日期之间的天数(trunc)
     *
     * @param before
     * @param after
     * @return
     */
    public static double getTruncDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = DateUtils.parseDate(DateUtils.formatDate(before, "yyyy-MM-dd")).getTime();
        long afterTime = DateUtils.parseDate(DateUtils.formatDate(after, "yyyy-MM-dd")).getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取两个字符串日期之间的天数
     * @param stime
     * @param etime
     * @return
     */
    public static List<String> getBetweenDays(String stime, String etime){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date sdate = null;
        Date eDate = null;

        try {
            sdate = df.parse(stime);
            eDate = df.parse(etime);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        long betweendays = (long) ((eDate.getTime() - sdate.getTime())
                / (1000 * 60 * 60 * 24) + 0.5);// 天数间隔
        Calendar c = Calendar.getInstance();
        List<String> list = new ArrayList<String>();
        while (sdate.getTime() <= eDate.getTime()) {
            list.add(df.format(sdate));
            c.setTime(sdate);
            c.add(Calendar.DATE, 1); // 日期加1天
            sdate = c.getTime();
        }
        return list;
    }

    /**
     * 获取指定月的开始日期
     * @param currentDate
     * @return
     */
    public static String getStartDate(String currentDate){
        StringBuilder str = new StringBuilder(currentDate);
        str.append("-00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(str.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = sdf.format(c.getTime());
        return first;
    }

    /**
     * 获取指定月的结束日期
     * @param currentDate
     * @return
     */
    public static String getEndDate(String currentDate){
        StringBuilder str = new StringBuilder(currentDate);
        str.append("-00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(sdf.parse(str.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ca.add(Calendar.MONTH, 1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = sdf.format(ca.getTime());
        return last;
    }

}
