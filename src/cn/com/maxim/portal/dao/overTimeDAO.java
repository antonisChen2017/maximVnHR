package cn.com.maxim.portal.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import cn.com.maxim.DB.DBManager;
import cn.com.maxim.portal.attendan.ro.configRO;
import cn.com.maxim.portal.attendan.ro.editProcessRO;
import cn.com.maxim.portal.attendan.ro.leaveEmailListRO;
import cn.com.maxim.portal.attendan.ro.overEmailListRO;
import cn.com.maxim.portal.attendan.ro.processCheckRO;
import cn.com.maxim.portal.attendan.ro.processUserRO;
import cn.com.maxim.portal.attendan.ro.supervisorRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.attendan.wo.EmailWO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DBUtilTList;
import cn.com.maxim.portal.util.DateUtil;
import cn.com.maxim.portal.util.EmailUtil;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.ReflectHelper;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.portal.util.vnStringUtil;
import cn.com.maxim.potral.consts.keyConts;
import cn.com.maxim.potral.consts.sqlConsts;

public class overTimeDAO {

    /**
     * 主管加班all通過
     */
    public static final String deptAllProcess(Connection con, overTimeVO otVo, String rowIDs) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	String[] rowIDLs = rowIDs.split("##");
	logger.info("rowIDLs"+rowIDLs[0]);
	logger.info("rowIDLslength"+rowIDLs.length);
	for (int i = 0; i < rowIDLs.length; i++) {
	    if(rowIDLs[i].length()>0){
        	    otVo.setRowID(rowIDLs[i]);
        	    deptProcess(con, otVo);
	    }
	}
	otVo.setMsg(keyConts.okMsg);
	return otVo.getMsg();
    }

    /**
     * 主管加班判斷流程
     */
    public static final String deptProcess(Connection con, overTimeVO otVo) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);

	// 正常加班流程
	editProcessRO edPR = DBUtil.getProcessOverIDData(otVo, con);

	
	// 查詢流程
	    if (otVo.getStatus().equals("G")) {// 組長
		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0")
			&& edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")) { 
		    logger.info("之後沒有審核");
		    otVo.setLeaveApply("1");// 請假完成
		    otVo.setNextStatus("X");
		    if (DBUtil.updateTimeOverSStatus(otVo, con)) {
			    otVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL1().equals("1")) { 
		    logger.info("之後單位主管審核");
		    otVo.setLeaveApply("0");// 請假完成
		    otVo.setNextStatus("U");
		    if (DBUtil.updateTimeOverSStatus(otVo, con)) {
			otVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("1")) { 
		    logger.info("之後部門主管審核");
		    otVo.setLeaveApply("0");// 請假完成
		    otVo.setNextStatus("D");
		    if (DBUtil.updateTimeOverSStatus(otVo, con)) {
			otVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")) { 
		    logger.info("之後經理審核");
		    otVo.setLeaveApply("0");// 請假完成
		    otVo.setNextStatus("L");
		    if (DBUtil.updateTimeOverSStatus(otVo, con)) {
			otVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")&& edPR.getSingRoleL4().equals("1")) { 
		    logger.info("之後副總審核");
		    otVo.setLeaveApply("0");// 請假完成
		    otVo.setNextStatus("B");
		    if (DBUtil.updateTimeOverSStatus(otVo, con)) {
			otVo.setMsg(keyConts.okMsg);
		    }
		}
	    }
	
	if (otVo.getStatus().equals("U")) {// 單位主管

	    if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")
		    && edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		logger.info("之後沒有審核");
		otVo.setLeaveApply("1");// 請假完成
		otVo.setNextStatus("X");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
	    }
	    if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("0")
		    && edPR.getSingRoleL4().equals("0")) { // 部門主管審核
		logger.info("部門主管審核");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("D");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 直接寄信 **/
	    }
	    if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1")
		    && edPR.getSingRoleL4().equals("0")) { // 部門主管審核
		logger.info("部門主管經理審核");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("D");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 直接寄信 **/
	    }
	    if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1")
		    && edPR.getSingRoleL4().equals("1")) { // 部門主管審核
		logger.info("部門主管審核");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("D");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 直接寄信 **/
	    }
	    if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")
		    && edPR.getSingRoleL4().equals("0")) { // 經理審核
		logger.info("經理審核");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("L");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 存入資料列表寄信 **/
	    }
	    if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")
		    && edPR.getSingRoleL4().equals("1")) { // 經理副總審核
		logger.info("經理副總審核");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("L");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 存入資料列表寄信 **/
	    }
	    if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")
		    && edPR.getSingRoleL4().equals("1")) { // 副總
		logger.info("副總審核");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("L");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 存入資料列表寄信 **/
	    }
	}
	// 查詢流程
	if (otVo.getStatus().equals("D")) {// 部門主管

	    logger.info("部門主管判斷流程 ");
	    if (edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		logger.info("之後沒有審核");
		otVo.setLeaveApply("1");// 請假完成
		otVo.setNextStatus("X");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
	    }
	    if (edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")) { // 跳過經理
		logger.info("跳過經理");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("B");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 存入資料列表寄信 **/
	    }
	    if (edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")) { // 之後經理副總審核
		logger.info("之後經理副總審核");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("L");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 直接寄信 **/
	    }
	    if (edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")) { // 之後經理審核
		logger.info("之後經理審核");
		otVo.setLeaveApply("0");// 請假未完成
		otVo.setNextStatus("L");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 直接寄信 **/
	    }
	}

	if (otVo.getStatus().equals("L")) {// 經理
	    if (edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		logger.info("之後沒有審核");
		otVo.setLeaveApply("1");// 請假完成
		otVo.setNextStatus("X");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
	    }
	    if (edPR.getSingRoleL4().equals("1")) { // 副總審核
		logger.info("副總審核");
		otVo.setLeaveApply("0");// 請假完成
		otVo.setNextStatus("B");
		if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		    otVo.setMsg(keyConts.okMsg);
		}
		/** 直接寄信 **/
	    }
	}

	if (otVo.getStatus().equals("B")) {// 副總
	    logger.info("副總判斷流程 ");
	    otVo.setLeaveApply("1");// 請假完成
	    otVo.setNextStatus("X");
	    if (DBUtil.updateTimeOverSStatus(otVo, con)) {
		otVo.setMsg(keyConts.okMsg);
	    }
	}

	return otVo.getMsg();
    }

    /**
     * 主管加班判斷流程-寄信通知
     */
    public static void deptProcessEmail(Connection con, overTimeVO otVo) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(overTimeDAO.class);

	editProcessRO edPR = DBUtil.getProcessOverIDData(otVo, con);

	// 查寄信人與主管等級差異-只差一級才能馬上寄
	if (otVo.getSearchEmployeeNo().isEmpty() || otVo.getSearchEmployeeNo().equals("0")) {
	    logger.info("SearchEmployeeNo IS NULL  " + SqlUtil.queryOverEPID(otVo.getRowID()));
	    otVo.setSearchEmployeeNo(DBUtil.queryDBField(con, SqlUtil.queryOverEPID(otVo.getRowID()), "EPID"));
	}
	logger.info("deptProcessEmail edPR " + edPR.toString());
	processUserRO ru = DBUtil.getEmpUser(con, otVo.getSearchEmployeeNo());
	logger.info("立刻寄信通知 SearchRole " + ru.getROLE());
	boolean checkSing = false;// 是否為上一級
	boolean checkEmail = false;// 是否有信箱
	String SingPeople = "", toEamil = "";// 寄信主管
	// 查出email資料-此主管無EMAIL就不寄
	// 員工 寄送 單位主管
	if (ru.getROLE().equals(keyConts.EmpRoleE) && edPR.getSingRoleL1().equals("1")) {
	    SingPeople = edPR.getSingRoleL1EP();
	    checkSing = true;
	}
	if (ru.getROLE().equals(keyConts.EmpRoleU) && edPR.getSingRoleL2().equals("1")) {
	    SingPeople = edPR.getSingRoleL2EP();
	    checkSing = true;
	}
	if (ru.getROLE().equals(keyConts.EmpRoleD) && edPR.getSingRoleL3().equals("1")) {
	    SingPeople = edPR.getSingRoleL3EP();
	    checkSing = true;
	}
	if (ru.getROLE().equals(keyConts.EmpRoleM) && edPR.getSingRoleL4().equals("1")) {
	    SingPeople = edPR.getSingRoleL4EP();

	    checkSing = true;
	}
	// 查出email資料-此主管無EMAIL就不寄
	if (checkSing) {
	    logger.info("只差一級:檢查此主管有無email");
	    ArrayList al = checkMisterEmail(con, SingPeople);
	    checkEmail = (boolean) al.get(0);
	    toEamil = (String) al.get(1);
	}
	// 馬上寄
	if (checkEmail) {

	    logger.info("此主管工號:" + SingPeople + "馬上寄信");
	    // 查出寄信伺服器資料

	    configRO ra = new configRO();
	    EmailWO ew = DBUtil.queryConfigRow(con, SqlUtil.getEmailConfig(), ra);
	    ew.setTO(toEamil);
	    ew.setSUBJECT(keyConts.EmailOverSubject);

	    String USER = DBUtil.queryDBField(con, SqlUtil.queryEmpName(SingPeople), "EMPLOYEE");
	    logger.info("主管名稱:" + USER + "email:" + ew.getTO());
	    ew.setUSER(USER);

	    String rUserNo = DBUtil.queryDBField(con, SqlUtil.queryEmpID(otVo.getSearchEmployeeNo()), "EMPLOYEENO");
	   // otVo.setSearchEmployeeNo(rUserNo);
	    String rUser = DBUtil.queryDBField(con, SqlUtil.queryEmpName(rUserNo), "EMPLOYEE");
	    ew.setCONTENT(EmailUtil.getOverEmailTemplate(USER, rUserNo, rUser));

	    // logger.info("getCONTENT:"+ew.getCONTENT());
	    String Emailmsg = EmailUtil.sendmail(ew);
	    logger.info("此主管工號:" + SingPeople + "寄信 " + Emailmsg);
	    ew.setEMP(SingPeople);
	    if (Emailmsg.equals("ok!")) {
		ew.setSENTSTATUS("0");// 正常寄信
	    } else {
		ew.setSENTSTATUS("1");// 寄信錯誤
	    }
	    ew.setEMAILSTATUS("0");// 正常寄信
	    // 寄信紀錄-EMAIL表

	    DBUtil.workLateOperationSql(con, SqlUtil.insterVnEmail(ew));
	} else {
	    logger.info("此主管無email 不寄信");
	}

    }

    /**
     * CS加班-4點寄信通知
     */
    public static void CS4ProcessTimeEmail() throws Exception {
	
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	logger.info("主管請假判斷流程-定時寄信通知");
	String SingPeople = "", SwLeave = "", toEamil = "";// 寄信主管
	boolean checkEmail = false;// 是否有信箱

	Connection con = DBUtil.getHRCon();

	/** --員工等待副總審查有幾人-START- **/
	logger.info("員工CS提交等待副總審查");
	overTimeVO lc = new overTimeVO();
	lc.setSearchRole("E");
	lc.setStatus("RT");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	lc.setEMAIL_STATUS("1");
	forCSSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --員工等待副總審查有幾人-END- **/
	logger.info("=================================");
    }

    /**
     * 設定郵件資料
     * 
     * @param con
     * @param SingEp
     * @return
     * @throws Exception
     */
    public static void forCSSendEmail(overTimeVO lc, Connection con, Logger logger, String toEamil,
	    boolean checkEmail) throws Exception {

	logger.info(" CS加班定時寄信通知 queryEmailSingep " + SqlUtil.queryEmailCSSingep(lc));
	List<supervisorRO> lsr = DBUtil.queryEmailSingep(con, SqlUtil.queryEmailCSSingep(lc));
	logger.info("CS加班定時寄信通知 lsr " + lsr.size());

	for (int i = 0; i < lsr.size(); i++) {
	    String SingEp = lsr.get(i).getSINGEP();
	    logger.info("主管工號 SingEp" + SingEp);
	    // 查出email資料-此主管無EMAIL就不寄
	    ArrayList al = checkMisterEmail(con, SingEp);

	    logger.info("al" + al.get(0));
	    checkEmail = (boolean) al.get(0);
	    toEamil = (String) al.get(1);
	    if (checkEmail) {
		// 主管有尚未審核請假單者寄信
		lc.setNote(lc.getNote() + " = '" + SingEp + "'");
		logger.info("加班 定時寄信通知 querySendEmailOverList " + SqlUtil.querySendEmailCSList(lc));
		List<overEmailListRO> ler = DBUtil.querySendEmailOverList(con, SqlUtil.querySendEmailCSList(lc));
		logger.info("ler " + ler);
		if (ler.size() > 0) {
		    setEmailCSSend(con, toEamil, SingEp, ler);
		}
	    }
	}
    }
    
    /**
     * CS加班-1點寄信通知
     */
    public static void CS1ProcessTimeEmail() throws Exception {
	
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	logger.info("主管請假判斷流程-定時寄信通知");
	String SingPeople = "", SwLeave = "", toEamil = "";// 寄信主管
	boolean checkEmail = false;// 是否有信箱

	Connection con = DBUtil.getHRCon();
	logger.info("=================================");
	/** --員工等待副總審查有幾人-START- **/
	logger.info("員工CS提交等待副總審查");
	overTimeVO lc = new overTimeVO();
	lc.setSearchRole("E");
	lc.setStatus("RT");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	lc.setEMAIL_STATUS("0");
	forCSSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --員工等待副總審查有幾人-END- **/
	logger.info("=================================");
    }
    
    /**
     * 主管加班判斷流程-定時寄信通知
     */
    public static void deptProcessTimeEmail() throws Exception {

	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	logger.info("主管請假判斷流程-定時寄信通知");
	String SingPeople = "", SwLeave = "", toEamil = "";// 寄信主管
	boolean checkEmail = false;// 是否有信箱

	Connection con = DBUtil.getHRCon();
	// 先查出當天請假需要寄信主管 查寄信人與主管等級差異-兩級以上定時寄
	logger.info("=================================");
	leaveCardVO lc = new leaveCardVO();
	
	
	
	
	/** --員工請假組長審過等待單位主管審查有幾個主管-START- **/
	logger.info("員工請假組長審過等待單位主管審查");
	lc.setSearchRole("E");
	lc.setStatus("G");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL1EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --員工請假組長審過等待單位主管審查-END- **/
	
	/** -特殊-員工組長審過跳到部門主管審查-START- **/
	logger.info("員工組長審過跳到部門主管審查");
	lc.setSearchRole("E");
	lc.setStatus("G");
	lc.setReturnMsg(" and SINGROLEL1='0'  ");
	lc.setNote(keyConts.SingRoleL2EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工組長審過跳到部門主管審查-END- **/
	
	/** -特殊-員工組長審過跳到經理審查-START- **/
	logger.info("員工組長審過跳到部門主管審查");
	lc.setSearchRole("E");
	lc.setStatus("G");
	lc.setReturnMsg(" and SINGROLEL1='0'   and SINGROLEL2='0' ");
	lc.setNote(keyConts.SingRoleL3EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工組長審過跳到經理審查-END- **/
	
	/** -特殊-員工組長審過跳到副總審查-START- **/
	logger.info("員工組長審過跳到部門主管審查");
	lc.setSearchRole("E");
	lc.setStatus("G");
	lc.setReturnMsg(" and SINGROLEL1='0'   and SINGROLEL2='0' and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工組長審過跳到副總審查-END- **/
	
	/** --員工請假單位主管審過等待部門主管審查有幾個主管-START- **/
	logger.info("員工加班單位主管審過等待部門主管審查");
	lc.setSearchRole("E");
	lc.setStatus("U");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL2EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --員工請假單位主管審過等待部門主管審查有幾個主管-END- **/
	logger.info("=================================");
	/** --員工部門主管審過等待經理審查有幾人主管-START- **/
	logger.info("員工部門主管審過等待經理審查");
	lc.setSearchRole("E");
	lc.setStatus("D");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL3EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	logger.info("=================================");
	/** --員工經理審過等待副總審查有幾人-START- **/
	logger.info("員工經理審過等待副總審查");
	lc.setSearchRole("E");
	lc.setStatus("L");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --員工經理審過等待副總審查有幾人-END- **/
	
	/** --組長請假單位主管審過等待部門主管審查有幾個主管-START- **/
	logger.info("組長請假單位主管審過等待部門主管審查");
	lc.setSearchRole("G");
	lc.setStatus("U");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL2EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --組長請假單位主管審過等待部門主管審查有幾個主管-END- **/
	logger.info("=================================");
	/** --組長部門主管審過等待經理審查-START- **/
	logger.info("組長部門主管審過等待經理審查");
	lc.setSearchRole("G");
	lc.setStatus("D");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL3EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	logger.info("=================================");
	
	
	/** --組長經理審過等待副總審查有幾人-START- **/
	logger.info("=================================");
	logger.info("組長待工經理審過等待副總審查");
	lc.setSearchRole("G");
	lc.setStatus("L");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	logger.info("=================================");
	/** --組長經理審過等待副總審查有幾人-END- **/
	
	/** --特殊-組長單位主管審過跳到經理審查-START- **/
	logger.info("組長單位主管審過跳到經理審查");
	lc.setSearchRole("G");
	lc.setStatus("U");
	lc.setReturnMsg("  and SINGROLEL2='0' ");
	lc.setNote(keyConts.SingRoleL3EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-組長單位主管審過跳到經理審查-END- **/
	logger.info("=================================");
	/** --特殊-組長單位主管審過跳到副總審查-START- **/
	logger.info("組長單位主管審過跳到副總審查");
	lc.setSearchRole("G");
	lc.setStatus("U");
	lc.setReturnMsg("   and SINGROLEL2='0'  and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工單位主管審過跳到副總審查-END- **/
	
	
	logger.info("=================================");
	/** --單位主管申請 部門主管審過等待經理審-START- **/
	logger.info("單位主管申請 部門主管審過等待經理審");
	lc.setSearchRole("U");
	lc.setStatus("D");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL3EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --單位主管申請 部門主管審過等待經理審-END- **/
	logger.info("=================================");
	/** --單位主管申請 部門主管審過等待副總審-START- **/
	logger.info("單位主管申請 部門主管審過等待副總審");
	lc.setSearchRole("U");
	lc.setStatus("L");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --單位主管申請 部門主管審過等待副總審-END- **/
	logger.info("=================================");

	/** --部門主管申請 經理審過等待副總審-START- **/
	logger.info("部門主管申請 經理審過等待副總審");
	lc.setSearchRole("D");
	lc.setStatus("L");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --部門主管申請 經理審過等待副總審-END- **/
	logger.info("=================================");
	/** --特殊-員工單位主管審過跳到經理審查-START- **/
	logger.info("員工單位主管審過跳到經理審查");
	lc.setSearchRole("E");
	lc.setStatus("U");
	lc.setReturnMsg("  and SINGROLEL2='0' ");
	lc.setNote(keyConts.SingRoleL3EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工單位主管審過跳到經理審查-END- **/
	logger.info("=================================");
	/** --特殊-員工單位主管審過跳到副總審查-START- **/
	logger.info("員工單位主管審過跳到副總審查");
	lc.setSearchRole("E");
	lc.setStatus("U");
	lc.setReturnMsg("   and SINGROLEL2='0'  and SINGROLEL3='0'");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工單位主管審過跳到副總審查-END- **/

	logger.info("=================================");
	/** --特殊-員工跳到副總審查-START- **/
	logger.info("員工跳到副總審查");
	lc.setSearchRole("E");
	lc.setStatus("T");
	lc.setReturnMsg("  and SINGROLEL1='0' and SINGROLEL2='0' and SINGROLEL3='0'");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工跳到副總審查-END- **/
	/** --特殊-單位主管跳過部門等待經理審-START- **/
	logger.info("特殊-單位主管跳過部門等待經理審");
	lc.setSearchRole("U");
	lc.setStatus("T");
	lc.setReturnMsg("   and SINGROLEL2='0' ");
	lc.setNote(keyConts.SingRoleL3EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-單位主管跳過部門等待經理審-END- **/
	logger.info("=================================");
	/** --特殊-單位主管直接副總審-START- **/
	logger.info("特殊-單位主管直接副總審");
	lc.setSearchRole("U");
	lc.setStatus("T");
	lc.setReturnMsg("   and SINGROLEL2='0'  and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-單位主管直接副總審-END- **/
	logger.info("=================================");
	/** --特殊-部門主管直接副總審-START- **/
	logger.info("特殊-部門主管直接副總審");
	lc.setSearchRole("D");
	lc.setStatus("T");
	lc.setReturnMsg("    and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forOverSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-部門主管直接副總審-END- **/
	logger.info("=================================");

    }

    /**
     * 檢查有無主管郵箱
     * 
     * @param con
     * @param SingEp
     * @return
     * @throws Exception
     */
    public static ArrayList checkMisterEmail(Connection con, String SingEp) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(overTimeDAO.class);
	String toEamil = "";// 寄信主管
	ArrayList al = new ArrayList();
	boolean checkEmail = false;// 是否有信箱
	toEamil = DBUtil.queryDBField(con, SqlUtil.queryEmail(SingEp), "Email");
	if (toEamil != null && !toEamil.equals("")) {
	    checkEmail = EmailUtil.isEmail(toEamil);
	    if (checkEmail) {
		logger.info("此主管工號:" + SingEp + "有email 可寄送");
	    } else {
		logger.info("此主管工號:" + SingEp + "有email 但email不規範 無法寄送");
	    }

	} else {
	    logger.info("此主管工號:" + SingEp + "無email");
	}
	al.add(checkEmail);
	al.add(toEamil);
	return al;
    }

    /**
     * 設定郵件資料
     * 
     * @param con
     * @param SingEp
     * @return
     * @throws Exception
     */
    public static void forOverSendEmail(leaveCardVO lc, Connection con, Logger logger, String toEamil,
	    boolean checkEmail) throws Exception {

	logger.info(" 加班定時寄信通知 queryEmailSingep " + SqlUtil.queryEmailOverSingep(lc));
	List<supervisorRO> lsr = DBUtil.queryEmailSingep(con, SqlUtil.queryEmailOverSingep(lc));
	logger.info("加班定時寄信通知 lsr " + lsr.size());

	for (int i = 0; i < lsr.size(); i++) {
	    String SingEp = lsr.get(i).getSINGEP();
	    logger.info("主管工號 SingEp" + SingEp);
	    // 查出email資料-此主管無EMAIL就不寄
	    ArrayList al = checkMisterEmail(con, SingEp);

	    logger.info("al" + al.get(0));
	    checkEmail = (boolean) al.get(0);
	    toEamil = (String) al.get(1);
	    if (checkEmail) {
		// 主管有尚未審核請假單者寄信
		lc.setNote(lc.getNote() + " = '" + SingEp + "'");
		logger.info("加班 定時寄信通知 querySendEmailOverList " + SqlUtil.querySendEmailOverList(lc));
		List<overEmailListRO> ler = DBUtil.querySendEmailOverList(con, SqlUtil.querySendEmailOverList(lc));
		logger.info("ler " + ler);
		if (ler.size() > 0) {
		    setEmailSend(con, toEamil, SingEp, ler);
		}
	    }
	}
    }

    /**
     * 設定郵件資料
     * 
     * @param con
     * @param SingEp
     * @return
     * @throws Exception
     */
    public static void setEmailSend(Connection con, String toEamil, String SingEp, List<overEmailListRO> ler)
	    throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(overTimeDAO.class);
	configRO ra = new configRO();
	EmailWO ew = DBUtil.queryConfigRow(con, SqlUtil.getEmailConfig(), ra);
	ew.setTO(toEamil);
	ew.setSUBJECT(keyConts.EmailOverSubject);

	String USER = DBUtil.queryDBField(con, SqlUtil.queryEmpName(SingEp), "EMPLOYEE");
	logger.info("主管名稱:" + USER + "email:" + ew.getTO());
	ew.setUSER(USER);
	ew.setCONTENT(EmailUtil.getOverEmailTemplateList(ler, USER));

	String Emailmsg = EmailUtil.sendmail(ew);
	logger.info("此主管工號:" + SingEp + "寄信 " + Emailmsg);
	ew.setEMP(SingEp);
	if (Emailmsg.equals("ok")) {
	    ew.setSENTSTATUS("0");// 正常寄信
	} else {
	    ew.setSENTSTATUS("1");// 寄信錯誤
	}
	ew.setEMAILSTATUS("0");// 直接寄信
	// 寄信紀錄-EMAIL表
	DBUtil.workLateOperationSql(con, SqlUtil.insterVnEmail(ew));
    }

    
    /**
     * 設定郵件資料
     * 
     * @param con
     * @param SingEp
     * @return
     * @throws Exception
     */
    public static void setEmailCSSend(Connection con, String toEamil, String SingEp, List<overEmailListRO> ler)
	    throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(overTimeDAO.class);
	configRO ra = new configRO();
	EmailWO ew = DBUtil.queryConfigRow(con, SqlUtil.getEmailConfig(), ra);
	ew.setTO(toEamil);
	ew.setSUBJECT(keyConts.EmailCSSubject);

	String USER = DBUtil.queryDBField(con, SqlUtil.queryEmpName(SingEp), "EMPLOYEE");
	logger.info("主管名稱:" + USER + "email:" + ew.getTO());
	ew.setUSER(USER);
	ew.setCONTENT(EmailUtil.getCSEmailTemplateList(ler, USER));

	String Emailmsg = EmailUtil.sendmail(ew);
	logger.info("此主管工號:" + SingEp + "寄信 " + Emailmsg);
	ew.setEMP(SingEp);
	if (Emailmsg.equals("ok")) {
	    ew.setSENTSTATUS("0");// 正常寄信
	} else {
	    ew.setSENTSTATUS("1");// 寄信錯誤
	}
	ew.setEMAILSTATUS("0");// 直接寄信
	// 寄信紀錄-EMAIL表
	DBUtil.workLateOperationSql(con, SqlUtil.insterVnEmail(ew));
    }
    
    
    /**
     * 主管CS加班判斷流程-定時寄信通知
     */
    public static void SOProcessTimeEmail() throws Exception {

	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	logger.info("CS加班判斷流程-定時寄信通知");
	String SingPeople = "", SwLeave = "", toEamil = "";// 寄信主管
	boolean checkEmail = false;// 是否有信箱
	Connection con = DBUtil.getHRCon();
	
	logger.info("=================================");

    }
    
    /**
     * CS加班判斷是否為當天並寫入不同參數
     * EMAIL_STATUS 0 當天 13:00寄信
     * EMAIL_STATUS 1 當天 16:00寄信
     *  EMAIL_STATUS 2 當天超過12:30不能填寫
     */
    public static String CSSaveTimeCheck( overTimeVO otVo) throws Exception {

	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	String re="";
	logger.info("=================================");
	logger.info("CS加班判斷是否為當天並寫入不同參數");
	logger.info("CS加班判斷 輸入時間"+otVo.getQueryDate());
	boolean flag=DateUtil.isSameDate(otVo.getQueryDate());
	logger.info("CS加班判斷 flag"+flag);
	if(flag){ //同一天
	    Date d=new Date();
		//用d.getHour()可以获取当前小时数。
		int hour=d.getHours();
		int Minutes=d.getMinutes();
		if(hour >12){
		    re="2";
		}else if (hour ==12 && Minutes>30){
		    re="2";
		}else{
		    re="0"; 
		}
	}else{//不同天
	    re="1";
	}
	
	
	logger.info("=================================");

	
	return re;

    }
    
    
    /**
     * 加班判斷連續兩天如何切分加班單
     * flag 0  按照原來複製
     *  flag 1  跨天分割兩張單
     */
    public static String OverTimeCheckTwoDay(List<String> dateList,overTimeVO otVo) throws Exception {
	String flag="0";
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	logger.info("=========================================");
	
	    if(dateList.size()==2){
		    String value=DateUtil.largerTime(otVo.getStartTimeHh()+":"+otVo.getStartTimemm(),otVo.getEndTimeHh()+":"+otVo.getEndTimemm());
		//1.確認第二個時間段較小
		    if(value.equals("1")){
			flag="1";
		    }else{
			flag="0";
		    }
    
	    }else{
		flag="0";
	    }
	   
	logger.info("=========================================");
	return flag;
    }
    
    
    /**
     * 複製新Vo
     */
    public static overTimeVO  OverTimeCopyVo(overTimeVO otVo) throws Exception {
	
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	logger.info("=========================================");
	overTimeVO newVo =new overTimeVO();
	BeanUtils.copyProperties(newVo, otVo); 
	logger.info("=========================================");
	return newVo;
    }
    
    
    /**
	 * 加班先檢查是否有設定流程
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	String  getOverProcess(Connection con, overTimeVO otVo) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryUserData);
		sql = sql.replace("<EMPLOYEENO/>", otVo.getSearchEmployeeNo());
	
		PreparedStatement STMT = null;
		ReflectHelper rh = new ReflectHelper();
		List<processUserRO> leo = null;
		try
		{
			STMT = con.prepareStatement(sql);
			ResultSet rs = STMT.executeQuery();
			leo = (List<processUserRO>) rh.getBean(rs, ra);

		}
		catch (Exception error)
		{
			logger.error(vnStringUtil.getExceptionAllinformation(error));
		}
		processUserRO ru=(processUserRO)leo.get(0);
	
	
		String STATUS="1";//正常流程
		
		ru.setSTATUS(STATUS);
		int count=0;
		
		processCheckRO  prow=new processCheckRO();
		String COUNT ="";
		if(ru.getROLE().equals("E") || ru.getROLE().equals("U") || ru.getROLE().equals("G") ) {//多組長
			logger.info("檢查此部門或單位:"+SqlUtil.queryDeptUnitOverCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryDeptUnitOverCount(ru), "COUNT");
			count=Integer.valueOf(COUNT);
			if(count>0){
			/**檢查欄位**/
    			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
    			logger.info("processOVTable sql="+SqlUtil.queryProcessCheck(ru,keyConts.processOVTable));
    			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processOVTable), new processCheckRO());
    			if(cr.size()>0){
				    prow=cr.get(0);
				}
			}
		}else{
			logger.info("檢查此部門:"+SqlUtil.queryOverPreossCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryOverPreossCount(ru), "COUNT");
			/**檢查欄位**/
			count=Integer.valueOf(COUNT);
			if(count>0){
    			ru.setUNIT("0");		
    			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
    			logger.info("queryProcessCheck sql="+SqlUtil.queryProcessCheck(ru,keyConts.processOVTable));
    			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processOVTable), new processCheckRO());
    			if(cr.size()>0){
    			    prow=cr.get(0);
    			}
			}
		}
		if(count>0){
			
			/**檢查5個欄位有無值無值一樣不能過**/
			if(prow.getSINGROLEL0().equals("0") && prow.getSINGROLEL1().equals("0") && prow.getSINGROLEL2().equals("0")
				&& prow.getSINGROLEL3().equals("0") && prow.getSINGROLEL4().equals("0")){
			    count=0;
			}
		}
		String msg="o";
		if(count==0){
			msg="x";
			if(ru.getROLE().equals("E") && !ru.getGROUP().equals("0")){
			    logger.info("此員工為組員組別為:"+ru.getGROUP());
			    msg="g#"+ru.getGROUP(); 
			}
		}
	
		return msg;
	}
    
    
}
