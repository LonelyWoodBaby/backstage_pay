package com.pay.beans.rules.entity;


import com.pay.beans.rules.FormatRule;

/**
 * @author LiYabin
 */
public class ConvertNameBean {
    private String fieldName;
    private FormatRule convertRule;

    public ConvertNameBean(String fieldName, FormatRule convertRule) {
        this.fieldName = fieldName;
        this.convertRule = convertRule;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FormatRule getConvertRule() {
        return convertRule;
    }

    public void setConvertRule(FormatRule convertRule) {
        this.convertRule = convertRule;
    }
}
