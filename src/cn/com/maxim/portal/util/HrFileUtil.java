package cn.com.maxim.portal.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class HrFileUtil
{
	/**
	 * 寫出文件 
	 * @param txt
	 * @param Path
	 */
    public static  void writeStr(String txt,String Path){
    	 FileWriter fw = null; 
    	 try
		{
    		fw = new FileWriter(Path); 
			fw.write(txt);  
			fw.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}
