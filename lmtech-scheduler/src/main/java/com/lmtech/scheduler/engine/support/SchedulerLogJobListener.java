package com.lmtech.scheduler.engine.support;

import com.lmtech.scheduler.engine.SchedulerContext;
import com.lmtech.scheduler.engine.SchedulerJobListener;
import com.lmtech.scheduler.model.SchedulerLog;
import com.lmtech.scheduler.service.SchedulerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SchedulerLogJobListener implements SchedulerJobListener {
	@Autowired
	private SchedulerLogService schedulerLogService;
	
	@Override
	public void actionPerform(SchedulerContext context) {
		Date startDate = context.getContextT(SchedulerContext.CONTEXT_START_DATE);
		Date endDate = context.getContextT(SchedulerContext.CONTEXT_END_DATE);
		int executeTime = context.getContextT(SchedulerContext.CONTEXT_USE_TIME);
		String jobCode = context.getContextT(SchedulerContext.CONTEXT_JOB_CODE);
		String jobName = context.getContextT(SchedulerContext.CONTEXT_JOB_NAME);
		boolean executeResult = context.getContextT(SchedulerContext.CONTEXT_EXECUTE_RESULT);
		String executeMessage = context.getContextT(SchedulerContext.CONTEXT_EXECUTE_MESSAGE);
		
		SchedulerLog log = new SchedulerLog();
		log.setJobCode(jobCode);
		log.setJobName(jobName);
		log.setStartDate(startDate);
		log.setEndDate(endDate);
		log.setExecuteTime(executeTime);
		log.setExecuteResult(executeResult);
		log.setMessage(executeMessage);
		schedulerLogService.add(log);
	}

	// property
	public SchedulerLogService getSchedulerLogService() {
		return schedulerLogService;
	}

	public void setSchedulerLogService(SchedulerLogService schedulerLogService) {
		this.schedulerLogService = schedulerLogService;
	}

}
