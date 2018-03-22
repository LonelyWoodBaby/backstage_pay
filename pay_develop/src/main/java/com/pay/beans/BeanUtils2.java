package com.pay.beans;

import com.pay.beans.beans2.CopyTemplate;
import com.pay.beans.dictionary.base.BaseBean;
import com.pay.beans.rules.FormatRule;
import com.pay.beans.rules.entity.ConvertNameBean;
import com.pay.beans.rules.entity.ConvertTypeBean;
import com.pay.beans.rules.regulations.ConvertRegulations;
import com.pay.beans.rules.rulehelper.ConvertNameHelper;
import com.pay.beans.rules.rulehelper.ConvertTypeHelper;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
public class BeanUtils2 {

    public static<T,R> CopyTemplate<T,R> prepare(T source,R target){
        return CopyTemplate.prepare(source,target);
    }
}

