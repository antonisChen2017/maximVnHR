package cn.com.maxim.portal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.empLateEarlyRO;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceDayRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.ro.repDailyRO;
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.hr.dem_LeaveCard;
import cn.com.maxim.portal.hr.mgr_LeaveCard;


public class DBUtil
{
	
	public static String queryDBID(Connection con, String sql)
	{	
		
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(DBUtil.class);
		String result = "";
		PreparedStatement STMT = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			if (rs.next())
			{
				result = rs.getString("ID");
			}
		}
		catch (SQLException error)
		{
			logger.error(error.getMessage());
		}
		return result;
	}
	/**
	 * 取得指定欄位資料
	 * @param con
	 * @param sql
	 * @param Field
	 * @return
	 */
	public static String queryDBField(Connection con, String sql,String Field)
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
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return result;
	}
	/**
	 * 存入main表
	 * @param SearchDepartmen
	 * @param queryDate
	 * @param addTime
	 * @param SearchEmployeeNo
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static  String addOverM(String SearchDepartmen, String queryDate, Connection con) 
	{
		
		
		
		String MID="";
		if ((SearchDepartmen == null) || (queryDate == null))
		{
			return"";
		}
		try
		{	
			PreparedStatement ps = con.prepareStatement(SqlUtil.setOvertimeM());
			ps.setString(1, SearchDepartmen);
			ps.setString(2, queryDate);
			ps.execute();
			MID= DBUtil.queryDBID(con, SqlUtil.getOvertimeID(SearchDepartmen, queryDate));
		}
		catch (SQLException error)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}

		return MID;
	}
	
	public static  boolean addTimeOverM(String newTotalTime ,String ID, Connection con) 
	{
		boolean flag=false;
		if ((newTotalTime == null) || (ID == null))
		{
			return false;
		}
		try
		{	
			  Statement stmt = con.createStatement();
			  stmt.executeUpdate(SqlUtil.setTotalTimeM(newTotalTime,ID));
			  stmt.close();
		
			flag=true;
		}
		catch (Exception Exception)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag=false;
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
	public static  String  addOverS(String MID, overTimeVO otVo,
			Connection con)
	{
		String result="x";
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
			ps.setString(4,  otVo.getQueryDate() + " " +  otVo.getStartTimeHh() + ":" + otVo.getStartTimemm());
			ps.setString(5, otVo.getQueryDate()+ " " + otVo.getEndTimeHh() + ":" + otVo.getEndTimemm());
			ps.setString(6, otVo.getSearchReasons());
			ps.setString(7, otVo.getSearchUnit());
			ps.setString(8, otVo.getNote());
			ps.setString(9, otVo.getStatus());
			ps.setString(10, otVo.getUserReason());
			ps.setString(11, otVo.getSubmitDate());
			ps.execute();
			ResultSet rs= ps.getGeneratedKeys();
			if (rs.next())
			{
				result = rs.getString("ID");
			}
			//System.out.println("SID result : "+result);
		}
		catch (Exception Exception)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			return "x";
		}

		return result;
	}
	
	
	public static  String getTotalTime(Connection con,String sql){
		PreparedStatement STMT = null;
		String result = "";
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			if (rs.next())
			{
				result = rs.getString("TOTAL_TIME");
			}

		}
		catch (SQLException error)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return result;
	}
	
	public static  String getStatus(Connection con,String sql){
		PreparedStatement STMT = null;
		String result = "";
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			if (rs.next())
			{
				result = rs.getString("STATUS");
			}

		}
		catch (SQLException error)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return result;
	}
	
	public static   String selectDBDepartmentID(Connection con, String UserEmployeeNo)
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
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		return result;
	}
	
	public static  boolean delOvertimeS(String ID, Connection con) 
	{
		boolean flag=false;
		if (ID == null)
		{
			return false;
		}
		try
		{	
			 Statement stmt = con.createStatement();
			 stmt.executeUpdate(SqlUtil.delOvertimeS(ID));
			 stmt.close();
			 flag=true;
		}
		catch (Exception Exception)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag=false;
		}

		return flag;
	}
	/**
	 * 共用刪除
	 * @param ID
	 * @param con
	 * @return
	 */
	public static  boolean delDBTableRow(String Sql, Connection con) 
	{
		boolean flag=false;
		if (Sql == null)
		{
			return false;
		}
		try
		{	
			 Statement stmt = con.createStatement();
			 stmt.executeUpdate(Sql);
			 stmt.close();
			 flag=true;
		}
		catch (Exception Exception)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag=false;
		}

		return flag;
	}
	
	public static  boolean updateTimeOverMStatus(String Status ,String ID, Connection con) 
	{
		boolean flag=false;
		if ((Status == null) || (ID == null))
		{
			return false;
		}
		try
		{	
			  Statement stmt = con.createStatement();
			  stmt.executeUpdate(SqlUtil.setStatusM(Status, ID));
			  stmt.close();
		
			flag=true;
		}
		catch (Exception Exception)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(Exception));
			flag=false;
		}

		return flag;
	}
	
	public static  String getEmp2(Connection con,String sql){
		PreparedStatement STMT = null;
		String result = "";
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			if (rs.next())
			{
				result = rs.getString("EMP_NO1");
			}

		}
		catch (SQLException error)
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		//System.out.println("result:"+result);
		return result;
	}
	
	/**
	 * 存入加班申請單
	 * 
	 * @param overTimeVO
	 * @param con
	 * @throws SQLException
	 */
	public static String saveTable(overTimeVO otVo, Connection con) throws SQLException
		{
			String msg="";
			String SID = "x";
			/**
			 * stop1 查詢是否有主表
			 */
			String resultID = "";
			resultID = DBUtil.queryDBID(con, SqlUtil.getOvertimeID(otVo.getSearchDepartmen(), otVo.getQueryDate()));
			
			
			if (resultID.equals(""))
			{
				/**
				 * stop2無主表寫入主表,然後寫入子表
				 */
		
				resultID = DBUtil.addOverM( otVo.getSearchDepartmen(), otVo.getQueryDate(), con);
			
				if(!resultID.equals("")){
					
					otVo.setStatus("S");
					SID = DBUtil.addOverS(resultID, otVo, con);
					/**
					 * stop3.2 查此人是否本月超過規定時間,有則寫入超時
					 */
					String yyyy=otVo.getQueryDate().split("/")[0];
					String mm=otVo.getQueryDate().split("/")[1];
					//System.out.println("time over sql : "+SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm));
					String flag=DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm), con);
					if(flag.equals("1")){
						if(otVo.isOverTimeSave()){
							updateSql(SqlUtil.updateMonthOverTims(flag,SID), con);
							msg="已新增加班申請單";
						}else{
						
							DBUtil.delOvertimeS(resultID,con);
							msg="超過每月規定加班時數,請聯絡人事";
						}
					}else{
						updateSql(SqlUtil.updateMonthOverTims(flag,SID), con);
						msg="已新增加班申請單";
					}
				
					
				}
				
			}
			else
			{
				
				/**
				 * stop3.1 有主表寫入子表
				 */
					otVo.setStatus("S");
					SID =DBUtil.addOverS(resultID, otVo, con);
					/**
					 * stop3.2 查此人是否本月超過規定時間,有則寫入超時
					 */
					String yyyy=otVo.getQueryDate().split("/")[0];
					String mm=otVo.getQueryDate().split("/")[1];
					//System.out.println("查此人是否本月超過規定時間 sql:  "+SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm));
					String flag=DBUtil.queryExceedMonthOverTime(SqlUtil.getAPPhoursflag(otVo.getSearchEmployeeNo(), yyyy, mm), con);
				
					/**
					 * stop3.2 查出來是否超時 寫入此條紀錄
					 */
				
					if(flag.equals("1")){
						if(otVo.isOverTimeSave()){
							updateSql(SqlUtil.updateMonthOverTims(flag,SID), con);
							msg="已新增加班申請單";
						}else{
							DBUtil.delOvertimeS(resultID,con);
							msg="超過每月規定加班時數,請聯絡人事";
						}
					}else{
						updateSql(SqlUtil.updateMonthOverTims(flag,SID), con);
						msg="已新增加班申請單";
					}
					
				
					
			}

			return msg;
			
		}
	
		public static String queryExceedMonthOverTime(String sql, Connection con) throws SQLException{
		
			PreparedStatement STMT = null;
			String result = "";
			try
			{
				STMT = con.prepareStatement(sql);
				ResultSet rs = STMT.executeQuery();
				if (rs.next())
				{
					result = rs.getString("flag");
				}

			}
			catch (SQLException error)
			{
				Log4jUtil lu=new Log4jUtil();
				Logger logger  =lu.initLog4j(DBUtil.class);
				logger.error(vnStringUtil.getExceptionAllinformation(error));
			}
			//System.out.println("result:"+result);
			return result;
			
			
		}
		
		/**
		 * UPDATE 共用
		 * @param Sql
		 * @param con
		 * @return
		 */
		public static  boolean updateSql(String Sql, Connection con) 
		{
			boolean flag=false;
			if ((Sql == null))
			{
				return flag;
			}
			try
			{	
				  Statement stmt = con.createStatement();
				  stmt.executeUpdate(Sql);
				  stmt.close();
			
				flag=true;
			}
			catch (Exception Exception)
			{
				Log4jUtil lu=new Log4jUtil();
				Logger logger  =lu.initLog4j(DBUtil.class);
				logger.error(vnStringUtil.getExceptionAllinformation(Exception));
				flag=false;
			}

			return flag;
		}
		
		
		public static  boolean updateTimeOverSStatus(String Status ,String ID, Connection con) 
		{
			boolean flag=false;
			if ((Status == null) || (ID == null))
			{
				return false;
			}
			try
			{	
				  Statement stmt = con.createStatement();
				  stmt.executeUpdate(SqlUtil.setStatusS(Status, ID));
				  stmt.close();
			
				flag=true;
			}
			catch (Exception Exception)
			{
				Log4jUtil lu=new Log4jUtil();
				Logger logger  =lu.initLog4j(DBUtil.class);
				logger.error(vnStringUtil.getExceptionAllinformation(Exception));
				flag=false;
			}

			return flag;
		}
		/**
		 * 共用更新
		 * @param Status
		 * @param ID
		 * @param con
		 * @return
		 */
		
		public static  boolean updateDbTable(String sql, Connection con) 
		{
			boolean flag=false;
			if ((sql == null) )
			{
				return false;
			}
			try
			{	
				  Statement stmt = con.createStatement();
				  stmt.executeUpdate(sql);
				  stmt.close();
			
				flag=true;
			}
			catch (Exception Exception)
			{
				Log4jUtil lu=new Log4jUtil();
				Logger logger  =lu.initLog4j(DBUtil.class);
				logger.error(vnStringUtil.getExceptionAllinformation(Exception));
				flag=false;
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
			
	
			String result="x",WorkFDate="";
			if ( (lcVo.getSearchEmployeeNo() == null))
			{
				return "x";
			}else{
				
				String startTime=lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() +":"+ lcVo.getStartLeaveMinute()+":00";
				String endTime=lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() +":"+ lcVo.getEndLeaveMinute()+":00";
				DateUtil dateUtil=new DateUtil();
				
				lcVo.setDayCount(String.valueOf(dateUtil.jisuan(startTime, endTime)));
				if(lcVo.getDayCount().equals("0.0") ){
					return "v";
				}
				if(lcVo.getDayCount().equals("0") ){
					return "v";
				}
				/**不能已有打卡時間**/
				/** stop1  同一天 **/
				if(lcVo.getStartLeaveDate().equals(lcVo.getEndLeaveDate())){
					//同一天
					//System.out.println("不能已有打卡時間 Sql: "+SqlUtil.getOnlyWorkTime(lcVo));
					WorkFDate=queryDBField(con,SqlUtil.getOnlyWorkTime(lcVo),"WorkFDate");
					//System.out.println("不能已有打卡時間 : WorkFDate"+WorkFDate);
					if(!WorkFDate.equals("")){
						return "o";
					}
					
					
					/**已請假不能再請**/
					//System.out.println("不能已請假 e ql: "+SqlUtil.getOnlyLeaveTime(lcVo));
					WorkFDate=queryDBField(con,SqlUtil.getOnlyLeaveTime(lcVo),"STARTLEAVEDATE");
					if(!WorkFDate.equals("")){
						return "w";
					}
				}else{
					//不同天
					leaveCardVO WorkFlcVo=new leaveCardVO();
					WorkFlcVo.setStartLeaveDate(lcVo.getStartLeaveDate());
					WorkFlcVo.setStartLeaveTime(lcVo.getStartLeaveTime());
					WorkFlcVo.setStartLeaveMinute(lcVo.getEndLeaveMinute());
					WorkFlcVo.setEndLeaveDate(lcVo.getStartLeaveDate());
					WorkFlcVo.setEndLeaveTime("23");
					WorkFlcVo.setEndLeaveMinute("59");
					WorkFlcVo.setSearchEmployeeNo(lcVo.getSearchEmployeeNo());
					//System.out.println("不能已有開始打卡時間 Sql: "+SqlUtil.getOnlyWorkTime(WorkFlcVo));
					WorkFDate=queryDBField(con,SqlUtil.getOnlyWorkTime(WorkFlcVo),"WorkFDate");
					//System.out.println("不能已有開始打卡時間 : WorkFDate"+WorkFDate);
					if(!WorkFDate.equals("")){
						return "o";
					}
					
					
					leaveCardVO WorkElcVo=new leaveCardVO();
					WorkElcVo.setStartLeaveDate(lcVo.getEndLeaveDate());
					WorkElcVo.setStartLeaveTime("05");
					WorkElcVo.setStartLeaveMinute("30");
					WorkElcVo.setEndLeaveDate(lcVo.getEndLeaveDate());
					WorkElcVo.setEndLeaveTime(lcVo.getEndLeaveTime());
					WorkElcVo.setEndLeaveMinute(lcVo.getEndLeaveTime());
					WorkElcVo.setSearchEmployeeNo(lcVo.getSearchEmployeeNo());
					//System.out.println("不能已有結束打卡時間 Sql: "+SqlUtil.getOnlyWorkTime(WorkElcVo));
					WorkFDate=queryDBField(con,SqlUtil.getOnlyWorkTime(WorkElcVo),"WorkFDate");
					//System.out.println("不能已有結束打卡時間 : WorkFDate"+WorkFDate);
					if(!WorkFDate.equals("")){
						return "o";
					}
					/**已請假不能再請**/
					WorkFDate=queryDBField(con,SqlUtil.getOnlyLeaveTime(lcVo),"STARTLEAVEDATE");
					if(!WorkFDate.equals("")){
						return "w";
					}
				}
				
			
				
				
				
				/**單天星期天不能請**/
			}
			try
			{
			
				PreparedStatement ps = con.prepareStatement(SqlUtil.saveLeaveCard(), Statement.RETURN_GENERATED_KEYS); 	
				ps.setString(1, lcVo.getSearchEmployeeNo());
				ps.setString(2, lcVo.getSearchHoliday());
				ps.setString(3, lcVo.getApplicationDate());
				ps.setString(4, lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute());
				ps.setString(5, lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute() );
				ps.setString(6, lcVo.getSearchAgent());
				ps.setString(7, lcVo.getNote());
				ps.setString(8, lcVo.getStatus());
				ps.setString(9, lcVo.getDayCount());
				ps.execute();
				ResultSet rs= ps.getGeneratedKeys();
				if (rs.next())
				{
					result = rs.getString("ID");
				}
				//System.out.println("LCID result : "+result);
			}
			catch (Exception Exception)
			{
				Log4jUtil lu=new Log4jUtil();
				Logger logger  =lu.initLog4j(DBUtil.class);
				logger.error(vnStringUtil.getExceptionAllinformation(Exception));
				return "x";
			}

			return result;
				
			}
		/**
		 * 由站台設定帳號人名搜尋單位資料
		 * @param con
		 * @param sql
		 * @param er
		 * @return
		 */
		public static List<employeeUserRO> queryUserList(Connection con, String sql,employeeUserRO er )
		{
			//System.out.println("sql  : "+sql);
			
			String result = "";
			PreparedStatement STMT = null;
			ReflectHelper rh=new ReflectHelper();
			List<employeeUserRO> lro=null;
			try
			{
				STMT = con.prepareStatement(sql);
				ResultSet rs = STMT.executeQuery();
				 lro=(List<employeeUserRO>)rh.getBean(rs,er);
			
			}
			catch (Exception error)
			{
				Log4jUtil lu=new Log4jUtil();
				Logger logger  =lu.initLog4j(DBUtil.class);
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
			
	
			String result="紀錄成功！";
			if ( (swVo.getSearchEmployeeNo() == null))
			{
				return "紀錄失敗！";
			}
			try
			{
			System.out.println("WorkDate : "+swVo.getStartStopWorkDate() + " " + swVo.getStartTimeHhmm() + ":00");
				PreparedStatement ps = con.prepareStatement(SqlUtil.saveStopWorking(), Statement.RETURN_GENERATED_KEYS); 	
				ps.setString(1, swVo.getSearchEmployeeNo());
				ps.setString(2, swVo.getAddDay());
				ps.setString(3, swVo.getNote());
				ps.setString(4, swVo.getStartStopWorkDate() + " " + swVo.getStartTimeHhmm() + ":00" );
				ps.setString(5, swVo.getEndStopWorkDate()+ " " + swVo.getEndTimeHhmm()+ ":00" );
				ps.setString(6, swVo.getSearchReasons());
				ps.execute();
				ResultSet rs= ps.getGeneratedKeys();
				if (rs.next())
				{
					//result = rs.getString("ID");
				}
				//System.out.println("LCID result : "+result);
			}
			catch (Exception Exception)
			{
				Log4jUtil lu=new Log4jUtil();
				Logger logger  =lu.initLog4j(DBUtil.class);
				logger.error(vnStringUtil.getExceptionAllinformation(Exception));
				result= "紀錄失敗！";
			}

			return result;
				
			}
		
		/**
		 * 查詢打卡資料
		 * @param con
		 * @param sql
		 * @param er
		 * @return
		 */
		public static List<yearMonthLateRO> queryLateList(Connection con, String sql,yearMonthLateRO er )
		{

			String result = "";
			PreparedStatement STMT = null;
			ReflectHelper rh=new ReflectHelper();
			List<yearMonthLateRO> leo=null;
			try
			{
				STMT = con.prepareStatement(sql);
				ResultSet rs = STMT.executeQuery();
				 leo=(List<yearMonthLateRO>)rh.getBean(rs,er);
				
			}
			catch (Exception error)
			{
				Log4jUtil lu=new Log4jUtil();
				Logger logger  =lu.initLog4j(DBUtil.class);
				logger.error(vnStringUtil.getExceptionAllinformation(error));
			}
			return leo;
		}
		
		/**
		 * 查詢日考勤報表資料
		 * @param con
		 * @param sql
		 * @param er
		 * @return
		 */
		public static List<repDailyRO> queryDailyList(Connection con, String sql,repDailyRO rd )
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			String result = "";
			PreparedStatement STMT = null;
			ReflectHelper rh=new ReflectHelper();
			List<repDailyRO> leo=null;
			try
			{
				STMT = con.prepareStatement(sql);
				ResultSet rs = STMT.executeQuery();
				 leo=(List<repDailyRO>)rh.getBean(rs,rd);
				
			}
			catch (Exception error)
			{
				logger.error(vnStringUtil.getExceptionAllinformation(error));
			}
			
			return leo;
		}
		
		/**
		 * 查詢月考勤報表資料
		 * @param con
		 * @param sql
		 * @param er
		 * @return
		 */
		public static List<repAttendanceRO> queryMonthAttendanceExcel(Connection con, String sql,repAttendanceRO rd )
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			String result = "";
			PreparedStatement STMT = null;
			ReflectHelper rh=new ReflectHelper();
			List<repAttendanceRO> leo=null;
			try
			{
				STMT = con.prepareStatement(sql);
				ResultSet rs = STMT.executeQuery();
				 leo=(List<repAttendanceRO>)rh.getBean(rs,rd);
				
			}
			catch (Exception error)
			{
				logger.error(vnStringUtil.getExceptionAllinformation(error));
			}
			
			return leo;
		}
		
		/**
		 * 查詢遲到早退名單表
		 * @param con
		 * @param sql
		 * @param er
		 * @return
		 */
		public static List<yearMonthLateRO> queryYmLateList(Connection con, String sql,yearMonthLateRO er )
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			String result = "";
			PreparedStatement STMT = null;
			ReflectHelper rh=new ReflectHelper();
			List<yearMonthLateRO> leo=null;
			try
			{
				STMT = con.prepareStatement(sql);
				ResultSet rs = STMT.executeQuery();
				 leo=(List<yearMonthLateRO>)rh.getBean(rs,er);
			
			}
			catch (Exception error)
			{
				logger.error(vnStringUtil.getExceptionAllinformation(error));
			}
			return leo;
		}
		
		
		/**
		 * 直接執行SQL 無參數 無返回
		 * @param con
		 * @param sql
		 * @return
		 */
		public static  String workLateOperationSql(Connection con, String sql )
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			String MID="";
			
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
		 * 查詢員工遲到早退打卡資料
		 * @param con
		 * @param sql
		 * @param er
		 * @return
		 */
		public static List<empLateEarlyRO> queryEmpLateEarlyList(Connection con, String sql,empLateEarlyRO lr )
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
			String result = "";
			PreparedStatement STMT = null;
			ReflectHelper rh=new ReflectHelper();
			List<empLateEarlyRO> leo=null;
			try
			{
				STMT = con.prepareStatement(sql);
				ResultSet rs = STMT.executeQuery();
				 leo=(List<empLateEarlyRO>)rh.getBean(rs,lr);
			
			}
			catch (Exception error)
			{
				logger.error(vnStringUtil.getExceptionAllinformation(error));
			}
			return leo;
		}
		
		/**
		 * 查詢全廠出勤sql
		 * @param con
		 * @param sql
		 * @param er
		 * @return
		 */
		public static List<repAttendanceDayRO> queryForeignCadres(Connection con, String sql,repAttendanceDayRO ra )
		{
			Log4jUtil lu=new Log4jUtil();
			Logger logger  =lu.initLog4j(DBUtil.class);
		//	logger.info("queryForeignCadres sql :"+sql);
			String result = "";
			PreparedStatement STMT = null;
			ReflectHelper rh=new ReflectHelper();
			List<repAttendanceDayRO> leo=null;
			try
			{
				STMT = con.prepareStatement(sql);
				ResultSet rs = STMT.executeQuery();
				 leo=(List<repAttendanceDayRO>)rh.getBean(rs,ra);
			
			}
			catch (Exception error)
			{
				logger.error(vnStringUtil.getExceptionAllinformation(error));
			}
			return leo;
		}
		
		
		
}
