package com.pay.database.dao;

import com.pay.database.dao.entity.PageInfo;

import java.util.List;
import java.util.Map;

public interface BasicDao<T> {
    //增加类

    /**
     * 新增新的用户
     * @param t
     * @return 返回新增后的用户信息，应该包含该用户的ID信息
     */
    boolean insertNewEntity(T t);

    /**
     * 将一个列表中的对象全部插入到库中
     * @param entityList
     * @return 如果插入成功，则返回正确信息
     */
    boolean insertAll(List<T> entityList);

    /**
     * 将一个数组中的对象全部插入到库中，可以使用数组，也可以使用不定参数队列
     * @param t 数组或不定参数队列，但实现代码中应当判断该参数队列不应为空
     * @return 如果全部插入成功，则返回正确信息
     */
    boolean insertAll(T... t);

    //删除类

    /**
     * 根据主键删除对应的数据库实体类
     * @param id 主键
     * @return 删除成功后返回true
     */
    boolean deleteByPrimaryKey(Object id);

    /**
     * 可依据实例对象进行删除，其中包含实例对象数组队列
     * @param t 要删除的实例对象，需要注意判断参数值是否非空
     * @return 删除成功后返回true
     */
    boolean delete(T... t);

    boolean deleteAll(List<T> entityList);

    //修改类

    boolean update(T t);

    //查询类
    /**
     * 根据ID进行查询
     * @param id 为该对象的主键，需要在DTM中添加对应的主键注解信息
     * @return 查询到的返回结果
     */
    T getById(Object id);

//    /**
//     * 根据查询映射返回查询结果
//     * @param selectMap
//     * @return 符合条件的结果集
//     */
////    List<T> findAll(Map selectMap);
    List<T> findAll(T t);

    List<T> findAllByCondition(Object condition);
            //SelectByConditionMapper

    int countAll(T t);
    PageInfo<T> getPageInfo(T t,int pageNum,int pageSize);
    PageInfo<T> getPageInfoByCondition(Object condition,int pageNum,int pageSize);

}
