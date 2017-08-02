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
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.UrlUtil;
import cn.com.maxim.potral.consts.htmlConsts;
/**
 * 員工查询考勤
 * @author Antonis.chen
 *
 */
public class emp_LateOutEarly extends TemplatePortalPen
{
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(emp_LateOutEarly.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		
		String actText = request.getParameter("act");
		
		lateOutEarlyVO eaVo = new lateOutEarlyVO(); 
		eaVo.setActionURI(ActionURI);
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(eaVo,request.getParameterMap()); 
					// 查询
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
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
		
	}
	public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		
		
	 }
	
	private void showHtml(Connection con, PrintWriter out,lateOutEarlyVO eaVo  , UserDescriptor UserInformation,HttpServletRequest request) throws Exception {
		
			employeeUserRO eo=new employeeUserRO();
			List<employeeUserRO> lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNODate(UserInformation.getUserTelephone()) ,eo);	
			eaVo.setEmpID(lro.get(0).getEMPLOYEENO());
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_emp_lateOutEarly);
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	eaVo.getActionURI());
			htmlPart1=htmlPart1.replace("<queryYearMonth/>",HtmlUtil.getYearMonthDiv("queryYearMonth",eaVo.getQueryYearMonth()));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",HtmlUtil.getLabelHtml(lro.get(0).getUNIT()));
			htmlPart1=htmlPart1.replace("<SearchEmployee/>",HtmlUtil.getLabelHtml(lro.get(0).getEMPLOYEE()));
			htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",HtmlUtil.getLabelHtml(lro.get(0).getEMPLOYEENO()));
			htmlPart1=htmlPart1.replace("<hiddenUserNo/>",ControlUtil.drawHidden(lro.get(0).getID(), "searchEmployee"));	
			htmlPart1=htmlPart1.replace("<hiddenUnit/>",ControlUtil.drawHidden(lro.get(0).getUID(), "searchUnit"));	
			htmlPart1=htmlPart1.replace("<hiddenUser/>",ControlUtil.drawHidden(lro.get(0).getID(), "searchEmployeeNo"));	
			htmlPart1=htmlPart1.replace("<hiddenEmployeeNo/>",ControlUtil.drawHidden(lro.get(0).getDID(), "searchDepartmen"));
			htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	 lro.get(0).getDEPARTMENT());
		
			if(eaVo.isShowDataTable()){

				htmlPart1=htmlPart1.replace("<drawTableM/>",ControlUtil.drawAccordions( con,eaVo));
			
			}			
		    out.println(htmlPart1);
		    }
	
	

}

