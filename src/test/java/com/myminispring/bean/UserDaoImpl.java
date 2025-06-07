package com.myminispring.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private static final Map<String, String> userMap = new HashMap<>();

    static {
        userMap.put("zhangsan", "beijing");
        userMap.put("lisi", "shanghai");
        userMap.put("wangwu", "hangzhou");
    }

    @Override
    public String queryUserName(String userName) {
        return userMap.get(userName);
    }
}
