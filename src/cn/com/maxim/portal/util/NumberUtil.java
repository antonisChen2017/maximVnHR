package cn.com.maxim.portal.util;

import java.text.NumberFormat;

import org.apache.log4j.Logger;

/**
 * 數字轉換
 * @author Antonis.chen
 *
 */
public class NumberUtil
{
	/**
	 * 數字變成百分比
	 * @param temp
	 * @return
	 */
	public static final String getPercentFormat(String temp)
	{
		java.text.NumberFormat percentFormat =java.text.NumberFormat.getPercentInstance(); percentFormat.setMaximumFractionDigits(2); //最大小数位数 
		percentFormat.setMaximumFractionDigits(3);//最大整数位数 
		percentFormat.setMinimumFractionDigits(1); //最小小数位数 
		if(temp.equals("-")){
			temp="0";
		}
		return percentFormat.format(Integer.valueOf(temp));//自动转换成百分比显示.. 

	}
	
	/**
	 * 兩個數字相除變成百分比
	 * @param temp
	 * @return
	 */
	public static final String getPercentFormat(int value1,int value2)
	{	
	    	Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(NumberUtil.class);
		  NumberFormat numberFormat = NumberFormat.getInstance();  
	        // 设置精确到小数点后2位  
	        numberFormat.setMaximumFractionDigits(2);  
	        String result = numberFormat.format((float) value1 / (float) value2 * 100);  
	
	        if(value2==0){
	            result="0.0";
	        }
		return result+ "%";//自动转换成百分比显示..
	}
	/**
	 * 兩個數字區間變成百分比
	 * @param temp
	 * @return
	 */
	public static final double  getBelate(double value1)
	{
		double d=0.0;
		 if(value1==0){
			  d=0.0;
		  }
		 else if(value1<=30){
			  d=0.5;
		  }
		  else  if(value1>30  &  value1<=60){
			  d=1.0;
		  }
		  else  if(value1>60  &  value1<=90 ){
			  d=1.5;
		  }
		  else  if(value1>90 &  value1<=120  ){
			  d=2.0;
		  }
		  else  if(value1>90 &  value1<=120  ){
			  d=2.0;
		  }
		  else  if(value1>120 &  value1<=150  ){
			  d=2.5;
		  }
		  else  if(value1>150 &  value1<=180  ){
			  d=3.0;
		  }
		  else  if(value1>180 &  value1<=210  ){
			  d=3.5;
		  }
		  else  if(value1>210 &  value1<=240  ){
			  d=4.0;
		  }
		  else  if(value1>240 &  value1<=270  ){
			  d=4.5;
		  }
		  else  if(value1>270 &  value1<=300  ){
			  d=5.0;
		  }
		  else  if(value1>300 &  value1<=330  ){
			  d=5.5;
		  }
		  else  if(value1>330 &  value1<=360  ){
			  d=6.0;
		  }
		  else  if(value1>360 &  value1<=390  ){
			  d=6.5;
		  }
		  else  if(value1>420 &  value1<=450  ){
			  d=7.0;
		  }
		  else  if(value1>450 &  value1<=480  ){
			  d=7.5;
		  }
		  else  if(value1>480   ){
			  d=8;
		  }
		  return d;
	}
	
}
