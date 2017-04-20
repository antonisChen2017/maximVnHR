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
	private String searchHoliday;
	/** 代理人 **/
	private String  searchAgent;
	/**部門 */
	private String searchDepartmen;
	/**單位 */
	private String searchUnit;
	/**姓名 */
	private String searchEmployee;
	/**工號 */
	private String searchEmployeeNo;
	/**申請日期 */
	private String  applicationDate ;
	/**是否顯示查詢結果*/
	private boolean  showDataTable;
	/**資料庫訊息*/
	private String  msg;
	/**請假開始-時 */
	private String startLeaveTime;
	
	/**請假結束-時 */
	private String endLeaveTime;
	/**請假開始-年月日 */
	private String startLeaveDate;
	/**請假結束-年月日 */
	private String endLeaveDate;
	/**備註 */
	private String note;
	/**請假卡狀態*/
	private String  status;
	/**請假天數*/
	private String  dayCount;
	/** 退回原因*/
	private String returnMsg;
	/**行ID**/
	private String  rowID;
	/**ActionURI**/
	private String  ActionURI;
	/**請假開始-分 */
	private String startLeaveMinute;
	/**請假結束-分 */
	private String endLeaveMinute;
	
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
	
	
	public String getActionURI()
	{
		return ActionURI;
	}
	public void setActionURI(String actionURI)
	{
		ActionURI = actionURI;
	}
	
	
	
	public String getStartLeaveMinute()
	{
		return startLeaveMinute;
	}
	public void setStartLeaveMinute(String startLeaveMinute)
	{
		this.startLeaveMinute = startLeaveMinute;
	}
	public String getEndLeaveMinute()
	{
		return endLeaveMinute;
	}
	public void setEndLeaveMinute(String endLeaveMinute)
	{
		this.endLeaveMinute = endLeaveMinute;
	}
	@Override
	public String toString()
	{
		return "leaveCardVO [searchHoliday=" + searchHoliday + ", searchAgent=" + searchAgent + ", searchDepartmen=" + searchDepartmen + ", searchUnit=" + searchUnit + ", searchEmployee=" + searchEmployee + ", searchEmployeeNo=" + searchEmployeeNo + ", applicationDate=" + applicationDate + ", showDataTable=" + showDataTable + ", msg=" + msg + ", startLeaveTime=" + startLeaveTime + ", endLeaveTime=" + endLeaveTime + ", startLeaveDate=" + startLeaveDate + ", endLeaveDate=" + endLeaveDate + ", note=" + note + ", status=" + status + ", dayCount=" + dayCount + ", returnMsg=" + returnMsg + ", rowID=" + rowID + ", ActionURI=" + ActionURI + ", startLeaveMinute=" + startLeaveMinute + ", endLeaveMinute=" + endLeaveMinute + "]";
	}
	
	
	


	
	
	
	
	
	
}
