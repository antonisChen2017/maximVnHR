package cn.com.maxim.potral.consts;


import java.util.WeakHashMap;

public class TranslateConsts
{
	
	
	   public static String tw2cn( String key) throws Exception {
		 //  WeakHashMap weakMap = new WeakHashMap<String, String>();
		   	key=key.replaceAll("工號", "工号");
		   	key=key.replaceAll("查询", "查询");
			key=key.replaceAll("全廠日出勤狀況報表", "全厂日出勤状况报表");
			key=key.replaceAll("单位", "单位");
			key=key.replaceAll("總階", "总阶");
			key=key.replaceAll("副總辦公室", "副总办公室");
	        return key;
	    }
}
