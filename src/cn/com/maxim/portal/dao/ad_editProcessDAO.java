package cn.com.maxim.portal.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.editProcessRO;
import cn.com.maxim.portal.attendan.vo.editProcessVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;
import cn.com.maxim.potral.consts.keyConts;


public class ad_editProcessDAO
{
	
	/**
	 * 建立部門請假加班流程資料
	 */
	public static final String insterDeptProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		
		/**部門主管三天以下**/
		editProcessRO epDD=new editProcessRO();
		epDD.setDept(edVo.getDept());
		epDD.setRole(keyConts.EmpRoleD);
		epDD.setUnit("0");
		epDD.setStatus(keyConts.processStatus0);
		epDD.setSingRoleL1("1");
		epDD.setSingRoleL1EP(edVo.getOneDAuditor());
		if(edVo.getOneDAgent().equals("0")){
			epDD.setSingRoleL2("0");
			epDD.setSingRoleL2EP("");
		}else{
			epDD.setSingRoleL2("1");
			epDD.setSingRoleL2EP(edVo.getOneDAgent());
		}
		epDD.setSingRoleL3("0");
		epDD.setSingRoleL3EP("");
		epDD.setSingRoleL4("0");
		epDD.setSingRoleL4EP("");
		epDD.setOneTitle(edVo.getOneDAuditorTitle());
		epDD.setTwoTitle(edVo.getOneDAgentTitle());
		logger.info("部門主管三天以下  insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epDD));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epDD), con);
		/**部門主管三天以上**/
		editProcessRO epDU=new editProcessRO();
		epDU.setDept(edVo.getDept());
		epDU.setRole(keyConts.EmpRoleD);
		epDU.setUnit("0");
		epDU.setStatus(keyConts.processStatus1);
		epDU.setSingRoleL1("0");
		epDU.setSingRoleL1EP("");
		epDU.setSingRoleL2("0");
		epDU.setSingRoleL2EP("");
		
		if(edVo.getThreeDLever3().equals("0")){
			epDU.setSingRoleL3("0");
			epDU.setSingRoleL3EP("");
		}else{
			epDU.setSingRoleL3("1");
			epDU.setSingRoleL3EP(edVo.getThreeDLever3());
		}
		if(edVo.getThreeDLever4().equals("0")){
			epDU.setSingRoleL4("0");
			epDU.setSingRoleL4EP("");
		}else{
			epDU.setSingRoleL4("1");
			epDU.setSingRoleL4EP(edVo.getThreeDLever4());
		}
		epDU.setOneTitle("");
		epDU.setTwoTitle("");
		logger.info("部門主管三天以上 insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epDU));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epDU), con);
				
		/**经理三天以下**/
		editProcessRO epMD=new editProcessRO();
		epMD.setDept(edVo.getDept());
		epMD.setRole(keyConts.EmpRoleM);
		epMD.setUnit("0");
		epMD.setStatus(keyConts.processStatus0);
		epMD.setSingRoleL1("1");
		epMD.setSingRoleL1EP(edVo.getOneMAuditor());
		if(edVo.getOneMAgent().equals("0")){
			epMD.setSingRoleL2("0");
			epMD.setSingRoleL2EP("");
		}else{
			epMD.setSingRoleL2("1");
			epMD.setSingRoleL2EP(edVo.getOneMAgent());
		}
		epMD.setSingRoleL3("0");
		epMD.setSingRoleL3EP("");
		epMD.setSingRoleL4("0");
		epMD.setSingRoleL4EP("");
		epMD.setOneTitle(edVo.getOneMAuditorTitle());
		epMD.setTwoTitle(edVo.getOneMAgentTitle());
		logger.info("经理三天以下  insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epMD));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epMD), con);
		
		/**经理三天以上**/
		editProcessRO epMU=new editProcessRO();
		epMU.setDept(edVo.getDept());
		epMU.setRole(keyConts.EmpRoleM);
		epMU.setUnit("0");
		epMU.setStatus(keyConts.processStatus1);
		epMU.setSingRoleL1("0");
		epMU.setSingRoleL1EP("");
		epMU.setSingRoleL2("0");
		epMU.setSingRoleL2EP("");
		epMU.setSingRoleL3("0");
		epMU.setSingRoleL3EP("");
		
		if(edVo.getThreeMLever4().equals("0")){
			epMU.setSingRoleL4("0");
			epMU.setSingRoleL4EP("");
		}else{
			epMU.setSingRoleL4("1");
			epMU.setSingRoleL4EP(edVo.getThreeMLever4());
		}
		epMU.setOneTitle("");
		epMU.setTwoTitle("");
		logger.info("经理三天以上  insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epMU));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epMU), con);
	
		return "";
	}
	
	/**
	 * 查詢部門請假流程資料
	 */
	public static final editProcessVO getDeptProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		
		/**部門主管三天以下**/
		editProcessRO epDD=new editProcessRO();
		edVo.setUnit("0");
		edVo.setStatus(keyConts.processStatus0);
		edVo.setRole(keyConts.EmpRoleD);
		logger.info("*部門主管三天以下 queryDeptLeaveData  : "+SqlUtil.queryDeptLeaveData(edVo));
		List<editProcessRO> oneD=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setOneDID(oneD.get(0).getID());
		edVo.setOneDAuditorTitle(oneD.get(0).getOneTitle());
		edVo.setOneDAuditor(oneD.get(0).getSingRoleL1EP());
		edVo.setOneDAgentTitle(oneD.get(0).getTwoTitle());
		edVo.setOneDAgent(oneD.get(0).getSingRoleL2EP());
		/**部門主管三天以上**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeD=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setThreeDID(threeD.get(0).getID());
		edVo.setThreeDLever3(threeD.get(0).getSingRoleL3EP());
		edVo.setThreeDLever4(threeD.get(0).getSingRoleL4EP());
	
		/**经理三天以下**/
		edVo.setStatus(keyConts.processStatus0);
		edVo.setRole(keyConts.EmpRoleM);
		List<editProcessRO>oneM=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setOneMID(oneM.get(0).getID());
		edVo.setOneMAuditorTitle(oneM.get(0).getOneTitle());
		edVo.setOneMAuditor(oneM.get(0).getSingRoleL1EP());
		edVo.setOneMAgentTitle(oneM.get(0).getTwoTitle());
		edVo.setOneMAgent(oneM.get(0).getSingRoleL2EP());
		/**经理三天以上**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeM=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setThreeMID(threeM.get(0).getID());
		edVo.setThreeMLever4(threeM.get(0).getSingRoleL4EP());
		return edVo;
	}
	/**
	 * 更新不門流程
	 * @param con
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String updateDeptProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		/**一般員工三天以下**/
		logger.info("updateDeptProcess OneDID : "+edVo.getOneDID());
		editProcessRO epDD=new editProcessRO();
		epDD.setID(edVo.getOneDID());
		epDD.setSingRoleL1("1");
		epDD.setSingRoleL1EP(edVo.getOneDAuditor());
		if(edVo.getOneDAgent().equals("0")){
			epDD.setSingRoleL2("0");
			epDD.setSingRoleL2EP("");
		}else{
			epDD.setSingRoleL2("1");
			epDD.setSingRoleL2EP(edVo.getOneDAgent());
		}
		epDD.setSingRoleL3("0");
		epDD.setSingRoleL3EP("");
		epDD.setSingRoleL4("0");
		epDD.setSingRoleL4EP("");
		epDD.setOneTitle(edVo.getOneDAuditorTitle());
		epDD.setTwoTitle(edVo.getOneDAgentTitle());
		logger.info("OneDID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epDD));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epDD),con);
		/**部門主管三天以上**/
		logger.info("updateDeptProcess ThreeDID : "+edVo.getThreeDID());
		editProcessRO epDU=new editProcessRO();
		epDU.setID(edVo.getThreeDID());
		epDU.setSingRoleL1("0");
		epDU.setSingRoleL1EP("");
		epDU.setSingRoleL2("0");
		epDU.setSingRoleL2EP("");
		
		if(edVo.getThreeDLever3().equals("0")){
			epDU.setSingRoleL3("0");
			epDU.setSingRoleL3EP("");
		}else{
			epDU.setSingRoleL3("1");
			epDU.setSingRoleL3EP(edVo.getThreeDLever3());
		}
		if(edVo.getThreeDLever4().equals("0")){
			epDU.setSingRoleL4("0");
			epDU.setSingRoleL4EP("");
		}else{
			epDU.setSingRoleL4("1");
			epDU.setSingRoleL4EP(edVo.getThreeDLever4());
		}
		epDU.setOneTitle("");
		epDU.setTwoTitle("");
		logger.info("ThreeDID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epDU));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epDU), con);
		
		/**经理三天以下**/
		logger.info("updateDeptProcess OneMID : "+edVo.getOneMID());
		editProcessRO epMD=new editProcessRO();
		epMD.setID(edVo.getOneMID());
		epMD.setSingRoleL1("1");
		epMD.setSingRoleL1EP(edVo.getOneMAuditor());
		if(edVo.getOneMAgent().equals("0")){
			epMD.setSingRoleL2("0");
			epMD.setSingRoleL2EP("");
		}else{
			epMD.setSingRoleL2("1");
			epMD.setSingRoleL2EP(edVo.getOneMAgent());
		}
		epMD.setSingRoleL3("0");
		epMD.setSingRoleL3EP("");
		epMD.setSingRoleL4("0");
		epMD.setSingRoleL4EP("");
		epMD.setOneTitle(edVo.getOneMAuditorTitle());
		epMD.setTwoTitle(edVo.getOneMAgentTitle());
		logger.info("OneMID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epMD));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epMD), con);

		/**经理三天以上**/
		logger.info("updateDeptProcess ThreeMID : "+edVo.getThreeMID());
		editProcessRO epMU=new editProcessRO();
		epMU.setID(edVo.getThreeMID());
		epMU.setSingRoleL1("0");
		epMU.setSingRoleL1EP("");
		epMU.setSingRoleL2("0");
		epMU.setSingRoleL2EP("");
		epMU.setSingRoleL3("0");
		epMU.setSingRoleL3EP("");
		
		if(edVo.getThreeMLever4().equals("0")){
			epMU.setSingRoleL4("0");
			epMU.setSingRoleL4EP("");
		}else{
			epMU.setSingRoleL4("1");
			epMU.setSingRoleL4EP(edVo.getThreeMLever4());
		}
		epMU.setOneTitle("");
		epMU.setTwoTitle("");
		logger.info("ThreeMID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epMU));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epMU), con);
		return "";
	}
	
	/**
	 * 更新單位流程
	 * @param con
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String updateUnitProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		/**一般員工三天以下**/
		logger.info("updateDeptProcess OneEID : "+edVo.getOneEID());
		editProcessRO epED=new editProcessRO();
		epED.setID(edVo.getOneEID());
		epED.setSingRoleL1("1");
		epED.setSingRoleL1EP(edVo.getOneEAuditor());
		if(edVo.getOneEAgent().equals("0")){
			epED.setSingRoleL2("0");
			epED.setSingRoleL2EP("");
		}else{
			epED.setSingRoleL2("1");
			epED.setSingRoleL2EP(edVo.getOneEAgent());
		}
		epED.setSingRoleL3("0");
		epED.setSingRoleL3EP("");
		epED.setSingRoleL4("0");
		epED.setSingRoleL4EP("");
		epED.setOneTitle(edVo.getOneEAuditorTitle());
		epED.setTwoTitle(edVo.getOneEAgentTitle());
		logger.info("OneDID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epED));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epED),con);
		/**一般員工三天以上**/
		logger.info("updateDeptProcess ThreeDID : "+edVo.getThreeEID());
		editProcessRO epEU=new editProcessRO();
		epEU.setID(edVo.getThreeEID());
		if(edVo.getThreeELever1().equals("0")){
			epEU.setSingRoleL1("0");
			epEU.setSingRoleL1EP("");
		}else{
			epEU.setSingRoleL1("1");
			epEU.setSingRoleL1EP(edVo.getThreeELever1());
		}
		if(edVo.getThreeELever2().equals("0")){
			epEU.setSingRoleL2("0");
			epEU.setSingRoleL2EP("");
		}else{
			epEU.setSingRoleL2("1");
			epEU.setSingRoleL2EP(edVo.getThreeELever2());
		}
		
		if(edVo.getThreeELever3().equals("0")){
			epEU.setSingRoleL3("0");
			epEU.setSingRoleL3EP("");
		}else{
			epEU.setSingRoleL3("1");
			epEU.setSingRoleL3EP(edVo.getThreeELever3());
		}
		if(edVo.getThreeELever4().equals("0")){
			epEU.setSingRoleL4("0");
			epEU.setSingRoleL4EP("");
		}else{
			epEU.setSingRoleL4("1");
			epEU.setSingRoleL4EP(edVo.getThreeELever4());
		}
		epEU.setOneTitle("");
		epEU.setTwoTitle("");
		logger.info("ThreeDID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epEU));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epEU), con);
		
		/**單位主管三天以下**/
		logger.info("updateDeptProcess OneUID : "+edVo.getOneUID());
		editProcessRO epMD=new editProcessRO();
		epMD.setID(edVo.getOneUID());
		epMD.setSingRoleL1("1");
		epMD.setSingRoleL1EP(edVo.getOneUAuditor());
		if(edVo.getOneUAgent().equals("0")){
			epMD.setSingRoleL2("0");
			epMD.setSingRoleL2EP("");
		}else{
			epMD.setSingRoleL2("1");
			epMD.setSingRoleL2EP(edVo.getOneUAgent());
		}
		epMD.setSingRoleL3("0");
		epMD.setSingRoleL3EP("");
		epMD.setSingRoleL4("0");
		epMD.setSingRoleL4EP("");
		epMD.setOneTitle(edVo.getOneUAuditorTitle());
		epMD.setTwoTitle(edVo.getOneUAgentTitle());
		logger.info("*單位主管三天以下 OneMID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epMD));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epMD), con);

		/**單位主管三天以上**/
		logger.info("updateDeptProcess ThreeUID : "+edVo.getThreeUID());
		editProcessRO epMU=new editProcessRO();
		epMU.setID(edVo.getThreeUID());
		epMU.setSingRoleL1("0");
		epMU.setSingRoleL1EP("");
		if(edVo.getThreeULever2().equals("0")){
			epMU.setSingRoleL2("0");
			epMU.setSingRoleL2EP("");
		}else{
			epMU.setSingRoleL2("1");
			epMU.setSingRoleL2EP(edVo.getThreeULever2());
		}
		if(edVo.getThreeULever3().equals("0")){
			epMU.setSingRoleL3("0");
			epMU.setSingRoleL3EP("");
		}else{
			epMU.setSingRoleL3("1");
			epMU.setSingRoleL3EP(edVo.getThreeULever3());
		}
		if(edVo.getThreeULever4().equals("0")){
			epMU.setSingRoleL4("0");
			epMU.setSingRoleL4EP("");
		}else{
			epMU.setSingRoleL4("1");
			epMU.setSingRoleL4EP(edVo.getThreeULever4());
		}
		epMU.setOneTitle("");
		epMU.setTwoTitle("");
		logger.info("ThreeMID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epMU));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epMU), con);
		return "";
	}
	/**
	 * 查詢單位請假流程資料
	 */
	public static final editProcessVO getUnitProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		editProcessRO epDD=new editProcessRO();
		/**員工三天以下**/
		editProcessRO epEU=new editProcessRO();
		edVo.setStatus(keyConts.processStatus0);
		edVo.setRole(keyConts.EmpRoleE);
		logger.info("queryDeptLeaveData "+SqlUtil.queryDeptLeaveData(edVo));
		List<editProcessRO> oneE=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epEU);
		edVo.setOneEID(oneE.get(0).getID());
		edVo.setOneEAuditorTitle(oneE.get(0).getOneTitle());
		edVo.setOneEAuditor(oneE.get(0).getSingRoleL1EP());
		edVo.setOneEAgentTitle(oneE.get(0).getTwoTitle());
		edVo.setOneEAgent(oneE.get(0).getSingRoleL2EP());
		/**員工三天以上**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeE=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epEU);
		edVo.setThreeEID(threeE.get(0).getID());
		edVo.setThreeELever1(threeE.get(0).getSingRoleL1EP());
		edVo.setThreeELever2(threeE.get(0).getSingRoleL2EP());
		edVo.setThreeELever3(threeE.get(0).getSingRoleL3EP());
		edVo.setThreeELever4(threeE.get(0).getSingRoleL4EP());
		
		/**單位主管三天以下**/
		edVo.setStatus(keyConts.processStatus0);
		edVo.setRole(keyConts.EmpRoleU);
		logger.info("單位主管三天以下"+SqlUtil.queryDeptLeaveData(edVo));
		List<editProcessRO>oneU=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epEU);
		edVo.setOneUID(oneU.get(0).getID());
		edVo.setOneUAuditorTitle(oneU.get(0).getOneTitle());
		edVo.setOneUAuditor(oneU.get(0).getSingRoleL1EP());
		edVo.setOneUAgentTitle(oneU.get(0).getTwoTitle());
		edVo.setOneUAgent(oneU.get(0).getSingRoleL2EP());
		/**單位主管三天以上**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeU=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epEU);
		edVo.setThreeUID(threeU.get(0).getID());
		edVo.setThreeULever2(threeU.get(0).getSingRoleL2EP());
		edVo.setThreeULever3(threeU.get(0).getSingRoleL3EP());
		edVo.setThreeULever4(threeU.get(0).getSingRoleL4EP());
		
		/**部門主管三天以下**/
	
		edVo.setUnit("0");
		edVo.setStatus(keyConts.processStatus0);
		edVo.setRole(keyConts.EmpRoleD);
		List<editProcessRO> oneD=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setOneDID(oneD.get(0).getID());
		edVo.setOneDAuditorTitle(oneD.get(0).getOneTitle());
		edVo.setOneDAuditor(oneD.get(0).getSingRoleL1EP());
		edVo.setOneDAgentTitle(oneD.get(0).getTwoTitle());
		edVo.setOneDAgent(oneD.get(0).getSingRoleL2EP());
		/**部門主管三天以上**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeD=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setThreeDID(threeD.get(0).getID());
		edVo.setThreeDLever3(threeD.get(0).getSingRoleL3EP());
		edVo.setThreeDLever4(threeD.get(0).getSingRoleL4EP());
	
		/**经理三天以下**/
		edVo.setStatus(keyConts.processStatus0);
		edVo.setRole(keyConts.EmpRoleM);
		List<editProcessRO>oneM=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setOneMID(oneM.get(0).getID());
		edVo.setOneMAuditorTitle(oneM.get(0).getOneTitle());
		edVo.setOneMAuditor(oneM.get(0).getSingRoleL1EP());
		edVo.setOneMAgentTitle(oneM.get(0).getTwoTitle());
		edVo.setOneMAgent(oneM.get(0).getSingRoleL2EP());
		/**经理三天以上**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeM=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setThreeMID(threeM.get(0).getID());
		edVo.setThreeMLever4(threeM.get(0).getSingRoleL4EP());
		
	
		return edVo;
	}
	
	/**
	 * 建立單位請假加班流程資料
	 */
	public static final String insterUnitProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		
		/**一般員工三天以下**/
		editProcessRO epED=new editProcessRO();
		epED.setDept(edVo.getDept());
		epED.setRole(keyConts.EmpRoleE);
		epED.setUnit(edVo.getUnit());
		epED.setStatus(keyConts.processStatus0);
		if(edVo.getOneEAuditor().equals("0")){
			epED.setSingRoleL1("0");
			epED.setSingRoleL1EP("");
		}else{
			epED.setSingRoleL1("1");
			epED.setSingRoleL1EP(edVo.getOneEAuditor());
		}
		if(edVo.getOneDAgent().equals("0")){
			epED.setSingRoleL2("0");
			epED.setSingRoleL2EP("");
		}else{
			epED.setSingRoleL2("1");
			epED.setSingRoleL2EP(edVo.getOneEAgent());
		}
		epED.setSingRoleL3("0");
		epED.setSingRoleL3EP("");
		epED.setSingRoleL4("0");
		epED.setSingRoleL4EP("");
		epED.setOneTitle(edVo.getOneEAuditorTitle());
		epED.setTwoTitle(edVo.getOneEAgentTitle());
		logger.info("insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epED));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epED), con);
		
		
		
		/**單位主管三天以下**/
		editProcessRO epUD=new editProcessRO();
		epUD.setDept(edVo.getDept());
		epUD.setRole(keyConts.EmpRoleU);
		epUD.setUnit(edVo.getUnit());
		epUD.setStatus(keyConts.processStatus0);
		epUD.setSingRoleL1("1");
		epUD.setSingRoleL1EP(edVo.getOneUAuditor());
		if(edVo.getOneMAgent().equals("0")){
			epUD.setSingRoleL2("0");
			epUD.setSingRoleL2EP("");
		}else{
			epUD.setSingRoleL2("1");
			epUD.setSingRoleL2EP(edVo.getOneUAgent());
		}
		epUD.setSingRoleL3("0");
		epUD.setSingRoleL3EP("");
		epUD.setSingRoleL4("0");
		epUD.setSingRoleL4EP("");
		epUD.setOneTitle(edVo.getOneUAuditorTitle());
		epUD.setTwoTitle(edVo.getOneUAgentTitle());
		logger.info("insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epUD));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epUD), con);
		

		/**一般員工三天以上**/
		editProcessRO epEU=new editProcessRO();
		epEU.setDept(edVo.getDept());
		epEU.setRole(keyConts.EmpRoleE);
		epEU.setUnit(edVo.getUnit());
		epEU.setStatus(keyConts.processStatus1);
		if(edVo.getThreeELever1().equals("0")){
			epEU.setSingRoleL1("0");
			epEU.setSingRoleL1EP("");
		}else{
			epEU.setSingRoleL1("1");
			epEU.setSingRoleL1EP(edVo.getThreeELever1());
		}
		if(edVo.getThreeELever2().equals("0")){
			epEU.setSingRoleL2("0");
			epEU.setSingRoleL2EP("");
		}else{
			epEU.setSingRoleL2("1");
			epEU.setSingRoleL2EP(edVo.getThreeELever2());
		}
		if(edVo.getThreeELever3().equals("0")){
			epEU.setSingRoleL3("0");
			epEU.setSingRoleL3EP("");
		}else{
			epEU.setSingRoleL3("1");
			epEU.setSingRoleL3EP(edVo.getThreeELever3());
		}
		
		if(edVo.getThreeELever4().equals("0")){
			epEU.setSingRoleL4("0");
			epEU.setSingRoleL4EP("");
		}else{
			epEU.setSingRoleL4("1");
			epEU.setSingRoleL4EP(edVo.getThreeMLever4());
		}
		epEU.setOneTitle("");
		epEU.setTwoTitle("");
		logger.info("insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epEU));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epEU), con);
		
		
		/**單位主管三天以上**/
		editProcessRO epDU=new editProcessRO();
		epDU.setDept(edVo.getDept());
		epDU.setRole(keyConts.EmpRoleU);
		epDU.setUnit(edVo.getUnit());
		epDU.setStatus(keyConts.processStatus1);
	
			epDU.setSingRoleL1("0");
			epDU.setSingRoleL1EP("");
	
		if(edVo.getThreeULever2().equals("0")){
			epDU.setSingRoleL2("0");
			epDU.setSingRoleL2EP("");
		}else{
			epDU.setSingRoleL2("1");
			epDU.setSingRoleL2EP(edVo.getThreeULever2());
		}
		
		if(edVo.getThreeULever3().equals("0")){
			epDU.setSingRoleL3("0");
			epDU.setSingRoleL3EP("");
		}else{
			epDU.setSingRoleL3("1");
			epDU.setSingRoleL3EP(edVo.getThreeULever3());
		}
		if(edVo.getThreeULever4().equals("0")){
			epDU.setSingRoleL4("0");
			epDU.setSingRoleL4EP("");
		}else{
			epDU.setSingRoleL4("1");
			epDU.setSingRoleL4EP(edVo.getThreeULever4());
		}
		epDU.setOneTitle("");
		epDU.setTwoTitle("");
		logger.info("insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epDU));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epDU), con);
				
	
	
		return "";
	}
	

}
