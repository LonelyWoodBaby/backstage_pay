package com.pay.schedule.service;

/**
 * 定时任务业务接口
 *
 * TODOList：
 * 1.查询全部的待执行任务列表
 * 2.当任务开始执行时，插入到数据库中
 * 3.任务发生改变时，同步到数据库中
 * 4.修改BaseJob，以实现执行任务时，先执行定时任务执行记录插入操作，在任务结束后执行定时任务结束操作，并记录执行状态
 */
public interface ScheduleJobService {
}
