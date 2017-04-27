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
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
/**
 * 請假卡 部門人員申請
 * @author Antonis.chen
 *
 */
public class emp_LeaveCard extends TemplatePortalPen
{
	 Log4jUtil lu=new Log4jUtil();
	 Logger logger  =lu.initLog4j(emp_LeaveCard.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		
	
		String actText = request.getParameter("act");
		//String us =this.getDBAttribute("us");//取管理功能設定屬性
		leaveCardVO lcVo = new leaveCardVO(); 
		lcVo.setActionURI(ActionURI);
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
						logger.info("請假卡  部門人員申請/Save : " +lcVo.toString());
						// 儲存db
						String msg=DBUtil.saveLeaveCard(lcVo , con);
						if(msg.equals("x")){
							lcVo.setMsg("儲存失敗!");
							logger.info("請假卡 部門人員申請/Save :保存失敗!");
						}else if(msg.equals("v")){
							lcVo.setMsg("請假天數為0!無法保存!");
							logger.info("請假卡 部門人員申請/請假 :請假天數為0!無法儲存!");
						}else if(msg.equals("o")){
							lcVo.setMsg("已有打卡紀錄!無法保存");
							logger.info("請假卡 部門人員申請/已有打卡紀錄!無法保存!");
						}else if(msg.equals("w")){
							lcVo.setMsg("已請假!無法保存");
							logger.info("請假卡 部門人員申請/已請假!無法保存!");
						}else{
							lcVo.setMsg("儲存成功!");
							logger.info("請假卡 部門人員申請/Save :儲存成功!");
						}
					
						showHtml(con, out,  lcVo,UserInformation);
						
					}
					if (actText.equals("Delete")) {
						String rowID = request.getParameter("rowID");
						//delete 請假單
						logger.info("請假卡 部門人員申請/Delete  " +lcVo.toString());
						boolean flag=DBUtil.delDBTableRow(SqlUtil.delDBRow(UrlUtil.dbTableCR,rowID),con);
						lcVo.setShowDataTable(true);
						if(flag){
							lcVo.setMsg("刪除成功!");
							logger.info("請假卡 部門人員申請/Delete  刪除成功!");
						}else{
							lcVo.setMsg("刪除失敗!");
							logger.info("請假卡 部門人員申請/Delete  刪除失敗!");
						}
					
						showHtml(con, out, lcVo,UserInformation);
					}
					if (actText.equals("Refer"))//提交審核
					{
						logger.info("請假卡 部門人員申請/Refer  " +lcVo.toString());
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
					lcVo.setStartLeaveMinute("0");
					lcVo.setEndLeaveMinute("0");
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
		 
		 if(ajax.equals("SwDUnit")){
			 String searchUnit = request.getParameter("searchUnit");
			 String searchDepartmen = request.getParameter("searchDepartmen");
			 String html="",subSql="";
			 overTimeVO otVo = new overTimeVO(); 
			 otVo.setSearchEmployeeNo("0");
		
			 if(searchUnit.equals("0")){
				 subSql=" DEPARTMENT_ID = '"+searchDepartmen + "' ";
			 }else{
				 subSql=" UNIT_ID ='" + searchUnit + "' ";
			 }
			// System.out.println("subSql : "+subSql);
			try
			
			{
				html = ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", subSql, otVo.getSearchEmployeeNo())
						+"#"+ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo());
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("html : "+html);
			 out.println(html);
	 	}
		 
	 }
	
	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo , UserDescriptor UserInformation) throws Exception {
			employeeUserRO eo=new employeeUserRO();
			List<employeeUserRO> lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNameDate(UserInformation.getUserName()) ,eo);	
		
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_emp_LeaveCard);
			lcVo.setSearchEmployeeNo(lro.get(0).getID());
			lcVo.setSearchEmployee(lro.get(0).getID());
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	lcVo.getActionURI());
			htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	UserInformation.getUserEmployeeNo());
			htmlPart1=htmlPart1.replace("<hiddenEmployeeNo/>",ControlUtil.drawHidden(lro.get(0).getDID(), "searchDepartmen"));	
			htmlPart1=htmlPart1.replace("<SearchAgent/>",ControlUtil.drawSelectDBControl(con, out, "searchAgent", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + lro.get(0).getDID()+ "'", lcVo.getSearchAgent()));
			htmlPart1=htmlPart1.replace("<searchHoliday/>",ControlUtil.drawSelectShared(con, out, "searchHoliday", "VN_HOLIDAY", "ID", "HOLIDAYNAME", "", lcVo.getSearchHoliday(),true));
			htmlPart1=htmlPart1.replace("<applicationDate/>",HtmlUtil.getDateDiv("applicationDate", lcVo.getApplicationDate()));
			htmlPart1=htmlPart1.replace("<startLeaveTime/>",HtmlUtil.getLeaveCardTimeDiv("startLeaveTime",lcVo.getStartLeaveDate()));
			htmlPart1=htmlPart1.replace("<endLeaveTime/>",HtmlUtil.getLeaveCardTimeDiv("endLeaveTime",lcVo.getEndLeaveTime()));
			htmlPart1=htmlPart1.replace("<startLeaveMinute/>",HtmlUtil.getLeaveCardMinuteDiv("startLeaveMinute",lcVo.getStartLeaveMinute()));
			htmlPart1=htmlPart1.replace("<endLeaveMinute/>",HtmlUtil.getLeaveCardMinuteDiv("endLeaveMinute",lcVo.getEndLeaveMinute()));
			htmlPart1=htmlPart1.replace("<startLeaveDate/>",HtmlUtil.getDateDiv("startLeaveDate", lcVo.getStartLeaveDate()));
			htmlPart1=htmlPart1.replace("<endLeaveDate/>",HtmlUtil.getDateDiv("endLeaveDate", lcVo.getEndLeaveDate()));

			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", lcVo.getNote()));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(lcVo.getMsg()));
			htmlPart1=htmlPart1.replace("<SPAN  id='dutiesDiv' ></SPAN>",lro.get(0).getDUTIES()) ;
			htmlPart1=htmlPart1.replace("<SPAN  id='entrydateDiv' ></SPAN>",String.valueOf(lro.get(0).getENTRYDATE())) ;
			htmlPart1=htmlPart1.replace("<SearchUnit/>",HtmlUtil.getLabelHtml(lro.get(0).getUNIT()));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",HtmlUtil.getLabelHtml(UserInformation.getUserName()));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",HtmlUtil.getLabelHtml(lro.get(0).getEMPLOYEENO()));
			htmlPart1=htmlPart1.replace("<hiddenUserNo/>",ControlUtil.drawHidden(lro.get(0).getID(), "searchEmployee"));	
			htmlPart1=htmlPart1.replace("<hiddenUnit/>",ControlUtil.drawHidden(lro.get(0).getUID(), "searchUnit"));	
			htmlPart1=htmlPart1.replace("<hiddenUser/>",ControlUtil.drawHidden(lro.get(0).getID(), "searchEmployeeNo"));	
			if(lcVo.isShowDataTable()){
			
					htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawLeaveCardTable(
							SqlUtil.getLeaveCard(lcVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageSave));
			}
		    out.println(htmlPart1);
	    }
}
