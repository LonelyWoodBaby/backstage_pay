package com.pay.schedule.entity;

public enum  ScheduleJobType {
    CRON_SCHEDULE("1"),
    SIMPLE_SCHEDULE("2");

    private String TYPE_VALUE;

    ScheduleJobType(String value){
        this.TYPE_VALUE = value;
    }

    String value(){
        return this.TYPE_VALUE;
    }
}
