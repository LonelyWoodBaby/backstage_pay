package com.pay.beans.rules.regulations;

import com.pay.beans.dictionary.DictionaryConfig;
import com.pay.beans.dictionary.DictionaryUtils;
import com.pay.beans.dictionary.view.UserStatusDict;
import com.pay.beans.rules.entity.ConvertNameBean;
import com.pay.beans.rules.entity.ConvertTypeBean;
import com.pay.beans.rules.FormatRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LiYabin
 */
public class ConvertRegulations {
    /**
     * 默认设置好的 T -> String的转换，主要对Date类型和Double类型的转换
     */
    public static final List<ConvertTypeBean> MODEL_TO_DATA_MODEL_REGULATION = getModelToDataModelRegulation();

    /**
     * 测试用的转换 String -> T的转换
     */
    public static final List<ConvertNameBean> VALUE_TO_MODEL_REGULATION = getValueToModelRegulation();

    private static List<ConvertTypeBean> getModelToDataModelRegulation(){
        List<ConvertTypeBean> beanList = new ArrayList<>();
        beanList.add(new ConvertTypeBean(Double.class, FormatRule.formatAmountRule("#,###.###")));
        beanList.add(new ConvertTypeBean(Date.class, FormatRule.formatDateRule("yyyy年MM月dd日HH时mm分ss秒：SSS")));
        return beanList;
    }

    private static List<ConvertNameBean> getValueToModelRegulation()  {
        List<ConvertNameBean> beanList = new ArrayList<>();
        beanList.add(new ConvertNameBean("salary", FormatRule.convertToDouble("#,###.###")));
        beanList.add(new ConvertNameBean("birthday", FormatRule.convertToDate("yyyy年MM月dd日HH时mm分ss秒")));
        return beanList;
    }

    /************以下为工具类的API方法集合*************/

    public static boolean hadClassType(List<ConvertTypeBean> typeBeanList,Class valClass){
        return typeBeanList.stream().anyMatch(c -> c.getClazz() == valClass);
    }

    public static boolean hadFieldName(List<ConvertNameBean> typeBeanList,String fieldName){
        return typeBeanList.stream().anyMatch(c -> c.getFieldName().equals(fieldName));
    }

    public static FormatRule getFormatRuleByType(List<ConvertTypeBean> typeBeanList,Class valClass){
        return typeBeanList.stream().filter(c -> c.getClazz() == valClass).findAny().get().getConvertRule();
    }

    public static FormatRule getFormatRuleByFieldName(List<ConvertNameBean> typeBeanList,String fieldName){
        return typeBeanList.stream().filter(c -> c.getFieldName().equals(fieldName)).findAny().get().getConvertRule();
    }
}
