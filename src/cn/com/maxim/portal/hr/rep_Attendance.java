package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.ro.repDailyRO;
import cn.com.maxim.portal.attendan.ro.repMonthDetailRO;
import cn.com.maxim.portal.attendan.ro.repMonthExcelDetailRO;
import cn.com.maxim.portal.attendan.ro.repMonthTotalRO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.repAttendanceVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.dao.MonthReportDAO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DBUtilTList;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.TranslateConsts;
import cn.com.maxim.potral.consts.UrlUtil;

/**
 * 月份考勤綜合表
 * @author Antonis.chen
 *
 */
public class rep_Attendance extends TemplatePortalPen
{
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(rep_Attendance.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
	
	
	
		String actText = request.getParameter("act");
		repAttendanceVO raVo = new repAttendanceVO(); 
		raVo.setActionURI(ActionURI);
		
		try
		{
			if (actText != null)
			{		
				
				BeanUtils.populate(raVo,request.getParameterMap()); 
				// 查询
				if (actText.equals("QUE")) {
					raVo.setShowDataTable(true);
					showHtml(con, out, raVo,UserInformation,request);
				}
				
			}else{
				//預設
				raVo.setSearchDepartmen("0");
				raVo.setSearchUnit("0");
				raVo.setDrawReportSelect("0");
				raVo.setQueryYearMonth(DateUtil.getSysYearMonth());
				showHtml(con, out, raVo,UserInformation,request);
			
			}
			
		}
		catch (Exception e)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(e));
			
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
	
		if(ajax.equals("SwUnit")){
			 String searchDepartmen = request.getParameter("searchDepartmen");
			 String html="";

			try
			{
				html = ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + searchDepartmen + "'", "0",false,null);
			}
			catch (SQLException e)
			{
				 logger.error(e.getMessage());
				
			}
	
			 out.println(html);
	 	}
		 
	 }
	
	private void showHtml(Connection con, PrintWriter out, repAttendanceVO raVo , UserDescriptor UserInformation ,HttpServletRequest request ) throws Exception {
		HtmlUtil hu=new HtmlUtil();
		String htmlPart1=hu.gethtml(htmlConsts.html_rep_attendance);
		htmlPart1=htmlPart1.replace("<drawReportSelect/>", 	ControlUtil.drawReportSelect(raVo.getDrawReportSelect()));
		htmlPart1=htmlPart1.replace("<ActionURI/>", 	raVo.getActionURI());
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(raVo.getMsg()));
		htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" +raVo.getSearchDepartmen() + "'", raVo.getSearchUnit(),false,null));
		htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getYearMonthDiv("queryYearMonth",raVo.getQueryYearMonth()));
		htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	ControlUtil.drawChosenSelect(con,  "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", null ,raVo.getSearchDepartmen( ),false,null));
		if(raVo.isShowDataTable()){
		       if(raVo.getDrawReportSelect().equals("0")){
        			htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawRepAttendanceTable(
        					SqlUtil.getMonthReport(con,raVo),HtmlUtil.drawTableMExcelButton(),  con, out,raVo));
        
        			 repAttendanceRO raRo=new repAttendanceRO();
        			 logger.info("getvnMonthAttendanceExcel : "+SqlUtil.getvnMonthAttendanceExcel(raVo));
        			 List<repAttendanceRO> raRolist=( List<repAttendanceRO>)DBUtil.queryMonthAttendanceExcel(con,SqlUtil.getvnMonthAttendanceExcel(raVo),raRo);
        			 request.getSession().setAttribute("Departmen", DBUtil.queryDBField(con,SqlUtil.getDeptName(raVo.getSearchDepartmen()),"DEPARTMENT"));
        			 request.getSession().setAttribute("raRolist", raRolist);
		       }
		       /**細節表**/
		       if(raVo.getDrawReportSelect().equals("1")){
			   
			   htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawRepAttendanceTable(
				   MonthReportDAO.getMonthDetailReport(con,raVo),HtmlUtil.drawTableMExcelButton(),  con, out,raVo));
			   /**報表用**/
			   repMonthExcelDetailRO raRo=new repMonthExcelDetailRO();
			   List<repMonthExcelDetailRO> raRolist=( List<repMonthExcelDetailRO>)DBUtil.queryMonthDetailExcel(con,SqlUtil.getMonthDetailExcelReport(raVo),raRo);
			   request.getSession().setAttribute("Departmen", DBUtil.queryDBField(con,SqlUtil.getDeptName(raVo.getSearchDepartmen()),"DEPARTMENT"));
	  	           request.getSession().setAttribute("raRolist", raRolist);
		       }
		       /**總表**/
		       if(raVo.getDrawReportSelect().equals("2")){
			   htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawRepAttendanceTable(
				   MonthReportDAO.getMonthTotalReport(con,raVo),HtmlUtil.drawTableMExcelButton(),  con, out,raVo));
			   /**報表用**/
			 repMonthTotalRO raRo=new repMonthTotalRO();
  			
  		
  			 List<repMonthTotalRO> raRolist=( List<repMonthTotalRO>)DBUtil.queryMonthTotalExcel(con,SqlUtil.getMonthTotalExcelReport(raVo),raRo);
  			 request.getSession().setAttribute("Departmen", DBUtil.queryDBField(con,SqlUtil.getDeptName(raVo.getSearchDepartmen()),"DEPARTMENT"));
  			 request.getSession().setAttribute("raRolist", raRolist);
		       }
		}
		 out.println(htmlPart1);
	    }
}
