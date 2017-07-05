package com.eh.util;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import cn.com.maxim.portal.util.Log4jUtil;
public class TimerManager {
    Log4jUtil lu=new Log4jUtil();
	org.apache.log4j.Logger logger  =lu.initLog4j(TimerManager.class);
  //时间间隔
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    public TimerManager() {
         Calendar calendar = Calendar.getInstance(); 
                
         /*** 定制每日15:30执行方法 ***/

         calendar.set(Calendar.HOUR_OF_DAY, 15);
         calendar.set(Calendar.MINUTE, 30);
         calendar.set(Calendar.SECOND, 0);
          
         Date date=calendar.getTime(); //第一次执行定时任务的时间
         logger.info(date);
         logger.info("before 方法比较："+date.before(new Date()));
         //如果第一次执行定时任务的时间 小于 当前的时间
         //此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。循环执行的周期则以当前时间为准
         if (date.before(new Date())) {
             date = this.addDay(date, 0);
             logger.info(date);
         }
         logger.info("此任务在下个时间点执行");
         Timer timer = new Timer();
          
         NFDFlightDataTimerTask task = new NFDFlightDataTimerTask();
         //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
         timer.schedule(task,date,PERIOD_DAY);
         logger.info("安排指定的任务在指定的时间开始进行重复的固定延迟执行。");
        }

        // 增加或减少天数
        public Date addDay(Date date, int num) {
         Calendar startDT = Calendar.getInstance();
         startDT.setTime(date);
         startDT.add(Calendar.DAY_OF_MONTH, num);
         return startDT.getTime();
        }
}
