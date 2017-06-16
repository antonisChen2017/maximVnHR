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
	/**三天以下部門主管審核者職級*/
	private String  oneDAuditorTitle;
	/**三天以下部門主管審核者*/
	private String  oneDAuditor;
	/**三天以下部門主管代理審核職級*/
	private String  oneDAgentTitle;
	/**三天以下部門主管代理審核者*/
	private String  oneDAgent;
	/**三天以下經理審核者職級*/
	private String  oneMAuditorTitle;
	/**三天以下經理審核者*/
	private String  oneMAuditor;
	/**三天以下經理代理審核職級*/
	private String  oneMAgentTitle;
	/**三天以下經理代理審核者*/
	private String  oneMAgent;
	/**三天以上部門主管審核-经理*/
	private String  threeDLever3;
	/**三天以上部門主管審核-副总*/
	private String  threeDLever4;
	/**三天以上經理審核-副总*/
	private String  threeMLever4;
	/**三天以下一般員工審核者職級*/
	private String  oneEAuditorTitle;
	/**三天以下一般員工審核者*/
	private String  oneEAuditor;
	/**三天以下一般員工代理審核職級*/
	private String  oneEAgentTitle;
	/**三天以下一般員工代理審核者*/
	private String  oneEAgent;
	/**三天以下單位主管審核者職級*/
	private String  oneUAuditorTitle;
	/**三天以下單位主管審核者*/
	private String  oneUAuditor;
	/**三天以下經理代理審核職級*/
	private String  oneUAgentTitle;
	/**三天以下經理代理審核者*/
	private String  oneUAgent;
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
	
	public String getOneDAuditorTitle()
	{
		return oneDAuditorTitle;
	}
	public void setOneDAuditorTitle(String oneDAuditorTitle)
	{
		this.oneDAuditorTitle = oneDAuditorTitle;
	}
	public String getOneDAuditor()
	{
		return oneDAuditor;
	}
	public void setOneDAuditor(String oneDAuditor)
	{
		this.oneDAuditor = oneDAuditor;
	}
	public String getOneDAgentTitle()
	{
		return oneDAgentTitle;
	}
	public void setOneDAgentTitle(String oneDAgentTitle)
	{
		this.oneDAgentTitle = oneDAgentTitle;
	}
	public String getOneDAgent()
	{
		return oneDAgent;
	}
	public void setOneDAgent(String oneDAgent)
	{
		this.oneDAgent = oneDAgent;
	}
	public String getOneMAuditorTitle()
	{
		return oneMAuditorTitle;
	}
	public void setOneMAuditorTitle(String oneMAuditorTitle)
	{
		this.oneMAuditorTitle = oneMAuditorTitle;
	}
	public String getOneMAuditor()
	{
		return oneMAuditor;
	}
	public void setOneMAuditor(String oneMAuditor)
	{
		this.oneMAuditor = oneMAuditor;
	}
	public String getOneMAgentTitle()
	{
		return oneMAgentTitle;
	}
	public void setOneMAgentTitle(String oneMAgentTitle)
	{
		this.oneMAgentTitle = oneMAgentTitle;
	}
	public String getOneMAgent()
	{
		return oneMAgent;
	}
	public void setOneMAgent(String oneMAgent)
	{
		this.oneMAgent = oneMAgent;
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
	public String getOneEAuditorTitle()
	{
		return oneEAuditorTitle;
	}
	public void setOneEAuditorTitle(String oneEAuditorTitle)
	{
		this.oneEAuditorTitle = oneEAuditorTitle;
	}
	public String getOneEAuditor()
	{
		return oneEAuditor;
	}
	public void setOneEAuditor(String oneEAuditor)
	{
		this.oneEAuditor = oneEAuditor;
	}
	public String getOneEAgentTitle()
	{
		return oneEAgentTitle;
	}
	public void setOneEAgentTitle(String oneEAgentTitle)
	{
		this.oneEAgentTitle = oneEAgentTitle;
	}
	public String getOneEAgent()
	{
		return oneEAgent;
	}
	public void setOneEAgent(String oneEAgent)
	{
		this.oneEAgent = oneEAgent;
	}
	public String getOneUAuditorTitle()
	{
		return oneUAuditorTitle;
	}
	public void setOneUAuditorTitle(String oneUAuditorTitle)
	{
		this.oneUAuditorTitle = oneUAuditorTitle;
	}
	public String getOneUAuditor()
	{
		return oneUAuditor;
	}
	public void setOneUAuditor(String oneUAuditor)
	{
		this.oneUAuditor = oneUAuditor;
	}
	public String getOneUAgentTitle()
	{
		return oneUAgentTitle;
	}
	public void setOneUAgentTitle(String oneUAgentTitle)
	{
		this.oneUAgentTitle = oneUAgentTitle;
	}
	public String getOneUAgent()
	{
		return oneUAgent;
	}
	public void setOneUAgent(String oneUAgent)
	{
		this.oneUAgent = oneUAgent;
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
	
	
}
