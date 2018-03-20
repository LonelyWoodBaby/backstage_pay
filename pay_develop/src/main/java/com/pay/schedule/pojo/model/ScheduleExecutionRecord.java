package com.pay.schedule.pojo.model;

import com.pay.schedule.pojo.model.dict.ScheduleExecutionState;
import org.quartz.JobDetail;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LiYabin
 * 定时任务执行记录
 */
public class ScheduleExecutionRecord implements Serializable{
    private String recordId;
    private String jobClassName;
    private String jobGroupName;
    private long executionTime;
    private Date occurTime;
    private Date endTime;
    private String exceptionMsg;
    private ScheduleExecutionState executionState;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public Date getOccurTime() {
        return (Date)occurTime.clone();
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = (Date)occurTime.clone();
    }

    public Date getEndTime() {
        return (Date)endTime.clone();
    }

    public void setEndTime(Date endTime) {
        this.endTime = (Date)endTime.clone();
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public ScheduleExecutionState getExecutionState() {
        return executionState;
    }

    public void setExecutionState(ScheduleExecutionState executionState) {
        this.executionState = executionState;
    }

    public static ScheduleExecutionRecord createNewRecord(JobDetail jobDetail){
        ScheduleExecutionRecord record = new ScheduleExecutionRecord();
        Date occurTime = new Date();
        record.setRecordId(String.valueOf(occurTime.getTime()));
        record.setExecutionState(ScheduleExecutionState.RUNNING);
        record.setOccurTime(occurTime);

        record.setJobClassName(jobDetail.getKey().getName());
        record.setJobGroupName(jobDetail.getKey().getGroup());
        return record;
//        ScheduleExecuteLog executeLog = new ScheduleExecuteLog();
//        Date occurTime = new Date();
//        executeLog.setExecuteLogId(String.valueOf(occurTime.getTime()));
//        executeLog.setExeStatus(ScheduleExecuteStatus.RUNNING.value());
//        executeLog.setOccurTime(QuartzSchedulerUtils.transTime2String(occurTime));
//        executeLog.setJobClassName(jobExecutionContext.getJobDetail().getKey().getName());

    }
}
