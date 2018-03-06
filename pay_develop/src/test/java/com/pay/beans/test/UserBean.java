package com.pay.beans.test;

import com.pay.pojo.entity.dtm.User;

import java.util.Date;
import java.util.List;

public class UserBean {
    private Integer userId;

    private String userName;

    private String password;

    private String phone;

    private Double salary;

    private Date birthday;

    private List<User> userList;
    private UserStatusDict userStatusDict;

    public UserStatusDict getUserStatusDict() {
        return userStatusDict;
    }

    public void setUserStatusDict(UserStatusDict userStatusDict) {
        this.userStatusDict = userStatusDict;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
