package cn.com.maxim.portal.leat;

import java.sql.Connection;
import java.util.Hashtable;
import java.util.List;

import cn.com.maxim.portal.attendan.ro.lateOutEarlyRO;
import cn.com.maxim.portal.attendan.ro.yearMonthLateRO;

public class leatLogic
{
	public static void ymLateListLogic(	List<lateOutEarlyRO> leo,List<yearMonthLateRO> lym ){
		EmpirixUser eu=new EmpirixUser();
		Emairix_Day1 eu1=new Emairix_Day1();
		eu.SetSuperior(eu1);
		 Hashtable<String, yearMonthLateRO> ListTable = new Hashtable<String, yearMonthLateRO>();  
		
		//eu.rowItemApplications(rowItems);
		
	}
}
