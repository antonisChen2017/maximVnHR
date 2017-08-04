package cn.com.maxim.portal.util;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.maxim.htmlDBcontrol.WebDBControl;
import cn.com.maxim.htmlDBcontrol.WebDBForm;
import cn.com.maxim.htmlDBcontrol.WebDBSelect;
import cn.com.maxim.htmlcontrol.WebHidden;
import cn.com.maxim.htmlcontrol.WebLabel;
import cn.com.maxim.htmlcontrol.WebSelect;
import cn.com.maxim.portal.attendan.ro.dayAttendanceRO;
import cn.com.maxim.portal.attendan.ro.empLateEarlyRO;
import cn.com.maxim.portal.attendan.ro.empYearChange;
import cn.com.maxim.portal.attendan.ro.repAttendanceDayRO;
import cn.com.maxim.portal.attendan.vo.calendarVO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.bean.dayTableMoble;

import cn.com.maxim.portal.hr.ad_editLholiday;
import cn.com.maxim.portal.hr.ts_delUserWriteData;
import cn.com.maxim.potral.consts.UrlUtil;
import cn.com.maxim.potral.consts.sqlConsts;

public class ControlUtil
{
	static WebDBSelect APSelector;
	static WebLabel APlLbel;
	static WebDBControl APlDBControl;
	static WebHidden APHidden;
	static WebDBForm Form;
	static WebSelect  APCustomSelect;

	/**
	 * 下拉選單
	 * @param con
	 * @param out
	 * @param name
	 * @param tableName
	 * @param valueField
	 * @param DisplayField
	 * @param whereSql
	 * @param SelectedOption
	 * @return
	 * @throws SQLException
	 */
	public static String drawSelectDBControl(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String whereSql, String SelectedOption) throws SQLException
	{

		WebSelect ws = APlDBControl.buildWebSelectFromDB(con, tableName, valueField, DisplayField,
				whereSql, null, null, false);
		ws.setID(name);
		ws.setName(name);
		ws.setClass("select2_category form-control");
		
		ws.addOption("0", "未選擇");
		ws.setSelectedOption(SelectedOption);
		return ws.toString();
	}
	
	public static String drawHidden(String Value, String htmlID)
	{
		APHidden = new WebHidden(htmlID);
		APHidden.setValue(Value);
		APHidden.setID(htmlID);
		//APHidden.writeHTML(out);
		return APHidden.toString();
	}
	public static String drawSelectShared(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String Filter, String SelectedOption) throws SQLException
	{

		APSelector = new WebDBSelect(name, tableName, valueField, DisplayField, Filter);
		APSelector.setClass("select2_category form-control");
		APSelector.setID(name);
		APSelector.refreshOptions(con);
		APSelector.addOption("0", "未選擇");
		//System.out.println("drawSelectShared SelectedOption :"+SelectedOption);
		APSelector.setSelectedOption(SelectedOption);
		//APSelector.writeHTML(out);
		return APSelector.toString();
	}
	public static String drawSelectShared(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String Filter, String SelectedOption,boolean flag) throws SQLException
	{

		APSelector = new WebDBSelect(name, tableName, valueField, DisplayField, Filter);
		APSelector.setClass("select2_category form-control");
		APSelector.setID(name);
		APSelector.refreshOptions(con);
		if(flag){
			APSelector.addOption("0", "未選擇");
		}
		APSelector.setSelectedOption(SelectedOption);
		return APSelector.toString();
	}
	
	
	public static String drawTranslateSelectShared( )
	{
		ArrayList al =new ArrayList();
		Hashtable ht=new Hashtable();
		ht.put("中文", "zh-CN");
		al.add(ht);
		Hashtable ht1=new Hashtable();
		ht1.put("英文", "en");
		al.add(ht1);
		
		return drawCustomSelectShared("Translate",al,"0");
	}
	
	
	public static String drawOLSelect(String  SelectedOption)
	{
		ArrayList al =new ArrayList();
		Hashtable htz=new Hashtable();
		htz.put("text", "未选择");
		htz.put("value", "0");
		al.add(htz);
		Hashtable ht=new Hashtable();
		ht.put("text", "请假单");
		ht.put("value", "1");
		al.add(ht);
		Hashtable ht1=new Hashtable();
		ht1.put("text", "加班单");
		ht1.put("value", "2");
		al.add(ht1);
		
		return drawCustomSelectShared("drawOLSelect",al,SelectedOption);
	}
	
	public static String drawReportSelect(String  SelectedOption)
	{
		ArrayList al =new ArrayList();
		Hashtable htz=new Hashtable();
		htz.put("text", "未选择");
		htz.put("value", "0");
		al.add(htz);
		Hashtable ht=new Hashtable();
		ht.put("text", "月份考勤明細表");
		ht.put("value", "1");
		al.add(ht);
		Hashtable ht1=new Hashtable();
		ht1.put("text", "月份考勤總表");
		ht1.put("value", "2");
		al.add(ht1);
		
		return drawCustomSelectShared("drawReportSelect",al,SelectedOption);
	}
	
	
	public static String drawCustomSelectShared( String Name,ArrayList Options, String SelectedOption)
	{
	        Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(ControlUtil.class);
		APCustomSelect = new WebSelect();
		APCustomSelect.setClass("populate select2_category form-control");
		APCustomSelect.setID(Name);
		APCustomSelect.setName(Name);
		
		//logger.info("Options : "+Options);
		for(int i=0;i<Options.size();i++){
			Hashtable ht=(Hashtable)Options.get(i);
		//	logger.info("value : "+(String)ht.get("value"));
		//	logger.info("text : "+(String)ht.get("text"));
			APCustomSelect.addOption((String)ht.get("value"),(String) ht.get("text"));
		}
		
		
		
		//System.out.println("drawCustomSelectShared SelectedOption :"+SelectedOption);
		APCustomSelect.setSelectedOption(SelectedOption);
		   StringBuilder Sb = new StringBuilder(APCustomSelect.toString());
		    Sb.append("</select> \r\n");
			Sb.append("<script>  \r\n");
			Sb.append("	jQuery(document).ready(function() {    \r\n");
			Sb.append(" $('#"+Name+"').select2(); \r\n");
			Sb.append("     }); \r\n");
			Sb.append("</script>  \r\n");
						
		return Sb.toString();
	}
	/**
	 * 可搜尋下拉
	 * @param con
	 * @param out
	 * @param name
	 * @param tableName
	 * @param valueField
	 * @param DisplayField
	 * @param whereSql
	 * @param SelectedOption
	 * @param isDistinct
	 * @param GroupField
	 * @return
	 * @throws SQLException
	 */
	public static String drawChosenSelect(Connection con,  String name, String tableName, String valueField,
			String DisplayField, String whereSql, String SelectedOption,boolean isDistinct,String GroupField) throws SQLException
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ControlUtil.class);
		
		String sql = "SELECT " + (isDistinct ? "DISTINCT " : " ") + valueField + 
			      " , " + DisplayField + " FROM " + tableName;
			    if (whereSql != null) {
			      sql = sql + " WHERE " + whereSql;
			    }
			    if ((GroupField != null))
			    {
			    	 sql = sql + " ORDER BY " + GroupField;
			    }
			    
			    Statement st = con.createStatement();
			    ResultSet rs = st.executeQuery(sql);
			    StringBuilder Sb = new StringBuilder("");
				Sb.append("<select class=\"populate select2_category form-control\"  id='"+name+"' name='"+name+"' data-placeholder=\"\"  tabindex=\"2\"> \r\n");
	    		Sb.append("<option value='0'>未選擇</option> \r\n");
			    int count=0;
			    while (rs.next()) {
			    	if(count==0){
			    	//	Sb.append("<select class=\"populate select2_category form-control\"  id='"+name+"' name='"+name+"' data-placeholder=\""+rs.getString(2)+"\"  tabindex=\"2\"> \r\n");
			    		//  Sb.append("<option value='0'>未選擇</option> \r\n");
			    	}
			
			    	  if(rs.getString(1).equals(SelectedOption)){
			    		    Sb.append("<option value='"+rs.getString(1)+"'  selected>"+rs.getString(2)+"</option> \r\n");
			    	  }else{
			    		    Sb.append("<option value='"+rs.getString(1)+"'>"+rs.getString(2)+"</option> \r\n");
			    	  }
			    	  count=count+1;
			      }
			  
			    Sb.append("</select> \r\n");
				Sb.append("<script>  \r\n");
				Sb.append("	jQuery(document).ready(function() {    \r\n");
				Sb.append(" $('#"+name+"').select2(); \r\n");
				Sb.append("     }); \r\n");
				Sb.append("</script>  \r\n");
			   

		return Sb.toString();
	}
	
	
	/**
	 * 可搜尋下拉-強化版(下拉顯示部門單位)
	 * @param con
	 * @param out
	 * @param name
	 * @param tableName
	 * @param valueField
	 * @param DisplayField
	 * @param whereSql
	 * @param SelectedOption
	 * @param isDistinct
	 * @param GroupField
	 * @return
	 * @throws SQLException
	 */
	public static String drawChosenSql(Connection con,  String name, String sql, String SelectedOption,String valueZ) throws SQLException
	{

		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ControlUtil.class);
		
	
			    Statement st = con.createStatement();
			    ResultSet rs = st.executeQuery(sql);
			    StringBuilder Sb = new StringBuilder("");
				Sb.append("<select class=\"populate select2_category form-control\"  id='"+name+"' name='"+name+"' data-placeholder=\"\"  tabindex=\"2\"> \r\n");
	    		Sb.append("<option value='0'>"+valueZ+"</option> \r\n");
			    int count=0;
			    while (rs.next()) {
			    	if(count==0){
			    	//	Sb.append("<select class=\"populate select2_category form-control\"  id='"+name+"' name='"+name+"' data-placeholder=\""+rs.getString(2)+"\"  tabindex=\"2\"> \r\n");
			    		//  Sb.append("<option value='0'>未選擇</option> \r\n");
			    	}
			  	  if(rs.getString(1).equals(SelectedOption)){
		    		    Sb.append("<option value='"+rs.getString(1)+"'  selected>"+rs.getString(2)+"</option> \r\n");
		    	  }else{
		    		    Sb.append("<option value='"+rs.getString(1)+"'>"+rs.getString(2)+"</option> \r\n");
		    	  }
			    	  count=count+1;
			      }
			
			    Sb.append("</select> \r\n");
				Sb.append("<script>  \r\n");
				Sb.append("	jQuery(document).ready(function() {    \r\n");
				Sb.append(" $('#"+name+"').select2(); \r\n");
				Sb.append("     }); \r\n");
				Sb.append("</script>  \r\n");
		

		return Sb.toString();
	}
	
	/**
	 * 手風琴式介面
	 * @param Name
	 * @param Options
	 * @param SelectedOption
	 * @return
	 * @throws Exception 
	 */
	public static String drawAccordions(Connection con,lateOutEarlyVO eaVo) throws Exception
	{	
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ControlUtil.class);
		String accordions ="";
		empLateEarlyRO newEmpLateEarlyRO=new empLateEarlyRO();
		eaVo.setQueryIsLate("1");//遲到
		List<empLateEarlyRO> leoLate=DBUtil.queryEmpLateEarlyList(con,SqlUtil.getEmplateOutEarly(con, eaVo), newEmpLateEarlyRO);
		//logger.info("late  sql "+SqlUtil.getEmplateOutEarly(con, eaVo));
		eaVo.setQueryIsLate("2");//早退
		List<empLateEarlyRO> leoEarly=DBUtil.queryEmpLateEarlyList(con,SqlUtil.getEmplateOutEarly(con, eaVo), newEmpLateEarlyRO);
	//	logger.info("Early  sql "+SqlUtil.getEmplateOutEarly(con, eaVo));
		
		/**調整早上或下午未打卡曠職**/
		int LateCount=0;
		int LateHour=0;
		int LateMinute=0;
		int EarlyCount=0;
		int EarlyHour=0;
		int EarlyMinute=0;
		int NotWork=0;
		int LeaveCount=0;
		String YMD="";
		int monthCount=DateUtil.getDaysOfStrMonth(eaVo.getQueryYearMonth());
		   logger.info("計算 天數 "+monthCount);
		for(int  i=1;i<monthCount;i++){
		    /**跳出禮拜天START**/
		    if(i<9){
			    YMD=eaVo.getQueryYearMonth()+"/0"+i;
			  //  logger.info(" drawAccordions YMD : "+YMD);
			    //logger.info(" drawAccordions Weekday : "+DateUtil.getWeekday(YMD));
			    if(DateUtil.getWeekday(YMD)==1){
				continue;
			    }
		    }else{
			    YMD=eaVo.getQueryYearMonth()+"/"+i;
			 //   logger.info(" drawAccordions YMD : "+YMD);
			  //  logger.info("drawAccordions  Weekday : "+DateUtil.getWeekday(YMD));
			    if(DateUtil.getWeekday(YMD)==1){
				continue;
			    }
		    }
		    /**跳出禮拜天END**/  
		
			//遲到
			String LateWT =leoLate.get(0).getWT(i);
			String EarlyWT =leoEarly.get(0).getWT(i);
		
			//下午為打卡 曠職一次 跳出
			if ((LateWT.equals("00:00") & (! EarlyWT.equals("00:00"))) ){
			    logger.info("下午沒打卡 曠職一次 檢查有無請假"+SqlUtil.queryLeaveToDate(YMD,eaVo.getEmpID()));
			    	String ID=DBUtil.queryDBField(con, SqlUtil.queryLeaveToDate(YMD,eaVo.getEmpID()), "ID");
			    	if(ID.equals("")){
			    	    NotWork=NotWork+1;
			    	}else{
			    	    LeaveCount=LeaveCount+1;
			    	}
				continue;
			}
			
			//早上沒打卡曠職一次跳出
			if (EarlyWT.equals("00:00") &(! LateWT.equals("00:00"))  ){
			    logger.info("早上沒打卡 曠職一次 檢查有無請假"+SqlUtil.queryLeaveToDate(YMD,eaVo.getEmpID()));
			    	String ID=DBUtil.queryDBField(con, SqlUtil.queryLeaveToDate(YMD,eaVo.getEmpID()), "ID");
			    	if(ID.equals("")){
			    	    NotWork=NotWork+1;
			    	}else{
			    	    LeaveCount=LeaveCount+1;
			    	}
				continue;
			}
			//全部沒打卡曠職一次跳出
			if ((LateWT.equals("00:00") & ( EarlyWT.equals("00:00"))) ){
			    logger.info("全天沒打卡 曠職一次 檢查有無請假"+SqlUtil.queryLeaveToDate(YMD,eaVo.getEmpID()));

			    	String ID=DBUtil.queryDBField(con, SqlUtil.queryLeaveToDate(YMD,eaVo.getEmpID()), "ID");
			    	   logger.info("全天沒打卡  ID"+ ID);
			    	if(ID.equals("")){
			    	    NotWork=NotWork+1;
			    	}else{
			    	    LeaveCount=LeaveCount+1;
			    	}
				continue;
			}
			//其他天檢查遲到早退
			if ((! LateWT.equals("00:00") & ( ! EarlyWT.equals("00:00"))) ){
			    int dayLateMinute=Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.queryLateMinute(YMD,eaVo.getEmpID()), "LateMinute"));
			  
			    if(dayLateMinute>0){
				  logger.info("計算 "+YMD+"遲到分 : "+dayLateMinute);
				LateMinute=LateMinute+dayLateMinute;
				LateCount++;
			    }
			    int dayEarlyMinute=Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.queryEarlyMinute(YMD,eaVo.getEmpID()), "EarlyMinute"));
			  
			    if(dayEarlyMinute<0){
				  logger.info("計算 "+YMD+"早退分 : "+(dayEarlyMinute*-1));
				EarlyMinute=EarlyMinute+(dayEarlyMinute*-1);
				EarlyCount++;
			    }
			   continue;
			}
		}
		LateHour=LateMinute/60;
		LateMinute=LateMinute%60;
		logger.info("計算 遲到次數 : "+LateCount);
		logger.info("計算 遲到小時 : "+LateHour);
		logger.info("計算 遲到分 : "+LateMinute);
		EarlyHour=EarlyMinute/60;
		EarlyMinute=EarlyMinute%60;
		logger.info("計算 早退次數 : "+EarlyCount);
		logger.info("計算 早退小時 : "+EarlyHour);
		logger.info("計算 早退分 : "+EarlyMinute);
		logger.info("計算 曠職: "+NotWork);
		logger.info("計算 請假: "+LeaveCount);
		if(LateCount<0){
		    LateCount=0;
		}
		if(LateHour<0){
			LateHour=0;
		}
		if(EarlyCount<0){
		    EarlyCount=0;
		}
		if(EarlyHour<0){
			EarlyHour=0;
		}
		
	
		
		
		
		HtmlUtil hu=new HtmlUtil();
		if(leoLate.size()==0 && leoEarly.size()==0){
			accordions =HtmlUtil.getMsgDiv("本月無打卡資料");
		}else{
			accordions =hu.gethtml(UrlUtil.control_accordions);
			accordions=accordions.replace("<year/>", eaVo.getQueryYearMonth().split("/")[0]);
			accordions=accordions.replace("<Month/>", eaVo.getQueryYearMonth().split("/")[1]);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String strDate = eaVo.getQueryYearMonth()+"/01";
			Date date=null;
			try
			{
				date = sdf.parse(strDate);
			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			calendarVO cv=HtmlUtil.getCalendar(date) ;
			accordions=accordions.replace(" <Calendar/>",cv.getCalendarHtml() );
			
			
			accordions=accordions.replace("<LeaveCount/>", String.valueOf(LeaveCount));
			//遲到
			if(leoLate.size()>0){
				accordions=accordions.replace("<NotWork/>", String.valueOf(NotWork));
				accordions=accordions.replace("<LateTimes/>", String.valueOf(LateCount));
				accordions=accordions.replace("<LateHour/>", String.valueOf(LateHour));
				accordions=accordions.replace("<LateMinute/>", String.valueOf(LateMinute));
				accordions=accordions.replace("<LateWT1/>", leoLate.get(0).getWT1());
				accordions=accordions.replace("<LateWT2/>", leoLate.get(0).getWT2());
				accordions=accordions.replace("<LateWT3/>", leoLate.get(0).getWT3());
				accordions=accordions.replace("<LateWT4/>", leoLate.get(0).getWT4());
				accordions=accordions.replace("<LateWT5/>", leoLate.get(0).getWT5());
				accordions=accordions.replace("<LateWT6/>", leoLate.get(0).getWT6());
				accordions=accordions.replace("<LateWT7/>", leoLate.get(0).getWT7());
				accordions=accordions.replace("<LateWT8/>", leoLate.get(0).getWT8());
				accordions=accordions.replace("<LateWT9/>", leoLate.get(0).getWT9());
				accordions=accordions.replace("<LateWT10/>", leoLate.get(0).getWT10());
				accordions=accordions.replace("<LateWT11/>", leoLate.get(0).getWT11());
				accordions=accordions.replace("<LateWT12/>", leoLate.get(0).getWT12());
				accordions=accordions.replace("<LateWT13/>", leoLate.get(0).getWT13());
				accordions=accordions.replace("<LateWT14/>", leoLate.get(0).getWT14());
				accordions=accordions.replace("<LateWT15/>", leoLate.get(0).getWT15());
				accordions=accordions.replace("<LateWT16/>", leoLate.get(0).getWT16());
				accordions=accordions.replace("<LateWT17/>", leoLate.get(0).getWT17());
				accordions=accordions.replace("<LateWT18/>", leoLate.get(0).getWT18());
				accordions=accordions.replace("<LateWT19/>", leoLate.get(0).getWT19());
				accordions=accordions.replace("<LateWT20/>", leoLate.get(0).getWT20());
				accordions=accordions.replace("<LateWT21/>", leoLate.get(0).getWT21());
				accordions=accordions.replace("<LateWT22/>", leoLate.get(0).getWT22());
				accordions=accordions.replace("<LateWT23/>", leoLate.get(0).getWT23());
				accordions=accordions.replace("<LateWT24/>", leoLate.get(0).getWT24());
				accordions=accordions.replace("<LateWT25/>", leoLate.get(0).getWT25());
				accordions=accordions.replace("<LateWT26/>", leoLate.get(0).getWT26());
				accordions=accordions.replace("<LateWT27/>", leoLate.get(0).getWT27());
				accordions=accordions.replace("<LateWT28/>", leoLate.get(0).getWT28());
				accordions=accordions.replace("<LateWT29/>", leoLate.get(0).getWT29());
				accordions=accordions.replace("<LateWT30/>", leoLate.get(0).getWT30());
				accordions=accordions.replace("<LateWT31/>", leoLate.get(0).getWT31());
			}else{
				accordions=accordions.replace("<NotWork/>", "0");
				accordions=accordions.replace("<LateTimes/>", "0");
				accordions=accordions.replace("<LateHour/>", "0");
				accordions=accordions.replace("<LateMinute/>", "0");
				accordions=accordions.replace("<LateWT1/>","");
				accordions=accordions.replace("<LateWT2/>", "");
				accordions=accordions.replace("<LateWT3/>", "");
				accordions=accordions.replace("<LateWT4/>","");
				accordions=accordions.replace("<LateWT5/>", "");
				accordions=accordions.replace("<LateWT6/>",  "");
				accordions=accordions.replace("<LateWT7/>",  "");
				accordions=accordions.replace("<LateWT8/>",  "");
				accordions=accordions.replace("<LateWT9/>",  "");
				accordions=accordions.replace("<LateWT10/>", "");
				accordions=accordions.replace("<LateWT11/>",  "");
				accordions=accordions.replace("<LateWT12/>",  "");
				accordions=accordions.replace("<LateWT13/>",  "");
				accordions=accordions.replace("<LateWT14/>",  "");
				accordions=accordions.replace("<LateWT15/>",  "");
				accordions=accordions.replace("<LateWT16/>",  "");
				accordions=accordions.replace("<LateWT17/>",  "");
				accordions=accordions.replace("<LateWT18/>",  "");
				accordions=accordions.replace("<LateWT19/>",  "");
				accordions=accordions.replace("<LateWT20/>",  "");
				accordions=accordions.replace("<LateWT21/>", "");
				accordions=accordions.replace("<LateWT22/>",  "");
				accordions=accordions.replace("<LateWT23/>", "");
				accordions=accordions.replace("<LateWT24/>",  "");
				accordions=accordions.replace("<LateWT25/>", "");
				accordions=accordions.replace("<LateWT26/>",  "");
				accordions=accordions.replace("<LateWT27/>",  "");
				accordions=accordions.replace("<LateWT28/>",  "");
				accordions=accordions.replace("<LateWT29/>", "");
				accordions=accordions.replace("<LateWT30/>",  "");
				accordions=accordions.replace("<LateWT31/>", "");
			}
			
			//早退
			if(leoEarly.size()>0){
				accordions=accordions.replace("<EarlyTimes/>", String.valueOf(EarlyCount));
				accordions=accordions.replace("<EarlyHour/>",  String.valueOf(EarlyHour));
				accordions=accordions.replace("<EarlyMinute/>", String.valueOf(EarlyMinute));
				accordions=accordions.replace("<EarlyWT1/>",leoEarly.get(0).getWT1());
				accordions=accordions.replace("<EarlyWT2/>",leoEarly.get(0).getWT2());
				accordions=accordions.replace("<EarlyWT3/>",leoEarly.get(0).getWT3());
				accordions=accordions.replace("<EarlyWT4/>",leoEarly.get(0).getWT4());
				accordions=accordions.replace("<EarlyWT5/>",leoEarly.get(0).getWT5());
				accordions=accordions.replace("<EarlyWT6/>",leoEarly.get(0).getWT6());
				accordions=accordions.replace("<EarlyWT7/>",leoEarly.get(0).getWT7());
				accordions=accordions.replace("<EarlyWT8/>",leoEarly.get(0).getWT8());
				accordions=accordions.replace("<EarlyWT9/>",leoEarly.get(0).getWT9());
				accordions=accordions.replace("<EarlyWT10/>",leoEarly.get(0).getWT10());
				accordions=accordions.replace("<EarlyWT11/>",leoEarly.get(0).getWT11());
				accordions=accordions.replace("<EarlyWT12/>",leoEarly.get(0).getWT12());
				accordions=accordions.replace("<EarlyWT13/>",leoEarly.get(0).getWT13());
				accordions=accordions.replace("<EarlyWT14/>",leoEarly.get(0).getWT14());
				accordions=accordions.replace("<EarlyWT15/>",leoEarly.get(0).getWT15());
				accordions=accordions.replace("<EarlyWT16/>",leoEarly.get(0).getWT16());
				accordions=accordions.replace("<EarlyWT17/>",leoEarly.get(0).getWT17());
				accordions=accordions.replace("<EarlyWT18/>",leoEarly.get(0).getWT18());
				accordions=accordions.replace("<EarlyWT19/>",leoEarly.get(0).getWT19());
				accordions=accordions.replace("<EarlyWT20/>",leoEarly.get(0).getWT20());
				accordions=accordions.replace("<EarlyWT21/>",leoEarly.get(0).getWT21());
				accordions=accordions.replace("<EarlyWT22/>",leoEarly.get(0).getWT22());
				accordions=accordions.replace("<EarlyWT23/>",leoEarly.get(0).getWT23());
				accordions=accordions.replace("<EarlyWT24/>",leoEarly.get(0).getWT24());
				accordions=accordions.replace("<EarlyWT25/>",leoEarly.get(0).getWT25());
				accordions=accordions.replace("<EarlyWT26/>",leoEarly.get(0).getWT26());
				accordions=accordions.replace("<EarlyWT27/>",leoEarly.get(0).getWT27());
				accordions=accordions.replace("<EarlyWT28/>",leoEarly.get(0).getWT28());
				accordions=accordions.replace("<EarlyWT29/>",leoEarly.get(0).getWT29());
				accordions=accordions.replace("<EarlyWT30/>",leoEarly.get(0).getWT30());
				accordions=accordions.replace("<EarlyWT31/>",leoEarly.get(0).getWT31());
			}else{
				accordions=accordions.replace("<EarlyTimes/>", "0");
				accordions=accordions.replace("<EarlyHour/>", "0");
				accordions=accordions.replace("<EarlyMinute/>","0");
				accordions=accordions.replace("<LateWT1/>","");
				accordions=accordions.replace("<EarlyWT2/>","");
				accordions=accordions.replace("<EarlyWT3/>","");
				accordions=accordions.replace("<EarlyWT4/>","");
				accordions=accordions.replace("<EarlyWT5/>","");
				accordions=accordions.replace("<EarlyWT6/>","");
				accordions=accordions.replace("<EarlyWT7/>","");
				accordions=accordions.replace("<EarlyWT8/>","");
				accordions=accordions.replace("<EarlyWT9/>","");
				accordions=accordions.replace("<EarlyWT10/>","");
				accordions=accordions.replace("<EarlyWT11/>","");
				accordions=accordions.replace("<EarlyWT12/>","");
				accordions=accordions.replace("<EarlyWT13/>","");
				accordions=accordions.replace("<EarlyWT14/>","");
				accordions=accordions.replace("<EarlyWT15/>","");
				accordions=accordions.replace("<EarlyWT16/>","");
				accordions=accordions.replace("<EarlyWT17/>","");
				accordions=accordions.replace("<EarlyWT18/>","");
				accordions=accordions.replace("<EarlyWT19/>","");
				accordions=accordions.replace("<EarlyWT20/>","");
				accordions=accordions.replace("<EarlyWT21/>","");
				accordions=accordions.replace("<EarlyWT22/>","");
				accordions=accordions.replace("<EarlyWT23/>","");
				accordions=accordions.replace("<EarlyWT24/>","");
				accordions=accordions.replace("<EarlyWT25/>","");
				accordions=accordions.replace("<EarlyWT26/>","");
				accordions=accordions.replace("<EarlyWT27/>","");
				accordions=accordions.replace("<EarlyWT28/>","");
				accordions=accordions.replace("<EarlyWT29/>","");
				accordions=accordions.replace("<EarlyWT30/>","");
				accordions=accordions.replace("<EarlyWT31/>","");
			}
		}
		return accordions;
	}
	
	
	/**
	 * 全廠日報表
	 * @param Name
	 * @param Options
	 * @param SelectedOption
	 * @return
	 * @throws Exception 
	 */
	public static String drawAttendanceDayTable(Connection con,leaveCardVO lcVo) throws Exception
	{
	    	Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ControlUtil.class);
		String Control="";
		HtmlUtil hu=new HtmlUtil();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
		Control =hu.gethtml(UrlUtil.control_attendanceDayTable);
		Control=Control.replace("<toDay/>",lcVo.getApplicationDate().replaceAll("/", ""));
		Date date = sdf.parse( lcVo.getApplicationDate().replaceAll("/", "") );
		date=DateUtil.addDays(date, -1);
		Control=Control.replace("<yesterDay/>",sdf.format(date));
		String Yday=DateUtil.addDay(lcVo.getApplicationDate(),-1);
		
		
		dayTableMoble dt=new dayTableMoble();
		logger.info("queryPlantData  SQL"+SqlUtil.queryPlantData(lcVo.getApplicationDate(),Yday));
		//跑出當天資料
		DBUtil.updateSql(SqlUtil.queryPlantData(lcVo.getApplicationDate(),Yday),con);
		//查出整體資料
		dayAttendanceRO daRo = new dayAttendanceRO();
		//logger.info("getAttendance "+SqlUtil.getAttendance(lcVo.getApplicationDate()));
		List<dayAttendanceRO> daRolist = (List<dayAttendanceRO>) DBUtil.
			queryExcelAttendanceDay(con, SqlUtil.getAttendance(lcVo.getApplicationDate()), daRo);
		String tempROW="",tempDept="",tempID="";
		int tempRow=0;
		dayAttendanceRO rowR = new dayAttendanceRO();
		dayAttendanceRO tmpR = new dayAttendanceRO();
		dayAttendanceRO maxR = new dayAttendanceRO();
		tmpR=setAttendanceRO(tmpR);
		maxR=setAttendanceRO(maxR);
		 logger.info("==================================================");
		for(int i =0;i<daRolist.size();i++){
		    	rowR=daRolist.get(i);
			/**一般行**/
		    	 // logger.info("UNIT   "+rowR.getUNIT());
		    	 // logger.info("DEPARTMENT   "+rowR.getDEPARTMENT() );
		    	  
		    	if(! rowR.getUNIT().equals("合计") && !rowR.getDEPARTMENT().equals("合计")){
		    	 
		    	    tmpR=tmpAttendanceRO(tmpR,rowR);
		    	    maxR=maxAttendanceRO(maxR,rowR);
		    	    if(tempRow==0){
		    		tempID=rowR.getID();
		    	    }
		    	// logger.info("一般行  rowR.getUNIT() "+rowR.getUNIT()+" tempRow "+tempRow+" tempID "+tempID);
		    	tempRow=tempRow+1;
		    	}
		    	/**部門合計行**/
		    	if(rowR.getUNIT().equals("合计") && !rowR.getDEPARTMENT().equals("合计")){
		    	    tmpR.setID(rowR.getID());
		    	 //   logger.info("部門合計行 updateAttendanceCmax "+SqlUtil.updateAttendanceCmax(tmpR));
		    	    DBUtil.updateSql(SqlUtil.updateAttendanceCmax(tmpR), con);
		    	    tmpR=setAttendanceRO(tmpR);
		    	    tmpR.setID(tempID);
		    	    tmpR.setROW(String.valueOf(Integer.valueOf(rowR.getROW())+1));
		    	    DBUtil.updateSql(SqlUtil.updateAttendanceRow(tmpR), con);
		    	    tmpR.setID(rowR.getID());
		    	    tmpR.setROW("1");
		    	    DBUtil.updateSql(SqlUtil.updateAttendanceRow(tmpR), con);
		    	    tempRow=0;
		   	   // logger.info("部門合計行  rowR.getUNIT() "+rowR.getUNIT()+" tempRow "+tempRow+" tempID "+tempID);
		    	}
		    	/**最後合計行**/
		    	if(rowR.getUNIT().equals("合计") && rowR.getDEPARTMENT().equals("合计")){
		    	    maxR.setID(rowR.getID());
		   	//    logger.info("合計 合計行 updateAttendanceCmax "+SqlUtil.updateAttendanceCmax(maxR));
		    	    DBUtil.updateSql(SqlUtil.updateAttendanceCmax(maxR), con);
		    	    tempRow=0;
		    	    maxR=setAttendanceRO(maxR);
		    	}
		    	/**每行更新離職率**/
		    	rowR.setC20(NumberUtil.getPercentFormat(Integer.valueOf(rowR.getC3()) ,Integer.valueOf(rowR.getC19())));
		    	DBUtil.updateSql(SqlUtil.updateAttendanceC20(rowR), con);
		}
		 logger.info("==================================================");
		//使用資料跑出頁面
		 daRolist = (List<dayAttendanceRO>) DBUtil.
				queryExcelAttendanceDay(con, SqlUtil.getAttendanceTable(lcVo.getApplicationDate()), daRo);
		 Control=Control.replace("<tbody/>", HtmlUtil.drawTbody(daRolist));
		 
		 
		//年資分布
		/**1年以下Data**/
		 
		 empYearChange ey= DateUtil.getThreeYears(lcVo.getApplicationDate());
		 logger.info("1年以下Data "+SqlUtil.getVnLessYear(ey,sqlConsts.sql_vnLessThanOneYear));
		dt.setYco1(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(ey,sqlConsts.sql_vnLessThanOneYear), "yco")));
		/**1年~2年Data**/
		 logger.info("1年~2年Data "+SqlUtil.getTowYear(ey,sqlConsts.sql_vnOneToTwoYears));
		dt.setYco2(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getTowYear(ey,sqlConsts.sql_vnOneToTwoYears), "yco")));
		/**2年~3年Data**/
		 logger.info("2年~3年Data "+SqlUtil.getTwoToThree(ey,sqlConsts.sql_vnTwoToThreeYears));
		dt.setYco3(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getTwoToThree(ey,sqlConsts.sql_vnTwoToThreeYears), "yco")));
		/**3年以上Data**/
		 logger.info("3年以上Data "+SqlUtil.getThreeYearsOver(ey,sqlConsts.sql_getThreeYearsOver));
		dt.setYco4(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getThreeYearsOver(ey,sqlConsts.sql_getThreeYearsOver), "yco")));
		
		/** 越籍年資分布**/
		 Control= Control.replace("<yco1/>",String.valueOf( dt.getYco1()));
		 Control= Control.replace("<yco2/>",String.valueOf( dt.getYco2()));
		 Control= Control.replace("<yco3/>",String.valueOf( dt.getYco3()));
		 Control= Control.replace("<yco4/>",String.valueOf( dt.getYco4()));
		 Control= Control.replace("<yco5/>",NumberUtil.getPercentFormat(dt.getYco1(),dt.getYmax()));
		 Control= Control.replace("<yco6/>",NumberUtil.getPercentFormat(dt.getYco2(),dt.getYmax()));
		 Control= Control.replace("<yco7/>",NumberUtil.getPercentFormat(dt.getYco3(),dt.getYmax()));
		 Control= Control.replace("<yco8/>",NumberUtil.getPercentFormat(dt.getYco4(),dt.getYmax()));
		 Control= Control.replace("<yco9/>",String.valueOf( dt.getYmax()));
		 DBUtil.saveEmpnum(con,dt,lcVo);
			
		return Control;
	}
	
	/**
	 * 左右移動表格
	 * @param Name
	 * @param Options
	 * @param SelectedOption
	 * @return
	 * @throws ParseException 
	 */
	public static String swTable(Connection con,leaveCardVO lcVo)
	{
		String Control="";
		HtmlUtil hu=new HtmlUtil();
		Control =hu.gethtml(UrlUtil.control_swTable);
		return Control;
	}
	
	/**
	 * 共用名稱工號下拉選單
	 * @param con
	 * @param html
	 * @return
	 * @throws SQLException 
	 */
	public static String sharedLabel(Connection con,String html,leaveCardVO lcVo) throws SQLException
	{
		html=html.replace("<SearchEmployeeNo/>",ControlUtil.drawChosenSelect(con, "searchEmployeeNo", "HR_EMPLOYEE", "ID", "EMPLOYEENO", "DEPARTMENT_ID='" + 	lcVo.getSearchDepartmen()+ "'", lcVo.getSearchEmployeeNo(),false,null));
		html=html.replace("<SearchEmployee/>",ControlUtil.drawChosenSelect(con,  "searchEmployee", "HR_EMPLOYEE", "ID", "EMPLOYEE", "DEPARTMENT_ID='" + lcVo.getSearchDepartmen() + "'", lcVo.getSearchEmployee(),false,null));
		return html;
	}
	/**
	 * 預先設定資料
	 * @param con
	 * @param html
	 * @return
	 * @throws SQLException 
	 */
	public static dayAttendanceRO setAttendanceRO(dayAttendanceRO rowR) throws SQLException
	{
	    	rowR.setC1("0");
	    	rowR.setC2("0");
	    	rowR.setC3("0");
	    	rowR.setC4("0");
	    	rowR.setC5("0");
	    	rowR.setC6("0");
	    	rowR.setC7("0");
	    	rowR.setC8("0");
	    	rowR.setC9("0");
	    	rowR.setC10("0");
	    	rowR.setC11("0");
	    	rowR.setC12("0");
	    	rowR.setC13("0");
	    	rowR.setC14("0");
	    	rowR.setC15("0");
	    	rowR.setC16("0");
	    	rowR.setC17("0");
	    	rowR.setC18("0");
	    	rowR.setC19("0");
	    	//rowR.setC20("0");
		return rowR;
	}
	/**
	 * 非合計行資料計算
	 * @param con
	 * @param html
	 * @return
	 * @throws SQLException 
	 */
	public static dayAttendanceRO tmpAttendanceRO(dayAttendanceRO tmpR,dayAttendanceRO rowR) throws SQLException
	{
	    	tmpR.setC1(String.valueOf(Integer.valueOf(tmpR.getC1())+Integer.valueOf(rowR.getC1())));
		tmpR.setC2(String.valueOf(Integer.valueOf(tmpR.getC2())+Integer.valueOf(rowR.getC2())));
		tmpR.setC3(String.valueOf(Integer.valueOf(tmpR.getC3())+Integer.valueOf(rowR.getC3())));
		tmpR.setC4(String.valueOf(Integer.valueOf(tmpR.getC4())+Integer.valueOf(rowR.getC4())));
		tmpR.setC5(String.valueOf(Integer.valueOf(tmpR.getC5())+Integer.valueOf(rowR.getC5())));
		tmpR.setC6(String.valueOf(Integer.valueOf(tmpR.getC6())+Integer.valueOf(rowR.getC6())));
		tmpR.setC7(String.valueOf(Integer.valueOf(tmpR.getC7())+Integer.valueOf(rowR.getC7())));
		tmpR.setC8(String.valueOf(Integer.valueOf(tmpR.getC8())+Integer.valueOf(rowR.getC8())));
		tmpR.setC9(String.valueOf(Integer.valueOf(tmpR.getC9())+Integer.valueOf(rowR.getC9())));
		tmpR.setC10(String.valueOf(Integer.valueOf(tmpR.getC10())+Integer.valueOf(rowR.getC10())));
		tmpR.setC11(String.valueOf(Integer.valueOf(tmpR.getC11())+Integer.valueOf(rowR.getC11())));
		tmpR.setC12(String.valueOf(Integer.valueOf(tmpR.getC12())+Integer.valueOf(rowR.getC12())));
		tmpR.setC13(String.valueOf(Integer.valueOf(tmpR.getC13())+Integer.valueOf(rowR.getC13())));
		tmpR.setC14(String.valueOf(Integer.valueOf(tmpR.getC14())+Integer.valueOf(rowR.getC14())));
		tmpR.setC15(String.valueOf(Integer.valueOf(tmpR.getC15())+Integer.valueOf(rowR.getC15())));
		tmpR.setC16(String.valueOf(Integer.valueOf(tmpR.getC16())+Integer.valueOf(rowR.getC16())));
		tmpR.setC17(String.valueOf(Integer.valueOf(tmpR.getC17())+Integer.valueOf(rowR.getC17())));
		tmpR.setC18(String.valueOf(Integer.valueOf(tmpR.getC18())+Integer.valueOf(rowR.getC18())));
		tmpR.setC19(String.valueOf(Integer.valueOf(tmpR.getC19())+Integer.valueOf(rowR.getC19())));

	
		return tmpR;
	}
	
	/**
	 * 非合計行資料計算-總合計
	 * @param con
	 * @param html
	 * @return
	 * @throws SQLException 
	 */
	public static dayAttendanceRO maxAttendanceRO(dayAttendanceRO maxR,dayAttendanceRO rowR) throws SQLException
	{
	   

		maxR.setC1(String.valueOf(Integer.valueOf(maxR.getC1())+Integer.valueOf(rowR.getC1())));
		maxR.setC2(String.valueOf(Integer.valueOf(maxR.getC2())+Integer.valueOf(rowR.getC2())));
		maxR.setC3(String.valueOf(Integer.valueOf(maxR.getC3())+Integer.valueOf(rowR.getC3())));
		maxR.setC4(String.valueOf(Integer.valueOf(maxR.getC4())+Integer.valueOf(rowR.getC4())));
		maxR.setC5(String.valueOf(Integer.valueOf(maxR.getC5())+Integer.valueOf(rowR.getC5())));
		maxR.setC6(String.valueOf(Integer.valueOf(maxR.getC6())+Integer.valueOf(rowR.getC6())));
		maxR.setC7(String.valueOf(Integer.valueOf(maxR.getC7())+Integer.valueOf(rowR.getC7())));
		maxR.setC8(String.valueOf(Integer.valueOf(maxR.getC8())+Integer.valueOf(rowR.getC8())));
		maxR.setC9(String.valueOf(Integer.valueOf(maxR.getC9())+Integer.valueOf(rowR.getC9())));
		maxR.setC10(String.valueOf(Integer.valueOf(maxR.getC10())+Integer.valueOf(rowR.getC10())));
		maxR.setC11(String.valueOf(Integer.valueOf(maxR.getC11())+Integer.valueOf(rowR.getC11())));
		maxR.setC12(String.valueOf(Integer.valueOf(maxR.getC12())+Integer.valueOf(rowR.getC12())));
		maxR.setC13(String.valueOf(Integer.valueOf(maxR.getC13())+Integer.valueOf(rowR.getC13())));
		maxR.setC14(String.valueOf(Integer.valueOf(maxR.getC14())+Integer.valueOf(rowR.getC14())));
		maxR.setC15(String.valueOf(Integer.valueOf(maxR.getC15())+Integer.valueOf(rowR.getC15())));
		maxR.setC16(String.valueOf(Integer.valueOf(maxR.getC16())+Integer.valueOf(rowR.getC16())));
		maxR.setC17(String.valueOf(Integer.valueOf(maxR.getC17())+Integer.valueOf(rowR.getC17())));
		maxR.setC18(String.valueOf(Integer.valueOf(maxR.getC18())+Integer.valueOf(rowR.getC18())));
		maxR.setC19(String.valueOf(Integer.valueOf(maxR.getC19())+Integer.valueOf(rowR.getC19())));
		
		return maxR;
	}
}
