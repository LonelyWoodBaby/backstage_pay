package com.pay.beans.rules;

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
}
