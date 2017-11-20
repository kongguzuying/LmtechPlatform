package com.lmtech.scheduler.engine;

import java.io.Serializable;

import com.lmtech.scheduler.config.SchedulerJobConfig;

/**
 * schedual job
 * @author huang.jb
 *
 */
public class SchedulerJob implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	public static final String STATUS_RUNNING = "running";
	public static final String STATUS_STOPPED = "stopped";
	
	public static final String HANDLE_STATUS_RUNNING = "running";
	public static final String HANDLE_STATUS_WATTING = "waitting";
	public static final String HANDLE_STATUS_STOPPED = "stopped";
	
	private SchedulerJobConfig config;
	private String status = STATUS_STOPPED;
	private String handleStatus = HANDLE_STATUS_STOPPED;
	
	// property
	public SchedulerJobConfig getConfig() {
		return config;
	}
	public void setConfig(SchedulerJobConfig config) {
		this.config = config;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
}
