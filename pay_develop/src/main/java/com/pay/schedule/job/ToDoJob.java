package com.pay.schedule.job;

import com.pay.schedule.QuartzSchedulerUtils;
import com.pay.schedule.entity.ScheduleExecuteLog;
import com.pay.schedule.entity.ScheduleExecuteStatus;
import com.pay.schedule.service.ScheduleExecuteLogService;
import org.quartz.JobExecutionContext;

import java.util.Date;

public abstract class ToDoJob implements BaseJob{
    protected ScheduleExecuteLogService scheduleExecuteLogService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        ScheduleExecuteLog executeLog = new ScheduleExecuteLog();
        Date occurTime = new Date();
        executeLog.setExecuteLogId(String.valueOf(occurTime.getTime()));
        executeLog.setExeStatus(ScheduleExecuteStatus.RUNNING.value());
        executeLog.setOccurTime(QuartzSchedulerUtils.transTime2String(occurTime));
        executeLog.setJobClassName(jobExecutionContext.getJobDetail().getKey().getName());
        scheduleExecuteLogService.insertNewExecuteLog(executeLog);

        try {
            this.executeJob(jobExecutionContext);
            executeLog.setExeStatus(ScheduleExecuteStatus.SUCCESS.value());
        } catch (Exception e) {
            e.printStackTrace();
            executeLog.setExceptionMsg(e.getMessage());
            executeLog.setExeStatus(ScheduleExecuteStatus.FAILURE.value());
        } finally {
            Date endTime = new Date();
            long executeTime = endTime.getTime() - occurTime.getTime();
            executeLog.setEndTime(QuartzSchedulerUtils.transTime2String(endTime));
            executeLog.setExecuteTime(executeTime);
        }

        scheduleExecuteLogService.saveExecuteLogResult(executeLog);
    }

    /**
     * 具体的执行业务在这里实现
     * @param jobExecutionContext
     */
    public abstract void executeJob(JobExecutionContext jobExecutionContext);


}
