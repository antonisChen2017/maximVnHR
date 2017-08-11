package cn.com.maxim.portal.util;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
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

import cn.com.maxim.portal.attendan.ro.empnumRO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.repAttendanceVO;
import cn.com.maxim.portal.hr.rep_Attendance;
import cn.com.maxim.portal.hr.rev_LeaveCard;

public class ExcelUtil<T>
{
    	Log4jUtil lu=new Log4jUtil();
	org.apache.log4j.Logger logger  =lu.initLog4j(ExcelUtil.class);
	
	/**
	 * 遲到早退報表
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param title1
	 * @param title2
	 * @param lcVo
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportLateEarlyExcel(String title, String[] headers, List<T> dataset,lateOutEarlyVO eaVo ,String rootPath) throws Exception {
	  	
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth((short) 21);  
        
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
        BufferedImage bufferImg = ImageIO.read(new File(rootPath+"\\images\\excel\\icon.png"));  
        ImageIO.write(bufferImg, "png", byteArrayOut);  
          
        HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();  
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,255, 255,(short) 1, 2, (short)  2, 3);
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));  
        
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("Times New Roman");   
        font.setFontHeightInPoints((short) 10.5);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式  
        style.setFont(font);
        // 欄位自動換行
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        
        HSSFCellStyle blueStyle = workbook.createCellStyle();
        blueStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        blueStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        blueStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);// 设置背景色
        // 生成一个字体  
        blueStyle.setFont(font);
        // 欄位自動換行
        blueStyle.setWrapText(true);
        blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        blueStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font2.setColor(HSSFColor.BLACK.index); //字体颜色
        // 把字体应用到当前的样式  
        style2.setFont(font2);

        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("Times New Roman");   
        headfont.setFontHeightInPoints((short) 14);// 字体大小   
     
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        style3.setFont(headfont);
        
        /****/
        // 生成并设置另一个样式  
        HSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.WHITE.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font5 = workbook.createFont();
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font5.setFontName("Times New Roman");   
        font5.setFontHeightInPoints((short) 10.5);// 字体大小   
        font5.setColor(HSSFColor.BLACK.index); //字体颜色
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        // 把字体应用到当前的样式  
        style4.setFont(font5);
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style5 = workbook.createCellStyle();
        style5.setFillForegroundColor(HSSFColor.WHITE.index);
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       // style5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 把字体应用到当前的样式  
        style5.setFont(font5);
        
        HSSFCellStyle style6 = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style6.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style6.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style6.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style6.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style6.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style6.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style6.setFont(font5);
        
        // 产生表格标题行  
           HSSFRow row = sheet.createRow(0);
           row.setHeightInPoints((float) 6.75);
           row = sheet.createRow(5);
           row.setHeightInPoints((float) 6.75);
        
            // 产生表格标题行  
             row = sheet.createRow(1);
             HSSFCell  cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             HSSFRichTextString textT= new HSSFRichTextString("CÔNG TY TNHH VIỆT NAM MỸ THANH");
             cellT.setCellValue(textT);
             
             
             // 产生表格标题行  
             row = sheet.createRow(2);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("越南美声服饰辅料有限公司");
             cellT.setCellValue(textT);
          
              // 产生表格标题行  
             row = sheet.createRow(3);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("BẢNG CHẤM CÔNG THÁNG "+eaVo.getQueryYearMonth().split("/")[0]+"/"+eaVo.getQueryYearMonth().split("/")[1]);
             cellT.setCellValue(textT);
             // 产生表格标题行  
             row = sheet.createRow(4);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString(eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月報表");
             cellT.setCellValue(textT);
        
        HSSFRow row6 = sheet.createRow(6);
        HSSFCell cell_6_0 = row6.createCell(0);
        cell_6_0.setCellValue("STT \n 序 \n 次");
        cell_6_0.setCellStyle(style);
      
         HSSFCell cell_6_1 = row6.createCell(1);
        cell_6_1.setCellValue("MST \n 工号");
        cell_6_1.setCellStyle(style);
        
        HSSFCell cell_6_2 = row6.createCell(2);
        cell_6_2.setCellValue("HỌ TÊN\n姓名");
        cell_6_2.setCellStyle(style);
        
        HSSFCell cell_6_3 = row6.createCell(3);
        cell_6_3.setCellValue("BỘ PHẬN\n部門");
        cell_6_3.setCellStyle(style);
        
        HSSFCell cell_6_4 = row6.createCell(4);
        cell_6_4.setCellValue("ĐƠN VỊ\n单位");
        cell_6_4.setCellStyle(style);
      
        HSSFCell cell_6_5 = row6.createCell(5);
        cell_6_5.setCellValue("1");
        cell_6_5.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"01"));
        
        HSSFCell cell_6_6 = row6.createCell(6);
        cell_6_6.setCellValue("2");
        cell_6_6.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"02"));
        
        HSSFCell cell_6_7 = row6.createCell(7);
        cell_6_7.setCellValue("3");
        cell_6_7.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"03"));
        
        HSSFCell cell_6_8 = row6.createCell(8);
        cell_6_8.setCellValue("4");
        cell_6_8.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"04"));
        
        HSSFCell cell_6_9 = row6.createCell(9);
        cell_6_9.setCellValue("5");
        cell_6_9.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"05"));
        
        HSSFCell cell_6_10 = row6.createCell(10);
        cell_6_10.setCellValue("6");
        cell_6_10.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"06"));
        
        HSSFCell cell_6_11 = row6.createCell(11);
        cell_6_11.setCellValue("7");
        cell_6_11.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"07"));
        
        HSSFCell cell_6_12 = row6.createCell(12);
        cell_6_12.setCellValue("8");
        cell_6_12.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"08"));
        
        HSSFCell cell_6_13 = row6.createCell(13);
        cell_6_13.setCellValue("9");
        cell_6_13.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"09"));
        
        HSSFCell cell_6_14 = row6.createCell(14);
        cell_6_14.setCellValue("10");
        cell_6_14.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"10"));
        
        HSSFCell cell_6_15 = row6.createCell(15);
        cell_6_15.setCellValue("11");
        cell_6_15.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"11"));
        
        HSSFCell cell_6_16 = row6.createCell(16);
        cell_6_16.setCellValue("12");
        cell_6_16.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"12"));
        
        HSSFCell cell_6_17 = row6.createCell(17);
        cell_6_17.setCellValue("13");
        cell_6_17.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"13"));
        
        HSSFCell cell_6_18 = row6.createCell(18);
        cell_6_18.setCellValue("14");
        cell_6_18.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"14"));
        
        HSSFCell cell_6_19 = row6.createCell(19);
        cell_6_19.setCellValue("15");
        cell_6_19.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"15"));
        
        HSSFCell cell_6_20 = row6.createCell(20);
        cell_6_20.setCellValue("16");
        cell_6_20.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"16"));
        
        HSSFCell cell_6_21 = row6.createCell(21);
        cell_6_21.setCellValue("17");
        cell_6_21.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"17"));
        
        HSSFCell cell_6_22 = row6.createCell(22);
        cell_6_22.setCellValue("18");
        cell_6_22.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"18"));
        
        HSSFCell cell_6_23 = row6.createCell(23);
        cell_6_23.setCellValue("19");
        cell_6_23.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"19"));
        
        HSSFCell cell_6_24 = row6.createCell(24);
        cell_6_24.setCellValue("20");
        cell_6_24.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"20"));
     
        HSSFCell cell_6_25 = row6.createCell(25);
        cell_6_25.setCellValue("21");
        cell_6_25.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"21"));
        
        HSSFCell cell_6_26 = row6.createCell(26);
        cell_6_26.setCellValue("22");
        cell_6_26.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"22"));
        
        HSSFCell cell_6_27 = row6.createCell(27);
        cell_6_27.setCellValue("23");
        cell_6_27.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"23"));
        
        HSSFCell cell_6_28 = row6.createCell(28);
        cell_6_28.setCellValue("24");
        cell_6_28.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"24"));
        
        HSSFCell cell_6_29 = row6.createCell(29);
        cell_6_29.setCellValue("25");
        cell_6_29.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"25"));
        
        HSSFCell cell_6_30 = row6.createCell(30);
        cell_6_30.setCellValue("26");
        cell_6_30.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"26"));
        
        HSSFCell cell_6_31 = row6.createCell(31);
        cell_6_31.setCellValue("27");
        cell_6_31.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"27"));
        
        HSSFCell cell_6_32 = row6.createCell(32);
        cell_6_32.setCellValue("28");
        cell_6_32.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"28"));
        
        HSSFCell cell_6_33 = row6.createCell(33);
        cell_6_33.setCellValue("29");
        cell_6_33.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"29"));
        
        HSSFCell cell_6_34 = row6.createCell(34);
        cell_6_34.setCellValue("30");
        cell_6_34.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"30"));
        
        HSSFCell cell_6_35 = row6.createCell(35);
        cell_6_35.setCellValue("31");
        cell_6_35.setCellStyle(DateUtil.checkReportDate(style,blueStyle,eaVo.getQueryYearMonth(),"31"));
        
        HSSFCell cell_6_36 = row6.createCell(36);
        cell_6_36.setCellValue("chia \n 分");
        cell_6_36.setCellStyle(style);
        
        HSSFCell cell_6_37 = row6.createCell(37);
        cell_6_37.setCellValue("giờ \n 小时");
        cell_6_37.setCellStyle(style);
        
        HSSFCell cell_6_38 = row6.createCell(38);
        if( eaVo.getQueryIsLate().equals("1")){
            cell_6_38.setCellValue("lần cuối \n 迟到次数");
        }else{
            cell_6_38.setCellValue("Để lại lần đầu \n 早退次数");
        }
        cell_6_38.setCellStyle(style);
        
        /**加上排序欄位**/
        sheet.setAutoFilter(CellRangeAddress.valueOf("A7:AM7"));
        /**凍結左邊兩行**/
        sheet.createFreezePane(3, 7, 3, 7);
        
        /**設定每一單元格寬度**/
       // sheet.setColumnWidth((short) 21);  
        sheet.setColumnWidth(0, (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(1, (int)((7.88 + 0.72) * 256));
        sheet.setColumnWidth(2,  (int)((21.63 + 0.72) * 256));
        sheet.setColumnWidth(3,  (int)((9 + 0.72) * 256));
        sheet.setColumnWidth(4,  (int)((9.38 + 0.72) * 256));
        sheet.setColumnWidth(5,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(6,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(7,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(8,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(9,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(10,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(11,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(12,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(13,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(14,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(15,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(16,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(17,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(18,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(19,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(20,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(21,  (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(22,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(23,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(24,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(25,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(26,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(27,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(28,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(29,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(30,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(31,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(32,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(33,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(34,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(35,   (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(36,   (int)((4.5 + 0.72) * 256));
        sheet.setColumnWidth(37,   (int)((4.5 + 0.72) * 256));
        sheet.setColumnWidth(38,   (int)((10 + 0.72) * 256));
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 6;

        
        
        HSSFFont font4 = workbook.createFont();
        font4.setFontName("Times New Roman");   
        font4.setFontHeightInPoints((short) 9);// 字体大小   
        font4.setColor(HSSFColor.BLACK.index);
        
        /**計算全廠出勤**/
        float   f  =0;
        try {

            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                row.setHeightInPoints((float) 30);
                T t = (T) it.next();

                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
               
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    
                  //  logger.info("late fieldName:"+fieldName);
                    cell.setCellStyle(DateUtil.checkReportColDate(style2,blueStyle,eaVo.getQueryYearMonth(),fieldName));
                    Class tCls = t.getClass();

                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});

                    //全部当做字符串来处理
                    String textValue = value.toString();
                    if(i==38){
                	f =f +Float.parseFloat(textValue);
                    }
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        richString.applyFont(font4);
                        cell.setCellValue(richString);
                    }
                }
            }
            //最後幾行
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <39; i++) {
        	   HSSFCell cell = row.createCell(i);
                   cell.setCellStyle(style6);
                   if(i==2){
                       cell.setCellValue("TỔNG");
                   }
                   if(i==38){
                       cell.setCellValue(f);
                   }
            }
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 6.75);
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <39; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==14){
                    cell.setCellValue("CHỦ QUẢGiám đốc xác nhận");
                }   
           }
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <39; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==15){
                    cell.setCellValue("主管确认");
                }   
           }
          
        } catch (NoSuchMethodException ex) {
           System.out.println("NoSuch ex="+ex);
        } catch (SecurityException ex) {
            System.out.println("Security ex="+ex);
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccess ex="+ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("(IllegalArgument ex="+ex);
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTarget ex="+ex);
        }
        
        return workbook;
    }
	
	
	
	

	/**
	 * 日報表
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param title1
	 * @param title2
	 * @param lcVo
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportDayExcel(String title, String[] headers,String[] twoHeaders, List<T> dataset,String title1,String title2,leaveCardVO lcVo,String rootPath) throws Exception {
	  	
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth((short) 21);  
        
        //打开图片  
      /*  InputStream is;
        is = new FileInputStream("D:\\mxportal\\webroot\\images\\excel\\icon.png");
        byte[] bytes = IOUtils.toByteArray(is);  
        // 增加图片到 Workbook  
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);  
        is.close();  
     // Create the drawing patriarch.  This is the top level container for all shapes.  
        Drawing drawing = sheet.createDrawingPatriarch();  
        CreationHelper helper = workbook.getCreationHelper(); 
        //add a picture shape  
        ClientAnchor anchor = helper.createClientAnchor();  
     // 设置图片位置  
        anchor.setCol1(1);  
        anchor.setRow1(2);  
        Picture pict = drawing.createPicture(anchor, pictureIdx);  
      //auto-size picture relative to its top-left corner  
        pict.resize();  
    //    pict.
        */
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
        BufferedImage bufferImg = ImageIO.read(new File(rootPath+"\\images\\excel\\icon.png"));  
        ImageIO.write(bufferImg, "png", byteArrayOut);  
          
        HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();  
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,255, 255,(short) 1, 2, (short)  2, 3);
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));  
        
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("Times New Roman");   
        font.setFontHeightInPoints((short) 10.5);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式  
        style.setFont(font);
        // 欄位自動換行
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font2.setColor(HSSFColor.BLACK.index); //字体颜色
        // 把字体应用到当前的样式  
        style2.setFont(font2);

        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("Times New Roman");   
        headfont.setFontHeightInPoints((short) 14);// 字体大小   
     
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        style3.setFont(headfont);
        
        /****/
        // 生成并设置另一个样式  
        HSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.WHITE.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font5 = workbook.createFont();
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font5.setFontName("Times New Roman");   
        font5.setFontHeightInPoints((short) 10.5);// 字体大小   
        font5.setColor(HSSFColor.BLACK.index); //字体颜色
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        // 把字体应用到当前的样式  
        style4.setFont(font5);
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style5 = workbook.createCellStyle();
        style5.setFillForegroundColor(HSSFColor.WHITE.index);
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       // style5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 把字体应用到当前的样式  
        style5.setFont(font5);
        
        HSSFCellStyle style6 = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style6.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style6.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style6.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style6.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style6.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style6.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style6.setFont(font5);
        
        // 产生表格标题行  
           HSSFRow row = sheet.createRow(0);
           row.setHeightInPoints((float) 6.75);
           row = sheet.createRow(5);
           row.setHeightInPoints((float) 6.75);
        
            // 产生表格标题行  
             row = sheet.createRow(1);
             HSSFCell  cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             HSSFRichTextString textT= new HSSFRichTextString("CÔNG TY TNHH VIỆT NAM MỸ THANH");
             cellT.setCellValue(textT);
             
             
             // 产生表格标题行  
             row = sheet.createRow(2);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("越南美声服饰辅料有限公司");
             cellT.setCellValue(textT);
          
              // 产生表格标题行  
             row = sheet.createRow(3);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("BẢNG CHẤM CÔNG THÁNG "+lcVo.getApplicationDate().split("/")[0]+"/"+lcVo.getApplicationDate().split("/")[1]+"/"+lcVo.getApplicationDate().split("/")[2]);
             cellT.setCellValue(textT);
             // 产生表格标题行  
             row = sheet.createRow(4);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString(lcVo.getApplicationDate().split("/")[0]+"年"+lcVo.getApplicationDate().split("/")[1]+"月"+lcVo.getApplicationDate().split("/")[2]+"日報表");
             cellT.setCellValue(textT);
        
        HSSFRow row6 = sheet.createRow(6);
        HSSFCell cell_6_0 = row6.createCell(0);
        cell_6_0.setCellValue("STT \n 序 \n 次");
        cell_6_0.setCellStyle(style);
      
         HSSFCell cell_6_1 = row6.createCell(1);
        cell_6_1.setCellValue("MST \n 工号");
        cell_6_1.setCellStyle(style);
        
        HSSFCell cell_6_2 = row6.createCell(2);
        cell_6_2.setCellValue("HỌ TÊN\n姓名");
        cell_6_2.setCellStyle(style);
        
        HSSFCell cell_6_3 = row6.createCell(3);
        cell_6_3.setCellValue("BỘ PHẬN\n部門");
        cell_6_3.setCellStyle(style);
        
        HSSFCell cell_6_4 = row6.createCell(4);
        cell_6_4.setCellValue("ĐƠN VỊ\n单位");
        cell_6_4.setCellStyle(style);
      
        HSSFCell cell_6_5 = row6.createCell(5);
        cell_6_5.setCellValue("正班出勤\n Giờ công ");
        cell_6_5.setCellStyle(style);
        
        HSSFCell cell_6_6 = row6.createCell(6);
        cell_6_6.setCellValue("加班\n làm thêm giờ");
        cell_6_6.setCellStyle(style);
        
        HSSFCell cell_6_7 = row6.createCell(7);
        cell_6_7.setCellValue("年假\nPhép năm ");
        cell_6_7.setCellStyle(style);
        
        HSSFCell cell_6_8 = row6.createCell(8);
        cell_6_8.setCellValue("公假\nPhép công");
        cell_6_8.setCellStyle(style);
        
        HSSFCell cell_6_9 = row6.createCell(9);
        cell_6_9.setCellValue("產假\n Thai sản");
        cell_6_9.setCellStyle(style);
        
        HSSFCell cell_6_10 = row6.createCell(10);
        cell_6_10.setCellValue("婚假\nKêt hôn");
        cell_6_10.setCellStyle(style);
        
        HSSFCell cell_6_11 = row6.createCell(11);
        cell_6_11.setCellValue("喪假\nPhép tang");
        cell_6_11.setCellStyle(style);
        
        HSSFCell cell_6_12 = row6.createCell(12);
        cell_6_12.setCellValue("病假\nPhép bệnh");
        cell_6_12.setCellStyle(style);
        
        HSSFCell cell_6_13 = row6.createCell(13);
        cell_6_13.setCellValue("事假\nViệc riêng");
        cell_6_13.setCellStyle(style);
        
        HSSFCell cell_6_14 = row6.createCell(14);
        cell_6_14.setCellValue("工伤\n vết thương");
        cell_6_14.setCellStyle(style);
        
        HSSFCell cell_6_15 = row6.createCell(15);
        cell_6_15.setCellValue("调休\n ngày nghỉ");
        cell_6_15.setCellStyle(style);
        
        HSSFCell cell_6_16 = row6.createCell(16);
        cell_6_16.setCellValue("公假\n khác");
        cell_6_16.setCellStyle(style);
        
        HSSFCell cell_6_17 = row6.createCell(17);
        cell_6_17.setCellValue("旷工\nLãng công");
        cell_6_17.setCellStyle(style);
        
        HSSFCell cell_6_18 = row6.createCell(18);
        cell_6_18.setCellValue("迟到\n  Đi trê ");
        cell_6_18.setCellStyle(style);
        
        HSSFCell cell_6_19 = row6.createCell(19);
        cell_6_19.setCellValue("早退\n về sớm ");
        cell_6_19.setCellStyle(style);
        
        HSSFCell cell_6_20 = row6.createCell(20);
        cell_6_20.setCellValue("待工\nNghichờ \n việc");
        cell_6_20.setCellStyle(style);
        
        HSSFCell cell_6_21 = row6.createCell(21);
        cell_6_21.setCellValue("簽名\n Ký tên");
        cell_6_21.setCellStyle(style);
        
        HSSFCell cell_6_22 = row6.createCell(22);
        cell_6_22.setCellValue("GHI CHÚ备注");
        cell_6_22.setCellStyle(style);
        
     //   HSSFCell cell_6_19 = row6.createCell(19);
     //   cell_6_19.setCellValue("");
      //  cell_6_19.setCellStyle(style4);
        
      //  HSSFCell cell_6_20 = row6.createCell(20);
      //  cell_6_20.setCellValue("phep thai san");
      //  cell_6_20.setCellStyle(style4);
        
       // HSSFCell cell_6_21 = row6.createCell(21);
       // cell_6_21.setCellValue("TRỪ THẺ \nBHYT");
       // cell_6_21.setCellStyle(style4);
        
      //  HSSFCell cell_6_22 = row6.createCell(22);
      //  cell_6_22.setCellValue("Trừ tiền đồng phục");
      //  cell_6_22.setCellStyle(style4);
        
      //  HSSFCell cell_6_23 = row6.createCell(23);
     //   cell_6_23.setCellValue("ok");
    //    cell_6_23.setCellStyle(style4);
        
     
        
   //     for (short i = 0; i < headers.length; i++) {
       //    HSSFCell cell = row2.createCell(i);
       //    cell.setCellStyle(style);     
       //     HSSFRichTextString text = new HSSFRichTextString(headers[i]);
       //    cell.setCellValue(text);
       // }
        /**加上排序欄位**/
        sheet.setAutoFilter(CellRangeAddress.valueOf("A7:W7"));
        /**凍結左邊兩行**/
        sheet.createFreezePane(3, 7, 3, 7);
        
        /**設定每一單元格寬度**/
       // sheet.setColumnWidth((short) 21);  
        sheet.setColumnWidth(0, (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(1, (int)((7.88 + 0.72) * 256));
        sheet.setColumnWidth(2,  (int)((21.63 + 0.72) * 256));
        sheet.setColumnWidth(3,  (int)((9 + 0.72) * 256));
        sheet.setColumnWidth(4,  (int)((9.38 + 0.72) * 256));
        sheet.setColumnWidth(5,  (int)((10.75 + 0.72) * 256));
        sheet.setColumnWidth(6, (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(7,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(8,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(9,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(10,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(11,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(12,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(13,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(14,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(15,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(16,  (int)((5.75+ 0.72) * 256));
        sheet.setColumnWidth(17,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(18,  (int)((10.25 + 0.72) * 256));
        sheet.setColumnWidth(19,  (int)((10.25 + 0.72) * 256));
        sheet.setColumnWidth(20,  (int)((10.25 + 0.72) * 256));
        sheet.setColumnWidth(21,  (int)((21.75 + 0.72) * 256));
        sheet.setColumnWidth(22,  (int)((21.88 + 0.72) * 256));
        sheet.setColumnWidth(23,  (int)((20.88 + 0.72) * 256));
        sheet.setColumnWidth(24,  (int)((15.25 + 0.72) * 256));
        sheet.setColumnWidth(25,  (int)((9.88 + 0.72) * 256));
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 6;

        
        
        HSSFFont font4 = workbook.createFont();
        font4.setFontName("Times New Roman");   
        font4.setFontHeightInPoints((short) 9);// 字体大小   
        font4.setColor(HSSFColor.BLACK.index);
        
        /**計算全廠出勤**/
        float   f  =0;
        try {

            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                row.setHeightInPoints((float) 30);
                T t = (T) it.next();

                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);

                    Class tCls = t.getClass();

                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});

                    //全部当做字符串来处理
                    String textValue = value.toString();
                    if(i==5){
                	if(!textValue.isEmpty()){
                	    f =f +Float.parseFloat(textValue);
                	}
                    }
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        richString.applyFont(font4);
                        cell.setCellValue(richString);
                    }
                }
            }
            //最後幾行
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <23; i++) {
        	   HSSFCell cell = row.createCell(i);
                   cell.setCellStyle(style6);
                   if(i==2){
                       cell.setCellValue("TỔNG");
                   }
                   if(i==5){
                       cell.setCellValue(f);
                   }
            }
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 6.75);
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <23; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==14){
                    cell.setCellValue("CHỦ QUẢGiám đốc xác nhận");
                }   
           }
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <23; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==15){
                    cell.setCellValue("主管确认");
                }   
           }
          
        } catch (NoSuchMethodException ex) {
           System.out.println("NoSuch ex="+ex);
        } catch (SecurityException ex) {
            System.out.println("Security ex="+ex);
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccess ex="+ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("(IllegalArgument ex="+ex);
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTarget ex="+ex);
        }
        
        return workbook;
    }
	
	
	/**
	 * CS報表
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param title1
	 * @param title2
	 * @param lcVo
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook CSReportExcel(String title,  List<T> dataset,repAttendanceVO raVo,String rootPath) throws Exception {
	  	
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth((short) 21);  
        
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
        BufferedImage bufferImg = ImageIO.read(new File(rootPath+"\\images\\excel\\icon.png"));  
        ImageIO.write(bufferImg, "png", byteArrayOut);  
          
        HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();  
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,255, 255,(short) 1, 2, (short)  2, 3);
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));  
        
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("Times New Roman");   
        font.setFontHeightInPoints((short) 10.5);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式  
        style.setFont(font);
        // 欄位自動換行
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font2.setColor(HSSFColor.BLACK.index); //字体颜色
        // 把字体应用到当前的样式  
        style2.setFont(font2);

        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("Times New Roman");   
        headfont.setFontHeightInPoints((short) 14);// 字体大小   
     
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        style3.setFont(headfont);
        
        /****/
        // 生成并设置另一个样式  
        HSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.WHITE.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font5 = workbook.createFont();
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font5.setFontName("Times New Roman");   
        font5.setFontHeightInPoints((short) 10.5);// 字体大小   
        font5.setColor(HSSFColor.BLACK.index); //字体颜色
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        // 把字体应用到当前的样式  
        style4.setFont(font5);
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style5 = workbook.createCellStyle();
        style5.setFillForegroundColor(HSSFColor.WHITE.index);
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       // style5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 把字体应用到当前的样式  
        style5.setFont(font5);
        
        HSSFCellStyle style6 = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style6.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style6.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style6.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style6.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style6.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style6.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style6.setFont(font5);
        
        // 产生表格标题行  
           HSSFRow row = sheet.createRow(0);
           row.setHeightInPoints((float) 6.75);
           row = sheet.createRow(5);
           row.setHeightInPoints((float) 6.75);
        
            // 产生表格标题行  
             row = sheet.createRow(1);
             HSSFCell  cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             HSSFRichTextString textT= new HSSFRichTextString("CÔNG TY TNHH VIỆT NAM MỸ THANH");
             cellT.setCellValue(textT);
             
             
             // 产生表格标题行  
             row = sheet.createRow(2);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("越南美声服饰辅料有限公司");
             cellT.setCellValue(textT);
          
              // 产生表格标题行  
             row = sheet.createRow(3);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("BẢNG CHẤM CÔNG THÁNG "+raVo.getQueryYearMonth().split("/")[0]+"/"+raVo.getQueryYearMonth().split("/")[1]);
             cellT.setCellValue(textT);
             // 产生表格标题行  
             row = sheet.createRow(4);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString(raVo.getQueryYearMonth().split("/")[0]+"年"+raVo.getQueryYearMonth().split("/")[1]+"月報表");
             cellT.setCellValue(textT);
        
        HSSFRow row6 = sheet.createRow(6);
        HSSFCell cell_6_0 = row6.createCell(0);
        cell_6_0.setCellValue("STT \n 序 \n 次");
        cell_6_0.setCellStyle(style);
      
         HSSFCell cell_6_1 = row6.createCell(1);
        cell_6_1.setCellValue("BỘ PHẬN\n部門");
        cell_6_1.setCellStyle(style);
        
        HSSFCell cell_6_2 = row6.createCell(2);
        cell_6_2.setCellValue("ĐƠN VỊ\n单位");
        cell_6_2.setCellStyle(style);
        
        HSSFCell cell_6_3 = row6.createCell(3);
        cell_6_3.setCellValue("MST \n 工号");
        cell_6_3.setCellStyle(style);
        
        HSSFCell cell_6_4 = row6.createCell(4);
        cell_6_4.setCellValue("HỌ TÊN\n姓名");
        cell_6_4.setCellStyle(style);
      
        HSSFCell cell_6_5 = row6.createCell(5);
        cell_6_5.setCellValue("ngày nộp hồ sơ\n 提交日期");
        cell_6_5.setCellStyle(style);
        
        HSSFCell cell_6_6 = row6.createCell(6);
        cell_6_6.setCellValue("Ngoài giờ Thời gian bắt đầu \n 加班開始时间");
        cell_6_6.setCellStyle(style);
        
        HSSFCell cell_6_7 = row6.createCell(7);
        cell_6_7.setCellValue("Thời gian làm thêm giờ cuối \n 加班結束时间");
        cell_6_7.setCellStyle(style);
        
        HSSFCell cell_6_8 = row6.createCell(8);
        cell_6_8.setCellValue("Tổng số giờ \n 总共小时");
        cell_6_8.setCellStyle(style);
        
        HSSFCell cell_6_9 = row6.createCell(9);
        cell_6_9.setCellValue("thêm giờ Chủ đề\n 加班事由");
        cell_6_9.setCellStyle(style);
        
        HSSFCell cell_6_10 = row6.createCell(10);
        cell_6_10.setCellValue("lý do cho sự trở lại \n 退回原因");
        cell_6_10.setCellStyle(style);
        
        HSSFCell cell_6_11 = row6.createCell(11);
        cell_6_11.setCellValue("chú ý \n 備註");
        cell_6_11.setCellStyle(style);
        
        HSSFCell cell_6_12 = row6.createCell(12);
        cell_6_12.setCellValue("action");
        cell_6_12.setCellStyle(style);
        

        /**加上排序欄位**/
        sheet.setAutoFilter(CellRangeAddress.valueOf("A7:M7"));
        /**凍結左邊兩行**/
        sheet.createFreezePane(3, 7, 3, 7);
        
        /**設定每一單元格寬度**/
       // sheet.setColumnWidth((short) 21);  
        sheet.setColumnWidth(0, (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(1, (int)((15 + 0.72) * 256));
        sheet.setColumnWidth(2,  (int)((15 + 0.72) * 256));
        sheet.setColumnWidth(3,  (int)((12 + 0.72) * 256));
        sheet.setColumnWidth(4,  (int)((12 + 0.72) * 256));
        sheet.setColumnWidth(5,  (int)((16 + 0.72) * 256));
        sheet.setColumnWidth(6, (int)((22 + 0.72) * 256));
        sheet.setColumnWidth(7,  (int)((22 + 0.72) * 256));
        sheet.setColumnWidth(8,  (int)((16 + 0.72) * 256));
        sheet.setColumnWidth(9,  (int)((20+ 0.72) * 256));
        sheet.setColumnWidth(10,  (int)((20 + 0.72) * 256));
        sheet.setColumnWidth(11,  (int)((20 + 0.72) * 256));
        sheet.setColumnWidth(12,  (int)((20 + 0.72) * 256));
        /**
        sheet.setColumnWidth(13,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(14,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(15,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(16,  (int)((5.75+ 0.72) * 256));
        sheet.setColumnWidth(17,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(18,  (int)((10.25 + 0.72) * 256));
        sheet.setColumnWidth(19,  (int)((10.25 + 0.72) * 256));
        sheet.setColumnWidth(20,  (int)((10.25 + 0.72) * 256));
        sheet.setColumnWidth(21,  (int)((21.75 + 0.72) * 256));
        sheet.setColumnWidth(22,  (int)((21.88 + 0.72) * 256));
        sheet.setColumnWidth(23,  (int)((20.88 + 0.72) * 256));
        sheet.setColumnWidth(24,  (int)((15.25 + 0.72) * 256));
        sheet.setColumnWidth(25,  (int)((9.88 + 0.72) * 256));
        **/
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 6;

        
        
        HSSFFont font4 = workbook.createFont();
        font4.setFontName("Times New Roman");   
        font4.setFontHeightInPoints((short) 9);// 字体大小   
        font4.setColor(HSSFColor.BLACK.index);
        
        /**計算全廠出勤**/
        float   f  =0;
        try {

            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                row.setHeightInPoints((float) 30);
                T t = (T) it.next();

                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);

                    Class tCls = t.getClass();

                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});

                    //全部当做字符串来处理
                    String textValue = value.toString();
                    if(i==8){
                	if(!textValue.isEmpty()){
                	    f =f +Float.parseFloat(textValue);
                	}
                    }
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        richString.applyFont(font4);
                        cell.setCellValue(richString);
                    }
                }
            }
            //最後幾行
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <13; i++) {
        	   HSSFCell cell = row.createCell(i);
                   cell.setCellStyle(style6);
                   if(i==3){
                       cell.setCellValue("TỔNG");
                   }
                   if(i==8){
                       cell.setCellValue(f);
                   }
            }
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 6.75);
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <13; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==10){
                    cell.setCellValue("CHỦ QUẢGiám đốc xác nhận");
                }   
           }
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <13; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==10){
                    cell.setCellValue("主管确认");
                }   
           }
          
        } catch (NoSuchMethodException ex) {
           System.out.println("NoSuch ex="+ex);
        } catch (SecurityException ex) {
            System.out.println("Security ex="+ex);
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccess ex="+ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("(IllegalArgument ex="+ex);
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTarget ex="+ex);
        }
        
        return workbook;
    }
	
	/**
	 * 月報表
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param title1
	 * @param title2
	 * @param lcVo
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportMonthExcel(String title, String[] headers, List<T> dataset,repAttendanceVO raVo ,String rootPath) throws Exception {
	  	
	
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth((short) 21);  
        
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
        BufferedImage bufferImg = ImageIO.read(new File(rootPath+"\\images\\excel\\icon.png"));  
        ImageIO.write(bufferImg, "png", byteArrayOut);  
          
        HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();  
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,255, 255,(short) 1, 2, (short)  2, 3);
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));  
        
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("Times New Roman");   
        font.setFontHeightInPoints((short) 10.5);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式  
        style.setFont(font);
        // 欄位自動換行
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        
        HSSFCellStyle blueStyle = workbook.createCellStyle();
        blueStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        blueStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        blueStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);// 设置背景色
        // 生成一个字体  
        blueStyle.setFont(font);
        // 欄位自動換行
        blueStyle.setWrapText(true);
        blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        blueStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setWrapText(true);
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font2.setColor(HSSFColor.BLACK.index); //字体颜色
        // 把字体应用到当前的样式  
        font2.setFontName("Times New Roman");   
        style2.setFont(font2);

        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("Times New Roman");   
        headfont.setFontHeightInPoints((short) 14);// 字体大小   
     
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        style3.setFont(headfont);
        
        /****/
        // 生成并设置另一个样式  
        HSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.WHITE.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font5 = workbook.createFont();
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font5.setFontName("Times New Roman");   
        font5.setFontHeightInPoints((short) 10.5);// 字体大小   
        font5.setColor(HSSFColor.BLACK.index); //字体颜色
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        // 把字体应用到当前的样式  
        style4.setFont(font5);
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style5 = workbook.createCellStyle();
        style5.setFillForegroundColor(HSSFColor.WHITE.index);
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       // style5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 把字体应用到当前的样式  
        style5.setFont(font5);
        
        HSSFCellStyle style6 = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style6.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style6.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style6.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style6.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style6.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style6.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style6.setFont(font);
        
        // 产生表格标题行  
           HSSFRow row = sheet.createRow(0);
           row.setHeightInPoints((float) 6.75);
           row = sheet.createRow(5);
           row.setHeightInPoints((float) 6.75);
        
            // 产生表格标题行  
             row = sheet.createRow(1);
             HSSFCell  cellT = row.createCell(15);
             cellT.setCellStyle(style3);
             HSSFRichTextString textT= new HSSFRichTextString("CÔNG TY TNHH VIỆT NAM MỸ THANH");
             cellT.setCellValue(textT);
             
             
             // 产生表格标题行  
             row = sheet.createRow(2);
             cellT = row.createCell(15);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("越南美声服饰辅料有限公司");
             cellT.setCellValue(textT);
          
              // 产生表格标题行  
             row = sheet.createRow(3);
             cellT = row.createCell(15);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("BẢNG CHẤM CÔNG THÁNG "+raVo.getQueryYearMonth().split("/")[0]+"/"+raVo.getQueryYearMonth().split("/")[1]);
             cellT.setCellValue(textT);
             // 产生表格标题行  
             row = sheet.createRow(4);
             cellT = row.createCell(15);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString(raVo.getQueryYearMonth().split("/")[0]+"年"+raVo.getQueryYearMonth().split("/")[1]+"月報表");
             cellT.setCellValue(textT);
        
        HSSFRow row6 = sheet.createRow(6);
        row6.setHeightInPoints((float) 36.75);
        HSSFCell cell_6_0 = row6.createCell(0);
        cell_6_0.setCellValue("STT \n 序 \n 次");
        cell_6_0.setCellStyle(style);
      
         HSSFCell cell_6_1 = row6.createCell(1);
        cell_6_1.setCellValue("MST \n 工号");
        cell_6_1.setCellStyle(style);
        
        HSSFCell cell_6_2 = row6.createCell(2);
        cell_6_2.setCellValue("HỌ TÊN\n姓名");
        cell_6_2.setCellStyle(style);
        
        HSSFCell cell_6_3 = row6.createCell(3);
        cell_6_3.setCellValue("BỘ PHẬN\n部門");
        cell_6_3.setCellStyle(style);
        
        HSSFCell cell_6_4 = row6.createCell(4);
        cell_6_4.setCellValue("ĐƠN VỊ\n单位");
        cell_6_4.setCellStyle(style);
      
        HSSFCell cell_6_5 = row6.createCell(5);
        cell_6_5.setCellValue("1");
        cell_6_5.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"01"));
        
        HSSFCell cell_6_6 = row6.createCell(6);
        cell_6_6.setCellValue("2");
        cell_6_6.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"02"));
        
        HSSFCell cell_6_7 = row6.createCell(7);
        cell_6_7.setCellValue("3");
        cell_6_7.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"03"));
        
        HSSFCell cell_6_8 = row6.createCell(8);
        cell_6_8.setCellValue("4");
        cell_6_8.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"04"));
        
        HSSFCell cell_6_9 = row6.createCell(9);
        cell_6_9.setCellValue("5");
        cell_6_9.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"05"));
        
        HSSFCell cell_6_10 = row6.createCell(10);
        cell_6_10.setCellValue("6");
        cell_6_10.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"06"));
        
        HSSFCell cell_6_11 = row6.createCell(11);
        cell_6_11.setCellValue("7");
        cell_6_11.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"07"));
        
        HSSFCell cell_6_12 = row6.createCell(12);
        cell_6_12.setCellValue("8");
        cell_6_12.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"08"));
        
        HSSFCell cell_6_13 = row6.createCell(13);
        cell_6_13.setCellValue("9");
        cell_6_13.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"09"));
        
        HSSFCell cell_6_14 = row6.createCell(14);
        cell_6_14.setCellValue("10");
        cell_6_14.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"10"));
        
        HSSFCell cell_6_15 = row6.createCell(15);
        cell_6_15.setCellValue("11");
        cell_6_15.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"11"));
        
        HSSFCell cell_6_16 = row6.createCell(16);
        cell_6_16.setCellValue("12");
        cell_6_16.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"12"));
        
        HSSFCell cell_6_17 = row6.createCell(17);
        cell_6_17.setCellValue("13");
        cell_6_17.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"13"));
        
        HSSFCell cell_6_18 = row6.createCell(18);
        cell_6_18.setCellValue("14");
        cell_6_18.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"14"));
        
        HSSFCell cell_6_19 = row6.createCell(19);
        cell_6_19.setCellValue("15");
        cell_6_19.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"15"));
        
        HSSFCell cell_6_20 = row6.createCell(20);
        cell_6_20.setCellValue("16");
        cell_6_20.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"16"));
        
        HSSFCell cell_6_21 = row6.createCell(21);
        cell_6_21.setCellValue("17");
        cell_6_21.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"17"));
        
        HSSFCell cell_6_22 = row6.createCell(22);
        cell_6_22.setCellValue("18");
        cell_6_22.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"18"));
        
        HSSFCell cell_6_23 = row6.createCell(23);
        cell_6_23.setCellValue("19");
        cell_6_23.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"19"));
        
        HSSFCell cell_6_24 = row6.createCell(24);
        cell_6_24.setCellValue("20");
        cell_6_24.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"20"));
     
        HSSFCell cell_6_25 = row6.createCell(25);
        cell_6_25.setCellValue("21");
        cell_6_25.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"21"));
        
        HSSFCell cell_6_26 = row6.createCell(26);
        cell_6_26.setCellValue("22");
        cell_6_26.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"22"));
        
        HSSFCell cell_6_27 = row6.createCell(27);
        cell_6_27.setCellValue("23");
        cell_6_27.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"23"));
        
        HSSFCell cell_6_28 = row6.createCell(28);
        cell_6_28.setCellValue("24");
        cell_6_28.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"24"));
        
        HSSFCell cell_6_29 = row6.createCell(29);
        cell_6_29.setCellValue("25");
        cell_6_29.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"25"));
        
        HSSFCell cell_6_30 = row6.createCell(30);
        cell_6_30.setCellValue("26");
        cell_6_30.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"26"));
        
        HSSFCell cell_6_31 = row6.createCell(31);
        cell_6_31.setCellValue("27");
        cell_6_31.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"27"));
        
        HSSFCell cell_6_32 = row6.createCell(32);
        cell_6_32.setCellValue("28");
        cell_6_32.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"28"));
        
        HSSFCell cell_6_33 = row6.createCell(33);
        cell_6_33.setCellValue("29");
        cell_6_33.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"29"));
        
        HSSFCell cell_6_34 = row6.createCell(34);
        cell_6_34.setCellValue("30");
        cell_6_34.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"30"));
        
        HSSFCell cell_6_35 = row6.createCell(35);
        cell_6_35.setCellValue("31");
        cell_6_35.setCellStyle(DateUtil.checkReportDate(style,blueStyle,raVo.getQueryYearMonth(),"31"));
        
        HSSFCell cell_6_36 = row6.createCell(36);
        cell_6_36.setCellValue(" 正班出勤 \n Giờ công ");
        cell_6_36.setCellStyle(style);
        
        HSSFCell cell_6_37 = row6.createCell(37);
        cell_6_37.setCellValue("年假\n Phép năm");
        cell_6_37.setCellStyle(style);
        
        HSSFCell cell_6_38 = row6.createCell(38);
        cell_6_38.setCellValue("工伤假\nPhép công");
        cell_6_38.setCellStyle(style);
        
        HSSFCell cell_6_39 = row6.createCell(39);
        cell_6_39.setCellValue("產假\nThai sản");
        cell_6_39.setCellStyle(style);
        
        HSSFCell cell_6_40 = row6.createCell(40);
        cell_6_40.setCellValue("婚假\nKêt hôn");
        cell_6_40.setCellStyle(style);
        
        HSSFCell cell_6_41 = row6.createCell(41);
        cell_6_41.setCellValue("喪假\nPhép tang");
        cell_6_41.setCellStyle(style);
        
        HSSFCell cell_6_42 = row6.createCell(42);
        cell_6_42.setCellValue("病假\nPhép bệnh");
        cell_6_42.setCellStyle(style);
        
        HSSFCell cell_6_43 = row6.createCell(43);
        cell_6_43.setCellValue("事假\nViệc riêng");
        cell_6_43.setCellStyle(style);
        
        HSSFCell cell_6_44 = row6.createCell(44);
        cell_6_44.setCellValue("调休\nngày nghỉ");
        cell_6_44.setCellStyle(style);
        
        HSSFCell cell_6_45 = row6.createCell(45);
        cell_6_45.setCellValue("其他\n khác");
        cell_6_45.setCellStyle(style);
        
        HSSFCell cell_6_46 = row6.createCell(46);
        cell_6_46.setCellValue("旷工\nLãng công");
        cell_6_46.setCellStyle(style);
        
        HSSFCell cell_6_47 = row6.createCell(47);
        cell_6_47.setCellValue("迟到\n Đi trễ   ");
        cell_6_47.setCellStyle(style);
        
        HSSFCell cell_6_48 = row6.createCell(48);
        cell_6_48.setCellValue("早退\n về sớm");
        cell_6_48.setCellStyle(style);
        
        HSSFCell cell_6_49 = row6.createCell(49);
        cell_6_49.setCellValue("待工\nNghichờ \nviệc");
        cell_6_49.setCellStyle(style);
        
        HSSFCell cell_6_50 = row6.createCell(50);
        cell_6_50.setCellValue("簽名\n Ký tên");
        cell_6_50.setCellStyle(style);
        
        HSSFCell cell_6_51 = row6.createCell(51);
        cell_6_51.setCellValue("GHI CHÚ\n备注");
        cell_6_51.setCellStyle(style);
   //     for (short i = 0; i < headers.length; i++) {
       //    HSSFCell cell = row2.createCell(i);
       //    cell.setCellStyle(style);     
       //     HSSFRichTextString text = new HSSFRichTextString(headers[i]);
       //    cell.setCellValue(text);
       // }
        /**加上排序欄位**/
        sheet.setAutoFilter(CellRangeAddress.valueOf("A7:AZ7"));
        /**凍結左邊兩行 上方7行**/
        sheet.createFreezePane(3, 7, 3, 7);
        
        /**設定每一單元格寬度**/
       // sheet.setColumnWidth((short) 21);  
        sheet.setColumnWidth(0, (int)((3.5 + 0.72) * 256));
        sheet.setColumnWidth(1, (int)((7.75 + 0.72) * 256));
        sheet.setColumnWidth(2,  (int)((21.63 + 0.72) * 256));
        sheet.setColumnWidth(3,  (int)((7.75  + 0.72) * 256));
        sheet.setColumnWidth(4,  (int)((7.75  + 0.72) * 256));
        sheet.setColumnWidth(5,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(6, (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(7,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(8,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(9,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(10,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(11,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(12,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(13,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(14,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(15,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(16,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(17,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(18,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(19,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(20,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(21,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(22,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(23,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(24,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(25,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(26,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(27,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(28,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(29,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(30,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(31,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(32,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(33,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(34,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(35,  (int)((3.63 + 0.72) * 256));
        sheet.setColumnWidth(36,  (int)((10.75 + 0.72) * 256));
        sheet.setColumnWidth(37,  (int)((5.25 + 0.72) * 256));
        sheet.setColumnWidth(38,  (int)((5.75 + 0.72) * 256));
        sheet.setColumnWidth(39,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(40,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(41,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(42,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(43,  (int)((6 + 0.72) * 256));
        sheet.setColumnWidth(44,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(45,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(46,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(47,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(48,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(49,  (int)((10.38 + 0.72) * 256));
        sheet.setColumnWidth(50,  (int)((21.23 + 0.72) * 256));
        
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 6;
        
        HSSFFont font4 = workbook.createFont();
        font4.setFontName("Times New Roman");   
        font4.setFontHeightInPoints((short) 9);// 字体大小   
        font4.setColor(HSSFColor.BLACK.index);
        
        /**計算全廠出勤**/
        float   f  =0;
        try {

            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                row.setHeightInPoints((float) 30);
                T t = (T) it.next();

                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();
              //  logger.info("fields"+fields);
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);                           
                    
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                   // logger.info("fieldName  : "+fieldName);
                    Class tCls = t.getClass();
              
                    cell.setCellStyle(DateUtil.checkReportColDate(style2,blueStyle,raVo.getQueryYearMonth(),fieldName));
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});

                    //全部当做字符串来处理
                    String textValue = value.toString();
                    if(i==36){
                	if(!textValue.isEmpty()){
                	    f =f +Float.parseFloat(textValue);
                	}
                    }
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        richString.applyFont(font4);
                        cell.setCellValue(richString);
                    }
                }
            }
            //最後幾行
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <52; i++) {
        	   HSSFCell cell = row.createCell(i);
                   cell.setCellStyle(style6);
                   if(i==2){
                       cell.setCellValue("TỔNG");
                   }
                   if(i==36){
                       cell.setCellValue(f);
                   }
            }
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 6.75);
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <52; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==44){
                    cell.setCellValue("CHỦ QUẢGiám đốc xác nhận");
                }   
           }
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <52; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==45){
                    cell.setCellValue("主管确认");
                }   
           }
          
        } catch (NoSuchMethodException ex) {
           System.out.println("NoSuch ex="+ex);
        } catch (SecurityException ex) {
            System.out.println("Security ex="+ex);
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccess ex="+ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("(IllegalArgument ex="+ex);
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTarget ex="+ex);
        }
        
        return workbook;
    }
	
	
	/**
	 * 日報表
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param title1
	 * @param title2
	 * @param lcVo
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcel(String title, String[] headers, List<T> dataset,String title1,String title2,String rootPath) throws Exception {
	  	
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth((short) 20);  
        
        //打开图片  
        InputStream is;
        is = new FileInputStream(rootPath+"\\images\\excel\\icon.png");
        byte[] bytes = IOUtils.toByteArray(is);  
        // 增加图片到 Workbook  
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);  
        is.close();  
     // Create the drawing patriarch.  This is the top level container for all shapes.  
        Drawing drawing = sheet.createDrawingPatriarch();  
        CreationHelper helper = workbook.getCreationHelper(); 
        //add a picture shape  
        ClientAnchor anchor = helper.createClientAnchor();  
     // 设置图片位置  
        anchor.setCol1(1);  
        anchor.setRow1(2);  
        Picture pict = drawing.createPicture(anchor, pictureIdx);  
      //auto-size picture relative to its top-left corner  
        pict.resize();  
        
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式  
        style.setFont(font);
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式  
        style2.setFont(font2);

        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("黑体");   
        headfont.setFontHeightInPoints((short) 22);// 字体大小   
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        style3.setFont(headfont);
        // 产生表格标题行  
           HSSFRow row = sheet.createRow(0);
            HSSFCell cellT = row.createCell(10);
           cellT.setCellStyle(style3);
            HSSFRichTextString textT= new HSSFRichTextString(title1);
            cellT.setCellValue(textT);
        
            // 产生表格标题行  
             row = sheet.createRow(1);
             cellT = row.createCell(10);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString(title2);
             cellT.setCellValue(textT);
        
        HSSFRow row2 = sheet.createRow(2);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row2.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        HSSFRow row3 = sheet.createRow(3);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row3.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        HSSFRow row4 = sheet.createRow(4);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row4.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 2;

        try {

            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                row.setHeightInPoints((float) 30);
                T t = (T) it.next();

                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);

                    Class tCls = t.getClass();

                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});

                    //全部当做字符串来处理
                    String textValue = value.toString();
           
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        HSSFFont font3 = workbook.createFont();
                        font3.setColor(HSSFColor.BLUE.index);
                        richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }
                }
            }
            
            /**凍結左邊兩行**/
          sheet.createFreezePane(0, 2, 0, 2);
          
        } catch (NoSuchMethodException ex) {
           System.out.println("NoSuch ex="+ex);
        } catch (SecurityException ex) {
            System.out.println("Security ex="+ex);
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccess ex="+ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("(IllegalArgument ex="+ex);
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTarget ex="+ex);
        }
        
        return workbook;
    }
	/**
	 * 全廠日報
	 * @param title
	 * @param headers
	 * @param empnumheaders
	 * @param dataset
	 * @param eRolist
	 * @param title1
	 * @param title2
	 * @param blueRow
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportTwoExcel(String title, String[] headers,String[] empnumheaders, List<T> dataset,
		List<empnumRO> eRolist ,String title1,String title2
		, Hashtable  blueRow,leaveCardVO lcVo,String rootPath) throws Exception {
		
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth((short) 21);  
        
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
        BufferedImage bufferImg = ImageIO.read(new File(rootPath+"\\images\\excel\\icon.png"));  
        ImageIO.write(bufferImg, "png", byteArrayOut);  
          
        HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();  
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,255, 255,(short) 1, 2, (short)  2, 3);
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));  
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("Times New Roman");   
        font.setFontHeightInPoints((short) 10.5);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式  
        style.setFont(font);
        // 欄位自動換行
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
     //   HSSFFont font2 = workbook.createFont();
     //   font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式  
     //   style2.setFont(font2);
        HSSFFont font5 = workbook.createFont();
        font5.setFontName("Times New Roman");   
        font5.setFontHeightInPoints((short) 11);// 字体大小   
        font5.setColor(HSSFColor.BLACK.index);
        style2.setFont(font5);

        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("Times New Roman");   
        headfont.setFontHeightInPoints((short) 14);// 字体大小   
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        style3.setFont(headfont);
        
        //生成統計行style
        HSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font4 = workbook.createFont();
        font4.setColor(HSSFColor.BLUE.index);
        style4.setFont(font5);
        
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints((float) 6.75);
        row = sheet.createRow(5);
        row.setHeightInPoints((float) 6.75);
     
         // 产生表格标题行  
          row = sheet.createRow(1);
          HSSFCell  cellT = row.createCell(5);
          cellT.setCellStyle(style3);
          HSSFRichTextString textT= new HSSFRichTextString("CÔNG TY TNHH VIỆT NAM MỸ THANH");
          cellT.setCellValue(textT);
          
          
          // 产生表格标题行  
          row = sheet.createRow(2);
          cellT = row.createCell(5);
          cellT.setCellStyle(style3);
          textT= new HSSFRichTextString("越南美声服饰辅料有限公司");
          cellT.setCellValue(textT);
       
           // 产生表格标题行  
          row = sheet.createRow(3);
          cellT = row.createCell(5);
          cellT.setCellStyle(style3);
          textT= new HSSFRichTextString("BẢNG CHẤM CÔNG THÁNG "+lcVo.getApplicationDate().split("/")[0]+"/"+lcVo.getApplicationDate().split("/")[1]+"/"+lcVo.getApplicationDate().split("/")[2]);
          cellT.setCellValue(textT);
          // 产生表格标题行  
          row = sheet.createRow(4);
          cellT = row.createCell(5);
          cellT.setCellStyle(style3);
          textT= new HSSFRichTextString(lcVo.getApplicationDate().split("/")[0]+"年"+lcVo.getApplicationDate().split("/")[1]+"月"+lcVo.getApplicationDate().split("/")[2]+"日報表");
          cellT.setCellValue(textT);
          
          /**加上排序欄位**/
          sheet.setAutoFilter(CellRangeAddress.valueOf("A7:V7"));
          /**凍結左邊兩行 上方7行**/
          sheet.createFreezePane(2, 7, 2, 7);
          /**設定每一單元格寬度**/
          // sheet.setColumnWidth((short) 21);  
           sheet.setColumnWidth(0, (int)((12 + 0.72) * 256));
           sheet.setColumnWidth(1, (int)((12 + 0.72) * 256));
           sheet.setColumnWidth(2,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(3,  (int)((10  + 0.72) * 256));
           sheet.setColumnWidth(4,  (int)((10  + 0.72) * 256));
           sheet.setColumnWidth(5,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(6, (int)((10+ 0.72) * 256));
           sheet.setColumnWidth(7,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(8,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(9,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(10,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(11,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(12,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(13,  (int)((10+ 0.72) * 256));
           sheet.setColumnWidth(14,  (int)((10+ 0.72) * 256));
           sheet.setColumnWidth(15,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(16,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(17,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(18,  (int)((10+ 0.72) * 256));
           sheet.setColumnWidth(19,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(20,  (int)((10 + 0.72) * 256));
           sheet.setColumnWidth(21,  (int)((10 + 0.72) * 256));
        
        HSSFRow row6 = sheet.createRow(6);
        row6.setHeightInPoints((float) 50);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row6.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 6;
        
    
        try {
            int count=1;
          //  logger.info("blueRow "+blueRow);
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                row.setHeightInPoints((float) 30);
                T t = (T) it.next();
              
              //  logger.info("count "+count);
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    
                      cell.setCellStyle(style2);
              
                   if(blueRow.containsKey(String.valueOf(count))){
         
                       cell.setCellStyle(style4);
                   }
             
                   
                
                    Field field = fields[i];
                    String fieldName = field.getName();
                    
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);

                    Class tCls = t.getClass();

                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});

                    //全部当做字符串来处理
                    String textValue = value.toString();
                    
                    if (textValue != null) {
                	 HSSFRichTextString richString = new HSSFRichTextString(textValue);
                         richString.applyFont(font5);
                         cell.setCellValue(richString);
                    }
                }
                count++;
            }
            index= index+2;
            HSSFRow row3 = sheet.createRow(index);
            row3.setHeightInPoints((float) 50);
            for (short i = 0; i < empnumheaders.length; i++) {
                HSSFCell cell = row3.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(empnumheaders[i]);
                cell.setCellValue(text);
            }
            
            
            // 遍历集合数据，产生数据行  
            Iterator<empnumRO> it2 = eRolist.iterator();
            
                while ( it2.hasNext()) {
                    index++;
                    row = sheet.createRow(index);
                    row.setHeightInPoints((float) 30);
                    empnumRO t= (empnumRO)  it2.next();

                    // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                    Field[] fields = t.getClass().getDeclaredFields();
                    for (short i = 0; i < fields.length; i++) {
                        HSSFCell cell = row.createCell(i);
                        cell.setCellStyle(style2);
                        Field field = fields[i];
                        String fieldName = field.getName();
                        String getMethodName = "get"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);

                        Class tCls = t.getClass();

                        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                        Object value = getMethod.invoke(t, new Object[]{});

                        //全部当做字符串来处理
                        String textValue = value.toString();
               
                        if (textValue != null) {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            richString.applyFont(font5);
                            cell.setCellValue(richString);
                        }
                    }
                }
            
        } catch (NoSuchMethodException ex) {
           System.out.println("NoSuch ex="+ex);
        } catch (SecurityException ex) {
            System.out.println("Security ex="+ex);
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccess ex="+ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("(IllegalArgument ex="+ex);
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTarget ex="+ex);
        }
        
        return workbook;
    }
	
	
	
	/**
	 * 月報總表
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param title1
	 * @param title2
	 * @param lcVo
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportMonthTotalExcel(String title, String[] headers, List<T> dataset,repAttendanceVO raVo ,String rootPath) throws Exception {
	  	
	
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth((short) 21);  
        
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
        BufferedImage bufferImg = ImageIO.read(new File(rootPath+"\\images\\excel\\icon.png"));  
        ImageIO.write(bufferImg, "png", byteArrayOut);  
          
        HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();  
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,255, 255,(short) 1, 2, (short)  2, 3);
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));  
        
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("Times New Roman");   
        font.setFontHeightInPoints((short) 10.5);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式  
        style.setFont(font);
        // 欄位自動換行
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        
        HSSFCellStyle blueStyle = workbook.createCellStyle();
        blueStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        blueStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        blueStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);// 设置背景色
        // 生成一个字体  
        blueStyle.setFont(font);
        // 欄位自動換行
        blueStyle.setWrapText(true);
        blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        blueStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setWrapText(true);
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font2.setColor(HSSFColor.BLACK.index); //字体颜色
        // 把字体应用到当前的样式  
        font2.setFontName("Times New Roman");   
        style2.setFont(font2);

        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("Times New Roman");   
        headfont.setFontHeightInPoints((short) 14);// 字体大小   
     
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        style3.setFont(headfont);
        
        /****/
        // 生成并设置另一个样式  
        HSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.WHITE.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font5 = workbook.createFont();
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font5.setFontName("Times New Roman");   
        font5.setFontHeightInPoints((short) 10.5);// 字体大小   
        font5.setColor(HSSFColor.BLACK.index); //字体颜色
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        // 把字体应用到当前的样式  
        style4.setFont(font5);
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style5 = workbook.createCellStyle();
        style5.setFillForegroundColor(HSSFColor.WHITE.index);
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       // style5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 把字体应用到当前的样式  
        style5.setFont(font5);
        
        HSSFCellStyle style6 = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style6.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style6.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style6.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style6.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style6.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style6.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style6.setFont(font);
        
        // 产生表格标题行  
           HSSFRow row = sheet.createRow(0);
           row.setHeightInPoints((float) 6.75);
           row = sheet.createRow(5);
           row.setHeightInPoints((float) 6.75);
        
            // 产生表格标题行  
             row = sheet.createRow(1);
             HSSFCell  cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             HSSFRichTextString textT= new HSSFRichTextString("CÔNG TY TNHH VIỆT NAM MỸ THANH");
             cellT.setCellValue(textT);
             
             
             // 产生表格标题行  
             row = sheet.createRow(2);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("越南美声服饰辅料有限公司");
             cellT.setCellValue(textT);
          
              // 产生表格标题行  
             row = sheet.createRow(3);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("BẢNG CHẤM CÔNG THÁNG "+raVo.getQueryYearMonth().split("/")[0]+"/"+raVo.getQueryYearMonth().split("/")[1]);
             cellT.setCellValue(textT);
             // 产生表格标题行  
             row = sheet.createRow(4);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString(raVo.getQueryYearMonth().split("/")[0]+"年"+raVo.getQueryYearMonth().split("/")[1]+"月份考勤总表");
             cellT.setCellValue(textT);
        
        HSSFRow row6 = sheet.createRow(6);
        row6.setHeightInPoints((float) 55.5);
        HSSFCell cell_6_0 = row6.createCell(0);
        cell_6_0.setCellValue("STT \n 序 \n 次");
        cell_6_0.setCellStyle(style);
      
         HSSFCell cell_6_1 = row6.createCell(1);
        cell_6_1.setCellValue("MST \n 工号");
        cell_6_1.setCellStyle(style);
        
        HSSFCell cell_6_2 = row6.createCell(2);
        cell_6_2.setCellValue("HỌ TÊN\n姓名");
        cell_6_2.setCellStyle(style);
        
        HSSFCell cell_6_3 = row6.createCell(3);
        cell_6_3.setCellValue("HỌ TÊN\n姓名");
        cell_6_3.setCellStyle(style);
        
        HSSFCell cell_6_4 = row6.createCell(4);
        cell_6_4.setCellValue("BỘ PHẬN\n部門");
        cell_6_4.setCellStyle(style);
        
        HSSFCell cell_6_5 = row6.createCell(5);
        cell_6_5.setCellValue("ĐƠN VỊ\n单位");
        cell_6_5.setCellStyle(style);
      
    
        HSSFCell cell_6_6= row6.createCell(6);
        cell_6_6.setCellValue(" 正班出勤 \n Giờ công ");
        cell_6_6.setCellStyle(style);
        
        HSSFCell cell_6_7 = row6.createCell(7);
        cell_6_7.setCellValue("加班工時\n Số giờ tăng ca");
        cell_6_7.setCellStyle(style);
        
        HSSFCell cell_6_8 = row6.createCell(8);
        cell_6_8.setCellValue("年假\n Phép năm");
        cell_6_8.setCellStyle(style);
        
        HSSFCell cell_6_9 = row6.createCell(9);
        cell_6_9.setCellValue("公假\n Phép công");
        cell_6_9.setCellStyle(style);
        
        HSSFCell cell_6_10 = row6.createCell(10);
        cell_6_10.setCellValue("產假\n  Thai sản");
        cell_6_10.setCellStyle(style);
        
        HSSFCell cell_6_11 = row6.createCell(11);
        cell_6_11.setCellValue("婚假\n hôn nhân");
        cell_6_11.setCellStyle(style);
        
        HSSFCell cell_6_12 = row6.createCell(12);
        cell_6_12.setCellValue("喪假\n Phép tang");
        cell_6_12.setCellStyle(style);
        
        HSSFCell cell_6_13 = row6.createCell(13);
        cell_6_13.setCellValue("病假\n Phép bệnh");
        cell_6_13.setCellStyle(style);
        
        HSSFCell cell_6_14 = row6.createCell(14);
        cell_6_14.setCellValue("事假\n Việc riêng");
        cell_6_14.setCellStyle(style);
        
        HSSFCell cell_6_15 = row6.createCell(15);
        cell_6_15.setCellValue("旷工\n Lãng công");
        cell_6_15.setCellStyle(style);
        
        HSSFCell cell_6_16 = row6.createCell(16);
        cell_6_16.setCellValue("迟到\n bị trễ");
        cell_6_16.setCellStyle(style);
        
        HSSFCell cell_6_17 = row6.createCell(17);
        cell_6_17.setCellValue("早退\n về sớm");
        cell_6_17.setCellStyle(style);
        
        HSSFCell cell_6_18 = row6.createCell(18);
        cell_6_18.setCellValue("待工\n Ngh chờ việc");
        cell_6_18.setCellStyle(style);
        
     
   //     for (short i = 0; i < headers.length; i++) {
       //    HSSFCell cell = row2.createCell(i);
       //    cell.setCellStyle(style);     
       //     HSSFRichTextString text = new HSSFRichTextString(headers[i]);
       //    cell.setCellValue(text);
       // }
        /**加上排序欄位**/
        sheet.setAutoFilter(CellRangeAddress.valueOf("A7:S7"));
        /**凍結左邊兩行 上方7行**/
        sheet.createFreezePane(3, 7, 3, 7);
        
        /**設定每一單元格寬度**/
       // sheet.setColumnWidth((short) 21);  
        sheet.setColumnWidth(0, (int)((4.13 + 0.72) * 256));
        sheet.setColumnWidth(1, (int)((9 + 0.72) * 256));
        sheet.setColumnWidth(2,  (int)((24.75 + 0.72) * 256));
        sheet.setColumnWidth(3,  (int)((24.75  + 0.72) * 256));
        sheet.setColumnWidth(4,  (int)((10.38  + 0.72) * 256));
        sheet.setColumnWidth(5,  (int)((8.75 + 0.72) * 256));
        sheet.setColumnWidth(6, (int)((12.38 + 0.72) * 256));
        sheet.setColumnWidth(7,  (int)((10+ 0.72) * 256));
        sheet.setColumnWidth(8,  (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(9,  (int)((10+ 0.72) * 256));
        sheet.setColumnWidth(10,  (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(11,  (int)((10+ 0.72) * 256));
        sheet.setColumnWidth(12,  (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(13,  (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(14,  (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(15,  (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(16,  (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(17,  (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(18,  (int)((10 + 0.72) * 256));
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 6;
        
        HSSFFont font4 = workbook.createFont();
        font4.setFontName("Times New Roman");   
        font4.setFontHeightInPoints((short) 10);// 字体大小   
        font4.setColor(HSSFColor.BLACK.index);
        
        /**計算全廠出勤**/
        float   f  =0;
        try {

            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                row.setHeightInPoints((float) 30);
                T t = (T) it.next();

                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();
              //  logger.info("fields"+fields);
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);                           
                    
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                   // logger.info("fieldName  : "+fieldName);
                    Class tCls = t.getClass();
              
                    cell.setCellStyle(style2);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});

                    //全部当做字符串来处理
                    String textValue = value.toString();
                    
                    if(i==6){
                	if(!textValue.isEmpty()){
                	    f =f +Float.parseFloat(textValue);
                	}
                    }
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        richString.applyFont(font4);
                        cell.setCellValue(richString);
                    }
                }
            }
            //最後幾行
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <19; i++) {
        	   HSSFCell cell = row.createCell(i);
                   cell.setCellStyle(style6);
                   if(i==2){
                       cell.setCellValue("TỔNG");
                   }
                   if(i==6){
                       cell.setCellValue(f);
                   }
            }
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 6.75);
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <19; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==8){
                    cell.setCellValue("CHỦ QUẢGiám đốc xác nhận");
                }   
           }
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <19; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==8){
                    cell.setCellValue("主管确认");
                }   
           }
          
        } catch (NoSuchMethodException ex) {
           System.out.println("NoSuch ex="+ex);
        } catch (SecurityException ex) {
            System.out.println("Security ex="+ex);
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccess ex="+ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("(IllegalArgument ex="+ex);
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTarget ex="+ex);
        }
        
        return workbook;
    }
	
	
	
	/**
	 * 月報明細表
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param title1
	 * @param title2
	 * @param lcVo
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportMonthDetailExcel(String title, String[] headers, List<T> dataset,repAttendanceVO raVo ,String rootPath) throws Exception {
	  	
	
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节  
        sheet.setDefaultColumnWidth((short) 21);  
        
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
        BufferedImage bufferImg = ImageIO.read(new File(rootPath+"\\images\\excel\\icon.png"));  
        ImageIO.write(bufferImg, "png", byteArrayOut);  
          
        HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();  
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,255, 255,(short) 1, 2, (short)  2, 3);
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));  
        
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("Times New Roman");   
        font.setFontHeightInPoints((short) 10.5);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式  
        style.setFont(font);
        // 欄位自動換行
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
     // 生成一个样式  
        HSSFCellStyle headstyle = workbook.createCellStyle();
        headstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headstyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色

        // 把字体应用到当前的样式  
        headstyle.setFont(font);
        // 欄位自動換行
        headstyle.setWrapText(true);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        
        
        HSSFCellStyle blueStyle = workbook.createCellStyle();
        blueStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        blueStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        blueStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        blueStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);// 设置背景色
        // 生成一个字体  
        blueStyle.setFont(font);
        // 欄位自動換行
        blueStyle.setWrapText(true);
        blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        blueStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setWrapText(true);
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font2.setColor(HSSFColor.BLACK.index); //字体颜色
        // 把字体应用到当前的样式  
        font2.setFontName("Times New Roman");   
        style2.setFont(font2);

     
        
        
        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("Times New Roman");   
        headfont.setFontHeightInPoints((short) 14);// 字体大小   
     
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        style3.setFont(headfont);
        
        /****/
        // 生成并设置另一个样式  
        HSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.WHITE.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体  
        HSSFFont font5 = workbook.createFont();
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font5.setFontName("Times New Roman");   
        font5.setFontHeightInPoints((short) 10.5);// 字体大小   
        font5.setColor(HSSFColor.BLACK.index); //字体颜色
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
        // 把字体应用到当前的样式  
        style4.setFont(font5);
        
        
        // 生成并设置另一个样式  
        HSSFCellStyle style5 = workbook.createCellStyle();
        style5.setFillForegroundColor(HSSFColor.WHITE.index);
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       // style5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    //    style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
     //   style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        style5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 把字体应用到当前的样式  
        style5.setFont(font5);
        
        HSSFCellStyle style6 = workbook.createCellStyle();
        // 设置这些样式  
      //  style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style6.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style6.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style6.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style6.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style6.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style6.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style6.setFont(font);
        
        // 产生表格标题行  
           HSSFRow row = sheet.createRow(0);
           row.setHeightInPoints((float) 6.75);
           row = sheet.createRow(5);
           row.setHeightInPoints((float) 6.75);
        
            // 产生表格标题行  
             row = sheet.createRow(1);
             HSSFCell  cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             HSSFRichTextString textT= new HSSFRichTextString("CÔNG TY TNHH VIỆT NAM MỸ THANH");
             cellT.setCellValue(textT);
             
             
             // 产生表格标题行  
             row = sheet.createRow(2);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("越南美声服饰辅料有限公司");
             cellT.setCellValue(textT);
          
              // 产生表格标题行  
             row = sheet.createRow(3);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString("BẢNG CHẤM CÔNG THÁNG "+raVo.getQueryYearMonth().split("/")[0]+"/"+raVo.getQueryYearMonth().split("/")[1]);
             cellT.setCellValue(textT);
             // 产生表格标题行  
             row = sheet.createRow(4);
             cellT = row.createCell(4);
             cellT.setCellStyle(style3);
             textT= new HSSFRichTextString(raVo.getQueryYearMonth().split("/")[0]+"年"+raVo.getQueryYearMonth().split("/")[1]+"月份考勤明细表");
             cellT.setCellValue(textT);
        
        HSSFRow row6 = sheet.createRow(6);
        row6.setHeightInPoints((float) 55.5);
        HSSFCell cell_6_0 = row6.createCell(0);
        cell_6_0.setCellValue("日期");
        cell_6_0.setCellStyle(headstyle);
      
         HSSFCell cell_6_1 = row6.createCell(1);
        cell_6_1.setCellValue("部門");
        cell_6_1.setCellStyle(headstyle);
        
        HSSFCell cell_6_2 = row6.createCell(2);
        cell_6_2.setCellValue("單位");
        cell_6_2.setCellStyle(headstyle);
        
        HSSFCell cell_6_3 = row6.createCell(3);
        cell_6_3.setCellValue("工號");
        cell_6_3.setCellStyle(headstyle);
        
        HSSFCell cell_6_4 = row6.createCell(4);
        cell_6_4.setCellValue("姓名");
        cell_6_4.setCellStyle(headstyle);
        
        HSSFCell cell_6_5 = row6.createCell(5);
        cell_6_5.setCellValue("姓名");
        cell_6_5.setCellStyle(headstyle);
      
    
        HSSFCell cell_6_6= row6.createCell(6);
        cell_6_6.setCellValue(" 上班時間 ");
        cell_6_6.setCellStyle(headstyle);
        
        HSSFCell cell_6_7 = row6.createCell(7);
        cell_6_7.setCellValue("下班時間");
        cell_6_7.setCellStyle(headstyle);
        
        HSSFCell cell_6_8 = row6.createCell(8);
        cell_6_8.setCellValue("工時");
        cell_6_8.setCellStyle(headstyle);
        
        HSSFCell cell_6_9 = row6.createCell(9);
        cell_6_9.setCellValue("加班\n 150%");
        cell_6_9.setCellStyle(headstyle);
        
        HSSFCell cell_6_10 = row6.createCell(10);
        cell_6_10.setCellValue("晚班\n  130%");
        cell_6_10.setCellStyle(headstyle);
        
        HSSFCell cell_6_11 = row6.createCell(11);
        cell_6_11.setCellValue("週日加班\n 200%");
        cell_6_11.setCellStyle(headstyle);
        
        HSSFCell cell_6_12 = row6.createCell(12);
        cell_6_12.setCellValue("節日加班\n 300%");
        cell_6_12.setCellStyle(headstyle);
        
        HSSFCell cell_6_13 = row6.createCell(13);
        cell_6_13.setCellValue("年假\n Phép năm");
        cell_6_13.setCellStyle(style);
        
        HSSFCell cell_6_14 = row6.createCell(14);
        cell_6_14.setCellValue("公假\n Phép công");
        cell_6_14.setCellStyle(style);
        
        HSSFCell cell_6_15 = row6.createCell(15);
        cell_6_15.setCellValue("產假 \n Thai sản");
        cell_6_15.setCellStyle(style);
        
        HSSFCell cell_6_16 = row6.createCell(16);
        cell_6_16.setCellValue("婚假\n Kết hôn");
        cell_6_16.setCellStyle(style);
        
        HSSFCell cell_6_17 = row6.createCell(17);
        cell_6_17.setCellValue("喪假\n Phép tang");
        cell_6_17.setCellStyle(style);
        
        HSSFCell cell_6_18 = row6.createCell(18);
        cell_6_18.setCellValue("病假\n Phép bệnh");
        cell_6_18.setCellStyle(style);
        
        HSSFCell cell_6_19= row6.createCell(19);
        cell_6_19.setCellValue("事假\n Việc riêng");
        cell_6_19.setCellStyle(style);
        
        HSSFCell cell_6_20= row6.createCell(20);
        cell_6_20.setCellValue("旷工\n Lãng công");
        cell_6_20.setCellStyle(style);
        
        HSSFCell cell_6_21= row6.createCell(21);
        cell_6_21.setCellValue("迟到时间\n Đi trễ");
        cell_6_21.setCellStyle(style);
        
        HSSFCell cell_6_22= row6.createCell(22);
        cell_6_22.setCellValue("早退时间\n về sớm");
        cell_6_22.setCellStyle(style);
        
        HSSFCell cell_6_23= row6.createCell(23);
        cell_6_23.setCellValue("待工\n Nghỉ chờ \n việc");
        cell_6_23.setCellStyle(style);
        
    
   //     for (short i = 0; i < headers.length; i++) {
       //    HSSFCell cell = row2.createCell(i);
       //    cell.setCellStyle(style);     
       //     HSSFRichTextString text = new HSSFRichTextString(headers[i]);
       //    cell.setCellValue(text);
       // }
        /**加上排序欄位**/
        sheet.setAutoFilter(CellRangeAddress.valueOf("A7:X7"));
        /**凍結左邊兩行 上方7行**/
        sheet.createFreezePane(3, 7, 3, 7);
        
        /**設定每一單元格寬度**/
       // sheet.setColumnWidth((short) 21);  
        sheet.setColumnWidth(0, (int)((13.63 + 0.72) * 256));
        sheet.setColumnWidth(1, (int)((13.63 + 0.72) * 256));
        sheet.setColumnWidth(2,  (int)((13.63 + 0.72) * 256));
        sheet.setColumnWidth(3,  (int)((13.63  + 0.72) * 256));
        sheet.setColumnWidth(4,  (int)((11  + 0.72) * 256));
        sheet.setColumnWidth(5,  (int)((13.63 + 0.72) * 256));
        sheet.setColumnWidth(6, (int)((25+ 0.72) * 256));
        sheet.setColumnWidth(7,  (int)((13.63+ 0.72) * 256));
        sheet.setColumnWidth(8,  (int)((8.5 + 0.72) * 256));
        sheet.setColumnWidth(9,  (int)((8.5 + 0.72) * 256));
        sheet.setColumnWidth(10,  (int)((8.5+ 0.72) * 256));
        sheet.setColumnWidth(11,  (int)((8.5+ 0.72) * 256));
        sheet.setColumnWidth(12,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(13,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(14,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(15,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(16,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(17,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(18,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(19,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(20,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(21,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(22,  (int)((8.38 + 0.72) * 256));
        sheet.setColumnWidth(23,  (int)((8.38 + 0.72) * 256));
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 6;
        
        HSSFFont font4 = workbook.createFont();
        font4.setFontName("Times New Roman");   
        font4.setFontHeightInPoints((short) 10);// 字体大小   
        font4.setColor(HSSFColor.BLACK.index);
        
        /**計算全廠出勤**/
        float   f  =0;
        try {

            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                row.setHeightInPoints((float) 30);
                T t = (T) it.next();

                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();
              //  logger.info("fields"+fields);
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);                           
                    
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                   // logger.info("fieldName  : "+fieldName);
                    Class tCls = t.getClass();
              
                    cell.setCellStyle(style2);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});

                    //全部当做字符串来处理
                    String textValue = value.toString();
                    
                    if(i==8){
                	if(!textValue.isEmpty()){
                	    f =f +Float.parseFloat(textValue);
                	}
                    }
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        richString.applyFont(font4);
                        cell.setCellValue(richString);
                    }
                }
            }
            //最後幾行
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <24; i++) {
        	   HSSFCell cell = row.createCell(i);
                   cell.setCellStyle(style6);
                   if(i==2){
                       cell.setCellValue("TỔNG");
                   }
                   if(i==8){
                       cell.setCellValue(f);
                   }
            }
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 6.75);
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <24; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==8){
                    cell.setCellValue("CHỦ QUẢGiám đốc xác nhận");
                }   
           }
            
            index++;
            row = sheet.createRow(index);
            row.setHeightInPoints((float) 30);
            for (short i = 0; i <24; i++) {
     	     HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style5);
                if(i==8){
                    cell.setCellValue("主管确认");
                }   
           }
          
        } catch (NoSuchMethodException ex) {
           System.out.println("NoSuch ex="+ex);
        } catch (SecurityException ex) {
            System.out.println("Security ex="+ex);
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccess ex="+ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("(IllegalArgument ex="+ex);
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTarget ex="+ex);
        }
        
        return workbook;
    }
	
}
