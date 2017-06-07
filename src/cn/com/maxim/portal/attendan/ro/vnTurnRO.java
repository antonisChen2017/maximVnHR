package cn.com.maxim.portal.attendan.ro;
/**
 * 查询班表VN_TURN 資料表RO
 * @author antonis.chen
 *
 */
public class vnTurnRO
{
	/**  ID **/
	private String  ID;
	/** Code 代號  **/
	private String  Code;
	/** 越文 名稱  **/
	private String  VName;
	/**中文名稱  **/
	private String  CBName;
	/**開始时间**/
	private String  FHM;
	/**結束时间**/
	private String  EHM;
	/**總时间(分)**/
	private String  AllMin;
	
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public String getCode()
	{
		return Code;
	}
	public void setCode(String code)
	{
		Code = code;
	}
	public String getVName()
	{
		return VName;
	}
	public void setVName(String vName)
	{
		VName = vName;
	}
	public String getCBName()
	{
		return CBName;
	}
	public void setCBName(String cBName)
	{
		CBName = cBName;
	}
	public String getFHM()
	{
		return FHM;
	}
	public void setFHM(String fHM)
	{
		FHM = fHM;
	}
	public String getEHM()
	{
		return EHM;
	}
	public void setEHM(String eHM)
	{
		EHM = eHM;
	}
	public String getAllMin()
	{
		return AllMin;
	}
	public void setAllMin(String allMin)
	{
		AllMin = allMin;
	}
	
}
