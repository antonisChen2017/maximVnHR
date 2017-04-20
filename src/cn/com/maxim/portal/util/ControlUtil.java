package cn.com.maxim.portal.util;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

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
import cn.com.maxim.portal.bean.repAttendanceDayBean;
import cn.com.maxim.portal.key.KeyUtil;

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
		/**外籍幹部人數Data**/
		repAttendanceDayRO ra=new repAttendanceDayRO();
		List<repAttendanceDayRO> co3 =DBUtil.queryForeignCadres(con, SqlUtil.getNoVnMaxPeople(lcVo), ra);
		/**昨天越籍應出勤人數Data**/
		List<repAttendanceDayRO> co4 =DBUtil.queryForeignCadres(con, SqlUtil.getYestoDayVnMaxPeople(lcVo), ra);
		/**今天越籍應出勤人數Data**/
		List<repAttendanceDayRO> co5 =DBUtil.queryForeignCadres(con, SqlUtil.getVnMaxPeople(lcVo), ra);
		/**今天越籍實際出勤人數Data**/
		List<repAttendanceDayRO> co6 =DBUtil.queryForeignCadres(con, SqlUtil.getActualMaxPeople(lcVo), ra);
		/**今天行政班次人數Data**/
		List<repAttendanceDayRO> co7 =DBUtil.queryForeignCadres(con, SqlUtil.getTurnA1People(lcVo), ra);
		/**今天早班次人數Data**/
		List<repAttendanceDayRO> co8 =DBUtil.queryForeignCadres(con, SqlUtil.getTurnC1People(lcVo), ra);
		/**今天中班次人數Data**/
		List<repAttendanceDayRO> co9 =DBUtil.queryForeignCadres(con, SqlUtil.getTurnC2People(lcVo), ra);
		/**今天夜班次人數Data**/
		List<repAttendanceDayRO> co10 =DBUtil.queryForeignCadres(con, SqlUtil.getTurnC3People(lcVo), ra);
		/**今天加班次人數Data**/
		List<repAttendanceDayRO> co11 =DBUtil.queryForeignCadres(con, SqlUtil.getOverTimePeople(lcVo), ra);
		/**今天產假人數Data**/
		List<repAttendanceDayRO> co12 =DBUtil.queryForeignCadres(con, SqlUtil.getOverTimePeople(lcVo), ra);
		Hashtable ht=new Hashtable();
		
		/**管理部 管理部**/
		repAttendanceDayBean row4=new repAttendanceDayBean();
		row4.setC3(DataStringUtil.getDayTableRow4(co3));
		row4.setC4(DataStringUtil.getDayTableRow4(co4));
		row4.setC5(DataStringUtil.getDayTableRow4(co5));
		row4.setC6(DataStringUtil.getDayTableRow4(co6));
		row4.setC7(DataStringUtil.getDayTableRow4(co7));
		row4.setC8(DataStringUtil.getDayTableRow4(co8));
		row4.setC9(DataStringUtil.getDayTableRow4(co9));
		row4.setC10(DataStringUtil.getDayTableRow4(co10));
		row4.setC11(DataStringUtil.getDayTableRow4(co11));
		ht.put("row4",row4 );
		Control=Control.replace("<r4c3/>",row4.getC3());
		Control=Control.replace("<r4c4/>",row4.getC4());
		Control=Control.replace("<r4c5/>",row4.getC5());
		Control=Control.replace("<r4c6/>",row4.getC6());
		Control=Control.replace("<r4c7/>",row4.getC7());
		Control=Control.replace("<r4c8/>",row4.getC8());
		Control=Control.replace("<r4c9/>",row4.getC9());
		Control=Control.replace("<r4c10/>",row4.getC10());
		Control=Control.replace("<r4c11/>",row4.getC11());
		/**管理部 1.人事課**/
		repAttendanceDayBean row5=new repAttendanceDayBean();
		row5.setC3(DataStringUtil.getDayTableRow5(co3));
		row5.setC4(DataStringUtil.getDayTableRow5(co4));
		row5.setC5(DataStringUtil.getDayTableRow5(co5));
		row5.setC6(DataStringUtil.getDayTableRow5(co6));
		row5.setC7(DataStringUtil.getDayTableRow5(co7));
		row5.setC8(DataStringUtil.getDayTableRow5(co8));
		row5.setC9(DataStringUtil.getDayTableRow5(co9));
		row5.setC10(DataStringUtil.getDayTableRow5(co10));
		row5.setC11(DataStringUtil.getDayTableRow5(co11));
		ht.put("row5",row5 );
		Control=Control.replace("<r5c3/>",row5.getC3());
		Control=Control.replace("<r5c4/>",row5.getC4()); 
		Control=Control.replace("<r5c5/>",row5.getC5()); 
		Control=Control.replace("<r5c6/>",row5.getC6()); 
		Control=Control.replace("<r5c7/>",row5.getC7());
		Control=Control.replace("<r5c8/>",row5.getC8()); 
		Control=Control.replace("<r5c9/>",row5.getC9()); 
		Control=Control.replace("<r5c10/>",row5.getC10()); 
		Control=Control.replace("<r5c11/>",row5.getC11()); 
		/**管理部 2.行政課**/
		Control=Control.replace("<r6c3/>",DataStringUtil.getDayTableRow6(co3));
		Control=Control.replace("<r6c4/>",DataStringUtil.getDayTableRow6(co4));
		Control=Control.replace("<r6c5/>",DataStringUtil.getDayTableRow6(co5));
		Control=Control.replace("<r6c6/>",DataStringUtil.getDayTableRow6(co6));
		Control=Control.replace("<r6c7/>",DataStringUtil.getDayTableRow6(co7));
		Control=Control.replace("<r6c8/>",DataStringUtil.getDayTableRow6(co8));
		Control=Control.replace("<r6c9/>",DataStringUtil.getDayTableRow6(co9));
		Control=Control.replace("<r6c10/>",DataStringUtil.getDayTableRow6(co10));
		Control=Control.replace("<r6c11/>",DataStringUtil.getDayTableRow6(co11));
		/**管理部 3.總務課**/
		Control=Control.replace("<r7c3/>",DataStringUtil.getDayTableRow7(co3));
		Control=Control.replace("<r7c4/>",DataStringUtil.getDayTableRow7(co4));
		Control=Control.replace("<r7c5/>",DataStringUtil.getDayTableRow7(co5));
		Control=Control.replace("<r7c6/>",DataStringUtil.getDayTableRow7(co6));
		Control=Control.replace("<r7c7/>",DataStringUtil.getDayTableRow7(co7));
		Control=Control.replace("<r7c8/>",DataStringUtil.getDayTableRow7(co8));
		Control=Control.replace("<r7c9/>",DataStringUtil.getDayTableRow7(co9));
		Control=Control.replace("<r7c10/>",DataStringUtil.getDayTableRow7(co10));
		Control=Control.replace("<r7c11/>",DataStringUtil.getDayTableRow7(co11));
		/**管理部 4.資訊課**/
		Control=Control.replace("<r8c3/>",DataStringUtil.getDayTableRow8(co3));
		Control=Control.replace("<r8c4/>",DataStringUtil.getDayTableRow8(co4));
		Control=Control.replace("<r8c5/>",DataStringUtil.getDayTableRow8(co5));
		Control=Control.replace("<r8c6/>",DataStringUtil.getDayTableRow8(co6));
		Control=Control.replace("<r8c7/>",DataStringUtil.getDayTableRow8(co7));
		Control=Control.replace("<r8c8/>",DataStringUtil.getDayTableRow8(co8));
		Control=Control.replace("<r8c9/>",DataStringUtil.getDayTableRow8(co9));
		Control=Control.replace("<r8c10/>",DataStringUtil.getDayTableRow8(co10));
		Control=Control.replace("<r8c11/>",DataStringUtil.getDayTableRow8(co11));
		/**管理部 5.機修課**/
		Control=Control.replace("<r9c3/>",DataStringUtil.getDayTableRow9(co3));
		Control=Control.replace("<r9c4/>",DataStringUtil.getDayTableRow9(co4));
		Control=Control.replace("<r9c5/>",DataStringUtil.getDayTableRow9(co5));
		Control=Control.replace("<r9c6/>",DataStringUtil.getDayTableRow9(co6));
		Control=Control.replace("<r9c7/>",DataStringUtil.getDayTableRow9(co7));
		Control=Control.replace("<r9c8/>",DataStringUtil.getDayTableRow9(co8));
		Control=Control.replace("<r9c9/>",DataStringUtil.getDayTableRow9(co9));
		Control=Control.replace("<r9c10/>",DataStringUtil.getDayTableRow9(co10));
		Control=Control.replace("<r9c11/>",DataStringUtil.getDayTableRow9(co11));
		/**管理部 合計**/
		Control=Control.replace("<r10c3/>","-");
		Control=Control.replace("<r10c4/>","-");
		Control=Control.replace("<r10c5/>","-");
		Control=Control.replace("<r10c6/>","-");
		Control=Control.replace("<r10c7/>","-");
		Control=Control.replace("<r10c8/>","-");
		Control=Control.replace("<r10c9/>","-");
		Control=Control.replace("<r10c10/>","-");
		Control=Control.replace("<r10c11/>","-");
		/**財務部 財務部**/
		Control=Control.replace("<r11c3/>",DataStringUtil.getDayTableRow11(co3));
		Control=Control.replace("<r11c4/>",DataStringUtil.getDayTableRow11(co4));
		Control=Control.replace("<r11c5/>",DataStringUtil.getDayTableRow11(co5));
		Control=Control.replace("<r11c6/>",DataStringUtil.getDayTableRow11(co6));
		Control=Control.replace("<r11c7/>",DataStringUtil.getDayTableRow11(co7));
		Control=Control.replace("<r11c8/>",DataStringUtil.getDayTableRow11(co8));
		Control=Control.replace("<r11c9/>",DataStringUtil.getDayTableRow11(co9));
		Control=Control.replace("<r11c10/>",DataStringUtil.getDayTableRow11(co10));
		Control=Control.replace("<r11c11/>",DataStringUtil.getDayTableRow11(co11));
		/**財務部 海關**/
		Control=Control.replace("<r12c3/>",DataStringUtil.getDayTableRow12(co3));
		Control=Control.replace("<r12c4/>",DataStringUtil.getDayTableRow12(co4));
		Control=Control.replace("<r12c5/>",DataStringUtil.getDayTableRow12(co5));
		Control=Control.replace("<r12c6/>",DataStringUtil.getDayTableRow12(co6));
		Control=Control.replace("<r12c7/>",DataStringUtil.getDayTableRow12(co7));
		Control=Control.replace("<r12c8/>",DataStringUtil.getDayTableRow12(co8));
		Control=Control.replace("<r12c9/>",DataStringUtil.getDayTableRow12(co9));
		Control=Control.replace("<r12c10/>",DataStringUtil.getDayTableRow12(co10));
		Control=Control.replace("<r12c11/>",DataStringUtil.getDayTableRow12(co11));
		/**財務部 會計**/
		Control=Control.replace("<r13c3/>",DataStringUtil.getDayTableRow13(co3));
		Control=Control.replace("<r13c4/>",DataStringUtil.getDayTableRow13(co4));
		Control=Control.replace("<r13c5/>",DataStringUtil.getDayTableRow13(co5));
		Control=Control.replace("<r13c6/>",DataStringUtil.getDayTableRow13(co6));
		Control=Control.replace("<r13c7/>",DataStringUtil.getDayTableRow13(co7));
		Control=Control.replace("<r13c8/>",DataStringUtil.getDayTableRow13(co8));
		Control=Control.replace("<r13c9/>",DataStringUtil.getDayTableRow13(co9));
		Control=Control.replace("<r13c10/>",DataStringUtil.getDayTableRow13(co10));
		Control=Control.replace("<r13c11/>",DataStringUtil.getDayTableRow13(co11));
		/**合計**/
		Control=Control.replace("<r14c3/>","-");
		Control=Control.replace("<r14c4/>","-");
		Control=Control.replace("<r14c5/>","-");
		Control=Control.replace("<r14c6/>","-");
		Control=Control.replace("<r14c7/>","-");
		Control=Control.replace("<r14c8/>","-");
		Control=Control.replace("<r14c9/>","-");
		Control=Control.replace("<r14c10/>","-");
		Control=Control.replace("<r14c11/>","-");
		/**業務部 業務部**/
		Control=Control.replace("<r15c3/>",DataStringUtil.getDayTableRow15(co3));
		Control=Control.replace("<r15c4/>",DataStringUtil.getDayTableRow15(co4));
		Control=Control.replace("<r15c5/>",DataStringUtil.getDayTableRow15(co5));
		Control=Control.replace("<r15c6/>",DataStringUtil.getDayTableRow15(co6));
		Control=Control.replace("<r15c7/>",DataStringUtil.getDayTableRow15(co7));
		Control=Control.replace("<r15c8/>",DataStringUtil.getDayTableRow15(co8));
		Control=Control.replace("<r15c9/>",DataStringUtil.getDayTableRow15(co9));
		Control=Control.replace("<r15c10/>",DataStringUtil.getDayTableRow15(co10));
		Control=Control.replace("<r15c11/>",DataStringUtil.getDayTableRow15(co11));
		/**業務部  業務A**/
		Control=Control.replace("<r16c3/>",DataStringUtil.getDayTableRow16(co3));
		Control=Control.replace("<r16c4/>",DataStringUtil.getDayTableRow16(co4));
		Control=Control.replace("<r16c5/>",DataStringUtil.getDayTableRow16(co5));
		Control=Control.replace("<r16c6/>",DataStringUtil.getDayTableRow16(co6));
		Control=Control.replace("<r16c7/>",DataStringUtil.getDayTableRow16(co7));
		Control=Control.replace("<r16c8/>",DataStringUtil.getDayTableRow16(co8));
		Control=Control.replace("<r16c9/>",DataStringUtil.getDayTableRow16(co9));
		Control=Control.replace("<r16c10/>",DataStringUtil.getDayTableRow16(co10));
		Control=Control.replace("<r16c11/>",DataStringUtil.getDayTableRow16(co11));
		/**業務部 業務B**/
		Control=Control.replace("<r17c3/>",DataStringUtil.getDayTableRow17(co3));
		Control=Control.replace("<r17c4/>",DataStringUtil.getDayTableRow17(co4));
		Control=Control.replace("<r17c5/>",DataStringUtil.getDayTableRow17(co5));
		Control=Control.replace("<r17c6/>",DataStringUtil.getDayTableRow17(co6));
		Control=Control.replace("<r17c7/>",DataStringUtil.getDayTableRow17(co7));
		Control=Control.replace("<r17c8/>",DataStringUtil.getDayTableRow17(co8));
		Control=Control.replace("<r17c9/>",DataStringUtil.getDayTableRow17(co9));
		Control=Control.replace("<r17c10/>",DataStringUtil.getDayTableRow17(co10));
		Control=Control.replace("<r17c11/>",DataStringUtil.getDayTableRow17(co11));
		/**合計**/
		Control=Control.replace("<r18c3/>","-");
		Control=Control.replace("<r18c4/>","-");
		Control=Control.replace("<r18c5/>","-");
		Control=Control.replace("<r18c6/>","-");
		Control=Control.replace("<r18c7/>","-");
		Control=Control.replace("<r18c8/>","-");
		Control=Control.replace("<r18c9/>","-");
		Control=Control.replace("<r18c10/>","-");
		Control=Control.replace("<r18c11/>","-");
		/**Prinshop Prinshop**/
		Control=Control.replace("<r19c3/>",DataStringUtil.getDayTableRow19(co3));
		Control=Control.replace("<r19c4/>",DataStringUtil.getDayTableRow19(co4));
		Control=Control.replace("<r19c5/>",DataStringUtil.getDayTableRow19(co5));
		Control=Control.replace("<r19c6/>",DataStringUtil.getDayTableRow19(co6));
		Control=Control.replace("<r19c7/>",DataStringUtil.getDayTableRow19(co7));
		Control=Control.replace("<r19c8/>",DataStringUtil.getDayTableRow19(co8));
		Control=Control.replace("<r19c9/>",DataStringUtil.getDayTableRow19(co9));
		Control=Control.replace("<r19c10/>",DataStringUtil.getDayTableRow19(co10));
		Control=Control.replace("<r19c11/>",DataStringUtil.getDayTableRow19(co11));
		/**Prinshop cs**/
		Control=Control.replace("<r20c3/>",DataStringUtil.getDayTableRow20(co3));
		Control=Control.replace("<r20c4/>",DataStringUtil.getDayTableRow20(co4));
		Control=Control.replace("<r20c5/>",DataStringUtil.getDayTableRow20(co5));
		Control=Control.replace("<r20c6/>",DataStringUtil.getDayTableRow20(co6));
		Control=Control.replace("<r20c7/>",DataStringUtil.getDayTableRow20(co7));
		Control=Control.replace("<r20c8/>",DataStringUtil.getDayTableRow20(co8));
		Control=Control.replace("<r20c9/>",DataStringUtil.getDayTableRow20(co9));
		Control=Control.replace("<r20c10/>",DataStringUtil.getDayTableRow20(co10));
		Control=Control.replace("<r20c11/>",DataStringUtil.getDayTableRow20(co11));
		/**Prinshop DPS**/
		Control=Control.replace("<r21c3/>",DataStringUtil.getDayTableRow21(co3));
		Control=Control.replace("<r21c4/>",DataStringUtil.getDayTableRow21(co4));
		Control=Control.replace("<r21c5/>",DataStringUtil.getDayTableRow21(co5));
		Control=Control.replace("<r21c6/>",DataStringUtil.getDayTableRow21(co6));
		Control=Control.replace("<r21c7/>",DataStringUtil.getDayTableRow21(co7));
		Control=Control.replace("<r21c8/>",DataStringUtil.getDayTableRow21(co8));
		Control=Control.replace("<r21c9/>",DataStringUtil.getDayTableRow21(co9));
		Control=Control.replace("<r21c10/>",DataStringUtil.getDayTableRow21(co10));
		Control=Control.replace("<r21c11/>",DataStringUtil.getDayTableRow21(co11));
		/**合計**/
		Control=Control.replace("<r22c3/>","-");
		Control=Control.replace("<r22c4/>","-");
		Control=Control.replace("<r22c5/>","-");
		Control=Control.replace("<r22c6/>","-");
		Control=Control.replace("<r22c7/>","-");
		Control=Control.replace("<r22c8/>","-");
		Control=Control.replace("<r22c9/>","-");
		Control=Control.replace("<r22c10/>","-");
		/**資材部 資材部**/
		Control=Control.replace("<r23c3/>",DataStringUtil.getDayTableRow23(co3));
		Control=Control.replace("<r23c4/>",DataStringUtil.getDayTableRow23(co4));
		Control=Control.replace("<r23c5/>",DataStringUtil.getDayTableRow23(co5));
		Control=Control.replace("<r23c6/>",DataStringUtil.getDayTableRow23(co6));
		Control=Control.replace("<r23c7/>",DataStringUtil.getDayTableRow23(co7));
		Control=Control.replace("<r23c8/>",DataStringUtil.getDayTableRow23(co8));
		Control=Control.replace("<r23c9/>",DataStringUtil.getDayTableRow23(co9));
		Control=Control.replace("<r23c10/>",DataStringUtil.getDayTableRow23(co10));
		Control=Control.replace("<r23c11/>",DataStringUtil.getDayTableRow23(co11));
		/**資材部 倉管**/
		Control=Control.replace("<r24c3/>",DataStringUtil.getDayTableRow24(co3));
		Control=Control.replace("<r24c4/>",DataStringUtil.getDayTableRow24(co4));
		Control=Control.replace("<r24c5/>",DataStringUtil.getDayTableRow24(co5));
		Control=Control.replace("<r24c6/>",DataStringUtil.getDayTableRow24(co6));
		Control=Control.replace("<r24c7/>",DataStringUtil.getDayTableRow24(co7));
		Control=Control.replace("<r24c8/>",DataStringUtil.getDayTableRow24(co8));
		Control=Control.replace("<r24c9/>",DataStringUtil.getDayTableRow24(co9));
		Control=Control.replace("<r24c10/>",DataStringUtil.getDayTableRow24(co10));
		Control=Control.replace("<r24c11/>",DataStringUtil.getDayTableRow24(co11));
		/**資材部 外發採購**/
		Control=Control.replace("<r25c3/>",DataStringUtil.getDayTableRow25(co3));
		Control=Control.replace("<r25c4/>",DataStringUtil.getDayTableRow25(co4));
		Control=Control.replace("<r25c5/>",DataStringUtil.getDayTableRow25(co5));
		Control=Control.replace("<r25c6/>",DataStringUtil.getDayTableRow25(co6));
		Control=Control.replace("<r25c7/>",DataStringUtil.getDayTableRow25(co7));
		Control=Control.replace("<r25c8/>",DataStringUtil.getDayTableRow25(co8));
		Control=Control.replace("<r25c9/>",DataStringUtil.getDayTableRow25(co9));
		Control=Control.replace("<r25c10/>",DataStringUtil.getDayTableRow25(co10));
		Control=Control.replace("<r25c11/>",DataStringUtil.getDayTableRow25(co11));
		/**資材部  原料採購**/
		Control=Control.replace("<r26c3/>",DataStringUtil.getDayTableRow26(co3));
		Control=Control.replace("<r26c4/>",DataStringUtil.getDayTableRow26(co4));
		Control=Control.replace("<r26c5/>",DataStringUtil.getDayTableRow26(co5));
		Control=Control.replace("<r26c6/>",DataStringUtil.getDayTableRow26(co6));
		Control=Control.replace("<r26c7/>",DataStringUtil.getDayTableRow26(co7));
		Control=Control.replace("<r26c8/>",DataStringUtil.getDayTableRow26(co8));
		Control=Control.replace("<r26c9/>",DataStringUtil.getDayTableRow26(co9));
		Control=Control.replace("<r26c10/>",DataStringUtil.getDayTableRow26(co10));
		Control=Control.replace("<r26c11/>",DataStringUtil.getDayTableRow25(co11));
		/**資材部  核價**/
		Control=Control.replace("<r27c3/>",DataStringUtil.getDayTableRow27(co3));
		Control=Control.replace("<r27c4/>",DataStringUtil.getDayTableRow27(co4));
		Control=Control.replace("<r27c5/>",DataStringUtil.getDayTableRow27(co5));
		Control=Control.replace("<r27c6/>",DataStringUtil.getDayTableRow27(co6));
		Control=Control.replace("<r27c7/>",DataStringUtil.getDayTableRow27(co7));
		Control=Control.replace("<r27c8/>",DataStringUtil.getDayTableRow27(co8));
		Control=Control.replace("<r27c9/>",DataStringUtil.getDayTableRow27(co9));
		Control=Control.replace("<r27c10/>",DataStringUtil.getDayTableRow27(co10));
		Control=Control.replace("<r27c11/>",DataStringUtil.getDayTableRow25(co11));
		/**資材部  合計**/
		Control=Control.replace("<r28c3/>","-");
		Control=Control.replace("<r28c4/>","-");
		Control=Control.replace("<r28c5/>","-");
		Control=Control.replace("<r28c6/>","-");
		Control=Control.replace("<r28c7/>","-");
		Control=Control.replace("<r28c8/>","-");
		Control=Control.replace("<r28c9/>","-");
		Control=Control.replace("<r28c10/>","-");
		Control=Control.replace("<r28c11/>","-");
		/**生管部  生管部**/
		Control=Control.replace("<r29c3/>",DataStringUtil.getDayTableRow29(co3));
		Control=Control.replace("<r29c4/>",DataStringUtil.getDayTableRow29(co4));
		Control=Control.replace("<r29c5/>",DataStringUtil.getDayTableRow29(co5));
		Control=Control.replace("<r29c6/>",DataStringUtil.getDayTableRow29(co6));
		Control=Control.replace("<r29c7/>",DataStringUtil.getDayTableRow29(co7));
		Control=Control.replace("<r29c8/>",DataStringUtil.getDayTableRow29(co8));
		Control=Control.replace("<r29c9/>",DataStringUtil.getDayTableRow29(co9));
		Control=Control.replace("<r29c10/>",DataStringUtil.getDayTableRow29(co10));
		Control=Control.replace("<r29c11/>",DataStringUtil.getDayTableRow25(co11));
		/**生管部  開單**/
		Control=Control.replace("<r30c3/>",DataStringUtil.getDayTableRow30(co3));
		Control=Control.replace("<r30c4/>",DataStringUtil.getDayTableRow30(co4));
		Control=Control.replace("<r30c5/>",DataStringUtil.getDayTableRow30(co5));
		Control=Control.replace("<r30c6/>",DataStringUtil.getDayTableRow30(co6));
		Control=Control.replace("<r30c7/>",DataStringUtil.getDayTableRow30(co7));
		Control=Control.replace("<r30c8/>",DataStringUtil.getDayTableRow30(co8));
		Control=Control.replace("<r30c9/>",DataStringUtil.getDayTableRow30(co9));
		Control=Control.replace("<r30c10/>",DataStringUtil.getDayTableRow30(co10));
		Control=Control.replace("<r30c11/>",DataStringUtil.getDayTableRow25(co11));
		/**生管部  外發**/
		Control=Control.replace("<r31c3/>",DataStringUtil.getDayTableRow31(co3));
		Control=Control.replace("<r31c4/>",DataStringUtil.getDayTableRow31(co4));
		Control=Control.replace("<r31c5/>",DataStringUtil.getDayTableRow31(co5));
		Control=Control.replace("<r31c6/>",DataStringUtil.getDayTableRow31(co6));
		Control=Control.replace("<r31c7/>",DataStringUtil.getDayTableRow31(co7));
		Control=Control.replace("<r31c8/>",DataStringUtil.getDayTableRow31(co8));
		Control=Control.replace("<r31c9/>",DataStringUtil.getDayTableRow31(co9));
		Control=Control.replace("<r31c10/>",DataStringUtil.getDayTableRow31(co10));
		Control=Control.replace("<r31c11/>",DataStringUtil.getDayTableRow25(co11));
		/**生管部  原料採購**/
		Control=Control.replace("<r32c3/>",DataStringUtil.getDayTableRow32(co3));
		Control=Control.replace("<r32c4/>",DataStringUtil.getDayTableRow32(co4));
		Control=Control.replace("<r32c5/>",DataStringUtil.getDayTableRow32(co5));
		Control=Control.replace("<r32c6/>",DataStringUtil.getDayTableRow32(co6));
		Control=Control.replace("<r32c7/>",DataStringUtil.getDayTableRow32(co7));
		Control=Control.replace("<r32c8/>",DataStringUtil.getDayTableRow32(co8));
		Control=Control.replace("<r32c9/>",DataStringUtil.getDayTableRow32(co9));
		Control=Control.replace("<r32c10/>",DataStringUtil.getDayTableRow32(co10));
		Control=Control.replace("<r32c11/>",DataStringUtil.getDayTableRow25(co11));
		/**生管部  工程**/
		Control=Control.replace("<r33c3/>",DataStringUtil.getDayTableRow33(co3));
		Control=Control.replace("<r33c4/>",DataStringUtil.getDayTableRow33(co4));
		Control=Control.replace("<r33c5/>",DataStringUtil.getDayTableRow33(co5));
		Control=Control.replace("<r33c6/>",DataStringUtil.getDayTableRow33(co6));
		Control=Control.replace("<r33c7/>",DataStringUtil.getDayTableRow33(co7));
		Control=Control.replace("<r33c8/>",DataStringUtil.getDayTableRow33(co8));
		Control=Control.replace("<r33c9/>",DataStringUtil.getDayTableRow33(co9));
		Control=Control.replace("<r33c10/>",DataStringUtil.getDayTableRow33(co10));
		Control=Control.replace("<r33c11/>",DataStringUtil.getDayTableRow25(co11));
		/**生管部  合計**/
		Control=Control.replace("<r34c3/>","-");
		Control=Control.replace("<r34c4/>","-");
		Control=Control.replace("<r34c5/>","-");
		Control=Control.replace("<r34c6/>","-");
		Control=Control.replace("<r34c7/>","-");
		Control=Control.replace("<r34c8/>","-");
		Control=Control.replace("<r34c9/>","-");
		Control=Control.replace("<r34c10/>","-");
		Control=Control.replace("<r34c11/>","-");
		/**印前部  畫稿**/
		Control=Control.replace("<r35c3/>",DataStringUtil.getDayTableRow35(co3));
		Control=Control.replace("<r35c4/>",DataStringUtil.getDayTableRow35(co4));
		Control=Control.replace("<r35c5/>",DataStringUtil.getDayTableRow35(co5));
		Control=Control.replace("<r35c6/>",DataStringUtil.getDayTableRow35(co6));
		Control=Control.replace("<r35c7/>",DataStringUtil.getDayTableRow35(co7));
		Control=Control.replace("<r35c8/>",DataStringUtil.getDayTableRow35(co8));
		Control=Control.replace("<r35c9/>",DataStringUtil.getDayTableRow35(co9));
		Control=Control.replace("<r35c10/>",DataStringUtil.getDayTableRow35(co10));
		Control=Control.replace("<r35c11/>",DataStringUtil.getDayTableRow25(co11));
		/**印前部 出菲林**/
		Control=Control.replace("<r36c3/>",DataStringUtil.getDayTableRow36(co3));
		Control=Control.replace("<r36c4/>",DataStringUtil.getDayTableRow36(co4));
		Control=Control.replace("<r36c5/>",DataStringUtil.getDayTableRow36(co5));
		Control=Control.replace("<r36c6/>",DataStringUtil.getDayTableRow36(co6));
		Control=Control.replace("<r36c7/>",DataStringUtil.getDayTableRow36(co7));
		Control=Control.replace("<r36c8/>",DataStringUtil.getDayTableRow36(co8));
		Control=Control.replace("<r36c9/>",DataStringUtil.getDayTableRow36(co9));
		Control=Control.replace("<r36c10/>",DataStringUtil.getDayTableRow36(co10));
		Control=Control.replace("<r36c11/>",DataStringUtil.getDayTableRow25(co11));
		/**印前部  EBS**/
		Control=Control.replace("<r37c3/>",DataStringUtil.getDayTableRow37(co3));
		Control=Control.replace("<r37c4/>",DataStringUtil.getDayTableRow37(co4));
		Control=Control.replace("<r37c5/>",DataStringUtil.getDayTableRow37(co5));
		Control=Control.replace("<r37c6/>",DataStringUtil.getDayTableRow37(co6));
		Control=Control.replace("<r37c7/>",DataStringUtil.getDayTableRow37(co7));
		Control=Control.replace("<r37c8/>",DataStringUtil.getDayTableRow37(co8));
		Control=Control.replace("<r37c9/>",DataStringUtil.getDayTableRow37(co9));
		Control=Control.replace("<r37c10/>",DataStringUtil.getDayTableRow37(co10));
		Control=Control.replace("<r37c11/>",DataStringUtil.getDayTableRow25(co11));
		/**印前部  合計**/
		Control=Control.replace("<r38c3/>","-");
		Control=Control.replace("<r38c4/>","-");
		Control=Control.replace("<r38c5/>","-");
		Control=Control.replace("<r38c6/>","-");
		Control=Control.replace("<r38c7/>","-");
		Control=Control.replace("<r38c8/>","-");
		Control=Control.replace("<r38c9/>","-");
		Control=Control.replace("<r38c10/>","-");
		Control=Control.replace("<r38c11/>","-");
		/**品保部  QC**/
		Control=Control.replace("<r39c3/>",DataStringUtil.getDayTableRow39(co3));
		Control=Control.replace("<r39c4/>",DataStringUtil.getDayTableRow39(co4));
		Control=Control.replace("<r39c5/>",DataStringUtil.getDayTableRow39(co5));
		Control=Control.replace("<r39c6/>",DataStringUtil.getDayTableRow39(co6));
		Control=Control.replace("<r39c7/>",DataStringUtil.getDayTableRow39(co7));
		Control=Control.replace("<r39c8/>",DataStringUtil.getDayTableRow39(co8));
		Control=Control.replace("<r39c9/>",DataStringUtil.getDayTableRow39(co9));
		Control=Control.replace("<r39c10/>",DataStringUtil.getDayTableRow39(co10));
		Control=Control.replace("<r39c11/>",DataStringUtil.getDayTableRow25(co11));
		/**品保部 QA,實驗,助理**/
		Control=Control.replace("<r40c3/>",DataStringUtil.getDayTableRow40(co3));
		Control=Control.replace("<r40c4/>",DataStringUtil.getDayTableRow40(co4));
		Control=Control.replace("<r40c5/>",DataStringUtil.getDayTableRow40(co5));
		Control=Control.replace("<r40c6/>",DataStringUtil.getDayTableRow40(co6));
		Control=Control.replace("<r40c7/>",DataStringUtil.getDayTableRow40(co7));
		Control=Control.replace("<r40c8/>",DataStringUtil.getDayTableRow40(co8));
		Control=Control.replace("<r40c9/>",DataStringUtil.getDayTableRow40(co9));
		Control=Control.replace("<r40c10/>",DataStringUtil.getDayTableRow40(co10));
		Control=Control.replace("<r40c11/>",DataStringUtil.getDayTableRow25(co11));
		/**品保部   切折**/
		Control=Control.replace("<r41c3/>",DataStringUtil.getDayTableRow41(co3));
		Control=Control.replace("<r41c4/>",DataStringUtil.getDayTableRow41(co4));
		Control=Control.replace("<r41c5/>",DataStringUtil.getDayTableRow41(co5));
		Control=Control.replace("<r41c6/>",DataStringUtil.getDayTableRow41(co6));
		Control=Control.replace("<r41c7/>",DataStringUtil.getDayTableRow41(co7));
		Control=Control.replace("<r41c8/>",DataStringUtil.getDayTableRow41(co8));
		Control=Control.replace("<r41c9/>",DataStringUtil.getDayTableRow41(co9));
		Control=Control.replace("<r41c10/>",DataStringUtil.getDayTableRow41(co10));
		Control=Control.replace("<r41c11/>",DataStringUtil.getDayTableRow25(co11));
		/**品保部  合計**/
		Control=Control.replace("<r42c3/>","-");
		Control=Control.replace("<r42c4/>","-");
		Control=Control.replace("<r42c5/>","-");
		Control=Control.replace("<r42c6/>","-");
		Control=Control.replace("<r42c7/>","-");
		Control=Control.replace("<r42c8/>","-");
		Control=Control.replace("<r42c9/>","-");
		Control=Control.replace("<r42c10/>","-");
		/**印務部  凸版,柔印,絲網**/
		Control=Control.replace("<r43c3/>",DataStringUtil.getDayTableRow43(co3));
		Control=Control.replace("<r43c4/>",DataStringUtil.getDayTableRow43(co4));
		Control=Control.replace("<r43c5/>",DataStringUtil.getDayTableRow43(co5));
		Control=Control.replace("<r43c6/>",DataStringUtil.getDayTableRow43(co6));
		Control=Control.replace("<r43c7/>",DataStringUtil.getDayTableRow43(co7));
		Control=Control.replace("<r43c8/>",DataStringUtil.getDayTableRow43(co8));
		Control=Control.replace("<r43c9/>",DataStringUtil.getDayTableRow43(co9));
		Control=Control.replace("<r43c10/>",DataStringUtil.getDayTableRow43(co10));
		Control=Control.replace("<r45c11/>",DataStringUtil.getDayTableRow25(co11));
		/**印務部 後道**/
		Control=Control.replace("<r44c3/>",DataStringUtil.getDayTableRow44(co3));
		Control=Control.replace("<r44c4/>",DataStringUtil.getDayTableRow44(co4));
		Control=Control.replace("<r44c5/>",DataStringUtil.getDayTableRow44(co5));
		Control=Control.replace("<r44c6/>",DataStringUtil.getDayTableRow44(co6));
		Control=Control.replace("<r44c7/>",DataStringUtil.getDayTableRow44(co7));
		Control=Control.replace("<r44c8/>",DataStringUtil.getDayTableRow44(co8));
		Control=Control.replace("<r44c9/>",DataStringUtil.getDayTableRow44(co9));
		Control=Control.replace("<r44c10/>",DataStringUtil.getDayTableRow44(co10));
		Control=Control.replace("<r45c11/>",DataStringUtil.getDayTableRow25(co11));
		/**印務部   平板**/
		Control=Control.replace("<r45c3/>",DataStringUtil.getDayTableRow45(co3));
		Control=Control.replace("<r45c4/>",DataStringUtil.getDayTableRow45(co4));
		Control=Control.replace("<r45c5/>",DataStringUtil.getDayTableRow45(co5));
		Control=Control.replace("<r45c6/>",DataStringUtil.getDayTableRow45(co6));
		Control=Control.replace("<r45c7/>",DataStringUtil.getDayTableRow45(co7));
		Control=Control.replace("<r45c8/>",DataStringUtil.getDayTableRow45(co8));
		Control=Control.replace("<r45c9/>",DataStringUtil.getDayTableRow45(co9));
		Control=Control.replace("<r45c10/>",DataStringUtil.getDayTableRow45(co10));
		Control=Control.replace("<r45c11/>",DataStringUtil.getDayTableRow25(co11));
		/**印務部  合計**/
		Control=Control.replace("<r46c3/>","-");
		Control=Control.replace("<r46c4/>","-");
		Control=Control.replace("<r46c5/>","-");
		Control=Control.replace("<r46c6/>","-");
		Control=Control.replace("<r46c7/>","-");
		Control=Control.replace("<r46c8/>","-");
		Control=Control.replace("<r46c9/>","-");
		Control=Control.replace("<r46c10/>","-");
		Control=Control.replace("<r46c11/>","-");
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
