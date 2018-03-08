package com.pay.schedule.pojo.model;

import com.pay.schedule.pojo.model.dict.ScheduleWorkType;

public class ScheduleWorkJob {
    private String jobId;
    private String jobClassName;
    private String jobGroupName;
    private String cronExpression;
    private ScheduleWorkType scheduleWorkType;
    private int intervalTime;
    private String description;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public ScheduleWorkType getScheduleWorkType() {
        return scheduleWorkType;
    }

    public void setScheduleWorkType(ScheduleWorkType scheduleWorkType) {
        this.scheduleWorkType = scheduleWorkType;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
