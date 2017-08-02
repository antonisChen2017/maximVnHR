package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
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
import cn.com.maxim.portal.dao.overTimeDAO;
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
						request.getSession().setAttribute("CSEdit","QUE");
						otVo.setSaveButText(keyConts.butSave);
						showHtml(con, out, otVo,UserInformation,actText);
					}
					
					if (actText.equals("Save")) {
					    
						String rowID = request.getParameter("rowID");
						/**
						 *  查詢是否當天申請 當天申請超過12點半不能填寫 當天一點發送超時加班信
						 *  申請之前之後都是四點發送超時加班信
						 */
						String timeValue=overTimeDAO.CSSaveTimeCheck(otVo);
						if(timeValue.equals("2")){
						    	otVo.setMsg(keyConts.noTimeCSMsg);
							logger.info(keyConts.noTimeCSMsg);
							
						}else{
        						List<employeeUserRO> lro=getUser(con,UserInformation,request);
        						otVo.setLogin(lro.get(0).getID());
        						// 查詢有無設定流程
        						String msg=DBUtil.getCSProcess(con,otVo);
        						
        						if(msg.equals("o")){
        							
        							String CSEdit=( String)request.getSession().getAttribute("CSEdit");
        							logger.info("isEmpty eotEdit:"+CSEdit);
        							if(CSEdit.isEmpty()){
        								//logger.info("isEmpty eotEdit:"+CSEdit);
        							}else if(CSEdit.equals("Update")){
        								logger.info("updateEmpOverTime : " +SqlUtil.updateCSOverTime(otVo));
        								boolean flag =DBUtil.updateSql(SqlUtil.updateCSOverTime(otVo), con);
        								if(flag){
        									otVo.setMsg(keyConts.editOK);
        								}else{
        									otVo.setMsg(keyConts.editNO);
        								}
        							}else{
        							        otVo.setStatus(keyConts.dbTableRS);
        							        otVo.setEMAIL_STATUS(timeValue);
        							        msg= DBUtil.saveExceedMaxOverTime(otVo,con);
        								otVo.setMsg(msg);
        							}
        							
        						}else{
        							otVo.setMsg(keyConts.noProcessOverMsg);
        							logger.info(keyConts.noProcessOverMsg);
        						
        						}
						}
						otVo = DBUtil.queryExOverTimeC(con,otVo);
						otVo.setShowDataTable(true);
						otVo.setStatus(keyConts.dbTableUT);
						otVo.setRowID(rowID);
						
						// 儲存db
						otVo.setSaveButText(keyConts.butSave);
						request.getSession().setAttribute("CSEdit","Save");
						showHtml(con, out,  otVo,UserInformation,actText);
					}
					
					if (actText.equals("Update")) {
						String rowID = request.getParameter("rowID");
						otVo.setRowID(rowID);
						logger.info("超時加班Update  : " +rowID);
						otVo.setShowDataTable(true);
						// 儲存db
						otVo=SharedCodeDown(con,otVo);
						otVo.setSaveButText(keyConts.butUpdate);
						request.getSession().setAttribute("CSEdit","Update");
						showHtml(con, out,  otVo,UserInformation,actText);
					}
					if (actText.equals("Edit")) {
					         String empno = request.getParameter("rowID");
						logger.info("超時更新Edit  : "+empno);
						
						
						otVo.setSearchEmployeeNo(empno);
						otVo.setShowDataTable(true);
						// 儲存db
						otVo = DBUtil.queryExOverTimeC(con,otVo);
						otVo.setSaveButText(keyConts.butSave);
						request.getSession().setAttribute("CSEdit","Edit");
						showHtml(con, out,  otVo,UserInformation,actText);
					}
					if (actText.equals("queryOK")) {//已申請完成 CS加班名單
						String rowID = request.getParameter("rowID");
						logger.info("queryOK : rowID" +rowID);
						//otVo.setRowID(rowID);
					
						//otVo=SharedCode(con,otVo);
						otVo.setShowfulfillData(true);
						otVo.setSearchDepartmen("");
						otVo.setSearchUnit("");
						otVo.setRowID("");
						otVo.setSearchReasons("");
						otVo.setSearchEmployee("");
						otVo.setSearchEmployeeNo("");
						
						showHtml(con, out,  otVo,UserInformation,actText);
					}
					if (actText.equals("Delete")) {
					        String rowID = request.getParameter("rowID");
						logger.info("超時更新Delete  : "+rowID);
						
						DBUtil.delDBTableRow(SqlUtil.deleteSecret(rowID), con);
						otVo.setShowDataTable(true);
						otVo = DBUtil.querySecretData(con,otVo);
						otVo.setMsg("已刪除");
						otVo.setSaveButText(keyConts.butSave);
						request.getSession().setAttribute("CSEdit","Delete");
						showHtml(con, out, otVo,UserInformation,actText);
					}
					if (actText.equals("Refer"))//送交
					{
					    	String rowID = request.getParameter("rowID");
						logger.info("超時更新Delete rowID : "+rowID);
						logger.info("超時更新Delete  otVo: "+otVo.toString());
						otVo.setLeaveApply("0");
						otVo.setStatus(keyConts.dbTableRT);
						//儲存
						DBUtil.updateCSstatus(otVo, con);
						otVo = DBUtil.querySecretData(con,otVo);
						otVo.setRowID(rowID);
						otVo.setShowDataTable(true);
						otVo.setMsg("已送交");
						otVo.setSaveButText(keyConts.butSave);
						request.getSession().setAttribute("CSEdit","Refer");
						showHtml(con, out, otVo,UserInformation,actText);
						
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
					otVo.setQueryDateTwo(DateUtil.NowDate());
					otVo.setStartQueryDate(DateUtil.NowDate());
				        otVo.setEndQueryDate(DateUtil.NowDate());
					otVo.setUserReason("");
					otVo.setQueryYearMonth(DateUtil.getSysYearMonth());
					otVo.setSaveButText(keyConts.butSave);
					request.getSession().setAttribute("CSEdit","QUE");
					showHtml(con, out, otVo,UserInformation,actText);
				
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

	private void showHtml(Connection con, PrintWriter out, overTimeVO otVo , UserDescriptor UserInformation,String actText) throws Exception {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_rev_ExceedOvertime);
			employeeUserRO eo=new employeeUserRO();
			
			
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
			htmlPart1=htmlPart1.replace("<applicationDate/>",HtmlUtil.getDateDivSw("startQueryDate","endQueryDate", otVo.getStartQueryDate(),otVo.getEndQueryDate()));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",otVo.getSearchUnit()+HtmlUtil.getHidden("searchUnit",otVo.getSearchUnit()));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",otVo.getSearchEmployeeNo()+HtmlUtil.getHidden("searchEmployeeNo",otVo.getSearchEmployeeNo()));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",otVo.getSearchEmployee()+HtmlUtil.getHidden("searchEmployee",otVo.getSearchEmployee()));
			htmlPart1=htmlPart1.replace("&SearchReasons",ControlUtil.drawChosenSelect(con,  "searchReasons", "VN_LREASONS", "ID", "REASONS", null, otVo.getSearchReasons(),false,null));
		//	htmlPart1=htmlPart1.replace("&OverTimeClass",HtmlUtil.getOverTimeClass( otVo));
			htmlPart1=htmlPart1.replace("&userReason",HtmlUtil.getTextDiv("userReason",otVo.getUserReason(),"可輸入自訂加班事由" ));
			htmlPart1=htmlPart1.replace("<Dept/>",otVo.getSearchDepartmen()+HtmlUtil.getHidden("searchDepartmen",otVo.getSearchDepartmen()));
			htmlPart1=htmlPart1.replace("&OverTimeClass",ControlUtil.drawChosenSelect(con,  "overTimeClass", "VN_TURN", "Code", "CBName", " 1=1  ", otVo.getOverTimeClass(),false,null));
		
			htmlPart1=htmlPart1.replace("&addTime",HtmlUtil.getSpinnerDiv("addTime",otVo.getAddTime(),keyConts.spinnerDayMax,keyConts.spinnerDayMin,keyConts.spinnerDayStep));
			htmlPart1=htmlPart1.replace("<TimeDiv/>",HtmlUtil.getTimeDiv("startTimeHh", "startTimemm", "endTimeHh", "endTimemm",otVo));
			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", otVo.getNote()));
			htmlPart1=htmlPart1.replace("&submitDate",otVo.getSubmitDate());
			htmlPart1=htmlPart1.replace("<queryDateTwo/>",HtmlUtil.getDateDivSw("queryDate","queryDateTwo", otVo.getQueryDate(),otVo.getQueryDateTwo()));
			htmlPart1=htmlPart1.replace("<SearchReasons/>",otVo.getSearchReasons());
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			htmlPart1=htmlPart1.replace("<rowID/>",otVo.getRowID());
			htmlPart1=htmlPart1.replace("<saveButText/>",otVo.getSaveButText());
			
			if(actText!=null && actText.equals("Edit")){
			    htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getLabelHtml(otVo.getQueryYearMonth())+HtmlUtil.getHidden("queryYearMonth",otVo.getQueryYearMonth()));
			}if(actText!=null && actText.equals("QUE")){
			    htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getLabelHtml(otVo.getQueryYearMonth())+HtmlUtil.getHidden("queryYearMonth",otVo.getQueryYearMonth()));
			}if(actText!=null && actText.equals("Save")){
			    htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getLabelHtml(otVo.getQueryYearMonth())+HtmlUtil.getHidden("queryYearMonth",otVo.getQueryYearMonth()));
			}if(actText!=null && actText.equals("Delete")){
			    htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getLabelHtml(otVo.getQueryYearMonth())+HtmlUtil.getHidden("queryYearMonth",otVo.getQueryYearMonth()));
			}if(actText!=null && actText.equals("Refer")){
			    htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getLabelHtml(otVo.getQueryYearMonth())+HtmlUtil.getHidden("queryYearMonth",otVo.getQueryYearMonth()));
			}if(actText!=null && actText.equals("Update")){
			    htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getLabelHtml(otVo.getQueryYearMonth())+HtmlUtil.getHidden("queryYearMonth",otVo.getQueryYearMonth()));
			}else{
			    htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getYearMonthDiv("queryYearMonth",otVo.getQueryYearMonth()));
			}
			//顯示工號待出直稱 到職日
			if(!otVo.getSearchEmployeeNo().equals("")){
				logger.info("getEmployeeNODate : "+SqlUtil.getEmployeeNODate(otVo.getSearchEmployeeNo()));
				List<employeeUserRO> lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNODate(otVo.getSearchEmployeeNo()) ,eo);	
				htmlPart1=htmlPart1.replace("<SPAN  id='dutiesDiv' ></SPAN>",lro.get(0).getDUTIES()) ;
				htmlPart1=htmlPart1.replace("<SPAN  id='entrydateDiv' ></SPAN>",String.valueOf(lro.get(0).getENTRYDATE())) ;
				}
			
			//顯示申請中加班單
			if(otVo.isShowDataTable()){
				logger.info("isShowDataTable ");
				logger.info(" get getExceedOvertimeC  :   "+SqlUtil.querySecretOverTime(otVo));
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
						SqlUtil.querySecretOverTime(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.personnelList));
				htmlPart1=htmlPart1.replace("<Status/>","true");
			}
			//可申請超時名單
			if(otVo.isShowExOverTimeData()){
				logger.info("isShowExOverTimeData ");
				logger.info("getrev_Condition  :   "+SqlUtil.getrev_Condition(otVo));
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawTableCondition(
						SqlUtil.getrev_Condition(otVo),"",  con, out,keyConts.personnelList));
				htmlPart1=htmlPart1.replace("<Status/>","true");
			}
			
			//顯示審核流程完成CS加班名單
			if(otVo.isShowfulfillData()){
			    logger.info("isShowfulfillData ");
				logger.info(" get getExceedOvertimeC  :   "+SqlUtil.querySecretOverTime(otVo));
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
						SqlUtil.querySecretOverTime(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.personnelList));
				htmlPart1=htmlPart1.replace("<Status/>","true");
			}
			
		    out.println(htmlPart1);
	}
	/**
	 * 共用查询區塊部門單位等
	 */
	 private overTimeVO SharedCode(Connection con,overTimeVO otVo){
		    exOvertimeRO er=new exOvertimeRO();
			List<exOvertimeRO> ero=DBUtil.queryExOvertimeList(con,SqlUtil.getExOvertimeRO(otVo.getRowID()),er);
			logger.info("SharedCode : " +SqlUtil.getExOvertimeRO(otVo.getRowID()));
			otVo.setSearchDepartmen(ero.get(0).getDEPARTMENT());
			otVo.setSearchUnit(ero.get(0).getUNIT());
			otVo.setRowID(ero.get(0).getID());
			otVo.setSearchReasons(ero.get(0).getUSERREASONS());
			otVo.setSearchEmployee(ero.get(0).getEMPLOYEE());
			otVo.setSearchEmployeeNo(ero.get(0).getEMPLOYEENO());
			
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
				UserName=UserInformation.getUserTelephone();
			}
			logger.info(" sql getEmployeeNameDate="+SqlUtil.getEmployeeNODate(UserName));
			List<employeeUserRO> lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNODate(UserName) ,eo);	
			return lro;
	 }
	 
	 
		/**
		 * 共用查询區塊(包含下拉)
		 * @throws ParseException 
		 */
		 private overTimeVO SharedCodeDown(Connection con,overTimeVO otVo) throws Exception{
			    exOvertimeRO er=new exOvertimeRO();
				List<exOvertimeRO> ero=DBUtil.queryExOvertimeList(con,SqlUtil.queryCSRowEditData(otVo),er);
				logger.info("SharedCodeDown : " +SqlUtil.queryCSRowEditData(otVo));
			
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
