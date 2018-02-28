package com.pay.schedule.service;

import com.pay.schedule.entity.ScheduleExecuteLog;

public interface ScheduleExecuteLogService {
    void insertNewExecuteLog(ScheduleExecuteLog executeLog);
    void saveExecuteLogResult(ScheduleExecuteLog executeLog);
}
