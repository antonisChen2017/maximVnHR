package cn.com.maxim.portal.attendan.wo;

public class dayAttendanceWO
{
	/** 年月日 **/
	private String  YMD;
	/** 行 **/
	private String  ROW;
	/** 部门 **/
	private String  DEPARTMENT;
	/** 单位 **/
	private String  UNIT;
	/** 欄位C1 **/
	private String  C1;
	/** 欄位C2 **/
	private String  C2;
	/** 欄位C3 **/
	private String  C3;
	/** 欄位C4 **/
	private String  C4;
	/** 欄位C5 **/
	private String  C5;
	/** 欄位C6**/
	private String  C6;
	/** 欄位C7 **/
	private String  C7;
	/** 欄位C8 **/
	private String  C8;
	/** 欄位C9 **/
	private String  C9;
	/** 欄位C10 **/
	private String  C10;
	/** 欄位C11 **/
	private String  C11;
	/** 欄位C12 **/
	private String  C12;
	/** 欄位C13 **/
	private String  C13;
	/** 欄位C14 **/
	private String  C14;
	/** 欄位C6**/
	private String  C15;
	/** 欄位C16 **/
	private String  C16;
	/** 欄位C17 **/
	private String  C17;
	/** 欄位C18 **/
	private String  C18;
	/** 欄位C19 **/
	private String  C19;
	/** 欄位C20 **/
	private String  C20;
	/** 越籍年資分布 欄位YEAR **/
	private String  YEAR;
	/** 越籍年資分布 欄位YMDKEY **/
	private String  YMDKEY;
	/** 越籍年資分布 欄位EMPNUM **/
	private String  EMPNUM;
	/** 越籍年資分布 欄位PERCENTAGE **/
	private String  PERCENTAGE;
	
	public String getYMD()
	{
		return YMD;
	}
	public void setYMD(String yMD)
	{
		YMD = yMD;
	}
	public String getDEPARTMENT()
	{
		return DEPARTMENT;
	}
	public void setDEPARTMENT(String dEPARTMENT)
	{
		DEPARTMENT = dEPARTMENT;
	}
	public String getUNIT()
	{
		return UNIT;
	}
	public void setUNIT(String uNIT)
	{
		UNIT = uNIT;
	}
	public String getROW()
	{
		return ROW;
	}
	public void setROW(String rOW)
	{
		ROW = rOW;
	}
	public String getC1()
	{
		return C1;
	}
	public void setC1(String c1)
	{
		C1 = c1;
	}
	public String getC2()
	{
		return C2;
	}
	public void setC2(String c2)
	{
		C2 = c2;
	}
	public String getC3()
	{
		return C3;
	}
	public void setC3(String c3)
	{
		C3 = c3;
	}
	public String getC4()
	{
		return C4;
	}
	public void setC4(String c4)
	{
		C4 = c4;
	}
	public String getC5()
	{
		return C5;
	}
	public void setC5(String c5)
	{
		C5 = c5;
	}
	public String getC6()
	{
		return C6;
	}
	public void setC6(String c6)
	{
		C6 = c6;
	}
	public String getC7()
	{
		return C7;
	}
	public void setC7(String c7)
	{
		C7 = c7;
	}
	public String getC8()
	{
		return C8;
	}
	public void setC8(String c8)
	{
		C8 = c8;
	}
	public String getC9()
	{
		return C9;
	}
	public void setC9(String c9)
	{
		C9 = c9;
	}
	public String getC10()
	{
		return C10;
	}
	public void setC10(String c10)
	{
		C10 = c10;
	}
	public String getC11()
	{
		return C11;
	}
	public void setC11(String c11)
	{
		C11 = c11;
	}
	public String getC12()
	{
		return C12;
	}
	public void setC12(String c12)
	{
		C12 = c12;
	}
	public String getC13()
	{
		return C13;
	}
	public void setC13(String c13)
	{
		C13 = c13;
	}
	public String getC14()
	{
		return C14;
	}
	public void setC14(String c14)
	{
		C14 = c14;
	}
	public String getC15()
	{
		return C15;
	}
	public void setC15(String c15)
	{
		C15 = c15;
	}
	public String getC16()
	{
		return C16;
	}
	public void setC16(String c16)
	{
		C16 = c16;
	}
	public String getC17()
	{
		return C17;
	}
	public void setC17(String c17)
	{
		C17 = c17;
	}
	public String getC18()
	{
		return C18;
	}
	public void setC18(String c18)
	{
		C18 = c18;
	}
	public String getC19()
	{
		return C19;
	}
	public void setC19(String c19)
	{
		C19 = c19;
	}
	public String getC20()
	{
		return C20;
	}
	public void setC20(String c20)
	{
		C20 = c20;
	}
	
	
	
	public String getCol(int colIndex)
	{
		String re="";
		switch (colIndex) {
			case 1:
				re=getYMD();
			    break;
			case 2:
				re=getROW();
			    break;
			case 3:
				re=getDEPARTMENT();
			    break;
			case 4:
				re=getUNIT();
			    break;
			case 5:
				re=getC1();
			    break;
			case 6:
				re=getC2();
			    break;
			case 7:
				re=getC3();
			    break;
			case 8:
				re=getC4();
			    break;
			case 9:
				re=getC5();
			    break;
			case 10:
				re=getC6();
			    break;
			case 11:
				re=getC7();
			    break;
			case 12:
				re=getC8();
			    break;
			case 13:
				re=getC9();
			    break;
			case 14:
				re=getC10();
			    break;
			case 15:
				re=getC11();
			    break;
			case 16:
				re=getC12();
			    break;
			case 17:
				re=getC13();
			    break;
			case 18:
				re=getC14();
			    break;
			case 19:
				re=getC15();
			    break;
			case 20:
				re=getC16();
			    break;
			case 21:
				re=getC17();
			    break;
			case 22:
				re=getC18();
			    break;
			case 23:
				re=getC19();
			    break;
			case 24:
				re=getC20();
			    break;
			
			
		
			}
		return re;
	}
	  
	
	public String getYEAR()
	{
		return YEAR;
	}
	public void setYEAR(String yEAR)
	{
		YEAR = yEAR;
	}
	public String getEMPNUM()
	{
		return EMPNUM;
	}
	public void setEMPNUM(String eMPNUM)
	{
		EMPNUM = eMPNUM;
	}
	public String getPERCENTAGE()
	{
		return PERCENTAGE;
	}
	public void setPERCENTAGE(String pERCENTAGE)
	{
		PERCENTAGE = pERCENTAGE;
	}
	
	
	public String getYMDKEY()
	{
		return YMDKEY;
	}
	public void setYMDKEY(String yMDKEY)
	{
		YMDKEY = yMDKEY;
	}
	
	public void setCol(int colIndex,String Value)
	{
		switch (colIndex) {
			case 1:
				setYMD(Value);
			    break;
			case 2:
				setROW(Value);	
			    break;
			case 3:
				setDEPARTMENT(Value);
			    break;
			case 4:
				setUNIT(Value);
			    break;
			case 5:
				 setC1(Value);
			    break;
			case 6:
				 setC2(Value);
			    break;
			case 7:
				 setC3(Value);
			    break;
			case 8:
				 setC4(Value);
			    break;
			case 9:
				 setC5(Value);
			    break;
			case 10:
				 setC6(Value);
			    break;
			case 11:
				 setC7(Value);
			    break;
			case 12:
				 setC8(Value);
			    break;
			case 13:
				 setC9(Value);
			    break;
			case 14:
				 setC10(Value);
			    break;
			case 15:
				 setC11(Value);
			    break;
			case 16:
				 setC12(Value);
			    break;
			case 17:
				 setC13(Value);
			    break;
			case 18:
				 setC14(Value);
			    break;
			case 19:
				 setC15(Value);
			    break;
			case 20:
				 setC16(Value);
			    break;
			case 21:
				 setC17(Value);
			    break;
			case 22:
				 setC18(Value);
			    break;
			case 23:
				 setC19(Value);
			    break;
			case 24:
				 setC20(Value);
			    break;
			}
	}
	
}
