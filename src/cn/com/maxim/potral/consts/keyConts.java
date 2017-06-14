package cn.com.maxim.potral.consts;

public class keyConts
{
	  /**
		  * key
	  */
	  public static final String returnMsg= "退回成功!";
	  public static final String okMsg= "审核通过成功!";
	  public static final String pageSave = "save";
	  public static final String pageList = "list";
	  public static final String pageEmpUnitList= "empUnitList";
	  /**人事系統介面**/
	  public static final String personnelList= "personnel";
	  public static final String pageDtmList= "dtmsList";
	  public static final String pageUsList= "usList";
	  public static final String pageMsList= "msList";
	  public static final String pageLList= "LList";
	  public static final String pageInspect = "inspect";
	  public static final String dbTableCR= "VN_LEAVECARD";
	  public static final String dbTableCRStatuS_U="U";
	  public static final String dbTableCRStatuS_M="M";
	  public static final String dbTableCRStatuS_D="D";
	  public static final String dbTableCRStatuS_B="B";
	  public static final String dbTableCRStatuS_R="R";
	  public static final String dbTableCRStatuS_T="T";
	  public static final String  lateExcelheaders="序号,工号,姓名,部门,单位,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 ,21,22,23,24,25,26,27,28,29,30,31,分,小时,迟到次数";
	  public static final String  earlyExcelheaders="序号,工号,姓名,部门,单位,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 ,21,22,23,24,25,26,27,28,29,30,31,分,小时,早退次数";
	  public static final String  dailyExcelheaders="序号,工号,姓名,部门,单位,正班出勤,加班,年假,公假,产假,婚假,丧假,病假,事假,旷工,迟到,待工,补贴餐费,备注";
	  public static final String  repAttendanceExcelheaders="序号,工号,姓名,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22 ,23,24,25,26,27,28,29,30,31,总共";
	  public static final String  repAttendanceDayExcelheaders="部,单位,外籍干部实际人数,&越籍初期应出勤人数,&越籍当日应出勤人数,&越籍当日实际出勤人数,行政班次07:30~16:30,早班次06:00~14 :00,中班次14:00~22:00,夜班次22:00~06:00,申请特加班次18:00~06:00,产假,请假,年假,出差,工伤,旷工(或未提出请假),周六排休,新人报道,调动,离职,离职率";
	  public static final String empnumOneYear="1年以下";
	  public static final String empnumTwoYear="1年~2年";
	  public static final String empnumThreeYear="2年~3年";
	  public static final String empnumFourYear="3年以上";
	  public static final String empnumMsg="合計";
	  public static final String  empnumheaders="越籍年资分布,年度,人数,";
	  public static final String  LRessons="加班原因";
	  public static final String  saveOK="新增成功";
	  public static final String  editOK="修改成功";
	  public static final String  editDeptNoUnit="请先将相关单位删除";
	  public static final String  editDeptIDRepeat="部门ID不可重复";
	  public static final String  editNO="修改失败";
	  public static final String   rowID="ID";
	  public static final String   overTimeReasons="加班原因";
	  public static final String   sortNO="排序编号";
	  public static final String   VLanguage="越南語";
	  public static final String  ELanguage="英语";
	  public static final String  Clas="代碼";
	  public static final String   holidayReasons="请假原因";
	  public static final String   ColDept="部门名称";
	  public static final String   ColDeptID="部门代号";
	  public static final String   ColENAME="英文名";
	  public static final String   ColUnit="单位名称";
	  public static final String   stopReasons="待工原因";
	  public static final String   masterCheck="经理审核通过";
	  public static final String   bossCheck="副总审核通过";
	  /**一般員工**/
	  public static final String   EmpRoleE="E";
	  /**单位主管**/
	  public static final String   EmpRoleU="U";
	  /**部门主管**/
	  public static final String   EmpRoleD="D";
	  /**經理**/
	  public static final String   EmpRoleM="M";
	  /**副總**/
	  public static final String   EmpRoleB="B";
	  /**单位退回狀態**/
	  public static final String dbTableUR="UR";
	  /**单位提交員工超時加班狀態**/
	  public static final String dbTableUT="UT";
	  /**人事提交員工超時加班狀態**/
	  public static final String dbTableRD="RD";
	  /**日期調整參數Max**/
	  public static final String spinnerDayMax="30";
	  /**日期調整參數Min**/
	  public static final String spinnerDayMin="0";
	  /**日期調整參數Step**/
	  public static final String spinnerDayStep="0.5";
	  /**小時調整參數Max**/
	  public static final String spinnerHourMax="7.5";
	  /**小時調整參數Min**/
	  public static final String spinnerHourMin="0";
	  /**小時調整參數Step**/
	  public static final String spinnerHourStep="0.5";
	  /**分鐘調整參數Max**/
	  public static final String spinnerMinuteMax="30";
	  /**分鐘調整參數Min**/
	  public static final String spinnerMinuteMin="0";
	  /**分鐘調整參數Step**/
	  public static final String spinnerMinuteStep="30";
	  /**月報表上班**/
	  public static final String monthReportX="X";
	  /**月報表工傷**/
	  public static final String monthReportF="F";
	  /**月報表輪休**/
	  public static final String monthReportL="L";
	  /**月報表產假**/
	  public static final String monthReportTS="TS";
	  /**月報表婚假**/
	  public static final String monthReportH="H";
	  /**月報表特休**/
	  public static final String monthReportP="P";
	  /**月報表公假**/
	  public static final String monthReportN="N";
	  /**月報表事假**/
	  public static final String monthReportR="R";
	  /**月報表病假**/
	  public static final String monthReportB="B";
	  /**月報表曠工**/
	  public static final String monthReportO="O";
	  /**月報表喪假**/
	  public static final String monthReportT="T";
	  /**月報表待工**/
	  public static final String monthReportW="W";
	 
}
