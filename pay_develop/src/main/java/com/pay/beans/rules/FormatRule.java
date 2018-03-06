package com.pay.beans.rules;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author LiYabin
 */
public interface FormatRule<T, R> {
    /**
     * 转换方法函数
     * @param sourceValue
     * @return 返回转换后的目标对象
     * @throws ParseException 转换失败时需要抛出异常
     */
    R transFunction(T sourceValue) throws ParseException;

    /**
     * 输入日期转换格式的正则 Date -> String
     * @param datePattern
     * @return 返回一个按照日期转换格式编译好的字符串
     * TODO: 需要将内部逻辑实现修改为java8推荐的LocalDateTime方法
     */
    static FormatRule formatDateRule(String datePattern) {
        Assert.notNull(datePattern,"格式化规则不可为空");
        return new SimpleDateFormat(datePattern)::format;
    }

    /**
     * 转换金钱至字符串格式 Double -> String
     * @param amountPattern DecimalFormat定义的字符串格式，可以为#,###.##，或者$#.##
     * @return 返回按照定义格式化好的字符串
     */
    static FormatRule formatAmountRule(String amountPattern) {
        Assert.notNull(amountPattern,"日期格式化规则不可为空");
        return new DecimalFormat(amountPattern)::format;
    }

    /**
     * 将布尔值按照规则格式化为对应的值对象
     * @param results
     * @return
     */
    static FormatRule formaBooleanRule(String[] results) {
        Assert.notNull(results,"布尔转换的数组格式不得为空");
        Assert.isTrue(results.length >=2,"转换依据数组的长度应不小于2");
        return (b)->{
            Assert.isTrue(b instanceof Boolean,"传入参数应为布尔值");
            return Boolean.valueOf((Boolean)b)? results[0]:results[1];
        };
    }

    /**
     * 将一个符合要求的字符串格式化转换为时间  String -> Date
     * @param datePattern 日期格式化正则
     * @return 格式化好的时间Date
     */
    static FormatRule convertToDate(String datePattern) {
        Assert.notNull(datePattern,"日期格式化规则不可为空");
        return (sourceDate)->{
            LocalDateTime localDateTime = LocalDateTime.parse((String)sourceDate, DateTimeFormatter.ofPattern(datePattern));
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        };
    }

    /**
     * 将一个金钱字符串格式化为double类型
     * @param amountPattern DecimalFormat定义的字符串格式，可以为#,###.##，或者$#.##
     * @return 按照定义返回转换后的Double类型的值
     */
    static FormatRule convertToDouble(String amountPattern){
        Assert.notNull(amountPattern,"日期格式化规则不可为空");
        return (sourceValue -> new DecimalFormat(amountPattern).parse((String) sourceValue).doubleValue());
    }

    /**
     * 将一个属性值转换为布尔值
     * @param value 当判断的依据为value时，返回布尔值为真，否则为false
     * @return 判断后的布尔值
     */
    static FormatRule convertToBoolean(String value){
        Assert.notNull(value,"判断真值的方式不应为空");
        return (v) -> value.equals((String)v);
    }
}
