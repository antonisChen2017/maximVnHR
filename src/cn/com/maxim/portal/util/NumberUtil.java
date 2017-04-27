package cn.com.maxim.portal.util;
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
}
