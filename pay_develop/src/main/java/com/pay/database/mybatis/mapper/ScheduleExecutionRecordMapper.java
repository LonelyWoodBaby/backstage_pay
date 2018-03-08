package com.pay.database.mybatis.mapper;

import com.pay.database.mybatis.config.BaseMapper;
import com.pay.schedule.pojo.dtm.ScheduleExecutionRecordDtm;
import com.pay.schedule.pojo.model.ScheduleExecutionRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleExecutionRecordMapper extends BaseMapper<ScheduleExecutionRecordDtm> {
}
