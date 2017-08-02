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
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.dao.leaveCardDAO;
import cn.com.maxim.portal.dao.overTimeDAO;
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
 * 經理 加班查询
 * @author Antonis.chen
 *
 */
public class mgr_OverTime extends TemplatePortalPen
{
		
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(mgr_OverTime.class);
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
						otVo.setMonthOverTime("1");//查询有無超過时间
						if (actText.equals("QUE")) {
							otVo.setShowDataTable(true);
							otVo.setStatus("I");
							otVo.setMonthOverTime("0");
							setHtmlPart1(con, out, otVo,UserInformation,request);
					
						}
						if (actText.equals("NUE")) {
							otVo.setShowDataTable(true);
							otVo.setStatus("U");
							otVo.setMonthOverTime("0");
							setHtmlPart1(con, out, otVo,UserInformation,request);
						}
						if (actText.equals("U")) {
				    		logger.info("超時加班時數/I : " +otVo.toString());				
				    			otVo.setStatus("L");//審核通過
							otVo.setMsg(overTimeDAO.deptProcess(con, otVo));		
							otVo.setShowDataTable(true);
							otVo.setStatus("U");//查詢用
							setHtmlPart1(con, out, otVo,UserInformation,request);
						
						}
						if (actText.equals("ALL")) {
							logger.info("加班全部通過");

							otVo.setShowDataTable(true);
							otVo.setStatus("L");//審核通過
							// 儲存db
							//檢查是否已經權限走到底
							otVo.setMsg(overTimeDAO.deptAllProcess(con, otVo,request.getParameter("rowID")));
							otVo.setStatus("D");
							setHtmlPart1(con, out, otVo,UserInformation,request);
						}
						
						if (actText.equals("R")) {
							
					    	logger.info("超時加班時數/R: " +otVo.toString());		
					    	otVo.setStatus("LR");
					    	otVo.setLeaveApply("2");
							DBUtil.updateTimeOverSStatus(otVo, con);
							DBUtil.updateSql(SqlUtil.setStatusReturnMsg(otVo.getReturnMsg(), request.getParameter("rowID")), con);
							
							otVo.setShowDataTable(true);
							otVo.setStatus("LR");
							setHtmlPart1(con, out, otVo,UserInformation,request);
						
						}
					}else{
					//	System.out.println("sql : "+SqlUtil.getDeptID(UserInformation.getUserEmployeeNo()));
						otVo.setSearchDepartmen("0");
						otVo.setSearchEmployeeNo("0");
						otVo.setSearchEmployee("0");
						otVo.setStartSubmitDate(DateUtil.NowDate());
						otVo.setEndSubmitDate(DateUtil.NowDate());
					    otVo.setStartQueryDate(DateUtil.NowDate());
						otVo.setEndQueryDate(DateUtil.NowDate());
						otVo.setQueryDate(DateUtil.NowDate());
						otVo.setStatus("U");
						otVo.setMonthOverTime("0");//查询有無超過时间
						otVo.setShowDataTable(true);
						setHtmlPart1(con, out,otVo,UserInformation,request);
					
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
					out.println(ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployee(),false,null));
				}
				catch (SQLException e)
				{
					out.println("");
				}
		 	}
		 }
		
		
		
		private void setHtmlPart1(Connection con, PrintWriter out, overTimeVO otVo,UserDescriptor UserInformation,HttpServletRequest request) throws Exception {
			
				List<employeeUserRO> lro=getUser(con,UserInformation,request);
				HtmlUtil hu=new HtmlUtil();
				String htmlPart1=hu.gethtml(htmlConsts.html_mgr_OverTime);
				htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
				htmlPart1=htmlPart1.replace("&SearchDepartmen", ControlUtil.drawChosenSql(con,  "searchDepartmen",SqlUtil.querySelectOverDept(lro.get(0).getEMPLOYEENO()), otVo.getSearchDepartmen(),keyConts.msgS));	
				htmlPart1=htmlPart1.replace("&submitDate",HtmlUtil.getDateDivSw("startSubmitDate","endSubmitDate", otVo.getStartSubmitDate(),otVo.getEndSubmitDate()));
				htmlPart1=htmlPart1.replace("	<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployeeNo(),false,null));
				htmlPart1=htmlPart1.replace("&SearchEmployee",ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployee(),false,null));
				htmlPart1=htmlPart1.replace("<Userdata/>",HtmlUtil.getLabel6Html(DBUtil.queryDBField(con,SqlUtil.queryChargeName(lro.get(0).getEMPLOYEENO()),"EMPLOYEE")));
				htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
				if(otVo.isShowDataTable()){
				logger.info("getOvertimeDept  :  "+SqlUtil.getLLOvertime(otVo,lro.get(0).getEMPLOYEENO()));
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
							SqlUtil.getLLOvertime(otVo,lro.get(0).getEMPLOYEENO()),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageLList));
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

