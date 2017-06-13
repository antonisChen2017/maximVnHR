package cn.com.maxim.portal.attendan.ro;
/**
 * 越籍年資分布  資料表RO
 * @author Antonis.chen
 *
 */
public class empnumRO
{
	/**越籍年資分布**/
	private String YMD;
	/**年度**/
	private String YEAR;
	/**人數**/
	private String EMPNUM;
	/**百分比**/
	private String PERCENTAGE;
	
	public String getYMD()
	{
		return YMD;
	}
	public void setYMD(String yMD)
	{
		YMD = yMD;
	}
	public String getYEAR()
	{
		return YEAR;
	}
	public void setYEAR(String yEAR)
	{
		YEAR = yEAR;
	}
	public String getEMPNUM()
	{
		return EMPNUM;
	}
	public void setEMPNUM(String eMPNUM)
	{
		EMPNUM = eMPNUM;
	}
	public String getPERCENTAGE()
	{
		return PERCENTAGE;
	}
	public void setPERCENTAGE(String pERCENTAGE)
	{
		PERCENTAGE = pERCENTAGE;
	}
	
	
}
