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
import cn.com.maxim.portal.attendan.vo.editSupervisorVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.travelAndTransferVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;

public class rev_TravelAndTransfer extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(ad_editLholiday.class);

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
	        travelAndTransferVO taVo = new travelAndTransferVO();
	        taVo.setActionURI(ActionURI);
		logger.info("actText :"+actText);	
		try
		{
		
			if (actText != null)
			{
				BeanUtils.populate(taVo, request.getParameterMap());
				taVo.setEmployeeNoL(request.getParameter("EmployeeNoL"));
				taVo.setStartDayL(request.getParameter("StartDayL"));
				taVo.setEndDayL(request.getParameter("EndDayL"));
				taVo.setNoteL(request.getParameter("NoteL"));
				if (actText.equals("SaveO"))
				{
					
				 
					taVo.setStatus("R");//調動
					taVo.setStartDayL(request.getParameter("StartDayR"));
					taVo.setNoteL(request.getParameter("NoteR"));
					taVo.setEmployeeNoL(request.getParameter("EmployeeNoR"));
					logger.info(" taVo.getEmployeeNoL() :"+ taVo.getEmployeeNoL());	
				  	logger.info("insterSupplementData :"+SqlUtil.insterSupplementData(taVo));	
				  	if(DBUtil.updateSql(SqlUtil.insterSupplementData(taVo), con)){
				    	    taVo.setMsg(keyConts.saveOK);
				    	}else{
				    	    taVo.setMsg(keyConts.saveOK);
				    	}
					//建立資料
				    	taVo.setShowDataTable(true);
					taVo.setNoteL("");
					taVo.setNoteR("");
					taVo.setStartDayR(DateUtil.NowDate());
				    	taVo.setTab("o");
					showHtml(con, out, taVo, UserInformation);
					
				}
				// 出差資料L
				if (actText.equals("SaveN"))
				{
					//檢查是否有工號
				    	taVo.setStatus("L");//出差
					logger.info(" taVo.toString() :"+ taVo.toString());	
				 	logger.info(" taVo.getEmployeeNoL() :"+ taVo.getEmployeeNoL());	
					logger.info("insterSupplementData :"+SqlUtil.insterSupplementData(taVo));	
				   
				    	if(DBUtil.updateSql(SqlUtil.insterSupplementData(taVo), con)){
				    	    taVo.setMsg(keyConts.saveOK);
				    	}else{
				    	    taVo.setMsg(keyConts.saveOK);
				    	}
					//建立資料
				    	taVo.setShowDataTable(true);
					taVo.setNoteL("");
					taVo.setNoteR("");
					taVo.setStartDayL(DateUtil.NowDate());
				    	taVo.setTab("n");
					showHtml(con, out, taVo	, UserInformation);
				}
				//查詢原資料庫主管資料
				if (actText.equals("QueryO"))
				{
				    	
				    	taVo.setShowDataTable(true);
					//edVo.setMsg("查詢原資料庫主管資料");
				    	taVo.setTab("o");
					showHtml(con, out, taVo, UserInformation);
				}
				//查詢編輯新主管資料
				if (actText.equals("QueryN"))
				{
				    	taVo.setShowDataTable(true);
				    	taVo.setTab("n");
					showHtml(con, out, taVo, UserInformation);
				}
				
				if (actText.equals("Delete"))
				{
				    	taVo.setRowID(request.getParameter("rowID"));
				    	logger.info(" getRowID:"+ taVo.getRowID());	
				    	logger.info(" deleteSupplement:"+ SqlUtil.deleteSupplement(taVo));	
					if(DBUtil.updateSql(SqlUtil.deleteSupplement(taVo), con)){
						taVo.setMsg(keyConts.editOK);
					}else{
						taVo.setMsg(keyConts.editNO);
					}
					taVo.setShowDataTable(true);
					taVo.setNoteL("");
					taVo.setNoteR("");
					taVo.setStartDayL(DateUtil.NowDate());
					taVo.setTab(request.getParameter("Romance"));
					showHtml(con, out, taVo, UserInformation);
				}
			}
			else
			{
				// 預設
			
				taVo.setTab("n");
				taVo.setStartDayL(DateUtil.NowDate());
				taVo.setStartDayR(DateUtil.NowDate());
				taVo.setEndDayL(DateUtil.NowDate());
				taVo.setNoteL("");
				taVo.setNoteR("");
				taVo.setShowDataTable(false);
				showHtml(con, out, taVo, UserInformation);

			}
		}
		catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
		
	}
	
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{

        	    if(ajax.equals("SwUnitL")){
        		 String searchDepartmen = request.getParameter("searchDepartmen");
        		 String unitID= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(searchDepartmen,"  V.UNIT_ID "),"UNIT_ID");
        		 String html="";
        		 overTimeVO otVo = new overTimeVO(); 
        		 otVo.setSearchEmployeeNo("0");
        		try
        		{
        			html = ControlUtil.drawChosenSelect(con, "UnitL", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + searchDepartmen + "'", "0",false,null)
        					+"%"+ControlUtil.drawChosenSelect(con,  "EmployeeNoL", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "UNIT_ID='" + unitID + "'", otVo.getSearchEmployeeNo(),false,null)
        					+"%"+ControlUtil.drawChosenSelect(con,  "EmployeeL", "HR_EMPLOYEE", "ID", "EMPLOYEE", "UNIT_ID='" + unitID + "'", otVo.getSearchEmployeeNo(),false,null);
        		}
        		catch (SQLException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		//System.out.println("html : "+html);
        		 out.println(html);
        	}
        	if(ajax.equals("SwUnitR")){
           		 String searchDepartmen = request.getParameter("searchDepartmen");
           		 String unitID= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(searchDepartmen,"  V.UNIT_ID "),"UNIT_ID");
           		 String html="";
           		 overTimeVO otVo = new overTimeVO(); 
           		 otVo.setSearchEmployeeNo("0");
           		try
           		{
           			html = ControlUtil.drawChosenSelect(con, "UnitR", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + searchDepartmen + "'", "0",false,null)
           					+"%"+ControlUtil.drawChosenSelect(con,  "EmployeeNoR", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "UNIT_ID='" + unitID + "'", otVo.getSearchEmployeeNo(),false,null)
           					+"%"+ControlUtil.drawChosenSelect(con,  "EmployeeR", "HR_EMPLOYEE", "ID", "EMPLOYEE", "UNIT_ID='" + unitID + "'", otVo.getSearchEmployeeNo(),false,null);
           		}
           		catch (SQLException e)
           		{
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		}
           		//System.out.println("html : "+html);
           		 out.println(html);
        	}
        	
        	if(ajax.equals("SwDUnitL")){
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
        			html = ControlUtil.drawChosenSelect(con,  "EmployeeNoL", "HR_EMPLOYEE", "ID", "EMPLOYEENO", subSql, otVo.getSearchEmployeeNo(),false,null)
        					+"%"+ControlUtil.drawChosenSelect(con,  "EmployeeL", "HR_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo(),false,null);
        		}
        		catch (SQLException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		// System.out.println("html : "+html);
        		 out.println(html);
        	}
        	if(ajax.equals("SwDUnitR")){
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
           			html = ControlUtil.drawChosenSelect(con,  "EmployeeNoR", "HR_EMPLOYEE", "ID", "EMPLOYEENO", subSql, otVo.getSearchEmployeeNo(),false,null)
           					+"%"+ControlUtil.drawChosenSelect(con,  "EmployeeR", "HR_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo(),false,null);
           		}
           		catch (SQLException e)
           		{
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		}
           		// System.out.println("html : "+html);
           		 out.println(html);
        	}
        	
        	 if(ajax.equals("duties")){
        		  String employeeID = request.getParameter("employeeID");
        		  String entryDate= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.ENTRYDATE "),"ENTRYDATE");
        		  String duties= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.DUTIES "),"DUTIES");
        		  String data= "[{ \"duties\":\""+duties+"\" , \"entryDate\":\""+entryDate+"\" }]";
        		// System.out.println("TimeDiv :"+TimeDiv);
        		  out.println(data);
        	}
        	 if(ajax.equals("SwUnitEmpNo")){
        		 String searchUnit = request.getParameter("searchUnit");
        		 overTimeVO otVo = new overTimeVO(); 
        		 otVo.setSearchEmployeeNo("0");
        		 try
        			{
        			 out.println(ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "UNIT_ID='" + searchUnit + "'", otVo.getSearchEmployeeNo(),false,null));
        			}
        			catch (SQLException e)
        			{
        				out.println("");
        			}
        	 }
        	 if(ajax.equals("SwUnitEmp")){
        		 String searchUnit = request.getParameter("searchUnit");
        		 overTimeVO otVo = new overTimeVO(); 
        		 otVo.setSearchEmployeeNo("0");
        		 try
        			{
        			 out.println(ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "UNIT_ID='" + searchUnit + "'", otVo.getSearchEmployeeNo(),false,null));
        			}
        			catch (SQLException e)
        			{
        				out.println("");
        			}
        	 }
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
        	 if(ajax.equals("SwEmpNoL")){
        	      String searchDepartmen = request.getParameter("searchDepartmen");
    		 	overTimeVO otVo = new overTimeVO(); 
    			otVo.setSearchEmployeeNo("0");
        		try
        		{
        			out.println(ControlUtil.drawChosenSelect(con,  "EmployeeNoL", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployeeNo(),false,null));
        		}
        		catch (SQLException e)
        		{
        			out.println("");
        		}
        	 }
        	 if(ajax.equals("SwEmpNoR")){
        	     String searchDepartmen = request.getParameter("searchDepartmen");
   		 	overTimeVO otVo = new overTimeVO(); 
   			otVo.setSearchEmployeeNo("0");
           		try
           		{
           			out.println(ControlUtil.drawChosenSelect(con,  "EmployeeNoR", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployeeNo(),false,null));
           		}
           		catch (SQLException e)
           		{
           			out.println("");
           		}
        	 }
        	 if(ajax.equals("SwEmpL")){
        		String searchDepartmen = request.getParameter("searchDepartmen");
        		overTimeVO otVo = new overTimeVO(); 
        		otVo.setSearchEmployee("0");
        		try
        		{
        			out.println(ControlUtil.drawChosenSelect(con,  "EmployeeL", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployee(),false,null));
        		}
        		catch (SQLException e)
        		{
        			out.println("");
        		}
        	}
        	 if(ajax.equals("SwEmpR")){
         		String searchDepartmen = request.getParameter("searchDepartmen");
         		overTimeVO otVo = new overTimeVO(); 
         		otVo.setSearchEmployee("0");
         		try
         		{
         			out.println(ControlUtil.drawChosenSelect(con,  "EmployeeR", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + searchDepartmen + "'", otVo.getSearchEmployee(),false,null));
         		}
         		catch (SQLException e)
         		{
         			out.println("");
         		}
        	 }
	}
	
	private void showHtml(Connection con, PrintWriter out, travelAndTransferVO taVo, UserDescriptor UserInformation) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String htmlPart1 = hu.gethtml(htmlConsts.html_rev_TravelAndTransfer);
		/*if( lcVo.getSearchEmployee().equals("0")){
		     lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNODate(UserInformation.getUserName()) ,eo);	
		}else{
			
		     logger.info("SqlUtil getEmployeeID"+SqlUtil.getEmployeeID(lcVo.getSearchEmployee()));
			 lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeID(lcVo.getSearchEmployee()) ,eo);	
		}
		String UnitSql="";
		if(lcVo.getSearchDepartmen( ).equals("0")){
			UnitSql=" DEPARTMENT_ID='0' ";
		}else{
			UnitSql=" DEPARTMENT_ID= '"+lcVo.getSearchDepartmen( )+"'";
		}*/
		htmlPart1 = htmlPart1.replace("<ActionURI/>", taVo.getActionURI());
		htmlPart1=htmlPart1.replace("<UnitR/>",ControlUtil.drawChosenSelect(con,  "UnitR", "VN_UNIT", "ID", "UNIT", " DEPARTMENT_ID='0' ", taVo.getDeptR(),false,null));
		htmlPart1=htmlPart1.replace("<UnitL/>",ControlUtil.drawChosenSelect(con,  "UnitL", "VN_UNIT", "ID", "UNIT", " DEPARTMENT_ID='0' ", taVo.getDeptL(),false,null));
		htmlPart1=htmlPart1.replace("<DeptR/>",	ControlUtil.drawChosenSelect(con,  "DeptR", "VN_DEPARTMENT", "ID", "DEPARTMENT", null,taVo.getDeptR( ),false,null));
		htmlPart1=htmlPart1.replace("<DeptL/>",	ControlUtil.drawChosenSelect(con,  "DeptL", "VN_DEPARTMENT", "ID", "DEPARTMENT", null,taVo.getDeptR( ),false,null));
		htmlPart1=htmlPart1.replace("<EmployeeNoR/>",ControlUtil.drawChosenSelect(con, "EmployeeNoR", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + 	taVo.getEmployeeNoR()+ "'", taVo.getEmployeeNoR(),false,null));
		htmlPart1=htmlPart1.replace("<EmployeeR/>",ControlUtil.drawChosenSelect(con,  "EmployeeL", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + taVo.getEmployeeR() + "'", taVo.getEmployeeR(),false,null));
		htmlPart1=htmlPart1.replace("<EmployeeNoL/>",ControlUtil.drawChosenSelect(con, "EmployeeNoL", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + 	taVo.getEmployeeNoL()+ "'", taVo.getEmployeeNoL(),false,null));
		htmlPart1=htmlPart1.replace("<EmployeeL/>",ControlUtil.drawChosenSelect(con,  "EmployeeL", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + taVo.getEmployeeL() + "'", taVo.getEmployeeL(),false,null));
		htmlPart1=htmlPart1.replace("<TravelDate/>", HtmlUtil.getDateDivSw("StartDayL", "EndDayL", taVo.getStartDayL(), taVo.getEndDayL()));
		htmlPart1=htmlPart1.replace("<NoteL/>",HtmlUtil.getNoteDiv("NoteL", taVo.getNoteL()));
		htmlPart1=htmlPart1.replace("<StartDayR/>",HtmlUtil.getDateDiv("StartDayR",taVo.getStartDayR()));
		htmlPart1=htmlPart1.replace("<NoteR/>",HtmlUtil.getNoteDiv("NoteR", taVo.getNoteR()));
		htmlPart1=htmlPart1.replace("<tab/>",taVo.getTab());
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(taVo.getMsg()));
		if (taVo.isShowDataTable())
		{
			if(taVo.getTab().equals("o")){
				logger.info("queryEmpLeverTrue :"+SqlUtil.getSupplementR(taVo));	
					htmlPart1 = htmlPart1.replace("<drawTableM/>",
							HtmlUtil.drawStopWorking(SqlUtil.getSupplementR(taVo), 
								HtmlUtil.drawTableMcheckButton(), con, out, keyConts.ColUnit));
			}
			if(taVo.getTab().equals("n")){
				logger.info("getEmpNoData :"+SqlUtil.getSupplementL(taVo));	
				htmlPart1 = htmlPart1.replace("<drawTableM/>",
						HtmlUtil.drawStopWorking(SqlUtil.getSupplementL(taVo), 
							HtmlUtil.drawTableMcheckButton(), con, out, keyConts.pageSave));
			}
	
		}

		out.println(htmlPart1);
	}
}
