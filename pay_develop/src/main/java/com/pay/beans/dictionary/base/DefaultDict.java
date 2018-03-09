package com.pay.beans.dictionary.base;

/**
 * @author LiYabin
 */

public enum DefaultDict implements BaseDict{
    /**
     * 没有这个值。
     */
    noValue(""),
    /**
     * 没有这个字典项
     */
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
