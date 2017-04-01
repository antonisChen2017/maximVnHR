package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;
/**
 * 請假卡 頁面參數 物件
 * @author Antonis.chen
 *
 */
public class leaveCardVO implements Serializable
{

	private static final long serialVersionUID = -918064309722333936L;

	
	/** 請假事由 **/
	public String searchHoliday;
	/** 代理人 **/
	public String  searchAgent;
	/**部門 */
	public String searchDepartmen;
	/**單位 */
	public String searchUnit;
	/**姓名 */
	public String searchEmployee;
	/**工號 */
	public String searchEmployeeNo;
	/**申請日期 */
	public String  applicationDate ;
	/**是否顯示查詢結果*/
	public boolean  showDataTable;
	/**資料庫訊息*/
	public String  msg;
	/**請假開始-時 */
	public String startLeaveTime;
	/**請假結束-時 */
	public String endLeaveTime;
	/**請假開始-年月日 */
	public String startLeaveDate;
	/**請假結束-年月日 */
	public String endLeaveDate;
	/**備註 */
	public String note;
	/**請假卡狀態*/
	public String  status;
	/**請假天數*/
	public String  dayCount;
	/** 退回原因*/
	public String returnMsg;
	/**行ID**/
	public String  rowID;
	
	
	public String getSearchHoliday()
	{
		return searchHoliday;
	}
	public void setSearchHoliday(String searchHoliday)
	{
		this.searchHoliday = searchHoliday;
	}
	public String getSearchAgent()
	{
		return searchAgent;
	}
	public void setSearchAgent(String searchAgent)
	{
		this.searchAgent = searchAgent;
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
	public String getApplicationDate()
	{
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate)
	{
		this.applicationDate = applicationDate;
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
	
	
	
	
	
	public String getStartLeaveDate()
	{
		return startLeaveDate;
	}
	public void setStartLeaveDate(String startLeaveDate)
	{
		this.startLeaveDate = startLeaveDate;
	}
	public String getEndLeaveDate()
	{
		return endLeaveDate;
	}
	public void setEndLeaveDate(String endLeaveDate)
	{
		this.endLeaveDate = endLeaveDate;
	}
	public String getStartLeaveTime()
	{
		return startLeaveTime;
	}
	public void setStartLeaveTime(String startLeaveTime)
	{
		this.startLeaveTime = startLeaveTime;
	}
	public String getEndLeaveTime()
	{
		return endLeaveTime;
	}
	public void setEndLeaveTime(String endLeaveTime)
	{
		this.endLeaveTime = endLeaveTime;
	}
	
	
	
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	
	
	
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getDayCount()
	{
		return dayCount;
	}
	public void setDayCount(String dayCount)
	{
		this.dayCount = dayCount;
	}
	
	
	
	public String getReturnMsg()
	{
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg)
	{
		this.returnMsg = returnMsg;
	}
	
	
	
	public String getRowID()
	{
		return rowID;
	}
	public void setRowID(String rowID)
	{
		this.rowID = rowID;
	}
	@Override
	public String toString()
	{
		return "leaveCardVO [searchHoliday=" + searchHoliday + ", searchAgent=" + searchAgent + ", searchDepartmen="
				+ searchDepartmen + ", searchUnit=" + searchUnit + ", searchEmployee=" + searchEmployee
				+ ", searchEmployeeNo=" + searchEmployeeNo + ", applicationDate=" + applicationDate + ", showDataTable="
				+ showDataTable + ", msg=" + msg + ", startLeaveTime=" + startLeaveTime + ", endLeaveTime="
				+ endLeaveTime + ", startLeaveDate=" + startLeaveDate + ", endLeaveDate=" + endLeaveDate + ", note="
				+ note + ", status=" + status + ", dayCount=" + dayCount + ", returnMsg=" + returnMsg + "]";
	}
	


	
	
	
	
	
	
}
