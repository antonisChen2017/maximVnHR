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

	/**年月 */
	public String  queryYearMonth;
	/** 遲到:1/早退:2  */
	public String  queryIsLate;
	/**是否顯示查詢結果*/
	public boolean  showDataTable;
	/**資料庫訊息*/
	public String  msg;
	
	
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
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	
	
	
	
}
