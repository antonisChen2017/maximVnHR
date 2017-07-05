package cn.com.maxim.portal.util;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import cn.com.maxim.portal.attendan.ro.empnumRO;
import cn.com.maxim.portal.hr.rev_LeaveCard;

public class ExcelUtil<T>
{
    	Log4jUtil lu=new Log4jUtil();
	org.apache.log4j.Logger logger  =lu.initLog4j(ExcelUtil.class);
	public HSSFWorkbook exportExcel(String title, String[] headers, List<T> dataset,String title1,String title2) {
	  	
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 15);  
        
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

        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 2;

        try {

            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
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
	
	
	public HSSFWorkbook exportTwoExcel(String title, String[] headers,String[] empnumheaders, List<T> dataset,
		List<empnumRO> eRolist ,String title1,String title2
		, Hashtable  blueRow) {
		
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 15);  
        
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
     //   HSSFFont font2 = workbook.createFont();
     //   font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式  
     //   style2.setFont(font2);
        HSSFFont font22 = workbook.createFont();
        font22.setColor(HSSFColor.BLUE.index);
        style2.setFont(font22);

        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("黑体");   
        headfont.setFontHeightInPoints((short) 22);// 字体大小   
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
        style4.setFont(font4);
        
        
        
        
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

        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();
        int index = 2;

        try {
            int count=1;
          //  logger.info("blueRow "+blueRow);
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
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
                        HSSFFont font3 = workbook.createFont();
                        //font3.setColor(HSSFColor.BLUE.index);
                       // richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }
                }
                count++;
            }
            index= index+2;
            HSSFRow row3 = sheet.createRow(index);
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
                           // HSSFFont font3 = workbook.createFont();
                           // font3.setColor(HSSFColor.BLUE.index);
                           // richString.applyFont(font3);
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
	
	
}
