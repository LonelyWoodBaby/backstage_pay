package com.pay.schedule.testJob;

import com.pay.schedule.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class HelloJob implements BaseJob {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info(jobExecutionContext.getTrigger().getKey().toString());
        logger.info(jobExecutionContext.getJobDetail().getKey().getName());
        logger.info(jobExecutionContext.getTrigger().getStartTime().toString());
        logger.info("hello 执行时间：" + new Date());
    }
}
