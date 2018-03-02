package com.pay.beans.entity;


import com.pay.beans.rules.FormatRule;

/**
 * @author LiYabin
 */
public class ConvertTypeBean {
    public ConvertTypeBean(Class clazz, FormatRule convertRule) {
        this.convertRule = convertRule;
        this.clazz = clazz;
    }

    private FormatRule convertRule;
    private Class clazz;


    public FormatRule getConvertRule() {
        return convertRule;
    }

    public void setConvertRule(FormatRule convertRule) {
        this.convertRule = convertRule;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
