package cn.com.maxim.portal.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import cn.com.maxim.DB.DBManager;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.configRO;
import cn.com.maxim.portal.attendan.ro.editProcessRO;
import cn.com.maxim.portal.attendan.ro.employeeUserRO;
import cn.com.maxim.portal.attendan.ro.leaveEmailListRO;
import cn.com.maxim.portal.attendan.ro.processCheckRO;
import cn.com.maxim.portal.attendan.ro.processUserRO;
import cn.com.maxim.portal.attendan.ro.supervisorRO;
import cn.com.maxim.portal.attendan.ro.workDateRO;
import cn.com.maxim.portal.attendan.vo.editProcessVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.wo.EmailWO;
import cn.com.maxim.portal.hr.dem_LeaveCard;
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

public class leaveCardDAO {

    /**
     * 主管請假all通過
     */
    public static final String deptAllProcess(Connection con, leaveCardVO lcVo, String rowIDs) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	logger.info("rowIDs"+rowIDs);
	
	String[] rowIDLs = rowIDs.split("#");
	logger.info("rowIDLs"+rowIDLs[0]);
	logger.info("rowIDLslength"+rowIDLs.length);
	for (int i = 0; i < rowIDLs.length; i++) {
	    if(rowIDLs[i].length()>0){
		String rowID="";
		if(rowIDLs[i].indexOf("#")!=-1){
		 rowID=rowIDLs[i].replaceAll("#", "");
		}else{
		    rowID=rowIDLs[i];
		}
	    lcVo.setRowID(rowID);
	    deptProcess(con, lcVo);
	    }
	}
	lcVo.setMsg(keyConts.okMsg);
	return lcVo.getMsg();
    }

    /**
     * 主管請假判斷流程
     */
    public static final String deptProcess(Connection con, leaveCardVO lcVo) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	String count = DBUtil.queryDBField(con, SqlUtil.queryLeaveCardProcess(lcVo), "count");
	// logger.info("count: " + count );
	if (count.equals("0")) {
	    editProcessRO edPR = DBUtil.getProcessIDData(lcVo.getRowID(), con);
	    
	    // 查詢流程
	    if (lcVo.getStatus().equals("G")) {// 組長
		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0")
			&& edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")) { 
		    logger.info("之後沒有審核");
		    lcVo.setLeaveApply("1");// 請假完成
		    lcVo.setNextStatus("X");// 請假完成
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL1().equals("1")) { 
		    logger.info("之後單位主管審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("U");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("1")) { 
		    logger.info("之後部門主管審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("D");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")) { 
		    logger.info("之後經理審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("L");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")&& edPR.getSingRoleL4().equals("1")) { 
		    logger.info("之後副總審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
	    }
	    // 查詢流程
	    if (lcVo.getStatus().equals("D")) {// 部門主管

		logger.info("部門主管判斷流程 ");
		if (edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		    logger.info("之後沒有審核");
		    lcVo.setLeaveApply("1");// 請假完成
		    lcVo.setNextStatus("X");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")) { // 跳過經理
		    logger.info("跳過經理");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL3().equals("1") ) { // 之後經理副總審核
		    logger.info("之後經理副總審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("L");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		
	    }
	    if (lcVo.getStatus().equals("U")) {// 單位主管

		if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")
			&& edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		    logger.info("之後沒有審核");
		    lcVo.setLeaveApply("1");// 請假完成
		    lcVo.setNextStatus("X");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("0")
			&& edPR.getSingRoleL4().equals("0")) { // 部門主管審核
		    logger.info("部門主管審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("D");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1")
			&& edPR.getSingRoleL4().equals("0")) { // 部門主管審核
		    logger.info("部門主管審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("D");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1")
			&& edPR.getSingRoleL4().equals("1")) { // 部門主管審核
		    logger.info("部門主管審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("D");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")
			&& edPR.getSingRoleL4().equals("0")) { // 經理審核
		    logger.info("經理審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("L");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")
			&& edPR.getSingRoleL4().equals("1")) { // 經理副總審核
		    logger.info("經理副總審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("L");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")
			&& edPR.getSingRoleL4().equals("1")) { // 副總
		    logger.info("副總審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
	    }

	    if (lcVo.getStatus().equals("L")) {// 經理
		if (edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		    logger.info("之後沒有審核");
		    lcVo.setLeaveApply("1");// 請假完成
		    lcVo.setNextStatus("X");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL4().equals("1")) { // 副總審核
		    logger.info("副總審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
	    }

	    if (lcVo.getStatus().equals("B")) {// 副總
		logger.info("副總判斷流程 ");
		lcVo.setLeaveApply("1");// 請假完成
		    lcVo.setNextStatus("X");
		if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
		    lcVo.setMsg(keyConts.okMsg);
		}
	    }
	} else {
	    // 三天以上查詢狀態
	    editProcessRO edPR = DBUtil.getProcessIDData(lcVo.getRowID(), con);

	    
	    if (lcVo.getStatus().equals("G")) {// 組長
	  		if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0")
	  			&& edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")) { 
	  		    logger.info("之後沒有審核");
	  		    lcVo.setLeaveApply("1");// 請假完成
	  		    lcVo.setNextStatus("X");
	  		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
	  			lcVo.setMsg(keyConts.okMsg);
	  		    }
	  		}
	  		if (edPR.getSingRoleL1().equals("1")) { 
			    logger.info("之後單位主管審核");
			    lcVo.setLeaveApply("0");// 請假完成
			    lcVo.setNextStatus("U");
			    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
				lcVo.setMsg(keyConts.okMsg);
			    }
			}
			if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("1")) { 
			    logger.info("之後部門主管審核");
			    lcVo.setLeaveApply("0");// 請假完成
			    lcVo.setNextStatus("D");
			    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
				lcVo.setMsg(keyConts.okMsg);
			    }
			}
			if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")) { 
			    logger.info("之後經理審核");
			    lcVo.setLeaveApply("0");// 請假完成
			    lcVo.setNextStatus("L");
			    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
				lcVo.setMsg(keyConts.okMsg);
			    }
			}
			if (edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")&& edPR.getSingRoleL4().equals("1")) { 
			    logger.info("之後副總審核");
			    lcVo.setLeaveApply("0");// 請假完成
			    lcVo.setNextStatus("B");
			    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
				lcVo.setMsg(keyConts.okMsg);
			    }
			}
	  	    }
	    
	    // 查詢流程
	    if (lcVo.getStatus().equals("D")) {// 部門主管

		logger.info("部門主管判斷流程 ");
		if (edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		    logger.info("之後沒有審核");
		    lcVo.setLeaveApply("1");// 請假完成
		    lcVo.setNextStatus("X");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")) { // 跳過經理
		    logger.info("跳過經理");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")) { // 之後經理副總審核
		    logger.info("之後經理副總審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")) { // 之後經理審核
		    logger.info("之後經理審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("L");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
	    }
	    if (lcVo.getStatus().equals("U")) {// 單位主管

		if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")
			&& edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		    logger.info("之後沒有審核");
		    lcVo.setLeaveApply("1");// 請假完成
		    lcVo.setNextStatus("X");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("0")
			&& edPR.getSingRoleL4().equals("0")) { // 部門主管審核
		    logger.info("部門主管審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("D");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1")
			&& edPR.getSingRoleL4().equals("0")) { // 部門主管審核
		    logger.info("部門主管審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("D");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1")
			&& edPR.getSingRoleL4().equals("1")) { // 部門主管審核
		    logger.info("部門主管審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("D");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")
			&& edPR.getSingRoleL4().equals("0")) { // 經理審核
		    logger.info("經理審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("L");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")
			&& edPR.getSingRoleL4().equals("1")) { // 經理副總審核
		    logger.info("經理副總審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
		if (edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")
			&& edPR.getSingRoleL4().equals("1")) { // 副總
		    logger.info("副總審核");
		    lcVo.setLeaveApply("0");// 請假未完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }

		}
	    }

	    if (lcVo.getStatus().equals("L")) {// 經理
		if (edPR.getSingRoleL4().equals("0")) { // 之後沒有審核
		    logger.info("之後沒有審核");
		    lcVo.setLeaveApply("1");// 請假完成
		    lcVo.setNextStatus("X");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL4().equals("1")) { // 副總審核
		    logger.info("副總審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		    /** 直接寄信 **/
		}
	    }

	    if (lcVo.getStatus().equals("B")) {// 副總
		logger.info("副總判斷流程 ");
		lcVo.setLeaveApply("1");// 請假完成
		   lcVo.setNextStatus("X");
		if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
		    lcVo.setMsg(keyConts.okMsg);
		}
	    }
	}

	return lcVo.getMsg();
    }

    
    /**
     * 人事請假判斷流程
     */
    public static final String Process(Connection con, leaveCardVO lcVo) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	String count = DBUtil.queryDBField(con, SqlUtil.queryLeaveCardProcess(lcVo), "count");
	// logger.info("count: " + count );
	//lcVo.setStatus("T");
	if (count.equals("0")) {
	    editProcessRO edPR = DBUtil.getProcessIDData(lcVo.getRowID(), con);
	    
	    // 查詢流程
	    if (lcVo.getStatus().equals("T")) {// 人事通過
		if (edPR.getSingRoleL0().equals("1") ) { 
		   
		    lcVo.setLeaveApply("0");// 
		    lcVo.setNextStatus("G");//下一個U
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("1")) { 
		    logger.info("之後單位主管審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("U");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("1")) { 
		    logger.info("之後部門主管審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("D");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("0") 
			&& edPR.getSingRoleL2().equals("0")&& edPR.getSingRoleL3().equals("1") ) { 
		    logger.info("之後經理審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("L");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
		if (edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("0") 
			&& edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")&& edPR.getSingRoleL4().equals("1")) { 
		    logger.info("之後副總審核");
		    lcVo.setLeaveApply("0");// 請假完成
		    lcVo.setNextStatus("B");
		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
			lcVo.setMsg(keyConts.okMsg);
		    }
		}
	    }
	  
	    
	} else {
	    // 三天以上查詢狀態
	    editProcessRO edPR = DBUtil.getProcessIDData(lcVo.getRowID(), con);

	    
	    if (lcVo.getStatus().equals("T")) {// 組長
        		if (edPR.getSingRoleL0().equals("1")) { 
        		    logger.info("之後組長審核");
        		    lcVo.setLeaveApply("0");// 請假完成
        		    lcVo.setNextStatus("G");
        		    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
        			lcVo.setMsg(keyConts.okMsg);
        		    }
        		}
	  		if (edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("1")) { 
			    logger.info("之後單位主管審核");
			    lcVo.setLeaveApply("0");// 請假完成
			    lcVo.setNextStatus("U");
			    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
				lcVo.setMsg(keyConts.okMsg);
			    }
			}
			if (edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("1")) { 
			    logger.info("之後部門主管審核");
			    lcVo.setLeaveApply("0");// 請假完成
			    lcVo.setNextStatus("D");
			    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
				lcVo.setMsg(keyConts.okMsg);
			    }
			}
			if (edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("0") && edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")) { 
			    logger.info("之後經理審核");
			    lcVo.setLeaveApply("0");// 請假完成
			    lcVo.setNextStatus("L");
			    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
				lcVo.setMsg(keyConts.okMsg);
			    }
			}
			if (edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("0") 
				&& edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")&& edPR.getSingRoleL4().equals("1")) { 
			    logger.info("之後副總審核");
			    lcVo.setLeaveApply("0");// 請假完成
			    lcVo.setNextStatus("B");
			    if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con)) {
				lcVo.setMsg(keyConts.okMsg);
			    }
			}
	  	    }
	}
	
	return lcVo.getMsg();
    }

    
    
    /**
     * 主管請假判斷流程-立刻寄信通知
     */
    public static void deptProcessEmail(Connection con, leaveCardVO lcVo) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	logger.info("deptProcessEmail lcVo " + lcVo);
	editProcessRO edPR = DBUtil.getProcessIDData(lcVo.getRowID(), con);
	logger.info("deptProcessEmail edPR " + edPR.toString());
	// 查寄信人與主管等級差異-只差一級才能馬上寄
	if (lcVo.getSearchEmployeeNo().equals("") || lcVo.getSearchEmployeeNo().equals("0")) {
	    lcVo.setSearchEmployeeNo(DBUtil.queryDBField(con, SqlUtil.queryEPID(lcVo.getRowID()), "EPID"));
	}
	processUserRO ru = DBUtil.getEmpUser(con, lcVo.getSearchEmployeeNo());
	logger.info("立刻寄信通知 SearchRole " + ru.getROLE());

	boolean checkSing = false;// 是否為上一級
	boolean checkEmail = false;// 是否有信箱
	String SingPeople = "", toEamil = "";// 寄信主管
	// 員工 寄送  組長
	if ((ru.getROLE().equals(keyConts.EmpRoleE) || ru.getROLE().equals(keyConts.EmpRoleG) )&& edPR.getSingRoleL0().equals("1")) {

	    SingPeople = edPR.getSingRoleL0EP();
	    checkSing = true;
	}
	// 員工 寄送 單位主管
	if ((ru.getROLE().equals(keyConts.EmpRoleE) || ru.getROLE().equals(keyConts.EmpRoleG)) && edPR.getSingRoleL0().equals("0") && edPR.getSingRoleL1().equals("1")) {

	    SingPeople = edPR.getSingRoleL1EP();
	    checkSing = true;
	}
	/** 差兩層 **/
	// 員工 直接寄送 部門主管
	if ((ru.getROLE().equals(keyConts.EmpRoleE )  || ru.getROLE().equals(keyConts.EmpRoleG)) && edPR.getSingRoleL1().equals("0")
		&& edPR.getSingRoleL2().equals("1")) {
	    // SingPeople = edPR.getSingRoleL2EP();

	    // checkSing = true;
	}
	/** 差三層 **/
	// 員工 直接寄送 經理
	if ((ru.getROLE().equals(keyConts.EmpRoleE) || ru.getROLE().equals(keyConts.EmpRoleG) ) && edPR.getSingRoleL1().equals("0")
		&& edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1")) {
	    // SingPeople=edPR.getSingRoleL3EP();

	    // checkSing=true;
	}
	/** 差四層 **/
	// 員工 直接寄送 副總
	if ((ru.getROLE().equals(keyConts.EmpRoleE) || ru.getROLE().equals(keyConts.EmpRoleG) )&& edPR.getSingRoleL1().equals("0")
		&& edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0")
		&& edPR.getSingRoleL4().equals("1")) {
	    // SingPeople=edPR.getSingRoleL4EP();
	    // checkSing=true;
	}
	if (ru.getROLE().equals(keyConts.EmpRoleU) && edPR.getSingRoleL2().equals("1")) {
	    SingPeople = edPR.getSingRoleL2EP();
	    checkSing = true;
	}
	/** 差兩層 **/

	if (ru.getROLE().equals(keyConts.EmpRoleU) && edPR.getSingRoleL2().equals("0")
		&& edPR.getSingRoleL3().equals("1")) {

	}
	/** 差三層 **/
	if (ru.getROLE().equals(keyConts.EmpRoleU) && edPR.getSingRoleL2().equals("0")
		&& edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")) {

	}
	if (ru.getROLE().equals(keyConts.EmpRoleD) && edPR.getSingRoleL3().equals("1")) {
	    SingPeople = edPR.getSingRoleL3EP();

	    checkSing = true;
	}

	/** 差兩層 **/
	if (ru.getROLE().equals(keyConts.EmpRoleD) && edPR.getSingRoleL3().equals("0")
		&& edPR.getSingRoleL4().equals("1")) {

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
	    ew.setSUBJECT(keyConts.EmailLeaveSubject);

	    String USER = DBUtil.queryDBField(con, SqlUtil.queryEmpName(SingPeople), "EMPLOYEE");
	    logger.info("主管名稱:" + USER + "email:" + ew.getTO());
	    ew.setUSER(USER);

	    String rUserNo = DBUtil.queryDBField(con, SqlUtil.queryEmpID(lcVo.getSearchEmployeeNo()), "EMPLOYEENO");
	    lcVo.setSearchEmployeeNo(rUserNo);
	    String rUser = DBUtil.queryDBField(con, SqlUtil.queryEmpName(rUserNo), "EMPLOYEE");
	    ew.setCONTENT(EmailUtil.getLeaveEmailTemplate(USER, lcVo.getSearchEmployeeNo(), rUser));

	    // logger.info("getCONTENT:"+ew.getCONTENT());
	    String Emailmsg = EmailUtil.sendmail(ew);
	    logger.info("此主管工號:" + SingPeople + "寄信 " + Emailmsg);
	    ew.setEMP(SingPeople);
	    if (Emailmsg.equals("ok")) {
		ew.setSENTSTATUS("0");// 正常寄信
	    } else {
		ew.setSENTSTATUS("1");// 寄信錯誤
	    }
	    ew.setEMAILSTATUS("0");// 直接寄信
	    // 寄信紀錄-EMAIL表

	    DBUtil.workLateOperationSql(con, SqlUtil.insterVnEmail(ew));
	} else {
	    logger.info("此主管無email 不寄信");
	}

    }

    /**
     * 主管請假判斷流程-定時寄信通知
     */
    public static void deptProcessTimeEmail() throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);

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
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --員工請假組長審過等待單位主管審查-END- **/
	
	/** -特殊-員工組長審過跳到部門主管審查-START- **/
	logger.info("員工組長審過跳到部門主管審查");
	lc.setSearchRole("E");
	lc.setStatus("G");
	lc.setReturnMsg(" and SINGROLEL1='0'  ");
	lc.setNote(keyConts.SingRoleL2EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工組長審過跳到部門主管審查-END- **/
	
	/** -特殊-員工組長審過跳到經理審查-START- **/
	logger.info("員工組長審過跳到部門主管審查");
	lc.setSearchRole("E");
	lc.setStatus("G");
	lc.setReturnMsg(" and SINGROLEL1='0'   and SINGROLEL2='0' ");
	lc.setNote(keyConts.SingRoleL3EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工組長審過跳到經理審查-END- **/
	
	/** -特殊-員工組長審過跳到副總審查-START- **/
	logger.info("員工組長審過跳到部門主管審查");
	lc.setSearchRole("E");
	lc.setStatus("G");
	lc.setReturnMsg(" and SINGROLEL1='0'   and SINGROLEL2='0' and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工組長審過跳到副總審查-END- **/
	
	
	/** --員工請假單位主管審過等待部門主管審查有幾個主管-START- **/
	logger.info("員工請假單位主管審過等待部門主管審查");
	lc.setSearchRole("E");
	lc.setStatus("U");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL2EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --員工請假單位主管審過等待部門主管審查有幾個主管-END- **/
	logger.info("=================================");
	/** --員工部門主管審過等待經理審查-START- **/
	logger.info("員工部門主管審過等待經理審查");
	lc.setSearchRole("E");
	lc.setStatus("D");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL3EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	logger.info("=================================");
	/** --特殊-員工單位主管審過跳到經理審查-START- **/
	logger.info("員工單位主管審過跳到經理審查");
	lc.setSearchRole("E");
	lc.setStatus("U");
	lc.setReturnMsg("  and SINGROLEL2='0' ");
	lc.setNote(keyConts.SingRoleL3EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工單位主管審過跳到經理審查-END- **/
	logger.info("=================================");
	/** --特殊-員工單位主管審過跳到副總審查-START- **/
	logger.info("員工單位主管審過跳到副總審查");
	lc.setSearchRole("E");
	lc.setStatus("U");
	lc.setReturnMsg("   and SINGROLEL2='0'  and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工單位主管審過跳到副總審查-END- **/

	logger.info("=================================");
	/** --特殊-員工跳到副總審查-START- **/
	logger.info("員工跳到副總審查");
	lc.setSearchRole("E");
	lc.setStatus("T");
	lc.setReturnMsg("  and SINGROLEL1='0' and SINGROLEL2='0' and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工跳到副總審查-END- **/

	logger.info("=================================");
	/** --員工經理審過等待副總審查有幾人-START- **/
	logger.info("員工經理審過等待副總審查");
	lc.setSearchRole("E");
	lc.setStatus("L");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --員工經理審過等待副總審查有幾人-END- **/
	logger.info("=================================");
	
	
	/** --組長請假單位主管審過等待部門主管審查有幾個主管-START- **/
	logger.info("組長請假單位主管審過等待部門主管審查");
	lc.setSearchRole("G");
	lc.setStatus("U");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL2EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --組長請假單位主管審過等待部門主管審查有幾個主管-END- **/
	logger.info("=================================");
	/** --組長部門主管審過等待經理審查-START- **/
	logger.info("組長部門主管審過等待經理審查");
	lc.setSearchRole("G");
	lc.setStatus("D");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL3EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	logger.info("=================================");
	/** --特殊-組長單位主管審過跳到經理審查-START- **/
	logger.info("組長單位主管審過跳到經理審查");
	lc.setSearchRole("G");
	lc.setStatus("U");
	lc.setReturnMsg("  and SINGROLEL2='0' ");
	lc.setNote(keyConts.SingRoleL3EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-組長單位主管審過跳到經理審查-END- **/
	logger.info("=================================");
	/** --特殊-組長單位主管審過跳到副總審查-START- **/
	logger.info("組長單位主管審過跳到副總審查");
	lc.setSearchRole("G");
	lc.setStatus("U");
	lc.setReturnMsg("   and SINGROLEL2='0'  and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工單位主管審過跳到副總審查-END- **/

	logger.info("=================================");
	/** --特殊-員工跳到副總審查-START- **/
	logger.info("組長跳到副總審查");
	lc.setSearchRole("G");
	lc.setStatus("T");
	lc.setReturnMsg("  and SINGROLEL1='0' and SINGROLEL2='0' and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-員工跳到副總審查-END- **/

	logger.info("=================================");
	/** --單位主管申請 部門主管審過等待經理審-START- **/
	logger.info("單位主管申請 部門主管審過等待經理審");
	lc.setSearchRole("U");
	lc.setStatus("D");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL3EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --單位主管申請 部門主管審過等待經理審-END- **/
	logger.info("=================================");
	/** --單位主管申請 部門主管審過等待副總審-START- **/
	logger.info("單位主管申請 部門主管審過等待副總審");
	lc.setSearchRole("U");
	lc.setStatus("L");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --單位主管申請 部門主管審過等待副總審-END- **/
	logger.info("=================================");
	/** --特殊-單位主管跳過部門等待經理審-START- **/
	logger.info("特殊-單位主管跳過部門等待經理審");
	lc.setSearchRole("U");
	lc.setStatus("T");
	lc.setReturnMsg("   and SINGROLEL2='0' ");
	lc.setNote(keyConts.SingRoleL3EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-單位主管跳過部門等待經理審-END- **/
	logger.info("=================================");
	/** --特殊-單位主管直接副總審-START- **/
	logger.info("特殊-單位主管直接副總審");
	lc.setSearchRole("U");
	lc.setStatus("T");
	lc.setReturnMsg("   and SINGROLEL2='0'  and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --特殊-單位主管直接副總審-END- **/
	logger.info("=================================");

	/** --部門主管申請 經理審過等待副總審-START- **/
	logger.info("部門主管申請 經理審過等待副總審");
	lc.setSearchRole("D");
	lc.setStatus("L");
	lc.setReturnMsg("");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
	/** --部門主管申請 經理審過等待副總審-END- **/
	logger.info("=================================");
	/** --特殊-部門主管直接副總審-START- **/
	logger.info("特殊-部門主管直接副總審");
	lc.setSearchRole("D");
	lc.setStatus("T");
	lc.setReturnMsg("    and SINGROLEL3='0' ");
	lc.setNote(keyConts.SingRoleL4EP);
	forLeaveSendEmail(lc, con, logger, toEamil, checkEmail);
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
	Logger logger = lu.initLog4j(leaveCardDAO.class);
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
    public static void setEmailSend(Connection con, String toEamil, String SingEp, List<leaveEmailListRO> ler)
	    throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	configRO ra = new configRO();
	EmailWO ew = DBUtil.queryConfigRow(con, SqlUtil.getEmailConfig(), ra);
	ew.setTO(toEamil);
	ew.setSUBJECT(keyConts.EmailLeaveSubject);

	String USER = DBUtil.queryDBField(con, SqlUtil.queryEmpName(SingEp), "EMPLOYEE");
	logger.info("主管名稱:" + USER + "email:" + ew.getTO());
	ew.setUSER(USER);
	ew.setCONTENT(EmailUtil.getLeaveEmailTemplateList(ler, USER));

	String Emailmsg = EmailUtil.sendmail(ew);
	logger.info("此主管工號:" + SingEp + "寄信 " + Emailmsg);
	ew.setEMP(SingEp);
	if (Emailmsg.equals("ok!")) {
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
    public static void forLeaveSendEmail(leaveCardVO lc, Connection con, Logger logger, String toEamil,
	    boolean checkEmail) throws Exception {

	logger.info("請假 定時寄信通知 queryEmailSingep " + SqlUtil.queryEmailSingep(lc));
	List<supervisorRO> lsr = DBUtil.queryEmailSingep(con, SqlUtil.queryEmailSingep(lc));
	logger.info("請假 定時寄信通知 lsr " + lsr.size());

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
		logger.info("請假 定時寄信通知 querySendEmailLeaveList " + SqlUtil.querySendEmailLeaveList(lc));
		List<leaveEmailListRO> ler = DBUtil.querySendEmailLeaveList(con, SqlUtil.querySendEmailLeaveList(lc));
		logger.info("ler " + ler);
		if (ler.size() > 0) {
		    setEmailSend(con, toEamil, SingEp, ler);
		}
	    }
	}
    }

    /**
     * 請假申請時檢查開始結束時間有無打卡紀錄 傳入開始結束時間 員工號 傳出ture 已打卡 false 未打卡
     */
    public static final boolean checkCadeTime(leaveCardVO lcVo, Connection con) throws Exception {
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	boolean flag = false ;
	
	/** 同一天 **/
	if (lcVo.getStartLeaveDate().equals(lcVo.getEndLeaveDate())) {
	    flag =checkCadeDay( lcVo,  con);

	} else {/** 不同天 **/
	    List<String> days = DateUtil.getDatesBetweenTwoDate(lcVo.getStartLeaveDate(), lcVo.getEndLeaveDate());
	    for(int i=0;i<days.size();i++){
		leaveCardVO tempVO =new leaveCardVO();
		
		logger.info("不同天 " + days.get(i));
		tempVO.setStartLeaveDate(days.get(i));
		tempVO.setStartLeaveTime(lcVo.getStartLeaveTime());
		tempVO.setStartLeaveMinute(lcVo.getStartLeaveMinute());
		tempVO.setEndLeaveDate(lcVo.getEndLeaveDate());
		tempVO.setEndLeaveTime(lcVo.getEndLeaveTime());
		tempVO.setEndLeaveMinute(lcVo.getEndLeaveMinute());
		tempVO.setSearchEmployeeNo(lcVo.getSearchEmployeeNo());
		
		flag =checkCadeDay( tempVO,  con);
		if(flag){
		    break;
		}
	    }
	

	}

	return flag;
    }

    public static final boolean checkCadeDay(leaveCardVO lcVo, Connection con) throws Exception {

	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(leaveCardDAO.class);
	boolean flag = true, BworkFDate = false, BworkEDate = false;
	String StartCardTime = "", EndCardTime = "";
	     logger.info( "getOnlyWorkTime : "+SqlUtil.getOnlyWorkTime(lcVo));
	     List<workDateRO> wr = DBUtil.queryworkDateList(con, SqlUtil.getOnlyWorkTime(lcVo));
	     if(wr.size()>0){
        	     logger.info( "getWorkFDate length :"+wr.get(0).getWorkFDate().length());
        	     logger.info( "getWorkEDate length:"+ wr.get(0).getWorkEDate().length());
        	    
        	     logger.info( "getWorkFDate : "+wr.get(0).getWorkFDate());
        	     logger.info( "getWorkEDate: "+wr.get(0).getWorkEDate());
        
        	    StartCardTime = wr.get(0).getWorkFDate().replaceAll("\\s*", "");
        	    EndCardTime = wr.get(0).getWorkEDate().replaceAll("\\s*", "");
        
        	    logger.info( "StartCardTime : "+StartCardTime);
        	    logger.info( "EndCardTime: "+EndCardTime);
	     }else{
		 BworkFDate = false;
		 BworkEDate = false;
	     }
	    if (StartCardTime == null || StartCardTime.isEmpty()) {
		BworkFDate = true;
	    }
	    if (EndCardTime == null || EndCardTime.isEmpty()) {
		BworkEDate = true;
	    }
	    logger.info("BworkFDate : " + BworkFDate);
	    logger.info("BworkEDate : " + BworkEDate);
	    /** 空的表示當天未打卡 允許請假 **/
	    if (BworkFDate && BworkEDate) {
		logger.info("上班下班都未打卡 允許請假 ");
		flag = false;
	    } else if (BworkFDate || BworkEDate) {
		logger.info("上班或下班未打卡 允許請假 ");
		/** 上班或下班未打卡 允許請假 **/
		flag = false;
	    } else {
		logger.info("上班下班都打卡 檢查時間 ");
		/** 上班下班都打卡 檢查時間 **/
		final SimpleDateFormat SF = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		StartCardTime = wr.get(0).getWorkFDate().replaceAll("-", "/");
		EndCardTime = wr.get(0).getWorkEDate().replaceAll("-", "/");
		logger.info(" 檢查時間 StartCardTime  : " + StartCardTime);
		logger.info(" 檢查時間 EndCardTime: " + EndCardTime);
		Date[] a = { SF.parse(StartCardTime), SF.parse(EndCardTime) };
		Date[] b = {
			SF.parse(lcVo.getStartLeaveDate() + " " + lcVo.getStartLeaveTime() + ":"
				+ lcVo.getStartLeaveMinute()),
			SF.parse(lcVo.getEndLeaveDate() + " " + lcVo.getEndLeaveTime() + ":"
				+ lcVo.getEndLeaveMinute()) };
		flag = DateUtil.isContainEnd(a, b);
		logger.info(" Date[] b " + b);
		logger.info("flag " + flag);
	    }
	
	    return flag;
    }
    
    /**
	 * 請假先檢查是否有設定流程
	 * @param con
	 * @param lcVo
	 * @param Day
	 * @return
	 * @throws Exception 
	 */
	public static final 	String  getPersonalProcess(Connection con, leaveCardVO lcVo) throws Exception
	{
		//用工號查出部門單位角色
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(DBUtil.class);
		HtmlUtil hu = new HtmlUtil();
		processUserRO ra=new processUserRO();
		String sql = "";
		sql = hu.gethtml(sqlConsts.sql_queryUserData);
		sql = sql.replace("<EMPLOYEENO/>", lcVo.getSearchEmployeeNo());
	
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
		logger.info("queryUserData:"+sql);
		processUserRO ru=(processUserRO)leo.get(0);

		float  dayCount=Float.parseFloat(lcVo.getDayCount());
		String STATUS="0";//三天以下
		if(dayCount>=3){
			 STATUS="1";//三天以上
		}
		ru.setSTATUS(STATUS);
		int count=0;
		
		String COUNT ="";

		processCheckRO  prow=new processCheckRO();
		if(ru.getROLE().equals("E") || ru.getROLE().equals("U") || ru.getROLE().equals("G") ) {//多組長
			logger.info("檢查此部門或單位或小組:"+SqlUtil.queryDeptLeaveCardCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryDeptLeaveCardCount(ru), "COUNT");
			count=Integer.valueOf(COUNT);
			if(count>0){
    			/**檢查欄位**/
    			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
    			logger.info("processOVTable sql="+SqlUtil.queryProcessCheck(ru,keyConts.processLETable));
    			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processLETable), new processCheckRO());
    			if(cr.size()>0){
    			    prow=cr.get(0);
    			}
			}
			
		}else{
			logger.info("檢查此部門:"+SqlUtil.queryLeavePreossCount(ru));
			COUNT = DBUtil.queryDBField(con,SqlUtil.queryLeavePreossCount(ru), "COUNT");
			count=Integer.valueOf(COUNT);
			if(count>0){
    			/**檢查欄位**/
    			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
    			logger.info("processOVTable sql="+SqlUtil.queryProcessCheck(ru,keyConts.processLETable));
    			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processLETable), new processCheckRO());
    			if(cr.size()>0){
    			    prow=cr.get(0);
    			}
			}
		}
		if(count>0){
			
			/**檢查伍個欄位有無值無值一樣不能過**/
			if(prow.getSINGROLEL0().equals("0") && prow.getSINGROLEL1().equals("0") && prow.getSINGROLEL2().equals("0")
				&& prow.getSINGROLEL3().equals("0") && prow.getSINGROLEL4().equals("0")){
			    count=0;
			}
		}
		String msg="o";
		
		if(count==0){
			msg="x";
			//  logger.info("此員工組別為:"+ru.getGROUP());
			/**增加檢查此員工有無設定組別 有組別-檢查有無設定流程**/
			if(ru.getROLE().equals("E") &&!ru.getGROUP().equals("0")){
			    logger.info("此員工為組員組別為:"+ru.getGROUP());
			    msg="g#"+ru.getGROUP(); 
			}
		}
	
		
		
		
		return msg;
	}
	
	
	  /**
		 * 查出下一個流程為何
		 * @param con
		 * @param lcVo
		 * @param Day
		 * @return
		 * @throws Exception 
		 */
		public static final 	String  getNextProcess(Connection con, leaveCardVO lcVo) throws Exception
		{
			//用工號查出部門單位角色
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(DBUtil.class);
			HtmlUtil hu = new HtmlUtil();
			processUserRO ra=new processUserRO();
			String sql = "";
			sql = hu.gethtml(sqlConsts.sql_queryUserData);
			sql = sql.replace("<EMPLOYEENO/>", lcVo.getSearchEmployeeNo());
		
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
			logger.info("queryUserData:"+sql);
			processUserRO ru=(processUserRO)leo.get(0);

			float  dayCount=Float.parseFloat(lcVo.getDayCount());
			String STATUS="0";//三天以下
			if(dayCount>=3){
				 STATUS="1";//三天以上
			}
			ru.setSTATUS(STATUS);
			int count=0;
			
			String COUNT ="";

			processCheckRO  prow=new processCheckRO();
			if(ru.getROLE().equals("E") || ru.getROLE().equals("U") || ru.getROLE().equals("G") ) {//多組長
				logger.info("檢查此部門或單位或小組:"+SqlUtil.queryDeptLeaveCardCount(ru));
				COUNT = DBUtil.queryDBField(con,SqlUtil.queryDeptLeaveCardCount(ru), "COUNT");
				count=Integer.valueOf(COUNT);
				if(count>0){
	    			/**檢查欄位**/
	    			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
	    			logger.info("processOVTable sql="+SqlUtil.queryProcessCheck(ru,keyConts.processLETable));
	    			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processLETable), new processCheckRO());
	    			if(cr.size()>0){
	    			    prow=cr.get(0);
	    			}
				}
				
			}else{
				logger.info("檢查此部門:"+SqlUtil.queryLeavePreossCount(ru));
				COUNT = DBUtil.queryDBField(con,SqlUtil.queryLeavePreossCount(ru), "COUNT");
				count=Integer.valueOf(COUNT);
				if(count>0){
	    			/**檢查欄位**/
	    			DBUtilTList<processCheckRO> pr=new DBUtilTList<processCheckRO>();
	    			logger.info("processOVTable sql="+SqlUtil.queryProcessCheck(ru,keyConts.processLETable));
	    			List<processCheckRO> cr=pr.queryTList(con, SqlUtil.queryProcessCheck(ru,keyConts.processLETable), new processCheckRO());
	    			if(cr.size()>0){
	    			    prow=cr.get(0);
	    			}
				}
			}
			if(count>0){
				
				/**檢查伍個欄位有無值無值一樣不能過**/
				if(prow.getSINGROLEL0().equals("0") && prow.getSINGROLEL1().equals("0") && prow.getSINGROLEL2().equals("0")
					&& prow.getSINGROLEL3().equals("0") && prow.getSINGROLEL4().equals("0")){
				    count=0;
				}
			}
			String msg="o";
			
			if(count==0){
				msg="x";
				//  logger.info("此員工組別為:"+ru.getGROUP());
				/**增加檢查此員工有無設定組別 有組別-檢查有無設定流程**/
				if(ru.getROLE().equals("E") &&!ru.getGROUP().equals("0")){
				    logger.info("此員工為組員組別為:"+ru.getGROUP());
				    msg="g#"+ru.getGROUP(); 
				}
			}
		
			
			
			
			return msg;
		}
	
	
	
}
