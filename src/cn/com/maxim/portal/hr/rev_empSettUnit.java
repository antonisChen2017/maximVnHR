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
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.TranslateUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.UrlUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;
import cn.com.maxim.potral.consts.sqlConsts;
/**
 *  管理部審核 設定員工與单位關係
 * @author Antonis.chen
 *
 */
public class rev_empSettUnit  extends TemplatePortalPen
{
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(rev_empSettUnit.class);
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		
		leaveCardVO lcVo = new leaveCardVO(); 
		HtmlUtil hu=new HtmlUtil();
		lcVo.setActionURI(ActionURI);
		boolean showSetData=false;
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(lcVo,request.getParameterMap()); 
					// 查询
					if (actText.equals("QUE")) {
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation,showSetData);
					}
					if (actText.equals("update")) {
						logger.info("審核 設定員工與单位關係 and 角色/update: " +lcVo.toString());	
						lcVo.setShowDataTable(true);
						lcVo=DBUtil.updateUnit(con,lcVo);
						lcVo=DBUtil.updateRole(con,lcVo);
						 showSetData=true;
						showHtml(con, out, lcVo,UserInformation,showSetData);
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
					showHtml(con, out, lcVo,UserInformation,showSetData);
				
				}
		}catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		 
		 if(ajax.equals("SwEmpNo")){
			 String searchDepartmen = request.getParameter("searchDepartmen");
			overTimeVO otVo = new overTimeVO(); 
			otVo.setSearchEmployeeNo("0");
			try
			{
				out.println(ControlUtil.drawChosenSelect(con,  "searchEmployeeNo", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEENO", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployeeNo(),false,null));
			}
			catch (SQLException e)
			{
				out.println("");
			}
	 	}
		if(ajax.equals("SwUnit")){
			 String searchDepartmen = request.getParameter("searchDepartmen");
			 String html="";
			
			try
			{
				html = ControlUtil.drawChosenSelect(con, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + searchDepartmen + "'", "0",
						false,null);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("html : "+html);
			 out.println(html);
	 	}
		
		 if(ajax.equals("SwEmp")){
				String searchDepartmen = request.getParameter("searchDepartmen");
				try
				{
				String whereSql= null;
				if(searchDepartmen.equals("0")){
					whereSql=null;
				}else{
					whereSql= " DEPARTMENT_ID='" + searchDepartmen + "'";
				}
					out.println(ControlUtil.drawChosenSelect(con, "searchEmployee", 
							"HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE",whereSql, "0",
							false,null));
				}
				catch (SQLException e)
				{
					out.println("");
				}
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
	
	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo , UserDescriptor UserInformation,boolean showSetData) throws Exception {
		
		
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_rev_empSettUnit);
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	lcVo.getActionURI());
			 String emp_subSql="",unit_subSql="";
			 String searchUnit = lcVo.getSearchUnit();
			 String searchDepartmen =lcVo.getSearchDepartmen();
			 if(! searchDepartmen.equals("0")){
				 unit_subSql=" DEPARTMENT_ID = '"+searchDepartmen + "' ";
				 emp_subSql=" DEPARTMENT_ID = '"+searchDepartmen + "' ";
			 }else{
				 unit_subSql="ID ='" + searchUnit + "' ";
				 emp_subSql=null;
			 }
			 
			htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	ControlUtil.drawChosenSelect(con, "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", null ,lcVo.getSearchDepartmen( ),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con, "searchEmployee", 
					"HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE",emp_subSql, lcVo.getSearchEmployee(),
					false,null));
			htmlPart1=htmlPart1.replace("<SearchRole/>",ControlUtil.drawChosenSelect(con,"searchRole", "VN_ROLE", "ID", "TITLE", null, lcVo.getSearchRole(),false,null));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawChosenSelect(con,"searchUnit", "VN_UNIT", "ID", "UNIT", unit_subSql, lcVo.getSearchUnit(),false,null));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEENO", emp_subSql, lcVo.getSearchEmployeeNo(),false,null));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(lcVo.getMsg()));
			if(lcVo.isShowDataTable()){
			
				if(showSetData){
				    logger.info("SqlUtil.getRoleUser(lcVo): " +SqlUtil.getRoleUser(lcVo));	
				    htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawTable(
						SqlUtil.getRoleUser(lcVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageMsList));
				}else{
				    logger.info("SqlUtil.getRoleUser(lcVo): " +SqlUtil.getRole(lcVo));	
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawTable(
						SqlUtil.getRole(lcVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageMsList));
				}
			}			

		    out.println(htmlPart1);
		    }
}
