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
import cn.com.maxim.portal.attendan.vo.editSupervisorVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;

public class ts_changeEmp extends TemplatePortalPen
{
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(rev_LeaveCard.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		
		editSupervisorVO edVo = new editSupervisorVO();
		edVo.setActionURI(ActionURI);
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(edVo,request.getParameterMap()); 
					// 查询
					if (actText.equals("Save")) {
						
						request.getSession().setAttribute("employeeNoSys", edVo.getOEmployeeNo());
						edVo.setMsg("登入帐号已更换工号");
						edVo.setShowDataTable(true);
						showHtml(con, out, edVo,UserInformation,request);
					}
				
				}else{
					//預設
					edVo.setORole("0");
					showHtml(con, out, edVo,UserInformation,request);
				
				}
		}catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		
		if(ajax.equals("SwEmpNo")){
			 String ORole = request.getParameter("ORole");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setSearchEmployeeNo("0");
			try
			{
				out.println(ControlUtil.drawChosenSelect(con,  "OEmployeeNo", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEENO", "ROLE='" + ORole + "'", otVo.getSearchEmployeeNo(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
	 	}
		 if(ajax.equals("SwEmp")){
			 String ORole = request.getParameter("ORole");
			
			try
			{
				out.println(ControlUtil.drawChosenSql(con,  "OEmployee",SqlUtil.getEmpDUdata(ORole), "0",keyConts.msgZ));
			}
			catch (Exception e)
			{
				out.println("");
			}
	 	}
	 }
	
	private void showHtml(Connection con, PrintWriter out, editSupervisorVO edVo , UserDescriptor UserInformation,HttpServletRequest request) throws Exception {
		HtmlUtil hu=new HtmlUtil();
		String htmlPart1=hu.gethtml(htmlConsts.html_changeEmp);

		htmlPart1=htmlPart1.replace("<newEmp/>", 	getUser(con,UserInformation,request));
		htmlPart1=htmlPart1.replace("<ActionURI/>", 	edVo.getActionURI());
		htmlPart1=htmlPart1.replace("<OEmail/>",HtmlUtil.getEmailDiv("OEmail",edVo.getOEmail(),"" ));
		htmlPart1=htmlPart1.replace("<ORole/>",ControlUtil.drawChosenSelect(con, "ORole", "VN_ROLE", "ID", "TITLE", " ID NOT IN ('E') " ,edVo.getORole(),false,null));
		htmlPart1=htmlPart1.replace("<OEmployee/>",ControlUtil.drawChosenSql(con,  "OEmployee",SqlUtil.getEmpDUdata(edVo.getORole()), edVo.getOEmployee(),keyConts.msgZ));	
		htmlPart1=htmlPart1.replace("<OEmployeeNo/>",ControlUtil.drawChosenSelect(con,  "OEmployeeNo", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEENO", "ROLE='"+edVo.getORole()+"' ", edVo.getOEmployeeNo(),false,null));
	    htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(edVo.getMsg()));
		
	    out.println(htmlPart1);
	    }
	
	 /**
	  * 切換成員
	  * @param con
	  * @param UserInformation
	  * @param request
	  * @return
	  */
	 private  String getUser(Connection con,UserDescriptor UserInformation,HttpServletRequest request){
		 	employeeUserRO eo=new employeeUserRO();
			String UserName="";
			String employeeNoSys=( String)request.getSession().getAttribute("employeeNoSys");
			if(employeeNoSys!=null && !employeeNoSys.equals("")){
					UserName=employeeNoSys;				
			}else{
					UserName=UserInformation.getUserName();
			}
		
			return UserName;
	 }
}

