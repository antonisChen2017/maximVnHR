package cn.com.maxim.portal.attendan.ro;
/**
 * 日考勤報表
 * @author Antonis.chen
 *
 */
public class repDailyRO
{
	/** 序號  **/
	private String ID;
	/** 工號  **/
	private String EMPLOYEENO;
	/** 名子  **/
	private String EMPLOYEE;
	/** 部門  **/
	private String DEPARTMENT;
	/** 單位 **/
	private String UNIT;
	/** 正班出勤 **/
	private String WorkTime;
	/** 晚班時數 **/
	private String NigthWorkTime;
	/** 加班時數 **/
	private String OverWorkTime;
	/** 年假 **/
	private String HolidayH;
	/** 病假**/
	private String HolidayB;
	/** 事假**/
	private String HolidayA;
	/** 公假**/
	private String HolidayC;
	/** 產假**/
	private String HolidayE;
	/** 喪假**/
	private String HolidayF;
	/** 曠工**/
	private String HolidayK;
	/** 遲到**/
	private String LTime;
	/** 待工**/
	private String StopWork;
	/** 補貼餐費**/
	private String SubsidyMeals;
	/** 加班備註**/
	private String Note;

	
	public String getEMPLOYEENO()
	{
		return EMPLOYEENO;
	}
	public void setEMPLOYEENO(String eMPLOYEENO)
	{
		EMPLOYEENO = eMPLOYEENO;
	}
	public String getEMPLOYEE()
	{
		return EMPLOYEE;
	}
	public void setEMPLOYEE(String eMPLOYEE)
	{
		EMPLOYEE = eMPLOYEE;
	}
	public String getDEPARTMENT()
	{
		return DEPARTMENT;
	}
	public void setDEPARTMENT(String dEPARTMENT)
	{
		DEPARTMENT = dEPARTMENT;
	}
	public String getUNIT()
	{
		return UNIT;
	}
	public void setUNIT(String uNIT)
	{
		UNIT = uNIT;
	}
	public String getWorkTime()
	{
		return WorkTime;
	}
	public void setWorkTime(String workTime)
	{
		WorkTime = workTime;
	}
	public String getNigthWorkTime()
	{
		return NigthWorkTime;
	}
	public void setNigthWorkTime(String nigthWorkTime)
	{
		NigthWorkTime = nigthWorkTime;
	}
	public String getOverWorkTime()
	{
		return OverWorkTime;
	}
	public void setOverWorkTime(String overWorkTime)
	{
		OverWorkTime = overWorkTime;
	}
	public String getHolidayH()
	{
		return HolidayH;
	}
	public void setHolidayH(String holidayH)
	{
		HolidayH = holidayH;
	}
	public String getHolidayC()
	{
		return HolidayC;
	}
	public void setHolidayC(String holidayC)
	{
		HolidayC = holidayC;
	}
	public String getHolidayE()
	{
		return HolidayE;
	}
	public void setHolidayE(String holidayE)
	{
		HolidayE = holidayE;
	}
	public String getHolidayF()
	{
		return HolidayF;
	}
	public void setHolidayF(String holidayF)
	{
		HolidayF = holidayF;
	}
	public String getHolidayB()
	{
		return HolidayB;
	}
	public void setHolidayB(String holidayB)
	{
		HolidayB = holidayB;
	}
	public String getHolidayA()
	{
		return HolidayA;
	}
	public void setHolidayA(String holidayA)
	{
		HolidayA = holidayA;
	}
	public String getHolidayK()
	{
		return HolidayK;
	}
	public void setHolidayK(String holidayK)
	{
		HolidayK = holidayK;
	}
	public String getLTime()
	{
		return LTime;
	}
	public void setLTime(String lTime)
	{
		LTime = lTime;
	}
	public String getStopWork()
	{
		return StopWork;
	}
	public void setStopWork(String stopWork)
	{
		StopWork = stopWork;
	}
	public String getSubsidyMeals()
	{
		return SubsidyMeals;
	}
	public void setSubsidyMeals(String subsidyMeals)
	{
		SubsidyMeals = subsidyMeals;
	}

	public String getNote()
	{
		return Note;
	}
	public void setNote(String note)
	{
		Note = note;
	}
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}

	
}
