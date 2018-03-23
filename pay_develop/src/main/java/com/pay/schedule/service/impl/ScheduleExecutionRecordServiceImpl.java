package com.pay.schedule.service.impl;

import com.pay.beans.BeanUtils;
import com.pay.database.dao.entity.PageInfo;
import com.pay.database.dao.impl.BasicDaoImpl;
import com.pay.database.mybatis.mapper.ScheduleExecutionRecordMapper;
import com.pay.schedule.pojo.dtm.ScheduleExecutionRecordDtm;
import com.pay.schedule.pojo.model.ScheduleExecutionRecord;
import com.pay.schedule.service.ScheduleExecutionRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 定时任务执行记录
 * @author Lee Yarbin
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class ScheduleExecutionRecordServiceImpl extends BasicDaoImpl<ScheduleExecutionRecordDtm> implements ScheduleExecutionRecordService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private ScheduleExecutionRecordMapper scheduleExecutionRecordMapper;

    @Autowired
    public void setBaseMapper() {
        super.setBaseMapper(scheduleExecutionRecordMapper);
    }

    @CacheEvict(value = {"getAllExecutionList"},allEntries = true)
    @Override
    public void insertNewExecuteLog(ScheduleExecutionRecord record) {
        logger.info("----------------------------\n" +
                "执行任务开始：----------------\n" +
                "执行类名：" + record.getJobClassName() +
                "\n执行时间：" + record.getOccurTime() +
                "\n执行ID："+ record.getRecordId() +
                "----------------------------------------\n");
        ScheduleExecutionRecordDtm recordDtm = new ScheduleExecutionRecordDtm();
        BeanUtils.copyBeanExtend(record,recordDtm);
        this.insertNewEntity(recordDtm);
    }

    @CacheEvict(value = {"getAllExecutionList"},allEntries = true)
    @Override
    public void saveExecuteLogResult(ScheduleExecutionRecord record) {
        logger.info("----------------------------" +
                "\n执行任务结束：----------------" +
                "\n执行ID："+ record.getRecordId() +
                "\n执行状态："+ record.getExecutionState() +
                "\n运行时间："+ record.getExecutionTime() +
                "\n----------------------------------------\n");
        ScheduleExecutionRecordDtm recordDtm = new ScheduleExecutionRecordDtm();
        BeanUtils.copyBeanExtend(record,recordDtm);
        this.update(recordDtm);
    }

    @Cacheable("getAllExecutionList")
    @Override
    public List<ScheduleExecutionRecord> getAllExecutionList(){
        logger.info("进入数据库方法进行查询");
        List<ScheduleExecutionRecordDtm> recordDtmList =  scheduleExecutionRecordMapper.selectAll();
        List<ScheduleExecutionRecord> recordList = recordDtmList.stream().map(dtm->{
            ScheduleExecutionRecord record = new ScheduleExecutionRecord();
            BeanUtils.copyBeanExtend(dtm,record);
            return record;
        }).collect(toList());
        System.out.println("查询结果长度是：" + recordList.size());
        return recordList;
    }

    @Override
    public List<ScheduleExecutionRecord> getExecutionListByPage(int pageNum, int pageSize){
        PageInfo<ScheduleExecutionRecordDtm> pageInfo = this.getPageInfo(new ScheduleExecutionRecordDtm(),pageNum,pageSize);
        List<ScheduleExecutionRecordDtm> recordDtmList = pageInfo.getResultList();
        List<ScheduleExecutionRecord> recordList = BeanUtils.copyBeanExtendForList(recordDtmList,ScheduleExecutionRecord.class);
        return recordList;
    }

}
