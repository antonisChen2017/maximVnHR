package cn.com.maxim.portal.attendan.ro;

import java.io.Serializable;
import java.util.Date;
/**
 * 員工個人資料  資料表RO
 * @author Antonis.chen
 *
 */
public class employeeUserRO  
{
	/**員工個人資料  ID **/
	private String  ID;
	/**員工個人資料  人名 **/
	private String EMPLOYEE;
	/** 单位 ID **/
	private String  UID;
	
	/** 到職日 **/
	private String  ENTRYDATE;
	
	/** 職稱 **/
	private String  DUTIES;
	
	/**单位 */
	private String UNIT;
	
	/**部门 */
	private String DEPARTMENT;
	
	/**工號 */
	private String EMPLOYEENO;
	
	/** 部门 ID **/
	private String  DID;
	/** 角色 **/
	private String  ROLE;
	
	public String getENTRYDATE()
	{
		return ENTRYDATE;
	}
	public void setENTRYDATE(String eNTRYDATE)
	{
		ENTRYDATE = eNTRYDATE;
	}
	public String getDUTIES()
	{
		return DUTIES;
	}
	public void setDUTIES(String dUTIES)
	{
		DUTIES = dUTIES;
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
	public String getEMPLOYEENO()
	{
		return EMPLOYEENO;
	}
	public void setEMPLOYEENO(String eMPLOYEENO)
	{
		EMPLOYEENO = eMPLOYEENO;
	}
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public String getUID()
	{
		return UID;
	}
	public void setUID(String uID)
	{
		UID = uID;
	}
	public String getDID()
	{
		return DID;
	}
	public void setDID(String dID)
	{
		DID = dID;
	}
	public String getROLE()
	{
		return ROLE;
	}
	public void setROLE(String rOLE)
	{
		ROLE = rOLE;
	}
	public String getEMPLOYEE()
	{
		return EMPLOYEE;
	}
	public void setEMPLOYEE(String eMPLOYEE)
	{
		EMPLOYEE = eMPLOYEE;
	}
	
	
	
	
	
	
}
