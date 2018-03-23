package com.lmtech.scheduler.engine;

import java.util.List;

import com.lmtech.scheduler.config.SchedulerJobConfig;

/**
 * 调度引擎
 * @author haung.jb
 *
 */
public interface SchedulerEngine extends SchedulerRunStatusService {
	/**
	 * 启动任务
	 * @param jobCode
	 */
	void start(String jobCode);
	/**
	 * 停止任务
	 * @param jobCode
	 */
	void stop(String jobCode);
	/**
	 * 添加任务
	 * @param job
	 */
	void addJob(SchedulerJobConfig job);
	/**
	 * 更新任务
	 * @param job
	 */
	void updateJob(SchedulerJobConfig job);
	/**
	 * 删除任务
	 * @param jobCode
	 */
	void removeJob(String jobCode);
	/**
	 * 获取任务
	 * @param code
	 * @return
	 */
	SchedulerJobConfig getSchedulerJob(String code);
	/**
	 * 获取任务列表
	 * @return
	 */
	List<SchedulerJob> getShedualJobs();
	/**
	 * 获取调度组件列表
	 * @return
	 */
	List<String> getSchedulerComponents();
}
