package cn.com.maxim.portal.webUI;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import cn.com.maxim.htmltable.DBColumn;
import cn.com.maxim.portal.attendan.ro.repAttendanceRO;
import cn.com.maxim.portal.attendan.vo.lateOutEarlyVO;
import cn.com.maxim.portal.attendan.vo.repAttendanceVO;
import cn.com.maxim.portal.util.DBUtil;
import cn.com.maxim.portal.util.DataStringUtil;
import cn.com.maxim.portal.util.SqlUtil;
/**
 * 遲到早退名單 table
 * @author Antonis.chen
 *
 */


public class WebDBTableLO  extends WebDBTableCL
{

	public WebDBTableLO(Connection DBCon, String SQL)
	{
		super(DBCon, SQL);
		// TODO Auto-generated constructor stub
	}
	private String getHeaderRowStopWork(PrintWriter out, String TRStyle, String delbut)
	{
		StringBuilder Sb = new StringBuilder("");
		if (TRStyle.equals(""))
		{
			TRStyle = "DefaultTR";
		}
		Sb.append(" <thead>  \r");
		Sb.append("  <tr class=\"" + TRStyle + "\"> \r");
		for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
		{
			DBColumn col = (DBColumn) i.next();
			if (col.getColumnVisible())
			{
				if (!col.ColumnHeader.equals("ID") && !col.ColumnHeader.equals("action") )
				{
					Sb.append("    <th class=\"text-center\">" + col.ColumnHeader + "</th>  \r");
				}
			}
		}

		Sb.append("  </tr>  \r");
		Sb.append(" </thead>\r");
		return Sb.toString();
	}

	
	private String getHeaderRowRA(PrintWriter out, String TRStyle, Connection con)
	{
		StringBuilder Sb = new StringBuilder("");
		if (TRStyle.equals(""))
		{
			TRStyle = "DefaultTR";
		}
		Sb.append(" <thead>  \r");
		Sb.append("  <tr class=\"" + TRStyle + "\"> \r");
		for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
		{
			DBColumn col = (DBColumn) i.next();
			if (col.getColumnVisible())
			{
				if (col.ColumnHeader.equals("姓名") )
				{
					Sb.append("    <th class=\"text-center\" width='20%' >" + col.ColumnHeader + "</th>  \r");
				}
				else if (col.ColumnHeader.equals("工號") )
				{
					Sb.append("    <th class=\"text-center\" width='10%' >" + col.ColumnHeader + "</th>  \r");
				}
				else if (col.ColumnHeader.equals("總共") )
				{					
					Sb.append("    <th class=\"text-center\" width='10%' >" + col.ColumnHeader + "</th>  \r");
				}else{
					Sb.append("    <th class=\"text-center\" width='4%' >" + col.ColumnHeader + "</th>  \r");
				}
			}
		}

		Sb.append("  </tr>  \r");
		Sb.append(" </thead>\r");
		return Sb.toString();
	}
	
	/**
	 * 請假卡TABLE
	 * 
	 * @param out
	 * @param NoRowMessage
	 * @param TableStyle
	 * @param msg
	 * @param htmlButton
	 * @param delbut
	 * @return
	 * @throws SQLException
	 */
	public String getHTMLTableRA(PrintWriter out, String NoRowMessage, String TableStyle, String msg, String htmlButton, Connection con,repAttendanceVO raVo) throws SQLException
	{
		StringBuilder Sb = new StringBuilder("");
		resetSno();
		if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
		{
			Sb.append("<table class=\"" + TableStyle + "\" border=\"" + this.Border + "\" cellspacing=\"" + this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + "\">   \r");
			Sb.append("  <tr><td>" + NoRowMessage + "</td></tr> \r");
			Sb.append("</table>  \r");
			return Sb.toString();
		}
		if (this.OutPutRowCount > 0)
		{
			movePage(this.OutPutRowCount, this.PageNumber, this.DBData);
		}

		Sb.append("<!-- BEGIN PAGE ROW-->  \r");
		Sb.append("   <div class=\"row \"> \r");
		Sb.append("   <div class=\"col-md-12 col-sm-12\"> \r");
		Sb.append("   <div class=\"portlet\"> \r");
		Sb.append("   <div class=\"portlet-title\">  \r");
		Sb.append("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查询結果  \r");
		if (!msg.equals(""))
		{
			Sb.append(msg);
		}
		Sb.append(" </div>  \r");
		Sb.append("	 </div>  \r");
		Sb.append("   <div class=\"portlet-body\">    \r");
		Sb.append("<table  id=\"data_table_1\" class=\"" + TableStyle + "\">    \r");
		writeFirstRow(out);
		if (this.MessageRowVisible)
		{
			Sb.append(this.getMessageRow(out, this.Message, this.MessageRowStyle));
		}
		if (this.OutputHeader)
		{
			Sb.append(getHeaderRowRA(out, "", con));
		}
		int i = 1;
		Sb.append("<tbody>");
		while (this.DBData.next())
		{

			Sb.append(getDataRowRA(out, "blue", con,raVo));

			if (i == this.OutPutRowCount + 1)
			{
				break;
			}
		}
		Sb.append(" </tbody>   \r");
		if (this.SummaryRowVisible)
		{
			Sb.append(this.getMessageRow(out, this.Summary, this.SummaryRowStyle));
		}
		writeLastRow(out);
		Sb.append("</table>   \r");
		Sb.append("<div class=\"form-actions right\">    \r");
		// 是否要excel按鈕
		if (!htmlButton.equals("")){
			Sb.append(htmlButton + "\r");
		 }
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
	 * 個人考績功能顯示表
	 * 
	 * @param out
	 * @param NoRowMessage
	 * @param TableStyle
	 * @param msg
	 * @param htmlButton
	 * @param delbut
	 * @return
	 * @throws SQLException
	 */
	public String getHTMLTableOne(PrintWriter out, String NoRowMessage, String TableStyle, String msg, String htmlButton, Connection con,lateOutEarlyVO eaVo) throws SQLException
	{
		StringBuilder Sb = new StringBuilder("");
		resetSno();
		if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
		{
			Sb.append("<table class=\"" + TableStyle + "\" border=\"" + this.Border + "\" cellspacing=\"" + this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + "\">   \r");
			Sb.append("  <tr><td>" + NoRowMessage + "</td></tr> \r");
			Sb.append("</table>  \r");
			return Sb.toString();
		}
		if (this.OutPutRowCount > 0)
		{
			movePage(this.OutPutRowCount, this.PageNumber, this.DBData);
		}

		Sb.append("<!-- BEGIN PAGE ROW-->  \r");
		Sb.append("   <div class=\"row \"> \r");
		Sb.append("   <div class=\"col-md-12 col-sm-12\"> \r");
		Sb.append("   <div class=\"portlet\"> \r");
		Sb.append("   <div class=\"portlet-title\">  \r");
		Sb.append("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查询結果  \r");
		if (!msg.equals(""))
		{
			Sb.append(msg);
		}
		Sb.append(" </div>  \r");
		Sb.append("	 </div>  \r");
		Sb.append("   <div class=\"portlet-body\">    \r");
		Sb.append("<table  id=\"data_table_1\" class=\"" + TableStyle + "\">    \r");
		writeFirstRow(out);
		if (this.MessageRowVisible)
		{
			Sb.append(this.getMessageRow(out, this.Message, this.MessageRowStyle));
		}
		if (this.OutputHeader)
		{
			Sb.append(getHeaderRowRA(out, "", con));
		}
		int i = 1;
		Sb.append("<tbody>");
		while (this.DBData.next())
		{

			Sb.append(getDataRowOne(out, "blue", con,eaVo));

			if (i == this.OutPutRowCount + 1)
			{
				break;
			}
		}
		Sb.append(" </tbody>   \r");
		if (this.SummaryRowVisible)
		{
			Sb.append(this.getMessageRow(out, this.Summary, this.SummaryRowStyle));
		}
		writeLastRow(out);
		Sb.append("</table>   \r");
		Sb.append("<div class=\"form-actions right\">    \r");
		// 是否要excel按鈕
		if (!htmlButton.equals("")){
			Sb.append(htmlButton + "\r");
		 }
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
	 * 
	 * @param out
	 * @param NoRowMessage
	 * @param TableStyle
	 * @param msg
	 * @param htmlButton
	 * @param delbut
	 * @return
	 * @throws SQLException
	 */
	public String getHTMLTableLO(PrintWriter out, String NoRowMessage, String TableStyle, String msg, String htmlButton, String delbut) throws SQLException
	{
		StringBuilder Sb = new StringBuilder("");
		resetSno();
		if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
		{
			Sb.append("<table class=\"" + TableStyle + "\" border=\"" + this.Border + "\" cellspacing=\"" + this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + "\">   \r");
			Sb.append("  <tr><td>" + NoRowMessage + "</td></tr> \r");
			Sb.append("</table>  \r");
			return Sb.toString();
		}
		if (this.OutPutRowCount > 0)
		{
			movePage(this.OutPutRowCount, this.PageNumber, this.DBData);
		}

		Sb.append("<!-- BEGIN PAGE ROW-->  \r");
		Sb.append("   <div class=\"row \"> \r");
		Sb.append("   <div class=\"col-md-12 col-sm-12\"> \r");
		Sb.append("   <div class=\"portlet\"> \r");
		Sb.append("   <div class=\"portlet-title\">  \r");
		Sb.append("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查询結果  \r");
		if (!msg.equals(""))
		{
			Sb.append(msg);
		}
		Sb.append(" </div>  \r");
		Sb.append("	 </div>  \r");
		Sb.append("   <div class=\"portlet-body\">    \r");
		Sb.append("<table  id=\"data_table_1\" class=\"" + TableStyle + "\">    \r");
		writeFirstRow(out);
		if (this.MessageRowVisible)
		{
			Sb.append(this.getMessageRow(out, this.Message, this.MessageRowStyle));
		}
		if (this.OutputHeader)
		{
			Sb.append(getHeaderRowStopWork(out, "", delbut));
		}
		int i = 1;
		Sb.append("<tbody>");
		while (this.DBData.next())
		{

			Sb.append(getDataRowLC(out, "blue", delbut));

			if (i == this.OutPutRowCount + 1)
			{
				break;
			}
		}
		Sb.append(" </tbody>   \r");
		if (this.SummaryRowVisible)
		{
			Sb.append(this.getMessageRow(out, this.Summary, this.SummaryRowStyle));
		}
		writeLastRow(out);
		Sb.append("</table>   \r");
		Sb.append("<div class=\"form-actions right\">    \r");
		// 是否要excel按鈕
		if (!htmlButton.equals("")){
			Sb.append(htmlButton + "\r");
		 }
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
	 * 
	 * @param out
	 * @param TRStyle
	 * @param delbut
	 * @return
	 */
	private String getDataRowLC(PrintWriter out, String TRStyle, String delbut)
	{
		StringBuilder Sb = new StringBuilder("");
		beforeWriteRow(out);

		Sb.append("  <tr > \r");
		for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
		{
			DBColumn col = (DBColumn) i.next();
			if (col.getColumnVisible())
			{
				String Data = getValue(col.ColumnName, true);
				String TDStyle = col.getColumnStyle();
				if ((Data == null) || (Data.equals("")))
				{
					Data = "&nbsp;";
				}
				if (TDStyle.length() > 0)
				{
					TDStyle = " class=\"" + TDStyle + "\" ";
				}
				if ( !col.ColumnName.equals("MOTime") && !col.ColumnName.equals("returnMSG"))
				{

					if (col.ColumnName.equals("action"))
					{
					
						
					//	System.out.println("delbut : "+delbut);

						if (delbut.equals("0"))
						{
						//	 System.out.println("Data : "+Data);
							Sb.append("    <td class=\"" + TRStyle + "\" data-title='" + col.ColumnHeader + "'" + (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
						}
						else
						{
							 System.out.println("Nothing");
						}

					}
					else if (col.ColumnName.equals("待料開始时间") || col.ColumnName.equals("待料結束时间"))
					{
						Data = Data.substring(0, Data.length() - 3);
						Sb.append("    <td class=\"" + TRStyle + "\" data-title='" + col.ColumnHeader + "'" + (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
					}
					else
					{
						Sb.append("    <td class=\"" + TRStyle + "\" data-title='" + col.ColumnHeader + "'" + (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
					}
				}

			}
		}

		Sb.append("  </tr> \r");

		afterWriteRow(out);

		return Sb.toString();
	}
	
	
	
	/**
	 * 月考勤表 tableRow
	 * 
	 * @param out
	 * @param TRStyle
	 * @param delbut
	 * @return
	 */
	private String getDataRowRA(PrintWriter out, String TRStyle, Connection con,repAttendanceVO raVo)
	{
		StringBuilder Sb = new StringBuilder("");
		beforeWriteRow(out);
	
		Sb.append("  <tr > \r");
		DataStringUtil du=new DataStringUtil();
		du.clear();
		for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
		{
			DBColumn col = (DBColumn) i.next();
			if (col.getColumnVisible())
			{
				String Data = getValue(col.ColumnName, true);
				String TDStyle = col.getColumnStyle();
				if ((Data == null) || (Data.equals("")))
				{
				 
					Data = "&nbsp;";
				    
					
				}
				if (TDStyle.length() > 0)
				{
					TDStyle = " class=\"" + TDStyle + "\" ";
				}else if (col.ColumnName.equals("总共") ){
					
					Sb.append("    <td class=\"" + TRStyle + "\" data-title='" + col.ColumnHeader + "'" + (this.nowrap ? "nowrap " : "") + TDStyle + ">" );
					Sb.append("    <a href=\"javascript:void(0);\" onclick=\"showAllData('"+Data+"');\"   > ");
					Sb.append("    统计</a></td> \r");
			
				}
				else{
					
					if (col.ColumnName.equals("工号") ){
						raVo.setEMPLOYEENO(Data);
					}
				    Sb.append("    <td class=\"" + TRStyle + "\" data-title='" + col.ColumnHeader + "'" + (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
				}
				

			}
		}

		Sb.append("  </tr> \r");

		afterWriteRow(out);

		return Sb.toString();
	}
	
	
	
	/**
	 * 個人月考勤表 tableRow
	 * 
	 * @param out
	 * @param TRStyle
	 * @param delbut
	 * @return
	 */
	private String getDataRowOne(PrintWriter out, String TRStyle, Connection con,lateOutEarlyVO eaVo)
	{
		StringBuilder Sb = new StringBuilder("");
		beforeWriteRow(out);
	
		Sb.append("  <tr > \r");
		DataStringUtil du=new DataStringUtil();
		du.clear();
		for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
		{
			DBColumn col = (DBColumn) i.next();
			if (col.getColumnVisible())
			{
				String Data = getValue(col.ColumnName, true);
				String TDStyle = col.getColumnStyle();
				if ((Data == null) || (Data.equals("")))
				{
				 
					Data = "&nbsp;";
				    
					
				}
				if (TDStyle.length() > 0)
				{
					TDStyle = " class=\"" + TDStyle + "\" ";
				}else if (col.ColumnName.equals("总共") ){
					
					Sb.append("    <td class=\"" + TRStyle + "\" data-title='" + col.ColumnHeader + "'" + (this.nowrap ? "nowrap " : "") + TDStyle + ">" );
					Sb.append("    <a href=\"javascript:void(0);\" onclick=\"showAllData('"+Data+"');\"   > ");
					Sb.append("    统计</a></td> \r");
			
				}
				else{
					
					if (col.ColumnName.equals("工号") ){
					    	eaVo.setEmpID(Data);
					}
				    Sb.append("    <td class=\"" + TRStyle + "\" data-title='" + col.ColumnHeader + "'" + (this.nowrap ? "nowrap " : "") + TDStyle + ">" + Data + "</td> \r");
				}
				

			}
		}

		Sb.append("  </tr> \r");

		afterWriteRow(out);

		return Sb.toString();
	}
	
	/**
	 * 請假卡TABLE
	 * 
	 * @param out
	 * @param NoRowMessage
	 * @param TableStyle
	 * @param msg
	 * @param htmlButton
	 * @param delbut
	 * @return
	 * @throws SQLException
	 */
	public String getHTMLTableLE(PrintWriter out, String NoRowMessage, String TableStyle, String msg, String htmlButton, String delbut) throws SQLException
	{
		StringBuilder Sb = new StringBuilder("");
		resetSno();
		if ((this.DBData.isAfterLast()) && (!NoRowMessage.equals("")))
		{
			Sb.append("<table class=\"" + TableStyle + "\" border=\"" + this.Border + "\" cellspacing=\"" + this.CellSpacing + "\" cellpadding=\"" + this.CellPadding + "\">   \r");
			Sb.append("  <tr><td>" + NoRowMessage + "</td></tr> \r");
			Sb.append("</table>  \r");
			return Sb.toString();
		}
		if (this.OutPutRowCount > 0)
		{
			movePage(this.OutPutRowCount, this.PageNumber, this.DBData);
		}

		Sb.append("<!-- BEGIN PAGE ROW-->  \r");
		Sb.append("   <div class=\"row \"> \r");
		Sb.append("   <div class=\"col-md-12 col-sm-12\"> \r");
		Sb.append("   <div class=\"portlet\"> \r");
		Sb.append("   <div class=\"portlet-title\">  \r");
		Sb.append("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查询結果  \r");
		if (!msg.equals(""))
		{
			Sb.append(msg);
		}
		Sb.append(" </div>  \r");
		Sb.append("	 </div>  \r");
		Sb.append("   <div class=\"portlet-body\">    \r");
		Sb.append("<table  id=\"data_table_1\" class=\"" + TableStyle + "\">    \r");
		writeFirstRow(out);
		if (this.MessageRowVisible)
		{
			Sb.append(this.getMessageRow(out, this.Message, this.MessageRowStyle));
		}
		if (this.OutputHeader)
		{
			Sb.append(getHeaderRowLE(out, "", delbut));
		}
		int i = 1;
		Sb.append("<tbody>");
		while (this.DBData.next())
		{

			Sb.append(getDataRowLC(out, "blue", delbut));

			if (i == this.OutPutRowCount + 1)
			{
				break;
			}
		}
		Sb.append(" </tbody>   \r");
		if (this.SummaryRowVisible)
		{
			Sb.append(this.getMessageRow(out, this.Summary, this.SummaryRowStyle));
		}
		writeLastRow(out);
		Sb.append("</table>   \r");
		Sb.append("<div class=\"form-actions right\">    \r");
		// 是否要excel按鈕
		if (!htmlButton.equals("")){
			Sb.append(htmlButton + "\r");
		 }
		Sb.append(" </div>  \r");
		Sb.append("</div>  \r");
		Sb.append("</div>  \r");
		Sb.append("</div>  \r");
		Sb.append("</div>   \r");
		Sb.append("<div class=\"clearfix\"> </div>   \r");
		Sb.append(" <!-- END PAGE ROW-->   \r");

		return Sb.toString();
	}
	
	private String getHeaderRowLE(PrintWriter out, String TRStyle, String delbut)
	{
		StringBuilder Sb = new StringBuilder("");
		if (TRStyle.equals(""))
		{
			TRStyle = "DefaultTR";
		}
		Sb.append(" <thead>  \r");
		Sb.append("  <tr class=\"" + TRStyle + "\"> \r");
		for (Iterator i = this.DBColumns.iterator(); i.hasNext();)
		{
			DBColumn col = (DBColumn) i.next();
			if (col.getColumnVisible())
			{
				if (col.ColumnHeader.equals("工號") && col.ColumnHeader.equals("姓名") && col.ColumnHeader.equals("部门") && col.ColumnHeader.equals("单位"))
				{
					Sb.append("<th class=\"text-center\"  width='4%'>" + col.ColumnHeader + "</th>  \r");
				}
				else if (col.ColumnHeader.equals("遲到次數") )
				{
					Sb.append("<th class=\"text-center\"  width='8%'>" + col.ColumnHeader + "</th>  \r");
				}
				else
				{
					Sb.append("<th class=\"text-center\"  width='2%'>" + col.ColumnHeader + "</th>  \r");
				}
			}
		}

		Sb.append("  </tr>  \r");
		Sb.append(" </thead>\r");
		return Sb.toString();
	}
	
}
