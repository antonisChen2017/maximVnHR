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
import cn.com.maxim.portal.attendan.vo.editProcessVO;
import cn.com.maxim.portal.dao.ad_editProcessDAO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;

public class ad_editProcess extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(ad_editLreasons.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		editProcessVO edVo = new editProcessVO();
		edVo.setActionURI(ActionURI);

		try
		{
		
			if (actText != null)
			{
				System.out.println("actText :" +actText);
			
				BeanUtils.populate(edVo, request.getParameterMap());
			
				//查詢
				if (actText.equals("QUE"))
				{
					edVo.setShowDataTable(true);
				//	DBUtil.updateSql(SqlUtil.updateLreasons(edVo), con);
					edVo.setMsg(keyConts.editOK);
					showHtml(con, out, edVo, UserInformation);
				}
				// 部門流程記錄
				if (actText.equals("saveDept"))
				{
					logger.info("saveDept");
					//edVo.setShowDataTable(true);
					ad_editProcessDAO.insterDeptProcess(edVo);
					edVo.setMsg(keyConts.editOK);
					showHtml(con, out, edVo, UserInformation);
				}
				// 新增
				if (actText.equals("INS"))
				{
				
					edVo.setShowDataTable(true);
				
					//DBUtil.updateSql(SqlUtil.saveLreasons(edVo), con);
					edVo.setMsg(keyConts.saveOK);
					showHtml(con, out, edVo, UserInformation);
				}
				
			}
			else
			{
				// 預設
				edVo.setID("0");
				edVo.setDept("0");
				edVo.setShowDataTable(true);
				showHtml(con, out, edVo, UserInformation);

			}
		}
		catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
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
		if(ajax.equals("ChosenSqlDiv")){
			String role = request.getParameter("role");
			String name = request.getParameter("name");
			String html="";
			editProcessVO edVo = new editProcessVO();
			try
			{
			
				//html = ControlUtil.drawChosenSelect(con,  "oneDAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='"+role+"'  ", "0",false,null);	
				html =ControlUtil.drawChosenSql(con,  name,SqlUtil.getEmpDUdata(role),  "0",keyConts.msgZ);	
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("html : "+html);
			 out.println(html);
	 	}
		
	}
	private void showHtml(Connection con, PrintWriter out, editProcessVO edVo, UserDescriptor UserInformation) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();

		String htmlPart1 = hu.gethtml(htmlConsts.html_ad_editProcess);
		htmlPart1 = htmlPart1.replace("<ActionURI/>", edVo.getActionURI());
		htmlPart1=htmlPart1.replace("<Dept/>", 	ControlUtil.drawChosenSelect(con, "Dept", "VN_DEPARTMENT", "ID", "DEPARTMENT", null ,edVo.getDept(),false,null));
		htmlPart1=htmlPart1.replace("<Unit/>",ControlUtil.drawChosenSelect(con,  "Unit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + edVo.getDept() +"'  ", edVo.getUnit(),false,null));
		htmlPart1=htmlPart1.replace("<oneEAuditorTitle/>",ControlUtil.drawChosenSelect(con,  "oneEAuditorTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('E')  ", edVo.getOneEAgentTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneEAgentTitle/>",ControlUtil.drawChosenSelect(con,  "oneEAgentTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('E')  ", edVo.getOneEAgentTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneUAuditorTitle/>",ControlUtil.drawChosenSelect(con,  "oneUAuditorTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E')  ", edVo.getOneUAgentTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneUAgentTitle/>",ControlUtil.drawChosenSelect(con,  "oneUAgentTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E')  ", edVo.getOneUAgentTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneDAuditorTitle/>",ControlUtil.drawChosenSelect(con,  "oneDAuditorTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E','D')  ", edVo.getOneEAgentTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneDAgentTitle/>",ControlUtil.drawChosenSelect(con,  "oneDAgentTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E','D')  ", edVo.getOneEAgentTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneMAuditorTitle/>",ControlUtil.drawChosenSelect(con,  "oneMAuditorTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E','D','M')  ", edVo.getOneUAgentTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneMAgentTitle/>",ControlUtil.drawChosenSelect(con,  "oneMAgentTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E','D','M')  ", edVo.getOneUAgentTitle(),false,null));	
		
		
		htmlPart1=htmlPart1.replace("<oneEAuditor/>",ControlUtil.drawChosenSelect(con,  "oneEAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneEAuditor(),false,null));	
		htmlPart1=htmlPart1.replace("<oneEAgent/>",ControlUtil.drawChosenSelect(con,  "oneEAgent", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneEAgent(),false,null));	
		
		htmlPart1=htmlPart1.replace("<oneUAuditor/>",ControlUtil.drawChosenSelect(con,  "oneUAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneUAuditor(),false,null));	
		htmlPart1=htmlPart1.replace("<oneUAgent/>",ControlUtil.drawChosenSelect(con,  "oneUAgent", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneUAgent(),false,null));	
		
		htmlPart1=htmlPart1.replace("<oneDAuditor/>",ControlUtil.drawChosenSelect(con,  "oneDAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneDAuditor(),false,null));	
		htmlPart1=htmlPart1.replace("<oneDAgent/>",ControlUtil.drawChosenSelect(con,  "oneDAgent", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneDAgent(),false,null));	
		
		htmlPart1=htmlPart1.replace("<oneMAuditor/>",ControlUtil.drawChosenSelect(con,  "oneMAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneMAuditor(),false,null));	
		htmlPart1=htmlPart1.replace("<oneMAgent/>",ControlUtil.drawChosenSelect(con,  "oneMAgent", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneMAgent(),false,null));	
		edVo.setRole("U");
		htmlPart1=htmlPart1.replace("<threeELever1/>",ControlUtil.drawChosenSql(con,  "threeELever1",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeELever1(),keyConts.msgZ));	
		edVo.setRole("D");
		htmlPart1=htmlPart1.replace("<threeELever2/>",ControlUtil.drawChosenSql(con,  "threeELever2",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeELever2(),keyConts.msgZ));		
		htmlPart1=htmlPart1.replace("<threeULever2/>",ControlUtil.drawChosenSql(con,  "threeULever2",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeULever2(),keyConts.msgZ));	
		edVo.setRole("M");
		htmlPart1=htmlPart1.replace("<threeELever3/>",ControlUtil.drawChosenSql(con,  "threeELever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeELever3(),keyConts.msgZ));	
		htmlPart1=htmlPart1.replace("<threeULever3/>",ControlUtil.drawChosenSql(con,  "threeULever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeULever3(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeDLever3/>",ControlUtil.drawChosenSql(con,  "threeDLever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeDLever3(),keyConts.msgZ));
		edVo.setRole("B");
		htmlPart1=htmlPart1.replace("<threeELever4/>",ControlUtil.drawChosenSql(con,  "threeELever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeELever4(),keyConts.msgZ));	
		htmlPart1=htmlPart1.replace("<threeULever4/>",ControlUtil.drawChosenSql(con,  "threeULever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeULever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeDLever4/>",ControlUtil.drawChosenSql(con,  "threeDLever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeDLever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeMLever4/>",ControlUtil.drawChosenSql(con,  "threeMLever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeMLever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<rowID/>",edVo.getID());
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(edVo.getMsg()));
		if (edVo.isShowDataTable())
		{
			//htmlPart1 = htmlPart1.replace("<drawTableM/>",	HtmlUtil.drawTableEdit(SqlUtil.getVnLreasons(edVo), 	HtmlUtil.drawTableMcheckButton(), con, out, keyConts.LRessons));
		}

		out.println(htmlPart1);
	}

}

