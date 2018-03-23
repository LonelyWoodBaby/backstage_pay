package com.pay.schedule.service;

import com.pay.database.dao.BasicDao;
import com.pay.schedule.pojo.dtm.ScheduleExecutionRecordDtm;
import com.pay.schedule.pojo.model.ScheduleExecutionRecord;

import java.util.List;

public interface ScheduleExecutionRecordService extends BasicDao<ScheduleExecutionRecordDtm>{
    /**
     * 插入新的执行记录日志
     * @param record
     */
    void insertNewExecuteLog(ScheduleExecutionRecord record);

    /**
     * 根据record实例来进行修改和更新记录
     * @param record
     */
    void saveExecuteLogResult(ScheduleExecutionRecord record);

    /**
     * 返回全部的执行记录日志
     * @return
     */
    List<ScheduleExecutionRecord> getAllExecutionList();
}
