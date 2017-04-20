package cn.com.maxim.portal.attendan.ro;

import java.io.Serializable;
import java.util.Date;
/**
 * 員工個人資料  資料庫物件
 * @author Antonis.chen
 *
 */
public class employeeUserRO  
{
	/**員工個人資料  ID **/
	private String  ID;
	
	/** 單位 ID **/
	private String  UID;
	
	/** 到職日 **/
	private String  ENTRYDATE;
	
	/** 職稱 **/
	private String  DUTIES;
	
	/**單位 */
	private String UNIT;
	
	/**部門 */
	private String DEPARTMENT;
	
	
	
	/**工號 */
	private String EMPLOYEENO;
	
	/** 部門 ID **/
	private String  DID;
	
	
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
	
	
	
	
	
	
}
