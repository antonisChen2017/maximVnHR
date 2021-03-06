package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.htmlDBcontrol.WebDBSelect;
import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
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
 * 加班 部门主管審核
 * @author Antonis.chen
 *
 */
public class dem_InspectOvertime extends TemplatePortalPen {
	static WebDBSelect APSelector;
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(dem_InspectOvertime.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con) 
	{
		String actText = request.getParameter("act");
		overTimeVO otVo = new overTimeVO(); 
		otVo.setActionURI(ActionURI);
		try
		{
				if (actText != null)
				{		
					BeanUtils.populate(otVo,request.getParameterMap()); 
					// 查询UI
					otVo.setSearchReasons("0");
					otVo.setMonthOverTime("0");//查询有無超過时间
					if (actText.equals("QUE")) {
					
						//setHtmlPart1(con, out, otVo,UserInformation);
					
						// 輸出查询UI
						//out.write(HtmlUtil.drawTableS(
						//		SqlUtil.getOvertimeNoSave(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageInspect));
						otVo.setShowDataTable(true);
						setHtmlPart(con, out, otVo,UserInformation,request);
					}
					if (actText.equals("D")) {
			
						logger.info("加班申請單 部门主管審核/D : " + otVo.toString());
						otVo.setLeaveApply("2");
						otVo.setStatus("UD");
						DBUtil.updateTimeOverSStatus(otVo, con);
						otVo.setShowDataTable(true);
						setHtmlPart(con, out, otVo,UserInformation,request);
					}
					if (actText.equals("U")) {
						
						logger.info("加班申請單 部门主管審核/U : " + otVo.toString());
						otVo.setStatus("D");
						otVo.setMsg(overTimeDAO.deptProcess(con, otVo));
						otVo.setShowDataTable(true);
						// 輸出查询UI
						setHtmlPart(con, out, otVo,UserInformation,request);
					}
					
					if (actText.equals("R")) {
						logger.info("加班申請單 審核/R: " +otVo.toString());		
						otVo.setLeaveApply("2");
						otVo.setStatus("DR");
						DBUtil.updateTimeOverSStatus(otVo, con);
						DBUtil.updateSql(SqlUtil.setStatusReturnMsg(otVo.getReturnMsg(), request.getParameter("rowID")), con);
						//setHtmlPart1(con, out, otVo,UserInformation);
				
						// 輸出查询UI
					//	out.write(HtmlUtil.drawTableS(
							//	SqlUtil.getOvertimeNoSave(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageInspect));
						otVo.setShowDataTable(true);
						setHtmlPart(con, out, otVo,UserInformation,request);
					}
					if (actText.equals("U")) {
						
						logger.info("加班申請單 部门主管審核/U : " + otVo.toString());
						otVo.setStatus("D");
						otVo.setMsg(overTimeDAO.deptProcess(con, otVo));
						otVo.setShowDataTable(true);
						// 輸出查询UI
						setHtmlPart(con, out, otVo,UserInformation,request);
					}
					if (actText.equals("ALL")) {
						logger.info("加班全部通過");

						otVo.setShowDataTable(true);
						otVo.setStatus("D");//審核通過
						// 儲存db
						//檢查是否已經權限走到底
						otVo.setMsg(overTimeDAO.deptAllProcess(con, otVo,request.getParameter("rowID")));
						setHtmlPart(con, out, otVo,UserInformation,request);
					}

					
				}else{
					otVo.setSearchDepartmen("0");
					otVo.setSearchEmployeeNo("0");
					otVo.setSearchEmployee("0");
					otVo.setStartSubmitDate(DateUtil.NowDate());
					otVo.setEndSubmitDate(DateUtil.NowDate());
					otVo.setStartQueryDate(DateUtil.NowDate());
					otVo.setEndQueryDate(DateUtil.NowDate());
					otVo.setQueryDate(DateUtil.NowDate());
					setHtmlPart(con, out,otVo,UserInformation,request);
				
				}
		}catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
	 
		
	}
	
	
 public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con){
		 
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
				out.println(ControlUtil.drawChosenSelect(con, "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployee(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
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
	 }
	
	private String drawSelectShared(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String Filter, String SelectedOption) throws SQLException
	{

		APSelector = new WebDBSelect(name, tableName, valueField, DisplayField, Filter);
		APSelector.setClass("select2_category form-control");
		APSelector.setID(name);
		APSelector.refreshOptions(con);
		APSelector.addOption("0", "未選擇");
		APSelector.setSelectedOption(SelectedOption);
		//APSelector.writeHTML(out);
		return APSelector.toString();
	}
	
	private void setHtmlPart(Connection con, PrintWriter out, overTimeVO otVo,UserDescriptor UserInformation,HttpServletRequest request) throws Exception {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_dem_InspectOvertime);

			List<employeeUserRO> lro=getUser(con,UserInformation,request);
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
		//	htmlPart1=htmlPart1.replace("<hiddenDepartmen/>",ControlUtil.drawHidden(lro.get(0).getDID(), "searchDepartmen"));	
			htmlPart1=htmlPart1.replace("<UserDepartmen/>", 	ControlUtil.drawChosenSql(con,  "searchDepartmen",SqlUtil.querySelectOverDept(lro.get(0).getEMPLOYEENO()), otVo.getSearchDepartmen(),keyConts.msgS));	
			htmlPart1=htmlPart1.replace("<applicationDate/>",HtmlUtil.getDateDivSw("startSubmitDate","endSubmitDate", otVo.getStartSubmitDate(),otVo.getEndSubmitDate()));
		    htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
		//	logger.info("  lro.get(0).getDID() : "+ lro.get(0).getDID());
			htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,  "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + lro.get(0).getDID()+ "'    ", otVo.getSearchUnit(),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "UNIT_ID='" +lro.get(0).getUID()+ "'", otVo.getSearchEmployeeNo(),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con, "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "UNIT_ID='" +lro.get(0).getUID()+ "'", otVo.getSearchEmployee(),false,null));
			htmlPart1=htmlPart1.replace("<Userdata/>",HtmlUtil.getLabel6Html(DBUtil.queryDBField(con,SqlUtil.queryChargeName(lro.get(0).getEMPLOYEENO()),"EMPLOYEE")));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			if(otVo.isShowDataTable()){

				logger.info(" getOvertimeNoSave : "+SqlUtil.getOvertimeDept(otVo,lro.get(0).getEMPLOYEENO()));
				htmlPart1 = htmlPart1.replace("<drawTableM/>", HtmlUtil.drawOvertimeTable(
						SqlUtil.getOvertimeDept(otVo,lro.get(0).getEMPLOYEENO()),HtmlUtil.drawTableMcheckButton(),  con, out, keyConts.pageDtmList));
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