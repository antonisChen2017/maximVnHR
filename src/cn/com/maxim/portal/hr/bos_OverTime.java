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
 * 
 * @author Antonis.chen
 *
 */
public class bos_OverTime  extends TemplatePortalPen
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
			    			logger.info("加班時數/B : " +otVo.toString());		
			    			otVo.setStatus("B");
						otVo.setMsg(overTimeDAO.deptProcess(con, otVo));		
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						setHtmlPart1(con, out, otVo,UserInformation,request);
					}
					if (actText.equals("ALL")) {
						logger.info("加班全部通過");

						otVo.setShowDataTable(true);
						otVo.setStatus("B");//審核通過
						// 儲存db
						//檢查是否已經權限走到底
						otVo.setMsg(overTimeDAO.deptAllProcess(con, otVo,request.getParameter("rowID")));
						otVo.setStatus("D");
						setHtmlPart1(con, out, otVo,UserInformation,request);
					}
					if (actText.equals("R")) {
						
					        logger.info("加班時數/R: " +otVo.toString());		
				    		otVo.setLeaveApply("2");
				    		otVo.setStatus("BR");
						DBUtil.updateTimeOverSStatus(otVo, con);
						DBUtil.updateSql(SqlUtil.setStatusReturnMsg(otVo.getReturnMsg(), request.getParameter("rowID")), con);
						
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						setHtmlPart1(con, out, otVo,UserInformation,request);
					
					}
					if (actText.equals("UB")) {
			    		logger.info("超時加班時數/UB : " +otVo.toString());					
			    		otVo.setLeaveApply("2");
			    		otVo.setStatus("UB");
						DBUtil.updateTimeOverSStatus(otVo, con);
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						setHtmlPart1(con, out, otVo,UserInformation,request);
					}
					
					/**超時加班用-退回**/
					if (actText.equals("RR")) {
						
					        logger.info("超時加班時數/RR: " +otVo.toString());		
				    		otVo.setLeaveApply("2");
				    		otVo.setStatus("RR");
						DBUtil.updateCSstatus(otVo, con);
						DBUtil.updateSql(SqlUtil.setCSReturnMsg(otVo.getReturnMsg(), request.getParameter("rowID")), con);
						
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						setHtmlPart1(con, out, otVo,UserInformation,request);
					
					}
					/**超時加班用- 通過**/
					if (actText.equals("RB")) {
						
					        logger.info("超時加班時數/RB : " +otVo.toString());		
			    			otVo.setStatus("RB");
			    			otVo.setLeaveApply("1");
			    			DBUtil.updateCSstatus(otVo, con);
						otVo.setMsg(keyConts.okMsg);	
						otVo.setShowDataTable(true);
						otVo.setStatus("U");
						setHtmlPart1(con, out, otVo,UserInformation,request);
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
			String htmlPart1=hu.gethtml(htmlConsts.html_bos_OverTime);
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
			htmlPart1=htmlPart1.replace("&SearchDepartmen", ControlUtil.drawChosenSql(con,  "searchDepartmen",SqlUtil.querySelectOverDept(lro.get(0).getEMPLOYEENO()), otVo.getSearchDepartmen(),keyConts.msgS));	
			htmlPart1=htmlPart1.replace("&submitDate",HtmlUtil.getDateDivSw("startSubmitDate","endSubmitDate", otVo.getStartSubmitDate(),otVo.getEndSubmitDate()));
			htmlPart1=htmlPart1.replace("	<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployeeNo(),false,null));
			htmlPart1=htmlPart1.replace("&SearchEmployee",ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + otVo.getSearchDepartmen( ) + "'", otVo.getSearchEmployee(),false,null));
			 htmlPart1=htmlPart1.replace("<Userdata/>",HtmlUtil.getLabel6Html(DBUtil.queryDBField(con,SqlUtil.queryChargeName(lro.get(0).getEMPLOYEENO()),"EMPLOYEE")));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			if(otVo.isShowDataTable()){
			logger.info("getBOvertime  :  "+SqlUtil.getBOvertime(otVo,lro.get(0).getEMPLOYEENO()));
			htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawOvertimeTable(
						SqlUtil.getBOvertime(otVo,lro.get(0).getEMPLOYEENO()),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageB));
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

