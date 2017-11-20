package com.lmtech.scheduler.engine;

/**
 * 调度器执行状态变更服务
 * @author huang.jb
 *
 */
public interface SchedulerRunStatusService {
	/**
	 * 任务任务处理状态
	 * @param jobCode
	 * @param status
	 */
	void setJobHandleStatus(String jobCode, String status);
}
