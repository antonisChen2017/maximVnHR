package cn.com.maxim.portal.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


//portal@maxim-group.com.vn     -- 邮件帐户名称（SQL Server 使用）
//portal@maxim-group.com.vn -- 发件人邮件地址
//smtp.maxim-group.com.vn           -- 邮件服务器地址
//portal@maxim-group.com.vn -- 用户名
//ptvn123      -- 密码

public class testSql {

    static String SSL_EMAIL = "javax.net.ssl.SSLSocketFactory";


     public static void main(String[] args) {
	 
	 String html="<H1>Jobs Report</H1><table border=\"1\"><tr><th>作业名</th><th>最近执行时间</th><th>最近执行状态</th><th>运行持续时间</th><th>最近运行状态信息</th><th>下次运行时间</th></tr>";
	 
          String s = sendmail("portal@maxim-group.com.vn", "ptvn123", "portal@maxim-group.com.vn", "15618790307@163.com", "TESTportal", html);
          System.out.println(s);
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
         props.setProperty("mail.smtp.host", "smtp.maxim-group.com.vn");
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

}
