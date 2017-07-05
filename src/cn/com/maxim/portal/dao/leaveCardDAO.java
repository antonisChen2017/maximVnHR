package cn.com.maxim.portal.dao;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import cn.com.maxim.DB.DBManager;
import cn.com.maxim.portal.UserDescriptor;
import cn.com.maxim.portal.attendan.ro.editProcessRO;
import cn.com.maxim.portal.attendan.vo.editProcessVO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.hr.dem_LeaveCard;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.EmailUtil;
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
					/**存入資料列表寄信**/
					
				}
				if(edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //之後經理副總審核
					logger.info("之後經理副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //之後經理審核
					logger.info("之後經理審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
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
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //部門主管審核
					logger.info("部門主管審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //部門主管審核
					logger.info("部門主管審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //經理審核
					logger.info("經理審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //經理副總審核
					logger.info("經理副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")){ //副總
					logger.info("副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
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
					/**直接寄信**/
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
					/**存入資料列表寄信**/
				}
				if(edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //之後經理副總審核
					logger.info("之後經理副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //之後經理審核
					logger.info("之後經理審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
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
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //部門主管審核
					logger.info("部門主管審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("1") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //部門主管審核
					logger.info("部門主管審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**直接寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("0")){ //經理審核
					logger.info("經理審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**存入資料列表寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("1") && edPR.getSingRoleL4().equals("1")){ //經理副總審核
					logger.info("經理副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**存入資料列表寄信**/
				}
				if(edPR.getSingRoleL2().equals("0") && edPR.getSingRoleL3().equals("0") && edPR.getSingRoleL4().equals("1")){ //副總
					logger.info("副總審核");
					lcVo.setLeaveApply("0");//請假未完成
					if (DBUtil.updateSql(SqlUtil.updateLcStatus(lcVo), con))
					{
						lcVo.setMsg(keyConts.okMsg);
					}
					/**存入資料列表寄信**/
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
					/**直接寄信**/
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
	
	
	/**
	 * 主管請假判斷流程-立刻寄信通知
	 */
	public static void deptProcessEmail(Connection con,leaveCardVO lcVo) throws Exception
	{
	    	Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(leaveCardDAO.class);
		editProcessRO edPR=DBUtil.getProcessIDData(lcVo,con);
		//查寄信人與主管等級差異-只差一級才能馬上寄
		logger.info("立刻寄信通知 SearchRole "+lcVo.getSearchRole());
		logger.info("立刻寄信通知 Status "+lcVo.getStatus());
		boolean checkSing=false;//是否為上一級
		boolean checkEmail=false;//是否有信箱
		String SingPeople="",SwLeave="",Eamil="";//寄信主管
		//員工 寄送 單位主管
		if(lcVo.getSearchRole().equals(keyConts.EmpRoleE) && edPR.getSingRoleL1().equals("1")){
		    SingPeople=edPR.getSingRoleL1EP();
		    SwLeave="1";
		    checkSing=true;
		}
		if(lcVo.getSearchRole().equals(keyConts.EmpRoleU) && edPR.getSingRoleL2().equals("1")){
		    SingPeople=edPR.getSingRoleL2EP();
		    SwLeave="2";
		    checkSing=true;
		}
		if(lcVo.getSearchRole().equals(keyConts.EmpRoleD) && edPR.getSingRoleL3().equals("1")){
		    SingPeople=edPR.getSingRoleL3EP();
		    SwLeave="3";
		    checkSing=true;
		}
		if(lcVo.getSearchRole().equals(keyConts.EmpRoleM) && edPR.getSingRoleL4().equals("1")){
		    SingPeople=edPR.getSingRoleL4EP();
		    SwLeave="4";
		    checkSing=true;
		}
		//查出email資料-此主管無EMAIL就不寄
		if(checkSing){
		    logger.info("只差一級:檢查此主管有無email");
		    Eamil= DBUtil.queryDBField(con, SqlUtil.queryEmail(SingPeople), "Email");   
		    if(Eamil!=null && !Eamil.equals("")){
			checkEmail=EmailUtil.isEmail(Eamil);		
		    }
		}
		//馬上寄
		if(checkEmail){
		    logger.info("此主管有email 馬上寄信");
		    
		  //寄信紀錄-EMAIL表 
		   //寄信紀錄 主表寫入已寄信
		}else{
		    logger.info("此主管無email 不寄信");
		}
		
		
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
