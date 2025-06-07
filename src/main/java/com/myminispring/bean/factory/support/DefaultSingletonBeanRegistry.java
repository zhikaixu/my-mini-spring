package com.myminispring.bean.factory.support;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 单例Bean容器
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    /**
     * 判断是否包含指定名称的单例Bean
     * @param beanName
     * @return 是否包含
     */
    protected boolean containsSingleton(String beanName) {
        return singletonObjects.containsKey(beanName);
    }

    /**
     * 获取所有单例Bean的名称
     * @return 单例Bean名称数组
     */
    protected String[] getSingletonNames() {
        return singletonObjects.keySet().toArray(new String[0]);
    }

    /**
     * 获取单例Bean的数量
     * @return 单例Bean数量
     */
    protected int getSingletonCount() {
        return singletonObjects.size();
    }
}
