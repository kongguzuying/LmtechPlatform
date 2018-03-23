package com.lmtech.scheduler.engine.support;

import com.lmtech.scheduler.config.SchedulerJobConfig;
import com.lmtech.scheduler.engine.SchedulerContext;
import com.lmtech.scheduler.engine.SchedulerEngine;
import com.lmtech.scheduler.engine.SchedulerJobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 调度结束任务清除监听器
 * @author huang.jb
 *
 */
@Service
public class SchedulerJobClearListener implements SchedulerJobListener {
	@Autowired
	private SchedulerEngine schedulerEngine;
	
	@Override
	public void actionPerform(SchedulerContext context) {
		String jobCode = context.getContextT(SchedulerContext.CONTEXT_JOB_CODE);
		
		SchedulerJobConfig jobConfig = schedulerEngine.getSchedulerJob(jobCode);
		if (jobConfig.getExecuteMode() == SchedulerJobConfig.EXECUTE_MODEL_ONETIME) {
			//仅执行一次时，处理完成后删除任务
			schedulerEngine.removeJob(jobConfig.getCode());
		}
	}

	// property
	public SchedulerEngine getSchedulerEngine() {
		return schedulerEngine;
	}

	public void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		this.schedulerEngine = schedulerEngine;
	}

}
