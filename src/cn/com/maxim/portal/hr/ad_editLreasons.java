package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.vo.editLreasonsVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;
/**
 * 新增刪除修改加班理由
 * @author antonis.chen
 *
 */
public class ad_editLreasons extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(ad_editLreasons.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		editLreasonsVO edVo = new editLreasonsVO();
		edVo.setActionURI(ActionURI);

		try
		{
		
			if (actText != null)
			{
				System.out.println("actText :" +actText);
			
				BeanUtils.populate(edVo, request.getParameterMap());
				edVo.setVName("");
			
				// 刪除
				if (actText.equals("DEL"))
				{
					
					edVo.setShowDataTable(true);
					DBUtil.updateSql(SqlUtil.deleteLreasons(edVo), con);
					edVo.setReasons("");
					edVo.setEName("");
					edVo.setVName("");
					edVo.setSortNo("");
					showHtml(con, out, edVo, UserInformation);
				}
				// 修改
				if (actText.equals("UPD"))
				{
					edVo.setShowDataTable(true);
					DBUtil.updateSql(SqlUtil.updateLreasons(edVo), con);
					edVo.setMsg(keyConts.editOK);
					showHtml(con, out, edVo, UserInformation);
				}
				// 新增
				if (actText.equals("INS"))
				{
				
					edVo.setShowDataTable(true);
				
					DBUtil.updateSql(SqlUtil.saveLreasons(edVo), con);
					edVo.setMsg(keyConts.saveOK);
					showHtml(con, out, edVo, UserInformation);
				}
				
			}
			else
			{
				// 預設
				edVo.setReasons("");
				edVo.setEName("");
				edVo.setVName("");
				edVo.setSortNo("");
				edVo.setRowID("0");
				edVo.setShowDataTable(true);
				showHtml(con, out, edVo, UserInformation);

			}
		}
		catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
	}
	
	private void showHtml(Connection con, PrintWriter out, editLreasonsVO edVo, UserDescriptor UserInformation) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		//employeeUserRO eo = new employeeUserRO();

		//List<employeeUserRO> lro = DBUtil.queryUserList(con, SqlUtil.getEmployeeNameDate(UserInformation.getUserName()), eo);
	//	lcVo.setSearchDepartmen(lro.get(0).getDID());

		String htmlPart1 = hu.gethtml(htmlConsts.html_ad_editLreasons);
		htmlPart1 = htmlPart1.replace("<ActionURI/>", edVo.getActionURI());
		htmlPart1=htmlPart1.replace("<reason/>",HtmlUtil.getTextDiv("reasons",edVo.getReasons(),"可輸入加班原因" ));
		htmlPart1=htmlPart1.replace("<EName/>",HtmlUtil.getTextDiv("EName",edVo.getEName(),"非必要輸入" ));
		htmlPart1=htmlPart1.replace("<VName/>",HtmlUtil.getTextDiv("VName",edVo.getVName(),"非必要輸入" ));
		htmlPart1=htmlPart1.replace("<sortNo/>",HtmlUtil.getTextDiv("sortNo",edVo.getSortNo(),"非必要輸入" ));
		htmlPart1=htmlPart1.replace("<rowID/>",edVo.getRowID());
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(edVo.getMsg()));
		if (edVo.isShowDataTable())
		{
		htmlPart1 = htmlPart1.replace("<drawTableM/>",
					HtmlUtil.drawTableEdit(SqlUtil.getVnLreasons(edVo), 
							HtmlUtil.drawTableMcheckButton(), con, out, keyConts.LRessons));
		}

		out.println(htmlPart1);
	}

}
