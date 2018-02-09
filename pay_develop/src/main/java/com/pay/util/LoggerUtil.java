package com.pay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lee Yarbin
 */
public class LoggerUtil {

    private static Logger thisLogger = LoggerFactory.getLogger(LoggerUtil.class);

    /**
     * 统一所有的运行时异常的处理机制。
     * @param e 传递一个运行时异常，可以包含信息。
     * @param logger 要进行记录的日志对象
     * @param errorMessage 自定义的错误信息。如果此信息为空，或者不传递此信息，则会使用e中的message属性作为异常信息记录
     * @return 返回一个runtime异常信息，此信息以后可以进行自定义
     */
    public static RuntimeException throwNewException(RuntimeException e, Logger logger, String errorMessage) {
        return throwNewExceptionFunction(() -> {
            logger.error(errorMessage == null ? e.getMessage() : errorMessage + "/n" , e);
            return e;
        },false);
    }

    public static RuntimeException throwNewException(RuntimeException e, Logger logger) {
        return throwNewException(e, logger, null);
    }

    public static RuntimeException throwNewExceptionFunction(LogFunction function,boolean isLog) {
        RuntimeException e =  function.process();
        e.printStackTrace();
        if(isLog){thisLogger.error(e.getMessage(),e);}
        return e;
    }

    public static RuntimeException throwNewExceptionFunction(LogFunction function) {
        return throwNewExceptionFunction(function,true);
    }

}
