package com.pay.beans.dictionary;

import com.pay.beans.dictionary.base.BaseDict;
import com.pay.beans.dictionary.base.DefaultDict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * @author LiYabin
 */
public class DictionaryUtils {
    private static Logger logger = LoggerFactory.getLogger(DictionaryConfig.class);

    public static String convertFromValueDictionary(String keyName, Enum enumValue){
        String result = convertValueFromValueDictionary(keyName,enumValue);
        return result == null || "".equals(result) ?
                convertValueFromDataDictionary(keyName,enumValue):result;
    }

    /**
     * 从视图对象字典中获取枚举类对象的映射值
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的映射值
     */
    public static String convertValueFromValueDictionary(String keyName, Enum enumValue){
        return convertValueFromEnum(keyName,enumValue,DictionaryConfig.valueObjectDictionaryCache);
    }

    /**
     * 从数据对象字典中获取枚举类对象的映射值
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的映射值
     */
    public static String convertValueFromDataDictionary(String keyName,Enum enumValue){
        return convertValueFromEnum(keyName,enumValue,DictionaryConfig.dataObjectDictionaryCache);
    }

    /**
     * 从全部对象字典中获取枚举类对象的映射值
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的映射值
     */
    public static String convertValueFromDictionary(String keyName,Enum enumValue){
        return convertValueFromEnum(keyName,enumValue,DictionaryConfig.allObjectDictionaryCache);
    }

    /**
     * 从视图对象字典中获取值在影射中的枚举对象
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的枚举对象
     */
    public static Enum transEnumFromValueDictionary(String keyName, String enumValue){
        return transEnumFromDictionary(keyName,enumValue,DictionaryConfig.valueObjectDictionaryCache);
    }

    /**
     * 从数据对象字典中获取值在影射中的枚举对象
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的枚举对象
     */
    public static Enum transEnumFromDataDictionary(String keyName,String enumValue){
        return transEnumFromDictionary(keyName,enumValue,DictionaryConfig.dataObjectDictionaryCache);
    }

    /**
     * 全部对象字典中获取值在影射中的枚举对象
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的枚举对象
     */
    public static Enum transEnumFromDictionary(String keyName, String enumValue){
        return transEnumFromDictionary(keyName,enumValue,DictionaryConfig.allObjectDictionaryCache);
    }

    public static Enum getEnumByValue(String enumName,Class enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method valuesMethod = enumClass.getMethod("values");
        Enum[] values = (Enum[]) valuesMethod.invoke(enumClass);
        Optional<Enum> result = Arrays.stream(values).filter(v -> v.name().equals(enumName)).findAny();
        Assert.isTrue(result.isPresent(),"Bean字典转换时没有找到对应的字典项。转换值"+enumName+",转换对象:"+enumClass.getName() );
        return result.get();
    }

    private static Enum transEnumFromDictionary(String keyName,String enumValue, Map<String,Map<String,BaseDict>> dictionaryCache){
        Map<String,BaseDict> singleParamMap = dictionaryCache.get(keyName);
        if(singleParamMap == null){
            logger.warn("没有获取到对应的字典集合。查询类名： " + keyName);
            return DefaultDict.noDictionary;
        }
        Optional<BaseDict> result = singleParamMap.values().stream().filter(baseDict -> baseDict.value().equals(enumValue)).findAny();
        if(result.isPresent()){
            return (Enum) result.get();
        }else{
            return DefaultDict.noValue;
        }
    }

    private static String convertValueFromEnum(String keyName,Enum enumValue,Map<String,Map<String,BaseDict>> dictionaryCache){
        Map<String,BaseDict> singleParamMap = dictionaryCache.get(keyName);
        if(singleParamMap == null){
            logger.warn("没有获取到对应的字典集合。查询类名： " + keyName);
            return DefaultDict.noDictionary.value();
        }
        return singleParamMap.getOrDefault(enumValue.name(), DefaultDict.noValue).value();
    }

}
