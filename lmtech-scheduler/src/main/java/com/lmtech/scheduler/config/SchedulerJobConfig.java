package com.lmtech.scheduler.config;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.ConfigEntityBase;

/**
 * 调度任务配置
 * @author huang.jb
 *
 */
@TableName("lm_sche_job_config")
public class SchedulerJobConfig extends ConfigEntityBase {

	private static final long serialVersionUID = 1L;
	
	/** 主动触发任务 */
	public static final int TYPE_NORMAL = 1;
	/** 被动地由其他任务触发执行 */
	public static final int TYPE_TRIGGER = 2;
	
	/** 任务仅执行一次 */
	public static final int EXECUTE_MODEL_ONETIME = 1;
	/** 任务始终执行 */
	public static final int EXECUTE_MODEL_FOREVER = 2;
	 
	@TableField("type")
	private int type;						//类型，1-主动执行，2-被动触发执行
	@TableField("comp_config_code")
	private String compConfigCode;			//调度组件配置代码
	@TableField("execute_model")
	private int executeMode;				//执行模式，1-执行一次，2-始终执行
	@TableField("scheduler_expression")
	private String schedulerExpression;		//触发表达式
	@TableField("group")
	private String group = "default";		//调度分组
	@TableField("description")
	private String description;				//描述
	@TableField("dependence_job")
	private String dependenceJob;			//依赖的调度
	@TableField("enable")
	private boolean enable;					//是否启用

	@TableField(exist = false)
	private SchedulerCompConfig componentConfig;	//调度组件
	
	/**
	 * 是否主动调用
	 * @return
	 */
	public boolean isNormal() {
		return type == TYPE_NORMAL;
	}
	/**
	 * 是否触发调用
	 * @return
	 */
	public boolean isTrigger() {
		return type == TYPE_TRIGGER;
	}
	/**
	 * 是否仅执行一次
	 * @return
	 */
	public boolean isRunOneTime() {
		return executeMode == EXECUTE_MODEL_ONETIME;
	}
	/**
	 * 是否永远执行
	 * @return
	 */
	public boolean isRunForever() {
		return executeMode == EXECUTE_MODEL_FOREVER;
	}
	
	// property
	public String getSchedulerExpression() {
		return schedulerExpression;
	}
	public void setSchedulerExpression(String schedulerExpression) {
		this.schedulerExpression = schedulerExpression;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDependenceJob() {
		return dependenceJob;
	}
	public void setDependenceJob(String dependenceJob) {
		this.dependenceJob = dependenceJob;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCompConfigCode() {
		return compConfigCode;
	}
	public void setCompConfigCode(String compConfigCode) {
		this.compConfigCode = compConfigCode;
	}
	public int getExecuteMode() {
		return executeMode;
	}
	public void setExecuteMode(int executeMode) {
		this.executeMode = executeMode;
	}
	public SchedulerCompConfig getComponentConfig() {
		return componentConfig;
	}
	public void setComponentConfig(SchedulerCompConfig componentConfig) {
		this.componentConfig = componentConfig;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
