package cn.com.maxim.portal.attendan.ro;

public class editProcessRO
{
	/**行號*/
	private String ID;
	/** */
	private String Dept;
	/** */
	private String Unit;
	/** */
	private String Group;
	/** */
	private String Role;
	/**B(,) */
	private String Status;
	/**A1 **/
	private String SingRoleL0;
	/**A1 **/
	private String SingRoleL1;
	/**A1 **/
	private String SingRoleL2;
	/**A1 **/
	private String SingRoleL3;
	/**A1 **/
	private String SingRoleL4;
	/**A1T **/
	private String SingRoleL0EP;
	/**A1T **/
	private String SingRoleL1EP;
	/**A2 T**/
	private String SingRoleL2EP;
	/**A3T **/
	private String SingRoleL3EP;
	/**A4 T**/
	private String SingRoleL4EP;
	/**省核者職稱**/
	private String oneTitle;
	/**代理审核人职称**/
	private String twoTitle;
	
	
	
	
	
	public String getGroup() {
	    return Group;
	}
	public void setGroup(String group) {
	    Group = group;
	}
	public String getSingRoleL0() {
	    return SingRoleL0;
	}
	public void setSingRoleL0(String singRoleL0) {
	    SingRoleL0 = singRoleL0;
	}
	public String getSingRoleL0EP() {
	    return SingRoleL0EP;
	}
	public void setSingRoleL0EP(String singRoleL0EP) {
	    SingRoleL0EP = singRoleL0EP;
	}
	public String getOneTitle()
	{
		return oneTitle;
	}
	public void setOneTitle(String oneTitle)
	{
		this.oneTitle = oneTitle;
	}
	public String getTwoTitle()
	{
		return twoTitle;
	}
	public void setTwoTitle(String twoTitle)
	{
		this.twoTitle = twoTitle;
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
	public String getUnit()
	{
		return Unit;
	}
	public void setUnit(String unit)
	{
		Unit = unit;
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
	@Override
	public String toString() {
	    return "editProcessRO [ID=" + ID + ", Dept=" + Dept + ", Unit=" + Unit + ", Role=" + Role + ", Status="
		    + Status + ", SingRoleL1=" + SingRoleL1 + ", SingRoleL2=" + SingRoleL2 + ", SingRoleL3="
		    + SingRoleL3 + ", SingRoleL4=" + SingRoleL4 + ", SingRoleL1EP=" + SingRoleL1EP + ", SingRoleL2EP="
		    + SingRoleL2EP + ", SingRoleL3EP=" + SingRoleL3EP + ", SingRoleL4EP=" + SingRoleL4EP + ", oneTitle="
		    + oneTitle + ", twoTitle=" + twoTitle + "]";
	}
	
	
	
}
