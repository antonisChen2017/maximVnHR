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
import cn.com.maxim.portal.attendan.ro.exLeaveCardRO;
import cn.com.maxim.portal.attendan.ro.exStopCardRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.dao.stopWorkDAO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.UrlUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;
/**
 * 部門員工停工申請
 * @author Antonis.chen
 *
 */
public class dep_StopWorking extends TemplatePortalPen
{
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(dep_StopWorking.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
	
		String actText = request.getParameter("act");
	
		stopWorkVO swVo = new stopWorkVO(); 
		swVo.setActionURI(ActionURI);
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(swVo,request.getParameterMap()); 
					// 查询
					if (actText.equals("QUE")) {
						swVo.setShowDataTable(true);
						swVo.setSaveButText(keyConts.butSave);
						showHtml(con, out, swVo,UserInformation);
					}
					if (actText.equals("Save")) {
					      String msg=stopWorkDAO.getStopProcess(con,swVo);
						if(msg.equals("o")){
						    String stopEdit=( String)request.getSession().getAttribute("stopEdit");
							if(stopEdit.equals("Update")){
							
							       swVo.setShowDataTable(true);
								String rowID = request.getParameter("rowID");
								logger.info("Save swVo : "+swVo);
								swVo.setRowID(rowID);
								logger.info("Save swVo : "+swVo);
								logger.info("Save updateDepLeavecard : " +SqlUtil.updateStopWork(swVo));
								boolean flag =DBUtil.updateSql(SqlUtil.updateStopWork(swVo), con);
								if(flag){
								    swVo.setMsg(keyConts.editOK);
								}else{
								    swVo.setMsg(keyConts.editNO);
								}
							    
							}else{
                						swVo.setShowDataTable(true);
                						// 儲存db
                						List<employeeUserRO> lro=getUser(con,UserInformation,request);
                						swVo.setLogin(lro.get(0).getID());
                						logger.info("setLogin : " +lro.get(0).getID());
                						msg=stopWorkDAO.saveStopWorking(con,swVo );
                						swVo.setMsg(msg);
							}
						}else{
						    swVo.setMsg(keyConts.noProcessOverMsg);
						    logger.info(keyConts.noProcessOverMsg);
						}
						swVo.setSaveButText(keyConts.butSave);
						request.getSession().setAttribute("stopEdit","Save");
						showHtml(con, out,  swVo,UserInformation);
					}
					if (actText.equals("Delete")) {
						logger.info("停工申請/Delete : swVo" +swVo.toString());
						String rowID = request.getParameter("rowID");
						//delete
						boolean flag=DBUtil.delDBTableRow(SqlUtil.delDBRow("VN_STOPWORKING",rowID),con);
						swVo.setShowDataTable(true);
						if(flag){
							swVo.setMsg("刪除成功!");
						}else{
							swVo.setMsg("刪除失敗!");
						}
						swVo.setSaveButText(keyConts.butSave);
						request.getSession().setAttribute("stopEdit","Delete");
						showHtml(con, out, swVo,UserInformation);
					}
					if (actText.equals("Refer"))//提交審核
					{
						String rowID = request.getParameter("rowID");
						swVo.setRowID(rowID);
					
						logger.info("Refer swVo : "+swVo);
					
						logger.info(" Refer: " +swVo.toString());
						DBUtil.updateSql(SqlUtil.upStopStatus(keyConts.dbTableCRStatuS_T,request.getParameter("rowID"),"0"), con);
						/**20170802暫時不寄信**/
						//stopWorkDAO.deptProcessEmail(con,swVo);
						swVo.setSearchEmployeeNo("0");
						swVo.setShowDataTable(true);
						swVo.setSaveButText(keyConts.butSave);
						showHtml(con, out, swVo,UserInformation);
						
					}
					if (actText.equals("Update"))//更新
					{
						//lcVo.setSubmitDate(DateUtil.NowDateTime());
					
						String rowID = request.getParameter("rowID");
				
						swVo.setShowDataTable(true);
						swVo.setRowID(rowID);
						swVo=SharedCode(con,swVo);
						swVo.setSaveButText(keyConts.butUpdate);
						swVo.setMsg(keyConts.editStopTip);
						request.getSession().setAttribute("stopEdit","Update");
						logger.info("Update : " +swVo.toString());
						showHtml(con, out,  swVo,UserInformation);	
					}
				}else{
					//預設
					swVo.setSearchDepartmen("0");
					swVo.setSearchUnit("0");
					swVo.setSearchEmployeeNo("0");
					swVo.setSearchReasons("0");
					swVo.setSearchEmployee("0");
					swVo.setRowID("0");
					swVo.setAddDay("1");
					swVo.setStartStopWorkDate(DateUtil.NowDate());
					swVo.setEndStopWorkDate(DateUtil.NowDate());
					swVo.setStartTimemm("0");
					swVo.setStartTimeHh("0");
					swVo.setEndTimeHh("0");
					swVo.setEndTimemm("0");
					request.getSession().setAttribute("stopEdit","");
					swVo.setNote("");
					swVo.setSaveButText(keyConts.butSave);
					showHtml(con, out, swVo,UserInformation);
				
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
			 subSql=" DEPARTMENT_ID = '"+searchDepartmen + "'  ";
		 }else{
			 subSql=" UNIT_ID ='" + searchUnit + "'  ";
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
	
	private void showHtml(Connection con, PrintWriter out, stopWorkVO swVo , UserDescriptor UserInformation) throws SQLException {
		HtmlUtil hu=new HtmlUtil();
		String htmlPart1=hu.gethtml(htmlConsts.html_dep_StopWorking);
		String DepartmentID=DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo());
		String UnitSql="";
		if(swVo.getSearchDepartmen( ).equals("0")){
			UnitSql=" DEPARTMENT_ID='0' ";
		}else{
			UnitSql=" DEPARTMENT_ID= '"+swVo.getSearchDepartmen( )+"'";
		}
		htmlPart1=htmlPart1.replace("<ActionURI/>", 	swVo.getActionURI());
		htmlPart1=htmlPart1.replace("<UserDepartmen/>", 	ControlUtil.drawChosenSelect(con,  "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", null,swVo.getSearchDepartmen( ),false,null));
			
		htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", UnitSql, swVo.getSearchUnit(),false,null));
		
		htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" +	swVo.getSearchDepartmen() + "'", swVo.getSearchEmployeeNo(),false,null));
	
		htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + swVo.getSearchDepartmen()+ "'", swVo.getSearchEmployee(),false,null));
	
		htmlPart1=htmlPart1.replace("<searchReasons/>",ControlUtil.drawChosenSelect(con,  "searchReasons", "VN_STOPWORKRESON", "ID", "STOPRESON", null, swVo.getSearchReasons(),false,null));
	
		htmlPart1=htmlPart1.replace("<addDay/>",HtmlUtil.getSpinnerDiv("addDay",swVo.getAddDay(),keyConts.spinnerDayMax,keyConts.spinnerDayMin,keyConts.spinnerDayStep));
	
		htmlPart1=htmlPart1.replace("<Note/>",HtmlUtil.getNoteDiv("note", swVo.getNote()));
	
		
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(swVo.getMsg()));
		htmlPart1=htmlPart1.replace("<startTimeHh/>",HtmlUtil.getLeaveCardTimeDiv("startTimeHh",swVo.getStartTimeHh()));
		htmlPart1=htmlPart1.replace("<endTimeHh/>",HtmlUtil.getLeaveCardTimeDiv("endTimeHh",swVo.getEndTimeHh()));
		htmlPart1=htmlPart1.replace("<startTimemm/>",HtmlUtil.getLeaveCardMinuteDiv("startTimemm",swVo.getStartTimemm()));
		htmlPart1=htmlPart1.replace("<endTimemm/>",HtmlUtil.getLeaveCardMinuteDiv("endTimemm",swVo.getEndTimemm()));
		htmlPart1=htmlPart1.replace("<startStopWorkDate/>",HtmlUtil.getDateDiv("startStopWorkDate", swVo.getStartStopWorkDate()));
		htmlPart1=htmlPart1.replace("<endStopWorkDate/>",HtmlUtil.getDateDiv("endStopWorkDate", swVo.getEndStopWorkDate()));
		logger.info("saveButText : " +swVo.getSaveButText());
		htmlPart1=htmlPart1.replace("<saveButText/>",swVo.getSaveButText());	
		htmlPart1=htmlPart1.replace("<rowID/>",swVo.getRowID());	
		if(swVo.isShowDataTable()){
		    	logger.info("sql  :  "+SqlUtil.getStopWorkTable(swVo));
			htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawStopWorking(
					SqlUtil.getStopWorkTable(swVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageSave));
		}
		
	    out.println(htmlPart1);
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
		 * 共用查询區塊
	 * @throws ParseException 
		 */
		 private stopWorkVO SharedCode(Connection con,stopWorkVO swVo) throws Exception{
		     		exStopCardRO er=new exStopCardRO();
				List<exStopCardRO> ero=DBUtil.queryStopCardList(con,SqlUtil.getStopViewData(swVo.getRowID()),er);
				logger.info("getBeLeaveCard : " +SqlUtil.getBeLeaveCard(swVo.getRowID()));
			
				swVo.setRowID(ero.get(0).getID());
				String [] StartsLeaves = ero.get(0).getSTARTSTOPDATE().split("\\s+");
				swVo.setStartStopWorkDate(StartsLeaves[0].replace("-", "/"));
				String [] StartsLeavess =StartsLeaves[1].split(":");
				swVo.setStartTimeHh(StartsLeavess[0]);
				swVo.setStartTimemm(StartsLeavess[1]);
			
				String [] EndLeaves = ero.get(0).getENDENDDATE().split("\\s+");
				swVo.setEndStopWorkDate(EndLeaves[0].replace("-", "/"));
				String [] EndLeavess =EndLeaves[1].split(":");
				swVo.setEndTimeHh(EndLeavess[0]);
				swVo.setEndTimemm(EndLeavess[1]);
				
				swVo.setAddDay( ero.get(0).getDAYCOUNT());
				
				swVo.setNote( ero.get(0).getNOTE());
				swVo.setSearchEmployee(ero.get(0).getEMPLOYEE());
				swVo.setSearchEmployeeNo(ero.get(0).getEMPLOYEENO());
				swVo.setSearchDepartmen(ero.get(0).getDEPARTMENT());
				swVo.setSearchUnit(ero.get(0).getUNIT());
				swVo.setSearchReasons(ero.get(0).getREASON_ID());
			 return swVo;
		 }
}
