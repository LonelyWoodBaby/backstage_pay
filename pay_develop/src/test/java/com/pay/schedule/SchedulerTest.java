package com.pay.schedule;

import com.pay.schedule.entity.SchedulerJobEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchedulerTest {
    @Autowired
    private QuartzSchedulerUtils quartzSchedulerUtils;

    @Ignore
    public void testAddNewJob() throws Exception {
        String jobClassName = "com.pay.schedule.testJob.HelloJob";
        String jobGroupName = "testGroup";
        String conExpression = "*/3 * * * * ?";
        SchedulerJobEntity jobHello = quartzSchedulerUtils.createScheduleJob(jobClassName,jobGroupName,conExpression);
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
        SchedulerJobEntity jobNew = quartzSchedulerUtils.createScheduleJob(jobClassName2,jobGroupName2,intervaltime);
        quartzSchedulerUtils.addSimpleJob(jobNew);
        Thread.sleep(10000);
    }

    @Test
    public void testToDoJob()throws Exception{
        String jobClassName3 = "com.pay.schedule.testJob.ScheduleLogJob";
        String jobGroupName3 = "testGroup";
        String conExpression3 = "*/5 * * * * ?";
        SchedulerJobEntity jobLog = quartzSchedulerUtils.createScheduleJob(jobClassName3,jobGroupName3,conExpression3);
        quartzSchedulerUtils.addCronJob(jobLog);
        Thread.sleep(10000);
    }
}
