package sing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期相关、转化等
 */
public class DateUtil {

    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_EN = "yyyy/MM";
    public static final String YYYY_MM_DD_EN = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH_MM_EN = "yyyy/MM/dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS_EN = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYY_MM_CN = "yyyy年MM月";
    public static final String YYYY_MM_DD_CN = "yyyy年MM月dd日";
    public static final String YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日 HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日 HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String MM_DD = "MM-dd";
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";
    public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static final String MM_DD_EN = "MM/dd";
    public static final String MM_DD_HH_MM_EN = "MM/dd HH:mm";
    public static final String MM_DD_HH_MM_SS_EN = "MM/dd HH:mm:ss";
    public static final String MM_DD_CN = "MM月dd日";
    public static final String MM_DD_HH_MM_CN = "MM月dd日 HH:mm";
    public static final String MM_DD_HH_MM_SS_CN = "MM月dd日 HH:mm:ss";

    /**
     * 获取两个日期相差的天数
     *
     * @param date      日期字符串
     * @param otherDate 另一个日期字符串
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(StringToDate(date, YYYY_MM_DD_HH_MM_SS), StringToDate(otherDate, YYYY_MM_DD_HH_MM_SS));
    }
    public static int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = DateUtil.StringToDate(DateUtil.getDate(date), YYYY_MM_DD);
        Date otherDateTmp = DateUtil.StringToDate(DateUtil.getDate(otherDate), YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    /**
     * 获取日期的年份。失败返回0。
     * @param date
     * @param pattern
     * @return
     */
    public static int getYear(String date,String pattern) {
        return getYear(StringToDate(date, pattern));
    }

    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    // 获取日期的月份。失败返回0。
    public static int getMonth(String date,String pattern) {
        return getMonth(StringToDate(date, pattern));
    }
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH) + 1;
    }

    // 获取日期的天数。失败返回0。
    public static int getDay(String date,String pattern) {
        return getDay(StringToDate(date, pattern));
    }
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    // 获取日期的小时。失败返回0。
    public static int getHour(String date,String pattern) {
        return getHour(StringToDate(date, pattern));
    }
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    // 获取日期的分钟。失败返回0。
    public static int getMinute(String date,String pattern) {
        return getMinute(StringToDate(date, pattern));
    }
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    // 获取日期的秒钟。失败返回0。
    public static int getSecond(String date,String pattern) {
        return getSecond(StringToDate(date, pattern));
    }
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    // 获取日期 。默认yyyy-MM-dd格式。失败返回null。
    public static String getDate(String date,String pattern) {
        return StringToString(date, pattern,YYYY_MM_DD);
    }

    public static String getDate(Date date) {
        return DateToString(date, YYYY_MM_DD);
    }

    // 获取日期的时间。默认HH:mm:ss格式。失败返回null。
    public static String getTime(String date,String pattern) {
        return StringToString(date, pattern,HH_MM_SS);
    }
    public static String getTime(Date date) {
        return DateToString(date, HH_MM_SS);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        try {
            myDate = getDateFormat(pattern).parse(date);
        } catch (Exception e) {
        }
        return myDate;
    }


    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date        旧日期字符串
     * @param olddPattern 旧日期格式
     * @param newPattern  新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, String olddPattern, String newPattern) {
        return DateToString(StringToDate(date, olddPattern), newPattern);
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    public static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();
        Object object = new Object();
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date     日期
     * @param dateType 日期格式
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }

    // 获得当前时间
    public static String getCurrentTime() {
        return getDateFormat(YYYY_MM_DD_HH_MM_SS).format(new Date());
    }
    public static String getCurrentTime(String pattern) {
        return getDateFormat(pattern).format(new Date());
    }

    // 可以获取昨天的日期
    public static String getYesterday() {
        Date date = new Date(System.currentTimeMillis() - 86400L * 1000L);
        String str = getDateFormat(YYYY_MM_DD).format(date);
        try {
            date = getDateFormat(YYYY_MM_DD_HH_MM_SS).parse(str + " 00:00:00");
            return getDateFormat(YYYY_MM_DD).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 可以获取后退N天的日期
     * 格式：传入2 得到2014-11-30
     * @param backDay
     * @return String
     */
    public String getStrDate(String backDay) {
        Calendar calendar = Calendar.getInstance() ;
        calendar.add(Calendar.DATE, Integer.parseInt("-" + backDay));
        String back = getDateFormat(YYYY_MM_DD).format(calendar.getTime()) ;
        return back ;
    }

    /**
     * 根据日期获取周几
     *
     * @param date
     * @return
     */
    public static String dayForWeek(String date,int type) {
        Date dates = null;
        try {
            dates = getDateFormat(YYYY_MM_DD).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.e("星期转换异常");
            return "";
        }

        String[] weeks;
        if (type == 0){
            weeks = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        }else{
            weeks = new String[]{"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dates);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

}