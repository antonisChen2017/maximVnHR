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
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;
/**
 * 人事 超時加班登錄
 * @author Antonis.chen
 *
 */
public class rev_ExceedOvertime extends TemplatePortalPen
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
						logger.info("超時人員查詢  : ");
						otVo.setShowExOverTimeData(true);
						otVo.setSearchDepartmen("");
						otVo.setSearchUnit("");
						otVo.setRowID("");
						otVo.setSearchReasons("");
						otVo.setSearchEmployee("");
						otVo.setSearchEmployeeNo("");
						showHtml(con, out, otVo,UserInformation);
					}
					
					if (actText.equals("Save")) {
						String rowID = request.getParameter("rowID");
						otVo = DBUtil.queryExOverTimeC(con,otVo);
						logger.info("超時加班Save  : ");
						otVo.setShowDataTable(true);
						otVo.setStatus(keyConts.dbTableRD);
						// 儲存db
						String msg= DBUtil.saveExceedOvertime(otVo,con);
						otVo.setMsg(msg);
						showHtml(con, out,  otVo,UserInformation);	
					}
					
					if (actText.equals("Edit")) {
						String rowID = request.getParameter("rowID");
						otVo.setRowID(rowID);
						logger.info("超時加班Update  : " +rowID);
						otVo.setShowDataTable(true);
						// 儲存db
						otVo = DBUtil.queryExOverTimeC(con,otVo);
					
						showHtml(con, out,  otVo,UserInformation);	
					}
					if (actText.equals("PL")) {//編輯时间
						String rowID = request.getParameter("rowID");
						logger.info("超時加班更新时间 : rowID" +rowID);
						otVo.setRowID(rowID);
					
						otVo=SharedCode(con,otVo);
						otVo.setShowDataTable(true);
						showHtml(con, out,  otVo,UserInformation);	
					}
					if (actText.equals("Delete")) {
						logger.info("超時更新Delete  : ");
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
						DBUtil.updateTimeOverSStatus(keyConts.dbTableCRStatuS_T, request.getParameter("rowID"), con);
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
					otVo.setAddTime("1");
					otVo.setStartTimeHh("0");
					otVo.setStartTimemm("0");
					otVo.setEndTimeHh("0");
					otVo.setEndTimemm("0");
					otVo.setNote("");
					otVo.setRowID("");
					otVo.setQueryDate(DateUtil.NowDate());
					 otVo.setStartQueryDate(DateUtil.NowDate());
					 otVo.setEndQueryDate(DateUtil.NowDate());
					otVo.setUserReason("");
					otVo.setQueryYearMonth(DateUtil.getSysYearMonth());
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
			String htmlPart1=hu.gethtml(htmlConsts.html_rev_ExceedOvertime);
			employeeUserRO eo=new employeeUserRO();
			logger.info("getEmployeeNODate : "+SqlUtil.getEmployeeNODate(otVo.getSearchEmployeeNo()));
			List<employeeUserRO> lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNODate(otVo.getSearchEmployeeNo()) ,eo);	
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
			htmlPart1=htmlPart1.replace("<applicationDate/>",HtmlUtil.getDateDivSw("startQueryDate","endQueryDate", otVo.getStartQueryDate(),otVo.getEndQueryDate()));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",otVo.getSearchUnit());
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",otVo.getSearchEmployeeNo());
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",otVo.getSearchEmployee());
			htmlPart1=htmlPart1.replace("&SearchReasons",ControlUtil.drawChosenSelect(con,  "searchReasons", "VN_LREASONS", "ID", "REASONS", null, otVo.getSearchReasons(),false,null));
		//	htmlPart1=htmlPart1.replace("&OverTimeClass",HtmlUtil.getOverTimeClass( otVo));
			htmlPart1=htmlPart1.replace("&userReason",HtmlUtil.getTextDiv("userReason",otVo.getUserReason(),"可輸入自訂加班事由" ));
			htmlPart1=htmlPart1.replace("<Dept/>",otVo.getSearchDepartmen());
			htmlPart1=htmlPart1.replace("&OverTimeClass",ControlUtil.drawChosenSelect(con,  "overTimeClass", "VN_TURN", "Code", "CBName", " 1=1  ", otVo.getOverTimeClass(),false,null));
		
			htmlPart1=htmlPart1.replace("&addTime",HtmlUtil.getSpinnerDiv("addTime",otVo.getAddTime(),keyConts.spinnerDayMax,keyConts.spinnerDayMin,keyConts.spinnerDayStep));
			htmlPart1=htmlPart1.replace("<TimeDiv/>",HtmlUtil.getTimeDiv("startTimeHh", "startTimemm", "endTimeHh", "endTimemm",otVo));
			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", otVo.getNote()));
			htmlPart1=htmlPart1.replace("&submitDate",otVo.getSubmitDate());
			htmlPart1=htmlPart1.replace("&queryDate",HtmlUtil.getDateDiv("queryDate", otVo.getQueryDate()));
			htmlPart1=htmlPart1.replace("<SearchReasons/>",otVo.getSearchReasons());
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			htmlPart1=htmlPart1.replace("<rowID/>",otVo.getRowID());
			htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getYearMonthDiv("queryYearMonth",otVo.getQueryYearMonth()));
			if(!otVo.getSearchEmployeeNo().equals("")){
				htmlPart1=htmlPart1.replace("<SPAN  id='dutiesDiv' ></SPAN>",lro.get(0).getDUTIES()) ;
				htmlPart1=htmlPart1.replace("<SPAN  id='entrydateDiv' ></SPAN>",String.valueOf(lro.get(0).getENTRYDATE())) ;
				}
			if(otVo.isShowDataTable()){
				logger.info("isShowDataTable ");
				logger.info(" get getExceedOvertimeC  :   "+SqlUtil.getExceedOvertimeC(otVo));
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
						SqlUtil.getExceedOvertimeC(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.personnelList));
				htmlPart1=htmlPart1.replace("<Status/>","true");
			}
			if(otVo.isShowExOverTimeData()){
				//logger.info("isShowExOverTimeData ");
				//logger.info("getrev_ExceedOvertime  :   "+SqlUtil.getrev_Condition(otVo));
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawTableCondition(
						SqlUtil.getrev_Condition(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.personnelList));
				htmlPart1=htmlPart1.replace("<Status/>","true");
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
			otVo.setSearchDepartmen(ero.get(0).getDEPARTMENT());
			otVo.setSearchUnit(ero.get(0).getUNIT());
			otVo.setRowID(ero.get(0).getID());
			otVo.setSearchReasons(ero.get(0).getUSERREASONS());
			otVo.setSearchEmployee(ero.get(0).getEMPLOYEE());
			otVo.setSearchEmployeeNo(ero.get(0).getEMPLOYEENO());
			
		 return otVo;
	 }

}
