package com.lmtech.scheduler.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * schedual context
 * @author huang.jb
 *
 */
public class SchedulerContext {
	
	public static final String CONTEXT_JOB_CODE = "jobCode";
	public static final String CONTEXT_JOB_NAME = "jobName";
	public static final String CONTEXT_USE_TIME = "useTime";
	public static final String CONTEXT_START_DATE = "startDate";
	public static final String CONTEXT_END_DATE = "endDate";
	public static final String CONTEXT_EXECUTE_RESULT = "executeResult";
	public static final String CONTEXT_EXECUTE_MESSAGE = "executeMessage";
	public static final String CONTEXT_IS_RUNNING = "isRunnnig";
	
	private Map<String, Object> context = new HashMap<String, Object>();
	
	public void setContextValue(String key, Object value) {
		if (key != null && !key.equals("") && value != null) {
			context.put(key, value);
		}
	}
	
	public Object getContextValue(String key) {
		if (context.containsKey(key)) {
			return context.get(key);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContextT(String key) {
		Object value = this.getContextValue(key);
		if (value != null) {
			return (T) value;
		} else {
			return null;
		}
	}
}
