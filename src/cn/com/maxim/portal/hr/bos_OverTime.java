package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;

public class bos_OverTime  extends TemplatePortalPen
{
	
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(mgr_OverTime.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con) 
	{
	
		String actText = request.getParameter("act");
		overTimeVO otVo = new overTimeVO(); 
		otVo.setActionURI(ActionURI);
		try
		{
				if (actText != null)
				{		
					BeanUtils.populate(otVo,request.getParameterMap()); 
					// 查询UI
					otVo.setSearchReasons("0");
					otVo.setMonthOverTime("1");//查询有無超過时间
					if (actText.equals("QUE")) {
						otVo.setShowDataTable(true);
						otVo.setStatus("I");
						otVo.setMonthOverTime("0");
						setHtmlPart1(con, out, otVo,UserInformation);
				
					}
					if (actText.equals("NUE")) {
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						otVo.setMonthOverTime("0");
						setHtmlPart1(con, out, otVo,UserInformation);
					}
					if (actText.equals("U")) {
			    		logger.info("加班時數/I : " +otVo.toString());						
						DBUtil.updateTimeOverSStatus("B", request.getParameter("rowID"), con);
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						setHtmlPart1(con, out, otVo,UserInformation);
					}
					if (actText.equals("R")) {
						
				    	logger.info("加班時數/R: " +otVo.toString());		
						DBUtil.updateTimeOverSStatus("LR", request.getParameter("rowID"), con);
						DBUtil.updateSql(SqlUtil.setStatusReturnMsg(otVo.getReturnMsg(), request.getParameter("rowID")), con);
						
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						setHtmlPart1(con, out, otVo,UserInformation);
					
					}
					if (actText.equals("UB")) {
			    		logger.info("超時加班時數/UB : " +otVo.toString());						
						DBUtil.updateTimeOverSStatus("UB", request.getParameter("rowID"), con);
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						setHtmlPart1(con, out, otVo,UserInformation);
					}
				}else{
				//	System.out.println("sql : "+SqlUtil.getDeptID(UserInformation.getUserEmployeeNo()));
					otVo.setSearchDepartmen("0");
					otVo.setSearchEmployeeNo("0");
					otVo.setSearchEmployee("0");
					otVo.setStartSubmitDate(DateUtil.NowDate());
					otVo.setEndSubmitDate(DateUtil.NowDate());
				    otVo.setStartQueryDate(DateUtil.NowDate());
					otVo.setEndQueryDate(DateUtil.NowDate());
					otVo.setQueryDate(DateUtil.NowDate());
					otVo.setStatus("U");
					otVo.setMonthOverTime("0");//查询有無超過时间
					otVo.setShowDataTable(true);
					setHtmlPart1(con, out,otVo,UserInformation);
				
				}
		}catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
			
		}
	 
		
	}
	
	
 public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con){
		 
		 if(ajax.equals("SwEmpNo")){
			 String searchDepartmen = request.getParameter("searchDepartmen");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setSearchEmployeeNo("0");
			try
			{
				out.println(ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployeeNo(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
	 	}
		 if(ajax.equals("SwEmp")){
			 String searchDepartmen = request.getParameter("searchDepartmen");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setSearchEmployee("0");
			try
			{
				out.println(ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployee(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
	 	}
	 }
	
	
	
	private void setHtmlPart1(Connection con, PrintWriter out, overTimeVO otVo,UserDescriptor UserInformation) throws Exception {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_mgr_OverTime);
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
			htmlPart1=htmlPart1.replace("&SearchDepartmen", ControlUtil.drawChosenSelect(con, "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", " 1=1 ",otVo.getSearchDepartmen( ),false,null));
			htmlPart1=htmlPart1.replace("&submitDate",HtmlUtil.getDateDivSw("startSubmitDate","endSubmitDate", otVo.getStartSubmitDate(),otVo.getEndSubmitDate()));
			htmlPart1=htmlPart1.replace("	<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployeeNo(),false,null));
			htmlPart1=htmlPart1.replace("&SearchEmployee",ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployee(),false,null));
			
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			if(otVo.isShowDataTable()){
			logger.info("getBOvertime  :  "+SqlUtil.getBOvertime(otVo));
			htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
						SqlUtil.getBOvertime(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageLList));
			}
			out.println(htmlPart1);
	}
}

