package com.lmtech.scheduler.quartz;

import com.lmtech.common.ContextManager;
import com.lmtech.common.StateResult;
import com.lmtech.scheduler.config.SchedulerCompConfig;
import com.lmtech.scheduler.config.SchedulerJobConfig;
import com.lmtech.scheduler.engine.SchedulerComponent;
import com.lmtech.scheduler.engine.SchedulerContext;
import com.lmtech.scheduler.engine.SchedulerJob;
import com.lmtech.scheduler.engine.SchedulerRunStatusService;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.SpringUtil;
import com.lmtech.util.StringUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class NormalQuartzJob implements Job {

	@Override
	public void execute(JobExecutionContext executeContext) throws JobExecutionException {
		if (!SpringUtil.hasContext()) {
			LoggerManager.debug("spring context initing ...");
			return;
		}

		ContextManager.buildSerialNumber();

		SchedulerRunStatusService schedulerRunStatusService = SpringUtil.getObjectT("schedulerEngineImpl");
		
		SchedulerContext context = new SchedulerContext();
		context.setContextValue(SchedulerContext.CONTEXT_START_DATE, new Date());
		context.setContextValue(SchedulerContext.CONTEXT_IS_RUNNING, true);
		
		String jobCode = executeContext.getJobDetail().getKey().getName();
		JobDataMap dataMap = executeContext.getJobDetail().getJobDataMap();
		dataMap.put("context", context);

		SchedulerJobConfig jobConfig = (SchedulerJobConfig) dataMap.get("jobConfig");

		long startTime = System.currentTimeMillis();
		try {
			if (jobConfig.getComponentConfig().getCompType() == SchedulerCompConfig.COMPTYPE_SPRING_BEAN) {
				executeBySpringBean(schedulerRunStatusService, context, jobConfig);
			} else if (jobConfig.getComponentConfig().getCompType() == SchedulerCompConfig.COMPTYPE_SERVICE_HOST) {
				executeByServiceHost(schedulerRunStatusService, context, jobConfig);
			} else {
				throw new RuntimeException("unknow compType:" + jobConfig.getComponentConfig().getCompType());
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			context.setContextValue(SchedulerContext.CONTEXT_IS_RUNNING, false);
			context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, false);
		} catch (IllegalAccessException e) {
			LoggerManager.error(e);
			context.setContextValue(SchedulerContext.CONTEXT_IS_RUNNING, false);
			context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, false);
		} catch (Exception e) {
			LoggerManager.error(e);
			context.setContextValue(SchedulerContext.CONTEXT_IS_RUNNING, false);
			context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, false);
		} finally {
			long endTime = System.currentTimeMillis();
			int useTime = (int) ((endTime - startTime) / 1000);
			context.setContextValue(SchedulerContext.CONTEXT_USE_TIME, useTime);
			context.setContextValue(SchedulerContext.CONTEXT_END_DATE, new Date());
			context.setContextValue(SchedulerContext.CONTEXT_IS_RUNNING, false);
			schedulerRunStatusService.setJobHandleStatus(jobCode, SchedulerJob.HANDLE_STATUS_WATTING);
		}
		ContextManager.cleanSerialNumber();
	}

	private void executeBySpringBean(SchedulerRunStatusService schedulerRunStatusService, SchedulerContext context, SchedulerJobConfig jobConfig) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String jobCode = jobConfig.getCode();
		String beanId = jobConfig.getComponentConfig().getBeanName();
		Class<? extends SchedulerComponent> clazz = (Class<? extends SchedulerComponent>) Class.forName(jobConfig.getComponentConfig().getComponentClass());

		schedulerRunStatusService.setJobHandleStatus(jobCode, SchedulerJob.HANDLE_STATUS_RUNNING);
		context.setContextValue(SchedulerContext.CONTEXT_JOB_CODE, jobCode);
		SchedulerComponent component;

		if (!StringUtil.isNullOrEmpty(beanId)) {
            component = (SchedulerComponent) SpringUtil.getObject(beanId);
        } else {
            try {
                component = (SchedulerComponent) SpringUtil.getObject(clazz);
            } finally {
            }
        }
		if (component == null) {
            component = clazz.newInstance();
        }

		LoggerManager.debug(String.format("job [%1$s] execute start", jobCode));
		component.handle(context);
		LoggerManager.debug(String.format("job [%1$s] execute end", jobCode));

		context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, true);
	}

	private void executeByServiceHost(SchedulerRunStatusService schedulerRunStatusService, SchedulerContext context, SchedulerJobConfig jobConfig) {
		String jobCode = jobConfig.getCode();
		String serviceHost = jobConfig.getComponentConfig().getServiceHost();
		String methodUrl = jobConfig.getComponentConfig().getMethodUrl();
		String methodType = jobConfig.getComponentConfig().getMethodType();

		RestTemplate restTemplate = (RestTemplate) SpringUtil.getObject("serviceRestTemplate");
		schedulerRunStatusService.setJobHandleStatus(jobCode, SchedulerJob.HANDLE_STATUS_RUNNING);
		context.setContextValue(SchedulerContext.CONTEXT_JOB_CODE, jobCode);

		LoggerManager.debug(String.format("job [%1$s] execute start", jobCode));
		String url;
		if (!StringUtil.isNullOrEmpty(methodUrl)) {
			url = serviceHost + "/" + methodUrl;
		} else {
			url = serviceHost;
		}

		if ("get".equalsIgnoreCase(methodType)) {
			StateResult result = restTemplate.getForObject(url, StateResult.class);
			if (result.isSuccess()) {
				context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, true);
				context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_MESSAGE, "执行成功");
			} else {
				context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, false);
				String message = ("httpMethod:get，状态码：" + result.getState() + "，信息：" + result.getMsg());
				context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_MESSAGE, message);
			}
		} else if ("post".equalsIgnoreCase(methodType)) {
			StateResult result = restTemplate.getForObject(url, StateResult.class);
			if (result.isSuccess()) {
				context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, true);
				context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_MESSAGE, "执行成功");
			} else {
				context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, false);
				String message = ("httpMethod:post，状态码：" + result.getState() + "，信息：" + result.getMsg());
				context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_MESSAGE, message);
			}
		} else {
			String message = "unknow method:" + methodType;
			context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_RESULT, false);
			context.setContextValue(SchedulerContext.CONTEXT_EXECUTE_MESSAGE, message);
			LoggerManager.error(message);
		}
		LoggerManager.debug(String.format("job [%1$s] execute end", jobCode));
	}
}
