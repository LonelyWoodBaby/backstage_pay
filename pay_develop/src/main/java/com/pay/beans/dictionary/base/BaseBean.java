package com.pay.beans.dictionary.base;

import com.pay.beans.dictionary.DictionaryConfig;
import com.pay.beans.entity.ConvertNameBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseBean {
    private Map<String,Class> mappingDictionaryConfig;
    public List<ConvertNameBean> getEnumToValueDictionaryRegulations(){
        return getEnumToValueDictionaryRegulations(getMappingDictionaryConfig());
    }

    public List<ConvertNameBean> getEnumToValueDictionaryRegulations(Map<String,Class> otherMappingConfig){
        List<ConvertNameBean> regulationsList = new ArrayList<>();
        for(String key:otherMappingConfig.keySet()){
            regulationsList.add(new ConvertNameBean(key,
                    (e)-> DictionaryConfig.convertValueFromDictionary(getMappingDictionaryConfig().get(key).getName(), (Enum) e)));
        }
        return regulationsList;
    }

    public List<ConvertNameBean> getValueToEnumDictionaryRegulations(){
        return getValueToEnumDictionaryRegulations(getMappingDictionaryConfig());
    }

    public List<ConvertNameBean> getValueToEnumDictionaryRegulations(Map<String,Class> otherMappingConfig){
        List<ConvertNameBean> regulationsList = new ArrayList<>();
        for(String key:otherMappingConfig.keySet()){
            regulationsList.add(new ConvertNameBean(key,
                    (value)-> DictionaryConfig.transEnumFromDictionary(getMappingDictionaryConfig().get(key).getName(), (String) value)));
        }
        return regulationsList;
    }


    public void setMappingDictionaryConfig(Map<String, Class> mappingDictionaryConfig) {
        this.mappingDictionaryConfig = mappingDictionaryConfig;
    }

    public Map<String, Class> getMappingDictionaryConfig() {
        if(mappingDictionaryConfig == null){
            return new HashMap<>();
        }
        return mappingDictionaryConfig;
    }
}
