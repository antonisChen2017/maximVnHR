package cn.com.maxim.portal.webUI;

import cn.com.maxim.htmlcontrol.PostableControl;
import cn.com.maxim.pdf.script.PipeString;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import cn.com.maxim.htmltable.CheckBoxRule;
import cn.com.maxim.htmltable.ConstantColumn;
import cn.com.maxim.htmltable.DBColumn;
import cn.com.maxim.htmltable.HighlightColumn;
import cn.com.maxim.htmltable.HighlightRule;
import cn.com.maxim.htmltable.ImageColumn;
import cn.com.maxim.htmltable.SNOColumn;
import cn.com.maxim.htmltable.WebDBTable;

public class WebDBTableEx  {
	
	  Connection DBCon;
	  public String SQL;
	  public Vector DBColumns;
	  ResultSet DBData;
	  public int CellSpacing = 0;
	  public int Border = 0;
	  public  int CellPadding = 0;
	  public int OutPutRowCount = -1;
	  public int PageNumber = 1;
	  public boolean MessageRowVisible = false;
	  public boolean SummaryRowVisible = false;
	  boolean HeaderRowVisible = true;
	  public String MessageRowStyle = "";
	  public String SummaryRowStyle = "";
	  public String Message = "";
	  public String Summary = "";
	  public static final String DEFAULT_HEADER_STYLE = "HeaderRow";
	  public static final String DEFAULT_ODDROW_STYLE = "OddRow";
	  public static final String DEFAULT_EVENROW_STYLE = "EvenRow";
	  public static final String DEFAULT_MESSAGE_STYLE = "MessageRow";
	  public static final String DEFAULT_SUMMARY_STYLE = "SummaryRow";
	  public String totalTime = "";
	  public String Status = "";
	  public String css = "";
	  public boolean excel ;
	  public WebDBTableEx(Connection DBCon)
	  {
	    this.DBColumns = new Vector();
	    this.DBCon = DBCon;
	  }
	  
	  public ResultSet getDataSet()
	  {
	    return this.DBData;
	  }
	  
	  public WebDBTableEx(Connection DBCon, String SQL)
	    throws SQLException
	  {
	    this(DBCon);
	    this.SQL = SQL;
	    executeSQL();
	  }
	  
	  
	  
	  public Object getCurrentValue(String FieldName)
	    throws SQLException
	  {
	    return this.DBData.getObject(FieldName);
	  }
	  
	  public int getOutPutRowCount()
	  {
	    return this.OutPutRowCount;
	  }
	  
	  public int getOutPutPage()
	  {
	    return this.PageNumber;
	  }
	  
	  public static int getCount(ResultSet rs, String FieldName)
	    throws SQLException
	  {
	    Hashtable ht = new Hashtable();
	    rs.first();
	    while (rs.next())
	    {
	      String Data = rs.getString(FieldName);
	      if (Data == null) {
	        Data = "__NULL__";
	      }
	      int a = 0;
	      try
	      {
	        a = ((Integer)ht.get(Data)).intValue();
	      }
	      catch (Exception er)
	      {
	        a = 0;
	      }
	      ht.put(Data, new Integer(a + 1));
	    }
	    return ht.size();
	  }
	  
	  public void resetSno()
	  {
	    int start = (this.PageNumber - 1) * this.OutPutRowCount;
	    if (start < 0) {
	      start = 0;
	    }
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if ((col instanceof SNOColumn))
	      {
	        SNOColumn col2 = (SNOColumn)col;
	        if (col2.getValueLasting()) {
	          col2.setStartValue(start + 1);
	        } else {
	          col2.setStartValue(1);
	        }
	      }
	    }
	  }
	  
	  public static Hashtable getDetail(ResultSet rs, String FieldName)
	    throws SQLException
	  {
	    Hashtable ht = new Hashtable();
	    rs.first();
	    while (rs.next())
	    {
	      String Data = rs.getString(FieldName);
	      if (Data == null) {
	        Data = "__NULL__";
	      }
	      int a = 0;
	      try
	      {
	        a = ((Integer)ht.get(Data)).intValue();
	      }
	      catch (Exception er)
	      {
	        a = 0;
	      }
	      ht.put(Data, new Integer(a + 1));
	    }
	    return ht;
	  }
	  
	  public static float getFloatSum(ResultSet rs, String FieldName)
	    throws SQLException
	  {
	    rs.first();
	    float Data = 0.0F;
	    while (rs.next()) {
	      Data += rs.getFloat(FieldName);
	    }
	    return Data;
	  }
	  
	  public static int getIntSum(ResultSet rs, String FieldName)
	    throws SQLException
	  {
	    rs.first();
	    int Data = 0;
	    while (rs.next()) {
	      Data += rs.getInt(FieldName);
	    }
	    return Data;
	  }
	  
	  public ResultSet getDBData()
	  {
	    return this.DBData;
	  }
	  
	  public void setMessageRowVisible(boolean MessageRowVisible)
	  {
	    this.MessageRowVisible = MessageRowVisible;
	  }
	  
	  public void setSummaryRowVisible(boolean SummaryRowVisible)
	  {
	    this.SummaryRowVisible = SummaryRowVisible;
	  }
	  
	  public void setMessageRowStyle(String MessageRowStyle)
	  {
	    this.MessageRowStyle = MessageRowStyle;
	  }
	  
	  public void setSummaryRowStyle(String SummaryRowStyle)
	  {
	    this.SummaryRowStyle = SummaryRowStyle;
	  }
	  
	  public void setMessage(String Message)
	  {
	    this.Message = Message;
	  }
	  
	  public void setSummary(String Summary)
	  {
	    this.Summary = Summary;
	  }
	  
	  public void setOutPutRowCount(int OutPutRowCount)
	  {
	    this.OutPutRowCount = OutPutRowCount;
	  }
	  
	  public void setOutPutAllRows()
	  {
	    this.OutPutRowCount = -1;
	  }
	  
	  public void setPageNumber(int PageNumber)
	  {
	    this.PageNumber = PageNumber;
	  }
	  
	  public DBColumn getDBColumn(String ColumnName)
	  {
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.ColumnName.equalsIgnoreCase(ColumnName)) {
	        return col;
	      }
	    }
	    return null;
	  }
	  
	  public void setBorder(int Border)
	  {
	    this.Border = Border;
	  }
	  
	  public void setCellSpacing(int CellSpacing)
	  {
	    this.CellSpacing = CellSpacing;
	  }
	  
	  public void setCellPadding(int CellPadding)
	  {
	    this.CellPadding = CellPadding;
	  }
	  
	  public void setColumnVisible(String ColumnName, boolean visible)
	  {
	    DBColumn col = getDBColumn(ColumnName);
	    if (col != null) {
	      col.setVisible(visible);
	    }
	  }
	  
	  public void setColumnHeader(String ColumnName, String Header)
	  {
	    DBColumn col = getDBColumn(ColumnName);
	    if (col != null) {
	      col.ColumnHeader = Header;
	    }
	  }
	  
	  public void setLinkedAction(String ColumnName, String LinkedAction)
	  {
	    DBColumn col = getDBColumn(ColumnName);
	    if (col != null) {
	      col.setLinkedAction(LinkedAction);
	    }
	  }
	  
	  public void setDataAlignment(String ColumnName, int DataAlign)
	  {
	    DBColumn col = getDBColumn(ColumnName);
	    if (col != null) {
	      col.setDataAlignment(DataAlign);
	    }
	  }
	  
	  public void setColumnStyle(String ColumnName, String ColumnStyle)
	  {
	    DBColumn col = getDBColumn(ColumnName);
	    if (col != null) {
	      col.setColumnStyle(ColumnStyle);
	    }
	  }
	  
	  public void setSQL(String SQL)
	  {
	    this.SQL = SQL;
	  }
	  
	  public void executeSQL()
	    throws SQLException
	  {
	    executeSQL(this.SQL);
	  }
	  
	  public void refreshDBData()
	    throws SQLException
	  {
	    this.DBData = this.DBCon.createStatement().executeQuery(this.SQL);
	  }
	  
	  public void executeSQL(String SQL)
	    throws SQLException
	  {
	    Statement stmt = this.DBCon.createStatement();
	    if (this.DBData != null)
	    {
	      this.DBData.close();
	      this.DBColumns.removeAllElements();
	    }
	    this.DBData = stmt.executeQuery(SQL);
	    ResultSetMetaData metadata = this.DBData.getMetaData();
	    for (int i = 1; i <= metadata.getColumnCount(); i++)
	    {
	      DBColumn col = new DBColumn(metadata.getColumnName(i), metadata
	        .getColumnName(i));
	      switch (metadata.getColumnType(i))
	      {
	      case 91: 
	      case 92: 
	      case 93: 
	        col.setDataAlignment(2);
	        break;
	      case -6: 
	      case 2: 
	      case 4: 
	      case 5: 
	      case 6: 
	      case 8: 
	        col.setDataAlignment(3);
	        break;
	      default: 
	        col.setDataAlignment(1);
	      }
	      addColumn(col);
	    }
	  }
	  
	  public void addColumn(DBColumn column)
	  {
	    this.DBColumns.addElement(column);
	  }
	  
	  public void addColumn(DBColumn column, int at)
	  {
	    this.DBColumns.insertElementAt(column, Math.min(at, this.DBColumns.size()));
	  }
	  
	  public void addCheckBoxColumn(String header, String ColName, String ValueField, String CheckName, int at)
	  {
	    HighlightColumn hlc = new HighlightColumn(header, ColName);
	    hlc.encode = false;
	    hlc.setHighlight(ValueField, new CheckBoxRule(CheckName, false));
	    addColumn(hlc, at);
	  }
	  
	  public void addCheckBoxColumn(String[] NullField, String[] NotNullField, String header, String ColName, String ValueField, String CheckName, int at)
	  {
	    HighlightColumn hlc = new HighlightColumn(header, ColName);
	    hlc.encode = false;
	   // hlc.setHighlight(ValueField, new CheckBoxRule(CheckName, false, NullField, NotNullField, this));
	    addColumn(hlc, at);
	  }
	  
	  public void addCheckBoxColumn(String header, String ColName, String ValueField, String CheckName, boolean checked, int at)
	  {
	    HighlightColumn hlc = new HighlightColumn(header, ColName);
	    hlc.setHighlight(ValueField, new CheckBoxRule(CheckName, checked));
	    addColumn(hlc, at);
	  }
	  
	  public void addFormControlColumn(PostableControl ctrl, String header, String ColName, String ValueField, int at)
	  {
	    HighlightColumn hlc = new HighlightColumn(header, ColName);
	    hlc.setHighlight(ValueField, new PostControlRule(ctrl));
	    addColumn(hlc, at);
	  }
	  
	  class PostControlRule
	    implements HighlightRule
	  {
	    public PostableControl ctrl;
	    
	    public PostControlRule(PostableControl ctrl)
	    {
	      this.ctrl = ctrl;
	    }
	    
	    public String getHighlightValue(String FieldValue)
	    {
	      this.ctrl.setControlValue(FieldValue);
	      return this.ctrl.toString();
	    }
	  }
	  
	  boolean nowrap = false;
	  
	  public void setNowrap(boolean nowrap)
	  {
	    this.nowrap = nowrap;
	  }
	  
	  private void writeDataRow(PrintWriter out, String TRStyle)
	  {
	    beforeWriteRow(out);
	   
	    out.println("  <tr >");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible())
	      {
	        String Data = getValue(col.ColumnName, true);
	        String TDStyle = col.getColumnStyle();
	        if ((Data == null) || (Data.equals(""))) {
	          Data = "&nbsp;";
	        }
	        if (TDStyle.length() > 0) {
	          TDStyle = " class=\"" + TDStyle + "\" ";
	        }
	        switch (col.getDataAlignment())
	        {
	        case 2: 
	          out.println("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + "align=\"center\">" + 
	            Data + "</td>");
	          break;
	        case 3: 
	          out.println("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + "align=\"right\">" + 
	            Data + "</td>");
	          break;
	        default: 
	          out.println("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td>");
	        }
	      }
	    }
	    out.println("  </tr>");
	
	    afterWriteRow(out);
	  }
	  
	  
	  private String getDataRow(PrintWriter out, String TRStyle,boolean delbut)
	  {
			StringBuilder Sb = new StringBuilder("");
			beforeWriteRow(out);
	   
			Sb.append("  <tr > \r");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible())
	      {
	        String Data = getValue(col.ColumnName, true);
	        String TDStyle = col.getColumnStyle();
	        if ((Data == null) || (Data.equals(""))) {
	          Data = "&nbsp;";
	        }
	        if (TDStyle.length() > 0) {
	          TDStyle = " class=\"" + TDStyle + "\" ";
	        }
	        if(!col.ColumnName.equals("ID")){
		        switch (col.getDataAlignment())
		        {
		        case 2: 
		        	Sb.append("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + "align=\"center\">" + 
		            Data + "</td>  \r");
		          break;
		        case 3: 
		        	Sb.append("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + "align=\"right\">" + 
		            Data + "</td> \r");
		          break;
		        default: 
		        	Sb.append("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
		        }
	        }
	      }
	    }
	    if(delbut){
    	  	String rowID = getValue("ID", true);
    	  	String rowHOURS = getValue("APPLICATION_HOURS", true);
	     	Sb.append("<td class=\"" + TRStyle + "\"  ><button onclick=\"ActionForm.act.value='Delete';ActionForm.deleteID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info btn-sm\">DELETE</button></td> \r");
	    }
	    Sb.append("  </tr> \r");
	
	    afterWriteRow(out);
	    
	    return Sb.toString();
	  }
	  
	  
	  private void writeDataRow(PrintStream out, String TRStyle)
	  {
	    beforeWriteRow(out);
	    out.println("  <tr class=\"" + TRStyle + "\">");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible())
	      {
	        String Data = getValue(col.ColumnName, true);
	        String TDStyle = col.getColumnStyle();
	        if ((Data == null) || (Data.equals(""))) {
	          Data = "&nbsp;";
	        }
	        if (TDStyle.length() > 0) {
	          TDStyle = " class=\"" + TDStyle + "\" ";
	        }
	        switch (col.getDataAlignment())
	        {
	        case 2: 
	          out.println("    <td nowrap " + TDStyle + 
	            "align=\"center\">" + Data + "</td>");
	          break;
	        case 3: 
	          out.println("    <td nowrap " + TDStyle + 
	            "align=\"right\">" + Data + "</td>");
	          break;
	        default: 
	          out.println("    <td nowrap " + TDStyle + ">" + Data + 
	            "</td>");
	        }
	      }
	    }
	    out.println("  </tr>");
	    afterWriteRow(out);
	  }
	  
	  public void writeHTMLTable(PrintWriter out)
	    throws SQLException
	  {
	    writeHTMLTable(out, "HeaderRow", "OddRow", 
	      "EvenRow");
	  }
	  
	  public void writeHTMLTable(PrintStream out)
	    throws SQLException
	  {
	    writeHTMLTable(out, "HeaderRow", "OddRow", 
	      "EvenRow");
	  }
	  
	  public static void movePage(int PageCount, int PageNumber, ResultSet rs)
	    throws SQLException
	  {
	    rs.beforeFirst();
	    for (int i = 1; i <= PageNumber - 1; i++) {
	      for (int j = 1; j <= PageCount; j++) {
	        if (!rs.next())
	        {
	          for (int k = 1; k <= j; k++) {
	            rs.previous();
	          }
	          return;
	        }
	      }
	    }
	  }
	  
	  
	    public void writeHTMLTable(PrintWriter out,String TableStyle,String msg,boolean execl)
			    throws SQLException
	   {
			    writeHTMLTable(out, "HeaderRow", "OddRow", 
			      "EvenRow","",TableStyle,msg,execl);
		}
	    public void writeHTMLTable(PrintWriter out,String TableStyle)
			    throws SQLException
	   {
			    writeHTMLTable(out, "HeaderRow", "OddRow", 
			      "EvenRow","",TableStyle,"",false);
		}
	  public void writeHTMLTable(PrintWriter out, String HeaderStyle, String OddRowStyle, String EvenRowStyle)
	    throws SQLException
	  {
	    writeHTMLTable(out, HeaderStyle, OddRowStyle, EvenRowStyle, "","","",false);
	  }
	  
	  public void writeHTMLTable(PrintStream out, String HeaderStyle, String OddRowStyle, String EvenRowStyle)
	    throws SQLException
	  {
	    writeHTMLTable(out, HeaderStyle, OddRowStyle, EvenRowStyle, "");
	  }
	  
	  public void writeHTMLTable(PrintWriter out, String HeaderStyle, String OddRowStyle, String EvenRowStyle, String NoRowMessage,String TableStyle,String msg,boolean execl)
	    throws SQLException
	  {
	    resetSno();
	    if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
	    {
	      out.println("<table class=\""+TableStyle+"\" border=\"" + this.Border + "\" cellspacing=\"" + 
	        this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + 
	        "\">");
	      out.println("  <tr><td>" + NoRowMessage + "</td></tr>");
	      out.println("</table>");
	      return;
	    }
	    if (this.OutPutRowCount > 0) {
	      movePage(this.OutPutRowCount, 
	        this.PageNumber, this.DBData);
	    }

	    out.println("<!-- BEGIN PAGE ROW-->");
	    out.println("   <div class=\"row \">");
	    out.println("   <div class=\"col-md-12 col-sm-12\">");
	    out.println("   <div class=\"portlet\">");
	    out.println("   <div class=\"portlet-title\">");
	    out.println("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查詢結果");
	    if(!msg.equals("")){
	        out.println(msg);
	    }
	    out.println(" </div>");
	    out.println("	 </div>");
	    out.println("   <div class=\"portlet-body\">");
	    out.println("<table  id=\"data_table_1\" class=\""+TableStyle+  "\">");
	    writeFirstRow(out);
	    if (this.MessageRowVisible) {
	      writeMessageRow(out, this.Message, 
	        this.MessageRowStyle);
	    }
	    if (this.OutputHeader) {
	      writeHeaderRow(out, HeaderStyle);
	    }
	    int i = 1;
	    out.println("<tbody>");
	    while (this.DBData.next())
	    {
	    	
	      writeDataRow(out, "blue");
	      if (i == this.OutPutRowCount + 1) {
	        break;
	      }
	    }
	    out.println(" </tbody>");
	    if (this.SummaryRowVisible) {
	      writeMessageRow(out, this.Summary, 
	        this.SummaryRowStyle);
	    }
	    writeLastRow(out);
	    out.println("</table>");
	    out.println("<div class=\"form-actions right\">");
	    //是否要excel按鈕
	    if(execl){
	    	out.println("<button class=\"btn btn-primary btn-sm\" onchick='outputExcel()' type=\"button\">匯出成Excel</button>");
	    }
	    out.println(" </div>");
	    out.println("</div>");
	    out.println("</div>");
	    out.println("</div>");
	    out.println("</div>");
	    out.println("<div class=\"clearfix\"> </div>");
	    out.println(" <!-- END PAGE ROW-->");
	  }
	  
	  public boolean OutputHeader = true;
	  
	  public void writeLastRow(PrintWriter out) {}
	  
	  public void writeFirstRow(PrintWriter out) {}
	  
	  public void beforeWriteRow(PrintWriter out) {}
	  
	  public void afterWriteRow(PrintWriter out) {}
	  
	  public void beforeWriteRow(PrintStream out) {}
	  
	  public void afterWriteRow(PrintStream out) {}
	  
	  public void writeHTMLTable(PrintStream out, String HeaderStyle, String OddRowStyle, String EvenRowStyle, String NoRowMessage)
	    throws SQLException
	  {
	    resetSno();
	    if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
	    {
	      out.println("<table border=\"" + this.Border + "\" cellspacing=\"" + 
	        this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + 
	        "\">");
	      out.println("  <tr><td " + (this.nowrap ? "nowrap" : "") + ">" + NoRowMessage + "</td></tr>");
	      out.println("</table>");
	      return;
	    }
	    if (this.OutPutRowCount > 0) {
	      movePage(this.OutPutRowCount, 
	        this.PageNumber, this.DBData);
	    }
	    out.println("<!-- Start DB HTML Table -->");
	    out.println("<table border=\"" + this.Border + "\" cellspacing=\"" + 
	      this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + 
	      "\">");
	    if (this.MessageRowVisible) {
	      writeMessageRow(out, this.Message, 
	        this.MessageRowStyle);
	    }
	    if (this.OutputHeader) {
	      writeHeaderRow(out, HeaderStyle);
	    }
	    int i = 1;
	    while (this.DBData.next())
	    {
	      writeDataRow(out, i++ % 2 == 1 ? OddRowStyle : EvenRowStyle);
	      if (i == this.OutPutRowCount + 1) {
	        break;
	      }
	    }
	    if (this.SummaryRowVisible) {
	      writeMessageRow(out, this.Summary, 
	        this.SummaryRowStyle);
	    }
	    out.println("</table>");
	    out.println("<!-- End DB HTML Table -->");
	  }
	  
	  public String toString()
	  {
	    StringWriter sw = new StringWriter();
	    try
	    {
	      writeHTMLTable(new PrintWriter(sw));
	    }
	    catch (Exception err)
	    {
	      err.printStackTrace();
	    }
	    return sw.toString();
	  }
	  
	  public String getValue(String ColumnName, boolean CheckAction)
	  {
	    DBColumn col = getDBColumn(ColumnName);
	    String value = "";
	    if ((col instanceof SNOColumn))
	    {
	      if (CheckAction) {
	        value = String.valueOf(((SNOColumn)col).getValue()+1);
	      } else {
	        value = String.valueOf(((SNOColumn)col).getValue() - 1);
	      }
	    }
	    else if ((col instanceof ConstantColumn))
	    {
	      value = ((ConstantColumn)col).ConstantValue;
	    }
	    else if ((col instanceof HighlightColumn))
	    {
	      HighlightColumn hcol = (HighlightColumn)col;
	      if (hcol.HighlightField.equals("")) {
	        return "&nbsp;";
	      }
	      DBColumn col2 = getDBColumn(hcol.HighlightField);
	      if (col2 == null) {
	        return "&nbsp;";
	      }
	      try
	      {
	        String SrcValue = this.DBData.getString(hcol.HighlightField);
	        value = hcol.Rule.getHighlightValue(SrcValue);
	      }
	      catch (SQLException sqle)
	      {
	        return "&nbsp;";
	      }
	    }
	    else if ((col instanceof ImageColumn))
	    {
	      value = ((ImageColumn)col).ImageLocation;
	      if (value.length() > 0) {
	        value = "<img src=\"" + value + "\">";
	      } else {
	        return "&nbsp;";
	      }
	    }
	    else
	    {
	      try
	      {
	        value = this.DBData.getString(ColumnName);
	     /*   if ((value == null) || (value.equals("")))
	        {
	          if (col.getColumnVisible()!= null) {
	            value = makeActionString(col.NullAction, col.encode);
	          } else {
	            return "&nbsp;";
	          }
	        }
	        else if (col.Command != null)
	        {
	          PipeString ps = new PipeString(value);
	          ps.runPipeScript(col.Command);
	          value = ps.toString();
	          if (col.NotNullAction != null) {
	            value = makeActionString(col.NotNullAction, col.encode) + value;
	          }
	        }*/
	      }
	      catch (SQLException sqle)
	      {
	        return "";
	      }
	    }
	    String action = "";
	    if ((CheckAction) && (col.getLinkedAction().length() > 0)) {
	      action = makeActionString(col.getLinkedAction(), col.encode);
	    } else {
	      return value;
	    }
	    if ((action == null) || (action.equals(""))) {
	      return value;
	    }
	    return "<a href=\"" + action + "\">" + value + "</a>";
	  }
	  
	  public int getVisibleColumnCount()
	  {
	    int res = 0;
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible()) {
	        res++;
	      }
	    }
	    return res;
	  }
	  
	  private void writeMessageRow(PrintWriter out, String Message, String TRStyle)
	  {
	    out.println("  <tr class=\"" + TRStyle + "\">");
	    out.println("    <td " + (this.nowrap ? "nowrap " : "") + " colspan=\"" + getVisibleColumnCount() + "\">" + 
	      Message + "</td>");
	    out.println("  </tr>");
	  }
	  
	 
	  
	  public String getMessageRow(PrintWriter out, String Message, String TRStyle)
	  {
			StringBuilder Sb = new StringBuilder("");
			  Sb.append("  <tr class=\"" + TRStyle + "\"> \r");
			  Sb.append("    <td " + (this.nowrap ? "nowrap " : "") + " colspan=\"" + getVisibleColumnCount() + "\">" +   Message + "</td> \r");
			  Sb.append("  </tr> \r");
			  return Sb.toString();
	  }
	  
	  private void writeMessageRow(PrintStream out, String Message, String TRStyle)
	  {
	    out.println("  <tr class=\"" + TRStyle + "\">");
	    out.println("     " + (this.nowrap ? "nowrap " : "") + "colspan=\"" + getVisibleColumnCount() + "\">" + 
	      Message + "</td>");
	    out.println("  </tr>");
	  }
	  
	  private void writeHeaderRow(PrintWriter out, String TRStyle)
	  {
	    if (TRStyle.equals("")) {
	      TRStyle = "DefaultTR";
	    }
	    out.println(" <thead>");
	    out.println("  <tr class=\"" + TRStyle + "\">");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible()) {
	        out.println("    <th class=\"text-center\">" + 
	          col.ColumnHeader + "</th>");
	      }
	    }
	
	    out.println("  </tr>");
	    out.println(" </thead>");
	  }
	  
	  private String getHeaderRow(PrintWriter out, String TRStyle,boolean delbut)
	  {
			StringBuilder Sb = new StringBuilder("");
	    if (TRStyle.equals("")) {
	      TRStyle = "DefaultTR";
	    }
	    Sb.append(" <thead>  \r");
	    Sb.append("  <tr class=\"" + TRStyle + "\"> \r");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible()) {
	    	  if( !col.ColumnHeader.equals("ID")   ){
		    	  Sb.append("    <th class=\"text-center\">" + 
		          col.ColumnHeader + "</th>  \r");
	    	  }
	      }
	    }
	    if(delbut){
	  	    Sb.append("    <th class=\"text-center\"></th>  \r");
	    }
	    Sb.append("  </tr>  \r");
	    Sb.append(" </thead>\r");
	    return Sb.toString();
	  }
	  
	  
	  private void writeHeaderRow(PrintStream out, String TRStyle)
	  {
	    if (TRStyle.equals("")) {
	      TRStyle = "DefaultTR";
	    }
	    out.println("  <tr class=\"" + TRStyle + "\">");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible()) {
	        out.println("    <th nowrap>" + 
	          col.ColumnHeader + "</th>");
	      }
	    }
	    out.println("  </tr>");
	  }
	  
	  private String makeActionString(String src, boolean encode)
	  {
	    StringTokenizer st = new StringTokenizer(src, "()");
	    String result = "";
	    while (st.hasMoreTokens())
	    {
	      String NowToken = st.nextToken().trim();
	      if ((NowToken.startsWith("#")) && (NowToken.endsWith("#")))
	      {
	        if (NowToken.equalsIgnoreCase("#&L#")) {
	          result = result + "(";
	        } else if (NowToken.equalsIgnoreCase("#&R#")) {
	          result = result + ")";
	        } else if (NowToken.toUpperCase().startsWith("#BREAK,")) {
	          try
	          {
	            StringTokenizer st2 = new StringTokenizer(NowToken, ",<=>");
	            st2.nextElement();
	            String p1 = st2.nextToken();
	            p1 = getValue(p1, false);
	            String p2 = st2.nextToken();
	            p2 = getValue(p2, false);
	            System.out.println(p1 + "," + p2);
	            NowToken.indexOf(">=");
	          }
	          catch (Exception er)
	          {
	            er.printStackTrace();
	          }
	        } else {
	          try
	          {
	            String ColumnName = NowToken.substring(1, 
	              NowToken.length() - 1).trim();
	            if (encode) {
	              result = result + URLEncoder.encode(
	                getValue(ColumnName, false), "UTF-8");
	            } else {
	              result = result + getValue(ColumnName, false);
	            }
	          }
	          catch (Exception localException1) {}
	        }
	      }
	      else if ((NowToken.startsWith("@")) && (NowToken.endsWith("@"))) {
	        try
	        {
	          String ColumnName = NowToken.substring(1, 
	            NowToken.length() - 1).trim();
	          String s = getValue(ColumnName, false);
	          if (s.indexOf("'") > -1) {
	            s = s.replaceAll("'", "\\\\'");
	          }
	          result = result + s;
	        }
	        catch (Exception localException2) {}
	      } else {
	        result = result + NowToken;
	      }
	    }
	    return result;
	  }
	  
	  public String  getHTMLTable(PrintWriter out,String TableStyle,String msg,String htmlButton,boolean delBut)
			    throws SQLException
	   {
		  	return getHTMLTable(out, "HeaderRow", "OddRow", 
			      "EvenRow","",TableStyle,msg,htmlButton,delBut);
		}
	  
	  public String getHTMLTable(PrintWriter out, String HeaderStyle, String OddRowStyle, String EvenRowStyle, 
			  String NoRowMessage,String TableStyle,String msg,String htmlButton,boolean delbut)
			    throws SQLException
			  {
		  		StringBuilder Sb = new StringBuilder("");
			    resetSno();
			    if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
			    {
			    	Sb.append("<table class=\""+TableStyle+"\" border=\"" + this.Border + "\" cellspacing=\"" + 
			        this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + 
			        "\">   \r");
			    	Sb.append("  <tr><td>" + NoRowMessage + "</td></tr> \r");
			    	Sb.append("</table>  \r");
			      return Sb.toString();
			    }
			    if (this.OutPutRowCount > 0) {
			      movePage(this.OutPutRowCount, 
			        this.PageNumber, this.DBData);
			    }
			   
			    Sb.append("<!-- BEGIN PAGE ROW-->  \r");
			    Sb.append("   <div class=\"row \"> \r");
			    Sb.append("   <div class=\"col-md-12 col-sm-12\"> \r");
			    Sb.append("   <div class=\"portlet\"> \r");
			    Sb.append("   <div class=\"portlet-title\">  \r");
			    Sb.append("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查詢結果  \r");
			    if(!msg.equals("")){
			    	Sb.append(msg);
			    }
			    Sb.append(" </div>  \r");
			    Sb.append("	 </div>  \r");
			    Sb.append("   <div class=\"portlet-body\">    \r");
			    Sb.append("<table  id=\"data_table_1\" class=\""+TableStyle+  "\">    \r");
			    writeFirstRow(out);
			    if (this.MessageRowVisible) {
			    	  Sb.append(	getMessageRow(out, this.Message, 
			        this.MessageRowStyle));
			    }
			    if (this.OutputHeader) {
			    	Sb.append(getHeaderRow(out, HeaderStyle, delbut));
			    }
			    int i = 1;
			    Sb.append("<tbody>");
			    while (this.DBData.next())
			    {
			    	
			    	Sb.append(getDataRow(out, "blue", delbut));
			      if (i == this.OutPutRowCount + 1) {
			        break;
			      }
			    }
			    Sb.append(" </tbody>   \r");
			    if (this.SummaryRowVisible) {
			    	Sb.append(getMessageRow(out, this.Summary, 
			        this.SummaryRowStyle));
			    }
			    writeLastRow(out);
			    Sb.append("</table>   \r");
			    Sb.append("<div class=\"form-actions right\">    \r");
			    //是否要excel按鈕
			   // if(execl){
			    	Sb.append(htmlButton+ "\r");
			  //  }
			    Sb.append(" </div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>   \r");
			    Sb.append("<div class=\"clearfix\"> </div>   \r");
			    Sb.append(" <!-- END PAGE ROW-->   \r");
			   
			    return Sb.toString();
			  }
	  
	  
	  
	  private String getHeaderRowVoS(PrintWriter out, String TRStyle,String delbut)
	  {
			StringBuilder Sb = new StringBuilder("");
	    if (TRStyle.equals("")) {
	      TRStyle = "DefaultTR";
	    }
	    Sb.append(" <thead>  \r");
	    Sb.append("  <tr class=\"" + TRStyle + "\"> \r");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible()) {
	    	  if( !col.ColumnHeader.equals("ID")  &&   !col.ColumnHeader.equals("MOTime") &&   !col.ColumnHeader.equals("returnMSG")){
		    	  Sb.append("    <th class=\"text-center\">" + 
		          col.ColumnHeader + "</th>  \r");
	    	  }
	      }
	    }
	   
	    Sb.append("  </tr>  \r");
	    Sb.append(" </thead>\r");
	    return Sb.toString();
	  }
	  
	  
	  private String getDataRowVoS(PrintWriter out, String TRStyle,String delbut)
	  {
			StringBuilder Sb = new StringBuilder("");
			beforeWriteRow(out);
	   
			Sb.append("  <tr > \r");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible())
	      {
	        String Data = getValue(col.ColumnName, true);
	        String TDStyle = col.getColumnStyle();
	        if ((Data == null) || (Data.equals(""))) {
	          Data = "&nbsp;";
	        }
	        if (TDStyle.length() > 0) {
	          TDStyle = " class=\"" + TDStyle + "\" ";
	        }
	        if(!col.ColumnName.equals("ID")  &&   !col.ColumnName.equals("MOTime") &&   !col.ColumnName.equals("returnMSG")){
		         
		        
		        	if(col.ColumnName.equals("action")){
		        		String rowID = getValue("ID", true);
		        		String rowaction = getValue("action", true);
		        		String returnMSG = getValue("returnMSG", true);
		        		if(returnMSG==null){
		        			returnMSG="";
		        		}
		        		if(rowaction.equals("S")){
		        			if(delbut.equals("0")){
		        				Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
		        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
		        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else{
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">已提交</td> \r");
		        			}
		        		}else if(rowaction.equals("U")){
			        	
		        			if(delbut.equals("0")){
		        				System.out.println("已提交 delbut :"+delbut);
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">已提交</td> \r");
		        			}else{
		        				System.out.println("退回 delbut :"+delbut);
		        				Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
				        				+ "<button onclick=\"upReturn("+rowID+")\"   type=\"button\" class=\"btn btn-warning  btn-sm\">退回</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='I';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-success  btn-sm\">審核通過</button></td> \r");
		        			}
			        			
		        		}else if(rowaction.equals("R")){
		        			if(delbut.equals("0")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else{
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">已退回</td> \r");
		        			
		        			}
		        		}else if(rowaction.equals("I")){
		        			
		        			Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">已審核通過</td> \r");
		        			
		        		}
		        	
		        	}else if(col.ColumnName.equals("加班開始時間") ||  col.ColumnName.equals("加班結束時間")){
		        		Data=Data.substring(0,Data.length()-3);
		        		Sb.append("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
		        	}else{
		        		Sb.append("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
		        	}
	        }
	      
	      }
	    }
	   
	    Sb.append("  </tr> \r");
	
	    afterWriteRow(out);
	    
	    return Sb.toString();
	  }
	  
	  public String getHTMLTableVoS( PrintWriter out,String NoRowMessage,String TableStyle,String msg,String htmlButton,String delbut)
			    throws SQLException
			  {
		  		StringBuilder Sb = new StringBuilder("");
			    resetSno();
			    if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
			    {
			    	Sb.append("<table class=\""+TableStyle+"\" border=\"" + this.Border + "\" cellspacing=\"" + 
			        this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + 
			        "\">   \r");
			    	Sb.append("  <tr><td>" + NoRowMessage + "</td></tr> \r");
			    	Sb.append("</table>  \r");
			      return Sb.toString();
			    }
			    if (this.OutPutRowCount > 0) {
			      movePage(this.OutPutRowCount, 
			        this.PageNumber, this.DBData);
			    }
			   
			    Sb.append("<!-- BEGIN PAGE ROW-->  \r");
			    Sb.append("   <div class=\"row \"> \r");
			    Sb.append("   <div class=\"col-md-12 col-sm-12\"> \r");
			    Sb.append("   <div class=\"portlet\"> \r");
			    Sb.append("   <div class=\"portlet-title\">  \r");
			    Sb.append("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查詢結果  \r");
			    if(!msg.equals("")){
			    	Sb.append(msg);
			    }
			    Sb.append(" </div>  \r");
			    Sb.append("	 </div>  \r");
			    Sb.append("   <div class=\"portlet-body\">    \r");
			    Sb.append("<table  id=\"data_table_1\" class=\""+TableStyle+  "\">    \r");
			    writeFirstRow(out);
			    if (this.MessageRowVisible) {
			    	  Sb.append(	getMessageRow(out, this.Message, 
			        this.MessageRowStyle));
			    }
			    if (this.OutputHeader) {
			    	Sb.append(getHeaderRowVoS(out,"", delbut));
			    }
			    int i = 1;
			    Sb.append("<tbody>");
			    while (this.DBData.next())
			    {
			    	String MOTime = DBData.getString("MOTime");
			    	if(MOTime!=null){
				    	if(MOTime.equals("1")){
				    		Sb.append(getDataRowVoS(out, "red", delbut));
				    	}else{
				    		Sb.append(getDataRowVoS(out, "blue", delbut));
				    	}
			    	}
			    	
				     if (i == this.OutPutRowCount + 1) {
				       break;
				     }
			    }
			    Sb.append(" </tbody>   \r");
			    if (this.SummaryRowVisible) {
			    	Sb.append(getMessageRow(out, this.Summary, 
			        this.SummaryRowStyle));
			    }
			    writeLastRow(out);
			    Sb.append("</table>   \r");
			    Sb.append("<div class=\"form-actions right\">    \r");
			    //是否要excel按鈕
			   // if(execl){
			    	Sb.append(htmlButton+ "\r");
			  //  }
			    Sb.append(" </div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>   \r");
			    Sb.append("<div class=\"clearfix\"> </div>   \r");
			    Sb.append(" <!-- END PAGE ROW-->   \r");
			   
			    return Sb.toString();
			  }
	  
	  		 /**
	  		  * 請假卡TABLE
	  		  * @param out
	  		  * @param NoRowMessage
	  		  * @param TableStyle
	  		  * @param msg
	  		  * @param htmlButton
	  		  * @param delbut
	  		  * @return
	  		  * @throws SQLException
	  		  */
	  public String getHTMLTableLC( PrintWriter out,String NoRowMessage,String TableStyle,String msg,String htmlButton,String delbut)
			    throws SQLException
			  {
		  		StringBuilder Sb = new StringBuilder("");
			    resetSno();
			    if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
			    {
			    	Sb.append("<table class=\""+TableStyle+"\" border=\"" + this.Border + "\" cellspacing=\"" + 
			        this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + 
			        "\">   \r");
			    	Sb.append("  <tr><td>" + NoRowMessage + "</td></tr> \r");
			    	Sb.append("</table>  \r");
			      return Sb.toString();
			    }
			    if (this.OutPutRowCount > 0) {
			      movePage(this.OutPutRowCount, 
			        this.PageNumber, this.DBData);
			    }
			   
			    Sb.append("<!-- BEGIN PAGE ROW-->  \r");
			    Sb.append("   <div class=\"row \"> \r");
			    Sb.append("   <div class=\"col-md-12 col-sm-12\"> \r");
			    Sb.append("   <div class=\"portlet\"> \r");
			    Sb.append("   <div class=\"portlet-title\">  \r");
			    Sb.append("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查詢結果  \r");
			    if(!msg.equals("")){
			    	Sb.append(msg);
			    }
			    Sb.append(" </div>  \r");
			    Sb.append("	 </div>  \r");
			    Sb.append("   <div class=\"portlet-body\">    \r");
			    Sb.append("<table  id=\"data_table_1\" class=\""+TableStyle+  "\">    \r");
			    writeFirstRow(out);
			    if (this.MessageRowVisible) {
			    	  Sb.append(	getMessageRow(out, this.Message, 
			        this.MessageRowStyle));
			    }
			    if (this.OutputHeader) {
			    	Sb.append(getHeaderRowVoS(out,"", delbut));
			    }
			    int i = 1;
			    Sb.append("<tbody>");
			    while (this.DBData.next())
			    {

				    		Sb.append(getDataRowLC(out, "blue", delbut));
				    
			    	
				     if (i == this.OutPutRowCount + 1) {
				       break;
				     }
			    }
			    Sb.append(" </tbody>   \r");
			    if (this.SummaryRowVisible) {
			    	Sb.append(getMessageRow(out, this.Summary, 
			        this.SummaryRowStyle));
			    }
			    writeLastRow(out);
			    Sb.append("</table>   \r");
			    Sb.append("<div class=\"form-actions right\">    \r");
			    //是否要excel按鈕
			   // if(execl){
			    	Sb.append(htmlButton+ "\r");
			  //  }
			    Sb.append(" </div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>  \r");
			    Sb.append("</div>   \r");
			    Sb.append("<div class=\"clearfix\"> </div>   \r");
			    Sb.append(" <!-- END PAGE ROW-->   \r");
			   
			    return Sb.toString();
			  }
	  
	  /**
	   * 請假卡 tableRow
	   * @param out
	   * @param TRStyle
	   * @param delbut
	   * @return
	   */
	  private String getDataRowLC(PrintWriter out, String TRStyle,String delbut)
	  {
			StringBuilder Sb = new StringBuilder("");
			beforeWriteRow(out);
	   
			Sb.append("  <tr > \r");
	    for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
	    {
	      DBColumn col = (DBColumn)i.next();
	      if (col.getColumnVisible())
	      {
	        String Data = getValue(col.ColumnName, true);
	        String TDStyle = col.getColumnStyle();
	        if ((Data == null) || (Data.equals(""))) {
	          Data = "&nbsp;";
	        }
	        if (TDStyle.length() > 0) {
	          TDStyle = " class=\"" + TDStyle + "\" ";
	        }
	        if(!col.ColumnName.equals("ID")  &&   !col.ColumnName.equals("MOTime") &&   !col.ColumnName.equals("returnMSG")){
		         
		        
		        	if(col.ColumnName.equals("action")){
		        		String rowID = getValue("ID", true);
		        		String rowaction = getValue("action", true);
		        		String returnMSG = getValue("returnMSG", true);
		        		
		        		System.out.println("rowaction : "+rowaction);
		        		System.out.println("delbut : "+delbut);
		        		if(returnMSG==null){
		        			returnMSG="";
		        		}
		        		
		        		if(rowaction.equals("S")){
		        			
		        			if(delbut.equals("0")){
		        				Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
		        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
		        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else  if(delbut.equals("E")){
		        				Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
		        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
		        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else{
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">已提交</td> \r");
		        			}
		        		}else if(rowaction.equals("T")){//申請人提交
			        	
		        			if(delbut.equals("0")){
		        			
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">已提交</td> \r");
		        			}else  if(delbut.equals("E")){
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">已提交</td> \r");
		        			}else{
		        			
		        				Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
				        				+ "<button onclick=\"upReturn("+rowID+")\"   type=\"button\" class=\"btn btn-warning  btn-sm\">退回</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='U';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-success  btn-sm\">審核通過</button></td> \r");
		        			}
			        			
		        		}else if(rowaction.equals("UR")){//單位退回
		        			if(delbut.equals("0")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">單位主管退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else  if(delbut.equals("E")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">部門主管退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else{
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">單位主管退回</td> \r");
		        			
		        			}
		        		}else if(rowaction.equals("DR")){//部門退回
		        			if(delbut.equals("0")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">部門主管退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else  if(delbut.equals("E")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">部門主管退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else{
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部門主管退回</td> \r");
		        			
		        			}
		        		}else if(rowaction.equals("MR")){//管理退回
		        			if(delbut.equals("0")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">管理部退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else  if(delbut.equals("E")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">管理部退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else{
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">管理部退回</td> \r");
		        			
		        			}
		        		}else if(rowaction.equals("LR")){//副總退回
		        			if(delbut.equals("0")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">副總退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else{
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">副總退回</td> \r");
		        			
		        			}
		        		}else if(rowaction.equals("U")){//單位主管審核ok
		        			
		        			if(delbut.equals("0")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        				+ "<button class=\"btn tooltips  btn-sm\" data-placement=\"left\" data-original-title=\""+returnMSG+"\" data-original-title=\"退回原因\">單位主管退回</button>"
				        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">提交</button></td> \r");
		        			}else if(delbut.equals("2")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
				        						+ "\n <button onclick=\"upReturn("+rowID+")\"   type=\"button\" class=\"btn btn-warning  btn-sm\">退回</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='D';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">審核通過</button></td> \r");
		        				
		        			}else if(delbut.equals("DT")){
		        				Sb.append("<td class=\"text-right  "+TRStyle+"\"  >"
		        						+ "\n <button onclick=\"upReturn("+rowID+")\"   type=\"button\" class=\"btn btn-warning  btn-sm\">退回</button>"
		        						+ " \n <button onclick=\"ActionForm.act.value='D';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">審核通過</button></td> \r");
        				
		        			}
		        			else  if(delbut.equals("U")){
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">單位主管審核通過</td> \r");
		        			}else  if(delbut.equals("1")){
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">單位主管審核通過</td> \r");
		        			}else  if(delbut.equals("E")){
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">單位主管審核通過</td> \r");
		        			}
		        			
		        		}else if(rowaction.equals("D")){//部門主管審核ok
		        			
		        			if(delbut.equals("0")){//
		        				Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
				        				+ "<button onclick=\"upReturn("+rowID+")\"   type=\"button\" class=\"btn btn-warning  btn-sm\">退回</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='M';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-success  btn-sm\">審核通過</button></td> \r");
		        		
		        			}if(delbut.equals("1")){//
		        				Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
				        				+ "<button onclick=\"upReturn("+rowID+")\"   type=\"button\" class=\"btn btn-warning  btn-sm\">退回</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='M';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-success  btn-sm\">審核通過</button></td> \r");
		        		
		        			}else if(delbut.equals("DT")){
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部門主管審核通過</td> \r");
        				
		        			}
		        			else if(delbut.equals("2")){
		        				
		        				Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
				        				+ "<button onclick=\"upReturn("+rowID+")\"   type=\"button\" class=\"btn btn-warning  btn-sm\">退回</button>"
				        						+ " \n <button onclick=\"ActionForm.act.value='M';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-success  btn-sm\">審核通過</button></td> \r");
		        				
		        				//Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部門主管審核通過</td> \r");
		        			}else{
		        				Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部門主管審核通過</td> \r");
		        			}
		        			
		        		}else if(rowaction.equals("L")){//副總審核ok
		        			
		        			Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">副總已審核通過</td> \r");
		        			
		        		}
		        		else if(rowaction.equals("M")){//管理部審核ok
		        			
		        			Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">管理部已審核通過</td> \r");
		        			
		        		}
		        	
		        	}else if(col.ColumnName.equals("請假開始時間") ||  col.ColumnName.equals("請假結束時間")){
		        		Data=Data.substring(0,Data.length()-3);
		        		Sb.append("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
		        	}else{
		        		Sb.append("    <td class=\"" + TRStyle + "\" data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
		        	}
	        }
	      
	      }
	    }
	   
	    Sb.append("  </tr> \r");
	
	    afterWriteRow(out);
	    
	    return Sb.toString();
	  }
	  	
	
}