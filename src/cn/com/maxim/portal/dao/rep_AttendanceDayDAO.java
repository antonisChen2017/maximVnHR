package cn.com.maxim.portal.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.dayAttendanceExcelRO;
import cn.com.maxim.portal.attendan.ro.dayPlantRO;
import cn.com.maxim.portal.attendan.vo.leaveCardVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.SqlUtil;


public class rep_AttendanceDayDAO {
    /**
     * 取出合計行行數
     * @param con
     * @param lcVo
     * @return
     * @throws Exception
     */
    	public static final Hashtable UnitCount(Connection con,leaveCardVO lcVo) throws Exception
	{
    	    
    	    	Log4jUtil lu = new Log4jUtil();
    	    	Logger logger = lu.initLog4j(rep_AttendanceDayDAO.class);
    	    	Hashtable ht=new Hashtable();
    	    	
    	
    	    	dayPlantRO daRo = new dayPlantRO();
    	 
    	    //	logger.info("UnitCount queryPlantBlueRow  : "+   SqlUtil.queryPlantBlueRow(lcVo.getApplicationDate()));
    		List<dayPlantRO> daRolist = (List<dayPlantRO>) DBUtil.
    			queryPlantBlueRow(con, SqlUtil.queryPlantBlueRow(lcVo.getApplicationDate()), daRo);
    		//	logger.info("UnitCount SORT  : "+daRolist.size());
    			for(int i=0;i<daRolist.size();i++){
    			    daRo= daRolist.get(i);
    			//    logger.info("UnitCount SORT  : "+daRo.getSORT());
    			    if(daRo.getSORT().equals("99")){
    				ht.put(daRo.getROW(), daRo.getSORT());
    			    }
    		}
    			
    	    	return ht;
	}
}
