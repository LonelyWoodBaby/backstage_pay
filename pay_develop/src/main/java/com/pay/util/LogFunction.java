package com.pay.util;

@FunctionalInterface
public interface LogFunction {
    RuntimeException process();
}
