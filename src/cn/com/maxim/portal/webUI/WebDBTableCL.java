package cn.com.maxim.portal.webUI;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import cn.com.maxim.htmltable.DBColumn;
import cn.com.maxim.potral.consts.keyConts;

public class WebDBTableCL extends WebDBTableEx
{

	public WebDBTableCL(Connection DBCon, String SQL)
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
			    if( !col.ColumnHeader.equals("ID")  
		    			  &&   !col.ColumnHeader.equals("MOTime") 
		    			  &&   !col.ColumnHeader.equals("returnMSG")
		    			  &&   !col.ColumnHeader.equals("ROLE")
		    			  &&   !col.ColumnHeader.equals("DAYCOUNT")
		    			  &&   !col.ColumnName.equals("LEAVEAPPLY")
		    			  ){
			    	  Sb.append("    <th class=\"text-center\">" + 
			          col.ColumnHeader + "</th>  \r");
		    	  }
			}
		}

		Sb.append("  </tr>  \r");
		Sb.append(" </thead>\r");
		return Sb.toString();
	}

	/**
	 * 待工
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
	public String getHTMLTableStopWork(PrintWriter out, String NoRowMessage, String TableStyle, String msg, String htmlButton, String page) throws SQLException
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
			Sb.append(getHeaderRowStopWork(out, "", page));
		}
		int i = 1;
		Sb.append("<tbody>");
		while (this.DBData.next())
		{

			Sb.append(getDataRowStopWork(out, "blue", page));

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
				if (!col.ColumnName.equals("ID") && !col.ColumnName.equals("MOTime") && !col.ColumnName.equals("returnMSG"))
				{

					if (col.ColumnName.equals("action"))
					{
						String rowID = getValue("ID", true);
						// String rowaction = getValue("action", true);
						// String returnMSG = getValue("returnMSG", true);

						// System.out.println("rowaction : "+rowaction);
						// System.out.println("delbut : "+delbut);
						// if(returnMSG==null){
						// returnMSG="";
						// }

						if (delbut.equals("0"))
						{
							Sb.append("<td class=\"text-right   " + TRStyle + "\"  >" + "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='" + rowID + "';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">刪除</button>	 \r");
						}
						else
						{
							// Sb.append(" <td class=\"text-right "+TRStyle+"\"
							// data-title='"+ col.ColumnHeader+"'"+ (this.nowrap
							// ? "nowrap " : "") + TDStyle + ">已提交</td> \r");
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
	 * 待工卡 tableRow
	 * 
	 * @param out
	 * @param TRStyle
	 * @param delbut
	 * @return
	 */
	private String getDataRowStopWork(PrintWriter out, String TRStyle, String page)
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
				    if(!col.ColumnName.equals("ID")  
			        		&&   !col.ColumnName.equals("returnMSG")
			        		&&   !col.ColumnName.equals("ROLE")
			        		&&   !col.ColumnName.equals("DAYCOUNT")
			        		&&   !col.ColumnName.equals("LEAVEAPPLY")
			        		){

					if (col.ColumnName.equals("action"))
					{
					    String rowID = getValue("ID", true);
			        		String rowaction = getValue("action", true);
			        		String returnMSG = getValue("returnMSG", true);
			        		String ROLE = getValue("ROLE", true);
			        		String DAYCOUNT = getValue("DAYCOUNT", true);
			        		String LEAVEAPPLY = getValue("LEAVEAPPLY", true);
			        		if(LEAVEAPPLY==null){
			        			LEAVEAPPLY="0";
			        		}
			        		logger.info("待工卡 ======================");
			        		logger.info("待工卡 rowID : "+rowID);
			        		logger.info("待工卡 rowaction : "+rowaction);
			        		logger.info("待工卡 page : "+page);
			        		logger.info("待工卡 LEAVEAPPLY : "+LEAVEAPPLY);
			        		logger.info("待工卡 DAYCOUNT : "+DAYCOUNT);
			        		logger.info("待工卡 ROLE : "+ROLE);
			        		logger.info("待工卡 ======================");
			        		//Double idaycount=Double.parseDouble(DAYCOUNT);
			        		
			        		if(returnMSG==null){
			        			returnMSG="";
			        		}
			        		if(rowaction.equals(keyConts.dbTableS)){//記錄
			        			if(page.equals(keyConts.pageSave)){//部門或個人填寫頁面
			        			    if(LEAVEAPPLY.equals("0")){
			        			    Sb.append("<td class=\"text-right   "+TRStyle+"\"  >"
		        						+ "\n <button onclick=\"ActionForm.act.value='Delete';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">删除</button>"
		        							+ "\n <button onclick=\"ActionForm.act.value='Update';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-info  btn-sm\">编辑</button>"
		        						+ " \n <button onclick=\"ActionForm.act.value='Refer';ActionForm.rowID.value='"+rowID+"';var index = layer.load(1, {shade: [0.1,'#fff'] });ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">送出待工单</button></td> \r");
			        			    }
			        			}
			        		}
			        		if(rowaction.equals(keyConts.dbTableCRStatuS_T)){//提交
			        		    if(page.equals(keyConts.pageSave)){//部門或個人填寫頁面
			        			  if(LEAVEAPPLY.equals("0")){
			        			      Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待审核</td> \r");   
			        			  }
			        			}
			        		    if(page.equals(keyConts.pageUsList)){//單位主管
			        			 if(LEAVEAPPLY.equals("0")){
			        			     Sb.append( getRUButton(keyConts.dbTableCRStatuS_U,rowID,TRStyle)); 
			        			  }
			        			
			        			}
			        		    if(page.equals(keyConts.pageB)){//單位主管
			        			 if(LEAVEAPPLY.equals("0")){
			        			     Sb.append( getRUButton(keyConts.dbTableCRStatuS_B,rowID,TRStyle)); 
			        			  }
			        		    }
			        		    if(page.equals(keyConts.pageLList)){//經理
			        			 if(LEAVEAPPLY.equals("0")){
			        			     Sb.append( getRUButton(keyConts.dbTableCRStatuS_L,rowID,TRStyle)); 
			        			  }
			        		    }
			        		    if(page.equals(keyConts.pageDtmList)){//部門主管
			        			 if(LEAVEAPPLY.equals("0")){
			        			     Sb.append( getRUButton(keyConts.dbTableCRStatuS_D,rowID,TRStyle)); 
			        			  }
			        		    }
			        		  
			        		}
			        		if(rowaction.equals(keyConts.EmpRoleG)){//組長
			        		    
			        		    if(page.equals(keyConts.EmpRoleG)){//單位主管
        			        		    if(LEAVEAPPLY.equals("0")){
        			        			      Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">组长审核通过</td> \r");
        				        			  }
        			        		    if(LEAVEAPPLY.equals("1")){
        			        			      Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
        				        			  }
			        		    }
			        		    if(page.equals(keyConts.pageUsList)){//單位主管
			        			
			        			  if(LEAVEAPPLY.equals("0")){
			        			      Sb.append( getRUButton(keyConts.dbTableCRStatuS_U,rowID,TRStyle)); 
				        			  }
			        			  if(LEAVEAPPLY.equals("1")){
			        			      Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        			  }
			        		    }
			         		    if(page.equals(keyConts.pageDtmList)){//部門主管
			         			  if(LEAVEAPPLY.equals("0")){
			         			     Sb.append( getRUButton(keyConts.dbTableCRStatuS_D,rowID,TRStyle)); 
				        		  }
			         			  if(LEAVEAPPLY.equals("1")){
			         			     Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
					        	}
			         		    }
			         		   if(page.equals(keyConts.pageSave)){//填寫
			         			  if(LEAVEAPPLY.equals("0")){
			         			     Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">组长审核通过</td> \r");
				        		  }
			         			  if(LEAVEAPPLY.equals("1")){
			         			     Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
					        	}
			         		    }
			         		  if(page.equals(keyConts.pageLList)){//單位主管
			         		     if(LEAVEAPPLY.equals("0")){
			         			   Sb.append( getRUButton(keyConts.dbTableCRStatuS_L,rowID,TRStyle)); 
				        		  }
			         		    if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			         		  }
			         		 if(page.equals(keyConts.pageB)){//單位主管
			         		    if(LEAVEAPPLY.equals("0")){
			        			    Sb.append( getRUButton(keyConts.dbTableCRStatuS_B,rowID,TRStyle)); 
				        		  }
			         			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			         		  }
			        		}
			        		if(rowaction.equals(keyConts.EmpRoleU)){//
			        		    
			        		    if(page.equals(keyConts.pageUsList)){//單位主管
			        			
			        			  if(LEAVEAPPLY.equals("0")){
			        			      Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">单位主管审核通过</td> \r");
				        			  }
			        			  if(LEAVEAPPLY.equals("1")){
			        			      Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        			  }
			        		    }
			         		    if(page.equals(keyConts.pageDtmList)){//部門主管
			         			  if(LEAVEAPPLY.equals("0")){
			         			     Sb.append( getRUButton(keyConts.dbTableCRStatuS_D,rowID,TRStyle)); 
				        		  }
			         			  if(LEAVEAPPLY.equals("1")){
			         			     Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
					        	}
			         		    }
			         		   if(page.equals(keyConts.pageSave)){//填寫
			         			  if(LEAVEAPPLY.equals("0")){
			         			     Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">单位主管审核通过</td> \r");
				        		  }
			         			  if(LEAVEAPPLY.equals("1")){
			         			     Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
					        	}
			         		    }
			         		  if(page.equals(keyConts.pageLList)){//單位主管
			         		     if(LEAVEAPPLY.equals("0")){
			         			   Sb.append( getRUButton(keyConts.dbTableCRStatuS_L,rowID,TRStyle)); 
				        		  }
			         		    if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			         		  }
			         		 if(page.equals(keyConts.pageB)){//單位主管
			         		    if(LEAVEAPPLY.equals("0")){
			        			    Sb.append( getRUButton(keyConts.dbTableCRStatuS_B,rowID,TRStyle)); 
				        		  }
			         			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			         		  }
			        		}
			        		if(rowaction.equals(keyConts.EmpRoleD)){//部門通過
			        		    if(page.equals(keyConts.pageDtmList)){//部門主管
			         			  if(LEAVEAPPLY.equals("0")){
			         			     Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部门主管审核通过</td> \r");
				        		  }
			         			  if(LEAVEAPPLY.equals("1")){
			         			     Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
					        	}
			         		    }
			        		    
			        		    if(page.equals(keyConts. pageLList)){
			        			if(LEAVEAPPLY.equals("0")){
			        			     Sb.append( getRUButton(keyConts.dbTableCRStatuS_L,rowID,TRStyle)); 
				        		  }
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageB)){
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        			if(LEAVEAPPLY.equals("0")){
			        			    Sb.append( getRUButton(keyConts.dbTableCRStatuS_B,rowID,TRStyle)); 
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageSave)){
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageList)){//人事部查詢
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        			
			        			}
			        		}
			        		
			        		if(rowaction.equals(keyConts.dbTableCRStatuS_L)){//經理
			        		    if(page.equals(keyConts.pageB)){//部門主管
			        			if(LEAVEAPPLY.equals("0")){
			        			    Sb.append( getRUButton(keyConts.dbTableCRStatuS_B,rowID,TRStyle)); 
				        		  }
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        			}
			        		    if(page.equals(keyConts.pageSave)){//紀錄頁面
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        			}
			        		    if(page.equals(keyConts.pageLList)){//部門主管
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        			}
			        		    if(page.equals(keyConts.pageList)){//人事部查詢
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        			}
			        		}
			        		
			        		if(rowaction.equals(keyConts.dbTableCRStatuS_B)){//狀態副總通過
			        		    if(page.equals(keyConts.pageB)){//部門主管
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        			}
			        		    
			        		    if(page.equals(keyConts.pageLList)){//部門主管
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        		    }
			        		    
			        		    if(page.equals(keyConts.pageDtmList)){//部門主管
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        		    }
			        		    
			        		    if(page.equals(keyConts.pageUsList)){//部門主管
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        		    }
			        		    
			        		    if(page.equals(keyConts.pageSave)){//部門主管
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        		    }
			        		    
			        		    if(page.equals(keyConts.pageList)){//人事部查詢
			        			if(LEAVEAPPLY.equals("1")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">待工申请完成</td> \r");
				        		  }
			        			}
			        		}
			        	
			        		if(rowaction.equals(keyConts.StatusBR)){//狀態副總退回
			        		    if(page.equals(keyConts.pageLList)){//經理
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">副总审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageDtmList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">副总审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageUsList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">副总审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageSave)){//填寫頁面
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">副总审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageList)){//人事部查詢
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">副总审核退回</td> \r");
				        		  }
			        			}
			        		}
			        		
			        		if(rowaction.equals(keyConts.StatusLR)){//狀態經理退回
			        		    if(page.equals(keyConts.pageLList)){//經理
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">经理审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageB)){//副總
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">经理审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageDtmList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">经理审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageUsList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">经理审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageSave)){//填寫頁面
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">经理审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageList)){//人事部查詢
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">经理审核退回</td> \r");
				        		  }
			        			}
			        		}
			        		if(rowaction.equals(keyConts.StatusDR)){//狀態部門退回
			        		    if(page.equals(keyConts.pageLList)){//經理
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部门主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageB)){//副總
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部门主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageDtmList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部门主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageUsList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部门主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageSave)){//填寫頁面
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部门主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageList)){//人事部查詢
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">部门主管审核退回</td> \r");
				        		  }
			        			}
			        		}
			        		if(rowaction.equals(keyConts.StatusUR)){//狀態單位退回
			        		    if(page.equals(keyConts.pageLList)){//經理
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">单位主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageB)){//副總
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">单位主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageDtmList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">单位主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageUsList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">单位主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageSave)){//填寫頁面
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">单位主管审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageList)){//人事部查詢
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">单位主管审核退回</td> \r");
				        		  }
			        			}
			        		}
			        		if(rowaction.equals(keyConts.StatusGR)){//狀態組長退回
			        		    if(page.equals(keyConts.pageLList)){//經理
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">组长审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageB)){//副總
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">组长审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageDtmList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">组长审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageUsList)){//部門主管
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">组长审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageSave)){//填寫頁面
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">组长审核退回</td> \r");
				        		  }
			        		    }
			        		    if(page.equals(keyConts.pageList)){//人事部查詢
			        			if(LEAVEAPPLY.equals("2")){
			        			    Sb.append("    <td class=\"text-right   "+TRStyle+"\"  data-title='"+ col.ColumnHeader+"'"+ (this.nowrap ? "nowrap " : "") + TDStyle + ">组长审核退回</td> \r");
				        		  }
			        			}
			        		}
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

/**
 * 退回與審核按鈕
 * @param rowID
 * @param TRStyle
 * @return
 */
private String getRUButton(String act,String rowID, String TRStyle){

	  String html="<td class=\"text-right  "+TRStyle+"\"  >"
		+ "\n <button onclick=\"upReturn("+rowID+")\"   type=\"button\" class=\"btn btn-warning  btn-sm\">退回</button>"
		+ " \n <button onclick=\"ActionForm.act.value='"+act+"';ActionForm.rowID.value='"+rowID+"';ActionForm.submit();\"   type=\"button\" class=\"btn btn-primary  btn-sm\">審核通過</button></td> \r";
	  return html;
}

}
