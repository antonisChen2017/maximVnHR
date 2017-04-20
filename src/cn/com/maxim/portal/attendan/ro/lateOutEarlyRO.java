package cn.com.maxim.portal.attendan.ro;

import java.util.Date;
/**
 * 寫入VN_YEAR_MONTH_LATE 表
 * 遲到早退名單 查詢打卡資料RO  資料庫物件
 * @author antonis.chen
 *
 */
public class lateOutEarlyRO
{
	/** 日期 Day **/
	private String  Day;
	/** SOON 員工編號  **/
	private String  SOON;
	/** 部門  **/
	private String  DemonstrateTeam;
	/** 員工名稱 **/
	private String  TanComplete;
	/** 上班刷卡時間 **/
	private String  WaitForTheCardToDo;
	/**下班刷卡時間 */
	private String HoldTheCardDown;
	/**遲到時間 早退時間 */
	private String dftime;
	/**遲到或早退狀態 */
	private String action;
	
	
	public String getSOON()
	{
		return SOON;
	}
	public void setSOON(String sOON)
	{
		SOON = sOON;
	}
	public String getDemonstrateTeam()
	{
		return DemonstrateTeam;
	}
	public void setDemonstrateTeam(String demonstrateTeam)
	{
		DemonstrateTeam = demonstrateTeam;
	}
	
	
	public String getDay()
	{
		return Day;
	}
	public void setDay(String day)
	{
		Day = day;
	}
	public String getTanComplete()
	{
		return TanComplete;
	}
	public void setTanComplete(String tanComplete)
	{
		TanComplete = tanComplete;
	}
	public String getWaitForTheCardToDo()
	{
		return WaitForTheCardToDo;
	}
	public void setWaitForTheCardToDo(String waitForTheCardToDo)
	{
		WaitForTheCardToDo = waitForTheCardToDo;
	}
	public String getHoldTheCardDown()
	{
		return HoldTheCardDown;
	}
	public void setHoldTheCardDown(String holdTheCardDown)
	{
		HoldTheCardDown = holdTheCardDown;
	}
	public String getDftime()
	{
		return dftime;
	}
	public void setDftime(String dftime)
	{
		this.dftime = dftime;
	}
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}
	
}
