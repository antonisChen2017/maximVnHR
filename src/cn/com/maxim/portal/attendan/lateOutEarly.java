package cn.com.maxim.portal.attendan;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.lateOutEarlyRO;
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.ExcelUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.codec.binary.Base64;

/**
 * 遲到早退統計
 * @author Antonis.chen
 *
 */
public class lateOutEarly extends TemplatePortalPen
{

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		
		lateOutEarlyVO eaVo = new lateOutEarlyVO(); 
	
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(eaVo,request.getParameterMap()); 
					// 查詢
					if (actText.equals("QUE")) {
						eaVo.setShowDataTable(true);
						showHtml(con, out, eaVo,UserInformation,request);
					}
					
				}else{
					//預設
				
					eaVo.setQueryYearMonth(DateUtil.getSysYearMonth());
					eaVo.setQueryIsLate("0");
				
					showHtml(con, out, eaVo,UserInformation,request);
				
				}
		}catch (Exception err)
		{
				out.println("<pre>");
				err.printStackTrace(out);
				out.println("</pre>");
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		
		
	 }
	
	private void showHtml(Connection con, PrintWriter out,lateOutEarlyVO eaVo  , UserDescriptor UserInformation,HttpServletRequest request) throws SQLException {
		
		
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(UrlUtil.lateOutEarly);
			htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getYearMonthDiv("queryYearMonth",eaVo.getQueryYearMonth()));
			htmlPart1=htmlPart1.replace("<queryIsLate/>",HtmlUtil.getIsLate(eaVo));
			//System.out.println("ateOutEarly sql "+SqlUtil.getlateOutEarly(eaVo));
			if(eaVo.isShowDataTable()){
				String drawTableM =HtmlUtil.drawLateOutEarlyTable(
						SqlUtil.getlateOutEarly(con, eaVo),SqlUtil.getYMLATE(eaVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageSave);
				htmlPart1=htmlPart1.replace("<drawTableM/>",drawTableM);
				//htmlPart1=htmlPart1.replace("<Exceltable/>","<table><tbody><tr><td>斕疑</td><td>bb</td></tr><tr><td>cc</td><td>dd</td></tr></tbody></table>");
				yearMonthLateRO ymRo=new yearMonthLateRO();
				 List<yearMonthLateRO> eaRolist=( List<yearMonthLateRO>)DBUtil.queryLateList(con,SqlUtil.getLateOutEarlyExcelSql(eaVo),ymRo);
				 request.getSession().setAttribute("eaRolist", eaRolist);
			}
		
			
		    out.println(htmlPart1);
		    }
	
	

}

