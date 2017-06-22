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
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;

public class ad_editProcess extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(ad_editProcess.class);
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
					showHtml(con, out, edVo, UserInformation);
				}
				
				// 查詢部門資料
				if (actText.equals("queryDept"))
				{
					
					//edVo.setShowDataTable(true);
					edVo.setDept( request.getParameter("Dept"));
					//先查有無資料
					int COUNT=0;
					logger.info("queryDept  queryDeptLeaveCount    "+	SqlUtil.queryDeptLeaveCount(edVo));
					String Dcount=DBUtil.queryDBField(con,SqlUtil.queryDeptLeaveCount(edVo), "DCOUNT");
					logger.info("Dcount :  "+Dcount);
					if(Dcount!=null && !Dcount.equals("")){
						 COUNT=Integer.valueOf(Dcount);
					}
					
					if(COUNT>0){
						edVo=ad_editProcessDAO.getDeptProcess(con,edVo);	
						edVo.setMsg(keyConts.processData);
					}else{
						edVo=setDeptNoData(edVo);
						edVo.setMsg(keyConts.processNoData);
					}
					//再查出資料
					showHtml(con, out, edVo, UserInformation);
				}
				
				// 部門流程記錄
				if (actText.equals("saveDept"))
				{
					logger.info("saveDept");
					edVo.setDept( request.getParameter("Dept"));
					int COUNT=0;
					logger.info("count saveDept  queryDeptLeaveCount"+	SqlUtil.queryDeptLeaveCount(edVo));
				
					String Dcount=DBUtil.queryDBField(con,SqlUtil.queryDeptLeaveCount(edVo), "DCOUNT");
					if(Dcount!=null && !Dcount.equals("")){
						 COUNT=Integer.valueOf(Dcount);
					}
					
					if(COUNT>0){
						ad_editProcessDAO.updateDeptProcess(con,edVo);
						edVo.setMsg(keyConts.editOK);
					}else{
						ad_editProcessDAO.insterDeptProcess(con,edVo);
						edVo.setMsg(keyConts.saveOK);
					}
			
				
					showHtml(con, out, edVo, UserInformation);
				}
				
				// 查詢單位資料
				if (actText.equals("queryUnit")){
					logger.info("queryUnit");
					edVo.setDept( request.getParameter("Dept"));
					edVo.setUnit( request.getParameter("Unit"));
					//logger.info("getUnit "+edVo.getUnit());
					int COUNT=0,COUNTD=0;
					String Ucount=DBUtil.queryDBField(con,SqlUtil.queryUnitLeaveCount(edVo), "COUNT");
					//logger.info("Ucount "+Ucount);
					if(Ucount!=null && !Ucount.equals("")){
						 COUNT=Integer.valueOf(Ucount);
					}
					
					if(COUNT>0){
						edVo=ad_editProcessDAO.getUnitProcess(con,edVo);	
						//edVo=ad_editProcessDAO.getDeptProcess(con,edVo);	
						edVo.setMsg(keyConts.processUnitData);
					}else{
						logger.info("COUNT "+COUNT);
						String Dcount=DBUtil.queryDBField(con,SqlUtil.queryDeptLeaveCount(edVo), "COUNT");
						if(Dcount!=null && !Dcount.equals("")){
							COUNTD=Integer.valueOf(Dcount);
						}
						if(COUNTD>0){
							edVo=ad_editProcessDAO.getDeptProcess(con,edVo);	
						}
						edVo.setMsg(keyConts.processUnitNoData);
						edVo=setUnitNoData(edVo);
					}
					edVo.setUnit( request.getParameter("Unit"));
					//再查出資料
					showHtml(con, out, edVo, UserInformation);
				}
				// 單位流程記錄
				if (actText.equals("saveUnit")){
					logger.info("saveUnit");
					
					edVo.setDept( request.getParameter("Dept"));
					edVo.setUnit( request.getParameter("Unit"));
					logger.info("Unit"+edVo.getUnit());
					int COUNT=0;
					String Ucount=DBUtil.queryDBField(con,SqlUtil.queryUnitLeaveCount(edVo), "COUNT");
					if(Ucount!=null && !Ucount.equals("")){
						 COUNT=Integer.valueOf(Ucount);
					}
					if(COUNT>0){
						
						ad_editProcessDAO.updateUnitProcess(con,edVo);	
						edVo=ad_editProcessDAO.getUnitProcess(con,edVo);	
						edVo.setMsg(keyConts.editOK);
					}else{
						ad_editProcessDAO.insterUnitProcess(con,edVo);	
						edVo=ad_editProcessDAO.getUnitProcess(con,edVo);	
						edVo.setMsg(keyConts.saveOK);
					}
					edVo.setUnit( request.getParameter("Unit"));
					//再查出資料
					showHtml(con, out, edVo, UserInformation);
				}
				
				
			}
			else
			{
				// 預設
				edVo=setData(edVo);
				//edVo.setShowDataTable(true);
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
		htmlPart1=htmlPart1.replace("<oneEAuditorTitle/>",ControlUtil.drawChosenSelect(con,  "oneEAuditorTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('E')  ", edVo.getOneEAuditorTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneEAgentTitle/>",ControlUtil.drawChosenSelect(con,  "oneEAgentTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('E')  ", edVo.getOneEAgentTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneUAuditorTitle/>",ControlUtil.drawChosenSelect(con,  "oneUAuditorTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E')  ", edVo.getOneUAuditorTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneUAgentTitle/>",ControlUtil.drawChosenSelect(con,  "oneUAgentTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E')  ", edVo.getOneUAgentTitle(),false,null));	
		htmlPart1=htmlPart1.replace("<oneDID/>",ControlUtil.drawHidden(edVo.getOneDID(), "oneDID"));
		htmlPart1=htmlPart1.replace("<oneMID/>",ControlUtil.drawHidden(edVo.getOneMID(), "oneMID"));
		htmlPart1=htmlPart1.replace("<threeDID/>",ControlUtil.drawHidden(edVo.getThreeDID(), "threeDID"));
		htmlPart1=htmlPart1.replace("<threeMID/>",ControlUtil.drawHidden(edVo.getThreeMID(), "threeMID"));
		htmlPart1=htmlPart1.replace("<oneEID/>",ControlUtil.drawHidden(edVo.getOneEID(), "oneEID"));
		htmlPart1=htmlPart1.replace("<oneUID/>",ControlUtil.drawHidden(edVo.getOneUID(), "oneUID"));
		htmlPart1=htmlPart1.replace("<threeEID/>",ControlUtil.drawHidden(edVo.getThreeEID(), "threeEID"));
		htmlPart1=htmlPart1.replace("<threeUID/>",ControlUtil.drawHidden(edVo.getThreeUID(), "threeUID"));
		  /**員工**/
		logger.info("getOneEAgent  "+edVo.getOneEAgent());
		logger.info("getOneEAuditorTitle  "+edVo.getOneEAuditorTitle());
		if( edVo.getOneEAuditor().equals("0")){
			htmlPart1=htmlPart1.replace("<oneEAuditor/>",ControlUtil.drawChosenSelect(con,  "oneEAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneEAuditor(),false,null));	
		}else{
			edVo.setRole(edVo.getOneEAuditorTitle());
			htmlPart1=htmlPart1.replace("<oneEAuditor/>",ControlUtil.drawChosenSql(con,  "oneEAuditor",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneEAuditor(),keyConts.msgZ));	
		}
		logger.info("getOneEAgent   "+edVo.getOneEAgent());
		logger.info("getOneEAgentTitle  "+edVo.getOneEAgentTitle());
		if( edVo.getOneEAgent().equals("0")){
			htmlPart1=htmlPart1.replace("<oneEAgent/>",ControlUtil.drawChosenSelect(con,  "oneEAgent", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneEAgent(),false,null));			
		}else{
			edVo.setRole(edVo.getOneEAgentTitle());
			htmlPart1=htmlPart1.replace("<oneEAgent/>",ControlUtil.drawChosenSql(con,  "oneEAgent",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneEAgent(),keyConts.msgZ));	
		}
		
		  /**單位主管**/
		if( edVo.getOneUAuditor().equals("0")){
			htmlPart1=htmlPart1.replace("<oneUAuditor/>",ControlUtil.drawChosenSelect(con,  "oneUAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneUAuditor(),false,null));	
		}else{
			edVo.setRole(edVo.getOneUAuditorTitle());
			htmlPart1=htmlPart1.replace("<oneUAuditor/>",ControlUtil.drawChosenSql(con,  "oneUAuditor",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneUAuditor(),keyConts.msgZ));	
			}
		
		if( edVo.getOneUAgent().equals("0")){
			htmlPart1=htmlPart1.replace("<oneUAgent/>",ControlUtil.drawChosenSelect(con,  "oneUAgent", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneUAgent(),false,null));	
		}else{
			edVo.setRole(edVo.getOneUAgentTitle());
			htmlPart1=htmlPart1.replace("<oneUAgent/>",ControlUtil.drawChosenSql(con,  "oneUAgent",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneUAgent(),keyConts.msgZ));	
		}

	
	   /**部門主管**/
		htmlPart1=htmlPart1.replace("<oneDAuditorTitle/>",ControlUtil.drawChosenSelect(con,  "oneDAuditorTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E','D')  ", edVo.getOneDAuditorTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneDAgentTitle/>",ControlUtil.drawChosenSelect(con,  "oneDAgentTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E','D')  ", edVo.getOneDAgentTitle(),false,null));
		
		if( edVo.getOneDAuditor().equals("0")){
			htmlPart1=htmlPart1.replace("<oneDAuditor/>",ControlUtil.drawChosenSelect(con,  "oneDAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneDAuditor(),false,null));	
		}else{
			edVo.setRole(edVo.getOneDAuditorTitle());
			htmlPart1=htmlPart1.replace("<oneDAuditor/>",ControlUtil.drawChosenSql(con,  "oneDAuditor",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneDAuditor(),keyConts.msgZ));	
		}
		if( edVo.getOneDAgent().equals("0")){
			htmlPart1=htmlPart1.replace("<oneDAgent/>",ControlUtil.drawChosenSelect(con,  "oneDAgent", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneDAgent(),false,null));	
		}else{
			edVo.setRole(edVo.getOneDAgentTitle());
			htmlPart1=htmlPart1.replace("<oneDAgent/>",ControlUtil.drawChosenSql(con,  "oneDAgent",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneDAgent(),keyConts.msgZ));	
		}
		/**经理**/
		htmlPart1=htmlPart1.replace("<oneMAuditorTitle/>",ControlUtil.drawChosenSelect(con,  "oneMAuditorTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E','D','M')  ", edVo.getOneMAuditorTitle(),false,null));
		htmlPart1=htmlPart1.replace("<oneMAgentTitle/>",ControlUtil.drawChosenSelect(con,  "oneMAgentTitle", "VN_ROLE", "ID", "TITLE", " ID NOT IN('U','E','D','M')  ", edVo.getOneMAgentTitle(),false,null));	
	
		if( edVo.getOneMAuditor().equals("0")){
			htmlPart1=htmlPart1.replace("<oneMAuditor/>",ControlUtil.drawChosenSelect(con,  "oneMAuditor", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneMAuditor(),false,null));	
		}else{
			
			edVo.setRole(edVo.getOneMAuditorTitle());
			htmlPart1=htmlPart1.replace("<oneMAuditor/>",ControlUtil.drawChosenSql(con,  "oneMAuditor",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneMAuditor(),keyConts.msgZ));	
		}
		if( edVo.getOneMAgent().equals("0")){
			htmlPart1=htmlPart1.replace("<oneMAgent/>",ControlUtil.drawChosenSelect(con,  "oneMAgent", "HR_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " ROLE='0'  ", edVo.getOneMAgent(),false,null));	
		}else{	
			edVo.setRole(edVo.getOneMAgentTitle());
			htmlPart1=htmlPart1.replace("<oneMAgent/>",ControlUtil.drawChosenSql(con,  "oneMAgent",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneMAgent(),keyConts.msgZ));	
		}
	
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

		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(edVo.getMsg()));
		if (edVo.isShowDataTable())
		{
			htmlPart1 = htmlPart1.replace("<drawTableM/>",	
					HtmlUtil.drawTableEdit(SqlUtil.getDeptLeaveSetData(edVo), 	
							HtmlUtil.drawTableMcheckButton(), con, out, keyConts.LRessons));
		}

		out.println(htmlPart1);
	}
	
	/**預設無資料**/
	private editProcessVO setData(editProcessVO edVo){
		edVo.setID("0");
		edVo.setDept("0");
		edVo.setOneDAuditor("0");
		edVo.setOneDAgent("0");
		edVo.setOneMAgent("0");
		edVo.setOneMAuditor("0");
		edVo.setOneEAuditor("0");
		edVo.setOneEAgent("0");
		edVo.setOneUAgent("0");
		edVo.setOneUAuditor("0");
		edVo.setOneDID("0");
		edVo.setOneMID("0");
		edVo.setThreeDID("0");
		edVo.setThreeMID("0");
		edVo.setOneUID("0");
		edVo.setOneEID("0");
		edVo.setThreeEID("0");
		edVo.setThreeUID("0");
		return edVo;
	}
	/**查詢部門無資料**/
	private editProcessVO setDeptNoData(editProcessVO edVo){
	
		edVo.setOneDAuditor("0");
		edVo.setOneDAgent("0");
		edVo.setOneMAgent("0");
		edVo.setOneMAuditor("0");
		edVo.setOneEAuditor("0");
		edVo.setOneEAgent("0");
		edVo.setOneUAgent("0");
		edVo.setOneUAuditor("0");
		edVo.setOneDID("0");
		edVo.setOneMID("0");
		edVo.setThreeDID("0");
		edVo.setThreeMID("0");
		edVo.setOneDAgentTitle("0");
		edVo.setOneDAuditorTitle("0");
		edVo.setOneMAgentTitle("0");
		edVo.setOneMAuditorTitle("0");
		edVo.setThreeDLever3("0");
		edVo.setThreeDLever4("0");
		edVo.setThreeMLever4("0");
		return edVo;
	}
	/**查詢單位無資料**/
	private editProcessVO setUnitNoData(editProcessVO edVo){
	
		edVo.setOneEAuditor("0");
		edVo.setOneEAgent("0");
		edVo.setOneUAgent("0");
		edVo.setOneUAuditor("0");
		edVo.setOneEID("0");
		edVo.setOneUID("0");
		edVo.setThreeEID("0");
		edVo.setThreeUID("0");
		edVo.setOneEAgentTitle("0");
		edVo.setOneEAuditorTitle("0");
		edVo.setOneUAgentTitle("0");
		edVo.setOneUAuditorTitle("0");
		edVo.setThreeELever1("0");
		edVo.setThreeELever2("0");
		edVo.setThreeELever3("0");
		edVo.setThreeELever4("0");
		edVo.setThreeULever2("0");
		edVo.setThreeULever3("0");
		edVo.setThreeULever4("0");
		return edVo;
	}
}

