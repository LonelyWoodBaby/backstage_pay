# MyBatis通用接口文档

## 使用依赖库：
````xml
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>1.2.3</version>
        </dependency>
````
## 分包说明
1. 与数据库底层交互的Javabean称为DataToModel，将对应的Bean类放置在pojo.entity.dtm包下面。
2. database包下将放置所有有关持久层交互的业务逻辑。不同的持久层可以考虑使用不同的包结构来进行区分。
    - 开发版本中使用mybatis，因此使用mybatis包，并在其下放置mybatis有关配置
3. 通用底层：为com.pay.database.mybatis.config.BaseMapper类，集成了tk.mybatis中的一些基本操作函数。
4. BaseMapper添加了一个默认方法：`findAllByPage`，集成了分页方法类。

## 未完成工作
1. 抽象出一个面对业务层的接口层面，不依赖于持久层。方便以后剥离持久层的开发。
2. 设置系统参数，使用默认的分页大小，不再作为参数进行传递。
