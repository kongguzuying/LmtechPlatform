package com.lmtech.scheduler.engine.support;

import com.lmtech.scheduler.config.SchedulerJobConfig;
import com.lmtech.scheduler.engine.*;
import com.lmtech.scheduler.service.SchedulerJobConfigService;
import com.lmtech.util.ClassUtil;
import com.lmtech.util.LoggerManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchedulerEngineImpl implements SchedulerEngine, ApplicationContextAware {
	@Autowired
	private SchedulerAdapter schedulerAdapter;
	@Autowired
	private SchedulerJobConfigService schedulerJobConfigService;

	private List<SchedulerJobListener> jobListeners = new ArrayList<>();
	private SchedulerJobGroup jobGroup = new SchedulerJobGroup();
	private List<String> components = null;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SchedulerJobListener schedulerJobClearListener = (SchedulerJobListener) context.getBean("schedulerJobClearListener");
		SchedulerJobListener schedulerTriggerJobListener = (SchedulerJobListener) context.getBean("schedulerTriggerJobListener");
		SchedulerJobListener schedulerLogJobListener = (SchedulerJobListener) context.getBean("schedulerLogJobListener");
		jobListeners.add(schedulerJobClearListener);
		jobListeners.add(schedulerTriggerJobListener);
		jobListeners.add(schedulerLogJobListener);

		List<SchedulerJobConfig> jobConfigs = schedulerJobConfigService.getSchedulerJobConfigs();
		jobGroup.init(jobConfigs);

		LoggerManager.info(String.format("fetch %1$d scheduler job", jobConfigs.size()));
		schedulerAdapter.startScheduler();
		schedulerAdapter.addGlobalListeners(jobListeners);

		List<SchedulerJob> schedulerJobs = this.getShedualJobs();
		if (schedulerJobs != null && schedulerJobs.size() > 0) {
			for (SchedulerJob schedulerJob : schedulerJobs) {
				try {
					this.start(schedulerJob.getConfig().getCode());
				} catch (Exception e) {
					LoggerManager.error(String.format("启动作业：%1$s 失败", schedulerJob.getConfig().getCode()), e);
				}
			}
		}
		LoggerManager.info("scheduler started");
	}
	
	@Override
	public void start(String jobCode) {
		if (jobGroup.existSchedulerJob(jobCode)) {
			if (!jobGroup.isStarted(jobCode)) {
				final SchedulerJobConfig jobConfig = schedulerJobConfigService.get(jobCode);
				schedulerAdapter.addJob(jobConfig);
				
				SchedulerJob job = jobGroup.getSchedulerJob(jobCode);
				job.setStatus(SchedulerJob.STATUS_RUNNING);
			} else {
				LoggerManager.warn(String.format("warn: job %1$s has started", jobCode));
			}
		} else {
			LoggerManager.warn(String.format("warn: job %1$s does not exist", jobCode));
		}
	}
	
	@Override
	public void stop(String jobCode) {
		if (jobGroup.existSchedulerJob(jobCode)) {
			if (jobGroup.isStarted(jobCode)) {
				SchedulerJob job = jobGroup.getSchedulerJob(jobCode);
				schedulerAdapter.removeJob(job.getConfig());
				job.setStatus(SchedulerJob.STATUS_STOPPED);
			} else {
				LoggerManager.warn(String.format("job %1$s does not start", jobCode));
			}
		} else {
			LoggerManager.warn(String.format("job %1$s does not exist", jobCode));
		}
	}

	@Override
	public void addJob(SchedulerJobConfig jobConfig) {
		if (jobConfig != null) {
			if (!jobGroup.existSchedulerJob(jobConfig.getCode())) {
				schedulerJobConfigService.add(jobConfig);
				
				SchedulerJob job = new SchedulerJob();
				job.setConfig(jobConfig);
				jobGroup.addSchedulerJob(job);
				
				LoggerManager.info(String.format("job %1$s add finished", jobConfig.getCode()));
			} else {
				LoggerManager.warn(String.format("warn: job %1$s has exist, could not add", jobConfig.getCode()));
			}
		} else {
			LoggerManager.warn("warn: job is null, could not add");
		}
	}
	
	public void updateJob(SchedulerJobConfig jobConfig) {
		if (jobConfig != null) {
			this.removeJob(jobConfig.getCode());
			
			schedulerJobConfigService.add(jobConfig);
			
			SchedulerJob job = new SchedulerJob();
			job.setConfig(jobConfig);
			jobGroup.addSchedulerJob(job);
			
			LoggerManager.info(String.format("job %1$s update finished", jobConfig.getCode()));
		} else {
			LoggerManager.warn("warn: job is null, could not update");
		}
	}

	@Override
	public void removeJob(String jobCode) {
		SchedulerJobConfig jobConfig = schedulerJobConfigService.get(jobCode);
		if (jobConfig != null) {
			this.stop(jobCode);
			schedulerAdapter.removeJob(jobConfig);
			schedulerJobConfigService.remove(jobCode);
			jobGroup.removeSchedulerJob(jobCode);
		} else {
			LoggerManager.warn(String.format("job %1$s does not exist", jobCode));
		}
	}

	@Override
	public SchedulerJobConfig getSchedulerJob(String code) {
		return schedulerJobConfigService.get(code);
	}
	
	@Override
	public List<SchedulerJob> getShedualJobs() {
		return jobGroup.getSchedulerJobs();
	}

	@Override
	public void setJobHandleStatus(String jobCode, String status) {
		SchedulerJob job = jobGroup.getSchedulerJob(jobCode);
		job.setHandleStatus(status);
	}
	
	@Override
	public List<String> getSchedulerComponents() {
		if (components == null) {
			components = new ArrayList<String>();
			List<Class<?>> classes = ClassUtil.getAllClassByInterface(SchedulerComponent.class, "com.lmtech");
			for (Class<?> clazz : classes) {
				components.add(clazz.getName());
			}
		}
		return components;
	}
}

class SchedulerJobGroup {

	private Map<String, SchedulerJob> schedulerJobs = new HashMap<String, SchedulerJob>();
	
	public void init(List<SchedulerJobConfig> jobConfigs) {
		if (jobConfigs != null && jobConfigs.size() > 0) {
			for (SchedulerJobConfig jobConfig : jobConfigs) {
				SchedulerJob job = new SchedulerJob();
				job.setConfig(jobConfig);
				this.addSchedulerJob(job);
			}
		}
	}
	
	public SchedulerJob getSchedulerJob(String jobCode) {
		if (schedulerJobs.containsKey(jobCode)) {
			return schedulerJobs.get(jobCode);
		} else {
			return null;
		}
	}
	
	public List<SchedulerJob> getSchedulerJobs() {
		List<SchedulerJob> result = new ArrayList<SchedulerJob>();
		for (SchedulerJob job : schedulerJobs.values()) {
			result.add(job);
		}
		return result;
	}
	
	public boolean isStarted(String jobCode) {
		SchedulerJob job = this.getSchedulerJob(jobCode);
		if (job != null) {
			return job.getStatus().equals(SchedulerJob.STATUS_RUNNING);
		} else {
			return false;
		}
	}
	
	public boolean existSchedulerJob(String jobCode) {
		if (jobCode != null && !jobCode.equals("")) {
			return schedulerJobs.containsKey(jobCode);
		} else {
			return false;
		}
	}
	
	public void addSchedulerJob(SchedulerJob job) {
		if (job != null) {
			String jobCode = job.getConfig().getCode();
			if (!existSchedulerJob(jobCode)) {
				schedulerJobs.put(jobCode, job);
			}
		}
	}
	
	public void removeSchedulerJob(String jobCode) {
		if (jobCode != null && !jobCode.trim().equals("")) {
			if (schedulerJobs.containsKey(jobCode)) {
				schedulerJobs.remove(jobCode);
			}
		}
	}
}
