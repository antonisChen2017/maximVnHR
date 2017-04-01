package cn.com.maxim.portal.util;

import cn.com.maxim.DB.DBManager;
import cn.com.maxim.portal.ErrorPortal;
import cn.com.maxim.portal.PortalManager;
import cn.com.maxim.portal.UserDescriptor;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.input.SAXBuilder;

public class Utilities {
	  public static final int NO_LENGTH = 6;
	  public static final int DAY_NORMAL = 0;
	  public static final int DAY_REST = 1;
	  public static final int DAY_HOLIDAY = 2;
	  public static final int DAY_NONE = 4;
	  public static final int DAY_SATDUTY = 8;
	  public static final int TYPE_QJ = 0;
	  public static final int TYPE_JB = 1;
	  public static final int TYPE_WC = 2;
	  public static final String ROLE_ADMIN = "HR_ADMIN";
	  public static final String ROLE_KQY = "HR_KQY";
	  public static final String ROLE_EMP = "HR_EMP";
	  public static final String ROLE_MANAGER = "HR_MANAGER";
	  public static final String ROLE_DLR = "HR_DLR";
	  public static String DBHR = "HRDB";
	  public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  public static DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	  public static final SAXBuilder saxBuilder = new SAXBuilder();
	  
	  public static Connection getHRConnection()
	  {
	    return PortalManager.getDBA().getConnection(DBHR);
	  }
	  
	  public static void releaseHRConnection(Connection con)
	  {
	    PortalManager.getDBA().freeConnection(DBHR, con);
	  }
	  
	  public static Connection getMainConnection()
	  {
	    return PortalManager.getDBA().getConnection("HR");
	  }
	  
	  public static void releaseMainConnection(Connection con)
	  {
	    PortalManager.getDBA().freeConnection("HR", con);
	  }
	  
	  public static void alert(HttpServletRequest request, HttpServletResponse response, UserDescriptor userInfo, String actionURI, Exception err, PrintWriter out)
	  {
	    PortalManager.ForErrorPortal.setErrorLevel("Portalet Level");
	    PortalManager.ForErrorPortal.setErrorType("Inner Error");
	    PortalManager.ForErrorPortal.setErrorMessage(err.getMessage());
	    PortalManager.ForErrorPortal.setSuggestion("");
	    PortalManager.ForErrorPortal.drawPanel(request, response, userInfo, out, actionURI, null);
	    out.println("<pre>");
	    err.printStackTrace(out);
	    out.println("</pre>");
	  }
	  
	  public static synchronized String getSer(String table, String field, String prefix, Connection con)
	  {
	    try
	    {
	      Calendar c = Calendar.getInstance();
	      int year = c.get(1);
	      int month = c.get(2);
	      int day = c.get(5);
	      

	      String preday = year + (month >= 9 ? "" : "0") + (month + 1) + (
	        day > 9 ? "" : "0") + day;
	      preday = preday.substring(2);
	      Statement stmt = con.createStatement();
	      String sql = "SELECT " + field + " FROM " + table + " WHERE " + field + " LIKE '" + 
	        prefix + preday + "%' ORDER BY " + 
	        field + " DESC";
	      int current = 0;
	      ResultSet rs = stmt.executeQuery(sql);
	      if (rs.next())
	      {
	        String value = rs.getString(1);
	        value = value.substring(value.length() - 4, value.length());
	        current = Integer.parseInt(value);
	      }
	      current++;
	      String value = String.valueOf(current);
	      for (int i = value.length(); i < 4; i++) {
	        value = "0" + value;
	      }
	      rs.close();
	      stmt.close();
	      return prefix + preday + value;
	    }
	    catch (Exception err)
	    {
	      err.printStackTrace(System.out);
	    }
	    return null;
	  }
	  
	  public static synchronized String getNextId(String table, String field, String prefix, Connection con)
	  {
	    try
	    {
	      Calendar c = Calendar.getInstance();
	      int year = c.get(1);
	      int month = c.get(2);
	      int day = c.get(5);
	      String today = year + "-" + (month >= 9 ? "" : "0") + (month + 1) + 
	        "-" + (day > 9 ? "" : "0") + day;
	      String preday = year + (month >= 9 ? "" : "0") + (month + 1) + (
	        day > 9 ? "" : "0") + day;
	      preday = preday.substring(2);
	      Statement stmt = con.createStatement();
	      String sql = "SELECT " + field + " FROM " + table + " WHERE req_date BETWEEN '" + 
	        today + " 00:00:00' AND '" + today + " 23:59:59' ORDER BY req_date, " + 
	        field + " DESC";
	      int current = 0;
	      ResultSet rs = stmt.executeQuery(sql);
	      if (rs.next())
	      {
	        String value = rs.getString(1);
	        value = value.substring(value.length() - 4, value.length());
	        current = Integer.parseInt(value);
	      }
	      current++;
	      String value = String.valueOf(current);
	      for (int i = value.length(); i < 4; i++) {
	        value = "0" + value;
	      }
	      rs.close();
	      stmt.close();
	      return prefix + preday + value;
	    }
	    catch (Exception err)
	    {
	      err.printStackTrace(System.out);
	    }
	    return null;
	  }
	  
	  public static HashSet getUserRole(Connection con, String user)
	  {
	    HashSet roles = new HashSet(4);
	    user = lPad(user);
	    String sql = "SELECT role FROM vh_user_role WHERE login='" + user + "'";
	    try
	    {
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(sql);
	      while (rs.next())
	      {
	        String role = rs.getString("role");
	        roles.add(role);
	      }
	      rs.close();
	      stmt.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return roles;
	  }
	  
	  public static String getUserDept(Connection con, String user)
	    throws SQLException
	  {
	    String dept = null;
	    //user = Integer.parseInt(user);
	    Statement stmt = con.createStatement();
	    String sql = "SELECT bmbh FROM employee where bh='" + user + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    if (rs.next()) {
	      dept = rs.getString("bmbh");
	    }
	    rs.close();
	    stmt.close();
	    return dept;
	  }
	  
	  public static String getDepKqy(Connection con, String dep)
	    throws SQLException
	  {
	    String result = null;
	    


	    Statement stmt = con.createStatement();
	    
	    String sql = "SELECT kqy AS bh FROM hr_kqy_info WHERE dep_no='" + dep + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    if (rs.next()) {
	      result = rs.getString("bh");
	    }
	    rs.close();
	    stmt.close();
	    return result;
	  }
	  
	  public static Vector getApprovesFromEmp(Connection con, String emp)
	  {
	    Vector approves = new Vector();
	    String sql = "SELECT approve1, approve2, approve3 FROM hr_kq_approveperson where emp='" + emp + "'";
	    try
	    {
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(sql);
	      if (rs.next())
	      {
	        String approve1 = rs.getString("approve1");
	        String approve2 = rs.getString("approve2");
	        String approve3 = rs.getString("approve3");
	        if (approve1 != null) {
	          approves.add(approve1);
	        }
	        if (approve2 != null) {
	          approves.add(approve2);
	        }
	        if (approve3 != null) {
	          approves.add(approve3);
	        }
	      }
	      rs.close();
	      stmt.close();
	    }
	    catch (Exception localException) {}
	    String checker = getCheckerFromEmp(con, emp);
	    if ((checker != null) && (!approves.contains(checker))) {
	      approves.add(checker);
	    }
	    return approves;
	  }
	  
	  public static String getCheckerFromEmp(Connection con, String emp)
	  {
	    String result = "";
	    try
	    {
	      Statement stmt = con.createStatement();
	      String sql = "SELECT fzr FROM department a INNER JOIN employee b ON b.bmbh=a.bmbh WHERE b.bh='" + 
	      

	        emp + "'";
	      ResultSet rs = stmt.executeQuery(sql);
	      if (rs.next()) {
	        result = rs.getString("fzr");
	      }
	      rs.close();
	      if (result != null)
	      {
	        sql = "SELECT attorney FROM hr_manager_out WHERE manager='" + result + "'";
	        rs = stmt.executeQuery(sql);
	        while (rs.next()) {
	          result = rs.getString("attorney");
	        }
	        rs.close();
	      }
	      stmt.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return result;
	  }
	  
	  public static String getSetFromVector(Vector v)
	  {
	    String result = "";
	    for (int i = 0; i < v.size(); i++)
	    {
	      if (i != 0) {
	        result = result + ",";
	      }
	      result = result + "'" + v.get(i) + "'";
	    }
	    if (result.equals("")) {
	      result = "''";
	    }
	    result = "(" + result + ")";
	    return result;
	  }
	  
	  public static Vector getManagerDep(Connection con, String manager)
	  {
	    Vector depts = new Vector();
	    try
	    {
	      Statement stmt = con.createStatement();
	      String sql = "SELECT bmbh FROM department WHERE fzr='" + manager + "'";
	      ResultSet rs = stmt.executeQuery(sql);
	      while (rs.next())
	      {
	        String bmbh = rs.getString("bmbh");
	        depts.add(bmbh);
	      }
	      stmt.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return depts;
	  }
	  
	  public static Vector getAttorneyDep(Connection con, String checker)
	  {
	    Vector depts = new Vector();
	    try
	    {
	      Statement stmt = con.createStatement();
	      String sql = "SELECT bmbh FROM department a INNER JOIN hr_manager_out b ON a.fzr=b.manager WHERE b.attorney='" + 
	      

	        checker + "'";
	      ResultSet rs = stmt.executeQuery(sql);
	      if (rs.next())
	      {
	        String bmbh = rs.getString("bmbh");
	        depts.add(bmbh);
	      }
	      stmt.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return depts;
	  }
	  
	  public static String getEmpNameFromId(Connection con, String id)
	  {
	    try
	    {
	      String name = null;
	      Statement stmt = con.createStatement();
	      String sql = "SELECT xm FROM employee WHERE bh='" + id + "'";
	      ResultSet rs = stmt.executeQuery(sql);
	      if (rs.next()) {
	        name = rs.getString("xm");
	      }
	      rs.close();
	      stmt.close();
	      return name;
	    }
	    catch (Exception e) {}
	    return null;
	  }
	  
	  public static Vector getKqyDept(Connection con, String kqy)
	  {
	    Vector depts = new Vector();
	    try
	    {
	      //kqy = Integer.parseInt(kqy);
	      Statement stmt = con.createStatement();
	      String sql = "SELECT dep_no FROM hr_kqy_info WHERE kqy='" + kqy + "'";
	      ResultSet rs = stmt.executeQuery(sql);
	      while (rs.next())
	      {
	        String bmbh = rs.getString("dep_no");
	        depts.add(bmbh);
	      }
	      stmt.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return depts;
	  }
	  
	  public static Vector getKqyCalendar(Connection con, String kqy)
	  {
	    Vector depts = new Vector();
	    try
	    {
	   //   kqy = Integer.parseInt(kqy);
	      Statement stmt = con.createStatement();
	      String sql = "SELECT cal_id FROM hr_kqy_calendar WHERE kqy_id='" + kqy + "'";
	      ResultSet rs = stmt.executeQuery(sql);
	      while (rs.next())
	      {
	        String bmbh = rs.getString("cal_id");
	        depts.add(bmbh);
	      }
	      stmt.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return depts;
	  }
	  
	  public static Connection getVHConnection()
	  {
	    return PortalManager.getDBA().getConnection("VH");
	  }
	  
	  public static void freeVHConnection(Connection con)
	  {
	    PortalManager.getDBA().freeConnection("VH", con);
	  }
	  
	  public static String lPad(String src, int len, char c)
	  {
	    String result = src;
	    while (result.length() < len) {
	      result = c + result;
	    }
	    return result;
	  }
	  
	  public static String lPad(String src)
	  {
	    return lPad(src, 6, '0');
	  }
	  
	  public static String convertTime(int lng)
	  {
	    double result = 0;
	    if (lng != 0)
	    {
	      int l = lng / 30;
	      result = l * 0.5D;
	    }
	    return String.valueOf(result);
	  }
}
