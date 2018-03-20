package com.pay.beans.dictionary;

import com.pay.beans.dictionary.base.BaseDict;
import com.pay.beans.dictionary.base.DefaultDict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    private static Logger logger = LoggerFactory.getLogger(DictionaryConfig.class);

    public static Map<String,Map<String,BaseDict>> valueObjectDictionaryCache = new HashMap<>();
    public static Map<String,Map<String,BaseDict>> dataObjectDictionaryCache = new HashMap<>();

    public static Map<String,Map<String,BaseDict>> allObjectDictionaryCache = new HashMap<>();

    @Autowired
    private DictionaryProperties dictionaryProperties;

    @Bean(name="valueObjectDictionary")
    @Cacheable("valueObjectDictionary")
    public Map<String,Map<String,BaseDict>> getValueObjectDictionaryMap(){
        try {
            if(valueObjectDictionaryCache == null || valueObjectDictionaryCache.size()==0){
                logger.info("初始化ValueObjectDictionary");
                this.initDictionaryCache(dictionaryProperties.getValuePackageName(),valueObjectDictionaryCache);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueObjectDictionaryCache;
    }

    @Bean(name="dataObjectDictionary")
    @Cacheable("dataObjectDictionary")
    public Map<String,Map<String,BaseDict>> getDataObjectDictionaryMap(){
        try {
            if(dataObjectDictionaryCache == null || dataObjectDictionaryCache.size() ==0){
                logger.info("初始化DataObjectDictionary");
                this.initDictionaryCache(dictionaryProperties.getDataPackageName(),dataObjectDictionaryCache);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataObjectDictionaryCache;
    }

    private Map<String,Map<String,BaseDict>> getAllObjectDictionaryCache(){
        if(allObjectDictionaryCache == null || allObjectDictionaryCache.size() == 0){
            allObjectDictionaryCache = new HashMap<>(1);
        }
        return allObjectDictionaryCache;
    }

    private void initDictionaryCache(String filePath,Map<String,Map<String,BaseDict>> dictionaryCache) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        logger.info("开始初始化Bean字典列表，读取路径：" + filePath);
        List<String> dicEnumNames = getClassName(filePath);
        for(String dicEnumName:dicEnumNames){
            Class<?> cls = Class.forName(dicEnumName);
            //判断是否实现了BaseDict接口
            if(BaseDict.class.isAssignableFrom(cls)){
                Map<String,BaseDict> singleValueMap = initSingleEnumValue(cls);
                dictionaryCache.put(cls.getName(),singleValueMap);
                getAllObjectDictionaryCache().put(cls.getName(),singleValueMap);
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
}
