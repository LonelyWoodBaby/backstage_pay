package com.pay.beans.dictionary;

import com.pay.beans.dictionary.view.UserStatusDict;
import com.pay.beans.entity.ConvertNameBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseBean {
    private Map<String,Class> mappingDictionaryConfig;
    public List<ConvertNameBean> getEnumToValueDictionaryRegulations(){
        List<ConvertNameBean> regulationsList = new ArrayList<>();
        for(String key:getMappingDictionaryConfig().keySet()){
            regulationsList.add(new ConvertNameBean(key,
                    (e)-> DictionaryConfig.convertValueFromValueDictionary(getMappingDictionaryConfig().get(key).getName(), (Enum) e)));
        }
        return regulationsList;
    }

    public List<ConvertNameBean> getValueToEnumDictionaryRegulations(){
        List<ConvertNameBean> regulationsList = new ArrayList<>();
        for(String key:getMappingDictionaryConfig().keySet()){
            regulationsList.add(new ConvertNameBean(key,
                    (value)-> DictionaryConfig.transEnumFromValueDictionary(getMappingDictionaryConfig().get(key).getName(), (String) value)));
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
