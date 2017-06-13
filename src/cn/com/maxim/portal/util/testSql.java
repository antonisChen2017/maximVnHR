package cn.com.maxim.portal.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;




public class testSql {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		// try
		//    {
		String sql = " SELECT  *  FROM VN_CONFIG";
		    
	
		/*  Class.forName("net.sourceforge.jtds.jdbc.Driver");
	      Connection con = DriverManager.getConnection(
	        "jdbc:jtds:sqlserver://192.168.4.199:1434/hr;charset=gb2312", "sa", "!Q@W3e4r");
	      Statement stmt = con.createStatement();
	   //   String sql = "SELECT bh, xm FROM employee";
	      
	      ResultSet rs = stmt.executeQuery(sql);
	      if (rs.next()) 
	      {
	        String id = rs.getString("VALUE");
	        System.out.println("VALUE :"+id);
	       
	      }
	      rs.close();
	      stmt.close();
	      con.close();
	      
	    //  process(args[1], args[2]);
	    }
	    catch (Exception err)
	    {
	      err.printStackTrace();
	    }*/
		//System.out.println(generateShortUuid());
	//}
	
	/*public static void main(String[] args) {
		
		 String s = "<table><tbody><tr><td>斕疑</td><td>bb</td></tr><tr><td>cc</td><td>dd</td></tr></tbody></table>";
		    ExcelClientService ecs = new ExcelClientService();
		    Vector datas = ecs.process(s);
		    for (int i = 0; i < datas.size(); i++)
		    {
		      Vector data = (Vector)datas.get(i);
		      for (int j = 0; j < data.size(); j++) {
		        System.out.println(data.get(j));
		      }
		    }
	
	}
	*/

	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
 
 
public static String generateShortUuid() {
    StringBuffer shortBuffer = new StringBuffer();
    String uuid = UUID.randomUUID().toString().replace("-", "");
    for (int i = 0; i < 8; i++) {
        String str = uuid.substring(i * 4, i * 4 + 4);
        int x = Integer.parseInt(str, 16);
        shortBuffer.append(chars[x % 0x3E]);
    }
    return shortBuffer.toString();
 
}
public static ArrayList getDates(String year, String month) {
	int maxDate = 0;
	Date first = null;
	Date two = null;
	Date[] dates =null;
	String newDay="";
	ArrayList listDates=new ArrayList();
	try {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		first = sdf.parse(year + month);
		cal.setTime(first);
		maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);

	//	 System.out.println("maxDate="+maxDate);
	SimpleDateFormat srf = new SimpleDateFormat("yyyy/MM/dd");
	 dates = new Date[maxDate];
	for (int i = 1; i <= maxDate; i++) {
		dates[i - 1] = new Date(first.getTime());
		first.setDate(first.getDate() + 1);
		newDay=srf.format(dates[i - 1]);
	
	    cal.setTime(dates[i - 1]);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		 {
		//	System.out.println("no SUNDAY");
		 }else{
			 if(newDay.split("/")[1].equals(month)) {
				 System.out.println(newDay);
				 listDates.add(newDay);
			 }
		 }
	
		//System.out.println(String.valueOf(dates[i - 1]));
	}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listDates;
}


public static int getSun(String year, String month) {
	int maxDate = 0;
	Date first = null;
	Date two = null;
	Date[] dates =null;
	String newDay="";
	ArrayList listDates=new ArrayList();
	int sun=0;
	try {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		first = sdf.parse(year + month);
		cal.setTime(first);
		maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);

	//	 System.out.println("maxDate="+maxDate);
	SimpleDateFormat srf = new SimpleDateFormat("yyyy/MM/dd");
	 dates = new Date[maxDate];
	for (int i = 1; i <= maxDate; i++) {
		dates[i - 1] = new Date(first.getTime());
		first.setDate(first.getDate() + 1);
		newDay=srf.format(dates[i - 1]);
	
	    cal.setTime(dates[i - 1]);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		 {
			sun++;
		 }
	
		//System.out.println(String.valueOf(dates[i - 1]));
	}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return sun*8;
}
public static void main(String[] args) {
	//getDates("2017", "05");
	
	System.out.println(getSun("2017", "05"));
}
}
