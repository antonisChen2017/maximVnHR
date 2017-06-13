package cn.com.maxim.portal.attendan.ro;

public class exLeaveCardRO
{
	/**行號**/
	private String ID;
	/**員工編號**/
	private String EMPLOYEENO;
	/**員工名稱**/
	private String EMPLOYEE;
	/**部门名稱**/
	private String DEPARTMENT;
	/**单位名稱**/
	private String UNIT;
	/**員工角色**/
	private String ROLE;
	/**假別**/
	private String HID;
	/**請假開始时间**/
	private String STARTLEAVEDATE;
	/**請假結束时间**/
	private String ENDLEAVEDATE;
	/**代理人**/
	private String Agent;
	/**請假天數**/
	private String DAYCOUNT;
	/**請假小時**/
	private String HOURCOUNT;
	/**請假分**/
	private String MINUTECOUNT;
	/**備註**/
	private String NOTE;
	
	public String getAgent()
	{
		return Agent;
	}
	public void setAgent(String agent)
	{
		Agent = agent;
	}
	public String getHID()
	{
		return HID;
	}
	public void setHID(String hID)
	{
		HID = hID;
	}
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public String getEMPLOYEENO()
	{
		return EMPLOYEENO;
	}
	public void setEMPLOYEENO(String eMPLOYEENO)
	{
		EMPLOYEENO = eMPLOYEENO;
	}
	public String getEMPLOYEE()
	{
		return EMPLOYEE;
	}
	public void setEMPLOYEE(String eMPLOYEE)
	{
		EMPLOYEE = eMPLOYEE;
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
	public String getROLE()
	{
		return ROLE;
	}
	public void setROLE(String rOLE)
	{
		ROLE = rOLE;
	}
	public String getSTARTLEAVEDATE()
	{
		return STARTLEAVEDATE;
	}
	public void setSTARTLEAVEDATE(String sTARTLEAVEDATE)
	{
		STARTLEAVEDATE = sTARTLEAVEDATE;
	}
	public String getENDLEAVEDATE()
	{
		return ENDLEAVEDATE;
	}
	public void setENDLEAVEDATE(String eNDLEAVEDATE)
	{
		ENDLEAVEDATE = eNDLEAVEDATE;
	}
	public String getDAYCOUNT()
	{
		return DAYCOUNT;
	}
	public void setDAYCOUNT(String dAYCOUNT)
	{
		DAYCOUNT = dAYCOUNT;
	}
	public String getHOURCOUNT()
	{
		return HOURCOUNT;
	}
	public void setHOURCOUNT(String hOURCOUNT)
	{
		HOURCOUNT = hOURCOUNT;
	}
	public String getMINUTECOUNT()
	{
		return MINUTECOUNT;
	}
	public void setMINUTECOUNT(String mINUTECOUNT)
	{
		MINUTECOUNT = mINUTECOUNT;
	}
	public String getNOTE()
	{
		return NOTE;
	}
	public void setNOTE(String nOTE)
	{
		NOTE = nOTE;
	}
	
	
	
}
