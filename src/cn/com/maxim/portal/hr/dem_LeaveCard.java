package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;

/**
 * 請假卡 部門主管審核
 * 
 * @author Antonis.chen
 *
 */
public class dem_LeaveCard extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(dem_LeaveCard.class);

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{

		String actText = request.getParameter("act");

		leaveCardVO lcVo = new leaveCardVO();
		lcVo.setActionURI(ActionURI);
		try
		{
			if (actText != null)
			{

				BeanUtils.populate(lcVo, request.getParameterMap());
				// 查詢
				if (actText.equals("QUE"))
				{
					lcVo.setShowDataTable(true);
					showHtml(con, out, lcVo, UserInformation);
				}
				// 請假退回
				if (actText.equals("R"))
				{
					logger.info("請假卡 部門主管審核/R : " + lcVo.toString());
					lcVo.setStatus("DR");
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(UrlUtil.returnMsg);
					}

					lcVo.setShowDataTable(true);
					showHtml(con, out, lcVo, UserInformation);
				}
				// 部門主管通過
				if (actText.equals("D"))
				{

					logger.info("請假卡 部門主管審核/D : " + lcVo.toString());
					lcVo.setShowDataTable(true);
					lcVo.setStatus(actText);
					// 儲存db
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(UrlUtil.okMsg);
					}

					showHtml(con, out, lcVo, UserInformation);
				}
			}
			else
			{
				// 預設
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
				showHtml(con, out, lcVo, UserInformation);

			}
		}
		catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}

	}

	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{

		if (ajax.equals("unit"))
		{
			String employeeID = request.getParameter("employeeID");
			String unitID = DBUtil.queryDBField(con, SqlUtil.getVnEmployee(employeeID, "  V.UNIT_ID "), "UNIT_ID");
			String html = "";
			try
			{
				html = ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", unitID);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("html : " + html);
			out.println(html);
		}
		if (ajax.equals("duties"))
		{
			String employeeID = request.getParameter("employeeID");
			String entryDate = DBUtil.queryDBField(con, SqlUtil.getVnEmployee(employeeID, "  V.ENTRYDATE "), "ENTRYDATE");
			String duties = DBUtil.queryDBField(con, SqlUtil.getVnEmployee(employeeID, "  V.DUTIES "), "DUTIES");
			String data = "[{ \"duties\":\"" + duties + "\" , \"entryDate\":\"" + entryDate + "\" }]";
			// System.out.println("TimeDiv :"+TimeDiv);
			out.println(data);
		}
	}

	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo, UserDescriptor UserInformation) throws SQLException
	{
		HtmlUtil hu = new HtmlUtil();
		employeeUserRO eo = new employeeUserRO();

		List<employeeUserRO> lro = DBUtil.queryUserList(con, SqlUtil.getEmployeeNameDate(UserInformation.getUserName()), eo);
		lcVo.setSearchDepartmen(lro.get(0).getDID());

		String htmlPart1 = hu.gethtml(htmlConsts.html_dem_LeaveCard);
		htmlPart1 = htmlPart1.replace("<ActionURI/>", lcVo.getActionURI());
		htmlPart1 = htmlPart1.replace("<SearchUnit/>", HtmlUtil.getLabelHtml(lro.get(0).getUNIT()));
		htmlPart1 = htmlPart1.replace("<UserEmployeeNo/>", UserInformation.getUserEmployeeNo());
		htmlPart1 = htmlPart1.replace("<hiddenEmployeeNo/>", ControlUtil.drawHidden(DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()), "searchDepartmen"));
		htmlPart1 = htmlPart1.replace("<applicationDate/>", HtmlUtil.getDateDivSw("startLeaveDate", "endLeaveDate", lcVo.getStartLeaveDate(), lcVo.getEndLeaveDate()));
		htmlPart1 = htmlPart1.replace("<SearchEmployeeNo/>", ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + lro.get(0).getDID() + "'", lcVo.getSearchEmployeeNo()));
		htmlPart1 = htmlPart1.replace("<SearchEmployee/>", ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + lro.get(0).getDID() + "'", lcVo.getSearchEmployee()));
		htmlPart1 = htmlPart1.replace("<msg/>", HtmlUtil.getMsgDiv(lcVo.getMsg()));

		// System.out.println("sql : "+SqlUtil.getDepartmenLeaveCard(lcVo));
		if (lcVo.isShowDataTable())
		{
			htmlPart1 = htmlPart1.replace("<drawTableM/>", HtmlUtil.drawLeaveCardTable(SqlUtil.getDepartmenLeaveCard(lcVo), HtmlUtil.drawTableMcheckButton(), con, out, UrlUtil.pageDtmList));
		}

		out.println(htmlPart1);
	}
}
