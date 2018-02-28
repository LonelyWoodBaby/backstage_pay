package com.pay.schedule.entity;

public enum  ScheduleJobType {
    /**
     * 按照指定时间执行任务的模式
     */
    CRON_SCHEDULE("1"),
    /**
     * 按照时间间隔执行任务的模式
     */
    SIMPLE_SCHEDULE("2");

    private String typeValue;

    ScheduleJobType(String value){
        this.typeValue = value;
    }

    String value(){
        return this.typeValue;
    }
}
