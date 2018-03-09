package com.pay.beans.dictionary.base;

/**
 * @author LiYabin
 */
public interface BaseDict {
    /**
     * 用于返回美剧状态的具体值
     * @return 返回这个枚举的具体值，为字符串格式
     */
    String value();

    /**
     * 将该枚举值保存进缓存中时使用的key值，建议使用该枚举类的name方法作为key
     * @return 保存进缓存中所使用的key值
     */
    String key();
}
