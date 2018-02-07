package com.pay.testmapper.service.impl;

import com.pay.database.mybatis.dao.UserMapper;
import com.pay.pojo.entity.dtm.User;
import com.pay.testmapper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        return null;
    }
}
