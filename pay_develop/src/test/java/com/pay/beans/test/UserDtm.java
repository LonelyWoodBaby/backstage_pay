package com.pay.beans.test;

import com.pay.beans.dictionary.base.BaseBean;
import com.pay.beans.dictionary.view.UserStatusDict;
import com.pay.pojo.entity.dtm.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDtm extends BaseBean{
    private Integer userId;

    private String userName;

    private String password;

    private String phone;
    private String salary;

    private String birthday;

    private List<User> userList;
    private String userStatusDict;

    public String getUserStatusDict() {
        return userStatusDict;
    }

    public void setUserStatusDict(String userStatusDict) {
        this.userStatusDict = userStatusDict;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Map<String, Class> getMappingDictionaryConfig() {
        Map<String, Class> dictionaryMap = new HashMap<>();
        dictionaryMap.put("userStatusDict", UserStatusDict.class);
        return dictionaryMap;
    }
}
