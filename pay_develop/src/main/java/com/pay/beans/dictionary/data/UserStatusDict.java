package com.pay.beans.dictionary.data;

import com.pay.beans.dictionary.base.BaseDict;

/**
 * @author LiYabin
 */

public enum UserStatusDict implements BaseDict {
    /**
     * 开启
     */
    on("1"),
    /**
     * 关闭
     */
    off("200")
    ;

    private String value;
    UserStatusDict(String value){
        this.value = value;
    }
    @Override
    public String value() {
        return this.value;
    }

    @Override
    public String key(){
        return this.name();
    }
}
