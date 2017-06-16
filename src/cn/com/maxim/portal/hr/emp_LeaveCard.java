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
import cn.com.maxim.portal.attendan.ro.exLeaveCardRO;
import cn.com.maxim.portal.attendan.ro.exOvertimeRO;
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
import cn.com.maxim.potral.consts.keyConts;
/**
 * 請假卡 個人
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
		lcVo.setApplicationDate(	DateUtil.NowDateTime());
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(lcVo,request.getParameterMap()); 
				
					// 查询
					if (actText.equals("QUE")) {
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation,request);
					}
					
					if (actText.equals("SwTime")) {
						showHtml(con, out, lcVo,UserInformation,request);
					}
					
					if (actText.equals("Save")) {
						if(lcVo.getRowID().equals("0")){
							lcVo.setApplicationDate(	DateUtil.NowDateTime());
							lcVo.setShowDataTable(true);
							lcVo.setStatus("S");
							logger.info("請假卡  部门人員申請/Save : " +lcVo.toString());
							// 儲存db
							String msg=DBUtil.saveLeaveCard(lcVo , con);
							if(msg.equals("x")){
								lcVo.setMsg("儲存失敗!");
								logger.info("請假卡 部门人員申請/Save :保存失敗!");
							}else if(msg.equals("v")){
								lcVo.setMsg("請假天數為0!無法保存!");
								logger.info("請假卡 部门人員申請/請假 :請假天數為0!無法儲存!");
							}else if(msg.equals("o")){
								lcVo.setMsg("已有打卡纪录!無法保存");
								logger.info("請假卡 部门人員申請/已有打卡纪录!無法保存!");
							}else if(msg.equals("w")){
								lcVo.setMsg("已請假!無法保存");
								logger.info("請假卡 部门人員申請/已請假!無法保存!");
							}else{
								lcVo.setMsg("儲存成功!");
								logger.info("請假卡 部门人員申請/Save :儲存成功!");
							}
						}else{
							logger.info("updateEmpOverTime : " +SqlUtil.updateEmpLeavecard(lcVo));
							boolean flag =DBUtil.updateSql(SqlUtil.updateEmpLeavecard(lcVo), con);
							if(flag){
								lcVo.setMsg(keyConts.editOK);
							}else{
								lcVo.setMsg(keyConts.editNO);
							}
							lcVo.setShowDataTable(true);
						}
						showHtml(con, out,  lcVo,UserInformation,request);
						
					}
					if (actText.equals("Delete")) {
						String rowID = request.getParameter("rowID");
						//delete 請假單
						logger.info("請假卡 部门人員申請/Delete  " +lcVo.toString());
						boolean flag=DBUtil.delDBTableRow(SqlUtil.delDBRow(keyConts.dbTableCR,rowID),con);
						lcVo.setShowDataTable(true);
						if(flag){
							lcVo.setMsg("刪除成功!");
							logger.info("請假卡 部门人員申請/Delete  刪除成功!");
						}else{
							lcVo.setMsg("刪除失敗!");
							logger.info("請假卡 部门人員申請/Delete  刪除失敗!");
						}
					
						showHtml(con, out, lcVo,UserInformation,request);
					}
					if (actText.equals("Refer"))//提交審核
					{
						logger.info("請假卡 部门人員申請/Refer  " +lcVo.toString());
						DBUtil.updateSql(SqlUtil.upLCStatus(keyConts.dbTableCRStatuS_T,request.getParameter("rowID")), con);
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation,request);
						
					}
					if (actText.equals("Update"))//送交
					{
						//lcVo.setSubmitDate(DateUtil.NowDateTime());
						logger.info("個人申請請假/Update : " +lcVo.toString());
						String rowID = request.getParameter("rowID");
				
						lcVo.setShowDataTable(true);
						lcVo.setRowID(rowID);
						lcVo=SharedCode(con,lcVo);
						showHtml(con, out,  lcVo,UserInformation,request);
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
					lcVo.setDayCount("0");
					lcVo.setHourCount("0");
					lcVo.setMinuteCount("0");
					lcVo.setStartLeaveDate(DateUtil.NowDate());
					lcVo.setEndLeaveDate(DateUtil.NowDate());
					lcVo.setNote("");
					lcVo.setRowID("0");
					showHtml(con, out, lcVo,UserInformation,request);
				
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
				 subSql=" DEPARTMENT_ID = '"+searchDepartmen + "' and role='E'  ";
			 }else{
				 subSql=" UNIT_ID ='" + searchUnit + "' and role='E'  ";
			 }
			// System.out.println("subSql : "+subSql);
			try
			
			{
				html = ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", subSql, otVo.getSearchEmployeeNo())
						+"%"+ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo());
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
	
	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo , UserDescriptor UserInformation,HttpServletRequest request) throws Exception {
			
			
			List<employeeUserRO> lro= getUser(con,UserInformation,request);
		
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_emp_LeaveCard);
			lcVo.setSearchEmployeeNo(lro.get(0).getID());
			lcVo.setSearchEmployee(lro.get(0).getID());
			lcVo.setSearchRole(lro.get(0).getROLE());
			
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	lcVo.getActionURI());
			htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	UserInformation.getUserEmployeeNo());
			htmlPart1=htmlPart1.replace("<hiddenEmployeeNo/>",ControlUtil.drawHidden(lro.get(0).getDID(), "searchDepartmen"));	
			htmlPart1=htmlPart1.replace("<SearchAgent/>",ControlUtil.drawChosenSelect(con, "searchAgent", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + lro.get(0).getDID()+ "'", lcVo.getSearchAgent(),false,null));
			htmlPart1=htmlPart1.replace("<searchHoliday/>",ControlUtil.drawChosenSelect(con, "searchHoliday", "VN_LHOLIDAY", "ID", "HOLIDAYNAME", null, lcVo.getSearchHoliday(),false,null));
			htmlPart1=htmlPart1.replace("<applicationDate/>",lcVo.getApplicationDate());
			htmlPart1=htmlPart1.replace("<startLeaveTime/>",HtmlUtil.getLeaveCardTimeDiv("startLeaveTime",lcVo.getStartLeaveDate()));
			htmlPart1=htmlPart1.replace("<endLeaveTime/>",HtmlUtil.getLeaveCardTimeDiv("endLeaveTime",lcVo.getEndLeaveTime()));
			htmlPart1=htmlPart1.replace("<startLeaveMinute/>",HtmlUtil.getLeaveCardMinuteDiv("startLeaveMinute",lcVo.getStartLeaveMinute()));
			htmlPart1=htmlPart1.replace("<endLeaveMinute/>",HtmlUtil.getLeaveCardMinuteDiv("endLeaveMinute",lcVo.getEndLeaveMinute()));
			htmlPart1=htmlPart1.replace("<startLeaveDate/>",HtmlUtil.getDateDiv("startLeaveDate",lcVo.getStartLeaveDate()));
			htmlPart1=htmlPart1.replace("<endLeaveDate/>",HtmlUtil.getDateDiv("endLeaveDate", lcVo.getEndLeaveDate()));
			htmlPart1=htmlPart1.replace("<dayCount/>",HtmlUtil.getSpinnerDiv("dayCount",lcVo.getDayCount(),keyConts.spinnerDayMax,keyConts.spinnerDayMin,keyConts.spinnerDayStep));
			htmlPart1=htmlPart1.replace("<hourCount/>",HtmlUtil.getSpinnerDiv("hourCount",lcVo.getHourCount(),keyConts.spinnerHourMax,keyConts.spinnerHourMin,keyConts.spinnerHourStep));
			htmlPart1=htmlPart1.replace("<minuteCount/>",HtmlUtil.getSpinnerDiv("minuteCount",lcVo.getMinuteCount(),keyConts.spinnerMinuteMax,keyConts.spinnerMinuteMin,keyConts.spinnerMinuteStep));
			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", lcVo.getNote()));
			htmlPart1=htmlPart1.replace("<rowID/>",lcVo.getRowID());
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(lcVo.getMsg()));
			htmlPart1=htmlPart1.replace("<SPAN  id='dutiesDiv' ></SPAN>",lro.get(0).getDUTIES()) ;
			htmlPart1=htmlPart1.replace("<SPAN  id='entrydateDiv' ></SPAN>",String.valueOf(lro.get(0).getENTRYDATE())) ;
			htmlPart1=htmlPart1.replace("<SearchUnit/>",HtmlUtil.getLabelHtml(lro.get(0).getUNIT()));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",HtmlUtil.getLabelHtml(lro.get(0).getEMPLOYEE()));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",HtmlUtil.getLabelHtml(lro.get(0).getEMPLOYEENO()));
			htmlPart1=htmlPart1.replace("<hiddenUserNo/>",ControlUtil.drawHidden(lro.get(0).getID(), "searchEmployee"));	
			htmlPart1=htmlPart1.replace("<hiddenUnit/>",ControlUtil.drawHidden(lro.get(0).getUID(), "searchUnit"));	
			htmlPart1=htmlPart1.replace("<hiddenUser/>",ControlUtil.drawHidden(lro.get(0).getID(), "searchEmployeeNo"));	
			if(lcVo.isShowDataTable()){
				logger.info(" sql getLeaveCard="+SqlUtil.getLeaveCard(lcVo));
					htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawLeaveCardTable(
							SqlUtil.getLeaveCard(lcVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageSave));
			}
		    out.println(htmlPart1);
	    }
	
		/**
		 * 共用查询區塊
		 */
		 private leaveCardVO SharedCode(Connection con,leaveCardVO lcVo){
			    exLeaveCardRO er=new exLeaveCardRO();
				List<exLeaveCardRO> ero=DBUtil.queryLeaveCardList(con,SqlUtil.getBeLeaveCard(lcVo.getRowID()),er);
				logger.info("getBeLeaveCard : " +SqlUtil.getBeLeaveCard(lcVo.getRowID()));
				lcVo.setSearchHoliday(ero.get(0).getHID());
				lcVo.setRowID(ero.get(0).getID());
				String [] StartsLeaves = ero.get(0).getSTARTLEAVEDATE().split("\\s+");
				lcVo.setStartLeaveDate(StartsLeaves[0].replace("-", "/"));
				String [] StartsLeavess =StartsLeaves[1].split(":");
				lcVo.setStartLeaveTime(StartsLeavess[0]);
				lcVo.setStartLeaveMinute(StartsLeavess[1]);
			
				String [] EndLeaves = ero.get(0).getENDLEAVEDATE().split("\\s+");
				lcVo.setEndLeaveDate(EndLeaves[0].replace("-", "/"));
				String [] EndLeavess =EndLeaves[1].split(":");
				lcVo.setEndLeaveTime(EndLeavess[0]);
				lcVo.setEndLeaveMinute(EndLeavess[1]);
				lcVo.setSearchAgent( ero.get(0).getAgent());
				lcVo.setDayCount( ero.get(0).getDAYCOUNT());
				lcVo.setHourCount( ero.get(0).getHOURCOUNT());
				lcVo.setMinuteCount( ero.get(0).getMINUTECOUNT());
				lcVo.setNote(     ero.get(0).getNOTE());
		
			 return lcVo;
		 }
		 /**
		  * 切換成員
		  * @param con
		  * @param UserInformation
		  * @param request
		  * @return
		  */
		 private  List<employeeUserRO> getUser(Connection con,UserDescriptor UserInformation,HttpServletRequest request){
			 	employeeUserRO eo=new employeeUserRO();
				String UserName="";
				String employeeNoSys=( String)request.getSession().getAttribute("employeeNoSys");
				if(employeeNoSys!=null && !employeeNoSys.equals("")){
						UserName=employeeNoSys;				
				}else{
						UserName=UserInformation.getUserName();
				}
				logger.info(" sql getEmployeeNameDate="+SqlUtil.getEmployeeNODate(UserName));
				List<employeeUserRO> lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNODate(UserName) ,eo);	
				return lro;
		 }
		 
		 
}
