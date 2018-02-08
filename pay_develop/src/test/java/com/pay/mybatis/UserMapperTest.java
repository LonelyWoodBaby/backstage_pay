package com.pay.mybatis;

import com.github.pagehelper.PageHelper;
import com.pay.database.mybatis.mapper.UserMapper;
import com.pay.pojo.entity.dtm.User;
import com.pay.testmapper.service.UserService;
import com.pay.testmapper.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试mybatis通用插件使用
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserService userService;


    @Ignore
    public void testInsert(){
        User user = new User();
        user.setPassword("1234");
        user.setPhone("1231");
        user.setUserName("admin1");

        userService.insertNewEntity(user);
    }

    @Test
    public void getPagePersonList(){
        List<User> userList = userService.findAllUser(2,4);
        System.out.println("当前页列表大小"+userList.size());
    }

    @Test
    public void getUserByUserId(){
        int userId = 9;
        User user = userService.getById(userId);
        System.out.println(user.getUserName());
    }

}
