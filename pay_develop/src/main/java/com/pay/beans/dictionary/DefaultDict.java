package com.pay.beans.dictionary;

import com.pay.beans.dictionary.BaseDict;

public enum DefaultDict implements BaseDict{
    noValue(""),
    noDictionary("");
    private String value;
    DefaultDict(String value){
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
