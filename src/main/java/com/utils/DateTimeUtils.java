package com.utils;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
public class DateTimeUtils extends DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String MM_DD = "MM/dd";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
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
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = dayBetween(diff);
        // 计算差多少小时
        long hour = hourBetween(diff);
        // 计算差多少分钟
        long min = minBetween(diff);
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    // 计算差多少天
    public static long dayBetween(long diff) {
        long nd = 1000 * 24 * 60 * 60;
        return diff / nd;
    }

    // 计算差多少小时
    public static long hourBetween(long diff) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        return diff % nd / nh;

    }

    // 计算差多少分钟
    public static long minBetween(long diff) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        return diff % nd % nh / nm;
    }

    //计算差多少天
    public static int getDifNum(String startDate, String endDate) throws Exception {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        /*天数差*/
        Date sDate = simpleFormat.parse(startDate);
        Date eDate = simpleFormat.parse(endDate);
        long from1 = sDate.getTime();
        long to1 = eDate.getTime();
        int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
        return days;
    }

    /**
     * 获取当天最小时间
     * 2022-12-08 14:17:52  -> 2022-12-08 00:00:00
     *
     * @param date
     */
    public static Date getIntradayMin(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 日期延后指定天数
     *
     * @param begin 开始日期
     * @param days  天数
     * @return
     */
    public static Date dateAdd(Date begin, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(begin);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获取前一天0点的时间
     * @return
     */
    public static Date yesterdayMidnightDate(){
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取当前时区
        ZoneId zoneId = ZoneId.systemDefault();
        // 获取带时区的当前时间
        ZonedDateTime zonedNow = ZonedDateTime.of(now, zoneId);
        // 获取前一天零点
        ZonedDateTime yesterdayMidnight = zonedNow.truncatedTo(ChronoUnit.DAYS).minusDays(1);

        // 将 ZonedDateTime 转换为 Date 对象
        Date yesterdayMidnightDate = Date.from(yesterdayMidnight.toInstant());
        return yesterdayMidnightDate;
    }

    /**
     * 获取本月以及往前11个月
     */
    public static List<String> getLast12Months(Date date) {
        List<String> months = new ArrayList<>();
        if (date==null){
            return null;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (int i = 0; i < 12; i++) {
            months.add(localDate.format(formatter));
            localDate = localDate.minusMonths(1);
        }

        return months;
    }

    /**
     * 获取本月以及往前11个月 时间的区间
     */
    public static List<String> getBeginEnd12Months(Date date) {
        List<String> months = new ArrayList<>();
        if (date==null){
            return null;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (int i = 0; i < 2; i++) {
            months.add(localDate.format(formatter));
            localDate = localDate.minusMonths(11);
        }

        return months;
    }


    /**
     * 比较两个日期的大小
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 如果date1在date2之前，返回负数；如果date1在date2之后，返回正数；如果两个日期相等，返回0。
     */
    public static int compare(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

    /**
     * 比较年月日 是否相等
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取前一天的时间
     * @param
     * @return
     */
    public static Date getPreviousDay(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;

        try {
            date = dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    /**
     * 只根据时间的 年月日比较时间大小
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int compareDate(Date date1, Date date2){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int month2 = c2.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if (year1 > year2) {
            return 1;
        } else if (year1 < year2) {
            return -1;
        } else {
            if (month1 > month2) {
                return 1;
            } else if (month1 < month2) {
                return -1;
            } else {
                if (day1 > day2) {
                    return 1;
                } else if (day1 < day2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * 获取指定时间区间内的每一天的日期列表，时分秒为0
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return 日期列表
     */
    public static List<Date> getDateRange(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<>();
        Date currentDate = DateUtil.beginOfDay(startDate);
        while (!currentDate.after(endDate)) {
            dateList.add(currentDate);
            currentDate = DateUtil.offsetDay(currentDate, 1);
        }
        return dateList;
    }

    /**
     *  Date 转换成指定格式的天
     * @param date
     * @return
     */
    public static String formatDateDay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        String day = formatter.format(date);
        if (day.startsWith("0")) {
            day = day.substring(1);
        }
        return day;
    }
    /**
     *  输入 开始时间  结束时间
     *  返回 map  yyyy-MM-dd  将当天的结束时间添加到Map中
     * @param startDate，endDate
     * @return
     */
    public static Map<String, Date> getEachDayEndTimes(Date startDate, Date endDate) {
        // 将传入的Date类型转换为LocalDateTime类型
        LocalDateTime startDateTime = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endDateTime = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

        // 创建一个Map，用于存储每一天的结束时间
        Map<String, Date> endTimes = new HashMap<>();

        // 循环遍历时间范围内的每一天，并将当天的结束时间添加到Map中
        LocalDateTime currentDateTime = startDateTime;
        while (!currentDateTime.isAfter(endDateTime)) {
            LocalDateTime endOfDay = LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.MAX);
            Date date = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
            endTimes.put(currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), date);
            currentDateTime = currentDateTime.plusDays(1);
        }

        return endTimes;
    }

    /**
     * 获取一天的截止时间
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 设置时分秒为23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    /**
     * 判断当前时间是否在日期集合中
     *
     * @param dateList 日期集合
     * @return 如果当前时间在日期集合中，则返回 true；否则返回 false。
     */
    public static boolean isCurrentDateInList(List<Date> dateList) {
        // 获取当前时间的字符串形式，格式为 yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = dateFormat.format(new Date());

        // 遍历日期集合，判断当前时间是否在集合中
        for (Date date : dateList) {
            String dateStrInList = dateFormat.format(date);
            if (dateStrInList.equals(todayStr)) {
                return true;
            }
        }
        return false;
    }



    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);
    public static void main(String[] args) {
//        ZoneId zone = ZoneId.systemDefault();
//        LocalDateTime now = LocalDateTime.now(zone);
//        Duration untilLastMonday = Duration.ofDays(now.getDayOfWeek().getValue() - 1).plusHours(-now.getHour()).plusMinutes(-now.getMinute()).plusSeconds(-now.getSecond());
//        Duration untilThisMonday = Duration.ofDays(7).plus(untilLastMonday);
//        LocalDateTime lastMonday = now.minus(untilLastMonday);
//        LocalDateTime thisMonday = lastMonday.plus(untilThisMonday);

        /**
         * 从上周一到下周一
         */
//        ZoneId zone = ZoneId.systemDefault();
//        LocalDateTime now = LocalDateTime.now(zone);
//        LocalDateTime lastMonday = now.with(DayOfWeek.MONDAY).minusWeeks(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
//        LocalDateTime nextMonday = lastMonday.plusWeeks(2);
//        System.out.println("From: " + lastMonday);
//        System.out.println("To: " + nextMonday);


        getThisWeekToNextWeekTimeRange();

    }

    /**
     * 本周一到下周一
     */
    public static Map<String,LocalDateTime> getThisWeekToNextWeekTimeRange() {
        Map<String,LocalDateTime> result = new HashMap<String,LocalDateTime>();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime now = LocalDateTime.now(zone);
        LocalDateTime thisMonday = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime nextMonday = thisMonday.plusWeeks(1);
        result.put("startTime",thisMonday);
        result.put("endTime",nextMonday);
        logger.info("下周一: {}",nextMonday);
        logger.info("本周一: {}",thisMonday);
        return result;
    }


    /**
     * 从上周一到本周一
     */
    private static void getLastWeekToThisWeekTimeRange() {
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime now = LocalDateTime.now(zone);
        LocalDateTime lastMonday = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0).minusWeeks(1);
        LocalDateTime thisMonday = lastMonday.plusWeeks(1);
        logger.info("上周一: {}", lastMonday);
        logger.info("本周一: {}", thisMonday);
    }


    /**
     * 上周一的零点到本周一的零点的所有的日期【天】
     */
    private static void listDatesFromLastMondayToThisMonday() {
        listDatesFromLastMondayToThisMonday();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime now = LocalDateTime.now(zone);
        LocalDateTime lastMonday = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0).minusWeeks(1);
        LocalDateTime thisMonday = lastMonday.plusWeeks(1);

        List<LocalDateTime> dates = new ArrayList<>();
        LocalDateTime current = lastMonday;
        while (current.isBefore(thisMonday)) {
            dates.add(current);
            current = current.plusDays(1);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (LocalDateTime date : dates) {
            System.out.println(date.format(formatter));
        }

    }


}
