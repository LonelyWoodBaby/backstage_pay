package com.pay.schedule.entity;

public enum ScheduleExecuteStatus {
    PREPARE("0"),
    RUNNING("1"),
    SUCCESS("2"),
    FAILURE("9");

    String statusValue;

    ScheduleExecuteStatus(String value){
        this.statusValue = value;
    }

    public String value(){
        return this.statusValue;
    }
}
