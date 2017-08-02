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
/**
 * 請假流程邏輯判斷
 * @author Antonis.chen
 *
 */
public class ad_editOProcessDAO
{
	/**
	 * 查詢部門請假流程資料
	 */
	public static final editProcessVO getDeptProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);

		editProcessRO epDD=new editProcessRO();
		edVo.setUnit("0");
		edVo.setStatus(keyConts.processStatus0);
		edVo.setRole(keyConts.EmpRoleD);
		
		/**部門主管正常加班流程**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeD=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptOverData(edVo),epDD);
		edVo.setThreeDID(threeD.get(0).getID());
		edVo.setThreeDLever3(threeD.get(0).getSingRoleL3EP());
		edVo.setThreeDLever4(threeD.get(0).getSingRoleL4EP());
	
	
		edVo.setRole(keyConts.EmpRoleM);
		/**经理正常加班流程**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeM=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptOverData(edVo),epDD);
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
		
		/**部門主管三天以上**/
		logger.info("updateDeptProcess ThreeDID : "+edVo.getThreeDID());
		editProcessRO epDU=new editProcessRO();
		epDU.setID(edVo.getThreeDID());
		epDU.setDept(edVo.getDept());
		if(edVo.getUnit()==null){
		    epDU.setUnit("0");
		}else{
		    epDU.setUnit(edVo.getUnit());
		}
		epDU.setRole(keyConts.EmpRoleD);
		epDU.setStatus("1");
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
		logger.info("ThreeDID updateDeptLerveRole  "+SqlUtil.updateDeptOverRole(epDU));
		DBUtil.updateSql(SqlUtil.updateDeptOverRole(epDU), con);
		/**更新相關加班單流程**/
		DBUtil.updateSql(SqlUtil.updateOversProcess(epDU),con);
		
		/**经理三天以上**/
		logger.info("updateDeptProcess ThreeMID : "+edVo.getThreeMID());
		editProcessRO epMU=new editProcessRO();
		epMU.setID(edVo.getThreeMID());
		epMU.setDept(edVo.getDept());
		if(edVo.getUnit()==null){
		    epMU.setUnit("0");
		}else{
		    epMU.setUnit(edVo.getUnit());
		}
		epMU.setRole(keyConts.EmpRoleM);
		epMU.setStatus("1");
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
		logger.info("ThreeMID updateDeptLerveRole  "+SqlUtil.updateDeptOverRole(epMU));
		DBUtil.updateSql(SqlUtil.updateDeptOverRole(epMU), con);
		/**更新相關加班單流程**/
		DBUtil.updateSql(SqlUtil.updateOversProcess(epMU),con);
		return "";
	}
	
	
	/**
	 * 建立部門請假加班流程資料
	 */
	public static final String insterDeptProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		
		/**部門主管三天以下**/
		
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
		logger.info("部門主管三天以上 insterDeptLerveRole  "+SqlUtil.insterDeptOverRole(epDU));
		DBUtil.updateSql(SqlUtil.insterDeptOverRole(epDU), con);
				
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
		logger.info("经理三天以上  insterDeptLerveRole  "+SqlUtil.insterDeptOverRole(epMU));
		DBUtil.updateSql(SqlUtil.insterDeptOverRole(epMU), con);
	
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
		
		editProcessRO epEU=new editProcessRO();
		edVo.setStatus(keyConts.processStatus0);
		edVo.setRole(keyConts.EmpRoleE);
		
		/**員工加班流程**/
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeE=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptOverData(edVo),epEU);
		if(threeE.size()>0){
        		edVo.setThreeEID(threeE.get(0).getID());
        		edVo.setThreeELever1(threeE.get(0).getSingRoleL1EP());
        		edVo.setThreeELever2(threeE.get(0).getSingRoleL2EP());
        		edVo.setThreeELever3(threeE.get(0).getSingRoleL3EP());
        		edVo.setThreeELever4(threeE.get(0).getSingRoleL4EP());
		}else{
		    	edVo.setThreeEID("");
			edVo.setThreeELever1("");
			edVo.setThreeELever2("");
			edVo.setThreeELever3("");
			edVo.setThreeELever4("");
		}
	
		/**單位主管加班流程**/
		edVo.setRole(keyConts.EmpRoleU);
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeU=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptOverData(edVo),epEU);
		if(threeU.size()>0){
			edVo.setThreeUID(threeU.get(0).getID());
			edVo.setThreeULever2(threeU.get(0).getSingRoleL2EP());
			edVo.setThreeULever3(threeU.get(0).getSingRoleL3EP());
			edVo.setThreeULever4(threeU.get(0).getSingRoleL4EP());
			
		}else{
			edVo.setThreeUID("");
			edVo.setThreeULever2("");
			edVo.setThreeULever3("");
			edVo.setThreeULever4("");
			
		}
	
	
		/**部門主管流程**/
		edVo.setUnit("0");
		edVo.setRole(keyConts.EmpRoleD);
		edVo.setStatus(keyConts.processStatus1);
		logger.info("queryDeptOverData "+SqlUtil.queryDeptOverData(edVo));
		List<editProcessRO> threeD=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptOverData(edVo),epDD);
		logger.info("threeD.size() "+threeD.size());
		if(threeD.size()>0){
			edVo.setThreeDID(threeD.get(0).getID());
			edVo.setThreeDLever3(threeD.get(0).getSingRoleL3EP());
			edVo.setThreeDLever4(threeD.get(0).getSingRoleL4EP());
		}else{
			edVo.setThreeDID("");
			edVo.setThreeDLever3("");
			edVo.setThreeDLever4("");
		}
	
	
		/**经理流程**/
		edVo.setRole(keyConts.EmpRoleM);
		edVo.setStatus(keyConts.processStatus1);
		List<editProcessRO> threeM=DBUtil.queryDeptLeaveData(con,SqlUtil.queryDeptOverData(edVo),epDD);
		if(threeM.size()>0){
		    	edVo.setThreeMID(threeM.get(0).getID());
			edVo.setThreeMLever4(threeM.get(0).getSingRoleL4EP());
			
		}else{
		    	edVo.setThreeMID("");
			edVo.setThreeMLever4("");
			
		}
		
		
		
	
		return edVo;
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
	
		
		/**一般員工加班流程**/
		logger.info("updateDeptProcess ThreeDID : "+edVo.getThreeEID());
		editProcessRO epEU=new editProcessRO();
		epEU.setID(edVo.getThreeEID());
		epEU.setDept(edVo.getDept());
		epEU.setUnit(edVo.getUnit());
		epEU.setRole(keyConts.EmpRoleE);
		epEU.setStatus("1");
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
		logger.info("ThreeDID updateDeptOverRole  "+SqlUtil.updateDeptOverRole(epEU));
		DBUtil.updateSql(SqlUtil.updateDeptOverRole(epEU), con);
		/**更新相關加班單流程**/
		DBUtil.updateSql(SqlUtil.updateOversProcess(epEU),con);
		
		
		/**單位主管加班流程**/
	
		logger.info("updateDeptProcess ThreeUID : "+edVo.getThreeUID());
		editProcessRO epMU=new editProcessRO();
		epMU.setID(edVo.getThreeUID());
		epMU.setDept(edVo.getDept());
		epMU.setUnit(edVo.getUnit());
		epMU.setRole(keyConts.EmpRoleU);
		epMU.setStatus("1");
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
		logger.info("ThreeMID updateDeptOverRole  "+SqlUtil.updateDeptOverRole(epMU));
		DBUtil.updateSql(SqlUtil.updateDeptOverRole(epMU), con);
		/**更新相關加班單流程**/
		DBUtil.updateSql(SqlUtil.updateOversProcess(epMU),con);
		return "";
	}
	
	/**
	 * 建立單位請假加班流程資料
	 */
	public static final String insterUnitProcess(Connection con,editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		
		editProcessRO epEU=new editProcessRO();
		epEU.setDept(edVo.getDept());
		epEU.setRole(keyConts.EmpRoleE);
		epEU.setUnit(edVo.getUnit());
		epEU.setStatus(keyConts.processStatus1);
		logger.info("insterUnitProcess edVo : "+ edVo.toString());
		
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
		
		logger.info("insterUnitProcess epEU : "+ epEU.toString());
		
		logger.info("insterDeptLerveRole  "+SqlUtil.insterDeptOverRole(epEU));
		DBUtil.updateSql(SqlUtil.insterDeptOverRole(epEU), con);
		
		/**單位主管加班流程**/
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
		logger.info("insterUnitProcess epDU : "+ epEU.toString());
		logger.info("insterDeptOverRole  "+SqlUtil.insterDeptOverRole(epDU));
		DBUtil.updateSql(SqlUtil.insterDeptOverRole(epDU), con);
				
	
	
		return "";
	}
}
