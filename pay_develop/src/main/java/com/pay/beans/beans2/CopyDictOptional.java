package com.pay.beans.beans2;

import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author LiYabin
 * 主要包括对字典项的处理
 */
public class CopyDictOptional<T,R> {
    private CopyTemplate template;
    private Map<String,Class> mappingDictionaryConfig;

    public CopyDictOptional(CopyTemplate template, Map<String, Class> mappingDictionaryConfig) {
        this.template = template;
        this.mappingDictionaryConfig = mappingDictionaryConfig;
    }

    public CopyTemplate<T,R> covertDictEnumToString(){
        Assert.notNull(mappingDictionaryConfig, "字典对象配置不得为null");
        //enum -> String
        CopyTemplate.copyWithRuleOnlyByNameForEnum(template.getSourceObject(),template.getTargetObject(),template.createEnumToValueDictionaryRegulations(this.mappingDictionaryConfig),false);
        return this.template;
    }

    public CopyTemplate<T,R> covertDictStringToEnum(){
        Assert.notNull(mappingDictionaryConfig, "字典对象配置不得为null");
        //String -> enum
        CopyTemplate.copyWithRuleOnlyByNameForEnum(template.getSourceObject(),template.getTargetObject(),template.createEnumToValueDictionaryRegulations(this.mappingDictionaryConfig),true);
        return this.template;
    }
}
