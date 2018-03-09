package com.pay.beans.rules.rulehelper;

import com.pay.beans.entity.ConvertTypeBean;

import java.util.ArrayList;
import java.util.List;

public interface ConvertTypeHelper {
    default List<ConvertTypeBean> convertToValueByType(){
        return new ArrayList<>();
    }

    default List<ConvertTypeBean> convertToFiledByType(){
        return new ArrayList<>();
    }
}
