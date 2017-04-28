package cn.com.maxim.portal.hr;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.htmlConsts;
import cn.com.maxim.potral.consts.keyConts;
/**
 * 加班申請單 員工
 * @author Antonis.chen
 *
 */
public class dep_OverTime extends TemplatePortalPen
{ 
	
	Log4jUtil lu=new Log4jUtil();
	Logger logger  =lu.initLog4j(dep_OverTime.class);
	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		
		String actText = request.getParameter("act");
		overTimeVO otVo = new overTimeVO(); 
		otVo.setActionURI(ActionURI);
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(otVo,request.getParameterMap()); 
					
					// 查詢
					if (actText.equals("QUE")) {
						otVo.setShowDataTable(true);
						showHtml(con, out, otVo,UserInformation);
					}
					
					if (actText.equals("SwTime")) {
						showHtml(con, out, otVo,UserInformation);
					}
					
					if (actText.equals("Save")) {
						logger.info("加班申請單 員工/Save : " +otVo.toString());
						otVo.setShowDataTable(true);
						//不能超過系統規定加班時數
						otVo.setOverTimeSave(false);
						// 儲存db
						String msg=DBUtil.saveTable(otVo , con);
						otVo.setMsg(msg);
					
						showHtml(con, out,  otVo,UserInformation);
						
					}
					if (actText.equals("Delete")) {
						String rowID = request.getParameter("rowID");
						//delete 加班單
						logger.info("加班申請單 員工/Delete : " +otVo.toString());
						String resultID = DBUtil.queryDBID(con, SqlUtil.getOvertimeID(otVo.getSearchDepartmen(), otVo.getQueryDate()));
						DBUtil.delOvertimeS(rowID,con);
						otVo.setShowDataTable(true);
						otVo.setMsg("已刪除");
						showHtml(con, out, otVo,UserInformation);
					}
					if (actText.equals("Refer"))//送交
					{
						logger.info("加班申請單 員工/Refer : " +otVo.toString());
						DBUtil.updateTimeOverSStatus("U", request.getParameter("rowID"), con);
						otVo.setShowDataTable(true);
						otVo.setMsg("已送交");
						showHtml(con, out, otVo,UserInformation);
						
					}
					
					
				}else{
					//預設
					otVo.setSearchDepartmen("0");
					otVo.setSearchUnit("0");
					otVo.setSearchEmployeeNo("0");
					otVo.setSearchEmployee("0");
					otVo.setSearchReasons("0");
					otVo.setOverTimeClass("0");
					otVo.setAddTime("1");
					otVo.setStartTimeHh("0");
					otVo.setStartTimemm("0");
					otVo.setEndTimeHh("0");
					otVo.setEndTimemm("0");
					otVo.setNote("");
					otVo.setQueryDate(DateUtil.NowDate());
					otVo.setSubmitDate(DateUtil.NowDate());
					otVo.setUserReason("");
					//System.out.println("actText null   otVo : "+otVo.toString());
					showHtml(con, out, otVo,UserInformation);
				
				}
		}catch (Exception err)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(err));
		}
	}
	
	 public void doAjax(String ajax, HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation, PrintWriter out, String ActionURI, Connection con) {
		 
		 if(ajax.equals("unit")){
			 String employeeID = request.getParameter("employeeID");
			 String unitID= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.UNIT_ID "),"UNIT_ID");
			 String html="";
			try
			{
				html = ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", unitID);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 out.println(html);
	 	}
		 if(ajax.equals("SwTime")){
			 overTimeVO otVo = new overTimeVO(); 
			 otVo.setOverTimeClass(request.getParameter("overTimeClass"));
			 otVo.setAct("SwTime");
			 
			 String ehm=DBUtil.queryDBField(con,SqlUtil.getEhm(otVo),"ehm");
			 if(ehm.indexOf(":")==-1){
				 ehm="00:00";
			 }
			 String[] ehms=ehm.split(":");
			 otVo.setStartTimeHh(ehms[0]);
			 otVo.setStartTimemm(ehms[1]);
			 int hr=Integer.valueOf(ehms[0])+5;
			 if(hr>23){
				 hr=23;
			 }
			 otVo.setEndTimeHh(String.valueOf(hr));
			 otVo.setEndTimemm(ehms[1]);
			 
			 String TimeDiv= HtmlUtil.getTimeDiv("startTimeHh", "startTimemm", "endTimeHh", "endTimemm",otVo);
			 System.out.println("TimeDiv :"+TimeDiv);
			 out.println(TimeDiv);
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
	 
	private void showHtml(Connection con, PrintWriter out, overTimeVO otVo , UserDescriptor UserInformation) throws SQLException {
			HtmlUtil hu=new HtmlUtil();
			String htmlPart1=hu.gethtml(htmlConsts.html_dep_OverTime);
			htmlPart1=htmlPart1.replace("<ActionURI/>", 	otVo.getActionURI());
			htmlPart1=htmlPart1.replace("&UserEmployeeNo", 	UserInformation.getUserEmployeeNo());
			htmlPart1=htmlPart1.replace("&hiddenEmployeeNo",ControlUtil.drawHidden(DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()), "searchDepartmen"));
			htmlPart1=htmlPart1.replace("<SearchUnit/>",ControlUtil.drawSelectDBControl(con, out, "searchUnit", "VN_UNIT", "ID", "UNIT", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", otVo.getSearchUnit()));
			htmlPart1=htmlPart1.replace("&SearchEmployeeNo",ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", otVo.getSearchEmployeeNo()));
			htmlPart1=htmlPart1.replace("&SearchEmployee",ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()) + "'", otVo.getSearchEmployee()));
			htmlPart1=htmlPart1.replace("&SearchReasons",ControlUtil.drawSelectShared(con, out, "searchReasons", "VN_REASONS", "ID", "REASONS", "", otVo.getSearchReasons(),true));
		//	htmlPart1=htmlPart1.replace("&OverTimeClass",HtmlUtil.getOverTimeClass( otVo));
			htmlPart1=htmlPart1.replace("&OverTimeClass",ControlUtil.drawSelectShared(con, out, "overTimeClass", "VN_TURN", "Code", "CBName", " 1=1  ", otVo.getOverTimeClass(),true));
		
			htmlPart1=htmlPart1.replace("&addTime",HtmlUtil.getSpinnerDiv("addTime",otVo.getAddTime()));
			htmlPart1=htmlPart1.replace("<TimeDiv/>",HtmlUtil.getTimeDiv("startTimeHh", "startTimemm", "endTimeHh", "endTimemm",otVo));
			htmlPart1=htmlPart1.replace("&Note",HtmlUtil.getNoteDiv("note", otVo.getNote()));
			htmlPart1=htmlPart1.replace("&submitDate",HtmlUtil.getDateDiv("submitDate", otVo.getSubmitDate()));
			htmlPart1=htmlPart1.replace("&queryDate",HtmlUtil.getDateDiv("queryDate", otVo.getQueryDate()));
			htmlPart1=htmlPart1.replace("&userReason",HtmlUtil.getTextDiv("userReason",otVo.getUserReason(),"可輸入自訂加班事由" ));
			htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(otVo.getMsg()));
			//System.out.println("getOvertime  :   "+SqlUtil.getOvertime(otVo));
			if(otVo.isShowDataTable()){
				htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawTableS(
						SqlUtil.getOvertime(otVo),HtmlUtil.drawTableMcheckButton(),  con, out,keyConts.pageSave));
			}
			
		    out.println(htmlPart1);
	}
	

}
