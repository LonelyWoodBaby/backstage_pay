# 项目自定义方法使用API说明

## 工具类：com.pay.util

### 日志统一处理工具：LoggerUtil

#### 简要描述：

* 项目开发过程中，如果出现需要抛出运行时异常的代码，尽量使用本类提供的方法。
* 可以统一处理运行时异常，防止出现抛异常时的不一致的行为
* 运行时异常必须要打印错误堆栈，并记录在log日志中。
* 开发时尽可能使用throwNewException方法，当然也可以使用lambda语法自定义异常的处理机制。

#### 使用示例：

1. 如果代码开发过程中需要抛出运行时异常，可使用如下方式来抛出。需要传递当前类的日志实例。
````java
//定义日志实例
private final Logger logger = LoggerFactory.getLogger(getClass());
//其他代码 ...
public boolean insertAll(Object[] arrays) {
    if(arrays == null || arrays.length == 0){
        //此处使用throwNewException方法抛出参数错误异常，并将
        throw LoggerUtil.throwNewException(new IllegalArgumentException("传递的参数值为空或不存在"),logger);
    }
    return insertAll(Arrays.asList(arrays));
}
````
2. 如果需要自定义日志记录的信息，可以使用如下的方式
```java
String logMessage = "传递的参数值为空或不存在";
//....其他代码
throw LoggerUtil.throwNewException(new IllegalArgumentException(),logger,logMessage);
//此时，日志里将记录的是logMessage的信息
```
3. 如果需要自定义日志记录的方式，可以使用lambda语法来进行设置。函数签名为`()->RuntimeException`
```java
//直接声明一个异常
throw LoggerUtil.throwNewExceptionFunction(IllegalArgumentException::new);

//自定义一个异常信息
throw LoggerUtil.throwNewExceptionFunction(() ->{
    IllegalArgumentException e = new IllegalArgumentException();
    logger.error("出现参数错误",e);
    return e;
});
```
4. 需要注意的是，使用自定义异常处理方式时，默认会使用LoggerUtil的日志管理实例来记录异常错误信息。如果不想让LoggerUtil自动记录日志，需要显式自定第二个参数isLog为`false`
```java
//声明第二个参数为false后，LoggerUtil将不会再记录一遍日志
throw LoggerUtil.throwNewExceptionFunction(() ->{
    IllegalArgumentException e = new IllegalArgumentException();
    logger.error("出现参数错误",e);
    return e;
},false);

```