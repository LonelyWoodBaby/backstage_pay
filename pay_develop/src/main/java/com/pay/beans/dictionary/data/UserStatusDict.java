package com.pay.beans.dictionary.data;

import com.pay.beans.dictionary.base.BaseDict;

public enum UserStatusDict implements BaseDict {
    on("1"),
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
