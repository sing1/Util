# DateUtil  

## 介绍
首先是一些日期格式，可以根据需求定义或直接传入String类型。

```JAVA  
public static final String YYYY_MM = "yyyy-MM";
public static final String YYYY_MM_DD = "yyyy-MM-dd";
public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
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
``` 
## API
###### 获取某日期是星期几
```JAVA
DateUtil.dayForWeek(String,int);//日期，类型(0时返回周一、周二，否则返回星期一、星期二)
```
###### 获取当前日期(yyyy-MM-dd HH:mm:ss)
```Java  
DateUtil.getCurrentTime(); 
```
###### 获取当前日期(自定义样式)
```Java  
DateUtil.getCurrentTime(String); 
```
###### 获取昨天日期(yyyy-MM-dd)
```Java  
DateUtil.getYesterday(); 
```
###### 获取后退N天的日期(yyyy-MM-dd)
```Java  
DateUtil.getStrDate(String); 
```
###### 获取两个日期相差的天数
```Java  
DateUtil.getIntervalDays(String, String);
DateUtil.getIntervalDays(Date, Date);
```
###### 获取日期的年份
```Java  
DateUtil.getYear(String, String);// 日期，日期的格式(如顶部格式)
DateUtil.getYear(Date);
```
###### 获取日期的月份
```Java  
DateUtil.getMonth(String, String);// 日期，日期的格式(如顶部格式)
DateUtil.getMonth(Date);
```
###### 获取日期的日期
```Java  
DateUtil.getDay(String, String);// 日期，日期的格式(如顶部格式)
DateUtil.getDay(Date);
```
###### 获取日期的小时
```Java  
DateUtil.getHour(String, String);// 日期，日期的格式(如顶部格式)
DateUtil.getHour(Date);
```
###### 获取日期的分钟
```Java  
DateUtil.getMinute(String, String);// 日期，日期的格式(如顶部格式)
DateUtil.getMinute(Date);
```
###### 获取日期的秒数
```Java  
DateUtil.getSecond(String, String);// 日期，日期的格式(如顶部格式)
DateUtil.getSecond(Date);
```
###### 获取日期的日期(yyyy-MM-dd)
```Java  
DateUtil.getDate(String, String);// 日期，日期的格式(如顶部格式)
DateUtil.getDate(Date);
```
###### 获取日期的时间(HH:mm:ss)
```Java  
DateUtil.getTime(String, String);// 日期，日期的格式(如顶部格式)
DateUtil.getTime(Date);
```
###### 将String转成Date
```Java  
DateUtil.StringToDate(String, String);// 日期，日期的格式(如顶部格式) 
```
###### 将Date转成String
```Java  
DateUtil.DateToString(String, String);// 日期，日期的格式(如顶部格式) 
``` 
###### 将String转成String
```Java  
DateUtil.StringToString(String, String, String);// 日期，日期的格式(如顶部格式) ，新的日期格式
``` 
###### 获取SimpleDateFormat
```Java  
DateUtil.getDateFormat(String);// 日期的格式(如顶部格式)
``` 
###### 获取日期中的某数值
```Java  
DateUtil.getInteger(Date, int);// 日期,类型(如:Calendar.HOUR_OF_DAY、Calendar.SECOND)
```