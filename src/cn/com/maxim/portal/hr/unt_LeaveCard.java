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
import cn.com.maxim.portal.dao.leaveCardDAO;
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
 * 請假卡 单位主管審核
 * @author Antonis.chen
 *
 */
public class unt_LeaveCard extends TemplatePortalPen
{
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(unt_LeaveCard.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		 Log4jUtil lu=new Log4jUtil();
		 Logger logger  =lu.initLog4j(unt_LeaveCard.class);
		String actText = request.getParameter("act");
		//String us =this.getDBAttribute("us");//取管理功能設定屬性
		leaveCardVO lcVo = new leaveCardVO(); 
		lcVo.setActionURI(ActionURI);
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
					//請假退回
					if (actText.equals("UR")) {
						
						logger.info("单位主管審核/請假退回 : " +lcVo.toString());
						lcVo.setStatus(actText);
						lcVo.setLeaveApply("2");//2退回
						logger.info("单位主管審核//請假退回 : updateLcStatus#sql" +SqlUtil.updateLcStatus(lcVo));
						if(DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)){
							lcVo.setMsg(keyConts.returnMsg);
						}
				
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation,request);
					}
					//单位主管通過
					if (actText.equals("U")) {
				
						logger.info("单位主管審核/单位主管通過 : " +lcVo.toString());
						lcVo.setShowDataTable(true);
						lcVo.setStatus(actText);
						// 儲存db
						//檢查是否已經權限走到底
						lcVo.setMsg(leaveCardDAO.deptProcess(con, lcVo));				
						showHtml(con, out, lcVo,UserInformation,request);
					}
					if (actText.equals("ALL")) {
						logger.info("請假全部通過");

						lcVo.setShowDataTable(true);
						lcVo.setStatus("U");
						// 儲存db
						//檢查是否已經權限走到底
						lcVo.setMsg(leaveCardDAO.deptAllProcess(con, lcVo,request.getParameter("rowID")));
						showHtml(con, out, lcVo,UserInformation,request);
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
					showHtml(con, out, lcVo,UserInformation,request);
				
				}
		}catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		
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
		
		
		 if(ajax.equals("duties")){
			  String employeeID = request.getParameter("employeeID");
			  String entryDate= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.ENTRYDATE "),"ENTRYDATE");
			  String duties= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.DUTIES "),"DUTIES");
			  String data= "[{ \"duties\":\""+duties+"\" , \"entryDate\":\""+entryDate+"\" }]";
			// System.out.println("TimeDiv :"+TimeDiv);
			  out.println(data);
	 	}
		 if(ajax.equals("SwUnitEmpNo")){
			 String searchUnit = request.getParameter("searchUnit");
			 overTimeVO otVo = new overTimeVO(); 
			 otVo.setSearchEmployeeNo("0");
			 try
				{
				 out.println(ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "UNIT_ID='" + searchUnit + "'", otVo.getSearchEmployeeNo(),false,null));
				}
				catch (SQLException e)
				{
					out.println("");
				}
		 }
		 if(ajax.equals("SwUnitEmp")){
			 String searchUnit = request.getParameter("searchUnit");
			 overTimeVO otVo = new overTimeVO(); 
			 otVo.setSearchEmployeeNo("0");
			 try
				{
				 out.println(ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "UNIT_ID='" + searchUnit + "'", otVo.getSearchEmployeeNo(),false,null));
				}
				catch (SQLException e)
				{
					out.println("");
				}
		 }
		 if(ajax.equals("SwEmpNo")){
			 String searchDepartmen = request.getParameter("searchDepartmen");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setSearchEmployeeNo("0");
			try
			{
				out.println(ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployeeNo(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
	 	}
		 if(ajax.equals("SwEmp")){
			String searchDepartmen = request.getParameter("searchDepartmen");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setSearchEmployee("0");
			try
			{
				out.println(ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployee(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
	 	}
	 }
	
	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo , UserDescriptor UserInformation,HttpServletRequest request) throws Exception {
		HtmlUtil hu=new HtmlUtil();
		employeeUserRO eo=new employeeUserRO();
		
		List<employeeUserRO> lro=getUser(con,UserInformation,request);
		String UnitSql="";
		UnitSql=" DEPARTMENT_ID= '"+lro.get(0).getDID()+"'";
		
		String htmlPart1=hu.gethtml(htmlConsts.html_unt_LeaveCard);
	
		htmlPart1=htmlPart1.replace("<ActionURI/>", 	lcVo.getActionURI());
	//	htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", UnitSql , lcVo.getSearchUnit(),false,null));
		//htmlPart1=htmlPart1.replace("<hiddenUnit/>",ControlUtil.drawHidden(lro.get(0).getUID(), "searchUnit"));	
		htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + lro.get(0).getDID()+ "'    ", lcVo.getSearchUnit(),false,null));
	//	htmlPart1=htmlPart1.replace("<hiddenDepartmen/>",ControlUtil.drawHidden(lro.get(0).getDID(), "searchDepartmen"));	
		htmlPart1=htmlPart1.replace("<UserDepartmen/>",ControlUtil.drawChosenSql(con,  "searchDepartmen",SqlUtil.querySelectDept(lro.get(0).getEMPLOYEENO()), lcVo.getSearchDepartmen(),keyConts.msgS));	
		htmlPart1=htmlPart1.replace("<applicationDate/>",HtmlUtil.getDateDivSw("startLeaveDate","endLeaveDate", lcVo.getStartLeaveDate(),lcVo.getEndLeaveDate()));
		htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "UNIT_ID='" +lro.get(0).getUID()+ "'", lcVo.getSearchEmployeeNo(),false,null));
		htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con, "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "UNIT_ID='" +lro.get(0).getUID()+ "'", lcVo.getSearchEmployee(),false,null));
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(lcVo.getMsg()));
		
		htmlPart1=htmlPart1.replace("<Userdata/>",HtmlUtil.getLabel6Html(DBUtil.queryDBField(con,SqlUtil.queryChargeName(lro.get(0).getEMPLOYEENO()),"EMPLOYEE")));
	
		if(lcVo.isShowDataTable()){
			logger.info("getUnitLeaveCard sql :"+SqlUtil.getUnitLeaveCard(lcVo,lro.get(0).getEMPLOYEENO()));
			
			htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawLeaveCardTable(
					SqlUtil.getUnitLeaveCard(lcVo,lro.get(0).getEMPLOYEENO()),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageUsList));
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
}

