package cn.com.maxim.portal.leat;

import java.util.Hashtable;
import java.util.List;

import cn.com.maxim.portal.attendan.ro.lateOutEarlyRO;
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;

public class rowItem
{
	private List<lateOutEarlyRO> leo ;
	private  Hashtable<String, yearMonthLateRO>  lym;
	
	
	public List<lateOutEarlyRO> getLeo()
	{
		return leo;
	}
	public void setLeo(List<lateOutEarlyRO> leo)
	{
		this.leo = leo;
	}
	public Hashtable<String, yearMonthLateRO> getLym()
	{
		return lym;
	}
	public void setLym(Hashtable<String, yearMonthLateRO> lym)
	{
		this.lym = lym;
	}
	
	
	
	
}
