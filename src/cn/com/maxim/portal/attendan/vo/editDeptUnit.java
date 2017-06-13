package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

public class editDeptUnit  implements Serializable
{

	private static final long serialVersionUID = -8289969948242931749L;
	/**部門編號**/
	private String DID;

	/**部門名稱**/
	private String DName;
	/**部門下拉**/
	private String Dept;
	/**單位名稱**/
	private String UName;
	/**部門行號**/
	private String DrowID;
	/**單位行號**/
	private String UrowID;
	/**部門英文**/
	private String DEName;
	/**單位英文**/
	private String UEName;
	
	/**是否顯示查询結果*/
	private boolean  showDataTable;
	/**資料庫訊息*/
	private String  msg;
	/**ActionURI*/
	private String  ActionURI;
	
	
	public String getDEName()
	{
		return DEName;
	}
	public void setDEName(String dEName)
	{
		DEName = dEName;
	}
	public String getUEName()
	{
		return UEName;
	}
	public void setUEName(String uEName)
	{
		UEName = uEName;
	}
	public String getDID()
	{
		return DID;
	}
	public void setDID(String dID)
	{
		DID = dID;
	}
	public boolean isShowDataTable()
	{
		return showDataTable;
	}
	public void setShowDataTable(boolean showDataTable)
	{
		this.showDataTable = showDataTable;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public String getActionURI()
	{
		return ActionURI;
	}
	public void setActionURI(String actionURI)
	{
		ActionURI = actionURI;
	}
	public String getDName()
	{
		return DName;
	}
	public void setDName(String dName)
	{
		DName = dName;
	}
	public String getDept()
	{
		return Dept;
	}
	public void setDept(String dept)
	{
		Dept = dept;
	}
	public String getUName()
	{
		return UName;
	}
	public void setUName(String uName)
	{
		UName = uName;
	}
	public String getDrowID()
	{
		return DrowID;
	}
	public void setDrowID(String drowID)
	{
		DrowID = drowID;
	}
	public String getUrowID()
	{
		return UrowID;
	}
	public void setUrowID(String urowID)
	{
		UrowID = urowID;
	}
	
	
	
}
