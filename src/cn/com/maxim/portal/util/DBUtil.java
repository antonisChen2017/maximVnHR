package cn.com.maxim.portal.util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.alibaba.druid.util.StringUtils;

import cn.com.maxim.DB.DBManager;
import cn.com.maxim.portal.attendan.ro.CSRepoRO;
import cn.com.maxim.portal.attendan.ro.configRO;
import cn.com.maxim.portal.attendan.ro.dayAttendanceExcelRO;
import cn.com.maxim.portal.attendan.ro.dayAttendanceRO;
import cn.com.maxim.portal.attendan.ro.dayPlantRO;
import cn.com.maxim.portal.attendan.ro.dayReportRO;
import cn.com.maxim.portal.attendan.ro.editProcessRO;
import cn.com.maxim.portal.attendan.ro.empLateEarlyRO;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.ro.empnumRO;
import cn.com.maxim.portal.attendan.ro.exLeaveCardCRO;
import cn.com.maxim.portal.attendan.ro.exLeaveCardRO;
import cn.com.maxim.portal.attendan.ro.exOvertimeRO;
import cn.com.maxim.portal.attendan.ro.leaveEmailListRO;
import cn.com.maxim.portal.attendan.ro.overEmailListRO;
import cn.com.maxim.portal.attendan.ro.processCOUserRO;
import cn.com.maxim.portal.attendan.ro.processCheckRO;
import cn.com.maxim.portal.attendan.ro.processIDUserRO;
import cn.com.maxim.portal.attendan.ro.processUserRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceDayRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.ro.repDailyRO;
import cn.com.maxim.portal.attendan.ro.supervisorRO;
import cn.com.maxim.portal.attendan.ro.workDateRO;
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;
import cn.com.maxim.portal.attendan.vo.editProcessVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.attendan.wo.EmailWO;
import cn.com.maxim.portal.attendan.wo.dayAttendanceWO;
import cn.com.maxim.portal.attendan.wo.monthReportNoteWO;
import cn.com.maxim.portal.attendan.wo.monthSumDataWo;
import cn.com.maxim.portal.bean.dayTableMoble;
import cn.com.maxim.portal.bean.repAttendanceDayBean;
import cn.com.maxim.portal.dao.leaveCardDAO;
import cn.com.maxim.portal.dao.overTimeDAO;
import cn.com.maxim.portal.leat.rowItem;
import cn.com.maxim.potral.consts.keyConts;
import cn.com.maxim.potral.consts.sqlConsts;

public class DBUtil
{

	public static String queryDBID(Connection con, String sql)
	{
		return queryDBField(con, sql, "ID");
	}
	/**
	 * 人事系統資料庫連線
	 * @return
	 * @throws Exception
	 */
	public static Connection getHRCon() throws Exception
	{
	    	SAXBuilder xmlBuilder = new SAXBuilder();
		Document doc = xmlBuilder.build(new File("D:\\mxportal\\script\\dba.xml"));
		DBManager dba = new DBManager(doc);
		Connection con=dba.getConnection("HR");
		return con;
	}
	/**
	 * 底層Portal資料庫連線
	 * @return
	 * @throws Exception
	 */
	public static Connection getVHCon() throws Exception
	{
	    	SAXBuilder xmlBuilder = new SAXBuilder();
		Document doc = xmlBuilder.build(new File("D:\\mxportal\\script\\dba.xml"));
		DBManager dba = new DBManager(doc);
		Connection con=dba.getConnection("VH");
		return con;
	}
	
	/**
	 * 取得指定欄位資料
	 * 
	 * @param con
	 * @param sql
	 * @param Field
	 * @return
	 */
	public static String queryDBField(Connection con, String sql, String Field)
	{

		String result = "";
		PreparedStatement STMT = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			if (rs.next())
			{
				result = rs.getString(Field);
			}
		}
		catch (SQLException error)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return result;
	}

	/**
	 * 存入main表
	 * 
	 * @param SearchDepartmen
	 * @param queryDate
	 * @param addTime
	 * @param SearchEmployeeNo
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static String addOverM(String SearchDepartmen, String queryDate, Connection con)
	{

		String MID = "";
		if ((SearchDepartmen == null) || (queryDate == null))
		{
			return "";
		}
		try
		{
			PreparedStatement ps = con.prepareStatement(SqlUtil.setOvertimeM());
			ps.setString(1, SearchDepartmen);
			ps.setString(2, queryDate);
			ps.execute();
			MID = DBUtil.queryDBID(con, SqlUtil.getOvertimeID(SearchDepartmen, queryDate));
		}
		catch (SQLException error)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}

		return MID;
	}
	/**
	 * 更新加班母表
	 * @param newTotalTime
	 * @param ID
	 * @param con
	 * @return
	 */
	public static boolean addTimeOverM(String newTotalTime, String ID, Connection con)
	{
		boolean flag = false;
		if ((newTotalTime == null) || (ID == null))
		{
			return false;
		}
		try
		{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SqlUtil.setTotalTimeM(newTotalTime, ID));
			stmt.close();

			flag = true;
		}
		catch (Exception Exception)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag = false;
		}

		return flag;
	}

	/**
	 * 存入子表
	 * 
	 * @param MID
	 * @param SearchUnit
	 * @param SearchReasons
	 * @param queryDate
	 * @param startTimeHh
	 * @param startTimemm
	 * @param endTimeHh
	 * @param endTimemm
	 * @param addTime
	 * @param SearchEmployeeNo
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static String addOverS(String MID, overTimeVO otVo, Connection con,editProcessRO ePro)
	{
		String result = "x";
		if ((MID == null) || (otVo.getSearchEmployeeNo() == null))
		{
			return "x";
		}
		try
		{

			PreparedStatement ps = con.prepareStatement(SqlUtil.setOvertimeS(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, MID);
			ps.setString(2, otVo.getSearchEmployeeNo());
			ps.setString(3, otVo.getAddTime());
			ps.setString(4, otVo.getQueryDate() + " " + otVo.getStartTimeHh() + ":" + otVo.getStartTimemm());
			ps.setString(5, otVo.getQueryDate() + " " + otVo.getEndTimeHh() + ":" + otVo.getEndTimemm());
			ps.setString(6, otVo.getSearchReasons());
			ps.setString(7, otVo.getSearchUnit());
			ps.setString(8, otVo.getNote());
			ps.setString(9, otVo.getStatus());
			ps.setString(10, otVo.getUserReason());
			ps.setString(11, otVo.getSubmitDate());
			ps.setString(12, otVo.getOverTimeClass());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
			{
				result = rs.getString("ID");
			}
			// System.out.println("SID result : "+result);
		}
		catch (Exception Exception)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			return "x";
		}

		return result;
	}

	
	/**
	 * 超時加班存入子表
	 * 
	 * @param MID
	 * @param SearchUnit
	 * @param SearchReasons
	 * @param queryDate
	 * @param SearchEmployeeNo
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static String addExceedOverS(String MID, overTimeVO otVo, Connection con,editProcessRO ePro)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String result = "x";
		if ((MID == null) || (otVo.getSearchEmployeeNo() == null))
		{
			return "x";
		}
		try
		{
		    //超時改存到另一張表
		    result=UUIDUtil.generateShortUuid();
		    logger.info("SqlUtil.setExceedOvertimeS() :"+SqlUtil.insterSecret(result,MID,otVo,ePro));
		    result= DBUtil.queryDBField(con,SqlUtil.insterSecret(result,MID,otVo,ePro),"ID");
		}
		catch (Exception Exception)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			return "x";
		}

		return result;
	}
	
	public static String getTotalTime(Connection con, String sql)
	{
		return queryDBField(con, sql, "TOTAL_TIME");
	}

	public static String getStatus(Connection con, String sql)
	{
		return queryDBField(con, sql, "STATUS");
	}

	public static String selectDBDepartmentID(Connection con, String UserEmployeeNo)
	{

		String result = "";
		PreparedStatement STMT = null;
		try
		{
			STMT = con.prepareStatement("select ID from VN_DEPARTMENT where DEPARTMENT =N'" + UserEmployeeNo + "'");
			ResultSet rs = STMT.executeQuery();
			if (rs.next())
			{
				result = rs.getString("ID");
			}
		}
		catch (SQLException error)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return result;
	}

	

	/**
	 * 共用刪除
	 * 
	 * @param ID
	 * @param con
	 * @return
	 */
	public static boolean delDBTableRow(String Sql, Connection con)
	{
		boolean flag = false;
		if (Sql == null)
		{
			return false;
		}
		try
		{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(Sql);
			stmt.close();
			flag = true;
		}
		catch (Exception Exception)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag = false;
		}

		return flag;
	}
   /**
    * 更新加班狀態
    * @param Status
    * @param ID
    * @param con
    * @return
    */
	public static boolean updateTimeOverMStatus(String Status, String ID, Connection con)
	{
		boolean flag = false;
		if ((Status == null) || (ID == null))
		{
			return false;
		}
		try
		{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SqlUtil.setStatusM(Status, ID));
			stmt.close();

			flag = true;
		}
		catch (Exception Exception)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag = false;
		}

		return flag;
	}

	public static String getEmp2(Connection con, String sql)
	{
		return queryDBField(con, sql, "EMP_NO1");
	}

	/**
	 * 多天加班申請單自動切分
	 * @param otVo
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public static String saveMaxOvertime(overTimeVO otVo, Connection con) throws Exception
	{
	    	Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String msg = "",re="";
		String Mode="0",t1="",t2="";
		
		if(otVo.getQueryDate().equals(otVo.getQueryDateTwo())){
		    /**同一天**/
		    t1=otVo.getQueryDate()+" "+otVo.getStartTimeHh()+":"+otVo.getStartTimemm();
		    t2=otVo.getQueryDate()+" "+otVo.getEndTimeHh()+":"+otVo.getEndTimemm();
		    otVo.setAddTime(String.valueOf(DateUtil.dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h")));         
		    msg =saveOvertime(otVo,con);
		    String[] mgs=msg.split("#");	  
		    re = mgs[0];
		}else{
		    /**不同天**/
        		    List<String> dateList =  DateUtil.getDatesBetweenTwoDate(otVo.getQueryDate(),otVo.getQueryDateTwo());
        		    /**情況連續兩天長時間加班得拆兩張1. 開始到第一天晚上12點  2. 第二天0點到結束時間**/
        		    Mode= overTimeDAO.OverTimeCheckTwoDay(dateList,otVo);
        		    if(Mode.equals("0")){
        		    /**情況多天各幾小時可以用以下流程**/
                		    for(int i=0;i<dateList.size();i++){
                			  otVo.setQueryDate(dateList.get(i));
                			  t1=otVo.getQueryDate()+" "+otVo.getStartTimeHh()+":"+otVo.getStartTimemm();
                			  t2=otVo.getQueryDate()+" "+otVo.getEndTimeHh()+":"+otVo.getEndTimemm();
                			  otVo.setAddTime(String.valueOf(DateUtil.dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h")));       
                			 msg =saveOvertime(otVo,con);
                			 String[] mgs=msg.split("#");
                			 if(mgs[1].equals("x")){
                			     re = mgs[0];
                			     break;
                			 }else{
                			     re = mgs[0];
                			 }
                		    }
        		    }
        		    
        		    if(Mode.equals("1")){
            		    /**情況多天各幾小時可以用以下流程**/
                    		    for(int i=0;i<dateList.size();i++){
                    			//1.複製一分otVO但時間段要改
                    			if(i==0){
                    			    	 overTimeVO newVo =overTimeDAO.OverTimeCopyVo(otVo);
                    			    	 newVo.setEndTimeHh("23");
                    			         newVo.setEndTimemm("59");
                    			      	 newVo.setQueryDate(dateList.get(i));      
                    			      	 t1=dateList.get(i)+" "+newVo.getStartTimeHh()+":"+newVo.getStartTimemm();
                    			         t2=dateList.get(i)+" 23:59";
                    			      	 newVo.setAddTime(String.valueOf(DateUtil.dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h")));          
                    			      	 logger.info("第一天 VO:"+newVo.toString());
                        			 msg =saveOvertime(newVo,con);
                        			 String[] mgs=msg.split("#");
                        			 if(mgs[1].equals("x")){
                        			     re = mgs[0];
                        			     break;
                        			 }else{
                        			     re = mgs[0];
                        			 }
                    			}
                    			if(i==1){
                    			    	overTimeVO newVo =overTimeDAO.OverTimeCopyVo(otVo);
                    			        newVo.setStartTimeHh("00");
               			                newVo.setStartTimemm("00");
                    			    	newVo.setQueryDate(dateList.get(i));     
                    			 	 t1=dateList.get(i)+" 00:00";
                    			         t2=dateList.get(i)+" "+newVo.getEndTimeHh()+":"+newVo.getEndTimemm();
                    			         newVo.setAddTime(String.valueOf(DateUtil.dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h")));         
                    				 logger.info("t1:"+t1);
                    				 logger.info("t2:"+t2);
                    			    	 msg =saveOvertime(newVo,con);
                        			 String[] mgs=msg.split("#");
                        			 if(mgs[1].equals("x")){
                        			     re = mgs[0];
                        			     break;
                        			 }else{
                        			     re = mgs[0];
                        			 }
                    			}
                    			
                    		    }
            		    }
		}
		
		
		logger.info("saveOvertime  re :"+ re );
		return  re ;
	}
	
	/**
	 * 存入加班申請單
	 * 
	 * @param overTimeVO
	 * @param con
	 * @throws Exception 
	 */
	public static String saveOvertime(overTimeVO otVo, Connection con) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String msg = "";
		String SID = "x";
		editProcessRO ePro=null;
		/**
		 * stop1 查询是否有主表
		 */
		
		String yyyy = otVo.getQueryDate().split("/")[0];
		String mm = otVo.getQueryDate().split("/")[1];
		String flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm), con);
		logger.info("saveOvertime flag:"+flag);
		/**檢查是否同時段已有加班**/
		logger.info("檢查是否同時段已有加班 msg:"+SqlUtil.getOvertimeSCount(otVo));
		String count = DBUtil.queryDBField(con,SqlUtil.getOvertimeSCount(otVo), "count");
		if(Integer.valueOf(count)>0)
		{
			flag="2";
		}
		
		if (flag.equals("1"))
		{
			msg = "超过每月规定加班时数#x";
			logger.info("saveOvertime msg:"+msg);
		}else if (flag.equals("2"))
		{
			msg = "已有相同时间段加班#x";
			logger.info("saveOvertime msg:"+msg);
		}else{
				String resultID = "";
				resultID = DBUtil.queryDBID(con, SqlUtil.getOvertimeID(otVo.getSearchDepartmen(), otVo.getQueryDate()));
				/** 部門單位角色查詢設定並寫入請假單 **/
				ePro=DBUtil.getOverProcessData(otVo , con);
				
				if (resultID.equals(""))
				{
					/**
					 * stop2無主表寫入主表,然後寫入子表
					 */
					logger.info("stop2無主表寫入主表,然後寫入子表");
					resultID = DBUtil.addOverM(otVo.getSearchDepartmen(), otVo.getQueryDate(), con);
					logger.info("saveOvertime resultID:"+resultID);
					if (!resultID.equals(""))
					{
		
						otVo.setStatus("S");
						SID = SqlUtil.insterOvertimeS(resultID, otVo, con,ePro);
						/**
						 * stop3.2 查此人是否本月超過規定时间,有則寫入超時
						 */
						 yyyy = otVo.getQueryDate().split("/")[0];
						 mm = otVo.getQueryDate().split("/")[1];
						
						//超過當月時數
						 flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm), con);
						if (flag.equals("1"))
						{
							
							if (otVo.isOverTimeSave())
							{
								updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
								msg = "已新增加班申请单#o";
							}
							else
							{
								    DBUtil.delDBTableRow(SqlUtil.delOvertimeM(resultID), con);
									DBUtil.delDBTableRow(SqlUtil.delOvertimeS(SID), con);
									msg = "超过每月规定加班时数#x";
							
							}
						}
						else
						{
							updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
							msg = "已新增加班申请单#o";
						}
		
					}
		
				}
				else
				{
					logger.info("stop3.1 有主表寫入子表");
					/**
					 * stop3.1 有主表寫入子表
					 */
					otVo.setStatus("S");
					SID = SqlUtil.insterOvertimeS(resultID, otVo, con,ePro);
					/**
					 * stop3.2 查此人是否本月超過規定时间,有則寫入超時
					 */
					yyyy = otVo.getQueryDate().split("/")[0];
					mm = otVo.getQueryDate().split("/")[1];
					// System.out.println("查此人是否本月超過規定时间 sql:
					//超過當月時數
					logger.info("getAPPhoursflag :"+SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm));
					flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm), con);
		
					/**
					 * stop3.2 查出來是否超時 寫入此條紀錄
					 */
		
					if (flag.equals("1"))
					{
						if (otVo.isOverTimeSave())
						{
						    
							updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
							msg = "已新增加班申请单#o";
						}
						else
						{
						
						
							    DBUtil.delDBTableRow(SqlUtil.delOvertimeS(SID), con);
								msg = "超过每月规定加班时数#x";
							
						}
					}
					else
					{
						updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
						msg = "已新增加班申请单#o";
					}
		
				}
		}
		logger.info("saveOvertime msg:"+msg);
		return msg;

	}
	
	
	
	/**
	 * 多天超時加班申請單自動切分
	 * @param otVo
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public static String saveExceedMaxOverTime(overTimeVO otVo, Connection con) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String msg = "",re="";
		String Mode="0",t1="",t2="";
		
		if(otVo.getQueryDate().equals(otVo.getQueryDateTwo())){
		    /**同一天**/
		    t1=otVo.getQueryDate()+" "+otVo.getStartTimeHh()+":"+otVo.getStartTimemm();
		    t2=otVo.getQueryDate()+" "+otVo.getEndTimeHh()+":"+otVo.getEndTimemm();
		    otVo.setAddTime(String.valueOf(DateUtil.dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h")));         
		    msg =saveExceedOvertime(otVo,con);
		    String[] mgs=msg.split("#");	  
		    re = mgs[0];
		}else{
		    /**不同天**/
        		    List<String> dateList =  DateUtil.getDatesBetweenTwoDate(otVo.getQueryDate(),otVo.getQueryDateTwo());
        		    /**情況連續兩天長時間加班得拆兩張1. 開始到第一天晚上12點  2. 第二天0點到結束時間**/
        		    Mode= overTimeDAO.OverTimeCheckTwoDay(dateList,otVo);
        		    if(Mode.equals("0")){
        		    /**情況多天各幾小時可以用以下流程**/
                		    for(int i=0;i<dateList.size();i++){
                			  otVo.setQueryDate(dateList.get(i));
                			  t1=otVo.getQueryDate()+" "+otVo.getStartTimeHh()+":"+otVo.getStartTimemm();
                			  t2=otVo.getQueryDate()+" "+otVo.getEndTimeHh()+":"+otVo.getEndTimemm();
                			  otVo.setAddTime(String.valueOf(DateUtil.dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h")));       
                			 msg =saveExceedOvertime(otVo,con);
                			 String[] mgs=msg.split("#");
                			 if(mgs[1].equals("x")){
                			     re = mgs[0];
                			     break;
                			 }else{
                			     re = mgs[0];
                			 }
                		    }
        		    }
        		    
        		    if(Mode.equals("1")){
            		    /**情況多天各幾小時可以用以下流程**/
                    		    for(int i=0;i<dateList.size();i++){
                    			//1.複製一分otVO但時間段要改
                    			if(i==0){
                    			    	 overTimeVO newVo =overTimeDAO.OverTimeCopyVo(otVo);
                    			    	 newVo.setEndTimeHh("23");
                    			         newVo.setEndTimemm("59");
                    			      	 newVo.setQueryDate(dateList.get(i));      
                    			      	 t1=dateList.get(i)+" "+newVo.getStartTimeHh()+":"+newVo.getStartTimemm();
                    			         t2=dateList.get(i)+" 23:59";
                    			      	 newVo.setAddTime(String.valueOf(DateUtil.dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h")));          
                    			      	 logger.info("第一天 VO:"+newVo.toString());
                        			 msg =saveExceedOvertime(newVo,con);
                        			 String[] mgs=msg.split("#");
                        			 if(mgs[1].equals("x")){
                        			     re = mgs[0];
                        			     break;
                        			 }else{
                        			     re = mgs[0];
                        			 }
                    			}
                    			if(i==1){
                    			    	overTimeVO newVo =overTimeDAO.OverTimeCopyVo(otVo);
                    			        newVo.setStartTimeHh("00");
               			                newVo.setStartTimemm("00");
                    			    	newVo.setQueryDate(dateList.get(i));     
                    			 	 t1=dateList.get(i)+" 00:00";
                    			         t2=dateList.get(i)+" "+newVo.getEndTimeHh()+":"+newVo.getEndTimemm();
                    			         newVo.setAddTime(String.valueOf(DateUtil.dateDiff(t1,t2,"yyyy/MM/dd HH:mm","h")));         
                    				 logger.info("t1:"+t1);
                    				 logger.info("t2:"+t2);
                    			    	 msg =saveExceedOvertime(newVo,con);
                        			 String[] mgs=msg.split("#");
                        			 if(mgs[1].equals("x")){
                        			     re = mgs[0];
                        			     break;
                        			 }else{
                        			     re = mgs[0];
                        			 }
                    			}
                    			
                    		    }
            		    }
		}
		
		
		logger.info("saveOvertime  re :"+ re );
		return  re ;
	}
	/**
	 * 存入超時加班申請單
	 * 
	 * @param overTimeVO
	 * @param con
	 * @throws Exception 
	 */
	public static String saveExceedOvertime(overTimeVO otVo, Connection con) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String msg = "";
		String SID = "x";
		editProcessRO ePro=null;
		/**
		 * stop1 查询是否有主表
		 */
		String resultID = "";
		
		processCOUserRO ru=DBUtil.getCSUSERData(otVo , con);
		otVo.setEP_ID(ru.getID());
		otVo.setDID(ru.getDEPARTMENT());
		otVo.setUID(ru.getUNIT());
		otVo.setSearchDepartmen(ru.getDEPARTMENT());
		//logger.info("Dep:"+otVo.getDID());
	
		logger.info("QueryDate:"+otVo.getQueryDate());
		resultID = DBUtil.queryDBID(con, SqlUtil.getOvertimeID(otVo.getSearchDepartmen(), otVo.getQueryDate()));
		logger.info("queryDBID resultID:"+resultID);
		String yyyy = otVo.getQueryDate().split("/")[0];
		String mm = otVo.getQueryDate().split("/")[1];
	//	logger.info("queryExceedMonthOverTime : "+SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm));
		if (resultID.equals(""))
		{
			/**
			 * stop2無主表寫入主表,然後寫入子表
			 */

			resultID = DBUtil.addOverM(otVo.getDID(), otVo.getQueryDate(), con);
			logger.info("addOverM resultID:"+resultID);
			
			
			if (!resultID.equals(""))
			{

			
				logger.info("addOverS resultID:"+resultID);
				logger.info("addOverS otVo.getEP_ID():"+otVo.getEP_ID());
			
			
				/**
				 * stop3.2 查此人是否本月超過規定时间,有則寫入超時
				 */
			
				//超過當月時數
				//logger.info("超過當月時數 getAPPhoursflag :"+SqlUtil.getMaxahflag(otVo.getEP_ID(), yyyy, mm));
				String flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getMaxahflag(otVo.getEP_ID(), yyyy, mm), con);
				//logger.info("queryExceedMonthOverTime flag:"+flag);
				logger.info("是否有相同時間 getOvertimeSCount :"+SqlUtil.getOvertimeSCount(otVo));
				String count = DBUtil.queryDBField(con,SqlUtil.getOvertimeSCount(otVo), "count");
				logger.info("是否有相同時間 count :"+count);
				
				logger.info("是否有CS相同時間 getCSCount :"+SqlUtil.getCSSavecount(otVo));
				String CScount = DBUtil.queryDBField(con,SqlUtil.getCSSavecount(otVo), "count");
				logger.info("是否有CS相同時間 :"+CScount);
				
				if(Integer.valueOf(count)>0)
				{
					flag="2";
				}
				if(Integer.valueOf(CScount)>0)
				{
					flag="2";
				}
				//logger.info("queryExceedMonthOverTime maxah:"+DBUtil.queryDBField(con, SqlUtil.queryMaxah(otVo.getEP_ID(), yyyy, mm), "maxah"));
				if (flag.equals("2"))
				{
					msg = "已有相同时间段加班#x";
					logger.info("saveExceedOvertime msg:"+msg);
				}
				else  if (flag.equals("1"))
				{
				    
				    
				    /** 部門單位角色查詢設定並寫入請假單 **/
					ePro=DBUtil.getCSProcessData(otVo , con,ru);
					SID = DBUtil.addExceedOverS(resultID, otVo, con,ePro);
					//logger.info(" 1 addOverS SID:"+SID);
					///logger.info("1 updateMonthOverTims SqlUtil.updateMonthOverTims(flag, SID):"+SqlUtil.updateMonthOverTims(flag, SID));
					//updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
						msg = "已新增超时加班申请单#o";
				}
				else
				{
					//DBUtil.delDBTableRow(SqlUtil.delOvertimeM(resultID), con);
					//DBUtil.delDBTableRow(SqlUtil.delOvertimeS(SID), con);
					msg = "未超时不能申请#x";
				}

			}

		}
		else
		{

			/**
			 * stop3.1 有主表寫入子表
			 */
			
			logger.info("addOverS resultID:"+resultID);
			logger.info("addOverS otVo.getEP_ID():"+otVo.getEP_ID());
		
			/**
			 * stop3.2 查此人是否本月超過規定时间,有則寫入超時
			 */
			//String yyyy = otVo.getQueryDate().split("/")[0];
			//String mm = otVo.getQueryDate().split("/")[1];
			// System.out.println("查此人是否本月超過規定时间 sql:
			//超過當月時數
			//logger.info("超過當月時數 getMaxahflag :"+SqlUtil.getMaxahflag(otVo.getEP_ID(), yyyy, mm));
			String flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getMaxahflag(otVo.getEP_ID(), yyyy, mm), con);
			//logger.info("queryExceedMonthOverTime flag:"+flag);
			//logger.info("queryExceedMonthOverTime maxah:"+DBUtil.queryDBField(con, SqlUtil.queryMaxah(otVo.getEP_ID(), yyyy, mm), "maxah"));
			/**
			 * stop3.2 查出來是否超時 寫入此條紀錄
			 */
			logger.info("是否有相同時間 getOvertimeSCount :"+SqlUtil.getOvertimeSCount(otVo));
			String count = DBUtil.queryDBField(con,SqlUtil.getOvertimeSCount(otVo), "count");
			logger.info("是否有相同時間 count :"+count);
			logger.info("是否有CS相同時間 getCSCount :"+SqlUtil.getCSSavecount(otVo));
			String CScount = DBUtil.queryDBField(con,SqlUtil.getCSSavecount(otVo), "count");
			logger.info("是否有CS相同時間 :"+CScount);
			
			if(Integer.valueOf(count)>0)
			{
				flag="2";
			}
			if(Integer.valueOf(CScount)>0)
			{
				flag="2";
			}
			//logger.info("queryExceedMonthOverTime maxah:"+DBUtil.queryDBField(con, SqlUtil.queryMaxah(otVo.getEP_ID(), yyyy, mm), "maxah"));
			if (flag.equals("2"))
			{
				msg = "已有相同时间段加班#x";
				logger.info("saveExceedOvertime msg:"+msg);
			}
			else if (flag.equals("1"))
			{
			    	/** 部門單位角色查詢設定並寫入請假單 **/
				ePro=DBUtil.getCSProcessData(otVo , con,ru);
				SID = DBUtil.addExceedOverS(resultID, otVo, con,ePro);
				//logger.info("2 addOverS SID:"+SID);
				//logger.info("2 updateMonthOverTims SqlUtil.updateMonthOverTims(flag, SID):"+SqlUtil.updateMonthOverTims(flag, SID));
				//updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
				msg = "已新增超时加班申请单#o";
			}
			else
			{
			       //DBUtil.delDBTableRow(SqlUtil.delOvertimeS(SID), con);
				msg = "未超时不能申请#x";
			}

		}

		return msg;

	}
	
	
	
	/**
	 * 每月加班時數
	 * @param sql
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static String queryExceedMonthOverTime(String sql, Connection con) throws SQLException
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		logger.info("ExceedMonthOverTime sql  : "+sql);
		return queryDBField(con, sql, "flag");
	}

	/**
	 * UPDATE 共用
	 * 
	 * @param Sql
	 * @param con
	 * @return
	 */
	public static boolean updateSql(String Sql, Connection con)
	{
		boolean flag = false;
		if ((Sql == null))
		{
			return flag;
		}
		try
		{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(Sql);
			stmt.close();

			flag = true;
		}
		catch (Exception Exception)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag = false;
		}

		return flag;
	}
	
	/**更新加班單**/
	public static boolean updateTimeOverSStatus(overTimeVO otVo, Connection con)
	{
	    
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag = false;
		if ((otVo.getStatus() == null) || (otVo.getRowID() == null))
		{
			return false;
		}
		try
		{
			Statement stmt = con.createStatement();
			logger.info("SqlUtil.setStatusS : "+SqlUtil.setStatusS(otVo));
			stmt.executeUpdate(SqlUtil.setStatusS(otVo));
			stmt.close();

			flag = true;
		}
		catch (Exception Exception)
		{
		
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag = false;
		}

		return flag;
	}
	
	
	/**更新CS加班單**/
	public static boolean updateCSstatus(overTimeVO otVo, Connection con)
	{
	    
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag = false;
		if ((otVo.getStatus() == null) || (otVo.getRowID() == null))
		{
			return false;
		}
		try
		{
			Statement stmt = con.createStatement();
			logger.info("SqlUtil.updateCSStatus : "+SqlUtil.updateCSStatus(otVo));
			stmt.executeUpdate(SqlUtil.updateCSStatus(otVo));
			stmt.close();

			flag = true;
		}
		catch (Exception Exception)
		{
		
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag = false;
		}

		return flag;
	}
	
	

	/**
	 * 存入請假卡
	 * 
	 * @param overTimeVO
	 * @param con
	 * @throws SQLException
	 */
	public static String saveLeaveCard(leaveCardVO lcVo, Connection con) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String result = "x", WorkFDate = "",WorkEDate;
		editProcessRO ePro=null;
		
		/**檢查有無打卡**/
		boolean checkCade=leaveCardDAO.checkCadeTime(lcVo,con);
		
		
		if ((lcVo.getSearchEmployeeNo() == null))
		{
			return "x";
		}
		else
		{

			
	
			if (lcVo.getDayCount().equals("0.0") && lcVo.getHourCount().equals("0"))
			{
				return "v";
			}
			if (lcVo.getDayCount().equals("0") && lcVo.getHourCount().equals("0"))
			{
				return "v";
			}
			/** 不能已有打卡时间 **/
			/** stop1 同一天 **/
			if (lcVo.getStartLeaveDate().equals(lcVo.getEndLeaveDate()))
			{
				// 同一天
			    	//logger.info(" 同一天 SqlUtil.getOnlyWorkTime(lcVo) "+SqlUtil.getOnlyWorkTime(lcVo));
			    	//WorkEDate = queryDBField(con, SqlUtil.getOnlyWorkTime(lcVo), "WorkEDate");
			 
				if (checkCade)
				{
					logger.info("不能已有打卡时间 WorkFDate "+WorkFDate);
					return "o";
				}

				/** 已請假不能再請 **/
			
				WorkFDate = queryDBField(con, SqlUtil.getOnlyLeaveTime(lcVo), "STARTLEAVEDATE");
			
				if (!WorkFDate.equals(""))
				{
					logger.info("不能已請假 WorkFDate "+WorkFDate);
					return "w";
				}
				float  dayCount=Float.parseFloat(lcVo.getDayCount());
				if(dayCount>1){
					logger.info("請假天數不正確 dayCount "+dayCount);
					return "d";
				}
				
			}
			else
			{
				// 不同天
				//leaveCardVO WorkFlcVo = new leaveCardVO();
				//WorkFlcVo.setStartLeaveDate(lcVo.getStartLeaveDate());
				//WorkFlcVo.setStartLeaveTime(lcVo.getStartLeaveTime());
				//WorkFlcVo.setStartLeaveMinute(lcVo.getEndLeaveMinute());
				//WorkFlcVo.setEndLeaveDate(lcVo.getStartLeaveDate());
				//WorkFlcVo.setEndLeaveTime("23");
				//WorkFlcVo.setEndLeaveMinute("59");
				//WorkFlcVo.setSearchEmployeeNo(lcVo.getSearchEmployeeNo());
			 	//logger.info("不同天  SqlUtil.getOnlyWorkTime(lcVo) "+SqlUtil.getOnlyWorkTime(lcVo));
				//WorkFDate = queryDBField(con, SqlUtil.getOnlyWorkTime(WorkFlcVo), "WorkFDate");
			
				if (checkCade)
				{
					logger.info("不能有打卡时间");
					return "o";
				}

				//leaveCardVO WorkElcVo = new leaveCardVO();
				//WorkElcVo.setStartLeaveDate(lcVo.getEndLeaveDate());
				//WorkElcVo.setStartLeaveTime("05");
				//WorkElcVo.setStartLeaveMinute("30");
				//WorkElcVo.setEndLeaveDate(lcVo.getEndLeaveDate());
				//WorkElcVo.setEndLeaveTime(lcVo.getEndLeaveTime());
				//WorkElcVo.setEndLeaveMinute(lcVo.getEndLeaveTime());
				//WorkElcVo.setSearchEmployeeNo(lcVo.getSearchEmployeeNo());
				// System.out.println("不能已有結束打卡时间 Sql:
				// "+SqlUtil.getOnlyWorkTime(WorkElcVo));
				//WorkFDate = queryDBField(con, SqlUtil.getOnlyWorkTime(WorkElcVo), "WorkFDate");
				// System.out.println("不能已有結束打卡时间 : WorkFDate"+WorkFDate);
				//if (!WorkFDate.equals(""))
				//{
				//	logger.info("不能已有結束打卡时间 : WorkFDate"+WorkFDate);
				//	return "o";
				//}
				/** 已請假不能再請 **/
			
				WorkFDate = queryDBField(con, SqlUtil.getOnlyLeaveTime(lcVo), "STARTLEAVEDATE");
			
				if (!WorkFDate.equals(""))
				{
					logger.info("不同天  已請假不能再請 WorkFDate: "+WorkFDate);
					return "w";
				}
				
				float  dayCount=Float.parseFloat(lcVo.getDayCount());
				if(dayCount<=1){
					logger.info("請假天數不正確 dayCount "+dayCount);
					return "d";
				}
			}

			/** 單天星期天不能請 **/
			
			/** 部門單位角色查詢設定並寫入請假單 **/
			ePro=DBUtil.getProcessData(lcVo , con);
			
		}
		try
		{
			logger.info("寫入請假卡SQL  "+SqlUtil.newSaveLeaveCard(lcVo,ePro));
			DBUtil.updateSql(SqlUtil.newSaveLeaveCard(lcVo,ePro), con);
			result="ok";
		}
		catch (Exception Exception)
		{
			
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			return "x";
		}

		return result;

	}

	
	/**
	 * 存入銷假卡
	 * 
	 * @param overTimeVO
	 * @param con
	 * @throws SQLException
	 */
	public static String saveSalesCard(leaveCardVO lcVo, Connection con) throws Exception
	{

		String result = "x", WorkFDate = "";
		if ((lcVo.getSearchEmployeeNo() == null))
		{
			return "x";
		}
		else
		{

		//	String startTime = lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute() + ":00";
	//		String endTime = lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute() + ":00";
		//	DateUtil dateUtil = new DateUtil();
			/** 只能在請假範圍內申請 **/
			WorkFDate = queryDBField(con, SqlUtil.getOnlyLeaveTime(lcVo), "STARTLEAVEDATE");
			if (!WorkFDate.equals(""))
			{
				try
				{

					PreparedStatement ps = con.prepareStatement(SqlUtil.saveSalesCard(), Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, lcVo.getSearchEmployeeNo());
					ps.setString(2, lcVo.getSearchHoliday());
					ps.setString(3, lcVo.getApplicationDate());
					ps.setString(4, lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute());
					ps.setString(5, lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute());
					ps.setString(6, lcVo.getSearchAgent());
					ps.setString(7, lcVo.getNote());
					ps.setString(8, lcVo.getStatus());
					//ps.setString(9, lcVo.getDayCount());
					ps.execute();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next())
					{
						result = rs.getString("ID");
					}
					// System.out.println("LCID result : "+result);
				}
				catch (Exception Exception)
				{
					Log4jUtil lu = new Log4jUtil();
					Logger logger = lu.initLog4j(DBUtil.class);
					logger.error(vnStringUtil.getExceptionAllinformation(Exception));
					return "x";
				}
			}else{
				return "w";
			}
		}
		

		return result;

	}
	
	
	/**
	 * 由站台設定帳號人名搜尋单位資料
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<employeeUserRO> queryUserList(Connection con, String sql, employeeUserRO er)
	{
		// System.out.println("sql : "+sql);

		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<employeeUserRO> lro = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			lro = (List<employeeUserRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return lro;
	}
	
	
	/**
	 * 加班更新按鈕搜尋資料
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<exLeaveCardRO> queryLeaveCardList(Connection con, String sql, exLeaveCardRO er)
	{
		// System.out.println("sql : "+sql);

		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<exLeaveCardRO> lro = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			lro = (List<exLeaveCardRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return lro;
	}
	
	/**
	 * 搜尋超時加班資料
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<exOvertimeRO> queryExOvertimeList(Connection con, String sql, exOvertimeRO er)
	{
		// System.out.println("sql : "+sql);

		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<exOvertimeRO> lro = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			lro = (List<exOvertimeRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return lro;
	}
	/**
	 * 存入待工明細表
	 * 
	 * @param overTimeVO
	 * @param con
	 * @throws SQLException
	 */
	public static String saveStopWorking(stopWorkVO swVo, Connection con) throws Exception
	{

		String result = "紀錄成功！";
		if ((swVo.getSearchEmployeeNo() == null))
		{
			return "紀錄失敗！";
		}
		try
		{
			//System.out.println("WorkDate : " + swVo.getStartStopWorkDate() + " " + swVo.getStartTimeHhmm() + ":00");
			PreparedStatement ps = con.prepareStatement(SqlUtil.saveStopWorking(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, swVo.getSearchEmployeeNo());
			ps.setString(2, swVo.getAddDay());
			ps.setString(3, swVo.getNote());
			ps.setString(4, swVo.getStartStopWorkDate() + " " + swVo.getStartTimeHh()+":"+swVo.getStartTimemm()+ ":00");
			ps.setString(5, swVo.getEndStopWorkDate() + " "  + swVo.getEndTimeHh()+":"+swVo.getEndTimemm()+ ":00");
			ps.setString(6, swVo.getSearchReasons());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
			{
				// result = rs.getString("ID");
			}
			// System.out.println("LCID result : "+result);
		}
		catch (Exception Exception)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			result = "紀錄失敗！";
		}

		return result;

	}

	/**
	 * 查询打卡資料
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<yearMonthLateRO> queryLateList(Connection con, String sql, yearMonthLateRO er)
	{

		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<yearMonthLateRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<yearMonthLateRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}

	/**
	 * 查询日考勤報表資料
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<repDailyRO> queryDailyList(Connection con, String sql, repDailyRO rd)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<repDailyRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<repDailyRO>) rh.getBean(rs, rd);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}

		return leo;
	}

	/**
	 * 查询月考勤報表資料
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<repAttendanceRO> queryMonthAttendanceExcel(Connection con, String sql, repAttendanceRO rd)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<repAttendanceRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<repAttendanceRO>) rh.getBean(rs, rd);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		for(int i=0;i<leo.size();i++){
		    /**年假/公假 加回正常上班**/
		  //  float fCodex=Float.parseFloat(leo.get(i).getCODEX());
		  //  float fCodeh=Float.parseFloat(leo.get(i).getCODEH());//年假
		  //  float fCodec=Float.parseFloat(leo.get(i).getCODEC());//公假
		 //   fCodex= fCodex+fCodeh;
		//    fCodex= fCodex+fCodec;
		//    leo.get(i).setCODEX(String.valueOf(fCodex));
		    leo.get(i).setNOTE(leo.get(i).getNOTE().replaceAll("<br/>", "\r\n"));
		    logger.info("曠工:"+leo.get(i).getCODEK());
		    /**休假抵掉曠工**/
		    float fCodek=Float.parseFloat(vnStringUtil.changeWString(leo.get(i).getCODEK()));//曠工
		    logger.info("曠工: fCodek"+fCodek);
		    
		    logger.info("事假 changeWString :"+vnStringUtil.changeWString(leo.get(i).getCODEA()));
		    float fCodea=Float.parseFloat(vnStringUtil.changeWString(leo.get(i).getCODEA()));//事假
		    logger.info("事假: fCodea"+fCodea);
		    
		    fCodek=  fCodek-fCodea;
		    float fCodeb=Float.parseFloat(vnStringUtil.changeWString(leo.get(i).getCODEB()));//病假
		    fCodek=  fCodek-fCodeb;
		    float fCoded=Float.parseFloat(vnStringUtil.changeWString(leo.get(i).getCODED()));//婚假
		    fCodek=  fCodek-fCoded;
		    float fCodee=Float.parseFloat(vnStringUtil.changeWString(leo.get(i).getCODEE()));//產假
		    fCodek=  fCodek-fCodee;
		    float fCodef=Float.parseFloat(vnStringUtil.changeWString(leo.get(i).getCODEF()));//喪假
		    fCodek=  fCodek-fCodef;
		    float fCodeW=Float.parseFloat(vnStringUtil.changeWString(leo.get(i).getCODEW()));//待工
		    fCodek=  fCodek-fCodeW;
		    leo.get(i).setCODEK(String.valueOf(fCodek));//曠工
		}
		
		return leo;
	}

	
	
	/**
	 * 查询CS報表資料
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<CSRepoRO> queryCSReportExcel(Connection con, String sql, CSRepoRO rd)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<CSRepoRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<CSRepoRO>) rh.getBean(rs, rd);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		
		
		return leo;
	}
	
	
	
	
	
	/**
	 * 查询遲到早退名單表
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<yearMonthLateRO> queryYmLateList(Connection con, String sql, yearMonthLateRO er)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<yearMonthLateRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<yearMonthLateRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}

	/**
	 * 直接執行SQL 無參數 無返回
	 * 
	 * @param con
	 * @param sql
	 * @return
	 */
	public static String workLateOperationSql(Connection con, String sql)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String MID = "";
	
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();

		}
		catch (Exception Exception)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
		}

		return MID;
	}

	/**
	 * 查询員工遲到早退打卡資料
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<empLateEarlyRO> queryEmpLateEarlyList(Connection con, String sql, empLateEarlyRO lr)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<empLateEarlyRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<empLateEarlyRO>) rh.getBean(rs, lr);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}

	/**
	 * 查询全廠出勤sql
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static List<repAttendanceDayRO> queryForeignCadres(Connection con, String sql, repAttendanceDayRO ra)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		// logger.info("queryForeignCadres sql :"+sql);
		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<repAttendanceDayRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<repAttendanceDayRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}

	/**
	 * 產生全廠日出勤狀況報表EXCEL報表
	 * 
	 * @param con
	 * @param sql
	 * @param ra
	 * @return
	 */
	public static List<dayAttendanceRO> queryExcelAttendanceDay(Connection con, String sql, dayAttendanceRO ra)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<dayAttendanceRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<dayAttendanceRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}
	/**
	 * 產生全廠日出勤狀況報表EXCEL報表
	 * 
	 * @param con
	 * @param sql
	 * @param ra
	 * @return
	 */
	public static List<dayAttendanceExcelRO> queryExcelDay(Connection con, String sql, dayAttendanceExcelRO ra)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<dayAttendanceExcelRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<dayAttendanceExcelRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}
	
	/**
	 * 產生越籍年資分布EXCEL報表
	 * 
	 * @param con
	 * @param sql
	 * @param ra
	 * @return
	 */
	public static List<empnumRO> queryExcelEmpnum(Connection con, String sql, empnumRO ra)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		//logger.info("queryExcelAttendanceDay sql :" + sql);
		String result = "";
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<empnumRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<empnumRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}
	
	

	/**
	 * 將全廠日報表寫入db(行)
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 * @throws ParseException
	 */
	private static void saveRepAttendanceDayRow(Connection con, dayAttendanceWO dw) throws Exception
	{
		updateSql(SqlUtil.saveAttendanceDayRow(dw), con);
	}
	
	/**
	 * 將越籍年資分布表寫入db
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 * @throws Exception 
	 */
	public static void saveEmpnum(Connection con ,dayTableMoble dt,leaveCardVO lcVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		String count = queryDBField(con, SqlUtil.getEmpnum(lcVo.getApplicationDate()), "count");
		if (Integer.valueOf(count) == 0)
		{
			//第一行
			dayAttendanceWO dw1 = new dayAttendanceWO();// 
			dw1.setYMDKEY(lcVo.getApplicationDate());
			dw1.setYMD(lcVo.getApplicationDate());
			dw1.setYEAR(keyConts.empnumOneYear);
			dw1.setEMPNUM(String.valueOf(dt.getYco1()));
			dw1.setPERCENTAGE(NumberUtil.getPercentFormat(dt.getYco1(),dt.getYmax()));
			saveEmpnumRow(con,dw1);
			//第2行
			dayAttendanceWO dw2 = new dayAttendanceWO();// 
			dw2.setYMDKEY(lcVo.getApplicationDate());
			dw2.setYMD("");
			dw2.setYEAR(keyConts.empnumTwoYear);
			dw2.setEMPNUM(String.valueOf(dt.getYco2()));
			dw2.setPERCENTAGE(NumberUtil.getPercentFormat(dt.getYco2(),dt.getYmax()));
			saveEmpnumRow(con,dw2);
			
			//第3行
			dayAttendanceWO dw3 = new dayAttendanceWO();// 
			dw3.setYMDKEY(lcVo.getApplicationDate());
			dw3.setYMD("");
			dw3.setYEAR(keyConts.empnumThreeYear);
			dw3.setEMPNUM(String.valueOf(dt.getYco3()));
			dw3.setPERCENTAGE(NumberUtil.getPercentFormat(dt.getYco3(),dt.getYmax()));
			saveEmpnumRow(con,dw3);		
			
			//第4行
			dayAttendanceWO dw4 = new dayAttendanceWO();// 
			dw4.setYMDKEY(lcVo.getApplicationDate());
			dw4.setYMD("");
			dw4.setYEAR(keyConts.empnumFourYear);
			dw4.setEMPNUM(String.valueOf(dt.getYco4()));
			dw4.setPERCENTAGE(NumberUtil.getPercentFormat(dt.getYco4(),dt.getYmax()));
			saveEmpnumRow(con,dw4);
			
			//第5行
			dayAttendanceWO dw5 = new dayAttendanceWO();// 
			dw5.setYMDKEY(lcVo.getApplicationDate());
			dw5.setYMD("");
			dw5.setYEAR(keyConts.empnumMsg);
			dw5.setEMPNUM(String.valueOf(dt.getYmax()));
			dw5.setPERCENTAGE("");
			saveEmpnumRow(con,dw5);
		}
	}
	
	
	private static void saveEmpnumRow(Connection con, dayAttendanceWO dw) throws Exception{
		updateSql(SqlUtil.saveEmpnumRow(dw), con);
	}
	/**
	 * 如果加班原因填寫欄不為空 且理由與下拉選單不符合 儲存到下拉選單
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static void 	saveReasons(Connection con, overTimeVO otVo) throws Exception{
		if(!otVo.getUserReason().equals("")){
			String count = queryDBField(con, SqlUtil.getReasons(otVo), "count");
			if(Integer.valueOf(count)==0){
				updateSql(SqlUtil.saveReasons(otVo), con);
			}
		}
	}
	
	/**
	 * 檢查有無此人資料 有新增 無更新
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static leaveCardVO updateUnit(Connection con, leaveCardVO lcVo) throws Exception{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		String count=queryDBField(con, SqlUtil.getEmployUnitCount(lcVo.getSearchEmployee()), "count");
		logger.info("getEmployUnitCount  : "+ SqlUtil.getEmployUnitCount(lcVo.getSearchEmployee()));
		logger.info("count  : "+ count);
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
		if(count.equals("0")){
			logger.info("saveEmployUnit  : "+ SqlUtil.saveEmployUnit(lcVo));
			DBUtil.updateSql(SqlUtil.saveEmployUnit(lcVo), con) ;
			String Sql=hu.gethtml(sqlConsts.sql_updateHREmployee);
			Sql=Sql.replace("<UNITID>", lcVo.getSearchUnit());
			Sql=Sql.replace("<EMPID>", lcVo.getSearchEmployee());
			Sql=Sql.replace("<ROLE>", lcVo.getSearchRole());
			logger.info("sql_updateHREmployee Sql : "+ Sql);
			 flag=DBUtil.updateSql(Sql, con) ;
			// flag=true;
		}else{
			String Sql=hu.gethtml(sqlConsts.sql_updateUnit);
			Sql=Sql.replace("<UNITID>", lcVo.getSearchUnit());
			Sql=Sql.replace("<EMPID>", lcVo.getSearchEmployee());
		    	logger.info("update Sql : "+ Sql);
			flag=DBUtil.updateSql(Sql, con) ;
			Sql=hu.gethtml(sqlConsts.sql_updateHREmployee);
			Sql=Sql.replace("<UNITID>", lcVo.getSearchUnit());
			Sql=Sql.replace("<EMPID>", lcVo.getSearchEmployee());
			Sql=Sql.replace("<ROLE>", lcVo.getSearchRole());
			logger.info("sql_updateHREmployee Sql : "+ Sql);
		    flag=DBUtil.updateSql(Sql, con) ;
		}
		if(flag){
			lcVo.setMsg("更新单位成功!");
		}else{
			lcVo.setMsg("更新单位失敗!");
		}
		return lcVo;
		
	}
	/**
	 * 更新員工角色
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static leaveCardVO updateRole(Connection con, leaveCardVO lcVo) throws Exception{
	
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
	
		String Sql=hu.gethtml(sqlConsts.sql_vnUpdateRole);
		Sql=Sql.replace("<ROLE/>", lcVo.getSearchRole());
		Sql=Sql.replace("<EMPID/>", lcVo.getSearchEmployeeNo());
		System.out.println("Update Sql : "+ Sql);
		logger.info("Update Sql : "+ Sql);
		flag=DBUtil.updateSql(Sql, con) ;
		
		if(flag){
			lcVo.setMsg("更新角色成功!");
		}else{
			lcVo.setMsg("更新角色失敗!");
		}
		return lcVo;
		
	}
	/**
	 * 更新員工角色
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static overTimeVO updateExOverTimeC(Connection con, overTimeVO otVo) throws Exception{
	
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
	
		String Sql=hu.gethtml(sqlConsts.sql_exUpdateOverTimes);
		Sql=Sql.replace("<HOURS/>", otVo.getAddTime());
		Sql=Sql.replace("<OVERTIMESTART/>", otVo.getStartQueryDate()+otVo.getStartTimeHh()+":"+otVo.getStartTimemm());
		Sql=Sql.replace("<OVERTIMEEND/>", otVo.getStartQueryDate()+otVo.getEndTimeHh()+":"+otVo.getEndTimemm());
		Sql=Sql.replace("<ID/>", otVo.getRowID());
		logger.info("Update Sql : "+ Sql);
		flag=DBUtil.updateSql(Sql, con) ;
		
		if(flag){
			otVo.setMsg(keyConts.editOK);
		}else{
			otVo.setMsg(keyConts.editNO);
		}
		return otVo;
		
	}
	
	
	/**
	 * 查詢超時人員部分資料
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static overTimeVO queryExOverTimeC(Connection con, overTimeVO otVo) throws Exception{
	
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
	
		String Sql=hu.gethtml(sqlConsts.sql_queryExOverTimeC);
		Sql=Sql.replace("<YEAR/>", otVo.getQueryYearMonth().split("/")[0]);
		Sql=Sql.replace("<MONTH/>", otVo.getQueryYearMonth().split("/")[1]);
		Sql=Sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
	
		logger.info("queryExOverTimeC Sql : "+ Sql);
		exLeaveCardCRO er=new exLeaveCardCRO();
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<exLeaveCardCRO> leo = null;
		try
		{
			STMT = con.prepareStatement(Sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<exLeaveCardCRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
	//	logger.info("queryExOverTimeC getDEPARTMENT : "+  leo.get(0).getDEPARTMENT());
		otVo.setSearchDepartmen( leo.get(0).getDEPARTMENT());
	//	logger.info("queryExOverTimeC getUNIT : "+  leo.get(0).getUNIT());
		otVo.setSearchUnit(  leo.get(0).getUID());
	//	logger.info("queryExOverTimeC getEMPLOYEENO : "+  leo.get(0).getEMPLOYEENO());
		otVo.setSearchEmployeeNo(  leo.get(0).getEMPLOYEENO());
	//	logger.info("queryExOverTimeC getEMPLOYEE : "+  leo.get(0).getEMPLOYEE());
		otVo.setSearchEmployee(  leo.get(0).getEMPLOYEE());
		otVo.setEP_ID(  leo.get(0).getEP_ID());
		otVo.setDID(  leo.get(0).getDID());
		otVo.setUID(  leo.get(0).getUID());
		return otVo;
		
	}
	
	
	
	/**
	 * 查詢超時人員部分資料
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static overTimeVO queryExOverTimeEmp(Connection con, overTimeVO otVo) throws Exception{
	
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
	
		String Sql=hu.gethtml(sqlConsts.sql_queryExOverTimeC);
		Sql=Sql.replace("<YEAR/>", otVo.getQueryYearMonth().split("/")[0]);
		Sql=Sql.replace("<MONTH/>", otVo.getQueryYearMonth().split("/")[1]);
		Sql=Sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
	
		logger.info("queryExOverTimeC Sql : "+ Sql);
		exLeaveCardCRO er=new exLeaveCardCRO();
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<exLeaveCardCRO> leo = null;
		try
		{
			STMT = con.prepareStatement(Sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<exLeaveCardCRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
	//	logger.info("queryExOverTimeC getDEPARTMENT : "+  leo.get(0).getDEPARTMENT());
		otVo.setSearchDepartmen( leo.get(0).getDEPARTMENT());
	//	logger.info("queryExOverTimeC getUNIT : "+  leo.get(0).getUNIT());
		otVo.setSearchUnit(  leo.get(0).getUID());
	//	logger.info("queryExOverTimeC getEMPLOYEENO : "+  leo.get(0).getEMPLOYEENO());
		otVo.setSearchEmployeeNo(  leo.get(0).getEMPLOYEENO());
	//	logger.info("queryExOverTimeC getEMPLOYEE : "+  leo.get(0).getEMPLOYEE());
		otVo.setSearchEmployee(  leo.get(0).getEMPLOYEE());
		otVo.setEP_ID(  leo.get(0).getEP_ID());
		otVo.setDID(  leo.get(0).getDID());
		otVo.setUID(  leo.get(0).getUID());
		return otVo;
		
	}
	
	
	/**
	 * 查詢編輯超時人員部分資料
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static overTimeVO querySecretData(Connection con, overTimeVO otVo) throws Exception{
	
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
	
		String Sql=hu.gethtml(sqlConsts.sql_querySecretData);
		Sql=Sql.replace("<YEAR/>", otVo.getQueryYearMonth().split("/")[0]);
		Sql=Sql.replace("<MONTH/>", otVo.getQueryYearMonth().split("/")[1]);
		Sql=Sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
	
		logger.info("queryExOverTimeC Sql : "+ Sql);
		exLeaveCardCRO er=new exLeaveCardCRO();
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<exLeaveCardCRO> leo = null;
		try
		{
			STMT = con.prepareStatement(Sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<exLeaveCardCRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
	//	logger.info("queryExOverTimeC getDEPARTMENT : "+  leo.get(0).getDEPARTMENT());
		otVo.setSearchDepartmen( leo.get(0).getDEPARTMENT());
	//	logger.info("queryExOverTimeC getUNIT : "+  leo.get(0).getUNIT());
		otVo.setSearchUnit(  leo.get(0).getUID());
	//	logger.info("queryExOverTimeC getEMPLOYEENO : "+  leo.get(0).getEMPLOYEENO());
		otVo.setSearchEmployeeNo(  leo.get(0).getEMPLOYEENO());
	//	logger.info("queryExOverTimeC getEMPLOYEE : "+  leo.get(0).getEMPLOYEE());
		otVo.setSearchEmployee(  leo.get(0).getEMPLOYEE());
		otVo.setEP_ID(  leo.get(0).getEP_ID());
		otVo.setDID(  leo.get(0).getDID());
		otVo.setUID(  leo.get(0).getUID());
		return otVo;
		
	}
	
	
	
	/**
	 * 查詢編輯超時人員編輯行資料
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static overTimeVO querySecretDown(Connection con, overTimeVO otVo) throws Exception{
	
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
	
		String Sql=hu.gethtml(sqlConsts.sql_querySecretData);
		Sql=Sql.replace("<YEAR/>", otVo.getQueryYearMonth().split("/")[0]);
		Sql=Sql.replace("<MONTH/>", otVo.getQueryYearMonth().split("/")[1]);
		Sql=Sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
	
		logger.info("queryExOverTimeC Sql : "+ Sql);
		exLeaveCardCRO er=new exLeaveCardCRO();
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<exLeaveCardCRO> leo = null;
		try
		{
			STMT = con.prepareStatement(Sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<exLeaveCardCRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
	//	logger.info("queryExOverTimeC getDEPARTMENT : "+  leo.get(0).getDEPARTMENT());
		otVo.setSearchDepartmen( leo.get(0).getDEPARTMENT());
	//	logger.info("queryExOverTimeC getUNIT : "+  leo.get(0).getUNIT());
		otVo.setSearchUnit(  leo.get(0).getUID());
	//	logger.info("queryExOverTimeC getEMPLOYEENO : "+  leo.get(0).getEMPLOYEENO());
		otVo.setSearchEmployeeNo(  leo.get(0).getEMPLOYEENO());
	//	logger.info("queryExOverTimeC getEMPLOYEE : "+  leo.get(0).getEMPLOYEE());
		otVo.setSearchEmployee(  leo.get(0).getEMPLOYEE());
		otVo.setEP_ID(  leo.get(0).getEP_ID());
		otVo.setDID(  leo.get(0).getDID());
		otVo.setUID(  leo.get(0).getUID());
		return otVo;
		
	}
	
	/**
	 * 查詢日報表資料並改寫內容
	 * @param con
	 * @param otVo
	 * @throws Exception
	 */
	public static void dayReportAlter(Connection con, leaveCardVO lcVo) {
	
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
	
		String Sql=hu.gethtml(sqlConsts.sql_getDayReportData);
		Sql=Sql.replace("<DAY/>", lcVo.getApplicationDate());

		logger.info("dayReportAlter Sql : "+ Sql);
		dayReportRO er=new dayReportRO();
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<dayReportRO> leo = null;
		try
		{
			STMT = con.prepareStatement(Sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<dayReportRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		String sNotWork="";
		for( int i=0;i< leo.size();i++){
			logger.info("  leo.get(i) "+ leo.get(i));
			/**如果有遲到 減少上班時數 START**/
			double  fAttendance=0;
			if(! leo.get(i).getATTENDANCE().equals("") && leo.get(i).getATTENDANCE()!=null){
				 fAttendance=Float.parseFloat(leo.get(i).getATTENDANCE());
			}
			if( leo.get(i).getBELATE()!=null && !leo.get(i).getBELATE().equals("")  ){
				double  fBelate=Float.parseFloat(leo.get(i).getBELATE());
				// double  fAttendance=Float.parseFloat(leo.get(i).getATTENDANCE());
					double fBelateCount= NumberUtil.getBelate(fBelate);
				 fAttendance=  fAttendance-fBelateCount;
				 if(fAttendance<0){
					 fAttendance=0;
				 }
				 if(fBelate!=0 ){
					 updateSql(SqlUtil.updateDayReportAttendance(lcVo,fAttendance,leo.get(i).getEMPLOYEENO()), con);
				 }
			}
			/**如果有遲到 減少上班時數 END**/
			
			/**夜班紀錄到130 START**/
			
			/**夜班紀錄到130  END**/
			/**周日上班紀錄到200 START**/
			
			/**周日上班紀錄到200  END**/
			/**節日日上班紀錄到300 START**/
			
			/**節日上班紀錄到300  END**/
			
			/**如果有早退 減少上班時數 START
			if( leo.get(i).getEARLY()!=null && !leo.get(i).getEARLY().equals("")  ){
				double  fEarly=Float.parseFloat(leo.get(i).getEARLY());
				// double  fAttendance=Float.parseFloat(leo.get(i).getATTENDANCE());
					double fEarlyCount= NumberUtil.getBelate(fEarly);
				 fAttendance=  fAttendance-fEarlyCount;
				 if(fAttendance<0){
					 fAttendance=0;
				 }
				 if(fEarly!=0 ){
					 updateSql(SqlUtil.updateDayReportAttendance(lcVo,fAttendance,leo.get(i).getEMPLOYEENO()), con);
				 }
			}
			如果有早退 減少上班時數 END**/
			
			/**如果出勤 扣掉曠工 START
			if( leo.get(i).getBELATE()!=null && !leo.get(i).getBELATE().equals("")){
				double  fBelate=Float.parseFloat(leo.get(i).getBELATE());
				 double  fAttendance=Float.parseFloat(leo.get(i).getATTENDANCE());
					double fBelateCount= NumberUtil.getBelate(fBelate);
				 fAttendance=  fAttendance-fBelateCount;
				 if(fAttendance<0){
					 fAttendance=0;
				 }
				 if(fBelate!=0 && fAttendance<8){
					 updateSql(SqlUtil.updateDayReportAttendance(lcVo,fAttendance,leo.get(i).getEMPLOYEENO()), con);
				 }
			}
			如果出勤 扣掉曠工 START**/
			
			/**如果有休假就扣掉曠工 START**/
			//logger.info(" getNOTWORK  "+ leo.get(i).getNOTWORK());
			if(!leo.get(i).getNOTWORK().equals("") || leo.get(i).getNOTWORK()==null){
				 double  dNotWork=Float.parseFloat(leo.get(i).getNOTWORK());
				 /**事假**/
				if( leo.get(i).getHOLIDAYA()!=null && !leo.get(i).getHOLIDAYA().equals("")){
					double  dHolidayA=Float.parseFloat(leo.get(i).getHOLIDAYA());
					dNotWork= dNotWork-dHolidayA;
			//		logger.info("a dNotWork  "+ dNotWork);
					 if(dNotWork<0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
						//logger.info("a dNotWork  "+ dNotWork);
					 if(dHolidayA!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
				 /**病假**/
				if( leo.get(i).getHOLIDAYB()!=null && !leo.get(i).getHOLIDAYB().equals("")){
	
					double  dHolidayB=Float.parseFloat(leo.get(i).getHOLIDAYB());
					dNotWork= dNotWork-dHolidayB;
				//	logger.info(" b dNotWork  "+ dNotWork);
					 if(dNotWork<0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//		logger.info("b dNotWork  "+ dNotWork);
					 if(dHolidayB!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
				 /**工傷假
				logger.info(" getHOLIDAYI  "+ leo.get(i).getHOLIDAYI());
				if( leo.get(i).getHOLIDAYI()!=null && !leo.get(i).getHOLIDAYI().equals("")){
	
					double  dHolidayI=Float.parseFloat(leo.get(i).getHOLIDAYI());
					dNotWork= dNotWork-dHolidayI;
				//	logger.info(" c dNotWork  "+ dNotWork);
					 if(dNotWork<0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//		logger.info("c dNotWork  "+ dNotWork);
					 if(dHolidayI!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
				**/
				 /**婚假**/
				if( leo.get(i).getHOLIDAYD()!=null && !leo.get(i).getHOLIDAYD().equals("")){
	
					double  dHolidayD=Float.parseFloat(leo.get(i).getHOLIDAYD());
					dNotWork= dNotWork-dHolidayD;
					//logger.info("d dNotWork  "+ dNotWork);
					 if(dNotWork<0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//	 logger.info("d dNotWork  "+ dNotWork);
					 if(dHolidayD!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
				 /**產假**/
				if( leo.get(i).getHOLIDAYE()!=null && !leo.get(i).getHOLIDAYE().equals("")){
	
					double  dHolidayE=Float.parseFloat(leo.get(i).getHOLIDAYE());
					dNotWork= dNotWork-dHolidayE;
				//	logger.info("e dNotWork  "+ dNotWork);
					 if(dNotWork<0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//	 logger.info("e dNotWork  "+ dNotWork);
					 if(dHolidayE!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
				 /**喪假**/
				if( leo.get(i).getHOLIDAYF()!=null && !leo.get(i).getHOLIDAYF().equals("")){
	
					double  dHolidayF=Float.parseFloat(leo.get(i).getHOLIDAYF());
					dNotWork= dNotWork-dHolidayF;
				//	logger.info("f dNotWork  "+ dNotWork);
					 if(dNotWork<=0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//		logger.info("f dNotWork  "+ dNotWork);
					 if(dHolidayF!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
				 /**年假**/
				if( leo.get(i).getHOLIDAYH()!=null && !leo.get(i).getHOLIDAYH().equals("")){
	
					double  dHolidayH=Float.parseFloat(leo.get(i).getHOLIDAYH());
					dNotWork= dNotWork-dHolidayH;
				//	logger.info("h dNotWork  "+ String.valueOf(dNotWork));
					 if(dNotWork<=0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//		logger.info("h dNotWork  "+ dNotWork);
					 if(dHolidayH!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
				 /**工傷假
				if( leo.get(i).getHOLIDAYI()!=null && !leo.get(i).getHOLIDAYI().equals("")){
	
					double  dHolidayI=Float.parseFloat(leo.get(i).getHOLIDAYI());
					dNotWork= dNotWork-dHolidayI;
				//	logger.info("h dNotWork  "+ String.valueOf(dNotWork));
					 if(dNotWork<=0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//		logger.info("h dNotWork  "+ dNotWork);
					 if(dHolidayI!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
				**/
				 /**公假**/
				if( leo.get(i).getHOLIDAYO()!=null && !leo.get(i).getHOLIDAYO().equals("")){
	
					double  dHolidayO=Float.parseFloat(leo.get(i).getHOLIDAYO());
					dNotWork= dNotWork-dHolidayO;
				//	logger.info(" b dNotWork  "+ dNotWork);
					 if(dNotWork<0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//		logger.info("b dNotWork  "+ dNotWork);
					 if(dHolidayO!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
			}
			/**如果有休假就扣掉曠工 END**/
		}
		
	}
	
	/**
	 * 取得日報表每日改寫過查詢結果
	 * @param con
	 * @param lcVo
	 * @return
	 */
public static List<dayReportRO> getDayReportMonth(Connection con, leaveCardVO lcVo) {
	
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		boolean flag=false;
		HtmlUtil hu=new HtmlUtil();
	
		String Sql=hu.gethtml(sqlConsts.sql_getDayReportMonth);
		Sql=Sql.replace("<DAY/>", lcVo.getApplicationDate());

		logger.info("sql_getDayReportMonth Sql : "+ Sql);
		dayReportRO er=new dayReportRO();
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<dayReportRO> leo = null;
		try
		{
			STMT = con.prepareStatement(Sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<dayReportRO>) rh.getBean(rs, er);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
	//	logger.info("sql_getDayReportMonth leo : "+leo);
		return leo;
}
	
	/**
	 * 月份考勤查出統計結果值
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final 	Hashtable  getMonthSumData(Connection con, leaveCardVO lcVo,String Day)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_getMonthSumData);
		sql = sql.replace("<DAY>", Day);
		if (lcVo.getSearchDepartmen().equals("0"))
		{
			sql = sql.replace("<DEPARTMENT/>", " 1=1");
		}
		else
		{
			sql = sql.replace("<DEPARTMENT/>", " D.ID='" + lcVo.getSearchDepartmen() + "'  ");
		}
		if (lcVo.getSearchUnit().equals("0"))
		{
			sql = sql.replace("<UNIT/>", " 1=1");
		}
		else
		{
			sql = sql.replace("<UNIT/>", "  U.ID='" + lcVo.getSearchUnit() + "'  ");
		}
		// 建立資料表資料
		logger.info("sql_getMonthSumData Sql : "+ sql);
		monthSumDataWo mw=new monthSumDataWo();
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<monthSumDataWo> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<monthSumDataWo>) rh.getBean(rs, mw);
	
		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		Hashtable listmW = new Hashtable();
		logger.info("leo.size() : "+leo.size());
		//logger.info("(monthReportNoteWO)leo.get(0): "+leo.get(0).getEMPLOYEENO());
		
		for(int i=0;i<leo.size();i++){
			mw=(monthSumDataWo)leo.get(i);
			logger.info(mw.getEMPLOYEENO()+":getMonthReportL : "+mw.getMonthReportL());
			listmW.put(mw.getEMPLOYEENO(), mw);
			
			
		}
		return listmW;
	}
	/**
	 * 更新人員
	 */
	public static final void changeHrEmployee(Connection con){
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_changeHrEmployee);
		try
		{
			
			updateSql(sql,con);
			logger.info("更新人員ok sql =>"+sql);
		}catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
			logger.info("更新人員error");
		}
	}
	
	/**
	 * 查詢請假權限流程部門資料 
	 * 
	 * @param con
	 * @param sql
	 * @param ra
	 * @return
	 */
	public static List<editProcessRO> queryDeptLeaveData(Connection con, String sql, editProcessRO ra)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<editProcessRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<editProcessRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}
	/**
	 * 請假先檢查是否有設定流程
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	String  getPersonalProcess(Connection con, leaveCardVO lcVo) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryUserData);
		sql = sql.replace("<EMPLOYEENO/>", lcVo.getSearchEmployeeNo());
	
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processUserRO ru=(processUserRO)leo.get(0);
	
		float  dayCount=Float.parseFloat(lcVo.getDayCount());
		String STATUS="0";//三天以下
		if(dayCount>=3){
			 STATUS="1";//三天以上
		}
		ru.setSTATUS(STATUS);
		int count=0;
		
		String COUNT ="";

		processCheckRO  prow=new processCheckRO();
		if(ru.getROLE().equals("E") || ru.getROLE().equals("U")){
			logger.info("檢查此部門或單位:"+SqlUtil.queryDeptLeaveCardCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryDeptLeaveCardCount(ru), "COUNT");
			count=Integer.valueOf(COUNT);
			if(count>0){
        			/**檢查欄位**/
        			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
        			logger.info("processOVTable sql="+SqlUtil.queryProcessCheck(ru,keyConts.processLETable));
        			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processLETable), new processCheckRO());
        			if(cr.size()>0){
        			    prow=cr.get(0);
        			}
			}
			
		}else{
			logger.info("檢查此部門:"+SqlUtil.queryLeavePreossCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryLeavePreossCount(ru), "COUNT");
			count=Integer.valueOf(COUNT);
			if(count>0){
        			/**檢查欄位**/
        			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
        			logger.info("processOVTable sql="+SqlUtil.queryProcessCheck(ru,keyConts.processLETable));
        			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processLETable), new processCheckRO());
        			if(cr.size()>0){
        			    prow=cr.get(0);
        			}
			}
		}
		if(count>0){
			
			/**檢查四個欄位有無值無值一樣不能過**/
			if(prow.getSINGROLEL1().equals("0") && prow.getSINGROLEL2().equals("0")
				&& prow.getSINGROLEL3().equals("0") && prow.getSINGROLEL4().equals("0")){
			    count=0;
			}
		}
		String msg="o";
		if(count==0){
			msg="x";
		}
	
		return msg;
	}
	
	/**
	 * 請假記錄取出後台設定寫入請假卡
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	editProcessRO  getProcessData( leaveCardVO lcVo,Connection con) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryUserData);
		sql = sql.replace("<EMPLOYEENO/>", lcVo.getSearchEmployeeNo());
		logger.info("用工號查出部門單位角色:"+sql);
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processUserRO ru=(processUserRO)leo.get(0);
		//logger.info("部門:"+ru.getDEPARTMENT());
		//logger.info("單位:"+ru.getUNIT());
		//logger.info("角色:"+ru.getROLE());
		float  dayCount=Float.parseFloat(lcVo.getDayCount());
		String STATUS="0";//三天以下
		if(dayCount>=3){
			 STATUS="1";//三天以上
		}
		editProcessRO epDD=new editProcessRO();
		editProcessVO edVo =new editProcessVO();
		edVo.setDept(ru.getDEPARTMENT());
		if(ru.getROLE().equals("E") || ru.getROLE().equals("U")){
			edVo.setUnit(ru.getUNIT());
		}else{
			edVo.setUnit("0");
		}
		edVo.setStatus(STATUS);
		edVo.setRole(ru.getROLE());
		List<editProcessRO> ep=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		
		return ep.get(0);
	}
	
	/**
	 * 用行ID查出請假流程
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	editProcessRO  getProcessIDData(String rowID,Connection con) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processIDUserRO ra=new processIDUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryLeaveCardUserData);
		//sql = sql.replace("<rowID/>", lcVo.getRowID());
		sql = sql.replace("<rowID/>", rowID);
		logger.info("sql_queryLeaveCardUserData sql : "+sql );
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processIDUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processIDUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processIDUserRO ru=(processIDUserRO)leo.get(0);
		//logger.info("部門:"+ru.getDEPARTMENT());
		//logger.info("單位:"+ru.getUNIT());
		//logger.info("角色:"+ru.getROLE());
		//logger.info("DAYCOUNT:"+ru.getDAYCOUNT());
		float  dayCount=Float.parseFloat(ru.getDAYCOUNT());
		String STATUS="0";//三天以下
		if(dayCount>=3){
			 STATUS="1";//三天以上
		}
		editProcessRO epDD=new editProcessRO();
		editProcessVO edVo =new editProcessVO();
		edVo.setDept(ru.getDEPARTMENT());
		if(ru.getROLE().equals("E") || ru.getROLE().equals("U")){
			edVo.setUnit(ru.getUNIT());
		}else{
			edVo.setUnit("0");
		}
		edVo.setStatus(STATUS);
		edVo.setRole(ru.getROLE());
		List<editProcessRO> ep=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		//logger.info("getSingRoleL1 :"+ep.get(0).getSingRoleL1());
	//	logger.info("getSingRoleL1EP :"+ep.get(0).getSingRoleL1EP());
		return ep.get(0);
	}
	
	
	/**
	 * 加班先檢查是否有設定流程
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	String  getOverProcess(Connection con, overTimeVO otVo) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryUserData);
		sql = sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
	
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processUserRO ru=(processUserRO)leo.get(0);
	
	
		String STATUS="1";//正常流程
		
		ru.setSTATUS(STATUS);
		int count=0;
		
		processCheckRO  prow=new processCheckRO();
		String COUNT ="";
		if(ru.getROLE().equals("E") || ru.getROLE().equals("U")){
			logger.info("檢查此部門或單位:"+SqlUtil.queryDeptUnitOverCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryDeptUnitOverCount(ru), "COUNT");
			/**檢查欄位**/
			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
			logger.info("processOVTable sql="+SqlUtil.queryProcessCheck(ru,keyConts.processOVTable));
			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processOVTable), new processCheckRO());
			prow=cr.get(0);
		}else{
			logger.info("檢查此部門:"+SqlUtil.queryOverPreossCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryOverPreossCount(ru), "COUNT");
			/**檢查欄位**/
			ru.setUNIT("0");		
			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
			logger.info("queryProcessCheck sql="+SqlUtil.queryProcessCheck(ru,keyConts.processOVTable));
			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processOVTable), new processCheckRO());
			prow=cr.get(0);
		}
		if(COUNT!=null || !COUNT.equals("") ){
			count=Integer.valueOf(COUNT);
			/**檢查四個欄位有無值無值一樣不能過**/
			if(prow.getSINGROLEL1().equals("0") && prow.getSINGROLEL2().equals("0")
				&& prow.getSINGROLEL3().equals("0") && prow.getSINGROLEL4().equals("0")){
			    count=0;
			}
		}
		String msg="o";
		if(count==0){
			msg="x";
		}
	
		return msg;
	}
	
	
	/**
	 * CS加班先檢查是否有設定流程
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	String  getCSProcess(Connection con, overTimeVO otVo) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryCSUserData);
		sql = sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processUserRO ru=(processUserRO)leo.get(0);
	
	
		String STATUS="1";//正常流程
		
		ru.setSTATUS(STATUS);
		int count=0;
		
		String COUNT ="";
		processCheckRO  prow=new processCheckRO();
		
			logger.info("檢查此部門:"+SqlUtil.queryCSPreossCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryCSPreossCount(ru), "COUNT");
			ru.setUNIT("0");
			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
			logger.info("queryProcessCheck sql="+SqlUtil.queryProcessCheck(ru,keyConts.processCSTable));
			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processCSTable), new processCheckRO());
			prow=cr.get(0);
			
		
		logger.info("prow.toString():"+prow.toString());
		
		if(COUNT!=null || !COUNT.equals("") ){
			count=Integer.valueOf(COUNT);
			logger.info("count 1"+count);
			/**檢查四個欄位有無值無值一樣不能過**/
			if(prow.getSINGROLEL1().equals("0") && prow.getSINGROLEL2().equals("0")
				&& prow.getSINGROLEL3().equals("0") && prow.getSINGROLEL4().equals("0")){
			    count=0;
			}
			logger.info("count 2"+count);
		}
		logger.info("檢查 count:"+count);
		String msg="o";
		if(count==0){
			msg="x";
		}
		logger.info("檢查 msg:"+msg);
		return msg;
	}
	
	
	
	
	/**
	 * CS加班先檢查是否有設定流程
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @throws Exception 
	 */
	public static final 	void  getProcessCheck(Connection con, String sql,overTimeVO otVo) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processUserRO ru=(processUserRO)leo.get(0);
	}
	
	/**
	 * 加班記錄取出後台設定寫入請假卡
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	editProcessRO  getOverProcessData( overTimeVO otVo,Connection con) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryUserData);
		sql = sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
		logger.info("用工號查出部門單位角色:"+sql);
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processUserRO ru=(processUserRO)leo.get(0);
		//logger.info("部門:"+ru.getDEPARTMENT());
		//logger.info("單位:"+ru.getUNIT());
		logger.info("角色:"+ru.getROLE());
	
		String	 STATUS="1";//正常流程
		
		editProcessRO epDD=new editProcessRO();
		editProcessVO edVo =new editProcessVO();
		edVo.setDept(ru.getDEPARTMENT());
		if(ru.getROLE().equals("E") || ru.getROLE().equals("U")){
			edVo.setUnit(ru.getUNIT());
		}else{
			edVo.setUnit("0");
		}
		edVo.setStatus(STATUS);
		edVo.setRole(ru.getROLE());
		List<editProcessRO> ep=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptOverData(edVo),epDD);
		
		return ep.get(0);
	}
	
	
	/**
	 * 超時加班使用工號查詢部門單位
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	processCOUserRO   getCSUSERData( overTimeVO otVo,Connection con) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processCOUserRO ra=new processCOUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryCSUserData);
		sql = sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
		logger.info("用工號查出部門單位角色:"+sql);
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processCOUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processCOUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processCOUserRO ru=(processCOUserRO)leo.get(0);
		logger.info("部門:"+ru.getDEPARTMENT());
		logger.info("單位:"+ru.getUNIT());
		logger.info("角色:"+ru.getROLE());

		return ru;
	}
	
	
	
	/**
	 * CS加班記錄取出後台設定寫入請假卡
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	editProcessRO  getCSProcessData( overTimeVO otVo,Connection con,processCOUserRO ru) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		
		logger.info("部門:"+ru.getDEPARTMENT());
		logger.info("單位:"+ru.getUNIT());
		logger.info("角色:"+ru.getROLE());
	
		String	 STATUS="1";//正常流程
		
		editProcessRO epDD=new editProcessRO();
		editProcessVO edVo =new editProcessVO();
		edVo.setDept(ru.getDEPARTMENT());
		//if(ru.getROLE().equals("E") || ru.getROLE().equals("U")){
		//	edVo.setUnit(ru.getUNIT());
		//}else{
			edVo.setUnit("0");
		//}
		edVo.setDept(ru.getDEPARTMENT());
		//edVo.setUnit(ru.getUNIT());
		edVo.setStatus(STATUS);
		edVo.setRole(ru.getROLE());
		logger.info("queryDeptCSData:"+SqlUtil.queryDeptCSData(edVo));
		List<editProcessRO> ep=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptCSData(edVo),epDD);
		
		return ep.get(0);
	}
	
	
	
	/**
	 * 用行ID查出加班流程
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	editProcessRO  getProcessOverIDData(overTimeVO otVo,Connection con) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processIDUserRO ra=new processIDUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryOverTimeUserData);
		sql = sql.replace("<rowID/>", otVo.getRowID());
		logger.info("sql_queryLeaveCardUserData sql : "+sql );
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processIDUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processIDUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processIDUserRO ru=(processIDUserRO)leo.get(0);
		logger.info("部門:"+ru.getDEPARTMENT());
		logger.info("單位:"+ru.getUNIT());
		logger.info("角色:"+ru.getROLE());
		logger.info("DAYCOUNT:"+ru.getDAYCOUNT());
	
		String STATUS="1";//正常加班
		
		editProcessRO epDD=new editProcessRO();
		editProcessVO edVo =new editProcessVO();
		edVo.setDept(ru.getDEPARTMENT());
		if(ru.getROLE().equals("E") || ru.getROLE().equals("U")){
			edVo.setUnit(ru.getUNIT());
		}else{
			edVo.setUnit("0");
		}
		edVo.setStatus(STATUS);
		edVo.setRole(ru.getROLE());
		
		logger.info("SqlUtil.queryDeptOverData(edVo) :"+SqlUtil.queryDeptOverData(edVo)); 
		List<editProcessRO> ep=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptOverData(edVo),epDD);
		editProcessRO eP=ep.get(0);
		return eP;
	}
	/**
	 * 查詢全廠報表合計行號
	 * 
	 * @param con
	 * @param sql
	 * @param ra
	 * @return
	 */
	public static List<dayPlantRO> queryPlantBlueRow(Connection con, String sql, dayPlantRO ra)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<dayPlantRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<dayPlantRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return leo;
	}
	
	
	/**
	 * 查詢寄信設定
	 * 
	 * @param con
	 * @param sql
	 * @param ra
	 * @return
	 */
	public static EmailWO queryConfigRow(Connection con, String sql, configRO ra)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		
		
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<configRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<configRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		EmailWO ew=new EmailWO();
		for(int i=0;i<leo.size();i++){
		    configRO row=leo.get(i);
		    if(row.getKEY().equals(keyConts.EmailSmtp)){
			ew.setSMTP(row.getVALUE());
		    }
		    if(row.getKEY().equals(keyConts.EmailPort)){
			ew.setPORT(row.getVALUE());
		    }	
		    if(row.getKEY().equals(keyConts.EmailUser)){
			ew.setUSER(row.getVALUE());
		    }	
		    if(row.getKEY().equals(keyConts.EmailPw)){
			ew.setPW(row.getVALUE());
		    }	
		    if(row.getKEY().equals(keyConts.EmailProw)){
			ew.setFROM(row.getVALUE());
		    }
		}
		
		return ew;
	}
	
	
	/**
	 * 取得user資料
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static  processUserRO  getEmpUser(Connection con, String EmployeeNo) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		String sql = "";
		
		sql = hu.gethtml(sqlConsts.sql_queryUserData);
		sql = sql.replace("<EMPLOYEENO/>", EmployeeNo);
		logger.info("sql queryUserData"+sql );
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		logger.info("leo"+leo.size() );
		processUserRO ru=(processUserRO)leo.get(0);
		return ru;
	}
	
	
	/**
	 * 取得寄信分組主管工號資料
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static  List<supervisorRO>  queryEmailSingep(Connection con, String sql) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		supervisorRO ra=new supervisorRO();

		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<supervisorRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<supervisorRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		
		return leo;
	}
	/**
	 * 取得寄信分組資料
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static  List<leaveEmailListRO>  querySendEmailLeaveList(Connection con, String sql) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		leaveEmailListRO ra=new leaveEmailListRO();

		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<leaveEmailListRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<leaveEmailListRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		
		return leo;
	}
	
	
	/**
	 * 取得加班分組資料
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static  List<overEmailListRO>  querySendEmailOverList(Connection con, String sql) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		overEmailListRO ra=new overEmailListRO();

		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<overEmailListRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<overEmailListRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		
		return leo;
	}
	
	/**
	 * 自動建立系統登入帳號
	 */
	public static final boolean buildSysLoginUser(Connection con,String sql){
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		boolean flag=true;
		try
		{
			
			updateSql(sql,con);
			logger.info("自動建立系統登入帳號ok sql =>"+sql);
		}catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
			logger.info("自動建立系統登入帳號error");
			flag=false;
		}
		return flag;
	}
	
	
	/**
	 * 刪除系統登入帳號
	 */
	public static final boolean deleteBuildSysUser(Connection con,String sql){
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		boolean flag=true;
		try
		{
			updateSql(sql,con);
			logger.info("刪除系統登入帳號ok sql =>"+sql);
		}catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
			logger.info("刪除系統登入帳號error");
			flag=false;
		}
		return flag;
	}
	
	
	/**
	 * 查詢打卡時間
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static  List<workDateRO>  queryworkDateList(Connection con, String sql) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		workDateRO ra=new workDateRO();

		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<workDateRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<workDateRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		
		return leo;
	}
	
}
