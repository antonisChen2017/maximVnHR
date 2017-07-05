package cn.com.maxim.portal.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import cn.com.maxim.DB.DBManager;
import cn.com.maxim.portal.PortalManager;
import cn.com.maxim.portal.UserDescriptor;

import cn.com.maxim.portal.PortalUserList;
import cn.com.maxim.portal.util.Log4jUtil;
import cn.com.maxim.portal.util.vnStringUtil;
/**
 * 短網址轉向
 * @author Antonis.chen
 *
 */
public class LoginChangeService extends HttpServlet{

    
    private static final long serialVersionUID = 154413699693356682L;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		Log4jUtil lu = new Log4jUtil();
		Logger logger = lu.initLog4j(ExcelOutService.class);
		request.getSession().setAttribute("LogonResult","");
		String Url="/PortalManager?pk=AT&pg=Attendan&mtb=3&pl=dm&_t=0";
		request.getSession().setAttribute("employeeNoSys", "16093520");
		SAXBuilder xmlBuilder = new SAXBuilder();
		Document doc = xmlBuilder.build(new File("D:\\mxportal\\script\\dba.xml"));
		logger.info(doc.getRootElement().getChild("drivers").getChild("driver").getChild("url").getText().trim());
		DBManager dba = new DBManager(doc);
		UserDescriptor User = new UserDescriptor("linda",  dba.getConnection("VH"));
		PortalUserList.UserListPut(request.getSession().getId(),User);		
		response.sendRedirect(Url);

    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
		{
			processRequest(request, response);
		}
		catch (Exception e)
		{
			Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(ExcelOutService.class);
			logger.error(vnStringUtil.getExceptionAllinformation(e));
		}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
		{
			processRequest(request, response);
		}
    	catch (Exception e)
		{
			// TODO Auto-generated catch block
    		Log4jUtil lu = new Log4jUtil();
			Logger logger = lu.initLog4j(ExcelOutService.class);
			logger.error(vnStringUtil.getExceptionAllinformation(e));
		}
    }
}
