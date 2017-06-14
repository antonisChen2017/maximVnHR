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
import cn.com.maxim.portal.attendan.ro.exOvertimeRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;
/**
 * 加班申請單 部门
 * @author Antonis.chen
 *
 */
public class dep_OverTime extends TemplatePortalPen
{ 
	
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(dep_OverTime.class);
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
						otVo.setRowID("0");
						request.getSession().setAttribute("dotEdit","QUE");
						showHtml(con, out, otVo,UserInformation);
					}

					if (actText.equals("Save")) {
						logger.info("加班申請單 員工/Save : " +otVo.toString());
						otVo.setShowDataTable(true);
						//不能超過系統規定加班時數 先檢查
						
						
						otVo.setOverTimeSave(false);
						otVo.setStatus("S");
						
						// 儲存db
						String dotEdit=( String)request.getSession().getAttribute("dotEdit");
						if(dotEdit.equals("Update")){
							
							logger.info("updateDepOverTimeM : " +SqlUtil.updateDepOverTimeM(otVo));
							boolean flag =DBUtil.updateSql(SqlUtil.updateDepOverTimeM(otVo), con);
							
							if(flag){
								logger.info("updateDepOverTime : " +SqlUtil.updateDepOverTime(otVo));
								boolean oflag =DBUtil.updateSql(SqlUtil.updateDepOverTime(otVo), con);
								if(oflag){
									otVo.setMsg(keyConts.editOK);
								}else{
									otVo.setMsg(keyConts.editNO);
								}
							}else{
								otVo.setMsg(keyConts.editNO);
							}
							//將rowid歸零
						}else{
						
							String msg=DBUtil.saveOvertime(otVo , con);
							otVo.setMsg(msg);
						}
						
						request.getSession().setAttribute("dotEdit","Save");
						showHtml(con, out,  otVo,UserInformation);
						
					}
					if (actText.equals("Delete")) {
						String rowID = request.getParameter("rowID");
						//delete 加班單
						logger.info("加班申請單 員工/Delete : " +otVo.toString());
						
					    DBUtil.delDBTableRow(SqlUtil.delOvertimeS(rowID), con);
					
						otVo.setShowDataTable(true);
						otVo.setMsg("已刪除");
						request.getSession().setAttribute("dotEdit","Delete");
						showHtml(con, out, otVo,UserInformation);
					}
					if (actText.equals("Refer"))//送交
					{
						logger.info("加班申請單 員工/Refer : " +otVo.toString());
						DBUtil.updateTimeOverSStatus(keyConts.dbTableCRStatuS_T, request.getParameter("rowID"), con);
						otVo.setShowDataTable(true);
						otVo.setMsg("已送交");
						request.getSession().setAttribute("dotEdit","Refer");
						showHtml(con, out, otVo,UserInformation);
						
					}
					if (actText.equals("Update"))//送交
					{
						otVo.setSubmitDate(DateUtil.NowDateTime());
						logger.info("部门申請加班/Update : " +otVo.toString());
						String rowID = request.getParameter("rowID");
				
						otVo.setShowDataTable(true);
						otVo.setRowID(rowID);
						otVo=SharedCode(con,otVo);
						request.getSession().setAttribute("dotEdit","Update");
						showHtml(con, out,  otVo,UserInformation);	
					}
					
				}else{
					//預設
					otVo.setSearchDepartmen("0");
					otVo.setSearchUnit("0");
					otVo.setSearchEmployeeNo("0");
					otVo.setSearchEmployee("0");
					otVo.setSearchReasons("0");
					otVo.setOverTimeClass("0");
					otVo.setAddTime("1");
					otVo.setStartTimeHh("0");
					otVo.setStartTimemm("0");
					otVo.setEndTimeHh("0");
					otVo.setEndTimemm("0");
					otVo.setNote("");
					otVo.setQueryDate(DateUtil.NowDate());
					otVo.setRowID("0");
					otVo.setmID("0");
					otVo.setUserReason("");
					otVo.setEP_ID("0");
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
		 
		 if(ajax.equals("duties")){
			  String employeeID = request.getParameter("employeeID");
			  String entryDate= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.ENTRYDATE "),"ENTRYDATE");
			  String duties= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.DUTIES "),"DUTIES");
			  String data= "[{ \"duties\":\""+duties+"\" , \"entryDate\":\""+entryDate+"\" }]";
			// System.out.println("TimeDiv :"+TimeDiv);
			  out.println(data);
	 	}
	 }
	 
	private void showHtml(Connection con, PrintWriter out, overTimeVO otVo , UserDescriptor UserInformation) throws SQLException {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_dep_OverTime);
			String UnitSql="",EmployeeSql="";
			if(otVo.getSearchDepartmen( ).equals("0")){
				UnitSql=" DEPARTMENT_ID='0' ";
			}else{
				UnitSql=" DEPARTMENT_ID= '"+otVo.getSearchDepartmen( )+"'";
			}
			
			if(otVo.getSearchUnit().equals("0")){
				EmployeeSql=" UNIT_ID='0' ";
			}else{
				EmployeeSql=" UNIT_ID='" + otVo.getSearchUnit() + "'";
			}
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
			htmlPart1=htmlPart1.replace("<UserDepartmen/>", 	ControlUtil.drawChosenSelect(con,  "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", null,otVo.getSearchDepartmen( ),false,null));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT",UnitSql , otVo.getSearchUnit(),false,null));
			htmlPart1=htmlPart1.replace("&SearchReasons",ControlUtil.drawChosenSelect(con,  "searchReasons", "VN_LREASONS", "ID", "REASONS", null, otVo.getSearchReasons(),false,null));
			htmlPart1=htmlPart1.replace("&OverTimeClass",ControlUtil.drawChosenSelect(con,  "overTimeClass", "VN_TURN", "Code", "CBName", " 1=1  ", otVo.getOverTimeClass(),false,null));
			htmlPart1=htmlPart1.replace("<rowID/>",otVo.getRowID());	
			htmlPart1=htmlPart1.replace("<mID/>",otVo.getmID());	
			htmlPart1=htmlPart1.replace("&addTime",HtmlUtil.getSpinnerDiv("addTime",otVo.getAddTime(),keyConts.spinnerDayMax,keyConts.spinnerDayMin,keyConts.spinnerDayStep));
			htmlPart1=htmlPart1.replace("<TimeDiv/>",HtmlUtil.getTimeDiv("startTimeHh", "startTimemm", "endTimeHh", "endTimemm",otVo));
			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", otVo.getNote()));
			htmlPart1=htmlPart1.replace("&submitDate",otVo.getSubmitDate());
			htmlPart1=htmlPart1.replace("&queryDate",HtmlUtil.getDateDiv("queryDate", otVo.getQueryDate()));
			htmlPart1=htmlPart1.replace("&userReason",HtmlUtil.getTextDiv("userReason",otVo.getUserReason(),"可輸入自訂加班事由" ));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			if(!otVo.getSearchEmployeeNo().equals("0")){
				htmlPart1=htmlPart1.replace("<duties/>",DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(otVo.getSearchEmployeeNo(),"  V.DUTIES "),"DUTIES"));
				htmlPart1=htmlPart1.replace("<entryDate/>",DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(otVo.getSearchEmployeeNo(),"  V.ENTRYDATE "),"ENTRYDATE"));
			}else{
				htmlPart1=htmlPart1.replace("<duties/>","");
				htmlPart1=htmlPart1.replace("<entryDate/>","");
			}
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO",EmployeeSql , otVo.getSearchEmployeeNo(),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con, "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", EmployeeSql, otVo.getSearchEmployee(),false,null));
		
			
			System.out.println("getOvertime  :   "+SqlUtil.getOvertime(otVo));
			if(otVo.isShowDataTable()){
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
						SqlUtil.getOvertime(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageEmpUnitList));
			}
			
		    out.println(htmlPart1);
	}
	
	/**
	 * 共用查询區塊
	 */
	 private overTimeVO SharedCode(Connection con,overTimeVO otVo){
		    exOvertimeRO er=new exOvertimeRO();
			List<exOvertimeRO> ero=DBUtil.queryExOvertimeList(con,SqlUtil.getExOvertimeRO(otVo.getRowID()),er);
			logger.info("getExOvertimeRO : " +SqlUtil.getExOvertimeRO(otVo.getRowID()));
		
			otVo.setOverTimeClass(ero.get(0).getTURN());
			otVo.setRowID(ero.get(0).getID());
			otVo.setmID(ero.get(0).getMID());
			if(ero.get(0).getREASONS().equals("0")){
				otVo.setUserReason(ero.get(0).getUSERREASONS());
			}else{
				otVo.setSearchReasons(ero.get(0).getREASONS());
			}
			otVo.setAddTime(ero.get(0).getAPPLICATION_HOURS());
			otVo.setNote(ero.get(0).getNOTE());
			String [] overTimeStarts = ero.get(0).getOVERTIMESTART().split("\\s+");
			String [] overTimeStartds =overTimeStarts[1].split(":");
			otVo.setStartTimeHh(overTimeStartds[0]);
			otVo.setStartTimemm(overTimeStartds[1]);
		    otVo.setQueryDate(overTimeStarts[0].replace("-", "/"));
			String [] overTimeEnds = ero.get(0).getOVERTIMEEND().split("\\s+");
			String [] overTimeEndds =overTimeEnds[1].split(":");
			otVo.setEndTimeHh(overTimeEndds[0]);
			otVo.setEndTimemm(overTimeEndds[1]);
			otVo.setSearchDepartmen(ero.get(0).getDEPARTMENT());
			otVo.setSearchUnit(ero.get(0).getUNIT());
			otVo.setSearchEmployee(ero.get(0).getEMPLOYEE());
			otVo.setSearchEmployeeNo(ero.get(0).getEMPLOYEENO());
		 return otVo;
	 }
}
