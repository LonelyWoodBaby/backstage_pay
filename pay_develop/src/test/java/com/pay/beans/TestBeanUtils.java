package com.pay.beans;

import com.pay.beans.entity.ConvertNameBean;
import com.pay.beans.entity.ConvertTypeBean;
import com.pay.beans.rules.FormatRule;
import com.pay.beans.rules.regulations.ConvertRegulations;
import com.pay.beans.test.UserBean;
import com.pay.beans.test.UserDtm;
import com.pay.pojo.entity.dtm.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestBeanUtils {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testConvertSource2TargetByTypeWithOneRule(){
        UserBean source = createUserBean();
        UserDtm target = createUserDtm();

        FormatRule dateRule = FormatRule.formatDateRule("yyyy年MM月dd日HH时mm分ss秒：SSS");
        ConvertTypeBean bean = new ConvertTypeBean(Double.class,dateRule);

        BeanUtils.copyBeanWithRuleByType(source,target,bean);

        sayUserDtm(target);
    }

    @Test
    public void testConvertSource2TargetByTypeWithRuleList(){
        UserBean source = createUserBean();
        UserDtm target = createUserDtm();

        List<ConvertTypeBean> beanList = ConvertRegulations.MODEL_TO_DATA_MODEL_REGULATION;

        BeanUtils.copyBeanWithRuleByType(source,target,beanList);

        sayUserDtm(target);
    }

    @Test
    public void testConvertSource2TargetByNameWithOneRule(){
        UserDtm source = createUserDtm();
        UserBean target = createUserBean();

        FormatRule salaryRule = FormatRule.convertToDouble("#,###.####");
        ConvertNameBean bean = new ConvertNameBean("salary",salaryRule);

        BeanUtils.copyBeanWithRuleByName(source,target,bean);

        sayUserBean(target);
    }

    @Test
    public void testConvertSource2TargetByNameWithRuleList(){
        UserDtm source = createUserDtm();
        UserBean target = createUserBean();

        List<ConvertNameBean> beanList = ConvertRegulations.VALUE_TO_MODEL_REGULATION;
        BeanUtils.copyBeanWithRuleByName(source,target,beanList);

        sayUserBean(target);
    }

    private UserBean createUserBean(){
        UserBean userBean = new UserBean();
        userBean.setPassword("1234");
        userBean.setPhone("123456789");
        userBean.setUserName("testAdmin");
        userBean.setUserId(12345);
        userBean.setBirthday(new Date());
        userBean.setSalary(1233123.0);
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUserId(1);
        user.setPassword("1111");
        user.setPhone("2222");
        user.setUserName("3333");
        list.add(user);
        user = new User();
        user.setUserId(2);
        user.setPassword("4444");
        user.setPhone("5555");
        user.setUserName("6666");
        list.add(user);
        userBean.setUserList(list);
        return userBean;
    }

    private UserDtm createUserDtm(){
        UserDtm userDtm = new UserDtm();
        userDtm.setPassword("1234");
        userDtm.setPhone("123456789");
        userDtm.setUserName("testAdmin");
        userDtm.setUserId(12345);
        userDtm.setBirthday("2013年06月12日07时28分33秒");
        userDtm.setSalary("22,312,333.9812");
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUserId(1);
        user.setPassword("1111");
        user.setPhone("2222");
        user.setUserName("3333");
        list.add(user);
        user = new User();
        user.setUserId(2);
        user.setPassword("4444");
        user.setPhone("5555");
        user.setUserName("6666");
        list.add(user);
        userDtm.setUserList(list);
        return userDtm;
    }

    private void sayUserBean(UserBean userBean){
        System.out.println(userBean.getPassword());
        System.out.println(userBean.getPhone());
        System.out.println(userBean.getUserName());
        System.out.println(userBean.getUserId());
        System.out.println(userBean.getBirthday());
        System.out.println(userBean.getSalary());
        System.out.println(userBean.getUserList().size());
        System.out.println(userBean.getUserList().get(1).getUserName());
    }

    private void sayUserDtm(UserDtm userDtm){
        System.out.println(userDtm.getPassword());
        System.out.println(userDtm.getPhone());
        System.out.println(userDtm.getUserName());
        System.out.println(userDtm.getUserId());
        System.out.println(userDtm.getBirthday());
        System.out.println(userDtm.getSalary());
        System.out.println(userDtm.getUserList().size());
        System.out.println(userDtm.getUserList().get(1).getUserName());
    }
}
