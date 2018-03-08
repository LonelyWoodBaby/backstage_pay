package com.pay.schedule.testJob;

import com.pay.schedule.job.AbstractWorkJob;
import com.pay.schedule.service.ScheduleExecutionRecordService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class PrepareWork extends AbstractWorkJob{
    @Override
    public void executeJob(JobExecutionContext jobExecutionContext) {
        System.err.println("开始测试我的新job");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("job执行结束");
    }

    @Autowired
    public void setScheduleExecuteLogService(ScheduleExecutionRecordService scheduleExecutionRecordService) {
        super.scheduleExecutionRecordService = scheduleExecutionRecordService;
    }
}
