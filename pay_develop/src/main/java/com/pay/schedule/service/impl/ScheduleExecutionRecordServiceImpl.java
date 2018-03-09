package com.pay.schedule.service.impl;

import com.pay.beans.BeanUtils;
import com.pay.database.dao.impl.BasicDaoImpl;
import com.pay.database.mybatis.mapper.ScheduleExecutionRecordMapper;
import com.pay.schedule.pojo.dtm.ScheduleExecutionRecordDtm;
import com.pay.schedule.pojo.model.ScheduleExecutionRecord;
import com.pay.schedule.service.ScheduleExecutionRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 定时任务执行记录
 * @author Lee Yarbin
 */
@Service
public class ScheduleExecutionRecordServiceImpl extends BasicDaoImpl<ScheduleExecutionRecordDtm> implements ScheduleExecutionRecordService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private ScheduleExecutionRecordMapper scheduleExecutionRecordMapper;

    @Autowired
    public void setBaseMapper() {
        super.setBaseMapper(scheduleExecutionRecordMapper);
    }

    @Override
    public void insertNewExecuteLog(ScheduleExecutionRecord record) {
        logger.info("----------------------------\n" +
                "执行任务开始：----------------\n" +
                "执行类名：" + record.getJobClassName() +
                "\n执行时间：" + record.getOccurTime() +
                "\n执行ID："+ record.getRecordId() +
                "----------------------------------------\n");
        ScheduleExecutionRecordDtm recordDtm = new ScheduleExecutionRecordDtm();
        BeanUtils.copyBeanBaseExtend(record,recordDtm);
        this.insertNewEntity(recordDtm);
    }

    @Override
    public void saveExecuteLogResult(ScheduleExecutionRecord record) {
        logger.info("----------------------------" +
                "\n执行任务结束：----------------" +
                "\n执行ID："+ record.getRecordId() +
                "\n执行状态："+ record.getExecutionState() +
                "\n运行时间："+ record.getExecutionTime() +
                "\n----------------------------------------\n");
        ScheduleExecutionRecordDtm recordDtm = new ScheduleExecutionRecordDtm();
        BeanUtils.copyBeanBaseExtend(record,recordDtm);
        this.update(recordDtm);
    }
}
