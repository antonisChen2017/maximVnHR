package cn.com.maxim.portal.util;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.repAttendanceVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.attendan.wo.dayAttendanceWO;
import cn.com.maxim.potral.consts.sqlConsts;

public class SqlUtil
{

	public static final String getOvertime(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder("select  S.ID  \n")
				.append(" ,EP.EMPLOYEENO   as '工號'  \n")
				.append(" ,EP.EMPLOYEE  as '姓名'   \n")
				.append(" ,DT.DEPARTMENT as '部門'  \n")
				.append(" ,UT.UNIT as '單位'  \n")
				.append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期' \n")
				.append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始時間' \n")
				.append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束時間' \n")
				.append(" ,S.APPLICATION_HOURS as '總共小時' \n")
				.append("  ,(  \n")
				.append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0' \n")
				.append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)  \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_REASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end   \n")
				.append("  )  \n")
				.append("  as '加班事由'   \n")
				.append(" , S.NOTE  as '備註' \n")
				.append(" , S.MONTHOVERTIME  as 'MOTime' \n")
				.append(" , S.RETURNMSG  as 'returnMSG' \n")
				.append(" ,  S.STATUS   as 'action'  \n")
				.append("	from VN_OVERTIME_M AS M  \n")
				.append("	INNER JOIN \n")
				.append("	VN_OVERTIME_S AS S \n")
				.append("	ON M.ID = S.M_ID \n")
				.append("	INNER JOIN \n")
				.append("	(SELECT     *  FROM   VN_REASONS  Union All  select '0','','','') AS RS \n")
				.append("	 ON S.REASONS = RS.ID \n")
				.append("	INNER JOIN \n")
				.append("	VN_DEPARTMENT AS DT \n")
				.append("	 ON M.DEPARTMENT = DT.ID \n")
				.append("	 INNER JOIN \n")
				.append("	 VN_UNIT AS UT \n")
				.append("	ON S.UNIT = UT.ID \n")
				.append("  INNER JOIN \n")
				.append("  VN_EMPLOYEE AS EP \n")
				.append("	ON S. EP_ID = EP.ID \n")
				.append("	 where 1=1 \n");
		if (!otVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID = '" + otVo.getSearchEmployeeNo() + "' \n");
		}
		if (!otVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT = '" + otVo.getSearchDepartmen() + "' \n");
		}
		if (!otVo.getSearchUnit().equals("0"))
		{
			Sb.append(" AND S.UNIT ='" + otVo.getSearchUnit() + "' \n");
		}
		if (!otVo.getSearchReasons().equals("0"))
		{
			Sb.append(" AND S.REASONS = '" + otVo.getSearchReasons() + "' \n");
		}
		if (!otVo.getSubmitDate().equals(""))
		{
			Sb.append(" AND S.SUBMITDATE = '" + otVo.getSubmitDate() + "' \n");
		}
		Sb.append(" ORDER BY S.ID DESC \n");
		return Sb.toString();

	}

	public static final String getStopWork(stopWorkVO swVo)
	{

		StringBuilder Sb = new StringBuilder(" select  S.ID  \n");
		Sb.append(",EP.EMPLOYEENO   as '工號'   \n");
		Sb.append("	 ,EP.EMPLOYEE  as '姓名'    \n");
		Sb.append("	 ,DT.DEPARTMENT as '部門'   \n");
		Sb.append("	 ,UT.UNIT as '單位'     \n");
		Sb.append(" ,RS.STOPRESON as '待工原因'    \n");
		Sb.append("	 ,CONVERT(varchar(100), S.STARTSTOPDATE, 120) as '待料開始時間'  \n");
		Sb.append("	  ,CONVERT(varchar(100), S.ENDENDDATE, 120)    as '待料結束時間'  \n");
		Sb.append("	  ,S.DAYCOUNT as '總天數'   \n");
		Sb.append("	   , S.NOTE  as '備註'  \n");
		Sb.append(" ,  S.NOTE   as 'action'    \n");
		Sb.append("  from VN_STOPWORKING AS S   \n");
		Sb.append("  	INNER JOIN   \n");
		Sb.append("  VN_STOPWORKRESON AS RS   \n");
		Sb.append("  	 ON S.REASON_ID = RS.ID   \n");
		Sb.append("    INNER JOIN   \n");
		Sb.append("   VN_EMPLOYEE AS EP   \n");
		Sb.append("   ON S.EP_ID = EP.ID   \n");
		Sb.append("   INNER JOIN   \n");
		Sb.append("   VN_UNIT AS UT   \n");
		Sb.append("   ON EP.UNIT_ID = UT.ID   \n");
		Sb.append("    INNER JOIN   \n");
		Sb.append("   VN_DEPARTMENT AS DT  \n");
		Sb.append("   ON EP.DEPARTMENT_ID = DT.ID   \n");
		Sb.append("   where 1=1  \n");

		if (!swVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID = '" + swVo.getSearchEmployeeNo() + "' ");
		}
		if (!swVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND DT.ID = '" + swVo.getSearchDepartmen() + "' ");
		}
		if (!swVo.getSearchUnit().equals("0"))
		{
			// Sb.append(" AND S.UNIT ='"+otVo.getSearchUnit()+"' ");
		}
		if (!swVo.getSearchReasons().equals("0"))
		{
			Sb.append(" AND RS.ID = '" + swVo.getSearchReasons() + "' ");
		}
		// if(!swVo.getQueryDate().equals("")){
		// Sb.append(" AND S.SUBMITDATE = '"+otVo.getQueryDate()+"' ");
		// }
		Sb.append(" ORDER BY S.ID DESC ");
		return Sb.toString();

	}

	public static final String getOvertimeNoSave(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder("select  S.ID  \n")
				.append(" ,EP.EMPLOYEENO   as '工號'     \n")
				.append(" ,EP.EMPLOYEE  as '姓名'     \n")
				.append(" ,DT.DEPARTMENT as '部門'    \n")
				.append(" ,UT.UNIT as '單位'    \n")
				.append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期'   \n")
				.append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始時間'   \n")
				.append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束時間'   \n")
				.append(" ,S.APPLICATION_HOURS as '總共小時'   \n")
				.append("  ,(    \n")
				.append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0'   \n")
				.append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)    \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_REASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end     \n")
				.append("  )    \n")
				.append("  as '加班事由'     \n")
				.append(" , S.NOTE  as '備註'   \n")
				.append(" , S.MONTHOVERTIME  as 'MOTime'   \n")
				.append(" ,  S.STATUS   as 'action'    \n")
				.append("	from VN_OVERTIME_M AS M    \n")
				.append("	INNER JOIN   \n")
				.append("	VN_OVERTIME_S AS S \n")
				.append("	ON M.ID = S.M_ID \n")
				.append("	INNER JOIN \n")
				.append("	(SELECT     *  FROM   VN_REASONS  Union All  select '0','','','') AS RS \n")
				.append("	 ON S.REASONS = RS.ID \n")
				.append("	INNER JOIN \n")
				.append("	VN_DEPARTMENT AS DT \n")
				.append("	 ON M.DEPARTMENT = DT.ID \n")
				.append("	 INNER JOIN \n")
				.append("	 VN_UNIT AS UT \n")
				.append("	ON S.UNIT = UT.ID \n")
				.append("  INNER JOIN \n")
				.append("  VN_EMPLOYEE AS EP \n")
				.append("	ON S. EP_ID = EP.ID \n")
				.append("	 where 1=1 \n");
				
	    //是否超時加班
		Sb.append("	AND S.MONTHOVERTIME='"+otVo.getMonthOverTime()+"'  \n");
		
			
		if (!otVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT = '" + otVo.getSearchDepartmen() + "' \n");
		}
		if (otVo.getStartSubmitDate() != otVo.getEndSubmitDate())
		{
			Sb.append(" AND   S.SUBMITDATE BETWEEN '" + otVo.getStartSubmitDate() + " 00:00:00" + "'  AND '" + otVo.getEndSubmitDate() + " 23:59:59' \n");
		}

		if (!otVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID  = '" + otVo.getSearchEmployeeNo() + "' \n");
		}

		Sb.append(" AND S.STATUS <>'S' \n");
		Sb.append(" ORDER BY S.ID DESC \n");
		return Sb.toString();

	}

	public static final String getOvertimeTotalNoSave(String SearchDepartmen, String SearchUnit, String SearchReasons, String queryDate)
	{

		StringBuilder Sb = new StringBuilder("select  M.TOTAL_TIME  ").append("	from VN_OVERTIME_M AS M  ").append("	INNER JOIN ").append("	VN_OVERTIME_S AS S ").append("	ON M.ID = S.M_ID ").append("	INNER JOIN ").append("	VN_REASONS AS RS ").append("	 ON S.REASONS = RS.ID ").append("	INNER JOIN ").append("	VN_DEPARTMENT AS DT ").append("	 ON M.DEPARTMENT = DT.ID ").append("	 INNER JOIN ").append("	 VN_UNIT AS UT ").append("	ON S.UNIT = UT.ID ").append("	 where 1=1 ");

		if (!SearchDepartmen.equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT= '" + SearchDepartmen + "' ");
		}
		if (!SearchUnit.equals("0"))
		{
			Sb.append(" AND S.UNIT = '" + SearchUnit + "' ");
		}
		if (!SearchReasons.equals("0"))
		{
			Sb.append(" AND S.REASONS = '" + SearchReasons + "' ");
		}
		if (!queryDate.equals(""))
		{
			Sb.append(" AND S.SUBMITDATE = '" + queryDate + "' ");
		}
		Sb.append(" AND M.STATUS <>'S' ");

		return Sb.toString();

	}

	public static final String getOvertimeTotal(String SearchDepartmen, String SearchUnit, String SearchReasons, String queryDate)
	{

		StringBuilder Sb = new StringBuilder("select  M.TOTAL_TIME  ").append("	from VN_OVERTIME_M AS M  ").append("	INNER JOIN ").append("	VN_OVERTIME_S AS S ").append("	ON M.ID = S.M_ID ").append("	INNER JOIN ").append("	VN_REASONS AS RS ").append("	 ON S.REASONS = RS.ID ").append("	INNER JOIN ").append("	VN_DEPARTMENT AS DT ").append("	 ON M.DEPARTMENT = DT.ID ").append("	 INNER JOIN ").append("	 VN_UNIT AS UT ").append("	ON S.UNIT = UT.ID ").append("	 where 1=1 ");

		if (!SearchDepartmen.equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT LIKE '%" + SearchDepartmen + "%' ");
		}
		if (!SearchUnit.equals("0"))
		{
			Sb.append(" AND S.UNIT LIKE '%" + SearchUnit + "%' ");
		}
		if (!SearchReasons.equals("0"))
		{
			Sb.append(" AND S.REASONS LIKE '%" + SearchReasons + "%' ");
		}
		if (!queryDate.equals(""))
		{
			Sb.append(" AND M.DAY LIKE '%" + queryDate + "%' ");
		}
		return Sb.toString();

	}

	public static final String getOvertimeStatusNoSave(String SearchDepartmen, String queryDate)
	{

		StringBuilder Sb = new StringBuilder(" select  M.STATUS ").append(" from VN_OVERTIME_M AS M  ").append("	 where 1=1 ");
		if (!SearchDepartmen.equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT ='" + SearchDepartmen + "' ");
		}
		if (!queryDate.equals(""))
		{
			Sb.append(" AND M.DAY = '" + queryDate + "' ");
		}
		Sb.append(" AND M.STATUS <>'S' ");
		return Sb.toString();

	}

	public static final String getOvertimeStatus(String SearchDepartmen, String queryDate)
	{

		StringBuilder Sb = new StringBuilder(" select  M.STATUS ").append(" from VN_OVERTIME_M AS M  ").append("	 where 1=1 ");
		if (!SearchDepartmen.equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT ='" + SearchDepartmen + "' ");
		}
		if (!queryDate.equals(""))
		{
			Sb.append(" AND M.DAY = '" + queryDate + "' ");
		}
		return Sb.toString();

	}

	public static final String getOvertimeID(String SearchDepartmen, String queryDate)
	{
		StringBuilder Sb = new StringBuilder(" select  M.ID ").append(" from VN_OVERTIME_M AS M  ").append("	 where 1=1 ");
		if (!SearchDepartmen.equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT ='" + SearchDepartmen + "' ");
		}
		if (!queryDate.equals(""))
		{
			Sb.append(" AND M.DAY = '" + queryDate + "' ");
		}
		return Sb.toString();
	}

	public static final String getOvertimeMaxTime(String SearchDepartmen, String queryDate)
	{
		StringBuilder Sb = new StringBuilder(" select  M.TOTAL_TIME ").append(" from VN_OVERTIME_M AS M  ").append("	 where 1=1 ");
		if (!SearchDepartmen.equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT ='" + SearchDepartmen + "' ");
		}
		if (!queryDate.equals(""))
		{
			Sb.append(" AND M.DAY = '" + queryDate + "' ");
		}
		return Sb.toString();
	}

	public static final String setOvertimeS()
	{

		StringBuilder Sb = new StringBuilder(" INSERT INTO VN_OVERTIME_S	 ").append(" (M_ID,EP_ID, ").append(" APPLICATION_HOURS,OVERTIME_START, ").append(" OVERTIME_END,REASONS, ").append(" UNIT,NOTE ,STATUS,SAVETIME,USERREASONS,submitDate) ").append("  VALUES(?,?,?,?,?,?,?,?,?,getdate(),?,?) ");

		return Sb.toString();
	}

	public static final String setOvertimeM()
	{
		StringBuilder Sb = new StringBuilder(" INSERT INTO VN_OVERTIME_M	 ").append(" (DEPARTMENT,DAY) ").append("  VALUES(?,?) ");

		return Sb.toString();
	}

	public static final String setTotalTimeM(String newTotalTime, String ID)
	{
		StringBuilder Sb = new StringBuilder(" update VN_OVERTIME_M	 ").append(" set TOTAL_TIME='" + newTotalTime + "'  ").append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	public static final String delOvertimeS(String ID)
	{
		StringBuilder Sb = new StringBuilder(" DELETE FROM  VN_OVERTIME_S	 ").append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	public static final String delDBRow(String Table, String ID)
	{
		StringBuilder Sb = new StringBuilder(" DELETE FROM  " + Table + "	 ").append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	public static final String getOvertimeSTime(String ID)
	{
		StringBuilder Sb = new StringBuilder(" select  S.APPLICATION_HOURS ").append(" from VN_OVERTIME_S AS S  ").append("	 where 1=1 ");
		if (!ID.equals(""))
		{
			Sb.append(" AND S.ID = '" + ID + "' ");
		}
		return Sb.toString();
	}

	public static final String setStatusM(String Status, String ID)
	{
		StringBuilder Sb = new StringBuilder(" update VN_OVERTIME_M	 ").append(" set Status='" + Status + "'  ");
		if (Status.equals("U"))
		{
			Sb.append(" SUBMITTIME='getdate()'  ");
		}
		if (Status.equals("I"))
		{
			Sb.append(" REVIEWTIME='getdate()'  ");
		}
		if (Status.equals("R"))
		{
			Sb.append(" RETURNTIME='getdate()'  ");
		}
		Sb.append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	public static final String getEmp2(String Login)
	{
		StringBuilder Sb = new StringBuilder(" select EMP_NO1 from VH_USER		 ");
		Sb.append("  where Login='" + Login + "' ");

		return Sb.toString();
	}

	/**
	 * 計算是否超過當月加班時間
	 * 
	 * @return
	 */
	public static final String getAPPhoursflag(String ID, String yyyy, String mm)
	{
		StringBuilder Sb = new StringBuilder(" declare @Res varchar(10)		 ");
		Sb.append("  select @Res = ");
		Sb.append("  case  when (   (  SELECT  sum( convert(int,APPLICATION_HOURS)) as maxah FROM VN_OVERTIME_S WHERE EP_ID=" + ID + " ");
		Sb.append("   and DATEPART(yyyy,SAVETIME) ='" + yyyy + "' and DATEPART ( mm ,SAVETIME )='" + mm + "' )    >    (select VALUE from VN_CONFIG where [key]='CTIME')");
		Sb.append("    )  then '1' else '0' end  select @Res as flag ");
		return Sb.toString();
	}

	public static final String setMonthOverTimes(String flag, String ID)
	{
		StringBuilder Sb = new StringBuilder(" update VN_OVERTIME_S	 ").append(" set MONTHOVERTIME='" + flag + "'  ").append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	public static final String getOvertimeSID(String SearchDepartmen, String queryDate)
	{
		StringBuilder Sb = new StringBuilder(" select  M.ID ").append(" from VN_OVERTIME_M AS M  ").append("	 where 1=1 ");
		if (!SearchDepartmen.equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT ='" + SearchDepartmen + "' ");
		}
		if (!queryDate.equals(""))
		{
			Sb.append(" AND M.DAY = '" + queryDate + "' ");
		}
		return Sb.toString();
	}

	public static final String updateMonthOverTims(String flag, String ID)
	{
		StringBuilder Sb = new StringBuilder(" update VN_OVERTIME_S	 ").append(" set MONTHOVERTIME='" + flag + "'  ").append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	/**
	 * 如果自填加班理由且未選擇下拉加班理由 改顯示自填加班理由
	 * 
	 * @param ID
	 * @return
	 */
	public static final String changeReasons(String ID)
	{
		StringBuilder Sb = new StringBuilder(" declare @Res nvarchar(10)       ").append(" select @Res =   ").append("  case  when ( (  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=" + ID + ") =  '5'    ").append("		)  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=" + ID + ")     ").append("	else (SELECT RS.REASONS FROM VN_OVERTIME_S as S INNER JOIN VN_REASONS AS RS ON S.REASONS = RS.ID WHERE S.ID=" + ID + " ) end  select @Res as flag    ");
		return Sb.toString();
	}

	public static final String getVnEmployee(String ID, String subSql)
	{
		// StringBuilder Sb = new StringBuilder(" select V.UNIT_ID ")
		StringBuilder Sb = new StringBuilder(" select " + subSql).append(" from VN_EMPLOYEE AS V ").append("	 where 1=1 ");
		if (!ID.equals(""))
		{
			Sb.append(" AND V.ID = '" + ID + "' ");
		}
		return Sb.toString();
	}

	/**
	 * 加班狀態修改 狀態
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String setStatusS(String Status, String ID)
	{
		StringBuilder Sb = new StringBuilder(" update VN_OVERTIME_S	 ").append(" set Status='" + Status + "' , ");
		if (Status.equals("U"))
		{ // 提交
			Sb.append(" SUBMITTIME=getdate()  ");
		}
		if (Status.equals("I"))
		{ // 審核ok
			Sb.append(" REVIEWTIME=getdate()  ");
		}
		if (Status.equals("R"))
		{// 退回
			Sb.append(" RETURNTIME=getdate()  ");
		}
		Sb.append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	/**
	 * 加班狀態修改 與退回訊息
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String setStatusReturnMsg(String returnMsg, String ID)
	{
		StringBuilder Sb = new StringBuilder(" update VN_OVERTIME_S	 ").append(" set RETURNMSG=N'" + returnMsg + "'  ");
		Sb.append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	/**
	 * 請假卡-新增
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String saveLeaveCard()
	{

		StringBuilder Sb = new StringBuilder(" INSERT INTO VN_LEAVECARD	 ").append(" (EP_ID, HD_ID, ").append("  APPLICATIONDATE,STARTLEAVEDATE, ").append(" ENDLEAVEDATE,AGENT, ").append(" NOTE,STATUS,").append(" DAYCOUNT,SAVETIME) ").append("  VALUES(?,?,?,?,?,?,?,?,?,getdate()) ");

		return Sb.toString();
	}

	/**
	 * 查詢請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  ").append("  EP.EMPLOYEE  as '姓名',   ").append("  EP.EMPLOYEENO  as '工號',   ").append("  C.APPLICATIONDATE  as '申請日期',  ").append("  HD.HOLIDAYNAME  as '假別',  ").append("  DAYCOUNT as  天數,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始時間', ").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束時間', ").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '備註', ").append("  C.STATUS   as 'action' ,  ").append("  C.RETURNMSG  as 'returnMSG' ").append("  FROM VN_LEAVECARD  AS C  ").append("  INNER JOIN      ").append("  VN_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  ").append("  INNER JOIN   ").append("  VN_HOLIDAY AS HD      ON C.HD_ID =HD.ID ").append("   INNER JOIN     ")
				.append("   VN_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID ").append("   where 1=1 ");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' ");
		}
		Sb.append(" ORDER BY C.ID DESC ");

		return Sb.toString();

	}

	/**
	 * 請假卡-修改狀態
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String upLCStatus(String Status, String ID)
	{
		StringBuilder Sb = new StringBuilder(" update VN_LEAVECARD	 ").append(" set Status='" + Status + "' , ");
		if (Status.equals("T"))
		{ // 提交
			Sb.append(" SUBMITTIME=getdate()  ");
		}

		if (Status.equals("UR"))
		{// 單位退回
			Sb.append(" RETURNTIME=getdate()  ");
		}
		if (Status.equals("DR"))
		{// 部門退回
			Sb.append(" RETURNTIME=getdate()  ");
		}
		if (Status.equals("LR"))
		{// 副總退回
			Sb.append(" RETURNTIME=getdate()  ");
		}
		if (Status.equals("MR"))
		{// 管理部退回
			Sb.append(" RETURNTIME=getdate()  ");
		}
		if (Status.equals("U"))
		{ // 單位主管審核ok
			Sb.append(" U_REVIEWTIME=getdate()  ");
		}
		if (Status.equals("D"))
		{ // 部門主管審核ok
			Sb.append(" D_REVIEWTIME=getdate()  ");
		}
		if (Status.equals("L"))
		{ // 副總審核ok
			Sb.append(" B_REVIEWTIME=getdate()  ");
		}
		if (Status.equals("M"))
		{ // 管理部審核ok
			Sb.append(" B_REVIEWTIME=getdate()  ");
		}
		Sb.append("  where ID='" + ID + "' ");

		return Sb.toString();
	}

	/**
	 * 使用系統人名帶出員工資料
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String getEmployeeNameDate(String name)
	{

		StringBuilder Sb = new StringBuilder(" SELECT ")
				.append(" E.ID ,U.ID as UID,ENTRYDATE,DUTIES,U.UNIT,D.DEPARTMENT,E.EMPLOYEENO,E.DEPARTMENT_ID as DID ")
				.append(" FROM VN_EMPLOYEE as E ")
				.append("	INNER JOIN ")
				.append("		VN_DEPARTMENT AS D  ")
				.append("		ON D.ID = E.DEPARTMENT_ID ")
				.append("		INNER JOIN ")
				.append("		VN_UNIT AS U  ")
				.append("		ON U.ID = E.UNIT_ID ");
		if (!name.equals(""))
		{
			Sb.append("   where EMPLOYEE LIKE N'%" + name + "%' ");
		}
		return Sb.toString();
	}

	/**
	 * 單位主管查詢請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getUnitLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  ").append("  EP.EMPLOYEE  as '姓名',   ").append("  EP.EMPLOYEENO  as '工號',   ").append("  C.APPLICATIONDATE  as '申請日期',  ").append("  HD.HOLIDAYNAME  as '假別',  ").append("  DAYCOUNT as  天數,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始時間', ").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束時間', ").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '備註', ").append("  C.STATUS   as 'action' , ").append("  C.RETURNMSG  as 'returnMSG' ").append("  FROM VN_LEAVECARD  AS C  ").append("  INNER JOIN      ").append("  VN_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  ").append("  INNER JOIN   ").append("  VN_HOLIDAY AS HD      ON C.HD_ID =HD.ID ").append("   INNER JOIN     ")
				.append("   VN_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID ").append("   where 1=1 ");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' ");
		}
		if (!lcVo.getStartLeaveDate().equals(lcVo.getEndLeaveDate()))
		{
			Sb.append(" AND   C.APPLICATIONDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59' \n");
		}
		if (!lcVo.getSearchUnit().equals("0"))
		{
			Sb.append(" and EP.UNIT_ID ='" + lcVo.getSearchUnit() + "' ");
		}
		Sb.append(" AND C.STATUS <>'S' ");
	
		Sb.append(" AND C.STATUS <>'UR' ");
		//Sb.append(" AND C.STATUS <>'DR' ");
		//Sb.append(" AND C.STATUS <>'LR' ");
	//	Sb.append(" AND C.STATUS <>'MR' ");
		Sb.append(" ORDER BY C.ID DESC ");

		return Sb.toString();

	}

	/**
	 * 部門主管查詢請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getDepartmenLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  ")
				.append("  EP.EMPLOYEE  as '姓名',   ")
				.append("  EP.EMPLOYEENO  as '工號',   ").append("  C.APPLICATIONDATE  as '申請日期',  ").append("  HD.HOLIDAYNAME  as '假別',  ").append("  DAYCOUNT as  天數,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始時間', ").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束時間', ").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '備註', ").append("  C.STATUS   as 'action' , ").append("  C.RETURNMSG  as 'returnMSG' ").append("  FROM VN_LEAVECARD  AS C  ").append("  INNER JOIN      ").append("  VN_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  ").append("  INNER JOIN   ").append("  VN_HOLIDAY AS HD      ON C.HD_ID =HD.ID ").append("   INNER JOIN     ")
				.append("   VN_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID ").append("   where 1=1 ");
		if (!lcVo.getSearchEmployeeNo().equals("0") )
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' ");
		}
		if (lcVo.getStartLeaveDate() != lcVo.getEndLeaveDate())
		{
			Sb.append(" AND   C.APPLICATIONDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59' \n");
		}
		if (!lcVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" and EP.DEPARTMENT_ID ='" + lcVo.getSearchDepartmen() + "' ");
		}

		Sb.append(" AND C.STATUS <>'T' ");
		Sb.append(" AND C.STATUS <>'S' ");
		Sb.append(" AND C.STATUS <>'UR' ");
		//Sb.append(" AND C.STATUS <>'DR' ");
		//Sb.append(" AND C.STATUS <>'LR' ");
		//Sb.append(" AND C.STATUS <>'MR' ");
		Sb.append(" ORDER BY C.ID DESC ");

		return Sb.toString();

	}

	/**
	 * 管理部查詢三天以下請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getMLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  ").append("  EP.EMPLOYEE  as '姓名',   ").append("  EP.EMPLOYEENO  as '工號',   ").append("  C.APPLICATIONDATE  as '申請日期',  ").append("  HD.HOLIDAYNAME  as '假別',  ").append("  DAYCOUNT as  天數,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始時間', ").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束時間', ").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '備註', ").append("  C.STATUS   as 'action' , ").append("  C.RETURNMSG  as 'returnMSG' ").append("  FROM VN_LEAVECARD  AS C  ").append("  INNER JOIN      ").append("  VN_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  ").append("  INNER JOIN   ").append("  VN_HOLIDAY AS HD      ON C.HD_ID =HD.ID ").append("   INNER JOIN     ")
				.append("   VN_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID ").append("   where 1=1 ");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' ");
		}
		if (lcVo.getStartLeaveDate() != lcVo.getEndLeaveDate())
		{
			Sb.append(" AND   C.APPLICATIONDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59' \n");
		}
		if (!lcVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" and EP.DEPARTMENT_ID ='" + lcVo.getSearchDepartmen() + "' ");
		}
		Sb.append(" AND DAYCOUNT <'3' ");
		Sb.append(" AND C.STATUS <>'T' ");
		Sb.append(" AND C.STATUS <>'S' ");
		Sb.append(" AND C.STATUS <>'UR' ");
		Sb.append(" AND C.STATUS <>'DR' ");
		Sb.append(" AND C.STATUS <>'LR' ");
		Sb.append(" AND C.STATUS <>'MR' ");
		Sb.append(" ORDER BY C.ID DESC ");

		return Sb.toString();

	}

	/**
	 * 副總查詢三天以上請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getLLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  ").append("  EP.EMPLOYEE  as '姓名',   ").append("  EP.EMPLOYEENO  as '工號',   ").append("  C.APPLICATIONDATE  as '申請日期',  ").append("  HD.HOLIDAYNAME  as '假別',  ").append("  DAYCOUNT as  天數,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始時間', ").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束時間', ").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '備註', ").append("  C.STATUS   as 'action' , ").append("  C.RETURNMSG  as 'returnMSG' ").append("  FROM VN_LEAVECARD  AS C  ").append("  INNER JOIN      ").append("  VN_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  ").append("  INNER JOIN   ").append("  VN_HOLIDAY AS HD      ON C.HD_ID =HD.ID ").append("   INNER JOIN     ")
				.append("   VN_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID ").append("   where 1=1 ");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' ");
		}
		if (lcVo.getStartLeaveDate() != lcVo.getEndLeaveDate())
		{
			Sb.append(" AND   C.APPLICATIONDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59' \n");
		}
		if (!lcVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" and EP.DEPARTMENT_ID ='" + lcVo.getSearchDepartmen() + "' ");
		}
		Sb.append(" and cast(C.DAYCOUNT as float) >= 3 ");
		Sb.append(" AND C.STATUS <>'T' ");
		Sb.append(" AND C.STATUS <>'S' ");
		Sb.append(" AND C.STATUS <>'M' ");
		Sb.append(" AND C.STATUS <>'U' ");
		Sb.append(" AND C.STATUS <>'UR' ");
		Sb.append(" AND C.STATUS <>'DR' ");
		Sb.append(" AND C.STATUS <>'LR' ");
		Sb.append(" AND C.STATUS <>'MR' ");
		Sb.append(" ORDER BY C.ID DESC ");

		return Sb.toString();

	}

	/**
	 * 請假卡-改變狀態與修改退回訊息
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String updateLcStatus(leaveCardVO lcVo)
	{
		StringBuilder Sb = new StringBuilder(" update VN_LEAVECARD	 ").append(" set  Status='" + lcVo.getStatus() + "'  ");
		
		if (lcVo.getStatus().equals("L"))
		{
			Sb.append(" , SAVETIME=getdate()  ");
		}
		if (lcVo.getStatus().equals("M"))
		{
			Sb.append(" , SAVETIME=getdate()  ");
		}
		if (!lcVo.getReturnMsg().equals(""))
		{
			Sb.append(" , RETURNMSG=N'" + lcVo.getReturnMsg() + "' ");
		}
	
		Sb.append("  where ID='" + lcVo.getRowID() + "' ");

		return Sb.toString();
	}

	/**
	 * 單位查部門
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String getUnitDT(String unitID)
	{
		StringBuilder Sb = new StringBuilder(" SELECT DEPARTMENT_ID ").append(" FROM VN_UNIT ").append("  where ID='" + unitID + "' ");

		return Sb.toString();
	}

	public static final String saveStopWorking()
	{

		StringBuilder Sb = new StringBuilder(" INSERT INTO VN_STOPWORKING	 ").append(" (EP_ID,DAYCOUNT, ").append(" NOTE,STARTSTOPDATE, ").append(" ENDENDDATE,REASON_ID, ").append(" CREATEDAY) ").append("  VALUES(?,?,?,?,?,?,getdate()) ");

		return Sb.toString();
	}

	/**
	 * 每日考勤表
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getdailyReport(leaveCardVO lcVo)
	{
		HtmlUtil hu=new HtmlUtil();
		String sql="";
		//遲到名單
		sql=hu.gethtml(sqlConsts.sql_rep_daily);
		sql=sql.replace("<FDate/>", lcVo.getApplicationDate());
		sql=sql.replace("<DEPARTMENT/>", lcVo.getSearchDepartmen());
		sql=sql.replace("<UNIT/>", lcVo.getSearchUnit());
		return sql;
	}

	/**
	 * 月份考勤表-建立資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getMonthReport(Connection con,repAttendanceVO raVo)
	{
		HtmlUtil hu=new HtmlUtil();
		String sql="";
		//遲到名單
		sql=hu.gethtml(sqlConsts.sql_month_Attendance);
		sql=sql.replace("<year/>", raVo.getQueryYearMonth().split("/")[0]);
		sql=sql.replace("<Month/>",raVo.getQueryYearMonth().split("/")[1]);
		sql=sql.replace("<DEPARTMENT_ID/>",raVo.getSearchDepartmen());
		sql=sql.replace("<UNIT_ID/>",raVo.getSearchUnit());
		HrFileUtil.writeStr(sql, "D:/out_sql_month_Attendance.txt");
		DBUtil.workLateOperationSql(con,sql);
		return  getvnMonthAttendance(raVo);
	}
	
	/**
	 * 月份考勤表-查出資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getvnMonthAttendance(repAttendanceVO raVo)
	{
	
		
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql.append(" EMPLOYEENO as 工號 ,  \n");
		tableSql.append(" EMPLOYEE as 姓名,  \n");
		tableSql.append(" DAY1 as '1'  ,  \n");
		tableSql.append(" DAY2 as '2'  ,  \n");
		tableSql.append(" DAY3 as '3'  ,  \n");
		tableSql.append(" DAY4 as '4'  ,  \n");
		tableSql.append(" DAY5 as '5'  ,  \n");
		tableSql.append(" DAY6 as '6'  ,  \n");
		tableSql.append(" DAY7 as '7'  ,  \n");
		tableSql.append(" DAY8 as '8'  ,  \n");
		tableSql.append(" DAY9 as '9'  , \n");
		tableSql.append(" DAY10 as '10'  ,  \n");
		tableSql.append(" DAY11 as '11'  ,  \n");
		tableSql.append(" DAY12 as '12'  ,  \n");
		tableSql.append(" DAY13 as '13'  ,  \n");
		tableSql.append(" DAY14 as '14'  ,  \n");
		tableSql.append(" DAY15 as '15'  ,  \n");
		tableSql.append(" DAY16 as '16'  ,  \n");
		tableSql.append(" DAY17 as '17'  ,  \n");
		tableSql.append(" DAY18 as '18' ,  \n");
		tableSql.append(" DAY19 as '19' ,  \n");
		tableSql.append(" DAY20 as '20' ,  \n");
		tableSql.append(" DAY21 as '21' ,  \n");
		tableSql.append(" DAY22 as '22' ,  \n");
		tableSql.append(" DAY23 as '23' ,  \n");
		tableSql.append(" DAY24 as '24' ,  \n");
		tableSql.append(" DAY25 as '25' ,  \n");
		tableSql.append(" DAY26 as '26' ,  \n");
		tableSql.append(" DAY27 as '27' ,  \n");
		tableSql.append(" DAY28 as '28' ,  \n");
		tableSql.append(" DAY29 as '29' ,  \n");
		tableSql.append(" DAY30 as '30' ,  \n");
		tableSql.append(" DAY31 as '31' ,   \n");
		tableSql.append(" NOTE as '總共'   \n");
		tableSql.append(" FROM  VN_YEAR_MONTH_ATTENDANCE   where YEAR='"+raVo.getQueryYearMonth().split("/")[0]+"'    \n"); 
		tableSql.append(" and  MONTH='"+raVo.getQueryYearMonth().split("/")[1]+"'    \n"); 
		tableSql.append(" and  DEPARTMENT='"+raVo.getSearchDepartmen()+"'    \n"); 
		tableSql.append(" and  UNIT='"+raVo.getSearchUnit()+"'    \n"); 		
		return  tableSql.toString();
	}
	
	/**
	 * 
	 */
	
	/**
	 * 個人查閱遲到早退and打卡時間
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getEmplateOutEarly(Connection con,lateOutEarlyVO erVo)
	{

		HtmlUtil hu=new HtmlUtil();
		String sql="",IsLate="";
		//遲到名單
				if(erVo.getQueryIsLate().equals("1")){
					IsLate="1";
				}
				if(erVo.getQueryIsLate().equals("2")){
					IsLate="0";
				}
		    sql=hu.gethtml(sqlConsts.sql_emp_IsQueryLate);
			sql=sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
			sql=sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
			sql=sql.replace("<isLate/>",IsLate);
			sql=sql.replace("<EMPLOYEENO/>",erVo.getEmpID());
			String LATE=DBUtil.queryDBField(con,sql,"LATE");
			if(Integer.valueOf(LATE)==0){
					if(erVo.getQueryIsLate().equals("1")){
						sql=hu.gethtml(sqlConsts.sql_empLate);
						
						sql=sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
						sql=sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
						sql=sql.replace("<EmpID/>", erVo.getEmpID());
				
						DBUtil.workLateOperationSql(con,sql);
					}
					if(erVo.getQueryIsLate().equals("2")){
						sql=hu.gethtml(sqlConsts.sql_empEarly);
					
						sql=sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
						sql=sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
						sql=sql.replace("<EmpID/>", erVo.getEmpID());
				
						DBUtil.workLateOperationSql(con,sql);
					}
			}
		
		return getEmpYMLate(erVo);
	}
	
	/**
	 * 遲到早退名單
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getlateOutEarly(Connection con,lateOutEarlyVO erVo)
	{

		HtmlUtil hu=new HtmlUtil();
		String sql="",IsLate="";
		//遲到名單
		if(erVo.getQueryIsLate().equals("1")){
			IsLate="1";
		}
		if(erVo.getQueryIsLate().equals("2")){
			IsLate="0";
		}
		 sql=hu.gethtml(sqlConsts.sql_lateIsQuery);
			sql=sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
			sql=sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
			sql=sql.replace("<isLate/>",IsLate);
			String LATE=DBUtil.queryDBField(con,sql,"LATE");
		if(Integer.valueOf(LATE)==0){
			if(erVo.getQueryIsLate().equals("1")){
				sql=hu.gethtml(sqlConsts.sql_yearMonthLate);
				sql=sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
				sql=sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
				DBUtil.workLateOperationSql(con,sql);
			}
			if(erVo.getQueryIsLate().equals("2")){
				sql=hu.gethtml(sqlConsts.sql_yesrMonthEarly);
				sql=sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
				sql=sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
				DBUtil.workLateOperationSql(con,sql);
			}
			
		}
		return getYMLATE(erVo);
	}

	/**
	 * 查詢遲到早退名單年月區分
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getYMLATE(lateOutEarlyVO erVo)
	{
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql.append(" EMPLOYEENO as 工號 ,  \n");
		tableSql.append(" EMPLOYEE as 姓名,  \n");
		tableSql.append(" DEPARTMENT as 部門,  \n");
		tableSql.append(" UNIT as 單位 ,  \n");
		tableSql.append(" DAY1 as '1'  ,  \n");
		tableSql.append(" DAY2 as '2'  ,  \n");
		tableSql.append(" DAY3 as '3'  ,  \n");
		tableSql.append(" DAY4 as '4'  ,  \n");
		tableSql.append(" DAY5 as '5'  ,  \n");
		tableSql.append(" DAY6 as '6'  ,  \n");
		tableSql.append(" DAY7 as '7'  ,  \n");
		tableSql.append(" DAY8 as '8'  ,  \n");
		tableSql.append(" DAY9 as '9'  , \n");
		tableSql.append(" DAY10 as '10'  ,  \n");
		tableSql.append(" DAY11 as '11'  ,  \n");
		tableSql.append(" DAY12 as '12'  ,  \n");
		tableSql.append(" DAY13 as '13'  ,  \n");
		tableSql.append(" DAY14 as '14'  ,  \n");
		tableSql.append(" DAY15 as '15'  ,  \n");
		tableSql.append(" DAY16 as '16'  ,  \n");
		tableSql.append(" DAY17 as '17'  ,  \n");
		tableSql.append(" DAY18 as '18' ,  \n");
		tableSql.append(" DAY19 as '19' ,  \n");
		tableSql.append(" DAY20 as '20' ,  \n");
		tableSql.append(" DAY21 as '21' ,  \n");
		tableSql.append(" DAY22 as '22' ,  \n");
		tableSql.append(" DAY23 as '23' ,  \n");
		tableSql.append(" DAY24 as '24' ,  \n");
		tableSql.append(" DAY25 as '25' ,  \n");
		tableSql.append(" DAY26 as '26' ,  \n");
		tableSql.append(" DAY27 as '27' ,  \n");
		tableSql.append(" DAY28 as '28' ,  \n");
		tableSql.append(" DAY29 as '29' ,  \n");
		tableSql.append(" DAY30 as '30' ,  \n");
		tableSql.append(" DAY31 as '31' ,  \n");
		tableSql.append(" MINUTE as 分 ,  \n"); 
		tableSql.append(" HOUR as 點 ,  \n"); 
		if(erVo.getQueryIsLate().equals("1")){
			tableSql.append(" LATETIMES as 遲到次數   \n"); 
		}	
		if(erVo.getQueryIsLate().equals("2")){
			tableSql.append(" LATETIMES as 早退次數   \n"); 
		}
		tableSql.append(" FROM  VN_YEAR_MONTH_LATE   where YEAR='"+erVo.getQueryYearMonth().split("/")[0]+"'    \n"); 
		tableSql.append(" and  MONTH='"+erVo.getQueryYearMonth().split("/")[1]+"'    \n"); 
		if(erVo.getQueryIsLate().equals("1")){
			tableSql.append(" and ISLATE='1'    \n"); 
		}	
		if(erVo.getQueryIsLate().equals("2")){
			tableSql.append(" and ISLATE='0'   \n"); 
		}
		
		
		return tableSql.toString();
	}
	
	
	
	/**
	 * 查詢員工個人遲到早退列表
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getEmpYMLate(lateOutEarlyVO erVo)
	{
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql.append(" WT1,  \n");
		tableSql.append(" WT2   ,  \n");
		tableSql.append(" WT3   ,  \n");
		tableSql.append(" WT4   ,  \n");
		tableSql.append(" WT5   ,  \n");
		tableSql.append(" WT6  ,  \n");
		tableSql.append(" WT7 ,  \n");
		tableSql.append(" WT8  ,  \n");
		tableSql.append(" WT9   , \n");
		tableSql.append(" WT10   ,  \n");
		tableSql.append(" WT11   ,  \n");
		tableSql.append(" WT12   ,  \n");
		tableSql.append(" WT13  ,  \n");
		tableSql.append(" WT14   ,  \n");
		tableSql.append(" WT15   ,  \n");
		tableSql.append(" WT16   ,  \n");
		tableSql.append(" WT17   ,  \n");
		tableSql.append(" WT18  ,  \n");
		tableSql.append(" WT19  ,  \n");
		tableSql.append(" WT20  ,  \n");
		tableSql.append(" WT21  ,  \n");
		tableSql.append(" WT22  ,  \n");
		tableSql.append(" WT23  ,  \n");
		tableSql.append(" WT24 ,  \n");
		tableSql.append(" WT25 ,  \n");
		tableSql.append(" WT26 ,  \n");
		tableSql.append(" WT27  ,  \n");
		tableSql.append(" WT28  ,  \n");
		tableSql.append(" WT29  ,  \n");
		tableSql.append(" WT30  ,  \n");
		tableSql.append(" WT31  ,  \n");
		tableSql.append(" MINUTE ,  \n"); 
		tableSql.append(" HOUR  ,  \n"); 
		if(erVo.getQueryIsLate().equals("1")){
			tableSql.append(" LATETIMES    \n"); 
		}	
		if(erVo.getQueryIsLate().equals("2")){
			tableSql.append(" LATETIMES    \n"); 
		}
		tableSql.append(" FROM  VN_EMP_LATE   where YEAR='"+erVo.getQueryYearMonth().split("/")[0]+"'    \n"); 
		tableSql.append(" and  MONTH='"+erVo.getQueryYearMonth().split("/")[1]+"'    \n"); 
		if(erVo.getQueryIsLate().equals("1")){
			tableSql.append(" and ISLATE='1'    \n"); 
		}	
		if(erVo.getQueryIsLate().equals("2")){
			tableSql.append(" and ISLATE='0'   \n"); 
		}
		tableSql.append(" and EMPLOYEENO='"+erVo.getEmpID()+"'   \n"); 
		
		return tableSql.toString();
	}
	
	
	/**
	 * 查詢遲到早退名單年月區分-報表
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getLateOutEarlyExcelSql(lateOutEarlyVO erVo)
	{
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql.append(" ROW_NUMBER() over(order by EMPLOYEENO) as ID,  \n");
		tableSql.append(" EMPLOYEENO ,  \n");
		tableSql.append(" EMPLOYEE ,  \n");
		tableSql.append(" DEPARTMENT ,  \n");
		tableSql.append(" UNIT  ,  \n");
		tableSql.append(" DAY1   ,  \n");
		tableSql.append(" DAY2  ,  \n");
		tableSql.append(" DAY3  ,  \n");
		tableSql.append(" DAY4  ,  \n");
		tableSql.append(" DAY5   ,  \n");
		tableSql.append(" DAY6  ,  \n");
		tableSql.append(" DAY7  ,  \n");
		tableSql.append(" DAY8  ,  \n");
		tableSql.append(" DAY9  , \n");
		tableSql.append(" DAY10   ,  \n");
		tableSql.append(" DAY11   ,  \n");
		tableSql.append(" DAY12   ,  \n");
		tableSql.append(" DAY13  ,  \n");
		tableSql.append(" DAY14  ,  \n");
		tableSql.append(" DAY15   ,  \n");
		tableSql.append(" DAY16   ,  \n");
		tableSql.append(" DAY17   ,  \n");
		tableSql.append(" DAY18  ,  \n");
		tableSql.append(" DAY19  ,  \n");
		tableSql.append(" DAY20  ,  \n");
		tableSql.append(" DAY21  ,  \n");
		tableSql.append(" DAY22  ,  \n");
		tableSql.append(" DAY23 ,  \n");
		tableSql.append(" DAY24  ,  \n");
		tableSql.append(" DAY25  ,  \n");
		tableSql.append(" DAY26  ,  \n");
		tableSql.append(" DAY27  ,  \n");
		tableSql.append(" DAY28  ,  \n");
		tableSql.append(" DAY29 ,  \n");
		tableSql.append(" DAY30  ,  \n");
		tableSql.append(" DAY31  ,  \n");
		tableSql.append(" MINUTE  ,  \n"); 
		tableSql.append(" HOUR  ,  \n"); 
		tableSql.append(" LATETIMES    \n"); 
	
		tableSql.append(" FROM  VN_YEAR_MONTH_LATE   where YEAR='"+erVo.getQueryYearMonth().split("/")[0]+"'    \n"); 
		tableSql.append(" and  MONTH='"+erVo.getQueryYearMonth().split("/")[1]+"'    \n"); 
		if(erVo.getQueryIsLate().equals("1")){
			tableSql.append(" and ISLATE='1'    \n"); 
		}	
		if(erVo.getQueryIsLate().equals("2")){
			tableSql.append(" and ISLATE='0'   \n"); 
		}
	
		
		
		return tableSql.toString();
	}
	
	/**
	 * 部門員工名單
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getDept(leaveCardVO lcVo)
	{

		HtmlUtil hu=new HtmlUtil();
		String sql="";
		//遲到名單
		
		 sql=hu.gethtml(sqlConsts.sql_dept);
		 sql=sql.replace("<DEPTID/>", lcVo.getSearchDepartmen());

		return sql;
	}
	/**
	 * 部門員工名單
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getEhm(overTimeVO otVo)
	{
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql .append(" ehm  \n");
		tableSql .append(" FROM VN_TURN  \n");
		tableSql .append( " WHERE CODE='"+otVo.getOverTimeClass()+"' \n");
		return tableSql.toString();
	}
	
	/**
	 * 日考勤表excel Sql
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getDailyExcelSql(leaveCardVO lcVo )
	{
		
		HtmlUtil hu=new HtmlUtil();
		String sql="";
		//遲到名單
		
		 sql=hu.gethtml(sqlConsts.sql_excelDaily);
		 sql=sql.replace("<FDate/>", lcVo.getApplicationDate());
		 sql=sql.replace("<DEPARTMENT/>", lcVo.getSearchDepartmen());
		 sql=sql.replace("<UNIT/>", lcVo.getSearchUnit());

		return sql;
	}
	
	
	/**
	 * 月考勤表excel Sql
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getvnMonthAttendanceExcel(repAttendanceVO raVo)
	{
	
		
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql.append(" ROW_NUMBER() over(order by EMPLOYEENO) as ID,  \n");
		tableSql.append(" EMPLOYEENO  ,  \n");
		tableSql.append(" EMPLOYEE ,  \n");
		tableSql.append(" DAY1  ,  \n");
		tableSql.append(" DAY2 ,  \n");
		tableSql.append(" DAY3 ,  \n");
		tableSql.append(" DAY4   ,  \n");
		tableSql.append(" DAY5   ,  \n");
		tableSql.append(" DAY6  ,  \n");
		tableSql.append(" DAY7  ,  \n");
		tableSql.append(" DAY8   ,  \n");
		tableSql.append(" DAY9   , \n");
		tableSql.append(" DAY10   ,  \n");
		tableSql.append(" DAY11   ,  \n");
		tableSql.append(" DAY12   ,  \n");
		tableSql.append(" DAY13   ,  \n");
		tableSql.append(" DAY14   ,  \n");
		tableSql.append(" DAY15 ,  \n");
		tableSql.append(" DAY16   ,  \n");
		tableSql.append(" DAY17   ,  \n");
		tableSql.append(" DAY18 ,  \n");
		tableSql.append(" DAY19 ,  \n");
		tableSql.append(" DAY20 ,  \n");
		tableSql.append(" DAY21 ,  \n");
		tableSql.append(" DAY22 ,  \n");
		tableSql.append(" DAY23  ,  \n");
		tableSql.append(" DAY24  ,  \n");
		tableSql.append(" DAY25 ,  \n");
		tableSql.append(" DAY26  ,  \n");
		tableSql.append(" DAY27  ,  \n");
		tableSql.append(" DAY28  ,  \n");
		tableSql.append(" DAY29 ,  \n");
		tableSql.append(" DAY30  ,  \n");
		tableSql.append(" DAY31  ,   \n");
		tableSql.append(" NOTE   \n");
		tableSql.append(" FROM  VN_YEAR_MONTH_ATTENDANCE   where YEAR='"+raVo.getQueryYearMonth().split("/")[0]+"'    \n"); 
		tableSql.append(" and  MONTH='"+raVo.getQueryYearMonth().split("/")[1]+"'    \n"); 
		tableSql.append(" and  DEPARTMENT='"+raVo.getSearchDepartmen()+"'    \n"); 
		tableSql.append(" and  UNIT='"+raVo.getSearchUnit()+"'    \n"); 		
		return  tableSql.toString();
	}

	/**
	 * 月份考勤表-修改統計資料
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String upRepAttendance(repAttendanceVO raVo)
	{
		StringBuilder Sb = new StringBuilder(" update VN_YEAR_MONTH_ATTENDANCE	 ").append(" set  NOTE='" + raVo.getNOTE()+ "'  ");
		Sb.append("  where EMPLOYEENO='" +raVo.getEMPLOYEENO() + "' ");
		Sb.append("  and YEAR='" +raVo.getQueryYearMonth().split("/")[0] + "' ");
		Sb.append("  and MONTH='" +raVo.getQueryYearMonth().split("/")[1] + "' ");
		return Sb.toString();
	}
	
	/**
	 * 查詢請假開始時間或結束時間有無在打卡區間時間內
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String getOnlyWorkTime(leaveCardVO lcVo)
	{
		StringBuilder Sb = new StringBuilder(" select WorkFDate,WorkEDate   from   PWERP_MS.dbo.RsKQResult R,PWERP_MS.dbo.RsEmployee E ,hr.dbo.VN_EMPLOYEE V ");
		Sb.append("  where   V.EMPLOYEENO=E.EmpID ");
		Sb.append("    and V.ID='"+lcVo.getSearchEmployeeNo()+"'  " );
		Sb.append(" and WorkFDate <='"+lcVo.getStartLeaveDate().replaceAll("/", "")+" "+lcVo.getStartLeaveTime()+":"+lcVo.getStartLeaveMinute()+"' ");
		Sb.append(" and WorkEDate >=  '"+lcVo.getEndLeaveDate().replaceAll("/", "")+" "+lcVo.getEndLeaveTime()+":"+lcVo.getEndLeaveMinute()+"' ");
			
		return Sb.toString();
	}
	
	/**
	 * 查詢請假開始時間有無請假
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String getOnlyLeaveTime(leaveCardVO lcVo)
	{
		StringBuilder Sb = new StringBuilder("  select STARTLEAVEDATE from hr.dbo.VN_LEAVECARD V ");
		Sb.append("	where V.EP_ID='"+lcVo.getSearchEmployeeNo()+"'  " );
		Sb.append(" and STARTLEAVEDATE <= '"+lcVo.getStartLeaveDate().replaceAll("/", "")+" "+lcVo.getStartLeaveTime()+":"+lcVo.getStartLeaveMinute()+"' ");
		Sb.append("  Union All ");
		Sb.append("  select STARTLEAVEDATE from hr.dbo.VN_LEAVECARD V ");
		Sb.append("	where V.EP_ID='"+lcVo.getSearchEmployeeNo()+"'  " );
		Sb.append(" and ENDLEAVEDATE <= '"+lcVo.getEndLeaveDate().replaceAll("/", "")+" "+lcVo.getEndLeaveTime()+":"+lcVo.getEndLeaveMinute()+"' ");

		return Sb.toString();
	}
	
	
	/**
	 * 外籍幹部人數
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getNoVnMaxPeople(leaveCardVO lcVo)
	{
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_noVnMaxPeople);
		sql=sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}
	
	/**
	 * 越籍前一天應出勤人數
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getYestoDayVnMaxPeople(leaveCardVO lcVo) throws ParseException
	{
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_VnMaxPeople);
		Date date = sdf.parse( lcVo.getApplicationDate().replaceAll("/", "") );
		date=DateUtil.addDays(date, -1);
		
		sql=sql.replace("<toDay>", sdf.format(date));
		return sql;
	}
	
	/**
	 * 越籍今天應出勤人數
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getVnMaxPeople(leaveCardVO lcVo) throws ParseException
	{
	
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_VnMaxPeople);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}
	
	/**
	 * 越籍今天實際出勤人數
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getActualMaxPeople(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_VnActualPeople);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		//logger.info("getActualMaxPeople sql :"+sql);
		return sql;
	}
	
	/**
	 * 行政班次
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getTurnA1People(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnTurnA1People);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		//logger.info("getActualMaxPeople sql :"+sql);
		return sql;
	}
	/**
	 * 早班次
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getTurnC1People(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnTurnC1People);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		//logger.info("getActualMaxPeople sql :"+sql);
		return sql;
	}
	/**
	 * 中班次
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getTurnC2People(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnTurnC2People);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		//logger.info("getActualMaxPeople sql :"+sql);
		return sql;
	}
	/**
	 * 夜班次
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getTurnC3People(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnTurnC3People);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		//logger.info("getActualMaxPeople sql :"+sql);
		return sql;
	}
	
	/**
	 * 加班申請
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getOverTimePeople(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnOverTimePeople);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));

		return sql;
	}
	
	/**
	 * 部門查詢
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getDeptID(String UserEmployeeNo)
	{
		String sql="select ID from VN_DEPARTMENT where DEPARTMENT =N'" + UserEmployeeNo + "'";
		return sql;
	}
	
	/**
	 * 假別查詢
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getVnHolidayPeople(leaveCardVO lcVo,String hdID) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnOverTimePeople);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		sql=sql.replace("<hdID>",hdID);
		return sql;
	}
	/**
	 * 曠工查詢
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getVnAbsenteeismPeople(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnAbsenteeismPeople);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}
	
	/**
	 * 新人報道查詢
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getVnInDatePeople(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu=new Log4jUtil();
		Logger logger  =lu.initLog4j(SqlUtil.class);
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnInDatePeople);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}
	
	/**
	 * 離職查詢
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getvnNoLeavePeople(leaveCardVO lcVo) throws ParseException
	{
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnNoLeavePeople);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}
	
	/**
	 * 1年以下查詢
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getVnLessYear(leaveCardVO lcVo,String sqlConsts) throws ParseException
	{
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts);
		sql=sql.replace("<toDay>",lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}
	/**
	 * 新增全廠報表(一行)
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String saveAttendanceDayRow(dayAttendanceWO dw) throws ParseException
	{
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnInsertAttendanceDay);
		sql=sql.replace("<YMD>",dw.getYMD());
		sql=sql.replace("<DEPARTMENT>",dw.getDEPARTMENT());
		          sql=sql.replace("<UNIT>",dw.getUNIT());
		          sql=sql.replace("<ROW>",dw.getROW());
		          sql=sql.replace("<C1>",dw.getC1());
		          sql=sql.replace("<C2>",dw.getC2());
		          sql=sql.replace("<C3>",dw.getC3());
		          sql=sql.replace("<C4>",dw.getC4());
		          sql=sql.replace("<C5>",dw.getC5());
		      //    System.out.println("c5 sql="+sql);
		          sql=sql.replace("<C6>",dw.getC6());
		          sql=sql.replace("<C7>",dw.getC7());
		          sql=sql.replace("<C8>",dw.getC8());
		          sql=sql.replace("<C9>",dw.getC9());
		          sql=sql.replace("<C10>",dw.getC10());
		     //     System.out.println("c10 sql="+sql);
		          sql=sql.replace("<C11>",dw.getC11());
		          sql=sql.replace("<C12>",dw.getC12());
		          sql=sql.replace("<C13>",dw.getC13());
		          sql=sql.replace("<C14>",dw.getC14());
		          sql=sql.replace("<C15>",dw.getC15());
		      //    System.out.println("c15 sql="+sql);
		          sql=sql.replace("<C16>",dw.getC16());
		          System.out.println("c16 ");
		          sql=sql.replace("<C17>",dw.getC17());
		          System.out.println("c17 ");
		          sql=sql.replace("<C18>",dw.getC18());
		          System.out.println("c18 ");
		          sql=sql.replace("<C19>",dw.getC19());
		          System.out.println("c19 ");
		          sql=sql.replace("<C20>",dw.getC20());
		          System.out.println("c20 sql="+sql);
		       //   sql=sql.replace("<C21/>",dw.getC21());
		       //   sql=sql.replace("<C22/>",dw.getC22());
		       
	
		return sql;
	}
	
	/**
	 * 查詢全廠報表
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getAttendanceDay(String YMD) throws ParseException
	{
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnGetAttendanceDay);
		sql=sql.replace("<YMD>",YMD);
		return sql;
	}
	/**
	 * 查詢全廠報表excel
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException 
	 */
	public static final String getExcelAttendanceDay(String YMD) throws ParseException
	{
		HtmlUtil hu=new HtmlUtil();
		String sql=hu.gethtml(sqlConsts.sql_vnGetExcelAttendanceDay);
		sql=sql.replace("<YMD>",YMD);
		return sql;
	}
}
