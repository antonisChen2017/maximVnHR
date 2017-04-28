package cn.com.maxim.portal.bean;

import java.util.Hashtable;

import cn.com.maxim.portal.key.attendanceDayKeyConsts;

public class dayTableRowMoble
{
	 private Hashtable ht;
	 private String Control;
	public Hashtable getHt()
	{
		return ht;
	}
	public void setHt(Hashtable ht)
	{
		this.ht = ht;
	}
	public String getControl()
	{
		return Control;
	}
	public void setControl(String control)
	{
		Control = control;
	}
	public String getKey(String colIndex)
	{
		String re="";
		switch (colIndex) {
			case "4":
				re=attendanceDayKeyConsts.attendanceDayTable_R4;
			    break;
			case "5":
				re=attendanceDayKeyConsts.attendanceDayTable_R5;
			    break;
			case "6":
				re=attendanceDayKeyConsts.attendanceDayTable_R6;
			    break;
			case "7":
				re=attendanceDayKeyConsts.attendanceDayTable_R7;
			    break;
			case "8":
				re=attendanceDayKeyConsts.attendanceDayTable_R8;
			    break;
			case "9":
				re=attendanceDayKeyConsts.attendanceDayTable_R9;
			    break;
			case "11":
				re=attendanceDayKeyConsts.attendanceDayTable_R11;
			    break;
			case "12":
				re=attendanceDayKeyConsts.attendanceDayTable_R12;
			    break;
			case "13":
				re=attendanceDayKeyConsts.attendanceDayTable_R13;
			    break;
			case "15":
				re=attendanceDayKeyConsts.attendanceDayTable_R15;
			    break;
			case "16":
				re=attendanceDayKeyConsts.attendanceDayTable_R16;
			    break;
			case "17":
				re=attendanceDayKeyConsts.attendanceDayTable_R17;
			    break;
	
			case "19":
				re=attendanceDayKeyConsts.attendanceDayTable_R19;
			    break;
			case "20":
				re=attendanceDayKeyConsts.attendanceDayTable_R20;
			    break;
			case "21":
				re=attendanceDayKeyConsts.attendanceDayTable_R21;
			    break;
	
			case "23":
				re=attendanceDayKeyConsts.attendanceDayTable_R23;
			    break;
			case "24":
				re=attendanceDayKeyConsts.attendanceDayTable_R24;
			    break;
			case "25":
				re=attendanceDayKeyConsts.attendanceDayTable_R25;
			    break;
			case "26":
				re=attendanceDayKeyConsts.attendanceDayTable_R26;
			    break;
			case "27":
				re=attendanceDayKeyConsts.attendanceDayTable_R27;
			    break;
			case "28":
				re=attendanceDayKeyConsts.attendanceDayTable_R27;
			    break;
			case "29":
				re=attendanceDayKeyConsts.attendanceDayTable_R29;
			    break;
			case "30":
				re=attendanceDayKeyConsts.attendanceDayTable_R30;
			    break;
			case "31":
				re=attendanceDayKeyConsts.attendanceDayTable_R31;
			    break;
			case "32":
				re=attendanceDayKeyConsts.attendanceDayTable_R32;
			    break;
			case "33":
				re=attendanceDayKeyConsts.attendanceDayTable_R33;
			    break;
			case "35":
				re=attendanceDayKeyConsts.attendanceDayTable_R35;
			    break;
			case "36":
				re=attendanceDayKeyConsts.attendanceDayTable_R36;
			    break;
			case "37":
				re=attendanceDayKeyConsts.attendanceDayTable_R37;
			    break;
			case "39":
				re=attendanceDayKeyConsts.attendanceDayTable_R39;
			    break;
			case "40":
				re=attendanceDayKeyConsts.attendanceDayTable_R40;
			    break;
			case "41":
				re=attendanceDayKeyConsts.attendanceDayTable_R41;
			    break;
			case "43":
				re=attendanceDayKeyConsts.attendanceDayTable_R43;
			    break;
			case "44":
				re=attendanceDayKeyConsts.attendanceDayTable_R44;
			    break;
			case "45":
				re=attendanceDayKeyConsts.attendanceDayTable_R45;
			    break;
			}
		return re;
	}
	
	 
}
