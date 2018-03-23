package com.lmtech.scheduler.engine;

/**
 * job listener
 * @author huang.jb
 *
 */
public interface SchedulerJobListener {
	void actionPerform(SchedulerContext context);
}
