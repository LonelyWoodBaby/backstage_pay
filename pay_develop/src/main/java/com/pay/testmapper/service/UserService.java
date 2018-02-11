package com.pay.testmapper.service;

import com.pay.database.dao.BasicDao;
import com.pay.pojo.entity.dtm.User;

import java.util.List;

public interface UserService extends BasicDao<User>{
    int addUser(User user);

    List<User> findAllUser(int pageNum,int pageSize);

    List<User> findAllUserFromCache();
}
