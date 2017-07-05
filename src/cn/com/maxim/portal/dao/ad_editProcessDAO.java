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
		epDD.setSingRoleL1("0");
		epDD.setSingRoleL1EP("");
		epDD.setSingRoleL2("0");
		epDD.setSingRoleL2EP("");
		
		if(edVo.getOneDLever3().equals("0")){
		    epDD.setSingRoleL3("0");
		    epDD.setSingRoleL3EP("");
		}else{
		    epDD.setSingRoleL3("1");
		    epDD.setSingRoleL3EP(edVo.getOneDLever3());
		}
		if(edVo.getOneDLever4().equals("0")){
		    epDD.setSingRoleL4("0");
		    epDD.setSingRoleL4EP("");
		}else{
		    epDD.setSingRoleL4("1");
		    epDD.setSingRoleL4EP(edVo.getOneDLever4());
		}
		epDD.setOneTitle("");
		epDD.setTwoTitle("");
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
		
		if(edVo.getOneDLever3().equals("0")){
			epDU.setSingRoleL3("0");
			epDU.setSingRoleL3EP("");
		}else{
			epDU.setSingRoleL3("1");
			epDU.setSingRoleL3EP(edVo.getOneDLever3());
		}
		if(edVo.getOneDLever4().equals("0")){
			epDU.setSingRoleL4("0");
			epDU.setSingRoleL4EP("");
		}else{
			epDU.setSingRoleL4("1");
			epDU.setSingRoleL4EP(edVo.getOneDLever4());
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
		epMD.setSingRoleL1("0");
		epMD.setSingRoleL1EP("");
		epMD.setSingRoleL2("0");
		epMD.setSingRoleL2EP("");
		epMD.setSingRoleL3("0");
		epMD.setSingRoleL3EP("");
		
		if(edVo.getOneMLever4().equals("0")){
		    epMD.setSingRoleL4("0");
		    epMD.setSingRoleL4EP("");
		}else{
		    epMD.setSingRoleL4("1");
		    epMD.setSingRoleL4EP(edVo.getOneMLever4());
		}
		epMD.setOneTitle("");
		epMD.setTwoTitle("");
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
		edVo.setOneDLever3(oneD.get(0).getSingRoleL3EP());
		edVo.setOneDLever4(oneD.get(0).getSingRoleL4EP());
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
		edVo.setThreeMLever4(oneM.get(0).getSingRoleL4EP());
		/**经理三天以上**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeM=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptLeaveData(edVo),epDD);
		edVo.setThreeMID(threeM.get(0).getID());
		edVo.setThreeMLever4(threeM.get(0).getSingRoleL4EP());
		return edVo;
	}
	/**
	 * 更新部門流程
	 * @param con
	 * @param edVo
	 * @return
	 * @throws Exception
	 */
	public static final String updateDeptProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		/**部門主管三天以下**/
		logger.info("updateDeptProcess OneDID : "+edVo.getOneDID());
		editProcessRO epDD=new editProcessRO();
		epDD.setDept(edVo.getDept());
		if(edVo.getUnit()==null){
			epDD.setUnit("0");
		}else{
		    epDD.setUnit(edVo.getUnit());
		}
		epDD.setRole(keyConts.EmpRoleD);
		epDD.setStatus("0");
		epDD.setID(edVo.getOneDID());
		epDD.setSingRoleL1("0");
		epDD.setSingRoleL1EP("");
		epDD.setSingRoleL2("0");
		epDD.setSingRoleL2EP("");
		
		if(edVo.getOneDLever3().equals("0")){
		    epDD.setSingRoleL3("0");
		    epDD.setSingRoleL3EP("");
		}else{
		    epDD.setSingRoleL3("1");
		    epDD.setSingRoleL3EP(edVo.getOneDLever3());
		}
		if(edVo.getOneDLever4().equals("0")){
		    epDD.setSingRoleL4("0");
		    epDD.setSingRoleL4EP("");
		}else{
		    epDD.setSingRoleL4("1");
		    epDD.setSingRoleL4EP(edVo.getOneDLever4());
		}
		epDD.setOneTitle("");
		epDD.setTwoTitle("");
	
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epDD),con);
		/**更新相關請假單流程**/
		logger.info("edRo.getUnit() "+epDD.getUnit());
		DBUtil.updateSql(SqlUtil.updateLeaveCardProcess(epDD),con);
		/**部門主管三天以上**/
		logger.info("updateDeptProcess ThreeDID : "+edVo.getThreeDID());
		editProcessRO epDU=new editProcessRO();
		epDU.setDept(edVo.getDept());
		if(edVo.getUnit()==null){
		    epDU.setUnit("0");
		}else{
		    epDU.setUnit(edVo.getUnit());
		}
		epDU.setRole(keyConts.EmpRoleD);
		epDU.setStatus("1");
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
		/**更新相關請假單流程**/
		DBUtil.updateSql(SqlUtil.updateLeaveCardProcess(epDU),con);
		
		/**经理三天以下**/
		logger.info("updateDeptProcess OneMID : "+edVo.getOneMID());
		editProcessRO epMD=new editProcessRO();
		epMD.setDept(edVo.getDept());
		epMD.setRole(edVo.getRole());
		if(edVo.getUnit()==null){
		    epMD.setUnit("0");
		}else{
		    epMD.setUnit(edVo.getUnit());
		}
		epMD.setRole(keyConts.EmpRoleM);
		epMD.setStatus("0");
		epMD.setID(edVo.getOneMID());
		epMD.setSingRoleL1("0");
		epMD.setSingRoleL1EP("");
		epMD.setSingRoleL2("0");
		epMD.setSingRoleL2EP("");
		epMD.setSingRoleL3("0");
		epMD.setSingRoleL3EP("");
		
		if(edVo.getOneMLever4().equals("0")){
		    epMD.setSingRoleL4("0");
		    epMD.setSingRoleL4EP("");
		}else{
		    epMD.setSingRoleL4("1");
		    epMD.setSingRoleL4EP(edVo.getOneMLever4());
		}
		epMD.setOneTitle("");
		epMD.setTwoTitle("");
		logger.info("OneMID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epMD));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epMD), con);
		/**更新相關請假單流程**/
		DBUtil.updateSql(SqlUtil.updateLeaveCardProcess(epMD),con);
		
		
		/**经理三天以上**/
		logger.info("updateDeptProcess ThreeMID : "+edVo.getThreeMID());
		editProcessRO epMU=new editProcessRO();
		epMU.setDept(edVo.getDept());
		if(edVo.getUnit()==null){
		    epMU.setUnit("0");
		}else{
		    epMU.setUnit(edVo.getUnit());
		}
		epMU.setRole(keyConts.EmpRoleM);
		epMU.setStatus("1");
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
		/**更新相關請假單流程**/
		DBUtil.updateSql(SqlUtil.updateLeaveCardProcess(epMU),con);
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
		epED.setDept(edVo.getDept());
		epED.setUnit(edVo.getUnit());
		epED.setRole(keyConts.EmpRoleE);
		epED.setStatus("0");
		epED.setID(edVo.getOneEID());
		if(edVo.getOneELever1().equals("0")){
		    epED.setSingRoleL1("0");
		    epED.setSingRoleL1EP("");
		}else{
		    epED.setSingRoleL1("1");
		    epED.setSingRoleL1EP(edVo.getOneELever1());
		}
		if(edVo.getOneELever2().equals("0")){
		    epED.setSingRoleL2("0");
		    epED.setSingRoleL2EP("");
		}else{
		    epED.setSingRoleL2("1");
		    epED.setSingRoleL2EP(edVo.getOneELever2());
		}
		
		if(edVo.getOneELever3().equals("0")){
		    epED.setSingRoleL3("0");
		    epED.setSingRoleL3EP("");
		}else{
		    epED.setSingRoleL3("1");
		    epED.setSingRoleL3EP(edVo.getOneELever3());
		}
		if(edVo.getOneELever4().equals("0")){
		    epED.setSingRoleL4("0");
		    epED.setSingRoleL4EP("");
		}else{
		    epED.setSingRoleL4("1");
		    epED.setSingRoleL4EP(edVo.getOneELever4());
		}
		epED.setOneTitle("");
		epED.setTwoTitle("");
		logger.info("OneDID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epED));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epED),con);
		/**更新相關請假單流程**/
		logger.info("OneDID updateLeaveCardProcess  "+SqlUtil.updateLeaveCardProcess(epED));
		DBUtil.updateSql(SqlUtil.updateLeaveCardProcess(epED),con);
		
		/**一般員工三天以上**/
		logger.info("updateDeptProcess ThreeDID : "+edVo.getThreeEID());
		editProcessRO epEU=new editProcessRO();
		epEU.setDept(edVo.getDept());
		epEU.setUnit(edVo.getUnit());
		epEU.setStatus("1");
		epEU.setRole(keyConts.EmpRoleE);
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
		/**更新相關請假單流程**/
		DBUtil.updateSql(SqlUtil.updateLeaveCardProcess(epEU),con);
		
		/**單位主管三天以下**/
		logger.info("updateDeptProcess OneUID : "+edVo.getOneUID());
		editProcessRO epMD=new editProcessRO();
		epMD.setDept(edVo.getDept());
		epMD.setUnit(edVo.getUnit());
		epMD.setRole(keyConts.EmpRoleU);
		epMD.setStatus("0");
		epMD.setID(edVo.getOneUID());
		epMD.setSingRoleL1("0");
		epMD.setSingRoleL1EP("");
		if(edVo.getOneULever2().equals("0")){
		    epMD.setSingRoleL2("0");
		    epMD.setSingRoleL2EP("");
		}else{
		    epMD.setSingRoleL2("1");
		    epMD.setSingRoleL2EP(edVo.getOneULever2());
		}
		if(edVo.getOneULever3().equals("0")){
		    epMD.setSingRoleL3("0");
		    epMD.setSingRoleL3EP("");
		}else{
		    epMD.setSingRoleL3("1");
			epMD.setSingRoleL3EP(edVo.getOneULever3());
		}
		if(edVo.getThreeULever4().equals("0")){
		    epMD.setSingRoleL4("0");
		    epMD.setSingRoleL4EP("");
		}else{
		    epMD.setSingRoleL4("1");
		    epMD.setSingRoleL4EP(edVo.getThreeULever4());
		}
		epMD.setOneTitle("");
		epMD.setTwoTitle("");
		logger.info("*單位主管三天以下 OneMID updateDeptLerveRole  "+SqlUtil.updateDeptLerveRole(epMD));
		DBUtil.updateSql(SqlUtil.updateDeptLerveRole(epMD), con);
		/**更新相關請假單流程**/
		DBUtil.updateSql(SqlUtil.updateLeaveCardProcess(epMD),con);
		
		
		/**單位主管三天以上**/
		logger.info("updateDeptProcess ThreeUID : "+edVo.getThreeUID());
		editProcessRO epMU=new editProcessRO();
		epMU.setDept(edVo.getDept());
		epMU.setUnit(edVo.getUnit());
		epMU.setRole(keyConts.EmpRoleU);
		epMU.setStatus("1");
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
		/**更新相關請假單流程**/
		DBUtil.updateSql(SqlUtil.updateLeaveCardProcess(epMU),con);
		
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
		edVo.setOneELever1(oneE.get(0).getSingRoleL1EP());
		edVo.setOneELever2(oneE.get(0).getSingRoleL2EP());
		edVo.setOneELever3(oneE.get(0).getSingRoleL3EP());
		edVo.setOneELever4(oneE.get(0).getSingRoleL4EP());
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
		edVo.setOneULever2(oneU.get(0).getSingRoleL2EP());
		edVo.setOneULever3(oneU.get(0).getSingRoleL3EP());
		edVo.setOneULever4(oneU.get(0).getSingRoleL4EP());
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
		edVo.setOneDLever3(oneD.get(0).getSingRoleL3EP());
		edVo.setOneDLever4(oneD.get(0).getSingRoleL4EP());
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
		edVo.setOneMLever4(oneM.get(0).getSingRoleL4EP());
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
		if(edVo.getOneELever1().equals("0")){
		    epED.setSingRoleL1("0");
		    epED.setSingRoleL1EP("");
		}else{
		    epED.setSingRoleL1("1");
		    epED.setSingRoleL1EP(edVo.getOneELever1());
		}
		if(edVo.getOneELever2().equals("0")){
		    epED.setSingRoleL2("0");
			epED.setSingRoleL2EP("");
		}else{
		    epED.setSingRoleL2("1");
		    epED.setSingRoleL2EP(edVo.getOneELever2());
		}
		if(edVo.getOneELever3().equals("0")){
		    epED.setSingRoleL3("0");
		    epED.setSingRoleL3EP("");
		}else{
		    epED.setSingRoleL3("1");
		    epED.setSingRoleL3EP(edVo.getOneELever3());
		}
		
		if(edVo.getOneELever4().equals("0")){
		    epED.setSingRoleL4("0");
		    epED.setSingRoleL4EP("");
		}else{
		    epED.setSingRoleL4("1");
		    epED.setSingRoleL4EP(edVo.getOneMLever4());
		}
		epED.setOneTitle("");
		epED.setTwoTitle("");
		logger.info("insterDeptLerveRole  "+SqlUtil.insterDeptLerveRole(epED));
		DBUtil.updateSql(SqlUtil.insterDeptLerveRole(epED), con);
		
		
		
		/**單位主管三天以下**/
		editProcessRO epUD=new editProcessRO();
		epUD.setDept(edVo.getDept());
		epUD.setRole(keyConts.EmpRoleU);
		epUD.setUnit(edVo.getUnit());
		epUD.setStatus(keyConts.processStatus0);
		epUD.setSingRoleL1("0");
		epUD.setSingRoleL1EP("");

        	if(edVo.getOneULever2().equals("0")){
        	    epUD.setSingRoleL2("0");
        	    epUD.setSingRoleL2EP("");
        	}else{
        	    epUD.setSingRoleL2("1");
        	    epUD.setSingRoleL2EP(edVo.getOneULever2());
        	}
        	
        	if(edVo.getOneULever3().equals("0")){
        	    epUD.setSingRoleL3("0");
        	    epUD.setSingRoleL3EP("");
        	}else{
        	    epUD.setSingRoleL3("1");
        	    epUD.setSingRoleL3EP(edVo.getOneULever3());
        	}
        	if(edVo.getOneULever4().equals("0")){
        	    epUD.setSingRoleL4("0");
        	    epUD.setSingRoleL4EP("");
        	}else{
        	    epUD.setSingRoleL4("1");
        	    epUD.setSingRoleL4EP(edVo.getOneULever4());
        	}
        	epUD.setOneTitle("");
        	epUD.setTwoTitle("");
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
