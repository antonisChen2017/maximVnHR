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
import java.util.List;
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
	 
	 
	 /*	final SimpleDateFormat SF = new SimpleDateFormat("yyyy/MM/dd HH:mm");
         
	        Date[] a = {SF.parse("2017/06/20 07:20"), SF.parse("2017/06/20 16:32")};
	        Date[] b = {SF.parse("2017/06/20 16:33"), SF.parse("2017/06/20 18:30")};
	         
	        System.out.println("您好, 智能的电脑! 请问:");
	        for (Date date : a) {
	            System.out.print(date.toString() + " ~  ");
	        }
	        System.out.println("包含:");
	        for (Date date : b) {
	            System.out.print(date.toString() + " ~  ");
	        }
	        System.out.println("吗?");
	         
	        boolean ret = DateUtil.isContain(a, b);
	        System.out.println("o(∩_∩)o 哈哈 ~ 我猜是: " + ret);
	         
	        ret = DateUtil.isContainEnd(a, b);
	        System.out.println("o(∩_∩)o 哈哈 ~ 允许首尾相等 我猜是: " + ret);*/
	// String t1="2017/07/03 16:30";
	// String t2="2017/07/03 23:59";
	// Long value=dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h");
	// System.out.println("o(∩_∩)o 哈哈 ~ 第一個時間為: " + value);
	// t1="2017/07/04 00:00";
	// t2="2017/07/04 02:30";
	// value=dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h");
	// System.out.println("o(∩_∩)o 哈哈 ~  第二個時間為: " + value);
	 String t1="2017/07/26";
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
	 	/*HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet("sheet1");
	        sheet.setDefaultColumnWidth(20); // 默认列宽

	        HSSFFont font = wb.createFont();
	        font.setFontName("Times New Roman");
	        font.setFontHeightInPoints((short) 13);// 设置字体大小
	        font.setColor(HSSFColor.BLACK.index); //字体颜色

	        HSSFCellStyle headStyle = wb.createCellStyle(); // 头部样式
	        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
	        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
	        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
	        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
	        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
	        headStyle.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
	        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        headStyle.setFont(font);// 选择需要用到的字体格式

	        HSSFCellStyle contentStyle = wb.createCellStyle(); // 内容样式
	        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
	        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
	        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
	        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

	        //打开图片  
	        InputStream is = new FileInputStream("D:\\Portal\\TEST\\excel.png");  
	        byte[] bytes = IOUtils.toByteArray(is);  
	          
	        // 增加图片到 Workbook  
	        int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);  
	        is.close();  
	     // Create the drawing patriarch.  This is the top level container for all shapes.  
	        Drawing drawing = sheet.createDrawingPatriarch();  
	        CreationHelper helper = wb.getCreationHelper(); 
	        //add a picture shape  
	        ClientAnchor anchor = helper.createClientAnchor();  
	     // 设置图片位置  
	        anchor.setCol1(3);  
	        anchor.setRow1(10);  
	        Picture pict = drawing.createPicture(anchor, pictureIdx);  
	      //auto-size picture relative to its top-left corner  
	        pict.resize();  
	        //表头
	        HSSFRow row1 = sheet.createRow((short) 0);
	        HSSFCell cell_1_0 = row1.createCell(0);
	        cell_1_0.setCellValue("员工编号");
	        cell_1_0.setCellStyle(headStyle);
	        
	        HSSFCell cell_1_1 = row1.createCell(1);
	        cell_1_1.setCellValue("姓名");
	        cell_1_1.setCellStyle(headStyle);
	        
	        HSSFCell cell_1_2 = row1.createCell(2);
	        cell_1_2.setCellValue("绩效等级");
	        cell_1_2.setCellStyle(headStyle);
	        
	        HSSFCell cell_1_3 = row1.createCell(3);
	        cell_1_3.setCellValue("考核类型");
	        cell_1_3.setCellStyle(headStyle);
	        
	        for(int i=1;i<=5;i++){
	            
	            HSSFRow row2 = sheet.createRow(i);
	            HSSFCell cell_2_0 = row2.createCell(0);
	            cell_2_0.setCellValue(String.valueOf((int)(Math.random()*100)));
	            cell_2_0.setCellStyle(contentStyle);
	            
	            HSSFCell cell_2_1 = row2.createCell(1);
	            cell_2_1.setCellValue("Test" + i);
	            cell_2_1.setCellStyle(contentStyle);
	            
	            HSSFCell cell_2_2 = row2.createCell(2);
	            cell_2_2.setCellValue("B");
	            cell_2_2.setCellStyle(contentStyle);

	            HSSFCell cell_2_3 = row2.createCell(3);
	            cell_2_3.setCellValue("月季考核");
	            cell_2_3.setCellStyle(contentStyle);
	            
	        }
	        
	        CellRangeAddress c = CellRangeAddress.valueOf("B1");
	        sheet.setAutoFilter(c);
	       
	        FileOutputStream fileOut = new FileOutputStream("d:/mysheet.xls"); //导出路径
	        wb.write(fileOut);
	        fileOut.close();
	        
	// String html="<H1>Jobs Report</H1><table border=\"1\"><tr><th>作业名</th><th>最近执行时间</th><th>最近执行状态</th><th>运行持续时间</th><th>最近运行状态信息</th><th>下次运行时间</th></tr>";
	 
       //  String s = sendmail("15618790307@163.com", "J1846K", "15618790307@163.com", "15618790307@163.com", "TESTportal", html);
       //  System.out.println(s);
        //  System.out.println(DateUtil.getWeekday("2017/07/08"));
	 
	/** Connection con=DBUtil.getHRCon();
	 leaveCardVO lc=new leaveCardVO();
	
		lc.setSearchRole("E");
		lc.setStatus("U");
		lc.setNote(keyConts.SingRoleL1EP);
		List<supervisorRO> lsr=DBUtil.queryEmailSingep(con,SqlUtil.queryEmailSingep(lc));
	 **/
	 
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
