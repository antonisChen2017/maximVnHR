package cn.com.maxim.portal.attendan;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;

public class stopWorkingWrite extends TemplatePortalPen
{

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
	
		stopWorkVO swVo = new stopWorkVO(); 
	
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(swVo,request.getParameterMap()); 
					// 查詢
					if (actText.equals("QUE")) {
						swVo.setShowDataTable(true);
						showHtml(con, out, swVo,UserInformation);
					}
					if (actText.equals("Save")) {
						swVo.setShowDataTable(true);
						// 儲存db
						String msg=DBUtil.saveStopWorking(swVo , con);
						swVo.setMsg(msg);
					
						showHtml(con, out,  swVo,UserInformation);
						
					}
					if (actText.equals("Delete")) {
						String rowID = request.getParameter("rowID");
						//delete 請假單
						boolean flag=DBUtil.delDBTableRow(SqlUtil.delDBRow("VN_STOPWORKING",rowID),con);
						swVo.setShowDataTable(true);
						if(flag){
							swVo.setMsg("刪除成功!");
						}else{
							swVo.setMsg("刪除失敗!");
						}
					
						showHtml(con, out, swVo,UserInformation);
					}
				}else{
					//預設
					swVo.setSearchDepartmen("0");
					swVo.setSearchUnit("0");
					swVo.setSearchEmployeeNo("0");
					swVo.setSearchReasons("0");
					swVo.setSearchEmployee("0");
					swVo.setAddDay("1");
					swVo.setStartStopWorkDate(DateUtil.NowDate());
					swVo.setEndStopWorkDate(DateUtil.NowDate());
					swVo.setStartTimeHhmm("0");
				
					swVo.setEndTimeHhmm("0");
				
					swVo.setNote("");
					showHtml(con, out, swVo,UserInformation);
				
				}
		}catch (Exception err)
		{
				out.println("<pre>");
				err.printStackTrace(out);
				out.println("</pre>");
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		
		
		
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
				html = ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", subSql, otVo.getSearchEmployeeNo())
						+"#"+ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", subSql, otVo.getSearchEmployeeNo());
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
		
		 if(ajax.equals("unit")){
			 String employeeID = request.getParameter("employeeID");
			 String unitID= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.UNIT_ID "),"UNIT_ID");
			 String html="";
			try
			{
				html = ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", unitID);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 out.println(html);
	 	}
	 }
	
	private void showHtml(Connection con, PrintWriter out, stopWorkVO swVo , UserDescriptor UserInformation) throws SQLException {
		HtmlUtil hu=new HtmlUtil();
		String htmlPart1=hu.gethtml(UrlUtil.stopWorkingWrite);
		String DepartmentID=DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo());
		htmlPart1=htmlPart1.replace("&UserEmployeeNo", 	UserInformation.getUserEmployeeNo());
	
		htmlPart1=htmlPart1.replace("&hiddenEmployeeNo",ControlUtil.drawHidden(DepartmentID, "searchDepartmen"));
	
		htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DepartmentID + "'", swVo.getSearchUnit()));
		
		htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + DepartmentID + "'", swVo.getSearchEmployeeNo()));
	
		htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + DepartmentID + "'", swVo.getSearchEmployee()));
	
		htmlPart1=htmlPart1.replace("<searchReasons/>",ControlUtil.drawSelectShared(con, out, "searchReasons", "VN_STOPWORKRESON", "ID", "STOPRESON", "", swVo.getSearchReasons(),true));
	
		htmlPart1=htmlPart1.replace("<addDay/>",HtmlUtil.getSpinnerDiv("addDay",swVo.getAddDay()));
	
		htmlPart1=htmlPart1.replace("<Note/>",HtmlUtil.getNoteDiv("note", swVo.getNote()));
	
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(swVo.getMsg()));
		htmlPart1=htmlPart1.replace("<getDaterangeHtml/>",HtmlUtil.getDaterangeHtml(
				  swVo.getStartStopWorkDate()
				 ,swVo.getEndStopWorkDate()
				 ,swVo.getStartTimeHhmm()
				 , swVo.getEndTimeHhmm() 
				));
		if(swVo.isShowDataTable()){
			System.out.println("sql  :  "+SqlUtil.getStopWork(swVo));
			htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawStopWorking(
					SqlUtil.getStopWork(swVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageSave));
		}
		
	    out.println(htmlPart1);
	    }
}
