package cn.com.maxim.portal.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;



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
    
    public static void  WriteFileBytes(byte[]  File,String FileName)
	{
                Log4jUtil lu = new Log4jUtil();
  		Logger logger = lu.initLog4j(HrFileUtil.class);
  		RandomAccessFile file_test = null;  
  		try {  
  		    File file = new File("D:\\mxportal\\tmp\\"+FileName);  //后缀自己定义  
  		    if (file.exists())  
  		        file.delete();  
  		        file_test = new RandomAccessFile(file, "rw");   //可读写  
  		} catch (Exception ex) {  
  		    logger.info("read"+ex.toString());  
  		}  
  		  
  		//调用  
  		try {  
  		    file_test.write(File);
  		} catch (Exception e) {  
  		logger.info("write"+e.getMessage());  
  		}  
	}
}
