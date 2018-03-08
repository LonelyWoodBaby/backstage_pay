package com.pay.testmapper.service.impl;

import com.pay.database.dao.entity.PageInfo;
import com.pay.database.dao.impl.BasicDaoImpl;
import com.pay.database.mybatis.config.BaseMapper;
import com.pay.database.mybatis.mapper.UserMapper;
import com.pay.pojo.entity.dtm.User;
import com.pay.testmapper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheResult;
import java.util.List;

@Service
@CacheDefaults(cacheName = "userCache")
public class UserServiceImpl extends BasicDaoImpl<User> implements UserService{
    @Resource
    private UserMapper userMapper;

    @Autowired
    public void setBaseMapper() {
        super.setBaseMapper(userMapper);
    }

    @Override
    public int addUser(User user) {
        return 0;
    }
    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        PageInfo<User> pageInfo = super.getPageInfo(null,pageNum,pageSize);
        return pageInfo.getResultList();
    }

    @Override
    @CacheResult
    public List<User> findAllUserFromCache(){
        return this.findAll(null);
    }
}
