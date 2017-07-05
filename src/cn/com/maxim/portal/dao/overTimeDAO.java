package cn.com.maxim.portal.dao;

import java.io.File;
import java.sql.Connection;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import cn.com.maxim.DB.DBManager;
import cn.com.maxim.portal.attendan.ro.editProcessRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.attendan.vo.overTimeVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.potral.consts.keyConts;

public class overTimeDAO
{
	/**
	 * 主管請假判斷流程
	 */
	public static final String deptProcess(Connection con,overTimeVO otVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(leaveCardDAO.class);
	
			//正常加班流程
			editProcessRO edPR=DBUtil.getProcessOverIDData(otVo,con);
			
			if(otVo.getStatus().equals("U")){//單位主管
				
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")){ //之後沒有審核
					logger.info("之後沒有審核");
					otVo.setLeaveApply("1");//請假完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")){ //部門主管審核
					logger.info("部門主管審核");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //部門主管審核
					logger.info("部門主管經理審核");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //部門主管審核
					logger.info("部門主管審核");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //經理審核
					logger.info("經理審核");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**存入資料列表寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //經理副總審核
					logger.info("經理副總審核");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**存入資料列表寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")){ //副總
					logger.info("副總審核");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**存入資料列表寄信**/
				}
			}
			//查詢流程
			if(otVo.getStatus().equals("D")){//部門主管
				
				logger.info("部門主管判斷流程 ");
				if(edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("0")){ //之後沒有審核
					logger.info("之後沒有審核");
					otVo.setLeaveApply("1");//請假完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")){ //跳過經理
					logger.info("跳過經理");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**存入資料列表寄信**/
				}
				if(edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //之後經理副總審核
					logger.info("之後經理副總審核");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //之後經理審核
					logger.info("之後經理審核");
					otVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}	
			}
			
			
			if(otVo.getStatus().equals("L")){//經理
				if(edPR.getSingRoleL4().equals("0") ){ //之後沒有審核
					logger.info("之後沒有審核");
					otVo.setLeaveApply("1");//請假完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
				}
				if(edPR.getSingRoleL4().equals("1") ){ //副總審核
					logger.info("副總審核");
					otVo.setLeaveApply("0");//請假完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
			}
			
			if(otVo.getStatus().equals("B")){//副總
					logger.info("副總判斷流程 ");
					otVo.setLeaveApply("1");//請假完成
					if (DBUtil.updateTimeOverSStatus(otVo, con))
					{
						otVo.setMsg(keyConts.okMsg);
					}
			}
		

		return otVo.getMsg();
	}
	/**
	 * 主管請假判斷流程-寄信通知
	 */
	public static void deptProcessEmail(Connection con,leaveCardVO lcVo) throws Exception
	{
	    	Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(leaveCardDAO.class);
		
		//查寄信人與主管等級差異-只差一級才能馬上寄
		
		//查出email資料-此主管無EMAIL就不寄
		
		//馬上寄
		
		//寄信紀錄-EMAIL表 
		
	}
	/**
	 * 主管請假判斷流程-定時寄信通知
	 */
	public static void deptProcessTimeEmail() throws Exception
	{
	    	Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(leaveCardDAO.class);
		SAXBuilder xmlBuilder = new SAXBuilder();
		
		Document doc = xmlBuilder.build(new File("D:\\mxportal\\script\\dba.xml"));
		DBManager dba = new DBManager(doc);
		Connection con=dba.getConnection("VH");

		//先查出當天請假需要寄信主管 查寄信人與主管等級差異-兩級以上定時寄
		logger.info("主管請假判斷流程-定時寄信通知");
		//查出email資料-此主管無EMAIL就不寄
		
		//尚未寄信者寄信
		
		//寄信紀錄-EMAIL表 
		
	}
}
