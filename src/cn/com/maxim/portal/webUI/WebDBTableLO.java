package cn.com.maxim.portal.webUI;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import cn.com.maxim.htmltable.DBColumn;
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
		Sb.append("   <div class=\"caption\"> <i class=\"fa fa-list\"></i>查詢結果  \r");
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
					else if (col.ColumnName.equals("待料開始時間") || col.ColumnName.equals("待料結束時間"))
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
}
