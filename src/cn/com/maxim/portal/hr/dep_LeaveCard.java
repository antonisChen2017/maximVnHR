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
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.dao.leaveCardDAO;
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
 *  部门申請  請假卡 
 * @author Antonis.chen
 *
 */
public class dep_LeaveCard extends TemplatePortalPen
{
	 Log4jUtil lu=new Log4jUtil();
	 Logger logger  =lu.initLog4j(dep_LeaveCard.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
	
		String actText = request.getParameter("act");
	
		leaveCardVO lcVo = new leaveCardVO(); 
		System.out.println("ActionURI : "+ActionURI);
		lcVo.setActionURI(ActionURI);
		lcVo.setApplicationDate(DateUtil.NowDateTime());
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(lcVo,request.getParameterMap()); 
				
					// 查询
					if (actText.equals("QUE")) {
						lcVo.setShowDataTable(true);
						lcVo.setSaveButText(keyConts.butSave);
						showHtml(con, out, lcVo,UserInformation);
					}
					
				
					
					if (actText.equals("Save")) {
						String msg=DBUtil.getPersonalProcess(con,lcVo);
						if(msg.equals("o")){
							String depLeaveEdit=( String)request.getSession().getAttribute("depLeaveEdit");
							if(depLeaveEdit.equals("Update")){
								
								lcVo.setShowDataTable(true);
								logger.info("updateDepLeavecard : " +SqlUtil.updateDepLeavecard(lcVo));
								boolean flag =DBUtil.updateSql(SqlUtil.updateDepLeavecard(lcVo), con);
								if(flag){
									lcVo.setMsg(keyConts.editOK);
								}else{
									lcVo.setMsg(keyConts.editNO);
								}
							}else{
											
								lcVo.setShowDataTable(true);
								lcVo.setStatus("S");
								lcVo.setApplicationDate(DateUtil.NowDateTime());
								// 儲存db
								logger.info("請假卡 員工/Save : " +lcVo.toString());
								msg=DBUtil.saveLeaveCard(lcVo , con);
								if(msg.equals("x")){
									lcVo.setMsg("儲存失敗!");
								}else if(msg.equals("o")){
									lcVo.setMsg("已有打卡纪录!無法保存");
									logger.info("請假卡 部门人員申請/已有打卡纪录!無法保存!");
								}else if(msg.equals("w")){
									lcVo.setMsg("已請假!無法保存");
									logger.info("請假卡 部门人員申請/已請假!無法保存!");
								}else if(msg.equals("v")){
									lcVo.setMsg("請假天數為0!無法儲存!");
								}else if(msg.equals("d")){
									lcVo.setMsg("請假天數不正確!無法保存");
									logger.info("請假卡 請假天數不正確!無法保存!");
								}else{
									lcVo.setMsg("儲存成功!");
								}
								
							}
						}else{
							lcVo.setMsg(keyConts.noProcessMsg);
							logger.info(keyConts.noProcessMsg);
						}
						lcVo.setSaveButText(keyConts.butSave);
						request.getSession().setAttribute("depLeaveEdit","Save");
						showHtml(con, out,  lcVo,UserInformation);
						
					}
					if (actText.equals("Delete")) {
						String rowID = request.getParameter("rowID");
						//delete 請假單
						logger.info("請假卡 員工/Save : Delete: " +lcVo.toString());
						boolean flag=DBUtil.delDBTableRow(SqlUtil.delDBRow(keyConts.dbTableCR,rowID),con);
						lcVo.setShowDataTable(true);
						lcVo.setSaveButText(keyConts.butSave);
						if(flag){
							lcVo.setMsg("刪除成功!");
						}else{
							lcVo.setMsg("刪除失敗!");
						}
					
						showHtml(con, out, lcVo,UserInformation);
					}
					if (actText.equals("Refer"))//提交審核
					{
						logger.info("請假卡 員工/Save : Refer: " +lcVo.toString());
						DBUtil.updateSql(SqlUtil.upLCStatus(keyConts.dbTableCRStatuS_T,request.getParameter("rowID"),"0"), con);
						leaveCardDAO.deptProcessEmail(con,lcVo);
						lcVo.setShowDataTable(true);
						lcVo.setSaveButText(keyConts.butSave);
						showHtml(con, out, lcVo,UserInformation);
						
					}
					if (actText.equals("Update"))//送交
					{
						//lcVo.setSubmitDate(DateUtil.NowDateTime());
						logger.info("部门申請請假/Update : " +lcVo.toString());
						String rowID = request.getParameter("rowID");
				
						lcVo.setShowDataTable(true);
						lcVo.setRowID(rowID);
						lcVo=SharedCode(con,lcVo);
						lcVo.setSaveButText(keyConts.butUpdate);
						lcVo.setMsg(keyConts.editLeaveTip);
						request.getSession().setAttribute("depLeaveEdit","Update");
						showHtml(con, out,  lcVo,UserInformation);	
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
					lcVo.setStartLeaveMinute("0");
					lcVo.setDayCount("0");
					lcVo.setHourCount("0");
					lcVo.setMinuteCount("0");
					lcVo.setEndLeaveMinute("0");
					lcVo.setStartLeaveDate(DateUtil.NowDate());
					lcVo.setEndLeaveDate(DateUtil.NowDate());
					lcVo.setNote("");
					lcVo.setRowID("0");
					lcVo.setSearchRole(keyConts.EmpRoleE);
					lcVo.setSaveButText(keyConts.butSave);
					request.getSession().setAttribute("depLeaveEdit","query");
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
				html = ControlUtil.drawChosenSelect(con, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", unitID,false,null);
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
			 //只能查出員工
			 if(searchUnit.equals("0")){
				 subSql=" DEPARTMENT_ID = '"+searchDepartmen + "' and role='E'  ";
			 }else{
				 subSql=" UNIT_ID ='" + searchUnit + "' and role='E'  ";
			 }
			// System.out.println("subSql : "+subSql);
			try
			
			{
				html = ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", subSql, otVo.getSearchEmployeeNo(),false,null)
						+"%"+ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo(),false,null)
						+"%"+ControlUtil.drawChosenSelect(con,  "searchAgent", "HR_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo(),false,null);
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
	
		private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo , UserDescriptor UserInformation) throws Exception {
			employeeUserRO eo=new employeeUserRO();
			List<employeeUserRO> lro=null;
	
			if( lcVo.getSearchEmployee().equals("0")){
			     lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNODate(UserInformation.getUserName()) ,eo);	
			}else{
				
			     logger.info("SqlUtil getEmployeeID"+SqlUtil.getEmployeeID(lcVo.getSearchEmployee()));
				 lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeID(lcVo.getSearchEmployee()) ,eo);	
			}
			String UnitSql="";
			if(lcVo.getSearchDepartmen( ).equals("0")){
				UnitSql=" DEPARTMENT_ID='0' ";
			}else{
				UnitSql=" DEPARTMENT_ID= '"+lcVo.getSearchDepartmen( )+"'";
			}
			//System.out.println("lro : "+lro);
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_dep_LeaveCard);
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	lcVo.getActionURI());
			htmlPart1=htmlPart1.replace("<UserDepartmen/>", 	ControlUtil.drawChosenSelect(con,  "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", null,lcVo.getSearchDepartmen( ),false,null));
			htmlPart1=htmlPart1.replace("<SearchAgent/>",ControlUtil.drawChosenSelect(con,  "searchAgent", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + lro.get(0).getDID()+ "'", lcVo.getSearchAgent(),false,null));
			htmlPart1=htmlPart1.replace("<searchHoliday/>",ControlUtil.drawChosenSelect(con, "searchHoliday", "VN_LHOLIDAY", "ID", "HOLIDAYNAME", null, lcVo.getSearchHoliday(),false,null));
			htmlPart1=htmlPart1.replace("<applicationDate/>", lcVo.getApplicationDate());
			
			htmlPart1=htmlPart1.replace("<startLeaveTime/>",HtmlUtil.getLeaveCardTimeDiv("startLeaveTime",lcVo.getStartLeaveTime()));
			htmlPart1=htmlPart1.replace("<endLeaveTime/>",HtmlUtil.getLeaveCardTimeDiv("endLeaveTime",lcVo.getEndLeaveTime()));
			htmlPart1=htmlPart1.replace("<startLeaveDate/>",HtmlUtil.getDateDiv("startLeaveDate", lcVo.getStartLeaveDate()));
			htmlPart1=htmlPart1.replace("<endLeaveDate/>",HtmlUtil.getDateDiv("endLeaveDate", lcVo.getEndLeaveDate()));
			htmlPart1=htmlPart1.replace("<startLeaveMinute/>",HtmlUtil.getLeaveCardMinuteDiv("startLeaveMinute",lcVo.getStartLeaveMinute()));
			htmlPart1=htmlPart1.replace("<endLeaveMinute/>",HtmlUtil.getLeaveCardMinuteDiv("endLeaveMinute",lcVo.getEndLeaveMinute()));
			htmlPart1=htmlPart1.replace("<dayCount/>",HtmlUtil.getSpinnerDiv("dayCount",lcVo.getDayCount(),keyConts.spinnerDayMax,keyConts.spinnerDayMin,keyConts.spinnerDayStep));
			htmlPart1=htmlPart1.replace("<hourCount/>",HtmlUtil.getSpinnerDiv("hourCount",lcVo.getHourCount(),keyConts.spinnerHourMax,keyConts.spinnerHourMin,keyConts.spinnerHourStep));
			htmlPart1=htmlPart1.replace("<minuteCount/>",HtmlUtil.getSpinnerDiv("minuteCount",lcVo.getMinuteCount(),keyConts.spinnerMinuteMax,keyConts.spinnerMinuteMin,keyConts.spinnerMinuteStep));
			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", lcVo.getNote()));
			htmlPart1=htmlPart1.replace("<rowID/>",lcVo.getRowID()) ;
	
			if(!lcVo.getSearchEmployeeNo().equals("0")){
				htmlPart1=htmlPart1.replace("<dutiesDiv/>",DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(lcVo.getSearchEmployeeNo(),"  V.DUTIES "),"DUTIES"));
				htmlPart1=htmlPart1.replace("<entrydateDiv/>",DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(lcVo.getSearchEmployeeNo(),"  V.ENTRYDATE "),"ENTRYDATE"));
			}else{
				htmlPart1=htmlPart1.replace("<dutiesDiv/>","");
				htmlPart1=htmlPart1.replace("<entrydateDiv/>","");
			}
			
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(lcVo.getMsg()));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", UnitSql, lcVo.getSearchUnit(),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + 	lcVo.getSearchDepartmen()+ "'", lcVo.getSearchEmployeeNo(),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + lcVo.getSearchDepartmen() + "'", lcVo.getSearchEmployee(),false,null));
		
			htmlPart1=htmlPart1.replace("<saveButText/>",lcVo.getSaveButText());	
		 
			if(lcVo.isShowDataTable()){
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawLeaveCardTable(
						SqlUtil.getLeaveCard(lcVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageEmpUnitList));
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
			lcVo.setNote( ero.get(0).getNOTE());
			lcVo.setSearchEmployee(ero.get(0).getEMPLOYEE());
			lcVo.setSearchEmployeeNo(ero.get(0).getEMPLOYEENO());
			lcVo.setSearchDepartmen(ero.get(0).getDEPARTMENT());
			lcVo.setSearchUnit(ero.get(0).getUNIT());
		 return lcVo;
	 }
}
