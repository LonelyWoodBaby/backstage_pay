package com.pay.schedule;

import com.pay.schedule.job.BaseJob;
import com.pay.schedule.pojo.model.ScheduleWorkJob;
import com.pay.schedule.pojo.model.dict.ScheduleWorkType;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Lee Yarbin
 * 定时任务工具类，可对定时任务进行添加，暂停，恢复使用，以及删除，重新部署等功能。
 */
@Component
public class QuartzSchedulerUtils {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Scheduler scheduler;

    public void addCronJob(ScheduleWorkJob entity) throws Exception {
        // 启动调度器
        if (!scheduler.isStarted()){
            scheduler.start();
        }

        if(scheduler.getTrigger(TriggerKey.triggerKey(entity.getJobClassName(), entity.getJobGroupName())) != null){
            rescheduleCronJob(entity);
            return ;
        }
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(entity.getJobClassName()).getClass())
                .withIdentity(entity.getJobClassName(), entity.getJobGroupName()).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCronExpression());

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(entity.getJobClassName(), entity.getJobGroupName())
                .withSchedule(scheduleBuilder).build();

        execute(entity.getJobClassName(),jobDetail,trigger);
    }

    public void addSimpleJob(ScheduleWorkJob entity) throws Exception {
        if (!scheduler.isStarted()){
            scheduler.start();
        }

        if(scheduler.getTrigger(TriggerKey.triggerKey(entity.getJobClassName(), entity.getJobGroupName())) != null){
            rescheduleSimpleJob(entity);
            return ;
        }
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(entity.getJobClassName()).getClass())
                .withIdentity(entity.getJobClassName(), entity.getJobGroupName()).build();
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(entity.getIntervalTime());
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(entity.getJobClassName(), entity.getJobGroupName()).withSchedule(scheduleBuilder).build();
        execute(entity.getJobClassName(),jobDetail,trigger);
    }

    private void execute(String jobClassName, JobDetail jobDetail, Trigger trigger) throws Exception {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("添加定时任务：【" + jobClassName +"】");
        } catch (SchedulerException e) {
            e.printStackTrace();
            System.out.println("创建定时任务失败"+e);
            throw new Exception("创建定时任务失败");
        }
    }

    public void pauseJob(String jobClassName, String jobGroupName) throws Exception{
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    public void resumeJob(String jobClassName, String jobGroupName) throws Exception{
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    public void rescheduleCronJob(ScheduleWorkJob entity) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(entity.getJobClassName(), entity.getJobGroupName());
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCronExpression());

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败"+e);
            throw new Exception("更新定时任务失败");
        }
    }

    public void rescheduleSimpleJob(ScheduleWorkJob entity) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(entity.getJobClassName(), entity.getJobGroupName());
            // 表达式调度构建器
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(entity.getIntervalTime());

            SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败"+e);
            throw new Exception("更新定时任务失败");
        }
    }

    public void deleteJob(String jobClassName, String jobGroupName) throws Exception{
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    private BaseJob getClass(String classname) throws Exception{
        Class<?> class1 = Class.forName(classname);
        return (BaseJob)class1.newInstance();
    }

    public ScheduleWorkJob createScheduleJob(String jobClassName, String jobGroupName, String cronExpression){
        return this.createScheduleJob(jobClassName,jobGroupName,cronExpression,"");
    }

    public ScheduleWorkJob createScheduleJob(String jobClassName, String jobGroupName, String cronExpression, String description){
        ScheduleWorkJob entity = new ScheduleWorkJob();
        entity.setCronExpression(cronExpression);
        entity.setJobClassName(jobClassName);
        entity.setJobGroupName(jobGroupName);
        entity.setDescription(description);
        entity.setScheduleWorkType(ScheduleWorkType.CRON_SCHEDULE);
        return createScheduleJob(entity);
    }

    public ScheduleWorkJob createScheduleJob(String jobClassName, String jobGroupName, int intervalSeconds){
        return this.createScheduleJob(jobClassName,jobGroupName,intervalSeconds,"");
    }

    public ScheduleWorkJob createScheduleJob(String jobClassName, String jobGroupName, int intervalSeconds,String description){
        ScheduleWorkJob entity = new ScheduleWorkJob();
        entity.setIntervalTime(intervalSeconds);
        entity.setJobClassName(jobClassName);
        entity.setJobGroupName(jobGroupName);
        entity.setDescription(description);
        entity.setScheduleWorkType(ScheduleWorkType.SIMPLE_SCHEDULE);
        return createScheduleJob(entity);
    }

    private ScheduleWorkJob createScheduleJob(ScheduleWorkJob entity){
        return entity;
    }
}
