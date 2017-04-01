package cn.com.maxim.portal.attendan;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.maxim.DB.DBUtils;
import cn.com.maxim.controls.TableControl;
import cn.com.maxim.htmlDBcontrol.WebDBControl;
import cn.com.maxim.htmlDBcontrol.WebDBForm;
import cn.com.maxim.htmlDBcontrol.WebDBSelect;
import cn.com.maxim.htmlcontrol.WebHidden;
import cn.com.maxim.htmlcontrol.WebLabel;
import cn.com.maxim.htmlcontrol.WebSelect;
import cn.com.maxim.htmltable.DBColumn;
import cn.com.maxim.htmltable.HighlightColumn;
import cn.com.maxim.htmltable.HighlightRule;
import cn.com.maxim.htmltable.WebDBTable;
import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
import cn.com.maxim.portal.webUI.WebDBTableEx;

public class saveOvertime extends TemplatePortalPen
{

	static WebDBSelect APSelector;
	static WebLabel APlLbel;
	static WebDBControl APlDBControl;
	static WebHidden APHidden;
	static WebDBForm Form;
	
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		
		// this.Form.setAction(ActionURI);
		
		HtmlUtil hu=new HtmlUtil();
		out.write(hu.getUICss());
		out.write(HtmlUtil.getTitleUIDiv("加班申請單", "填寫"));

		String actText = request.getParameter("act");
		overTimeVO otVo = new overTimeVO(); 
		try
		{
			if (actText != null)
			{
				String SearchDepartmen = request.getParameter("SearchDepartmen");
				String SearchUnit = request.getParameter("SearchUnit");
				String SearchReasons = request.getParameter("SearchReasons");
				String queryDate = request.getParameter("queryDate");
				String startTimeHh = request.getParameter("startTimeHh");
				String startTimemm = request.getParameter("startTimemm");
				String endTimeHh = request.getParameter("endTimeHh");
				String endTimemm = request.getParameter("endTimemm");
				String addTime = request.getParameter("addTime");
				String SearchEmployee = request.getParameter("SearchEmployee");
				String SearchEmployeeNo = request.getParameter("SearchEmployeeNo");
				if (actText.equals("Qwe"))
				{
					// 查詢UI
					setHtml(con, out, SearchReasons, SearchUnit, SearchEmployee,SearchEmployeeNo,addTime, queryDate, 
							UserInformation.getUserEmployeeNo(),startTimeHh,startTimemm,endTimeHh,endTimemm,"");
					// 輸出查詢UI
					out.write(HtmlUtil.drawTableM(SqlUtil.getOvertimeTotal(SearchDepartmen, "0", "0", queryDate),
							SqlUtil.getOvertime(otVo),
							SqlUtil.getOvertimeStatus(SearchDepartmen, queryDate),"",  con, out,UrlUtil.pageSave));
				}
				else if (actText.equals("Save")) //紀錄
				{
					// 儲存db
					String msg=DBUtil.saveTable(otVo , con);

					// 查詢UI
					setHtml(con, out, SearchReasons,SearchUnit, SearchEmployee,SearchEmployeeNo,addTime, queryDate, 
							UserInformation.getUserEmployeeNo(),startTimeHh,startTimemm,endTimeHh,endTimemm,msg);
					// 輸出查詢UI
					out.write(HtmlUtil.drawTableM(SqlUtil.getOvertimeTotal(SearchDepartmen, "0", "0", queryDate),
							SqlUtil.getOvertime(otVo),
							SqlUtil.getOvertimeStatus(SearchDepartmen, queryDate),"",   con, out,UrlUtil.pageSave));

				}
				if (actText.equals("Delete")){
						String deleteID = request.getParameter("deleteID");
						//delete 請假單
						String resultID = DBUtil.queryDBID(con, SqlUtil.getOvertimeID(SearchDepartmen, queryDate));
						DBUtil.delOvertimeS(deleteID,con);
						// 查詢UI
						setHtml(con, out, SearchReasons, SearchUnit, SearchEmployee,SearchEmployeeNo,addTime, queryDate, 
								UserInformation.getUserEmployeeNo(),startTimeHh,startTimemm,endTimeHh,endTimemm,"");
						// 輸出查詢UI
						out.write(HtmlUtil.drawTableM(SqlUtil.getOvertimeTotal(SearchDepartmen, "0", "0", queryDate),
								SqlUtil.getOvertime(otVo),
								SqlUtil.getOvertimeStatus(SearchDepartmen, queryDate),"", con, out,UrlUtil.pageSave));
				}
				

			}
			else
			{

				// 查詢UI
				setHtml(con, out, "0", "0", "0","0", "1", DateUtil.NowDate(), UserInformation.getUserEmployeeNo(),"","","","","");
			}
			out.write(HtmlUtil.getFooterUIDiv());
			out.write(hu.getUIJs());
			out.write(HtmlUtil.getJsDwGearingDiv("SearchEmployee", "SearchEmployeeNo"));
		}
		catch (Exception err)
		{
			out.println("<pre>");
			err.printStackTrace(out);
			out.println("</pre>");
		}
	}

	public void initPen(Connection con) throws SQLException
	{
	}

	private void drawSelectShared(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String Filter, String SelectedOption) throws SQLException
	{

		APSelector = new WebDBSelect(name, tableName, valueField, DisplayField, Filter);
		APSelector.setClass("select2_category form-control");
		APSelector.setID(name);
		APSelector.refreshOptions(con);
		APSelector.addOption("0", "未選擇");
		APSelector.setSelectedOption(SelectedOption);
		APSelector.writeHTML(out);
		//System.out.println("APSelector :"+APSelector.toString());
	}

	private void drawLabelShared(String title, PrintWriter out)
	{
		APlLbel = new WebLabel(title);
		APlLbel.setClass("control-label col-md-3");
		APlLbel.writeHTML(out);
		//System.out.println("Lbel :"+APlLbel.toString());
	}

	/**
	 * 隱藏欄位 值,ID,out
	 * 
	 * @param Value
	 * @param htmlID
	 * @param out
	 */
	private void drawHidden(String Value, String htmlID, PrintWriter out)
	{
		APHidden = new WebHidden(htmlID);
		APHidden.setValue(Value);
		APHidden.setID(htmlID);
		APHidden.writeHTML(out);
	}

	

	private String drawSelectDBControl(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String whereSql, String SelectedOption) throws SQLException
	{

		WebSelect ws = APlDBControl.buildWebSelectFromDB(con, tableName, valueField, DisplayField,
				whereSql, null, null, false);
		ws.setID(name);
		ws.setName(name);
		ws.setClass("select2_category form-control");
		ws.addOption("0", "未選擇");
		ws.setSelectedOption(SelectedOption);
		//ws.writeHTML(out);
		return ws.toString();
	}

	/**
	 * 頁面
	 * 3.
	 * 4.
	 * 5.
	 * 6.
	 * 
	 * @param con
	 * @param out
	 * @param SDSelect
	 * @param SUSelect
	 * @param SRSelect
	 * @param queryDate
	 * @param UserEmployeeNo
	 * @throws SQLException
	 */
	private void setHtml(Connection con, PrintWriter out, String reasonsSelect, String unitSelect, String employeeSelect,
			String employeeNoSelect ,String spinnerValue,String queryDate, String UserEmployeeNo,String startTimeHh,String startTimemm
			,String endTimeHh,String endTimemm,String msg) throws SQLException
	{

		overTimeVO otVo = new overTimeVO(); 
		out.write(HtmlUtil.getRowUIDivStart("填寫"));
		out.write(
				"<form class=\"form-horizontal\" name=\"ActionForm\"  method=\"post\"  action=\"/PortalManager?pk=AT&mtb=1&pg=Attendan&pl=at&tb=0&pn=at002\"> \r <input type='hidden' name='act'>\r");
		out.write("<input type='hidden' name='deleteID' id='deleteID' >\r");
		out.write("<div class=\"form-body\">\r");
		// row 1 r
		out.println("<div class=\"row\">");
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"form-group\">");
		drawLabelShared("部門:", out);
		drawHidden(DBUtil.selectDBDepartmentID(con, UserEmployeeNo), "SearchDepartmen", out);
		out.println("<div class=\"col-md-9\">");
		drawLabelShared(UserEmployeeNo, out);
		out.println("</div>");
		out.println(" </div>");
		out.println("</div>");
		// row 1 l
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("日期:", out);
		out.write("<div class=\"col-md-9\">\r");
		out.write(HtmlUtil.getDateDiv("queryDate", queryDate));

		out.write(" </div>\r");
		out.write("</div>\r");
		out.write("</div>\r");
		out.write("</div>\r");
		// row 2 r
		out.write("<div class=\"row\">\r");
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("加班事由:", out);
		out.write("<div class=\"col-md-9\">\r");
		drawSelectShared(con, out, "SearchReasons", "VN_REASONS", "ID", "REASONS", "", reasonsSelect);
		out.write("</div>\r");
		out.write(" </div>\r");
		out.write("</div>\r");
		// row 2 l
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("單位:", out);
		out.write("<div class=\"col-md-9\">\r");
		out.write(drawSelectDBControl(con, out, "SearchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserEmployeeNo) + "'", unitSelect));
		out.write("</div> \r");
		out.write(" </div>\r");
		out.write("</div>\r");
		out.write("</div>\r");
		// row 2 end

		// row 3 r
		out.write("<div class=\"row\">\r");
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("加班時間:", out);
		out.write("<div class=\"col-md-9\">\r");
		out.write(HtmlUtil.getTimeDiv("SsTime", "SeTime", "EsTime", "EeTime",otVo));
		out.write("</div>\r");
		out.write(" </div>\r");
		out.write("</div>\r");
		// row 3 l
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("總共小時:", out);
		out.write("<div class=\"col-md-9\">\r");
		// out.write(HtmlUtil.getDateDiv("queryDate",queryDate));
		out.write(HtmlUtil.getSpinnerDiv("addTime",spinnerValue));
		out.write("</div> \r");
		out.write(" </div>\r");
		out.write("</div>\r");
		out.write("</div>\r");
		// row 3 end

		// row 4 r
		out.write("<div class=\"row\">\r");
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("姓名:", out);
		out.write("<div class=\"col-md-9\">\r");
		out.write(drawSelectDBControl(con, out, "SearchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserEmployeeNo) + "'", employeeSelect));
		out.write("</div>\r");
		out.write(" </div>\r");
		out.write("</div>\r");
		// row 4 l
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("工號:", out);
		out.write("<div class=\"col-md-9\">\r");
		out.write(drawSelectDBControl(con, out, "SearchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserEmployeeNo) + "'", employeeNoSelect));
		out.write("</div> \r");
		out.write(" </div>\r");
		out.write("</div>\r");
		out.write("</div>\r");
		// row 4 end

		out.write("</div>\r");
		out.write("</div>\r");
		out.write("<div class=\"form-actions right\">  \r");
		out.write(
				"<button class=\"btn btn-success \" onclick=\"ActionForm.act.value='Qwe';ActionForm.submit();\" type=\"button\">查詢</button> \r");
		//out.write("<button class=\"btn btn-primary \" onclick=\"ActionForm.act.value='Save';ActionForm.submit();\" type=\"button\">保存</button> \r");
		out.write("<button id='saveBut' class=\"btn btn-primary \" onclick=\"saveData()\" type=\"button\">保存</button> \r");
		out.write("</div></FORM> \r");
		// <!-- END FORM-->
		out.write("<div class=\"form-actions right\">  \r");
		if(!msg.equals("")){
			out.write("<div class=\"alert alert-success\">  \r");
			out.write("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\"></button> \r");
			out.write(" <strong>"+msg+"</strong </div> \r");
			 out.write("</div>");
		}
		out.write(HtmlUtil.getRowUIDivEnd());
		out.write(HtmlUtil.getSaveStatusJs());
	}

	

	
		

	
	
	
	

	
	

	
}
