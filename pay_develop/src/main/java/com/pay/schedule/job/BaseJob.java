package com.pay.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface BaseJob extends Job {
    /**
     * 具体的工作内容实现接口
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException ;
}
