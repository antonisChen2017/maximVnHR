package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.editLholidayVO;
import cn.com.maxim.portal.attendan.vo.editLreasonsVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;

public class ad_editLholiday extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(ad_editLholiday.class);

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		editLholidayVO edVo = new editLholidayVO();
		edVo.setActionURI(ActionURI);

		try
		{
		
			if (actText != null)
			{
				BeanUtils.populate(edVo, request.getParameterMap());
				edVo.setVName("");
				// 刪除
				if (actText.equals("DEL"))
				{
					
					edVo.setShowDataTable(true);
					DBUtil.updateSql(SqlUtil.deleteLholiday(edVo), con);
					edVo.setHolidIyName("");
					edVo.setEName("");
					edVo.setVName("");
					edVo.setSortNo("");
					showHtml(con, out, edVo, UserInformation);
				}
				
				// 修改
				if (actText.equals("UPD"))
				{
					edVo.setShowDataTable(true);
					DBUtil.updateSql(SqlUtil.updateLholiday(edVo), con);
					edVo.setMsg(keyConts.editOK);
					showHtml(con, out, edVo, UserInformation);
				}
				// 新增
				if (actText.equals("INS"))
				{
					edVo.setShowDataTable(true);
					DBUtil.updateSql(SqlUtil.saveLholiday(edVo), con);
					edVo.setMsg(keyConts.saveOK);
					showHtml(con, out, edVo, UserInformation);
				}
				
			}
			else
			{
				// 預設
				edVo.setHolidIyName("");
				edVo.setEName("");
				edVo.setVName("");
				edVo.setSortNo("");
				edVo.setRowID("0");
				edVo.setHolidIyClas("");
				edVo.setShowDataTable(true);
				showHtml(con, out, edVo, UserInformation);

			}
		}
		catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
		
	}
	private void showHtml(Connection con, PrintWriter out, editLholidayVO edVo, UserDescriptor UserInformation) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		//employeeUserRO eo = new employeeUserRO();

		//List<employeeUserRO> lro = DBUtil.queryUserList(con, SqlUtil.getEmployeeNameDate(UserInformation.getUserName()), eo);
	//	lcVo.setSearchDepartmen(lro.get(0).getDID());

		String htmlPart1 = hu.gethtml(htmlConsts.html_ad_editLholiday);
	//	htmlPart1 = htmlPart1.replace("<Translate/>", ControlUtil.drawTranslateSelectShared());
		htmlPart1 = htmlPart1.replace("<ActionURI/>", edVo.getActionURI());
		htmlPart1=htmlPart1.replace("<holidIyName/>",HtmlUtil.getTextDiv("holidIyName",edVo.getHolidIyName(),"可輸入請假原因" ));
		htmlPart1=htmlPart1.replace("<EName/>",HtmlUtil.getTextDiv("EName",edVo.getEName(),"非必要輸入" ));
		htmlPart1=htmlPart1.replace("<VName/>",HtmlUtil.getTextDiv("VName",edVo.getVName(),"非必要輸入" ));
		htmlPart1=htmlPart1.replace("<sortNo/>",HtmlUtil.getTextDiv("sortNo",edVo.getSortNo(),"非必要輸入" ));
		htmlPart1=htmlPart1.replace("<holidIyClas/>",HtmlUtil.getTextDiv("holidIyClas",edVo.getHolidIyClas(),"必要輸入" ));
		htmlPart1=htmlPart1.replace("<rowID/>",edVo.getRowID());
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(edVo.getMsg()));
		if (edVo.isShowDataTable())
		{
		htmlPart1 = htmlPart1.replace("<drawTableM/>",
					HtmlUtil.drawTableEdit(SqlUtil.getVnLhReasons(edVo), 
							HtmlUtil.drawTableMcheckButton(), con, out, keyConts.holidayReasons));
		}

		out.println(htmlPart1);
	}
}
