package com.pay.beans.rules.rulehelper;

import com.pay.beans.rules.entity.ConvertNameBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiYabin
 */
public interface ConvertNameHelper {
    /**
     * 自定义的转换套件列表，主要是将业务类型转换为展现实体Bean时使用。
     * @return
     */
    default List<ConvertNameBean> convertToValueByName(){
        return new ArrayList<>();
    }

    /**
     * 自定义的转换套件列表，主要是将展现实体bean转换为业务实体Bean时使用。
     * @return
     */
    default List<ConvertNameBean> convertToFiledByName(){
        return new ArrayList<>();
    }
}
