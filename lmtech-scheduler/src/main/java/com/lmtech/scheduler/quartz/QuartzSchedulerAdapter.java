package com.lmtech.scheduler.quartz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;

import com.lmtech.scheduler.config.SchedulerJobConfig;
import com.lmtech.scheduler.engine.SchedulerAdapter;
import com.lmtech.scheduler.engine.SchedulerContext;
import com.lmtech.scheduler.engine.SchedulerJobListener;
import com.lmtech.util.LoggerManager;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Service;

@Service
public class QuartzSchedulerAdapter implements SchedulerAdapter {

	private Scheduler scheduler;
	
	@Override
	public void addJob(SchedulerJobConfig jobConfig) {
		if (jobConfig != null && jobConfig.getComponentConfig() != null) {
			try {
				this.createJob(jobConfig);
			} catch (ClassNotFoundException e) {
				LoggerManager.error(e);
			} catch (ParseException e) {
				LoggerManager.error(e);
			} catch (SchedulerException e) {
				LoggerManager.error(e);
			}
		}
	}

	@Override
	public void removeJob(SchedulerJobConfig jobConfig) {
		try {
			this.deleteJob(jobConfig);
		} catch (SchedulerException e) {
			LoggerManager.error(e);
		} catch (Exception e) {
			LoggerManager.error(e);
		}
	}

	@Override
	public void startScheduler() {
		try {
			if (scheduler == null) {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
			}
			if (!scheduler.isStarted()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			LoggerManager.error(e);
		}
	}
	
	@Override
	public void stopScheduler() {
		try {
			if (scheduler != null && !scheduler.isShutdown()) {
				scheduler.shutdown(true);
			}
		} catch (SchedulerException e) {
			LoggerManager.error(e);
		}
	}
	
	@Override
	public void addGlobalListener(SchedulerJobListener listener) {
		List<SchedulerJobListener> listeners = new ArrayList<SchedulerJobListener>();
		listeners.add(listener);
		this.addGlobalListeners(listeners);
	}

	@Override
	public void addGlobalListeners(final List<SchedulerJobListener> listeners) {
		try {
			if (listeners == null || listeners.size() <= 0) throw new Exception("listeners should not be null or empty");

			scheduler.getListenerManager().addJobListener(new JobListener() {
				@Override
				public void jobWasExecuted(JobExecutionContext context, JobExecutionException e) {
					JobDetail executedJob = context.getJobDetail();
					JobDataMap dataMap = executedJob.getJobDataMap();
					SchedulerContext schedulerContext = (SchedulerContext) dataMap.get("context");
					for (SchedulerJobListener listener : listeners) {
						try {
							listener.actionPerform(schedulerContext);
							LoggerManager.debug(String.format("listener %1$s handle end", listener.getClass().getSimpleName()));
						} catch (Exception err) {
							LoggerManager.error(e);
						}
					}
				}
				@Override
				public void jobToBeExecuted(JobExecutionContext arg0) {
				}
				@Override
				public void jobExecutionVetoed(JobExecutionContext arg0) {
				}
				@Override
				public String getName() {
					return "jobGlobalListener";
				}
			});
		} catch (SchedulerException e) {
			LoggerManager.error(e);
		} catch (Exception e) {
			LoggerManager.error(e);
		}
	}

	@Override
	public void init(List<SchedulerJobConfig> jobConfigs) {
		try {
			if (scheduler == null) {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
			}
			
			if (jobConfigs != null && jobConfigs.size() > 0) {
				for (SchedulerJobConfig jobConfig : jobConfigs) {
					this.createJob(jobConfig);
				}
			}
		} catch (Exception e) {
			LoggerManager.error(e);
		}
	}

	@Override
	public void triggerJob(SchedulerJobConfig jobConfig) {
		if (jobConfig != null) {
			try {
				JobKey jobKey = new JobKey(jobConfig.getCode(), jobConfig.getGroup());
				scheduler.triggerJob(jobKey);
			} catch (SchedulerException e) {
				LoggerManager.error(e);
			}
		}
	}

	@Override
	public boolean existJob(String jobCode, String group) {
		JobDetail jd;
		try {
			JobKey jobKey = new JobKey(jobCode, group);
			jd = scheduler.getJobDetail(jobKey);
			return jd != null;
		} catch (SchedulerException e) {
			LoggerManager.error(e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private void createJob(SchedulerJobConfig jobConfig) throws ClassNotFoundException, ParseException, SchedulerException {
		String jobCode = jobConfig.getCode();
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("jobConfig", jobConfig);
		JobDetailImpl job = new JobDetailImpl();
		job.setName(jobCode);
		job.setGroup(jobConfig.getGroup());
		job.setJobClass(NormalQuartzJob.class);
		job.setJobDataMap(dataMap);
		
		Trigger trigger;
		if (jobConfig.getType() == SchedulerJobConfig.TYPE_NORMAL) {
			// use cron trigger
			CronTriggerImpl cronTrigger = new CronTriggerImpl();
			JobKey jobKey = new JobKey(jobCode, jobConfig.getGroup());
			cronTrigger.setJobKey(jobKey);
			cronTrigger.setGroup(jobKey.getGroup());
			cronTrigger.setName(jobKey.getName());
			cronTrigger.setCronExpression(jobConfig.getSchedulerExpression());
			trigger = cronTrigger;
		} else {
			throw new SchedulerException(String.format("job type %1$s not found", jobConfig.getType()));
		}
		scheduler.scheduleJob(job, trigger);
		scheduler.addJob(job, true, true);
	}
	
	private void deleteJob(SchedulerJobConfig jobConfig) throws SchedulerException {
		JobKey jobKey = new JobKey(jobConfig.getCode(), jobConfig.getGroup());
		scheduler.deleteJob(jobKey);
	}

	// property
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

}
