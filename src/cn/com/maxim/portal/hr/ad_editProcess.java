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
				    	edVo=setData(edVo);
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
					logger.info("saveDept 1");
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
					edVo.setUnit( request.getParameter("Unit"));
					edVo.setGroup( request.getParameter("Group"));
					logger.info("saveDept  2");
					showHtml(con, out, edVo, UserInformation);
				}
				
				// 查詢單位資料
				if (actText.equals("queryUnit")){
				
					edVo.setDept( request.getParameter("Dept"));
					edVo.setUnit( request.getParameter("Unit"));
					edVo.setGroup( request.getParameter("Group"));
					//logger.info("getUnit "+edVo.getUnit());
					int COUNT=0,COUNTD=0;
					String Ucount=DBUtil.queryDBField(con,SqlUtil.queryUnitLeaveCount(edVo), "COUNT");
					//logger.info("Ucount "+Ucount);
					if(Ucount!=null && !Ucount.equals("")){
						 COUNT=Integer.valueOf(Ucount);
					}
					logger.info("queryUnit COUNT "+COUNT);
					if(COUNT>0){
						edVo=ad_editProcessDAO.getUnitProcess(con,edVo);	
					
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
					
					
					edVo.setDept( request.getParameter("Dept"));
					edVo.setUnit( request.getParameter("Unit"));
					edVo.setGroup( request.getParameter("Group"));
					int COUNT=0;
					String Ucount=DBUtil.queryDBField(con,SqlUtil.queryUnitLeaveCount(edVo), "COUNT");
					if(Ucount!=null && !Ucount.equals("")){
						 COUNT=Integer.valueOf(Ucount);
					}
					if(COUNT>0){
						logger.info("updateUnitProcess");
						ad_editProcessDAO.updateUnitProcess(con,edVo);	
						edVo=ad_editProcessDAO.getUnitProcess(con,edVo);	
						edVo.setMsg(keyConts.editOK);
					}else{
						logger.info("insterUnitProcess");
						ad_editProcessDAO.insterUnitProcess(con,edVo);	
						edVo=ad_editProcessDAO.getUnitProcess(con,edVo);	
						edVo.setMsg(keyConts.saveOK);
					}
					edVo.setDept( request.getParameter("Dept"));
					edVo.setUnit( request.getParameter("Unit"));
					edVo.setGroup( request.getParameter("Group"));
					//再查出資料
					showHtml(con, out, edVo, UserInformation);
				}
				if (actText.equals("saveGroup")){
				    edVo.setDept( request.getParameter("Dept"));
				    edVo.setUnit( request.getParameter("Unit"));
				    edVo.setGroup( request.getParameter("Group"));
				    int COUNT=0;
				   String Ucount=DBUtil.queryDBField(con,SqlUtil.queryGroupLeaveCount(edVo.getGroup()), "COUNT");
				   if(Ucount!=null && !Ucount.equals("")){
						COUNT=Integer.valueOf(Ucount);
				    }
				   if(COUNT>0){
					logger.info("updateGroupProcess");
					ad_editProcessDAO.updateGroupProcess(con,edVo);	
					edVo=ad_editProcessDAO.getGroupProcess(con,edVo);	
					edVo.setMsg(keyConts.editOK);
				}else{
					logger.info("insterGroupProcess");
					ad_editProcessDAO.insterGroupProcess(con,edVo);	
					edVo=ad_editProcessDAO.getGroupProcess(con,edVo);	
					edVo.setMsg(keyConts.saveOK);
				}
				   edVo.setDept( request.getParameter("Dept"));
					edVo.setUnit( request.getParameter("Unit"));
					edVo.setGroup( request.getParameter("Group"));
					
				  //再查出資料
				  showHtml(con, out, edVo, UserInformation);
				}
				/**查出組別資料**/
				if (actText.equals("queryGroup")){
				    
				    edVo.setDept( request.getParameter("Dept"));
				    edVo.setUnit( request.getParameter("Unit"));
				    edVo.setGroup( request.getParameter("Group"));
				    
				         int COUNT=0,COUNTD=0;
				         logger.info("SqlUtil queryGroupLeaveCount   "+SqlUtil.queryGroupLeaveCount(edVo.getGroup()));
				         String Gcount=DBUtil.queryDBField(con,SqlUtil.queryGroupLeaveCount(edVo.getGroup()), "COUNT");
					   if(Gcount!=null && !Gcount.equals("")){
							COUNT=Integer.valueOf(Gcount);
					    }
					//logger.info("Ucount "+Ucount);
					
					logger.info("queryGroup COUNT "+COUNT);
					if(COUNT>0){
						edVo=ad_editProcessDAO.getGroupProcess(con,edVo);	
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
						String Ucount=DBUtil.queryDBField(con,SqlUtil.queryUnitLeaveCount(edVo), "COUNT");
						if(Ucount!=null && !Ucount.equals("")){
							COUNTD=Integer.valueOf(Ucount);
						}
						if(COUNTD>0){
						        edVo.setGroup("0");
							edVo=ad_editProcessDAO.getUnitProcess(con,edVo);	
						}
						edVo.setMsg(keyConts.processGroupNoData);
						edVo=setGroupNoData(edVo);
					}
				    
					    edVo.setDept( request.getParameter("Dept"));
					    edVo.setUnit( request.getParameter("Unit"));
					    edVo.setGroup( request.getParameter("Group"));
				    
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
		String GroupSql = "";
		if (edVo.getUnit().equals("0")) {
		    GroupSql = " Unit='0' ";
		} else {
		    GroupSql = " Unit= '" + edVo.getUnit() + "'";
		}
		
		htmlPart1=htmlPart1.replace("<Group/>",  ControlUtil.drawChosenSelect(con, "Group", "VN_GROUP", "[GROUP]","[GROUP]", GroupSql, edVo.getGroup(), false, null));
		htmlPart1=htmlPart1.replace("<oneDID/>",ControlUtil.drawHidden(edVo.getOneDID(), "oneDID"));
		htmlPart1=htmlPart1.replace("<oneMID/>",ControlUtil.drawHidden(edVo.getOneMID(), "oneMID"));
		htmlPart1=htmlPart1.replace("<threeDID/>",ControlUtil.drawHidden(edVo.getThreeDID(), "threeDID"));
		htmlPart1=htmlPart1.replace("<threeMID/>",ControlUtil.drawHidden(edVo.getThreeMID(), "threeMID"));
		htmlPart1=htmlPart1.replace("<oneEID/>",ControlUtil.drawHidden(edVo.getOneEID(), "oneEID"));
		htmlPart1=htmlPart1.replace("<oneUID/>",ControlUtil.drawHidden(edVo.getOneUID(), "oneUID"));
		htmlPart1=htmlPart1.replace("<threeEID/>",ControlUtil.drawHidden(edVo.getThreeEID(), "threeEID"));
		htmlPart1=htmlPart1.replace("<threeUID/>",ControlUtil.drawHidden(edVo.getThreeUID(), "threeUID"));
		htmlPart1=htmlPart1.replace("<threeGEID/>",ControlUtil.drawHidden(edVo.getThreeGEID(), "threeGEID"));
		htmlPart1=htmlPart1.replace("<oneGEID/>",ControlUtil.drawHidden(edVo.getOneGEID(), "oneGEID"));
	
		/**組長**/
		edVo.setRole("G");
		htmlPart1=htmlPart1.replace("<threeGELever0/>",ControlUtil.drawChosenSql(con,  "threeGELever0",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeGELever0(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<oneGELever0/>",ControlUtil.drawChosenSql(con,  "oneGELever0",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneGELever0(),keyConts.msgZ));	
	
		edVo.setRole("U");
		htmlPart1=htmlPart1.replace("<threeELever1/>",ControlUtil.drawChosenSql(con,  "threeELever1",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeELever1(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<oneELever1/>",ControlUtil.drawChosenSql(con,  "oneELever1",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneELever1(),keyConts.msgZ));	
		/**組別用**/
		htmlPart1=htmlPart1.replace("<threeGELever1/>",ControlUtil.drawChosenSql(con,  "threeGELever1",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeGELever1(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<oneGELever1/>",ControlUtil.drawChosenSql(con,  "oneGELever1",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneGELever1(),keyConts.msgZ));	
		
		edVo.setRole("D");
		htmlPart1=htmlPart1.replace("<threeELever2/>",ControlUtil.drawChosenSql(con,  "threeELever2",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeELever2(),keyConts.msgZ));		
		htmlPart1=htmlPart1.replace("<threeULever2/>",ControlUtil.drawChosenSql(con,  "threeULever2",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeULever2(),keyConts.msgZ));	
		htmlPart1=htmlPart1.replace("<oneELever2/>",ControlUtil.drawChosenSql(con,  "oneELever2",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneELever2(),keyConts.msgZ));		
		htmlPart1=htmlPart1.replace("<oneULever2/>",ControlUtil.drawChosenSql(con,  "oneULever2",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneULever2(),keyConts.msgZ));	
		/**組別用**/
		htmlPart1=htmlPart1.replace("<oneGELever2/>",ControlUtil.drawChosenSql(con,  "oneGELever2",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneGELever2(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeGELever2/>",ControlUtil.drawChosenSql(con,  "threeGELever2",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeGELever2(),keyConts.msgZ));	
		
		edVo.setRole("M");
		htmlPart1=htmlPart1.replace("<threeELever3/>",ControlUtil.drawChosenSql(con,  "threeELever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeELever3(),keyConts.msgZ));	
		htmlPart1=htmlPart1.replace("<threeULever3/>",ControlUtil.drawChosenSql(con,  "threeULever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeULever3(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeDLever3/>",ControlUtil.drawChosenSql(con,  "threeDLever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeDLever3(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<oneELever3/>",ControlUtil.drawChosenSql(con,  "oneELever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneELever3(),keyConts.msgZ));	
		htmlPart1=htmlPart1.replace("<oneULever3/>",ControlUtil.drawChosenSql(con,  "oneULever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneULever3(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<oneDLever3/>",ControlUtil.drawChosenSql(con,  "oneDLever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneDLever3(),keyConts.msgZ));
		/**組別用**/
		htmlPart1=htmlPart1.replace("<oneGELever3/>",ControlUtil.drawChosenSql(con,  "oneGELever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneGELever3(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeGELever3/>",ControlUtil.drawChosenSql(con,  "threeGELever3",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeGELever3(),keyConts.msgZ));	
		
		edVo.setRole("B");
		htmlPart1=htmlPart1.replace("<threeELever4/>",ControlUtil.drawChosenSql(con,  "threeELever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeELever4(),keyConts.msgZ));	
		htmlPart1=htmlPart1.replace("<threeULever4/>",ControlUtil.drawChosenSql(con,  "threeULever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeULever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeDLever4/>",ControlUtil.drawChosenSql(con,  "threeDLever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeDLever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeMLever4/>",ControlUtil.drawChosenSql(con,  "threeMLever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeMLever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<oneELever4/>",ControlUtil.drawChosenSql(con,  "oneELever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneELever4(),keyConts.msgZ));	
		htmlPart1=htmlPart1.replace("<oneULever4/>",ControlUtil.drawChosenSql(con,  "oneULever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneULever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<oneDLever4/>",ControlUtil.drawChosenSql(con,  "oneDLever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneDLever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<oneMLever4/>",ControlUtil.drawChosenSql(con,  "oneMLever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneMLever4(),keyConts.msgZ));
		/**組別用**/
		htmlPart1=htmlPart1.replace("<oneGELever4/>",ControlUtil.drawChosenSql(con,  "oneGELever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getOneGELever4(),keyConts.msgZ));
		htmlPart1=htmlPart1.replace("<threeGELever4/>",ControlUtil.drawChosenSql(con,  "threeGELever4",SqlUtil.getEmpDUdata(edVo.getRole()), edVo.getThreeGELever4(),keyConts.msgZ));	
	
		
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
		edVo.setUnit("0");
		edVo.setGroup("0");
		edVo.setOneDID("0");
		edVo.setOneMID("0");
		edVo.setThreeDID("0");
		edVo.setThreeMID("0");
		edVo.setOneUID("0");
		edVo.setOneEID("0");
		edVo.setThreeEID("0");
		edVo.setThreeUID("0");
		edVo.setThreeGEID("0");
		edVo.setOneGEID("0");
		return edVo;
	}
	/**查詢部門無資料**/
	private editProcessVO setDeptNoData(editProcessVO edVo){
	
		edVo.setUnit("0");
		edVo.setOneDID("0");
		edVo.setOneMID("0");
		edVo.setThreeDID("0");
		edVo.setThreeMID("0");
	
		edVo.setThreeDLever3("0");
		edVo.setThreeDLever4("0");
		edVo.setThreeMLever4("0");
		
		edVo.setOneDLever3("0");
		edVo.setOneDLever4("0");
		edVo.setOneMLever4("0");
		return edVo;
	}
	/**查詢單位無資料**/
	private editProcessVO setUnitNoData(editProcessVO edVo){
	
	
		edVo.setOneEID("0");
		edVo.setOneUID("0");
		edVo.setThreeEID("0");
		edVo.setThreeUID("0");
	
		edVo.setThreeELever1("0");
		edVo.setThreeELever2("0");
		edVo.setThreeELever3("0");
		edVo.setThreeELever4("0");
		edVo.setThreeULever2("0");
		edVo.setThreeULever3("0");
		edVo.setThreeULever4("0");
		
		edVo.setOneELever1("0");
		edVo.setOneELever2("0");
		edVo.setOneELever3("0");
		edVo.setOneELever4("0");
		edVo.setOneULever2("0");
		edVo.setOneULever3("0");
		edVo.setOneULever4("0");
		return edVo;
	}
	
	/**查詢單位無資料**/
	private editProcessVO setGroupNoData(editProcessVO edVo){
	
	
		edVo.setOneGEID("0");
		edVo.setThreeGEID("0");
		edVo.setThreeGELever0("0");
		edVo.setThreeGELever1("0");
		edVo.setThreeGELever2("0");
		edVo.setThreeGELever3("0");
		edVo.setThreeGELever4("0");
		edVo.setOneGELever0("0");
		edVo.setOneGELever1("0");
		edVo.setOneGELever2("0");
		edVo.setOneGELever3("0");
		edVo.setOneGELever4("0");
		
		return edVo;
	}
}

