package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.editDeptUnit;
import cn.com.maxim.portal.attendan.vo.editSupervisorVO;
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
 * 設定主管資料
 * @author Antonis.chen
 *
 */
public class rev_editSupervisor extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(ad_editLholiday.class);

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
	

		editSupervisorVO edVo = new editSupervisorVO();
		edVo.setActionURI(ActionURI);
		logger.info("actText :"+actText);	
		try
		{
		
			if (actText != null)
			{
				BeanUtils.populate(edVo, request.getParameterMap());
			
				// 原資料庫主管新增EMAIL
				if (actText.equals("SaveO"))
				{
					
					edVo.setShowDataTable(true);
					logger.info("updateHROEmail  "+SqlUtil.updateHROEmail(edVo));	
					DBUtil.updateSql(SqlUtil.updateHROEmail(edVo), con);
					edVo.setMsg(keyConts.editOK);
					edVo.setShowDataTable(true);
					edVo.setTab("o");
					showHtml(con, out, edVo, UserInformation);
					
				}
				// 增加編輯新主管資料
				if (actText.equals("SaveN"))
				{
					
					edVo.setShowDataTable(true);
					edVo.setMsg("增加編輯新主管資料");
					edVo.setTab("n");
					showHtml(con, out, edVo, UserInformation);
				}
				//查詢原資料庫主管資料
				if (actText.equals("QueryO"))
				{
					
					edVo.setShowDataTable(true);
					//edVo.setMsg("進入刪除流程");
				
				
					edVo.setMsg("查詢原資料庫主管資料");
					edVo.setTab("o");
					showHtml(con, out, edVo, UserInformation);
				}
				//查詢編輯新主管資料
				if (actText.equals("QueryN"))
				{
					
					edVo.setShowDataTable(true);
					//edVo.setMsg("進入刪除流程");
					//logger.info("getUrowID "+UrowID);	
				
					edVo.setMsg("查詢編輯新主管資料");
					edVo.setTab("n");
					showHtml(con, out, edVo, UserInformation);
				}
			}
			else
			{
				// 預設
				edVo.setNEmail("");
				edVo.setOEmail("");
				edVo.setNEmployee("");
				edVo.setNEmployeeNo("");
				edVo.setOEmployeeNo("0");
				edVo.setOEmployee("0");
				edVo.setORole("0");
				edVo.setNRole("0");
				edVo.setTab("n");
				edVo.setNEntryDate(DateUtil.NowDate());
				edVo.setShowDataTable(true);
				showHtml(con, out, edVo, UserInformation);

			}
		}
		catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
		
	}
	
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{

		 if(ajax.equals("SwEmpNo")){
			 String ORole = request.getParameter("ORole");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setSearchEmployeeNo("0");
			try
			{
				out.println(ControlUtil.drawChosenSelect(con,  "OEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "ROLE='" + ORole + "'", otVo.getSearchEmployeeNo(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
	 	}
		 if(ajax.equals("SwEmp")){
			 String ORole = request.getParameter("ORole");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setSearchEmployee("0");
			try
			{
				out.println(ControlUtil.drawChosenSelect(con,  "OEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "ROLE='" + ORole + "'", otVo.getSearchEmployee(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
	 	}
		 if(ajax.equals("SwUnit")){
			 String searchDepartmen = request.getParameter("searchDepartmen");	
			 logger.info("searchDepartmen :"+searchDepartmen);	
			 
			 String html="";
			 overTimeVO otVo = new overTimeVO(); 
			 otVo.setSearchEmployeeNo("0");
			try
			{
				html = ControlUtil.drawChosenSelect(con,  "NUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + searchDepartmen + "'", "0",false,null);	
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 logger.info("html :"+html);	
			 out.println(html);
	 	}
	}
	
	private void showHtml(Connection con, PrintWriter out, editSupervisorVO edVo, UserDescriptor UserInformation) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String htmlPart1 = hu.gethtml(htmlConsts.html_editSupervisor);

		htmlPart1 = htmlPart1.replace("<ActionURI/>", edVo.getActionURI());
	
		htmlPart1=htmlPart1.replace("<OEmail/>",HtmlUtil.getEmailDiv("OEmail",edVo.getOEmail(),"" ));
		htmlPart1=htmlPart1.replace("<ORole/>",ControlUtil.drawChosenSelect(con, "ORole", "VN_ROLE", "ID", "TITLE", " ID NOT IN ('E') " ,edVo.getORole(),false,null));
		htmlPart1=htmlPart1.replace("<OEmployee/>",ControlUtil.drawChosenSelect(con,  "OEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "ROLE='"+edVo.getORole()+"' ", edVo.getOEmployee(),false,null));
		htmlPart1=htmlPart1.replace("<OEmployeeNo/>",ControlUtil.drawChosenSelect(con,  "OEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEE", "ROLE='"+edVo.getORole()+"' ", edVo.getOEmployeeNo(),false,null));
		
		htmlPart1=htmlPart1.replace("<NRole/>",ControlUtil.drawChosenSelect(con, "NRole", "VN_ROLE", "ID", "TITLE", " ID NOT IN ('E')  ",edVo.getNRole(),false,null));
		htmlPart1=htmlPart1.replace("<NDepartment/>", 	ControlUtil.drawChosenSelect(con, "NDepartment", "VN_DEPARTMENT", "ID", "DEPARTMENT", null,edVo.getNDepartment(),false,null));
		htmlPart1=htmlPart1.replace("<NUnit/>",ControlUtil.drawChosenSelect(con,  "NUnit", "VN_UNIT", "ID", "UNIT", " ID='0' ", edVo.getNUnit(),false,null));
		htmlPart1=htmlPart1.replace("<NEmployee/>",HtmlUtil.getTextDiv("NEmployee",edVo.getNEmployee(),"必要输入" ));
		htmlPart1=htmlPart1.replace("<NEmployeeNo/>",HtmlUtil.getTextDiv("NEmployeeNO",edVo.getNEmployeeNo(),"必要输入" ));
		htmlPart1=htmlPart1.replace("<NDutes/>",HtmlUtil.getTextDiv("NDutes",edVo.getNEmployee(),"必要输入" ));
		htmlPart1=htmlPart1.replace("<NEntryDate/>",HtmlUtil.getDateDiv("NEntryDate", edVo.getNEntryDate()));
		htmlPart1=htmlPart1.replace("<NVietnamese/>",HtmlUtil.getTextDiv("NVietnamese",edVo.getNEmployee(),"非必要输入" ));
		htmlPart1=htmlPart1.replace("<NEmail/>",HtmlUtil.getEmailDiv("NEmail",edVo.getNEmail(),"必要输入" ));
		htmlPart1=htmlPart1.replace("<tab/>",edVo.getTab());
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(edVo.getMsg()));
		if (edVo.isShowDataTable())
		{
		if(edVo.getOEmail().equals("o")){
			//logger.info("UnitSQL :"+SqlUtil.getVnUnit(edVo));	
			//	htmlPart1 = htmlPart1.replace("<drawTableU/>",
			//			HtmlUtil.drawTableEditT(SqlUtil.getVnUnit(edVo), 
			//					HtmlUtil.drawTableMcheckButton(), con, out, keyConts.ColUnit));
		}
		if(edVo.getOEmail().equals("n")){
			
		}
	
		}

		out.println(htmlPart1);
	}
}

