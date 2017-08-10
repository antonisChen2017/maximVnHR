package cn.com.maxim.portal.util;

import java.util.Date;
import java.util.List;
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

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.leaveEmailListRO;
import cn.com.maxim.portal.attendan.ro.overEmailListRO;
import cn.com.maxim.portal.attendan.wo.EmailWO;
import cn.com.maxim.potral.consts.UrlUtil;

public class EmailUtil {
    static String SSL_EMAIL = "javax.net.ssl.SSLSocketFactory";

    public static void main(String[] args) {

	String html = "<H1>Jobs Report</H1><table border=\"1\"><tr><th>作业名</th><th>最近执行时间</th><th>最近执行状态</th><th>运行持续时间</th><th>最近运行状态信息</th><th>下次运行时间</th></tr>";

	// String s =
	// sendmail("smtp.maxim-group.com.vn","25","portal@maxim-group.com.vn",
	// "ptvn123", "portal@maxim-group.com.vn", "15618790307@163.com",
	// "TESTportal", html);
	// System.out.println(s);
    }

    /**
     * @param host
     *            郵件伺服器地址
     * @param port
     *            郵件伺服器port
     * @param username
     *            发件人邮件的用户名
     * @param pass
     *            发件人邮件的密码（此密码一定要是授权码 授权码：在发件人邮箱内开启smtp/pop3协议所获得的授权码）
     * @param from
     *            发件人邮箱
     * @param to
     *            收件人邮箱
     * @param subject
     *            邮件标题
     * @param content
     *            邮件内容
     * @return
     */
    public static String sendmail(EmailWO ew) {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(EmailUtil.class);
	// logger.info("ew:"+ew.toString());
	// 设置系统参数
	Properties props = System.getProperties();
	props.setProperty("mail.smtp.socketFactory.class", SSL_EMAIL);
	props.setProperty("mail.smtp.socketFactory.fallback", "true");
	props.setProperty("mail.store.protocol", "smtp");
	props.setProperty("mail.smtp.host", ew.getSMTP().trim());
	props.setProperty("mail.smtp.port", ew.getPORT().trim());
	// props.setProperty("mail.smtp.socketFactory.port", "25");
	props.put("mail.smtp.auth", "true");
	// 创建邮件会话
	Session session = Session.getInstance(props, new Authenticator() {
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(ew.getFROM().trim(), ew.getPW().trim());
	    }
	});
	// 创建邮件信息

	Message msg = new MimeMessage(session);
	try {
	    msg.setFrom(new InternetAddress(ew.getFROM().trim()));
	    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ew.getTO().trim(), false));
	    msg.setSubject(ew.getSUBJECT().trim());
	    // msg.setText(content);
	    msg.setContent(ew.getCONTENT().trim(), "text/html;charset = utf-8");
	    msg.setSentDate(new Date());
	    Transport.send(msg);
	    logger.info("send email is ok");
	    return "ok!";
	} catch (MessagingException e) {
	    logger.info("send email is error : " + e.getMessage());
	    return e.getMessage();
	}
    }

    /**
     * 檢查EMAIL格式
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
	Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	Matcher matcher = emailPattern.matcher(email);
	if (matcher.find()) {
	    return true;
	}
	return false;
    }

    /**
     * Template
     * 
     * @param email
     * @return
     */
    public static String getLeaveEmailTemplate(String USER, String EID, String RUSER) {
	HtmlUtil hu = new HtmlUtil();
	String Template = hu.gethtml(UrlUtil.email_oneLeaveEmail);
	Template = Template.replace("<USER/>", USER);
	Template = Template.replace("<EID/>", EID);
	Template = Template.replace("<RUSER/>", RUSER);
	return Template;
    }
    /**
     * 待工Template
     * 
     * @param email
     * @return
     */
    public static String getStopEmailTemplate(String USER, String EID, String RUSER) {
	HtmlUtil hu = new HtmlUtil();
	String Template = hu.gethtml(UrlUtil.email_oneStopEmail);
	Template = Template.replace("<USER/>", USER);
	Template = Template.replace("<EID/>", EID);
	Template = Template.replace("<RUSER/>", RUSER);
	return Template;
    }
    /**
     * Template
     * 
     * @param email
     * @return
     */
    public static String getOverEmailTemplate(String USER, String EID, String RUSER) {
	HtmlUtil hu = new HtmlUtil();
	String Template = hu.gethtml(UrlUtil.email_oneOverEmail);
	Template = Template.replace("<USER/>", USER);
	Template = Template.replace("<EID/>", EID);
	Template = Template.replace("<RUSER/>", RUSER);
	return Template;
    }

    /**
     * CS Template
     * 
     * @param email
     * @return
     */
    public static String getOverUrgentEmailTemplate(String USER, String EID, String RUSER) {
	HtmlUtil hu = new HtmlUtil();
	String Template = hu.gethtml(UrlUtil.email_oneUrgantOverEmail);
	Template = Template.replace("<USER/>", USER);
	Template = Template.replace("<EID/>", EID);
	Template = Template.replace("<RUSER/>", RUSER);
	return Template;
    }

    /**
     * 定時寄送列表email
     * 
     * @param USER
     * @param EID
     * @param RUSER
     * @return
     */
    public static String getLeaveEmailTemplateList(List<leaveEmailListRO> ler, String USER) {
	HtmlUtil hu = new HtmlUtil();
	String Template = hu.gethtml(UrlUtil.email_timeLeaveEmail);
	Template = Template.replace("<USER/>", USER);
	String row = "", tmp = "";
	for (int i = 0; i < ler.size(); i++) {
	    tmp = hu.gethtml(UrlUtil.email_LeaveEmailRow);

	    tmp = tmp.replace("<EMPLOYEE/>", ler.get(i).getEMPLOYEE());
	    tmp = tmp.replace("<EMPLOYEENO/>", ler.get(i).getEMPLOYEENO());
	    tmp = tmp.replace("<DEPARTMENT/>", ler.get(i).getDEPARTMENT());
	    tmp = tmp.replace("<UNIT/>", ler.get(i).getUNIT());
	    tmp = tmp.replace("<APPLICATIONDATE/>", ler.get(i).getAPPLICATIONDATE());
	    tmp = tmp.replace("<STARTLEAVEDATE/>", ler.get(i).getSTARTLEAVEDATE());
	    tmp = tmp.replace("<ENDLEAVEDATE/>", ler.get(i).getENDLEAVEDATE());
	    tmp = tmp.replace("<HOLIDAYNAME/>", ler.get(i).getHOLIDAYNAME());
	    row = row + tmp;
	}
	Template = Template.replace("<row/>", row);
	return Template;
    }

    /**
     * 定時寄送列表email
     * 
     * @param USER
     * @param EID
     * @param RUSER
     * @return
     */
    public static String getOverEmailTemplateList(List<overEmailListRO> ler, String USER) {
	HtmlUtil hu = new HtmlUtil();
	String Template = hu.gethtml(UrlUtil.email_timeOverEmail);
	Template = Template.replace("<USER/>", USER);
	String row = "", tmp = "";
	for (int i = 0; i < ler.size(); i++) {
	    tmp = hu.gethtml(UrlUtil.email_overEmailRow);

	    tmp = tmp.replace("<EMPLOYEE/>", ler.get(i).getEMPLOYEE());
	    tmp = tmp.replace("<EMPLOYEENO/>", ler.get(i).getEMPLOYEENO());
	    tmp = tmp.replace("<DEPARTMENT/>", ler.get(i).getDEPARTMENT());
	    tmp = tmp.replace("<UNIT/>", ler.get(i).getUNIT());
	    tmp = tmp.replace("<APPLICATIONHOURS/>", ler.get(i).getAPPLICATION_HOURS());
	    tmp = tmp.replace("<OVERTIMESTART/>", ler.get(i).getOVERTIME_START());
	    tmp = tmp.replace("<OVERTIMEEND/>", ler.get(i).getOVERTIME_END());
	    tmp = tmp.replace("<REASONS/>", ler.get(i).getREASONS());
	    row = row + tmp;
	}
	Template = Template.replace("<row/>", row);
	return Template;
    }

    
    /**
     * 定時寄送代工列表email
     * 
     * @param USER
     * @param EID
     * @param RUSER
     * @return
     */
    public static String getStopEmailTemplateList(List<overEmailListRO> ler, String USER) {
	HtmlUtil hu = new HtmlUtil();
	String Template = hu.gethtml(UrlUtil.email_timeStopEmail);
	Template = Template.replace("<USER/>", USER);
	String row = "", tmp = "";
	for (int i = 0; i < ler.size(); i++) {
	    tmp = hu.gethtml(UrlUtil.email_overEmailRow);

	    tmp = tmp.replace("<EMPLOYEE/>", ler.get(i).getEMPLOYEE());
	    tmp = tmp.replace("<EMPLOYEENO/>", ler.get(i).getEMPLOYEENO());
	    tmp = tmp.replace("<DEPARTMENT/>", ler.get(i).getDEPARTMENT());
	    tmp = tmp.replace("<UNIT/>", ler.get(i).getUNIT());
	    tmp = tmp.replace("<APPLICATIONHOURS/>", ler.get(i).getAPPLICATION_HOURS());
	    tmp = tmp.replace("<OVERTIMESTART/>", ler.get(i).getOVERTIME_START());
	    tmp = tmp.replace("<OVERTIMEEND/>", ler.get(i).getOVERTIME_END());
	    tmp = tmp.replace("<REASONS/>", ler.get(i).getREASONS());
	    row = row + tmp;
	}
	Template = Template.replace("<row/>", row);
	return Template;
    }
    
    /**
     * 定時寄送CS列表email
     * 
     * @param USER
     * @param EID
     * @param RUSER
     * @return
     */
    public static String getCSEmailTemplateList(List<overEmailListRO> ler, String USER) {
	HtmlUtil hu = new HtmlUtil();
	String Template = hu.gethtml(UrlUtil.email_timeCSEmail);
	Template = Template.replace("<USER/>", USER);
	String row = "", tmp = "";
	for (int i = 0; i < ler.size(); i++) {
	    tmp = hu.gethtml(UrlUtil.email_overEmailRow);

	    tmp = tmp.replace("<EMPLOYEE/>", ler.get(i).getEMPLOYEE());
	    tmp = tmp.replace("<EMPLOYEENO/>", ler.get(i).getEMPLOYEENO());
	    tmp = tmp.replace("<DEPARTMENT/>", ler.get(i).getDEPARTMENT());
	    tmp = tmp.replace("<UNIT/>", ler.get(i).getUNIT());
	    tmp = tmp.replace("<APPLICATIONHOURS/>", ler.get(i).getAPPLICATION_HOURS());
	    tmp = tmp.replace("<OVERTIMESTART/>", ler.get(i).getOVERTIME_START());
	    tmp = tmp.replace("<OVERTIMEEND/>", ler.get(i).getOVERTIME_END());
	    tmp = tmp.replace("<REASONS/>", ler.get(i).getREASONS());
	    row = row + tmp;
	}
	Template = Template.replace("<row/>", row);
	return Template;
    }
    
}
