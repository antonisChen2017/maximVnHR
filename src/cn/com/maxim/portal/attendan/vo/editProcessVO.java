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
	
	
}
