package com.pay.beans;

import com.pay.beans.dictionary.BaseBean;
import com.pay.beans.dictionary.DefaultDict;
import com.pay.beans.dictionary.DictionaryConfig;
import com.pay.beans.entity.ConvertNameBean;
import com.pay.beans.entity.ConvertTypeBean;
import com.pay.beans.rules.FormatRule;
import com.pay.beans.rules.regulations.ConvertRegulations;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author 亚斌
 * 主要用于各层之间的Bean对象的转换，并提供转换时对一些值的自动转换
 * 使用约束：
 * 1.要求源对象与目标对象之间的变量名称保持一致（区分大小写）
 * 2.如果存在相同名称但不相同的类型，需要添加转换规则。
 */
public class BeanUtils {

    /**
     * 单纯的对象复制，采用spring提供的复制方法，可以有效复制变量值以及对应的列表数据。
     * 是目前来说效率最高的复制方法。
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyBeanBase(@NotNull Object source, Object target) {
        Assert.notNull(source, "转换源对象不得为null对象");
        Assert.notNull(target, "转换目标对象不得为null对象");
        org.springframework.beans.BeanUtils.copyProperties(source,target);
    }

    /**
     * 根据转换规则来进行转换，需要传入一个规则定义，将会把符合该类型的所有变量按照对应的转换规则改变
     * @param source 源对象
     * @param target 目标对象
     * @param beans 需要进行转换的规则，为一个不定对象组。
     */
    public static void copyBeanWithRuleByType(Object source, Object target, @Nullable ConvertTypeBean... beans){
        if(beans == null || beans.length == 0){
            copyBeanBase(source,target);
            return;
        }
        if(beans.length == 1){
            Predicate<Field> noneMatchFunction = (Field f)-> Arrays.stream(target.getClass().getDeclaredFields())
                    .noneMatch(tf ->tf.getName().equals(f.getName()));
            Predicate<Field> validateFunction = (Field f)-> beans[0].getClazz() == f.getType();
            Function<Field,FormatRule> getRuleFunction = f -> beans[0].getConvertRule();
            copyBeanWithRuleService(source,target,noneMatchFunction,validateFunction,getRuleFunction);
        }else{
            copyBeanWithRuleByType(source,target,Arrays.asList(beans));
        }
    }

    /**
     * 根据转换规则来进行转换，需要传入一个规则定义，将会把符合该类型的所有变量按照对应的转换规则改变
     * @param source 源对象
     * @param target 目标对象
     * @param convertRegulations 自定义的转换规则列表。
     */
    public static void copyBeanWithRuleByType(Object source, Object target, List<ConvertTypeBean> convertRegulations){
        Predicate<Field> noneMatchFunction = (Field f)-> Arrays.stream(target.getClass().getDeclaredFields())
                .noneMatch(tf ->tf.getName().equals(f.getName()) && ConvertRegulations.hadClassType(convertRegulations,f.getType()));
        Predicate<Field> validateFunction = f -> true;
        Function<Field,FormatRule> getRuleFunction = f -> ConvertRegulations.getFormatRuleByType(convertRegulations,f.getType());
        copyBeanWithRuleService(source,target,noneMatchFunction,validateFunction,getRuleFunction);
    }

    /**
     * 定义好需要特殊格式化的变量名称（区分大小写），如若查找到符合要求的变量，则会根据格式化规则进行转换
     * 需要注意的是，确定好目标对象的对象类型
     * @param source 源对象
     * @param target 目标对象
     * @param beans 要转换的规则对象，不定参数。
     */
    public static void copyBeanWithRuleByName(Object source, Object target, @Nullable ConvertNameBean... beans){
        if(beans == null || beans.length == 0){
            copyBeanBase(source,target);
            return;
        }
        if(beans.length == 1){
            Predicate<Field> noneMatchFunction = (Field f)-> Arrays.stream(target.getClass().getDeclaredFields())
                    .noneMatch(tf ->tf.getName().equals(f.getName()));
            Predicate<Field> validateFunction = (Field f)-> beans[0].getFieldName().equals(f.getName());
            Function<Field,FormatRule> getRuleFunction = f -> beans[0].getConvertRule();
            copyBeanWithRuleService(source,target,noneMatchFunction,validateFunction,getRuleFunction);
        }else{
            copyBeanWithRuleByName(source,target,Arrays.asList(beans));
        }
    }

    /**
     * 定义好需要特殊格式化的变量名称（区分大小写），如若查找到符合要求的变量，则会根据格式化规则进行转换
     * 需要注意的是，确定好目标对象的对象类型
     * @param source 源对象
     * @param target 目标对象
     * @param convertRegulations 要转换的规则对象，此处为列表格式
     */
    public static void copyBeanWithRuleByName(Object source, Object target, List<ConvertNameBean> convertRegulations){
        copyBeanWithRuleByNameForEnum(source,target,convertRegulations,false);
    }

    public static void copyBeanBaseForEnum(@NotNull Object source, Object target) {
        Assert.notNull(source, "转换源对象不得为null对象");
        Assert.notNull(target, "转换目标对象不得为null对象");
        copyBeanBase(source,target);
        //enum -> String
        if(BaseBean.class.isAssignableFrom(target.getClass())
                && ((BaseBean)target).getEnumToValueDictionaryRegulations().size() > 0){
            copyBeanWithRuleByName(source,target,((BaseBean)target).getEnumToValueDictionaryRegulations());
        }
        //String -> enum
        if(BaseBean.class.isAssignableFrom(source.getClass())
                && ((BaseBean)source).getValueToEnumDictionaryRegulations().size() > 0){
            copyBeanWithRuleByNameForEnum(source,target,((BaseBean)source).getValueToEnumDictionaryRegulations(),true);
        }
    }

    private static void copyBeanWithRuleByNameForEnum(Object source, Object target, List<ConvertNameBean> convertRegulations,boolean isConvertEnum){
        Predicate<Field> noneMatchFunction = (Field f)-> Arrays.stream(target.getClass().getDeclaredFields())
                .noneMatch(tf ->tf.getName().equals(f.getName())
                        && ConvertRegulations.hadFieldName(convertRegulations,tf.getName()));
        Predicate<Field> validateFunction = f -> true;
        Function<Field,FormatRule> getRuleFunction = f -> ConvertRegulations.getFormatRuleByFieldName(convertRegulations,f.getName());
        copyBeanWithRuleService(source,target,noneMatchFunction,validateFunction,getRuleFunction,isConvertEnum);
    }

    /**
     * 抽象化了赋值对象的所有行为，将有可能发生改变的函数抽离出来，尽可能减少代码的重复部分。
     * 本来想把forEach中的函数也抽象化出来，但担心会导致代码层级过深不好阅读。暂且这么设计。
     * @param source 源对象
     * @param target 目标对象
     * @param noneMatchFunction 做一个过滤判断，符合此判断规则的才会进行下一步操作，否则将会直接返回。
     * @param validateFunction 在满足noneMatchFunction的基础上，判断直接运行的条件，当符合要求时开始执行格式化规则
     * @param getRuleFunction 获取具体格式化规则的函数
     */
    private static void copyBeanWithRuleService(Object source, Object target, Predicate<Field> noneMatchFunction, Predicate<Field> validateFunction, Function<Field,FormatRule> getRuleFunction){
        copyBeanWithRuleService(source,target,noneMatchFunction,validateFunction,getRuleFunction,false);
    }

    private static void copyBeanWithRuleService(Object source, Object target, Predicate<Field> noneMatchFunction, Predicate<Field> validateFunction, Function<Field,FormatRule> getRuleFunction,boolean isConvertEnum){
        copyBeanBase(source,target);
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
                        }else if(validateFunction.test(f)){
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


}
