package cn.com.maxim.portal.attendan;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
/**
 * 請假卡 員工
 * @author Antonis.chen
 *
 */
public class userLeaveCard extends TemplatePortalPen
{

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
	
		leaveCardVO lcVo = new leaveCardVO(); 
	
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(lcVo,request.getParameterMap()); 
					// 查詢
					if (actText.equals("QUE")) {
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation);
					}
					
					if (actText.equals("SwTime")) {
						showHtml(con, out, lcVo,UserInformation);
					}
					
					if (actText.equals("Save")) {
						
						lcVo.setShowDataTable(true);
						lcVo.setStatus("S");
						// 儲存db
						String msg=DBUtil.saveLeaveCard(lcVo , con);
						if(msg.equals("x")){
							lcVo.setMsg("儲存失敗!");
						}else if(msg.equals("v")){
							lcVo.setMsg("請假天數為0!無法儲存!");
						}else{
							lcVo.setMsg("儲存成功!");
						}
					
						showHtml(con, out,  lcVo,UserInformation);
						
					}
					if (actText.equals("Delete")) {
						String rowID = request.getParameter("rowID");
						//delete 請假單
						boolean flag=DBUtil.delDBTableRow(SqlUtil.delDBRow(UrlUtil.dbTableCR,rowID),con);
						lcVo.setShowDataTable(true);
						if(flag){
							lcVo.setMsg("刪除成功!");
						}else{
							lcVo.setMsg("刪除失敗!");
						}
					
						showHtml(con, out, lcVo,UserInformation);
					}
					if (actText.equals("Refer"))//提交審核
					{
						
						DBUtil.updateDbTable(SqlUtil.upLCStatus(UrlUtil.dbTableCRStatuS_T,request.getParameter("rowID")), con);
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation);
						
					}
					
					
				}else{
					//預設
					lcVo.setSearchDepartmen("0");
					lcVo.setSearchUnit("0");
					lcVo.setSearchEmployeeNo("0");
					lcVo.setSearchEmployee("0");
					lcVo.setSearchHoliday("0");
					lcVo.setStartLeaveTime("0");
					lcVo.setEndLeaveTime("0");
					lcVo.setSearchAgent("0");
					lcVo.setApplicationDate(DateUtil.NowDate());
					lcVo.setStartLeaveDate(DateUtil.NowDate());
					lcVo.setEndLeaveDate(DateUtil.NowDate());
					lcVo.setNote("");
					showHtml(con, out, lcVo,UserInformation);
				
				}
		}catch (Exception err)
		{
				out.println("<pre>");
				err.printStackTrace(out);
				out.println("</pre>");
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		
		if(ajax.equals("unit")){
			 String employeeID = request.getParameter("employeeID");
			 String unitID= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.UNIT_ID "),"UNIT_ID");
			 String html="";
			try
			{
				html = ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", unitID);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 out.println(html);
	 	}
		 if(ajax.equals("duties")){
			  String employeeID = request.getParameter("employeeID");
			  String entryDate= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.ENTRYDATE "),"ENTRYDATE");
			  String duties= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.DUTIES "),"DUTIES");
			  String data= "[{ \"duties\":\""+duties+"\" , \"entryDate\":\""+entryDate+"\" }]";
			// System.out.println("TimeDiv :"+TimeDiv);
			  out.println(data);
	 	}
	 }
	
	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo , UserDescriptor UserInformation) throws SQLException {
		HtmlUtil hu=new HtmlUtil();
		String htmlPart1=hu.gethtml(UrlUtil.userLeaveCard);
		htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	UserInformation.getUserEmployeeNo());
		htmlPart1=htmlPart1.replace("<hiddenEmployeeNo/>",ControlUtil.drawHidden(DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()), "searchDepartmen"));	
		htmlPart1=htmlPart1.replace("<SearchAgent/>",ControlUtil.drawSelectDBControl(con, out, "searchAgent", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", lcVo.getSearchAgent()));
		htmlPart1=htmlPart1.replace("<searchHoliday/>",ControlUtil.drawSelectShared(con, out, "searchHoliday", "VN_HOLIDAY", "ID", "HOLIDAYNAME", "", lcVo.getSearchHoliday(),true));
		htmlPart1=htmlPart1.replace("<applicationDate/>",HtmlUtil.getDateDiv("applicationDate", lcVo.getApplicationDate()));
		htmlPart1=htmlPart1.replace("<startLeaveDate/>",HtmlUtil.getDateDiv("startLeaveDate", lcVo.getStartLeaveDate()));
		htmlPart1=htmlPart1.replace("<endLeaveDate/>",HtmlUtil.getDateDiv("endLeaveDate", lcVo.getEndLeaveDate()));
		htmlPart1=htmlPart1.replace("<startLeaveTime/>",HtmlUtil.getLeaveCardTimeDiv("startLeaveTime",lcVo.getStartLeaveDate()));
		htmlPart1=htmlPart1.replace("<endLeaveTime/>",HtmlUtil.getLeaveCardTimeDiv("endLeaveTime",lcVo.getEndLeaveTime()));
		htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", lcVo.getNote()));
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(lcVo.getMsg()));
		if(lcVo.isShowDataTable()){
			htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawLeaveCardTable(
					SqlUtil.getLeaveCard(lcVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageSave));
		}
		htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", lcVo.getSearchUnit()));
		htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", lcVo.getSearchEmployeeNo()));
		htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", lcVo.getSearchEmployee()));
		
	    out.println(htmlPart1);
}
}
