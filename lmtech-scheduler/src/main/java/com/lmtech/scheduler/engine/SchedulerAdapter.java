package com.lmtech.scheduler.engine;

import java.util.List;

import com.lmtech.scheduler.config.SchedulerJobConfig;

public interface SchedulerAdapter {
	void addJob(SchedulerJobConfig jobConfig);
	void removeJob(SchedulerJobConfig jobConfig);
	void startScheduler();
	void stopScheduler();
	void addGlobalListener(SchedulerJobListener listener);
	void addGlobalListeners(List<SchedulerJobListener> listeners);
	void init(List<SchedulerJobConfig> jobConfigs);
	void triggerJob(SchedulerJobConfig jobConfig);
	boolean existJob(String jobCode, String group);
}
