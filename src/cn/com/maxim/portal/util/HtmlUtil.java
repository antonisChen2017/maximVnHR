package cn.com.maxim.portal.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.vo.calendarVO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.repAttendanceVO;
import cn.com.maxim.portal.hr.rev_empSettUnit;
import cn.com.maxim.portal.webUI.WebDBTableCL;
import cn.com.maxim.portal.webUI.WebDBTableEx;
import cn.com.maxim.portal.webUI.WebDBTableLO;
import cn.com.maxim.potral.consts.keyConts;

public class HtmlUtil
{
	
	public final String gethtml(String htmlURL)
	{

		InputStreamReader isr;
		String UI = "";
		try
		{
			isr = new InputStreamReader(this.getClass().getResourceAsStream(htmlURL), "UTF-8");
			BufferedReader read = new BufferedReader(isr);
			String line = "";

			while ((line = read.readLine()) != null)
			{
				UI = UI + line + "\r\n";
			}
			// out.write(totelLine);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UI;
	}

	public final String getUIJs()
	{
		InputStreamReader isr;
		String UI = "";
		try
		{
			isr = new InputStreamReader(this.getClass().getResourceAsStream("/cn/com/maxim/portal/html/includeJS.html"), "UTF-8");
			BufferedReader read = new BufferedReader(isr);

			String line = "";
			while ((line = read.readLine()) != null)
			{
				UI = UI + line + "\r\n";
			}
			// out.write(totelLine);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UI;
	}

	public final String getUICss()
	{
		InputStreamReader isr;
		String UI = "";
		try
		{
			isr = new InputStreamReader(this.getClass().getResourceAsStream("/cn/com/maxim/portal/html/includeCss.html"), "UTF-8");
			BufferedReader read = new BufferedReader(isr);

			String line = "";
			while ((line = read.readLine()) != null)
			{
				UI = UI + line + "\r\n";
			}
			// out.write(totelLine);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UI;
	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public static final String getRowUIDivStart(String title)
	{
		StringBuilder Sb = new StringBuilder("<!-- BEGIN PAGE ROW--> \r").append("<div class=\"row \"> \r").append("<div class=\"col-md-12 col-sm-12\"> \r").append("<div class=\"portlet no-border\"> \r").append("<div class=\"tab-pane\" id=\"tab_form_1\"> \r").append("<div class=\"portlet\"> \r").append(" <div class=\"portlet-title\"> \r").append("  <div class=\"caption\"> <i class=\"fa fa-search\"></i>" + title + "</div> \r").append("  <div class=\"tools\"> <a href=\"javascript:;\" class=\"collapse\"></a>  </div> \r").append("</div> \r").append("<div class=\"portlet-body form\">  \r").append("  <!-- BEGIN FORM--> \r");

		return Sb.toString();

	}

	public static final String getRowUIDivEnd()
	{
		StringBuilder Sb = new StringBuilder(" <!-- END FORM-->   \r").append("</div>  \r").append("</div>  \r").append("<script type=\"text/javascript\"> \r").append(" function    saveData(){  \r").append("		if($('#SearchReasons').val()=='0'){  \r").append("				alert('請選擇加班事由');  \r").append("				return;  \r").append("		}  \r")

				.append("		if($('#SearchUnit').val()=='0'){  \r").append("				alert('請選擇单位');  \r").append("				return;  \r").append("		}  \r")

				.append("		if($('#startTimeHh').val()=='00'){  \r").append("				alert('請選擇開始时间');  \r").append("				return;  \r").append("		}  \r")

				.append("		if($('#endTimeHh').val()=='00'){  \r").append("				alert('請選擇結束时间');  \r").append("				return;  \r").append("		}  \r")

				.append("		if($('#addTime').val()=='0'){  \r").append("				alert('請選擇总共小时');  \r").append("				return;  \r").append("		}  \r")

				.append("		if($('#SearchEmployee').val()=='0'){  \r").append("				alert('請選擇姓名工號');  \r").append("				return;  \r").append("		}  \r").append("		  \r").append("		ActionForm.act.value='Save';  \r").append("		ActionForm.submit();   \r")

				.append("	}  \r")

				.append(" function    queryData(){  \r")

				.append("		if($('#SearchDepartmen').val()=='0'){  \r").append("				alert('请选择部门');  \r").append("				return;  \r").append("		}  \r")

				.append("		ActionForm.act.value='QUE';  \r").append("		ActionForm.submit();   \r")

				.append("	}  \r").append("	</script> \r");

		return Sb.toString();

	}

	public static final String getFooterUIDiv()
	{
		StringBuilder Sb = new StringBuilder(" </div> \r").append("</div>\r").append("  <!-- END PAGE CONTENT --> \r").append("</div>\r").append("<!-- END PAGE CONTAINER -->  <!-- BEGIN FOOTER --> \r").append("<div id=\"footer\" class=\"footer\"> \r").append("<div class=\"footer-inner\"> </div> \r").append("<div class=\"footer-tools\"></div> \r").append("<!-- END FOOTER -->  \r").append("</div> \r").append("<!-- END WRAPPER -->  \r");

		return Sb.toString();

	}

	public static final String getTitleUIDiv(String title, String subTitle)
	{
		StringBuilder Sb = new StringBuilder("<!-- BEGIN PAGE CONTAINER --> \r").append("<div id=\"content\" class=\"page-container\">  \r").append("<!-- BEGIN PAGE CONTENT -->  \r").append("<div class=\"page-content-wrapper\">   \r").append("<!-- END TICKER-->  \r").append("<div class=\"page-content fullwidth\">   \r").append("  <!-- BEGIN PAGE HEADER-->  \r").append(" <div class=\"row\">   \r").append("   <!-- BEGIN PAGE TITLE-->  \r").append("   <h3 class=\"page-title\">" + title + "<small>" + subTitle + "</small> </h3>  \r").append("   <!-- END PAGE TITLE-->   \r").append(" </div>  \r").append("<div class=\"clearfix\"> </div>\r").append("<!-- END PAGE ROW--> \r");

		return Sb.toString();

	}

	/**
	 * 選擇年月
	 * 
	 * @param ID
	 * @param Date
	 * @return
	 */
	public static final String getYearMonthDiv(String ID, String Date)
	{

		StringBuilder Sb = new StringBuilder("<div class=\"input-group input-medium date \" data-date-format=\"yyyy/mm\"> \r\n")
				.append("  <input type=\"text\"   width='50px'  ID='" + ID + "'  name='" + ID + "' style=\"width:110px;background:url(/javascript/images/cal.png) no-repeat right;\" class=\"form-control\"   value='" + Date + "' >  \r\n").append("   <span class=\"input-group-btn\"> \r\n").append("    </span></div> \r\n");
		Sb.append("   	<script> \r\n");
		Sb.append("  $('#" + ID + "').datepicker({  \r\n");
		Sb.append(" changeMonth: true,    \r\n");
		Sb.append(" changeYear: true,    \r\n");
		Sb.append(" dateFormat: 'yy/mm',    \r\n");
		Sb.append(" showButtonPanel: true,    \r\n");
		Sb.append(" monthNamesShort: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],    \r\n");
		Sb.append(" closeText: '选择',    \r\n");
		Sb.append(" currentText: '本月',    \r\n");
		Sb.append(" isSelMon:'true',    \r\n");
		Sb.append(" onChangeMonthYear : function (dateText, inst) {    \r\n");
		Sb.append(" //alert(dateText);   \r\n");
	    Sb.append("    var month = +$(\"#ui-datepicker-div .ui-datepicker-month :selected\").val() + 1,    \r\n");
		Sb.append("       year = $(\"#ui-datepicker-div .ui-datepicker-year :selected\").val();    \r\n");
		Sb.append("    if (month < 10) {    \r\n");
		Sb.append("        month = '0' + month;    \r\n");
		Sb.append("    }    \r\n");

		Sb.append("   this.value = year + '/' + month;    \r\n");
		//Sb.append("    if (typeof this.blur === 'function') {    \r\n");
		//Sb.append("         this.blur();    \r\n");
		//Sb.append("    }    \r\n");
		Sb.append("   }   \r\n");
		Sb.append("    });   \r\n");
		Sb.append("   	</script> \r\n");
		Sb.append(" <style type=\"text/css\"> \r\n");
		Sb.append(" .ui-datepicker-calendar { \r\n");
		Sb.append("     display: none; \r\n");
		Sb.append("     } \r\n");
		Sb.append(".ui-datepicker-div{font-size:40%;}  \r\n");
		
		
		Sb.append("     </style> \r\n");
		return Sb.toString();

	}

	
	/**
	 * 選擇日期(開始)
	 * 
	 * @param ID
	 * @param Date
	 * @return
	 */
	public static final String getStartDateDiv(String ID,String EID ,String Date)
	{

		StringBuilder Sb = new StringBuilder("<div class=\"input-group input-medium date \"  data-date-format=\"yyyy/mm/dd\"> \r").
				append("  <input type=\"text\" ID='" + ID + "'  name='" + ID + "' style=\"width:110px;background:url(/javascript/images/cal.png) no-repeat right;\" class=\"form-control\"    value='" + Date + "' >  \r").
				append("   <span class=\"input-group-btn\"> \r").append("    </span> \r");
		Sb.append("   	<script> \r\n");
		Sb.append("  $('#" + ID + "').datepicker({  \r\n");
		Sb.append(" dateFormat: 'yy/mm/dd',    \r\n");
		Sb.append(" showButtonPanel: true,    \r\n");
		Sb.append(" closeText: '选择',    \r\n");
		Sb.append(" currentText: '本月',    \r\n");
		Sb.append(" isSelMon:'false'   \r\n");
		Sb.append("    }).on( \"change\", function() { ");
		Sb.append("   "+EID+".datepicker( \"option\", \"minDate\", getDate( this ) );  \r\n");
		Sb.append("  }  \r\n");
		Sb.append("   	</script> </span></div>\r\n");
		Sb.append(" <style type=\"text/css\"> \r\n");
		Sb.append(" ui-datepicker-div{font-size:40%;}  \r\n");
		Sb.append("     </style> \r\n");
		return Sb.toString();

	}
	/**
	 * 選擇日期(開始)
	 * 
	 * @param ID
	 * @param Date
	 * @return
	 */
	public static final String getEndDateDiv(String ID,String SID ,String Date)
	{

		StringBuilder Sb = new StringBuilder("<div class=\"input-group input-medium date \"  data-date-format=\"yyyy/mm/dd\"> \r").
				append("  <input type=\"text\" ID='" + ID + "'  name='" + ID + "' style=\"width:110px;background:url(/javascript/images/cal.png) no-repeat right;\" class=\"form-control\"    value='" + Date + "' >  \r").
				append("   <span class=\"input-group-btn\"> \r").append("    </span> \r");
		Sb.append("   	<script> \r\n");
		Sb.append("  $('#" + ID + "').datepicker({  \r\n");
		Sb.append(" dateFormat: 'yy/mm/dd',    \r\n");
		Sb.append(" showButtonPanel: true,    \r\n");
		Sb.append(" closeText: '选择',    \r\n");
		Sb.append(" currentText: '本月',    \r\n");
		Sb.append(" isSelMon:'false'  \r\n");
		Sb.append("   })  \r\n");
		Sb.append("   	</script> </span></div>\r\n");
		Sb.append(" <style type=\"text/css\"> \r\n");
		Sb.append(" ui-datepicker-div{font-size:40%;}  \r\n");
		Sb.append("     </style> \r\n");
		return Sb.toString();

	}
	/**
	 * 選擇日期
	 * 
	 * @param ID
	 * @param Date
	 * @return
	 */
	public static final String getDateDiv(String ID, String Date)
	{

		StringBuilder Sb = new StringBuilder("<div class=\"input-group input-medium date \"  data-date-format=\"yyyy/mm/dd\"> \r").
				append("  <input type=\"text\" ID='" + ID + "'  name='" + ID + "' style=\"width:110px;background:url(/javascript/images/cal.png) no-repeat right;\" class=\"form-control\"    value='" + Date + "' >  \r").
				append("   <span class=\"input-group-btn\"> \r").append("    </span> \r");
		Sb.append("   	<script> \r\n");
		Sb.append("  $('#" + ID + "').datepicker({  \r\n");
		Sb.append(" dateFormat: 'yy/mm/dd',    \r\n");
		Sb.append(" showButtonPanel: true,    \r\n");
		Sb.append(" closeText: '选择',    \r\n");
		Sb.append(" currentText: '本月',    \r\n");
		Sb.append(" isSelMon:'false'   \r\n");
		Sb.append("    });   \r\n");
		Sb.append("   	</script> </span></div>\r\n");
		Sb.append(" <style type=\"text/css\"> \r\n");
		Sb.append(" ui-datepicker-div{font-size:40%;}  \r\n");
		Sb.append("     </style> \r\n");
		return Sb.toString();

	}

	/**
	 * 選擇日期(開始年月日~結束年月日)
	 * 
	 * @param ID
	 * @param Date
	 * @return
	 */
	public static final String getDateDivSw(String startID, String endID, String startValue, String endValue)
	{

		StringBuilder Sb = new StringBuilder("")

				.append("    <div class=\"input-group input-large date-picker input-daterange\" data-date=\"10/11/2012\" data-date-format=\"yyyy/mm/dd\"> \r").append("     <input type=\"text\"  ID='" + startID + "'  name='" + startID + "'  class=\"form-control\" name=\"from\"   value='" + startValue + " '> \r").append("      <span class=\"input-group-addon\"> 至 </span> \r").append("     <input type=\"text\"  '" + endID + "'  name='" + endID + "' class=\"form-control\" name=\"to\"    value='" + endValue + "' > \r").append("    </div> \r");

		return Sb.toString();

	}

	/**
	 * 選擇时间
	 * 
	 * @param StartID
	 * @param EndID
	 * @param StartTime
	 * @param EndTime
	 * @return
	 */
	public static final String getTimeDiv(String StartID, String EndID, String StartTime, String EndTime, overTimeVO otVo)
	{
		String startTimeHh = otVo.getStartTimeHh();
		String startTimemm = otVo.getStartTimemm();
		String endTimeHh = otVo.getEndTimeHh();
		String endTimemm = otVo.getEndTimemm();
	
				
		// 各班加班时间切換 start
		int iSHh = 0;
		int iEHh = 23;

		// 各班加班时间切換 end

		StringBuilder Sb = new StringBuilder("").append("<div  id='changeTimeDiv' class=\"row\">\r").append("<div class=\"col-md-5\"><div class=\"col-md-4\"> \r")
				// .append("<div class=\"form-group\">\r")
				.append("開始时间</div> \r").append("<div class=\"col-md-3\"> \r").append("<SELECT class=\"populate select2_category form-control\"  id='startTimeHh' name='startTimeHh' data-placeholder=\"\"  tabindex=\"2\" >  \r");
		for (int i = iSHh; i <= iEHh; i++)
		{
			if (i <= 9)
			{

				String timeHh = "0" + i;
				if (startTimeHh.equals(timeHh))
				{
					Sb.append("<OPTION value=0" + i + " selected>0" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=0" + i + " >0" + i + "</OPTION>  \r");
				}
			}
			else
			{
				String timeHh = String.valueOf(i);
				if (startTimeHh.equals(timeHh))
				{
					Sb.append("<OPTION value=" + i + " selected>" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=" + i + ">" + i + "</OPTION>  \r");
				}
			}

		}
		Sb.append("</SELECT> \r\n");
		Sb.append(" </div><div class=\"col-md-1\">時</div><div class=\"col-md-3\">  \r");
		Sb.append("<SELECT class=\"populate select2_category form-control\"  id='startTimemm' name='startTimemm' data-placeholder=\"\"  tabindex=\"2\" > \r");
		//Sb.append("<OPTION value=01 >01</OPTION> \r");
		//Sb.append("<OPTION value=30 >30</OPTION> \r");
		for (int i = 0; i <= 59; i=i+30)
		{
			System.out.println("1");
				if (i <= 9)
				{
					System.out.println("2");
					String sTimemm = String.valueOf( i);
					System.out.println("startTimemm"+startTimemm);
					System.out.println("sTimemm"+sTimemm);
						if (startTimemm.equals(sTimemm))
						{
							System.out.println("startTimemm  2");
							Sb.append("<OPTION value=0" + i + " selected>0" + i + "</OPTION> \r");
						}
						else
						{
							Sb.append("<OPTION value=0" + i + " >0" + i + "</OPTION> \r");
						}
					
				}
				else
				{
					String sTimemm = String.valueOf(i);
					if (startTimemm.equals(sTimemm))
					{
						Sb.append("<OPTION value=" + i + " selected>" + i + "</OPTION> \r");
					}
					else
					{
						Sb.append("<OPTION value=" + i + " >" + i + "</OPTION> \r");
					}
				}
			
		}
		Sb.append("</SELECT> \r");
		Sb.append("</div> \r");
		Sb.append("<div class=\"col-md-1\">分</div>  \r");
		Sb.append("</div>  \r");
		Sb.append(" <div class=\"col-md-2\"> \r");
		Sb.append(" <div class=\"col-md-12\">  \r");
		Sb.append(" <span class=\"input-group-addon\"> 至 </span> \r");
		Sb.append("</div>  \r");
		Sb.append("</div>  \r");
		Sb.append("<div class=\"col-md-5\"> \r");
		Sb.append("<div class=\"col-md-4\">結束时间</div> \r");
		Sb.append("<div class=\"col-md-3\"> \r");
		Sb.append("<SELECT class=\"populate select2_category form-control\"  id='endTimeHh' name='endTimeHh' data-placeholder=\"\"  tabindex=\"2\"  >  \r");
		for (int i = iSHh; i <= iEHh; i++)
		{
			if (i <= 9)
			{

				String timeHh = "0" + i;
				if (endTimeHh.equals(timeHh))
				{
					Sb.append("<OPTION value=0" + i + " selected>0" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=0" + i + " >0" + i + "</OPTION>  \r");
				}

			}
			else
			{
				String timeHh = String.valueOf(i);
				if (endTimeHh.equals(timeHh))
				{
					Sb.append("<OPTION value=" + i + " selected>" + i + "</OPTION> \r");
				}
				else
				{
					Sb.append("<OPTION value=" + i + " >" + i + "</OPTION> \r");
				}
			}

		}
		Sb.append("</SELECT>   \r");
		Sb.append("</div><div class=\"col-md-1\"> \r");
		Sb.append("時 \r");
		Sb.append("</div>  \r");
		Sb.append("<div class=\"col-md-3\"> \r");
		Sb.append("<SELECT class=\"populate select2_category form-control\"  id='endTimemm' name='endTimemm' data-placeholder=\"\"  tabindex=\"2\" > \r");
	
		for (int i = 0; i <= 59; i=i+30)
		{
		
				if (i <= 9)
				{
					String eTimemm = String.valueOf( i);
					if (endTimemm.equals(eTimemm))
					{
						Sb.append("<OPTION value=0" + i + " selected>0" + i + "</OPTION>  \r");
					}
					else
					{
						Sb.append("<OPTION value=0" + i + " >0" + i + "</OPTION>  \r");
					}
				}
				else
				{
					String eTimemm = String.valueOf(i);
					if (endTimemm.equals(eTimemm))
					{
						Sb.append("<OPTION value=" + i + " selected>" + i + "</OPTION>  \r");
					}
					else
					{
						Sb.append("<OPTION value=" + i + " >" + i + "</OPTION>  \r");
					}
				}
			
		}
		Sb.append("</SELECT> </div><div class=\"col-md-1\">分 </div> \r");
		Sb.append("<script>  \r\n");
		Sb.append("	jQuery(document).ready(function() {    \r\n");
		Sb.append(" $('#startTimemm').select2(); \r\n");
		Sb.append(" $('#endTimeHh').select2(); \r\n");
		Sb.append(" $('#endTimemm').select2(); \r\n");
		Sb.append(" $('#startTimeHh').select2(); \r\n");
		Sb.append("     }); \r\n");
		Sb.append("</script>  \r\n");
		Sb.append("</div> </div>  \r");

		return Sb.toString();
	}

	/**
	 * 選擇时间
	 * 
	 * @param StartID
	 * @param EndID
	 * @param StartTime
	 * @param EndTime
	 * @return
	 */
	public static final String getStopWorkTimeDiv(String StartID, String EndID, String StartTime, String EndTime, overTimeVO otVo)
	{
		String startTimeHh = otVo.getStartTimeHh();
		String startTimemm = otVo.getStartTimemm();
		String endTimeHh = otVo.getEndTimeHh();
		String endTimemm = otVo.getEndTimemm();
		// 各班加班时间切換 start
		int iSHh = 0;
		int iEHh = 23;

		// 各班加班时间切換 end

		StringBuilder Sb = new StringBuilder("").append("<div  id='changeTimeDiv' class=\"row\">\r").append("<div class=\"col-md-5\"><div class=\"col-md-4\"> \r")
				// .append("<div class=\"form-group\">\r")
				.append("開始时间</div> \r").append("<div class=\"col-md-3\"> \r").append("<SELECT class=\"form-control\" name='startTimeHh' id='startTimeHh'>  \r");

		for (int i = iSHh; i <= iEHh; i++)
		{
			if (i <= 9)
			{

				String timeHh = "0" + i;
				if (startTimeHh.equals(timeHh))
				{
					Sb.append("<OPTION value=0" + i + " selected>0" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=0" + i + " >0" + i + "</OPTION>  \r");
				}
			}
			else
			{
				String timeHh = String.valueOf(i);
				if (startTimeHh.equals(timeHh))
				{
					Sb.append("<OPTION value=" + i + " selected>" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=" + i + ">" + i + "</OPTION>  \r");
				}
			}

		}
		Sb.append("</SELECT></div><div class=\"col-md-1\">時</div><div class=\"col-md-3\">  \r");
		Sb.append(" <SELECT class=\"form-control\" name='startTimemm' id='startTimemm'> \r");
		for (int i = 0; i <= 59; i++)
		{
			if (i <= 9)
			{

				String sTimemm = "0" + i;
				if (startTimemm.equals(sTimemm))
				{
					Sb.append("<OPTION value=0" + i + " selected>0" + i + "</OPTION> \r");
				}
				else
				{
					Sb.append("<OPTION value=0" + i + " >0" + i + "</OPTION> \r");
				}

			}
			else
			{
				String sTimemm = String.valueOf(i);
				if (startTimemm.equals(sTimemm))
				{
					Sb.append("<OPTION value=" + i + " selected>" + i + "</OPTION> \r");
				}
				else
				{
					Sb.append("<OPTION value=" + i + " >" + i + "</OPTION> \r");
				}
			}
		}
		Sb.append("</SELECT></div> \r");
		Sb.append("<div class=\"col-md-1\">分</div>  \r");
		Sb.append("</div>  \r");
		Sb.append(" <div class=\"col-md-2\"> \r");
		Sb.append(" <div class=\"col-md-12\">  \r");
		Sb.append(" <span class=\"input-group-addon\"> 至 </span> \r");
		Sb.append("</div>  \r");
		Sb.append("</div>  \r");
		Sb.append("<div class=\"col-md-5\"> \r");
		Sb.append("<div class=\"col-md-4\">結束时间</div> \r");
		Sb.append("<div class=\"col-md-3\"> \r");
		Sb.append("<SELECT class=\"form-control\" name='endTimeHh' id='endTimeHh'> \r");
		for (int i = iSHh; i <= iEHh; i++)
		{
			if (i <= 9)
			{

				String timeHh = "0" + i;
				if (endTimeHh.equals(timeHh))
				{
					Sb.append("<OPTION value=0" + i + " selected>0" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=0" + i + " >0" + i + "</OPTION>  \r");
				}

			}
			else
			{
				String timeHh = String.valueOf(i);
				if (endTimeHh.equals(timeHh))
				{
					Sb.append("<OPTION value=" + i + " selected>" + i + "</OPTION> \r");
				}
				else
				{
					Sb.append("<OPTION value=" + i + " >" + i + "</OPTION> \r");
				}
			}

		}
		Sb.append("</SELECT></div>  \r");
		Sb.append("<div class=\"col-md-1\"> \r");
		Sb.append("時 \r");
		Sb.append("</div>  \r");
		Sb.append("<div class=\"col-md-3\"> \r");
		Sb.append(" <SELECT class=\"form-control\" name='endTimemm' id='endTimemm'> \r");
		for (int i = 0; i <= 59; i++)
		{
			if (i <= 9)
			{
				String eTimemm = "0" + i;
				if (endTimemm.equals(eTimemm))
				{
					Sb.append("<OPTION value=0" + i + " selected>0" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=0" + i + " >0" + i + "</OPTION>  \r");
				}
			}
			else
			{
				String eTimemm = String.valueOf(i);
				if (endTimemm.equals(eTimemm))
				{
					Sb.append("<OPTION value=" + i + " selected>" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=" + i + " >" + i + "</OPTION>  \r");
				}
			}
		}
		Sb.append("</SELECT> </div><div class=\"col-md-1\">分 </div> \r");
		Sb.append("</div> </div>  \r");

		return Sb.toString();
	}

	/**
	 * 選擇时间
	 * 
	 * @param StartID
	 * @param EndID
	 * @param StartTime
	 * @param EndTime
	 * @return
	 */
	public static final String getSpinnerDiv(String ID, String value, String max, String min, String step)
	{
		StringBuilder Sb = new StringBuilder("")
				.append("<input id='" + ID + "' name='" + ID + "'   type=\"text\" class=\"form-control\" value=\"" + value + "\" > \r")
				.append("<script type=\"text/javascript\"> \r")
				.append("$(function(){ \r")
				.append("$( \"#" + ID + "\" ).spinner({ \r")
				.append("	   max:"+max+", \r")
				.append("		min:"+min+", \r")
				.append("		step:"+step+" \r")
				.append("	}); \r")
				.append("	}); \r")
				.append("	</script> \r");

		return Sb.toString();
	}

	/**
	 * 選擇时间
	 * 
	 * @param StartID
	 * @param EndID
	 * @param StartTime
	 * @param EndTime
	 * @return
	 */
	public static final String getJsDwGearingDiv(String DwID1, String DwID2)
	{
		StringBuilder Sb = new StringBuilder("").append("<script type=\"text/javascript\"> \r").append(" $(document).ready(function(){  \r").append("    $('#" + DwID1 + "').change(function(){  \r").append("	     var p1=$(this).children('option:selected').val();  //这就是selected的值  \r").append("     $('#" + DwID2 + "').val(p1);       \r").append("	   })  \r").append("    $('#" + DwID2 + "').change(function(){  \r").append("	     var p1=$(this).children('option:selected').val();  //这就是selected的值  \r").append("     $('#" + DwID1 + "').val(p1);       \r").append("	   })  \r").append("	})   \r").append("	</script> \r");

		return Sb.toString();
	}

	public static final String getSaveStatusJs()
	{

		StringBuilder Sb = new StringBuilder("").append("<script type=\"text/javascript\"> \r").append(" $(document).ready(function(){  \r").append("		var Status=$('#Status').val();  \r")
				// .append(" alert('Status :' +Status) \r")
				.append("	if( Status=='U'){ \r").append("		$('#saveBut').attr('disabled','disabled')  \r")
				// .append(" alert('disabled :' +disabled) \r")
				.append("		} \r").append("	if( Status=='I'){ \r").append("		$('#saveBut').attr('disabled','disabled')  \r")
				// .append(" alert('disabled :' +disabled) \r")
				.append("		} \r").append("		 \r").append("	})   \r").append("	</script> \r");
		return Sb.toString();
	}

	public static final String getListStatusJs()
	{

		StringBuilder Sb = new StringBuilder("").append("<script type=\"text/javascript\"> \r").append(" $(document).ready(function(){  \r").append("		var Status=$('#Status').val();  \r")
				// .append(" alert('Status :' +Status) \r")
				.append("	if( Status=='U'){ \r").append("		$('#ReferBut').attr('disabled','disabled')  \r")
				// .append(" alert('disabled :' +disabled) \r")
				.append("		} \r").append("	if( Status=='I'){ \r").append("		$('#ReferBut').attr('disabled','disabled')  \r")
				// .append(" alert('disabled :' +disabled) \r")
				.append("		} \r").append("		 \r").append("	})   \r").append("	</script> \r");
		return Sb.toString();
	}

	public static String drawTableM(String sqlTotal, String sql, String sqlStatus, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";
		totalTime = DBUtil.getTotalTime(con, sqlTotal);
		resultS = DBUtil.getStatus(con, sqlStatus);

		boolean bStatus = false;

		String msg = "";

		if (resultS.equals("S"))
		{
			Status = "<span class=\"label label-primary btn-sm\">保存</span><input type='hidden' name='Status' id='Status' value='" + resultS + "'>";
			bStatus = true;
			msg = "		總時數:" + totalTime + "小時    狀態:" + Status;
		}
		if (resultS.equals("U"))
		{
			Status = "<span class=\"label label-info btn-sm\">提交</span><input type='hidden' name='Status' id='Status' value='" + resultS + "'>";
			bStatus = false;
			msg = "		總時數:" + totalTime + "小時    狀態:" + Status;
		}
		if (resultS.equals("I"))
		{
			Status = "<span class=\"label label-success btn-sm\">審核通過</span><input type='hidden' name='Status' id='Status' value='" + resultS + "'>";
			bStatus = false;
			msg = "		總時數:" + totalTime + "小時    狀態:" + Status;
		}
		if (resultS.equals("R"))
		{
			Status = "<span class=\"label label-danger btn-sm\">退回</span><input type='hidden' name='Status' id='Status' value='" + resultS + "'>";
			bStatus = true;
			msg = "		總時數:" + totalTime + "小時    狀態:" + Status;
		}

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}
		if (!page.equals("save"))
		{
			bStatus = false;
		}

		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTable(out, "table table-striped table-bordered table-hover", msg, htmlButton, bStatus);
		// System.out.println("table :"+table.toString());
	}

	public static String drawTableMcheckButton()
	{
		return "<button class=\"btn btn-success\" id='butI' onclick=\"checkOK()\" type=\"button\">審核通過</button> \r <button id='butR' class=\"btn btn-danger\" onclick=\"checkNO()\"   type=\"button\">退回</button>";
	}

	public static String drawTableMReferButton()
	{
		return " <button id='ReferBut' class=\"btn btn-info \" onclick=\"ActionForm.act.value='Refer';ActionForm.submit();\" type=\"button\">提交</button>";
	}

	public static String drawTableMSaveButton()
	{
		return " <button id='saveBut' class=\"btn btn-primary \" onclick=\"saveData()\" type=\"button\">保存</button>";
	}

	public static String drawTableMExcelButton()
	{
		return "<button id='excelBut' class=\"btn btn-primary \" onclick=\"outExecl()\" type=\"button\">輸出EXECL</button>";
	}

	public static String getOverTimeClass(overTimeVO otVo)
	{

		ArrayList Options = new ArrayList();
		Hashtable ht = new Hashtable();
		ht.put("value", "1");
		ht.put("text", "早班");
		Options.add(ht);
		Hashtable ht2 = new Hashtable();
		ht2.put("value", "2");
		ht2.put("text", "晚班");
		Options.add(ht2);

		return ControlUtil.drawCustomSelectShared("overTimeClass", Options, otVo.getOverTimeClass());
	}

	public static final String getNoteDiv(String ID, String Value)
	{
		return "<textarea id='" + ID + "'   name='" + ID + "'   class=\"form-control\" rows=\"2\">" + Value + "</textarea>";
	}

	public static final String getTextDiv(String ID, String Value, String placeholder)
	{
		// return "<input type=\"text\" name=\"userReasons\" id=\"userReasons\"
		// class=\"form-control\" placeholder=\"可輸入自訂加班事由\">
		return "<input type=\"text\"   name='" + ID + "'   id='" + ID + "'  class=\"form-control\" value='" + Value + "'placeholder='" + placeholder + "'>";
	}
    /**
     * email
     * @param ID
     * @param Value
     * @param placeholder
     * @return
     */
	public static final String getEmailDiv(String ID, String Value, String placeholder)
	{
		// return "<input type=\"text\" name=\"userReasons\" id=\"userReasons\"
		// class=\"form-control\" placeholder=\"可輸入自訂加班事由\">
		return "<input type=\"email\"   name='" + ID + "'   id='" + ID + "'  class=\"form-control\" value='" + Value + "'placeholder='" + placeholder + "'>";
	}
	/*
	 * 訊息
	 */
	public static final String getMsgDiv(String msg)
	{
		StringBuilder Sb = new StringBuilder("&nbsp;");
		if (msg != null)
		{
			if (!msg.equals(""))
			{
				Sb.append("<script>");
				Sb.append(" jQuery(document).ready(function() { ");
				Sb.append("   layer.msg('");
				Sb.append(msg);
				Sb.append("'  ,  {offset: ['200px']} ); ");
				Sb.append("  });   ");
				Sb.append("</script>  ");
			}
		}
		return Sb.toString();
	}

	
	public static String drawTable(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}
		if (!page.equals("save"))
		{
			bStatus = "1";
		}

		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTableNo(out, "無資料", css, htmlButton, msg, bStatus);
		// System.out.println("table :"+table.toString());
	}
	
	public static String drawTableS(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}
		if (!page.equals("save"))
		{
			bStatus = "1";
		}

		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTableVoS(out, "無資料", css, htmlButton, msg, bStatus);
		// System.out.println("table :"+table.toString());
	}

	
	public static String drawTableSupplement(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}
		if (!page.equals("save"))
		{
			bStatus = "1";
		}

		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTableSupplement(out, "無資料", css, htmlButton, msg, bStatus);
		// System.out.println("table :"+table.toString());
	}
	
	/**
	 * 加班卡table
	 * @param sql
	 * @param htmlButton
	 * @param con
	 * @param out
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public static String drawOvertimeTable(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}

		if (page.equals("save"))
		{
			bStatus = "0";
		}
		if (page.equals(keyConts.pageMsList))
		{
			bStatus = "2";
		}
		if (page.equals(keyConts.pageLList))
		{
			bStatus = "1";
		}
		if (page.equals(keyConts.pageUsList))
		{
			bStatus = "U";
		}
		if (page.equals(keyConts.pageEmpUnitList))
		{
			bStatus = "E";
		}
		if (page.equals(keyConts.pageDtmList))
		{
			bStatus = "DT";
		}
		if (page.equals(keyConts.personnelList))
		{
			bStatus = "PL";
		}
		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getOvertimeTable(out, "無資料", css, htmlButton, msg, bStatus);
		// System.out.println("table :"+table.toString());
	}
	
	public static String drawLeaveCardTable(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}

		if (page.equals("save"))
		{
			bStatus = "0";
		}
		if (page.equals(keyConts.pageMsList))
		{
			bStatus = "2";
		}
		if (page.equals(keyConts.pageLList))
		{
			bStatus = "1";
		}
		if (page.equals(keyConts.pageUsList))
		{
			bStatus = "U";
		}
		if (page.equals(keyConts.pageEmpUnitList))
		{
			bStatus = "E";
		}
		if (page.equals(keyConts.pageDtmList))
		{
			bStatus = "DT";
		}
		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTableLC(out, "無資料", css, htmlButton, msg, bStatus);
		// System.out.println("table :"+table.toString());
	}

	/**
	 * 編輯資料表格
	 * @param sql
	 * @param htmlButton
	 * @param con
	 * @param out
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public static String drawTableEdit(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
	
		String css = "table table-striped table-bordered table-hover";


		String msg = "";


		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTableEdit(out, "無資料", css, htmlButton, msg, page);
		// System.out.println("table :"+table.toString());
	}
	
	
	/**
	 * 編輯資料表格
	 * @param sql
	 * @param htmlButton
	 * @param con
	 * @param out
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public static String drawTableEditT(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
	
		String css = "table table-striped table-bordered table-hover";


		String msg = "";


		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTableEditT(out, "無資料", css, htmlButton, msg, page);
		// System.out.println("table :"+table.toString());
	}
	
	
	public static String drawSalesCardTable(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}

		if (page.equals("save"))
		{
			bStatus = "0";
		}
		if (page.equals(keyConts.pageMsList))
		{
			bStatus = "2";
		}
		if (page.equals(keyConts.pageLList))
		{
			bStatus = "1";
		}
		if (page.equals(keyConts.pageUsList))
		{
			bStatus = "U";
		}
		if (page.equals(keyConts.pageEmpUnitList))
		{
			bStatus = "E";
		}
		if (page.equals(keyConts.pageDtmList))
		{
			bStatus = "DT";
		}
		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTableSales(out, "無資料", css, htmlButton, msg, bStatus);
		// System.out.println("table :"+table.toString());
	}
	
	
	
	
	public static String drawLateOutEarlyTable(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{

		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (page.equals("save"))
		{
			bStatus = "0";
		}

		WebDBTableLO table = new WebDBTableLO(con, sql);

		return table.getHTMLTableLE(out, "無資料", css, msg, htmlButton, bStatus);
		// System.out.println("table :"+table.toString());
	}

	/**
	 * 月考績表table
	 * 
	 * @param sql
	 * @param htmlButton
	 * @param con
	 * @param out
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public static String drawRepAttendanceTable(String sql, String htmlButton, Connection con, PrintWriter out, repAttendanceVO raVo) throws SQLException
	{

		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		// if(page.equals("save")){
		bStatus = "0";
		// }

		WebDBTableLO table = new WebDBTableLO(con, sql);

		return table.getHTMLTableRA(out, "無資料", css, msg, htmlButton, con, raVo);
		// System.out.println("table :"+table.toString());
	}

	/**
	 * 員工查询遲到早退表單
	 * 
	 * @param sql
	 * @param htmlButton
	 * @param con
	 * @param out
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public static String drawEmpLateOutEarly(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		// System.out.println("drawEmpLateOutEarly sql :"+sql);
		// htmlButton="<button id='saveBut' class=\"btn btn-primary \"
		// onclick=\"outExecl()\" type=\"button\">輸出EXECL</button>";

		if (page.equals("save"))
		{
			bStatus = "0";
		}

		WebDBTableLO table = new WebDBTableLO(con, sql);

		return table.getHTMLTableLO(out, "無資料", css, msg, htmlButton, bStatus);
		// System.out.println("table :"+table.toString());
	}

	/**
	 * 選擇小時
	 * 
	 * @param StartID
	 * @param EndID
	 * @param StartTime
	 * @param EndTime
	 * @return
	 */
	public static final String getLeaveCardTimeDiv(String ID, String Time)
	{

		StringBuilder Sb = new StringBuilder("").append("<SELECT class=\"populate select2_category form-control\"   data-placeholder=\"\"  tabindex=\"2\" name='" + ID + "' id='" + ID + "'>  \r");

		for (int i = 5; i <= 23; i++)
		{
			if (i <= 9)
			{
				if (Time.equals("0" + i))
				{
					// System.out.println("i : 0" + i);
					Sb.append("<OPTION value='0" + i + "' selected>0" + i + "</OPTION>  \r");
				}
				else
				{

					Sb.append("<OPTION value='0" + i + "' >0" + i + "</OPTION>  \r");
				}
			}
			else
			{
				if (Time.equals(String.valueOf(i)))
				{

					Sb.append("<OPTION value='" + i + "' selected>" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value='" + i + "' >" + i + "</OPTION>  \r");
				}
			}

		}
		Sb.append("</SELECT> \r\n");
		Sb.append("<script>  \r\n");
		Sb.append("	jQuery(document).ready(function() {    \r\n");
		Sb.append(" $('#" + ID + "').select2(); \r\n");
		Sb.append("     }); \r\n");
		Sb.append("</script>  \r\n");

		return Sb.toString();
	}

	/**
	 * 選擇分鐘
	 * 
	 * @param StartID
	 * @param EndID
	 * @param StartTime
	 * @param EndTime
	 * @return
	 */
	public static final String getLeaveCardMinuteDiv(String ID, String Time)
	{

		StringBuilder Sb = new StringBuilder("").append("<SELECT class=\"populate select2_category form-control\"   data-placeholder=\"\"  tabindex=\"2\" name='" + ID + "' id='" + ID + "'>  \r");

		for (int i = 0; i <= 59; i=i+30)
		{
			if (i <= 9)
			{
				if (Time.equals("0" + i))
				{
					// System.out.println("i : 0" + i);
					Sb.append("<OPTION value='0" + i + "' selected>0" + i + "</OPTION>  \r");
				}
				else
				{

					Sb.append("<OPTION value='0" + i + "' >0" + i + "</OPTION>  \r");
				}
			}
			else
			{
				if (Time.equals(String.valueOf(i)))
				{

					Sb.append("<OPTION value='" + i + "' selected>" + i + "</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value='" + i + "' >" + i + "</OPTION>  \r");
				}
			}

		}
		Sb.append("</SELECT> \r");
		Sb.append("<script>  \r\n");
		Sb.append("	jQuery(document).ready(function() {    \r\n");
		Sb.append(" $('#" + ID + "').select2(); \r\n");
		Sb.append("     }); \r\n");
		Sb.append("</script>  \r\n");

		return Sb.toString();
	}

	public static final String getLabelHtml(String value)
	{
		return "<SPAN class=\"control-label col-md-3\">" + value + "</SPAN>";
	}

	public static final String getDaterangeHtml(String startStopWorkDate, String endStopWorkDate, String startStopWorkTime, String endStopWorkTime)
	{

		StringBuilder Sb = new StringBuilder("");

		Sb.append("<div class=\"input-group  input-large date-picker input-daterange\" data-date=\"\" data-date-format=\"yyyy/mm/dd\"> ");
		Sb.append("<input type=\"text\" class=\"form-control\"  id='startStopWorkDate'  value='" + startStopWorkDate + "' name=\"startStopWorkDate\"> ");
		Sb.append("<SELECT class=\"form-control\"   name='startTimeHhmm'   id='startTimeHhmm'>");

		for (int i = 0; i <= 23; i++)
		{
			if (i <= 9)
			{
				if (startStopWorkTime.equals("0" + i + ":00"))
				{

					Sb.append("<OPTION value=0" + i + ":00  selected>0" + i + ":00</OPTION>  \r");
					Sb.append("<OPTION value=0" + i + ":30  selected>0" + i + ":30</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=0" + i + ":00 >0" + i + ":00</OPTION>  \r");
					Sb.append("<OPTION value=0" + i + ":30 >0" + i + ":30</OPTION>  \r");
				}
			}
			else
			{
				if (startStopWorkTime.equals(i + ":00"))
				{
					Sb.append("<OPTION value=" + i + ":00 selected>" + i + ":00</OPTION>  \r");
					Sb.append("<OPTION value=" + i + ":30 selected>" + i + ":30</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=" + i + ":00 >" + i + ":00</OPTION>  \r");
					Sb.append("<OPTION value=" + i + ":30 >" + i + ":30</OPTION>  \r");
				}
			}

		}
		if (startStopWorkTime.equals("23:59"))
		{
			Sb.append(" <OPTION value='23:59'  selected>23:59</OPTION> \r");
		}
		else
		{
			Sb.append(" <OPTION value='23:59'>23:59</OPTION> \r");
		}
		Sb.append("</SELECT> ");
		Sb.append(" <span class=\"input-group-addon\"> 至 </span>  ");
		Sb.append("<input type=\"text\" class=\"form-control\"  id='endStopWorkDate' value='" + endStopWorkDate + "'  name=\"endStopWorkDate\">  ");
		Sb.append("<SELECT class=\"form-control\" name='endTimeHhmm' id='endTimeHhmm' >  \r");
		for (int i = 0; i <= 23; i++)
		{
			if (i <= 9)
			{
				if (endStopWorkTime.equals("0" + i + ":00"))
				{
					Sb.append("<OPTION value=0" + i + ":00 selected>0" + i + ":00</OPTION>  \r");
					Sb.append("<OPTION value=0" + i + ":30 selected>0" + i + ":30</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=0" + i + ":00 >0" + i + ":00</OPTION>  \r");
					Sb.append("<OPTION value=0" + i + ":30 >0" + i + ":30</OPTION>  \r");
				}
			}
			else
			{
				if (endStopWorkTime.equals(i + ":00"))
				{
					Sb.append("<OPTION value=" + i + ":00 selected>" + i + ":00</OPTION>  \r");
					Sb.append("<OPTION value=" + i + ":30 selected>" + i + ":30</OPTION>  \r");
				}
				else
				{
					Sb.append("<OPTION value=" + i + ":00 >" + i + ":00</OPTION>  \r");
					Sb.append("<OPTION value=" + i + ":30 >" + i + ":30</OPTION>  \r");
				}
			}

		}
		if (endStopWorkTime.equals("23:59"))
		{
			Sb.append(" <OPTION value='23:59'  selected>23:59</OPTION> \r");
		}
		else
		{
			Sb.append(" <OPTION value='23:59'>23:59</OPTION> \r");
		}
		Sb.append("</SELECT> ");
		Sb.append("</div>  ");

		return Sb.toString();
	}

	public static String drawStopWorking(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}
		if (page.equals("save"))
		{
			bStatus = "0";
		}
		if (page.equals(keyConts.pageMsList))
		{
			bStatus = "2";
		}
		if (page.equals(keyConts.pageList))
		{
			bStatus = "1";
		}

		WebDBTableCL table = new WebDBTableCL(con, sql);

		return table.getHTMLTableStopWork(out, "無資料", css, htmlButton, msg, bStatus);
		// System.out.println("table :"+table.toString());
	}

	/**
	 * 取得下拉年分
	 * 
	 * @param eaVo
	 * @return
	 */
	public static String getYear(lateOutEarlyVO eaVo)
	{

		/*
		 * ArrayList Options=new ArrayList(); for(int i=2017;i<2037;i++){
		 * Hashtable ht=new Hashtable(); ht.put("value",String.valueOf(i));
		 * ht.put("text", String.valueOf(i)); Options.add(ht); }
		 */

		return "<input type=\"text\" class=\"input\" id=\"yearMomth\"> ";
	}

	/**
	 * 取得下拉月分
	 * 
	 * @param eaVo
	 * @return
	 */
	public static String getIsLate(lateOutEarlyVO eaVo)
	{

		StringBuilder Sb = new StringBuilder("");
		Sb.append("<SELECT class=\"populate select2_category form-control\" name='queryIsLate' id='queryIsLate' data-placeholder=\"\"  tabindex=\"2\" >  \r");
		if (eaVo.getQueryIsLate().equals("0"))
		{
			Sb.append("<OPTION value='0' selected>未選擇</OPTION>  \r");
		}
		else
		{
			Sb.append("<OPTION value='0' >未選擇</OPTION>  \r");
		}
		if (eaVo.getQueryIsLate().equals("1"))
		{
			Sb.append("<OPTION value='1' selected>遲到</OPTION>  \r");
		}
		else
		{
			Sb.append("<OPTION value='1' >遲到</OPTION>  \r");
		}
		if (eaVo.getQueryIsLate().equals("2"))
		{
			Sb.append("<OPTION value='2' selected>早退</OPTION>  \r");
		}
		else
		{
			Sb.append("<OPTION value='2' >早退</OPTION>  \r");
		}

		Sb.append("</SELECT> \r");
		Sb.append("<script>  \r\n");
		Sb.append("	jQuery(document).ready(function() {    \r\n");
		Sb.append(" $('#queryIsLate').select2(); \r\n");
		Sb.append("     }); \r\n");
		Sb.append("</script>  \r\n");
		return Sb.toString();
	}

	/**
	 * 產生當月月歷html
	 * 
	 * @param eaVo
	 * @return
	 */
	public static calendarVO getCalendar(Date date)
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(HtmlUtil.class);
		
		calendarVO cv = new calendarVO();
		Calendar ca = Calendar.getInstance();
		int day = DateUtil.getweekday(date, ca) - 1;
		System.out.println("day : " + day);
		int dayCount = DateUtil.getDaysOfTheMonth(date);
		StringBuilder Sb = new StringBuilder("");
		Sb.append("<table class=\"table\"> \r\n");
		int thcount = 1;
		// 第一行
		for (int i = 0; i < 7; i++)
		{
			if (i == 0)
			{
				Sb.append(" <th width='12.5%'>打卡时段</th>  \r\n");
			}
			if (i >= day)
			{
				Sb.append(" <th width='12.5%'>" + thcount + "</th>\r\n");
				thcount++;
			}
			else
			{
				Sb.append(" <th width='12.5%'> </th>\r\n");
			}

		}
		Sb.append(" </tr> </thead><tbody><tr>\r\n");
		int tducount = 1;
		// 第2行
		for (int i = 0; i < 7; i++)
		{
			if (i == 0)
			{
				Sb.append(" <td>上班 </td>\r\n");
			}
			if (i >= day)
			{
				Sb.append("<td><LateWT" + tducount + "/></th>\r\n");
				tducount++;
			}
			else
			{
				Sb.append(" <td> </td>\r\n");
			}

		}
		Sb.append(" </tr><tr>\r");
		// 第3行
		int tddcount = 1;
		for (int i = 0; i < 7; i++)
		{
			if (i == 0)
			{
				Sb.append(" <td>下班 </td>\r");
			}
			if (i >= day)
			{
				Sb.append("<td><EarlyWT" + tddcount + "/></th> \r\n");
				tddcount++;
			}
			else
			{
				Sb.append(" <td> </td>\r");
			}

		}
		Sb.append(" </tr> </tbody></table> <table class=\"table\"><thead>  <tr>\r\n");

		// table2 第一行

		// table3 第一行
		for (int i = 0; i < 8; i++)
		{
			if (i == 0)
			{
				Sb.append(" <th width='12.5%'>打卡時段</th>\r\n");
			}
			else
			{

				Sb.append(" <th width='12.5%'>" + thcount + "</th>\r\n");
				thcount++;
			}

		}
		Sb.append(" </tr> </thead><tbody><tr>\r\n");

		// 第2行
		for (int i = 0; i < 8; i++)
		{
			if (i == 0)
			{
				Sb.append(" <td>上班 </td>\r\n");
			}
			else
			{

				Sb.append("<td><LateWT" + tducount + "/></th>\r\n");
				tducount++;
			}

		}
		Sb.append(" </tr><tr>\r\n");
		// 第3行

		for (int i = 0; i < 8; i++)
		{
			if (i == 0)
			{
				Sb.append(" <td>下班 </td>\r\n");
			}
			else
			{

				Sb.append("<td><EarlyWT" + tddcount + "/></th>\r\n");
				tddcount++;
			}

		}
		Sb.append(" </tr> </tbody></table> <table class=\"table\"><thead>  <tr>\r\n");

		// table4 第一行
		for (int i = 0; i < 8; i++)
		{
			if (i == 0)
			{
				Sb.append(" <th width='12.5%'>打卡時段</th>\r\n");
			}
			else
			{

				Sb.append(" <th width='12.5%'>" + thcount + "</th>\r\n");
				thcount++;
			}

		}
		Sb.append(" </tr> </thead><tbody><tr>  \r\n");

		// 第2行
		for (int i = 0; i < 8; i++)
		{
			if (i == 0)
			{
				Sb.append(" <td>上班 </td> \r\n");
			}
			else
			{

				Sb.append("<td><LateWT" + tducount + "/></th> \r\n");
				tducount++;
			}

		}
		Sb.append(" </tr><tr> \r\n");
		// 第3行

		for (int i = 0; i < 8; i++)
		{
			if (i == 0)
			{
				Sb.append(" <td>下班 </td>\r\n");
			}
			else
			{
				Sb.append("<td><EarlyWT" + tddcount + "/></th>\r\n");
				tddcount++;
			}

		}
		Sb.append(" </tr> </tbody></table> <table class=\"table\"><thead>  <tr>\r\n");

		if (dayCount > thcount)
		{
			// table5 第一行
			for (int i = 0; i < 8; i++)
			{

				if (i == 0)
				{
					Sb.append(" <th width='12.5%'>打卡時段</th>\r\n");
				}
				else if (thcount <= dayCount)
				{

					Sb.append(" <th width='12.5%'>" + thcount + "</th>\r\n");
					thcount++;
				}
				else
				{
					Sb.append(" <th width='12.5%'></th>\r\n");
					thcount++;
				}

			}
			Sb.append(" </tr> </thead><tbody><tr>  \r\n");

			// 第2行
			for (int i = 0; i < 8; i++)
			{
				if (i == 0)
				{
					Sb.append(" <td>上班 </td> \r\n");
				}
				else if (tducount <= dayCount)
				{

					Sb.append("<td><LateWT" + tducount + "/></th> \r\n");
					tducount++;
				}
				else
				{
					Sb.append("<td></th> \r\n");
					tducount++;
				}

			}
			Sb.append(" </tr><tr> \r\n");
			// 第3行

			for (int i = 0; i < 8; i++)
			{
				if (i == 0)
				{
					Sb.append(" <td>下班 </td>\r\n");
				}
				else if (tddcount <= dayCount)
				{

					Sb.append("<td><EarlyWT" + tddcount + "/></th> \r\n");
					tddcount++;
				}
				else
				{
					Sb.append("<td></th>\r\n");
					tddcount++;
				}

			}
			Sb.append(" </tr> </tbody></table> <table class=\"table\"><thead>  <tr>\r\n");
		}
		
	
		if (dayCount >= thcount)
		{
			
			// table6 第一行
			for (int i = 0; i < 8; i++)
			{

				if (i == 0)
				{
					Sb.append(" <th width='12.5%'>打卡時段</th>\r\n");
				}
				else if (thcount <= dayCount)
				{

					Sb.append(" <th width='12.5%'>" + thcount + "</th>\r\n");
					thcount++;
				}
				else
				{
					Sb.append(" <th width='12.5%'></th>\r\n");
					thcount++;
				}

			}
			Sb.append(" </tr> </thead><tbody><tr>  \r\n");

			// 第2行
			for (int i = 0; i < 8; i++)
			{
				if (i == 0)
				{
					Sb.append(" <td>上班 </td> \r\n");
				}
				else if (tducount <= dayCount)
				{

					Sb.append("<td><LateWT" + tducount + "/></th> \r\n");
					tducount++;
				}
				else
				{
					Sb.append("<td></th> \r\n");
					tducount++;
				}

			}
			Sb.append(" </tr><tr> \r\n");
			// 第3行

			for (int i = 0; i < 8; i++)
			{
				if (i == 0)
				{
					Sb.append(" <td>下班 </td>\r\n");
				}
				else if (tddcount <= dayCount)
				{

					Sb.append("<td><EarlyWT" + tddcount + "/></th> \r\n");
					tddcount++;
				}
				else
				{
					Sb.append("<td></th>\r\n");
					tddcount++;
				}

			}
			Sb.append(" </tr> </tbody></table> <table class=\"table\"><thead>  <tr>\r\n");
		}
		
		
		if (dayCount >= thcount)
		{
		
			// table7 第一行
			for (int i = 0; i < 8; i++)
			{

				if (i == 0)
				{
					Sb.append(" <th width='12.5%'>打卡時段</th>\r\n");
				}
				else if (thcount <= dayCount)
				{

					Sb.append(" <th width='12.5%'>" + thcount + "</th>\r\n");
					thcount++;
				}
				else
				{
					Sb.append(" <th width='12.5%'></th>\r\n");
					thcount++;
				}

			}
			Sb.append(" </tr> </thead><tbody><tr>  \r\n");

			// 第2行
			for (int i = 0; i < 8; i++)
			{
				if (i == 0)
				{
					Sb.append(" <td>上班 </td> \r\n");
				}
				else if (tducount <= dayCount)
				{

					Sb.append("<td><LateWT" + tducount + "/></th> \r\n");
					tducount++;
				}
				else
				{
					Sb.append("<td></th> \r\n");
					tducount++;
				}

			}
			Sb.append(" </tr><tr> \r\n");
			// 第3行

			for (int i = 0; i < 8; i++)
			{
				if (i == 0)
				{
					Sb.append(" <td>下班 </td>\r\n");
				}
				else if (tddcount <= dayCount)
				{

					Sb.append("<td><EarlyWT" + tddcount + "/></th> \r\n");
					tddcount++;
				}
				else
				{
					Sb.append("<td></th>\r\n");
					tddcount++;
				}

			}
			Sb.append(" </tr> </tbody></table> <table class=\"table\"><thead>  <tr>\r\n");
		}
		System.out.println("dayCount:" + dayCount);

		cv.setCalendarHtml(Sb.toString());
		cv.setEndDay(dayCount);
		return cv;
	}
	/**
	 * 符合超時加班人員名單查詢用
	 * @param sql
	 * @param htmlButton
	 * @param con
	 * @param out
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public static String drawTableCondition(String sql, String htmlButton, Connection con, PrintWriter out, String page) throws SQLException
	{
		String totalTime = "", resultS = "", Status = "";
		String css = "table table-striped table-bordered table-hover";

		String bStatus = "0";

		String msg = "";

		if (totalTime.equals(""))
		{
			htmlButton = "";
		}
		if (!page.equals("save"))
		{
			bStatus = "1";
		}

		WebDBTableEx table = new WebDBTableEx(con, sql);

		return table.getHTMLTableCondition(out, "無資料", css, htmlButton, msg, bStatus);
		// System.out.println("table :"+table.toString());
	}
}
