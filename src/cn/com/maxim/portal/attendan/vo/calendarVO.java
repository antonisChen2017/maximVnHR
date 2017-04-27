package cn.com.maxim.portal.attendan.vo;

import java.io.Serializable;

public class calendarVO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8732522965852608745L;
	/**月歷html */
	private String calendarHtml;
	/**結束日 */
	private int endDay;
	
	public String getCalendarHtml()
	{
		return calendarHtml;
	}
	public void setCalendarHtml(String calendarHtml)
	{
		this.calendarHtml = calendarHtml;
	}
	public int getEndDay()
	{
		return endDay;
	}
	public void setEndDay(int endDay)
	{
		this.endDay = endDay;
	}
	@Override
	public String toString()
	{
		return "calendarVO [calendarHtml=" + calendarHtml + ", endDay=" + endDay + "]";
	}
	
}
