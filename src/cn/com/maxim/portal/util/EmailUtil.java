package cn.com.maxim.portal.util;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
    static String SSL_EMAIL = "javax.net.ssl.SSLSocketFactory";
    
    
    public static void main(String[] args) {
	 
  	    String html="<H1>Jobs Report</H1><table border=\"1\"><tr><th>作业名</th><th>最近执行时间</th><th>最近执行状态</th><th>运行持续时间</th><th>最近运行状态信息</th><th>下次运行时间</th></tr>";
  	 
            String s = sendmail("smtp.maxim-group.com.vn","25","portal@maxim-group.com.vn", "ptvn123", "portal@maxim-group.com.vn", "15618790307@163.com", "TESTportal", html);
            System.out.println(s);
       }
    
    /**
     *  @param host 郵件伺服器地址
     *  @param port 郵件伺服器port
     * @param username 发件人邮件的用户名
     * @param pass 发件人邮件的密码（此密码一定要是授权码   授权码：在发件人邮箱内开启smtp/pop3协议所获得的授权码）
     * @param from 发件人邮箱
     * @param to   收件人邮箱
     * @param subject 邮件标题
     * @param content 邮件内容
     * @return
     */
    public static String sendmail(String host,String port,final String username, final String pass,
            String from, String to, String subject, String content) {
        //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        //设置系统参数
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.socketFactory.class", SSL_EMAIL);
        props.setProperty("mail.smtp.socketFactory.fallback", "true");
        props.setProperty("mail.store.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
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
    
    /**
     * 檢查EMAIL格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
	Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	Matcher matcher = emailPattern.matcher(email);
	if(matcher.find()){
	return true;
	}
	return false;
	}
    
}
