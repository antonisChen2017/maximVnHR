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
	/** 部门  **/
	private String DEPARTMENT;
	/** 单位 **/
	private String UNIT;
	/** 正班出勤 **/
	private String ATTENDANCE;
	/** 加班時數 **/
	private String OVERTIME;
	/** 年假 **/
	private String HOLIDAYH;
	/** 病假**/
	private String HOLIDAYC;
	/** 產假**/
	private String HOLIDAYE;
	/** 公假**/
	private String HOLIDAYD;
	/** 喪假**/
	private String HOLIDAYF;
	/** 喪假**/
	private String HOLIDAYB;
	/** 事假**/
	private String HOLIDAYA;
	/** 工傷**/
	private String HOLIDAYI;
	/** 調休**/
	private String HOLIDAYG;
	/** 其他**/
	private String HOLIDAYO;
	/** 曠工**/
       private String NOTWORK;
	/** 遲到**/
	private String BELATE;
	/** 早退**/
	private String EARLY;
	/** 待工**/
	private String STOPWORK;
	/** 補貼餐費**/
	private String MEALS;
	/** 加班備註**/
	private String NOTE;

	
	
	
	public String getEARLY() {
	    return EARLY;
	}
	public void setEARLY(String eARLY) {
	    EARLY = eARLY;
	}
	public String getHOLIDAYO() {
	    return HOLIDAYO;
	}
	public void setHOLIDAYO(String hOLIDAYO) {
	    HOLIDAYO = hOLIDAYO;
	}
	public String getHOLIDAYG() {
	    return HOLIDAYG;
	}
	public void setHOLIDAYG(String hOLIDAYG) {
	    HOLIDAYG = hOLIDAYG;
	}
	public String getHOLIDAYI() {
	    return HOLIDAYI;
	}
	public void setHOLIDAYI(String hOLIDAYI) {
	    HOLIDAYI = hOLIDAYI;
	}
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
	
	public String getATTENDANCE()
	{
		return ATTENDANCE;
	}
	public void setATTENDANCE(String aTTENDANCE)
	{
		ATTENDANCE = aTTENDANCE;
	}
	public String getOVERTIME()
	{
		return OVERTIME;
	}
	public void setOVERTIME(String oVERTIME)
	{
		OVERTIME = oVERTIME;
	}
	public String getHOLIDAYH()
	{
		return HOLIDAYH;
	}
	public void setHOLIDAYH(String hOLIDAYH)
	{
		HOLIDAYH = hOLIDAYH;
	}
	public String getHOLIDAYC()
	{
		return HOLIDAYC;
	}
	public void setHOLIDAYC(String hOLIDAYC)
	{
		HOLIDAYC = hOLIDAYC;
	}
	public String getHOLIDAYE()
	{
		return HOLIDAYE;
	}
	public void setHOLIDAYE(String hOLIDAYE)
	{
		HOLIDAYE = hOLIDAYE;
	}
	public String getHOLIDAYD()
	{
		return HOLIDAYD;
	}
	public void setHOLIDAYD(String hOLIDAYD)
	{
		HOLIDAYD = hOLIDAYD;
	}
	public String getHOLIDAYF()
	{
		return HOLIDAYF;
	}
	public void setHOLIDAYF(String hOLIDAYF)
	{
		HOLIDAYF = hOLIDAYF;
	}
	public String getHOLIDAYB()
	{
		return HOLIDAYB;
	}
	public void setHOLIDAYB(String hOLIDAYB)
	{
		HOLIDAYB = hOLIDAYB;
	}
	public String getHOLIDAYA()
	{
		return HOLIDAYA;
	}
	public void setHOLIDAYA(String hOLIDAYA)
	{
		HOLIDAYA = hOLIDAYA;
	}
	public String getNOTWORK()
	{
		return NOTWORK;
	}
	public void setNOTWORK(String nOTWORK)
	{
		NOTWORK = nOTWORK;
	}
	public String getBELATE()
	{
		return BELATE;
	}
	public void setBELATE(String bELATE)
	{
		BELATE = bELATE;
	}
	public String getSTOPWORK()
	{
		return STOPWORK;
	}
	public void setSTOPWORK(String sTOPWORK)
	{
		STOPWORK = sTOPWORK;
	}
	public String getMEALS()
	{
		return MEALS;
	}
	public void setMEALS(String mEALS)
	{
		MEALS = mEALS;
	}
	public String getNOTE()
	{
		return NOTE;
	}
	public void setNOTE(String nOTE)
	{
		NOTE = nOTE;
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
