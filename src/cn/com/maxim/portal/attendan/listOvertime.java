package cn.com.maxim.portal.attendan;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.com.maxim.htmlDBcontrol.WebDBControl;
import cn.com.maxim.htmlDBcontrol.WebDBForm;
import cn.com.maxim.htmlDBcontrol.WebDBSelect;
import cn.com.maxim.htmlcontrol.WebButton;
import cn.com.maxim.htmlcontrol.WebCheckBox;
import cn.com.maxim.htmlcontrol.WebDateEdit;
import cn.com.maxim.htmlcontrol.WebDateSelector;
import cn.com.maxim.htmlcontrol.WebEdit;
import cn.com.maxim.htmlcontrol.WebExampleControl;
import cn.com.maxim.htmlcontrol.WebForm;
import cn.com.maxim.htmlcontrol.WebHidden;
import cn.com.maxim.htmlcontrol.WebLabel;
import cn.com.maxim.htmlcontrol.WebOption;
import cn.com.maxim.htmlcontrol.WebPreformat;
import cn.com.maxim.htmlcontrol.WebSelect;
import cn.com.maxim.htmlcontrol.WebSpan;
import cn.com.maxim.htmlcontrol.WebTable;
import cn.com.maxim.htmlcontrol.WebTableBody;
import cn.com.maxim.htmlcontrol.WebTableRow;
import cn.com.maxim.htmltable.DBColumn;
import cn.com.maxim.htmltable.HighlightColumn;
import cn.com.maxim.htmltable.HighlightRule;
import cn.com.maxim.htmltable.SNOColumn;
import cn.com.maxim.htmltable.WebDBTable;
import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.RequestToBean;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
import cn.com.maxim.portal.webUI.WebDBTableEx;



public class listOvertime extends TemplatePortalPen {

	// private HRStatViewControl viewControl;
	// private BatchSetControl control;
	static WebDBSelect APSelector;
	static WebLabel APlLbel;
	static WebDBForm Form;
	
	private void drawLabelShared(String title, PrintWriter out) {
		APlLbel = new WebLabel(title);
		APlLbel.setClass("control-label col-md-3");
		APlLbel.writeHTML(out);
	}

	public listOvertime() {
		// APSelector=new WebDBSelect("SearchText", "VN_REASONS",
		// "ID","REASONS", "");

	}

	

	public void drawTableT(String sql, Connection con, PrintWriter out) throws SQLException {

		WebDBTableEx table = new WebDBTableEx(con, sql);
		table.writeHTMLTable(out, "table table-striped table-bordered table-hover");
	}

	private void drawSelectShared(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String Filter, String SelectedOption) throws SQLException {

		APSelector = new WebDBSelect(name, tableName, valueField, DisplayField, Filter);
		APSelector.setClass("select2_category form-control");
		APSelector.setID(name);
		APSelector.refreshOptions(con);
		APSelector.addOption("0", "未選擇");
		APSelector.setSelectedOption(SelectedOption);
		APSelector.writeHTML(out);
		// return APSelector;
	}

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con) {
		
		// this.Form.setAction(ActionURI);
		HtmlUtil hu=new HtmlUtil();
		out.write(hu.getUICss());
		out.write(HtmlUtil.getTitleUIDiv("加班申請單", "提交"));

		String actText = request.getParameter("act");
	
		overTimeVO otVo = (overTimeVO)RequestToBean.getBeanToRequest(request,overTimeVO.class);
		try {
			if (actText != null) {
			
				
				if (actText.equals("QUE")) {
				
					// 查詢UI
					setHtml(con, out, otVo);
					// 輸出查詢UI
					out.write(HtmlUtil.drawTableM(SqlUtil.getOvertimeTotal(otVo.getSearchDepartmen(), "0", "0", otVo.getQueryDate()),
							SqlUtil.getOvertime(otVo),
							SqlUtil.getOvertimeStatus(otVo.getSearchDepartmen(), otVo.getQueryDate()),HtmlUtil.drawTableMReferButton(),  con, out,UrlUtil.pageList));
				}else if (actText.equals("Refer"))//送交
				{
					String resultID = DBUtil.queryDBID(con, SqlUtil.getOvertimeID(otVo.getSearchDepartmen(), otVo.getQueryDate()));
					DBUtil.updateTimeOverMStatus("U", resultID, con);
					
					// 查詢UI
					setHtml(con, out, otVo);
					// 輸出查詢UI
					out.write(HtmlUtil.drawTableM(SqlUtil.getOvertimeTotal(otVo.getSearchDepartmen(), "0", "0", otVo.getQueryDate()),
							SqlUtil.getOvertime(otVo),
							SqlUtil.getOvertimeStatus(otVo.getSearchDepartmen(), otVo.getQueryDate()),HtmlUtil.drawTableMReferButton(), con, out,UrlUtil.pageList));
				}

			} else {
				
				otVo.setQueryDate(DateUtil.NowDate());
				//查詢UI
				setHtml(con, out, otVo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		out.write(HtmlUtil.getFooterUIDiv());
		out.write(hu.getUIJs());

	}

	private void setHtml(Connection con, PrintWriter out, 	overTimeVO otVo) throws SQLException {

		out.write(HtmlUtil.getRowUIDivStart("查詢"));
		out.write(
				"<form class=\"form-horizontal\" name=\"ActionForm\"  method=\"post\"  action=\"/PortalManager?pk=AT&mtb=1&pg=Attendan&pl=at&tb=1&pn=at008\"> \r <input type='hidden' name='act'>\r");
		out.write("<div class=\"form-body\">\r");

		out.write("<div class=\"row\">\r");
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("部門:", out);
		out.write("<div class=\"col-md-9\">\r");
		drawSelectShared(con, out, "SearchDepartmen", "VN_DEPARTMENT", "ID", "DEPARTMENT", "", otVo.getSearchDepartmen());
		out.write("</div>\r");
		out.write(" </div>\r");
		out.write("</div>\r");

		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");

		drawLabelShared("日期:", out);
		out.write("<div class=\"col-md-9\">\r");

		out.write(HtmlUtil.getDateDiv("queryDate", otVo.queryDate));
		out.write(" </div>\r");
		out.write("</div>\r");
		out.write("</div>\r");
		out.write("</div>\r");

		/*
		out.write("<div class=\"row\">\r");
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");

		drawLabelShared("加班事由:", out);

		out.write("<div class=\"col-md-9\">\r");
		drawSelectShared(con, out, "SearchReasons", "VN_REASONS", "ID", "REASONS", "", SRSelect);
		out.write("</div>\r");
		out.write(" </div>\r");
		out.write("</div>\r");
		out.write("<div class=\"col-md-6\">\r");
		out.write("<div class=\"form-group\">\r");
		drawLabelShared("單位:", out);
		out.write("<div class=\"col-md-9\">\r");
		drawSelectShared(con, out, "SearchUnit", "VN_UNIT", "ID", "UNIT", "", SUSelect);
		out.write("</div> \r");
		out.write(" </div>\r");
		out.write("</div>\r");
		out.write("</div>\r");
		//row
		 * 
		 */
		out.write("</div>\r");
		out.write("</div>\r");
		out.write("<div class=\"form-actions right\">\r");
		//out.write("<button class=\"btn btn-primary\" onclick=\"ActionForm.act.value='QUE';ActionForm.submit();\" type=\"button\">查詢</button>\r");
		out.write("<button class=\"btn btn-primary\" onclick=\"queryData()\"  type=\"button\">查詢</button>\r");
		//out.write			"<button class=\"btn btn-info \" onclick=\"ActionForm.act.value='Refer';ActionForm.submit();\" type=\"button\">提交</button>\r");
		out.write("</div></FORM>\r");
		// <!-- END FORM-->
		out.write(HtmlUtil.getRowUIDivEnd());
		out.write(HtmlUtil.getListStatusJs());
	}

}
