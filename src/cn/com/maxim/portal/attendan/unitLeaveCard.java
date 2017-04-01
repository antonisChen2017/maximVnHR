package cn.com.maxim.portal.attendan;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.com.maxim.portal.TemplatePortalPen;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.util.ControlUtil;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.UrlUtil;

/**
 * 請假卡 單位主管審核
 * @author Antonis.chen
 *
 */
public class unitLeaveCard extends TemplatePortalPen
{

	@Override
	public void drawPanel(HttpServletRequest request, HttpServletResponse response, UserDescriptor UserInformation,
			PrintWriter out, String ActionURI, Connection con)
	{
		String actText = request.getParameter("act");
		//String us =this.getDBAttribute("us");//取管理功能設定屬性
		leaveCardVO lcVo = new leaveCardVO(); 
		//lcVo.setUs(us);
		try
		{
				if (actText != null)
				{		
					
					BeanUtils.populate(lcVo,request.getParameterMap()); 
					// 查詢
					if (actText.equals("QUE")) {
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation);
					}
					//請假退回
					if (actText.equals("R")) {
				
						lcVo.setStatus(actText);
						if(DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)){
							lcVo.setMsg(UrlUtil.returnMsg);
						}
						
						lcVo.setShowDataTable(true);
						showHtml(con, out, lcVo,UserInformation);
					}
					//單位主管通過
					if (actText.equals("U")) {
				

						lcVo.setShowDataTable(true);
						lcVo.setStatus(actText);
						// 儲存db
						if(DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)){
							lcVo.setMsg(UrlUtil.okMsg);
						}
				
						showHtml(con, out, lcVo,UserInformation);
					}
				}else{
					//預設
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
					showHtml(con, out, lcVo,UserInformation);
				
				}
		}catch (Exception err)
		{
				out.println("<pre>");
				err.printStackTrace(out);
				out.println("</pre>");
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
			System.out.println("html : "+html);
			 out.println(html);
	 	}
		 if(ajax.equals("duties")){
			  String employeeID = request.getParameter("employeeID");
			  String entryDate= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.ENTRYDATE "),"ENTRYDATE");
			  String duties= DBUtil.queryDBField(con,  SqlUtil.getVnEmployee(employeeID,"  V.DUTIES "),"DUTIES");
			  String data= "[{ \"duties\":\""+duties+"\" , \"entryDate\":\""+entryDate+"\" }]";
			// System.out.println("TimeDiv :"+TimeDiv);
			  out.println(data);
	 	}
	 }
	
	private void showHtml(Connection con, PrintWriter out, leaveCardVO lcVo , UserDescriptor UserInformation) throws SQLException {
		HtmlUtil hu=new HtmlUtil();
		employeeUserRO eo=new employeeUserRO();
	
		List<employeeUserRO> lro=DBUtil.queryUserList(con,SqlUtil.getEmployeeNameDate(UserInformation.getUserName()) ,eo);	
		lcVo.setSearchUnit(lro.get(0).getUID());
		String htmlPart1=hu.gethtml(UrlUtil.unitLeaveCard);
		htmlPart1=htmlPart1.replace("<SearchUnit/>",HtmlUtil.getLabelHtml(lro.get(0).getUNIT()));
		htmlPart1=htmlPart1.replace("<UserEmployeeNo/>", 	UserInformation.getUserEmployeeNo());
		htmlPart1=htmlPart1.replace("<hiddenEmployeeNo/>",ControlUtil.drawHidden(DBUtil.selectDBDepartmentID(con, UserInformation.getUserEmployeeNo()), "searchDepartmen"));	
		htmlPart1=htmlPart1.replace("<applicationDate/>",HtmlUtil.getDateDivSw("startLeaveDate","endLeaveDate", lcVo.getStartLeaveDate(),lcVo.getEndLeaveDate()));
		htmlPart1=htmlPart1.replace("<SearchEmployeeNo/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployeeNo", "VN_EMPLOYEE", "ID", "EMPLOYEENO", "UNIT_ID='" + lro.get(0).getUID()+ "'", lcVo.getSearchEmployeeNo()));
		htmlPart1=htmlPart1.replace("<SearchEmployee/>",ControlUtil.drawSelectDBControl(con, out, "searchEmployee", "VN_EMPLOYEE", "ID", "EMPLOYEE", "UNIT_ID='" + lro.get(0).getUID()+ "'", lcVo.getSearchEmployee()));
		htmlPart1=htmlPart1.replace("<msg/>",HtmlUtil.getMsgDiv(lcVo.getMsg()));
		
	
		if(lcVo.isShowDataTable()){
			htmlPart1=htmlPart1.replace("<drawTableM/>",HtmlUtil.drawLeaveCardTable(
					SqlUtil.getUnitLeaveCard(lcVo),HtmlUtil.drawTableMcheckButton(),  con, out,UrlUtil.pageMsList));
		}
	
		
	    out.println(htmlPart1);
	    }
}

