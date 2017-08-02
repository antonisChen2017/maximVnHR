package cn.com.maxim.portal.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.com.maxim.portal.attendan.ro.CSRepoRO;
import cn.com.maxim.portal.attendan.ro.dayAttendanceRO;
import cn.com.maxim.portal.attendan.ro.empnumRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.ro.repDailyRO;
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.repAttendanceVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.ExcelUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.keyConts;

public class ExcelOutService  extends HttpServlet{

	private static final long serialVersionUID = -2665915740474640230L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IllegalAccessException, InvocationTargetException, Exception {
		String _name = request.getParameter("_name");
		response.setHeader("Connection", "close");
	        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ExcelOutService.class);
		String rootPath= config.getServletContext().getRealPath("/");
		
		if (_name.equals("lateOutEarly")) {
			lateOutEarlyVO eaVo = new lateOutEarlyVO(); 
			BeanUtils.populate(eaVo,request.getParameterMap());
		     
		    	String IsLate="",title1="",title2="";
		        OutputStream out = null;
		        
		        try {
		        	 String[] headers =null;  
		        	   List<yearMonthLateRO> eaRolist=(List<yearMonthLateRO>)request.getSession().getAttribute("eaRolist");
		        	   
		        	   yearMonthLateRO yr= eaRolist.get(0);
			            if( eaVo.getQueryIsLate().equals("1")){
			            	  headers = keyConts.lateExcelheaders.split(",");  
			            	  IsLate=eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月份員工遲到名單";
			            	//  title1="Tháng "+eaVo.getQueryYearMonth().split("/")[1]+" năm "+eaVo.getQueryYearMonth().split("/")[0]+" nhân viên danh sách cuối";
			            	  title2=yr.getDEPARTMENT().trim()+eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月份員工遲到名單";
					}
					if( eaVo.getQueryIsLate().equals("2")){
						 headers = keyConts.earlyExcelheaders.split(",");  
						 IsLate=eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月份員工早退名單";
							 // title1="Tháng "+eaVo.getQueryYearMonth().split("/")[1]+" năm "+eaVo.getQueryYearMonth().split("/")[0]+" danh sách các nhân viên về sớm";
						 title2=yr.getDEPARTMENT().trim()+eaVo.getQueryYearMonth().split("/")[0]+"年"+eaVo.getQueryYearMonth().split("/")[1]+"月份員工早退名單";
					}
		            
		           // List<yearMonthLateRO> eaRolist=(List<yearMonthLateRO>)request.getSession().getAttribute("eaRolist");
		            ExcelUtil<yearMonthLateRO> eu = new ExcelUtil<yearMonthLateRO>();
		            HSSFWorkbook workbook = eu.exportLateEarlyExcel(title2,headers,eaRolist, eaVo,rootPath);
		            
		           // String filename = System.currentTimeMillis() + "yearMonthLate.xls";
		       
		        //    filename=filename.replace("+", "");
		            String filename =title2+".xls";
				 response.setHeader("Content-Disposition", "attachment;filename=" +encodeFileName(request,filename)); 
		            out = response.getOutputStream();  
		            workbook.write(out);  
		        } finally {
		            if(out!=null){
		                out.close();
		            }
		        }
		}
		/**
		 * 每日出勤狀況報表
		 */
		if (_name.equals("daily")) {
			leaveCardVO lcVo = new leaveCardVO(); 
			BeanUtils.populate(lcVo,request.getParameterMap());
	     
	        String title1="";
	        OutputStream out = null;
	        try {
	        	 String[] headers =null;  
	        	 String[] twoHeaders =null;  
	        	 String SearchDepartmen=( String)request.getSession().getAttribute("SearchDepartmen");
	        	 List<repDailyRO> eaRolist=( List<repDailyRO>)request.getSession().getAttribute("daRolist");
	        	 repDailyRO ro=eaRolist.get(0);
	        	 
			     headers = keyConts.dailyExcelheaders.split(",");  
			//     twoHeaders = keyConts.dailyTwoExcelheaders.split(",");  
			     if(SearchDepartmen.equals("0")){
			    	 title1="全厂"+lcVo.getApplicationDate().split("/")[0]+"年"+lcVo.getApplicationDate().split("/")[1]+"月"+lcVo.getApplicationDate().split("/")[2]+"日考勤表";
			     }else{
				 title1=ro.getDEPARTMENT().trim()+lcVo.getApplicationDate().split("/")[0]+"年"+lcVo.getApplicationDate().split("/")[1]+"月"+lcVo.getApplicationDate().split("/")[2]+"日考勤表";
			     }
	            
			   
	            ExcelUtil<repDailyRO> eu = new ExcelUtil<repDailyRO>();
	            HSSFWorkbook workbook = eu.exportDayExcel(title1,headers,twoHeaders,eaRolist,title1,"",lcVo,rootPath);
	            
	           // String filename = System.currentTimeMillis() + "daily.xls";
	            String filename =title1+".xls";
			 response.setHeader("Content-Disposition", "attachment;filename=" +encodeFileName(request,filename)); 
	            out = response.getOutputStream();  
	            workbook.write(out);  
	        } finally {
	            if(out!=null){
	                out.close();
	            }
	        }
		}
		/**
		 * 月份考勤表
		 */
		if (_name.equals("repAttendance")) {
			repAttendanceVO raVo = new repAttendanceVO(); 
			BeanUtils.populate(raVo,request.getParameterMap());
		
		        String title1="";
		        OutputStream out = null;
		        try {
		        	 String[] headers =null;  
		        	  List<repAttendanceRO> eaRolist=( List<repAttendanceRO>)request.getSession().getAttribute("raRolist");
		        	  String  Departmen= (String)request.getSession().getAttribute("Departmen");
				      headers = keyConts.repAttendanceExcelheaders.split(",");  	     
				      title1=Departmen.trim()+raVo.getQueryYearMonth().split("/")[0]+"年"+raVo.getQueryYearMonth().split("/")[1]+"月分考勤表";
				   
				    
			         ExcelUtil<repAttendanceRO> eu = new ExcelUtil<repAttendanceRO>();
			         HSSFWorkbook workbook = eu.exportMonthExcel(title1,headers,eaRolist,raVo,rootPath);
			         
			         String filename =title1+".xls";
				 response.setHeader("Content-Disposition", "attachment;filename=" +encodeFileName(request,filename)); 
					 
					  
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
		
		        String title1="";
		        OutputStream out = null;
		        try {
		        	 String[] headers =null,empnumheaders=null;  
				     headers = keyConts.repAttendanceDayExcelheaders.split(",");    
				     SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
				 	Date date = sdf.parse( lcVo.getApplicationDate().replaceAll("/", "") );
					date=DateUtil.addDays(date, -1);
				     headers[3]=headers[3].replace("&",sdf.format(date));
				     headers[4]=headers[4].replace("&",lcVo.getApplicationDate().replaceAll("/", ""));
				     headers[5]=headers[5].replace("&",lcVo.getApplicationDate().replaceAll("/", ""));
				     title1="全廠"+lcVo.getApplicationDate().split("/")[0]+"年"+lcVo.getApplicationDate().split("/")[1]+"月"+lcVo.getApplicationDate().split("/")[2]+"日報表";
				     List<dayAttendanceRO> daRolist =(List<dayAttendanceRO>)request.getSession().getAttribute("daRolist");
				    // System.out.println("daRolist : "+daRolist);
				     List<empnumRO> eRolist =(List<empnumRO>)request.getSession().getAttribute("eRolist");
				     Hashtable  blueRow=(Hashtable)request.getSession().getAttribute("blueRow");
				 
				     empnumheaders = keyConts.empnumheaders.split(",");    
			         ExcelUtil<dayAttendanceRO> eu = new ExcelUtil<dayAttendanceRO>();
			         HSSFWorkbook workbook = eu.exportTwoExcel(title1,headers,empnumheaders,daRolist,eRolist,title1,"",blueRow,lcVo,rootPath);
			         String filename =title1+".xls";
				 response.setHeader("Content-Disposition", "attachment;filename=" +encodeFileName(request,filename)); 
			         out = response.getOutputStream();  
			         workbook.write(out);  
		        } finally {
		            if(out!=null){
		                out.close();
		            }
		        }
		}
		
		
		/**
		 * CS報表
		 */
		if (_name.equals("CSReport")) {
		    	repAttendanceVO raVo = new repAttendanceVO(); 
			BeanUtils.populate(raVo,request.getParameterMap());
		
		        String title1="";
		        OutputStream out = null;
		        try {
		        	 String[] headers =null;  
		        	  List<CSRepoRO> eaRolist=( List<CSRepoRO>)request.getSession().getAttribute("raRolist");
		        	  String  Departmen= (String)request.getSession().getAttribute("Departmen");
		        	  
				      title1=Departmen.trim()+raVo.getQueryYearMonth().split("/")[0]+"年"+raVo.getQueryYearMonth().split("/")[1]+"月分考勤表";
				
				    
			         ExcelUtil<CSRepoRO> eu = new ExcelUtil<CSRepoRO>();
			         HSSFWorkbook workbook = eu.CSReportExcel(title1,eaRolist,raVo,rootPath);
			         
			         String filename =title1+".xls";
				 response.setHeader("Content-Disposition", "attachment;filename=" +encodeFileName(request,filename)); 
					 
					  
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
            return  java.net.URLEncoder.encode(fileName, "UTF-8");
        }
    }

    private ServletConfig config;
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
		{
			processRequest(request, response);
		}
		catch (Exception e)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(ExcelOutService.class);
			logger.error(vnStringUtil.getExceptionAllinformation(e));
		}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
		{
			processRequest(request, response);
		}
    	catch (Exception e)
		{
			// TODO Auto-generated catch block
    		Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(ExcelOutService.class);
			logger.error(vnStringUtil.getExceptionAllinformation(e));
		}
    }
}
