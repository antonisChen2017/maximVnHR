package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

/**加班申請單 頁面參數 物件 */

/**
 * @author Antonis 2017/03/13
 * 
 */
public class overTimeVO implements Serializable
{

	private static final long serialVersionUID = -8717143017939146669L;
	/**部門 */
	public String searchDepartmen;
	/** 加班日期 */
	public String queryDate;
	/**單位 */
	public String searchUnit;
	/**加班原因 */
	public String searchReasons;
	/**加班開始-時 */
	public String startTimeHh;
	/**加班開始分 */
	public String startTimemm;
	/**加班結束-時 */
	public String endTimeHh;
	/**加班結束-分 */
	public String endTimemm;
	/**總共小時 */
	public String addTime;
	/**姓名 */
	public String searchEmployee;
	/**工號 */
	public String searchEmployeeNo;
	/**註解*/
	public String note;
	/**早晚班別*/
	public String overTimeClass;
	/**提交日期*/
	public String  submitDate;
	/**使用者自訂加班理由*/
	public String  userReason;
	/**判斷按鈕功能*/
	public String  act;
	/**加班單狀態*/
	public String  status;
	/**資料庫訊息*/
	public String  msg;
	/**是否顯示查詢結果*/
	public boolean  showDataTable;
	/**行ID**/
	public String  rowID;
	/**start 提交日期*/
	public String startSubmitDate;
	/**end 提交日期*/
	public String EndSubmitDate;
	/**start 提交日期*/
	public String startQueryDate;
	/**end 提交日期*/
	public String endQueryDate;
	/** 退回原因*/
	public String returnMsg;

	public String getSearchDepartmen()
	{
		return searchDepartmen;
	}
	public void setSearchDepartmen(String searchDepartmen)
	{
		this.searchDepartmen = searchDepartmen;
	}
	public String getQueryDate()
	{
		return queryDate;
	}
	public void setQueryDate(String queryDate)
	{
		this.queryDate = queryDate;
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
	public String getAddTime()
	{
		return addTime;
	}
	public void setAddTime(String addTime)
	{
		this.addTime = addTime;
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
	public String getOverTimeClass()
	{
		return overTimeClass;
	}
	public void setOverTimeClass(String overTimeClass)
	{
		this.overTimeClass = overTimeClass;
	}
	
	
	
	
	public String getSubmitDate()
	{
		return submitDate;
	}
	public void setSubmitDate(String submitDate)
	{
		this.submitDate = submitDate;
	}
	
	public String getUserReason()
	{
		return userReason;
	}
	public void setUserReason(String userReason)
	{
		this.userReason = userReason;
	}
	
	
	public String getAct()
	{
		return act;
	}
	public void setAct(String act)
	{
		this.act = act;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public boolean isShowDataTable()
	{
		return showDataTable;
	}
	public void setShowDataTable(boolean showDataTable)
	{
		this.showDataTable = showDataTable;
	}
	
	public String getRowID()
	{
		return rowID;
	}
	public void setRowID(String rowID)
	{
		this.rowID = rowID;
	}
	public String getStartSubmitDate()
	{
		return startSubmitDate;
	}
	public void setStartSubmitDate(String startSubmitDate)
	{
		this.startSubmitDate = startSubmitDate;
	}
	public String getEndSubmitDate()
	{
		return EndSubmitDate;
	}
	public void setEndSubmitDate(String eNDSubmitDate)
	{
		EndSubmitDate = eNDSubmitDate;
	}

	public String getStartQueryDate()
	{
		return startQueryDate;
	}
	public void setStartQueryDate(String startQueryDate)
	{
		this.startQueryDate = startQueryDate;
	}
	public String getEndQueryDate()
	{
		return endQueryDate;
	}
	public void setEndQueryDate(String endQueryDate)
	{
		this.endQueryDate = endQueryDate;
	}
	
	public String getReturnMsg()
	{
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg)
	{
		this.returnMsg = returnMsg;
	}
	@Override
	public String toString()
	{
		return "overTimeVO [searchDepartmen=" + searchDepartmen + ", queryDate=" + queryDate + ", searchUnit="
				+ searchUnit + ", searchReasons=" + searchReasons + ", startTimeHh=" + startTimeHh + ", startTimemm="
				+ startTimemm + ", endTimeHh=" + endTimeHh + ", endTimemm=" + endTimemm + ", addTime=" + addTime
				+ ", searchEmployee=" + searchEmployee + ", searchEmployeeNo=" + searchEmployeeNo + ", note=" + note
				+ ", overTimeClass=" + overTimeClass + ", submitDate=" + submitDate + ", userReason=" + userReason
				+ ", act=" + act + ", status=" + status + ", msg=" + msg + ", showDataTable=" + showDataTable
				+ ", rowID=" + rowID + ", startSubmitDate=" + startSubmitDate + ", EndSubmitDate=" + EndSubmitDate
				+ ", startQueryDate=" + startQueryDate + ", endQueryDate=" + endQueryDate + ", returnMsg=" + returnMsg
				+ "]";
	}
	
	

	
	

	
}
