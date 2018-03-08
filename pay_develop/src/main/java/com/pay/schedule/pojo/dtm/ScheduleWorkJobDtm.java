package com.pay.schedule.pojo.dtm;

import com.pay.beans.dictionary.base.BaseBean;
import com.pay.beans.dictionary.data.ScheduleWorkTypeDict;

import java.util.HashMap;
import java.util.Map;

public class ScheduleWorkJobDtm extends BaseBean{
    private String jobId;
    private String jobClassName;
    private String jobGroupName;
    private String cronExpression;
    private String scheduleWorkType;
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

    public String getScheduleWorkType() {
        return scheduleWorkType;
    }

    public void setScheduleWorkType(String scheduleWorkType) {
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

    @Override
    public Map<String, Class> getMappingDictionaryConfig() {
        Map<String, Class> map = new HashMap<>();
        map.put("scheduleWorkType", ScheduleWorkTypeDict.class);
        return map;
    }
}
