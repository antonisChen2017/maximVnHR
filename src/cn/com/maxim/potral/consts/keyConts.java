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
	  /**人事系統介面**/
	  public static final String personCSList= "CSRp";
	  
	  public static final String pageDtmList= "DeptView";
	  public static final String pageUsList= "usList";
	  public static final String pageGList= "gList";
	  public static final String pageMsList= "msList";
	  public static final String pagePList= "pList";
	  public static final String pageLList= "LList";
	  public static final String pageInspect = "inspect";
	  public static final String dbTableCR= "VN_LEAVECARD";
	  public static final String dbTableCRStatuS_U="U";
	  public static final String dbTableCRStatuS_D="D";
	  public static final String dbTableCRStatuS_B="B";
	  public static final String dbTableCRStatuS_R="R";
	  public static final String dbTableCRStatuS_T="T";
	  
	  public static final String LeaveStatus_M="M";
	  public static final String LeaveCRStatus_MR="MR";
	  /**經理**/
	  public static final String dbTableCRStatuS_L="L";
	  public static final String  lateExcelheaders="STT\n序\n次,MST\n工号,TÊN\n姓名,BỘ PHẬN\n部门,ĐƠN VỊ\n单位,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 ,21,22,23,24,25,26,27,28,29,30,31,分,小时,迟到次数";
	  public static final String  earlyExcelheaders="STT\n序\n次,MST\n工号,TÊN\n姓名,BỘ PHẬN\n部门,ĐƠN VỊ\n单位,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 ,21,22,23,24,25,26,27,28,29,30,31,分,小时,早退次数";
	  public static final String  dailyExcelheaders="STT\n序\n次,MST\n工号,HỌ TÊN\n姓名,BỘ PHẬN\n部门,ĐƠN VỊ\n单位,正班出勤\nGiờ công,加班,年假 \n Phép năm,公假 \n Phép công,产假 \n Thai sản,婚假 \n Kết hôn ,丧假  \n  Phép tang,病假 \n Phép bệnh ,事假 \n Việc riêng,旷工 \n Lãng công ,迟到  \n Đi trễ  ,    ,待工\n Nghỉ    chờ  \n  việ  ,簽名 \n Ký tê,GHI CHÚ  \n 备注";
	  public static final String  repAttendanceExcelheaders="序号,工号,姓名,部门,单位,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22 ,23,24,25,26,27,28,29,30,31,总共";
	  public static final String  repAttendanceDayExcelheaders="部,单位,外籍干部实际人数,&越籍初期应出勤人数,&越籍当日应出勤人数,&越籍当日实际出勤人数,行政班次07:30~16:30,早班次06:00~14 :00,中班次14:00~22:00,夜班次22:00~06:00,申请特加班次18:00~06:00,产假,请假,年假,出差,工伤,旷工(或未提出请假),周六排休,新人报道,调动,离职,离职率";
	  public static final String empnumOneYear="1年以下";
	  public static final String empnumTwoYear="1年~2年";
	  public static final String empnumThreeYear="2年~3年";
	  public static final String empnumFourYear="3年以上";
	  public static final String empnumMsg="合計";
	  public static final String  empnumheaders="越籍年资分布,年度,人数,";
	  public static final String  LRessons="加班原因";
	  public static final String  saveOK="新增成功";
	  public static final String  saveNO="新增失败";
	  public static final String  editOverTip="修改加班单";
	  public static final String  editLeaveTip="修改请假单";
	  public static final String  editStopTip="修改待工单";
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
	  public static final String   ColGroup="组别";
	  public static final String   stopReasons="待工原因";
	  public static final String   masterCheck="经理审核通过";
	  public static final String   bossCheck="副总审核通过";
	  public static final String   noProcessMsg="此部门或单位尚未设定请假流程,请洽人事";
	  public static final String   noGroupMsgP1="此組別:";
	  public static final String   noGroupMsgP2="尚未设定请假流程,请洽人事";
	  public static final String   noGroupMsgP3="尚未设定加班流程,请洽人事";
	  public static final String   noGroupMsgP4="尚未设定待工流程,请洽人事";
	  public static final String   noProcessOverMsg="此部门或单位尚未设定加班流程,请洽人事";
	  public static final String   noTimeCSMsg="當天申請CS加班不能超過12:30";
	  public static final String   deletOK="删除成功";
	  public static final String   deletNO="删除失败";
	  public static final String  updateOK="更新成功";
	  public static final String  updateNO="更新失败";
	  /**組長**/
	  public static final String   EmpRoleG="G";
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
	  
	  /**副總退回**/
	  public static final String   StatusBR="BR";
	  /**經理退回**/
	  public static final String   StatusLR="LR";
	  /**組長退回**/
	  public static final String   StatusGR="GR";
	  /**部門主管退回**/
	  public static final String   StatusDR="DR";
	  /**單位主管退回**/
	  public static final String   StatusUR="UR";
	  /**单位退回狀態**/
	  public static final String dbTableUR="UR";
	  /**单位提交員工超時加班狀態**/
	  public static final String dbTableUT="UT";
	  /**員工超時加班狀態-紀錄**/
	  public static final String dbTableRS="RS";
	  /**員工超時加班狀態-提交**/
	  public static final String dbTableRT="RT";
	  /**員工超時加班狀態-副總通過**/
	  public static final String dbTableRB="RB";
	  /***員工超時加班狀態-副總退回**/
	  public static final String dbTableRR="RR";
	  /**紀錄資料狀態**/
	  public static final String dbTableS="S";
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
	  /**工作流程未選擇**/
	  public static final String msgZ= "無";
	  /**流程三天以下**/
	  public static final String processStatus0= "0";
	  /**流程三天以上**/
	  public static final String processStatus1= "1";
	  /**有此流程**/
	  public static final String processLv1= "1";
	  /**無此流程**/
	  public static final String processLv0= "0";
	  /**部门尚未设定审核流程**/
	  public static final String processNoData= "部门尚未设定审核流程";
	  /**取得部门设定审核流程**/
	  public static final String processData= "取得部门设定审核流程";
	  /**單位尚未设定审核流程**/
	  public static final String processUnitNoData= "单位尚未设定审核流程";
	  /**组别尚未设定审核流程**/
	  public static final String processGroupNoData= "组别尚未设定审核流程";
	  /**取得單位设定审核流程**/
	  public static final String processUnitData= "取得单位设定审核流程";
	  /**取得组别设定审核流程**/
	  public static final String processGroupData= "取得组别设定审核流程";
	  /**下拉未選擇**/
	  public static final String msgS= "未選擇";
	  /**副總請假頁面**/
	  public static final String pageB= "B";
	  /**請假頁面按紐狀態**/
	  public static final String butSave= "保存";
	  /**請假頁面按紐狀態**/
	  public static final String butUpdate= "更新";	  
	  /**請假信件標題**/
	  public static final String EmailLeaveSubject= "请假单审核通知";
	  /**待工信件標題**/
	  public static final String EmailStopSubject= "待工单审核通知";
	
	  /**加班信件標題**/
	  public static final String EmailOverSubject= "加班单审核通知";
	  /**加班信件標題**/
	  public static final String EmailCSSubject= "緊急加班单审核通知";
	  /**CS加班信件標題**/
	  public static final String EmailUrgentOverSubject= "紧急加班单审核通知";
	  
	  public static final String processCSTable= "VN_DEPT_CS_ROLE";
	  
	  public static final String processOVTable= "VN_DEPT_OVER_ROLE";
	  
	  public static final String processSPTable= "VN_DEPT_STOP_ROLE";
	  
	  public static final String processLETable= " VN_DEPT_LEAVE_ROLE";
	  /**SMTP**/
	  public static final String EmailSmtp="EMAIL_SMTP";
	  /**PORT**/
	  public static final String EmailPort="EMAIL_PORT";
	  /**USER**/
	  public static final String EmailUser="EMAIL_USER";
	  /**PW**/
	  public static final String EmailPw="EMAIL_PW";
	  /**FROM**/
	  public static final String EmailProw="EMAIL_FROM";
	  /**SingRoleL1EP**/
	  public static final String SingRoleL1EP="SINGROLEL1EP";
	  /**SingRoleL2EP**/
	  public static final String SingRoleL2EP="SINGROLEL2EP";
	  /**SingRoleL3EP**/
	  public static final String SingRoleL3EP="SINGROLEL3EP";
	  /**SingRoleL4EP**/
	  public static final String SingRoleL4EP="SINGROLEL4EP";
	  /**建立新帳號**/
	  public static final String buildMsg= "建立新帐号成功!";
	  /**建立新帳號**/
	  public static final String unitBuildMsg= "更新部门单位与登入帐号修改权限成功";
	  /**建立新帳號**/
	  public static final String unitBuildNoMsg= "更新部门单位与登入帐号修改权限失败";
	  /**建立新帳號**/
	  public static final String buildNoMsg= "建立新帐号失败";
	  
	  /**刪除登入帳號**/
	  public static final String delbuildMsg= "删除登入帐号成功!";
	  /***刪除登入帳號**/
	  public static final String delbuildNoMsg= "删除登入帐号失败";
	  /**報表-顯示未打卡**/
	  public static final String NoCard="No Punch Card";
	  /**報表-無打卡時間**/
	  public static final String noTime="00:00";
	  
	  public static final String checkDayTime="加班一天不能超过4小时";
	  
	  public static final String checkWeekTime="加班一周不能超过12小时";
	  
}
