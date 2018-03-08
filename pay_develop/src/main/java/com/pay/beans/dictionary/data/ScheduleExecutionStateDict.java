package com.pay.beans.dictionary.data;

import com.pay.beans.dictionary.base.extend.DataDict;

/**
 * @author LiYabin
 * 执行状态
 */
public enum ScheduleExecutionStateDict implements DataDict{
    PREPARE("0"),
    RUNNING("1"),
    SUCCESS("2"),
    FAILURE("9")
    ;

    private String value;
    ScheduleExecutionStateDict(String value){
        this.value = value;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public String key() {
        return this.name();
    }
}
