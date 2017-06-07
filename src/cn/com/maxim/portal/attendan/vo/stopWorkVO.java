package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

public class stopWorkVO implements Serializable
{
	private static final long serialVersionUID = -7902613727843012480L;
	/**部门 */
	private String searchDepartmen;
	/**单位 */
	private String searchUnit;
	/**原因 */
	private String searchReasons;
	/**start 待料開始日期*/
	private String startStopWorkDate;
	/**end 待料結束日期*/
	private String endStopWorkDate;
	/**待料開始-時 */
	private String startTimeHh;
	/**待料開始-分 */
	private String startTimemm;
	/**待料結束-時 */
	private String endTimeHh;
	/**待料結束-時 */
	private String endTimemm;
	/**總天數 */
	private String addDay;
	/**姓名 */
	private String searchEmployee;
	/**工號 */
	private String searchEmployeeNo;
	/**註解*/
	private String note;
	/**是否顯示查询結果*/
	private boolean  showDataTable;
	/**資料庫訊息*/
	private String  msg;
	/**ActionURI*/
	private String  ActionURI;
	
	
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
	public String getSearchReasons()
	{
		return searchReasons;
	}
	public void setSearchReasons(String searchReasons)
	{
		this.searchReasons = searchReasons;
	}
	public String getStartStopWorkDate()
	{
		return startStopWorkDate;
	}
	public void setStartStopWorkDate(String startStopWorkDate)
	{
		this.startStopWorkDate = startStopWorkDate;
	}
	public String getEndStopWorkDate()
	{
		return endStopWorkDate;
	}
	public void setEndStopWorkDate(String endStopWorkDate)
	{
		this.endStopWorkDate = endStopWorkDate;
	}

	
	public String getStartTimeHh()
	{
		return startTimeHh;
	}
	public void setStartTimeHh(String startTimeHh)
	{
		this.startTimeHh = startTimeHh;
	}
	public String getStartTimemm()
	{
		return startTimemm;
	}
	public void setStartTimemm(String startTimemm)
	{
		this.startTimemm = startTimemm;
	}
	public String getEndTimeHh()
	{
		return endTimeHh;
	}
	public void setEndTimeHh(String endTimeHh)
	{
		this.endTimeHh = endTimeHh;
	}
	public String getEndTimemm()
	{
		return endTimemm;
	}
	public void setEndTimemm(String endTimemm)
	{
		this.endTimemm = endTimemm;
	}
	public String getAddDay()
	{
		return addDay;
	}
	public void setAddDay(String addDay)
	{
		this.addDay = addDay;
	}
	public String getSearchEmployee()
	{
		return searchEmployee;
	}
	public void setSearchEmployee(String searchEmployee)
	{
		this.searchEmployee = searchEmployee;
	}
	public String getSearchEmployeeNo()
	{
		return searchEmployeeNo;
	}
	public void setSearchEmployeeNo(String searchEmployeeNo)
	{
		this.searchEmployeeNo = searchEmployeeNo;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
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
		return "stopWorkVO [searchDepartmen=" + searchDepartmen + ", searchUnit=" + searchUnit + ", searchReasons=" + searchReasons + ", startStopWorkDate=" + startStopWorkDate + ", endStopWorkDate=" + endStopWorkDate + ", startTimeHh=" + startTimeHh + ", startTimemm=" + startTimemm + ", endTimeHh=" + endTimeHh + ", endTimemm=" + endTimemm + ", addDay=" + addDay + ", searchEmployee=" + searchEmployee + ", searchEmployeeNo=" + searchEmployeeNo + ", note=" + note + ", showDataTable=" + showDataTable + ", msg=" + msg + ", ActionURI=" + ActionURI + "]";
	}
	
	
	
	
	
	
	
	
}
