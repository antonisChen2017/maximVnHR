package cn.com.maxim.portal.util;

/**
 * $Id: DateUtil.java,v 1.0, 2015-04-13 03:41:01Z, Evan Tung$
 * Copyright (c) 2007-2008 Stark Technology	Inc. All Rights	Reserved.
 */

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
//import org.apache.struts.util.LabelValueBean;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import cn.com.maxim.portal.attendan.ro.checkYearDayRO;
import cn.com.maxim.portal.attendan.ro.empYearChange;

/**
 * The <code>DateUtil</code> class.
 *
 * @version $Id: DateUtil.java,v 1.0, 2015-04-13 03:41:01Z, Evan Tung$
 */
public class DateUtil {

    private static Logger logger = Logger.getLogger(DateUtil.class);

    private static final Pattern ROC_DATE_PATTERN = Pattern.compile("^(\\d{1,3})\\D(\\d{2})\\D(\\d{2})$");

    /**
     * 以固定格式(yy/MM/dd)將民國年月日轉為西元年月日 96/12/01 -> 2007/12/01
     *
     * @param rocDate
     * @return Date
     */
    public static final Date parseRocDate(String rocDate) {

	if (StringUtils.isEmpty(rocDate)) {
	    return null;
	}
	Matcher matcher = ROC_DATE_PATTERN.matcher(rocDate);
	String rocYear = null;
	String rocMonth = null;
	String rocDay = null;
	Date result = null;
	if (matcher.matches()) {
	    rocYear = matcher.group(1);
	    rocMonth = matcher.group(2);
	    rocDay = matcher.group(3);

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    try {
		result = sdf.parse(String.valueOf(Integer.parseInt(rocYear) + 1911) + rocMonth + rocDay);
	    } catch (Exception e) {
		logger.error(e);
	    }
	}
	return result;
    }

    /**
     * 西元日期轉民國日期
     * 
     * @param date:西元日期
     * @return date: 民國日期
     */
    public static final String parseAdDateToRocDate(String adDate) {
	if (adDate == null || "".equals(adDate)) {
	    return "";
	} else if (!adDate.matches("\\d{4}/\\d{2}/\\d{2}")) {
	    return "";
	}

	int y = Integer.parseInt(adDate.substring(0, 4)) - 1911;
	String birthday = y + adDate.substring(4);

	return birthday;
    }

    public static final String getRocBirthdayString(String rocDate) {
	if (StringUtils.isEmpty(rocDate)) {
	    return null;
	}
	Matcher matcher = ROC_DATE_PATTERN.matcher(rocDate);
	String rocYear = null;
	String rocMonth = null;
	String rocDay = null;
	String result = "";
	if (matcher.matches()) {
	    rocYear = matcher.group(1);
	    rocMonth = matcher.group(2);
	    rocDay = matcher.group(3);

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    try {
		result = rocYear + "年" + rocMonth + "月" + rocDay + "日";
	    } catch (Exception e) {
		logger.error(e);
	    }
	}
	return result;
    }

    public static final Date parseDateWithFormat(String source, String format) {
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	Date date = null;
	try {
	    date = sdf.parse(source);
	} catch (ParseException e) {
	    logger.error(e);
	}
	return date;
    }

    public static final String formatDateWithFormat(Date source, String format) {
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	String date = null;
	date = sdf.format(source);
	return date;
    }

    public static final String formatDate(Date source, String format) {
	if (source != null) {
	    return formatDateWithFormat(source, format);
	}
	return "";
    }

    /**
     * Returns a new Date with the hours, milliseconds, seconds and minutes set
     * to 0.
     *
     * @param date
     *            - date to calculate start of day from.
     * @return start of date.
     */
    public static final Date startOfDay(Date date) {
	if (date == null)
	    return null;

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);

	calendar.set(Calendar.HOUR_OF_DAY, 0);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.SECOND, 0);
	calendar.set(Calendar.MILLISECOND, 0);
	return calendar.getTime();
    }

    /**
     * Returns the last millisecond of the specified date.
     *
     * @param date
     *            - date to calculate end of day from.
     * @return last millisecond of date.
     */
    public static final Date endOfDay(Date date) {
	if (date == null)
	    return null;

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);

	calendar.set(Calendar.HOUR_OF_DAY, 23);
	calendar.set(Calendar.MINUTE, 59);
	calendar.set(Calendar.SECOND, 59);
	calendar.set(Calendar.MILLISECOND, 999);
	return calendar.getTime();
    }

    /**
     * 計算二個日期相差的天數。
     */
    public static final int calculateDay(Date startDate, Date endDate) {
	Calendar calendar = Calendar.getInstance();

	calendar.setTime(startDate);
	long startTimeInMillis = calendar.getTimeInMillis();

	calendar.setTime(endDate);
	long endTimeInMillis = calendar.getTimeInMillis();

	return Math.round((endTimeInMillis - startTimeInMillis) / DateUtils.MILLIS_PER_DAY);
    }

    /**
     * 取得指定日期的前n天
     * 
     * @param date
     *            Date 指定日期
     * @param n
     *            int 前n天，0表示當天
     * @param format
     *            輸出格式
     * @return String
     */
    public static final String getBeforeDate(Date date, int n, String format) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DAY_OF_MONTH, n);
	return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * 日期加減天數
     * 
     * @param date
     *            基準日
     * @param days
     *            加減天數
     * @return
     */
    public static final Date addDays(Date date, int days) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DAY_OF_MONTH, days);
	return cal.getTime();
    }

    public static final String NowDate() {

	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");// 设置日期格式
	return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static final String NowDateTime() {

	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");// 设置日期格式
	return df.format(new Date());// new Date()为获取当前系统时间
    }

    /**
     * 取得日期之時分秒之LabelValueBean Object
     * 
     * @param type
     *            1:start date; 2:end date。
     * @return ArrayList<LabelValueBean>
     * 
     *         public static final ArrayList<LabelValueBean>
     *         getDateHmsLabelValueBean(int type){ ArrayList<LabelValueBean>
     *         list = new ArrayList<LabelValueBean>(); for(int i=0; i <=
     *         24;i++){ String hmsLabel = ""; String hmsValue = "";
     * 
     *         if(i < 10){ hmsLabel = "0" + i + ":00"; hmsValue = hmsLabel +
     *         ":00"; }else if(i == 24){ if(type == 2){ hmsLabel = "23:59";
     *         hmsValue = "23:59:59"; }else{ break; } }else{ hmsLabel = i +
     *         ":00"; hmsValue = hmsLabel + ":00"; }
     * 
     *         list.add(new LabelValueBean(hmsLabel, hmsValue)); }
     * 
     *         return list; }
     */
    public static final boolean isValidDate(String dateString, String formatter) {
	boolean isValid = false;

	try {
	    SimpleDateFormat sdf = new SimpleDateFormat(formatter);
	    sdf.parse(dateString);
	    isValid = true;
	} catch (ParseException e) {
	    logger.info("非合法之日期格式" + e.getMessage());
	}

	return isValid;
    }

    /** 判斷禮拜天 **/
    // public boolean jisuan(String s1 ,String s2) throws ParseException{

    // }

    /**
     * 判斷多日請假請假中有無假日
     * 
     * @param s1
     * @param s2
     * @return
     * @throws ParseException
     */
    public double jisuan(String s1, String s2) throws ParseException {
	SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date d1 = s.parse(s1);
	Date d2 = s.parse(s2);
	SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd");
	Date dd1 = ss.parse(s1);
	Date dd11 = ss.parse(s1);
	Date dd2 = ss.parse(s2);

	Calendar ca = Calendar.getInstance();
	if (dd1.getTime() == dd2.getTime()) {// 同一天
	    if (this.getweekdayloc(dd1, ca) == 0) {// 星期天
		return 0;
	    } else {

		return this.getOneShijian(d1, d2, ca);
	    }
	} else {
	    double bb = 0.000;
	    while (dd2.getTime() >= dd1.getTime()) {
		if (this.getweekdayloc(dd1, ca) == 1) {

		    if (dd1.getTime() == dd11.getTime()) {
			if (this.getshijian(d1, ca) < 0.5) {

			    bb += 0.5;

			} else {
			    bb += this.getshijian(d1, ca);

			}

		    } else if (dd1.getTime() == dd2.getTime()) {
			if ((1 - this.getshijian(d2, ca)) < 0.5) {
			    bb += 0.5;

			} else {
			    bb += (1 - this.getshijian(d2, ca));

			}

		    } else {
			bb += 1;

		    }
		}

		ca.setTime(d1);
		ca.add(Calendar.DATE, 1);
		d1 = ca.getTime();
		ca.clear();
		ca.setTime(dd1);
		ca.add(Calendar.DATE, 1);
		dd1 = ca.getTime();

	    }

	    return bb;
	}

    }

    /**
     * 月份每日考勤表
     * 
     * @param s1
     * @param s2
     * @return
     * @throws ParseException
     */
    /*
     * public double monthSunDay(String s1 ,String s2) throws ParseException{
     * SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); Date d1
     * = s.parse(s1); Date d2 = s.parse(s2); SimpleDateFormat ss = new
     * SimpleDateFormat("yyyy/MM/dd"); Date dd1 = ss.parse(s1); Date dd11 =
     * ss.parse(s1); Date dd2 = ss.parse(s2);
     * 
     * Calendar ca = Calendar.getInstance();
     * if(dd1.getTime()==dd2.getTime()){//同一天
     * if(this.getweekdayloc(dd1,ca)==0){//星期天 return 0; }else{ //
     * System.out.println("jisuan sw1 day: "+String.valueOf(this.getshijian(d2,
     * ca)-(1-this.getshijian(d1,ca)))); return
     * this.getshijian(d2,ca)-(1-this.getshijian(d1,ca)); } }else{ double
     * bb=0.000; while(dd2.getTime()>=dd1.getTime()){
     * if(this.getweekdayloc(dd1,ca)==1){
     * 
     * if(dd1.getTime()==dd11.getTime()){ if(this.getshijian(d1,ca)<0.5){
     * 
     * bb+=0.5; // System.out.println("jisuan sw2 day: "+bb); }else{
     * bb+=this.getshijian(d1,ca); // System.out.println("jisuan sw3 day: "+bb);
     * }
     * 
     * }else if(dd1.getTime()==dd2.getTime()){
     * if((1-this.getshijian(d2,ca))<0.5){ bb+=0.5; //
     * System.out.println("jisuan sw4 bb :"+bb); //
     * System.out.println("jisuan sw4 day:  "+this.getshijian(d2,ca)); }else{
     * bb+=(1-this.getshijian(d2,ca)); //
     * System.out.println("jisuan sw5 day: "+bb); }
     * 
     * }else{ bb+=1; // System.out.println("jisuan sw6 day: "+bb); } }
     * 
     * ca.setTime(d1); ca.add(Calendar.DATE, 1); d1=ca.getTime(); ca.clear();
     * ca.setTime(dd1); ca.add(Calendar.DATE, 1); dd1=ca.getTime();
     * 
     * }
     * 
     * return bb; }
     */

    // 传进一个日期判断是否是周六、日 。六日返回0，其他返回1
    private int getweekdayloc(Date date, Calendar ca) {
	ca.setTime(date);
	if (ca.get(Calendar.DAY_OF_WEEK) == 1 || ca.get(Calendar.DAY_OF_WEEK) == 7) {
	    return 0;
	} else {
	    // return getHolidayloc(date,1);
	    return 1;
	}

    }

    /**
     * 传进一个日期判断是否是周六、日 六返回7 日返回1
     * 
     * @throws Exception
     **/
    public static int getWeekday(String YMD) throws Exception {
	Calendar ca = Calendar.getInstance();
	SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd");
	Date dd1 = ss.parse(YMD);
	ca.setTime(dd1);
	return ca.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 報表判斷是否星期天
     * 
     * @param styleday
     * @param styleSun
     * @param YM
     * @param Day
     * @return
     * @throws Exception
     */
    public static HSSFCellStyle checkReportDate(HSSFCellStyle styleday, HSSFCellStyle styleSun, String YM, String Day)
	    throws Exception {
	if (getWeekday(YM + "/" + Day) == 1) {
	    return styleSun;
	} else {
	    return styleday;
	}
    }

    /**
     * 報表判斷是否星期天
     * 
     * @param styleday
     * @param styleSun
     * @param YM
     * @param Day
     * @return
     * @throws Exception
     */
    public static HSSFCellStyle checkReportColDate(HSSFCellStyle styleday, HSSFCellStyle styleSun, String YM,
	    String FileName) throws Exception {

	if (FileName.equals("DAY1")) {
	    return checkReportDate(styleday, styleSun, YM, "01");
	} else if (FileName.equals("DAY2")) {
	    return checkReportDate(styleday, styleSun, YM, "02");
	} else if (FileName.equals("DAY3")) {
	    return checkReportDate(styleday, styleSun, YM, "03");
	} else if (FileName.equals("DAY4")) {
	    return checkReportDate(styleday, styleSun, YM, "04");
	} else if (FileName.equals("DAY5")) {
	    return checkReportDate(styleday, styleSun, YM, "05");
	} else if (FileName.equals("DAY6")) {
	    return checkReportDate(styleday, styleSun, YM, "06");
	} else if (FileName.equals("DAY7")) {
	    return checkReportDate(styleday, styleSun, YM, "07");
	} else if (FileName.equals("DAY8")) {
	    return checkReportDate(styleday, styleSun, YM, "08");
	} else if (FileName.equals("DAY9")) {
	    return checkReportDate(styleday, styleSun, YM, "09");
	} else if (FileName.equals("DAY10")) {
	    return checkReportDate(styleday, styleSun, YM, "10");
	} else if (FileName.equals("DAY11")) {
	    return checkReportDate(styleday, styleSun, YM, "11");
	} else if (FileName.equals("DAY12")) {
	    return checkReportDate(styleday, styleSun, YM, "12");
	} else if (FileName.equals("DAY13")) {
	    return checkReportDate(styleday, styleSun, YM, "13");
	} else if (FileName.equals("DAY14")) {
	    return checkReportDate(styleday, styleSun, YM, "14");
	} else if (FileName.equals("DAY15")) {
	    return checkReportDate(styleday, styleSun, YM, "15");
	} else if (FileName.equals("DAY16")) {
	    return checkReportDate(styleday, styleSun, YM, "16");
	} else if (FileName.equals("DAY17")) {
	    return checkReportDate(styleday, styleSun, YM, "17");
	} else if (FileName.equals("DAY18")) {
	    return checkReportDate(styleday, styleSun, YM, "18");
	} else if (FileName.equals("DAY19")) {
	    return checkReportDate(styleday, styleSun, YM, "19");
	} else if (FileName.equals("DAY20")) {
	    return checkReportDate(styleday, styleSun, YM, "20");
	} else if (FileName.equals("DAY21")) {
	    return checkReportDate(styleday, styleSun, YM, "21");
	} else if (FileName.equals("DAY22")) {
	    return checkReportDate(styleday, styleSun, YM, "22");
	} else if (FileName.equals("DAY23")) {
	    return checkReportDate(styleday, styleSun, YM, "23");
	} else if (FileName.equals("DAY24")) {
	    return checkReportDate(styleday, styleSun, YM, "24");
	} else if (FileName.equals("DAY25")) {
	    return checkReportDate(styleday, styleSun, YM, "25");
	} else if (FileName.equals("DAY26")) {
	    return checkReportDate(styleday, styleSun, YM, "26");
	} else if (FileName.equals("DAY27")) {
	    return checkReportDate(styleday, styleSun, YM, "27");
	} else if (FileName.equals("DAY28")) {
	    return checkReportDate(styleday, styleSun, YM, "28");
	} else if (FileName.equals("DAY29")) {
	    return checkReportDate(styleday, styleSun, YM, "29");
	} else if (FileName.equals("DAY30")) {
	    return checkReportDate(styleday, styleSun, YM, "30");
	} else if (FileName.equals("DAY31")) {
	    return checkReportDate(styleday, styleSun, YM, "31");
	} else {
	    return styleday;
	}

    }

    /**
     * 传进一个日期判断星期幾
     * 
     * @param date
     * @param ca
     * @return
     */
    public static int getweekday(Date date, Calendar ca) {
	ca.setTime(date);
	return ca.get(Calendar.DAY_OF_WEEK);

    }

    /**
     * 計算當月幾天
     * 
     * @param date
     * @return
     */
    public static int getDaysOfStrMonth(String YM) throws Exception {// 获取当月天数
	Calendar ca = Calendar.getInstance();
	SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM");
	Date dd1 = ss.parse(YM);
	ca.setTime(dd1); // 要计算你想要的月份，改变这里即可
	int days = ca.getActualMaximum(Calendar.DAY_OF_MONTH);

	return days;
    }

    /**
     * 計算當月幾天
     * 
     * @param date
     * @return
     */
    public static int getDaysOfTheMonth(Date date) {// 获取当月天数
	Calendar rightNow = Calendar.getInstance();
	rightNow.setTime(date); // 要计算你想要的月份，改变这里即可
	int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);

	return days;
    }

    // 传进一个日期判断是否是公司放假日 。是返回0，其他返回1
    private int getHolidayloc(Date date, int re) {
	SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
	String dd1 = ss.format(date);
	ArrayList al = new ArrayList();
	al.add("2017-03-27");// 假設放假日
	for (int i = 0; i < al.size(); i++) {

	    if (dd1.equals(al.get(i))) {
		re = 0;
		break;
	    }
	}
	return re;
    }

    // 判断
    private double getshijian(Date date, Calendar ca) {
	ca.setTime(date);
	int shi = ca.get(Calendar.HOUR_OF_DAY);
	int fen = ca.get(Calendar.MINUTE);
	double d = shi + fen / 60.000;
	if (shi < 9.000) {
	    return 1.000;
	} else if (d <= 12.500 && d >= 9.000) {

	    return (12.500 - d + (18.000 - 13.500)) / 8;
	} else if (d > 12.500 && d < 13.000) {

	    return (18.000 - 13.500) / 8;
	} else if (d >= 13.500 && d <= 18.000) {

	    return (18.000 - d) / 8;
	} else {
	    return 0.000;
	}
    }

    // 判断 同一天
    private double getOneShijian(Date date1, Date date2, Calendar ca) {
	ca.setTime(date1);
	int shi1 = ca.get(Calendar.HOUR_OF_DAY);
	int fen1 = ca.get(Calendar.MINUTE);
	double d1 = shi1 + fen1 / 60.000;

	ca.setTime(date2);
	int shi2 = ca.get(Calendar.HOUR_OF_DAY);
	int fen2 = ca.get(Calendar.MINUTE);
	double d2 = shi2 + fen2 / 60.000;
	double d = 0.0;
	d = d2 - d1;
	double r = 0.0;

	if (d >= 4) {
	    r = 1;
	} else if (d <= 4) {
	    r = 0.5;
	} else if (d <= 0) {
	    r = 0;
	}
	return r;
    }

    /**
     * 傳出系統年/月
     * 
     * @return
     */
    public static String getSysYearMonth() {
	String re = "";
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	if (month <= 9) {
	    re = year + "/0" + month;
	} else {
	    re = year + "/" + month;
	}
	return re;
    }

    /** 全月天數 **/
    public static ArrayList getMonthDates(String year, String month) {

	int maxDate = 0;
	Date first = null;
	Date two = null;
	Date[] dates = null;
	String newDay = "";
	ArrayList listDates = new ArrayList();
	try {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	    first = sdf.parse(year + month);
	    cal.setTime(first);
	    maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);

	    // System.out.println("maxDate="+maxDate);
	    SimpleDateFormat srf = new SimpleDateFormat("yyyy/MM/dd");
	    dates = new Date[maxDate];
	    for (int i = 1; i <= maxDate; i++) {
		dates[i - 1] = new Date(first.getTime());
		first.setDate(first.getDate() + 1);
		newDay = srf.format(dates[i - 1]);

		cal.setTime(dates[i - 1]);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
		    // System.out.println("no SUNDAY");
		} else {
		    if (newDay.split("/")[1].equals(month)) {
			listDates.add(newDay);
		    }
		}

		// System.out.println(String.valueOf(dates[i - 1]));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return listDates;
    }

    /** 當月最大天數 **/
    public static int getMonthDateMax(String year, String month) {

	int maxDate = 0;
	Date first = null;
	Date two = null;
	Date[] dates = null;
	String newDay = "";

	try {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	    first = sdf.parse(year + month);
	    cal.setTime(first);
	    maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return maxDate;
    }

    /** 當月有幾個星期天 **/
    public static int getMonthSunDateMax(String year, String month) {

	int maxDate = 0;
	Date first = null;
	Date two = null;
	Date[] dates = null;
	String newDay = "";

	try {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	    first = sdf.parse(year + month);
	    cal.setTime(first);
	    maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return maxDate;
    }

    /** 當月須扣除星期天曠職 **/
    public static int getSun(String year, String month) {
	int maxDate = 0;
	Date first = null;
	Date two = null;
	Date[] dates = null;
	String newDay = "";
	ArrayList listDates = new ArrayList();
	int sun = 0;
	try {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	    first = sdf.parse(year + month);
	    cal.setTime(first);
	    maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);

	    // System.out.println("maxDate="+maxDate);
	    SimpleDateFormat srf = new SimpleDateFormat("yyyy/MM/dd");
	    dates = new Date[maxDate];
	    for (int i = 1; i <= maxDate; i++) {
		dates[i - 1] = new Date(first.getTime());
		first.setDate(first.getDate() + 1);
		newDay = srf.format(dates[i - 1]);

		cal.setTime(dates[i - 1]);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
		    sun++;
		}

		// System.out.println(String.valueOf(dates[i - 1]));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return sun * 8;
    }

    /**
     * 时间段的比较处理 , 如果包含了传来的 时段 了, 就说明 时间冲突了
     * 
     * @return
     */
    public static boolean isContain(Date[] a, Date[] b) {

	long astatr = a[0].getTime();
	long aend = a[1].getTime();

	long bstatr = b[0].getTime();
	long bend = b[1].getTime();

	// a0 包在了 b0 ~ b1 之间
	if (astatr >= bstatr && astatr <= bend)
	    return true;

	// b0 包在了 a0 ~ a1 之间
	if (astatr <= bstatr && aend >= bstatr)
	    return true;

	return false;
    }

    /**
     * 时间段的比较处理 , 如果包含了传来的 时段 了, 就说明 时间冲突了 , (允许首尾相等而不包含的情况)
     * 
     * @return
     */
    public static boolean isContainEnd(Date[] a, Date[] b) {

	long astatr = a[0].getTime();
	long aend = a[1].getTime();

	long bstatr = b[0].getTime();
	long bend = b[1].getTime();

	// a0 包在了 b0 ~ b1 之间
	if (astatr >= bstatr && astatr < bend)
	    return true;

	// b0 包在了 a0 ~ a1 之间
	if (astatr <= bstatr && aend > bstatr)
	    return true;

	// 相等
	if (astatr == bstatr && aend == bend)
	    return true;

	return false;
    }

    // 功能 工具 扩展

    public static boolean isContain(String astatr, String aend, String bstatr, String bend) {
	return isContain(new String[] { astatr, aend }, new String[] { bstatr, bend });
    }

    public static boolean isContain(String[] aStr, String[] bStr) {
	return isContain(aStr, bStr, "yyyy/MM/dd HH:mm");
    }

    public static boolean isContain(String[] aStr, String[] bStr, String pattern) {
	final SimpleDateFormat SF = new SimpleDateFormat(pattern);
	try {
	    return isContain(new Date[] { SF.parse(aStr[0]), SF.parse(aStr[1]) },
		    new Date[] { SF.parse(bStr[0]), SF.parse(bStr[1]) });
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }

    public static boolean isContainEnd(String astatr, String aend, String bstatr, String bend) {
	return isContainEnd(new String[] { astatr, aend }, new String[] { bstatr, bend });
    }

    public static boolean isContainEnd(String[] aStr, String[] bStr) {
	return isContainEnd(aStr, bStr, "yyyy/MM/dd HH:mm");
    }

    public static boolean isContainEnd(String[] aStr, String[] bStr, String pattern) {
	final SimpleDateFormat SF = new SimpleDateFormat(pattern);
	try {
	    return isContainEnd(new Date[] { SF.parse(aStr[0]), SF.parse(aStr[1]) },
		    new Date[] { SF.parse(bStr[0]), SF.parse(bStr[1]) });
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     * 
     * @param beginDate
     * @param endDate
     * @return List<String>
     */
    public static List<String> getDatesBetweenTwoDate(String beginDate1, String endDate1) throws Exception {
	Date beginDate;
	Date endDate;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	beginDate = format.parse(beginDate1);
	endDate = format.parse(endDate1);
	List<String> dateList = new ArrayList<String>();
	dateList.add(beginDate1);// 把开始时间加入集合
	Calendar cal = Calendar.getInstance();
	// 使用给定的 Date 设置此 Calendar 的时间
	cal.setTime(beginDate);
	if (cal.getTime().after(endDate)) {
	    throw new RuntimeException("开始时间大于结束时间");
	}
	boolean bContinue = true;
	while (bContinue) {
	    // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    // 测试此日期是否在指定日期之后
	    if (endDate.after(cal.getTime())) {
		dateList.add(format.format(cal.getTime()));
	    } else {
		break;
	    }
	}
	dateList.add(endDate1);// 把结束时间加入集合
	return dateList;
    }

    /**
     * 是否同一天
     * 
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static boolean isSameDate(String SbeginDate) throws Exception {
	Date beginDate;
	Date endDate;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	beginDate = format.parse(SbeginDate);
	endDate = format.parse(NowDate());
	Calendar cal1 = Calendar.getInstance();
	cal1.setTime(beginDate);
	Calendar cal2 = Calendar.getInstance();
	cal2.setTime(endDate);
	boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	return isSameDate;
    }

    /**
     * 比較時間大小
     * 
     * @param t1
     * @param t2
     * @return
     */
    public static String largerTime(String t1, String t2) {
	Date date1, date2;
	DateFormat formart = new SimpleDateFormat("hh:mm");
	try {
	    date1 = formart.parse(t1);
	    date2 = formart.parse(t2);
	    if (date1.compareTo(date2) < 0) {
		return "2";
	    } else {
		return "1";
	    }
	} catch (ParseException e) {
	    System.out.println("date init fail!");
	    e.printStackTrace();
	    return null;
	}

    }

    /**
     * 計算過去一年兩年三年日期
     * 
     * @param date
     * @return
     * @throws Exception 
     */
    public static empYearChange getThreeYears(String Day) throws Exception {// 获取当月天数

	empYearChange ey = new empYearChange();
	String t1 = "2017/07/26";
	int value = -1;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	Calendar c = Calendar.getInstance();
	Date frist = format.parse(t1);
	c.setTime(frist);
	c.add(Calendar.YEAR, value);
	Date y = c.getTime();
	String year = format.format(y);
	System.out.println("过去1年：" + year);
	ey.setLastYear(year);
	value = -2;
	frist = format.parse(t1);
	c.setTime(frist);
	c.add(Calendar.YEAR, value);
	y = c.getTime();
	year = format.format(y);
	System.out.println("过去2年：" + year);
	ey.setTwoYears(year);
	value = -3;
	frist = format.parse(t1);
	c.setTime(frist);
	c.add(Calendar.YEAR, value);
	y = c.getTime();
	year = format.format(y);
	System.out.println("过去3年：" + year);
	ey.setThreeYears(year);
	return ey;
    }

    // 增加或减少天数 (字串轉字串)
    public static String addDay(String Sdate, int num) throws Exception {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	Calendar startDT = Calendar.getInstance();
	startDT.setTime(sdf.parse(Sdate));
	startDT.add(Calendar.DAY_OF_MONTH, num);
	return sdf.format(startDT.getTime());
    }

    /**
     * 比較兩個時間差 輸出小時
     * 
     * @param startTime
     * @param endTime
     * @param format
     * @param str
     * @return
     */
    public static double dateDiff(String startTime, String endTime, String format, String str) {
	// 按照传入的格式生成一个simpledateformate对象
	SimpleDateFormat sd = new SimpleDateFormat(format);
	long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
	long nh = 1000 * 60 * 60;// 一小时的毫秒数
	long nm = 1000 * 60;// 一分钟的毫秒数
	long ns = 1000;// 一秒钟的毫秒数
	long diff;
	long day = 0;
	double hour = 0;
	long min = 0;
	long sec = 0;
	// 获得两个时间的毫秒时间差异
	try {
	    diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
	    day = diff / nd;// 计算差多少天
	    hour = diff % nd / nh + day * 24;// 计算差多少小时
	    min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
	    sec = diff % nd % nh % nm / ns;// 计算差多少秒
	    // 输出结果
	    System.out.println(
		    "时间相差：" + day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟" + sec + "秒。");
	    System.out.println("hour=" + hour + ",min=" + min);
	    if (min >= 28) {
		hour = hour + 0.5;
	    }
	    if (str.equalsIgnoreCase("h")) {
		return hour;
	    } else {
		return min;
	    }

	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	if (str.equalsIgnoreCase("h")) {
	    return hour;
	} else {
	    return min;
	}
    }
    
    
    
/**
 * 判斷日期如果與節日相同 輸出false
 * @param chro
 * @param Day
 * @return
 */
    public static boolean checkDays(checkYearDayRO chro,String Day) {
	boolean flag=false;
	
	String toDay=Day.split("/")[1]+"/"+Day.split("/")[2];
		
	if(chro.getCHECKDAY1().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY2().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY3().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY4().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY5().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY6().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY7().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY8().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY9().equals(toDay)){
	    flag=true;
	}
	if(chro.getCHECKDAY10().equals(toDay)){
	    flag=true;
	}
	return flag;
	
    }
    
    
}
