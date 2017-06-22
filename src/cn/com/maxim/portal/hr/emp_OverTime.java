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
import cn.com.maxim.portal.attendan.ro.exOvertimeRO;
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
 * 個人申請加班
 * @author Antonis.chen
 *
 */
public class emp_OverTime extends TemplatePortalPen
{
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(emp_OverTime.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		
		String actText = request.getParameter("act");
		overTimeVO otVo = new overTimeVO(); 
		//System.out.println("ActionURI : "+ActionURI);
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
						request.getSession().setAttribute("eotEdit","QUE");
						showHtml(con, out, otVo,UserInformation,request);
					}
					
				
					
					if (actText.equals("Save")) {
						logger.info("個人申請加班/Save : " +otVo.toString());
						String msg=DBUtil.getPersonalProcess(con,otVo);
						
						otVo.setShowDataTable(true);
						//不能超過系統規定加班時數
						otVo.setOverTimeSave(false);
						// 儲存db
						
						String eotEdit=( String)request.getSession().getAttribute("eotEdit");
						
						if(eotEdit.equals("Update")){
							logger.info("updateEmpOverTime : " +SqlUtil.updateEmpOverTime(otVo));
							boolean flag =DBUtil.updateSql(SqlUtil.updateEmpOverTime(otVo), con);
							if(flag){
								otVo.setMsg(keyConts.editOK);
							}else{
								otVo.setMsg(keyConts.editNO);
							}
						}else{
							msg=DBUtil.saveOvertime(otVo , con);
							otVo.setMsg(msg);
						}
						
						request.getSession().setAttribute("eotEdit","Save");
						showHtml(con, out,  otVo,UserInformation,request);
						
					}
					if (actText.equals("Delete")) {
						logger.info("個人申請加班/Delete : " +otVo.toString());
						String rowID = request.getParameter("rowID");
						//delete 加班單
						
					    DBUtil.delDBTableRow(SqlUtil.delOvertimeS(rowID), con);
						otVo.setShowDataTable(true);
						otVo.setMsg("已刪除");
						request.getSession().setAttribute("eotEdit","Delete");
						showHtml(con, out, otVo,UserInformation,request);
					}
					if (actText.equals("Refer"))//送交
					{
						otVo.setSubmitDate(DateUtil.NowDateTime());
						logger.info("個人申請加班/Refer : " +otVo.toString());
						DBUtil.updateTimeOverSStatus(keyConts.dbTableCRStatuS_T, request.getParameter("rowID"), con);
						otVo.setShowDataTable(true);
						otVo.setMsg("已送交");
						request.getSession().setAttribute("eotEdit","Refer");
						showHtml(con, out, otVo,UserInformation,request);
						
					}
					if (actText.equals("Update"))//送交
					{
						otVo.setSubmitDate(DateUtil.NowDateTime());
						logger.info("個人申請加班/Update : " +otVo.toString());
						String rowID = request.getParameter("rowID");
				
						otVo.setShowDataTable(true);
						otVo.setRowID(rowID);
						otVo=SharedCode(con,otVo);
						request.getSession().setAttribute("eotEdit","Update");
						showHtml(con, out,  otVo,UserInformation,request);
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
					otVo.setEndQueryDate("0");
					otVo.setStartQueryDate("0");
					otVo.setNote("");
					otVo.setQueryDate(DateUtil.NowDate());
					otVo.setUserReason("");
					otVo.setRowID("0");
					request.getSession().setAttribute("eotEdit","");
					showHtml(con, out, otVo,UserInformation,request);
				
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
				html = ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", subSql, otVo.getSearchEmployeeNo())
						+"#"+ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo());
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
	 
	private void showHtml(Connection con, PrintWriter out, overTimeVO otVo , UserDescriptor UserInformation,HttpServletRequest request) throws SQLException {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_emp_OverTime);
			employeeUserRO eo=new employeeUserRO();
			
			List<employeeUserRO> lro=getUser(con,UserInformation,request);
			otVo.setSearchEmployeeNo(lro.get(0).getID());
			otVo.setSearchEmployee(lro.get(0).getID());
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
			htmlPart1=htmlPart1.replace("<SearchUnit/>",HtmlUtil.getLabelHtml(lro.get(0).getUNIT()));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",HtmlUtil.getLabelHtml(lro.get(0).getEMPLOYEE()));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",HtmlUtil.getLabelHtml(lro.get(0).getEMPLOYEENO()));
			htmlPart1=htmlPart1.replace("<hiddenUserNo/>",ControlUtil.drawHidden(lro.get(0).getID(), "searchEmployee"));	
			htmlPart1=htmlPart1.replace("<hiddenUnit/>",ControlUtil.drawHidden(lro.get(0).getUID(), "searchUnit"));	
			htmlPart1=htmlPart1.replace("<hiddenUser/>",ControlUtil.drawHidden(lro.get(0).getID(), "searchEmployeeNo"));	
			htmlPart1=htmlPart1.replace("<rowID/>",otVo.getRowID());	
			htmlPart1=htmlPart1.replace("<hiddenEmployeeNo/>",ControlUtil.drawHidden(lro.get(0).getDID(), "searchDepartmen"));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'  AND UNIT not like '%部%'  ", otVo.getSearchUnit(),false,null));
			htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	UserInformation.getUserEmployeeNo());
			htmlPart1=htmlPart1.replace("&SearchEmployee",ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" +lro.get(0).getDEPARTMENT() + "'", otVo.getSearchEmployee()));
			htmlPart1=htmlPart1.replace("&SearchReasons",ControlUtil.drawChosenSelect(con, "searchReasons", "VN_LREASONS", "ID", "REASONS", null, otVo.getSearchReasons(),false,null));
			htmlPart1=htmlPart1.replace("&OverTimeClass",ControlUtil.drawChosenSelect(con,  "overTimeClass", "VN_TURN", "Code", "CBName", " 1=1  ", otVo.getOverTimeClass(),false,null));
		
			htmlPart1=htmlPart1.replace("&addTime",HtmlUtil.getSpinnerDiv("addTime",otVo.getAddTime(),keyConts.spinnerDayMax,keyConts.spinnerDayMin,keyConts.spinnerDayStep));
			htmlPart1=htmlPart1.replace("<TimeDiv/>",HtmlUtil.getTimeDiv("startTimeHh", "startTimemm", "endTimeHh", "endTimemm",otVo));
			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", otVo.getNote()));
			htmlPart1=htmlPart1.replace("<SPAN  id='dutiesDiv' ></SPAN>",lro.get(0).getDUTIES()) ;
			htmlPart1=htmlPart1.replace("<SPAN  id='entrydateDiv' ></SPAN>",String.valueOf(lro.get(0).getENTRYDATE())) ;
			htmlPart1=htmlPart1.replace("&submitDate", otVo.getSubmitDate());
			htmlPart1=htmlPart1.replace("&queryDate",HtmlUtil.getDateDiv("queryDate", otVo.getQueryDate()));
			htmlPart1=htmlPart1.replace("&userReason",HtmlUtil.getTextDiv("userReason",otVo.getUserReason(),"可輸入自訂加班事由" ));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			logger.info("getOvertime  :   "+SqlUtil.getOvertime(otVo));
			if(otVo.isShowDataTable()){
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
						SqlUtil.getOvertime(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageSave));
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
			
		 return otVo;
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
