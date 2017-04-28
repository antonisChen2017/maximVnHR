package cn.com.maxim.portal.util;

import java.text.NumberFormat;

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
		  NumberFormat numberFormat = NumberFormat.getInstance();  
	        // 设置精确到小数点后2位  
	        numberFormat.setMaximumFractionDigits(2);  
	        String result = numberFormat.format((float) value1 / (float) value2 * 100);  
	     
		return result+ "%";//自动转换成百分比显示.. 

	}
}
