package cn.com.maxim.portal.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

import cn.com.maxim.portal.attendan.ro.supervisorRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.potral.consts.keyConts;


//portal@maxim-group.com.vn     -- 邮件帐户名称（SQL Server 使用）
//portal@maxim-group.com.vn -- 发件人邮件地址
//smtp.maxim-group.com.vn           -- 邮件服务器地址
//portal@maxim-group.com.vn -- 用户名
//ptvn123      -- 密码

public class testSql {

    static String SSL_EMAIL = "javax.net.ssl.SSLSocketFactory";


     public static void main(String[] args) throws Exception {
	 	String t1="2017/09/03";
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		
		//Map<String, Object>  re=convertWeekByDate(  sd.parse(t1));
	 
	
	/** String t1="2017/07/26";
	 int value=-1;
	 SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	        Calendar c = Calendar.getInstance();
	        Date frist= format.parse(t1);
	        c.setTime(frist);
	        c.add(Calendar.YEAR, value);
	        Date y = c.getTime();
	        String year = format.format(y);
	        System.out.println("过去一年："+year);
	        value=-2;
	       frist= format.parse(t1);
	        c.setTime(frist);
	        c.add(Calendar.YEAR, value);
	        y = c.getTime();
	        year = format.format(y);
	        System.out.println("过去2年："+year);

	// System.out.println("此月分最後一個上班日:"+ getMonthWorkDay("2017/06"));
	     String yesterday="";
	     String ym="2017/06";
	     Calendar cal = Calendar.getInstance();
	     cal.add(Calendar.DATE, 0);
	     String today = new SimpleDateFormat( "yyyy/MM/dd").format(cal.getTime());
	     String[] todays=today.split("/");
	     String[] yms=ym.split("/");
	     if(todays[0].equals(yms[0]) && todays[1].equals(yms[1])){
		  System.out.println("本月取昨天 但不能低於01日");
		  System.out.println("todays[2]  "+todays[2]);
		  if(todays[2].trim().equals("01")){
		      yesterday=today;
		  }else{
		      cal.add(Calendar.DATE, -1);
		      yesterday= new SimpleDateFormat( "yyyy/MM/dd").format(cal.getTime());
		  }
	     }else{
		  System.out.println("其他月取當月最後一天");
		  int maxDate = 0;
		Date first = null;
			 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		first = sdf.parse(ym);
		 cal.setTime(first);
		  int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置创造新日期，这个日期是本月的最后一天
		cal.set(Calendar.DATE, days);
		// 然后打印出来
		Date newD = cal.getTime();
		// 加上格式化
		System.out.println("本月最后一天的日期是："+ new SimpleDateFormat("yyyy/MM/dd").format(newD));
		 yesterday=  new SimpleDateFormat("yyyy/MM/dd").format(newD);
	     }
	    System.out.println(yesterday);
	    **/
     }
     
     /** 
      * 根据日期计算所在周的上下界 
      *  
      * @param time 
     * @throws Exception 
      */  
     public static String convertWeekByDate(String t1) throws Exception {  
	 SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
	 Date time=sd.parse(t1);
         Map<String, Object> map = new HashMap<String, Object>();  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式  
         Calendar cal = Calendar.getInstance();  
         cal.setTime(time);  
         // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
         int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
         if (1 == dayWeek) {  
             cal.add(Calendar.DAY_OF_MONTH, -1);  
         }  
         System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期  
         cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
         int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
         cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
         String imptimeBegin = sdf.format(cal.getTime());  
         System.out.println("所在周星期一的日期：" + imptimeBegin);  
         cal.add(Calendar.DATE, 6);  
         String imptimeEnd = sdf.format(cal.getTime());  
         System.out.println("所在周星期日的日期：" + imptimeEnd);  
   
         map.put("first", imptimeBegin);  
   
         map.put("last", imptimeEnd);  
   
         return imptimeBegin;  
     } 
  
     /**
      * 計算當月最後一個上班日
      * 
      * @param date
      * @return
      */
     public static String getMonthWorkDay(String YM) throws Exception {// 获取当月天数
 	String re="";
 	Calendar ca = Calendar.getInstance();
 	SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM");
 	 System.out.println("1");
 	Date dd1 = ss.parse(YM);
 	ca.setTime(dd1); // 要计算你想要的月份，改变这里即可
	 System.out.println("2");
 	int days = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
	 System.out.println("3 days="+days);
 	for(int i=days;i>0;i--){
 		String ymd=YM+"/"+i;
 		 System.out.println("4 ymd="+ymd);
 	 	if(DateUtil.getWeekday(ymd)!=1){
 	 	  System.out.println("5 ymd="+ymd);
 	 	   re=ymd;
 	 	   break;
 	 	}
 	}
 	return re;
     }
     
     public static Long dateDiff(String startTime, String endTime,     
	            String format, String str) {     
	        // 按照传入的格式生成一个simpledateformate对象     
	        SimpleDateFormat sd = new SimpleDateFormat(format);     
	        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数     
	        long nh = 1000 * 60 * 60;// 一小时的毫秒数     
	        long nm = 1000 * 60;// 一分钟的毫秒数     
	        long ns = 1000;// 一秒钟的毫秒数     
	        long diff;     
	        long day = 0;     
	        long hour = 0;     
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
	            System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"    
	                    + (min - day * 24 * 60) + "分钟" + sec + "秒。");     
	            System.out.println("hour=" + hour + ",min=" + min);     
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
      * 
      * @param username 发件人邮件的用户名
      * @param pass 发件人邮件的密码（此密码一定要是授权码   授权码：在发件人邮箱内开启smtp/pop3协议所获得的授权码）
      * @param from 发件人邮箱
      * @param to   收件人邮箱
      * @param subject 邮件标题
      * @param content 邮件内容
      * @return
      */
     public static String sendmail(final String username, final String pass,
             String from, String to, String subject, String content) {
         //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
         //设置系统参数
         Properties props = System.getProperties();
         props.setProperty("mail.smtp.socketFactory.class", SSL_EMAIL);
         props.setProperty("mail.smtp.socketFactory.fallback", "true");
         props.setProperty("mail.store.protocol", "smtp");
         props.setProperty("mail.smtp.host", "smtp.163.com");
         props.setProperty("mail.smtp.port", "25");
        // props.setProperty("mail.smtp.socketFactory.port", "25");
          props.put("mail.smtp.auth", "true");
         //创建邮件会话
         Session session = Session.getInstance(props, new Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, pass);
             }
         });
         //创建邮件信息
         
         Message msg = new MimeMessage(session);
         try {
             msg.setFrom(new InternetAddress(username));
             msg.setRecipients(Message.RecipientType.TO,
                     InternetAddress.parse(to, false));
             msg.setSubject(subject);
            // msg.setText(content);
             msg.setContent(content, "text/html;charset = utf-8");  
             msg.setSentDate(new Date());
             Transport.send(msg);
             return "1";
         } catch (MessagingException e) {
             return e.getMessage();
         }
     }
	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
 
 
public static String generateShortUuid() {
    StringBuffer shortBuffer = new StringBuffer();
    String uuid = UUID.randomUUID().toString().replace("-", "");
    for (int i = 0; i < 8; i++) {
        String str = uuid.substring(i * 4, i * 4 + 4);
        int x = Integer.parseInt(str, 16);
        shortBuffer.append(chars[x % 0x3E]);
    }
    return shortBuffer.toString();
 
}
public static ArrayList getDates(String year, String month) {
	int maxDate = 0;
	Date first = null;
	Date two = null;
	Date[] dates =null;
	String newDay="";
	ArrayList listDates=new ArrayList();
	try {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		first = sdf.parse(year + month);
		cal.setTime(first);
		maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);

	//	 System.out.println("maxDate="+maxDate);
	SimpleDateFormat srf = new SimpleDateFormat("yyyy/MM/dd");
	 dates = new Date[maxDate];
	for (int i = 1; i <= maxDate; i++) {
		dates[i - 1] = new Date(first.getTime());
		first.setDate(first.getDate() + 1);
		newDay=srf.format(dates[i - 1]);
	
	    cal.setTime(dates[i - 1]);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		 {
		//	System.out.println("no SUNDAY");
		 }else{
			 if(newDay.split("/")[1].equals(month)) {
				 System.out.println(newDay);
				 listDates.add(newDay);
			 }
		 }
	
		//System.out.println(String.valueOf(dates[i - 1]));
	}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listDates;
}


public static int getSun(String year, String month) {
	int maxDate = 0;
	Date first = null;
	Date two = null;
	Date[] dates =null;
	String newDay="";
	ArrayList listDates=new ArrayList();
	int sun=0;
	try {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		first = sdf.parse(year + month);
		cal.setTime(first);
		maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);

	//	 System.out.println("maxDate="+maxDate);
	SimpleDateFormat srf = new SimpleDateFormat("yyyy/MM/dd");
	 dates = new Date[maxDate];
	for (int i = 1; i <= maxDate; i++) {
		dates[i - 1] = new Date(first.getTime());
		first.setDate(first.getDate() + 1);
		newDay=srf.format(dates[i - 1]);
	
	    cal.setTime(dates[i - 1]);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		 {
			sun++;
		 }
	
		//System.out.println(String.valueOf(dates[i - 1]));
	}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return sun*8;
}
/**
 * 比較時間大小
 * @param t1
 * @param t2
 * @return
 */
private static String largerTime(String t1,String t2) 
{
    Date date1 ,date2;
    DateFormat formart = new SimpleDateFormat("hh:mm");
    try
    {
        date1 = formart.parse(t1);
        date2 = formart.parse(t2);
        if(date1.compareTo(date2)<0)
        {
            return "2";
        }
        else
        {
            return "1";
        }
    }
    catch (ParseException e)
    {
        System.out.println("date init fail!");
        e.printStackTrace();
        return null;
    }
     
}
}
