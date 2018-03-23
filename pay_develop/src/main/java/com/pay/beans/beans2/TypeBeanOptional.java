package com.pay.beans.beans2;

import com.pay.beans.rules.entity.ConvertTypeBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author LiYabin
 * 实现对ConvertTypeBean的业务处理
 */
public class TypeBeanOptional<T,R> {
    private CopyTemplate template;
    private List<ConvertTypeBean> typeBeanList;

    public TypeBeanOptional(CopyTemplate template, List<ConvertTypeBean> typeBeanList) {
        this.template = template;
        this.typeBeanList = typeBeanList;
    }

    public CopyTemplate<T,R> convertByTypeBean(){
        Assert.notNull(typeBeanList, "类型转换列表配置不得为null");
        CopyTemplate.copyWithRuleOnlyByType(template.getSourceObject(),template.getTargetObject(),typeBeanList);
        return template;
    }

}
