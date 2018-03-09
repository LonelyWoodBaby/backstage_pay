package com.pay.beans.rules.rulehelper;

import com.pay.beans.entity.ConvertNameBean;
import com.pay.beans.entity.ConvertTypeBean;

import java.util.ArrayList;
import java.util.List;

public interface ConvertNameHelper {
    default List<ConvertNameBean> convertToValueByName(){
        return new ArrayList<>();
    }

    default List<ConvertNameBean> convertToFiledByName(){
        return new ArrayList<>();
    }
}
