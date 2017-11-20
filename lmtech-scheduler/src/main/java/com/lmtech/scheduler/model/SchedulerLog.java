package com.lmtech.scheduler.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

/**
 * scheduler log
 *
 * @author huang.jb
 */
@TableName("lm_sche_log")
public class SchedulerLog extends DbEntityBase {
    @TableField("job_code")
    private String jobCode;
    @TableField("job_name")
    private String jobName;
    @TableField("start_date")
    private Date startDate;
    @TableField("end_date")
    private Date endDate;
    @TableField("execute_time")
    private int executeTime;
    @TableField("execute_result")
    private boolean executeResult;
    @TableField("message")
    private String message;

    // property
    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(int executeTime) {
        this.executeTime = executeTime;
    }

    public boolean isExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
