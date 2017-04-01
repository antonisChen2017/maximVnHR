package cn.com.maxim.portal.util;

import java.sql.Connection;
import java.sql.SQLException;
import maxim.vn.controls.TableControl;
import cn.com.maxim.htmlcontrol.WebEdit;

public class AllApGroupControl extends TableControl
	{
	  WebEdit group_id;
	  WebEdit group_name;
	  WebEdit i18n;
	  WebEdit sort_no;
	  
	  public AllApGroupControl(Connection con)
	    throws SQLException
	  {
	    super("VN_REASONS", con);
	    initAppGroupForm(con);
	    refreshFormLayout();
	  }
	  
	  private void initAppGroupForm(Connection con)
	    throws SQLException
	  {
		  this.group_id = new WebEdit("gid");
		    this.group_id.setClass("KeyStyle");
		    this.group_id.setSize(12);
		    this.group_id.setMaxLength(8);
		    this.group_name = new WebEdit("gname");
		    this.group_name.setSize(30);
		  //  this.i18n = new WebEdit("i18n");
		    this.sort_no = new WebEdit("sort");
		    this.sort_no.setSize(8);
		    this.sort_no.setMaxLength(6);
		    setDBFormKeyField("ID", "Group Key", 12, this.group_id);
		    addDBFormField("REASONS", "Group Name", 12, this.group_name);
		    addDBFormField("SORT_NO", "Sort", 12, this.sort_no);
	  }
	}
