package com.pay.beans.beans2;

import com.pay.beans.dictionary.DictionaryUtils;
import com.pay.beans.rules.FormatRule;
import com.pay.beans.rules.entity.ConvertNameBean;
import com.pay.beans.rules.entity.ConvertTypeBean;
import com.pay.beans.rules.regulations.ConvertRegulations;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * copyBean的临时储放对象
 * @author liyabin
 * 实现了类型自动推断
 */
public class CopyTemplate<T,R> {
    private T sourceObject;
    private R targetObject;

    public static<T,R> CopyTemplate<T,R> prepare(T source,R target){
        return new CopyTemplate(source,target);
    }
    public CopyTemplate<T,R> copyBeanBase(){
        Assert.notNull(sourceObject, "转换源对象不得为null对象");
        Assert.notNull(targetObject, "转换目标对象不得为null对象");
        org.springframework.beans.BeanUtils.copyProperties(sourceObject,targetObject);
        return this;
    }

    public CopyDictOptional<T,R> setDictionaryConfig(Map<String,Class> dictionaryConfig){
        return new CopyDictOptional(this,dictionaryConfig);
    }

    public TypeBeanOptional<T,R> setConvertTypeBean(List<ConvertTypeBean> typeBeanList){
        return new TypeBeanOptional(this,typeBeanList);
    }

    public NameBeanOptional<T,R> setConvertNameBean(List<ConvertNameBean> nameBeanList){
        return new NameBeanOptional(this,nameBeanList);
    }

    List<ConvertNameBean> createEnumToValueDictionaryRegulations(Map<String,Class> otherMappingConfig){
        List<ConvertNameBean> regulationsList = new ArrayList<>();
        for(String key:otherMappingConfig.keySet()){
            regulationsList.add(new ConvertNameBean(key,
                    (e)-> DictionaryUtils.convertValueFromDictionary(otherMappingConfig.get(key).getName(), (Enum) e)));
        }
        return regulationsList;
    }

    static void copyWithRuleOnlyByType(Object source, Object target, List<ConvertTypeBean> convertRegulations){
        Predicate<Field> noneMatchFunction = (Field f)-> Arrays.stream(target.getClass().getDeclaredFields())
                .noneMatch(tf ->tf.getName().equals(f.getName()) && ConvertRegulations.hadClassType(convertRegulations,f.getType()));
        Predicate<Field> validateFunction = f -> true;
        Function<Field,FormatRule> getRuleFunction = f -> ConvertRegulations.getFormatRuleByType(convertRegulations,f.getType());
        copyBeanWithRuleService(source,target,noneMatchFunction,validateFunction,getRuleFunction);
    }

    static void copyBeanWithRuleService(Object source, Object target, Predicate<Field> noneMatchFunction, Predicate<Field> validateFunction, Function<Field,FormatRule> getRuleFunction){
        copyBeanWithRuleService(source,target,noneMatchFunction,validateFunction,getRuleFunction,false);
    }

    static void copyBeanWithRuleService(Object source, Object target, Predicate<Field> noneMatchFunction, Predicate<Field> validateFunction, Function<Field,FormatRule> getRuleFunction,boolean isConvertEnum){

        Field[] fields = source.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(
                (Field f) -> {
                    if(noneMatchFunction.test(f)){
                        return;
                    }
                    String fieldName = f.getName();

                    String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                    String setMethodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                    try {
                        Method sourceGetMethod = source.getClass().getMethod(getMethodName);
                        Method targetSetMethod = target.getClass().getMethod(setMethodName,target.getClass().getDeclaredField(fieldName).getType());
                        if(isConvertEnum && target.getClass().getDeclaredField(fieldName).getType().isEnum() && validateFunction.test(f)){
                            Enum enumName = (Enum) getRuleFunction.apply(f).transFunction( sourceGetMethod.invoke(source));
                            targetSetMethod.invoke(target, getEnumByValue(enumName.name(),target.getClass().getDeclaredField(fieldName).getType()));
                        }else if(validateFunction.test(f) && sourceGetMethod.invoke(source) != null){
                            targetSetMethod.invoke(target,getRuleFunction.apply(f).transFunction( sourceGetMethod.invoke(source)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private static Enum getEnumByValue(String enumName,Class enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method valuesMethod = enumClass.getMethod("values");
        Enum[] values = (Enum[]) valuesMethod.invoke(enumClass);
        Optional<Enum> result = Arrays.stream(values).filter(v -> v.name().equals(enumName)).findAny();
        Assert.isTrue(result.isPresent(),"Bean字典转换时没有找到对应的字典项。转换值"+enumName+",转换字典项:"+enumClass.getName() );
        return result.get();
    }

    static void copyWithRuleOnlyByNameForEnum(Object source, Object target, List<ConvertNameBean> convertRegulations, boolean isConvertEnum){
        Predicate<Field> noneMatchFunction = (Field f)-> Arrays.stream(target.getClass().getDeclaredFields())
                .noneMatch(tf ->tf.getName().equals(f.getName())
                        && ConvertRegulations.hadFieldName(convertRegulations,tf.getName()));
        Predicate<Field> validateFunction = f -> true;
        Function<Field,FormatRule> getRuleFunction = f -> ConvertRegulations.getFormatRuleByFieldName(convertRegulations,f.getName());
        copyBeanWithRuleService(source,target,noneMatchFunction,validateFunction,getRuleFunction,isConvertEnum);
    }

    public T getSourceObject() {
        return sourceObject;
    }

    public R getTargetObject() {
        return targetObject;
    }

    private CopyTemplate(T sourceObject, R targetObject) {
        this.sourceObject = sourceObject;
        this.targetObject = targetObject;
    }
}
