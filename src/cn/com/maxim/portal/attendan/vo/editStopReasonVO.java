package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;
/**
 * 修改待工原因
 * @author antonis.chen
 *
 */
public class editStopReasonVO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5448082865034433655L;
	
	private String rowID;
	
	private String stopReason;
	
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
	public String getStopReason()
	{
		return stopReason;
	}
	public void setStopReason(String stopReason)
	{
		this.stopReason = stopReason;
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
		return "editStopReasonVO [rowID=" + rowID + ", stopReason=" + stopReason + ", sortNo=" + sortNo + ", VName=" + VName + ", EName=" + EName + ", showDataTable=" + showDataTable + ", msg=" + msg + ", ActionURI=" + ActionURI + "]";
	}
	
	
	
}
