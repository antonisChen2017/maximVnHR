package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

public class lateOutEarlyVO  implements Serializable
{

	/**
	 * 遲到早退 頁面參數 物件
	 * @author Antonis.chen
	 *
	 */
	private static final long serialVersionUID = 7213593713535360585L;

	
	/**部门 */
	private String searchDepartmen;
	/**年月 */
	private String  queryYearMonth;
	/** 遲到:1/早退:2  */
	private String  queryIsLate;
	/**是否顯示查询結果*/
	private boolean  showDataTable;
	/**資料庫訊息*/
	private String  msg;
	/**員工代號*/
	private String  EmpID;
	/**ActionURI*/
	private String  ActionURI;
	
	public String getQueryYearMonth()
	{
		return queryYearMonth;
	}
	public void setQueryYearMonth(String queryYearMonth)
	{
		this.queryYearMonth = queryYearMonth;
	}
	public boolean isShowDataTable()
	{
		return showDataTable;
	}
	public void setShowDataTable(boolean showDataTable)
	{
		this.showDataTable = showDataTable;
	}
	
	public String getQueryIsLate()
	{
		return queryIsLate;
	}
	public void setQueryIsLate(String queryIsLate)
	{
		this.queryIsLate = queryIsLate;
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
	public String getEmpID()
	{
		return EmpID;
	}
	public void setEmpID(String empID)
	{
		EmpID = empID;
	}
	
	
	public String getSearchDepartmen()
	{
		return searchDepartmen;
	}
	public void setSearchDepartmen(String searchDepartmen)
	{
		this.searchDepartmen = searchDepartmen;
	}
	@Override
	public String toString()
	{
		return "lateOutEarlyVO [queryYearMonth=" + queryYearMonth + ", queryIsLate=" + queryIsLate + ", showDataTable=" + showDataTable + ", msg=" + msg + ", EmpID=" + EmpID + ", ActionURI=" + ActionURI + "]";
	}

	
	
	
	
	
}
