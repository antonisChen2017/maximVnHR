package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

public class editSupervisorVO
{
	
	/**設定行標籤*/
	private String rowID;
	
	/**是否顯示查询結果*/
	private boolean  showDataTable;
	/**資料庫訊息*/
	private String  msg;
	/**ActionURI*/
	private String  ActionURI;
	/**新增或現有人員*/
	private String Romance;
	/**資料庫角色*/
	private String ORole;
	/**現有主管工號*/
	private String OEmployeeNo;
	/**現有主管名稱*/
	private String OEmployee;
	/**現有主管EMAIL*/
	private String OEmail;
	/**資料庫角色(新增用)*/
	private String NRole;
	/**新增主管工號*/
	private String NEmployeeNo;
	/**新增主管名稱*/
	private String NEmployee;
	/**新增EMAIL*/
	private String NEmail;
	/**新增單位*/
	private String NUnit;
	/**新增部門*/
	private String NDepartment;
	/**新增越文名*/
	private String NVietnamese;
	/**新增到職日*/
	private String NEntryDate;
	/**新增職稱*/
	private String NDutes;
	/**設定頁標籤定位*/
	private String Tab;

	
	public String getTab()
	{
		return Tab;
	}
	public void setTab(String tab)
	{
		Tab = tab;
	}
	public String getNUnit()
	{
		return NUnit;
	}
	public void setNUnit(String nUnit)
	{
		NUnit = nUnit;
	}
	public String getNDepartment()
	{
		return NDepartment;
	}
	public void setNDepartment(String nDepartment)
	{
		NDepartment = nDepartment;
	}
	public String getNVietnamese()
	{
		return NVietnamese;
	}
	public void setNVietnamese(String nVietnamese)
	{
		NVietnamese = nVietnamese;
	}
	public String getNEntryDate()
	{
		return NEntryDate;
	}
	public void setNEntryDate(String nEntryDate)
	{
		NEntryDate = nEntryDate;
	}
	public String getNDutes()
	{
		return NDutes;
	}
	public void setNDutes(String nDutes)
	{
		NDutes = nDutes;
	}

	public String getRomance()
	{
		return Romance;
	}
	public void setRomance(String romance)
	{
		Romance = romance;
	}
	public String getORole()
	{
		return ORole;
	}
	public void setORole(String oRole)
	{
		ORole = oRole;
	}
	public String getRowID()
	{
		return rowID;
	}
	public void setRowID(String rowID)
	{
		this.rowID = rowID;
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
	public String getOEmployeeNo()
	{
		return OEmployeeNo;
	}
	public void setOEmployeeNo(String oEmployeeNo)
	{
		OEmployeeNo = oEmployeeNo;
	}
	public String getOEmployee()
	{
		return OEmployee;
	}
	public void setOEmployee(String oEmployee)
	{
		OEmployee = oEmployee;
	}
	public String getOEmail()
	{
		return OEmail;
	}
	public void setOEmail(String oEmail)
	{
		OEmail = oEmail;
	}
	public String getNRole()
	{
		return NRole;
	}
	public void setNRole(String nRole)
	{
		NRole = nRole;
	}
	public String getNEmployeeNo()
	{
		return NEmployeeNo;
	}
	public void setNEmployeeNo(String nEmployeeNo)
	{
		NEmployeeNo = nEmployeeNo;
	}
	public String getNEmployee()
	{
		return NEmployee;
	}
	public void setNEmployee(String nEmployee)
	{
		NEmployee = nEmployee;
	}
	public String getNEmail()
	{
		return NEmail;
	}
	public void setNEmail(String nEmail)
	{
		NEmail = nEmail;
	}
	
	
	
}
