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
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
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

public class unt_ExceedOvertime extends TemplatePortalPen
{
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(unt_InspectOvertime.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		
		String actText = request.getParameter("act");
		overTimeVO otVo = new overTimeVO(); 
		otVo.setActionURI(ActionURI);
		otVo.setSubmitDate(DateUtil.NowDateTime());
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(otVo,request.getParameterMap()); 
				
					// 查询
					if (actText.equals("QUE")) {
						otVo.setShowDataTable(true);
						showHtml(con, out, otVo,UserInformation);
					}
					
					if (actText.equals("SwTime")) {
						showHtml(con, out, otVo,UserInformation);
					}
					
					if (actText.equals("Save")) {
						logger.info("超時加班申請單 員工/Save : " +otVo.toString());
						otVo.setShowDataTable(true);
						//需超過系統規定加班時數
						otVo.setOverTimeSave(true);
						otVo.setStatus("S");
						// 儲存db
						String msg=DBUtil.saveExceedOvertime(otVo , con);
						otVo.setMsg(msg);
						DBUtil.saveReasons( con,otVo );
						showHtml(con, out,  otVo,UserInformation);
						
					}
					if (actText.equals("Delete")) {
						String rowID = request.getParameter("rowID");
						//delete 加班單
						logger.info("加班申請單 員工/Delete : " +otVo.toString());
						
					    DBUtil.delDBTableRow(SqlUtil.delOvertimeS(rowID), con);
					
						otVo.setShowDataTable(true);
						otVo.setMsg("已刪除");
						showHtml(con, out, otVo,UserInformation);
					}
					if (actText.equals("Refer"))//送交
					{
						logger.info("加班申請單 員工/Refer : " +otVo.toString());
						otVo.setStatus(keyConts.dbTableUT);
						otVo.setStatus("0");
						DBUtil.updateTimeOverSStatus(otVo, con);
						otVo.setShowDataTable(true);
						otVo.setMsg("已送交");
						showHtml(con, out, otVo,UserInformation);
						
					}
					
					
				}else{
					//預設
					otVo.setSearchDepartmen("0");
					otVo.setSearchUnit("0");
					otVo.setSearchEmployeeNo("0");
					otVo.setSearchEmployee("0");
					otVo.setSearchReasons("0");
					otVo.setOverTimeClass("0");
					otVo.setAddTime("0");
					otVo.setStartTimeHh("0");
					otVo.setStartTimemm("0");
					otVo.setEndTimeHh("0");
					otVo.setEndTimemm("0");
					otVo.setNote("");
					otVo.setQueryDate(DateUtil.NowDate());
			
					otVo.setUserReason("");
					//System.out.println("actText null   otVo : "+otVo.toString());
					showHtml(con, out, otVo,UserInformation);
				
				}
		}catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
	}
	
	 public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		 
		 if(ajax.equals("unit")){
			 String employeeID = request.getParameter("employeeID");
			 String unitID= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.UNIT_ID "),"UNIT_ID");
			 String html="";
			try
			{
				html = ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", unitID,false,null);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 out.println(html);
	 	}
		 if(ajax.equals("SwTime")){
			 overTimeVO otVo = new overTimeVO(); 
			 otVo.setOverTimeClass(request.getParameter("overTimeClass"));
			 otVo.setAct("SwTime");
			 
			 String ehm=DBUtil.queryDBField(con,SqlUtil.getEhm(otVo),"ehm");
			 if(ehm.indexOf(":")==-1){
				 ehm="00:00";
			 }
			 String[] ehms=ehm.split(":");
			 otVo.setStartTimeHh(ehms[0]);
			 otVo.setStartTimemm(ehms[1]);
			 int hr=Integer.valueOf(ehms[0])+5;
			 if(hr>23){
				 hr=23;
			 }
			 otVo.setEndTimeHh(String.valueOf(hr));
			 otVo.setEndTimemm(ehms[1]);
			 
			 String TimeDiv= HtmlUtil.getTimeDiv("startTimeHh", "startTimemm", "endTimeHh", "endTimemm",otVo);
			 System.out.println("TimeDiv :"+TimeDiv);
			 out.println(TimeDiv);
	 	}
		 if(ajax.equals("SwDUnit")){
			 String searchUnit = request.getParameter("searchUnit");
			 String searchDepartmen = request.getParameter("searchDepartmen");
			 String html="",subSql="";
			 overTimeVO otVo = new overTimeVO(); 
			 otVo.setSearchEmployeeNo("0");
		
			 if(searchUnit.equals("0")){
				 subSql=" DEPARTMENT_ID = '"+searchDepartmen + "' and role='E'  ";
			 }else{
				 subSql=" UNIT_ID ='" + searchUnit + "' and role='E'  ";
			 }
			// System.out.println("subSql : "+subSql);
			try
			
			{
				html = ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", subSql, otVo.getSearchEmployeeNo(),false,null)
						+"%"+ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo(),false,null);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("html : "+html);
			 out.println(html);
	 	}
		 
		 if(ajax.equals("SwUnit")){
			 String searchDepartmen = request.getParameter("searchDepartmen");
			 String unitID= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(searchDepartmen,"  V.UNIT_ID "),"UNIT_ID");
			 String html="";
			 overTimeVO otVo = new overTimeVO(); 
			 otVo.setSearchEmployeeNo("0");
			try
			{
				html = ControlUtil.drawChosenSelect(con, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + searchDepartmen + "'", "0",false,null)
						+"%"+ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "UNIT_ID='" + unitID + "'", otVo.getSearchEmployeeNo(),false,null)
						+"%"+ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "UNIT_ID='" + unitID + "'", otVo.getSearchEmployeeNo(),false,null);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("html : "+html);
			 out.println(html);
	 	}
	 }
	 
	private void showHtml(Connection con, PrintWriter out, overTimeVO otVo , UserDescriptor UserInformation) throws SQLException {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_ad_exceedOvertime);
			
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
			htmlPart1=htmlPart1.replace("<UserDepartmen/>", 	ControlUtil.drawChosenSelect(con,  "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", null,otVo.getSearchDepartmen( ),false,null));
			String DEPARTMENT_ID="";
			if(otVo.getSearchDepartmen( ).equals("0")){
				DEPARTMENT_ID=DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) ;
			}else{
				DEPARTMENT_ID=otVo.getSearchDepartmen( );
			}
			htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", " DEPARTMENT_ID='" +DEPARTMENT_ID + "'  AND UNIT not like '%部%' ", otVo.getSearchUnit(),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", " DEPARTMENT_ID='"+otVo.getSearchDepartmen()+"'", otVo.getSearchEmployeeNo(),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con, "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='"+otVo.getSearchDepartmen()+"'", otVo.getSearchEmployee(),false,null));
			htmlPart1=htmlPart1.replace("&SearchReasons",ControlUtil.drawChosenSelect(con,  "searchReasons", "VN_LREASONS", "ID", "REASONS", null, otVo.getSearchReasons(),false,null));
		//	htmlPart1=htmlPart1.replace("&OverTimeClass",HtmlUtil.getOverTimeClass( otVo));
			htmlPart1=htmlPart1.replace("&OverTimeClass",ControlUtil.drawChosenSelect(con,  "overTimeClass", "VN_TURN", "Code", "CBName", " 1=1  ", otVo.getOverTimeClass(),false,null));
			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", otVo.getNote()));
			htmlPart1=htmlPart1.replace("&submitDate",otVo.getSubmitDate());
			htmlPart1=htmlPart1.replace("&userReason",HtmlUtil.getTextDiv("userReason",otVo.getUserReason(),"可輸入自訂加班事由" ));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			logger.info("ExceedOvertime getExceedOvertime  :   "+SqlUtil.getExceedOvertime(otVo));
			if(otVo.isShowDataTable()){
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
						SqlUtil.getExceedOvertime(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageEmpUnitList));
			}
			
		    out.println(htmlPart1);
	}
	

}
