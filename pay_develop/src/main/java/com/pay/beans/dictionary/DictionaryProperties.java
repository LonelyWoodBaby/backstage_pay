package com.pay.beans.dictionary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LiYabin
 */
@Component
@ConfigurationProperties(prefix = "pay.dictionary.package")
public class DictionaryProperties {
    private String valuePackageName;
    private String dataPackageName;

    public String getValuePackageName() {
        return valuePackageName;
    }

    public void setValuePackageName(String valuePackageName) {
        this.valuePackageName = valuePackageName;
    }

    public String getDataPackageName() {
        return dataPackageName;
    }

    public void setDataPackageName(String dataPackageName) {
        this.dataPackageName = dataPackageName;
    }
}
