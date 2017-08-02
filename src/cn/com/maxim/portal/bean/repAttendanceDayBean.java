package cn.com.maxim.portal.bean;
/**
 * 全廠日報表物件
 * @author Antonis.chen
 *
 */
public class repAttendanceDayBean
{
	  /**部**/
	  private   String c1;
	  /**单位**/
	  private   String c2;
	  /**外籍幹部人數**/
	  private   String c3;
	  /**前一天越籍出勤人數**/
	  private   String c4;
	  /**越籍出勤人數**/
	  private   String c5;
	  /**實際出勤人數**/
	  private   String c6;
	  /**行政班**/
	  private   String c7;
	  /**早班**/
	  private   String c8;
	  /**中班**/
	  private   String c9;
	  /**夜班**/
	  private   String c10;
	  /**特加班次**/
	  private   String c11;
	  /**產假**/
	  private   String c12;
	  /**請假**/
	  private   String c13;
	  /**年假**/
	  private   String c14;
	  /**出差**/
	  private   String c15;
	  /**工傷**/
	  private   String c16;
	  /**曠工**/
	  private   String c17;
	  /**周六排休**/
	  private   String c18;
	  /**新人報到**/
	  private   String c19;
	  /**調動**/
	  private   String c20;
	  /**離職**/
	  private   String c21;
	  /**離職率**/
	  private   String c22;
	  
	public String getC1()
	{
		return c1;
	}
	public void setC1(String c1)
	{
		this.c1 = c1;
	}
	public String getC2()
	{
		return c2;
	}
	public void setC2(String c2)
	{
		this.c2 = c2;
	}
	public String getC3()
	{
		return c3;
	}
	public void setC3(String c3)
	{
		this.c3 = c3;
	}
	public String getC4()
	{
		return c4;
	}
	public void setC4(String c4)
	{
		this.c4 = c4;
	}
	public String getC5()
	{
		return c5;
	}
	public void setC5(String c5)
	{
		this.c5 = c5;
	}
	public String getC6()
	{
		return c6;
	}
	public void setC6(String c6)
	{
		this.c6 = c6;
	}
	public String getC7()
	{
		return c7;
	}
	public void setC7(String c7)
	{
		this.c7 = c7;
	}
	public String getC8()
	{
		return c8;
	}
	public void setC8(String c8)
	{
		this.c8 = c8;
	}
	public String getC9()
	{
		return c9;
	}
	public void setC9(String c9)
	{
		this.c9 = c9;
	}
	public String getC10()
	{
		return c10;
	}
	public void setC10(String c10)
	{
		this.c10 = c10;
	}
	public String getC11()
	{
		return c11;
	}
	public void setC11(String c11)
	{
		this.c11 = c11;
	}
	public String getC12()
	{
		return c12;
	}
	public void setC12(String c12)
	{
		this.c12 = c12;
	}
	public String getC13()
	{
		return c13;
	}
	public void setC13(String c13)
	{
		this.c13 = c13;
	}
	public String getC14()
	{
		return c14;
	}
	public void setC14(String c14)
	{
		this.c14 = c14;
	}
	public String getC15()
	{
		return c15;
	}
	public void setC15(String c15)
	{
		this.c15 = c15;
	}
	public String getC16()
	{
		return c16;
	}
	public void setC16(String c16)
	{
		this.c16 = c16;
	}
	public String getC17()
	{
		return c17;
	}
	public void setC17(String c17)
	{
		this.c17 = c17;
	}
	public String getC18()
	{
		return c18;
	}
	public void setC18(String c18)
	{
		this.c18 = c18;
	}
	public String getC19()
	{
		return c19;
	}
	public void setC19(String c19)
	{
		this.c19 = c19;
	}
	public String getC20()
	{
		return c20;
	}
	public void setC20(String c20)
	{
		this.c20 = c20;
	}
	public String getC21()
	{
		return c21;
	}
	public void setC21(String c21)
	{
		this.c21 = c21;
	}
	public String getC22()
	{
		return c22;
	}
	public void setC22(String c22)
	{
		this.c22 = c22;
	}
	
	  
	public String getCol(int colIndex)
	{
		String re="";
		switch (colIndex) {
			case 1:
				re=getC1();
			    break;
			case 2:
				re=getC2();
			    break;
			case 3:
				re=getC3();
			    break;
			case 4:
				re=getC4();
			    break;
			case 5:
				re=getC5();
			    break;
			case 6:
				re=getC6();
			    break;
			case 7:
				re=getC7();
			    break;
			case 8:
				re=getC8();
			    break;
			case 9:
				re=getC9();
			    break;
			case 10:
				re=getC10();
			    break;
			case 11:
				re=getC11();
			    break;
			case 12:
				re=getC12();
			    break;
			case 13:
				re=getC13();
			    break;
			case 14:
				re=getC14();
			    break;
			case 15:
				re=getC15();
			    break;
			case 16:
				re=getC16();
			    break;
			case 17:
				re=getC17();
			    break;
			case 18:
				re=getC18();
			    break;
			case 19:
				re=getC19();
			    break;
			case 20:
				re=getC20();
			    break;
			case 21:
				re=getC21();
			    break;
			case 22:
				re=getC22();
			    break;
		
			}
		return re;
	}
	  
	
	public void setCol(int colIndex,String Value)
	{
		switch (colIndex) {
			case 1:
				   setC1(Value);
			    break;
			case 2:
				   setC2(Value);
			    break;
			case 3:
				   setC3(Value);
			    break;
			case 4:
				   setC4(Value);
			    break;
			case 5:
				 setC5(Value);
			    break;
			case 6:
				 setC6(Value);
			    break;
			case 7:
				 setC7(Value);
			    break;
			case 8:
				 setC8(Value);
			    break;
			case 9:
				 setC9(Value);
			    break;
			case 10:
				 setC10(Value);
			    break;
			case 11:
				 setC11(Value);
			    break;
			case 12:
				 setC12(Value);
			    break;
			case 13:
				 setC13(Value);
			    break;
			case 14:
				 setC14(Value);
			    break;
			case 15:
				 setC15(Value);
			    break;
			case 16:
				 setC16(Value);
			    break;
			case 17:
				 setC17(Value);
			    break;
			case 18:
				 setC18(Value);
			    break;
			case 19:
				 setC19(Value);
			    break;
			case 20:
				 setC20(Value);
			    break;
			case 21:
				 setC21(Value);
			    break;
			case 22:
				 setC22(Value);
			    break;
			}
	}
	  
}
