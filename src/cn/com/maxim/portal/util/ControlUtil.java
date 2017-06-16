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
import cn.com.maxim.portal.attendan.ro.empLateEarlyRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceDayRO;
import cn.com.maxim.portal.attendan.vo.calendarVO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.bean.dayTableMoble;
import cn.com.maxim.portal.bean.dayTableRowMoble;
import cn.com.maxim.portal.key.attendanceDayKeyConsts;
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
	
	public static String drawCustomSelectShared( String Name,ArrayList Options, String SelectedOption)
	{

		APCustomSelect = new WebSelect();
		APCustomSelect.setClass("populate select2_category form-control");
		APCustomSelect.setID(Name);
		APCustomSelect.setName(Name);
		
		for(int i=0;i<Options.size();i++){
			Hashtable ht=(Hashtable)Options.get(i);
			APCustomSelect.addOption((String)ht.get("value"),(String) ht.get("text"));
		}
		
		APCustomSelect.addOption("0", "未選擇");
		
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

		
		String sql = "SELECT " + (isDistinct ? "DISTINCT " : " ") + valueField + 
			      " , " + DisplayField + " FROM " + tableName;
			    if (whereSql != null) {
			      sql = sql + " WHERE " + whereSql;
			    }
			    if ((GroupField != null))
			    {
			    	 sql = sql + " ORDER BY " + GroupField;
			    }
			   // System.out.println("drawChosenSelect sql : "+sql);
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

		
		
			   // System.out.println("drawChosenSelect sql : "+sql);
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
	 */
	public static String drawAccordions(Connection con,lateOutEarlyVO eaVo)
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
		int Latetimes=Integer.valueOf(leoLate.get(0).getLATETIMES());
		int LateHour=Integer.valueOf(leoLate.get(0).getHOUR());
		int Earlytimes=Integer.valueOf(leoEarly.get(0).getLATETIMES());
		int EarlyHour=Integer.valueOf(leoEarly.get(0).getHOUR());
		int NotWork=0;
		logger.info("1 Latetimes : "+Latetimes);
		logger.info("1 LateHour : "+LateHour);
		logger.info("1 Earlytimes : "+Earlytimes);
		logger.info("1 EarlyHour : "+EarlyHour);
		logger.info("1 NotWork : "+NotWork);
	
		for(int  i=1;i<32;i++){
			//遲到
			String LateWT =leoLate.get(0).getWT(i);
			String EarlyWT =leoEarly.get(0).getWT(i);
		
			if (LateWT.equals("00:00") & (! leoEarly.get(0).getWT(i).equals("00:00"))){
				
				Latetimes=Latetimes-1;
				LateHour=LateHour-8;
				NotWork=NotWork+1;
				logger.info(" count "+i +" 遲到 LateWT : "+LateWT);
				logger.info(" count "+i+" 遲到 EarlyWT : "+EarlyWT);
				logger.info(" count "+i+" 遲到 Latetimes : "+Latetimes);
				logger.info(" count "+i+" 遲到 LateHour : "+LateHour);
				logger.info(" count "+i+" 遲到 NotWork : "+NotWork);
			}
			if (EarlyWT.equals("00:00") &(! LateWT.equals("00:00"))){
				
				Earlytimes=Earlytimes-1;
				EarlyHour=EarlyHour-8;
				NotWork=NotWork+1;
				logger.info(" count "+i+"早退  LateWT : "+LateWT);
				logger.info(" count "+i+"早退  EarlyWT : "+EarlyWT);
				logger.info(" count "+i+"早退  Earlytimes : "+Earlytimes);
				logger.info(" count "+i+"早退 EarlyHour : "+EarlyHour);
				logger.info(" count "+i+"早退 NotWork : "+NotWork);
			}
		
		
		}
		
		logger.info("2 Latetimes : "+Latetimes);
		logger.info("2 LateHour : "+LateHour);
		logger.info("2 Earlytimes : "+Earlytimes);
		logger.info("2 EarlyHour : "+EarlyHour);
		logger.info("2 NotWork : "+NotWork);
		
		if(Latetimes<0){
			Latetimes=0;
		}
		if(LateHour<0){
			LateHour=0;
		}
		if(Earlytimes<0){
			Earlytimes=0;
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
			
			
			
			//遲到
			if(leoLate.size()>0){
				accordions=accordions.replace("<NotWork/>", String.valueOf(NotWork));
				accordions=accordions.replace("<LateTimes/>", String.valueOf(Latetimes));
				accordions=accordions.replace("<LateHour/>", String.valueOf(LateHour));
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
				accordions=accordions.replace("<EarlyTimes/>", String.valueOf(Earlytimes));
				accordions=accordions.replace("<EarlyHour/>",  String.valueOf(EarlyHour));
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
	 * @throws Exception 
	 */
	public static String drawAttendanceDayTable(Connection con,leaveCardVO lcVo) throws Exception
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
		dayTableRowMoble dr=new dayTableRowMoble();
		
		
		
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
		dt.setCo12(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,attendanceDayKeyConsts.holiayType5), ra));
		/**今天請假人數Data**/
		dt.setCo13(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,attendanceDayKeyConsts.holiayTypeAll), ra));
		/**今天年假人數Data**/
		dt.setCo14(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,attendanceDayKeyConsts.holiayType8), ra));
		/**今天出差人數Data**/
		dt.setCo15(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,attendanceDayKeyConsts.holiayType8), ra));
		/**今天公傷人數Data**/
		dt.setCo16(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,attendanceDayKeyConsts.holiayType3), ra));
		/**今天曠工或未請假人數Data**/
		dt.setCo17(DBUtil.queryForeignCadres(con, SqlUtil.getVnAbsenteeismPeople(lcVo), ra));
		/**今天周六排休人數Data**/
		dt.setCo18(DBUtil.queryForeignCadres(con, SqlUtil.getVnHolidayPeople(lcVo,attendanceDayKeyConsts.holiayType7), ra));
		/**新人報道人數Data**/
		dt.setCo19(DBUtil.queryForeignCadres(con, SqlUtil.getVnInDatePeople(lcVo), ra));
		/**調動人數Data**/
		dt.setCo20(DBUtil.queryForeignCadres(con, SqlUtil.getDayTransfer(lcVo), ra));
		/**離職人數Data**/
		dt.setCo21(DBUtil.queryForeignCadres(con, SqlUtil.getvnNoLeavePeople(lcVo), ra));
		/**離職率Data**/
		dt=DataStringUtil.turnoverRate(dt,dr);
	
		
		/**1年以下Data**/
		dt.setYco1(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(lcVo,sqlConsts.sql_vnLessThanOneYear), "yco")));
		/**1年~2年Data**/
		dt.setYco2(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(lcVo,sqlConsts.sql_vnOneToTwoYears), "yco")));
		/**2年~3年Data**/
		dt.setYco3(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(lcVo,sqlConsts.sql_vnTwoToThreeYears), "yco")));
		/**3年以上Data**/
		dt.setYco4(Integer.valueOf(DBUtil.queryDBField(con, SqlUtil.getVnLessYear(lcVo,sqlConsts.sql_vnLessThanOneYear), "yco")));
		
		
	
		Hashtable ht=new Hashtable();
		dr.setHt(ht);
		
		/**數據行**/
		String[] updateDayRows=attendanceDayKeyConsts.rowS.split(",");
		for(int i=0;i<updateDayRows.length;i++){
			 Control=DataStringUtil.updateDayTableRow(Control,updateDayRows[i],attendanceDayKeyConsts.colStart,attendanceDayKeyConsts.colEnd,dt, dr);
		}
		/**統計行**/
		String[] replaceAddRows=attendanceDayKeyConsts.maxRowS.split(",");
		for(int i=0;i<replaceAddRows.length;i++){
			String[] values=replaceAddRows[i].split("#");
			 Control=DataStringUtil.replaceAddUpDayTableRow(Control,values[0],attendanceDayKeyConsts.colStart,
					 attendanceDayKeyConsts.colEnd, values,dr);
		}

		/** 合計**/
		 String[] addRow=attendanceDayKeyConsts.rep_AttendanceDayAddRow.split(",");
		 Control=DataStringUtil.updateMaxAddUpTableRow(Control,"47",attendanceDayKeyConsts.colStart,attendanceDayKeyConsts.colEnd,addRow,dr);
		 
		/**存入db**/
		String[] allRowS =attendanceDayKeyConsts.allRowS.split(",");
		DBUtil.saveRepAttendanceDay(con,dr,lcVo,allRowS);
		 
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
		 /** 越籍年資分布 存入db**/
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
	
	
}
