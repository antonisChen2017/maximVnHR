package cn.com.maxim.portal.attendan;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;

public class empSettUnit  extends TemplatePortalPen
{
	
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		
		leaveCardVO lcVo = new leaveCardVO(); 
		HtmlUtil hu=new HtmlUtil();

		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(lcVo,request.getParameterMap()); 
					// 查詢
					if (actText.equals("QUE")) {
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation);
					}
					if (actText.equals("update")) {
						lcVo.setShowDataTable(true);
						String Sql=hu.gethtml(UrlUtil.sql_updateUnit);
					    Sql=Sql.replace("<UNITID>", lcVo.getSearchUnit());
					    Sql=Sql.replace("<EMPID>", lcVo.getSearchEmployee());
						boolean flag=DBUtil.updateSql(Sql, con) ;
						if(flag){
							lcVo.setMsg("更新單位成功!");
						}else{
							lcVo.setMsg("更新單位失敗!");
						}
						showHtml(con, out, lcVo,UserInformation);
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
					showHtml(con, out, lcVo,UserInformation);
				
				}
		}catch (Exception err)
		{
				out.println("<pre>");
				err.printStackTrace(out);
				out.println("</pre>");
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		 

		if(ajax.equals("SwUnit")){
			 String searchDepartmen = request.getParameter("searchDepartmen");
			 String html="";
			
			try
			{
				html = ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + searchDepartmen + "'", "0");
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
				//overTimeVO otVo = new overTimeVO(); 
			//	otVo.setSearchEmployee("0");
				System.out.println("searchDepartmen : "+searchDepartmen);
				try
				{
					out.println(ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " DEPARTMENT_ID='" + searchDepartmen + "'", "0"));
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
		 
		 
		
		
	 }
	
	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo , UserDescriptor UserInformation) throws SQLException {
		
		
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(UrlUtil.empSettUnit);
			htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	ControlUtil.drawSelectShared(con, out, "searchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", "",lcVo.getSearchDepartmen( )));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "EMPLOYEENO", "EMPLOYEE", " DEPARTMENT_ID='" + lcVo.getSearchDepartmen()+"'", lcVo.getSearchEmployee()));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", " DEPARTMENT_ID='" + lcVo.getSearchDepartmen()+"'", lcVo.getSearchUnit()));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(lcVo.getMsg()));
			if(lcVo.isShowDataTable()){
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawLeaveCardTable(
						SqlUtil.getDept(lcVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageMsList));
			}
		
			
		    out.println(htmlPart1);
		    }
}
