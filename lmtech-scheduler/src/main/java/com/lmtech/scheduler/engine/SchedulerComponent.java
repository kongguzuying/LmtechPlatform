package com.lmtech.scheduler.engine;

/**
 * schedual component, all job component should implements it
 * @author huang.jb
 *
 */
public interface SchedulerComponent {
	/**
	 * schedual handle
	 * @param context
	 */
	void handle(SchedulerContext context);
}
