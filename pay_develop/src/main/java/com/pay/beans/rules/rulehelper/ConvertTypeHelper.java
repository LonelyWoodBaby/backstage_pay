package com.pay.beans.rules.rulehelper;

import com.pay.beans.rules.entity.ConvertTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiYabin
 * 用于扩展按照类型转换Bean的接口继承此接口的beans将在被转换时按照赋予的转换规则自动赋值
 */
public interface ConvertTypeHelper {
    /**
     * 自定义的转换套件列表，主要是将业务类型转换为展现实体Bean时使用。
     * @return
     */
    default List<ConvertTypeBean> convertToValueByType(){
        return new ArrayList<>();
    }

    /**
     * 自定义的转换套件列表，主要是将展现实体bean转换为业务实体Bean时使用。
     * @return
     */
    default List<ConvertTypeBean> convertToFiledByType(){
        return new ArrayList<>();
    }
}
