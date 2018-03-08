package com.pay.beans.test;

import com.pay.beans.dictionary.base.DefaultDict;
import com.pay.beans.dictionary.DictionaryConfig;
import com.pay.beans.dictionary.DictionaryProperties;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBeanUtilsForDic {
    @Autowired
    private DictionaryProperties dictionaryProperties;

    @Autowired
    private DictionaryConfig dictionaryConfig;

    @Ignore
    public void testReadPropertiesValue(){
        System.out.println(dictionaryProperties.getDataPackageName());
    }

    @Ignore
    public void testInitValueObjectCache() {

        String value = DictionaryConfig.convertValueFromValueDictionary(com.pay.beans.dictionary.view.UserStatusDict.class.getName(),UserStatusDict.noStatus);
        Assert.assertEquals(value,"");
    }

    @Ignore
    public void testGetEnumFromCache(){
        String value = "200";
        com.pay.beans.dictionary.view.UserStatusDict userStatusDict = (com.pay.beans.dictionary.view.UserStatusDict) DictionaryConfig.transEnumFromValueDictionary(com.pay.beans.dictionary.view.UserStatusDict.class.getName(),value);
        System.out.println(userStatusDict.name());
    }

    @Test
    public void testGetEnumByName() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String name = "ffff";
        Enum result = DictionaryConfig.getEnumByValue(name,UserStatusDict.class);
        if(result != DefaultDict.noValue){
            UserStatusDict userStatusDict = (UserStatusDict)result;
            System.out.println(userStatusDict.name());
        }else{
            Assert.assertEquals(result,DefaultDict.noValue);
        }

    }
}
