package cn.com.maxim.portal.util;

public class vnStringUtil
{
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
}
