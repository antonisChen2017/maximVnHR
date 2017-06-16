package cn.com.maxim.portal.dao;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.editProcessRO;
import cn.com.maxim.portal.attendan.vo.editProcessVO;
import cn.com.maxim.portal.util.HtmlUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.potral.consts.keyConts;
import cn.com.maxim.potral.consts.sqlConsts;

public class ad_editProcessDAO
{
	
	/**
	 * 建立部門請假加班流程資料
	 */
	public static final String insterDeptProcess(editProcessVO edVo) throws Exception
	{
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ad_editProcessDAO.class);
		
		/**部門主管三天以下**/
		editProcessRO epDD=new editProcessRO();
		/**部門主管三天以上**/
		editProcessRO epDU=new editProcessRO();
		/**经理三天以下**/
		editProcessRO epMD=new editProcessRO();
		/**经理三天以上**/
		editProcessRO epMU=new editProcessRO();
		
		logger.info("部門 :"+edVo.getDept());
		
		logger.info("角色 :"+keyConts.EmpRoleD);
		
		return "";
	}
}
