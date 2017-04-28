package cn.com.maxim.portal.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.com.maxim.portal.attendan.ro.dayAttendanceRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.ro.repDailyRO;
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.repAttendanceVO;
import cn.com.maxim.portal.util.ExcelUtil;
import cn.com.maxim.potral.consts.keyConts;

public class ExcelOutService  extends HttpServlet{

	private static final long serialVersionUID = -2665915740474640230L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		    String _name = request.getParameter("_name");
		    response.setHeader("Connection", "close");
	        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
		if (_name.equals("lateOutEarly")) {
			 	lateOutEarlyVO eaVo = new lateOutEarlyVO(); 
				BeanUtils.populate(eaVo,request.getParameterMap());
		        String filename = System.currentTimeMillis() + "yearMonthLate.xls";
		        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		    	String IsLate="",title1="",title2="";
		        OutputStream out = null;
		        
		        try {
		        	 String[] headers =null;  
			            if( eaVo.getQueryIsLate().equals("1")){
			            	  headers = keyConts.lateExcelheaders.split(",");  
			            	  IsLate=eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月份員工遲到名單";
			            	  title1="Tháng "+eaVo.getQueryYearMonth().split("/")[1]+" năm "+eaVo.getQueryYearMonth().split("/")[0]+" nhân viên danh sách cuối";
			            	  title2=eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月份員工遲到名單";
						}
						if( eaVo.getQueryIsLate().equals("2")){
							 headers = keyConts.earlyExcelheaders.split(",");  
							  IsLate=eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月份員工早退名單";
							  title1="Tháng "+eaVo.getQueryYearMonth().split("/")[1]+" năm "+eaVo.getQueryYearMonth().split("/")[0]+" danh sách các nhân viên về sớm";
							  title2=eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月份員工早退名單";
						}
		            
		            List<yearMonthLateRO> eaRolist=(List<yearMonthLateRO>)request.getSession().getAttribute("eaRolist");
		            ExcelUtil<yearMonthLateRO> eu = new ExcelUtil<yearMonthLateRO>();
		            HSSFWorkbook workbook = eu.exportExcel(IsLate,headers,eaRolist,title1,title2);
		            
		            out = response.getOutputStream();  
		            workbook.write(out);  
		        } finally {
		            if(out!=null){
		                out.close();
		            }
		        }
		}
		
		if (_name.equals("daily")) {
			leaveCardVO lcVo = new leaveCardVO(); 
			BeanUtils.populate(lcVo,request.getParameterMap());
	        String filename = System.currentTimeMillis() + "daily.xls";
	        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
	        String title1="";
	        OutputStream out = null;
	        try {
	        	 String[] headers =null;  
		           
			     headers = keyConts.dailyExcelheaders.split(",");  
			     title1=lcVo.getApplicationDate().split("/")[0]+"年"+lcVo.getApplicationDate().split("/")[1]+"月"+lcVo.getApplicationDate().split("/")[2]+"日考勤表";
					
	            
			    List<repDailyRO> eaRolist=( List<repDailyRO>)request.getSession().getAttribute("daRolist");
	            ExcelUtil<repDailyRO> eu = new ExcelUtil<repDailyRO>();
	            HSSFWorkbook workbook = eu.exportExcel(title1,headers,eaRolist,title1,"");
	            
	            out = response.getOutputStream();  
	            workbook.write(out);  
	        } finally {
	            if(out!=null){
	                out.close();
	            }
	        }
		}
		
		if (_name.equals("repAttendance")) {
			repAttendanceVO raVo = new repAttendanceVO(); 
			BeanUtils.populate(raVo,request.getParameterMap());
			String filename = System.currentTimeMillis() + "repAttendance.xls";
			  response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		        String title1="";
		        OutputStream out = null;
		        try {
		        	 String[] headers =null;  
				     headers = keyConts.repAttendanceExcelheaders.split(",");  	     
				     title1=raVo.getQueryYearMonth().split("/")[0]+"年"+raVo.getQueryYearMonth().split("/")[1]+"月分每日考勤表";
				     List<repAttendanceRO> eaRolist=( List<repAttendanceRO>)request.getSession().getAttribute("raRolist");
				     System.out.println("eaRolist : "+eaRolist);
			         ExcelUtil<repAttendanceRO> eu = new ExcelUtil<repAttendanceRO>();
			         HSSFWorkbook workbook = eu.exportExcel(title1,headers,eaRolist,title1,"");
			            
			         out = response.getOutputStream();  
			         workbook.write(out);  
		        } finally {
		            if(out!=null){
		                out.close();
		            }
		        }
			
		}
		
		
		/**
		 * 全廠日報表
		 */
		if (_name.equals("attendanceDay")) {
			leaveCardVO lcVo=new leaveCardVO();
			BeanUtils.populate(lcVo,request.getParameterMap());
			String filename = System.currentTimeMillis() + "repAttendanceDay.xls";
			  response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		        String title1="";
		        OutputStream out = null;
		        try {
		        	 String[] headers =null;  
				     headers = keyConts.repAttendanceDayExcelheaders.split(",");    
				     title1="全廠"+lcVo.getApplicationDate().split("/")[0]+"年"+lcVo.getApplicationDate().split("/")[1]+"月"+lcVo.getApplicationDate().split("/")[2]+"日報表";
				     List<dayAttendanceRO> daRolist =(List<dayAttendanceRO>)request.getSession().getAttribute("daRolist");
				     System.out.println("daRolist : "+daRolist);
			         ExcelUtil<dayAttendanceRO> eu = new ExcelUtil<dayAttendanceRO>();
			         HSSFWorkbook workbook = eu.exportExcel(title1,headers,daRolist,title1,"");
			            
			         out = response.getOutputStream();  
			         workbook.write(out);  
		        } finally {
		            if(out!=null){
		                out.close();
		            }
		        }
		}
	
		
    }
    
    public String encodeFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT");

        if (null != agent && -1 != agent.indexOf("MSIE")) {
            return URLEncoder.encode(fileName, "UTF-8");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
            return "=?UTF-8?B?"+ (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
        } else {
            return fileName;
        }
    }

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
		{
			processRequest(request, response);
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
		{
			processRequest(request, response);
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
