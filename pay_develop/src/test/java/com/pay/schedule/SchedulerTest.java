package com.pay.schedule;

import com.pay.beans.BeanUtils;
import com.pay.beans.entity.ConvertTypeBean;
import com.pay.beans.rules.FormatRule;
import com.pay.schedule.pojo.dtm.ScheduleExecutionRecordDtm;
import com.pay.schedule.pojo.model.ScheduleExecutionRecord;
import com.pay.schedule.pojo.model.ScheduleWorkJob;
import com.pay.schedule.pojo.model.dict.ScheduleExecutionState;
import com.pay.schedule.service.ScheduleExecutionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SchedulerTest {
    @Autowired
    private QuartzSchedulerUtils quartzSchedulerUtils;

    @Autowired
    private ScheduleExecutionRecordService scheduleExecutionRecordService;

    @Ignore
    public void testAddNewJob() throws Exception {
        String jobClassName = "com.pay.schedule.testJob.HelloJob";
        String jobGroupName = "testGroup";
        String conExpression = "*/3 * * * * ?";
        ScheduleWorkJob jobHello = quartzSchedulerUtils.createScheduleJob(jobClassName,jobGroupName,conExpression);
        quartzSchedulerUtils.addCronJob(jobHello);
        Thread.sleep(10000);

        quartzSchedulerUtils.pauseJob(jobClassName,jobGroupName);
        Thread.sleep(10000);

        quartzSchedulerUtils.resumeJob(jobClassName,jobGroupName);
        Thread.sleep(10000);

        quartzSchedulerUtils.deleteJob(jobClassName,jobGroupName);
        Thread.sleep(20000);
    }

    @Ignore
    public void addTestNewJob() throws Exception {
        String jobClassName2 = "com.pay.schedule.testJob.NewJob";
        String jobGroupName2 = "testGroup";
        int intervaltime = 3;
        ScheduleWorkJob jobNew = quartzSchedulerUtils.createScheduleJob(jobClassName2,jobGroupName2,intervaltime);
        quartzSchedulerUtils.addSimpleJob(jobNew);
        Thread.sleep(10000);
    }

    @Ignore
    public void testToDoJob()throws Exception{
        String jobClassName3 = "com.pay.schedule.testJob.PrepareWork";
        String jobGroupName3 = "testGroup";
        String conExpression3 = "*/5 * * * * ?";
        ScheduleWorkJob workJob = quartzSchedulerUtils.createScheduleJob(jobClassName3,jobGroupName3,conExpression3);
        quartzSchedulerUtils.addCronJob(workJob);
        Thread.sleep(10000);
    }

    @Test
    public void insertNewExecutionLog(){
        ScheduleExecutionRecord record = new ScheduleExecutionRecord();
        record.setRecordId(String.valueOf(new Date().getTime()));
        record.setExecutionTime(1000L);
        record.setEndTime(new Date());
        record.setExceptionMsg("没错误");
        record.setExecutionState(ScheduleExecutionState.RUNNING);
        record.setJobGroupName("12345");
        record.setJobClassName("56789");
        record.setOccurTime(new Date());

//        ScheduleExecutionRecordDtm recordDtm = new ScheduleExecutionRecordDtm();
//        List<ConvertTypeBean> convertList = new ArrayList<>();
//        convertList.add(new ConvertTypeBean(Date.class, FormatRule.formatDateRule("yyyy_MM_dd:HH_mm_ss:SSS")));
//        BeanUtils.copyBeanWithRuleByType(record,recordDtm,convertList);

        scheduleExecutionRecordService.insertNewExecuteLog(record);

        record.setJobGroupName("888888");
        record.setJobClassName("999999");

        scheduleExecutionRecordService.saveExecuteLogResult(record);


    }
}
