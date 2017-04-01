package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

public class stopWorkVO implements Serializable
{
	private static final long serialVersionUID = -7902613727843012480L;
	/**部門 */
	public String searchDepartmen;
	/**單位 */
	public String searchUnit;
	/**原因 */
	public String searchReasons;
	/**start 待料開始日期*/
	public String startStopWorkDate;
	/**end 待料結束日期*/
	public String endStopWorkDate;
	/**待料開始-時 */
	public String startTimeHhmm;

	/**待料結束-時 */
	public String endTimeHhmm;

	/**總天數 */
	public String addDay;
	/**姓名 */
	public String searchEmployee;
	/**工號 */
	public String searchEmployeeNo;
	/**註解*/
	public String note;
	/**是否顯示查詢結果*/
	public boolean  showDataTable;
	/**資料庫訊息*/
	public String  msg;
	
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

	public String getStartTimeHhmm()
	{
		return startTimeHhmm;
	}
	public void setStartTimeHhmm(String startTimeHhmm)
	{
		this.startTimeHhmm = startTimeHhmm;
	}
	public String getEndTimeHhmm()
	{
		return endTimeHhmm;
	}
	public void setEndTimeHhmm(String endTimeHhmm)
	{
		this.endTimeHhmm = endTimeHhmm;
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
	@Override
	public String toString()
	{
		return "stopWorkVO [searchDepartmen=" + searchDepartmen + ", searchUnit=" + searchUnit + ", searchReasons="
				+ searchReasons + ", startStopWorkDate=" + startStopWorkDate + ", endStopWorkDate=" + endStopWorkDate
				+ ", startTimeHhmm=" + startTimeHhmm + ", endTimeHhmm=" + endTimeHhmm + ", addDay=" + addDay
				+ ", searchEmployee=" + searchEmployee + ", searchEmployeeNo=" + searchEmployeeNo + ", note=" + note
				+ ", showDataTable=" + showDataTable + ", msg=" + msg + "]";
	}
	
	
	
	
	
	
}
