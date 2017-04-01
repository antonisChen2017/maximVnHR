package cn.com.maxim.portal.util;

import java.sql.Connection;

import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;

public class SqlUtil
{

	public static final String getOvertime(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder("select  S.ID  ").append(" ,EP.EMPLOYEENO   as '工號'   ").append(" ,EP.EMPLOYEE  as '姓名'   ").append(" ,DT.DEPARTMENT as '部門'  ").append(" ,UT.UNIT as '單位'  ").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期' ").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始時間' ").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束時間' ").append(" ,S.APPLICATION_HOURS as '總共小時' ").append("  ,(  ").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='5' ").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)  ").append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_REASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end   ").append("  )  ")
				.append("  as '加班事由'   ").append(" , S.NOTE  as '備註' ").append(" , S.MONTHOVERTIME  as 'MOTime' ").append(" , S.RETURNMSG  as 'returnMSG' ").append(" ,  S.STATUS   as 'action'  ").append("	from VN_OVERTIME_M AS M  ").append("	INNER JOIN ").append("	VN_OVERTIME_S AS S ").append("	ON M.ID = S.M_ID ").append("	INNER JOIN ").append("	VN_REASONS AS RS ").append("	 ON S.REASONS = RS.ID ").append("	INNER JOIN ").append("	VN_DEPARTMENT AS DT ").append("	 ON M.DEPARTMENT = DT.ID ").append("	 INNER JOIN ").append("	 VN_UNIT AS UT ").append("	ON S.UNIT = UT.ID ").append("  INNER JOIN ").append("  VN_EMPLOYEE AS EP ").append("	ON S. EP_ID = EP.ID ").append("	 where 1=1 ");
		if (!otVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID = '" + otVo.getSearchEmployeeNo() + "' ");
		}
		if (!otVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT = '" + otVo.getSearchDepartmen() + "' ");
		}
		if (!otVo.getSearchUnit().equals("0"))
		{
			Sb.append(" AND S.UNIT ='" + otVo.getSearchUnit() + "' ");
		}
		if (!otVo.getSearchReasons().equals("5"))
		{
			Sb.append(" AND S.REASONS = '" + otVo.getSearchReasons() + "' ");
		}
		if (!otVo.getQueryDate().equals(""))
		{
			Sb.append(" AND S.SUBMITDATE = '" + otVo.getQueryDate() + "' ");
		}
		Sb.append(" ORDER BY S.ID DESC ");
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

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'     \n").append(" ,EP.EMPLOYEE  as '姓名'     \n").append(" ,DT.DEPARTMENT as '部門'    \n").append(" ,UT.UNIT as '單位'    \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始時間'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束時間'   \n").append(" ,S.APPLICATION_HOURS as '總共小時'   \n").append("  ,(    \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='5'   \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)    \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_REASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end     \n").append("  )    \n").append("  as '加班事由'     \n").append(" , S.NOTE  as '備註'   \n").append(" , S.MONTHOVERTIME  as 'MOTime'   \n").append(" ,  S.STATUS   as 'action'    \n").append("	from VN_OVERTIME_M AS M    \n").append("	INNER JOIN   \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	VN_REASONS AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n").append("	 VN_UNIT AS UT \n").append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n")
				.append("  VN_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");

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

		StringBuilder Sb = new StringBuilder(" SELECT ").append(" E.ID ,U.ID as UID,ENTRYDATE,DUTIES,U.UNIT,D.DEPARTMENT,E.EMPLOYEENO,E.DEPARTMENT_ID as DID ").append(" FROM VN_EMPLOYEE as E ").append("	INNER JOIN ").append("		VN_DEPARTMENT AS D  ").append("		ON D.ID = E.DEPARTMENT_ID ").append("		INNER JOIN ").append("		VN_UNIT AS U  ").append("		ON U.ID = E.UNIT_ID ");
		if (!name.equals(""))
		{
			Sb.append("   where EMPLOYEE =N'" + name + "' ");
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
		Sb.append(" AND C.STATUS <>'DR' ");
		Sb.append(" AND C.STATUS <>'LR' ");
		Sb.append(" AND C.STATUS <>'MR' ");
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
		Sb.append(" AND DAYCOUNT >= '3' ");
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

		StringBuilder Sb = new StringBuilder("  SELECT  \n");
		Sb.append(" CONVERT(varchar(100),  DAY, 111) AS 日期,  \n");
		Sb.append(" SOON  AS 工號, \n");
		Sb.append(" DemonstrateTeam  AS 部門 ,  \n");
		Sb.append("  TanComplete 姓名,   \n");
		Sb.append("  WaitForTheCardToDo  AS  上班打卡時間,  \n");
		Sb.append("  HoldTheCardDown  AS  下班打卡時間,  \n");
		Sb.append("  HowToMakeMajority AS 出勤時數, \n");
		Sb.append("  TheTimeToIncreaseTheShift AS 晚班時數,  \n");
		Sb.append(" CONVERT(varchar(100),  DAY, 23)+' '+HoursOfCaShift as overtime  \n");
		Sb.append(" FROM VN_DETAIL AS D  \n");
		Sb.append(" INNER JOIN     \n");
		Sb.append(" VN_DEPARTMENT AS VD   \n");
		Sb.append(" ON VD.DEPARTMENT = D.DemonstrateTeam  \n");
		Sb.append(" where 1=1  \n");
		if (!lcVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" and VD.ID ='" + lcVo.getSearchDepartmen() + "'  \n");
		}
		// Sb.append(" AND VD.ID='15' ");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND D.SOON='" + lcVo.getSearchEmployeeNo() + "'  \n");
		}
		// Sb.append(" AND D.SOON='13062304' ");

		return Sb.toString();
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
		 sql=hu.gethtml(UrlUtil.sql_lateIsQuery);
			sql=sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
			sql=sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
			sql=sql.replace("<isLate/>",IsLate);
			String LATE=DBUtil.queryDBField(con,sql,"LATE");
		if(Integer.valueOf(LATE)==0){
			if(erVo.getQueryIsLate().equals("1")){
				sql=hu.gethtml(UrlUtil.sql_yearMonthLate);
				sql=sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
				sql=sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
				DBUtil.workLateOperationSql(con,sql);
			}
			if(erVo.getQueryIsLate().equals("2")){
				sql=hu.gethtml(UrlUtil.sql_yesrMonthEarly);
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
		String sql="",IsLate="";
		//遲到名單
		
		 sql=hu.gethtml(UrlUtil.sql_dept);
		 sql=sql.replace("<DEPTID/>", lcVo.getSearchDepartmen());

		return sql;
	}
}
