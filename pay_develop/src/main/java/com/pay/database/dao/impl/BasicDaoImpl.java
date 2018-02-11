package com.pay.database.dao.impl;

import com.github.pagehelper.PageHelper;
import com.pay.database.dao.BasicDao;
import com.pay.database.dao.entity.PageInfo;
import com.pay.database.mybatis.config.BaseMapper;
import com.pay.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Liyabin
 */
@Service
public class BasicDaoImpl<T> implements BasicDao<T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BaseMapper<T> baseMapper;


    @Override
    public boolean insertNewEntity(Object o) {
        return baseMapper.insert((T)o) == 1;
    }

    @Override
    public boolean insertAll(List entityList) {
        return baseMapper.insertList(entityList) == 1;
    }

    @Override
    public boolean insertAll(Object[] arrays) {
        if(arrays == null || arrays.length == 0){
            throw LoggerUtil.throwNewException(new IllegalArgumentException("传递的参数值为空或不存在"),logger);
        }
        return insertAll(Arrays.asList(arrays));
    }

    @Override
    public boolean deleteByPrimaryKey(Object id) {
        return baseMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public boolean delete(Object[] arrays) {
        if(arrays == null || arrays.length == 0){
            throw LoggerUtil.throwNewException(new IllegalArgumentException("传递的参数值为空或不存在"),logger,"删除对象时获取的参数值为空/n ");
        }
        //TODO 完成批量删除的方法
        return false;
    }

    @Override
    public boolean deleteAll(List entityList) {
        //TODO 完成批量删除的方法
        return false;
    }

    @Override
    public boolean update(Object o) {
        return baseMapper.updateByPrimaryKey((T)o) == 1;
    }

    @Override
    public T getById(Object id) {
        return baseMapper.selectByPrimaryKey(id);
    }

//    @Override
//    public List<T> findAll(Map selectMap) {
//        //TODO 完成键值映射的查询方法
//        return null;
//    }

    @Override
    public List<T> findAllByCondition(Object condition) {
        return baseMapper.selectByExample(condition);
    }

    @Override
    public List<T> findAll(Object o) {
        return baseMapper.select((T)o);
    }

    @Override
    public int countAll(Object o) {
        return baseMapper.selectCount((T)o);
    }

    @Override
    public PageInfo<T> getPageInfo(Object o, int pageNum, int pageSize) {
        PageInfo<T> pageInfo = new PageInfo<>();
        try {
            pageInfo.setCount(baseMapper.selectCount((T)o));
            PageHelper.startPage(pageNum,pageSize);
            pageInfo.setResultList(baseMapper.select((T)o));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return pageInfo;
    }

    @Override
    public PageInfo<T> getPageInfoByCondition(Object condition, int pageNum, int pageSize) {
        PageInfo<T> pageInfo = new PageInfo<>();
        try {
            pageInfo.setCount(baseMapper.selectCountByExample(condition));
            PageHelper.startPage(pageNum,pageSize);
            pageInfo.setResultList(baseMapper.selectByExample(condition));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return pageInfo;
    }
}
