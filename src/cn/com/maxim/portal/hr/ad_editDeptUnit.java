package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.editDeptUnit;
import cn.com.maxim.portal.attendan.vo.editLholidayVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;

/**
 * 部門單位編輯
 * @author Antonis.chen
 *
 */
public class ad_editDeptUnit extends TemplatePortalPen
{
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(ad_editLholiday.class);

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response,
		UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		String UrowID = request.getParameter("UrowID");
		String DrowID = request.getParameter("DrowID");
		editDeptUnit edVo = new editDeptUnit();
		edVo.setActionURI(ActionURI);
		logger.info("actText :"+actText);	
		try
		{
		
			if (actText != null)
			{
				BeanUtils.populate(edVo, request.getParameterMap());
			
				// 單位刪除
				if (actText.equals("UDEL"))
				{
					
					edVo.setShowDataTable(true);
					//edVo.setMsg("進入刪除流程");
					logger.info("getUrowID "+UrowID);	
					edVo.setUrowID(UrowID);
					logger.info("delete "+SqlUtil.deleteUnit(edVo));	
					DBUtil.updateSql(SqlUtil.deleteUnit(edVo), con);
					edVo.setDID("");
					edVo.setDept("");
					edVo.setDName("");
					edVo.setUName("");
					edVo.setDEName("");
					edVo.setUEName("");
					edVo.setDrowID("0");
					edVo.setUrowID("0");
					edVo.setMsg(keyConts.editOK);
					showHtml(con, out, edVo, UserInformation);
				}
				//部門刪除
				if (actText.equals("DDEL"))
				{
					
					edVo.setShowDataTable(true);
					
					edVo.setDrowID(DrowID);
					String DID = request.getParameter("DID");
					edVo.setDID(DID);
					String DName = request.getParameter("DName");
					edVo.setDName(DName);
					String DEName = request.getParameter("DEName");
					edVo.setDEName(DEName);
			        //檢查有無單位 有不能刪除

					logger.info("count "+SqlUtil.getUnitDeptCount(edVo));	
					String COUNT=DBUtil.queryDBField(con,SqlUtil.getUnitDeptCount(edVo),"COUNT");
					if(Integer.valueOf(COUNT)>0){
						edVo.setMsg(keyConts.editDeptNoUnit);
					}else{
						DBUtil.updateSql(SqlUtil.deleteDept(edVo), con);
						edVo.setMsg(keyConts.editOK);
					}
					edVo.setUrowID("0");
					edVo.setDrowID("0");
					showHtml(con, out, edVo, UserInformation);
				}
				// 單位修改
				if (actText.equals("UUPD"))
				{
					edVo.setShowDataTable(true);
					edVo.setUrowID(UrowID);
					String Dept = request.getParameter("Dept");
					edVo.setDept(Dept);
					String UEName = request.getParameter("UEName");
					edVo.setUEName(UEName);
					String UName = request.getParameter("UName");
					edVo.setUName(UName);
					String Sing = request.getParameter("Sing");
					edVo.setSing(Sing);
					logger.info("SQL "+SqlUtil.updateUnitData(edVo));	
					DBUtil.updateSql(SqlUtil.updateUnitData(edVo), con);
					edVo.setMsg(keyConts.editOK);
					edVo.setDrowID("0");
					showHtml(con, out, edVo, UserInformation);
				}
				// 部門修改
				if (actText.equals("DUPD"))
				{
					edVo.setShowDataTable(true);
					String DID = request.getParameter("DID");
					edVo.setDID(DID);
					edVo.setDrowID(DID);
					String DName = request.getParameter("DName");
					edVo.setDName(DName);
					String DEName = request.getParameter("DEName");
					edVo.setDEName(DEName);
					logger.info("SQL "+SqlUtil.updateDept(edVo));	
					DBUtil.updateSql(SqlUtil.updateDept(edVo), con);
					
					edVo.setMsg(keyConts.editOK);
					edVo.setUrowID("0");
					edVo.setDrowID("0");
					showHtml(con, out, edVo, UserInformation);
				}
				// 單位新增
				if (actText.equals("UINS"))
				{
					edVo.setShowDataTable(true);
					edVo.setUrowID(UrowID);
					String Dept = request.getParameter("Dept");
					edVo.setDept(Dept);
					String UEName = request.getParameter("UEName");
					edVo.setUEName(UEName);
					String UName = request.getParameter("UName");
					String Sing = request.getParameter("Sing");
					edVo.setSing(Sing);
					edVo.setDrowID("0");
					
					logger.info("SQL "+SqlUtil.InsterUnitData(edVo));	
					DBUtil.updateSql(SqlUtil.InsterUnitData(edVo), con);
					
					edVo.setMsg(keyConts.saveOK);
					showHtml(con, out, edVo, UserInformation);
				}
				// 部門新增
				if (actText.equals("DINS"))
				{
					edVo.setShowDataTable(true);
					edVo.setUrowID(UrowID);
					String Dept = request.getParameter("Dept");
					edVo.setDept(Dept);
					String UEName = request.getParameter("UEName");
					edVo.setUEName(UEName);
					String UName = request.getParameter("UName");
					edVo.setUName(UName);
					/**檢查有無相同ID 有不能新增**/
					String COUNT=DBUtil.queryDBField(con,SqlUtil.getDeptIDCount(edVo),"COUNT");
					if(Integer.valueOf(COUNT)>0){
						edVo.setMsg(keyConts.editDeptIDRepeat);
					}else{
						DBUtil.updateSql(SqlUtil.InsterDept(edVo), con);
						edVo.setMsg(keyConts.saveOK);
					}
					edVo.setUrowID("0");
					edVo.setDrowID("0");
					showHtml(con, out, edVo, UserInformation);
				}
			}
			else
			{
				// 預設
				edVo.setDID("");
				edVo.setDept("");
				edVo.setDName("");
				edVo.setUName("");
				edVo.setDEName("");
				edVo.setUEName("");
				edVo.setDrowID("0");
				edVo.setUrowID("0");
				edVo.setSing("");
				edVo.setShowDataTable(true);
				showHtml(con, out, edVo, UserInformation);

			}
		}
		catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
		
	}
	private void showHtml(Connection con, PrintWriter out, editDeptUnit edVo, UserDescriptor UserInformation) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
	

		String htmlPart1 = hu.gethtml(htmlConsts.html_ad_editDeptUnit);
	//	htmlPart1 = htmlPart1.replace("<Translate/>", ControlUtil.drawTranslateSelectShared());
		htmlPart1 = htmlPart1.replace("<ActionURI/>", edVo.getActionURI());
		htmlPart1=htmlPart1.replace("<DID/>",HtmlUtil.getTextDiv("DID",edVo.getDID(),"必要输入" ));
		htmlPart1=htmlPart1.replace("<DName/>",HtmlUtil.getTextDiv("DName",edVo.getDName(),"必要输入" ));
		htmlPart1=htmlPart1.replace("<DEName/>",HtmlUtil.getTextDiv("DEName",edVo.getDEName(),"非必要输入" ));
		htmlPart1=htmlPart1.replace("<Sing/>",HtmlUtil.getTextDiv("Sing",edVo.getSing(),"非必要输入" ));
		htmlPart1=htmlPart1.replace("<Dept/>",ControlUtil.drawChosenSelect(con, "Dept", "VN_DEPARTMENT", "ID", "DEPARTMENT", null ,edVo.getDept(),false,null));
		htmlPart1=htmlPart1.replace("<UName/>",HtmlUtil.getTextDiv("UName",edVo.getUName(),"必要输入" ));
		htmlPart1=htmlPart1.replace("<UEName/>",HtmlUtil.getTextDiv("UEName",edVo.getUEName(),"非必要输入" ));
		htmlPart1=htmlPart1.replace("<DrowID/>",edVo.getDrowID());
		htmlPart1=htmlPart1.replace("<UrowID/>",edVo.getUrowID());
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(edVo.getMsg()));
		if (edVo.isShowDataTable())
		{
			logger.info("DeptSQL :"+SqlUtil.getVnDept(edVo));	
		htmlPart1 = htmlPart1.replace("<drawTableD/>",
					HtmlUtil.drawTableEdit(SqlUtil.getVnDept(edVo), 
							HtmlUtil.drawTableMcheckButton(), con, out, keyConts.ColDept));
		logger.info("UnitSQL :"+SqlUtil.getVnUnit(edVo));	
		htmlPart1 = htmlPart1.replace("<drawTableU/>",
				HtmlUtil.drawTableEditT(SqlUtil.getVnUnit(edVo), 
						HtmlUtil.drawTableMcheckButton(), con, out, keyConts.ColUnit));
		}

		out.println(htmlPart1);
	}
}