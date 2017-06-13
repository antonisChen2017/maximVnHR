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
import cn.com.maxim.portal.attendan.vo.editStopReasonVO;
import cn.com.maxim.portal.attendan.vo.editSupplementVO;
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

public class ad_TransferStaff extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(ad_editLholiday.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		editSupplementVO edVo = new editSupplementVO();
		edVo.setActionURI(ActionURI);

		try
		{
		
			if (actText != null)
			{
				BeanUtils.populate(edVo, request.getParameterMap());
				
				if (actText.equals("QUE")){
					edVo.setShowDataTable(true);
					showHtml(con, out, edVo, UserInformation);
				}
				// 刪除
				if (actText.equals("DEL"))
				{
					
					  edVo.setShowDataTable(true);
					  DBUtil.updateSql(SqlUtil.deleteOvertimePermission(edVo), con);
			
					 showHtml(con, out, edVo, UserInformation);
				}
				
				// 修改
				if (actText.equals("UPD"))
				{
					edVo.setShowDataTable(true);
					//DBUtil.updateSql(SqlUtil.updateStopReasons(edVo), con);
					edVo.setMsg(keyConts.editOK);
					showHtml(con, out, edVo, UserInformation);
				}
				// 新增
				if (actText.equals("INS"))
				{
					edVo.setShowDataTable(true);
				    DBUtil.updateSql(SqlUtil.saveTransferStaff(edVo), con);
					edVo.setMsg(keyConts.saveOK);
					showHtml(con, out, edVo, UserInformation);
				}
				
			}
			else
			{
				// 預設
				 edVo.setSearchDepartmen("0");
				///edVo.setEName("");
			  //	edVo.setVName("");
				edVo.setTransferDate(DateUtil.NowDate());
				edVo.setRowID("0");
				edVo.setShowDataTable(false);
				showHtml(con, out, edVo, UserInformation);

			}
		}
		catch (Exception err)
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
	
	private void showHtml(Connection con, PrintWriter out, editSupplementVO edVo, UserDescriptor UserInformation) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		//employeeUserRO eo = new employeeUserRO();

		//List<employeeUserRO> lro = DBUtil.queryUserList(con, SqlUtil.getEmployeeNameDate(UserInformation.getUserName()), eo);
	//	lcVo.setSearchDepartmen(lro.get(0).getDID());

		String htmlPart1 = hu.gethtml(htmlConsts.html_ad_TransferStaff);
		htmlPart1 = htmlPart1.replace("<ActionURI/>", edVo.getActionURI());
		htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	ControlUtil.drawChosenSelect(con,  "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", null ,edVo.getSearchDepartmen( ),false,null));
		htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + 	edVo.getSearchDepartmen()+ "'", edVo.getSearchEmployeeNo(),false,null));
		htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + edVo.getSearchDepartmen() + "'", edVo.getSearchEmployee(),false,null));
		htmlPart1=htmlPart1.replace("<queryDate/>",HtmlUtil.getDateDiv("transferDate", edVo.getTransferDate()));
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(edVo.getMsg()));
		if (edVo.isShowDataTable())
		{
			System.out.println("sql->"+SqlUtil.getTransferStaff(edVo));
			htmlPart1 = htmlPart1.replace("<drawTableM/>",
					HtmlUtil.drawTableSupplement(SqlUtil.getTransferStaff(edVo), 
									HtmlUtil.drawTableMcheckButton(), con, out, keyConts.stopReasons));
		}

		out.println(htmlPart1);
	}
}
