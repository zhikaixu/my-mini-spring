package com.myminispring.bean;

public class UserService {
    private String name;
    private UserDao userDao;

    public void init() {
        System.out.println("UserService init");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String queryUserInfo() {
        return userDao.queryUserName(name);
    }
}
