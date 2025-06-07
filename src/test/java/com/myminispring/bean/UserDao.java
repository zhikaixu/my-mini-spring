package com.myminispring.bean;

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    String queryUserName(String userName);
}
