package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

public class dailyVO  implements Serializable
{
	/**
	 * 每日考勤 頁面參數 物件
	 * @author Antonis.chen
	 *
	 */
	private static final long serialVersionUID = 7213593713535360585L;

	/**年月日 */
	private String  queryYearMonth;
	/**部門 */
	private String searchDepartmen;
	/**單位 */
	private String searchUnit;
	/**是否顯示查詢結果*/
	private boolean  showDataTable;
	/**資料庫訊息*/
	private String  msg;
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
	public String getSearchDepartmen()
	{
		return searchDepartmen;
	}
	public void setSearchDepartmen(String searchDepartmen)
	{
		this.searchDepartmen = searchDepartmen;
	}
	public String getSearchUnit()
	{
		return searchUnit;
	}
	public void setSearchUnit(String searchUnit)
	{
		this.searchUnit = searchUnit;
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
	@Override
	public String toString()
	{
		return "dailyVO [queryYearMonth=" + queryYearMonth + ", searchDepartmen=" + searchDepartmen + ", searchUnit=" + searchUnit + ", showDataTable=" + showDataTable + ", msg=" + msg + ", ActionURI=" + ActionURI + "]";
	}
	
	
	
}
