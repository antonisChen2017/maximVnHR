package cn.com.maxim.portal.attendan.ro;
/**
 * 查詢班表VN_TURN 資料庫物件
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
	/**開始時間**/
	private String  FHM;
	/**結束時間**/
	private String  EHM;
	/**總時間(分)**/
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
