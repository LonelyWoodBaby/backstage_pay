package com.pay.util;

@FunctionalInterface
public interface LogFunction {
    /**
     * 实现用于日志系统的函数化接口
     * @return
     */
    RuntimeException process();
}
