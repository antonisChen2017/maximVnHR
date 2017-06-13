package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

/**
 * 修改請假原因
 * @author antonis.chen
 *
 */
public class editLholidayVO implements Serializable
{
	private static long serialVersionUID = 8171431734547648034L;
	private String rowID;
	private String holidIyName;
	private String  holidIyClas;
	private String sortNo;
	private String VName;
	private String EName;
	/**是否顯示查询結果*/
	private boolean  showDataTable;
	/**資料庫訊息*/
	private String  msg;
	/**ActionURI*/
	private String  ActionURI;
	
	public String getRowID()
	{
		return rowID;
	}
	public void setRowID(String rowID)
	{
		this.rowID = rowID;
	}
	public String getHolidIyName()
	{
		return holidIyName;
	}
	public void setHolidIyName(String holidIyName)
	{
		this.holidIyName = holidIyName;
	}
	public String getHolidIyClas()
	{
		return holidIyClas;
	}
	public void setHolidIyClas(String holidIyClas)
	{
		this.holidIyClas = holidIyClas;
	}
	public String getSortNo()
	{
		return sortNo;
	}
	public void setSortNo(String sortNo)
	{
		this.sortNo = sortNo;
	}
	public String getVName()
	{
		return VName;
	}
	public void setVName(String vName)
	{
		VName = vName;
	}
	public String getEName()
	{
		return EName;
	}
	public void setEName(String eName)
	{
		EName = eName;
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
		return "editLholidayVO [rowID=" + rowID + ", holidIyName=" + holidIyName + ", holidIyClas=" + holidIyClas + ", sortNo=" + sortNo + ", VName=" + VName + ", EName=" + EName + ", showDataTable=" + showDataTable + ", msg=" + msg + ", ActionURI=" + ActionURI + "]";
	}
	
	
}
