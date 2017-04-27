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

import cn.com.maxim.DB.DBUtils;
import cn.com.maxim.htmlDBcontrol.WebDBControl;
import cn.com.maxim.htmlDBcontrol.WebDBForm;
import cn.com.maxim.htmlDBcontrol.WebDBSelect;
import cn.com.maxim.htmlcontrol.WebHidden;
import cn.com.maxim.htmlcontrol.WebLabel;
import cn.com.maxim.htmlcontrol.WebSelect;
import cn.com.maxim.portal.attendan.ro.empLateEarlyRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceDayRO;
import cn.com.maxim.portal.attendan.vo.calendarVO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.bean.dayTableMoble;
import cn.com.maxim.portal.bean.dayTableRowMoble;
import cn.com.maxim.portal.bean.repAttendanceDayBean;
import cn.com.maxim.portal.key.KeyUtil;
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
	//	System.out.println("drawSelectDBControl SelectedOption :"+SelectedOption);
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
	public static String drawCustomSelectShared( String Name,ArrayList Options, String SelectedOption)
	{

		APCustomSelect = new WebSelect();
		APCustomSelect.setClass("select2_category form-control");
		APCustomSelect.setID(Name);
		APCustomSelect.setName(Name);
		
		for(int i=0;i<Options.size();i++){
			Hashtable ht=(Hashtable)Options.get(i);
			APCustomSelect.addOption((String)ht.get("value"),(String) ht.get("text"));
		}
		
		APCustomSelect.addOption("0", "未選擇");
		
		//System.out.println("drawCustomSelectShared SelectedOption :"+SelectedOption);
		APCustomSelect.setSelectedOption(SelectedOption);
	
		return APCustomSelect.toString();
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

		
		String sql = "SELECT " + (isDistinct ? "DISTINCT " : " ") + valueField + 
			      " , " + DisplayField + " FROM " + tableName;
			    if (whereSql != null) {
			      sql = sql + " WHERE " + whereSql;
			    }
			    if ((GroupField != null))
			    {
			    	 sql = sql + " ORDER BY " + GroupField;
			    }
			    System.out.println("drawChosenSelect sql : "+sql);
			    Statement st = con.createStatement();
			    ResultSet rs = st.executeQuery(sql);
			    StringBuilder Sb = new StringBuilder("");
			
			    int count=0;
			    while (rs.next()) {
			    	if(count==0){
			    		Sb.append("<select class=\"populate select2_category form-control\"  id='"+name+"' name='"+name+"' data-placeholder=\""+rs.getString(2)+"\"  tabindex=\"2\"> \r\n");
			    		  Sb.append("<option value='0'>未選擇</option> \r\n");
			    	}
			    	  if(rs.getString(1).equals(SelectedOption)){
			    		    Sb.append("<option value='"+rs.getString(1)+"'  selected>"+rs.getString(2)+"</option> \r\n");
			    	  }else{
			    		    Sb.append("<option value='"+rs.getString(1)+"'>"+rs.getString(2)+"</option> \r\n");
			    	  }
			    	  count=count+1;
			      }
			  
			    Sb.append("</select> \r\n");
				Sb.append("<script>");
				Sb.append("	jQuery(document).ready(function() {    "); 
				Sb.append(" $('#"+name+"').select2(); "); 
				Sb.append("     }); "); 
				Sb.append("</script>  ");
			   

		return Sb.toString();
	}
	
	/**
	 * 手風琴式介面
	 * @param Name
	 * @param Options
	 * @param SelectedOption
	 * @return
	 */
	public static String drawAccordions(Connection con,lateOutEarlyVO eaVo)
	{
		String accordions ="";
		empLateEarlyRO newEmpLateEarlyRO=new empLateEarlyRO();
		eaVo.setQueryIsLate("1");//遲到
		List<empLateEarlyRO> leoLate=DBUtil.queryEmpLateEarlyList(con,SqlUtil.getEmplateOutEarly(con, eaVo), newEmpLateEarlyRO);
	//	System.out.println("late  sql "+SqlUtil.getEmplateOutEarly(con, eaVo));
		eaVo.setQueryIsLate("2");//早退
		List<empLateEarlyRO> leoEarly=DBUtil.queryEmpLateEarlyList(con,SqlUtil.getEmplateOutEarly(con, eaVo), newEmpLateEarlyRO);
	//	System.out.println("Early  sql "+SqlUtil.getEmplateOutEarly(con, eaVo));
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
			
			
			
			//遲到
			if(leoLate.size()>0){
				accordions=accordions.replace("<LateTimes/>", leoLate.get(0).getLATETIMES());
				accordions=accordions.replace("<LateHour/>", leoLate.get(0).getHOUR());
				accordions=accordions.replace("<LateMinute/>", leoLate.get(0).getMINUTE());
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
				accordions=accordions.replace("<EarlyTimes/>", leoEarly.get(0).getLATETIMES());
				accordions=accordions.replace("<EarlyHour/>", leoEarly.get(0).getHOUR());
				accordions=accordions.replace("<EarlyMinute/>", leoEarly.get(0).getMINUTE());
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
	 * @throws ParseException 
	 */
	public static String drawAttendanceDayTable(Connection con,leaveCardVO lcVo) throws ParseException
	{
		String Control="";
		HtmlUtil hu=new HtmlUtil();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
		Control =hu.gethtml(UrlUtil.control_attendanceDayTable);
		Control=Control.replace("<toDay/>",lcVo.getApplicationDate().replaceAll("/", ""));
		Date date = sdf.parse( lcVo.getApplicationDate().replaceAll("/", "") );
		date=DateUtil.addDays(date, -1);
		Control=Control.replace("<yesterDay/>",sdf.format(date));
		dayTableMoble dt=new dayTableMoble();
		
		/**外籍幹部人數Data**/
		repAttendanceDayRO ra=new repAttendanceDayRO();
		dt.setCo3(DBUtil.queryForeignCadres(con, SqlUtil.getNoVnMaxPeople(lcVo), ra));
		/**昨天越籍應出勤人數Data**/
		dt.setCo4(DBUtil.queryForeignCadres(con, SqlUtil.getYestoDayVnMaxPeople(lcVo), ra));
		/**今天越籍應出勤人數Data**/
		dt.setCo5(DBUtil.queryForeignCadres(con, SqlUtil.getVnMaxPeople(lcVo), ra));
		/**今天越籍實際出勤人數Data**/
		dt.setCo6(DBUtil.queryForeignCadres(con, SqlUtil.getActualMaxPeople(lcVo), ra));
		/**今天行政班次人數Data**/
		dt.setCo7(DBUtil.queryForeignCadres(con, SqlUtil.getTurnA1People(lcVo), ra));
		/**今天早班次人數Data**/
		dt.setCo8(DBUtil.queryForeignCadres(con, SqlUtil.getTurnC1People(lcVo), ra));
		/**今天中班次人數Data**/
		dt.setCo9(DBUtil.queryForeignCadres(con, SqlUtil.getTurnC2People(lcVo), ra));
		/**今天夜班次人數Data**/
		dt.setCo10(DBUtil.queryForeignCadres(con, SqlUtil.getTurnC3People(lcVo), ra));
		/**今天加班次人數Data**/
		dt.setCo11(DBUtil.queryForeignCadres(con, SqlUtil.getOverTimePeople(lcVo), ra));
		/**今天產假人數Data**/
		dt.setCo12(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,KeyUtil.holiayType5), ra));
		/**今天請假人數Data**/
		dt.setCo13(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,KeyUtil.holiayTypeAll), ra));
		/**今天年假人數Data**/
		dt.setCo14(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,KeyUtil.holiayType8), ra));
		/**今天出差人數Data**/
		dt.setCo15(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,KeyUtil.holiayType8), ra));
		/**今天公傷人數Data**/
		dt.setCo16(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,KeyUtil.holiayType3), ra));
		/**今天曠工或未請假人數Data**/
		dt.setCo17(DBUtil.queryForeignCadres(con, SqlUtil.getVnAbsenteeismPeople(lcVo), ra));
		/**今天周六排休人數Data**/
		dt.setCo18(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,KeyUtil.holiayType7), ra));
		/**新人報道人數Data**/
		dt.setCo19(DBUtil.queryForeignCadres(con, SqlUtil.getVnInDatePeople(lcVo), ra));
		/**調動人數Data**/
		dt.setCo20(DBUtil.queryForeignCadres(con, SqlUtil.getvnNoLeavePeople(lcVo), ra));
		/**離職人數Data**/
		dt.setCo21(DBUtil.queryForeignCadres(con, SqlUtil.getvnNoLeavePeople(lcVo), ra));
		/**離職率Data**/
		dt=DataStringUtil.turnoverRate(dt);
		/**1年以下Data**/
		dt.setYco1(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(lcVo,sqlConsts.sql_vnLessThanOneYear), "yco")));
		/**1年~2年Data**/
		dt.setYco2(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(lcVo,sqlConsts.sql_vnOneToTwoYears), "yco")));
		/**2年~3年Data**/
		dt.setYco3(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(lcVo,sqlConsts.sql_vnTwoToThreeYears), "yco")));
		/**3年以上Data**/
		dt.setYco4(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(lcVo,sqlConsts.sql_vnLessThanOneYear), "yco")));
		
		
		dayTableRowMoble dr=new dayTableRowMoble();
		Hashtable ht=new Hashtable();
		dr.setHt(ht);
		
		/**數據行**/
		String[] updateDayRows=KeyUtil.rowS.split(",");
		for(int i=0;i<updateDayRows.length;i++){
			 Control=DataStringUtil.updateDayTableRow(Control,updateDayRows[i],KeyUtil.colStart,KeyUtil.colEnd,dt, dr);
		}
		/**統計行**/
		String[] replaceAddRows=KeyUtil.maxRowS.split(",");
		for(int i=0;i<replaceAddRows.length;i++){
			String[] values=replaceAddRows[i].split("#");
			 Control=DataStringUtil.replaceAddUpDayTableRow(Control,values[0],KeyUtil.colStart,
					 KeyUtil.colEnd,Integer.valueOf(values[1]),Integer.valueOf(values[2]),dr);
		}

		/** 合計**/
		 String[] addRow=UrlUtil.rep_AttendanceDayAddRow.split(",");
		 Control=DataStringUtil.updateMaxAddUpTableRow(Control,"47",KeyUtil.colStart,KeyUtil.colEnd,addRow,dr);
		 
		/** 越籍年資分布**/
		 Control= Control.replace("<yco1/>",String.valueOf( dt.getYco1()));
		 Control= Control.replace("<yco2/>",String.valueOf( dt.getYco2()));
		 Control= Control.replace("<yco3/>",String.valueOf( dt.getYco3()));
		 Control= Control.replace("<yco4/>",String.valueOf( dt.getYco4()));
		 System.out.println( dt.getYco1()/dt.getYmax());
		 Control= Control.replace("<yco5/>",String.valueOf( dt.getYco1()/dt.getYmax()));
		 Control= Control.replace("<yco6/>",String.valueOf( dt.getYco2()/dt.getYmax()));
		 Control= Control.replace("<yco7/>",String.valueOf( dt.getYco3()/dt.getYmax()));
		 Control= Control.replace("<yco8/>",String.valueOf( dt.getYco4()/dt.getYmax()));
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
	
}
