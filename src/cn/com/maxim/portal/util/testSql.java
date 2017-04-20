package cn.com.maxim.portal.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import cn.com.maxim.service.ExcelClientService;

public class testSql {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try
		    {
		String sql = " SELECT  *  FROM PT_MODEL";
		    
	
		  Class.forName("net.sourceforge.jtds.jdbc.Driver");
	      Connection con = DriverManager.getConnection(
	        "jdbc:jtds:sqlserver://192.168.4.199/jj;charset=gb2312", "sa", "!Q@W3e4r");
	      Statement stmt = con.createStatement();
	   //   String sql = "SELECT bh, xm FROM employee";
	      
	      ResultSet rs = stmt.executeQuery(sql);
	      if (rs.next()) 
	      {
	        String id = rs.getString("MODEL");
	        System.out.println("[MODEL]:"+id);
	       
	      }
	      rs.close();
	      stmt.close();
	      con.close();
	      
	    //  process(args[1], args[2]);
	    }
	    catch (Exception err)
	    {
	      err.printStackTrace();
	    }
	}
	
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

}
