package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;
/**
 * 年月考勤表(假期)
 * @author Antonis.chen
 *
 */
public class repAttendanceVO implements Serializable
{

	private static final long serialVersionUID = 1937575219098094082L;
	/**報表選擇**/
	private String drawReportSelect;
	/**部门 */
	private String searchDepartmen;
	/**单位 */
	private String searchUnit;
	/**ActionURI**/
	private String  ActionURI;
	/**年月日 */
	private String  queryYearMonth;
	/**資料庫訊息*/
	private String  msg;
	/**是否顯示查询結果*/
	private boolean  showDataTable;
	/**總共*/
	private String  NOTE;
	/**工號*/
	private String  EMPLOYEENO;
	
	
	
	
	public String getDrawReportSelect() {
	    return drawReportSelect;
	}
	public void setDrawReportSelect(String drawReportSelect) {
	    this.drawReportSelect = drawReportSelect;
	}
	public String getSearchDepartmen()
	{
		return searchDepartmen;
	}
	public void setSearchDepartmen(String searchDepartmen)
	{
		this.searchDepartmen = searchDepartmen;
	}
	public String getSearchUnit()
	{
		return searchUnit;
	}
	public void setSearchUnit(String searchUnit)
	{
		this.searchUnit = searchUnit;
	}
	public String getActionURI()
	{
		return ActionURI;
	}
	public void setActionURI(String actionURI)
	{
		ActionURI = actionURI;
	}
	public String getQueryYearMonth()
	{
		return queryYearMonth;
	}
	public void setQueryYearMonth(String queryYearMonth)
	{
		this.queryYearMonth = queryYearMonth;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public boolean isShowDataTable()
	{
		return showDataTable;
	}
	public void setShowDataTable(boolean showDataTable)
	{
		this.showDataTable = showDataTable;
	}
	
	
	public String getNOTE()
	{
		return NOTE;
	}
	public void setNOTE(String nOTE)
	{
		NOTE = nOTE;
	}
	public String getEMPLOYEENO()
	{
		return EMPLOYEENO;
	}
	public void setEMPLOYEENO(String eMPLOYEENO)
	{
		EMPLOYEENO = eMPLOYEENO;
	}
	@Override
	public String toString()
	{
		return "repAttendanceVO [searchDepartmen=" + searchDepartmen + ", searchUnit=" + searchUnit + ", ActionURI=" + ActionURI + ", queryYearMonth=" + queryYearMonth + ", msg=" + msg + ", showDataTable=" + showDataTable + ", NOTE=" + NOTE + ", EMPLOYEENO=" + EMPLOYEENO + "]";
	}

	
}
