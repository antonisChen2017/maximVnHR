package cn.com.maxim.portal.attendan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.com.maxim.htmlDBcontrol.WebDBSelect;
import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
import cn.com.maxim.portal.util.Utilities;
/**
 * 加班申請單 審核
 * @author Antonis.chen
 *
 */
public class inspectOvertime extends TemplatePortalPen {
	static WebDBSelect APSelector;
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con) 
	{
		String actText = request.getParameter("act");
		overTimeVO otVo = new overTimeVO(); 
		try
		{
				if (actText != null)
				{		
					BeanUtils.populate(otVo,request.getParameterMap()); 
					// 查詢UI
				
					if (actText.equals("QUE")) {
						
						setHtmlPart1(con, out, otVo,UserInformation);
						System.out.println("OvertimeNoSave  :   "+SqlUtil.getOvertimeNoSave(otVo));
						// 輸出查詢UI
						out.write(HtmlUtil.drawTableS(
								SqlUtil.getOvertimeNoSave(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageInspect));
						
						setHtmlPart2(con, out);
					}
					if (actText.equals("I")) {
												
						DBUtil.updateTimeOverSStatus("I", request.getParameter("rowID"), con);
						
						setHtmlPart1(con, out, otVo,UserInformation);
						//System.out.println("OvertimeNoSave  :   "+SqlUtil.getOvertimeNoSave(otVo));
						// 輸出查詢UI
						out.write(HtmlUtil.drawTableS(
								SqlUtil.getOvertimeNoSave(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageInspect));
						
						setHtmlPart2(con, out);
					}
					if (actText.equals("R")) {
						
						DBUtil.updateTimeOverSStatus("R", request.getParameter("rowID"), con);
						DBUtil.updateSql(SqlUtil.setStatusReturnMsg(otVo.getReturnMsg(), request.getParameter("rowID")), con);
						setHtmlPart1(con, out, otVo,UserInformation);
						System.out.println("returnMsg  :   "+SqlUtil.setStatusReturnMsg(otVo.getReturnMsg(), request.getParameter("rowID")));
						// 輸出查詢UI
						out.write(HtmlUtil.drawTableS(
								SqlUtil.getOvertimeNoSave(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageInspect));
						
						setHtmlPart2(con, out);
					}
				}else{
					otVo.setSearchDepartmen(DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()));
					otVo.setSearchEmployeeNo("0");
					otVo.setSearchEmployee("0");
					otVo.setStartSubmitDate(DateUtil.NowDate());
					otVo.setEndSubmitDate(DateUtil.NowDate());
					otVo.setStartQueryDate(DateUtil.NowDate());
					otVo.setEndQueryDate(DateUtil.NowDate());
					otVo.setQueryDate(DateUtil.NowDate());
					setHtmlPart1(con, out,otVo,UserInformation);
					setHtmlPart2(con, out);
				}
		}catch (Exception err)
		{
				out.println("<pre>");
				err.printStackTrace(out);
				out.println("</pre>");
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
	
	private String drawSelectShared(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String Filter, String SelectedOption) throws SQLException
	{

		APSelector = new WebDBSelect(name, tableName, valueField, DisplayField, Filter);
		APSelector.setClass("select2_category form-control");
		APSelector.setID(name);
		APSelector.refreshOptions(con);
		APSelector.addOption("0", "未選擇");
		APSelector.setSelectedOption(SelectedOption);
		//APSelector.writeHTML(out);
		return APSelector.toString();
	}
	
	private void setHtmlPart1(Connection con, PrintWriter out, overTimeVO otVo,UserDescriptor UserInformation) throws SQLException {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(UrlUtil.inspectOvertimePart1);
			htmlPart1=htmlPart1.replace("&SearchDepartmen", drawSelectShared(con, out, "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", "",otVo.getSearchDepartmen( )));
			htmlPart1=htmlPart1.replace("&submitDate",HtmlUtil.getDateDivSw("startSubmitDate","endSubmitDate", otVo.getStartSubmitDate(),otVo.getEndSubmitDate()));
			htmlPart1=htmlPart1.replace("	<SearchEmployeeNo/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployeeNo()));
			htmlPart1=htmlPart1.replace("&SearchEmployee",ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployee()));
			out.println(htmlPart1);
	}
	private void setHtmlPart2(Connection con, PrintWriter out) throws SQLException {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart2=hu.gethtml(UrlUtil.inspectOvertimePart2);
			out.println(htmlPart2);
	}
	
}
