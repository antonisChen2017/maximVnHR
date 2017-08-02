package com.eh.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
 
public class NFDFlightDataTaskListener implements  ServletContextListener {
 
    public void contextInitialized(ServletContextEvent sce) {
	/**請假加班定時寄信14:00执行寄信方法**/
         new TimerManager();
         /**超時加班前一天16:00寄信**/
         new TimerCS4Manager();
         /**超時加班當天13:00寄信**/
         new TimerCS1Manager();
         /**12:00更新員工資料**/
         new TimerSetUser12Manager();
         
    }
 
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
         
    }
}
