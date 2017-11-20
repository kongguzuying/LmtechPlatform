package com.lmtech.scheduler.service;

import java.util.List;

import com.lmtech.scheduler.config.SchedulerJobConfig;
import com.lmtech.service.DbManagerBase;

/**
 * system job manager
 * @author huang.jb
 *
 */
public interface SchedulerJobConfigService extends DbManagerBase<SchedulerJobConfig> {
	List<SchedulerJobConfig> getSchedulerJobConfigs();
	List<SchedulerJobConfig> getNormalSchedulerJobConfigs();
	List<SchedulerJobConfig> getTriggerSchedulerJobConfigs(String dependenceJobCode);
}
