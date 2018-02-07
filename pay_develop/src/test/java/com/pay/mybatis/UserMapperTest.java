package com.pay.mybatis;

import com.github.pagehelper.PageHelper;
import com.pay.database.mybatis.mapper.UserMapper;
import com.pay.pojo.entity.dtm.User;
import com.pay.testmapper.service.UserService;
import org.junit.Assert;
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


    @Test
    public void testInsert(){
        User user = new User();
//        user.setUserId(6);
        user.setPassword("1234");
        user.setPhone("1231");
        user.setUserName("admin");

        userService.addUser(user);
    }

    @Test
    public void getPagePersonList(){
        List<User> userList = userService.findAllUser(2,4);
        System.out.println(userList.size());
    }

}
