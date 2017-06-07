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
	/**部门 */
	private String searchDepartmen;
	/** 加班日期 */
	private String queryDate;
	/**单位 */
	private String searchUnit;
	/**加班原因 */
	private String searchReasons;
	/**加班開始-時 */
	private String startTimeHh;
	/**加班開始分 */
	private String startTimemm;
	/**加班結束-時 */
	private String endTimeHh;
	/**加班結束-分 */
	private String endTimemm;
	/**总共小时 */
	private String addTime;
	/**姓名 */
	private String searchEmployee;
	/**工號 */
	private String searchEmployeeNo;
	/**註解*/
	private String note;
	/**早晚班別*/
	private String overTimeClass;
	/**提交日期*/
	private String  submitDate;
	/**使用者自訂加班理由*/
	private String  userReason;
	/**判斷按鈕功能*/
	private String  act;
	/**加班單狀態*/
	private String  status;
	/**資料庫訊息*/
	private String  msg;
	/**是否顯示查询結果*/
	private boolean  showDataTable;
	/**是否顯示超時資料查询結果*/
	private boolean showExOverTimeData;
	/**行ID**/
	private String  rowID;
	/**主表行ID**/
	private String  mID;
	/**start 提交日期*/
	private String startSubmitDate;
	/**end 提交日期*/
	private String EndSubmitDate;
	/**start 提交日期*/
	private String startQueryDate;
	/**end 提交日期*/
	private String endQueryDate;
	/** 退回原因*/
	private String returnMsg;
	/** 查询是否超過加班時數  **/
	private String  monthOverTime;
	/** 超過时间是否可以寫入  **/
	private boolean  OverTimeSave;
	/**ActionURI**/
	private String  ActionURI;
	/**年月日 */
	private String  queryYearMonth;
	/**員工ID**/
	private String EP_ID;
	/**部门ID */
	private String DID;
	/**单位ID**/
	private String UID;
	
	
	public String getUID()
	{
		return UID;
	}
	public void setUID(String uID)
	{
		UID = uID;
	}
	public String getDID()
	{
		return DID;
	}
	public void setDID(String dID)
	{
		DID = dID;
	}
	public String getEP_ID()
	{
		return EP_ID;
	}
	public void setEP_ID(String eP_ID)
	{
		EP_ID = eP_ID;
	}
	public boolean isShowExOverTimeData()
	{
		return showExOverTimeData;
	}
	public void setShowExOverTimeData(boolean showExOverTimeData)
	{
		this.showExOverTimeData = showExOverTimeData;
	}
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
	
	public String getMonthOverTime()
	{
		return monthOverTime;
	}
	public void setMonthOverTime(String monthOverTime)
	{
		this.monthOverTime = monthOverTime;
	}
	


	
	
	
	
	
	public boolean isOverTimeSave()
	{
		return OverTimeSave;
	}
	public void setOverTimeSave(boolean overTimeSave)
	{
		OverTimeSave = overTimeSave;
	}
	public String getActionURI()
	{
		return ActionURI;
	}
	public void setActionURI(String actionURI)
	{
		ActionURI = actionURI;
	}
	
	
	public String getmID()
	{
		return mID;
	}
	public void setmID(String mID)
	{
		this.mID = mID;
	}
	@Override
	public String toString()
	{
		return "overTimeVO [searchDepartmen=" + searchDepartmen + ", queryDate=" + queryDate + ", searchUnit=" + searchUnit + ", searchReasons=" + searchReasons + ", startTimeHh=" + startTimeHh + ", startTimemm=" + startTimemm + ", endTimeHh=" + endTimeHh + ", endTimemm=" + endTimemm + ", addTime=" + addTime + ", searchEmployee=" + searchEmployee + ", searchEmployeeNo=" + searchEmployeeNo + ", note=" + note + ", overTimeClass=" + overTimeClass + ", submitDate=" + submitDate + ", userReason=" + userReason + ", act=" + act + ", status=" + status + ", msg=" + msg + ", showDataTable=" + showDataTable + ", rowID=" + rowID + ", mID=" + mID + ", startSubmitDate=" + startSubmitDate + ", EndSubmitDate=" + EndSubmitDate + ", startQueryDate=" + startQueryDate + ", endQueryDate=" + endQueryDate
				+ ", returnMsg=" + returnMsg + ", monthOverTime=" + monthOverTime + ", OverTimeSave=" + OverTimeSave + ", ActionURI=" + ActionURI + "]";
	}
	
	
	
	
	
	

	
	

	
}
