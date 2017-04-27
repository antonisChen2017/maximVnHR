package cn.com.maxim.portal.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jUtil
{
	public Logger initLog4j(Class czass)
	{

		Properties prop = new Properties();
		// ### set log levels ###
		prop.setProperty("log4j.rootLogger", "debug ,stdout ,D ,E");
		// ### 输出到控制台 ###
		prop.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		prop.setProperty("log4j.appender.stdout.Target", "System.out");
		prop.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%d{ABSOLUTE} %5p %c{ 1 }:%L - %m%n");

		// ### 输出到日志文件 ###
		prop.setProperty("log4j.appender.D", " org.apache.log4j.DailyRollingFileAppender");
		prop.setProperty("log4j.appender.D.File", "vnlog/log.log");
		prop.setProperty("log4j.appender.D.Append", "true");
		prop.setProperty("log4j.appender.D.Threshold", "INFO");
		prop.setProperty("log4j.appender.D.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.D.layout.ConversionPattern", " %-d{yyyy-MM-dd HH:mm:ss}  [%F:%L ] - [ %p ]  %m%n");


		// ### 保存异常信息到单独文件 ###
		prop.setProperty("log4j.appender.E", "org.apache.log4j.DailyRollingFileAppender");
		prop.setProperty("log4j.appender.E.File", "vnlog/error.log");
		prop.setProperty("log4j.appender.E.Append", " true");
		prop.setProperty("log4j.appender.E.Threshold" , "ERROR");
		prop.setProperty("log4j.appender.E.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.E.layout.ConversionPattern", "%-d{yyyy-MM-dd HH:mm:ss} [%F:%L ]  - [ %p ]  %m%n");

		PropertyConfigurator.configure(prop);
		return Logger.getLogger(czass);
	}
}
