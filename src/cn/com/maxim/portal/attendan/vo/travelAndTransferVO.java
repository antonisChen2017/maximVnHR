package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

public class travelAndTransferVO implements Serializable {

    private static final long serialVersionUID = -8228494499640375401L;

    /** 出差-部门 **/
    private String DeptL;
    /** 調動-部门 **/
    private String DeptR;
    /** 出差-單位 **/
    private String UnitL;
    /** 調動-單位 **/
    private String UnitR;
    /** 出差-工號 **/
    private String EmployeeNoL;
    /** 調動-工號 **/
    private String EmployeeNoR;
    /** 出差-姓名 **/
    private String EmployeeL;
    /** 調動-姓名 **/
    private String EmployeeR;
    /** 出差-開始日期 **/
    private String StartDayL;
    /** 出差-結束日期 **/
    private String EndDayL;
    /** 調動-日期 **/
    private String StartDayR;
    /** 出差-備註 **/
    private String NoteR;
    /** 調動-備註 **/
    private String NoteL;
    /** 是否顯示查询結果 */
    private boolean showDataTable;
    /** 資料庫訊息 */
    private String msg;
    /** ActionURI */
    private String ActionURI;
    /** 設定頁標籤定位 */
    private String Tab;
    /** 設定行標籤 */
    private String rowID;
    /** 設定狀態L出差R調動 */
    private String Status;
    /**  */
    private String  Romance;
    /** */
    private String  returnMsg;
    
    
    
    public String getRomance() {
        return Romance;
    }

    public void setRomance(String romance) {
        Romance = romance;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getStatus() {
	return Status;
    }

    public void setStatus(String status) {
	Status = status;
    }

    public boolean isShowDataTable() {
	return showDataTable;
    }

    public void setShowDataTable(boolean showDataTable) {
	this.showDataTable = showDataTable;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public String getActionURI() {
	return ActionURI;
    }

    public void setActionURI(String actionURI) {
	ActionURI = actionURI;
    }

    public String getTab() {
	return Tab;
    }

    public void setTab(String tab) {
	Tab = tab;
    }

    public String getRowID() {
	return rowID;
    }

    public void setRowID(String rowID) {
	this.rowID = rowID;
    }

    public String getStartDayL() {
	return StartDayL;
    }

    public void setStartDayL(String startDayL) {
	StartDayL = startDayL;
    }

    public String getEndDayL() {
	return EndDayL;
    }

    public void setEndDayL(String endDayL) {
	EndDayL = endDayL;
    }

    public String getStartDayR() {
	return StartDayR;
    }

    public void setStartDayR(String startDayR) {
	StartDayR = startDayR;
    }

    public String getNoteR() {
	return NoteR;
    }

    public void setNoteR(String noteR) {
	NoteR = noteR;
    }

    public String getNoteL() {
	return NoteL;
    }

    public void setNoteL(String noteL) {
	NoteL = noteL;
    }

    public String getDeptR() {
	return DeptR;
    }

    public void setDeptR(String deptR) {
	DeptR = deptR;
    }

    public String getUnitL() {
	return UnitL;
    }

    public void setUnitL(String unitL) {
	UnitL = unitL;
    }

    public String getUnitR() {
	return UnitR;
    }

    public void setUnitR(String unitR) {
	UnitR = unitR;
    }

    public String getEmployeeNoL() {
	return EmployeeNoL;
    }

    public void setEmployeeNoL(String employeeNoL) {
	EmployeeNoL = employeeNoL;
    }

    public String getEmployeeNoR() {
	return EmployeeNoR;
    }

    public void setEmployeeNoR(String employeeNoR) {
	EmployeeNoR = employeeNoR;
    }

    public String getEmployeeL() {
	return EmployeeL;
    }

    public void setEmployeeL(String employeeL) {
	EmployeeL = employeeL;
    }

    public String getEmployeeR() {
	return EmployeeR;
    }

    public void setEmployeeR(String employeeR) {
	EmployeeR = employeeR;
    }

    public String getDeptL() {
	return DeptL;
    }

    public void setDeptL(String deptL) {
	DeptL = deptL;
    }

    @Override
    public String toString() {
	return "travelAndTransferVO [DeptL=" + DeptL + ", DeptR=" + DeptR + ", UnitL=" + UnitL + ", UnitR=" + UnitR
		+ ", EmployeeNoL=" + EmployeeNoL + ", EmployeeNoR=" + EmployeeNoR + ", EmployeeL=" + EmployeeL
		+ ", EmployeeR=" + EmployeeR + ", StartDayL=" + StartDayL + ", EndDayL=" + EndDayL + ", StartDayR="
		+ StartDayR + ", NoteR=" + NoteR + ", NoteL=" + NoteL + ", showDataTable=" + showDataTable + ", msg="
		+ msg + ", ActionURI=" + ActionURI + ", Tab=" + Tab + ", rowID=" + rowID + ", Status=" + Status + "]";
    }

}
