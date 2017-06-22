package cn.com.maxim.portal.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.editProcessRO;
import cn.com.maxim.portal.attendan.vo.editProcessVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.hr.dem_LeaveCard;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.potral.consts.keyConts;

public class leaveCardDAO
{
	/**
	 * 主管請假判斷流程
	 */
	public static final String deptProcess(Connection con,leaveCardVO lcVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(leaveCardDAO.class);
		String count=DBUtil.queryDBField(con,SqlUtil.queryLeaveCardProcess(lcVo),"count");
		//logger.info("count: " + count );
		if(count.equals("0")){
				lcVo.setLeaveApply("1");//請假完成
				if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
				{
					lcVo.setMsg(keyConts.okMsg);
				}
		}else{
			//三天以上查詢狀態
			editProcessRO edPR=DBUtil.getProcessIDData(lcVo,con);
		
			//查詢流程
			if(lcVo.getStatus().equals("D")){//部門主管
				
				logger.info("部門主管判斷流程 ");
				if(edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")){ //之後沒有審核
					logger.info("之後沒有審核");
					lcVo.setLeaveApply("1");//請假完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")){ //跳過經理
					logger.info("跳過經理");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //之後經理副總審核
					logger.info("之後經理副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //之後經理審核
					logger.info("之後經理審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}	
			}
			if(lcVo.getStatus().equals("U")){//單位主管
				
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")){ //之後沒有審核
					logger.info("之後沒有審核");
					lcVo.setLeaveApply("1");//請假完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")){ //部門主管審核
					logger.info("部門主管審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //部門主管審核
					logger.info("部門主管審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //部門主管審核
					logger.info("部門主管審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //經理審核
					logger.info("經理審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //經理副總審核
					logger.info("經理副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")){ //副總
					logger.info("副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
			}
			
			if(lcVo.getStatus().equals("L")){//經理
				if(edPR.getSingRoleL4().equals("0") ){ //之後沒有審核
					logger.info("之後沒有審核");
					lcVo.setLeaveApply("1");//請假完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL4().equals("1") ){ //副總審核
					logger.info("副總審核");
					lcVo.setLeaveApply("0");//請假完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
				}
			}
			
			if(lcVo.getStatus().equals("B")){//副總
					logger.info("副總判斷流程 ");
					lcVo.setLeaveApply("1");//請假完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
			}
		}
	
		return lcVo.getMsg();
	}
}
