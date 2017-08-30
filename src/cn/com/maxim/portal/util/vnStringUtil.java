package cn.com.maxim.portal.util;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.dayReportRO;
import cn.com.maxim.portal.attendan.wo.monthReportNoteWO;
import cn.com.maxim.potral.consts.keyConts;

public class vnStringUtil
{
	
	Log4jUtil lu = new Log4jUtil();
	Logger logger = lu.initLog4j(vnStringUtil.class);
	/**
	 * 錯誤訊息擷取
	 * @param ex
	 * @return
	 */
	
	public static String getExceptionAllinformation(Exception ex){
        String sOut = "";
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        return sOut;
 }
	 /**
     * 月報表欄位假期記號判斷
     */
    public static String getDAY(dayReportRO dr){
    	String day="";
    	double da=changeDouble(dr.getHOLIDAYA());
    	
    	double db=changeDouble(dr.getHOLIDAYB());
    	
    	//double dc=changeDouble(dr.getHOLIDAYC());
    
    	double dd=changeDouble(dr.getHOLIDAYD());
    	
    	double de=changeDouble(dr.getHOLIDAYE());
    	
    	double df=changeDouble(dr.getHOLIDAYF());
    	
    	double ae=changeDouble(dr.getATTENDANCE());
    	
    	double ah=changeDouble(dr.getHOLIDAYH());
    	//工傷
    //	double ai=changeDouble(dr.getHOLIDAYI());
    	
    	double nw=changeDouble(dr.getNOTWORK());
    
    	//double sw=changeDouble(dr.getSTOPWORK());
    
    
    	/**上班**/
    	if(ae>0){
    		day=keyConts.monthReportX;
    	}
    	///**公傷**/
    //	if(dc>0){
    //		day=keyConts.monthReportI;
    	//}
    	/**輪休**/
    //	if(ai>0){
    //		day=keyConts.monthReportL;
    //	}
    	/**曠工**/
    	if(nw>0){
    		day=keyConts.monthReportO;
    	}
    	/**病假**/
    	if(db>0){
    		day=keyConts.monthReportB;
    	}
    	/**事假**/
    	if(da>0){
    		day=keyConts.monthReportR;
    	}  	
    //	/**公假**/
    //	if(dc>0){
    //		day=keyConts.monthReportN;
    //	}
    	/**婚假**/
    	if(dd>0){
    		day=keyConts.monthReportH;
    	}
    	/**喪假**/
    	if(df>0){
    		day=keyConts.monthReportT;
    	}
    	/**產假**/
    	if(de>0){
    		day=keyConts.monthReportTS;
    	}
    	/**特休**/
    	if(ah>0){
    		day=keyConts.monthReportP;
    	}
    	/**待工**/
    ///	if(sw>0){
    //		day=keyConts.monthReportW;
    	//}
    	return day;
    }
    
    /**
     * 月報表欄位總共欄位更新
     */
    public static void getNOTE(dayReportRO dr,monthReportNoteWO mW){
    	
    	//if(!dr.getSTATUS().equals("X")){
	    	double da=changeDouble(dr.getHOLIDAYA());
	    	
	    	double db=changeDouble(dr.getHOLIDAYB());
	    	
	  //  	double dc=changeDouble(dr.getHOLIDAYC());
	    
	    	double dd=changeDouble(dr.getHOLIDAYD());
	    	
	    	double de=changeDouble(dr.getHOLIDAYE());
	    	
	    	double df=changeDouble(dr.getHOLIDAYF());
	    	
	    //	double ae=changeDouble(dr.getATTENDANCE());
	    	
	    	double ah=changeDouble(dr.getHOLIDAYH());
	    	
	 //   	double ai=changeDouble(dr.getHOLIDAYI());
	    	
	    	double nw=changeDouble(dr.getNOTWORK());
	 //   
	 //   	double sw=changeDouble(dr.getSTOPWORK());
	   
	    
	    	/**上班**/
	    	//if(ae>0){
	    	//	mW.setMonthReportX(mW.getMonthReportX()+ae);
	    	//}
	    	/**公傷**/
	    	//if(dc>0){
	    	//	mW.setMonthReportF(mW.getMonthReportF()+dc);
	    	//}
	    	/**輪休**/
	    //	if(ai>0){
	    //		mW.setMonthReportL(mW.getMonthReportL()+ai);
	    //	}
	    	/**曠工**/
	    	if(nw>0){
	    		mW.setMonthReportO(mW.getMonthReportO()+nw);
	    	}
	    	/**病假**/
	    	if(db>0){
	    		mW.setMonthReportB(mW.getMonthReportB()+db);
	    	}
	    	/**事假**/
	    	if(da>0){
	    		mW.setMonthReportR(mW.getMonthReportR()+da);
	    	}  	
	    	/**公假**/
	    	//if(dc>0){
	    	//
	    	//	mW.setMonthReportN(mW.getMonthReportN()+dc);
	    	//}
	    	/**婚假**/
	    	if(dd>0){
	    		mW.setMonthReportH(mW.getMonthReportH()+dd);
	    	}
	    	/**喪假**/
	    	if(df>0){
	    		
	    		mW.setMonthReportT(mW.getMonthReportT()+df);
	    	}
	    	/**產假**/
	    	if(de>0){
	    		mW.setMonthReportTS(mW.getMonthReportTS()+de);
	    	}
	    	/**特休**/
	    	if(ah>0){
	    		mW.setMonthReportP(mW.getMonthReportP()+ah);
	    	}
	//    	/**待工**/
	 //   	if(sw>0){
	  //  		mW.setMonthReportW(mW.getMonthReportW()+sw);
	   // 	}
    //}
    //	return mW;
    }
    
    public static double changeDouble( String value){
    	double sw=0;
    	if(value!=null && !value.equals("")){
    		sw=Double.parseDouble(value);
    	}
    	return sw;
    }
    
    public static String changeString( String value){
    	String sw="";
    	if(value!=null && !value.equals("")){
    		sw=value;
    	}else{
    		sw="";
    	}
    	return sw;
    }
    
    public static String changeNote( monthReportNoteWO mW){
    	String note="";
    	Log4jUtil lu = new Log4jUtil();
    	Logger logger = lu.initLog4j(vnStringUtil.class);
    	double monthReportO =mW.getMonthReportO();
    
	double monthReportX =mW.getMonthReportX();
	double monthReportN =mW.getMonthReportN();//調休
	
    //	if(monthReportN>0){
    //	    monthReportX= monthReportX+monthReportN;//公假補回正班出勤	
    //	}

    	if(monthReportX>0){
    		note=note+"正班出勤:"+monthReportX+"HR <br/>";
       }
    	double monthReportB =mW.getMonthReportB();
    	if(monthReportB>0){
    	    	monthReportO= monthReportO-monthReportB;//曠工時數減去病假
    		note=note+"病假:"+monthReportB+"HR <br/>";
    	}
    	double monthReportR =mW.getMonthReportR();
    	if(monthReportR>0){
    	    	monthReportO= monthReportO-monthReportR;//曠工時數減去事假
    		note=note+"事假:"+monthReportR+"HR <br/>";
    	}  
    	if(monthReportN>0){
        	monthReportO= monthReportO-monthReportN;//曠工時數減去调休
    		note=note+"调休:"+monthReportN+"HR <br/>";
       }
    	double monthReportP =mW.getMonthReportP();
    	if(monthReportP>0){
    	    	monthReportO= monthReportO-monthReportP;//曠工時數減去特休
    		note=note+"年假:"+monthReportP+"HR <br/>";
    	}
    	double monthReportH =mW.getMonthReportH();
    	if(monthReportH>0){
    	    	monthReportO= monthReportO-monthReportH;//曠工時數減去婚假
    		note=note+"婚假:"+monthReportH+"HR <br/>";
    	}
    	double monthReportT =mW.getMonthReportT();
    	if(monthReportT>0){
    	    	monthReportO= monthReportO-monthReportT;//曠工時數減去丧假
    		note=note+"丧假:"+monthReportT+"HR <br/>";
    	}
    	double monthReportTS =mW.getMonthReportTS();
    	if(monthReportTS>0){
    	    	monthReportO= monthReportO-monthReportTS;//曠工時數減去产假
    		note=note+"产假:"+monthReportTS+"HR <br/>";
    	}
     //	double monthReportF =mW.getMonthReportF();
    //	if(monthReportF>0){
    //	    	monthReportO= monthReportO-monthReportF;//曠工時數減去公伤
    	//	note=note+"公伤:"+monthReportF+"HR <br/>";
    	//}
    	
	double monthReportOT =mW.getMonthReportOT();
    	if(monthReportOT>0){
    	    	monthReportO= monthReportO-monthReportOT;//曠工時數減去产假
    		note=note+"公假:"+monthReportOT+"HR <br/>";
    	}
    	double monthReportL =mW.getMonthReportL();
    	if(monthReportL>0){//工傷假
    	    	monthReportO= monthReportO-monthReportL;//曠工時數減去轮休
    		note=note+"工伤:"+monthReportL+"HR <br/>";
    	}
    	double monthReportW =mW.getMonthReportW();
    	if(monthReportW>0){
    	    	monthReportO= monthReportO-monthReportW;//曠工時數減去待工
    		note=note+"待工:"+monthReportW+"HR <br/>";
    	}
	if(monthReportO>0){
		note=note+"旷工:"+monthReportO+"HR <br/>";
	}
	double monthReportSL =mW.getMonthReportSL();
	if(monthReportSL>0){
		note=note+"迟到:"+monthReportSL+"分 <br/>";
	}
	double monthReportER =mW.getMonthReportER();
	if(monthReportER>0){
		note=note+"早退:"+monthReportER+"分 <br/>";
	}
    	return note;
    }
    
    /**
     * 月報表 曠職為空時補上0.0
     * @param value
     * @return
     */
    public static String changeWString( String value){
    	String sw="";
    	if(value!=null && !value.equals("") && value.length()>0){
    		sw=value;
    	}else{
    		sw="0.0";
    	}
    	return sw;
    }
    /**
     * 將空欄位換為0
     * @param value
     * @return
     */
    public static String changeColUnil(String value){
	if(value.equals("") || value==null){
	    value="0";
	}
        return value;
 }
}
