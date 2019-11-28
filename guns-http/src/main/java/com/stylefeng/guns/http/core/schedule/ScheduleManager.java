package com.stylefeng.guns.http.core.schedule;
/**
* create by guanqing
* 2019年11月27日 下午2:30:43
*/

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleManager {

	//日志记录操作延时
	private final int OPERATE_DELAY_TIME = 10;
	
	//异步操作记录日志的线程池
	private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
	
	private ScheduleManager() {}
	
	public static ScheduleManager logManager = new ScheduleManager();
	
	public static ScheduleManager me() {
		return logManager;
	}
	
	public void executeTask(TimerTask task) {
		executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}
}
