package com.pay.testmapper.service;

import com.pay.pojo.entity.dtm.User;

import java.util.List;

public interface UserService {
    int addUser(User user);

    List<User> findAllUser(int pageNum,int pageSize);
}
