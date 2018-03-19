# javaBeans分层结构说明

## 名词

### Bean的分层架构

1. `业务逻辑层Bean` - `BasicObject` - `BO`
- 主要为业务操作实体Bean，此处bean的属性类型的设计应符合业务模型，不应受展示或持久化所限制。
2. `展示层Bean` - `ViewObject - VO`
- 可作为前端展现时的相互约定的字段模型
- 可作为通信端通讯时使用的bean模型
- 如果使用hibernate的Validation组件时可以在vo上进行设置
- 字典项的值应为字符串格式
- 与映射的BO对象应保持同样的变量名称
3. `持久层Bean` - `DataObject - DO`
- 主要功用是提供给持久层框架使用的Bean实体对象
- 变量属性的类型设计应尽量贴合数据库的字段属性
- 字典项的值应为字符串格式
- 与映射的BO对象应保持同样的变量名称
- 使用持久层框架注解时（例如@Table，@Column等）可以直接在DO上进行设置

### Q&A

Q: 为什么分层？
> A：
>
> 1. 分层后Bean的代码变多，但是各自承担了自己的工作任务，例如VO将仅对前端或者通讯C端负责，DO将只对数据库负责，从责任上区分开每个Bean的任务。
> 
> 2. 有些组件需要在Bean上添加注解来使用，例如与前端通讯时我们可以使用Hibernate提供的Validation组件或者Spring提供的VierJson组件来设置外部通讯时对于Bean的处理，而后端需要与数据库映射，可能会使用到@Table或者@Column等注解进行映射。分开这些Bean，可以将逻辑设置得更清晰
>
> 3. 面对不同的C端，所使用的模型可能不同。在java开发逻辑内部，我们可以使用Date类型来计算时间，或者Double类型等计算金额，但对于前端或者数据库来说，需要格式化部分的字段类型。例如金额需要格式化为$33,123.22或者时间需要格式化为YYYY年MM月dd日等，如果在设计基础类型时还要考虑前端的展示和后端的存储，则这个类承担的思想过多，容易混乱。
>
> 4. 不同的端所使用的字典项可能不同。同样是成功(success)，后端可能保存为0，前端可能直接展示为success。因此需要不同的Bean实体来划分各自负责的区域。字典项的拆分开也有助于面对不同的C端采用不同的字典项。同样的字段，只需要配置不同的字典项，可以转换成为不同的结果。

> Q: 设计思路是怎样的？
> 
> A: 
> 
> 尽可能使用`无配置化、配置代码化`的方式进行设计。尽可能的解耦化和可拆解。表现为：
> 1. 需要进行转换的Bean，可以外接抽象类或者接口，实现对应的方法。需要不同的转换，改变所要实现的接口即可。
> 2. 字典项配置为枚举类型，这样在基础业务类型（BO）中，可以直接使> 用枚举来代表状态，而不用再去判断字符串值。同样，VO与DO可以通过配置好的字典，直接拿到转换后的值。
> 3. 字典项的值可能会根据业务需求发生变动，例如某选项需要四个option，也就是说该字典有四个状态值，但可能因为业务变动只需要展示> 两个，我们只需要另写一个枚举配置类即可。
> 4. 如果多个状态值可能表达的是同一个结果，我们也只需要修改该字典项的value值一样即可。不需要另外配置。
> 
> Q: 主要解决的问题？
> 
> A： 
> 
> 类转换过程中主要需要解决两个方面的问题：
> 1. `不同类型的格式化转换`，例如Date -> String格式，Boolean -> [0],[1]格式等等。
> 2. 将字典项配置为枚举类型后，如何将该`枚举自动转换为对应的字符串`（或其他格式）的结果。
> 
> 另外需要和正在解决的问题是，
> 1. 考虑到项目实际情况，尽可能将学习成本降低，以及后期的维护化。
> 2. 在兼顾功能的情况下，尽可能的提升转换效率

## 代码架构说明：

### 字典
1. 字典均为枚举类型.
2. 给业务层使用的BO类可以设为基础类型，即不设置结果值的枚举类。但VO类和DO类的字典枚举需要分别实现`ValueDict`和`DataDict`接口，并实现其中的`value()`和`key()`方法。
3. value方法主要目的为设置每个状态实际值，也就是VO和DO转换后最终所保存的值。此版本暂时都设置为字符串项。name()方法为该字典项保存进缓存中的key名。
4. 字典枚举类的类名随意`不做限制`，但如果相互转换时，设置匹配时，将会根据使用该枚举类的`字段名`作为索引进行检查
5. 原则上，字典项的枚举类存放位置可以随意。组件启动时，仅会根据是否继承了ValueDict和DataDict来判断该枚举是否为字典项，从而加入缓存。但为效率考虑，`建议将字典项集中存储在某个包中。可以在该包下建立子包来分类保存，理清逻辑关系`。

### 转换规则
1. 转换规则实际上就是实现`FormatRule<T,R>`接口。该接口接收一个值，并返回转换后的结果值。
2. 已经提供了三种常用的转换，分别为`Date <-> String`,`Double <-> String`，`Boolean <-> String`
3. 使用了两个实体类来保存转换规则接口，分别是`ConvertNameBean`和`ConvertTypeBean`，前者是利用`字段属性的名称`来判断哪些字段需要转换，后者是根据`字段类型`来进行判断。例如
```java
new ConvertTypeBean(Date.class, FormatRule.formatDateRule("yyyy年MM月dd日HH时mm分ss秒：SSS"))
```
则是声明凡是Date类型的，都会被格式化为yyyy年MM月dd日HH时mm分ss秒：SSS的格式类型字符串

## 使用

我们假设一个业务类型Bean为User类，分析他的基本架构，设计如下
```java
public class User{
    private String username;//名称
    private double salary;//薪水

    //这是个字典项
    private WokState wokstate; //工作状态
    private Date birthday; //生日
    

    //get...set...
}

```

接下来设计该字典项：工作状态，分别代表了失业、工作中、退休，以及学习状态。
```java
public enum WorkState{
    unemployment,
    working,
    retire,
    study
    ;
}
```
接下来设置User的展现类，比如UserView。作为展现给前端的值，需要提供不同的日期格式，薪水的格式也要产生变化，为字符串格式,工作状态也需要一个具体的值来代表
```java
public class UserView{
    private String username;
    private String salary;
    private String workstate;
    private String birthday;

    //get...set...
}
```
然后我们为UserView设置一个对应的字典配置，WorkStateDict,继承展示层字典接口ValueDict
```java
public enum WorkStateDict implements ValueDict{ 
    //需要给每隔选项设置一个具体的值。
    unemployment("0"),
    working("1"),
    retire("2"),
    study("3")
    ;

    //实现以下方法，包括一个value方法和一个key方法，以及一个构造函数。
    private String value;
    WorkStateDict(String value){
        this.value = value;
    }

    //在没有特殊情况下，在你自定义的字典项中，可以直接复制以下方法使用
    @Override
    public String value() {
        return this.value;
    }

    @Override
    public String key() {
        return this.name();
    }
}
```

如果你的UserView需要进行转换，即User -> UserView，那么需要给UserView加一些佐料。

1. 如果需要字典项的转换，则继承BaseBean，以实现枚举类的自动转换

```java
 public class UserView extends BaseBean{
     //省略属性值
     ....

     //覆盖BaseBean中的方法，设置枚举的绑定映射。key值为绑定的字符串名称，value值为绑定的字典项类
     public Map<String, Class> getMappingDictionaryConfig() {
        Map<String,Class> map = new HashMap<>();
        map.put("workstate",WorkStateDict.class);
        return map;
    }
 }

```

2. 如果需要格式化的转换，根据需要选择`ConvertNameHelper`接口（根据字段名称绑定）或者`ConvertTypeHelper`接口（根据字段类型绑定),并覆盖其中的方法

```java
 public class UserView implements ConvertNameHelper{
     //省略属性值
     ....

     //实现并覆盖ConvertNameHelper中的方法，设置转换的绑定映射。ConvertNameBean对象中，参数1为为绑定的字符串名称，参数2为绑定的转换接口实现

     //convertToValueByName主要为BO向DO和VO转换时使用的转换规则，反过来，则需要实现覆盖convertToFiledByName方法
     public List<ConvertNameBean> convertToValueByName() {
        List<ConvertNameBean> convertList = new ArrayList<>();

        //使用FormatRule中已经提供的时间类型格式化
        convertList.add(new ConvertNameBean("birthday", FormatRule.formatDateRule("yyyy_MM_dd:HH_mm_ss:SSS")));

        //使用默认提供的金额类型格式化
        convertList.add(new ConvertNameBean("salary", FormatRule.formatAmountRule("￥##,###.##")));

        return convertList;
    }
 }

```

3. 注：
- BaseBean与ConvertNameHelper、ConvertTypeHelper接口可以`根据需要`分别继承或者实现，不一定非要全部实现。
- 继承或实现了上述接口，没有强制要求实现上述方法。BaseBean抽象类以及Helper接口`已经提供了默认实现，不会造成空指针现象`。
- 我们可以看到，所有的配置和操作均在UserView类中完成，业务模型User不做任何操作，对应的字典项类亦然，`业务模型Bean应该作为单纯的设计存在，第三方组件不应侵入业务模型中`。

4. 最终使用：当需要进行复制时，使用工具类BeanUtils即可进行复制
```java
//从其他地方获取User实例
User user = userService.getUser();

//声明一个UserView对象
UserView userView = new UserView();
BeanUtils.copyBeanExtend(user,userView);

//接下来就可以对UserView做其他操作了
this.update(userView)
```

5. BeanUtils中提供了很多方法，常用的为：
- `copyBeanOnly(S,T);`提供最基础的复制，不对字典项转换，也不对字段进行格式化。`速度最快。建议简单复制`时使用这个方法。
- `copyBeanBase(S,T)`;提供带字典项的转换，被转换的对象如果继承了BaseBean，则会对所配置的字典映射进行转换。
- `copyBeanExtend(S,T)`;提供扩展化的Beans转换，将字典项，格式化以及基本属性都可以转换成功。

其他常用操作可直接查看BeanUtils中的javadoc
