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

## API说明

|方法名| 传入参数 | 返回参数 | 是否已完成 | 简要说明
- | :-: | :-: | :-: | :-: |
|insertNewEntity | T | boolean | <font color='00FF7F'>Y</font> |  插入一条实例数据 
|insertAll | List\<T\> | boolean | <font color='00FF7F'>Y</font> | 将一个列表中的数据全部插入
|insertAll | T... | boolean | <font color='00FF7F'>Y</font> | 使用不定参数，使用方式如上
|deleteByPrimaryKey | T | boolean | <font color='00FF7F'>Y</font> | 根据主键进行删除
|delete | T... | boolean | <font color='FA8072'>N</font> | 传入一个不定参数的实例数组，将其全部删除
|deleteAll | List\<T\> | boolean | <font color='FA8072'>N</font> | 传入一个不定参数的实例列表，将其全部删除
|update | T | boolean | <font color='00FF7F'>Y</font> | 按照`主键`查找对象，并将其修改
|getById | Object | T | <font color='00FF7F'>Y</font> | 根据`主键`查询对象实例
|findAll | Map | List\<T\> | <font color='FA8072'>N</font> | 根据封装好的映射进行查询
|findAll | T | List\<T\> | <font color='00FF7F'>Y</font> | 将对象的条件赋予在实例对象中，并进行查询
|findAllByCondition | Object:condition, <br>int:pageNum, <br>int:pageSize | List\<T\> | <font color='00FF7F'>Y</font> | 将条件放置进condition的条件集合中，并将其封装进分页对象当中
|getPageInfo | T, <br>int:pageNum, <br>int:pageSize | PageInfo\<T\> | <font color='00FF7F'>Y</font> | 将条件放置进dtm的实例对象中，进行分页查询
|getPageInfoByCondition | Object:condition, <br>int:pageNum, <br>int:pageSize | PageInfo\<T\> | <font color='00FF7F'>Y</font> | 将条件放置进condition的条件集合中，进行分页查询



## 未完成工作
1. 条件查询未完成
2. 批量删除方法未完成
3. 设置系统参数，使用默认的分页大小，不再作为参数进行传递。
