package com.pay.mybatis;

import com.pay.pojo.entity.dtm.User;
import com.pay.testmapper.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserService userService;

    @Test
    public void testInsert(){
        User user = new User();
        user.setUserId(3);
        user.setPassword("1234");
        user.setPhone("1231");
        user.setUserName("admin");

        userService.addUser(user);
    }
}
