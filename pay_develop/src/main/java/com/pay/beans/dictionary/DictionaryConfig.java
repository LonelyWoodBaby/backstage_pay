package com.pay.beans.dictionary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.cache.annotation.CacheDefaults;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author LiYabin
 */
@Component
@CacheDefaults(cacheName = "dictionary")
public class DictionaryConfig {
    Logger logger = LoggerFactory.getLogger(getClass());

    public static Map<String,Map<String,BaseDict>> valueObjectDictionaryCache = new HashMap<>();
    public static Map<String,Map<String,BaseDict>> dataObjectDictionaryCache = new HashMap<>();

    @Autowired
    private DictionaryProperties dictionaryProperties;

    @Bean(name="valueObjectDictionary")
    public Map<String,Map<String,BaseDict>> getValueObjectDictionaryMap(){
        try {
            if(valueObjectDictionaryCache == null || valueObjectDictionaryCache.size()==0){
                logger.info("初始化ValueObjectDictionary");
                this.initDictionaryCache(dictionaryProperties.getValuePackageName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueObjectDictionaryCache;
    }

    @Bean(name="dataObjectDictionary")
    public Map<String,Map<String,BaseDict>> getDataObjectDictionaryMap(){
        try {
            if(dataObjectDictionaryCache == null || dataObjectDictionaryCache.size() ==0){
                logger.info("初始化DataObjectDictionary");
                this.initDictionaryCache(dictionaryProperties.getDataPackageName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataObjectDictionaryCache;
    }

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
        return convertValueFromEnum(keyName,enumValue,valueObjectDictionaryCache);
    }

    /**
     * 从数据对象字典中获取枚举类对象的映射值
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的映射值
     */
    public static String convertValueFromDataDictionary(String keyName,Enum enumValue){
        return convertValueFromEnum(keyName,enumValue,dataObjectDictionaryCache);
    }

    /**
     * 从视图对象字典中获取值在影射中的枚举对象
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的枚举对象
     */
    public static Enum transEnumFromValueDictionary(String keyName, String enumValue){
        return transEnumFromDictionary(keyName,enumValue,valueObjectDictionaryCache);
    }

    /**
     * 从数据对象字典中获取值在影射中的枚举对象
     * @param keyName 枚举类的名称，保存为全路径名称
     * @param enumValue 枚举类的实际值
     * @return 对应的枚举对象
     */
    public static Enum transEnumFromDataDictionary(String keyName,String enumValue){
        return transEnumFromDictionary(keyName,enumValue,dataObjectDictionaryCache);
    }

    private static Enum transEnumFromDictionary(String keyName,String enumValue, Map<String,Map<String,BaseDict>> dictionaryCache){
        Map<String,BaseDict> singleParamMap = dictionaryCache.get(keyName);
        if(singleParamMap == null){
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
            return DefaultDict.noDictionary.value();
        }
        return singleParamMap.getOrDefault(enumValue.name(), DefaultDict.noValue).value();
    }

    private void initDictionaryCache(String filePath) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        logger.info("开始初始化Bean字典列表，读取路径：" + filePath);
        List<String> dicEnumNames = getClassName(filePath);
        for(String dicEnumName:dicEnumNames){
            Class<?> cls = Class.forName(dicEnumName);
            //判断是否实现了BaseDict接口
            if(BaseDict.class.isAssignableFrom(cls)){
                Map<String,BaseDict> singleValueMap = initSingleEnumValue(cls);
                valueObjectDictionaryCache.put(cls.getName(),singleValueMap);
            }
        }
    }

    private Map<String,BaseDict> initSingleEnumValue(Class<?> enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String,BaseDict> singleEnumValueMap = new HashMap<>(5);
        Method method = enumClass.getMethod("values");
        BaseDict[] baseDict = (BaseDict[]) method.invoke(null, new Object[]{});
        Arrays.stream(baseDict).forEach((e)->{
            singleEnumValueMap.put(e.key(),e);
        });
        return singleEnumValueMap;
    }

    private List<String> getClassName(String packageName) {
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
        List<String> fileNames = getClassName(filePath, null);
        return fileNames;
    }

    private List<String> getClassName(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<String>();
        filePath = filePath.replaceAll("test-classes","classes");
        File file = new File(filePath);
        Assert.isTrue(file.exists(),"文件不存在");

        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassName(childFile.getPath(), myClassName));
            } else {
                String childFilePath = childFile.getPath();
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                childFilePath = childFilePath.replace("\\", ".");
                myClassName.add(childFilePath);
            }
        }

        return myClassName;
    }


    public static Enum getEnumByValue(String enumName,Class enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method valuesMethod = enumClass.getMethod("values");
        Enum[] values = (Enum[]) valuesMethod.invoke(enumClass);
        Optional<Enum> result = Arrays.stream(values).filter(v -> v.name().equals(enumName)).findAny();
        Assert.isTrue(result.isPresent(),"Bean字典转换时没有找到对应的字典项。转换值"+enumName+",转换对象:"+enumClass.getName() );
        return result.get();
    }

}
