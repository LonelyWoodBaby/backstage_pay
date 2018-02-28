package com.pay.schedule.job;

import com.pay.schedule.entity.ScheduleExecuteLog;
import com.pay.schedule.service.ScheduleExecuteLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public interface BaseJob extends Job {
    @Override
    void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException ;
}
