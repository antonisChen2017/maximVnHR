package cn.com.maxim.portal.webUI;

import java.sql.Connection;
import java.sql.SQLException;

public class WebDBTableCo  extends WebDBTableEx
{

	public WebDBTableCo(Connection DBCon, String SQL)
	{
		super(DBCon);
		this.SQL = SQL;
		try
		{
			this.executeSQL();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
