package com.pay.schedule.service.impl;

import com.pay.schedule.entity.ScheduleExecuteLog;
import com.pay.schedule.service.ScheduleExecuteLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 定时任务执行记录
 * @author Lee Yarbin
 */
@Service
public class ScheduleExecuteLogServiceImpl implements ScheduleExecuteLogService{
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void insertNewExecuteLog(ScheduleExecuteLog executeLog) {
        logger.info("----------------------------\n" +
                "执行任务开始：----------------\n" +
                "执行类名：" + executeLog.getJobClassName() +
                "\n执行时间：" + executeLog.getOccurTime() +
                "\n执行ID："+ executeLog.getExecuteLogId() +
        "----------------------------------------\n");
    }

    @Override
    public void saveExecuteLogResult(ScheduleExecuteLog executeLog) {
        logger.info("----------------------------" +
                "\n执行任务结束：----------------" +
                "\n执行ID："+ executeLog.getExecuteLogId() +
                "\n执行状态："+ executeLog.getExeStatus() +
                "\n运行时间："+ executeLog.getExecuteTime()+
                "\n----------------------------------------\n");
    }
}
