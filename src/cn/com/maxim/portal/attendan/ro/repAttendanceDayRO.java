package cn.com.maxim.portal.attendan.ro;
/**
 * 寫入VN_YEAR_MONTH_PLANT_ATTENDANCE 表
 * 全廠日出勤狀況報表 查询 RO 
 * @author antonis.chen
 *
 */
public class repAttendanceDayRO
{
	/** 日期 Day **/
	private String  DAY;
	/** 部门 **/
	private String  DEPARTMENT;
	/** 单位 **/
	private String  UNIT;
	/** 人數 **/
	private String  EmpInCount;
	
	public String getDAY()
	{
		return DAY;
	}
	public void setDAY(String dAY)
	{
		DAY = dAY;
	}
	public String getDEPARTMENT()
	{
		return DEPARTMENT;
	}
	public void setDEPARTMENT(String dEPARTMENT)
	{
		DEPARTMENT = dEPARTMENT;
	}
	public String getUNIT()
	{
		return UNIT;
	}
	public void setUNIT(String uNIT)
	{
		UNIT = uNIT;
	}
	public String getEmpInCount()
	{
		return EmpInCount;
	}
	public void setEmpInCount(String empInCount)
	{
		EmpInCount = empInCount;
	}
	
	
}
