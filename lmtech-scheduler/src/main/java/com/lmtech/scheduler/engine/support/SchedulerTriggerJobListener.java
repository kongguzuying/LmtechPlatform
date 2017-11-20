package com.lmtech.scheduler.engine.support;

import java.util.List;

import com.lmtech.scheduler.config.SchedulerJobConfig;
import com.lmtech.scheduler.engine.SchedulerAdapter;
import com.lmtech.scheduler.engine.SchedulerContext;
import com.lmtech.scheduler.engine.SchedulerJobListener;
import com.lmtech.scheduler.service.SchedulerJobConfigService;
import com.lmtech.util.LoggerManager;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerTriggerJobListener implements SchedulerJobListener {
	@Autowired
	private SchedulerJobConfigService schedulerJobConfigService;
	@Autowired
	private SchedulerAdapter schedulerAdapter;
	 
	@Override
	public void actionPerform(SchedulerContext context) {
		String jobCode = context.getContextT(SchedulerContext.CONTEXT_JOB_CODE);
		
		List<SchedulerJobConfig> triggerJobConfigs = schedulerJobConfigService.getTriggerSchedulerJobConfigs(jobCode);
		
		for (SchedulerJobConfig jobConfig : triggerJobConfigs) {
			/*if (!schedulerAdapter.existJob(jobConfig.getCode(), jobConfig.getGroup())) {
				//create job detail
				schedulerAdapter.addJob(jobConfig);
			}*/
			
			schedulerAdapter.triggerJob(jobConfig);
			LoggerManager.debug(String.format("from job %1$s to trigger job %2$s", jobCode, jobConfig.getCode()));
		}
	}

	// property
	public SchedulerJobConfigService getSchedulerJobConfigService() {
		return schedulerJobConfigService;
	}

	public void setSchedulerJobConfigService(
			SchedulerJobConfigService schedulerJobConfigService) {
		this.schedulerJobConfigService = schedulerJobConfigService;
	}

	public SchedulerAdapter getSchedulerAdapter() {
		return schedulerAdapter;
	}

	public void setSchedulerAdapter(SchedulerAdapter schedulerAdapter) {
		this.schedulerAdapter = schedulerAdapter;
	}

}
