package com.pay.schedule.job;

import com.pay.schedule.pojo.model.ScheduleExecutionRecord;
import com.pay.schedule.pojo.model.dict.ScheduleExecutionState;
import com.pay.schedule.service.ScheduleExecutionRecordService;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * @author LiYabin
 * 定时任务通用接口，封装了定时任务执行前后需要执行的任务执行记录持久化
 */
public abstract class AbstractWorkJob implements BaseJob{
    protected ScheduleExecutionRecordService scheduleExecutionRecordService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        ScheduleExecutionRecord record = ScheduleExecutionRecord.createNewRecord(jobExecutionContext.getJobDetail());
        scheduleExecutionRecordService.insertNewExecuteLog(record);
        try {
            this.executeJob(jobExecutionContext);
            record.setExecutionState(ScheduleExecutionState.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            record.setExecutionState(ScheduleExecutionState.FAILURE);
            record.setExceptionMsg(e.getMessage());
        } finally {
            Date endTime = new Date();
            long executeTime = endTime.getTime() - record.getOccurTime().getTime();
            record.setEndTime(endTime);
            record.setExecutionTime(executeTime);
        }
        scheduleExecutionRecordService.saveExecuteLogResult(record);
    }

    /**
     * 具体的执行业务在这里实现
     * @param jobExecutionContext
     */
    public abstract void executeJob(JobExecutionContext jobExecutionContext);


}
