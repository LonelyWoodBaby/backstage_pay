package com.pay.beans.beans2;

import com.pay.beans.rules.entity.ConvertNameBean;
import org.springframework.util.Assert;

import java.util.List;

public class NameBeanOptional<T,R> {
    private CopyTemplate template;
    private List<ConvertNameBean> nameBeanList;

    public NameBeanOptional(CopyTemplate template, List<ConvertNameBean> nameBeanList) {
        this.template = template;
        this.nameBeanList = nameBeanList;
    }

    public CopyTemplate<T,R> convertByNameBean(){
        Assert.notNull(this.nameBeanList, "字段名称转换列表配置不得为null");
        CopyTemplate.copyWithRuleOnlyByNameForEnum(template.getSourceObject(),template.getTargetObject(),nameBeanList,false);
        return template;
    }
}
