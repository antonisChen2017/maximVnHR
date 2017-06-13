package cn.com.maxim.portal.attendan.wo;

public class monthReportNoteWO
{
	/**工號**/
	private String EMPLOYEENO;
	/**年**/
	private String YEAR;
	/**月**/
	private String MONTH;
	/**1日**/
	private String DAY1;
	/**2日**/
	private String DAY2;
	/**3日**/
	private String DAY3;
	/**4日**/
	private String DAY4;
	/**5日**/
	private String DAY5;
	/**6日**/
	private String DAY6;
	/**7日**/
	private String DAY7;
	/**8日**/
	private String DAY8;
	/**9日**/
	private String DAY9;
	/**10日**/
	private String DAY10;
	/**11日**/
	private String DAY11;
	/**12日**/
	private String DAY12;
	/**13日**/
	private String DAY13;
	/**14日**/
	private String DAY14;
	/**15日**/
	private String DAY15;
	/**16日**/
	private String DAY16;
	/**17日**/
	private String DAY17;
	/**18日**/
	private String DAY18;
	/**19日**/
	private String DAY19;
	/**20日**/
	private String DAY20;
	/**21日**/
	private String DAY21;
	/**22日**/
	private String DAY22;
	/**23日**/
	private String DAY23;
	/**24日**/
	private String DAY24;
	/**25日**/
	private String DAY25;
	/**26日**/
	private String DAY26;
	/**27日**/
	private String DAY27;
	/**28日**/
	private String DAY28;
	/**29日**/
	private String DAY29;
	/**30日**/
	private String DAY30;
	/**31日**/
	private String DAY31;
	/**總共**/
	private String NOTE;
	/**上班**/
	private double monthReportX;
	/**輪休**/
	private double monthReportL;
	/**曠工**/
	private double monthReportO;
  	/**病假**/
	private double monthReportB;
  	/**事假**/
	private double monthReportR;
	/**公假**/
	private double monthReportN;
	/**婚假**/
	private double monthReportH;
	/**喪假**/
	private double monthReportT;
	/**產假**/
	private double monthReportTS;
	/**公傷**/
	private double monthReportF;
	/**待工**/
	private double monthReportW;
	/**特休**/
	private double monthReportP;
	
	
	public void setDay(String colIndex,String Value)
	{
		switch (colIndex) {
			case "01":
				setDAY1(Value);
			    break;
			case "02":
				setDAY2(Value);	
			    break;
			case "03":
				setDAY3(Value);
			    break;
			case "04":
				setDAY4(Value);
			    break;
			case "05":
				setDAY5(Value);
			    break;
			case "06":
				setDAY6(Value);
			    break;
			case "07":
				setDAY7(Value);
			    break;
			case "08":
				setDAY8(Value);
			    break;
			case "09":
				setDAY9(Value);
			    break;
			case "10":
				setDAY10(Value);
			    break;
			case "11":
				setDAY11(Value);
			    break;
			case "12":
				setDAY12(Value);
			    break;
			case "13":
				setDAY13(Value);
			    break;
			case "14":
				setDAY14(Value);
			    break;
			case "15":
				setDAY15(Value);
			    break;
			case "16":
				setDAY16(Value);
			    break;
			case "17":
				setDAY17(Value);
			    break;
			case "18":
				setDAY18(Value);
			    break;
			case "19":
				setDAY19(Value);
			    break;
			case "20":
				setDAY20(Value);
			    break;
			case "21":
				setDAY21(Value);
			    break;
			case  "22":
				setDAY22(Value);
			    break;
			case "23":
				setDAY23(Value);
			    break;
			case "24":
				setDAY24(Value);
			    break;
			case "25":
				setDAY25(Value);
			    break;
			case "26":
				setDAY26(Value);
			    break;
			case "27":
				setDAY27(Value);
			    break;
			case "28":
				setDAY28(Value);
			    break;
			case "29":
				setDAY29(Value);
			    break;
			case "30":
				setDAY30(Value);
			    break;
			case "31":
				setDAY31(Value);
			    break;
			}
	}
	
	public String getYEAR()
	{
		return YEAR;
	}
	public void setYEAR(String yEAR)
	{
		YEAR = yEAR;
	}
	public String getMONTH()
	{
		return MONTH;
	}
	public void setMONTH(String mONTH)
	{
		MONTH = mONTH;
	}
	public String getDAY1()
	{
		return DAY1;
	}
	public void setDAY1(String dAY1)
	{
		DAY1 = dAY1;
	}
	public String getDAY2()
	{
		return DAY2;
	}
	public void setDAY2(String dAY2)
	{
		DAY2 = dAY2;
	}
	public String getDAY3()
	{
		return DAY3;
	}
	public void setDAY3(String dAY3)
	{
		DAY3 = dAY3;
	}
	public String getDAY4()
	{
		return DAY4;
	}
	public void setDAY4(String dAY4)
	{
		DAY4 = dAY4;
	}
	public String getDAY5()
	{
		return DAY5;
	}
	public void setDAY5(String dAY5)
	{
		DAY5 = dAY5;
	}
	public String getDAY6()
	{
		return DAY6;
	}
	public void setDAY6(String dAY6)
	{
		DAY6 = dAY6;
	}
	public String getDAY7()
	{
		return DAY7;
	}
	public void setDAY7(String dAY7)
	{
		DAY7 = dAY7;
	}
	public String getDAY8()
	{
		return DAY8;
	}
	public void setDAY8(String dAY8)
	{
		DAY8 = dAY8;
	}
	public String getDAY9()
	{
		return DAY9;
	}
	public void setDAY9(String dAY9)
	{
		DAY9 = dAY9;
	}
	public String getDAY10()
	{
		return DAY10;
	}
	public void setDAY10(String dAY10)
	{
		DAY10 = dAY10;
	}
	public String getDAY11()
	{
		return DAY11;
	}
	public void setDAY11(String dAY11)
	{
		DAY11 = dAY11;
	}
	public String getDAY12()
	{
		return DAY12;
	}
	public void setDAY12(String dAY12)
	{
		DAY12 = dAY12;
	}
	public String getDAY13()
	{
		return DAY13;
	}
	public void setDAY13(String dAY13)
	{
		DAY13 = dAY13;
	}
	public String getDAY14()
	{
		return DAY14;
	}
	public void setDAY14(String dAY14)
	{
		DAY14 = dAY14;
	}
	public String getDAY15()
	{
		return DAY15;
	}
	public void setDAY15(String dAY15)
	{
		DAY15 = dAY15;
	}
	public String getDAY16()
	{
		return DAY16;
	}
	public void setDAY16(String dAY16)
	{
		DAY16 = dAY16;
	}
	public String getDAY17()
	{
		return DAY17;
	}
	public void setDAY17(String dAY17)
	{
		DAY17 = dAY17;
	}
	public String getDAY18()
	{
		return DAY18;
	}
	public void setDAY18(String dAY18)
	{
		DAY18 = dAY18;
	}
	public String getDAY19()
	{
		return DAY19;
	}
	public void setDAY19(String dAY19)
	{
		DAY19 = dAY19;
	}
	public String getDAY20()
	{
		return DAY20;
	}
	public void setDAY20(String dAY20)
	{
		DAY20 = dAY20;
	}
	public String getDAY21()
	{
		return DAY21;
	}
	public void setDAY21(String dAY21)
	{
		DAY21 = dAY21;
	}
	public String getDAY22()
	{
		return DAY22;
	}
	public void setDAY22(String dAY22)
	{
		DAY22 = dAY22;
	}
	public String getDAY23()
	{
		return DAY23;
	}
	public void setDAY23(String dAY23)
	{
		DAY23 = dAY23;
	}
	public String getDAY24()
	{
		return DAY24;
	}
	public void setDAY24(String dAY24)
	{
		DAY24 = dAY24;
	}
	public String getDAY25()
	{
		return DAY25;
	}
	public void setDAY25(String dAY25)
	{
		DAY25 = dAY25;
	}
	public String getDAY26()
	{
		return DAY26;
	}
	public void setDAY26(String dAY26)
	{
		DAY26 = dAY26;
	}
	public String getDAY27()
	{
		return DAY27;
	}
	public void setDAY27(String dAY27)
	{
		DAY27 = dAY27;
	}
	public String getDAY28()
	{
		return DAY28;
	}
	public void setDAY28(String dAY28)
	{
		DAY28 = dAY28;
	}
	public String getDAY29()
	{
		return DAY29;
	}
	public void setDAY29(String dAY29)
	{
		DAY29 = dAY29;
	}
	public String getDAY30()
	{
		return DAY30;
	}
	public void setDAY30(String dAY30)
	{
		DAY30 = dAY30;
	}
	public String getDAY31()
	{
		return DAY31;
	}
	public void setDAY31(String dAY31)
	{
		DAY31 = dAY31;
	}
	public String getNOTE()
	{
		return NOTE;
	}
	public void setNOTE(String nOTE)
	{
		NOTE = nOTE;
	}
	public double getMonthReportP()
	{
		return monthReportP;
	}
	public void setMonthReportP(double monthReportP)
	{
		this.monthReportP = monthReportP;
	}
	public double getMonthReportW()
	{
		return monthReportW;
	}
	public void setMonthReportW(double monthReportW)
	{
		this.monthReportW = monthReportW;
	}
	public double getMonthReportL()
	{
		return monthReportL;
	}
	public void setMonthReportL(double monthReportL)
	{
		this.monthReportL = monthReportL;
	}
	public double getMonthReportF()
	{
		return monthReportF;
	}
	public void setMonthReportF(double monthReportF)
	{
		this.monthReportF = monthReportF;
	}
	public double getMonthReportO()
	{
		return monthReportO;
	}
	public void setMonthReportO(double monthReportO)
	{
		this.monthReportO = monthReportO;
	}
	public double getMonthReportB()
	{
		return monthReportB;
	}
	public void setMonthReportB(double monthReportB)
	{
		this.monthReportB = monthReportB;
	}
	public double getMonthReportR()
	{
		return monthReportR;
	}
	public void setMonthReportR(double monthReportR)
	{
		this.monthReportR = monthReportR;
	}
	public double getMonthReportN()
	{
		return monthReportN;
	}
	public void setMonthReportN(double monthReportN)
	{
		this.monthReportN = monthReportN;
	}
	public double getMonthReportH()
	{
		return monthReportH;
	}
	public void setMonthReportH(double monthReportH)
	{
		this.monthReportH = monthReportH;
	}
	public double getMonthReportT()
	{
		return monthReportT;
	}
	public void setMonthReportT(double monthReportT)
	{
		this.monthReportT = monthReportT;
	}
	public double getMonthReportTS()
	{
		return monthReportTS;
	}
	public void setMonthReportTS(double monthReportTS)
	{
		this.monthReportTS = monthReportTS;
	}
	public String getEMPLOYEENO()
	{
		return EMPLOYEENO;
	}
	public void setEMPLOYEENO(String eMPLOYEENO)
	{
		EMPLOYEENO = eMPLOYEENO;
	}
	public double getMonthReportX()
	{
		return monthReportX;
	}
	public void setMonthReportX(double monthReportX)
	{
		this.monthReportX = monthReportX;
	}
	
	
}
