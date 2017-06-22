package cn.com.maxim.portal.attendan.ro;
/**
 * 請假查詢流程
 * @author Antonis.chen
 *
 */
public class processIDUserRO
{
private String DEPARTMENT;
	
	private String UNIT;
	
	private String ROLE;
	
	private String STATUS;
	
	private String DAYCOUNT;
		
	public String getDAYCOUNT()
	{
		return DAYCOUNT;
	}
	public void setDAYCOUNT(String dAYCOUNT)
	{
		DAYCOUNT = dAYCOUNT;
	}
	public String getSTATUS()
	{
		return STATUS;
	}
	public void setSTATUS(String sTATUS)
	{
		STATUS = sTATUS;
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
}
