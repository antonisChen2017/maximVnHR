package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.dayAttendanceExcelRO;
import cn.com.maxim.portal.attendan.ro.dayAttendanceRO;
import cn.com.maxim.portal.attendan.ro.empnumRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.dao.rep_AttendanceDayDAO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.TranslateConsts;
import cn.com.maxim.potral.consts.UrlUtil;

/**
 * 全廠日報表
 * 
 * @author Antonis.chen
 *
 */
public class rep_AttendanceDay extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(rep_AttendanceDay.class);

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{

		String actText = request.getParameter("act");

		leaveCardVO swVo = new leaveCardVO();

		swVo.setActionURI(ActionURI);
		try
		{
			if (actText != null)
			{

				BeanUtils.populate(swVo, request.getParameterMap());

				// 查询
				if (actText.equals("QUE"))
				{
					swVo.setShowDataTable(true);
					showHtml(con, out, swVo, UserInformation, request);
				}
			}
			else
			{
				// 預設

				swVo.setApplicationDate(DateUtil.NowDate());

				showHtml(con, out, swVo, UserInformation, request);

			}

		}
		catch (Exception e)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(e));
		}

	}

	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{

	}

	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo, UserDescriptor UserInformation, HttpServletRequest request) throws SQLException, Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String htmlPart1 = hu.gethtml(htmlConsts.html_rep_AttendanceDay);
		htmlPart1 = htmlPart1.replace("<applicationDate/>", HtmlUtil.getDateDiv("applicationDate", lcVo.getApplicationDate()));
		htmlPart1 = htmlPart1.replace("<ActionURI/>", lcVo.getActionURI());
		if (lcVo.isShowDataTable())
		{
			htmlPart1 = htmlPart1.replace("<drawTableM/>", ControlUtil.drawAttendanceDayTable(con, lcVo));
			dayAttendanceExcelRO daRo = new dayAttendanceExcelRO();
			List<dayAttendanceExcelRO> daRolist = (List<dayAttendanceExcelRO>) DBUtil.
				queryExcelDay(con, SqlUtil.getAttDayExcel(lcVo.getApplicationDate()), daRo);
			//System.out.println("daRolist : "+daRolist);
			request.getSession().setAttribute("daRolist", daRolist);
			empnumRO eRo = new empnumRO();
			List<empnumRO> eRolist = (List<empnumRO>) DBUtil.
					queryExcelEmpnum(con, SqlUtil.getExcelEmpnum(lcVo.getApplicationDate()), eRo);
		//	System.out.println("ExcelEmpnum : "+SqlUtil.getExcelEmpnum(lcVo.getApplicationDate().replaceAll("/", "")));
			request.getSession().setAttribute("eRolist", eRolist);
			Hashtable  blueRow=rep_AttendanceDayDAO.UnitCount(con, lcVo);
			request.getSession().setAttribute("blueRow", blueRow);
			
		}
		 out.println(htmlPart1);
	}
}
