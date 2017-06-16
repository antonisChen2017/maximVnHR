package cn.com.maxim.portal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;

import cn.com.maxim.portal.attendan.ro.dayAttendanceRO;
import cn.com.maxim.portal.attendan.ro.dayReportRO;
import cn.com.maxim.portal.attendan.ro.empLateEarlyRO;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.ro.empnumRO;
import cn.com.maxim.portal.attendan.ro.exLeaveCardCRO;
import cn.com.maxim.portal.attendan.ro.exLeaveCardRO;
import cn.com.maxim.portal.attendan.ro.exOvertimeRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceDayRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.ro.repDailyRO;
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.attendan.wo.dayAttendanceWO;
import cn.com.maxim.portal.attendan.wo.monthReportNoteWO;
import cn.com.maxim.portal.attendan.wo.monthSumDataWo;
import cn.com.maxim.portal.bean.dayTableMoble;
import cn.com.maxim.portal.bean.dayTableRowMoble;
import cn.com.maxim.portal.bean.repAttendanceDayBean;
import cn.com.maxim.portal.key.attendanceDayKeyConsts;
import cn.com.maxim.potral.consts.keyConts;
import cn.com.maxim.potral.consts.sqlConsts;

public class DBUtil
{

	public static String queryDBID(Connection con, String sql)
	{
		return queryDBField(con, sql, "ID");
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
	public static String addOverS(String MID, overTimeVO otVo, Connection con)
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
	public static String addExceedOverS(String MID, overTimeVO otVo, Connection con)
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
			logger.info("SqlUtil.setExceedOvertimeS() :"+SqlUtil.setExceedOvertimeS());
			
			logger.info(" INSERT INTO VN_OVERTIME_S	  "
					+ "(M_ID,EP_ID  ,REASONS,UNIT  ,NOTE ,STATUS  ,SAVETIME,USERREASONS "
					+ " ,submitDate,APPLICATION_HOURS  ,OVERTIME_START,OVERTIME_END  ,TURN)"
					+ "  VALUES('"+MID+"','"+otVo.getEP_ID()+"' ,'"+otVo.getSearchReasons()+"','"+otVo.getUID()+"' ,'"+ otVo.getNote()+"','"+otVo.getStatus()+"','"+otVo.getSubmitDate()+" ,getdate(),"
					+  otVo.getUserReason()+"','"+otVo.getAddTime()+"','"+otVo.getQueryDate()+" "+ otVo.getStartTimeHh()+":"+otVo.getStartTimemm()+"','"
					+  otVo.getQueryDate()+" "+ otVo.getEndTimeHh()+":"+otVo.getEndTimemm()+"','"+otVo.getOverTimeClass()+"') ");
			
			PreparedStatement ps = con.prepareStatement(SqlUtil.setExceedOvertimeS(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, MID);
			ps.setString(2, otVo.getEP_ID());
			ps.setString(3, otVo.getSearchReasons());
			ps.setString(4, otVo.getUID());
			ps.setString(5, otVo.getNote());
			ps.setString(6, otVo.getStatus());
			ps.setString(7, otVo.getUserReason());
			ps.setString(8, otVo.getSubmitDate());
			ps.setString(9, otVo.getAddTime());
			ps.setString(10, otVo.getQueryDate()+" "+ otVo.getStartTimeHh()+":"+otVo.getStartTimemm());
			ps.setString(11, otVo.getQueryDate()+" "+ otVo.getEndTimeHh()+":"+otVo.getEndTimemm());
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
		/**
		 * stop1 查询是否有主表
		 */
		
		String yyyy = otVo.getQueryDate().split("/")[0];
		String mm = otVo.getQueryDate().split("/")[1];
		String flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm), con);
		logger.info("saveOvertime flag:"+flag);
		/**檢查是否同時段已有加班**/
		String count = DBUtil.queryDBField(con,SqlUtil.getOvertimeSCount(otVo), "count");
		if(Integer.valueOf(count)>0)
		{
			flag="2";
		}
		
		if (flag.equals("1"))
		{
			msg = "超过每月规定加班时数";
			logger.info("saveOvertime msg:"+msg);
		}else if (flag.equals("2"))
		{
			msg = "已有相同时间段加班";
			logger.info("saveOvertime msg:"+msg);
		}else{
				String resultID = "";
				resultID = DBUtil.queryDBID(con, SqlUtil.getOvertimeID(otVo.getSearchDepartmen(), otVo.getQueryDate()));
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
						SID = DBUtil.addOverS(resultID, otVo, con);
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
								msg = "已新增加班申请单";
							}
							else
							{
								    DBUtil.delDBTableRow(SqlUtil.delOvertimeM(resultID), con);
									DBUtil.delDBTableRow(SqlUtil.delOvertimeS(SID), con);
									msg = "超过每月规定加班时数";
							
							}
						}
						else
						{
							updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
							msg = "已新增加班申请单";
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
					SID = DBUtil.addOverS(resultID, otVo, con);
					/**
					 * stop3.2 查此人是否本月超過規定时间,有則寫入超時
					 */
					yyyy = otVo.getQueryDate().split("/")[0];
					mm = otVo.getQueryDate().split("/")[1];
					// System.out.println("查此人是否本月超過規定时间 sql:
					//超過當月時數
					flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm), con);
		
					/**
					 * stop3.2 查出來是否超時 寫入此條紀錄
					 */
		
					if (flag.equals("1"))
					{
						if (otVo.isOverTimeSave())
						{
							updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
							msg = "已新增加班申请单";
						}
						else
						{
						
						
							    DBUtil.delDBTableRow(SqlUtil.delOvertimeS(SID), con);
								msg = "超过每月规定加班时数";
							
						}
					}
					else
					{
						updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
						msg = "已新增加班申请单";
					}
		
				}
		}
		logger.info("saveOvertime msg:"+msg);
		return msg;

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
		/**
		 * stop1 查询是否有主表
		 */
		String resultID = "";
		logger.info("Dep:"+otVo.getDID());
		otVo.setQueryDate(DateUtil.NowDate());
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
			
			//String yyyy = otVo.getQueryDate().split("/")[0];
			//String mm = otVo.getQueryDate().split("/")[1];
		//	logger.info("queryExceedMonthOverTime : "+SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm));
			
			if (!resultID.equals(""))
			{

			
				logger.info("addOverS resultID:"+resultID);
				logger.info("addOverS otVo.getEP_ID():"+otVo.getEP_ID());
			
			
				/**
				 * stop3.2 查此人是否本月超過規定时间,有則寫入超時
				 */
			
				//超過當月時數
				String flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getEP_ID(), yyyy, mm), con);
				logger.info("queryExceedMonthOverTime flag:"+flag);
				if (flag.equals("1"))
				{
					SID = DBUtil.addExceedOverS(resultID, otVo, con);
					logger.info(" 1 addOverS SID:"+SID);
					logger.info("1 updateMonthOverTims SqlUtil.updateMonthOverTims(flag, SID):"+SqlUtil.updateMonthOverTims(flag, SID));
						updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
						msg = "已新增超时加班申请单";
				}
				else
				{
					DBUtil.delDBTableRow(SqlUtil.delOvertimeM(resultID), con);
				    DBUtil.delDBTableRow(SqlUtil.delOvertimeS(SID), con);
					msg = "未超时不能申请";
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
			String flag = DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getEP_ID(), yyyy, mm), con);

			/**
			 * stop3.2 查出來是否超時 寫入此條紀錄
			 */

			if (flag.equals("1"))
			{
				SID = DBUtil.addExceedOverS(resultID, otVo, con);
				logger.info("2 addOverS SID:"+SID);
				logger.info("2 updateMonthOverTims SqlUtil.updateMonthOverTims(flag, SID):"+SqlUtil.updateMonthOverTims(flag, SID));
				updateSql(SqlUtil.updateMonthOverTims(flag, SID), con);
				msg = "已新增超时加班申请单";
			}
			else
			{
			    DBUtil.delDBTableRow(SqlUtil.delOvertimeS(SID), con);
				msg = "未超时不能申请";
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

	public static boolean updateTimeOverSStatus(String Status, String ID, Connection con)
	{
		boolean flag = false;
		if ((Status == null) || (ID == null))
		{
			return false;
		}
		try
		{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SqlUtil.setStatusS(Status, ID));
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
		String result = "x", WorkFDate = "";
		if ((lcVo.getSearchEmployeeNo() == null))
		{
			return "x";
		}
		else
		{

			String startTime = lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute() + ":00";
			String endTime = lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute() + ":00";
			DateUtil dateUtil = new DateUtil();

			//lcVo.setDayCount(String.valueOf(dateUtil.jisuan(startTime, endTime)));
			if (lcVo.getDayCount().equals("0.0"))
			{
				return "v";
			}
			if (lcVo.getDayCount().equals("0"))
			{
				return "v";
			}
			/** 不能已有打卡时间 **/
			/** stop1 同一天 **/
			if (lcVo.getStartLeaveDate().equals(lcVo.getEndLeaveDate()))
			{
				// 同一天
				// System.out.println("不能已有打卡时间 Sql:
				// "+SqlUtil.getOnlyWorkTime(lcVo));
				WorkFDate = queryDBField(con, SqlUtil.getOnlyWorkTime(lcVo), "WorkFDate");
				// System.out.println("不能已有打卡时间 : WorkFDate"+WorkFDate);
				if (!WorkFDate.equals(""))
				{
					return "o";
				}

				/** 已請假不能再請 **/
				logger.info("不能已請假 "+SqlUtil.getOnlyLeaveTime(lcVo));
				WorkFDate = queryDBField(con, SqlUtil.getOnlyLeaveTime(lcVo), "STARTLEAVEDATE");
				logger.info("WorkFDate "+WorkFDate);
				if (!WorkFDate.equals(""))
				{
					return "w";
				}
			}
			else
			{
				// 不同天
				leaveCardVO WorkFlcVo = new leaveCardVO();
				WorkFlcVo.setStartLeaveDate(lcVo.getStartLeaveDate());
				WorkFlcVo.setStartLeaveTime(lcVo.getStartLeaveTime());
				WorkFlcVo.setStartLeaveMinute(lcVo.getEndLeaveMinute());
				WorkFlcVo.setEndLeaveDate(lcVo.getStartLeaveDate());
				WorkFlcVo.setEndLeaveTime("23");
				WorkFlcVo.setEndLeaveMinute("59");
				WorkFlcVo.setSearchEmployeeNo(lcVo.getSearchEmployeeNo());
				logger.info("不能已有開始打卡时间 Sql: "+SqlUtil.getOnlyWorkTime(WorkFlcVo));
				WorkFDate = queryDBField(con, SqlUtil.getOnlyWorkTime(WorkFlcVo), "WorkFDate");
				logger.info("不能已有開始打卡时间 : WorkFDate"+WorkFDate);
				if (!WorkFDate.equals(""))
				{
					return "o";
				}

				leaveCardVO WorkElcVo = new leaveCardVO();
				WorkElcVo.setStartLeaveDate(lcVo.getEndLeaveDate());
				WorkElcVo.setStartLeaveTime("05");
				WorkElcVo.setStartLeaveMinute("30");
				WorkElcVo.setEndLeaveDate(lcVo.getEndLeaveDate());
				WorkElcVo.setEndLeaveTime(lcVo.getEndLeaveTime());
				WorkElcVo.setEndLeaveMinute(lcVo.getEndLeaveTime());
				WorkElcVo.setSearchEmployeeNo(lcVo.getSearchEmployeeNo());
				// System.out.println("不能已有結束打卡时间 Sql:
				// "+SqlUtil.getOnlyWorkTime(WorkElcVo));
				WorkFDate = queryDBField(con, SqlUtil.getOnlyWorkTime(WorkElcVo), "WorkFDate");
				// System.out.println("不能已有結束打卡时间 : WorkFDate"+WorkFDate);
				if (!WorkFDate.equals(""))
				{
					return "o";
				}
				/** 已請假不能再請 **/
				logger.info("不同天 已請假不能再請 Sql: "+SqlUtil.getOnlyLeaveTime(lcVo));
				WorkFDate = queryDBField(con, SqlUtil.getOnlyLeaveTime(lcVo), "STARTLEAVEDATE");
				logger.info("不同天  已請假不能再請 WorkFDate: "+WorkFDate);
				if (!WorkFDate.equals(""))
				{
					return "w";
				}
			}

			/** 單天星期天不能請 **/
			
		}
		try
		{

			PreparedStatement ps = con.prepareStatement(SqlUtil.saveLeaveCard(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, lcVo.getSearchEmployeeNo());
			ps.setString(2, lcVo.getSearchHoliday());
			ps.setString(3, lcVo.getApplicationDate());
			ps.setString(4, lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute());
			ps.setString(5, lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute());
			ps.setString(6, lcVo.getSearchAgent());
			ps.setString(7, lcVo.getNote());
			ps.setString(8, lcVo.getStatus());
			ps.setString(9, lcVo.getDayCount());
			ps.setString(10, lcVo.getHourCount());
			ps.setString(11, lcVo.getMinuteCount());
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
		String result = "";
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
		//logger.info("queryExcelAttendanceDay sql :" + sql);
		String result = "";
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
	 * 將全廠日報表寫入db
	 * 
	 * @param con
	 * @param sql
	 * @param er
	 * @return
	 */
	public static void saveRepAttendanceDay(Connection con, dayTableRowMoble dr, leaveCardVO lcVo, String[] allRowS)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);

		try
		{

			String count = queryDBField(con, SqlUtil.getAttendanceDay(lcVo.getApplicationDate().replaceAll("/", "")), "count");
			//logger.info("sql count " + SqlUtil.getAttendanceDay(lcVo.getApplicationDate().replaceAll("/", "")));
		//	logger.info("count " + count);
			if (Integer.valueOf(count) == 0)
			{
				dayAttendanceWO dw1 = new dayAttendanceWO();// 寫入資料庫用
				dw1.setYMD(lcVo.getApplicationDate().replaceAll("/", ""));
				dw1.setROW("1");
				String[] dbRow1s = attendanceDayKeyConsts.dbRow1.split(",");
			//	logger.info(attendanceDayKeyConsts.dbRow1);
				for (int i = 0; i < dbRow1s.length; i++)
				{

					dw1.setCol((i + 3), dbRow1s[i]);
				}
			//	logger.info("dw1 : "+dw1);
				saveRepAttendanceDayRow(con, dw1);

				dayAttendanceWO dw2 = new dayAttendanceWO();// 寫入資料庫用
				dw2.setYMD(lcVo.getApplicationDate().replaceAll("/", ""));
				dw2.setROW("2");
				String[] dbRow2s = attendanceDayKeyConsts.dbRow2.split(",");
			//	logger.info(attendanceDayKeyConsts.dbRow2);
				for (int i = 0; i < dbRow2s.length; i++)
				{
					dw2.setCol((i + 3), dbRow2s[i]);
				}
			//	logger.info("dw2 : "+dw2);
				saveRepAttendanceDayRow(con, dw2);

				dayAttendanceWO dw3 = new dayAttendanceWO();// 寫入資料庫用
				dw3.setYMD(lcVo.getApplicationDate().replaceAll("/", ""));
				dw3.setROW("3");
				String[] dbRow3s = attendanceDayKeyConsts.dbRow3.split(",");
			//	logger.info(attendanceDayKeyConsts.dbRow3);
				for (int i = 0; i < dbRow3s.length; i++)
				{
					dw3.setCol((i + 3), dbRow3s[i]);
				}
		//		logger.info("dw3 : "+dw3);
				saveRepAttendanceDayRow(con, dw3);
		//		logger.info("allRowS : "+allRowS);
				for (int i = 0; i < allRowS.length; i++)
				{
					repAttendanceDayBean row = (repAttendanceDayBean) dr.getHt().get("row" + allRowS[i]);
				
					dayAttendanceWO dwRow = new dayAttendanceWO();// 寫入資料庫用
					dwRow.setYMD(lcVo.getApplicationDate().replaceAll("/", ""));
					dwRow.setROW(allRowS[i]);
					String[] dbRowMaxs = attendanceDayKeyConsts.dbRowMax.split(",");
					for (int J = 0; J < dbRowMaxs.length; J++)
					{
						dwRow.setCol((J + 3), row.getCol((J + 1)));
			//			logger.info("save row i=" +i+"J="+J+" row: "+row.getCol((J+1)));
						// System.out.println("save row i=" +i+"J="+J+" row: "
						// +row.getCol((J+1)));
					}
					saveRepAttendanceDayRow(con, dwRow);
				
				}

			}
		}
		catch (Exception e)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(e));
		}
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
		String count = queryDBField(con, SqlUtil.getEmpnum(lcVo.getApplicationDate().replaceAll("/", "")), "count");
		if (Integer.valueOf(count) == 0)
		{
			//第一行
			dayAttendanceWO dw1 = new dayAttendanceWO();// 
			dw1.setYMDKEY(lcVo.getApplicationDate().replaceAll("/", ""));
			dw1.setYMD(lcVo.getApplicationDate().replaceAll("/", ""));
			dw1.setYEAR(keyConts.empnumOneYear);
			dw1.setEMPNUM(String.valueOf(dt.getYco1()));
			dw1.setPERCENTAGE(NumberUtil.getPercentFormat(dt.getYco1(),dt.getYmax()));
			saveEmpnumRow(con,dw1);
			//第2行
			dayAttendanceWO dw2 = new dayAttendanceWO();// 
			dw2.setYMDKEY(lcVo.getApplicationDate().replaceAll("/", ""));
			dw2.setYMD("");
			dw2.setYEAR(keyConts.empnumTwoYear);
			dw2.setEMPNUM(String.valueOf(dt.getYco2()));
			dw2.setPERCENTAGE(NumberUtil.getPercentFormat(dt.getYco2(),dt.getYmax()));
			saveEmpnumRow(con,dw2);
			
			//第3行
			dayAttendanceWO dw3 = new dayAttendanceWO();// 
			dw3.setYMDKEY(lcVo.getApplicationDate().replaceAll("/", ""));
			dw3.setYMD("");
			dw3.setYEAR(keyConts.empnumThreeYear);
			dw3.setEMPNUM(String.valueOf(dt.getYco3()));
			dw3.setPERCENTAGE(NumberUtil.getPercentFormat(dt.getYco3(),dt.getYmax()));
			saveEmpnumRow(con,dw3);		
			
			//第4行
			dayAttendanceWO dw4 = new dayAttendanceWO();// 
			dw4.setYMDKEY(lcVo.getApplicationDate().replaceAll("/", ""));
			dw4.setYMD("");
			dw4.setYEAR(keyConts.empnumFourYear);
			dw4.setEMPNUM(String.valueOf(dt.getYco4()));
			dw4.setPERCENTAGE(NumberUtil.getPercentFormat(dt.getYco4(),dt.getYmax()));
			saveEmpnumRow(con,dw4);
			
			//第5行
			dayAttendanceWO dw5 = new dayAttendanceWO();// 
			dw5.setYMDKEY(lcVo.getApplicationDate().replaceAll("/", ""));
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
			 flag=true;
		}else{
			String Sql=hu.gethtml(sqlConsts.sql_updateUnit);
		    Sql=Sql.replace("<UNITID>", lcVo.getSearchUnit());
		    Sql=Sql.replace("<EMPID>", lcVo.getSearchEmployee());
		    logger.info("update Sql : "+ Sql);
			flag=DBUtil.updateSql(Sql, con) ;
			Sql=hu.gethtml(sqlConsts.sql_updateHREmployee);
			Sql=Sql.replace("<UNITID>", lcVo.getSearchUnit());
			Sql=Sql.replace("<EMPID>", lcVo.getSearchEmployee());
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
		Sql=Sql.replace("<EMPLOYEENO/>", otVo.getRowID());
	
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
		otVo.setSearchUnit(  leo.get(0).getUNIT());
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
				 /**公傷假**/
				if( leo.get(i).getHOLIDAYC()!=null && !leo.get(i).getHOLIDAYC().equals("")){
	
					double  dHolidayC=Float.parseFloat(leo.get(i).getHOLIDAYC());
					dNotWork= dNotWork-dHolidayC;
				//	logger.info(" c dNotWork  "+ dNotWork);
					 if(dNotWork<0 ){
						 dNotWork=0;
					 }
					 if( String.valueOf(dNotWork).equals("0.0")){
						 dNotWork=0;
					 }
					 sNotWork= String.valueOf(dNotWork).replaceAll(".0", "");
				//		logger.info("c dNotWork  "+ dNotWork);
					 if(dHolidayC!=0 & dNotWork<=8){
						 updateSql(SqlUtil.updateDayReportdNotWork(lcVo,sNotWork,leo.get(i).getEMPLOYEENO()), con);
					 }
				}
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
				 /**輪休**/
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
		logger.info("(monthReportNoteWO)leo.get(0): "+leo.get(0).getEMPLOYEENO());
		for(int i=0;i<leo.size();i++){
			mw=(monthSumDataWo)leo.get(i);
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
			logger.info("更新人員ok");
		}catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
			logger.info("更新人員error");
		}
	}
	
}
