package cn.com.maxim.portal.util;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import cn.com.maxim.htmlDBcontrol.WebDBControl;
import cn.com.maxim.htmlDBcontrol.WebDBForm;
import cn.com.maxim.htmlDBcontrol.WebDBSelect;
import cn.com.maxim.htmlcontrol.WebHidden;
import cn.com.maxim.htmlcontrol.WebLabel;
import cn.com.maxim.htmlcontrol.WebSelect;

public class ControlUtil
{
	static WebDBSelect APSelector;
	static WebLabel APlLbel;
	static WebDBControl APlDBControl;
	static WebHidden APHidden;
	static WebDBForm Form;
	static WebSelect  APCustomSelect;
	
	
	public static String drawSelectDBControl(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String whereSql, String SelectedOption) throws SQLException
	{

		WebSelect ws = APlDBControl.buildWebSelectFromDB(con, tableName, valueField, DisplayField,
				whereSql, null, null, false);
		ws.setID(name);
		ws.setName(name);
		ws.setClass("select2_category form-control");
		ws.addOption("0", "未選擇");
	//	System.out.println("drawSelectDBControl SelectedOption :"+SelectedOption);
		ws.setSelectedOption(SelectedOption);
		return ws.toString();
	}
	
	public static String drawHidden(String Value, String htmlID)
	{
		APHidden = new WebHidden(htmlID);
		APHidden.setValue(Value);
		APHidden.setID(htmlID);
		//APHidden.writeHTML(out);
		return APHidden.toString();
	}
	public static String drawSelectShared(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String Filter, String SelectedOption) throws SQLException
	{

		APSelector = new WebDBSelect(name, tableName, valueField, DisplayField, Filter);
		APSelector.setClass("select2_category form-control");
		APSelector.setID(name);
		APSelector.refreshOptions(con);
		APSelector.addOption("0", "未選擇");
		//System.out.println("drawSelectShared SelectedOption :"+SelectedOption);
		APSelector.setSelectedOption(SelectedOption);
		//APSelector.writeHTML(out);
		return APSelector.toString();
	}
	public static String drawSelectShared(Connection con, PrintWriter out, String name, String tableName, String valueField,
			String DisplayField, String Filter, String SelectedOption,boolean flag) throws SQLException
	{

		APSelector = new WebDBSelect(name, tableName, valueField, DisplayField, Filter);
		APSelector.setClass("select2_category form-control");
		APSelector.setID(name);
		APSelector.refreshOptions(con);
		if(flag){
			APSelector.addOption("0", "未選擇");
		}
		APSelector.setSelectedOption(SelectedOption);
		return APSelector.toString();
	}
	public static String drawCustomSelectShared( String Name,ArrayList Options, String SelectedOption)
	{

		APCustomSelect = new WebSelect();
		APCustomSelect.setClass("select2_category form-control");
		APCustomSelect.setID(Name);
		APCustomSelect.setName(Name);
		
		for(int i=0;i<Options.size();i++){
			Hashtable ht=(Hashtable)Options.get(i);
			APCustomSelect.addOption((String)ht.get("value"),(String) ht.get("text"));
		}
		
		APCustomSelect.addOption("0", "未選擇");
		
		//System.out.println("drawCustomSelectShared SelectedOption :"+SelectedOption);
		APCustomSelect.setSelectedOption(SelectedOption);
	
		return APCustomSelect.toString();
	}
	
}
