package com.pay.schedule.entity;

public class SchedulerJobEntity {
    /**
     *  ID值
     */
    private String scheduleId;
    /**
     * 执行任务的类名。需要使用全路径
     */
    private String jobClassName;

    //分组名称
    private String jobGroupName;

    //分组
    private String cronExpression;

    /**
     * 定时任务类型，1为指定时间定时执行，2为按间隔执行
     */
    private ScheduleJobType jobType;

    /**
     * 间隔时间，单位为秒
     */
    private int intervalTime;

    /**
     * 任务描述
     */
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
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

    public ScheduleJobType getJobType() {
        return jobType;
    }

    public void setJobType(ScheduleJobType jobType) {
        this.jobType = jobType;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }
}
