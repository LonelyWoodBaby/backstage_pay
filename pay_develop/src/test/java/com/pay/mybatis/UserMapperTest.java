package com.pay.mybatis;

import com.pay.pojo.entity.dtm.User;
import com.pay.testmapper.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 测试mybatis通用插件使用
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;


    /**
     * 测试添加用户的方法，用于测试通用数据库接口是否好用
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setPassword("1234");
        user.setPhone("1231");
        user.setUserName("admin1");

        userService.insertNewEntity(user);
    }

    /**
     * 测试列表查询及分页查询的方法
     */
    @Test
    public void getPagePersonList(){
        List<User> userList = userService.findAllUser(2,4);
        System.out.println("当前页列表大小"+userList.size());
    }

    /**
     * 测试根据条件获取实例的方法
     */
    @Test
    public void getUserByUserId(){
        int userId = 9;
        User user = userService.getById(userId);
        System.out.println(user.getUserName());
    }

    @Test
    public void getUserByCondition(){
        Condition condition = new Condition(User.class);
        condition.createCriteria().andCondition("user_name like '%1%' ");
        condition.setOrderByClause("user_id desc");
        List<User> userList = userService.findAllByCondition(condition);
        logger.info(condition.toString());
        System.out.println(userList.stream().filter(user -> user.getUserName().equals("admin1")).count());
    }

    @Test
    public void testInsertMoreUser(){
        User[] users = new User[5];
        for(int i=0;i<5;i++){
            users[i] = new User();
            users[i].setUserName("new_user" + i);
            users[i].setPhone("phone" + i);
            users[i].setPassword("password" + i);
        }
        userService.insertAll(users);
    }
    //ProviderSqlSource.createSqlSource
}
