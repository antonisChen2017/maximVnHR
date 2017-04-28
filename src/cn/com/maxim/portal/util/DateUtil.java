package cn.com.maxim.portal.util;

/**
 * $Id: DateUtil.java,v 1.0, 2015-04-13 03:41:01Z, Evan Tung$
 * Copyright (c) 2007-2008 Stark Technology	Inc. All Rights	Reserved.
 */

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
//import org.apache.struts.util.LabelValueBean;

/**
 * The <code>DateUtil</code> class.
 *
 * @version		$Id: DateUtil.java,v 1.0, 2015-04-13 03:41:01Z, Evan Tung$
 */
public class DateUtil   {

	
	private static Logger logger = Logger.getLogger(DateUtil.class);
	
	private static final Pattern ROC_DATE_PATTERN =	Pattern.compile("^(\\d{1,3})\\D(\\d{2})\\D(\\d{2})$");

	/**
	 * 以固定格式(yy/MM/dd)將民國年月日轉為西元年月日	96/12/01 ->	2007/12/01
	 *
	 * @param rocDate
	 * @return Date
	 */
	public static final	Date parseRocDate(String rocDate) {

		if (StringUtils.isEmpty(rocDate)) {
			return null;
		}
		Matcher	matcher	= ROC_DATE_PATTERN.matcher(rocDate);
		String rocYear = null;
		String rocMonth	= null;
		String rocDay =	null;
		Date result	= null;
		if (matcher.matches()) {
			rocYear	= matcher.group(1);
			rocMonth = matcher.group(2);
			rocDay = matcher.group(3);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try	{
				result = sdf.parse(String
						.valueOf(Integer.parseInt(rocYear) + 1911)
						+ rocMonth + rocDay);
			} catch	(Exception e) {
				logger.error(e);
			}
		}
		return result;
	}
	
	/**
	 * 西元日期轉民國日期
	 * @param date:西元日期
	 * @return date: 民國日期
	 */
	public static final String parseAdDateToRocDate(String adDate) {
		if(adDate == null || "".equals(adDate)) {
			return "";
		} else if(!adDate.matches("\\d{4}/\\d{2}/\\d{2}")) {
			return "";
		}
		
		int y = Integer.parseInt(adDate.substring(0, 4)) - 1911;		
		String birthday = y + adDate.substring(4);
		
		return birthday;
	}

	public static final	String getRocBirthdayString(String rocDate)	{
		if (StringUtils.isEmpty(rocDate)) {
			return null;
		}
		Matcher	matcher	= ROC_DATE_PATTERN.matcher(rocDate);
		String rocYear = null;
		String rocMonth	= null;
		String rocDay =	null;
		String result =	"";
		if (matcher.matches()) {
			rocYear	= matcher.group(1);
			rocMonth = matcher.group(2);
			rocDay = matcher.group(3);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try	{
				result = rocYear + "年" + rocMonth +"月" +rocDay+"日";
			} catch	(Exception e) {
				logger.error(e);
			}
		}
		return result;
	}

	public static final	Date parseDateWithFormat(String	source,	String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date =	null;
		try	{
			date = sdf.parse(source);
		} catch	(ParseException	e) {
			logger.error(e);
		}
		return date;
	}

	public static final	String formatDateWithFormat(Date source, String	format)	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date	= null;
		date = sdf.format(source);
		return date;
	}

    public static final	String formatDate(Date source, String	format)	{
        if(source != null){
            return formatDateWithFormat(source, format);
        }
        return "";
    }

	/**
	 * Returns a new Date with the hours, milliseconds,	seconds	and	minutes	set	to 0.
	 *
	 * @param date - date to calculate start of	day	from.
	 * @return start of	date.
	 */
	public static final	Date startOfDay	(Date date) {
		if (date == null)
			return null;

		Calendar calendar =	Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Returns the last	millisecond	of the specified date.
	 *
	 * @param date - date to calculate end of day from.
	 * @return last	millisecond	of date.
	 */
	public static final	Date endOfDay (Date	date)  {
		if (date == null)
			return null;

		Calendar calendar =	Calendar.getInstance();
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
	public static final int calculateDay (Date startDate, Date endDate)  {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(startDate);
		long startTimeInMillis = calendar.getTimeInMillis();

		calendar.setTime(endDate);
		long endTimeInMillis = calendar.getTimeInMillis();

		return Math.round((endTimeInMillis - startTimeInMillis)/DateUtils.MILLIS_PER_DAY);
	}

    /**
     * 取得指定日期的前n天
     * @param date Date 指定日期
     * @param n int 前n天，0表示當天
     * @param format 輸出格式
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
     * @param date 基準日
     * @param days 加減天數
     * @return
     */
    public static final	Date addDays (Date date, int days)  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
    }
    
    public static final String  NowDate() {
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
    	return df.format(new Date());// new Date()为获取当前系统时间
    	}

    /**
     * 取得日期之時分秒之LabelValueBean Object
     * @param type 1:start date; 2:end date。 
     * @return ArrayList<LabelValueBean>
     
    public static final ArrayList<LabelValueBean> getDateHmsLabelValueBean(int type){
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		for(int i=0; i <= 24;i++){
			String hmsLabel = "";
			String hmsValue = "";
			
			if(i < 10){
				hmsLabel = "0" + i + ":00";
				hmsValue = hmsLabel + ":00";
			}else if(i == 24){
				if(type  == 2){
					hmsLabel = "23:59";
					hmsValue = "23:59:59";
				}else{
					break;
				}
			}else{
				hmsLabel = i + ":00";
				hmsValue = hmsLabel + ":00";
			}
			
			list.add(new LabelValueBean(hmsLabel, hmsValue));
		}
		
		return list;
	}
    */
    public static final boolean isValidDate(String dateString, String formatter){
    	boolean isValid = false;
    	
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
    		sdf.parse(dateString);
			isValid = true;
		} catch (ParseException e) {
			logger.info("非合法之日期格式"+e.getMessage());
		}
    	
    	return isValid;
    }
    
    /**
     * 判斷多日請假請假中有無假日
     * @param s1
     * @param s2
     * @return
     * @throws ParseException
     */
    public  double jisuan(String s1 ,String s2) throws ParseException{
        SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d1 = s.parse(s1);
        Date d2 = s.parse(s2);
        SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd");
        Date dd1 = ss.parse(s1);
        Date dd11 = ss.parse(s1);
        Date dd2 = ss.parse(s2);
    
        Calendar ca = Calendar.getInstance();
        if(dd1.getTime()==dd2.getTime()){//同一天
            if(this.getweekdayloc(dd1,ca)==0){//星期天
                return 0;
            }else{
             
                return this.getOneShijian(d1,d2,ca);
            }
        }else{
            double bb=0.000;
            while(dd2.getTime()>=dd1.getTime()){
                if(this.getweekdayloc(dd1,ca)==1){
            
                    if(dd1.getTime()==dd11.getTime()){
                    	if(this.getshijian(d1,ca)<0.5){
                    	
                    	      bb+=0.5;
                    	
                    	}else{
                    	      bb+=this.getshijian(d1,ca);
                    	
                    	}
                  
                    }else if(dd1.getTime()==dd2.getTime()){
                    	if((1-this.getshijian(d2,ca))<0.5){
                    		   bb+=0.5;
                    
                    	}else{
                    	    bb+=(1-this.getshijian(d2,ca));
                    	
                  	  }
                    
                    }else{
                        bb+=1;
                   
                    }
                  }
                
                ca.setTime(d1);
                ca.add(Calendar.DATE, 1);
                d1=ca.getTime();
                ca.clear();
                ca.setTime(dd1);
                ca.add(Calendar.DATE, 1);
                dd1=ca.getTime();
             
            }
       	
            return bb;
        }
         
    }
     
    /**
     *月份每日考勤表
     * @param s1
     * @param s2
     * @return
     * @throws ParseException
     */
  /*  public  double monthSunDay(String s1 ,String s2) throws ParseException{
        SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d1 = s.parse(s1);
        Date d2 = s.parse(s2);
        SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd");
        Date dd1 = ss.parse(s1);
        Date dd11 = ss.parse(s1);
        Date dd2 = ss.parse(s2);
    
        Calendar ca = Calendar.getInstance();
        if(dd1.getTime()==dd2.getTime()){//同一天
            if(this.getweekdayloc(dd1,ca)==0){//星期天
                return 0;
            }else{
            //  	System.out.println("jisuan sw1 day: "+String.valueOf(this.getshijian(d2,ca)-(1-this.getshijian(d1,ca))));
                return this.getshijian(d2,ca)-(1-this.getshijian(d1,ca));
            }
        }else{
            double bb=0.000;
            while(dd2.getTime()>=dd1.getTime()){
                if(this.getweekdayloc(dd1,ca)==1){
            
                    if(dd1.getTime()==dd11.getTime()){
                    	if(this.getshijian(d1,ca)<0.5){
                    	
                    	      bb+=0.5;
                    	  	// System.out.println("jisuan sw2 day: "+bb);
                    	}else{
                    	      bb+=this.getshijian(d1,ca);
                    	 	// System.out.println("jisuan sw3 day: "+bb);
                    	}
                  
                    }else if(dd1.getTime()==dd2.getTime()){
                    	if((1-this.getshijian(d2,ca))<0.5){
                    		   bb+=0.5;
                    		//	 System.out.println("jisuan sw4 bb :"+bb);
                    		//	 System.out.println("jisuan sw4 day:  "+this.getshijian(d2,ca));
                    	}else{
                    	    bb+=(1-this.getshijian(d2,ca));
                    	//	 System.out.println("jisuan sw5 day: "+bb);
                  	  }
                    
                    }else{
                        bb+=1;
                   //	 System.out.println("jisuan sw6 day: "+bb);
                    }
                  }
                
                ca.setTime(d1);
                ca.add(Calendar.DATE, 1);
                d1=ca.getTime();
                ca.clear();
                ca.setTime(dd1);
                ca.add(Calendar.DATE, 1);
                dd1=ca.getTime();
             
            }
             
            return bb;
        }
    */
    
     
    //传进一个日期判断是否是周六、日    。六日返回0，其他返回1
    private  int getweekdayloc(Date date,Calendar ca){
        ca.setTime(date);
        if(ca.get(Calendar.DAY_OF_WEEK)==1 || ca.get(Calendar.DAY_OF_WEEK)==7){
            return 0;
        }else{
           // return getHolidayloc(date,1);
        	 return 1;
        }
         
    }
    
  /**
   * 传进一个日期判断星期幾
   * @param date
   * @param ca
   * @return
   */
    public static  int getweekday(Date date,Calendar ca){
        ca.setTime(date);
        return ca.get(Calendar.DAY_OF_WEEK);
         
    }
    
    /**
     * 計算當月幾天
     * @param date
     * @return
     */
    public static int getDaysOfTheMonth(Date date){//获取当月天数
		Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date); // 要计算你想要的月份，改变这里即可
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
	 
		return days;
	}
    
  //传进一个日期判断是否是公司放假日    。是返回0，其他返回1
    private int getHolidayloc(Date date,int re){
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
        String dd1 = ss.format(date);
        ArrayList al=new ArrayList();
        al.add("2017-03-27");//假設放假日
        for(int i=0;i<al.size();i++){
      
        	if(dd1.equals(al.get(i))){
        		re=0;
        		break;
        	}
        }
         return re;
    }
    
    //判断 
    private double getshijian(Date date,Calendar ca){
        ca.setTime(date);
        int shi = ca.get(Calendar.HOUR_OF_DAY);
        int fen = ca.get(Calendar.MINUTE);
        double d= shi+fen/60.000;
        if(shi<9.000){
            return 1.000;
        }else if(d<=12.500 && d>=9.000){
        
            return (12.500-d+(18.000-13.500))/8;
        }else if(d>12.500 && d<13.000){
        
            return (18.000-13.500)/8;
        }else if(d>=13.500&& d<=18.000){
         
            return (18.000-d)/8;
        }else{
            return 0.000;
        }
    }
    //判断 同一天
    private double getOneShijian(Date date1,Date date2,Calendar ca){
        ca.setTime(date1);
        int shi1= ca.get(Calendar.HOUR_OF_DAY);
        int fen1 = ca.get(Calendar.MINUTE);
        double d1 = shi1+fen1/60.000;
        
        ca.setTime(date2);
        int shi2 = ca.get(Calendar.HOUR_OF_DAY);
        int fen2 = ca.get(Calendar.MINUTE);
        double d2 = shi2+fen2/60.000;
        double d = 0.0;
        d=d2-d1;
        double r= 0.0;
   
        if(d>=4){
            r =1;
        }else if(d<=4){       
            r =0.5;
        }else if(d<=0){
            r =0;
        }
        return r;
    }
    /**
     * 傳出系統年/月
     * @return
     */
    public static  String getSysYearMonth(){
    	 String re="";
    	 Calendar cal = Calendar.getInstance();
    	 int year = cal.get(Calendar.YEAR);
    	 int month = cal.get(Calendar.MONTH )+1;
    	 if(month<=9){
    		 re=year+"/0"+month;
    	 }else{
    		 re=year+"/"+month;
    	 }
    	 return re;
    } 
    
    
}
