package cn.com.maxim.portal.attendan.ro;
/**
 * 個人請假查詢員工資料
 * @author Antonis.chen
 *
 */
public class processUserRO
{
	private String DEPARTMENT;
	
	private String UNIT;
	
	private String ROLE;
	
	private String STATUS;
	
	private String GROUP;

	public String getGROUP() {
	    return GROUP;
	}
	public void setGROUP(String gROUP) {
	    GROUP = gROUP;
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
