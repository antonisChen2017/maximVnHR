package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

/**
 * 修改請假流程
 * @author Antonis.chen
 *
 */
public class editProcessVO implements Serializable
{
	private static final long serialVersionUID = 3096779579646407557L;

	/**行號*/
	private String ID;
	/**部门 */
	private String Dept;
	/**單位 */
	private String Unit;
	/**角色 */
	private String Role;
	/**狀態(三天以下,以上) */
	private String Status;
	/**審核階層1 **/
	private String SingRoleL1;
	/**審核階層1 **/
	private String SingRoleL2;
	/**審核階層1 **/
	private String SingRoleL3;
	/**審核階層1 **/
	private String SingRoleL4;
	/**審核階層1員工編號 **/
	private String SingRoleL1EP;
	/**審核階層2 員工編號**/
	private String SingRoleL2EP;
	/**審核階層3員工編號 **/
	private String SingRoleL3EP;
	/**審核階層4 員工編號**/
	private String SingRoleL4EP;
	/**資料庫訊息*/
	private String  msg;
	/**ActionURI**/
	private String  ActionURI;
	/**是否顯示查询結果*/
	private boolean  showDataTable;

	
	
	/**三天以下部門主管審核-经理*/
	private String  oneDLever3;
	/**三天以下部門主管審核-副总*/
	private String  oneDLever4;
	/**三天以下經理審核-副总*/
	private String  oneMLever4;
	
	/**三天以上一般員工審核-單位主管*/
	private String oneELever1;
	/**三天以上一般員工審核-部門主管*/
	private String oneELever2;
	/**三天以上一般員工審核-經理*/
	private String  oneELever3;
	/**三天以上一般員工審核-副總*/
	private String  oneELever4;
	/**三天以上單位主管審核-部門主管*/
	private String  oneULever2;
	/**三天以上單位主管審核-經理*/
	private String  oneULever3;
	/**三天以上單位主管審核-副總*/
	private String oneULever4;
	
	
	/**三天以上部門主管審核-经理*/
	private String  threeDLever3;
	/**三天以上部門主管審核-副总*/
	private String  threeDLever4;
	/**三天以上經理審核-副总*/
	private String  threeMLever4;
	
	/**三天以上一般員工審核-單位主管*/
	private String  threeELever1;
	/**三天以上一般員工審核-部門主管*/
	private String  threeELever2;
	/**三天以上一般員工審核-經理*/
	private String  threeELever3;
	/**三天以上一般員工審核-副總*/
	private String  threeELever4;
	/**三天以上單位主管審核-部門主管*/
	private String  threeULever2;
	/**三天以上單位主管審核-經理*/
	private String  threeULever3;
	/**三天以上單位主管審核-副總*/
	private String  threeULever4;
	
	/**三天以下部門主管ID*/
	private String  oneDID;
	/**三天以下經理ID*/
	private String  oneMID;
	/**三天以上部門主管ID*/
	private String  threeDID;
	/**三天以上經理ID*/
	private String  threeMID;
	
	/**三天以上員工ID*/
	private String  oneEID;
	/**三天以上單位員工ID*/
	private String  oneUID;
	/**三天以下員工ID*/
	private String  threeEID;
	/**三天以下經理ID*/
	private String  threeUID;
	

	public String getOneEID()
	{
		return oneEID;
	}
	public void setOneEID(String oneEID)
	{
		this.oneEID = oneEID;
	}
	public String getOneUID()
	{
		return oneUID;
	}
	public void setOneUID(String oneUID)
	{
		this.oneUID = oneUID;
	}
	public String getThreeEID()
	{
		return threeEID;
	}
	public void setThreeEID(String threeEID)
	{
		this.threeEID = threeEID;
	}
	public String getThreeUID()
	{
		return threeUID;
	}
	public void setThreeUID(String threeUID)
	{
		this.threeUID = threeUID;
	}
	public String getOneDID()
	{
		return oneDID;
	}
	public void setOneDID(String oneDID)
	{
		this.oneDID = oneDID;
	}
	public String getOneMID()
	{
		return oneMID;
	}
	public void setOneMID(String oneMID)
	{
		this.oneMID = oneMID;
	}
	public String getThreeDID()
	{
		return threeDID;
	}
	public void setThreeDID(String threeDID)
	{
		this.threeDID = threeDID;
	}
	public String getThreeMID()
	{
		return threeMID;
	}
	public void setThreeMID(String threeMID)
	{
		this.threeMID = threeMID;
	}
	
	public String getThreeDLever3()
	{
		return threeDLever3;
	}
	public void setThreeDLever3(String threeDLever3)
	{
		this.threeDLever3 = threeDLever3;
	}
	public String getThreeMLever4()
	{
		return threeMLever4;
	}
	public void setThreeMLever4(String threeMLever4)
	{
		this.threeMLever4 = threeMLever4;
	}
	
	public String getThreeELever1()
	{
		return threeELever1;
	}
	public void setThreeELever1(String threeELever1)
	{
		this.threeELever1 = threeELever1;
	}
	public String getThreeELever2()
	{
		return threeELever2;
	}
	public void setThreeELever2(String threeELever2)
	{
		this.threeELever2 = threeELever2;
	}
	public String getThreeELever3()
	{
		return threeELever3;
	}
	public void setThreeELever3(String threeELever3)
	{
		this.threeELever3 = threeELever3;
	}
	public String getThreeELever4()
	{
		return threeELever4;
	}
	public void setThreeELever4(String threeELever4)
	{
		this.threeELever4 = threeELever4;
	}
	public String getThreeULever2()
	{
		return threeULever2;
	}
	public void setThreeULever2(String threeULever2)
	{
		this.threeULever2 = threeULever2;
	}
	public String getThreeULever3()
	{
		return threeULever3;
	}
	public void setThreeULever3(String threeULever3)
	{
		this.threeULever3 = threeULever3;
	}
	public String getThreeULever4()
	{
		return threeULever4;
	}
	public void setThreeULever4(String threeULever4)
	{
		this.threeULever4 = threeULever4;
	}
	public String getUnit()
	{
		return Unit;
	}
	public void setUnit(String unit)
	{
		Unit = unit;
	}
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public String getDept()
	{
		return Dept;
	}
	public void setDept(String dept)
	{
		Dept = dept;
	}
	public String getRole()
	{
		return Role;
	}
	public void setRole(String role)
	{
		Role = role;
	}
	public String getStatus()
	{
		return Status;
	}
	public void setStatus(String status)
	{
		Status = status;
	}
	public String getSingRoleL1()
	{
		return SingRoleL1;
	}
	public void setSingRoleL1(String singRoleL1)
	{
		SingRoleL1 = singRoleL1;
	}
	public String getSingRoleL2()
	{
		return SingRoleL2;
	}
	public void setSingRoleL2(String singRoleL2)
	{
		SingRoleL2 = singRoleL2;
	}
	public String getSingRoleL3()
	{
		return SingRoleL3;
	}
	public void setSingRoleL3(String singRoleL3)
	{
		SingRoleL3 = singRoleL3;
	}
	public String getSingRoleL4()
	{
		return SingRoleL4;
	}
	public void setSingRoleL4(String singRoleL4)
	{
		SingRoleL4 = singRoleL4;
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
	public boolean isShowDataTable()
	{
		return showDataTable;
	}
	public void setShowDataTable(boolean showDataTable)
	{
		this.showDataTable = showDataTable;
	}
	public String getSingRoleL1EP()
	{
		return SingRoleL1EP;
	}
	public void setSingRoleL1EP(String singRoleL1EP)
	{
		SingRoleL1EP = singRoleL1EP;
	}
	public String getSingRoleL2EP()
	{
		return SingRoleL2EP;
	}
	public void setSingRoleL2EP(String singRoleL2EP)
	{
		SingRoleL2EP = singRoleL2EP;
	}
	public String getSingRoleL3EP()
	{
		return SingRoleL3EP;
	}
	public void setSingRoleL3EP(String singRoleL3EP)
	{
		SingRoleL3EP = singRoleL3EP;
	}
	public String getSingRoleL4EP()
	{
		return SingRoleL4EP;
	}
	public void setSingRoleL4EP(String singRoleL4EP)
	{
		SingRoleL4EP = singRoleL4EP;
	}
	public String getThreeDLever4()
	{
		return threeDLever4;
	}
	public void setThreeDLever4(String threeDLever4)
	{
		this.threeDLever4 = threeDLever4;
	}
	public String getOneDLever3() {
	    return oneDLever3;
	}
	public void setOneDLever3(String oneDLever3) {
	    this.oneDLever3 = oneDLever3;
	}
	public String getOneDLever4() {
	    return oneDLever4;
	}
	public void setOneDLever4(String oneDLever4) {
	    this.oneDLever4 = oneDLever4;
	}
	public String getOneMLever4() {
	    return oneMLever4;
	}
	public void setOneMLever4(String oneMLever4) {
	    this.oneMLever4 = oneMLever4;
	}
	public String getOneELever1() {
	    return oneELever1;
	}
	public void setOneELever1(String oneELever1) {
	    this.oneELever1 = oneELever1;
	}
	public String getOneELever2() {
	    return oneELever2;
	}
	public void setOneELever2(String oneELever2) {
	    this.oneELever2 = oneELever2;
	}
	public String getOneELever3() {
	    return oneELever3;
	}
	public void setOneELever3(String oneELever3) {
	    this.oneELever3 = oneELever3;
	}
	public String getOneELever4() {
	    return oneELever4;
	}
	public void setOneELever4(String oneELever4) {
	    this.oneELever4 = oneELever4;
	}
	public String getOneULever2() {
	    return oneULever2;
	}
	public void setOneULever2(String oneULever2) {
	    this.oneULever2 = oneULever2;
	}
	public String getOneULever3() {
	    return oneULever3;
	}
	public void setOneULever3(String oneULever3) {
	    this.oneULever3 = oneULever3;
	}
	public String getOneULever4() {
	    return oneULever4;
	}
	public void setOneULever4(String oneULever4) {
	    this.oneULever4 = oneULever4;
	}
	@Override
	public String toString() {
	    return "editProcessVO [ID=" + ID + ", Dept=" + Dept + ", Unit=" + Unit + ", Role=" + Role + ", Status="
		    + Status + ", SingRoleL1=" + SingRoleL1 + ", SingRoleL2=" + SingRoleL2 + ", SingRoleL3="
		    + SingRoleL3 + ", SingRoleL4=" + SingRoleL4 + ", SingRoleL1EP=" + SingRoleL1EP + ", SingRoleL2EP="
		    + SingRoleL2EP + ", SingRoleL3EP=" + SingRoleL3EP + ", SingRoleL4EP=" + SingRoleL4EP + ", msg="
		    + msg + ", ActionURI=" + ActionURI + ", showDataTable=" + showDataTable + ", oneDLever3="
		    + oneDLever3 + ", oneDLever4=" + oneDLever4 + ", oneMLever4=" + oneMLever4 + ", oneELever1="
		    + oneELever1 + ", oneELever2=" + oneELever2 + ", oneELever3=" + oneELever3 + ", oneELever4="
		    + oneELever4 + ", oneULever2=" + oneULever2 + ", oneULever3=" + oneULever3 + ", oneULever4="
		    + oneULever4 + ", threeDLever3=" + threeDLever3 + ", threeDLever4=" + threeDLever4
		    + ", threeMLever4=" + threeMLever4 + ", threeELever1=" + threeELever1 + ", threeELever2="
		    + threeELever2 + ", threeELever3=" + threeELever3 + ", threeELever4=" + threeELever4
		    + ", threeULever2=" + threeULever2 + ", threeULever3=" + threeULever3 + ", threeULever4="
		    + threeULever4 + ", oneDID=" + oneDID + ", oneMID=" + oneMID + ", threeDID=" + threeDID
		    + ", threeMID=" + threeMID + ", oneEID=" + oneEID + ", oneUID=" + oneUID + ", threeEID=" + threeEID
		    + ", threeUID=" + threeUID + "]";
	}

	
	
	
}
