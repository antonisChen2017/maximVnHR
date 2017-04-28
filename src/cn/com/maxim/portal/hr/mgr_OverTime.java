package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.htmlDBcontrol.WebDBSelect;
import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;
import cn.com.maxim.portal.util.UrlUtil;
/**
 * 超時加班時數
 * @author Antonis.chen
 *
 */
public class mgr_OverTime extends TemplatePortalPen
{
		
	
		@Override
		public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
				PrintWriter out, String ActionURI, Connection con) 
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(mgr_OverTime.class);
			String actText = request.getParameter("act");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setActionURI(ActionURI);
			try
			{
					if (actText != null)
					{		
						BeanUtils.populate(otVo,request.getParameterMap()); 
						// 查詢UI
						otVo.setSearchReasons("0");
						otVo.setMonthOverTime("1");//查詢有無超過時間
						if (actText.equals("QUE")) {
							otVo.setShowDataTable(true);
							setHtmlPart1(con, out, otVo,UserInformation);
					
						}
						if (actText.equals("I")) {
				    		logger.info("超時加班時數/I : " +otVo.toString());						
							DBUtil.updateTimeOverSStatus("I", request.getParameter("rowID"), con);
							otVo.setShowDataTable(true);
							setHtmlPart1(con, out, otVo,UserInformation);
							//System.out.println("OvertimeNoSave  :   "+SqlUtil.getOvertimeNoSave(otVo));
							// 輸出查詢UI
						//	out.write(HtmlUtil.drawTableS(
						//			SqlUtil.getOvertimeNoSave(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageInspect));
							
							//setHtmlPart2(con, out);
						}
						if (actText.equals("R")) {
							
					    	logger.info("超時加班時數/R: " +otVo.toString());		
							DBUtil.updateTimeOverSStatus("R", request.getParameter("rowID"), con);
							DBUtil.updateSql(SqlUtil.setStatusReturnMsg(otVo.getReturnMsg(), request.getParameter("rowID")), con);
							
							otVo.setShowDataTable(true);
							setHtmlPart1(con, out, otVo,UserInformation);
						
						}
					}else{
					//	System.out.println("sql : "+SqlUtil.getDeptID(UserInformation.getUserEmployeeNo()));
						otVo.setSearchDepartmen(DBUtil.queryDBField(con, SqlUtil.getDeptID(UserInformation.getUserEmployeeNo()),"ID"));
						otVo.setSearchEmployeeNo("0");
						otVo.setSearchEmployee("0");
						otVo.setStartSubmitDate(DateUtil.NowDate());
						otVo.setEndSubmitDate(DateUtil.NowDate());
					  otVo.setStartQueryDate(DateUtil.NowDate());
						otVo.setEndQueryDate(DateUtil.NowDate());
						otVo.setQueryDate(DateUtil.NowDate());
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
					out.println(ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployeeNo()));
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
					out.println(ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployee()));
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
				htmlPart1=htmlPart1.replace("&SearchDepartmen", ControlUtil.drawSelectDBControl(con, out, "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", " 1=1 ",otVo.getSearchDepartmen( )));
				htmlPart1=htmlPart1.replace("&submitDate",HtmlUtil.getDateDivSw("startSubmitDate","endSubmitDate", otVo.getStartSubmitDate(),otVo.getEndSubmitDate()));
				htmlPart1=htmlPart1.replace("	<SearchEmployeeNo/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployeeNo()));
				htmlPart1=htmlPart1.replace("&SearchEmployee",ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployee()));
				htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
				if(otVo.isShowDataTable()){
			//		System.out.println("sql  :  "+SqlUtil.getStopWork(otVo));
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawTableS(
							SqlUtil.getOvertimeNoSave(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageInspect));
				}
				out.println(htmlPart1);
		}
}

