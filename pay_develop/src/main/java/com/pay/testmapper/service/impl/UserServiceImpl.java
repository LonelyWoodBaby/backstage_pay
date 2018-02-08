package com.pay.testmapper.service.impl;

import com.pay.database.dao.entity.PageInfo;
import com.pay.database.dao.impl.BasicDaoImpl;
import com.pay.pojo.entity.dtm.User;
import com.pay.testmapper.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BasicDaoImpl<User> implements UserService{

    @Override
    public int addUser(User user) {
        return 0;
    }
//cannot be inherited with different type argument
    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        PageInfo<User> pageInfo = super.getPageInfo(null,pageNum,pageSize);
        System.out.println("查询总数为：" + pageInfo.getCount());
        return pageInfo.getResultList();
    }
}
