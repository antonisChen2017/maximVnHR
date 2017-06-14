package cn.com.maxim.portal.util;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.dayReportRO;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.vo.editDeptUnit;
import cn.com.maxim.portal.attendan.vo.editLholidayVO;
import cn.com.maxim.portal.attendan.vo.editLreasonsVO;
import cn.com.maxim.portal.attendan.vo.editStopReasonVO;
import cn.com.maxim.portal.attendan.vo.editSupervisorVO;
import cn.com.maxim.portal.attendan.vo.editSupplementVO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.vo.repAttendanceVO;
import cn.com.maxim.portal.attendan.vo.stopWorkVO;
import cn.com.maxim.portal.attendan.wo.dayAttendanceWO;
import cn.com.maxim.portal.attendan.wo.monthReportNoteWO;
import cn.com.maxim.portal.attendan.wo.monthSumDataWo;
import cn.com.maxim.portal.hr.rep_daily;
import cn.com.maxim.potral.consts.keyConts;
import cn.com.maxim.potral.consts.sqlConsts;

public class SqlUtil
{

	public static final String getExceedOvertime(overTimeVO otVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'  \n").append(" ,EP.EMPLOYEE  as '姓名'   \n").append(" ,DT.DEPARTMENT as '部门'  \n").append(" ,UT.UNIT as '单位'  \n").append(" ,EP.ROLE  \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期' \n").append("  ,(  \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0' \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)  \n").append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end   \n").append("  )  \n").append("  as '加班事由'   \n").append(" , S.NOTE  as '備註' \n").append(" , S.MONTHOVERTIME  as 'MOTime' \n").append(" , S.RETURNMSG  as 'returnMSG' \n")
				.append(" ,  S.STATUS   as 'action'  \n").append("	from VN_OVERTIME_M AS M  \n").append("	INNER JOIN \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n").append("	 VN_UNIT AS UT \n").append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");
		Sb.append("  AND S.MONTHOVERTIME='1'  \n");

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

		Sb.append(" ORDER BY S.ID DESC \n");

		return Sb.toString();

	}

	/**
	 * 超時人員查詢列表
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getExceedOvertimeC(overTimeVO otVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'  \n").append(" ,EP.EMPLOYEE  as '姓名'   \n").append(" ,DT.DEPARTMENT as '部门'  \n").append(" ,UT.UNIT as '单位'  \n").append(" ,EP.ROLE  \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期' \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始时间' \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束时间' \n").append(" ,S.APPLICATION_HOURS as '总共小时' \n").append("  ,(  \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0' \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)  \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end   \n").append("  )  \n").append("  as '加班事由'   \n").append(" , S.NOTE  as '備註' \n").append(" , S.MONTHOVERTIME  as 'MOTime' \n").append(" , S.RETURNMSG  as 'returnMSG' \n").append(" ,  S.STATUS   as 'action'  \n").append("	from VN_OVERTIME_M AS M  \n").append("	INNER JOIN \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n")
				.append("	 VN_UNIT AS UT \n").append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n").append("  AND S.MONTHOVERTIME='1'  \n").append(" and DATEPART(yyyy,OVERTIME_START) ='" + otVo.getQueryYearMonth().split("/")[0] + "'   \n").append("  and DATEPART ( mm ,OVERTIME_START )='" + otVo.getQueryYearMonth().split("/")[1] + "'   \n");
		Sb.append(" ORDER BY S.ID DESC \n");
		return Sb.toString();

	}

	public static final String getOvertime(overTimeVO otVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'  \n").append(" ,EP.EMPLOYEE  as '姓名'   \n").append(" ,DT.DEPARTMENT as '部门'  \n").append(" ,UT.UNIT as '单位'  \n").append(" ,EP.ROLE  \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期' \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始时间' \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束时间' \n").append(" ,S.APPLICATION_HOURS as '总共小时' \n").append("  ,(  \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0' \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)  \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end   \n").append("  )  \n").append("  as '加班事由'   \n").append(" , S.NOTE  as '備註' \n").append(" , S.MONTHOVERTIME  as 'MOTime' \n").append(" , S.RETURNMSG  as 'returnMSG' \n").append(" ,  S.STATUS   as 'action'  \n").append("	from VN_OVERTIME_M AS M  \n").append("	INNER JOIN \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n")
				.append("	 VN_UNIT AS UT \n").append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");
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
		// if (!otVo.getSearchReasons().equals("0"))
		// {
		// Sb.append(" AND S.REASONS = '" + otVo.getSearchReasons() + "' \n");
		// }
		// if (!otVo.getSubmitDate().equals(""))
		// {
		// Sb.append(" AND S.SUBMITDATE = '" + otVo.getSubmitDate() + "' \n");
		// }
		Sb.append(" ORDER BY S.ID DESC \n");

		return Sb.toString();

	}

	/**
	 * 人事功能 超時加班查询
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getrev_ExceedOvertime(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder(" select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'  \n").append(" ,EP.EMPLOYEE  as '姓名'   \n").append(" ,DT.DEPARTMENT as '部门'  \n").append(" ,UT.UNIT as '单位'  \n").append(" ,EP.ROLE  \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期' \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始时间' \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束时间' \n").append(" ,S.APPLICATION_HOURS as '总共小时' \n").append("  ,(  \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0' \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)  \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end   \n").append("  )  \n").append("  as '加班事由'   \n").append(" , S.NOTE  as '備註' \n").append(" , S.MONTHOVERTIME  as 'MOTime' \n").append(" , S.RETURNMSG  as 'returnMSG' \n").append(" ,  S.STATUS   as 'action'  \n").append("	from VN_OVERTIME_M AS M  \n").append("	INNER JOIN \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n")
				.append("	 VN_UNIT AS UT \n").append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");
		// 超時加班
		// Sb.append(" AND S.MONTHOVERTIME='1' \n");
		// 副總通過
		// Sb.append(" AND S.STATUS='UB' \n");
		// 时间區間
		// if
		// (!otVo.getStartQueryDate().trim().equals(otVo.getEndQueryDate().trim()))
		// {
		// Sb.append(" AND S.SUBMITDATE BETWEEN '" + otVo.getStartQueryDate() +
		// " 00:00:00" + "' AND '" + otVo.getEndQueryDate() + " 23:59:59' \n");
		// }

		Sb.append(" ORDER BY S.ID DESC \n");

		return Sb.toString();

	}

	/**
	 * 人事功能 符合超時加班人員查询
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getrev_Condition(overTimeVO otVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_overTimeCondition);
		sql = sql.replace("<YEAR/>", otVo.getQueryYearMonth().split("/")[0]);
		sql = sql.replace("<MONTH/>", otVo.getQueryYearMonth().split("/")[1]);

		return sql;
	}

	/**
	 * 人事功能 超時加班查询-取得編輯資料
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getExOvertimeRO(String rowID)
	{

		StringBuilder Sb = new StringBuilder(" select  S.ID  \n").append("  ,M.ID as MID    \n").append("  ,EP.ID as 'EMPLOYEENO'     \n").append("  ,EP.ID as 'EMPLOYEE'   \n").append("  ,DT.ID  as 'DEPARTMENT'  \n").append("  ,UT.ID as 'UNIT'  \n").append("  ,EP.ROLE  \n").append("  ,CONVERT(varchar(100),  S.SUBMITDATE, 111)  as 'SUBMITDATE' \n").append("  ,CONVERT(varchar(100), S.OVERTIME_START, 120) as 'OVERTIMESTART' \n").append("  ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as 'OVERTIMEEND' \n").append("  ,S.APPLICATION_HOURS as 'APPLICATION_HOURS' \n").append("  ,S.REASONS  \n").append("  ,S.USERREASONS  \n").append("  ,S.TURN  \n").append("  ,S.NOTE  \n").append("	from VN_OVERTIME_M AS M  \n").append("	INNER JOIN \n").append("	VN_OVERTIME_S AS S \n")
				.append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n").append("	 VN_UNIT AS UT \n").append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");

		if (!rowID.trim().equals(""))
		{
			Sb.append(" AND S.ID='" + rowID + "'  \n");
		}

		Sb.append(" ORDER BY S.ID DESC \n");

		return Sb.toString();

	}

	public static final String getStopWork(stopWorkVO swVo)
	{

		StringBuilder Sb = new StringBuilder(" select  S.ID  \n");
		Sb.append(",EP.EMPLOYEENO   as '工號'   \n");
		Sb.append("	 ,EP.EMPLOYEE  as '姓名'    \n");
		Sb.append("	 ,DT.DEPARTMENT as '部门'   \n");
		Sb.append("	 ,UT.UNIT as '单位'     \n");
		Sb.append(" ,RS.STOPRESON as '待工原因'    \n");
		Sb.append("	 ,CONVERT(varchar(100), S.STARTSTOPDATE, 120) as '待料開始时间'  \n");
		Sb.append("	  ,CONVERT(varchar(100), S.ENDENDDATE, 120)    as '待料結束时间'  \n");
		Sb.append("	  ,S.DAYCOUNT as '總天數'   \n");
		Sb.append("	   , S.NOTE  as '備註'  \n");
		Sb.append(" ,  S.NOTE   as 'action'    \n");
		Sb.append("  from VN_STOPWORKING AS S   \n");
		Sb.append("  	INNER JOIN   \n");
		Sb.append("  VN_STOPWORKRESON AS RS   \n");
		Sb.append("  	 ON S.REASON_ID = RS.ID   \n");
		Sb.append("    INNER JOIN   \n");
		Sb.append("   HR_EMPLOYEE AS EP   \n");
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

	/**
	 * 部门查询加班
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getOvertimeDept(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'     \n").append(" ,EP.EMPLOYEE  as '姓名'     \n").append(" ,DT.DEPARTMENT as '部门'    \n").append(" ,UT.UNIT as '单位'    \n").append(" ,EP.ROLE    \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始时间'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束时间'   \n").append(" ,S.APPLICATION_HOURS as '总共小时'   \n").append("  ,(    \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0'   \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)    \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end     \n").append("  )    \n").append("  as '加班事由'     \n").append(" , S.NOTE  as '備註'   \n").append("  , (  \n").append("   case  when ((  S.MONTHOVERTIME ) ='0'      \n").append("  )  then '加班'       \n").append("  else '超時加班' end        \n").append("   )  as '加班狀態'     \n").append(" , S.MONTHOVERTIME  as 'MOTime'   \n").append(" ,  S.STATUS   as 'action'    \n").append("	from VN_OVERTIME_M AS M    \n").append("	INNER JOIN   \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n")
				.append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n").append("	 VN_UNIT AS UT \n").append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");

		// 是否超時加班
		// Sb.append(" AND S.MONTHOVERTIME='"+otVo.getMonthOverTime()+"' \n");

		if (!otVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT = '" + otVo.getSearchDepartmen() + "' \n");
		}
		if (!otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND   S.SUBMITDATE BETWEEN '" + otVo.getStartSubmitDate() + " 00:00:00" + "'  AND '" + otVo.getEndSubmitDate() + " 23:59:59' \n");
		}
		if (otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND     CONVERT(varchar(100),  S.SUBMITDATE, 111) = '" + otVo.getStartSubmitDate() + "' \n");
		}

		if (!otVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID  = '" + otVo.getSearchEmployeeNo() + "' \n");
		}

		// Sb.append(" AND (EP.ROLE='M' AND S.STATUS='T' OR EP.ROLE='E' AND
		// S.STATUS='UT' OR EP.ROLE='E' AND S.STATUS='UD' ) \n");

		/** 已審核或退回 **/

		Sb.append(" AND S.STATUS <>'S' \n");
		Sb.append(" AND S.STATUS <>'UR'  \n");
		Sb.append(" AND not (S.STATUS ='T' AND EP.ROLE ='D')  \n");
		Sb.append("  AND not (S.STATUS='B' AND EP.ROLE ='M')   \n");
		Sb.append(" AND not (S.STATUS ='T' AND EP.ROLE ='E')   \n");

		Sb.append(" ORDER BY S.ID DESC \n");
		return Sb.toString();

	}

	/**
	 * 經理查询加班
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getLLOvertime(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'     \n").append(" ,EP.EMPLOYEE  as '姓名'     \n").append(" ,DT.DEPARTMENT as '部门'    \n").append(" ,UT.UNIT as '单位'    \n").append(" ,EP.ROLE    \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始时间'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束时间'   \n").append(" ,S.APPLICATION_HOURS as '总共小时'   \n").append("  ,(    \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0'   \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)    \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end     \n").append("  )    \n").append("  as '加班事由'     \n").append(" , S.NOTE  as '備註'   \n").append(" , S.MONTHOVERTIME  as 'MOTime'   \n").append(" ,  S.STATUS   as 'action'    \n").append("	from VN_OVERTIME_M AS M    \n").append("	INNER JOIN   \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n").append("	 VN_UNIT AS UT \n")
				.append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");

		// 是否超時加班
		// Sb.append(" AND S.MONTHOVERTIME='"+otVo.getMonthOverTime()+"' \n");

		if (!otVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT = '" + otVo.getSearchDepartmen() + "' \n");
		}
		if (!otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND   S.SUBMITDATE BETWEEN '" + otVo.getStartSubmitDate() + " 00:00:00" + "'  AND '" + otVo.getEndSubmitDate() + " 23:59:59' \n");
		}
		if (otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND     CONVERT(varchar(100),  S.SUBMITDATE, 111) = '" + otVo.getStartSubmitDate() + "' \n");
		}

		if (!otVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID  = '" + otVo.getSearchEmployeeNo() + "' \n");
		}
		/** 未審核 **/
		if (otVo.getStatus().equals("U"))
		{
			// Sb.append(" AND EP.ROLE NOT IN ('E','U','D','M') \n");
			Sb.append(" AND (EP.ROLE='D' AND S.STATUS='T')  \n");
		}
		/** 已審核或退回 **/
		if (otVo.getStatus().equals("I"))
		{
			Sb.append(" AND S.STATUS <>'S' \n");
			Sb.append(" AND S.STATUS <>'UR'  \n");
			Sb.append(" AND not (S.STATUS ='T' AND EP.ROLE ='D')  \n");
			Sb.append("  AND not (S.STATUS='B' AND EP.ROLE ='M')   \n");
			Sb.append(" AND not (S.STATUS ='T' AND EP.ROLE ='E')   \n");
			Sb.append(" AND not (S.STATUS ='T' AND EP.ROLE ='M')   \n");

		}

		Sb.append(" ORDER BY S.ID DESC \n");
		return Sb.toString();

	}

	/**
	 * 副總查询加班
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getBOvertime(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'     \n").append(" ,EP.EMPLOYEE  as '姓名'     \n").append(" ,DT.DEPARTMENT as '部门'    \n").append(" ,UT.UNIT as '单位'    \n").append(" ,EP.ROLE    \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始时间'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束时间'   \n").append(" ,S.APPLICATION_HOURS as '总共小时'   \n").append("  ,(    \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0'   \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)    \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end     \n").append("  )    \n").append("  as '加班事由'     \n").append(" , S.NOTE  as '備註'   \n").append(", ( \n").append("  case  when ((  S.MONTHOVERTIME ) ='0'    \n").append(" )  then '加班'     \n").append(" else '超時加班' end      \n").append("  )  as '加班狀態'   \n").append(" , S.MONTHOVERTIME  as 'MOTime'   \n").append(" ,  S.STATUS   as 'action'    \n").append("	from VN_OVERTIME_M AS M    \n").append("	INNER JOIN   \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n")
				.append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n").append("	 VN_UNIT AS UT \n").append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");

		// 是否超時加班
		// Sb.append(" AND S.MONTHOVERTIME='"+otVo.getMonthOverTime()+"' \n");

		if (!otVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT = '" + otVo.getSearchDepartmen() + "' \n");
		}
		if (!otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND   S.SUBMITDATE BETWEEN '" + otVo.getStartSubmitDate() + " 00:00:00" + "'  AND '" + otVo.getEndSubmitDate() + " 23:59:59' \n");
		}
		if (otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND     CONVERT(varchar(100),  S.SUBMITDATE, 111) = '" + otVo.getStartSubmitDate() + "' \n");
		}

		if (!otVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID  = '" + otVo.getSearchEmployeeNo() + "' \n");
		}
		/** 未審核 **/
		if (otVo.getStatus().equals("U"))
		{

			Sb.append(" AND ( EP.ROLE='M' AND S.STATUS='T' or   EP.ROLE='E' AND S.STATUS='UD' )  \n");
		}
		/** 已審核或退回 **/
		if (otVo.getStatus().equals("I"))
		{
			Sb.append(" AND S.STATUS <>'S' \n");
			Sb.append(" AND S.STATUS <>'UR'  \n");
			Sb.append(" AND not (S.STATUS ='T' AND EP.ROLE ='D')  \n");
			Sb.append(" AND not (S.STATUS ='T' AND EP.ROLE ='E')   \n");
			Sb.append(" AND not (EP.ROLE='M' AND S.STATUS='T')  \n");
		}

		Sb.append(" ORDER BY S.ID DESC \n");
		return Sb.toString();

	}

	/**
	 * 加班單查询
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getOvertimeNoSave(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'     \n").append(" ,EP.EMPLOYEE  as '姓名'     \n").append(" ,DT.DEPARTMENT as '部门'    \n").append(" ,UT.UNIT as '单位'    \n").append(" ,EP.ROLE    \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始时间'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束时间'   \n").append(" ,S.APPLICATION_HOURS as '总共小时'   \n").append("  ,(    \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0'   \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)    \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end     \n").append("  )    \n").append("  as '加班事由'     \n").append(" , S.NOTE  as '備註'   \n").append(" , S.MONTHOVERTIME  as 'MOTime'   \n").append(" ,  S.STATUS   as 'action'    \n").append("	from VN_OVERTIME_M AS M    \n").append("	INNER JOIN   \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n").append("	 VN_UNIT AS UT \n")
				.append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");

		// 是否超時加班
		Sb.append("	AND S.MONTHOVERTIME='" + otVo.getMonthOverTime() + "'  \n");

		if (!otVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT = '" + otVo.getSearchDepartmen() + "' \n");
		}
		if (!otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND   S.SUBMITDATE BETWEEN '" + otVo.getStartSubmitDate() + " 00:00:00" + "'  AND '" + otVo.getEndSubmitDate() + " 23:59:59' \n");
		}
		if (otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND     CONVERT(varchar(100),  S.SUBMITDATE, 111) = '" + otVo.getStartSubmitDate() + "' \n");
		}

		if (!otVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID  = '" + otVo.getSearchEmployeeNo() + "' \n");
		}

		Sb.append(" AND S.STATUS <>'S' \n");
		Sb.append(" AND S.STATUS <>'S' \n");
		// if(otVo.getStatus() !=null && ! otVo.getStatus().trim().equals("")){
		// Sb.append(" AND S.STATUS ='"+otVo.getStatus().trim()+"' \n");
		// }

		Sb.append(" ORDER BY S.ID DESC \n");
		return Sb.toString();

	}

	/**
	 * 管理人事加班單查询
	 * 
	 * @param otVo
	 * @return
	 */
	public static final String getOvertimeRev(overTimeVO otVo)
	{

		StringBuilder Sb = new StringBuilder("select  S.ID  \n").append(" ,EP.EMPLOYEENO   as '工號'     \n").append(" ,EP.EMPLOYEE  as '姓名'     \n").append(" ,DT.DEPARTMENT as '部门'    \n").append(" ,UT.UNIT as '单位'    \n").append(" ,EP.ROLE    \n").append(" ,  CONVERT(varchar(100),  S.SUBMITDATE, 111)  as '提交日期'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_START, 120) as '加班開始时间'   \n").append(" ,CONVERT(varchar(100), S.OVERTIME_END, 120)    as '加班結束时间'   \n").append(" ,S.APPLICATION_HOURS as '总共小时'   \n").append("  ,(    \n").append(" case  when ((  SELECT  REASONS FROM VN_OVERTIME_S WHERE ID=S.ID ) ='0'   \n").append(" )  then (SELECT  USERREASONS FROM VN_OVERTIME_S WHERE ID=S.ID)    \n")
				.append(" else (SELECT RS.REASONS FROM VN_OVERTIME_S as VS INNER JOIN VN_LREASONS AS RS ON VS.REASONS = RS.ID WHERE VS.ID=S.ID ) end     \n").append("  )    \n").append("  as '加班事由'     \n").append(" , S.NOTE  as '備註'   \n").append(" , S.MONTHOVERTIME  as 'MOTime'   \n").append(" ,  S.STATUS   as 'action'    \n").append("	from VN_OVERTIME_M AS M    \n").append("	INNER JOIN   \n").append("	VN_OVERTIME_S AS S \n").append("	ON M.ID = S.M_ID \n").append("	INNER JOIN \n").append("	(SELECT     *  FROM   VN_LREASONS  Union All  select '0','','','','') AS RS \n").append("	 ON S.REASONS = RS.ID \n").append("	INNER JOIN \n").append("	VN_DEPARTMENT AS DT \n").append("	 ON M.DEPARTMENT = DT.ID \n").append("	 INNER JOIN \n").append("	 VN_UNIT AS UT \n")
				.append("	ON S.UNIT = UT.ID \n").append("  INNER JOIN \n").append("  HR_EMPLOYEE AS EP \n").append("	ON S. EP_ID = EP.ID \n").append("	 where 1=1 \n");

		// 是否超時加班
		Sb.append("	AND S.MONTHOVERTIME='" + otVo.getMonthOverTime() + "'  \n");

		if (!otVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" AND M.DEPARTMENT = '" + otVo.getSearchDepartmen() + "' \n");
		}
		if (!otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND   S.SUBMITDATE BETWEEN '" + otVo.getStartSubmitDate() + " 00:00:00" + "'  AND '" + otVo.getEndSubmitDate() + " 23:59:59' \n");
		}
		if (otVo.getStartSubmitDate().trim().equals(otVo.getEndSubmitDate().trim()))
		{
			Sb.append(" AND     CONVERT(varchar(100),  S.SUBMITDATE, 111) = '" + otVo.getStartSubmitDate() + "' \n");
		}

		if (!otVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" AND S.EP_ID  = '" + otVo.getSearchEmployeeNo() + "' \n");
		}

		Sb.append(" AND S.STATUS   IN ('U','D','L','B','UR','DR','LR','BR')  \n");
		Sb.append(" AND EP.ROLE IN ('E','U','D','M','B')  \n");

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

	/**
	 * 加班單增加
	 * 
	 * @return
	 */
	public static final String setOvertimeS()
	{

		StringBuilder Sb = new StringBuilder(" INSERT INTO VN_OVERTIME_S	 ").append(" (M_ID,EP_ID, ").append(" APPLICATION_HOURS,OVERTIME_START, ").append(" OVERTIME_END,REASONS, ").append(" UNIT,NOTE ,STATUS,SAVETIME,USERREASONS,submitDate,TURN) ").append("  VALUES(?,?,?,?,?,?,?,?,?,getdate(),?,?,?) ");

		return Sb.toString();
	}

	/**
	 * 超時加班單增加
	 * 
	 * @return
	 */
	public static final String setExceedOvertimeS()
	{

		StringBuilder Sb = new StringBuilder(" INSERT INTO VN_OVERTIME_S	 ").append(" (M_ID,EP_ID ").append(" ,REASONS,UNIT ").append(" ,NOTE ,STATUS ").append(" ,USERREASONS ").append(" ,submitDate,APPLICATION_HOURS ").append(" ,OVERTIME_START,OVERTIME_END ").append(" ,TURN,SAVETIME) ").append(" VALUES(?,? ").append(",?,? ").append(",?,? ").append(",?").append(",?,?").append(",?,?").append(",?,getdate()) ");

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

	public static final String delOvertimeM(String ID)
	{
		StringBuilder Sb = new StringBuilder(" DELETE FROM  VN_OVERTIME_M	 ").append("  where ID='" + ID + "' ");

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
	 * 計算是否超過當月加班时间
	 * 
	 * @return
	 */
	public static final String getAPPhoursflag(String ID, String yyyy, String mm)
	{
		StringBuilder Sb = new StringBuilder(" declare @Res varchar(10)		 ");
		Sb.append("  select @Res = ");
		Sb.append("  case  when (   (  SELECT  sum( convert(float,APPLICATION_HOURS)) as maxah FROM VN_OVERTIME_S WHERE EP_ID=" + ID + " ");
	//	Sb.append("   and STATUS IN ('RD','U','B','D','L','M') ");/** 已通過加班 **/
		Sb.append("   and DATEPART(yyyy,OVERTIME_START) ='" + yyyy + "' and DATEPART ( mm ,OVERTIME_START )='" + mm + "' )    >=   (select VALUE from VN_CONFIG where [key]='CTIME')");
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
		StringBuilder Sb = new StringBuilder(" select " + subSql).append(" from HR_EMPLOYEE AS V ").append("	 where 1=1 ");
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
		if (Status.equals("S"))
		{ // 提交
			Sb.append(" SUBMITTIME=getdate()  ");
		}
		if (Status.equals("L"))
		{ // 提交
			Sb.append(" SUBMITTIME=getdate()  ");
		}
		if (Status.equals("M"))
		{ // 提交
			Sb.append(" SUBMITTIME=getdate()  ");
		}
		if (Status.equals("T"))
		{ // 審核ok
			Sb.append(" REVIEWTIME=getdate()  ");
		}
		if (Status.indexOf("R") != -1)
		{// 退回
			Sb.append(" RETURNTIME=getdate()  ");
		}

		if (Status.equals("D"))
		{ // 提交
			Sb.append(" REVIEWTIME=getdate() ");
		}
		if (Status.equals("UD"))
		{ // 超時部门通過
			Sb.append(" SUBMITTIME=getdate() ");
		}
		if (Status.equals("UB"))
		{ // 超時副總通過
			Sb.append(" SUBMITTIME=getdate() ");
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

		StringBuilder Sb = new StringBuilder(" INSERT INTO VN_LEAVECARD	 ").append(" (EP_ID, HD_ID, ").append("  APPLICATIONDATE,STARTLEAVEDATE, ").append(" ENDLEAVEDATE,AGENT, ").append(" NOTE,STATUS,").append(" DAYCOUNT,HOURCOUNT,MINUTECOUNT,SAVETIME) ").append("  VALUES(?,?,?,?,?,?,?,?,?,?,?,getdate()) ");

		return Sb.toString();
	}

	/**
	 * 銷假卡-新增
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String saveSalesCard()
	{

		StringBuilder Sb = new StringBuilder(" update VN_LEAVECARD	 ").append(" (EP_ID, HD_ID, ").append("  APPLICATIONDATE,STARTSALESDATE, ").append(" ENDSALESDATE,AGENT, ").append(" NOTE,SALES_STATUS,").append(" SALES_SAVETIME) ").append("  VALUES(?,?,?,?,?,?,?,?,getdate()) ");

		return Sb.toString();
	}

	/**
	 * 更新按鈕查询請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getBeLeaveCard(String rowID)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  \n").append("  D.ID as 'DEPARTMENT' ,     \n").append("  U.ID as 'UNIT' ,    \n").append("  EP.ROLE,     \n").append("  EP.ID as 'EMPLOYEE',       \n").append("  EP.ID AS 'EMPLOYEENO' ,   \n").append("  C.APPLICATIONDATE ,  \n").append("  HD.ID  AS HID   ,  \n").append("  DAYCOUNT,  \n").append("  HOURCOUNT ,  \n").append("  MINUTECOUNT ,  \n").append("  DAYCOUNT ,  \n").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as 'STARTLEAVEDATE', \n").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as 'ENDLEAVEDATE', \n").append("  EP2.ID  as 'Agent', \n").append("  C.NOTE as 'NOTE', \n").append("  C.STATUS   as 'action' ,  \n").append("  C.RETURNMSG  as 'returnMSG' \n").append("  FROM VN_LEAVECARD  AS C  \n")
				.append("  INNER JOIN      \n").append("  HR_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  \n").append("  INNER JOIN   \n").append("  VN_LHOLIDAY AS HD      ON C.HD_ID =HD.ID  \n").append("   INNER JOIN     \n").append("   HR_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID \n").append("  INNER JOIN     \n").append("    VN_UNIT AS U      \n").append("    ON U.ID =EP.UNIT_ID     \n").append("     INNER JOIN     \n").append("     VN_DEPARTMENT AS D      \n").append(" 	  ON  D.ID =EP.DEPARTMENT_ID   \n").append("   where 1=1 \n");
		if (!rowID.trim().equals(""))
		{
			Sb.append(" AND C.ID='" + rowID + "'  \n");
		}

		Sb.append(" ORDER BY C.ID DESC \n");
		return Sb.toString();
	}

	/**
	 * 查询請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  \n").append(" D.DEPARTMENT as '部门',     \n").append(" U.UNIT   as '单位',   \n").append("   EP.ROLE,     \n").append("  EP.EMPLOYEE  as '姓名',   \n").append("  EP.EMPLOYEENO  as '工號',   \n").append("  C.APPLICATIONDATE  as '申請日期',  \n").append("  HD.HOLIDAYNAME  as '假別',  \n").append("  DAYCOUNT as  天数,  \n").append("  HOURCOUNT as  小时,  \n").append("  MINUTECOUNT as  分,  \n").append("  DAYCOUNT ,  \n").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始时间', \n").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束时间', \n").append("  EP2.EMPLOYEE  as '代理人', \n").append("  C.NOTE as '備註', \n").append("  C.STATUS   as 'action' ,  \n").append("  C.RETURNMSG  as 'returnMSG' \n")
				.append("  FROM VN_LEAVECARD  AS C  \n").append("  INNER JOIN      \n").append("  HR_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  \n").append("  INNER JOIN   \n").append("  VN_LHOLIDAY AS HD      ON C.HD_ID =HD.ID  \n").append("   INNER JOIN     \n").append("   HR_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID \n").append("  INNER JOIN     \n").append("    VN_UNIT AS U      \n").append("    ON U.ID =EP.UNIT_ID     \n").append("     INNER JOIN     \n").append("     VN_DEPARTMENT AS D      \n").append(" 	  ON  D.ID =EP.DEPARTMENT_ID   \n").append("   where 1=1 \n");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' \n");
		}
		/** 根據登入者查出角色或者程式決定可查出角色 **/
		if (lcVo.getSearchRole() == null)
		{
			lcVo.setSearchRole(keyConts.EmpRoleE);
		}
		Sb.append(" and EP.ROLE ='" + lcVo.getSearchRole() + "' \n");
		Sb.append(" ORDER BY C.ID DESC \n");

		return Sb.toString();

	}

	/**
	 * 查询銷假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getSalesLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  ").append("  EP.EMPLOYEE  as '姓名',   ").append("  EP.EMPLOYEENO  as '工號',   ").append("  C.APPLICATIONDATE  as '申請日期',  ").append("  HD.HOLIDAYNAME  as '假別',  ").append("  DAYCOUNT as  天數,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始时间', ").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束时间', ").append("  CONVERT(varchar(100), C.STARTSALESDATE, 120)  as '銷假開始时间', ").append("  CONVERT(varchar(100), C.ENDSALESDATE, 120)   as '銷假結束时间', ").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '備註', ").append("  C.SALES_STATUS   as 'action' ,  ").append("  C.RETURNMSG  as 'returnMSG' ").append("  FROM VN_LEAVECARD  AS C  ").append("  INNER JOIN      ")
				.append("  HR_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  ").append("  INNER JOIN   ").append("  VN_LHOLIDAY AS HD      ON C.HD_ID =HD.ID ").append("   INNER JOIN     ").append("   HR_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID ").append("   where 1=1 ");
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
		{// 单位退回
			Sb.append(" RETURNTIME=getdate()  ");
		}
		if (Status.equals("DR"))
		{// 部门退回
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
		{ // 单位主管審核ok
			Sb.append(" U_REVIEWTIME=getdate()  ");
		}
		if (Status.equals("D"))
		{ // 部门主管審核ok
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
	 * 使用系統工號帶出員工資料
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String getEmployeeNODate(String EMPLOYEENO)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  \n").append(" E.ID ,E.EMPLOYEE,E.ROLE,U.ID as UID,ENTRYDATE,DUTIES,U.UNIT,D.DEPARTMENT,E.EMPLOYEENO,E.DEPARTMENT_ID as DID \n").append(" FROM HR_EMPLOYEE as E \n").append("	INNER JOIN \n").append("		VN_DEPARTMENT AS D  \n").append("		ON D.ID = E.DEPARTMENT_ID \n").append("		INNER JOIN \n").append("		VN_UNIT AS U  \n").append("		ON U.ID = E.UNIT_ID \n");
		if (!EMPLOYEENO.equals(""))
		{
			Sb.append("   where EMPLOYEENO LIKE N'%" + EMPLOYEENO + "%'  \n");
		}
		return Sb.toString();
	}

	/**
	 * 使用id帶出員工資料
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String getEmployeeID(String ID)
	{

		StringBuilder Sb = new StringBuilder(" SELECT ").append(" E.ID ,U.ID as UID,ENTRYDATE,DUTIES,U.UNIT,D.DEPARTMENT,E.EMPLOYEENO,E.DEPARTMENT_ID as DID, E.EMPLOYEE,E.ROLE ").append(" FROM HR_EMPLOYEE as E ").append("	INNER JOIN ").append("		VN_DEPARTMENT AS D  ").append("		ON D.ID = E.DEPARTMENT_ID ").append("		INNER JOIN ").append("		VN_UNIT AS U  ").append("		ON U.ID = E.UNIT_ID ");
		if (!ID.equals(""))
		{
			Sb.append("    where E.ID LIKE N'%" + ID + "%' ");
		}
		return Sb.toString();
	}

	/**
	 * 单位主管查询請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getUnitLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  \n").append("  D.DEPARTMENT as '部门',   \n").append("  U.UNIT   as '单位', \n").append("  EP.ROLE,   \n").append("  EP.EMPLOYEE  as '姓名',   \n").append("  EP.EMPLOYEENO  as '工號',   \n").append("  C.APPLICATIONDATE  as '申請日期',  \n").append("  HD.HOLIDAYNAME  as '假別',  \n").append("  DAYCOUNT as  天數,  \n").append("  HOURCOUNT as  小时,  \n").append("  MINUTECOUNT as  分,  \n").append("  DAYCOUNT ,  \n").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始时间', \n").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束时间', \n").append("  EP2.EMPLOYEE  as '代理人', \n").append("  C.NOTE as '備註', \n").append("  C.STATUS   as 'action' , \n").append("  C.RETURNMSG  as 'returnMSG' \n").append("  FROM VN_LEAVECARD  AS C  \n")
				.append("  INNER JOIN      \n").append("  HR_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  \n").append("  INNER JOIN   \n").append("  VN_LHOLIDAY AS HD      ON C.HD_ID =HD.ID \n").append("   INNER JOIN     \n").append("   HR_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID \n").append("   INNER JOIN   \n").append("   VN_UNIT AS U    \n").append("   ON U.ID =EP.UNIT_ID   \n").append("    INNER JOIN   \n").append("    VN_DEPARTMENT AS D    \n").append("	  ON  D.ID =EP.DEPARTMENT_ID \n").append("   where 1=1 \n");

		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' \n");
		}
		if (!lcVo.getStartLeaveDate().equals(lcVo.getEndLeaveDate()))
		{
			Sb.append(" AND   C.APPLICATIONDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59' \n");
		}
		if (!lcVo.getSearchUnit().equals("0"))
		{
			Sb.append(" and EP.UNIT_ID ='" + lcVo.getSearchUnit() + "' \n");
		}
		Sb.append(" AND C.STATUS <>'S' \n");
		Sb.append(" AND EP.ROLE ='E' \n");// 只能查到普通員工 三天以下
		Sb.append(" AND cast(C.DAYCOUNT as float)  <='3' \n");
		Sb.append(" AND C.STATUS <>'UR' \n");
		// Sb.append(" AND C.STATUS <>'DR' ");
		// Sb.append(" AND C.STATUS <>'LR' ");
		// Sb.append(" AND C.STATUS <>'MR' ");
		Sb.append(" ORDER BY C.ID DESC \n");

		return Sb.toString();

	}

	/**
	 * 部门主管查询請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getDepartmenLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,   \n").append("   D.DEPARTMENT as '部门',    \n").append("  U.UNIT   as '单位',  \n").append("  EP.ROLE,    \n").append("  EP.EMPLOYEE  as '姓名',    \n").append("  EP.EMPLOYEENO  as '工號',    \n").append("  C.APPLICATIONDATE  as '申請日期',   \n").append("  HD.HOLIDAYNAME  as '假別',   \n").append("  DAYCOUNT as  天數,   \n").append("  HOURCOUNT as  小时,  \n").append("  MINUTECOUNT as  分,  \n").append("  DAYCOUNT ,   \n").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始时间',  \n").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束时间',  \n").append("  EP2.EMPLOYEE  as '代理人',  \n").append("  C.NOTE as '備註',  \n").append("  C.STATUS   as 'action' , \n").append("  C.RETURNMSG  as 'returnMSG'  \n")
				.append("  FROM VN_LEAVECARD  AS C   \n").append("  INNER JOIN       \n").append("  HR_EMPLOYEE AS EP      ON C.EP_ID =EP.ID   \n").append("  INNER JOIN    \n").append("  VN_LHOLIDAY AS HD      ON C.HD_ID =HD.ID  \n").append("   INNER JOIN      \n").append("   HR_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID  \n").append("   INNER JOIN    \n").append("   VN_UNIT AS U     \n").append("   ON U.ID =EP.UNIT_ID    \n").append("    INNER JOIN    \n").append("    VN_DEPARTMENT AS D    \n").append("	  ON  D.ID =EP.DEPARTMENT_ID  \n").append("   where 1=1  \n");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "'  \n");
		}
		if (lcVo.getStartLeaveDate() != lcVo.getEndLeaveDate())
		{
			Sb.append(" AND   C.APPLICATIONDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59' \n");
		}
		if (!lcVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" and EP.DEPARTMENT_ID ='" + lcVo.getSearchDepartmen() + "'  \n");
		}

		// Sb.append(" AND C.STATUS <>'T' \n");
		Sb.append(" AND C.STATUS <>'S'  \n");
		Sb.append(" AND C.STATUS <>'UR'  \n");
		Sb.append(" AND not (C.STATUS ='T' AND EP.ROLE ='D')  \n");
		Sb.append(" AND not(C.STATUS ='T' AND EP.ROLE ='U' and cast(DAYCOUNT as float)>=3  )  \n");
		Sb.append("  AND not (C.STATUS ='B' AND EP.ROLE ='M')   \n");
		Sb.append(" ORDER BY C.ID DESC  \n");

		return Sb.toString();

	}

	/**
	 * 管理部查询三天以下請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getMLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  ").append("  EP.EMPLOYEE  as '姓名',   ").append("  EP.EMPLOYEENO  as '工號',   ").append("  C.APPLICATIONDATE  as '申請日期',  ").append("  HD.HOLIDAYNAME  as '假別',  ").append("  DAYCOUNT as  天數,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始时间', ").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束时间', ").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '備註', ").append("  C.STATUS   as 'action' , ").append("  C.RETURNMSG  as 'returnMSG' ").append("  FROM VN_LEAVECARD  AS C  ").append("  INNER JOIN      ").append("  HR_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  ").append("  INNER JOIN   ").append("  VN_LHOLIDAY AS HD      ON C.HD_ID =HD.ID ").append("   INNER JOIN     ")
				.append("   HR_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID ").append("   where 1=1 ");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' ");
		}
		if (!lcVo.getStartLeaveDate().equals(lcVo.getEndLeaveDate()))
		{
			Sb.append(" AND   C.STARTLEAVEDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59' \n");
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
	 * 經理查询請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getLLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  \n").append(" D.DEPARTMENT as '部门',    \n").append(" U.UNIT   as '单位',  \n").append("  EP.ROLE,    \n").append("  EP.EMPLOYEE  as '姓名',   \n").append("  EP.EMPLOYEENO  as '工號',   \n").append("  C.APPLICATIONDATE  as '申請日期',  \n").append("  HD.HOLIDAYNAME  as '假別',  \n").append("  DAYCOUNT as  '天數',  ").append("  HOURCOUNT as  '小时',  \n").append("  MINUTECOUNT as  '分',  \n").append("  DAYCOUNT ,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '請假開始时间', \n").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '請假結束时间', \n").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '備註', \n").append("  C.STATUS   as 'action' , \n").append("  C.RETURNMSG  as 'returnMSG' \n")
				.append("  FROM VN_LEAVECARD  AS C  \n").append("  INNER JOIN      \n").append("  HR_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  \n").append("  INNER JOIN   \n").append("  VN_LHOLIDAY AS HD      ON C.HD_ID =HD.ID \n").append("   INNER JOIN     \n").append("   HR_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID \n").append("   INNER JOIN     \n").append("   VN_UNIT AS U      \n").append("   ON U.ID =EP.UNIT_ID     \n").append("    INNER JOIN     \n").append("	  VN_DEPARTMENT AS D     \n").append("    ON  D.ID =EP.DEPARTMENT_ID  \n").append("   where 1=1  \n");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' \n");
		}
		if (!lcVo.getStartLeaveDate().trim().equals(lcVo.getEndLeaveDate().trim()))
		{

			Sb.append(" AND   C.APPLICATIONDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59'  \n");
		}
		if (lcVo.getStartLeaveDate().trim().equals(lcVo.getEndLeaveDate().trim()))
		{

			Sb.append(" AND     CONVERT(varchar(100),  C.APPLICATIONDATE, 111) = '" + lcVo.getStartLeaveDate() + "' \n");
		}
		if (!lcVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" and EP.DEPARTMENT_ID ='" + lcVo.getSearchDepartmen() + "' \n");
		}
		// Sb.append(" and cast(C.DAYCOUNT as float) >= 3 \n");
		// Sb.append(" AND C.STATUS <>'T' \n");
		// Sb.append(" AND C.STATUS <>'S' \n");
		// Sb.append(" AND C.STATUS <>'M' \n");
		// Sb.append(" AND C.STATUS <>'U' \n");
		// Sb.append(" AND C.STATUS <>'UR' \n");
		// Sb.append(" AND C.STATUS <>'DR' \n");
		// Sb.append(" AND C.STATUS <>'LR' \n");
		// Sb.append(" AND C.STATUS <>'MR' \n");
		/** 未審核 **/
		if (lcVo.getStatus().equals("D"))
		{
			Sb.append(" AND  ((C.STATUS ='T' AND EP.ROLE ='D' AND cast(DAYCOUNT as float) <=3) \n");
			Sb.append(" OR (C.STATUS ='T' AND EP.ROLE ='U' AND cast(DAYCOUNT as float) >3)   \n");
			Sb.append(" OR (C.STATUS ='T' AND EP.ROLE ='D' AND cast(DAYCOUNT as float) >3)) \n");
		}
		/** 已審核或退回 **/
		if (lcVo.getStatus().equals("L"))
		{
			Sb.append(" AND C.STATUS  IN ('U','D','L','UR','DR','LR')  \n");
			Sb.append(" AND EP.ROLE IN ('E','U','D','M')  \n");
			// Sb.append(" AND EP.ROLE ='E' \n");
		}

		Sb.append(" ORDER BY C.ID DESC  \n");

		return Sb.toString();

	}

	/**
	 * 副總查询請假卡
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getBLeaveCard(leaveCardVO lcVo)
	{

		StringBuilder Sb = new StringBuilder(" SELECT  C.ID,  \n").append(" D.DEPARTMENT as '部门',    \n").append(" U.UNIT   as '单位',  \n").append("  EP.ROLE,    \n").append("  EP.EMPLOYEE  as '姓名',   \n").append("  EP.EMPLOYEENO  as '工号',   \n").append("  C.APPLICATIONDATE  as '申请日期',  \n").append("  HD.HOLIDAYNAME  as '假別',  \n").append("  DAYCOUNT as  天数,  ").append("  HOURCOUNT as  小时,  \n").append("  MINUTECOUNT as  分,  \n").append("  DAYCOUNT ,  ").append("  CONVERT(varchar(100), C.STARTLEAVEDATE, 120)  as '请假开始时间', \n").append("  CONVERT(varchar(100), C.ENDLEAVEDATE, 120)   as '请假结束时间', \n").append("  EP2.EMPLOYEE  as '代理人', ").append("  C.NOTE as '备注', \n").append("  C.STATUS   as 'action' , \n").append("  C.RETURNMSG  as 'returnMSG' \n").append("  FROM VN_LEAVECARD  AS C  \n")
				.append("  INNER JOIN      \n").append("  HR_EMPLOYEE AS EP      ON C.EP_ID =EP.ID  \n").append("  INNER JOIN   \n").append("  VN_LHOLIDAY AS HD      ON C.HD_ID =HD.ID \n").append("   INNER JOIN     \n").append("   HR_EMPLOYEE AS EP2      ON C.AGENT =EP2.ID \n").append("   INNER JOIN     \n").append("   VN_UNIT AS U      \n").append("   ON U.ID =EP.UNIT_ID     \n").append("    INNER JOIN     \n").append("	  VN_DEPARTMENT AS D     \n").append("    ON  D.ID =EP.DEPARTMENT_ID  \n").append("   where 1=1  \n");
		if (!lcVo.getSearchEmployeeNo().equals("0"))
		{
			Sb.append(" and C.EP_ID ='" + lcVo.getSearchEmployeeNo() + "' \n");
		}
		if (!lcVo.getStartLeaveDate().trim().equals(lcVo.getEndLeaveDate().trim()))
		{

			Sb.append(" AND   C.APPLICATIONDATE  BETWEEN '" + lcVo.getStartLeaveDate() + " 00:00:00" + "'  AND '" + lcVo.getEndLeaveDate() + " 23:59:59'  \n");
		}
		if (lcVo.getStartLeaveDate().trim().equals(lcVo.getEndLeaveDate().trim()))
		{

			Sb.append(" AND     CONVERT(varchar(100),  C.APPLICATIONDATE, 111) = '" + lcVo.getStartLeaveDate() + "' \n");
		}
		if (!lcVo.getSearchDepartmen().equals("0"))
		{
			Sb.append(" and EP.DEPARTMENT_ID ='" + lcVo.getSearchDepartmen() + "' \n");
		}
		// Sb.append(" and cast(C.DAYCOUNT as float) >= 3 \n");
		// Sb.append(" AND C.STATUS <>'T' \n");
		// Sb.append(" AND C.STATUS <>'S' \n");
		// Sb.append(" AND C.STATUS <>'M' \n");
		// Sb.append(" AND C.STATUS <>'U' \n");
		// Sb.append(" AND C.STATUS <>'UR' \n");
		// Sb.append(" AND C.STATUS <>'DR' \n");
		// Sb.append(" AND C.STATUS <>'LR' \n");
		// Sb.append(" AND C.STATUS <>'MR' \n");
		/** 未審核 **/
		if (lcVo.getStatus().equals("D"))
		{
			// Sb.append(" AND EP.ROLE NOT IN ('E','U','D','M') \n");
			Sb.append(" AND (EP.ROLE='M' AND C.STATUS='T')  \n");
		}
		/** 已審核或退回 **/
		if (lcVo.getStatus().equals("L"))
		{
			Sb.append(" AND C.STATUS  IN ('U','D','L','B','UR','DR','LR','BR')  \n");
			Sb.append(" AND EP.ROLE IN ('E','U','D','M','B')  \n");
			// Sb.append(" AND EP.ROLE ='E' \n");
		}

		Sb.append(" ORDER BY C.ID DESC  \n");

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

		Sb.append(" , SAVETIME=getdate()  ");

		if (!lcVo.getReturnMsg().equals(""))
		{
			Sb.append(" , RETURNMSG=N'" + lcVo.getReturnMsg() + "' ");
		}

		Sb.append("  where ID='" + lcVo.getRowID() + "' ");

		return Sb.toString();
	}

	/**
	 * 单位查部门
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
	 * 每日考勤表建立資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getdailyReport(Connection con, leaveCardVO lcVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_rep_daily);
		sql = sql.replace("<FDate/>", lcVo.getApplicationDate());
		if (lcVo.getSearchDepartmen().equals("0"))
		{
			sql = sql.replace("<VEDEPARTMENT/>", " 1=1");
			sql = sql.replace("<SVEDEPARTMENT/>", " 1=1");
		}
		else
		{
			sql = sql.replace("<VEDEPARTMENT/>", " VE.DEPARTMENT_ID='" + lcVo.getSearchDepartmen() + "'  ");
			sql = sql.replace("<SVEDEPARTMENT/>", " SVE.DEPARTMENT_ID='" + lcVo.getSearchDepartmen() + "'  ");
		}
		if (lcVo.getSearchUnit().equals("0"))
		{
			sql = sql.replace("<SVEUNIT/>", " 1=1");
			sql = sql.replace("<VEUNIT/>", " 1=1");
		}
		else
		{
			sql = sql.replace("<VEUNIT/>", "  VE.UNIT_ID='" + lcVo.getSearchUnit() + "'  ");
			sql = sql.replace("<SVEUNIT/>", "  SVE.UNIT_ID='" + lcVo.getSearchUnit() + "'  ");
		}
		logger.info("1 sql_rep_daily sql : " + sql);
		// 建立資料表資料
		DBUtil.workLateOperationSql(con, sql);
		logger.info("2 sql_rep_daily ");
		// 執行修改程式
		DBUtil.dayReportAlter(con, lcVo);
		logger.info("3 sql_rep_daily ");
		// 查出結果
		logger.info("4 getDayReport" + getDayReport(lcVo));
		return getDayReport(lcVo);
	}

	/**
	 * 月份考勤表-建立資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getMonthReport(Connection con, repAttendanceVO raVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		leaveCardVO lcVo = new leaveCardVO();
		String[] moth = raVo.getQueryYearMonth().split("/");
		ArrayList listDates = DateUtil.getMonthDates(moth[0], moth[1]);
		int dayMax=DateUtil.getMonthDateMax(moth[0], moth[1]);
		Hashtable listmW = new Hashtable();
		lcVo.setSearchDepartmen(raVo.getSearchDepartmen());
		lcVo.setSearchUnit(raVo.getSearchUnit());
	
		//先建立整個月日報
		long startTime=System.currentTimeMillis();//记录开始时间
		getMonthdailyReportP1(con,	lcVo,raVo.getQueryYearMonth()+"/01");
		
		List<dayReportRO> leo =null;
	
		
		
	/** 建立日報資料 輸出查詢資料 **/
		lcVo.setApplicationDate(raVo.getQueryYearMonth());	
		leo = getMonthdailyReportP2(con,	lcVo);/** 建立日報資料 輸出查詢資料 **/
		
		long endTime=System.currentTimeMillis();//记录结束时间
		float excTime=(float)(endTime-startTime)/1000;
		logger.info("leo.size()"+leo.size()+" 建立日報資料 輸出查詢資料 执行时间："+excTime+"s");
		
		boolean checkStart=false;//是否為第一天
		
		
		startTime=System.currentTimeMillis();//记录开始时间
		Hashtable listSumData =DBUtil.getMonthSumData(con,	lcVo,raVo.getQueryYearMonth());/** 輸出計算資料 **/
		endTime=System.currentTimeMillis();//记录结束时间
	    excTime=(float)(endTime-startTime)/1000;
		logger.info("  輸出計算資料 执行时间："+excTime+"s");
		
		
		startTime=System.currentTimeMillis();//记录开始时间
		for (int j = 0; j < leo.size(); j++)
		{
			/**第一天**/
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/01")&&  listDates.contains(raVo.getQueryYearMonth()+"/01")){
				checkStart=true;
				monthSumDataWo sW =(monthSumDataWo)listSumData.get(leo.get(j).getEMPLOYEENO());/** 輸出計算資料 **/
				monthReportNoteWO mW=new monthReportNoteWO();
				mW.setEMPLOYEENO(leo.get(j).getEMPLOYEENO());
				mW.setMonthReportX(vnStringUtil.changeDouble(sW.getMonthReportX()));
				mW.setMonthReportP(vnStringUtil.changeDouble(sW.getMonthReportP()));
				mW.setMonthReportN(vnStringUtil.changeDouble(sW.getMonthReportN()));
				mW.setMonthReportTS(vnStringUtil.changeDouble(sW.getMonthReportTS()));
				mW.setMonthReportH(vnStringUtil.changeDouble(sW.getMonthReportH()));
				mW.setMonthReportT(vnStringUtil.changeDouble(sW.getMonthReportT()));
				mW.setMonthReportB(vnStringUtil.changeDouble(sW.getMonthReportB()));
				mW.setMonthReportR(vnStringUtil.changeDouble(sW.getMonthReportR()));
				mW.setMonthReportL(vnStringUtil.changeDouble(sW.getMonthReportL()));
				mW.setMonthReportO(vnStringUtil.changeDouble(sW.getMonthReportO()));
				mW.setMonthReportW(vnStringUtil.changeDouble(sW.getMonthReportW()));
				mW.setYEAR(moth[0]);
				mW.setMONTH(moth[1]);
				mW.setDay("01",leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
				listmW.put(leo.get(j).getEMPLOYEENO(), mW);
			} 
			/**02為第一天**/
			if(!checkStart &&leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/02") &&  listDates.contains(raVo.getQueryYearMonth()+"/02")){
				
				monthSumDataWo sW =(monthSumDataWo)listSumData.get(leo.get(j).getEMPLOYEENO());/** 輸出計算資料 **/
				monthReportNoteWO mW=new monthReportNoteWO();
				mW.setEMPLOYEENO(leo.get(j).getEMPLOYEENO());
				mW.setMonthReportX(vnStringUtil.changeDouble(sW.getMonthReportX()));
				mW.setMonthReportP(vnStringUtil.changeDouble(sW.getMonthReportP()));
				mW.setMonthReportN(vnStringUtil.changeDouble(sW.getMonthReportN()));
				mW.setMonthReportTS(vnStringUtil.changeDouble(sW.getMonthReportTS()));
				mW.setMonthReportH(vnStringUtil.changeDouble(sW.getMonthReportH()));
				mW.setMonthReportT(vnStringUtil.changeDouble(sW.getMonthReportT()));
				mW.setMonthReportB(vnStringUtil.changeDouble(sW.getMonthReportB()));
				mW.setMonthReportR(vnStringUtil.changeDouble(sW.getMonthReportR()));
				mW.setMonthReportL(vnStringUtil.changeDouble(sW.getMonthReportL()));
				mW.setMonthReportO(vnStringUtil.changeDouble(sW.getMonthReportO()));
				mW.setMonthReportW(vnStringUtil.changeDouble(sW.getMonthReportW()));
				mW.setYEAR(moth[0]);
				mW.setMONTH(moth[1]);
				mW.setDay("02", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
				listmW.put(leo.get(j).getEMPLOYEENO(), mW);				
			} 
			/**02為第二天**/
			if(checkStart && leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/02") &&  listDates.contains(raVo.getQueryYearMonth()+"/02")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("02", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			
		}
		endTime=System.currentTimeMillis();//记录结束时间
	    excTime=(float)(endTime-startTime)/1000;
		logger.info("leo.size()"+leo.size()+"  第一二天迴圈 执行时间："+excTime+"s");
		
		startTime=System.currentTimeMillis();//记录开始时间
		
		for (int j = 0; j < leo.size(); j++)
		{
		
		
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/03") &&  listDates.contains(raVo.getQueryYearMonth()+"/03")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("03", leo.get(j).getSTATUS());
			//	vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/04") &&  listDates.contains(raVo.getQueryYearMonth()+"/04")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("04", leo.get(j).getSTATUS());
			//	vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/05" ) &&  listDates.contains(raVo.getQueryYearMonth()+"/05")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("05", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/06" ) &&  listDates.contains(raVo.getQueryYearMonth()+"/06")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("06", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/07")&&  listDates.contains(raVo.getQueryYearMonth()+"/07")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("07", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/08")&&  listDates.contains(raVo.getQueryYearMonth()+"/08")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("08", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/09")&&  listDates.contains(raVo.getQueryYearMonth()+"/09")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("09", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/10")&&  listDates.contains(raVo.getQueryYearMonth()+"/10")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("10", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/11")&&  listDates.contains(raVo.getQueryYearMonth()+"/11")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("11", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/12")&&  listDates.contains(raVo.getQueryYearMonth()+"/12")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("12", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/13")&&  listDates.contains(raVo.getQueryYearMonth()+"/13")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("13", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/14")&&  listDates.contains(raVo.getQueryYearMonth()+"/14")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("14", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/15")&&  listDates.contains(raVo.getQueryYearMonth()+"/15")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("15", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/16")&&  listDates.contains(raVo.getQueryYearMonth()+"/16")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("16", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/17")&&  listDates.contains(raVo.getQueryYearMonth()+"/17")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("17", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/18")&&  listDates.contains(raVo.getQueryYearMonth()+"/18")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("18", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/19")&&  listDates.contains(raVo.getQueryYearMonth()+"/19")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("19", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/20")&&  listDates.contains(raVo.getQueryYearMonth()+"/20")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("20", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/21")&&  listDates.contains(raVo.getQueryYearMonth()+"/21")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("21", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/22")&&  listDates.contains(raVo.getQueryYearMonth()+"/22")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("22", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/23")&&  listDates.contains(raVo.getQueryYearMonth()+"/23")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("23", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/24")&&  listDates.contains(raVo.getQueryYearMonth()+"/24")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("24", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/25")&&  listDates.contains(raVo.getQueryYearMonth()+"/25")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("25", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/26")&&  listDates.contains(raVo.getQueryYearMonth()+"/26")){
	
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("26", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/27")&&  listDates.contains(raVo.getQueryYearMonth()+"/27")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("27", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/28")&&  listDates.contains(raVo.getQueryYearMonth()+"/28")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("28", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/29")&&  listDates.contains(raVo.getQueryYearMonth()+"/29")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("29", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/30")&&  listDates.contains(raVo.getQueryYearMonth()+"/30")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("30", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
			if(leo.get(j).getDAY().equals(raVo.getQueryYearMonth()+"/31")&&  listDates.contains(raVo.getQueryYearMonth()+"/31")){
				
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				mW.setDay("31", leo.get(j).getSTATUS());
				//vnStringUtil.getNOTE(leo.get(j), mW);
			} 
		}
		endTime=System.currentTimeMillis();//记录结束时间
	    excTime=(float)(endTime-startTime)/1000;
		logger.info("leo.size()"+leo.size()+"  其他天迴圈 执行时间："+excTime+"s");
	
		startTime=System.currentTimeMillis();//记录开始时间
	

		StringBuilder insterSql = new StringBuilder("");
		ArrayList listEmp = new ArrayList();
		for (int j = 0; j < leo.size(); j++)
		{
			if(!listEmp.contains(leo.get(j).getEMPLOYEENO())){
				monthReportNoteWO mW = (monthReportNoteWO) listmW.get(leo.get(j).getEMPLOYEENO());
				insterSql.append(insterMonthdailyReport(lcVo, leo.get(j), mW));
				insterSql.append("      \n");
			}
			listEmp.add(leo.get(j).getEMPLOYEENO());
		}
		endTime=System.currentTimeMillis();//记录结束时间
	    excTime=(float)(endTime-startTime)/1000;
		logger.info("leo.size()"+leo.size()+" 建立新增SQL 执行时间："+excTime+"s");
		
		startTime=System.currentTimeMillis();//记录开始时间
		DBUtil.workLateOperationSql(con, insterSql.toString());
		endTime=System.currentTimeMillis();//记录结束时间
	    excTime=(float)(endTime-startTime)/1000;
		logger.info("  資料庫建立月報 执行时间："+excTime+"s");
		
		logger.info("getvnMonthAttendance sql" + getvnMonthAttendance(raVo));
		return getvnMonthAttendance(raVo);
	}

	/**
	 * 月份考勤建立每日考勤資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final void getMonthdailyReportP1(Connection con, leaveCardVO lcVo,String Day)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_setDailyMonth);
		sql = sql.replace("<FDate/>", Day);
		if (lcVo.getSearchDepartmen().equals("0"))
		{
			sql = sql.replace("<VEDEPARTMENT/>", " 1=1");
			sql = sql.replace("<SVEDEPARTMENT/>", " 1=1");
		}
		else
		{
			sql = sql.replace("<VEDEPARTMENT/>", " VE.DEPARTMENT_ID='" + lcVo.getSearchDepartmen() + "'  ");
			sql = sql.replace("<SVEDEPARTMENT/>", " SVE.DEPARTMENT_ID='" + lcVo.getSearchDepartmen() + "'  ");
		}
		if (lcVo.getSearchUnit().equals("0"))
		{
			sql = sql.replace("<SVEUNIT/>", " 1=1");
			sql = sql.replace("<VEUNIT/>", " 1=1");
		}
		else
		{
			sql = sql.replace("<VEUNIT/>", "  VE.UNIT_ID='" + lcVo.getSearchUnit() + "'  ");
			sql = sql.replace("<SVEUNIT/>", "  SVE.UNIT_ID='" + lcVo.getSearchUnit() + "'  ");
		}
		// 建立資料表資料
		logger.info("getMonthdailyReportP1 sql:"+sql);
		DBUtil.workLateOperationSql(con, sql);
	}
	
	
	
	
	/**
	 * 更新當天日報並查出
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final List<dayReportRO> getMonthdailyReportP2(Connection con, leaveCardVO lcVo)
	{
		DBUtil.dayReportAlter(con, lcVo);
		// 查出結果
		return DBUtil.getDayReportMonth(con, lcVo);
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
		tableSql.append(" EMPLOYEENO as 工号 ,  \n");
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
		tableSql.append(" NOTE as '总共'   \n");
		tableSql.append(" FROM  VN_YEAR_MONTH_ATTENDANCE   where YEAR='" + raVo.getQueryYearMonth().split("/")[0] + "'    \n");
		tableSql.append(" and  MONTH='" + raVo.getQueryYearMonth().split("/")[1] + "'    \n");
		if (!raVo.getSearchDepartmen().equals("0"))
		{
			tableSql.append(" and  DEPARTMENT='" + raVo.getSearchDepartmen() + "'    \n");
		}
		if (!raVo.getSearchUnit().equals("0"))
		{
			tableSql.append(" and  UNIT='" + raVo.getSearchUnit() + "'    \n");
		}
		return tableSql.toString();
	}

	/**
	 * 
	 */

	/**
	 * 個人查閱遲到早退and打卡时间
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getEmplateOutEarly(Connection con, lateOutEarlyVO erVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = "", IsLate = "";
		// 遲到名單
		if (erVo.getQueryIsLate().equals("1"))
		{
			IsLate = "1";
		}
		if (erVo.getQueryIsLate().equals("2"))
		{
			IsLate = "0";
		}
		sql = hu.gethtml(sqlConsts.sql_emp_IsQueryLate);
		sql = sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
		sql = sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
		sql = sql.replace("<isLate/>", IsLate);
		sql = sql.replace("<EMPLOYEENO/>", erVo.getEmpID());
		logger.info("sql_emp_IsQueryLate sql : " + sql);
		String LATE = DBUtil.queryDBField(con, sql, "LATE");
		logger.info("sql_emp_IsQueryLate LATE : " + LATE);

		if (Integer.valueOf(LATE) == 0)
		{
			if (erVo.getQueryIsLate().equals("1"))
			{
				sql = hu.gethtml(sqlConsts.sql_empLate);

				sql = sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
				sql = sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
				sql = sql.replace("<EmpID/>", erVo.getEmpID());
				logger.info("sql_empLate sql : " + sql);
				DBUtil.workLateOperationSql(con, sql);
			}
			if (erVo.getQueryIsLate().equals("2"))
			{
				sql = hu.gethtml(sqlConsts.sql_empEarly);

				sql = sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
				sql = sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
				sql = sql.replace("<EmpID/>", erVo.getEmpID());
				logger.info("sql_empEarly sql : " + sql);
				DBUtil.workLateOperationSql(con, sql);
			}
		}
		logger.info("getEmpYMLate sql : " + getEmpYMLate(erVo));
		return getEmpYMLate(erVo);
	}

	/**
	 * 遲到早退名單
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getlateOutEarly(Connection con, lateOutEarlyVO erVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = "", IsLate = "";
		// 遲到名單
		if (erVo.getQueryIsLate().equals("1"))
		{
			IsLate = "1";
		}
		// 早退名單
		if (erVo.getQueryIsLate().equals("2"))
		{
			IsLate = "0";
		}
		/*
		 * sql=hu.gethtml(sqlConsts.sql_lateIsQuery); sql=sql.replace("<year/>",
		 * erVo.getQueryYearMonth().split("/")[0]); sql=sql.replace("<Month/>",
		 * erVo.getQueryYearMonth().split("/")[1]);
		 * sql=sql.replace("<isLate/>",IsLate);
		 * logger.info("getlateOutEarly  sql="+ sql); String
		 * LATE=DBUtil.queryDBField(con,sql,"LATE");
		 * if(Integer.valueOf(LATE)==0){
		 */
		if (erVo.getQueryIsLate().equals("1"))
		{
			sql = hu.gethtml(sqlConsts.sql_yearMonthLate);
			sql = sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
			sql = sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
			logger.info("sql_yearMonthLate  sql=" + sql);
			DBUtil.workLateOperationSql(con, sql);
		}
		if (erVo.getQueryIsLate().equals("2"))
		{// 早退名單
			sql = hu.gethtml(sqlConsts.sql_yesrMonthEarly);
			sql = sql.replace("<year/>", erVo.getQueryYearMonth().split("/")[0]);
			sql = sql.replace("<Month/>", erVo.getQueryYearMonth().split("/")[1]);
			logger.info("sql_yesrMonthEarly  sql=" + sql);
			DBUtil.workLateOperationSql(con, sql);
		}

		// }
		return getYMLATE(erVo);
	}

	/**
	 * 查询遲到早退名單年月區分
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getYMLATE(lateOutEarlyVO erVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql.append(" V.EMPLOYEENO as 工號 ,  \n");
		tableSql.append(" V.EMPLOYEE as 姓名,  \n");
		tableSql.append(" D.DEPARTMENT as 部门,  \n");
		tableSql.append(" U.UNIT  as 单位 ,  \n");
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
		tableSql.append(" HOUR as 小时 ,  \n");
		if (erVo.getQueryIsLate().equals("1"))
		{
			tableSql.append(" LATETIMES as 遲到次數   \n");
		}
		if (erVo.getQueryIsLate().equals("2"))
		{
			tableSql.append(" LATETIMES as 早退次數   \n");
		}
		tableSql.append(" FROM  VN_YEAR_MONTH_LATE V ,HR_EMPLOYEE E  ,VN_UNIT U  ,VN_DEPARTMENT D \n");
		tableSql.append(" where E.EMPLOYEENO=V.EMPLOYEENO   \n");
		tableSql.append(" and E.UNIT_ID=U.ID   \n");
		tableSql.append(" and E.DEPARTMENT_ID=D.ID  \n");
		tableSql.append(" and YEAR='" + erVo.getQueryYearMonth().split("/")[0] + "'   \n");
		tableSql.append(" and  MONTH='" + erVo.getQueryYearMonth().split("/")[1] + "'    \n");
		if (erVo.getQueryIsLate().equals("1"))
		{
			tableSql.append(" and ISLATE='1'    \n");
		}
		if (erVo.getQueryIsLate().equals("2"))
		{
			tableSql.append(" and ISLATE='0'   \n");
		}
		if (!erVo.getSearchDepartmen().equals("0"))
		{
			tableSql.append(" and  D.ID='" + erVo.getSearchDepartmen() + "'    \n");
		}
		logger.info("tableSql=" + tableSql.toString());

		return tableSql.toString();
	}

	/**
	 * 查询員工個人遲到早退列表
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
		tableSql.append(" NOTWORK   ,  \n");
		if (erVo.getQueryIsLate().equals("1"))
		{
			tableSql.append(" LATETIMES    \n");
		}
		if (erVo.getQueryIsLate().equals("2"))
		{
			tableSql.append(" LATETIMES    \n");
		}
		tableSql.append(" FROM  VN_EMP_LATE   where YEAR='" + erVo.getQueryYearMonth().split("/")[0] + "'    \n");
		tableSql.append(" and  MONTH='" + erVo.getQueryYearMonth().split("/")[1] + "'    \n");
		if (erVo.getQueryIsLate().equals("1"))
		{
			tableSql.append(" and ISLATE='1'    \n");
		}
		if (erVo.getQueryIsLate().equals("2"))
		{
			tableSql.append(" and ISLATE='0'   \n");
		}
		tableSql.append(" and EMPLOYEENO='" + erVo.getEmpID() + "'   \n");

		return tableSql.toString();
	}

	/**
	 * 查询遲到早退名單年月區分-報表
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getLateOutEarlyExcelSql(lateOutEarlyVO erVo)
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql.append("  ROW_NUMBER() over(order by V.EMPLOYEENO) as ID,  \n");
		tableSql.append(" V.EMPLOYEENO ,  \n");
		tableSql.append(" V.EMPLOYEE ,  \n");
		tableSql.append(" D.DEPARTMENT ,  \n");
		tableSql.append(" U.UNIT  ,  \n");
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

		tableSql.append(" FROM  VN_YEAR_MONTH_LATE V , VN_UNIT U,HR_EMPLOYEE E, VN_DEPARTMENT D      \n");
		tableSql.append(" where E.EMPLOYEENO=V.EMPLOYEENO     \n");
		tableSql.append(" and E.UNIT_ID=U.ID  \n");
		tableSql.append(" and YEAR='" + erVo.getQueryYearMonth().split("/")[0] + "'    \n");
		tableSql.append(" and  MONTH='" + erVo.getQueryYearMonth().split("/")[1] + "'    \n");
		tableSql.append(" and E.DEPARTMENT_ID=D.ID  \n");
		if (erVo.getQueryIsLate().equals("1"))
		{
			tableSql.append(" and ISLATE='1'    \n");
		}
		if (erVo.getQueryIsLate().equals("2"))
		{
			tableSql.append(" and ISLATE='0'   \n");
		}
		if (!erVo.getSearchDepartmen().equals("0"))
		{
			tableSql.append(" and  D.ID='" + erVo.getSearchDepartmen() + "'    \n");
		}
		logger.info("ISLATE=" + tableSql.toString());

		return tableSql.toString();
	}

	/**
	 * 部门員工名單
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getDept(leaveCardVO lcVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		// 遲到名單

		sql = hu.gethtml(sqlConsts.sql_dept);
		sql = sql.replace("<DEPTID/>", lcVo.getSearchDepartmen());

		return sql;
	}

	/**
	 * 部门員工名單
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getEhm(overTimeVO otVo)
	{
		StringBuilder tableSql = new StringBuilder("  SELECT  \n");
		tableSql.append(" ehm  \n");
		tableSql.append(" FROM VN_TURN  \n");
		tableSql.append(" WHERE CODE='" + otVo.getOverTimeClass() + "' \n");
		return tableSql.toString();
	}

	/**
	 * 日考勤表excel Sql
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getDailyExcelSql(leaveCardVO lcVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		// 遲到名單

		sql = hu.gethtml(sqlConsts.sql_excelDaily);
		sql = sql.replace("<DAY/>", lcVo.getApplicationDate());

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
		tableSql.append(" FROM  VN_YEAR_MONTH_ATTENDANCE   where YEAR='" + raVo.getQueryYearMonth().split("/")[0] + "'    \n");
		tableSql.append(" and  MONTH='" + raVo.getQueryYearMonth().split("/")[1] + "'    \n");
		tableSql.append(" and  DEPARTMENT='" + raVo.getSearchDepartmen() + "'    \n");
		if (!raVo.getSearchUnit().equals("0"))
		{
			tableSql.append(" and  UNIT='" + raVo.getSearchUnit() + "'    \n");
		}
		return tableSql.toString();
	}

	/**
	 * 月份考勤表-修改統計資料
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String upRepAttendance(repAttendanceVO raVo)
	{
		StringBuilder Sb = new StringBuilder(" update VN_YEAR_MONTH_ATTENDANCE	 ").append(" set  NOTE='" + raVo.getNOTE() + "'  ");
		Sb.append("  where EMPLOYEENO='" + raVo.getEMPLOYEENO() + "' ");
		Sb.append("  and YEAR='" + raVo.getQueryYearMonth().split("/")[0] + "' ");
		Sb.append("  and MONTH='" + raVo.getQueryYearMonth().split("/")[1] + "' ");
		return Sb.toString();
	}

	/**
	 * 查询請假開始时间或結束时间有無在打卡區間时间內
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String getOnlyWorkTime(leaveCardVO lcVo)
	{
		StringBuilder Sb = new StringBuilder(" select WorkFDate,WorkEDate   from   PWERP_MS.dbo.RsKQResult R,PWERP_MS.dbo.RsEmployee E ,hr.dbo.HR_EMPLOYEE V ");
		Sb.append("  where   V.EMPLOYEENO=E.EmpID ");
		Sb.append("    and V.ID='" + lcVo.getSearchEmployeeNo() + "'  ");
		Sb.append(" and WorkFDate <='" + lcVo.getStartLeaveDate().replaceAll("/", "") + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute() + "' ");
		Sb.append(" and WorkEDate >=  '" + lcVo.getEndLeaveDate().replaceAll("/", "") + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute() + "' ");

		return Sb.toString();
	}

	/**
	 * 查询請假開始时间有無請假
	 * 
	 * @param unitID
	 * @return
	 */
	public static final String getOnlyLeaveTime(leaveCardVO lcVo)
	{
		StringBuilder Sb = new StringBuilder("  select STARTLEAVEDATE from hr.dbo.VN_LEAVECARD V ");
		Sb.append("	where V.EP_ID='" + lcVo.getSearchEmployeeNo() + "'  ");
		Sb.append(" and STARTLEAVEDATE >= '" + lcVo.getStartLeaveDate().replaceAll("/", "") + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute() + "' ");
		Sb.append(" and STARTLEAVEDATE <= '" + lcVo.getEndLeaveDate().replaceAll("/", "") + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute() + "' ");
		Sb.append("  Union All ");
		Sb.append("  select STARTLEAVEDATE from hr.dbo.VN_LEAVECARD V ");
		Sb.append("	where V.EP_ID='" + lcVo.getSearchEmployeeNo() + "'  ");
		Sb.append(" and ENDLEAVEDATE >= '" + lcVo.getStartLeaveDate().replaceAll("/", "") + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute() + "' ");
		Sb.append(" and ENDLEAVEDATE <= '" + lcVo.getEndLeaveDate().replaceAll("/", "") + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute() + "' ");
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
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_noVnMaxPeople);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_VnMaxPeople);
		Date date = sdf.parse(lcVo.getApplicationDate().replaceAll("/", ""));
		date = DateUtil.addDays(date, -1);

		sql = sql.replace("<toDay>", sdf.format(date));
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

		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_VnMaxPeople);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
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

		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_VnActualPeople);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		// logger.info("getActualMaxPeople sql :"+sql);
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

		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnTurnA1People);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		// logger.info("getActualMaxPeople sql :"+sql);
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

		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnTurnC1People);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		// logger.info("getActualMaxPeople sql :"+sql);
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

		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnTurnC2People);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		// logger.info("getActualMaxPeople sql :"+sql);
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

		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnTurnC3People);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		// logger.info("getActualMaxPeople sql :"+sql);
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
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnOverTimePeople);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));

		return sql;
	}

	/**
	 * 部门查询
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getDeptID(String UserEmployeeNo)
	{
		String sql = "select ID from VN_DEPARTMENT where DEPARTMENT =N'" + UserEmployeeNo + "'";
		return sql;
	}

	/**
	 * 部门查询中文
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getDeptName(String ID)
	{
		String sql = "select DEPARTMENT from VN_DEPARTMENT where ID =N'" + ID + "'";
		return sql;
	}

	/**
	 * 假別查询
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getVnHolidayPeople(leaveCardVO lcVo, String hdID) throws ParseException
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnOverTimePeople);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		sql = sql.replace("<hdID>", hdID);
		return sql;
	}

	/**
	 * 曠工查询
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getVnAbsenteeismPeople(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnAbsenteeismPeople);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}

	/**
	 * 新人報道查询
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getVnInDatePeople(leaveCardVO lcVo) throws ParseException
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInDatePeople);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}

	/**
	 * 離職查询
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getvnNoLeavePeople(leaveCardVO lcVo) throws ParseException
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnNoLeavePeople);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}

	/**
	 * 調動查询
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getDayTransfer(leaveCardVO lcVo) throws ParseException
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetDayTransfer);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
		return sql;
	}

	/**
	 * 1年以下查询
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getVnLessYear(leaveCardVO lcVo, String sqlConsts) throws ParseException
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts);
		sql = sql.replace("<toDay>", lcVo.getApplicationDate().replaceAll("/", ""));
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
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInsertAttendanceDay);
		sql = sql.replace("<YMD>", dw.getYMD());
		sql = sql.replace("<DEPARTMENT>", dw.getDEPARTMENT());
		sql = sql.replace("<UNIT>", dw.getUNIT());
		sql = sql.replace("<ROW>", dw.getROW());
		sql = sql.replace("<C1>", dw.getC1());
		sql = sql.replace("<C2>", dw.getC2());
		sql = sql.replace("<C3>", dw.getC3());
		sql = sql.replace("<C4>", dw.getC4());
		sql = sql.replace("<C5>", dw.getC5());

		sql = sql.replace("<C6>", dw.getC6());
		sql = sql.replace("<C7>", dw.getC7());
		sql = sql.replace("<C8>", dw.getC8());
		sql = sql.replace("<C9>", dw.getC9());
		sql = sql.replace("<C10>", dw.getC10());

		sql = sql.replace("<C11>", dw.getC11());
		sql = sql.replace("<C12>", dw.getC12());
		sql = sql.replace("<C13>", dw.getC13());
		sql = sql.replace("<C14>", dw.getC14());
		sql = sql.replace("<C15>", dw.getC15());

		sql = sql.replace("<C16>", dw.getC16());
		sql = sql.replace("<C17>", dw.getC17());
		sql = sql.replace("<C18>", dw.getC18());
		sql = sql.replace("<C19>", dw.getC19());
		sql = sql.replace("<C20>", dw.getC20());

		return sql;
	}

	/**
	 * 查询全廠報表
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getAttendanceDay(String YMD) throws ParseException
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetAttendanceDay);
		sql = sql.replace("<YMD>", YMD);
		return sql;
	}

	/**
	 * 查询全廠報表excel
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getExcelAttendanceDay(String YMD) throws ParseException
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetExcelAttendanceDay);
		sql = sql.replace("<YMD>", YMD);
		return sql;
	}

	/**
	 * 越籍年資分布(一行)
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String saveEmpnumRow(dayAttendanceWO dw) throws ParseException
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInsertEmpnum);
		sql = sql.replace("<YMDKEY>", dw.getYMDKEY());
		sql = sql.replace("<YMD>", dw.getYMD());
		sql = sql.replace("<YEAR>", dw.getYEAR());
		sql = sql.replace("<EMPNUM>", dw.getEMPNUM());
		sql = sql.replace("<PERCENTAGE>", dw.getPERCENTAGE());
		return sql;
	}

	/**
	 * 查询越籍年資分布
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getEmpnum(String YMD) throws ParseException
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetEmpnum);
		sql = sql.replace("<YMD>", YMD);
		return sql;
	}

	/**
	 * 查询全廠報表excel
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String getExcelEmpnum(String YMD) throws ParseException
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetExcelEmpnum);
		sql = sql.replace("<YMD>", YMD);
		return sql;
	}

	/**
	 * 新增下拉加班原因
	 * 
	 * @param lcVo
	 * @return
	 * @throws ParseException
	 */
	public static final String saveReasons(overTimeVO otVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInsertReasons);
		sql = sql.replace("<REASONS>", otVo.getUserReason());

		return sql;
	}

	public static final String getReasons(overTimeVO otVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetReasons);
		sql = sql.replace("<REASONS>", otVo.getUserReason());
		return sql;
	}

	/**
	 * 查询加班原因
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getVnLreasons(editLreasonsVO edVo) throws Exception
	{
		StringBuilder Sb = new StringBuilder("  select  ");
		Sb.append(" ID,  \n");
		Sb.append(" REASONS as 加班原因,  \n");
		Sb.append(" SORT_NO as 排序编号,  \n");
		Sb.append("Ename as 英语  \n");
		Sb.append("FROM [hr].[dbo].[VN_LREASONS]   \n");
		return Sb.toString();
	}

	/**
	 * 查询請假原因
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getVnLhReasons(editLholidayVO edVo) throws Exception
	{
		StringBuilder Sb = new StringBuilder("  select  ");
		Sb.append(" ID,  \n");
		Sb.append(" HOLIDAYNAME as 请假原因,  \n");
		Sb.append(" SORT_NO as 排序编号,  \n");
		Sb.append(" HOLIDAYCLAS as 代码  \n");
		Sb.append("FROM [hr].[dbo].[VN_LHOLIDAY]   \n");
		return Sb.toString();
	}

	public static final String saveLreasons(editLreasonsVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInsertLreasons);
		System.out.println(" 1 sql :" + sql);
		sql = sql.replace("<REASONS/>", edVo.getReasons());

		sql = sql.replace("<SORT_NO/>", edVo.getSortNo());

		sql = sql.replace("<VName/>", edVo.getVName());

		sql = sql.replace("<Ename/>", edVo.getEName());

		return sql;
	}

	public static final String deleteLreasons(editLreasonsVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnDeleteLreasons);
		sql = sql.replace("<rowID/>", edVo.getRowID());
		// System.out.println("delete sql="+sql);
		return sql;
	}

	public static final String updateLreasons(editLreasonsVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnUpdateLreasons);
		sql = sql.replace("<REASONS/>", edVo.getReasons());
		sql = sql.replace("<SORT_NO/>", edVo.getSortNo());
		sql = sql.replace("<VName/>", edVo.getVName());
		sql = sql.replace("<Ename/>", edVo.getEName());
		sql = sql.replace("<rowID/>", edVo.getRowID());
		// System.out.println("update sql="+sql);
		return sql;
	}

	public static final String saveLholiday(editLholidayVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInsertLholiday);
		// System.out.println("edVo.getHolidIyName()
		// sql="+edVo.getHolidIyName());
		sql = sql.replace("<HOLIDAYNAME/>", edVo.getHolidIyName());
		// System.out.println("edVo.gettHolidIyClas()
		// sql="+edVo.getHolidIyClas());
		sql = sql.replace("<HOLIDAYCLAS/>", edVo.getHolidIyClas());
		sql = sql.replace("<HOLIDAYVNAME/>", edVo.getVName());
		sql = sql.replace("<Ename/>", edVo.getEName());
		sql = sql.replace("<SORTNO/>", edVo.getSortNo());
		// System.out.println("update sql="+sql);
		return sql;
	}

	public static final String deleteLholiday(editLholidayVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnDeleteLholiday);
		sql = sql.replace("<rowID/>", edVo.getRowID());
		return sql;
	}

	public static final String updateLholiday(editLholidayVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnUpdateLholiday);
		sql = sql.replace("<HOLIDAYNAME/>", edVo.getHolidIyName());
		sql = sql.replace("<HOLIDAYCLAS/>", edVo.getHolidIyClas());
		sql = sql.replace("<HOLIDAYVNAME/>", edVo.getVName());
		sql = sql.replace("<Ename/>", edVo.getEName());
		sql = sql.replace("<SORTNO/>", edVo.getSortNo());
		sql = sql.replace("<rowID/>", edVo.getRowID());
		// System.out.println("update sql="+sql);
		return sql;
	}

	/**
	 * 查询待工原因
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getVnStopReasons(editStopReasonVO edVo) throws Exception
	{
		StringBuilder Sb = new StringBuilder("  select  ");
		Sb.append(" ID,  \n");
		Sb.append(" STOPRESON as 待工原因,  \n");
		Sb.append(" SORT_NO as 排序编号,  \n");
		Sb.append(" EName as 英语  \n");
		Sb.append(" FROM [hr].[dbo].[VN_STOPWORKRESON]  \n");
		return Sb.toString();
	}

	/**
	 * 查询加班超時員工權限
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getOvertimePermission(editSupplementVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetOvertimePermission);
		return sql;
	}

	/**
	 * 新增加班超時員工權限
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String saveOvertimePermission(editSupplementVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInsertOvertimePermission);
		System.out.println("edVo.getHolidIyName() sql=" + sql);
		sql = sql.replace("<EMPLOYEENO/>", edVo.getSearchEmployeeNo());
		sql = sql.replace("<TIMEOUT_PERMISSIONS/>", "1");
		System.out.println("save sql=" + sql);
		return sql;
	}

	/**
	 * 刪除加班超時員工權限
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String deleteOvertimePermission(editSupplementVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnDeleteSupplement);
		sql = sql.replace("<rowID/>", edVo.getRowID());
		return sql;
	}

	/**
	 * 查询調職員工資料
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getTransferStaff(editSupplementVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetTransferStaff);
		return sql;

	}

	public static final String saveTransferStaff(editSupplementVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInsertTransferStaff);
		System.out.println("edVo.getHolidIyName() sql=" + sql);
		sql = sql.replace("<EMPLOYEENO/>", edVo.getSearchEmployeeNo());
		sql = sql.replace("<TRANSFER_STAFF/>", "1");
		sql = sql.replace("<TRANSFER_DATE/>", edVo.getTransferDate());
		System.out.println("save sql=" + sql);
		return sql;

	}

	public static final String saveStopReasons(editStopReasonVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnInsertStopReason);
		// System.out.println("edVo.getHolidIyName()
		// sql="+edVo.getStopReason());
		sql = sql.replace("<STOPRESON/>", edVo.getStopReason());
		sql = sql.replace("<VName/>", edVo.getVName());
		sql = sql.replace("<EName/>", edVo.getEName());
		sql = sql.replace("<SORTNO/>", edVo.getSortNo());
		// System.out.println("update sql="+sql);
		return sql;
	}

	public static final String deleteStopReasons(editStopReasonVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnDeleteStopReason);
		sql = sql.replace("<rowID/>", edVo.getRowID());
		return sql;
	}

	public static final String updateStopReasons(editStopReasonVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnUpdateStopReason);
		sql = sql.replace("<STOPRESON/>", edVo.getStopReason());
		sql = sql.replace("<EName/>", edVo.getEName());
		sql = sql.replace("<VName/>", edVo.getVName());
		sql = sql.replace("<SORTNO/>", edVo.getSortNo());
		sql = sql.replace("<rowID/>", edVo.getRowID());
		// System.out.println("update sql="+sql);
		return sql;
	}

	/**
	 * 加班申請查询有無超時加班權限
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getSupplement(overTimeVO otVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_vnGetSupplement);
		sql = sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
		return sql;

	}

	/**
	 * 查询UNIT關聯有無此人
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getEmployUnitCount(String EmployeeNo) throws Exception
	{
		StringBuilder tableSql = new StringBuilder("  SELECT count([ID]) as count   \n");
		tableSql.append(" FROM [hr].[dbo].[VN_EMPLOYEE_UNIT]");
		tableSql.append("	 where [EMPLOYEENO]='" + EmployeeNo + "'");

		return tableSql.toString();
	}

	/**
	 * save UNIT關聯
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String saveEmployUnit(leaveCardVO lcVo) throws Exception
	{
		StringBuilder tableSql = new StringBuilder("  INSERT INTO [hr].[dbo].[VN_EMPLOYEE_UNIT]  \n");
		tableSql.append("     ([EMPLOYEENO] ");
		tableSql.append("	  ,[UNIT_ID] ");
		tableSql.append("	   ,[DEPARTMENT_ID]) ");
		tableSql.append("	    VALUES ");
		tableSql.append("	    ('" + lcVo.getSearchEmployee() + "'  ,");
		tableSql.append("	    '" + lcVo.getSearchUnit() + "'  ,");
		tableSql.append("	    '" + lcVo.getSearchDepartmen() + "' )");

		return tableSql.toString();
	}

	/**
	 * 權限名單
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getRole(leaveCardVO lcVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_vnGetRole);
		sql = sql.replace("<DEPTID/>", lcVo.getSearchDepartmen());

		return sql;
	}

	/**
	 * 個人申請加班更新資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String updateEmpOverTime(overTimeVO otVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_updateEmpOverTime);
		sql = sql.replace("<rowID>", otVo.getRowID());
		sql = sql.replace("<APPLICATION_HOURS/>", otVo.getAddTime());
		sql = sql.replace("<OVERTIME_START/>", otVo.getQueryDate() + " " + otVo.getStartTimeHh() + ":" + otVo.getStartTimemm());
		sql = sql.replace("<OVERTIME_END/>", otVo.getQueryDate() + " " + otVo.getEndTimeHh() + ":" + otVo.getEndTimemm());
		sql = sql.replace("<REASONS/>", otVo.getSearchReasons());
		sql = sql.replace("<UNIT/>", otVo.getSearchUnit());
		sql = sql.replace("<NOTE/>", otVo.getNote());
		sql = sql.replace("<USERREASONS/>", otVo.getUserReason());
		sql = sql.replace("<TURN/>", otVo.getOverTimeClass());
		return sql;
	}

	/**
	 * 部门申請加班更新資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String updateDepOverTime(overTimeVO otVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_updateDepOverTime);
		sql = sql.replace("<rowID>", otVo.getRowID());
		sql = sql.replace("<APPLICATION_HOURS/>", otVo.getAddTime());
		sql = sql.replace("<OVERTIME_START/>", otVo.getQueryDate() + " " + otVo.getStartTimeHh() + ":" + otVo.getStartTimemm());
		sql = sql.replace("<OVERTIME_END/>", otVo.getQueryDate() + " " + otVo.getEndTimeHh() + ":" + otVo.getEndTimemm());
		sql = sql.replace("<REASONS/>", otVo.getSearchReasons());
		sql = sql.replace("<UNIT/>", otVo.getSearchUnit());
		sql = sql.replace("<NOTE/>", otVo.getNote());
		sql = sql.replace("<USERREASONS/>", otVo.getUserReason());
		sql = sql.replace("<TURN/>", otVo.getOverTimeClass());
		sql = sql.replace("<EP_ID/>", otVo.getSearchEmployee());
		return sql;
	}

	/**
	 * 部门申請加班更新主資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String updateDepOverTimeM(overTimeVO otVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_updateDepOverTimeM);
		sql = sql.replace("<MID/>", otVo.getmID());
		sql = sql.replace("<DAY/>", otVo.getQueryDate());
		sql = sql.replace("<DEPARTMENT/>", otVo.getSearchDepartmen());

		return sql;
	}

	/**
	 * 個人申請請假更新資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String updateEmpLeavecard(leaveCardVO lcVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_updateEmpLeavecard);
		sql = sql.replace("<rowID/>", lcVo.getRowID());
		sql = sql.replace("<HD_ID/>", lcVo.getSearchHoliday());
		sql = sql.replace("<STARTLEAVEDATE/>", lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute());
		sql = sql.replace("<ENDLEAVEDATE/>", lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute());
		sql = sql.replace("<AGENT/>", lcVo.getSearchAgent());
		sql = sql.replace("<DAYCOUNT/>", lcVo.getDayCount());
		sql = sql.replace("<HOURCOUNT/>", lcVo.getHourCount());
		// sql=sql.replace("<USERREASONS/>", lcVo.get);
		sql = sql.replace("<MINUTECOUNT/>", lcVo.getMinuteCount());
		sql = sql.replace("<NOTE/>", lcVo.getNote());

		return sql;
	}

	/**
	 * 部门申請請假更新資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String updateDepLeavecard(leaveCardVO lcVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_updateDepLeavecard);
		sql = sql.replace("<rowID/>", lcVo.getRowID());
		sql = sql.replace("<HD_ID/>", lcVo.getSearchHoliday());
		sql = sql.replace("<STARTLEAVEDATE/>", lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() + ":" + lcVo.getStartLeaveMinute());
		sql = sql.replace("<ENDLEAVEDATE/>", lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() + ":" + lcVo.getEndLeaveMinute());
		sql = sql.replace("<AGENT/>", lcVo.getSearchAgent());
		sql = sql.replace("<DAYCOUNT/>", lcVo.getDayCount());
		sql = sql.replace("<HOURCOUNT/>", lcVo.getHourCount());
		// sql=sql.replace("<USERREASONS/>", lcVo.get);
		sql = sql.replace("<MINUTECOUNT/>", lcVo.getMinuteCount());
		sql = sql.replace("<NOTE/>", lcVo.getNote());
		sql = sql.replace("<EP_ID/>", lcVo.getSearchEmployeeNo());
		return sql;
	}

	/**
	 * 日考勤表-查出資料
	 * 
	 * @param lcVo
	 * @return
	 */
	public static final String getDayReport(leaveCardVO lcVo)
	{

		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_getDayReport);
		sql = sql.replace("<DAY/>", lcVo.getApplicationDate());
		return sql;
	}

	/**
	 * 因遲到改寫出勤時數
	 * 
	 * @param flag
	 * @param ID
	 * @return
	 */
	public static final String updateDayReportAttendance(leaveCardVO lcVo, double fAttendance, String EMPLOYEENO)
	{
		StringBuilder Sb = new StringBuilder(" UPDATE [hr].[dbo].[VN_DAY_REPORT] ").append("SET [ATTENDANCE] ='" + fAttendance + "' ").append("WHERE [DAY]='" + lcVo.getApplicationDate() + "'  ").append("and [EMPLOYEENO]='" + EMPLOYEENO + "'  ");

		return Sb.toString();
	}

	/**
	 * 因有請假改寫曠工時間
	 * 
	 * @param flag
	 * @param ID
	 * @return
	 */
	public static final String updateDayReportdNotWork(leaveCardVO lcVo, String dNotWork, String EMPLOYEENO)
	{
		StringBuilder Sb = new StringBuilder(" UPDATE [hr].[dbo].[VN_DAY_REPORT] ").append("SET [NOTWORK] ='" + dNotWork + "' ").append("WHERE [DAY]='" + lcVo.getApplicationDate() + "'  ").append("and [EMPLOYEENO]='" + EMPLOYEENO + "'  ");

		return Sb.toString();
	}

	public static final String insterMonthdailyReport(leaveCardVO lcVo, dayReportRO dr, monthReportNoteWO mW)
	{
		
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(SqlUtil.class);
		
		long startTime=System.currentTimeMillis();//记录开始时间
		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_InsterMonthReport);
		long endTime=System.currentTimeMillis();//记录结束时间
		float excTime=(float)(endTime-startTime)/1000;
		logger.info("  讀出 InsterMonthReport 時間："+excTime+"s");
		
		startTime=System.currentTimeMillis();//记录开始时间
		
		sql = sql.replace("<EMPLOYEENO/>", dr.getEMPLOYEENO());
		sql = sql.replace("<YEAR/>", lcVo.getApplicationDate().split("/")[0]);
		sql = sql.replace("<MONTH/>", lcVo.getApplicationDate().split("/")[1]);
		sql = sql.replace("<EMPLOYEE/>", dr.getEMPLOYEE());
		sql = sql.replace("<DEPARTMENT/>", dr.getDEPARTMENT());
		sql = sql.replace("<UNIT/>", dr.getUNIT());
		
		endTime=System.currentTimeMillis();//记录结束时间
		excTime=(float)(endTime-startTime)/1000;
		logger.info("  UNIT 時間："+excTime+"s");
		
		startTime=System.currentTimeMillis();//记录开始时间
		sql = sql.replace("<DAY1/>", vnStringUtil.changeString(mW.getDAY1()));
		sql = sql.replace("<DAY2/>", vnStringUtil.changeString(mW.getDAY2()));
		sql = sql.replace("<DAY3/>", vnStringUtil.changeString(mW.getDAY3()));
		sql = sql.replace("<DAY4/>", vnStringUtil.changeString(mW.getDAY4()));
		sql = sql.replace("<DAY5/>", vnStringUtil.changeString(mW.getDAY5()));
		sql = sql.replace("<DAY6/>", vnStringUtil.changeString(mW.getDAY6()));
		sql = sql.replace("<DAY7/>", vnStringUtil.changeString(mW.getDAY7()));
		sql = sql.replace("<DAY8/>", vnStringUtil.changeString(mW.getDAY8()));
		sql = sql.replace("<DAY9/>", vnStringUtil.changeString(mW.getDAY9()));
		sql = sql.replace("<DAY10/>", vnStringUtil.changeString(mW.getDAY10()));
		sql = sql.replace("<DAY11/>", vnStringUtil.changeString(mW.getDAY11()));
		sql = sql.replace("<DAY12/>", vnStringUtil.changeString(mW.getDAY12()));
		sql = sql.replace("<DAY13/>", vnStringUtil.changeString(mW.getDAY13()));
		sql = sql.replace("<DAY14/>", vnStringUtil.changeString(mW.getDAY14()));
		sql = sql.replace("<DAY15/>", vnStringUtil.changeString(mW.getDAY15()));
		sql = sql.replace("<DAY16/>", vnStringUtil.changeString(mW.getDAY16()));
		sql = sql.replace("<DAY17/>", vnStringUtil.changeString(mW.getDAY17()));
		sql = sql.replace("<DAY18/>", vnStringUtil.changeString(mW.getDAY18()));
		sql = sql.replace("<DAY19/>", vnStringUtil.changeString(mW.getDAY19()));
		sql = sql.replace("<DAY20/>", vnStringUtil.changeString(mW.getDAY20()));
		sql = sql.replace("<DAY21/>", vnStringUtil.changeString(mW.getDAY21()));
		sql = sql.replace("<DAY22/>", vnStringUtil.changeString(mW.getDAY22()));
		sql = sql.replace("<DAY23/>", vnStringUtil.changeString(mW.getDAY23()));
		sql = sql.replace("<DAY24/>", vnStringUtil.changeString(mW.getDAY24()));
		sql = sql.replace("<DAY25/>", vnStringUtil.changeString(mW.getDAY25()));
		sql = sql.replace("<DAY26/>", vnStringUtil.changeString(mW.getDAY26()));
		sql = sql.replace("<DAY27/>", vnStringUtil.changeString(mW.getDAY27()));
		sql = sql.replace("<DAY28/>", vnStringUtil.changeString(mW.getDAY28()));
		sql = sql.replace("<DAY29/>", vnStringUtil.changeString(mW.getDAY29()));
		sql = sql.replace("<DAY30/>", vnStringUtil.changeString(mW.getDAY30()));
		sql = sql.replace("<DAY31/>", vnStringUtil.changeString(mW.getDAY31()));
		
		endTime=System.currentTimeMillis();//记录结束时间
		excTime=(float)(endTime-startTime)/1000;
		logger.info("  DAY 時間："+excTime+"s");
		
		startTime=System.currentTimeMillis();//记录开始时间
		sql = sql.replace("<NOTE/>", vnStringUtil.changeNote(mW));

		endTime=System.currentTimeMillis();//记录结束时间
		excTime=(float)(endTime-startTime)/1000;
		logger.info("  NOTE 時間："+excTime+"s");
		
		return sql;
	}

	public static final String updateMonthdailyReport(leaveCardVO lcVo, dayReportRO dr, monthReportNoteWO mW)
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_updateMonthReport);
		sql = sql.replace("<EMPLOYEENO/>", dr.getEMPLOYEENO());
		sql = sql.replace("<YEAR/>", lcVo.getApplicationDate().split("/")[0]);
		sql = sql.replace("<MONTH/>", lcVo.getApplicationDate().split("/")[1]);
		String day = lcVo.getApplicationDate().split("/")[2];
		if (day.substring(0, 1).equals("0"))
		{
			day = "DAY" + day.substring(1, 2);
		}
		else
		{
			day = "DAY" + day;
		}
		sql = sql.replace("<DAYX>", day + "='" + vnStringUtil.getDAY(dr) + "'");
		vnStringUtil.getNOTE(dr, mW);
		return sql;
	}
	
	
	/**
	 * 查询部門詳細資料
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getVnDept(editDeptUnit edVo) throws Exception
	{
		StringBuilder Sb = new StringBuilder("  select  ");
		Sb.append(" ID as 部门代号 , \n");
		Sb.append(" DEPARTMENT as 部门名称, \n");
		Sb.append(" ENAME as 英文名  \n");
		Sb.append(" FROM [hr].[dbo].[VN_DEPARTMENT]   \n");
		return Sb.toString();
	}
	/**
	 * 查询單位詳細資料
	 * 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getVnUnit(editDeptUnit edVo) throws Exception
	{
		StringBuilder Sb = new StringBuilder("  select  ");
		Sb.append(" ID,  \n");
		Sb.append(" [DEPARTMENT_ID] as 部门代号,  \n");
		Sb.append(" [UNIT] as 单位名称,  \n");
		Sb.append(" [ENAME] as 英文名  \n");
		Sb.append(" FROM [hr].[dbo].[VN_UNIT]  \n");
		Sb.append("  ORDER BY ID    \n");
		return Sb.toString();
	}
	
	/**
	 * 刪除單位
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String deleteUnit(editDeptUnit edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_deleteUnit);
		sql = sql.replace("<rowID/>", edVo.getUrowID());
		return sql;
	}
	/**
	 * 更新單位
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String updateUnitData(editDeptUnit edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_updateUnitData);
		sql = sql.replace("<DEPARTMENT/>", edVo.getDept());
		sql = sql.replace("<UNIT/>", edVo.getUName());
		sql = sql.replace("<ENAME/>", edVo.getUEName());
		sql = sql.replace("<rowID/>", edVo.getUrowID());
		return sql;
	}
	/**
	 * 新增單位
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String InsterUnitData(editDeptUnit edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_InsterUnit);
		sql = sql.replace("<DID/>", edVo.getDept());
		sql = sql.replace("<UNIT/>", edVo.getUName());
		sql = sql.replace("<ENAME/>", edVo.getUEName());
		return sql;
	}

	/**
	 * 查詢部門有無相關單位 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getUnitDeptCount(editDeptUnit edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_getUnitDeptCount);
		sql = sql.replace("<DEPT/>", edVo.getDID());
		return sql;
	}
	/**
	 * 新增部門
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String InsterDept(editDeptUnit edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_InsterDept);
		sql = sql.replace("<ID/>", edVo.getDID());
		sql = sql.replace("<DEPARTMENT/>", edVo.getDName());
		sql = sql.replace("<ENAME/>", edVo.getDEName());
		return sql;
	}
	/**
	 * 刪除部門
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String deleteDept(editDeptUnit edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_deleteDept);
		sql = sql.replace("<rowID/>", edVo.getDrowID());
		return sql;
	}
	/**
	 * 刪除部門
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String updateDept(editDeptUnit edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_updateDept);
		sql = sql.replace("<DEPARTMENT/>", edVo.getDName());
		sql = sql.replace("<ENAME/>", edVo.getDEName());
		sql = sql.replace("<rowID/>", edVo.getDrowID());
		return sql;
	}
	
	/**
	 * 查詢部門有無相關單位 
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String getDeptIDCount(editDeptUnit edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_getDeptIDCount);
		sql = sql.replace("<ID/>", edVo.getDID());
		return sql;
	}
	/**
	 * 更新現有主管email資料
	 */
	
	public static final String updateHROEmail(editSupervisorVO edVo) throws Exception
	{
		HtmlUtil hu = new HtmlUtil();
		String sql = hu.gethtml(sqlConsts.sql_updateHROEmail);
		sql = sql.replace("<ID/>", edVo.getOEmployeeNo());
		sql = sql.replace("<Email/>", edVo.getOEmail());
		return sql;
	}
}
