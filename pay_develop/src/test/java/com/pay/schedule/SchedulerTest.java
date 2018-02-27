package com.pay.schedule;

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

    @Test
    public void testAddNewJob() throws Exception {
        String jobClassName = "com.pay.schedule.testJob.HelloJob";
        String jobGroupName = "testGroup";
        String conExpression = "*/3 * * * * ?";
        quartzSchedulerUtils.addCronJob(jobClassName,jobGroupName,conExpression);
        Thread.sleep(10000);

        String jobClassName2 = "com.pay.schedule.testJob.NewJob";
        String jobGroupName2 = "testGroup";
        int intervaltime = 3;
        quartzSchedulerUtils.addSimpleJob(jobClassName2,jobGroupName2,intervaltime);
        Thread.sleep(10000);

        quartzSchedulerUtils.pauseJob(jobClassName,jobGroupName);
        Thread.sleep(10000);

        quartzSchedulerUtils.resumeJob(jobClassName,jobGroupName);
        Thread.sleep(10000);

        quartzSchedulerUtils.deleteJob(jobClassName,jobGroupName);
        Thread.sleep(20000);
    }
}
