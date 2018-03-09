package com.pay.beans.dictionary.data;

import com.pay.beans.dictionary.base.extend.DataDict;

/**
 * @author LiYabin
 */

public enum ScheduleWorkTypeDict implements DataDict {

    /**
     * 定时模式
     */
    CRON_SCHEDULE("1"),
    /**
     * 时间间隔模式
     */
    SIMPLE_SCHEDULE("2")
    ;

    private String value;
    ScheduleWorkTypeDict(String value){
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
