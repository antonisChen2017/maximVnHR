package cn.com.maxim.portal.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.maxim.portal.attendan.ro.repAttendanceDayRO;
import cn.com.maxim.portal.bean.dayTableMoble;
import cn.com.maxim.portal.bean.repAttendanceDayBean;

public class DataStringUtil
{
	/** 事假 **/
	private int LeaveTypeA;
	/** 病假 **/
	private int LeaveTypeB;
	/** 公傷假 **/
	private int LeaveTypeC;
	/** 婚假 **/
	private int LeaveTypeD;
	/** 產假 **/
	private int LeaveTypeE;
	/** 喪假 **/
	private int LeaveTypeF;
	/** 周六調休 **/
	private int LeaveTypeG;
	/** 年假 **/
	private int LeaveTypeH;
	/** 排休 **/
	private int LeaveTypeI;
	/** 产假 **/
	private int LeaveTypeJ;
	/** 旷工 **/
	private int LeaveTypeK;
	/** 旷 **/
	private int LeaveTypeL;
	
	private static Logger logger = Logger.getLogger(DataStringUtil.class);
	
	public void setDataString(String Data)
	{
		String[] Datas = Data.split("R");
		DataStringUtil ds = new DataStringUtil();

		for (int i = 0; i < Datas.length; i++)
		{

			System.out.println("Datas  :  " + Datas[i]);
			
			if (Datas[i].indexOf("事假:") != -1)
			{
				String value = Datas[i].replaceAll("事假:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeA = LeaveTypeA + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("病假:") != -1)
			{
				String value = Datas[i].replaceAll("病假:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeB = LeaveTypeB + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("公傷假:") != -1)
			{
				String value = Datas[i].replaceAll("公傷假:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeC = LeaveTypeC + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("婚假:") != -1)
			{
				String value = Datas[i].replaceAll("婚假:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeD = LeaveTypeD + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("產假:") != -1)
			{
				String value = Datas[i].replaceAll("產假:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeE = LeaveTypeE + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("喪假:") != -1)
			{
				String value = Datas[i].replaceAll("喪假:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeF = LeaveTypeF + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("周六調休:") != -1)
			{
				String value = Datas[i].replaceAll("周六調休:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeG = LeaveTypeG + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("年假:") != -1)
			{
				String value = Datas[i].replaceAll("年假:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeH = LeaveTypeH + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("排休:") != -1)
			{
				String value = Datas[i].replaceAll("排休:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeI = LeaveTypeI + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("产假:") != -1)
			{
				String value = Datas[i].replaceAll("产假:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeJ = LeaveTypeJ + Integer.valueOf(value);
			}
			if (Datas[i].indexOf("旷工:") != -1)
			{
				String value = Datas[i].replaceAll("旷工:", "");
				value = value.replaceAll("HOU", "");
				LeaveTypeK = LeaveTypeK + Integer.valueOf(value);
			}
			
			if (Datas[i].indexOf("旷") != -1)
			{
				//String value = Datas[i].replaceAll("旷工:", "");
			//	value = value.replaceAll("HOU", "");
				LeaveTypeL = LeaveTypeL + 8;
			}
		}

	}

	public String getDataString()
	{
		String max = "";
		if (LeaveTypeA != 0)
		{
			max = max + "事假:" + LeaveTypeA + "HOUR";
		}
		if (LeaveTypeB != 0)
		{
			max = max + "病假:" + LeaveTypeB + "HOUR";
		}
		if (LeaveTypeC != 0)
		{
			max = max + "公傷假:" + LeaveTypeC + "HOUR";
		}
		if (LeaveTypeD != 0)
		{
			max = max + "婚假:" + LeaveTypeD + "HOUR";
		}
		if (LeaveTypeE != 0)
		{
			max = max + "產假:" + LeaveTypeE + "HOUR";
		}
		if (LeaveTypeF != 0)
		{
			max = max + "喪假:" + LeaveTypeF + "HOUR";
		}
		if (LeaveTypeG != 0)
		{
			max = max + "周六調休:" + LeaveTypeG + "HOUR";
		}
		if (LeaveTypeH != 0)
		{
			max = max + "排休:" + LeaveTypeH + "HOUR";
		}
		if (LeaveTypeI != 0)
		{
			max = max + "婚假:" + LeaveTypeI + "HOUR";
		}
		if (LeaveTypeJ != 0)
		{
			max = max + "产假:" + LeaveTypeJ + "HOUR";
		}
		if (LeaveTypeK != 0)
		{
			max = max + "旷工:" + LeaveTypeK + "HOUR";
		}
		if (LeaveTypeL != 0)
		{
			max = max + "旷工:" + LeaveTypeL + "HOUR";
		}
		return max;
	}

	public void clear()
	{
		LeaveTypeA = 0;
		LeaveTypeB = 0;
		LeaveTypeC = 0;
		LeaveTypeD = 0;
		LeaveTypeE = 0;
		LeaveTypeF = 0;
		LeaveTypeG = 0;
		LeaveTypeH = 0;
		LeaveTypeI = 0;
		LeaveTypeJ = 0;
		LeaveTypeK = 0;
		LeaveTypeL = 0;
	}

	/** AttendanceDayTable 判斷key取值 **/
	public static String getAttendanceDayTableDataString(List<repAttendanceDayRO> ras, String Key)
	{
		String temp = "";
		String[] Keys = Key.split("#");
		for (int i = 0; i < ras.size(); i++)
		{
			repAttendanceDayRO rA = ras.get(i);
			if (rA.getDEPARTMENT().trim().equals(Keys[0]) && rA.getUNIT().trim().equals(Keys[1]))
			{
				temp = rA.getEmpInCount();
				break;
			}
		}
		if (temp.equals(""))
		{
			temp = "-";
		}
		return temp;
	}

	/** get DayTable Row **/
	public static String getDayTableRow(List<repAttendanceDayRO> co, String Key)
	{
		String temp = getAttendanceDayTableDataString(co, Key);
		return temp;
	}

	/**
	 * get DayTable Row
	 * 
	 * @throws ParseException
	 **/
	public static repAttendanceDayBean saveDayTableRow(dayTableMoble dt, String Key) throws ParseException
	{

		repAttendanceDayBean row = new repAttendanceDayBean();
		String[] keys = Key.split("#");
		row.setC1(keys[0]);
		row.setC2(keys[1]);
		row.setC3(DataStringUtil.getDayTableRow(dt.getCo3(), Key));
		row.setC4(DataStringUtil.getDayTableRow(dt.getCo4(), Key));
		row.setC5(DataStringUtil.getDayTableRow(dt.getCo5(), Key));
		row.setC6(DataStringUtil.getDayTableRow(dt.getCo6(), Key));
		row.setC7(DataStringUtil.getDayTableRow(dt.getCo7(), Key));
		row.setC8(DataStringUtil.getDayTableRow(dt.getCo8(), Key));
		row.setC9(DataStringUtil.getDayTableRow(dt.getCo9(), Key));
		row.setC10(DataStringUtil.getDayTableRow(dt.getCo10(), Key));
		row.setC11(DataStringUtil.getDayTableRow(dt.getCo11(), Key));
		row.setC12(DataStringUtil.getDayTableRow(dt.getCo12(), Key));
		row.setC13(DataStringUtil.getDayTableRow(dt.getCo13(), Key));
		row.setC14(DataStringUtil.getDayTableRow(dt.getCo14(), Key));
		row.setC15(DataStringUtil.getDayTableRow(dt.getCo15(), Key));
		row.setC16(DataStringUtil.getDayTableRow(dt.getCo16(), Key));
		row.setC17(DataStringUtil.getDayTableRow(dt.getCo17(), Key));
		row.setC18(DataStringUtil.getDayTableRow(dt.getCo18(), Key));
		row.setC19(DataStringUtil.getDayTableRow(dt.getCo19(), Key));
		row.setC20(DataStringUtil.getDayTableRow(dt.getCo20(), Key));
		row.setC21(DataStringUtil.getDayTableRow(dt.getCo21(), Key));
		row.setC22(DataStringUtil.getDayTableRow(dt.getCo22(), Key));
		// row.setC23(DataStringUtil.getDayTableRow(dt.getCo23(),Key));
		// row.setC24(DataStringUtil.getDayTableRow(dt.getCo24(),Key));
		return row;

	}

	/**
	 * get DayTable Row
	 * 
	 * @throws ParseException
	 **/
	public static String replaceDayTableRow(String Control, String srow, int in, int out, repAttendanceDayBean row) throws ParseException
	{
		for (int i = in; i <= out; i++)
		{

			if (i == 22)
			{// 百分比
				Control = Control.replace("<r" + srow + "c" + i + "/>", NumberUtil.getPercentFormat(row.getCol(i)));
				row.setCol(i, NumberUtil.getPercentFormat(row.getCol(i)));
			}
			else
			{
				Control = Control.replace("<r" + srow + "c" + i + "/>", row.getCol(i));
			}
		}

		return Control;

	}

	

	

	

	

	/**
	 * 統計欄位數字
	 * 
	 * @param sCol
	 * @param in
	 * @param out
	 * @param dr
	 * @return
	 * @throws ParseException
	 */

	public static int addUpDayTableRow(int sCol, int count, repAttendanceDayBean rowBean) throws ParseException
	{
		// System.out.println(" updateMax 3");
		
		String sColValue=rowBean.getCol(sCol);
		boolean isColValueInt=true;
		if (sColValue.equals("-") ){
			isColValueInt=false;
		}
		if (sColValue.indexOf("%")!=-1 ){
			isColValueInt=false;
		}
		if (isColValueInt ){
			//logger.info("rowBean.getCol(sCol) :" +rowBean.getCol(sCol));
			count = count + Integer.valueOf(rowBean.getCol(sCol));
		}
		return count;
	}

	

}
